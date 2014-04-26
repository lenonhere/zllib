package com.gui.components;

import static com.gui.utils.GuiUtils.getDecimalFormat;
import static com.gui.utils.GuiUtils.getToday;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PrintDirFilesInfo implements ActionListener {
	private static String dirPath = "E:\\logs\\";
	private static String logPath = "E:\\logs\\dirfile.txt";
	private static int depth = 0;
	private static int fileCount = 0;
	private static int dirCount = 0;
	private static int sizes = 0;
	private static String content = "";
	private static JPanel jp = null;
	private JFileChooser jfc = null;

	private JLabel dirJL = new JLabel("目录:");
	private JTextField dirJTF = new JTextField(dirPath, 40);
	private JButton dirJB = new JButton("选择");

	private JLabel logJL = new JLabel("日志:");
	private JTextField logJTF = new JTextField(logPath, 40);
	private JButton logJB = new JButton("选择");

	private JLabel exeJL = new JLabel("信息:");
	private JTextArea exeJTA = new JTextArea(20, 40);
	private JButton exeJB = new JButton("查询");

	private JCheckBox jcb = new JCheckBox("生成文件");

	private void init() {
		depth = 0;
		fileCount = 0;
		dirCount = 0;
		sizes = 0;
		content = "";
		this.exeJTA.setText("");
	}

	public PrintDirFilesInfo() {
		init();
		showGUI();
	}

	public static JPanel getJPanel() {
		if (null == jp) {
			new PrintDirFilesInfo();
		}
		return jp;
	}

	private void showGUI() {

		jp = new JPanel();

		this.logJTF.setEditable(false);
		this.logJB.setEnabled(false);
		this.jcb.addActionListener(this);

		JScrollPane jspJTA = new JScrollPane();
		this.exeJTA.setAutoscrolls(true);
		this.exeJTA.setLineWrap(true);
		this.exeJTA.setWrapStyleWord(true);
		jspJTA.getViewport().add(this.exeJTA);

		Container host = jp;
		GroupLayout gl = new GroupLayout(host);
		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);
		host.setLayout(gl);

		GroupLayout.ParallelGroup jlHGP = gl
				.createParallelGroup(GroupLayout.Alignment.LEADING);
		jlHGP.addComponent(this.dirJL);
		jlHGP.addComponent(this.logJL);
		jlHGP.addComponent(this.exeJL);

		GroupLayout.ParallelGroup jtfHGP = gl
				.createParallelGroup(GroupLayout.Alignment.LEADING);
		jtfHGP.addComponent(this.dirJTF);
		jtfHGP.addComponent(this.logJTF);
		jtfHGP.addComponent(jspJTA);

		GroupLayout.ParallelGroup jbHGP = gl
				.createParallelGroup(GroupLayout.Alignment.LEADING);
		jbHGP.addComponent(this.dirJB);
		jbHGP.addComponent(this.logJB);
		jbHGP.addComponent(this.exeJB);
		jbHGP.addComponent(this.jcb);

		this.dirJB.addActionListener(this);
		this.logJB.addActionListener(this);
		this.exeJB.addActionListener(this);

		gl.setHorizontalGroup(gl.createSequentialGroup().addGroup(jlHGP)
				.addGroup(jtfHGP).addGroup(jbHGP));

		GroupLayout.ParallelGroup dirVPG = gl
				.createParallelGroup(GroupLayout.Alignment.LEADING);
		dirVPG.addComponent(this.dirJL);
		dirVPG.addComponent(this.dirJTF);
		dirVPG.addComponent(this.dirJB);

		GroupLayout.ParallelGroup logVPG = gl
				.createParallelGroup(GroupLayout.Alignment.LEADING);
		logVPG.addComponent(this.logJL);
		logVPG.addComponent(this.logJTF);
		logVPG.addComponent(this.logJB);

		GroupLayout.ParallelGroup exeVPG = gl
				.createParallelGroup(GroupLayout.Alignment.TRAILING);
		exeVPG.addComponent(this.exeJL);
		exeVPG.addComponent(jspJTA);
		exeVPG.addComponent(this.exeJB);

		GroupLayout.ParallelGroup jpbVPG = gl
				.createParallelGroup(GroupLayout.Alignment.TRAILING);
		jpbVPG.addComponent(this.jcb);

		gl.setVerticalGroup(gl.createSequentialGroup().addGroup(dirVPG)
				.addGroup(logVPG).addGroup(exeVPG).addGroup(jpbVPG));
		gl.linkSize(1, new Component[] { this.dirJB, this.logJB, this.exeJB,
				this.jcb });

	}

	public void actionPerformed(ActionEvent e) {
		init();
		Object obj = e.getSource();
		if (this.dirJB.equals(obj)) {
			this.jfc = new JFileChooser(dirPath);
			this.jfc.setFileSelectionMode(2);
			int f = this.jfc.showOpenDialog(null);
			if (f == 0) {
				String path = this.jfc.getSelectedFile().getAbsolutePath();
				this.dirJTF.setText(path);
			}
		}
		if (this.logJB.equals(obj)) {
			if (this.jcb.isSelected()) {
				this.jfc = new JFileChooser(logPath);
				this.jfc.setFileSelectionMode(0);
				int f = this.jfc.showOpenDialog(null);
				if (f == 0) {
					String path = this.jfc.getSelectedFile().getAbsolutePath();
					this.logJTF.setText(path);
				}
			} else {
				this.logJB.isDisplayable();
				JOptionPane.showMessageDialog(null, "【生成文件】复选框未勾选时,点此按钮无效!",
						"是否生成日志文件", 1);
			}
		}
		if (this.exeJB.equals(obj)) {
			boolean runFlag = true;
			if (this.jcb.isSelected()) {
				try {
					String path = this.logJTF.getText();

					if (path.trim().length() == 0) {
						int cf = JOptionPane.showConfirmDialog(null,
								"日志文件保存路径未选择,是否保存到默认路径?", "日志文件保存路径", 0);

						if (cf == 0) {
							this.logJTF.setText(logPath);
							File logFile = new File(logPath);

							if (!logFile.exists())
								logFile.createNewFile();
						} else {
							runFlag = false;
						}
					}
				} catch (Exception e1) {
					System.out.println(e1);
				}
			}

			if (runFlag) {
				Thread thread = new Thread(new Runnable() {

					public void run() {
						String info = PrintDirFilesInfo.getDirInfo(dirJTF
								.getText());
						exeJTA.setText(info);
						if (jcb.isSelected())
							PrintDirFilesInfo.writerFileInfo(logJTF.getText(),
									info);
					}
				});

				thread.start();
			}
		}
		if (this.jcb.equals(obj)) {
			if (this.jcb.isSelected())
				this.logJB.setEnabled(true);
			else
				this.logJB.setEnabled(false);
		}
	}

	private static String getFileInfo(String filePath) {
		StringBuffer info = new StringBuffer();
		try {
			File file = new File(filePath);
			String size = "";
			if (file.exists()) {
				String fileName = file.getName();
				long l = file.length();
				if (l < 1024L)
					size = String.valueOf(l) + "B(字节)";
				else if (l < 1048576L)
					size = getDecimalFormat(l / 1024L) + "KB(千字节)";
				else if (l < 1073741824L)
					size = getDecimalFormat(l / 1024L / 1024L) + "MB(兆)";
				else {
					size = getDecimalFormat(l / 1024L / 1024L / 1024L)
							+ "GB(千兆)";
				}
				fileCount += 1;
				sizes = (int) (sizes + l);
				info.append(" 文件名称:" + fileName + "\r\n");
				info.append(" 文件路径:" + filePath + "\r\n");
				info.append(" 文件大小:" + size + " [" + l + "Bytes]\r\n");
			} else {
				System.out.println("file is not exist!");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return info.toString();
	}

	private static String getDirInfo(String dirPath) {
		String info = dirPath + "\t>>>详细信息如下：\r\n" + getFileInfoToo(dirPath)
				+ "\r\n总文件个数：" + fileCount + " 个" + "\r\n总目录个数：" + dirCount
				+ " 个" + "\r\n总文件大小：" + getDecimalFormat(sizes) + " Bytes（字节）"
				+ getDecimalFormat(sizes * 1.0D / 1024.0D) + " KB（千字节）"
				+ getDecimalFormat(sizes * 1.0D / 1024.0D / 1024.0D) + " MB（兆）"
				+ getDecimalFormat(sizes * 1.0D / 1024.0D / 1024.0D / 1024.0D)
				+ " GB（千兆）";
		return info;
	}

	private static boolean isRootDisk(String dirPath) {
		boolean b = false;
		if (dirPath.length() <= 3) {
			b = true;
		}
		return b;
	}

	private static String getFileInfoToo(String dirPath) {
		File dirFile = new File(dirPath);

		if (isRootDisk(dirPath)) {
			File[] files = dirFile.listFiles();
			for (File file : files)
				if (!file.isHidden()) {
					getFileInfoToo(file.getPath());
				}
		} else {
			String size = "";

			if ((dirFile.exists()) && (dirFile.isDirectory())) {
				File[] files = dirFile.listFiles();
				files = sort(files);

				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					String path = file.getPath();

					if (file.isFile()) {
						fileCount += 1;
						long l = file.length();

						if (l < 1024L)
							size = String.valueOf(l) + "B(字节)";
						else if (l < 1048576L)
							size = getDecimalFormat(l * 1.0D / 1024.0D)
									+ "KB(千字节)";
						else if (l < 1073741824L)
							size = getDecimalFormat(l * 1.0D / 1024.0D / 1024.0D)
									+ "MB(兆)";
						else {
							size = getDecimalFormat(l * 1.0D / 1024.0D
									/ 1024.0D / 1024.0D)
									+ "GB(千兆)";
						}

						content = content + getDirFlag(depth + 1, "-") + path
								+ " 文件大小:" + size + " [" + l + "Bytes]\r\n";
						sizes = (int) (sizes + l);
					}

					if (file.isDirectory()) {
						dirCount += 1;
						content = content + getDirFlag(depth, "-├-") + path
								+ "\r\n";
						depth += 1;
						getFileInfoToo(path);
						depth -= 1;
					}
				}

			} else if (dirFile.isFile()) {
				content += getFileInfo(dirPath);
			} else {
				content = dirPath + "文件目录不存在! ";
			}
		}

		return content;
	}

	private static File[] sort(File[] files) {
		ArrayList<File> fileList = new ArrayList<File>();
		File[] arrayOfFile = files;
		int j = files.length;
		for (int i = 0; i < j; i++) {
			File file = arrayOfFile[i];
			if (file.isFile()) {
				fileList.add(file);
			}
		}
		arrayOfFile = files;
		j = files.length;
		for (int i = 0; i < j; i++) {
			File file = arrayOfFile[i];
			if (file.isDirectory()) {
				fileList.add(file);
			}
		}

		return (File[]) fileList.toArray(new File[files.length]);
	}

	private static String getDirFlag(int length, String flag) {
		String str = "";
		for (int i = 0; i < length; i++) {
			str += flag;
		}
		return "├" + str;
	}

	private static void writerFileInfo(String logPath, String dirInfo) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(logPath));
			bw.write("-----/ " + getToday() + " /-----\r\n");
			bw.write(dirInfo);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public String showDirContent(String path) { // 该方法实现取得目录内容
		File file = new File(path); // 用路径实例化一个文件对象
		File[] files = file.listFiles(); // 重点:取得目录内所有文件列表
		StringBuffer message = new StringBuffer(); // 实例化一个StringBuffer,用于处理显示的字符串
		message.append(path); // 增加信息
		message.append(" 内容如下：\n");
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) { // 如果这是一个目录
				message.append("<dir>\t"); // 增加目录标识
			} else {
				message.append("\t");
			}
			message.append(files[i].getName()); // 增加文件或目录名
			message.append("\n");
		}
		return message.toString(); // 显示消息
	}

}
