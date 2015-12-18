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
package de.simu.decomap.config.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.simu.decomap.config.gui.components.TextblockButton;

/**
 * Dialog used for text block setting in ModeConfig
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 *
 */
@SuppressWarnings("serial")
public class TextblockDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final TextblockButton textblockButton;
	private final String splitMode;

	private JTextPane textPane;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Create the dialog.
	 */
	public TextblockDialog(final TextblockButton button, final String splitMode) {
		logger.debug("Creating TextblockDialog");
		setModal(true);
		this.splitMode = splitMode;
		textblockButton = button;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{
				textPane = new JTextPane();
				textPane.setText(formatText(textblockButton.getTextblockText()));
				scrollPane.setViewportView(textPane);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						logger.debug("Setting text and disposing TextBlockDialog");
						textblockButton.setTextblockText(textPane.getText()
								.replace("\n", ""));
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						logger.debug("Disposing TextBlockDialog");
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setLocationRelativeTo(getOwner());
		logger.debug("Creating TextblockDialog compelte");
		setVisible(true);
	}

	/**
	 * Split the text and place linebreaks instead	
	 * @param text
	 * 		text to formate
	 * @return
	 * 		formated text
	 */
	private String formatText(String text) {
		if (splitMode.equals("sql")) {
			String split[] = text.split(" [A-Z][A-Z]+ ");
			for (String substring : split) {
				text = text.replace(substring + " ", substring + " \n");
			}
			return text.replace(",", ",\n");
		} else if(splitMode.equals("list")){
			return text.replace(",", ",\n");
		} else if(splitMode.equals("semi")){
			return text.replace(";", ";\n");
		} else{
			return text;
		}
	}
}
