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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.simu.decomap.config.gui.components.MatcherButton;
import de.simu.decomap.config.gui.panels.MatcherPanel;

import javax.swing.JScrollPane;

import java.awt.GridLayout;

import javax.swing.ScrollPaneConstants;

/**
 * Dialog used for matcher settings in ModeConfig
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 * 
 */
@SuppressWarnings("serial")
public class MatcherPanelDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Create the dialog.
	 */
	public MatcherPanelDialog(final MatcherButton button) {
		setModal(true);
		logger.debug("Creating MatcherPanelDialog");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPanel.add(scrollPane);

		final MatcherPanel matcher = new MatcherPanel();
		matcher.setValue(button.getMatcherText());

		JPanel boxPanel = new JPanel();
		scrollPane.setViewportView(boxPanel);
		boxPanel.setLayout(new BorderLayout(0, 0));

		boxPanel.add(matcher, BorderLayout.NORTH);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						logger.debug("Setting text and disposing TextBlockDialog");
						button.setMatcherText(matcher.getValues());
						dispose();
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						logger.debug("Disposing MatcherDialog");
						dispose();
					}
				});
			}
		}
		
		setMinimumSize(new Dimension(500, 300));
		pack();
		scrollPane.setPreferredSize(new Dimension(getX(), 400));
		
		setLocationRelativeTo(getOwner());
		setVisible(true);
	}

}
