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

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JPasswordField;
import javax.swing.SpinnerNumberModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.simu.decomap.config.gui.values.SSHServer;
import de.simu.decomap.config.gui.values.SSHSettings;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Dialog for SSHServer settings
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 *
 */
@SuppressWarnings("serial")
public class SSHConfigDialog extends JDialog {

	private final String servername;
	private final JPanel contentPanel = new JPanel();
	private JTextField hostValue;
	private JSpinner portValue;
	private JTextField usernameValue;
	private JPasswordField passwordValue;
	private JTextField exportDirValue;

	private int height;
	private int width;
	private JTextField nameValue;
	private SSHConfigDialog frame = this;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Create the dialog.
	 * 
	 * @param loggingProperties
	 * @param configProperties
	 */
	public SSHConfigDialog(final String servername) {
		logger.debug("Creating SSHConfigDialog");
		this.servername = servername;
		SSHServer server = null;
		String host;
		int port;
		String username;
		String password;
		String dir;

		if (servername != null && servername.length() > 0) {
			server = SSHSettings.SERVERLIST.get(servername);
			host = server.getHOST();
			port = server.getPORT();
			username = server.getUSERNAME();
			password = server.getPASSWORD();
			dir = server.getDIR();
		} else {
			host = "";
			port = 22;
			username = "";
			password = "";
			dir = "";
		}
		setTitle("SSH Config");
		setBounds(100, 100, width, height);
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		{
			JPanel labelPanel = new JPanel();
			contentPanel.add(labelPanel);
			labelPanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JPanel nameLablePanel = new JPanel();
				nameLablePanel.setBorder(new EmptyBorder(5, 20, 5, 5));
				labelPanel.add(nameLablePanel);
				nameLablePanel.setLayout(new GridLayout(0, 1, 0, 0));
				{
					JLabel lblName = new JLabel("Name:");
					nameLablePanel.add(lblName);
				}
			}
			{
				JPanel hostLabelPanel = new JPanel();
				hostLabelPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
				labelPanel.add(hostLabelPanel);
				hostLabelPanel.setLayout(new GridLayout(0, 1, 0, 0));
				{
					JLabel hostLable = new JLabel("Host:");
					hostLabelPanel.add(hostLable);
				}
			}
			{
				JPanel portLablePanel = new JPanel();
				portLablePanel.setBorder(new EmptyBorder(5, 20, 5, 5));
				labelPanel.add(portLablePanel);
				portLablePanel.setLayout(new GridLayout(0, 1, 0, 0));
				{
					JLabel portLable = new JLabel("Port:");
					portLablePanel.add(portLable);
				}
			}
			{
				JPanel usernameLablePanel = new JPanel();
				usernameLablePanel.setBorder(new EmptyBorder(5, 20, 5, 5));
				labelPanel.add(usernameLablePanel);
				usernameLablePanel.setLayout(new GridLayout(0, 1, 0, 0));
				{
					JLabel usernameLable = new JLabel("Username:");
					usernameLablePanel.add(usernameLable);
				}
			}
			{
				JPanel passwordLablePanel = new JPanel();
				passwordLablePanel.setBorder(new EmptyBorder(5, 20, 5, 5));
				labelPanel.add(passwordLablePanel);
				passwordLablePanel.setLayout(new GridLayout(0, 1, 0, 0));
				{
					JLabel passwordLable = new JLabel("Password:");
					passwordLablePanel.add(passwordLable);
				}
			}
			{
				JPanel exportDirLablePanel = new JPanel();
				exportDirLablePanel.setBorder(new EmptyBorder(5, 20, 5, 5));
				labelPanel.add(exportDirLablePanel);
				exportDirLablePanel.setLayout(new GridLayout(0, 1, 0, 0));
				{
					JLabel exportDirLable = new JLabel("Config directory:");
					exportDirLablePanel.add(exportDirLable);
				}
			}
		}
		{
			JPanel valuePanel = new JPanel();
			contentPanel.add(valuePanel);
			valuePanel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JPanel nameValuePanel = new JPanel();
				nameValuePanel.setBorder(new EmptyBorder(5, 5, 5, 0));
				valuePanel.add(nameValuePanel);
				nameValuePanel.setLayout(new GridLayout(0, 1, 0, 0));
				{
					nameValue = new JTextField();
					nameValue.setText(servername);
					nameValuePanel.add(nameValue);
					nameValue.setColumns(10);
				}
			}
			{
				JPanel hostValuePanel = new JPanel();
				hostValuePanel.setBorder(new EmptyBorder(5, 5, 5, 0));
				valuePanel.add(hostValuePanel);
				hostValuePanel.setLayout(new GridLayout(1, 0, 0, 0));
				{
					hostValue = new JTextField();
					hostValue.setText(host);
					hostValuePanel.add(hostValue);
					hostValue.setColumns(10);
				}
			}
			{
				JPanel portValuePanel = new JPanel();
				portValuePanel.setBorder(new EmptyBorder(5, 5, 5, 0));
				valuePanel.add(portValuePanel);
				portValuePanel.setLayout(new GridLayout(1, 0, 0, 0));
				{
					SpinnerNumberModel numberModel = new SpinnerNumberModel(
							new Integer(22), // value
							new Integer(1), // min
							new Integer(65535), // max
							new Integer(1) // step
					);
					portValue = new JSpinner(numberModel);
					portValue.setValue(port);
					portValue.setEditor(new JSpinner.NumberEditor(portValue,
							"0"));
					portValuePanel.add(portValue);
				}
			}
			{
				JPanel usernameValuePanel = new JPanel();
				usernameValuePanel.setBorder(new EmptyBorder(5, 5, 5, 0));
				valuePanel.add(usernameValuePanel);
				usernameValuePanel.setLayout(new GridLayout(1, 0, 0, 0));
				{
					usernameValue = new JTextField();
					usernameValue.setText(username);
					usernameValuePanel.add(usernameValue);
					usernameValue.setColumns(10);
				}
			}
			{
				JPanel passwordValuePanel = new JPanel();
				passwordValuePanel.setBorder(new EmptyBorder(5, 5, 5, 0));
				valuePanel.add(passwordValuePanel);
				passwordValuePanel.setLayout(new GridLayout(1, 0, 0, 0));
				{
					passwordValue = new JPasswordField();
					passwordValue.setText(password);
					passwordValuePanel.add(passwordValue);
				}
			}
			{
				JPanel exportDirValuePanel = new JPanel();
				exportDirValuePanel.setBorder(new EmptyBorder(5, 5, 5, 0));
				valuePanel.add(exportDirValuePanel);
				exportDirValuePanel.setLayout(new GridLayout(1, 0, 0, 0));
				{
					exportDirValue = new JTextField();
					exportDirValue.setText(dir);
					exportDirValuePanel.add(exportDirValue);
					exportDirValue.setColumns(10);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				{
					buttonPane.setLayout(new BorderLayout(0, 0));
				}
				{
					JPanel saveCanelPanel = new JPanel();
					buttonPane.add(saveCanelPanel, BorderLayout.EAST);
					JButton cancelButton = new JButton("Save");
					saveCanelPanel.add(cancelButton);
					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if (nameValue.getText().length() > 0) {
								if (!nameValue.getText().equals(servername) && SSHSettings.SERVERNAMELIST.contains(nameValue.getText())) {
									logger.error("Name already exists!");
									JOptionPane.showMessageDialog(frame,
											"Name already exists\nPlease change the name", "Info",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									saveSshSettings();
									logger.debug("Disposing SSHConfigDialog");
									dispose();
								}
							} else {
								logger.error("Name can't be empty");
								JOptionPane.showMessageDialog(frame,
										"Name can't be empty", "Info",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
					});
					cancelButton.setActionCommand("Cancel");
					{
						JButton btnCancel = new JButton("Cancel");
						btnCancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								logger.debug("Disposing SSHConfigDialog");
								dispose();
							}
						});
						saveCanelPanel.add(btnCancel);
					}
				}
			}
		}
		setLocationRelativeTo(getOwner());
		logger.debug("Creating SSHConfigDialog complete");
		setVisible(true);
	}

	/**
	 * saving the ssh configuration into a properties file
	 */
	@SuppressWarnings("deprecation")
	private void saveSshSettings() {
		logger.debug("Saving settings!");
		if (SSHSettings.SERVERNAMELIST.contains(servername)) {

			SSHServer newServer = new SSHServer(nameValue.getText(),
					hostValue.getText(), (int) portValue.getValue(),
					usernameValue.getText(), passwordValue.getText(),
					exportDirValue.getText());
			SSHSettings.SERVERLIST.remove(servername);
			SSHSettings.SERVERLIST.put(nameValue.getText(), newServer);
			SSHSettings.SERVERNAMELIST.remove(servername);
			SSHSettings.SERVERNAMELIST.add(nameValue.getText());
		} else {
			SSHServer newServer = new SSHServer(nameValue.getText(),
					hostValue.getText(), (int) portValue.getValue(),
					usernameValue.getText(), passwordValue.getText(),
					exportDirValue.getText());
			SSHSettings.SERVERLIST.put(nameValue.getText(), newServer);
			SSHSettings.SERVERNAMELIST.add(nameValue.getText());
		}
	}

}
