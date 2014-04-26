package com.zl.common;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class Performance {
	private static PrintWriter log;
	static long smemory = 0L;
	static long stime = 0L;
	static long ememory = 0L;
	static long etime = 0L;
	static String sName = "";
	private static Performance instance;

	public static Performance getInstance(String paramString) {
		if (instance == null)
			instance = new Performance();
		sName = paramString;
		return instance;
	}

	private Performance() {
		init();
	}

	public static void Start() {
		smemory = Runtime.getRuntime().totalMemory();
		stime = System.currentTimeMillis();
	}

	public static void End() {
		Calendar localCalendar = Calendar.getInstance();
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy/MM/dd hh:mm:ss");
		String str = localSimpleDateFormat.format(localCalendar.getTime());
		ememory = Runtime.getRuntime().totalMemory();
		etime = System.currentTimeMillis();
		log.println(str + "\tUsed Time          :" + (etime - stime)
				+ "\tUsed Memory        :" + (ememory - smemory)
				+ "\tTrade Name          :" + sName);
	}

	private void init() {
		InputStream localInputStream = getClass().getResourceAsStream(
				"/db.properties");
		Properties localProperties = new Properties();
		try {
			localProperties.load(localInputStream);
		} catch (Exception localException) {
			System.err
					.println("不能读取属性文件. 请确保performance.properties在CLASSPATH指定的路径中");
			return;
		}
		String str = localProperties.getProperty("performancelogfile",
				"performance.log");
		try {
			log = new PrintWriter(new FileWriter(str, true), true);
		} catch (IOException localIOException) {
			System.err.println("无法打开日志文件: " + str);
			log = new PrintWriter(System.err);
		}
	}

}

/*
 * Location: E:\zllib\zllib.jar Qualified Name: com.zl.base.core.Performance
 * JD-Core Version: 0.6.1
 */