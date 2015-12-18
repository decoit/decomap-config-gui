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

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * Exporting Files over SSH
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 *
 */
public class SSHFileExport {

	private final String SFTPHOST;
	private final int SFTPPORT;
	private final String SFTPUSER;
	private final String SFTPPASS;
	private String SFTPWORKINGDIR;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * constructor
	 * 
	 * @param host
	 * 		Server ip
	 * @param port
	 * 		Server port
	 * @param username
	 * 		server login username
	 * @param password
	 * 		server login password
	 * @param exportDir
	 * 		export directory, where to put the files
	 */
	public SSHFileExport(final String host, final int port,
			final String username, final String password, final String exportDir) {
		SFTPHOST = host;
		SFTPPORT = port;
		SFTPUSER = username;
		SFTPPASS = password;
		SFTPWORKINGDIR = exportDir;
	}

	/**
	 * change export dir
	 * 
	 * @param exportDir
	 * 		dir to export to
	 */
	public void changeDir(final String exportDir){
		SFTPWORKINGDIR = exportDir;
	}
	
	/**
	 * exporting files to the server
	 * 
	 * @param filePaths
	 * 		files to export
	 * 
	 * @return
	 * 		success
	 */
	public boolean export(final ArrayList<String> filePaths) {
		logger.info("Starting SSH export!");
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		String[] dirs;

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			dirs = SFTPWORKINGDIR.split("/");
			if(SFTPWORKINGDIR.startsWith("/")){
				dirs[0] = "/"+dirs[0];
			}
			for(String dir : dirs){
				if(dir.length() > 0){
					try{
						channelSftp.cd(dir);
					} catch(Exception e){
						channelSftp.mkdir(dir);
						channelSftp.cd(dir);
					}
				}
			}
			for (String filePath : filePaths) {
				File f = new File(filePath);
				logger.info("Sending file: "+f.getName());
				channelSftp.put(new FileInputStream(f), f.getName());
			}
			logger.info("Export complete!");
			return true;
		} catch (Exception ex) {
			logger.error("Export failed!", ex);
			return false;
		}

	}
}
