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
import java.util.ArrayList;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.simu.decomap.config.gui.values.ConfigSettings;
import de.simu.decomap.config.gui.values.PropertyFileSettings;

/**
 * Loading all kinds of properties-files for different modes
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 *
 */
public class LoadModePropertyFiles {

	public Properties prop;
	public FileInputStream in;
	private ArrayList<String> values = new ArrayList<String>();

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Constructor
	 * 
	 * @param propertiesPath
	 *            path to the properties which should be loaded
	 */
	public LoadModePropertyFiles(final String propertiesPath) {
		try {
			logger.info("Start loading file from: "+propertiesPath);
			prop = new Properties();
			in = new FileInputStream(propertiesPath);
			prop.load(in);
			ConfigSettings.LOADEDPATH = propertiesPath;
			in.close();
		} catch (IOException e) {
			logger.error("Error while loading file!", e);
			// returning false on load
		}
	}

	/**
	 * 
	 * Loading the properties and setting the values
	 * 
	 * @param mode
	 * 		mode settings to load
	 * @param propertieFile
	 * 		propertieFile settings to load
	 * @param dataArray
	 * 		Reference data
	 * @return
	 * 		Successful?
	 */
	public boolean load(final String mode, final String propertieFile,
			final ArrayList<String[]> dataArray) {
		try {
			String[] data;
			for (int i = 0; i < dataArray.size(); i++) {
				data = dataArray.get(i)[2].split("=", 2);
				values.add(dataArray.get(i)[0]+";"+dataArray.get(i)[1]+";"+data[0] + "=" + prop.getProperty(data[0], data[1]).replace("\\", "\\\\"));
			}
			PropertyFileSettings.PROPERTIESINFOS.get(mode).put(propertieFile, values);
		} catch (Exception e) {
			logger.error("Error while loading file "+propertieFile+"!", e);
			return false;
		}
		logger.info("Loading successful!");
		return true;
	}
}
