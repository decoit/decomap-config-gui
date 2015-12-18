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

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.simu.decomap.config.gui.main.ConfigGuiMain;
import de.simu.decomap.config.gui.values.ConfigSettings;
import de.simu.decomap.config.gui.values.PropertyFileSettings;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Dialog for main Application configuration
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 * 
 */
@SuppressWarnings("serial")
public class ApplicationConfigDialog extends JDialog {

	private final ConfigGuiMain mainFrame;

	private final JPanel contentPanel = new JPanel();
	private JSpinner pollingIntervallValueSpinner;
	private JTextField clientAddressValueTextfield;
	private JComboBox<String> sendOldValueCombobox;
	private JComboBox<String> messageModeValue;

	private final JToggleButton isServiceValueToggleButton;
	private JSpinner servicePortValueSpinner;
	private JTextField serviceTypeValueTextField;
	private JTextField serviceNameValueTextField;
	private JTextField administrativDomainValueTextField;

	private JLabel servicePortLabel;
	private JLabel serviceTypeLabel;
	private JLabel serviceNameLabel;
	private JLabel administrativDomainLabel;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Create the dialog.
	 * 
	 * @param mainFrame
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ApplicationConfigDialog(final ConfigGuiMain mainFrame) {
		setResizable(false);
		logger.debug("Creating ApplicationConfigDialog!");
		this.mainFrame = mainFrame;
		getParent().repaint();
		setTitle("Application Config Dialog");
		setBounds(100, 100, 380, 360);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JPanel messageModePanel = new JPanel();
			messageModePanel.setBorder(new EmptyBorder(5, 20, 5, 5));
			contentPanel.add(messageModePanel);
			messageModePanel.setLayout(new GridLayout(1, 0, 0, 0));
			{
				JLabel lblMessagingMode = new JLabel("Polling Module:");
				messageModePanel.add(lblMessagingMode);
			}
		}
		{
			JPanel messageModeValuePanel = new JPanel();
			messageModeValuePanel.setBorder(new EmptyBorder(5, 10, 5, 5));
			contentPanel.add(messageModeValuePanel);
			messageModeValuePanel.setLayout(new GridLayout(1, 0, 0, 0));
			{
				messageModeValue = new JComboBox<String>(
						(new DefaultComboBoxModel(
								PropertyFileSettings.MODULE.toArray())));
				messageModeValue.setSelectedItem(ConfigSettings.POLLINGMODULE);
				messageModeValuePanel.add(messageModeValue);
			}
		}
		{
			JPanel pollingIntervallPanel = new JPanel();
			pollingIntervallPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
			contentPanel.add(pollingIntervallPanel);
			pollingIntervallPanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JLabel lblNewLabel = new JLabel("Polling intervall:");
				pollingIntervallPanel.add(lblNewLabel);
			}
		}
		{
			JPanel pollingIntervallValuePanel = new JPanel();
			pollingIntervallValuePanel.setBorder(new EmptyBorder(5, 10, 5, 5));
			contentPanel.add(pollingIntervallValuePanel);
			pollingIntervallValuePanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				final SpinnerNumberModel numberModel = new SpinnerNumberModel(
						new Integer(20), // value
						new Integer(1), // min
						new Integer(Integer.MAX_VALUE), // max
						new Integer(1) // step
				);
				pollingIntervallValueSpinner = new JSpinner(numberModel);
				pollingIntervallValueSpinner
						.setValue(ConfigSettings.POLLINGINTERVALL);
				pollingIntervallValueSpinner
						.setEditor(new JSpinner.NumberEditor(
								pollingIntervallValueSpinner, "0"));
				pollingIntervallValuePanel.add(pollingIntervallValueSpinner);
			}
		}
		{
			JPanel sendOldPanel = new JPanel();
			sendOldPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
			contentPanel.add(sendOldPanel);
			sendOldPanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JLabel sendOldLabel = new JLabel("Send old messages:");
				sendOldPanel.add(sendOldLabel);
			}
		}
		{
			JPanel sendOldValuePanel = new JPanel();
			sendOldValuePanel.setBorder(new EmptyBorder(5, 10, 5, 5));
			contentPanel.add(sendOldValuePanel);
			sendOldValuePanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				sendOldValueCombobox = new JComboBox<String>();
				sendOldValueCombobox.setModel(new DefaultComboBoxModel<String>(
						new String[] { "true", "false" }));
				sendOldValuePanel.add(sendOldValueCombobox);
				if (ConfigSettings.SENDOLD) {
					sendOldValueCombobox.setSelectedIndex(0);
				} else {
					sendOldValueCombobox.setSelectedIndex(1);
				}
			}
		}
		{
			JPanel clientAddressPanel = new JPanel();
			clientAddressPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
			contentPanel.add(clientAddressPanel);
			clientAddressPanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JLabel clientAddressLabel = new JLabel("Client IP-Address:");
				clientAddressPanel.add(clientAddressLabel);
			}
		}
		{
			JPanel clientAddressValuePanel = new JPanel();
			clientAddressValuePanel.setBorder(new EmptyBorder(5, 10, 5, 5));
			contentPanel.add(clientAddressValuePanel);
			clientAddressValuePanel.setLayout(new GridLayout(1, 0, 0, 0));
			{
				clientAddressValueTextfield = new JTextField();
				clientAddressValuePanel.add(clientAddressValueTextfield);
				clientAddressValueTextfield.setColumns(10);
				{
					JPanel isServicePanel = new JPanel();
					isServicePanel.setBorder(new EmptyBorder(5, 20, 5, 5));
					contentPanel.add(isServicePanel);
					isServicePanel.setLayout(new GridLayout(0, 1, 0, 0));
					{
						JLabel isServiceLabel = new JLabel("Is Service:");
						isServicePanel.add(isServiceLabel);
					}
				}
				{
					JPanel isServiceValuePanel = new JPanel();
					isServiceValuePanel.setBorder(new EmptyBorder(5, 10, 5, 5));
					contentPanel.add(isServiceValuePanel);
					isServiceValuePanel.setLayout(new GridLayout(1, 0, 0, 0));
					{
						isServiceValueToggleButton = new JToggleButton();
						isServiceValuePanel.add(isServiceValueToggleButton);
						isServiceValueToggleButton
								.setSelected(ConfigSettings.ISSERVICE);
						if (isServiceValueToggleButton.isSelected()) {
							isServiceValueToggleButton.setText("Enabled");
						} else {
							isServiceValueToggleButton.setText("Disabled");
						}
						isServiceValueToggleButton
								.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent arg0) {
										if (isServiceValueToggleButton
												.isSelected()) {
											isServiceValueToggleButton
													.setText("Enabled");
										} else {
											isServiceValueToggleButton
													.setText("Disabled");
										}

										servicePortValueSpinner
												.setEnabled(isServiceValueToggleButton
														.isSelected());
										serviceTypeValueTextField
												.setEnabled(isServiceValueToggleButton
														.isSelected());
										serviceNameValueTextField
												.setEnabled(isServiceValueToggleButton
														.isSelected());
										administrativDomainValueTextField
												.setEnabled(isServiceValueToggleButton
														.isSelected());

										servicePortLabel
												.setEnabled(isServiceValueToggleButton
														.isSelected());
										serviceTypeLabel
												.setEnabled(isServiceValueToggleButton
														.isSelected());
										serviceNameLabel
												.setEnabled(isServiceValueToggleButton
														.isSelected());
										administrativDomainLabel
												.setEnabled(isServiceValueToggleButton
														.isSelected());
									}
								});
					}
				}
				{
					JPanel servicePortPanel = new JPanel();
					servicePortPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
					contentPanel.add(servicePortPanel);
					servicePortPanel.setLayout(new GridLayout(0, 1, 0, 0));
					{
						servicePortLabel = new JLabel("Service Port:");
						servicePortPanel.add(servicePortLabel);
						servicePortLabel.setEnabled(isServiceValueToggleButton
								.isSelected());
					}
				}
				{
					JPanel servicePortValuePanel = new JPanel();
					servicePortValuePanel
							.setBorder(new EmptyBorder(5, 10, 5, 5));
					contentPanel.add(servicePortValuePanel);
					servicePortValuePanel.setLayout(new GridLayout(1, 0, 0, 0));
					{
						final SpinnerNumberModel numberModel = new SpinnerNumberModel(
								new Integer(0), // value
								new Integer(0), // min
								new Integer(65535), // max
								new Integer(1) // step
						);
						servicePortValueSpinner = new JSpinner(numberModel);
						if (ConfigSettings.PORT != null
								&& !ConfigSettings.PORT.trim().equals("")) {
							try {
								servicePortValueSpinner.setValue(Integer
										.parseInt(ConfigSettings.PORT));
							} catch (NumberFormatException e) {
								ConfigGuiMain.error("Error in config! Port is not a valid value!", e, this, true);
							}
						}
						servicePortValuePanel.add(servicePortValueSpinner);
						servicePortValueSpinner
								.setEditor(new JSpinner.NumberEditor(
										servicePortValueSpinner, "0"));
						servicePortValueSpinner
								.setEnabled(isServiceValueToggleButton
										.isSelected());
					}
				}
				{
					JPanel serviceTypePanel = new JPanel();
					serviceTypePanel.setBorder(new EmptyBorder(5, 20, 5, 5));
					contentPanel.add(serviceTypePanel);
					serviceTypePanel.setLayout(new GridLayout(0, 1, 0, 0));
					{
						serviceTypeLabel = new JLabel("Service Type:");
						serviceTypePanel.add(serviceTypeLabel);
						serviceTypeLabel.setEnabled(isServiceValueToggleButton
								.isSelected());
					}
				}
				{
					JPanel serviceTypeValuePanel = new JPanel();
					serviceTypeValuePanel
							.setBorder(new EmptyBorder(5, 10, 5, 5));
					contentPanel.add(serviceTypeValuePanel);
					serviceTypeValuePanel.setLayout(new GridLayout(1, 0, 0, 0));
					{
						serviceTypeValueTextField = new JTextField(
								ConfigSettings.SERVICETYPE);
						serviceTypeValueTextField.setColumns(10);
						serviceTypeValuePanel.add(serviceTypeValueTextField);
						serviceTypeValueTextField
								.setEnabled(isServiceValueToggleButton
										.isSelected());
					}
				}
				{
					JPanel serviceNamePanel = new JPanel();
					serviceNamePanel.setBorder(new EmptyBorder(5, 20, 5, 5));
					contentPanel.add(serviceNamePanel);
					serviceNamePanel.setLayout(new GridLayout(0, 1, 0, 0));
					{
						serviceNameLabel = new JLabel("Service Name:");
						serviceNamePanel.add(serviceNameLabel);
						serviceNameLabel.setEnabled(isServiceValueToggleButton
								.isSelected());
					}
				}
				{
					JPanel serviceNameValuePanel = new JPanel();
					serviceNameValuePanel
							.setBorder(new EmptyBorder(5, 10, 5, 5));
					contentPanel.add(serviceNameValuePanel);
					serviceNameValuePanel.setLayout(new GridLayout(1, 0, 0, 0));
					{
						serviceNameValueTextField = new JTextField(
								ConfigSettings.SERVICENAME);
						serviceNameValueTextField.setColumns(10);
						serviceNameValuePanel.add(serviceNameValueTextField);
						serviceNameValueTextField
								.setEnabled(isServiceValueToggleButton
										.isSelected());
					}
				}
				{
					JPanel administrativDomainPanel = new JPanel();
					administrativDomainPanel.setBorder(new EmptyBorder(5, 20,
							5, 5));
					contentPanel.add(administrativDomainPanel);
					administrativDomainPanel.setLayout(new GridLayout(0, 1, 0,
							0));
					{
						administrativDomainLabel = new JLabel(
								"Administrativ Domain:");
						administrativDomainPanel.add(administrativDomainLabel);
						administrativDomainLabel
								.setEnabled(isServiceValueToggleButton
										.isSelected());
					}
				}
				{
					JPanel administrativDomainValuePanel = new JPanel();
					administrativDomainValuePanel.setBorder(new EmptyBorder(5,
							10, 5, 5));
					contentPanel.add(administrativDomainValuePanel);
					administrativDomainValuePanel.setLayout(new GridLayout(1,
							0, 0, 0));
					{
						administrativDomainValueTextField = new JTextField(
								ConfigSettings.ADMINISTRATIVDOMAIN);
						administrativDomainValueTextField.setColumns(10);
						administrativDomainValuePanel
								.add(administrativDomainValueTextField);
						administrativDomainValueTextField
								.setEnabled(isServiceValueToggleButton
										.isSelected());
					}
				}
				if (ConfigSettings.IPADDRESS != null) {
					clientAddressValueTextfield
							.setText(ConfigSettings.IPADDRESS.split(":")[0]);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				// closing the dialog and saving the settings
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						logger.debug("Saving changes");
						ConfigSettings.POLLINGINTERVALL = (int) pollingIntervallValueSpinner
								.getValue();
						ConfigSettings.SENDOLD = Boolean
								.parseBoolean(((String) sendOldValueCombobox
										.getSelectedItem()));
						// ConfigSettings.MESSAGINGTYPE = (String)
						// messageTypeValueCombobox
						// .getSelectedItem();
						ConfigSettings.IPADDRESS = clientAddressValueTextfield
								.getText();
						ConfigSettings.POLLINGMODULE = (String) messageModeValue
								.getSelectedItem();

						ConfigSettings.ISSERVICE = isServiceValueToggleButton
								.isSelected();
						ConfigSettings.PORT = servicePortValueSpinner
								.getValue() + "";
						ConfigSettings.SERVICETYPE = serviceTypeValueTextField
								.getText();
						ConfigSettings.SERVICENAME = serviceNameValueTextField
								.getText();
						ConfigSettings.ADMINISTRATIVDOMAIN = administrativDomainValueTextField
								.getText();

						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				// closing the dialog
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setLocationRelativeTo(getOwner());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		logger.debug("Creating ApplicationConfigDialog complete!");
		setVisible(true);
		pack();
		setMinimumSize(getSize());
	}

	@Override
	public void dispose() {
		// enable the applicationButton
		logger.debug("Disposing ApplicationConfigDialog");
		mainFrame.disableApplicationConfig(false);
		super.dispose();
	}

}
