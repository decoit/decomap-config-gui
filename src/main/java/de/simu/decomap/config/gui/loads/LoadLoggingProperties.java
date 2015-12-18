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
import de.simu.decomap.config.gui.values.LoggingSettings;

/**
 * Loading logging.properties into data-structure
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 * 
 */
public class LoadLoggingProperties {

	public Properties prop;
	public FileInputStream in;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Constructor
	 * 
	 * @param propertiesPath
	 *            path to the properties which should be loaded
	 */
	public LoadLoggingProperties(final String propertiesPath) {
		try {
			logger.info("Start loading logger.properties");
			prop = new Properties();
			in = new FileInputStream(propertiesPath);
			prop.load(in);
			ConfigSettings.LOADEDPATH = propertiesPath;
			in.close();
		} catch (IOException e) {
			logger.error("Error while loading logger.properties!", e);
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
			LoggingSettings.consoleAppender = prop
					.getProperty("log4j.appender.stdout",
							"org.apache.log4j.ConsoleAppender");
			LoggingSettings.consolePatternLayout = prop.getProperty(
					"log4j.appender.stdout.layout",
					"org.apache.log4j.PatternLayout");
			LoggingSettings.consoelPattern = prop.getProperty(
					"log4j.appender.stdout.layout.ConversionPattern",
					"%d{ABSOLUTE} %p %t: %m%n");
			LoggingSettings.fileAppender = prop.getProperty(
					"log4j.appender.FILE", "org.apache.log4j.FileAppender");
			LoggingSettings.filePatternLayout = prop.getProperty(
					"log4j.appender.FILE.layout",
					"org.apache.log4j.PatternLayout");
			LoggingSettings.filePattern = prop.getProperty(
					"log4j.appender.FILE.layout.conversionPattern",
					"%d{DATE} %p %t: %m%n");
			LoggingSettings.append = Boolean.parseBoolean(prop.getProperty(
					"log4j.appender.FILE.Append", "true"));
			LoggingSettings.immediateFlush = Boolean.parseBoolean(prop
					.getProperty("log4j.appender.FILE.ImmediateFlush", "true"));
			LoggingSettings.logFile = prop.getProperty(
					"log4j.appender.FILE.File", "log/decomap.log");
			LoggingSettings.logLevelConsole = prop.getProperty(
					"log4j.category.de", "INFO, stdout");
			LoggingSettings.logLevelFile = prop.getProperty("log4j.rootLogger",
					"ALL, FILE");

			logger.info("Loading logging.properties successful!");
			return true;
		} catch (Exception e) {
			logger.error("Loading logging.properties failed!");
			return false;
		}
	}

}
