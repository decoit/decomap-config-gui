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

import javax.swing.JDialog;

import de.simu.decomap.config.gui.main.ConfigGuiMain;
import de.simu.decomap.config.gui.panels.PropertieFilesPanel;
import de.simu.decomap.config.gui.values.ConfigSettings;
import de.simu.decomap.config.gui.values.PropertyFileSettings;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JTabbedPane;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dialog to configure each Module and his files
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 * 
 */
@SuppressWarnings("serial")
public class ModeConfigDialog extends JDialog {

	private JTabbedPane tabbedPane;
	private JComboBox<String> pollingModuleCombobox;
	private ConfigGuiMain mainFrame;
	private boolean moduleSet = false;

	private ArrayList<Component> componentPuffer = new ArrayList<Component>();

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Create the dialog.
	 * 
	 * @param mainFrame
	 */
	public ModeConfigDialog(ConfigGuiMain mainFrame) {
		super(mainFrame);
		this.mainFrame = mainFrame;
		initGUI();
	}

	/**
	 * init the GUI components
	 */
	private void initGUI() {
		logger.debug("Creating ModeConfigDialog");
		setTitle("Mode Config Dialog");
		setBounds(100, 100, 580, 450);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel modulePanel = new JPanel();
		getContentPane().add(modulePanel, BorderLayout.NORTH);
		modulePanel.setLayout(new BorderLayout(0, 0));

		JPanel moduleAktivePanel = new JPanel();
		moduleAktivePanel.setBorder(new EmptyBorder(5, 0, 5, 0));
		modulePanel.add(moduleAktivePanel, BorderLayout.NORTH);
		GridBagLayout gbl_moduleAktivePanel = new GridBagLayout();
		gbl_moduleAktivePanel.columnWidths = new int[] { 20, 130, 30, 312, 0 };
		gbl_moduleAktivePanel.rowHeights = new int[] { 25, 0 };
		gbl_moduleAktivePanel.columnWeights = new double[] { 0.0, 0.0, 0.0,
				1.0, Double.MIN_VALUE };
		gbl_moduleAktivePanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		moduleAktivePanel.setLayout(gbl_moduleAktivePanel);

		pollingModuleCombobox = new JComboBox<String>();
		pollingModuleCombobox.setModel(new DefaultComboBoxModel<String>(
				PropertyFileSettings.MODULE.toArray(new String[0])));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		moduleAktivePanel.add(pollingModuleCombobox, gbc_comboBox);

		final JCheckBox chckbxNewCheckBox = new JCheckBox("Aktiv");
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxNewCheckBox.fill = GridBagConstraints.BOTH;
		gbc_chckbxNewCheckBox.gridx = 2;
		gbc_chckbxNewCheckBox.gridy = 0;
		moduleAktivePanel.add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);
		chckbxNewCheckBox.setSelected(((String) pollingModuleCombobox
				.getSelectedItem()).equals(ConfigSettings.POLLINGMODULE));

		pollingModuleCombobox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				chckbxNewCheckBox.setSelected(((String) pollingModuleCombobox
						.getSelectedItem())
						.equals(ConfigSettings.POLLINGMODULE));
				save();
				tabbedPane.removeAll();
				setTabs();
				moduleSet = true;
			}
		});

		chckbxNewCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (chckbxNewCheckBox.isSelected()) {
					ConfigSettings.POLLINGMODULE = (String) pollingModuleCombobox
							.getSelectedItem();
				} else {
					if (((String) pollingModuleCombobox.getSelectedItem())
							.equals(ConfigSettings.POLLINGMODULE)) {
						ConfigSettings.POLLINGMODULE = null;
					}
				}
				logger.debug("New active module selected: "
						+ ConfigSettings.POLLINGMODULE);
			}
		});

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
					if (i == tabbedPane.getSelectedIndex()) {
						tabbedPane.setComponentAt(
								tabbedPane.getSelectedIndex(), componentPuffer
										.get(tabbedPane.getSelectedIndex()));
					} else {
						tabbedPane.setComponentAt(i, new Panel());
					}
				}
			}
		});
		if (ConfigSettings.POLLINGMODULE != null) {
			pollingModuleCombobox.setSelectedItem(ConfigSettings.POLLINGMODULE);
		}

		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPanel.add(okButton);
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				save();
				logger.debug("Disposing ModeConfigDialog");
				dispose();
			}
		});

		JButton cancleButton = new JButton("Cancel");
		cancleButton.setActionCommand("Cancel");
		buttonPanel.add(cancleButton);
		cancleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.debug("Disposing ModeConfigDialog");
				dispose();
			}
		});

		if (!moduleSet) {
			setTabs();
		}

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		logger.debug("Creating ModeConfigDialog complete!");
		setVisible(true);
	}

	/**
	 * set all Tabs for the selected Module
	 */
	private void setTabs() {
		logger.debug("Create property-file tabs!");
		int i = 0;
		componentPuffer.clear();
		for (String properties : PropertyFileSettings.PROPERTIESPATHS
				.get((String) pollingModuleCombobox.getSelectedItem())) {

			if (properties != null && properties.trim().length() != 0) {
				String[] file = properties.split("/");
				// add all panels
				logger.debug("Creating tab: " + properties);
				componentPuffer.add(new PropertieFilesPanel(
						(String) pollingModuleCombobox.getSelectedItem(), i,
						this));
				tabbedPane.add(file[file.length - 1], new Panel());
				i++;
			}
		}
		tabbedPane.setSelectedIndex(0);
	}

	/**
	 * Save the changed Values
	 */
	private void save() {
		logger.debug("Saving settings!");
		for (int i = 0; i < componentPuffer.size(); i++) {
			((PropertieFilesPanel) componentPuffer.get(i)).save();
		}
	}

	@Override
	public void dispose() {
		// enable the dialog Button
		logger.debug("Disposing ModeConfigDialog!");
		mainFrame.disableModeConfig(false);
		super.dispose();
	}
}
