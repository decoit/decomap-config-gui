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
 * Represent a SSH-Server
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 *
 */
public class SSHServer {

	public String name;
	public String host;
	public int port = 22;
	public String username;
	public String password;
	public String dir;

	
	public SSHServer(){
	}
	
	public SSHServer(final String name, final String host, final int port, final String username, final String password, final String dir){
		this.name = name;
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.dir = dir;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String nAME){
		name = nAME;
	}
	
	public String getHOST() {
		return host;
	}

	public void setHOST(String hOST) {
		host = hOST;
	}

	public int getPORT() {
		return port;
	}

	public void setPORT(int pORT) {
		port = pORT;
	}

	public String getUSERNAME() {
		return username;
	}

	public void setUSERNAME(String uSERNAME) {
		username = uSERNAME;
	}

	public String getPASSWORD() {
		return password;
	}

	public void setPASSWORD(String pASSWORD) {
		password = pASSWORD;
	}

	public String getDIR() {
		return dir;
	}

	public void setDIR(String dIR) {
		dir = dIR;
	}

}
