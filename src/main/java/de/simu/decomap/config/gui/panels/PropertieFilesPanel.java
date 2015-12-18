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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.simu.decomap.config.gui.components.MatcherButton;
import de.simu.decomap.config.gui.components.TextblockButton;
import de.simu.decomap.config.gui.dialogs.ModeConfigDialog;
import de.simu.decomap.config.gui.loads.LoadModePropertyFiles;
import de.simu.decomap.config.gui.values.ConfigSettings;
import de.simu.decomap.config.gui.values.PropertyFileSettings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * Panel files with components to match a specific mode file
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 * 
 */
@SuppressWarnings("serial")
public class PropertieFilesPanel extends JPanel {

	private final String mode;
	private final int propertieFile;
	private JPanel lablePanel;
	private JPanel valuePanel = new JPanel();
	private ArrayList<String> values = new ArrayList<String>();
	private final ModeConfigDialog frame;
	private ArrayList<String[]> dataArray = new ArrayList<String[]>();

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * creating panel
	 * 
	 * @param mode
	 *            mode of panel
	 * @param propertieFile
	 *            panel for this file
	 * @param frame
	 *            dialog
	 */
	public PropertieFilesPanel(final String mode, final int propertieFile,
			final ModeConfigDialog frame) {
		this.frame = frame;
		this.mode = mode;
		this.propertieFile = propertieFile;
		initGUI();
		loadValues();
	}

	/**
	 * init GUI components
	 */
	private void initGUI() {
		logger.debug("Creating PropertiesFilePanel");
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.CENTER);

		
		JPanel boxPufferPanel = new JPanel();
		scrollPane.setViewportView(boxPufferPanel);
		boxPufferPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel contenPanel = new JPanel();
		boxPufferPanel.add(contenPanel, BorderLayout.NORTH);
		// add(contenPanel, BorderLayout.NORTH);
		contenPanel.setLayout(new BorderLayout(0, 0));


		lablePanel = new JPanel();
		lablePanel.setBorder(new EmptyBorder(5, 20, 5, 20));
		contenPanel.add(lablePanel, BorderLayout.WEST);

		valuePanel.setBorder(new EmptyBorder(5, 20, 5, 5));
		contenPanel.add(valuePanel, BorderLayout.CENTER);

