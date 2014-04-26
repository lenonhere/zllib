package com.gui.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.UIManager;

import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class GuiUtils {
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_YMDHMS = "yyyyMMddHHmmss";
	public static final String FILTER_PATH = "C:/";

	/**
	 * @param flag
	 */
	public static void setLookAndFeel(int flag) {
		try {
			switch (flag) {
			case 0:
				/* Nimbus风格，JDK6Up10 版本开始出现 */
				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				break;
			case 1:
				/* Motif风格，外观接近windows经典，但宽宽大大，而且不是黑灰主色，而是蓝黑 */
				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
				break;
			case 2:
				/* Liquid风格（需要导入liquidlnf.jar包） */
				UIManager
						.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
				break;
			case 3:
				/* Nimrod风格（需要导入nimrodlf-1.2.jar包） */
				UIManager
						.setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
				break;
			case 4:
				/* jdk-7u5-rt.jar */
				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
				break;
			case 5:
				/* windows风格 */
				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				break;
			case 6:
				/* 跨平台的Java界面风格 */
				UIManager.setLookAndFeel(UIManager
						.getCrossPlatformLookAndFeelClassName());
				break;
			case 7:
				/* JAVA默认的跨平台外观风格 */
				UIManager
						.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				break;
			default:
				/* 当前系统风格 */
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检索系统环境变量信息
	 *
	 * @param envName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getEnv(String envName) {
		String env = "";
		Map<String, String> map = System.getenv();

		Iterator<Entry<String, String>> i = map.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry entry = (Map.Entry) i.next();
			String key = entry.getKey().toString().toLowerCase();
			if (key.equals(envName.toLowerCase())) {
				env = entry.getValue().toString();
			}
		}
		return env;
	}

	/**
	 * 格式化当前日期时间(默认格式: yyyy-MM-dd HH:mm:ss)
	 *
	 * @return String
	 */
	public static String getToday() {
		return getToday(null);
	}

	/**
	 * 格式化当前日期时间 (默认格式: yyyy-MM-dd HH:mm:ss)
	 *
	 * @param format
	 * @return String
	 */
	public static String getToday(String format) {
		if (null == format || "".equals(format)) {
			format = DEFAULT_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	/**
	 * 数字小数处理
	 *
	 * @param number
	 * @return 0.####
	 */
	public static String getDecimalFormat(double number) {
		String num = new DecimalFormat("0.####").format(number);
		return num;
	}

	/**
	 * @param shell
	 * @param fileType
	 * @return
	 */
	public static String getFilePath(Shell shell, String[] fileType) {
		FileDialog fileDialog = new FileDialog(shell, 4096);
		fileDialog.setText("打开文件");
		fileDialog.setFilterPath(FILTER_PATH);

		fileDialog.setFilterExtensions(fileType);
		String selected = fileDialog.open();
		if (selected == null) {
			selected = "";
		}
		return selected;
	}

	/**
	 * @param shell
	 * @param lastPath
	 * @return
	 */
	public static String getDirectoryPath(Shell shell, String lastPath) {
		DirectoryDialog dirDialog = new DirectoryDialog(shell, 4096);
		dirDialog.setText("打开文件");
		if ((lastPath != null) && (!"".equals(lastPath)))
			dirDialog.setFilterPath(lastPath);
		else {
			dirDialog.setFilterPath(FILTER_PATH);
		}
		dirDialog.open();
		String selected = dirDialog.getFilterPath();
		if (selected == null) {
			selected = "";
		}
		return selected;
	}

	/**
	 * @param shell
	 * @param lastPath
	 * @return
	 */
	public static String getFilePath(Shell shell, String lastPath) {
		FileDialog fileDialog = new FileDialog(shell, 4096);
		fileDialog.setText("打开文件");
		if ((lastPath != null) && (!"".equals(lastPath)))
			fileDialog.setFilterPath(lastPath);
		else {
			fileDialog.setFilterPath(FILTER_PATH);
		}

		String[] filterExt = { "*.*" };
		fileDialog.setFilterExtensions(filterExt);
		String selected = fileDialog.open();
		if (selected == null) {
			selected = "";
		}
		return selected;
	}
}
