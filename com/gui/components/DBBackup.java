package com.gui.components;

import static com.gui.utils.GuiUtils.DATE_FORMAT_YMDHMS;
import static com.gui.utils.GuiUtils.getEnv;
import static com.gui.utils.GuiUtils.getToday;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DBBackup {
	private JLabel dbNameJL;
	private JButton restoreJB;
	private JButton backupJB;
	private JButton openPathJB;
	private JButton savePathJB;
	private JTextField openPathJTF;
	private JLabel openPathJL;
	private JTextField savePathJTF;
	private JPasswordField passWordJPF;
	private JTextField userNameJTF;
	private JTextField dbNameJTF;
	private JLabel savePathJL;
	private JLabel passWordJL;
	private JLabel userNameJL;
	private static JPanel orclJP;
	//
	private JFileChooser jfc = null;
	private static final String DEFAULT_BACKUPPATH = "E:/logs";

	public DBBackup() {
		initGUI();
	}

	public static JPanel getJPanel() {
		if (null == orclJP) {
			new DBBackup();
		}
		return orclJP;
	}

	private void initGUI() {
		try {
			{
				orclJP = new JPanel();
				GroupLayout orclJPLayout = new GroupLayout((JComponent) orclJP);
				orclJP.setLayout(orclJPLayout);
				{
					userNameJL = new JLabel();
					userNameJL.setText("UserName:");
					userNameJL.setSize(68, 20);
				}
				{
					dbNameJL = new JLabel();
					dbNameJL.setText("HostName:");
					dbNameJL.setSize(68, 20);
				}
				{
					passWordJL = new JLabel();
					passWordJL.setText("PassWord:");
					passWordJL.setSize(68, 20);
				}
				{
					dbNameJTF = new JTextField();
					dbNameJTF.setText("192.168.100.3/ORCL");
				}
				{
					userNameJTF = new JTextField();
					// userNameJTF.setText("userNameJTF");
				}
				{
					passWordJPF = new JPasswordField();
					// passWordJPF.setText("passWordJTF");
				}
				{
					savePathJTF = new JTextField();
					// savePathJTF.setText("savePathJTF");
				}
				{
					openPathJTF = new JTextField();
					// openPathJTF.setText("openPathJTF");
				}
				{
					savePathJB = new JButton();
					savePathJB.setText("...");
					savePathJB.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							savePathJBActionPerformed(evt);
						}
					});
				}
				{
					openPathJB = new JButton();
					openPathJB.setText("...");
					openPathJB.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							openPathJBActionPerformed(evt);
						}
					});
				}
				{
					backupJB = new JButton();
					backupJB.setText("Export");
					backupJB.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							backupJBActionPerformed(evt);
						}
					});
				}
				{
					restoreJB = new JButton();
					restoreJB.setText("Import");
					restoreJB.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							restoreJBActionPerformed(evt);
						}
					});
				}
				{
					savePathJL = new JLabel();
					savePathJL.setText("SavePath:");
					savePathJL.setSize(68, 20);
				}
				{
					openPathJL = new JLabel();
					openPathJL.setText("OpenPath:");
					openPathJL.setSize(68, 20);
				}
				orclJPLayout
						.setHorizontalGroup(orclJPLayout
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										orclJPLayout
												.createParallelGroup()
												.addGroup(
														GroupLayout.Alignment.LEADING,
														orclJPLayout
																.createSequentialGroup()
																.addComponent(
																		openPathJL,
																		GroupLayout.PREFERRED_SIZE,
																		62,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(6))
												.addGroup(
														GroupLayout.Alignment.LEADING,
														orclJPLayout
																.createSequentialGroup()
																.addComponent(
																		savePathJL,
																		GroupLayout.PREFERRED_SIZE,
																		62,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(6))
												.addComponent(
														passWordJL,
														GroupLayout.Alignment.LEADING,
														GroupLayout.PREFERRED_SIZE,
														68,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														userNameJL,
														GroupLayout.Alignment.LEADING,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														dbNameJL,
														GroupLayout.Alignment.LEADING,
														GroupLayout.PREFERRED_SIZE,
														68,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										orclJPLayout
												.createParallelGroup()
												.addComponent(
														openPathJTF,
														GroupLayout.Alignment.LEADING,
														0, 226, Short.MAX_VALUE)
												.addGroup(
														orclJPLayout
																.createSequentialGroup()
																.addComponent(
																		savePathJTF,
																		GroupLayout.PREFERRED_SIZE,
																		225,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(0,
																		0,
																		Short.MAX_VALUE))
												.addGroup(
														orclJPLayout
																.createSequentialGroup()
																.addComponent(
																		passWordJPF,
																		GroupLayout.PREFERRED_SIZE,
																		225,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(0,
																		0,
																		Short.MAX_VALUE))
												.addGroup(
														orclJPLayout
																.createSequentialGroup()
																.addComponent(
																		userNameJTF,
																		GroupLayout.PREFERRED_SIZE,
																		225,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(0,
																		0,
																		Short.MAX_VALUE))
												.addGroup(
														orclJPLayout
																.createSequentialGroup()
																.addComponent(
																		dbNameJTF,
																		GroupLayout.PREFERRED_SIZE,
																		225,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(0,
																		0,
																		Short.MAX_VALUE))
												.addGroup(
														GroupLayout.Alignment.LEADING,
														orclJPLayout
																.createSequentialGroup()
																.addGap(0,
																		86,
																		Short.MAX_VALUE)
																.addComponent(
																		backupJB,
																		GroupLayout.PREFERRED_SIZE,
																		64,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		restoreJB,
																		GroupLayout.PREFERRED_SIZE,
																		64,
																		GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										orclJPLayout
												.createParallelGroup()
												.addComponent(
														savePathJB,
														GroupLayout.Alignment.LEADING,
														GroupLayout.PREFERRED_SIZE,
														29,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														openPathJB,
														GroupLayout.Alignment.LEADING,
														GroupLayout.PREFERRED_SIZE,
														29,
														GroupLayout.PREFERRED_SIZE))
								.addContainerGap(26, 26));
				orclJPLayout
						.setVerticalGroup(orclJPLayout
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										orclJPLayout
												.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
												.addComponent(
														dbNameJTF,
														GroupLayout.Alignment.BASELINE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														dbNameJL,
														GroupLayout.Alignment.BASELINE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										orclJPLayout
												.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
												.addComponent(
														userNameJTF,
														GroupLayout.Alignment.BASELINE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														userNameJL,
														GroupLayout.Alignment.BASELINE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										orclJPLayout
												.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
												.addComponent(
														passWordJPF,
														GroupLayout.Alignment.BASELINE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														passWordJL,
														GroupLayout.Alignment.BASELINE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										orclJPLayout
												.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
												.addComponent(
														savePathJB,
														GroupLayout.Alignment.BASELINE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														savePathJTF,
														GroupLayout.Alignment.BASELINE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														savePathJL,
														GroupLayout.Alignment.BASELINE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										orclJPLayout
												.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
												.addComponent(
														openPathJB,
														GroupLayout.Alignment.BASELINE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														openPathJTF,
														GroupLayout.Alignment.BASELINE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														openPathJL,
														GroupLayout.Alignment.BASELINE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(21)
								.addGroup(
										orclJPLayout
												.createParallelGroup(
														GroupLayout.Alignment.BASELINE)
												.addComponent(
														backupJB,
														GroupLayout.Alignment.BASELINE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														restoreJB,
														GroupLayout.Alignment.BASELINE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addContainerGap(18, 18));
			}
			// }
			// pack();
			// this.setSize(400, 300);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

	private void savePathJBActionPerformed(ActionEvent evt) {
		System.out.println("savePathJB.actionPerformed, event=" + evt);
		// TODO add your code for savePathJB.actionPerformed
		jfc = new JFileChooser(DEFAULT_BACKUPPATH);
		this.jfc.setFileSelectionMode(1);
		int flag = this.jfc.showSaveDialog(null);
		if (flag == 0) {
			String savePath = this.jfc.getSelectedFile().getAbsolutePath();
			String DBName = this.dbNameJTF.getText();

			if (DBName.isEmpty()) {
				showMyDialog("DataBase Name is null, Please check!",
						"Set DataBase Name", 2);
			} else {
				String scheam = DBName;
				if (DBName.indexOf("/") > 0) {
					int index = DBName.lastIndexOf("/");
					scheam = DBName.substring(index + 1);
				}

				String saveName = "/" + scheam + "_"
						+ getToday(DATE_FORMAT_YMDHMS) + ".dmp";
				String outFilePath = savePath + saveName;
				this.savePathJTF.setText(outFilePath);
			}
		}
	}

	private void openPathJBActionPerformed(ActionEvent evt) {
		System.out.println("openPathJB.actionPerformed, event=" + evt);
		// TODO add your code for openPathJB.actionPerformed
		this.jfc = new JFileChooser(DEFAULT_BACKUPPATH);
		this.jfc.setFileSelectionMode(0);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.dmp",
				new String[] { "dmp" });
		this.jfc.setFileFilter(filter);
		int flag = this.jfc.showOpenDialog(null);
		if (flag == 0) {
			String path = this.jfc.getSelectedFile().getAbsolutePath();
			String prefix = path.substring(path.lastIndexOf(".") + 1);
			if ("dmp".equalsIgnoreCase(prefix))
				this.openPathJTF.setText(path);
			else {
				showMyDialog("您选择的文件类型不是*.dmp文件,请检查!", "文件类型不正确", 2);
			}
		}
	}

	private void backupJBActionPerformed(ActionEvent evt) {
		System.out.println("backupJB.actionPerformed, event=" + evt);
		// TODO add your code for backupJB.actionPerformed
		try {
			boolean runFlag = true;
			String DBName = this.dbNameJTF.getText();
			String userName = this.userNameJTF.getText();
			String passWord = String.valueOf(this.passWordJPF.getPassword());
			String orclBinPath = getOracleBinPath();
			String outFilePath = this.savePathJTF.getText();
			String fullFlag = "n";

			if (orclBinPath.isEmpty()) {
				runFlag = false;
			}
			if (DBName.isEmpty()) {
				runFlag = false;
			}
			if (userName.isEmpty()) {
				runFlag = false;
			}
			if (outFilePath.isEmpty()) {
				String scheam = DBName;
				if (DBName.indexOf("/") > 0) {
					int index = DBName.lastIndexOf("/");
					scheam = DBName.substring(index + 1);
				}
				outFilePath = DEFAULT_BACKUPPATH + "/" + scheam + "_"
						+ getToday(DATE_FORMAT_YMDHMS) + ".dmp";
				System.out.println(outFilePath + "AAA");
				int cf = JOptionPane.showConfirmDialog(null,
						"备份文件输出路径未选择,是否使用默认备份路径? " + outFilePath, "选择备份路径", 0);
				if (cf == 0)
					this.savePathJTF.setText(outFilePath);
				else {
					runFlag = false;
				}
			}

			if (runFlag) {
				String orclCMD = getOracleExpCMD(orclBinPath, DBName, userName,
						passWord, outFilePath, fullFlag);

				if (orclCMD.isEmpty()) {
					showMyDialog("expCMD 命令生成失败!", "expCMD", 2);
				} else {
					String scheam = DBName;
					if (DBName.indexOf("/") > 0) {
						int index = DBName.lastIndexOf("/");
						scheam = DBName.substring(index + 1);
					}
					String batFilePath = outFilePath.substring(0,
							outFilePath.lastIndexOf("/") + 1)
							+ scheam + "-exp.bat";

					runFlag = generationOracleBatFile(orclBinPath, orclCMD,
							batFilePath);
					if (runFlag) {
						String msgContent = "exp.exe Command Execute Success!";
						String msgTitle = "Success";
						int msgType = 1;

						int f = execCMD(batFilePath);

						if (f != 0) {
							msgContent = "exp.exe Eommand Execute Failure!";
							msgTitle = "Failure";
							msgType = 0;
						}
						showMyDialog(msgContent, msgTitle, msgType);
					} else {
						showMyDialog(batFilePath + " 生成失败!", "bat file", 2);
					}
				}
			} else {
				showMyDialog(
						"Oracle bin DIR not found OR DataBase Name not set OR UserName not set OR backup URI not set. Please check!",
						"plase set init params", 2);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void restoreJBActionPerformed(ActionEvent evt) {
		System.out.println("restoreJB.actionPerformed, event=" + evt);
		// TODO add your code for restoreJB.actionPerformed
		try {
			boolean runFlag = true;
			String DBName = this.dbNameJTF.getText();
			String userName = this.userNameJTF.getText();
			String passWord = String.valueOf(this.passWordJPF.getPassword());
			String orclBinPath = getOracleBinPath();
			String inFilePath = this.openPathJTF.getText();
			String fullFlag = "n";
			if (orclBinPath.isEmpty()) {
				runFlag = false;
			}
			if (DBName.isEmpty()) {
				runFlag = false;
			}
			if (userName.isEmpty()) {
				runFlag = false;
			}

			if (inFilePath.isEmpty()) {
				runFlag = false;
				int cfd = JOptionPane.showConfirmDialog(null,
						"备份文件未选择,是否现在去选择?", "备份文件选择", 1);
				if (cfd == 0) {
					this.jfc = new JFileChooser(DEFAULT_BACKUPPATH);
					this.jfc.setFileSelectionMode(0);
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
							"*.dmp", new String[] { "dmp" });
					this.jfc.setFileFilter(filter);
					int flag = this.jfc.showOpenDialog(null);
					if (flag == 0) {
						String path = this.jfc.getSelectedFile()
								.getAbsolutePath();
						String prefix = path.substring(path.lastIndexOf("."));
						if ("dmp".equalsIgnoreCase(prefix)) {
							this.openPathJTF.setText(path);
						} else {
							showMyDialog("您选择的文件类型不是*.dmp文件,请检查!", "文件类型不正确", 2);
							runFlag = false;
						}
					}
				} else {
					runFlag = false;
				}
			}
			if (runFlag) {
				String orclCMD = getOracleImpCMD(orclBinPath, DBName, userName,
						passWord, inFilePath, fullFlag);

				if (orclCMD.isEmpty()) {
					showMyDialog("impCMD 命令生成失败!", "impCMD", 2);
				} else {
					String batFilePath = inFilePath.substring(0,
							inFilePath.lastIndexOf("/") + 1)
							+ DBName + "-imp.bat";

					runFlag = generationOracleBatFile(orclBinPath, orclCMD,
							batFilePath);
					if (runFlag) {
						String msgContent = "imp.exe Command Execute Success!";
						String msgTitle = "Success";
						int msgType = 1;

						int f = execCMD(batFilePath);

						if (f != 0) {
							msgContent = "imp.exe Eommand Execute Failure!";
							msgTitle = "Failure";
							msgType = 0;
						}
						showMyDialog(msgContent, msgTitle, msgType);
					} else {
						showMyDialog(batFilePath + " 生成失败!", "bat file", 2);
					}
				}
			} else {
				showMyDialog(
						"Oracle bin DIR not found OR DataBase Name not set OR UserName not set OR restore URI not set. Please check!",
						"plase set init params", 2);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static boolean generationOracleBatFile(String orclBinPath,
			String orclCMD, String batFilePath) {
		boolean b = false;
		String root = orclBinPath.substring(0, orclBinPath.indexOf(":") + 1);

		StringBuffer cmdStr = new StringBuffer();
		cmdStr.append("@echo off\r\n");
		cmdStr.append(root + "\r\n");
		cmdStr.append(orclCMD + "\r\n");
		cmdStr.append("@echo on\r\n");
		System.out.println("batFilePath >> " + batFilePath);
		try {
			FileWriter fw = new FileWriter(batFilePath);
			fw.write(cmdStr.toString());
			fw.flush();
			fw.close();
			b = true;
		} catch (IOException e) {
			b = false;
			System.out.println(e);
		}
		return b;
	}

	private static String getOracleExpCMD(String orclBinPath, String dbName,
			String userName, String passWord, String outFilePath,
			String fullFlag) {
		StringBuffer expCMD = new StringBuffer();
		expCMD.append(orclBinPath + "/exp.exe ");
		expCMD.append(userName + "/" + passWord + "@" + dbName);
		expCMD.append(" file=" + outFilePath);
		expCMD.append(" full=" + fullFlag);
		expCMD.append(" statistics=none");

		return expCMD.toString();
	}

	private static String getOracleImpCMD(String orclBinPath, String dbName,
			String userName, String passWord, String inFilePath, String fullFlag) {
		// String root = orclBinPath.substring(0, orclBinPath.indexOf(":") + 1);

		StringBuffer impCMD = new StringBuffer();
		impCMD.append(orclBinPath + "/imp.exe ");
		impCMD.append(userName + "/" + passWord + "@" + dbName);
		impCMD.append(" file=" + inFilePath);
		impCMD.append(" full=" + fullFlag);
		impCMD.append(" ignore=y ");
		return impCMD.toString();
	}

	private static int execCMD(String command) throws Exception {
		Integer execFlag = null;

		Process proc = Runtime.getRuntime().exec("cmd /c " + command);

		InputStream fis = proc.getInputStream();

		final BufferedReader br2 = new BufferedReader(new InputStreamReader(
				proc.getErrorStream()));

		InputStreamReader isr = new InputStreamReader(fis);

		final BufferedReader br = new BufferedReader(isr);

		Thread t1 = new Thread() {

			public void run() {
				try {
					for (String line = null; (line = br2.readLine()) != null;)
						System.out.println(line);

				} catch (IOException e) {
					System.out.println((new StringBuilder("Thread-1 >>"))
							.append(e).toString());
				}
			}

		};
		Thread t2 = new Thread() {

			public void run() {
				try {
					for (String line = null; (line = br.readLine()) != null;)
						System.out.println(line);

				} catch (IOException e) {
					System.out.println((new StringBuilder("Thread-2 >>"))
							.append(e).toString());
				}
			}

		};
		t1.start();
		t2.start();

		execFlag = Integer.valueOf(proc.waitFor());
		return execFlag.intValue();
	}

	private static String getOracleBinPath() {
		String path = "";
		String[] paths = getEnv("path").split(";");
		for (int i = 0; i < paths.length; i++) {
			String str = paths[i];
			if (str.indexOf("oracle") != -1) {
				path = str;
			}
		}

		return path;
	}

	private static void showMyDialog(String msgContent, String msgTitle,
			int msgType) {
		JOptionPane.showMessageDialog(null, msgContent, msgTitle, msgType);
	}
}