		JPanel buttonPane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) buttonPane.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(buttonPane, BorderLayout.SOUTH);

		JButton btnLoad = new JButton("  Load  ");
		buttonPane.add(btnLoad);
		btnLoad.addActionListener(new ActionListener() {

			// loading a properties file
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser(
						ConfigSettings.LOADEDPATH);
				int returnVal = chooser.showOpenDialog(new Dialog(frame));
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					LoadModePropertyFiles loader = new LoadModePropertyFiles(
							chooser.getSelectedFile().getPath());
					if (loader.load(mode, propertieFile + "", dataArray)) {
						loadValues();
						logger.info("Loading successful!");
						JOptionPane.showMessageDialog(frame, "LOAD SUCCESSFUL",
								"load", JOptionPane.INFORMATION_MESSAGE);
					} else {
						logger.error("Load failed!");
						JOptionPane.showMessageDialog(frame, "LOAD FAILED!",
								"load", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		
		logger.debug("Creating PropertiesFilePanel complete!");
	}

	/**
	 * saving the settings
	 */
	@SuppressWarnings("unchecked")
	public void save() {
		logger.info("Saving values!");
		values.clear();
		String[] data;

		PropertyFileSettings.PROPERTIESPATHS.get(mode)[propertieFile] = ((JTextField) ((JPanel) valuePanel
				.getComponent(0)).getComponent(0)).getText();

		for (int i = 0; i < dataArray.size(); i++) {
			data = dataArray.get(i);
			if (valuePanel.getComponent(i + 1) != null) {
				Component comp = ((JPanel) valuePanel.getComponent(i + 1))
						.getComponent(0);
				if (comp instanceof JSpinner) {
					values.add(data[0] + ";" + data[1] + ";"
							+ data[2].split("=")[0] + "="
							+ ((JSpinner) comp).getValue());
				} else if (comp instanceof JTextField) {
					values.add(data[0] + ";" + data[1] + ";"
							+ data[2].split("=")[0] + "="
							+ ((JTextField) comp).getText());
				} else if (comp instanceof JCheckBox) {
					values.add(data[0] + ";" + data[1] + ";"
							+ data[2].split("=")[0] + "="
							+ ((JCheckBox) comp).isSelected());
				} else if (comp instanceof JComboBox) {
					values.add(data[0] + ";" + data[1] + ";"
							+ data[2].split("=")[0] + "="
							+ ((JComboBox<String>) comp).getSelectedItem());
				} else if (comp instanceof TextblockButton) {
					values.add(data[0] + ";" + data[1] + ";"
							+ data[2].split("=")[0] + "="
							+ ((TextblockButton) comp).getTextblockText());
				} else if (comp instanceof MatcherButton) {
					values.add(data[0] + ";" + data[1] + ";"
							+ data[2].split("=")[0] + "="
							+ ((MatcherButton) comp).getMatcherText());
				}
			}
		}
		HashMap<String, ArrayList<String>> temp = PropertyFileSettings.PROPERTIESINFOS
				.get(mode);
		temp.put(propertieFile + "", values);
		PropertyFileSettings.PROPERTIESINFOS.put(propertieFile + "", temp);
	}

	/**
	 * getting the number of components
	 * 
	 * @return number of components in this pane
	 */
	public int getSizeCount() {
		// if (!propertieFile.equals("enforcement.properties")) {
		return lablePanel.getComponentCount();
		// } else {
		// return enforcement.getSizeCount();
		// }
	}

	/**
	 * Constructing the tab with the loaded data
	 * 
	 */
	private void loadValues() {
		logger.debug("Loading Values!");
		dataArray.clear();
		for (String line : PropertyFileSettings.PROPERTIESINFOS.get(mode).get(
				propertieFile + "")) {
			dataArray.add(line.split(";", 3));
			logger.debug("Analysing line: " + line);
		}

		lablePanel.removeAll();
		valuePanel.removeAll();

		addPath();

		String[] data;
		for (int i = 0; i < dataArray.size(); i++) {
			data = dataArray.get(i);

			// text
			{
				JPanel textPanel = new JPanel();
				lablePanel.add(textPanel);
				textPanel.setLayout(new GridLayout(1, 0, 0, 0));

				JLabel lblText = new JLabel(data[0] + ":");
				textPanel.add(lblText);
			}

			// value
			{
				JPanel componentPanel = new JPanel();
				componentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				valuePanel.add(componentPanel);
				componentPanel.setLayout(new GridLayout(0, 1, 0, 0));
				componentPanel.add(getComponent(data[1], data[2]));
			}
		}

	}

	/**
	 * Add a component to change the Path from the Property-File
	 */
	private void addPath() {
		lablePanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JPanel textPanel = new JPanel();
			lablePanel.add(textPanel);
			textPanel.setLayout(new GridLayout(1, 0, 0, 0));

			JLabel lblText = new JLabel("Properties Filepath:");
			textPanel.add(lblText);
		}
		valuePanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JPanel componentPanel = new JPanel();
			componentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			valuePanel.add(componentPanel);
			componentPanel.setLayout(new GridLayout(0, 1, 0, 0));
			final JTextField textField = new JTextField();
			textField.setColumns(10);
			textField
					.setText(PropertyFileSettings.PROPERTIESPATHS.get(mode)[propertieFile]);
			textField.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					check();
				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					check();
				}

				@Override
				public void changedUpdate(DocumentEvent arg0) {
					check();
				}

				private void check() {
					if (textField.getText().trim().length() == 0) {
						logger.warn("Path is empty!");
						JOptionPane.showMessageDialog(PropertieFilesPanel.this,
								"Path can't be empty!");
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								textField
										.setText(PropertyFileSettings.PROPERTIESPATHS
												.get(mode)[propertieFile]);
							}

						});

					}
				}
			});

			componentPanel.add(textField);
		}
	}

	/**
	 * getting the component with the right type and value
	 * 
	 * @param dataType
	 *            type of component
	 * @param valueLine
	 *            line with the value of this component
	 * @return component in the given type and value
	 */
	private Component getComponent(String dataType, String valueLine) {
		String[] value = valueLine.split("=", 2);
		// try loading saved value
		if (PropertyFileSettings.PROPERTIESINFOS.get(mode).get(
				propertieFile + "") != null) {
			for (String save : PropertyFileSettings.PROPERTIESINFOS.get(mode)
					.get(propertieFile + "")) {
				if (value[0].equals(save.split("=")[0])) {
					if (save.split("=").length < 2) {
						value[1] = "";
					} else {
						value[1] = save.split("=", 2)[1];
					}
				}
			}
		}
		// formating into right type
		if (dataType.equals("String")) {
			JTextField textField = new JTextField();
			textField.setColumns(10);
			textField.setText(value[1]);
			return textField;
		} else if (dataType.equals("int")) {
			SpinnerNumberModel numberModel = new SpinnerNumberModel(
					new Integer(20), // value
					new Integer(1), // min
					new Integer(Integer.MAX_VALUE), // max
					new Integer(1) // step
			);
			JSpinner spinner = new JSpinner(numberModel);
			spinner.setValue(Integer.parseInt(value[1]));
			spinner.setEditor(new JSpinner.NumberEditor(spinner, "0"));
			return spinner;
		} else if (dataType.equals("boolean")) {
			JCheckBox checkBox = new JCheckBox();
			checkBox.setSelected(Boolean.parseBoolean(value[1]));
			return checkBox;
		} else if (dataType.startsWith("Array")) {
			String[] values = dataType.split(":")[1].split(",");
			final JComboBox<String> cBox = new JComboBox<String>();
			cBox.setModel(new DefaultComboBoxModel<String>(values));
			cBox.setSelectedItem(value[1]);
			return cBox;
		} else if (dataType.startsWith("int:")) {
			String[] values = dataType.split(":")[1].split(",");
			SpinnerNumberModel numberModel = new SpinnerNumberModel(
					new Integer(20), // value
					new Integer(Integer.parseInt(values[0])), // min
					new Integer(Integer.parseInt(values[1])), // max
					new Integer(1) // step
			);
			JSpinner spinner = new JSpinner(numberModel);
			spinner.setValue(Integer.parseInt(value[1]));
			spinner.setEditor(new JSpinner.NumberEditor(spinner, "0"));
			return spinner;
		} else if (dataType.startsWith("Textblock:")) {
			String splitMode = dataType.split(":")[1];
			TextblockButton button = new TextblockButton(splitMode);
			button.setTextblockText(value[1]);
			return button;
		} else if (dataType.startsWith("Matcher")) {
			MatcherButton matcher = new MatcherButton();
			matcher.setMatcherText(value[1]);
			return matcher;
		}
		logger.error("This should not happen!");
		return null;
	}
}
