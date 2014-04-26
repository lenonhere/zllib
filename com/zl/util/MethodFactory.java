package com.zl.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.jdom.Verifier;

import com.qmx.dateutils.DateUtils;
import com.zl.common.PageControl;

public class MethodFactory {
	private static final Logger log = Logger.getLogger(MethodFactory.class);

	/**
	 * 将数字字符串转换成中文形式
	 *
	 * @param numStr
	 *            数字字符串
	 * @return 相应的中文形式字符串+(单位:元整)
	 */
	public static String ConvertCharge(String numStr) {
		return ConvertCharge(numStr, null);
	}

	/**
	 * 将数字字符串转换成中文形式
	 *
	 * @param numStr
	 *            数字字符串
	 * @param suffix
	 *            后缀(单位:元整)
	 * @return 相应的中文形式字符串+suffix
	 */
	public static String ConvertCharge(String numStr, String suffix) {
		StringBuffer retStrBuf = new StringBuffer();
		String num[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[] = { "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };
		int dot = 0; // 小数点位置
		int ivalue = 0;
		int len = 0;
		if (numStr == null || numStr.length() <= 0) {
			return num[0];
		}
		dot = numStr.indexOf(".");
		if (dot == -1) {
			String tmpStr = numStr;
			len = tmpStr.length();
			int i = 0;
			for (i = 0; i < len - 1; i++) {
				ivalue = Integer.parseInt(tmpStr.substring(i, i + 1));
				retStrBuf.append(num[ivalue]).append(unit[(len - i - 1) % 8]);
			}
			ivalue = Integer
					.parseInt(String.valueOf(tmpStr.substring(i, i + 1)));
			retStrBuf.append(num[ivalue]);
		} else {
			String tmpStr1 = numStr.substring(0, dot);
			len = tmpStr1.length();
			int i = 0;
			for (i = 0; i < len - 1; i++) {
				ivalue = Integer.parseInt(tmpStr1.substring(i, i + 1));
				retStrBuf.append(num[ivalue]).append(unit[(len - i - 1) % 8]);
			}
			ivalue = Integer.parseInt(tmpStr1.substring(i, i + 1));
			retStrBuf.append(num[ivalue]);

			if (dot < numStr.length() - 1) {
				retStrBuf.append("点");
				String tmpStr2 = numStr.substring(dot + 1);
				len = tmpStr2.length();
				for (i = 0; i < len; i++) {
					ivalue = Integer.parseInt(tmpStr2.substring(i, i + 1));
					retStrBuf.append(num[ivalue]);
				}
			}
		}

		String str = "";
		if (null != suffix) {
			str = suffix;
		}
		retStrBuf.append(str);
		return retStrBuf.toString();
	}

	/**
	 * 判断字符串str是否为空 [null | "" | length]
	 *
	 * @param str
	 * @return 为空返回true,否则返回false.
	 */
	public static boolean isEmpty(String str) {
		boolean flag = false;
		if (null == str) {
			flag = true;
		} else {
			String src = str.trim();
			if (src.length() == 0 || "".equals(src)) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 换行
	 */
	public static void print() {
		print("", "INFO");
	}

	/**
	 * @param msg
	 *            打印msg
	 */
	public static void print(Object msg) {
		print(msg, "QMX");
	}

	/**
	 * @param msg
	 *            打印msg
	 * @param info
	 *            提示标记
	 */
	public static void print(Object msg, String info) {
		System.out.println("" + DateUtils.getCurrentDateTime() + " [" + info
				+ "] " + msg);
	}

	/**
	 * @param msg
	 *            打印msg
	 * @param clas
	 *            类名Object.class
	 */
	public static void print(Object msg, Class<?> clas) {
		print(msg, clas.toString());
	}

	/**
	 * 去除src最前和最后的split
	 *
	 * @param src
	 *            源字符串
	 * @param split
	 *            要去除的字符
	 * @return
	 */
	public static String trims(String src, String split) {
		String str = src;
		int len = str.length();
		int index = str.indexOf(split);
		while (index == 0) {
			str = str.substring(1, len);
			len = str.length();
			index = str.indexOf(split);
		}
		len = str.length();
		index = str.lastIndexOf(split);
		while (index == len - 1) {
			str = str.substring(0, len - 1);
			len = str.length();
			index = str.lastIndexOf(split);
		}

		return str;
	}

	/**
	 *
	 * 获取文件前缀(默认分隔符 .)
	 *
	 * @param str
	 *            readme.txt
	 * @return readme
	 */
	public static String getPrefix(String str) {
		return getPrefix(str, ".");
	}

	/**
	 * 获取文件前缀(指定分隔符)
	 *
	 * @param str
	 *            readme.txt
	 * @param split
	 *            .
	 * @return readme
	 */
	public static String getPrefix(String str, String split) {
		String prefix = "";
		if (str != null && !"".equals(str)) {
			int index = str.lastIndexOf(".");
			prefix = str.substring(0, index);
		}
		return prefix;
	}

	/**
	 *
	 * 获取文件后缀(默认分隔符 .)
	 *
	 * @param str
	 *            readme.txt
	 * @return txt
	 */
	public static String getSuffix(String str) {
		return getSuffix(str, ".");
	}

	/**
	 * 获取文件后缀(指定分隔符)
	 *
	 * @param str
	 *            readme.txt
	 * @param split
	 *            .
	 * @return txt
	 */
	public static String getSuffix(String str, String split) {
		String suffix = "";
		if (str != null && !"".equals(str)) {
			int len = str.length();
			int index = str.lastIndexOf(".");
			suffix = str.substring(index + 1, len);
		}
		return suffix;
	}

	/**
	 *
	 * @param filePath
	 *            例: E:/temp/files/readme.txt
	 * @return String[5]
	 *         <p>
	 *         0.文件目录 E:/temp/files/
	 *         <p>
	 *         1.文件名称 readme.txt
	 *         <p>
	 *         2.文件名 readme
	 *         <p>
	 *         3.文件后缀 .txt
	 *         <p>
	 *         4.文件路径 E:/temp/files/readme.txt
	 */
	public static String[] getFileInfo(String filePath) {
		String[] str = new String[5];

		if (!isEmpty(filePath)) {
			int len = filePath.length();
			int index = filePath.lastIndexOf("/");
			String dirStr = filePath.substring(0, index + 1);
			String fileStr = filePath.substring(index + 1, len);
			// print(len + "==" + index + "==" + dirStr + "==" + fileStr,
			// "目录信息");

			// len = fileStr.length();
			// index = fileStr.lastIndexOf(".");
			// String prefix = fileStr.substring(0, index);
			// String suffix = fileStr.substring(index, len);
			// print(len + "==" + index + "==" + prefix + "==" + suffix,
			// "文件信息");

			str[0] = dirStr;
			str[1] = fileStr;
			// str[2] = prefix;
			// str[3] = suffix;
			str[2] = getPrefix(fileStr);
			str[3] = "." + getSuffix(fileStr);
			str[4] = filePath;
		}
		print(str[4] + " --> " + str[0] + ", " + str[1] + ", " + str[2] + ", "
				+ str[3], "文件信息");
		return str;
	}

	/**
	 * 创建文件
	 *
	 * @param filePath
	 * @return
	 */
	public static File newFile(String filePath) {
		File file = null;
		if (!isEmpty(filePath)) {
			try {
				file = new File(filePath);
				if (!file.exists()) {
					file.createNewFile();
				}
			} catch (IOException e) {
				e.printStackTrace();
				print(filePath, "文件创建失败");
			}
		}
		return file;
	}

	/**
	 * 写入文件
	 *
	 * @param file
	 * @param content
	 * @return
	 */
	public static boolean writeFile(File file, String content) {
		boolean flag = true;
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.append(content);
			bw.append("\n");
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
				print("关闭文件流异常!!");
			}
		}
		return flag;
	}

	public static void generateVariable(String paramString) {
		Class localClass = null;
		try {
			localClass = Class.forName(paramString);
			PropertyDescriptor[] arrayOfPropertyDescriptor = Introspector
					.getBeanInfo(localClass).getPropertyDescriptors();
			for (int i = 0; i < arrayOfPropertyDescriptor.length; i++) {
				StringBuffer localStringBuffer = new StringBuffer();
				String str = "";
				localStringBuffer.append("private ");
				if (arrayOfPropertyDescriptor[i].getPropertyType().getName()
						.indexOf("String") >= 0) {
					localStringBuffer.append("String");
					str = "\"\"";
				} else if (arrayOfPropertyDescriptor[i].getPropertyType()
						.getName().indexOf("Integer") >= 0) {
					localStringBuffer.append("int");
					str = "Constants.NULL_INT";
				} else if (arrayOfPropertyDescriptor[i].getPropertyType()
						.getName().indexOf("Double") >= 0) {
					localStringBuffer.append("double");
					str = "0.0";
				} else {
					if (arrayOfPropertyDescriptor[i].getPropertyType()
							.getName().indexOf("Timestamp") < 0)
						continue;
					localStringBuffer.append("String");
					str = "\"\"";
				}
				localStringBuffer.append(" form"
						+ arrayOfPropertyDescriptor[i].getName());
				localStringBuffer.append(" = " + str + ";");
				System.out.println(localStringBuffer);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public static String getThisString(Object obj) {
		String s = "";
		if (obj != null)
			s = obj.toString().trim();
		return s;
	}

	public static String getThisString(int i) {
		String s = "";
		s = Integer.toString(i);
		s.trim();
		return s;
	}

	public static String digitsToStr(double d, int i) {
		double d1 = Math.abs(d);
		double d2 = Math.floor(d1);
		double d3 = d1 - d2;
		String s = "";
		if (d < 0.0D)
			s = s + "-";
		s = s + Math.round(d2) + ".";
		for (int j = 0; j < i; j++) {
			d3 = (d3 - Math.floor(d3)) * 10.0D + 1.0E-010D;
			long l = (long) Math.floor(d3);
			s = s + l;
		}

		return s;
	}

	public static String getThisString(char c) {
		Character character = new Character(c);
		return character.toString();
	}

	public static ArrayList splitString(String s, String s1, boolean flag) {
		ArrayList arraylist = new ArrayList();
		if (s != null) {
			s = s.trim();
			int i = 0;
			int j = 0;
			do {
				j = s.indexOf(s1, i);
				if (j >= 0)
					arraylist.add(s.substring(i, j));
				else if (flag)
					arraylist.add(s.substring(i));
				i = j + 1;
			} while (j >= 0);
		}
		return arraylist;
	}

	public static ArrayList splitString(String s, String s1) {
		return splitString(s, s1, false);
	}

	public static String replace(String paramString1, String paramString2, String paramString3, int paramInt)
	  {
	    String str = null;
	    int i = 0;
	    int j = 0;
	    try
	    {
	      str = new String(paramString1);
	      i = str.indexOf(paramString2);
	      while ((i >= 0) && ((paramInt == 0) || (paramInt > j)))
	      {
	        str = str.substring(0, i) + paramString3 + str.substring(i + paramString2.length());
	        i += paramString3.length();
	        i = str.indexOf(paramString2, i);
	        j += 1;
	      }
	      return str;
	    }
	    catch (Exception e)
	    {
	      log.error(e);
	    }
	    return str;
	  }

	/**
	 * @param s
	 *            原字符串
	 * @param s1
	 * @param s2
	 * @param i
	 * @return
	 */
	public static String replace2(String s, String s1, String s2, int i) {
		String s3 = null;
		try {
			int k = 0;
			s3 = new String(s);
			int j = s3.indexOf(s1);
			do {
				s3 = s3.substring(0, j) + s2 + s3.substring(j + s1.length());
				j += s2.length();
				j = s3.indexOf(s1, j);

				k++;
				if (j < 0)
					break;
			} while ((i == 0) || (i > k));
		} catch (Exception e) {
			log.error(e);
		}
		return s3;
	}

	public static String replace(String s, String s1, String s2) {
		return replace(s, s1, s2, 0);
	}

	public static String checkXMLElement(String s) {
		String s1 = s;
		String[] as = { "<", ">" };
		String[] as1 = { "〈", "〉" };
		if (Verifier.checkElementName(s) != null)
			try {
				for (int i = 0; i < as.length; i++)
					s1 = replace(s1, as[i], as1[i]);
			} catch (Exception localException) {
			}
		return s1;
	}

	public static void copyProperties(Object obj, Object obj1, boolean flag)
			throws Exception {
		HashMap hashmap = new HashMap();
		try {
			Map map = BeanUtils.describe(obj1);
			Set set = map.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String s = (String) iterator.next();
				if (flag) {
					hashmap.put("form" + s, map.get(s));
				} else if (s.startsWith("form")) {
					s = s.substring("form".length());
					hashmap.put(s, map.get("form" + s));
				} else {
					hashmap.put(s, map.get(s));
				}
			}

			BeanUtils.populate(obj, hashmap);
		} catch (Exception exception) {
			throw exception;
		}
	}

	public static void copyProperties(Object obj, Object obj1) throws Exception {
		try {
			if ((obj != null) && (obj1 != null)) {
				Set set = BeanUtils.describe(obj1).keySet();
				Set set1 = BeanUtils.describe(obj).keySet();
				Iterator iterator = set1.iterator();

				while (iterator.hasNext()) {
					String s = (String) iterator.next();
					if (set.contains(s.toLowerCase())) {
						Object obj2 = PropertyUtils.getProperty(obj1,
								s.toLowerCase());
						obj2 = obj2 != null ? obj2.toString() : null;
						PropertyUtils.setProperty(obj, s, obj2);
					}
				}
			}
		} catch (Exception exception) {
			throw exception;
		}
	}

	public static ArrayList objectToArrayList(Object obj) {
		ArrayList arraylist = new ArrayList();
		arraylist.add(obj);
		return arraylist;
	}

	public static void initSessionGrid(HttpServletRequest httpservletrequest,
			String s, ArrayList arraylist) {
		HttpSession httpsession = httpservletrequest.getSession();
		try {
			if (httpsession.getAttribute(s) != null)
				httpsession.removeAttribute(s);
			httpsession.setAttribute(s, arraylist);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static boolean isAddNew(Integer integer) {
		boolean flag = true;
		try {
			if ((integer != null) && (integer.intValue() > 0))
				flag = false;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return flag;
	}

	public static Object getApplyUpdate2(Map map) {
		Object obj = null;
		try {
			if (map != null) {
				ArrayList arraylist = new ArrayList();
				if (map.containsKey("DATA")) {
					ArrayList arraylist1 = (ArrayList) map.get("DATA");
					if (arraylist1.size() > 0)
						obj = arraylist1.get(0);
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return obj;
	}

	public static Object getSingleObject(Map map) {
		Object obj = null;
		try {
			if (map != null) {
				ArrayList arraylist = new ArrayList();
				if (map.containsKey("DETAIL")) {
					ArrayList arraylist1 = (ArrayList) map.get("DETAIL");
					if (arraylist1.size() > 0)
						obj = arraylist1.get(0);
				}
				if (map.containsKey("DATA"))
					obj = map.get("DATA");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return obj;
	}

	public static void initQueryCondition(PageControl condition,
			HttpServletRequest httpservletrequest)
			throws IllegalArgumentException {
		if (condition == null) {
			throw new IllegalArgumentException("查询条件对象不能为空!");
		}
		int i = getCurrentPage(httpservletrequest);
		log.debug("Current PageNO:" + i);
		condition.setStart((i - 1) * 10 + 1);
		condition.setEnd(i * 10);
	}

	public static PageControl initQueryResult(Map map,
			HttpServletRequest httpservletrequest) {
		PageControl pagecontrol = null;
		try {
			ArrayList arraylist = (ArrayList) map.get("DETAIL");
			int i = ((Integer) map.get("COUNT")).intValue();
			int j = getCurrentPage(httpservletrequest);
			pagecontrol = new PageControl(i, arraylist, j, 10);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return pagecontrol;
	}

	private static int getCurrentPage(HttpServletRequest httpservletrequest) {
		String s = httpservletrequest.getParameter("PageNo");
		String s1 = httpservletrequest.getParameter("totalPageCount");
		int i = 1;
		String s2 = httpservletrequest.getParameter("isNewQuery");
		if (s2 == null) {
			if (s != null)
				i = Integer.parseInt(s);
			if ((s1 != null) && (Integer.parseInt(s) > Integer.parseInt(s1)))
				i = Integer.parseInt(s1);
		}
		return i;
	}

	public static void clearSessionGrid(HttpServletRequest httpservletrequest) {
		HttpSession httpsession = httpservletrequest.getSession();
		try {
			if (httpsession.getAttribute("session.griddata.first") != null)
				httpsession.removeAttribute("session.griddata.first");
			if (httpsession.getAttribute("session.griddata.second") != null)
				httpsession.removeAttribute("session.griddata.second");
			if (httpsession.getAttribute("session.griddata.third") != null)
				httpsession.removeAttribute("session.griddata.third");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void generateBeanPropertySet(String s) {
		Object obj = null;
		try {
			Class class1 = Class.forName(s);
			PropertyDescriptor[] apropertydescriptor = Introspector
					.getBeanInfo(class1).getPropertyDescriptors();
			for (int i = 0; i < apropertydescriptor.length; i++) {
				StringBuffer stringbuffer = new StringBuffer();
				stringbuffer
						.append("digester.addBeanPropertySetter(\"details/detail/");
				stringbuffer.append(apropertydescriptor[i].getName());
				stringbuffer.append("\", \"" + apropertydescriptor[i].getName()
						+ "\");");
				System.out.println(stringbuffer);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void generateFormValidate(String s) {
		Object obj = null;
		try {
			Class class1 = Class.forName(s);
			PropertyDescriptor[] apropertydescriptor = Introspector
					.getBeanInfo(class1).getPropertyDescriptors();
			for (int i = 0; i < apropertydescriptor.length; i++)
				if (apropertydescriptor[i].getPropertyType().getName()
						.indexOf("Timestamp") >= 0) {
					StringBuffer stringbuffer = new StringBuffer();
					stringbuffer.append("form"
							+ apropertydescriptor[i].getName());
					stringbuffer.append(" = MethodFactory.formatDBDate(form"
							+ apropertydescriptor[i].getName() + ");");
					System.out.println(stringbuffer);
				}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void generateNewMethod(String s) {
		Object obj = null;
		try {
			Class class1 = Class.forName(s);
			Method[] amethod = class1.getMethods();
			for (int i = 0; i < amethod.length; i++)
				if (amethod[i].getName().indexOf("get") >= 0) {
					StringBuffer stringbuffer = new StringBuffer();
					stringbuffer.append("public static ArrayList "
							+ amethod[i].getName() + "(");
					Class[] aclass = amethod[i].getParameterTypes();
					for (int j = 0; j < aclass.length; j++) {
						stringbuffer.append(aclass[j].getName() + " param" + j
								+ ",");
					}
					if (aclass.length > 0)
						stringbuffer.deleteCharAt(stringbuffer.length() - 1);
					stringbuffer.append(") {\n\t");
					stringbuffer.append("\treturn " + amethod[i].getName()
							+ "(");
					for (int k = 0; k < aclass.length; k++) {
						stringbuffer.append("param" + k + ",");
					}
					stringbuffer.append("false);\n");
					stringbuffer.append("}");
					System.out.println(stringbuffer);
				}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static String getCurrentMonthFirstByYesterday() {
		String s = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy");
		String s1 = simpledateformat.format(calendar.getTime());
		simpledateformat = new SimpleDateFormat("MM");
		String s2 = simpledateformat.format(calendar.getTime());
		simpledateformat = new SimpleDateFormat("dd");
		String s3 = simpledateformat.format(calendar.getTime());
		int i = Integer.parseInt(s2);
		int j = Integer.parseInt(s3);
		if (j == 1) {
			if (i == 1)
				s = Integer.toString(Integer.parseInt(s1) - 1) + "/" + "12/01";
			else if (i < 11)
				s = s1 + "/0" + Integer.toString(i - 1) + "/01";
			else
				s = s1 + "/" + Integer.toString(i - 1) + "/01";
		} else
			s = s1 + "/" + s2 + "/" + "01";

		return s;
	}

	public static String getPreviousMonthYesterday() {
		String s = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy");
		String s1 = simpledateformat.format(calendar.getTime());
		simpledateformat = new SimpleDateFormat("MM");
		String s2 = simpledateformat.format(calendar.getTime());
		simpledateformat = new SimpleDateFormat("dd");
		String s3 = simpledateformat.format(calendar.getTime());
		int i = Integer.parseInt(s2);
		int j = Integer.parseInt(s3);
		if (j == 1) {
			if (i == 2)
				s = Integer.toString(Integer.parseInt(s1) - 1) + "/12/31";
			else if (i == 1)
				s = Integer.toString(Integer.parseInt(s1) - 1) + "/11/30";
			else if (i < 12)
				s = s1 + "/0" + Integer.toString(i - 2) + "/"
						+ getMonthLastDay(i - 2);
			else
				s = s1 + "/" + Integer.toString(i - 2) + "/"
						+ getMonthLastDay(i - 2);
		} else if (i == 1)
			s = Integer.toString(Integer.parseInt(s1) - 1) + "/12/"
					+ Integer.toString(j - 1);
		else if (i < 11)
			s = s1 + "/0" + Integer.toString(i - 1) + "/"
					+ Integer.toString(j - 1);
		else
			s = s1 + "/" + Integer.toString(i - 1) + "/"
					+ Integer.toString(j - 1);
		return s;
	}

	public static String getPreviousMonthFirstByYesterday() {
		String s = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy");
		String s1 = simpledateformat.format(calendar.getTime());
		simpledateformat = new SimpleDateFormat("MM");
		String s2 = simpledateformat.format(calendar.getTime());
		simpledateformat = new SimpleDateFormat("dd");
		String s3 = simpledateformat.format(calendar.getTime());
		int i = Integer.parseInt(s2);
		int j = Integer.parseInt(s3);
		if (j == 1) {
			if (i == 2)
				s = Integer.toString(Integer.parseInt(s1) - 1) + "/12/01";
			else if (i == 1)
				s = Integer.toString(Integer.parseInt(s1) - 1) + "/11/01";
			else if (i < 12)
				s = s1 + "/0" + Integer.toString(i - 2) + "/01";
			else
				s = s1 + "/" + Integer.toString(i - 2) + "/01";
		} else if (i == 1)
			s = Integer.toString(Integer.parseInt(s1) - 1) + "/12/01";
		else if (i < 11)
			s = s1 + "/0" + Integer.toString(i - 1) + "/01";
		else
			s = s1 + "/" + Integer.toString(i - 1) + "/01";
		return s;
	}

	public static String getPreviousYearCurrentMonthLastDay() {
		String s = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy");
		String s1 = simpledateformat.format(calendar.getTime());
		simpledateformat = new SimpleDateFormat("MM");
		String s2 = simpledateformat.format(calendar.getTime());
		int i = Integer.parseInt(s2);
		s = Integer.toString(Integer.parseInt(s1) - 1) + "/" + s2 + "/"
				+ getMonthLastDay(i);
		return s;
	}

	public static String getCurrentYearCurrentMonthLastDay() {
		String s = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy");
		String s1 = simpledateformat.format(calendar.getTime());
		simpledateformat = new SimpleDateFormat("MM");
		String s2 = simpledateformat.format(calendar.getTime());
		int i = Integer.parseInt(s2);
		s = s1 + "/" + s2 + "/" + getMonthLastDay(i);
		return s;
	}

	public static String getMonthLastDay(int i) {
		String s = "";
		if ((i == 1) || (i == 3) || (i == 5) || (i == 7) || (i == 8)
				|| (i == 10) || (i == 12))
			s = "31";
		else if (i == 2)
			s = "28";
		else
			s = "30";
		return s;
	}

	public static String[] split(String s, String s1) {
		ArrayList arraylist = new ArrayList();
		boolean flag = false;
		int j = 0;
		String s2 = s;
		for (int i = s2.indexOf(s1); i >= 0; i = s2.indexOf(s1)) {
			j++;
			if (i > 0)
				arraylist.add(s2.substring(0, i));
			else
				arraylist.add("");
			s2 = s2.substring(i + s1.length());
		}

		arraylist.add(s2);
		String[] as = new String[arraylist.size()];
		for (int k = 0; k < arraylist.size(); k++) {
			as[k] = ((String) arraylist.get(k));
			as[k] = as[k].trim();
		}

		return as;
	}

	public static String getStringFromClob(Clob clob) {
		Clob clob1 = clob;
		String s = "";
		try {
			if (clob1 != null) {
				Reader reader = clob1.getCharacterStream();
				BufferedReader bufferedreader = new BufferedReader(reader);
				StringBuffer stringbuffer = new StringBuffer();
				for (String s1 = bufferedreader.readLine(); s1 != null; s1 = bufferedreader
						.readLine()) {
					stringbuffer.append(s1);
				}
				s = stringbuffer.toString();
			}
		} catch (Exception exception) {
			log.error(exception);
		}
		return s;
	}

	public static String[] split(String s, String s1, boolean flag) {
		ArrayList arraylist = new ArrayList();
		boolean flag1 = false;
		int j = 0;
		String s2 = s;
		for (int i = s2.indexOf(s1); i >= 0; i = s2.indexOf(s1)) {
			j++;
			if (i > 0)
				arraylist.add(s2.substring(0, i));
			else
				arraylist.add("");
			s2 = s2.substring(i + s1.length());
		}

		arraylist.add(s2);
		String[] as = new String[arraylist.size()];
		for (int k = 0; k < arraylist.size(); k++) {
			as[k] = ((String) arraylist.get(k));
			if (flag) {
				as[k] = as[k].trim();
			}
		}
		return as;
	}

	public static String toGb2312(String s) {
		try {
			return new String(s.getBytes("ISO-8859-1"), "GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static void main(String[] paramArrayOfString) {
		// TODO Main
		try {
			// System.out.println(getPreviousYearCurrentMonthFirst());
			// System.out.println(Timestamp.valueOf("2003-01-01 00:00:00.0")
			// .compareTo(Timestamp.valueOf("1990-01-02 00:00:00.0")));
			// System.out.println(formatDBDate(getDate()));

			// getFileInfo("E:/temp/files/filename.txt");
			// print(getPrefix("filename.txt"));
			// print(getSuffix("filename.txt"));

			// print(ConvertCharge("1234.50"));
			String str = replace("<id='1' name='qmx'>", ">", " age='25'>", 10);
			print(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
