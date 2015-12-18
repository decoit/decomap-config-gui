/* 
 * Copyright 2015 DECOIT GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.simu.decomap.config.gui.connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import de.simu.decomap.config.gui.dialogs.SSHServerListDialog;
import de.simu.decomap.config.gui.loads.LoadConfigProperties;
import de.simu.decomap.config.gui.loads.LoadLoggingProperties;
import de.simu.decomap.config.gui.loads.LoadModePropertyFiles;
import de.simu.decomap.config.gui.main.ConfigGuiMain;
import de.simu.decomap.config.gui.values.ConfigSettings;
import de.simu.decomap.config.gui.values.PropertyFileSettings;
import de.simu.decomap.config.gui.values.SSHSettings;

/**
 * Downloading Files over SSH and set Values
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 * 
 */
public class SSHFileDownload {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String[] FILENAMES = new String[] { "config.properties",
	"logging.properties" };
	
	/**
	 * Set loaded values over SSH
	 * 
	 * @param hostName
	 *            Server Name
	 * @param sshServerListDialog
	 *            ParentDialog
	 */
	public void sshPreload(final String hostName,
			SSHServerListDialog sshServerListDialog) {
		String SFTPHOST = SSHSettings.SERVERLIST.get(hostName).getHOST();
		int SFTPPORT = SSHSettings.SERVERLIST.get(hostName).getPORT();
		String SFTPUSER = SSHSettings.SERVERLIST.get(hostName).getUSERNAME();
		String SFTPPASS = SSHSettings.SERVERLIST.get(hostName).getPASSWORD();
		String SFTPWORKINGDIR = SSHSettings.SERVERLIST.get(hostName).getDIR();

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		try {
			logger.info("Connecting to server over SSH!");
			JSch jsch = new JSch();
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			logger.info("Connection established!");
			
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(SFTPWORKINGDIR);
			byte[] buffer = new byte[1024];

			BufferedInputStream bis;
			File newFile;
			OutputStream os;
			BufferedOutputStream bos;

			ArrayList<String[]> dataArray = new ArrayList<String[]>();

			// getting standard files
			for (String filename : FILENAMES) {
				logger.info("Downoading file: "+filename);
				bis = new BufferedInputStream(channelSftp.get(filename));
				newFile = new File("in");
				if (!newFile.exists()) {
					newFile.mkdir();
				}
				newFile = new File("in/" + filename);
				os = new FileOutputStream(newFile);
				bos = new BufferedOutputStream(os);
				int readCount;
				while ((readCount = bis.read(buffer)) > 0) {
					bos.write(buffer, 0, readCount);
				}
				bis.close();
				bos.close();
				logger.info("Downloading complete!");
			}

			logger.info("Start reading basic property-files!");
			LoadConfigProperties configLoader = new LoadConfigProperties(
					"in/config.properties");
			LoadLoggingProperties loggingLoader = new LoadLoggingProperties(
					"in/logging.properties");
			if (configLoader.load() && loggingLoader.load()) {
				logger.info("Loading complete! Start downloading spezific mode propertiy-files");
				if (ConfigSettings.POLLINGMODULE != null) {
					for (int i = 0; i < 4; i++) {
						
						if (PropertyFileSettings.PROPERTIESPATHS
								.get(ConfigSettings.POLLINGMODULE)[i] == null
								|| PropertyFileSettings.PROPERTIESPATHS
										.get(ConfigSettings.POLLINGMODULE)[i]
										.trim().length() == 0) {
							logger.warn("Skipping file "+i+", because path found!");
							continue;
						}
						File dir;
						File file;
						String path = PropertyFileSettings.PROPERTIESPATHS
								.get(ConfigSettings.POLLINGMODULE)[i]
								.substring(
										0,
										PropertyFileSettings.PROPERTIESPATHS
												.get(ConfigSettings.POLLINGMODULE)[i]
												.lastIndexOf("/") + 1);
						logger.info("Downloading file: "+path);
						dir = new File("in/" + path);
						dir.mkdirs();

						channelSftp.cd(SFTPWORKINGDIR
								+ path.replace("config/", ""));

						String fileName = PropertyFileSettings.PROPERTIESPATHS
								.get(ConfigSettings.POLLINGMODULE)[i].replace(
								path, "");

						bis = new BufferedInputStream(channelSftp.get(fileName));

						file = new File(
								"in/"
										+ PropertyFileSettings.PROPERTIESPATHS
												.get(ConfigSettings.POLLINGMODULE)[i]);
						file.createNewFile();

						os = new FileOutputStream(file);
						bos = new BufferedOutputStream(os);
						int readCount;
						while ((readCount = bis.read(buffer)) > 0) {
							bos.write(buffer, 0, readCount);
						}
						bis.close();
						bos.close();
						logger.info("Download complete!");
						logger.info("Start reading file!");

						LoadModePropertyFiles loader = new LoadModePropertyFiles(
								"in/"
										+ PropertyFileSettings.PROPERTIESPATHS
												.get(ConfigSettings.POLLINGMODULE)[i]);
						dataArray.clear();
						for (String line : PropertyFileSettings.PROPERTIESINFOS
								.get(ConfigSettings.POLLINGMODULE).get(i + "")) {
							dataArray.add(line.split(";", 3));
						}

						loader.load(ConfigSettings.POLLINGMODULE, i + "",
								dataArray);
						
						logger.info("Loading complete!");
					}
				}
				logger.info("Load successful!");
				JOptionPane.showMessageDialog(sshServerListDialog,
						"LOAD SUCCESSFUL", "load",
						JOptionPane.INFORMATION_MESSAGE);
				sshServerListDialog.dispose();
			} else {
				logger.error("Loading files over SSH failed!");
				JOptionPane.showMessageDialog(sshServerListDialog,
						"LOAD FAILED!", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception ex) {
			ConfigGuiMain.error("Loading files over SSH failed!" , ex, sshServerListDialog, false);
		}
	}
}
