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

import java.util.concurrent.Semaphore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hshannover.f4.trust.ifmapj.IfmapJ;
import de.hshannover.f4.trust.ifmapj.IfmapJHelper;
import de.hshannover.f4.trust.ifmapj.channel.SSRC;
import de.hshannover.f4.trust.ifmapj.exception.InitializationException;
import de.simu.decomap.config.gui.main.ConfigGuiMain;

/**
 * Class to Test connection to Map Server
 * @author Leonid Schwenke, DECOIT GmbH
 *
 */
public class ConnectionTest {

	private SSRC mSSRC = null;
	private Semaphore mSemaphore;
	private int maxPoll = 0;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * constructor
	 * 
	 * @param url
	 * 		server url
	 * @param keyPath
	 * 		keypath for server
	 * @param keyPass
	 * 		keypassword for server
	 * @param trustPath
	 * 		trustpath for server
	 * @param trustPass
	 * 		trustpassword for server
	 * @param enableBasicAuth
	 * 		authentification with server
	 * @param basicUser
	 * 		username
	 * @param basicPass
	 * 		userpassword
	 * @param mp
	 * 		maximal Polling
	 * @throws InitializationException
	 */
	public ConnectionTest(String url, String keyPath, String keyPass,
			String trustPath, String trustPass, boolean enableBasicAuth,
			String basicUser, String basicPass, int mp)
			throws InitializationException {
		logger.info("Prepering test-connection to map-server");
		
		TrustManager[] tms = IfmapJHelper
				.getTrustManagers(trustPath, trustPass);
		if (enableBasicAuth) {
			mSSRC = IfmapJ.createSSRC(url, basicUser, basicPass, tms);
		} else {
			KeyManager[] kms = IfmapJHelper.getKeyManagers(keyPath, keyPass);
			mSSRC = IfmapJ.createSSRC(url, kms, tms);
		}
		mSemaphore = new Semaphore(1);
		maxPoll = mp;
	}

	/**
	 * Opening new session
	 * @return
	 * 	true on success
	 */
	public boolean test() {
		try {
			logger.info("Connecting to map-server...");
			if (maxPoll == 0) {
				mSSRC.newSession();
			} else {
				mSSRC.newSession(maxPoll);
			}
			logger.info("Connection successful!");
			return true;
		} catch (Exception e) {
			logger.warn("Testing connection to map-server failed", e);
			return false;
		}

	}

	/**
	 * Ending session
	 */
	public void disconnect() {
		if (mSSRC != null) {
			logger.info("Closing connection to map-server...");
			try {
				mSemaphore.acquire();
				mSSRC.endSession();
				mSSRC.closeTcpConnection();
				mSemaphore.release();
			} catch (Exception e) {
				mSemaphore.release();
				ConfigGuiMain.error("Error while disconnecting from connection test!" , e, null, false);
			}
			logger.info("Diconnect successful");
		}
	}

}
