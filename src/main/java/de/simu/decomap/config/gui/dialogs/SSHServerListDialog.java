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
import javax.swing.JList;

import java.awt.GridLayout;

import javax.swing.AbstractListModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.simu.decomap.config.gui.connection.SSHFileDownload;
import de.simu.decomap.config.gui.main.ConfigGuiMain;
import de.simu.decomap.config.gui.values.SSHServer;
import de.simu.decomap.config.gui.values.SSHSettings;

/**
 * SSHServer-List for Loading
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 *
 */
@SuppressWarnings("serial")
public class SSHServerListDialog extends JDialog {


	private final JPanel contentPanel = new JPanel();

	private JList<String> serverList;
	private SSHServerListDialog frame = this;
	private int lastSelectedIndex = 0;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Create the dialog.
	 */
	public SSHServerListDialog(final ConfigGuiMain mainFrame) {
		logger.debug("Creating SSHServerListDialog");
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
											&& serverList.getSelectedValue().length() > 0) {
										lastSelectedIndex = serverList.getSelectedIndex();
										SSHServer copyServer = SSHSettings.SERVERLIST
												.get(serverList.getSelectedValue());
										copyServer.setName(copyServer.getName() + " copy");
										while (true) {
											if (!SSHSettings.SERVERNAMELIST
													.contains(copyServer.getName())) {
												SSHSettings.SERVERNAMELIST.add(copyServer
														.getName());
												SSHSettings.SERVERLIST.put(
														copyServer.getName(), copyServer);
												break;
											} else {
												copyServer.setName(copyServer.getName()
														+ " copy");
											}
										}
										reloadList();
									} else {
										logger.warn("No server selected");
										JOptionPane.showMessageDialog(frame,
												"No server selected", "Info",
												JOptionPane.INFORMATION_MESSAGE);
									}
								}
							});
						}
						btnChangeButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								logger.debug("Changing SSH-Server");
								if (serverList.getSelectedValue() != null
										&& serverList.getSelectedValue().length() > 0) {
									lastSelectedIndex = serverList.getSelectedIndex();
									new SSHConfigDialog(serverList.getSelectedValue());
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
								lastSelectedIndex = serverList.getSelectedIndex() - 1;
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
					JButton okButton = new JButton(" Load ");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (serverList.getSelectedValue() != null
									&& serverList.getSelectedValue().length() > 0) {
								logger.info("Start loading "+serverList.getSelectedValue());
								SSHFileDownload sshLoader = new SSHFileDownload();
								sshLoader.sshPreload(serverList.getSelectedValue(), SSHServerListDialog.this);
							} else {
								logger.debug("No server selected!");
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
							logger.debug("Disposing SSHServerListDialog");
							dispose();
						}
					});
					loadCanclePanel.add(cancelButton);
					cancelButton.setActionCommand("Cancel");
				}
			}
		}
		setLocationRelativeTo(mainFrame);
		setModal(true);
		setVisible(true);
	}


	/**
	 *  reload server List
	 */
	private void reloadList() {
		logger.debug("Reloading SSH-Serverlist");
		final String[] values = new String[SSHSettings.SERVERNAMELIST.size()];
		for (int i = 0; i < SSHSettings.SERVERNAMELIST.size(); i++) {
			values[i] = SSHSettings.SERVERNAMELIST.get(i);
		}
		serverList.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6076290316207355001L;

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
	 * 		Name of server to delete
	 */
	private void delete(final String serverName) {
		SSHSettings.SERVERNAMELIST.remove(serverName);
		SSHSettings.SERVERLIST.remove(serverName);
	}

}
