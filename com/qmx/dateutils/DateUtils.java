package com.qmx.dateutils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils implements Serializable {

	private static final long serialVersionUID = 1L;
	// private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
	/**
	 * 时间范围：年
	 */
	public static final int YEAR = 1;

	/**
	 * 时间范围：季度
	 */
	public static final int QUARTER = 2;

	/**
	 * 时间范围：月
	 */
	public static final int MONTH = 3;

	/**
	 * 时间范围：旬
	 */
	public static final int TENDAYS = 4;

	/**
	 * 时间范围：周
	 */
	public static final int WEEK = 5;

	/**
	 * 时间范围：日
	 */
	public static final int DAY = 6;

	/* 基准时间 */
	private Date fiducialDate = null;
	private Calendar cal = null;

	private DateUtils(Date fiducialDate) {
		if (fiducialDate != null) {
			this.fiducialDate = fiducialDate;
		} else {
			this.fiducialDate = new Date(System.currentTimeMillis());
		}

		this.cal = Calendar.getInstance();
		this.cal.setTime(this.fiducialDate);
		this.cal.set(Calendar.HOUR_OF_DAY, 0);
		this.cal.set(Calendar.MINUTE, 0);
		this.cal.set(Calendar.SECOND, 0);
		this.cal.set(Calendar.MILLISECOND, 0);

		this.fiducialDate = this.cal.getTime();
	}

	/**
	 * 获取DateHelper实例
	 *
	 * @param fiducialDate
	 *            基准时间
	 * @return
	 */
	public static DateUtils getInstance(Date fiducialDate) {
		return new DateUtils(fiducialDate);
	}

	/**
	 * 获取DateHelper实例, 使用当前时间作为基准时间
	 *
	 * @return
	 */
	public static DateUtils getInstance() {
		return new DateUtils(null);
	}

	/**
	 * 获取年的第一天
	 *
	 * @param offset
	 *            偏移量
	 * @return
	 */
	public Date getFirstDayOfYear(int offset) {
		cal.setTime(this.fiducialDate);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + offset);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取年的最后一天
	 *
	 * @param offset
	 *            偏移量
	 * @return
	 */
	public Date getLastDayOfYear(int offset) {
		cal.setTime(this.fiducialDate);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + offset);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		return cal.getTime();
	}

	/**
	 * 获取季度的第一天
	 *
	 * @param offset
	 *            偏移量
	 * @return
	 */
	public Date getFirstDayOfQuarter(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.MONTH, offset * 3);
		int mon = cal.get(Calendar.MONTH);
		if (mon >= Calendar.JANUARY && mon <= Calendar.MARCH) {
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (mon >= Calendar.APRIL && mon <= Calendar.JUNE) {
			cal.set(Calendar.MONTH, Calendar.APRIL);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (mon >= Calendar.JULY && mon <= Calendar.SEPTEMBER) {
			cal.set(Calendar.MONTH, Calendar.JULY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (mon >= Calendar.OCTOBER && mon <= Calendar.DECEMBER) {
			cal.set(Calendar.MONTH, Calendar.OCTOBER);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		return cal.getTime();
	}

	/**
	 * 获取季度的最后一天
	 *
	 * @param offset
	 *            偏移量
	 * @return
	 */
	public Date getLastDayOfQuarter(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.MONTH, offset * 3);

		int mon = cal.get(Calendar.MONTH);
		if (mon >= Calendar.JANUARY && mon <= Calendar.MARCH) {
			cal.set(Calendar.MONTH, Calendar.MARCH);
			cal.set(Calendar.DAY_OF_MONTH, 31);
		}
		if (mon >= Calendar.APRIL && mon <= Calendar.JUNE) {
			cal.set(Calendar.MONTH, Calendar.JUNE);
			cal.set(Calendar.DAY_OF_MONTH, 30);
		}
		if (mon >= Calendar.JULY && mon <= Calendar.SEPTEMBER) {
			cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
			cal.set(Calendar.DAY_OF_MONTH, 30);
		}
		if (mon >= Calendar.OCTOBER && mon <= Calendar.DECEMBER) {
			cal.set(Calendar.MONTH, Calendar.DECEMBER);
			cal.set(Calendar.DAY_OF_MONTH, 31);
		}
		return cal.getTime();
	}

	/**
	 * 获取月的最后一天
	 *
	 * @param offset
	 *            偏移量
	 * @return
	 */
	public Date getFirstDayOfMonth(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.MONTH, offset);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取月的最后一天
	 *
	 * @param offset
	 *            偏移量
	 * @return
	 */
	public Date getLastDayOfMonth(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.MONTH, offset + 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取旬的第一天
	 *
	 * @param offset
	 *            偏移量
	 * @return
	 */
	public Date getFirstDayOfTendays(int offset) {
		cal.setTime(this.fiducialDate);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day >= 21) {
			day = 21;
		} else if (day >= 11) {
			day = 11;
		} else {
			day = 1;
		}

		if (offset > 0) {
			day = day + 10 * offset;
			int monOffset = day / 30;
			day = day % 30;
			cal.add(Calendar.MONTH, monOffset);
			cal.set(Calendar.DAY_OF_MONTH, day);
		} else {
			int monOffset = 10 * offset / 30;
			int dayOffset = 10 * offset % 30;
			if ((day + dayOffset) > 0) {
				day = day + dayOffset;
			} else {
				monOffset = monOffset - 1;
				day = day - dayOffset - 10;
			}
			cal.add(Calendar.MONTH, monOffset);
			cal.set(Calendar.DAY_OF_MONTH, day);
		}
		return cal.getTime();
	}

	/**
	 * 获取旬的最后一天
	 *
	 * @param offset
	 *            偏移量
	 * @return
	 */
	public Date getLastDayOfTendays(int offset) {
		Date date = getFirstDayOfTendays(offset + 1);
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取周的第一天(MONDAY)
	 *
	 * @param offset
	 *            偏移量
	 * @return
	 */
	public Date getFirstDayOfWeek(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.DAY_OF_MONTH, offset * 7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	/**
	 * 获取周的最后一天(SUNDAY)
	 *
	 * @param offset
	 *            偏移量
	 * @return
	 */
	public Date getLastDayOfWeek(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.DAY_OF_MONTH, offset * 7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.add(Calendar.DAY_OF_MONTH, 6);
		return cal.getTime();
	}

	/**
	 * 获取指定时间范围的第一天
	 *
	 * @param dateRangeType
	 *            时间范围类型
	 * @param offset
	 *            偏移量
	 * @return
	 */
	private Date getFirstDate(int dateRangeType, int offset) {
		return null;
	}

	/**
	 * 获取指定时间范围的最后一天
	 *
	 * @param dateRangeType
	 *            时间范围类型
	 * @param offset
	 *            偏移量
	 * @return
	 */
	private Date getLastDate(int dateRangeType, int offset) {
		return null;
	}

	/**
	 * 根据日历的规则，为基准时间添加指定日历字段的时间量
	 *
	 * @param field
	 *            日历字段, 使用Calendar类定义的日历字段常量
	 * @param offset
	 *            偏移量
	 * @return
	 */
	public Date add(int field, int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(field, offset);
		return cal.getTime();
	}

	/**
	 * 根据日历的规则，为基准时间添加指定日历字段的单个时间单元
	 *
	 * @param field
	 *            日历字段, 使用Calendar类定义的日历字段常量
	 * @param up
	 *            指定日历字段的值的滚动方向。true:向上滚动 / false:向下滚动
	 * @return
	 */
	public Date roll(int field, boolean up) {
		cal.setTime(this.fiducialDate);
		cal.roll(field, up);
		return cal.getTime();
	}

	/**
	 * 把字符串转换为日期
	 *
	 * @param dateStr
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static Date strToDate(String dateStr, String format) {
		Date date = null;

		if (dateStr != null && (!dateStr.equals(""))) {
			DateFormat df = new SimpleDateFormat(format);
			try {
				date = df.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 把字符串转换为日期，日期的格式为yyyy-MM-dd HH:ss
	 *
	 * @param dateStr
	 *            日期字符串
	 * @return
	 */
	public static Date strToDate(String dateStr) {
		Date date = null;

		if (dateStr != null && (!dateStr.equals(""))) {
			if (dateStr.matches("//d{4}-//d{1,2}-//d{1,2}")) {
				dateStr = dateStr + " 00:00";
			} else if (dateStr.matches("//d{4}-//d{1,2}-//d{1,2} //d{1,2}")) {
				dateStr = dateStr + ":00";
			} else {
				System.out.println(dateStr + " 格式不正确");
				return null;
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss");
			try {
				date = df.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 把日期转换为字符串
	 *
	 * @param date
	 *            日期实例
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String dateToStr(Date date, String format) {
		return (date == null) ? "" : new SimpleDateFormat(format).format(date);
	}

	/**
	 * @return "yyyy-MM-dd HH:mm:ss"
	 */
	public static SimpleDateFormat getSimpleDateFormat() {
		return new SimpleDateFormat(DATE_FORMAT_YMDHMS);
	}

	/**
	 * @param format
	 * @return
	 */
	public static SimpleDateFormat getSimpleDateFormat(String format) {
		SimpleDateFormat sdf = null;
		if (null == format || "".equals(format)) {
			sdf = getSimpleDateFormat();
		} else {
			sdf = new SimpleDateFormat(format);
		}
		return sdf;
	}

	/**
	 * 返回当前日期时间字符串。
	 *
	 * @return 当前日期时间 "yyyy-MM-dd HH:mm:ss"
	 */
	public static String getToday() {
		return format(new Date(), DATE_FORMAT_YMDHMS);

	}

	/**
	 * 根据给定的格式，返回时间字符串。
	 *
	 * @param format
	 *            日期格式字符串
	 * @return String 指定格式的当前日期字符串.
	 */
	public static String getToday(String format) {
		return format(new Date(), format);

	}

	/**
	 * 根据给定的格式与时间(Date类型的)，返回时间字符串。
	 *
	 * @param date
	 *            指定的日期
	 * @param format
	 *            日期格式字符串
	 * @return String 指定格式的日期字符串.
	 */
	public static String format(Date date, String format) {
		return getSimpleDateFormat(format).format(date);
	}

	/**
	 * 根据给定的格式与时间(Date类型的)，返回时间字符串。
	 *
	 * @param date
	 *            指定的日期
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String format(Date date) {
		return getSimpleDateFormat().format(date);
	}

	/**
	 * 根据给定的格式与时间(String类型的)，返回时间字符串。
	 *
	 * @param date
	 *            指定的日期
	 * @param format
	 *            日期格式字符串
	 * @return String 指定格式的日期字符串.
	 */
	public static String format(String date, String format) {
		return getSimpleDateFormat(format).format(date);
	}

	/**
	 * 根据给定的格式与时间(String类型的)，返回时间字符串。
	 *
	 * @param date
	 *            指定的日期
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String format(String date) {
		return getSimpleDateFormat().format(date);
	}

	/**
	 * 返回当前指定的时间戳。格式为yyyy-MM-dd HH:mm:ss
	 *
	 * @return 格式为yyyy-MM-dd HH:mm:ss，总共19位。
	 */
	public static String getCurrentDateTime() {
		return getToday(DATE_FORMAT_YMDHMS);
	}

	/**
	 * 取得当前日期 年-月-日 返回当前时间字符串。 格式：yyyy-MM-dd
	 *
	 * @return String 指定格式的日期字符串.
	 */
	public static String getCurrentDate() {
		// DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		// String date= f.format(Calendar.getInstance().getTime());
		return getToday("yyyy-MM-dd");
	}

	/**
	 * 取得当前日期的年份，以yyyy格式返回.
	 *
	 * @return 当年 yyyy
	 */
	public static String getCurrentYear() {
		return getToday("yyyy");
	}

	/**
	 * 取得当前日期的月份，以MM格式返回.
	 *
	 * @return 当前月份 MM
	 */
	public static String getCurrentMonth() {
		return getToday("MM");
	}

	/**
	 * 取得当前日期的天数，以格式"dd"返回.
	 *
	 * @return 当前月中的某天dd
	 */
	public static String getCurrentDay() {
		return getToday("dd");
	}

	public static String getHalf() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("MM");
		String s = simpledateformat.format(calendar.getTime());
		return Integer.parseInt(s) >= 7 ? "1" : "0";
	}

	public static String getDate() {
		return getDate(0);
	}

	public static String getDate(int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(5, i);
		return formatDate(calendar.getTime());
	}

	public static String formatDate(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy/MM/dd");
		return simpledateformat.format(date);
	}

	public static String formatDate(String s) {
		return s;
	}

	public static String formatDBDate(String s) {
		return formatDBDate(s, "yyyy-MM-dd hh:mm:ss");
	}

	public static String formatDate(String s, String s1) {
		return formatDBDate(s, s1);
	}

	public static String formatDBDate(String s, String s1) {
		String[] as = { "yyyy-MM-dd hh:mm:ss", "yyyy/MM/dd" };
		SimpleDateFormat simpledateformat = new SimpleDateFormat();
		Date date = null;
		String s2 = null;
		if ((s != null) && (!s.trim().equals("")))
			try {
				int i = 0;

				while (i < as.length) {
					date = parseDate(s, as[i]);
					if (date != null)
						break;
					i++;
				}
				simpledateformat.applyPattern(s1);
				s2 = simpledateformat.format(date);
			} catch (Exception localException) {
			}
		return s2;
	}

	public static Date parseDate(String s, String s1) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(s1);
		Date date = null;
		try {
			simpledateformat.setLenient(true);
			date = simpledateformat.parse(s);
		} catch (Exception localException) {
		}
		return date;
	}

	public static String getMonth() {
		return getMonth(0);
	}

	public static String getMonth(int i) {
		Calendar calendar = Calendar.getInstance();
		return getMonth(calendar.getTime(), i);
	}

	public static String getMonth(String s) {
		return getMonth(s, 0);
	}

	public static String getMonth(String s, int i) {
		DateFormat dateformat = DateFormat.getDateTimeInstance();
		Date date = null;
		try {
			dateformat.setLenient(true);
			date = dateformat.parse(s);
		} catch (Exception localException) {
		}
		return getMonth(date, i);
	}

	public static String getMonth(Date date) {
		return getMonth(date, 0);
	}

	public static String getMonth(Date date, int i) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(2, i);
		return simpledateformat.format(calendar.getTime());
	}

	public static String getCurrentMonthFirst() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy/MM");
		String s = simpledateformat.format(calendar.getTime()) + "/01";
		return s;
	}

	public static String getYear(int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(1, i);
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy");
		String s = simpledateformat.format(calendar.getTime());
		return s;
	}

	public static String getPreviousMonthFirst() {
		String s = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("MM");
		String s1 = simpledateformat.format(calendar.getTime());
		simpledateformat = new SimpleDateFormat("yyyy");
		String s2 = simpledateformat.format(calendar.getTime());
		int i = Integer.parseInt(s1);
		if (i == 1)
			s = Integer.toString(Integer.parseInt(s2) - 1) + "/12/01";
		else if (i < 11)
			s = s2 + "/0" + Integer.toString(i - 1) + "/01";
		else
			s = s2 + "/" + Integer.toString(i - 1) + "/01";
		return s;
	}

	public static String getPreviousMonthCurrent() {
		String s = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("MM");
		String s1 = simpledateformat.format(calendar.getTime());
		simpledateformat = new SimpleDateFormat("yyyy");
		String s2 = simpledateformat.format(calendar.getTime());
		simpledateformat = new SimpleDateFormat("dd");
		String s3 = simpledateformat.format(calendar.getTime());
		int i = Integer.parseInt(s1);
		if (i == 1)
			s = Integer.toString(Integer.parseInt(s2) - 1) + "/12/" + s3;
		else if (i < 11)
			s = s2 + "/0" + Integer.toString(i - 1) + "/" + s3;
		else
			s = s2 + "/" + Integer.toString(i - 1) + "/" + s3;
		return s;
	}

	public static String getPreviousYearCurrent() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy");
		String s = simpledateformat.format(calendar.getTime());
		String s1 = Integer.toString(Integer.parseInt(s) - 1) + "/";
		simpledateformat = new SimpleDateFormat("MM/dd");
		s1 = s1 + simpledateformat.format(calendar.getTime());
		return s1;
	}

	public static String getPreviousYearDate(int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(1, -1);
		calendar.add(5, i);
		return formatDate(calendar.getTime());
	}

	public static String getPreviousYearCurrentMonthFirst() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy");
		String s = simpledateformat.format(calendar.getTime());
		s = Integer.toString(Integer.parseInt(s) - 1) + "/";
		simpledateformat = new SimpleDateFormat("MM");
		String s1 = simpledateformat.format(calendar.getTime());
		String s2 = s + s1 + "/01";
		return s2;
	}

	public static String getNextYearCurrent() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy");
		String s = simpledateformat.format(calendar.getTime());
		String s1 = Integer.toString(Integer.parseInt(s) + 1) + "/";
		simpledateformat = new SimpleDateFormat("MM/dd");
		s1 = s1 + simpledateformat.format(calendar.getTime());
		return s1;
	}

}
