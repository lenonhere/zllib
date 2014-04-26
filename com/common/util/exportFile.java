package com.common.util;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.PropertyUtils;

import com.zl.util.MethodFactory;


public class exportFile {
	private static String getfilename() {
		String returnvalue = "";
		GregorianCalendar calendar = new GregorianCalendar();
		// 年
		String stryear = String.valueOf(calendar.get(Calendar.YEAR));
		// 月
		String strmonth = "00"
				+ String.valueOf(calendar.get(Calendar.MONTH) + 1);
		strmonth = strmonth.substring(strmonth.length() - 2, strmonth.length());
		// 日
		String strday = "00"
				+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		strday = strday.substring(strday.length() - 2, strday.length());
		// 时
		String strhour = "00"
				+ String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		strhour = strhour.substring(strhour.length() - 2, strhour.length());
		// 分
		String strminute = "00" + String.valueOf(calendar.get(Calendar.MINUTE));
		strminute = strminute.substring(strminute.length() - 2,
				strminute.length());
		// 秒
		String strsecond = "00" + String.valueOf(calendar.get(Calendar.SECOND));
		strsecond = strsecond.substring(strsecond.length() - 2,
				strsecond.length());
		// 毫秒
		String strmsecond = "00"
				+ String.valueOf(calendar.get(Calendar.MILLISECOND));
		strmsecond = strmsecond.substring(strmsecond.length() - 2,
				strmsecond.length());
		returnvalue = stryear + strmonth + strday + strhour + strminute
				+ strsecond + strmsecond;
		return returnvalue;
	}

	public static void exportDataToFile(ArrayList caption, ArrayList rst,
			String Title, String condition, String inputDate,
			HttpServletResponse response) {

		int intShowColCount = caption.size();
		if (intShowColCount == 0 || rst == null) {

		} else {
			OutputStream bos = null;
			try {

				response.setContentType("application/msexcel");
				response.setHeader("Content-disposition",
						"attachment; filename=" + getfilename() + ".csv");
				bos = response.getOutputStream();
				writedata(caption, rst, Title, condition, inputDate, bos,
						intShowColCount);
			} catch (Exception ex) {
			} finally {
				try {
					bos.close();
				} catch (Exception ex) {
				}
			}
		}
		return;
	}

	private static void deleteDir(String DirName) {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			// 年
			String stryear = String.valueOf(calendar.get(Calendar.YEAR));
			// 月
			String strmonth = "00"
					+ String.valueOf(calendar.get(Calendar.MONTH) + 1);
			strmonth = strmonth.substring(strmonth.length() - 2,
					strmonth.length());
			// 日
			String strday = "00"
					+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			strday = strday.substring(strday.length() - 2, strday.length());
			String strdatetoday = stryear + strmonth + strday;
			int intdatetoday = Integer.parseInt(strdatetoday);
			File file = new File(DirName);
			if (file.exists()) {
				File[] fe = file.listFiles();
				for (int i = 0; i < fe.length; i++) {
					String filename = fe[i].getName();
					if (filename.length() == 20) {
						String datetemp = filename.substring(0, 8);
						String type = filename.substring(17, 20);
						if (type.equals("xls")
								&& Integer.parseInt(datetemp) < intdatetoday) {
							fe[i].delete(); // 删除已经是空的子目录
						}
					}
				}
			}
		} catch (Exception ex) {

		}

	}

	private static String replaceStringXml(String value) {
		try {
			value = MethodFactory.replace(value, "&", "&amp;");
			value = MethodFactory.replace(value, "<", "&lt;");
			value = MethodFactory.replace(value, ">", "&gt;");
		} catch (Exception ex) {
		}
		return value;
	}

	private static void writedata(ArrayList caption, ArrayList rst,
			String Title, String condition, String inputDate, OutputStream bos,
			int colcount) throws Exception {
		try {
			int i = 0;
			String strCaption = "";
			int capWidth = 0;

			for (i = 0; i < caption.size(); i++) {
				Object rows = caption.get(i);
				try {
					strCaption = MethodFactory.getThisString(PropertyUtils
							.getProperty(rows, "caption"));
					capWidth = ((Integer) PropertyUtils.getProperty(rows,
							"width")).intValue();
					strCaption = replaceStringXml(strCaption);
				} catch (Exception ex) {
					strCaption = "";
				}
				if (capWidth != 0) {
					bos.write(strCaption.getBytes());
					if (i < caption.size() - 1)
						bos.write(',');
				}
			}
			bos.write('\n');

			String strvalue = "";
			String strProperty = "";
			int strWidth = 0;

			for (int j = 0; j < rst.size(); j++) {
				for (i = 0; i < caption.size(); i++) {
					Object rows = caption.get(i);
					try {
						strProperty = MethodFactory.getThisString(PropertyUtils
								.getProperty(rows, "property"));
						strWidth = ((Integer) PropertyUtils.getProperty(rows,
								"width")).intValue();
					} catch (Exception ex) {
						strProperty = "";
					}
					try {
						System.out.println(strvalue);
						strvalue = MethodFactory.getThisString(PropertyUtils
								.getProperty(rst.get(j), strProperty.trim()
										.toLowerCase()));
						strvalue = replaceStringXml(strvalue);
					} catch (Exception Ex) {
						System.out.println(strProperty + "::" + Ex.toString());
						strvalue = "";
					}
					if (strWidth != 0) {
						strvalue = MethodFactory.replace(strvalue, ",", "，");
						bos.write(strvalue.getBytes());

						if (i < caption.size() - 1)
							bos.write(',');
					}
				}
				bos.write('\n');
			}
			bos.write('\n');
		} catch (Exception ex) {
			throw ex;
		}
	}

	private static int getShowColCount(ArrayList caption) {
		int returnValue = 0;
		int i = 0;
		String strWidth = "";
		String strisexport = "";
		if (caption == null) {
			returnValue = 0;
		} else {
			for (i = 0; i < caption.size(); i++) {
				BasicDynaBean rows = (BasicDynaBean) caption.get(i);
				try {
					strWidth = MethodFactory.getThisString(PropertyUtils
							.getProperty(rows, "width"));
				} catch (Exception ex) {
					strWidth = "0";
				}
				try {
					strisexport = MethodFactory.getThisString(PropertyUtils
							.getProperty(rows, "isexport"));
				} catch (Exception ex) {
					strisexport = "0";
				}
				if (!(strWidth.trim().equals("0") || strisexport.trim().equals(
						"0"))) {
					returnValue = returnValue + 1;
				}
			}
		}
		System.out.println(returnValue);
		return returnValue;
	}

	public static void main(String[] args) {
	}
}
