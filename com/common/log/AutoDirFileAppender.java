package com.common.log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author:
 * @date:
 * @company:
 * @desribe:日志Appender类，可以将日志分别打到“/日期/包.类名/按小时划分的日志文件”中
 * @modify_author:
 * @modify_time:修改同时打日志时，不同类的日志打到同一个日志文件中的bug
 */
public class AutoDirFileAppender extends FileAppender implements Runnable {

	// The code assumes that the following constants are in a increasing
	// sequence.
	static final int TOP_OF_TROUBLE = -1;
	static final int TOP_OF_MINUTE = 0;
	static final int TOP_OF_HOUR = 1;
	static final int HALF_DAY = 2;
	static final int TOP_OF_DAY = 3;
	static final int TOP_OF_WEEK = 4;
	static final int TOP_OF_MONTH = 5;

	/**
	 * 日志根目录
	 */
	private String rootDir = "/";

	/**
	 * 日志日期目录，默认格式是yyyyMMdd
	 */
	private String logFolder = "yyyyMMdd";

	/**
	 * 删除过期日志的线程
	 */
	private Thread houseKeeper;

	/**
	 * 日志保留天数
	 */
	private int logKeepDays = 7;

	/**
	 * The date pattern. By default, the pattern is set to "'.'yyyy-MM-dd"
	 * meaning daily rollover.
	 */
	private String datePattern = "yyyy-MM-dd'.'hh'.log'";

	/**
	 * The log file will be renamed to the value of the scheduledFilename
	 * variable when the next interval is entered. For example, if the rollover
	 * period is one hour, the log file will be renamed to the value of
	 * "scheduledFilename" at the beginning of the next hour.
	 *
	 * The precise time when a rollover occurs depends on logging activity.
	 */
	private String scheduledFilename;

	/**
	 * 上次打开的日志文件
	 */
	private String lastLogFile;

	/**
	 * The next time we estimate a rollover should occur.
	 */
	// private long nextCheck = System.currentTimeMillis () - 1;

	Date now = new Date();

	SimpleDateFormat sdf;

	RollingCalendar rc = new RollingCalendar();

	int checkPeriod = TOP_OF_TROUBLE;

	// The gmtTimeZone is used only in computeCheckPeriod() method.
	static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");

	private static Log logger = LogFactory.getLog();

	/**
	 * The default constructor does nothing.
	 */
	public AutoDirFileAppender() {
		super();
		// 启动清扫过期日志的线程
		houseKeeper = new Thread(this);
		houseKeeper.setDaemon(true);
		houseKeeper.start();
	}

	/**
	 * Instantiate a <code>DailyRollingFileAppender</code> and open the file
	 * designated by <code>filename</code>. The opened filename will become the
	 * ouput destination for this appender.
	 */
	public AutoDirFileAppender(Layout layout, String filename,
			String datePattern) throws IOException {
		super(layout, filename, true);
		this.datePattern = datePattern;
		activateOptions();
		// 启动清扫过期日志的线程
		houseKeeper = new Thread(this);
		houseKeeper.setDaemon(true);
		houseKeeper.start();
	}

	/**
	 * The <b>DatePattern</b> takes a string in the same format as expected by
	 * {@link SimpleDateFormat}. This options determines the rollover schedule.
	 */
	public void setDatePattern(String pattern) {
		datePattern = pattern;
	}

	/** Returns the value of the <b>DatePattern</b> option. */
	public String getDatePattern() {
		return datePattern;
	}

	public void activateOptions() {
		super.activateOptions();
		if (datePattern != null) {
			now.setTime(System.currentTimeMillis());
			sdf = new SimpleDateFormat(datePattern);
			int type = computeCheckPeriod();
			printPeriodicity(type);
			rc.setType(type);
			// File file = new File(rootDir+sdf.format(new Date()));
			// scheduledFilename = sdf.format(new Date());
			// log.debug(rootDir+scheduledFilename);
		} else {
			LogLog.error("Either File or DatePattern options are not set for appender ["
					+ name + "].");
		}
	}

	void printPeriodicity(int type) {
		switch (type) {
		case TOP_OF_MINUTE:
			LogLog.debug("Appender [" + name + "] to be rolled every minute.");
			break;
		case TOP_OF_HOUR:
			LogLog.debug("Appender [" + name
					+ "] to be rolled on top of every hour.");
			break;
		case HALF_DAY:
			LogLog.debug("Appender [" + name
					+ "] to be rolled at midday and midnight.");
			break;
		case TOP_OF_DAY:
			LogLog.debug("Appender [" + name + "] to be rolled at midnight.");
			break;
		case TOP_OF_WEEK:
			LogLog.debug("Appender [" + name
					+ "] to be rolled at start of week.");
			break;
		case TOP_OF_MONTH:
			LogLog.debug("Appender [" + name
					+ "] to be rolled at start of every month.");
			break;
		default:
			LogLog.warn("Unknown periodicity for appender [" + name + "].");
		}
	}

