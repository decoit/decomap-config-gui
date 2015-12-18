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
package de.simu.decomap.config.gui.loads;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.simu.decomap.config.gui.values.ConfigSettings;
import de.simu.decomap.config.gui.values.PropertyFileSettings;

/**
 * Loading config.properties into data-structure
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 * 
 */
public class LoadConfigProperties {

	private Properties prop;
	private FileInputStream in;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * construktor
	 * 
	 * @param propertiesPath
	 *            path to the properties which should be loaded
	 */
	public LoadConfigProperties(final String propertiesPath) {
		try {
			logger.info("Start loading config.properties from: "
					+ propertiesPath);
			prop = new Properties();
			in = new FileInputStream(propertiesPath);
			prop.load(in);
			ConfigSettings.LOADEDPATH = propertiesPath;
			in.close();
		} catch (IOException e) {
			logger.error("Error while loading config.proeprties!", e);
			// returning false on load
		}
	}

	/**
	 * Loading the properties and setting the values
	 * 
	 * @return Successful?
	 */
	public boolean load() {
		try {
			ConfigSettings.VERSION = prop.getProperty("application.version",
					"0.2");
			ConfigSettings.POLLINGINTERVALL = Integer.parseInt(prop
					.getProperty("application.polling.interval", "20"));
			ConfigSettings.SENDOLD = Boolean.parseBoolean(prop.getProperty(
					"application.messaging.sendold", "false"));
			ConfigSettings.IPADDRESS = prop
					.getProperty("application.ipaddress");
			ConfigSettings.ISSERVICE = Boolean.parseBoolean(prop.getProperty(
					"application.isservice", "false"));
			if (ConfigSettings.ISSERVICE) {
				ConfigSettings.PORT = prop
						.getProperty("application.serviceport", "0");
				ConfigSettings.SERVICETYPE = prop
						.getProperty("application.servicetype", "");
				ConfigSettings.SERVICENAME = prop
						.getProperty("application.servicename", "");
				ConfigSettings.ADMINISTRATIVDOMAIN = prop
						.getProperty("application.administrativdomain", "");
			}
			ConfigSettings.POLLINGMODULE = prop
					.getProperty("application.component");

			String[] paths = new String[4];
			paths[0] = prop.getProperty("application.pollingconfig.path");
			paths[1] = prop.getProperty("application.mappingconfig.path");
			paths[2] = prop.getProperty("application.regexconfig.path");
			if (PropertyFileSettings.PROPERTIESPATHS
					.get(ConfigSettings.POLLINGMODULE)[3] != null) {
				paths[3] = prop
						.getProperty("application.pollresultfilterconfig.path");
			}
			for (int i = 0; i < 4; i++) {
				if (PropertyFileSettings.PROPERTIESPATHS
						.get(ConfigSettings.POLLINGMODULE)[i] != null) {
					if (paths[i] == null) {
						paths[i] = PropertyFileSettings.PROPERTIESPATHS
								.get(ConfigSettings.POLLINGMODULE)[i];
					}
				}
			}
			PropertyFileSettings.PROPERTIESPATHS.put(
					ConfigSettings.POLLINGMODULE, paths);

			ConfigSettings.MAPSERVERADDRESS = prop.getProperty("mapserver.url");
			ConfigSettings.KEYSTOREPATH = prop.getProperty(
					"mapserver.keystore.path", "/keystore/iptablesmap.jks");
			ConfigSettings.KEYSTOREPASSWORD = prop.getProperty(
					"mapserver.keystore.password", "iptablesmap");
			ConfigSettings.TRUSTSTOREPATH = prop.getProperty(
					"mapserver.truststore.path", "/keystore/iptablesmap.jks");
			ConfigSettings.TRUSTSTOREPASSWORD = prop.getProperty(
					"mapserver.truststore.password", "iptablesmap");
			ConfigSettings.MAPSERVERAUTHENTIFICATION = Boolean
					.parseBoolean(prop.getProperty(
							"mapserver.basicauth.enabled", "false"));
			if (ConfigSettings.MAPSERVERAUTHENTIFICATION) {
				ConfigSettings.MAPSERVERUSER = prop
						.getProperty("mapserver.basicauth.user");
				ConfigSettings.MAPSERVERPASSWORD = prop
						.getProperty("mapserver.basicauth.password");
			}

			logger.info("Loading config.proeprties successful!");
			return true;
		} catch (Exception e) {
			logger.error("Loading config.properties failed!", e);
			return false;
		}
	}

}
