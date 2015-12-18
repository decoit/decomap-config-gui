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
import java.awt.Dialog;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridLayout;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;

import de.simu.decomap.config.gui.loads.LoadLoggingProperties;
import de.simu.decomap.config.gui.main.ConfigGuiMain;
import de.simu.decomap.config.gui.values.ConfigSettings;
import de.simu.decomap.config.gui.values.LoggingSettings;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Dialog to configure logging settings
 * 
 *@author Leonid Schwenke, DECOIT GmbH
 *
 */
@SuppressWarnings("serial")
public class LoggingConfigDialog extends JDialog {

	private final ConfigGuiMain mainFrame;

	private final JPanel contentPanel = new JPanel();
	private JTextField consoleAppenderValueTextField;
	private JTextField fileAppenderValueTextField;
	private JTextField filePatternlayoutValueTextField;
	private JTextField filehandlerPatternValueTextfield;
	private JLabel filehandlerFormatterLable;
	private JLabel consolhandlerFormatterLable;
	private JLabel handlerLable;
	private JComboBox<String> consolhandlerLoglevelValueCombobox;
	private JComboBox<String> fileloglevelValueComboBox;
	private JCheckBox filehandlerAppendValueCheckbox;
	private JCheckBox immediateflushValueCheckBox;
	private JLabel lblFilePatternlayout;

