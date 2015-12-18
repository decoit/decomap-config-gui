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
package de.simu.decomap.config.gui.panels;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import javax.swing.JCheckBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.BorderLayout;

/**
 * Panel to configure ResultProcessor rules
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 *
 */
@SuppressWarnings("serial")
public class MatcherPanel extends JPanel {
	
	
	private JTextField textField;
	private JTextField attributeValueTextField;
	private JTextField typeValueTextfield;
	private JPanel valuePanel;
	private JButton addButton;
	private JButton removeButton;
	private JCheckBox chckbxSkip;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Create the panel.
	 * 
	 * @param dialog
	 */
	public MatcherPanel() {
		logger.debug("Creating MatcherPanel");
		setLayout(new BorderLayout(0, 0));

		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.NORTH);

		addButton = new JButton("+");

		buttonPanel.add(addButton);

		removeButton = new JButton(" - ");
		removeButton.setEnabled(false);
		buttonPanel.add(removeButton);

		chckbxSkip = new JCheckBox("skip");
		buttonPanel.add(chckbxSkip);
		
				valuePanel = new JPanel();
				add(valuePanel);
				valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.Y_AXIS));

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.debug("Adding row!");
				valuePanel.add(getNewRow());
				removeButton.setEnabled(true);
				updateUI();
//				pack();
			}
		});

		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.debug("removing row!");
				if (valuePanel.getComponentCount() <= 1) {
					removeButton.setEnabled(false);
				}
				valuePanel.remove(valuePanel.getComponentCount() - 1);
				updateUI();
//				pack();
			}
		});

		chckbxSkip.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				logger.debug("Using Skip!");
				addButton.setVisible(!chckbxSkip.isSelected());
				removeButton.setVisible(!chckbxSkip.isSelected());
				valuePanel.setVisible(!chckbxSkip.isSelected());
				updateUI();
//				pack();
			}
		});
		logger.debug("Creating MatcherPanel complete!");
	}

	@SuppressWarnings("unchecked")
	public void setValue(String values) {
		logger.debug("Saving matcherPanel");
		String[] settings = values.split(",");
		for (String setting : settings) {
			String[] split = setting.split("_", 4);
			String name = split[0];
			if (name.equals("skip")) {
				chckbxSkip.doClick();
			} else {
				String attribute = null;
				String operator = null;
				String value = null;
				String filter = "matches";
				if (split.length == 4) {
					attribute = split[1];
					operator = split[2];
					value = split[3];

					switch (operator) {
					case "c":
						filter = "contains";
						break;
					case "m":
						filter = "matches";
						break;
					default:
						filter = "matches";
					}

				}

				addButton.doClick();
				JPanel newPanel = (JPanel) valuePanel.getComponent(valuePanel
						.getComponentCount() - 1);
				valuePanel.add(newPanel);

				((JTextField) newPanel.getComponent(1)).setText(name);
				((JTextField) newPanel.getComponent(3)).setText(attribute);
				((JComboBox<String>) newPanel.getComponent(4))
						.setSelectedItem(filter);
				((JTextField) newPanel.getComponent(5)).setText(value);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public String getValues() {
		logger.debug("Loading MatcherPanel values");
		String entry = "";
		if (chckbxSkip.isSelected()) {
			entry = "skip";
		} else {
			for (int i = 0; i < valuePanel.getComponentCount(); i++) {
				if (i != 0) {
					entry = entry + ",";
				}
				JPanel panel = (JPanel) valuePanel.getComponent(i);

				String name = ((JTextField) panel.getComponent(1)).getText();
				String attribute = ((JTextField) panel.getComponent(3))
						.getText();
				String filter = (String) ((JComboBox<String>) panel
						.getComponent(4)).getSelectedItem();
				String value = ((JTextField) panel.getComponent(5)).getText();

				switch (filter) {
				case "contains":
					filter = "c";
					break;
				case "matches":
					filter = "m";
					break;
				}
				entry = entry + name + "_" + attribute + "_" + filter + "_"
						+ value;
			}
		}
		return entry;
	}

	/**
	 * Create a new configuration row
	 * 
	 * @return
	 * 		new row
	 */
	private JPanel getNewRow() {
		logger.debug("creating new row!");
		JPanel rowPanel = new JPanel();
		rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel newTypeLabel = new JLabel("Type:");
		rowPanel.add(newTypeLabel);

		typeValueTextfield = new JTextField();
		rowPanel.add(typeValueTextfield);
		typeValueTextfield.setColumns(10);

		JLabel newAttributeLabel = new JLabel("Attribute:");
		rowPanel.add(newAttributeLabel);

		attributeValueTextField = new JTextField();
		rowPanel.add(attributeValueTextField);
		attributeValueTextField.setColumns(10);

		JComboBox<String> matchValueCombobox = new JComboBox<String>();
		matchValueCombobox.setModel(new DefaultComboBoxModel<String>(new String[] {
				"contains", "matches" }));
		rowPanel.add(matchValueCombobox);

		textField = new JTextField();
		textField.setColumns(10);
		rowPanel.add(textField);

		return rowPanel;
	}

}