	// This method computes the roll over period by looping over the
	// periods, starting with the shortest, and stopping when the r0 is
	// different from from r1, where r0 is the epoch formatted according
	// the datePattern (supplied by the user) and r1 is the
	// epoch+nextMillis(i) formatted according to datePattern. All date
	// formatting is done in GMT and not local format because the test
	// logic is based on comparisons relative to 1970-01-01 00:00:00
	// GMT (the epoch).
	int computeCheckPeriod() {
		RollingCalendar rollingCalendar = new RollingCalendar(gmtTimeZone,
				Locale.ENGLISH);
		// set sate to 1970-01-01 00:00:00 GMT
		Date epoch = new Date(0);
		if (datePattern != null) {
			for (int i = TOP_OF_MINUTE; i <= TOP_OF_MONTH; i++) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						datePattern);
				simpleDateFormat.setTimeZone(gmtTimeZone); // do all date
															// formatting in GMT
				String r0 = simpleDateFormat.format(epoch);
				rollingCalendar.setType(i);
				Date next = new Date(rollingCalendar.getNextCheckMillis(epoch));
				String r1 = simpleDateFormat.format(next);
				// System.out.println("Type = "+i+", r0 = "+r0+", r1 = "+r1);
				if (r0 != null && r1 != null && !r0.equals(r1)) {
					return i;
				}
			}
		}
		return TOP_OF_TROUBLE; // Deliberately head for trouble...
	}

	/**
	 * 生成新的文件（每个小时生成一个文件，如果文件大于大小限制，也生成一个新文件）
	 */
	void rollOver(LoggingEvent event) throws IOException {

		/* datePattern用于日志文件按时间生成，例如yyyy-MM-dd hh表示每个小时生成一个文件，必须存在 */
		if (datePattern == null) {
			errorHandler.error("Missing DatePattern option in rollOver().");
			return;
		}

		String datedFilename = sdf.format(now);// 当前时间作为日志文件名

		if (scheduledFilename == null)
			makeFileName();

		String folder = new SimpleDateFormat(logFolder).format(now);// 文件夹
		// String javaClassName =
		// event.getLocationInformation().getClassName();// 打日志的类
		// System.out.println(event.getLocationInformation().getClassName());
		// System.out.println(event.getLocationInformation().fullInfo);
		// System.out.println(event.getLocationInformation().getMethodName());

		String logFileName = (rootDir.endsWith("/") ? rootDir : (rootDir + "/"))
				+ folder + "/" + scheduledFilename;
		// + folder + "/" + javaClassName + "/" + scheduledFilename;
		// 上次打开的日志和这次打开的日志不一致，将上次日志文件关闭
		if (!logFileName.equals(lastLogFile)) {
			this.closeFile();
			lastLogFile = logFileName;
		} else {
			return;
		}

		// 创建日志文件夹
		File target = new File(logFileName);
		if (!target.exists()) {
			String parentDir = target.getParent();
			File parent = new File(parentDir);
			if (!parent.exists()) {
				System.out.println("创建日志文件夹...");
				parent.mkdirs();
			}
		}

		try {
			// This will also close the file. This is OK since multiple
			// close operations are safe.
			this.setFile(logFileName, true, this.bufferedIO, this.bufferSize);
		} catch (IOException e) {
			errorHandler.error("setFile(" + logFileName
					+ ", false) call failed.");
		}
		scheduledFilename = datedFilename;
	}

	/**
	 * This method differentiates DailyRollingFileAppender from its super class.
	 *
	 * <p>
	 * Before actually logging, this method will check whether it is time to do
	 * a rollover. If it is, it will schedule the next rollover time and then
	 * rollover.
	 * */
	protected void subAppend(LoggingEvent event) {
		// long n = System.currentTimeMillis();
		// if (n >= nextCheck) {
		// now.setTime(n);
		// nextCheck = rc.getNextCheckMillis(now);
		now.setTime(System.currentTimeMillis());
		try {
			rollOver(event);
		} catch (IOException ioe) {
			LogLog.error("rollOver() failed.", ioe);
		}
		// }
		super.subAppend(event);
	}

	private synchronized void deleteOldFolder() {
		File dir = new File(rootDir.endsWith("/") ? rootDir : (rootDir + "/"));
		logger.info("日志文件夹根目录：" + dir.getPath());
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				String lastday = new SimpleDateFormat(logFolder)
						.format(new Date(System.currentTimeMillis()
								- logKeepDays * 24 * 60 * 60 * 1000));
				if (files[i].isDirectory()
						&& files[i].getName().compareTo(lastday) <= 0) {
					deleteFolder(files[i]);
				}
			}
		}
	}

	private void deleteFolder(File file) {
		try {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteFolder(files[i]);
					}
					files[i].delete();
					logger.info("删除日志文件" + files[i].getPath() + "成功");
				}
			}
			file.delete();
		} catch (Exception e) {
			logger.error("删除日志文件夹失败： " + file.getPath());
		}
	}

	/**
	 * @return the rootDir
	 */
	public String getRootDir() {
		return rootDir;
	}

	/**
	 * @param rootDir
	 *            the rootDir to set
	 */
	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	private void makeFileName() {
		scheduledFilename = sdf.format(now);
	}

	/**
	 * @return the logFolder
	 */
	public String getLogFolder() {
		return logFolder;
	}

	/**
	 * @param logFolder
	 *            the logFolder to set
	 */
	public void setLogFolder(String logFolder) {
		this.logFolder = logFolder;
	}

	/**
	 * @return the logKeepDays
	 */
	public int getLogKeepDays() {
		return logKeepDays;
	}

	/**
	 * @param logKeepDays
	 *            the logKeepDays to set
	 */
	public void setLogKeepDays(int logKeepDays) {
		this.logKeepDays = logKeepDays;
	}

	/*
	 * 线程在系统初始化是启动，但日志文件获取路径及参数在日志第一次调用时启动， 所以在第一次线程run时，不进行日志清理工作。
	 *
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		logger.info("清理日志线程启动："
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(
						System.currentTimeMillis())));
		while (true) {
			logger.info("清理日志开始："
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date(System.currentTimeMillis())));
			deleteOldFolder();
			logger.info("清理日志结束："
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date(System.currentTimeMillis())));

			try {
				Thread.sleep(1 * 24 * 60 * 60 * 1000);// 每隔1天清理一次
			} catch (InterruptedException e) {
				return;
			}
		}
	}

}

/**
 * RollingCalendar is a helper class to DailyRollingFileAppender. Given a
 * periodicity type and the current time, it computes the start of the next
 * interval.
 * */
