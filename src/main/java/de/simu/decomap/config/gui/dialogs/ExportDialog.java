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
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import de.simu.decomap.config.gui.export.ExportFiles;
import de.simu.decomap.config.gui.values.ConfigSettings;
import de.simu.decomap.config.gui.values.PropertyFileSettings;

import javax.swing.BoxLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dialog for exportation of Property-Files
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 *
 */
@SuppressWarnings("serial")
public class ExportDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JCheckBox loggingProperties;
	private JCheckBox configProperties;
	private JPanel specificFilesPanel;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Create the dialog.
	 */
	public ExportDialog() {
		logger.debug("Creating ExportDialog");
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			{
				{
					JPanel standardFilesPanel = new JPanel();
					contentPanel.add(standardFilesPanel);
					standardFilesPanel.setBorder(new EmptyBorder(5, 5, 0, 5));
					standardFilesPanel.setLayout(new GridLayout(0, 1, 0, 0));
					{
						configProperties = new JCheckBox("config.properties");
						if (ConfigSettings.POLLINGMODULE != null) {
							standardFilesPanel.add(configProperties);
							configProperties.setSelected(true);
						} else {
							configProperties.setSelected(false);
						}
					}
					{
						loggingProperties = new JCheckBox("logging.properties");
						standardFilesPanel.add(loggingProperties);
						loggingProperties.setSelected(true);
					}
				}
				specificFilesPanel = new JPanel();
				contentPanel.add(specificFilesPanel);
				specificFilesPanel.setBorder(new EmptyBorder(0, 5, 5, 5));
				specificFilesPanel.setLayout(new GridLayout(0, 1, 0, 0));

				// load specific files
				String[] fileList = PropertyFileSettings.PROPERTIESPATHS
						.get(ConfigSettings.POLLINGMODULE);
				if (fileList != null) {
					for (String file : fileList) {
						{
							if (file != null && file.trim().length() > 0) {
								String[] filename = file.split("/");
								if (logger.isDebugEnabled()) {
									logger.debug("Adding "+filename[filename.length - 1]+ " onto panel");
								}
								JCheckBox specificFile = new JCheckBox(
										filename[filename.length - 1]);
								specificFile.setSelected(true);
								specificFilesPanel.add(specificFile);
							}
						}
					}
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton localExportButton = new JButton("Local export");
				localExportButton.addActionListener(new ActionListener() {

					// export files
					@Override
					public void actionPerformed(ActionEvent arg0) {
						logger.info("Local export!");
						JFileChooser chooser = new JFileChooser(
								ConfigSettings.LOADEDPATH);
						chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int returnVal = chooser.showSaveDialog(new Dialog(
								getOwner()));
						if (returnVal == JFileChooser.APPROVE_OPTION) {

							if (ExportFiles.export(
									configProperties.isSelected(),
									loggingProperties.isSelected(), getFiles(),
									chooser.getSelectedFile().getPath()+"/config")) {
								logger.info("Export successful into: "+chooser.getSelectedFile().getPath());
								JOptionPane.showMessageDialog(getOwner(),
										"EXPORT SUCCESSFUL", "export",
										JOptionPane.INFORMATION_MESSAGE);
								dispose();
							} else {
								logger.error("Export failed!");
								JOptionPane.showMessageDialog(getOwner(),
										"EXPORT FAILED!", "export",
										JOptionPane.ERROR_MESSAGE);
								dispose();
							}
						}
					}
				});
				localExportButton.setActionCommand("OK");
				buttonPane.add(localExportButton);
				getRootPane().setDefaultButton(localExportButton);
			}
			{
				JButton sshExportButton = new JButton("SSH export");
				sshExportButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						logger.info("SSH export!");
						if (ExportFiles.export(configProperties.isSelected(),
								loggingProperties.isSelected(), getFiles(),
								"out/")) {
							dispose();
							new SSHExportServerListDialog(configProperties
									.isSelected(), loggingProperties
									.isSelected(), getFiles());
						} else {
							logger.error("SSH export failed!");
							JOptionPane.showMessageDialog(getOwner(),
									"EXPORT FAILED!", "export",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				buttonPane.add(sshExportButton);
			}

			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					// close dialog
					@Override
					public void actionPerformed(ActionEvent arg0) {
						logger.debug("Disposing ExportDialog");
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getOwner());
		pack();
		setVisible(true);
		logger.debug("Creating ExportDialog complete!");
	}

	/**
	 * Name of the Files selected
	 * @return
	 * 		List of selected Filenames
	 */
	private ArrayList<String> getFiles() {
		ArrayList<String> files = new ArrayList<String>();
		for (Component box : specificFilesPanel.getComponents()) {
			if (((JCheckBox) box).isSelected()) {
				files.add(((JCheckBox) box).getText());
			}
		}
		return files;
	}

}