	private JTextField consolePatternValueTextField;
	private JTextField logpathValueTextField;
	private JTextField consolePatternLayoutValueTextField;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Create the dialog.
	 * 
	 * @param mainFrame
	 */
	public LoggingConfigDialog(ConfigGuiMain mainFrame) {
		setResizable(false);
		logger.debug("Creating LoggingConfigDialog");
		this.mainFrame = mainFrame;
		setTitle("Logging Config Dialog");
		setBounds(100, 100, 730, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel mainConfigPanel = new JPanel();
			contentPanel.add(mainConfigPanel, BorderLayout.WEST);
			mainConfigPanel.setLayout(new BorderLayout(0, 0));
			{
				JPanel mainConfigLabelPanel = new JPanel();
				mainConfigPanel.add(mainConfigLabelPanel, BorderLayout.WEST);
				mainConfigLabelPanel.setLayout(new GridLayout(0, 1, 0, 0));
				{
					JPanel defaultLogLevelPanel = new JPanel();
					defaultLogLevelPanel
							.setBorder(new EmptyBorder(5, 20, 5, 5));
					mainConfigLabelPanel.add(defaultLogLevelPanel);
					defaultLogLevelPanel.setLayout(new GridLayout(0, 1, 0, 0));
					{
						JLabel defaultLogLevelLabel = new JLabel(
								"File loglevel:");
						defaultLogLevelPanel.add(defaultLogLevelLabel);
					}
				}
				{
					JPanel consolhandlerLogLevelPanel = new JPanel();
					consolhandlerLogLevelPanel.setBorder(new EmptyBorder(5, 20,
							5, 5));
					mainConfigLabelPanel.add(consolhandlerLogLevelPanel);
					consolhandlerLogLevelPanel.setLayout(new GridLayout(0, 1,
							0, 0));
					{
						JLabel consolhandleLogLevelLabel = new JLabel(
								"Consol loglevel:");
						consolhandlerLogLevelPanel
								.add(consolhandleLogLevelLabel);
					}
				}
				{
					JPanel filehandlerLogLevelPanel = new JPanel();
					filehandlerLogLevelPanel.setBorder(new EmptyBorder(5, 20,
							5, 5));
					mainConfigLabelPanel.add(filehandlerLogLevelPanel);
					filehandlerLogLevelPanel.setLayout(new GridLayout(0, 1, 0,
							0));
					{
						JLabel filehandlerLogLevelLabel = new JLabel(
								"Immediate flush:");
						filehandlerLogLevelPanel.add(filehandlerLogLevelLabel);
					}
				}
				{
					JPanel filehandlerAppendPanel = new JPanel();
					filehandlerAppendPanel.setBorder(new EmptyBorder(5, 20, 5,
							5));
					mainConfigLabelPanel.add(filehandlerAppendPanel);
					filehandlerAppendPanel
							.setLayout(new GridLayout(0, 1, 0, 0));
					{
						JLabel filehandlerAppendLabel = new JLabel(
								"Filehandler append:");
						filehandlerAppendPanel.add(filehandlerAppendLabel);
					}
				}
				{
					JPanel filehandlerPatternPanel = new JPanel();
					filehandlerPatternPanel.setBorder(new EmptyBorder(5, 20, 5,
							5));
					mainConfigLabelPanel.add(filehandlerPatternPanel);
					filehandlerPatternPanel
							.setLayout(new GridLayout(0, 1, 0, 0));
					{
						JLabel filehandlerPatternLabel = new JLabel(
								"Filehandler pattern:");
						filehandlerPatternPanel.add(filehandlerPatternLabel);
					}
				}
				{
					JPanel consolePatternLabel = new JPanel();
					consolePatternLabel.setBorder(new EmptyBorder(5, 20, 5, 5));
					mainConfigLabelPanel.add(consolePatternLabel);
					consolePatternLabel.setLayout(new GridLayout(0, 1, 0, 0));
					{
						JLabel label = new JLabel("Console pattern:");
						consolePatternLabel.add(label);
					}
				}
				{
					JPanel logfilePathLabel = new JPanel();
					logfilePathLabel.setBorder(new EmptyBorder(5, 20, 5, 5));
					mainConfigLabelPanel.add(logfilePathLabel);
					logfilePathLabel.setLayout(new GridLayout(0, 1, 0, 0));
					{
						JLabel lblConsolePattern = new JLabel("Logfile Path:");
						logfilePathLabel.add(lblConsolePattern);
					}
				}
			}
			{
				JPanel mainConfigValuePanel = new JPanel();
				mainConfigValuePanel.setBorder(new EmptyBorder(0, 0, 0, 20));
				mainConfigPanel.add(mainConfigValuePanel);
				mainConfigValuePanel.setLayout(new GridLayout(0, 1, 0, 0));
				{
					JPanel defaultLogLevelValuePanel = new JPanel();
					defaultLogLevelValuePanel.setBorder(new EmptyBorder(5, 20,
							5, 5));
					mainConfigValuePanel.add(defaultLogLevelValuePanel);
					defaultLogLevelValuePanel.setLayout(new GridLayout(0, 1, 0,
							0));
					{
						fileloglevelValueComboBox = new JComboBox<String>();
						fileloglevelValueComboBox
								.setModel(new DefaultComboBoxModel<String>(
										new String[] { "ALL", "TRACE", "DEBUG",
												"INFO", "WARN", "ERROR",
												"FATAL", "OFF" }));
						defaultLogLevelValuePanel
								.add(fileloglevelValueComboBox);
						fileloglevelValueComboBox
								.setSelectedItem(LoggingSettings.logLevelFile.split(",")[0]);
					}
				}
				{
					JPanel consolhandlerLogLevelValuePanel = new JPanel();
					consolhandlerLogLevelValuePanel.setBorder(new EmptyBorder(
							5, 20, 5, 5));
					mainConfigValuePanel.add(consolhandlerLogLevelValuePanel);
					consolhandlerLogLevelValuePanel.setLayout(new GridLayout(0,
							1, 0, 0));
					{
						consolhandlerLoglevelValueCombobox = new JComboBox<String>();
						consolhandlerLoglevelValueCombobox
								.setModel(new DefaultComboBoxModel<String>(
										new String[] { "ALL", "TRACE", "DEBUG",
												"INFO", "WARN", "ERROR",
												"FATAL", "OFF" }));
						consolhandlerLogLevelValuePanel
								.add(consolhandlerLoglevelValueCombobox);
						consolhandlerLoglevelValueCombobox
						.setSelectedItem(LoggingSettings.logLevelConsole.split(",")[0]);
					}
				}
				{
					JPanel filehandlerLoglevelValuePanel = new JPanel();
					filehandlerLoglevelValuePanel.setBorder(new EmptyBorder(5,
							20, 5, 5));
					mainConfigValuePanel.add(filehandlerLoglevelValuePanel);
					filehandlerLoglevelValuePanel.setLayout(new GridLayout(0,
							1, 0, 0));
					{
						immediateflushValueCheckBox = new JCheckBox("");
						filehandlerLoglevelValuePanel
								.add(immediateflushValueCheckBox);
						immediateflushValueCheckBox
								.setSelected(LoggingSettings.immediateFlush);
					}
				}
				{
					JPanel filehandlerAppendValuePanel = new JPanel();
					filehandlerAppendValuePanel.setBorder(new EmptyBorder(5,
							20, 5, 5));
					mainConfigValuePanel.add(filehandlerAppendValuePanel);
					filehandlerAppendValuePanel.setLayout(new GridLayout(0, 1,
							0, 0));
					{
						filehandlerAppendValueCheckbox = new JCheckBox("");
						filehandlerAppendValueCheckbox
								.setSelected(LoggingSettings.append);
						filehandlerAppendValuePanel
								.add(filehandlerAppendValueCheckbox);
					}
				}
				{
					JPanel filehandlerPatternValuePanel = new JPanel();
					filehandlerPatternValuePanel.setBorder(new EmptyBorder(5,
							20, 5, 5));
					mainConfigValuePanel.add(filehandlerPatternValuePanel);
					filehandlerPatternValuePanel.setLayout(new GridLayout(0, 1,
							0, 0));
					{
						filehandlerPatternValueTextfield = new JTextField();
						filehandlerPatternValuePanel
								.add(filehandlerPatternValueTextfield);
						filehandlerPatternValueTextfield.setColumns(20);
						filehandlerPatternValueTextfield
								.setText(LoggingSettings.filePattern);
					}
				}
				{
					JPanel consolepatternlabel = new JPanel();
					consolepatternlabel.setBorder(new EmptyBorder(5, 20, 5, 5));
					mainConfigValuePanel.add(consolepatternlabel);
					consolepatternlabel.setLayout(new GridLayout(0, 1, 0, 0));
					{
						consolePatternValueTextField = new JTextField();
						consolePatternValueTextField
								.setText(LoggingSettings.consoelPattern);
						consolePatternValueTextField.setColumns(20);
						consolepatternlabel.add(consolePatternValueTextField);
					}
				}
				{
					JPanel logpathlabel = new JPanel();
					logpathlabel.setBorder(new EmptyBorder(5, 20, 5, 5));
					mainConfigValuePanel.add(logpathlabel);
					logpathlabel.setLayout(new GridLayout(0, 1, 0, 0));
					{
						logpathValueTextField = new JTextField();
						logpathValueTextField.setText(LoggingSettings.logFile);
						logpathValueTextField.setColumns(10);
						logpathlabel.add(logpathValueTextField);
					}
				}
			}
		}
		{
			JPanel advancedConfigPanel = new JPanel();
			contentPanel.add(advancedConfigPanel, BorderLayout.CENTER);
			advancedConfigPanel.setLayout(new BorderLayout(0, 0));
			{
				JPanel advancedConfigLabelPanel = new JPanel();
				advancedConfigPanel.add(advancedConfigLabelPanel,
						BorderLayout.WEST);
				advancedConfigLabelPanel.setLayout(new BorderLayout(0, 0));
				{
					JPanel lablePanel = new JPanel();
					advancedConfigLabelPanel.add(lablePanel,
							BorderLayout.CENTER);
					lablePanel.setLayout(new GridLayout(0, 1, 0, 0));
					{
						JPanel advancedOptionPanel = new JPanel();
						advancedOptionPanel.setBorder(new EmptyBorder(5, 20, 5,
								5));
						lablePanel.add(advancedOptionPanel);
						advancedOptionPanel
								.setLayout(new GridLayout(1, 0, 0, 0));
						{
							JLabel advancedOptionLabel = new JLabel(
									"Advanced option:");
							advancedOptionPanel.add(advancedOptionLabel);
						}
					}
					{
						JPanel seperatorLabelPanelX = new JPanel();
						lablePanel.add(seperatorLabelPanelX);
						seperatorLabelPanelX.setLayout(new GridLayout(0, 1, 0,
								0));
						{
							JSeparator separator = new JSeparator();
							seperatorLabelPanelX.add(separator);
						}
					}
					{
						JPanel emptyPanel = new JPanel();
						lablePanel.add(emptyPanel);
						emptyPanel.setLayout(new GridLayout(0, 1, 0, 0));
					}
					{
						JPanel handlerPanel = new JPanel();
						lablePanel.add(handlerPanel);
						handlerPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
						handlerPanel.setLayout(new GridLayout(0, 1, 0, 0));
						{
							handlerLable = new JLabel("Console Appender:");
							handlerPanel.add(handlerLable);
							handlerLable.setEnabled(false);
						}
					}
					{
						JPanel consolHandlerFormatterPanel = new JPanel();
						lablePanel.add(consolHandlerFormatterPanel);
						consolHandlerFormatterPanel.setBorder(new EmptyBorder(
								5, 20, 5, 5));
						consolHandlerFormatterPanel.setLayout(new GridLayout(0,
								1, 0, 0));
						{
							consolhandlerFormatterLable = new JLabel(
									"Console Patternlayout:");
							consolHandlerFormatterPanel
									.add(consolhandlerFormatterLable);
							consolhandlerFormatterLable.setEnabled(false);
						}
					}
					{
						JPanel filehandlerFormatterPanel = new JPanel();
						lablePanel.add(filehandlerFormatterPanel);
						filehandlerFormatterPanel.setBorder(new EmptyBorder(5,
								20, 5, 5));
						filehandlerFormatterPanel.setLayout(new GridLayout(0,
								1, 0, 0));
						{
							filehandlerFormatterLable = new JLabel(
									"File Appender:");
							filehandlerFormatterPanel
									.add(filehandlerFormatterLable);
							filehandlerFormatterLable.setEnabled(false);
						}
					}
					{
						JPanel filePatternLayoutPanel = new JPanel();
						filePatternLayoutPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
						lablePanel.add(filePatternLayoutPanel);
						filePatternLayoutPanel.setLayout(new GridLayout(0, 1, 0, 0));
						{
							lblFilePatternlayout = new JLabel(
									"File Patternlayout:");
							lblFilePatternlayout.setEnabled(false);
							filePatternLayoutPanel.add(lblFilePatternlayout);
						}
					}
				}
				{
					JPanel seperatorPanelY = new JPanel();
					advancedConfigLabelPanel.add(seperatorPanelY,
							BorderLayout.WEST);
					seperatorPanelY.setLayout(new GridLayout(0, 1, 0, 0));
					{
						JSeparator separator = new JSeparator();
						separator.setOrientation(SwingConstants.VERTICAL);
						seperatorPanelY.add(separator);
					}
				}
			}
			{
				JPanel advancedConfigValuePanel = new JPanel();
				advancedConfigPanel.add(advancedConfigValuePanel);
				advancedConfigValuePanel.setLayout(new GridLayout(0, 1, 0, 0));
				{
					JPanel advancedOptionValuePanel = new JPanel();
					advancedOptionValuePanel.setBorder(new EmptyBorder(5, 20,
							5, 50));
					advancedConfigValuePanel.add(advancedOptionValuePanel);
					advancedOptionValuePanel.setLayout(new GridLayout(1, 0, 0,
							0));
					{
						// button to enable/disable advanced option
						final JToggleButton advancedOptionValueTogglebutton = new JToggleButton(
								"disabled");
						advancedOptionValueTogglebutton
								.addChangeListener(new ChangeListener() {

									@Override
									public void stateChanged(ChangeEvent arg0) {
										if (advancedOptionValueTogglebutton
												.isSelected()) {
											advancedOptionValueTogglebutton
													.setText("enabled");
										} else {
											advancedOptionValueTogglebutton
													.setText("disabled");
										}
										if (consoleAppenderValueTextField != null
												&& fileAppenderValueTextField != null
												&& filePatternlayoutValueTextField != null) {
											consoleAppenderValueTextField
													.setEnabled(advancedOptionValueTogglebutton
															.isSelected());
											fileAppenderValueTextField
													.setEnabled(advancedOptionValueTogglebutton
															.isSelected());
											filePatternlayoutValueTextField
													.setEnabled(advancedOptionValueTogglebutton
															.isSelected());
											handlerLable
													.setEnabled(advancedOptionValueTogglebutton
															.isSelected());
											consolhandlerFormatterLable
													.setEnabled(advancedOptionValueTogglebutton
															.isSelected());
											filehandlerFormatterLable
													.setEnabled(advancedOptionValueTogglebutton
															.isSelected());
											consolePatternLayoutValueTextField
													.setEnabled(advancedOptionValueTogglebutton
															.isSelected());
											lblFilePatternlayout
													.setEnabled(advancedOptionValueTogglebutton
															.isSelected());
										}

									}
								});
						advancedOptionValuePanel
								.add(advancedOptionValueTogglebutton);
					}
				}
				{
					JPanel seperatorValuePanelX = new JPanel();
					advancedConfigValuePanel.add(seperatorValuePanelX);
					seperatorValuePanelX.setLayout(new GridLayout(1, 0, 0, 0));
					{
						JSeparator separator = new JSeparator();
						seperatorValuePanelX.add(separator);
					}
				}
				{
					JPanel emptyPanel = new JPanel();
					advancedConfigValuePanel.add(emptyPanel);
					emptyPanel.setLayout(new GridLayout(1, 0, 0, 0));
				}
				{
					JPanel handlerValuePanel = new JPanel();
					advancedConfigValuePanel.add(handlerValuePanel);
					handlerValuePanel.setBorder(new EmptyBorder(5, 20, 5, 5));
					handlerValuePanel.setLayout(new GridLayout(0, 1, 0, 0));
					{
						consoleAppenderValueTextField = new JTextField();
						handlerValuePanel.add(consoleAppenderValueTextField);
						consoleAppenderValueTextField.setColumns(25);
						consoleAppenderValueTextField
								.setText(LoggingSettings.consoleAppender);
						consoleAppenderValueTextField.setEnabled(false);
					}
				}
				{
					JPanel consolePatternLayoutPanel = new JPanel();
					consolePatternLayoutPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
					advancedConfigValuePanel.add(consolePatternLayoutPanel);
					consolePatternLayoutPanel.setLayout(new GridLayout(0, 1, 0, 0));
					{
						consolePatternLayoutValueTextField = new JTextField();
						consolePatternLayoutValueTextField
								.setText(LoggingSettings.consolePatternLayout);
						consolePatternLayoutValueTextField.setEnabled(false);
						consolePatternLayoutValueTextField.setColumns(10);
						consolePatternLayoutPanel.add(consolePatternLayoutValueTextField);
					}
				}
				{
					JPanel consolhandlerFormatterValueLabel = new JPanel();
					advancedConfigValuePanel
							.add(consolhandlerFormatterValueLabel);
					consolhandlerFormatterValueLabel.setBorder(new EmptyBorder(
							5, 20, 5, 5));
					consolhandlerFormatterValueLabel.setLayout(new GridLayout(
							0, 1, 0, 0));
					{
						fileAppenderValueTextField = new JTextField();
						consolhandlerFormatterValueLabel
								.add(fileAppenderValueTextField);
						fileAppenderValueTextField.setColumns(10);
						fileAppenderValueTextField
								.setText(LoggingSettings.fileAppender);
						fileAppenderValueTextField.setEnabled(false);
					}
				}
				{
					JPanel filehandlerFormatterValuePanel = new JPanel();
					advancedConfigValuePanel
							.add(filehandlerFormatterValuePanel);
					filehandlerFormatterValuePanel.setBorder(new EmptyBorder(5,
							20, 5, 5));
					filehandlerFormatterValuePanel.setLayout(new GridLayout(0,
							1, 0, 0));
					{
						filePatternlayoutValueTextField = new JTextField();
						filehandlerFormatterValuePanel
								.add(filePatternlayoutValueTextField);
						filePatternlayoutValueTextField.setColumns(10);
						filePatternlayoutValueTextField
								.setText(LoggingSettings.filePatternLayout);
						filePatternlayoutValueTextField.setEnabled(false);
					}
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				buttonPane.setLayout(new BorderLayout(0, 0));
				{
					JPanel okAndCancelPane = new JPanel();
					buttonPane.add(okAndCancelPane, BorderLayout.EAST);

					// button for closing the dialog and saving the settings
					JButton okButton = new JButton("OK");
					okAndCancelPane.add(okButton);
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							LoggingSettings.logLevelFile = ((String) fileloglevelValueComboBox
									.getSelectedItem()) + ", FILE";
							LoggingSettings.logLevelConsole = ((String) consolhandlerLoglevelValueCombobox
									.getSelectedItem()) + ", stdout";
							LoggingSettings.immediateFlush = immediateflushValueCheckBox
									.isSelected();
							LoggingSettings.append = filehandlerAppendValueCheckbox
									.isSelected();
							LoggingSettings.filePattern = filehandlerPatternValueTextfield
									.getText();
							LoggingSettings.consoelPattern = consolePatternValueTextField
									.getText();
							LoggingSettings.logFile = logpathValueTextField
									.getText();

							LoggingSettings.fileAppender = fileAppenderValueTextField
									.getText();
							LoggingSettings.filePatternLayout = filePatternlayoutValueTextField
									.getText();
							LoggingSettings.consoleAppender = consoleAppenderValueTextField
									.getText();
							LoggingSettings.consolePatternLayout = consolePatternLayoutValueTextField
									.getText();
							logger.debug("Saving changes and disposing LoggingConfigDialog!");
							dispose();
						}
					});
					okButton.setActionCommand("OK");
					getRootPane().setDefaultButton(okButton);
					{
						// button for closing the dialog
						JButton cancelButton = new JButton("Cancel");
						okAndCancelPane.add(cancelButton);
						cancelButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								logger.debug("Disposing LoggingConfigDialog!");
								dispose();
							}
						});
						cancelButton.setActionCommand("Cancel");
					}
				}
			}
			{
				JPanel loadPanel = new JPanel();
				buttonPane.add(loadPanel, BorderLayout.WEST);
				{
					// button for loading a logging.properties file
					JButton btnLoad = new JButton("  Load  ");
					btnLoad.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JFileChooser chooser = new JFileChooser(
									ConfigSettings.LOADEDPATH);
							int returnVal = chooser.showOpenDialog(new Dialog(
									getOwner()));
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								LoadLoggingProperties loader = new LoadLoggingProperties(
										chooser.getSelectedFile().getPath());
								if (loader.load()) {
									fileloglevelValueComboBox
											.setSelectedItem(LoggingSettings.logLevelFile.split(",")[0]);
									consolhandlerLoglevelValueCombobox
											.setSelectedItem(LoggingSettings.logLevelConsole.split(",")[0]);
									immediateflushValueCheckBox
											.setSelected(LoggingSettings.immediateFlush);
									filehandlerAppendValueCheckbox
											.setSelected(LoggingSettings.append);
									filehandlerPatternValueTextfield
											.setText(LoggingSettings.filePattern);
									consolePatternValueTextField
											.setText(LoggingSettings.consoelPattern);
									logpathValueTextField
											.setText(LoggingSettings.logFile);

									fileAppenderValueTextField
											.setText(LoggingSettings.fileAppender);
									filePatternlayoutValueTextField
											.setText(LoggingSettings.filePatternLayout);
									consoleAppenderValueTextField
											.setText(LoggingSettings.consoleAppender);
									consolePatternLayoutValueTextField
											.setText(LoggingSettings.consolePatternLayout);

									JOptionPane.showMessageDialog(getOwner(),
											"LOAD SUCCESSFUL", "load",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									JOptionPane.showMessageDialog(getOwner(),
											"LOAD FAILED!", "load",
											JOptionPane.ERROR_MESSAGE);
								}

							}
						}
					});
					loadPanel.add(btnLoad);
				}
			}
		}
		setLocationRelativeTo(getOwner());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		pack();
		setMinimumSize(getSize());
	}

	@Override
	public void dispose() {
		// enable the dialog Button
		mainFrame.disableLoggingConfig(false);
		super.dispose();
	}

}
