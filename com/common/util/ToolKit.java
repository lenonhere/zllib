package com.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ToolKit {

	static FileCopyer _fileCopyer = null;

	public static String filterTime(String time) {

		String ret = "";

		if ((time == null) || (time == "")) {

			return ret;
		}

		try {

			int pos = time.lastIndexOf(' ');

			if (pos != -1) {
				ret = time.substring(0, pos);
			} else {
				ret = time;
			}
		} catch (IndexOutOfBoundsException e) {
			ret = "";
		}

		return ret;
	}

	public static String filterTimeShort(Object time) {
		return filterTime("yyyy-MM-dd", time);
	}

	/**
	 * 过滤掉yyyy-mm-dd hh:mm:ss.fffffffff的后半部分，变为yyyy-mm-dd hh:mm
	 *
	 * @param time
	 * @return
	 */
	public static String filterTimetohm(String time) {

		String ret = "";

		if ((time == null) || (time == "")) {

			return ret;
		}

		try {

			int pos = time.lastIndexOf('.');

			if (pos != -1) {
				ret = time.substring(0, pos);
			} else {
				ret = time;
			}
		} catch (IndexOutOfBoundsException e) {
			ret = "";
		}

		return ret;
	}

	public static String filterTime(String pattern, Object time) {

		if (time == null) {
			return "";
		}
		try {
			return new SimpleDateFormat(pattern).format(Timestamp.valueOf(time
					.toString()));
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 过滤掉yyyy-mm-dd hh:mm:ss.fffffffff的后半部分，变为yyyy-mm-dd
	 *
	 * @param time
	 *            Date,Time或TimeStamp对象
	 * @return
	 */
	public static String filterTime(Object time) {

		return (time == null ? "" : filterTime(time.toString()));
	}

	/**
	 * 将对象数组连接为字符串，分隔符为[,]
	 *
	 * @param objs
	 * @return
	 */
	public static String join(Object[] objs) {

		StringBuffer sbRet = new StringBuffer("");

		if ((objs == null) || (objs.length == 0)) {

			return "";
		}

		boolean needSeparator = false;

		for (int i = 0; i < objs.length; i++) {

			if (objs[i] == null || objs[i].toString().trim().length() == 0) {

				continue;
			}

			if (needSeparator) {
				sbRet.append(",");
			}

			sbRet.append(objs[i].toString());
			needSeparator = true;
		}

		return sbRet.toString();
	}

	public static String join(Object[] objs, char pars, char appe) {

		StringBuffer sbRet = new StringBuffer("");

		if ((objs == null) || (objs.length == 0)) {

			return "";
		}

		boolean needSeparator = false;

		for (int i = 0; i < objs.length; i++) {

			if (objs[i] == null || objs[i].toString().trim().length() == 0) {

				continue;
			}

			if (needSeparator) {
				sbRet.append(pars);
			}

			sbRet.append(appe).append(objs[i].toString()).append(appe);
			needSeparator = true;
		}

		return sbRet.toString();
	}

	/**
	 * 截取字符串的一部分
	 *
	 * @param src
	 * @param len
	 * @return
	 */
	public static String truncate(String src, int len) {

		if (len < 0) {

			return src;
		}

		int size = src.length();

		if (size <= len) {

			return src;
		}

		StringBuffer sbRet = new StringBuffer(src);

		if (size > len) {
			sbRet.delete(len, size);
			// sbRet.append("...");
		}

		return sbRet.toString();
	}

	public static String truncateEx(String src, int len, boolean check) {

		if (len < 0) {

			return src;
		}

		int size = src.length();

		if (size <= len) {

			return src;
		}

		StringBuffer sbRet = new StringBuffer(src);

		if (size > len) {
			sbRet.delete(len, size);
			if (check)
				sbRet.append("...");
		}

		return sbRet.toString();
	}

	/**
	 * 分析单个E-Mail地址是否合法
	 *
	 * @param address
	 * @return
	 */
	public static boolean parseAddress(String address) {

		if (address.length() == 0) {

			return true;
		}

		int atindex = address.indexOf('@');
		int pointindex = address.indexOf('.');

		if (atindex > 0 && atindex < pointindex
				&& pointindex < address.length() - 2) {

			return true;
		} else {

			return false;
		}
	}

	/**
	 * 分析一组E-Mail地址是否合法，地址之间用[,]连接
	 *
	 * @param addresses
	 * @return
	 */
	public static boolean parseAddresses(String addresses) {

		boolean valid = true;
		int index = 0;

		while ((index = addresses.indexOf(',')) > 0) {
			valid = valid && parseAddress(addresses.substring(0, index));

			if (!valid) {

				return false;
			} else {
				addresses = addresses.substring(index + 1);
			}
		}

		return parseAddress(addresses);
	}

	public static String blankNull(Object obj) {

		return (obj == null) ? "" : obj.toString();
	}

	public static boolean copyFile(File fileIn, File fileOut, boolean overwrite)
			throws FileNotFoundException, IOException {

		if (_fileCopyer == null) {
			_fileCopyer = new FileCopyer();
		}

		return _fileCopyer.copyFile(fileIn, fileOut, overwrite);
	}

	public static void duplicateFoldStructure(File foldIn, File foldOut,
			boolean limitLevels, int levels) {

		if (_fileCopyer == null) {
			_fileCopyer = new FileCopyer();
		}

		_fileCopyer
				.duplicateFoldStructure(foldIn, foldOut, limitLevels, levels);
	}

	public static void copyFold(File foldIn, File foldOut,
			boolean includeSubfolders, boolean overwrite) {

		if (_fileCopyer == null) {
			_fileCopyer = new FileCopyer();
		}

		_fileCopyer.copyFold(foldIn, foldOut, includeSubfolders, overwrite);
	}

	/**
	 * 除法运算，百分比，商小数点后保留2位
	 *
	 * @param dividend
	 * @param divisor
	 * @return
	 */
	public static float percent(long dividend, long divisor) {

		return (float) (dividend * 10000 / divisor) / 100f;
	}

	public static String capitalize(String src) {

		if ((src == null) || (src.length() == 0)) {

			return src;
		}

		StringBuffer sbTemp = new StringBuffer(src);
		sbTemp.replace(0, 1, String.valueOf(sbTemp.charAt(0)).toUpperCase());

		return sbTemp.toString();
	}

	public static String toString(Object[] o) {

		if (o == null) {

			return "";
		}

		return toString(Arrays.asList(o));
	}

	public static String toString(Collection c) {

		return toString(c, ",");
	}

	public static String toString(Object[] o, String delim) {

		if (o == null) {

			return "";
		}

		return toString(Arrays.asList(o), delim);
	}

	public static String toString(Collection c, String delim) {

		if (c == null) {

			return "";
		}

		Iterator iterator = c.iterator();
		StringBuffer buffer = new StringBuffer();
		boolean needSeparator = false;

		while (iterator.hasNext()) {

			String str = iterator.next().toString();

			if (str.trim().length() == 0) {

				continue;
			}

			if (needSeparator) {
				buffer.append(delim);
			}

			buffer.append(str);
			needSeparator = true;
		}
		// System.out.println(buffer.toString());
		return buffer.toString();
	}

	public static Object[] splitString(String s, char separator) {

		return splitStr(s, separator);
	}

	public static String[] splitStr(String s, char separator) {

		return splitStr(s, String.valueOf(separator));
	}

	public static Object[] splitString(String s, String separator) {
		return splitStr(s, separator);

	}

	public static String[] splitStr(String s, String separator) {

		if (s == null || s.trim().length() == 0) {

			return new String[0];
		}

		//
		while (s.startsWith(separator)) {
			s = s.substring(separator.length());
		}

		if (!s.endsWith(separator)) {
			s = s + separator;
		}

		return s.split(separator);
	}

	public static boolean isAncestor(File ancestor, File descendant) {

		if (_fileCopyer == null) {
			_fileCopyer = new FileCopyer();
		}

		return _fileCopyer.isAncestor(ancestor, descendant);
	}

	public static int getChildFileCount(File fold) {

		if (_fileCopyer == null) {
			_fileCopyer = new FileCopyer();
		}

		return _fileCopyer.getChildFileCount(fold);
	}

	public static String replaceToHTML(String src) {
		String[] oldStrs = { "\n" };
		String[] newStrs = { "<br>" };
		return replace(src, oldStrs, newStrs);
	}

	public static String replaceTOHtml(String src) {
		String[] oldStrs = { "</P><br>", "<br><P>", "</p><br>", "<br><p>" };
		String[] newStrs = { "</P>", "<P>", "</p>", "<p>" };

		return replace(src, oldStrs, newStrs);

	}

	public static String replaceHTML(String src) {

		String[] oldStrs = { "<", ">", "\n", " " };
		String[] newStrs = { "&lt;", "&gt;", "<br>", "&nbsp;" };

		return replace(src, oldStrs, newStrs);
	}

	public static String replace(String src, String[] oldStrs, String[] newStrs) {

		String dst = src;

		for (int i = 0; i < oldStrs.length; i++) {
			dst = dst.replaceAll(oldStrs[i], newStrs[i]);
		}

		return dst;
	}

	public static String[] toArray(String s) {

		return toArray(s, ",");
	}

	public static String[] toArray(String s, String delim) {

		return splitStr(s, delim);
	}

	public static boolean isValidURI(String str) {

		String[] prefix = { "http://", "ftp://", "mms://", "https://",
				"rstp://" };

		if (str == null) {

			return false;
		}

		str = str.toLowerCase();

		for (int i = 0; i < prefix.length; i++) {

			if (str.startsWith(prefix[i])) {

				return true;
			}
		}

		return false;
	}

	public static boolean isImageFile(String s) {

		String[] ext = { ".bmp", ".gif", ".jpe", ".jpeg", ".jpg", ".png",
				".pnm", ".pnt", ".psd", ".tif", ".tiff" };

		if (s == null) {

			return false;
		}

		s = s.toLowerCase();

		for (int i = 0; i < ext.length; i++) {

			if (s.endsWith(ext[i])) {

				return true;
			}
		}

		return false;
	}

	public static boolean isFlashFile(String s) {

		String[] ext = { ".swf" };

		if (s == null) {

			return false;
		}

		s = s.toLowerCase();

		for (int i = 0; i < ext.length; i++) {

			if (s.endsWith(ext[i])) {

				return true;
			}
		}

		return false;
	}

	public static boolean isZipFile(String s) {

		String[] ext = { ".zip", ".rar", ".exe" };

		if (s == null) {

			return false;
		}

		s = s.toLowerCase();

		for (int i = 0; i < ext.length; i++) {

			if (s.endsWith(ext[i])) {

				return true;
			}
		}

		return false;
	}

	public static Timestamp makeTimestamp(String yearMonthDate)
			throws IllegalArgumentException {

		// Timestamp format must be yyyy-mm-dd hh:mm:ss.fffffffff
		return java.sql.Timestamp
				.valueOf(yearMonthDate + " 00:00:00.000000000");
	}

	public static String getExtension(File file) {

		return _fileCopyer.getExtension(file);
	}

	public static boolean isDigit(String s) {

		if (s == null) {

			return false;
		}

		return s.matches("^[0-9]+$");
	}

	public static boolean isDigitComma(String s) {

		if (s == null) {

			return false;
		}

		return s.matches("^[0-9, \t]*$");
	}

	public static boolean isASCII(String s) {

		if (s == null) {

			return false;
		}

		return s.matches("^[\u0000-\u007F]*$");
	}

	public static String[] makeEmptyArray(int columnCount) {

		String[] columns = new String[columnCount];

		for (int i = 0; i < columns.length; i++) {
			columns[i] = "";
		}

		return columns;
	}

	public static String execCommand(String commandLine, String workFold) {

		try {
			InputStream input = executeCommand(commandLine, workFold);
			BufferedReader br = new BufferedReader(new InputStreamReader(input));
			StringBuffer buffer = new StringBuffer();
			String inline;

			while (null != (inline = br.readLine())) {
				buffer.append(inline + "\n");
			}

			return buffer.toString();
		} catch (IOException ex) {
			return ex.getMessage();
		}
	}

	public static InputStream executeCommand(String commandLine, String workFold)
			throws IOException {
		String command = "cmd /C " + commandLine;
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(command, null, new File(workFold));
		InputStream input = process.getInputStream();
		return input;
	}

	public static String toUpperNum(double pnum, int pround) {
		String RESULT = "";
		String NUM_ROUND = String.valueOf(pnum);
		int pos_left = NUM_ROUND.indexOf('.');
		if (pos_left != -1 && pos_left + pround + 1 < NUM_ROUND.length())
			NUM_ROUND = NUM_ROUND.substring(0, pos_left + pround + 1);

		String str1 = "零壹贰参肆伍陆柒捌玖"; // 数字大写
		String str2 = "点拾佰仟万拾佰仟亿拾佰仟万拾佰仟"; // --数字位数(从低至高)
		int NUM_PRE = 1; // 前一位上的数字
		int NUM_CURRENT; // 当前位上的数字
		int NUM_COUNT = 0; // 当前数字位数

		String NUM_LEFT = "";
		String NUM_RIGHT = "";

		if (pos_left == -1) {
			NUM_LEFT = NUM_ROUND;
			NUM_RIGHT = "";
		} else {
			NUM_LEFT = NUM_ROUND.substring(0, pos_left);
			NUM_RIGHT = NUM_ROUND.substring(pos_left + 1, NUM_ROUND.length());
		}

		if (NUM_LEFT.length() > 16)
			return "**********";

		// 采用从低至高的算法，先处理小数点左边的数字
		for (int i = NUM_LEFT.length() - 1; i >= 0; i--) {

			NUM_CURRENT = Integer.parseInt(NUM_LEFT.substring(i, i + 1)); // 当前位上的数字
			NUM_COUNT = NUM_COUNT + 1; // 当前数字位数
			if (NUM_CURRENT > 0) {
				// 当前位上数字不为0按正常处理
				RESULT = str1.substring(NUM_CURRENT, NUM_CURRENT + 1)
						+ str2.substring(NUM_COUNT - 1, NUM_COUNT) + RESULT;
			} else {
				// 当前位上数字为0时
				if (NUM_COUNT - 1 % 4 == 0) {
					// 当前位是点、万或亿时
					RESULT = str2.substring(NUM_COUNT - 1, NUM_COUNT) + RESULT;
					NUM_PRE = 0; // 点、万,亿前不准加零
				}
				if (NUM_PRE > 0 || NUM_LEFT.length() == 1) {
					// 上一位数字不为0或只有个位时
					RESULT = str1.substring(NUM_CURRENT, NUM_CURRENT + 1)
							+ RESULT;
				}
			}
			NUM_PRE = NUM_CURRENT;

		}

		// 再处理小数点右边的数字
		if (NUM_RIGHT.length() > 0) {
			// --(从高至低)
			for (int i = 0; i < NUM_RIGHT.length(); i++) {
				NUM_CURRENT = Integer.parseInt(NUM_RIGHT.substring(i, i + 1)); // 当前位上的数字
				RESULT = RESULT + str1.substring(NUM_CURRENT, NUM_CURRENT + 1);
			}
		} else {
			RESULT = RESULT.replaceAll("点", ""); // 无小数时去掉点
		}

		if (pnum < 0)
			// 转换数字是负数时
			RESULT = "负" + RESULT;

		return RESULT;

	}

	class TextInfo {
		private int start;

		private int end;

		private String text;

		public TextInfo() {
		}

		public TextInfo(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

		public int getEnd() {
			return end;
		}

		public int getStart() {
			return start;
		}

		public void setText(String text) {
			this.text = text;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public void setStart(int start) {
			this.start = start;
		}
	}

}
