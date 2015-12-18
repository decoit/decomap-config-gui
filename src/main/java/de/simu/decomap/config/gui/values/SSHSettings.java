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
 * SSH-Server settings
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 *
 */
public class SSHSettings {
	
	public static HashMap<String, SSHServer> SERVERLIST = new HashMap<String, SSHServer>();
	
	public static ArrayList<String> SERVERNAMELIST = new ArrayList<String>();
	
}
