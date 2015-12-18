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

/**
 * config.Properties settings
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 * 
 */
public class ConfigSettings {

	// current version string
	public static String VERSION = "0.2";

	// interval for polling-threads
	public static int POLLINGINTERVALL = 20;

	// Type of IF-MAP-Message that will be send to MAP server (update/notify)
	// public static String MESSAGINGTYPE = "update";

	// flag for controlling whether the read in events that happened before the
	// ifmapclient started should
	// also be send to MAP-Server or should be skipped
	public static boolean SENDOLD = false;

	// ip-address of the machine where the ip-tables-client runs(atm only for
	// iptables)
	public static String IPADDRESS = "";

	// Is Service?
	public static boolean ISSERVICE = false;
	
	// Port of the Service
	public static String PORT = "";

	// Service Typ of the client
	public static String SERVICETYPE = "";

	// Service Name of the client
	public static String SERVICENAME = "";

	// clients ad
	public static String ADMINISTRATIVDOMAIN = "";

	// wich component should be used
	public static String POLLINGMODULE = null;

	// mapserver address
	public static String MAPSERVERADDRESS = "";

	// Path to the keystore to be used by irondhcp
	public static String KEYSTOREPATH = "/keystore/iptablesmap.jks";

	// password for the keystore
	public static String KEYSTOREPASSWORD = "iptablesmap";

	// Path to the truststore to be used by irondhcp
	public static String TRUSTSTOREPATH = "/keystore/iptablesmap.jks";

	// password for the truststore
	public static String TRUSTSTOREPASSWORD = "iptablesmap";

	// Whether or not to use basic authentication
	public static boolean MAPSERVERAUTHENTIFICATION = false;

	// Username used for basic authentication
	public static String MAPSERVERUSER = "";

	// Password used for basic authentication
	public static String MAPSERVERPASSWORD = "";

	// loaded path
	public static String LOADEDPATH;

}
