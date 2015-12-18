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
package de.simu.decomap.config.gui.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.simu.decomap.config.gui.values.ConfigSettings;
import de.simu.decomap.config.gui.values.LoggingSettings;
import de.simu.decomap.config.gui.values.PropertyFileSettings;

/**
 * 
 * Exporting given files on a local directory
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 * 
 */
public class ExportFiles {

	private static BufferedWriter bw;
	private static String folder;

	private final static Logger logger = LoggerFactory
			.getLogger("ExportFiles.java");

	/**
	 * exporting the setting into properties files
	 * 
	 * @return success
	 */
	public static boolean export(final boolean configProperties,
			final boolean loggingProperties, final ArrayList<String> files,
			final String path) {
		logger.info("Starting export!");
		folder = path;
		File dir = new File(folder);
		dir.mkdirs();
		boolean success = true;
		if (configProperties) {
			if (!createConfigProperties()) {
				success = false;
			}
		}
		if (loggingProperties) {
			if (!createLoggingProperties()) {
				success = false;
			}
		}
		if (files != null && files.size() > 0) {
			if (!creatSpecficFiles(files)) {
				success = false;
			}
		}
		return success;
	}

	/**
	 * creating config.properties
	 * 
	 * @return Success
	 */
	private static Boolean createConfigProperties() {
		try {
			logger.info("Start exporting config.properties");
			File config = new File(folder + "/config.properties");
			config.createNewFile();
			bw = new BufferedWriter(new FileWriter(config, false));

			bw.write("application.version=" + ConfigSettings.VERSION + "\n");
			bw.write("application.polling.interval="
					+ ConfigSettings.POLLINGINTERVALL + "\n");
			bw.write("application.messaging.sendold=" + ConfigSettings.SENDOLD
					+ "\n");
			bw.write("application.ipaddress=" + ConfigSettings.IPADDRESS + "\n");
			bw.write("application.isservice=" + ConfigSettings.ISSERVICE + "\n");
			bw.write("application.serviceport=" + ConfigSettings.PORT + "\n");
			bw.write("application.servicetype=" + ConfigSettings.SERVICETYPE
					+ "\n");
			bw.write("application.servicename=" + ConfigSettings.SERVICENAME
					+ "\n");
			bw.write("application.administrativdomain="
					+ ConfigSettings.ADMINISTRATIVDOMAIN + "\n");

			bw.write("application.component=" + ConfigSettings.POLLINGMODULE
					+ "\n");

			String[] paths = PropertyFileSettings.PROPERTIESPATHS
					.get(ConfigSettings.POLLINGMODULE);
			if (paths != null) {
				bw.write("application.pollingconfig.path=" + paths[0] + "\n");
				bw.write("application.mappingconfig.path=" + paths[1] + "\n");
				bw.write("application.regexconfig.path=" + paths[2] + "\n");
				if (paths[3] != null && paths[3].trim().length() > 0) {
					bw.write("application.pollresultfilterconfig.path="
							+ paths[3] + "\n");
				}
			}

			bw.write("mapserver.url=" + ConfigSettings.MAPSERVERADDRESS + "\n");
			bw.write("mapserver.keystore.path=" + ConfigSettings.KEYSTOREPATH
					+ "\n");
			bw.write("mapserver.keystore.password="
					+ ConfigSettings.KEYSTOREPASSWORD + "\n");
			bw.write("mapserver.truststore.path="
					+ ConfigSettings.TRUSTSTOREPATH + "\n");
			bw.write("mapserver.truststore.password="
					+ ConfigSettings.TRUSTSTOREPASSWORD + "\n");
			bw.write("mapserver.basicauth.enabled="
					+ ConfigSettings.MAPSERVERAUTHENTIFICATION + "\n");
			if (ConfigSettings.MAPSERVERAUTHENTIFICATION) {
				bw.write("mapserver.basicauth.user="
						+ ConfigSettings.MAPSERVERUSER + "\n");
				bw.write("mapserver.basicauth.password="
						+ ConfigSettings.MAPSERVERPASSWORD + "\n");
			}
			bw.close();
		} catch (Exception e) {
			logger.error("Error while exporting config.properties!", e);
			return false;
		}
		logger.info("Export config.properties complete!");
		return true;
	}

