package com.gui.components;

import static com.gui.utils.GuiUtils.getEnv;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Wsdl2Java implements ActionListener {
	private static JPanel jp = null;
	private JTextField axisDirJTF = null;
	private JButton openAxisDirJB = new JButton("AxisHome...");
	private JTextField inDirJTF = null;
	private JButton inDirJB = new JButton("WSDL文件...");
	private JTextField outDirJTF = null;
	private JButton outDirJB = new JButton("输出目录...");
	private JTextField packageDirJTF = null;
	private JTextArea wsdl2javaCmdJTA = null;
	private JButton generateSubFileJB = new JButton("生成客户端");
	private JFileChooser jfc = null;
	private Integer flag = null;

	private String axisDirJTF_title = "请在此录入AXIS_HOME系统环境变量名称!";

	public Wsdl2Java() {
		dosCMDw();
	}

	public static JPanel getJPanel() {
		if (null == jp) {
			new Wsdl2Java();
		}
		return jp;
	}

	private void dosCMDw() {
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();

		jp = new JPanel();
		jp.setLayout(gbl);

		JLabel axisJL = new JLabel("axisHome:");
		this.axisDirJTF = new JTextField("axis_home", 30);
		this.openAxisDirJB.addActionListener(this);

		gbc.fill = 1;
		gbl.addLayoutComponent(axisJL, gbc);
		jp.add(axisJL);

		gbc.gridwidth = 4;
		gbl.addLayoutComponent(this.axisDirJTF, gbc);
		jp.add(this.axisDirJTF);

		gbc.gridwidth = 0;
		gbl.addLayoutComponent(this.openAxisDirJB, gbc);
		jp.add(this.openAxisDirJB);

		JLabel inJL = new JLabel("WSDLFile:");
		this.inDirJTF = new JTextField(30);
		this.inDirJB.addActionListener(this);

		gbc.gridwidth = 1;
		gbl.addLayoutComponent(inJL, gbc);
		jp.add(inJL);

		gbc.gridwidth = 4;
		gbl.addLayoutComponent(this.inDirJTF, gbc);
		jp.add(this.inDirJTF);

		gbc.gridwidth = 0;
		gbl.addLayoutComponent(this.inDirJB, gbc);
		jp.add(this.inDirJB);

		JLabel outJL = new JLabel("OutFileDir:");
		this.outDirJTF = new JTextField(30);
		this.outDirJB.addActionListener(this);

		gbc.gridwidth = 1;
		gbl.addLayoutComponent(outJL, gbc);
		jp.add(outJL);

		gbc.gridwidth = 4;
		gbl.addLayoutComponent(this.outDirJTF, gbc);
		jp.add(this.outDirJTF);

		gbc.gridwidth = 0;
		gbl.addLayoutComponent(this.outDirJB, gbc);
		jp.add(this.outDirJB);

		JLabel pkgJL = new JLabel("Package:");
		this.packageDirJTF = new JTextField("com.qmx.client", 30);

		gbc.gridwidth = 1;
		gbl.addLayoutComponent(pkgJL, gbc);
		jp.add(pkgJL);

		gbc.gridwidth = 0;
		gbl.addLayoutComponent(this.packageDirJTF, gbc);
		jp.add(this.packageDirJTF);

		JLabel cmdJL = new JLabel("Command:");
		this.wsdl2javaCmdJTA = new JTextArea(2, 30);
		this.wsdl2javaCmdJTA.setAutoscrolls(true);
		this.wsdl2javaCmdJTA.setLineWrap(true);
		this.wsdl2javaCmdJTA.setWrapStyleWord(true);
		this.generateSubFileJB.addActionListener(this);

		gbc.gridwidth = 1;
		gbc.gridheight = 4;
		gbl.addLayoutComponent(cmdJL, gbc);
		jp.add(cmdJL);

		gbc.gridwidth = 4;
		gbc.gridheight = 4;
		gbl.addLayoutComponent(this.wsdl2javaCmdJTA, gbc);
		jp.add(new JScrollPane(this.wsdl2javaCmdJTA));

		gbc.gridwidth = 22;
		gbc.gridheight = 4;
		gbl.addLayoutComponent(this.generateSubFileJB, gbc);
		jp.add(this.generateSubFileJB);
	}

	private static int execCMD(String command) throws Exception {
		Integer execFlag = null;
		Process proc = Runtime.getRuntime().exec("cmd /c " + command);
		InputStream fis = proc.getInputStream();
		InputStreamReader isr = new InputStreamReader(fis);
		final BufferedReader br = new BufferedReader(isr);
		final BufferedReader br2 = new BufferedReader(new InputStreamReader(
				proc.getErrorStream()));

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

	public void actionPerformed(ActionEvent event) {
		Object obj = event.getSource();

		if (obj.equals(this.openAxisDirJB)) {
			String key = this.axisDirJTF.getText();
			if (key.isEmpty()) {
				key = "axis_home";
			} else {
				String axisDirPath = getEnv(key);
				if (axisDirPath.isEmpty()) {
					int cfd = JOptionPane
							.showConfirmDialog(
									null,
									"未找到系统环境变量"
											+ key
											+ ",您可以手动指定%AXIS_HOME%.您也可以重新输入系统环境变量名.\r\n[是]手动指定%AXIS_HOME%目录\r\n[否]重新输入系统环境变量名",
									"手动指定%AXIS_HOME%目录路径", 1);
					if (cfd == 0) {
						this.jfc = new JFileChooser("E:\\");
						this.jfc.setFileSelectionMode(1);
						int f = this.jfc.showOpenDialog(null);
						if (f == 0) {
							axisDirPath = this.jfc.getSelectedFile()
									.getAbsolutePath();
							this.axisDirJTF.setText(axisDirPath);
						}
					} else if (cfd == 1) {
						this.axisDirJTF.setText(this.axisDirJTF_title);
					} else {
						this.axisDirJTF.setText(key);
					}
				} else {
					this.axisDirJTF.setText(axisDirPath);
				}
			}
		}

		if (obj.equals(this.inDirJB)) {
			this.jfc = new JFileChooser("E:\\");
			this.jfc.setFileSelectionMode(0);
			FileNameExtensionFilter fnef = new FileNameExtensionFilter(
					"*.wsdl", new String[] { "wsdl" });
			this.jfc.setFileFilter(fnef);
			int f = this.jfc.showOpenDialog(null);
			if (f == 0) {
				String inSubFilePath = this.jfc.getSelectedFile()
						.getAbsolutePath();
				String suffix = inSubFilePath.substring(inSubFilePath
						.lastIndexOf(".") + 1);
				if ("wsdl".equalsIgnoreCase(suffix))
					this.inDirJTF.setText(inSubFilePath);
				else {
					JOptionPane.showMessageDialog(null,
							"您选择的文件类型不是*.wsdl文件,请检查!", "文件类型不正确", 2);
				}
			}
		}

		if (obj.equals(this.outDirJB)) {
			this.jfc = new JFileChooser("E:\\");
			this.jfc.setFileSelectionMode(1);
			int f = this.jfc.showOpenDialog(null);
			if (f == 0) {
				String outSubFilePath = this.jfc.getSelectedFile()
						.getAbsolutePath();
				this.outDirJTF.setText(outSubFilePath);
			}
		}

		if (obj.equals(this.generateSubFileJB))
			try {
				String axisHome = this.axisDirJTF.getText();
				String uri = this.inDirJTF.getText();
				String o = this.outDirJTF.getText();
				String p = this.packageDirJTF.getText();
				String batName = "";
				String batPath = "";

				boolean runFlag = true;
				if (axisHome.isEmpty()) {
					runFlag = false;
				}
				if (axisHome.equals("axis_home")) {
					this.openAxisDirJB.doClick();
				}
				if (axisHome.equals(this.axisDirJTF_title)) {
					runFlag = false;
				}
				if (uri.isEmpty()) {
					runFlag = false;
				}

				if (o.isEmpty()) {
					o = "E:/logs/wsdl2java_out";

					int cf = JOptionPane.showConfirmDialog(null,
							"输出路径未选择,是否使用默认保存路径? " + o, "选择输出路径", 0);
					if (cf == 0)
						this.outDirJTF.setText(o);
					else {
						runFlag = false;
					}
				}

				if (p.isEmpty()) {
					p = "com.axis.sub";
					this.packageDirJTF.setText(p);
				}

				if (runFlag) {
					batName = uri.substring(uri.lastIndexOf("\\") + 1,
							uri.lastIndexOf("."))
							+ ".bat";
					batPath = o + "\\" + batName;

					String cmdStr = getWSDL2JAVACmd(uri, o, p);
					this.wsdl2javaCmdJTA.setText(cmdStr);
				}

				String cmd = this.wsdl2javaCmdJTA.getText();
				if (cmd.isEmpty()) {
					runFlag = false;
				}

				if (runFlag) {
					runFlag = generateBatFile(axisHome, cmd, batPath, true);
					if (runFlag) {
						String msgContent = "wsdl2java.bat Commant Execute Successed!";
						String msgTitle = "Success";
						int msgType = 1;

						this.flag = Integer.valueOf(execCMD(batPath));
						if (this.flag.intValue() != 0) {
							msgContent = "wsdl2java.bat Commant Execute Failured!";
							msgTitle = "Failure";
							msgType = 0;
						}
						showMyDialog(msgContent, msgTitle, msgType);
					} else {
						showMyDialog(batName + "文件生成失败!!", "bat文件不存在", 0);
					}
				} else {
					showMyDialog(
							"%AXIS_HOME%或*.WSDL文件未选择或wsdl2java命令无效,请检查后重试!",
							"参数未设置", 2);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
	}

	private String getWSDL2JAVACmd(String uri, String o, String p) {
		// set Axis_Lib=E:\jar\axis-bin-1_4\axis-1_4\lib
		// set Java_Cmd=java-Djava.ext.dirs=%Axis_Lib%
		// set Output_Path=D:\src
		// set Package=com.hsipcc.wsdl
		// "%Java_Cmd%org.apache.axis.wsdl.WSDL2Java
		// -o%Output_Path%
		// -p%Package%
		// http://localhost:8000/service/Business?wsdl"
		StringBuffer axisCmd = new StringBuffer();
		axisCmd.append("wsdl2java.bat -uri " + uri);
		axisCmd.append(" -s -o " + o);
		axisCmd.append(" -p " + p);
		return axisCmd.toString();
	}

	private boolean generateBatFile(String axisHome, String cmd,
			String batPath, boolean createBatFile) {
		boolean b = false;
		try {
			String axis_home_bin = axisHome + "\\bin";
			String root = axis_home_bin.substring(0,
					axis_home_bin.indexOf(":") + 1);

			StringBuffer cmdStr = new StringBuffer();
			cmdStr.append("@echo off \r\n");
			cmdStr.append(root + " \r\n");
			cmdStr.append("cd " + axis_home_bin + "\r\n");
			cmdStr.append(cmd);
			cmdStr.append("\r\n@echo on \r\n");

			if (createBatFile) {
				FileWriter file = new FileWriter(batPath);

				file.write(cmdStr.toString());
				file.flush();
				file.close();
			}

			b = true;
		} catch (IOException e) {
			b = false;
			System.out.println(e);
		}
		return b;
	}

	private void showMyDialog(String msgContent, String msgTitle, int msgType) {
		JOptionPane.showMessageDialog(null, msgContent, msgTitle, msgType);
	}
}