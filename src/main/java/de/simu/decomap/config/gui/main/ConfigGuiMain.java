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
package de.simu.decomap.config.gui.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.simu.decomap.config.gui.dialogs.ApplicationConfigDialog;
import de.simu.decomap.config.gui.dialogs.ExportDialog;
import de.simu.decomap.config.gui.dialogs.LoggingConfigDialog;
import de.simu.decomap.config.gui.dialogs.MapServerConfigDialog;
import de.simu.decomap.config.gui.dialogs.ModeConfigDialog;
import de.simu.decomap.config.gui.dialogs.SSHServerListDialog;
import de.simu.decomap.config.gui.loads.LoadConfigProperties;
import de.simu.decomap.config.gui.values.ConfigSettings;
import de.simu.decomap.config.gui.values.PropertyFileSettings;
import de.simu.decomap.config.gui.values.SSHServer;
import de.simu.decomap.config.gui.values.SSHSettings;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.GridLayout;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * MainFrame
 * 
 * @author Leonid Schwenke, DECOIT GmbH
 * 
 */
@SuppressWarnings("serial")
public class ConfigGuiMain extends JFrame {

	private static ConfigGuiMain mainFrame;

	private JPanel contentPane;

	private int height;
	private int width;
	private JButton applicationConfig;
	private JButton modeConfig;
	private JButton mapServerConfig;
	private JButton logConfig;
	private JPanel underLoadExportPanel;
	private JButton btnNewButton;
	private JPanel sshConfigPanel;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final static Logger mainLogger = LoggerFactory.getLogger("Main");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					mainFrame = new ConfigGuiMain();
					mainFrame.setLocationRelativeTo(null);
					mainFrame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Critical Error!: " + e.getStackTrace(), "ERROR",
							JOptionPane.ERROR_MESSAGE);
					mainLogger.error(
							"Critical Error! Shutting down Application!", e);
					System.exit(1);
				}
			}
		});
	}

	public static void error(String errorMessage, Throwable error, Component component, boolean fatal) {
		if(component == null){
			component = mainFrame;
		}
		mainLogger.error(errorMessage, error);
		if (fatal) {
			JOptionPane.showMessageDialog(component,
					errorMessage, "ERROR",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} else{
			JOptionPane.showMessageDialog(component,
					errorMessage + ": " + error.getStackTrace(), "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Create the mainframe.
	 */
	public ConfigGuiMain() {
		PropertyConfigurator.configureAndWatch("settings/logging.properties");
		logger.info("Application start!");
		mainFrame = this;
		loadMods();
		loadSSHProperties("settings/ssh_export.properties");
		initGUI();
	}

	/**
	 * Creating GUI components
	 */
	private void initGUI() {
		logger.debug("Creating ConfigGUIMain!");
		setTitle("IfMapClient Config GUI");
		setResizable(false);

		// closing operation
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				logger.info("Shuting down! Bye :D");
				saveProperties();
				System.exit(0);
			}
		});

		setBounds(100, 100, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel dialogButtonPanel = new JPanel();
		dialogButtonPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(dialogButtonPanel, BorderLayout.NORTH);

		// Button for the ApplicationConfigDialog
		applicationConfig = new JButton("Application configuration");
		applicationConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				disableApplicationConfig(true);
				new ApplicationConfigDialog(mainFrame);
			}
		});

		// Button for the ModeConfigDialog
		modeConfig = new JButton("Mode configuration");
		modeConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				disableModeConfig(true);
				new ModeConfigDialog(mainFrame);
			}
		});
		dialogButtonPanel.setLayout(new GridLayout(4, 0, 0, 0));
		dialogButtonPanel.add(applicationConfig);

		// Button for the MapServerConfigDialog
		mapServerConfig = new JButton("Map-Server configuration");
		mapServerConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				disableMapServerConfig(true);
				new MapServerConfigDialog(mainFrame);
			}
		});
		dialogButtonPanel.add(mapServerConfig);

		// Button for the LoggingConfigDialog
		logConfig = new JButton("Logging configuration");
		logConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disableLoggingConfig(true);
				new LoggingConfigDialog(mainFrame);
			}
		});
		dialogButtonPanel.add(logConfig);
		dialogButtonPanel.add(modeConfig);

		JPanel loadAndExportPanel = new JPanel();
		contentPane.add(loadAndExportPanel, BorderLayout.SOUTH);
		loadAndExportPanel.setLayout(new BorderLayout(0, 0));

		underLoadExportPanel = new JPanel();
		loadAndExportPanel.add(underLoadExportPanel, BorderLayout.EAST);

		// Button for loading config.properties files
		JButton load = new JButton("  Load  ");
		underLoadExportPanel.add(load);

		// Button for writing the output files
		JButton export = new JButton("Export");
		underLoadExportPanel.add(export);

		sshConfigPanel = new JPanel();
		loadAndExportPanel.add(sshConfigPanel, BorderLayout.WEST);

		btnNewButton = new JButton("SSH serverlist");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SSHServerListDialog(mainFrame);
			}
		});
		sshConfigPanel.add(btnNewButton);
		export.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (applicationConfig.isEnabled()
						&& mapServerConfig.isEnabled() && logConfig.isEnabled()
						&& modeConfig.isEnabled()) {
					new ExportDialog();
				} else {
					logger.warn("Not all configuration dialogs closed!");
					JOptionPane.showMessageDialog(getOwner(),
							"Please first close all configuration dialogs",
							"error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Loading local config.properies");
				if (applicationConfig.isEnabled()
						&& mapServerConfig.isEnabled() && logConfig.isEnabled()
						&& modeConfig.isEnabled()) {
					JFileChooser chooser = new JFileChooser(
							ConfigSettings.LOADEDPATH);
					int returnVal = chooser.showOpenDialog(new Dialog(
							getOwner()));
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						LoadConfigProperties loader = new LoadConfigProperties(
								chooser.getSelectedFile().getPath());
						if (loader.load()) {
							logger.info("Loading successful!");
							JOptionPane.showMessageDialog(getOwner(),
									"LOAD SUCCESSFUL", "load",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							logger.info("Loading failed!");
							JOptionPane.showMessageDialog(getOwner(),
									"LOAD FAILED!", "load",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					logger.warn("Not all configuration dialogs closed!");
					JOptionPane.showMessageDialog(getOwner(),
							"Please first close all configuration dialogs",
							"error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		pack();
	}
	
	/**
	 * loading SSH properties
	 * 
	 * @param path
	 *            path to the SSH property file
	 */
	private void loadSSHProperties(final String path) {
		try {
			logger.info("Start loading SSH-properties!");
			Properties properties = new Properties();
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream(path));
			properties.load(stream);

			String[] nameList = properties.getProperty("ssh.namelist", "")
					.split(",");

			for (String name : nameList) {

				SSHServer server = new SSHServer(name, properties.getProperty(
						"ssh.host." + name, ""), Integer.parseInt(properties
						.getProperty("ssh.port." + name, "22")),
						properties.getProperty("ssh.username." + name, ""),
						properties.getProperty("ssh.password." + name, ""),
						properties.getProperty("ssh.dir." + name, ""));
				SSHSettings.SERVERNAMELIST.add(name);
				SSHSettings.SERVERLIST.put(name, server);

			}
			logger.info("Loading successful!");
		} catch (Exception e) {
			error("Error while loading SSH-Properties!", e, this, false);
		}
	}

	/**
	 * loading informations from the settings file
	 * 
	 * @param path
	 *            path to the setting file
	 */
	private void loadMods() {
		try {
			logger.info("Start loading polling_mapping!");
			BufferedReader br = new BufferedReader(new FileReader(
					"settings/polling_mapping"));
			String line;
			while ((line = br.readLine()) != null) {
				PropertyFileSettings.MODULE.add(line);
				String[] paths = new String[4];
				for (int i = 0; i < 4; i++) {
					paths[i] = br.readLine();
				}
				PropertyFileSettings.PROPERTIESPATHS.put(line, paths);
			}
			br.close();
			logger.info("Loading done!");

			// Saving Lineinformations
			ArrayList<String> infos;

			// Templates for properties infos
			HashMap<String, ArrayList<String>> template = new HashMap<String, ArrayList<String>>();

			String deepLine;

			logger.info("Start loading polling_mapping!");
			br = new BufferedReader(new FileReader(
					"settings/propertiesTemplates"));
			while ((line = br.readLine()) != null) {
				if (!line.trim().equals("") && !line.startsWith("#")) {
					if (logger.isDebugEnabled()) {
						logger.debug("Parsing block: " + line);
					}
					infos = new ArrayList<String>();
					while ((deepLine = br.readLine()) != null
							&& !deepLine.trim().equals("")
							&& !deepLine.startsWith("#")) {
						if (deepLine.startsWith("@")) {
							for (String temp : template.get(deepLine)) {
								infos.add(temp);
							}
						} else {
							infos.add(deepLine);
						}
					}
					template.put("@" + line, infos);
				}
			}
			br.close();
			logger.info("Loading done!");

			logger.info("Start loading propertiesInfos!");
			br = new BufferedReader(new FileReader("settings/propertiesInfos"));

			while ((line = br.readLine()) != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Parsing block: " + line);
				}
				String[] counts = line.split(";");
				HashMap<String, ArrayList<String>> files = new HashMap<String, ArrayList<String>>();

				for (int j = 1; j < counts.length; j++) {
					infos = new ArrayList<String>();
					for (int i = 0; i < Integer.parseInt(counts[j]); i++) {
						deepLine = br.readLine();
						if (deepLine.startsWith("@")) {
							for (String temp : template.get(deepLine)) {
								infos.add(temp);
							}
						} else {
							infos.add(deepLine);
						}
					}
					files.put(j - 1 + "", infos);
					br.readLine();
				}

				// infos = new ArrayList<String>();
				// for (int i = 0; i < Integer.parseInt(counts[2]); i++) {
				// infos.add(br.readLine());
				// }
				// files.put("1", infos);
				// br.readLine();
				//
				// infos = new ArrayList<String>();
				// for (int i = 0; i < Integer.parseInt(counts[3]); i++) {
				// infos.add(br.readLine());
				// }
				// files.put("2", infos);
				// br.readLine();
				//
				// if (counts.length == 5) {
				// infos = new ArrayList<String>();
				// for (int i = 0; i < Integer.parseInt(counts[4]); i++) {
				// infos.add(br.readLine());
				// }
				// files.put("3", infos);
				// br.readLine();
				// }
				PropertyFileSettings.PROPERTIESINFOS.put(counts[0], files);
			}

			br.close();
			logger.info("loading done!");

		} catch (Exception e) {
			error("Error on loading settings!", e, this, true);
		}
	}

	/**
	 * saving all ssh servers
	 */
	private void saveProperties() {
		logger.info("Saving properties!");
		Properties prop;
		OutputStream output = null;
		try {
			prop = new Properties();
			output = new FileOutputStream("settings/ssh_export.properties");

			StringBuilder namelist = new StringBuilder();

			// set the properties value
			for (String name : SSHSettings.SERVERNAMELIST) {
				if (name.length() > 0) {
					namelist.append(name + ",");
					prop.setProperty("ssh.host." + name, SSHSettings.SERVERLIST
							.get(name).getHOST());
					prop.setProperty("ssh.port." + name, SSHSettings.SERVERLIST
							.get(name).getPORT() + "");
					prop.setProperty("ssh.username." + name,
							SSHSettings.SERVERLIST.get(name).getUSERNAME());
					prop.setProperty("ssh.password." + name,
							SSHSettings.SERVERLIST.get(name).getPASSWORD());
					prop.setProperty("ssh.dir." + name, SSHSettings.SERVERLIST
							.get(name).getDIR());
				}
			}

			if (namelist.length() > 1) {
				prop.setProperty("ssh.namelist",
						namelist.substring(0, namelist.length() - 1));
			}

			// save properties to project root folder
			prop.store(output, null);

		} catch (Exception e) {
			error("Error while saving properties!", e, this, false);
		} finally {
			if (output != null) {
				try {
					logger.info("Saving successful!");
					output.close();
				} catch (Exception e) {
					error("Error while saving!", e, this, false);
				}
			}

		}
	}

	public void disableApplicationConfig(final boolean disable) {
		applicationConfig.setEnabled(!disable);
	}

	public void disableMapServerConfig(final boolean disable) {
		mapServerConfig.setEnabled(!disable);
	}

	public void disableLoggingConfig(final boolean disable) {
		logConfig.setEnabled(!disable);
	}

	public void disableModeConfig(final boolean disable) {
		modeConfig.setEnabled(!disable);
	}
}
