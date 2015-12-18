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
 * logging.properties settings
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 *
 */
public class LoggingSettings {

	//console logging
	public static String consoleAppender = "org.apache.log4j.ConsoleAppender";
	public static String consolePatternLayout = "org.apache.log4j.PatternLayout";
	public static String consoelPattern = "%d{ABSOLUTE} %p %t: %m%n";
	
	//file logging
	public static String fileAppender = "org.apache.log4j.FileAppender";
	public static String filePatternLayout = "org.apache.log4j.PatternLayout";
	public static String filePattern = "%d{DATE} %p %t: %m%n";
	public static boolean append = true;
	public static boolean immediateFlush = true;
	public static String logFile = "log/decomap.log";
	
	//logging
	public static String logLevelFile = "DEBUG, FILE";
	public static String logLevelConsole = "INFO, stdout";
}
