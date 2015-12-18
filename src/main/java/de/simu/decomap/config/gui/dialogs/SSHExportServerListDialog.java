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
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.simu.decomap.config.gui.connection.SSHFileExport;
import de.simu.decomap.config.gui.values.ConfigSettings;
import de.simu.decomap.config.gui.values.PropertyFileSettings;
import de.simu.decomap.config.gui.values.SSHServer;
import de.simu.decomap.config.gui.values.SSHSettings;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * SSH-ServerList for Exportation
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 * 
 */
@SuppressWarnings("serial")
public class SSHExportServerListDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final boolean configProperties;
	private final boolean loggingProperties;
	private final ArrayList<String> files;

	private JList<String> serverList;
	private SSHExportServerListDialog frame = this;
	private int lastSelectedIndex = 0;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Create the dialog.
	 * 
	 * @param loggingProperties
	 * @param configProperties
	 */
	public SSHExportServerListDialog(final boolean configProperties,
			final boolean loggingProperties, final ArrayList<String> files) {
		logger.debug("Creating SSHExportServerListDialog");

		this.configProperties = configProperties;
		this.loggingProperties = loggingProperties;
		this.files = files;

		setTitle("SSH Serverlist");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				serverList = new JList<String>();
				reloadList();
				scrollPane.setViewportView(serverList);
			}
		}
		{
			JPanel listFunktionPanel = new JPanel();
			contentPanel.add(listFunktionPanel, BorderLayout.EAST);
			{
				listFunktionPanel.setLayout(new BorderLayout(0, 0));
			}
			{
				JPanel panel = new JPanel();
				listFunktionPanel.add(panel, BorderLayout.NORTH);
				panel.setLayout(new GridLayout(0, 1, 0, 0));
				JButton btnAdd = new JButton("   Add   ");
				panel.add(btnAdd);
				{
					JButton btnDelete = new JButton(" Delete ");
					panel.add(btnDelete);
					{
						JButton btnChangeButton = new JButton("Change");
						panel.add(btnChangeButton);
						{
							JButton btnCopy = new JButton("Copy");
							panel.add(btnCopy);
							btnCopy.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									logger.debug("Copying SSH-Server");
									if (serverList.getSelectedValue() != null
											&& serverList.getSelectedValue()
													.length() > 0) {
										lastSelectedIndex = serverList
												.getSelectedIndex();
										SSHServer copyServer = SSHSettings.SERVERLIST
												.get(serverList
														.getSelectedValue());
										copyServer.setName(copyServer.getName()
												+ " copy");
										while (true) {
											if (!SSHSettings.SERVERNAMELIST
													.contains(copyServer
															.getName())) {
												SSHSettings.SERVERNAMELIST
														.add(copyServer
																.getName());
												SSHSettings.SERVERLIST.put(
														copyServer.getName(),
														copyServer);
												break;
											} else {
												copyServer.setName(copyServer
														.getName() + " copy");
											}
										}
										reloadList();
									} else {
										logger.warn("No server selected");
										JOptionPane
												.showMessageDialog(
														frame,
														"No server selected",
														"Info",
														JOptionPane.INFORMATION_MESSAGE);
									}
								}
							});
						}
						btnChangeButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								logger.debug("Changing SSH-Server");
								if (serverList.getSelectedValue() != null
										&& serverList.getSelectedValue()
												.length() > 0) {
									lastSelectedIndex = serverList
											.getSelectedIndex();
									new SSHConfigDialog(serverList
											.getSelectedValue());
									reloadList();
								} else {
									logger.warn("No server selected!");
									JOptionPane.showMessageDialog(frame,
											"No server selected", "Info",
											JOptionPane.INFORMATION_MESSAGE);
								}
							}
						});
					}
					btnDelete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							logger.debug("Deleteing SSH-Server");
							if (serverList.getSelectedValue() != null
									&& serverList.getSelectedValue().length() > 0) {
								lastSelectedIndex = serverList
										.getSelectedIndex() - 1;
								if (lastSelectedIndex == -1
										&& serverList.getComponentCount() > 0) {
									lastSelectedIndex = 0;
								}
								delete(serverList.getSelectedValue());
								reloadList();
							} else {
								logger.warn("No server selected!");
								JOptionPane.showMessageDialog(frame,
										"No server selected", "Info",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
					});
				}
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						logger.debug("Addinng new SSH-Server");
						new SSHConfigDialog(null);
						lastSelectedIndex = serverList.getLastVisibleIndex() + 1;
						reloadList();
					}
				});
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BorderLayout(0, 0));
			{
				JPanel loadCanclePanel = new JPanel();
				buttonPane.add(loadCanclePanel, BorderLayout.EAST);
				{
					JButton okButton = new JButton("Export");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (serverList.getSelectedValue() != null
									&& serverList.getSelectedValue().length() > 0) {
								sshExport(serverList.getSelectedValue());
							} else {
								logger.warn("No server selected!");
								JOptionPane.showMessageDialog(frame,
										"No server selected", "Info",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
					});
					loadCanclePanel.add(okButton);
					okButton.setActionCommand("OK");
					getRootPane().setDefaultButton(okButton);
				}
				{
					JButton cancelButton = new JButton("Cancel");
					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							logger.debug("Disposing SSHExportServerListDialog");
							dispose();
						}
					});
					loadCanclePanel.add(cancelButton);
					cancelButton.setActionCommand("Cancel");
				}
			}
		}
		setLocationRelativeTo(getOwner());
		setModal(true);
		logger.debug("Creating SSHExportServerListDialog complete");
		setVisible(true);
	}

	/**
	 * Export files onto selected Server
	 * 
	 * @param hostName
	 *            Name of selected Host
	 */
	public void sshExport(final String hostName) {
		logger.info("Start exporting onto " + serverList.getSelectedValue()
				+ "!");

		// export
		boolean success = true;
		SSHServer server = SSHSettings.SERVERLIST.get(hostName);
		SSHFileExport export = new SSHFileExport(server.getHOST(),
				server.getPORT(), server.getUSERNAME(), server.getPASSWORD(),
				server.getDIR());
		ArrayList<String> mainFiles = new ArrayList<String>();
		if (loggingProperties) {
			mainFiles.add("out/logging.properties");
		}
		if (configProperties) {
			mainFiles.add("out/config.properties");
		}
		if (!export.export(mainFiles)) {
			success = false;
		}

		if (ConfigSettings.POLLINGMODULE != null && files.size() > 0) {
			ArrayList<String> filePaths = new ArrayList<String>();
			for (int i = 0; i < 4; i++) {
				for (String file : files) {
					if (PropertyFileSettings.PROPERTIESPATHS
							.get(ConfigSettings.POLLINGMODULE)[i] != null
							&& PropertyFileSettings.PROPERTIESPATHS
									.get(ConfigSettings.POLLINGMODULE)[i]
									.contains(file)) {
						if (server.getDIR()
								.substring(server.getDIR().length() - 1)
								.equals("/")) {
							export.changeDir(server.getDIR()
									+ PropertyFileSettings.PROPERTIESPATHS
											.get(ConfigSettings.POLLINGMODULE)[i]
											.replace(file, "").replace(
													"config/", ""));
						} else {
							export.changeDir(server.getDIR()
									+ "/"
									+ PropertyFileSettings.PROPERTIESPATHS
											.get(ConfigSettings.POLLINGMODULE)[i]
											.replace(file, "").replace(
													"config/", ""));
						}
						filePaths.add("out/"
								+ PropertyFileSettings.PROPERTIESPATHS
										.get(ConfigSettings.POLLINGMODULE)[i]
										.replace("config/", ""));
						if (!export.export(filePaths)) {
							success = false;
						}
					}
				}
			}
		}
		if (success) {
			logger.info("Export successful!");
			JOptionPane.showMessageDialog(getOwner(), "EXPORT SUCCESSFUL",
					"export", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		} else {
			logger.error("Export failed!");
			JOptionPane.showMessageDialog(getOwner(), "EXPORT FAILED!",
					"export", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Update Serverlist
	 */
	private void reloadList() {
		logger.debug("Reloading SSH-Serverlist");
		final String[] values = new String[SSHSettings.SERVERNAMELIST.size()];
		for (int i = 0; i < SSHSettings.SERVERNAMELIST.size(); i++) {
			values[i] = SSHSettings.SERVERNAMELIST.get(i);
		}
		serverList.setModel(new AbstractListModel<String>() {
			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		serverList.setSelectedIndex(lastSelectedIndex);
	}

	/**
	 * Delete selected Server from list
	 * 
	 * @param serverName
	 *            Name of Server to delete
	 */
	private void delete(final String serverName) {
		SSHSettings.SERVERNAMELIST.remove(serverName);
		SSHSettings.SERVERLIST.remove(serverName);
	}

}