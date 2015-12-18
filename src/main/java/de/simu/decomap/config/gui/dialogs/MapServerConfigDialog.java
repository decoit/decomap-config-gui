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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JPasswordField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.simu.decomap.config.gui.connection.ConnectionTest;
import de.simu.decomap.config.gui.main.ConfigGuiMain;
import de.simu.decomap.config.gui.values.ConfigSettings;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Dialog to configure Map-Settings
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 *
 */
@SuppressWarnings("serial")
public class MapServerConfigDialog extends JDialog {

	private final ConfigGuiMain mainFrame;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField mapServerAddressValue;
	private JTextField keystorePathValue;
	private JPasswordField keystorePasswordValue;
	private JPasswordField truststorePasswordValue;
	private JTextField truststorePathValue;
	private JPasswordField mapServerPasswordValue;
	private JTextField mapServerUsernameValue;
	private final JToggleButton mapServerAuthentificationValueTogglebutton;
	private JLabel lblMapserverUsername;
	private JLabel lblMapserverPassword;
	
	private int height;
	private int width;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Create the dialog.
	 * @param mainFrame 
	 */
	public MapServerConfigDialog(ConfigGuiMain mainFrame) {
		setResizable(false);
		logger.debug("Creating MapServerConfigDialog");
		this.mainFrame = mainFrame;
		setTitle("Mapserver Config Dialog");
		setBounds(100, 100, width, height);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JPanel mapServerAddressPanel = new JPanel();
			mapServerAddressPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
			contentPanel.add(mapServerAddressPanel);
			mapServerAddressPanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JLabel lblMapserverAddress = new JLabel("Map-Server address (URL):");
				mapServerAddressPanel.add(lblMapserverAddress);
			}
		}
		{
			JPanel mapServerAddressValuePanel = new JPanel();
			contentPanel.add(mapServerAddressValuePanel);
			mapServerAddressValuePanel.setBorder(new EmptyBorder(5, 20, 5, 0));
			mapServerAddressValuePanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				mapServerAddressValue = new JTextField();
				mapServerAddressValuePanel.add(mapServerAddressValue);
				mapServerAddressValue.setColumns(10);
				mapServerAddressValue.setText(ConfigSettings.MAPSERVERADDRESS);
			}
		}
		{
			JPanel keystorePathPanel = new JPanel();
			keystorePathPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
			contentPanel.add(keystorePathPanel);
			keystorePathPanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JLabel lblKeystorePath = new JLabel("Keystore path:");
				keystorePathPanel.add(lblKeystorePath);
			}
		}
		{
			JPanel keystorePathValuePanel = new JPanel();
			keystorePathValuePanel.setBorder(new EmptyBorder(5, 20, 5, 0));
			contentPanel.add(keystorePathValuePanel);
			keystorePathValuePanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				keystorePathValue = new JTextField();
				keystorePathValuePanel.add(keystorePathValue);
				keystorePathValue.setColumns(10);
				keystorePathValue.setText(ConfigSettings.KEYSTOREPATH);
			}
		}
		{
			JPanel keystorePasswordPanel = new JPanel();
			keystorePasswordPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
			contentPanel.add(keystorePasswordPanel);
			keystorePasswordPanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JLabel lblKeystorePassword = new JLabel("Keystore password:");
				keystorePasswordPanel.add(lblKeystorePassword);
			}
		}
		{
			JPanel keystorePasswordValuePanel = new JPanel();
			keystorePasswordValuePanel.setBorder(new EmptyBorder(5, 20, 5, 0));
			contentPanel.add(keystorePasswordValuePanel);
			keystorePasswordValuePanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				keystorePasswordValue = new JPasswordField();
				keystorePasswordValuePanel.add(keystorePasswordValue);
				keystorePasswordValue.setText(ConfigSettings.KEYSTOREPASSWORD);
			}
		}
		{
			JPanel truststorePathPanel = new JPanel();
			truststorePathPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
			contentPanel.add(truststorePathPanel);
			truststorePathPanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JLabel lblTruststorePath = new JLabel("Truststore path:");
				truststorePathPanel.add(lblTruststorePath);
			}
		}
		{
			JPanel truststorePathValuePanel = new JPanel();
			truststorePathValuePanel.setBorder(new EmptyBorder(5, 20, 5, 0));
			contentPanel.add(truststorePathValuePanel);
			truststorePathValuePanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				truststorePathValue = new JTextField();
				truststorePathValuePanel.add(truststorePathValue);
				truststorePathValue.setColumns(10);
				truststorePathValue.setText(ConfigSettings.TRUSTSTOREPATH);
			}
		}
		{
			JPanel truststorePasswordPanel = new JPanel();
			truststorePasswordPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
			contentPanel.add(truststorePasswordPanel);
			truststorePasswordPanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JLabel lblTruststorePassword = new JLabel("Truststore password:");
				truststorePasswordPanel.add(lblTruststorePassword);
			}
		}
		{
			JPanel truststorePasswordValuePanel = new JPanel();
			truststorePasswordValuePanel.setBorder(new EmptyBorder(5, 20, 5, 0));
			contentPanel.add(truststorePasswordValuePanel);
			truststorePasswordValuePanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				truststorePasswordValue = new JPasswordField();
				truststorePasswordValuePanel.add(truststorePasswordValue);
				truststorePasswordValue.setText(ConfigSettings.TRUSTSTOREPASSWORD);
			}
		}
		{
			JPanel mapserverAuthPanel = new JPanel();
			mapserverAuthPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
			contentPanel.add(mapserverAuthPanel);
			mapserverAuthPanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JLabel lblMapserverAuthentification = new JLabel("Map-Server authentification:");
				mapserverAuthPanel.add(lblMapserverAuthentification);
			}
		}
		{
			JPanel mapserverAuthValuePanel = new JPanel();
			mapserverAuthValuePanel.setBorder(new EmptyBorder(5, 20, 5, 0));
			contentPanel.add(mapserverAuthValuePanel);
			mapserverAuthValuePanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				mapServerAuthentificationValueTogglebutton = new JToggleButton("disabled");
				mapServerAuthentificationValueTogglebutton.setSelected(ConfigSettings.MAPSERVERAUTHENTIFICATION);
				if(mapServerAuthentificationValueTogglebutton.isSelected()){
					mapServerAuthentificationValueTogglebutton.setText("enabled");
				}
				mapServerAuthentificationValueTogglebutton.addChangeListener(new ChangeListener() {
					
					//activating or deactivating the username and password field depending on the mapServerAuthentificationValueTogglebutton
					@Override
					public void stateChanged(ChangeEvent arg0) {
						if(mapServerAuthentificationValueTogglebutton.isSelected()){
							mapServerAuthentificationValueTogglebutton.setText("enabled");
							if(mapServerUsernameValue!=null && mapServerPasswordValue!=null && lblMapserverUsername!= null && lblMapserverPassword!=null){
								mapServerUsernameValue.setEnabled(true);
								mapServerPasswordValue.setEnabled(true);
								lblMapserverUsername.setEnabled(true);
								lblMapserverPassword.setEnabled(true);
							}
							
						} else{
							mapServerAuthentificationValueTogglebutton.setText("disabled");
							if(mapServerPasswordValue != null && mapServerUsernameValue!=null && lblMapserverUsername!= null && lblMapserverPassword!=null){
								mapServerUsernameValue.setEnabled(false);
								mapServerPasswordValue.setEnabled(false);
								lblMapserverUsername.setEnabled(false);
								lblMapserverPassword.setEnabled(false);
							}
						}
					}
				});
				mapserverAuthValuePanel.add(mapServerAuthentificationValueTogglebutton);
			}
		}
		{
			JPanel mapserverUnsernamePanel = new JPanel();
			mapserverUnsernamePanel.setBorder(new EmptyBorder(5, 20, 5, 5));
			contentPanel.add(mapserverUnsernamePanel);
			mapserverUnsernamePanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				lblMapserverUsername = new JLabel("Map-Server username:");
				mapserverUnsernamePanel.add(lblMapserverUsername);
				if(!mapServerAuthentificationValueTogglebutton.isSelected()){
					lblMapserverUsername.setEnabled(false);
				}
			}
		}
		{
			JPanel mapserverUsernameValuePanel = new JPanel();
			mapserverUsernameValuePanel.setBorder(new EmptyBorder(5, 20, 5, 0));
			contentPanel.add(mapserverUsernameValuePanel);
			mapserverUsernameValuePanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				mapServerUsernameValue = new JTextField();
				mapserverUsernameValuePanel.add(mapServerUsernameValue);
				mapServerUsernameValue.setColumns(10);
				mapServerUsernameValue.setText(ConfigSettings.MAPSERVERUSER);
				if(!mapServerAuthentificationValueTogglebutton.isSelected()){
					mapServerUsernameValue.setEnabled(false);
				}
			}
		}
		{
			JPanel mapserverPasswordPanel = new JPanel();
			mapserverPasswordPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
			contentPanel.add(mapserverPasswordPanel);
			mapserverPasswordPanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				lblMapserverPassword = new JLabel("Map-Server password:");
				mapserverPasswordPanel.add(lblMapserverPassword);
				if(!mapServerAuthentificationValueTogglebutton.isSelected()){
					lblMapserverPassword.setEnabled(false);
				}
			}
		}
		{
			JPanel mapserverPasswordValuePanel = new JPanel();
			mapserverPasswordValuePanel.setBorder(new EmptyBorder(5, 20, 5, 0));
			contentPanel.add(mapserverPasswordValuePanel);
			mapserverPasswordValuePanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				mapServerPasswordValue = new JPasswordField();
				mapserverPasswordValuePanel.add(mapServerPasswordValue);
				mapServerPasswordValue.setText(ConfigSettings.MAPSERVERPASSWORD);
				if(!mapServerAuthentificationValueTogglebutton.isSelected()){
					mapServerPasswordValue.setEnabled(false);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				buttonPane.setLayout(new BorderLayout(0, 0));
				{
					JPanel endDialogPanel = new JPanel();
					buttonPane.add(endDialogPanel, BorderLayout.EAST);
					//button for closing the dialog and saving the settings
					JButton okButton = new JButton("OK");
					endDialogPanel.add(okButton);
					okButton.addActionListener(new ActionListener() {
						@SuppressWarnings("deprecation")
						public void actionPerformed(ActionEvent e) {
							logger.debug("Saving settings!");
							ConfigSettings.MAPSERVERADDRESS = mapServerAddressValue.getText();
							ConfigSettings.KEYSTOREPATH = keystorePathValue.getText();
							ConfigSettings.KEYSTOREPASSWORD = keystorePasswordValue.getText();
							ConfigSettings.TRUSTSTOREPATH = truststorePathValue.getText();
							ConfigSettings.TRUSTSTOREPASSWORD = truststorePasswordValue.getText();
							ConfigSettings.MAPSERVERAUTHENTIFICATION = mapServerAuthentificationValueTogglebutton.isSelected();
							ConfigSettings.MAPSERVERUSER =  mapServerUsernameValue.getText();
							ConfigSettings.MAPSERVERPASSWORD = mapServerPasswordValue.getText();
							dispose();
						}
					});
					okButton.setActionCommand("OK");
					getRootPane().setDefaultButton(okButton);
					{
						//button for closing the dialog
						JButton cancelButton = new JButton("Cancel");
						endDialogPanel.add(cancelButton);
						cancelButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								dispose();
							}
						});
						cancelButton.setActionCommand("Cancel");
					}
				}
			}
			{
				JPanel connectionPanel = new JPanel();
				buttonPane.add(connectionPanel, BorderLayout.WEST);
				{
					JButton btnNewButton = new JButton("Test connection");
					btnNewButton.addActionListener(new ActionListener() {
						@SuppressWarnings("deprecation")
						public void actionPerformed(ActionEvent e) {
							ConnectionTest test;
							try{
								logger.info("Starting connectiontest");
								test = new ConnectionTest(mapServerAddressValue.getText(), keystorePathValue.getText(), keystorePasswordValue.getText(),
										truststorePathValue.getText(), truststorePasswordValue.getText(), mapServerAuthentificationValueTogglebutton.isSelected(),
										mapServerUsernameValue.getText(), mapServerPasswordValue.getText(), 20);
								if(test.test()){
									logger.info("Test successful!");
									JOptionPane.showMessageDialog(getOwner(),
					            		    "CONNECTION SUCCESSFUL",
					            		    "connection",
					            		    JOptionPane.INFORMATION_MESSAGE);
								} else{
									logger.error("Connectiontest failed!");
									JOptionPane.showMessageDialog(getOwner(),
					            		    "CONNECTION FAILED!",
					            		    "connection",
					            		    JOptionPane.ERROR_MESSAGE);
								}
								test.disconnect();
							} catch(Exception fail){
								ConfigGuiMain.error(fail.getMessage(), fail, MapServerConfigDialog.this, false);
							}
						}
					});
					connectionPanel.add(btnNewButton);
				}
			}
		}
		setLocationRelativeTo(getOwner());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		logger.debug("Creating MapServerConfigDialog complete!");
		setVisible(true);
		pack();
		setMinimumSize(getSize());
	}
	
	@Override
	public void dispose(){
		//enable the dialog Button
		logger.debug("Disposing MapServerConfigDialog");
		mainFrame.disableMapServerConfig(false);
		super.dispose();
	}

}
