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
package de.simu.decomap.config.gui.values;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Settings for each property-file for each mode
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 *
 */
public class PropertyFileSettings {

	//ArrayList in reference to a mode which contains properties lines
	public static HashMap<String, HashMap<String, ArrayList<String>>> PROPERTIESINFOS = new HashMap<String,HashMap<String, ArrayList<String>>>();
	
	//ArrayList in reference to a mode which contains properties lines
	public static HashMap<String, String[]> PROPERTIESPATHS = new HashMap<String, String[]>();
	
	//ArrayList with all modes
	public static ArrayList<String> MODULE = new ArrayList<String>();

}