class RollingCalendar extends GregorianCalendar {

	private static final long serialVersionUID = 2463787719538844772L;

	int type = AutoDirFileAppender.TOP_OF_TROUBLE;

	RollingCalendar() {
		super();
	}

	RollingCalendar(TimeZone tz, Locale locale) {
		super(tz, locale);
	}

	void setType(int type) {
		this.type = type;
	}

	public long getNextCheckMillis(Date now) {
		return getNextCheckDate(now).getTime();
	}

	public Date getNextCheckDate(Date now) {
		this.setTime(now);

		switch (type) {
		case AutoDirFileAppender.TOP_OF_MINUTE:
			this.set(Calendar.SECOND, 0);
			this.set(Calendar.MILLISECOND, 0);
			this.add(Calendar.MINUTE, 1);
			break;
		case AutoDirFileAppender.TOP_OF_HOUR:
			this.set(Calendar.MINUTE, 0);
			this.set(Calendar.SECOND, 0);
			this.set(Calendar.MILLISECOND, 0);
			this.add(Calendar.HOUR_OF_DAY, 1);
			break;
		case AutoDirFileAppender.HALF_DAY:
			this.set(Calendar.MINUTE, 0);
			this.set(Calendar.SECOND, 0);
			this.set(Calendar.MILLISECOND, 0);
			int hour = get(Calendar.HOUR_OF_DAY);
			if (hour < 12) {
				this.set(Calendar.HOUR_OF_DAY, 12);
			} else {
				this.set(Calendar.HOUR_OF_DAY, 0);
				this.add(Calendar.DAY_OF_MONTH, 1);
			}
			break;
		case AutoDirFileAppender.TOP_OF_DAY:
			this.set(Calendar.HOUR_OF_DAY, 0);
			this.set(Calendar.MINUTE, 0);
			this.set(Calendar.SECOND, 0);
			this.set(Calendar.MILLISECOND, 0);
			this.add(Calendar.DATE, 1);
			break;
		case AutoDirFileAppender.TOP_OF_WEEK:
			this.set(Calendar.DAY_OF_WEEK, getFirstDayOfWeek());
			this.set(Calendar.HOUR_OF_DAY, 0);
			this.set(Calendar.MINUTE, 0);
			this.set(Calendar.SECOND, 0);
			this.set(Calendar.MILLISECOND, 0);
			this.add(Calendar.WEEK_OF_YEAR, 1);
			break;
		case AutoDirFileAppender.TOP_OF_MONTH:
			this.set(Calendar.DATE, 1);
			this.set(Calendar.HOUR_OF_DAY, 0);
			this.set(Calendar.MINUTE, 0);
			this.set(Calendar.SECOND, 0);
			this.set(Calendar.MILLISECOND, 0);
			this.add(Calendar.MONTH, 1);
			break;
		default:
			throw new IllegalStateException("Unknown periodicity type.");
		}
		return getTime();
	}

}
