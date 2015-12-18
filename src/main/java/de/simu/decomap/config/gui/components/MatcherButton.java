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
package de.simu.decomap.config.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import de.simu.decomap.config.gui.dialogs.MatcherPanelDialog;

/**
 * MatcherButton component for Modeconfig to open a MatcherPanelDialog
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 * 
 */
@SuppressWarnings("serial")
public class MatcherButton extends JButton {

	private final MatcherButton button = this;
	private String textblockText;

	/**
	 * constructor
	 * 
	 */
	public MatcherButton() {
		setText("Edit");
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MatcherPanelDialog(button);
			}
		});
	}

	/**
	 * Set the text of the matcher
	 * 
	 * @param text
	 *            text to set
	 */
	public void setMatcherText(final String text) {
		textblockText = text;
	}

	/**
	 * 
	 * @return Matcher text
	 */
	public String getMatcherText() {
		return textblockText;
	}

}