	/**
	 * creating logging.properties
	 * 
	 * @return Success
	 */
	private static Boolean createLoggingProperties() {
		try {
			logger.info("Start exporting logging.properties");
			File logging = new File(folder + "/logging.properties");
			logging.createNewFile();
			bw = new BufferedWriter(new FileWriter(logging, false));

			bw.write("log4j.appender.stdout=" + LoggingSettings.consoleAppender
					+ "\n");
			bw.write("log4j.appender.stdout.layout="
					+ LoggingSettings.consolePatternLayout + "\n");
			bw.write("log4j.appender.stdout.layout.ConversionPattern="
					+ LoggingSettings.consoelPattern + "\n");

			bw.write("log4j.appender.FILE=" + LoggingSettings.fileAppender
					+ "\n");
			bw.write("log4j.appender.FILE.File=" + LoggingSettings.logFile
					+ "\n");
			bw.write("log4j.appender.FILE.Append=" + LoggingSettings.append
					+ "\n");
			bw.write("log4j.appender.FILE.ImmediateFlush="
					+ LoggingSettings.immediateFlush + "\n");
			bw.write("log4j.appender.FILE.layout="
					+ LoggingSettings.filePatternLayout + "\n");
			bw.write("log4j.appender.FILE.layout.conversionPattern="
					+ LoggingSettings.filePattern + "\n");
			bw.write("log4j.category.de=" + LoggingSettings.logLevelConsole
					+ "\n");
			bw.write("log4j.rootLogger=" + LoggingSettings.logLevelFile + "\n");
			bw.close();

		} catch (Exception e) {
			logger.error("Error while exporting logging.properties!", e);
			return false;
		}
		logger.info("Export logging.properties complete!");
		return true;
	}

	/**
	 * creating specific files needed for the selected mode and polling
	 * combination
	 * 
	 * @param files
	 *            selected files which should be created
	 * @return success
	 */
	public static boolean creatSpecficFiles(final ArrayList<String> files) {
		try {

			for (int i = 0; i < 4; i++) {
				if (PropertyFileSettings.PROPERTIESPATHS
						.get(ConfigSettings.POLLINGMODULE)[i] == null
						|| PropertyFileSettings.PROPERTIESPATHS
								.get(ConfigSettings.POLLINGMODULE)[i].trim()
								.length() == 0) {
					continue;
				}
				File dir;
				File buffer;
				dir = new File(
						folder
								+ "/"
								+ PropertyFileSettings.PROPERTIESPATHS
										.get(ConfigSettings.POLLINGMODULE)[i]
										.substring(
												0,
												PropertyFileSettings.PROPERTIESPATHS
														.get(ConfigSettings.POLLINGMODULE)[i]
														.lastIndexOf("/") + 1).replace("config/", ""));
				dir.mkdirs();
				buffer = new File(
						folder
								+ "/"
								+ PropertyFileSettings.PROPERTIESPATHS
										.get(ConfigSettings.POLLINGMODULE)[i].replace("config/", ""));
				logger.info("Exporting " + buffer);
				buffer.createNewFile();
				bw = new BufferedWriter(new FileWriter(buffer, false));

				for (String line : PropertyFileSettings.PROPERTIESINFOS.get(
						ConfigSettings.POLLINGMODULE).get(i + "")) {
					bw.write(line.split(";", 3)[2] + "\n");
				}
				bw.close();
				logger.info("Exporting " + buffer + " complete!");
			}
		} catch (Exception e) {
			logger.error("Error while exporting mode-specific properties!", e);
			return false;
		}
		logger.info("Export for all mode-specific files complete!");
		return true;
	}
}
