package com.zl.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import com.zl.util.TradeList;

public class PFValue {
	static private PFValue instance; // 唯一实例
	// 控制是否初始化成功
	private boolean alreadyInit = false;
	// 保存公共数据
	private HashMap publicvalue = new HashMap();
	// 设置数据库连接的名称
	private String datasource = "idb";

	static synchronized public PFValue getInstance() {
		if (instance == null) {
			instance = new PFValue();
		}
		return instance;
	}

	public static String toGb2312(byte b[]) {
		String retStr = "";
		try {
			retStr = new String(b, "GB2312");
			for (int i = 0; i < b.length; i++) {
				byte b1 = b[i];
				if (b1 == 0) {
					retStr = new String(b, 0, i, "GB2312");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); // To change body of catch statement use
									// File | Settings | File Templates.
		}
		return retStr;
	}

	public static void downcontent(HttpServletRequest request, JspWriter out,
			String filename) throws IOException {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(request
					.getSession().getServletContext()
					.getRealPath("//content//" + filename)));
			byte[] buff = new byte[2048];
			int bytesRead = 0;
			while ((bytesRead = bis.read(buff, 0, buff.length)) != -1) {
				out.write(new String(buff, "GBK"));
			}
		} catch (Exception e) {
			System.out.println("IOException." + e);
		} finally {
			if (bis != null)
				bis.close();
		}
		return;
	}

	public static java.sql.Date getSQLDate() {
		GregorianCalendar calendar = new GregorianCalendar();
		return new java.sql.Date(calendar.getTime().getTime());
	}

	public static String getPathname() {
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

	public void init() {
		if (this.alreadyInit == false) {
//			Executer.getInstance().setDataSouce(datasource);
			try {
				initpublicvalue();
				this.alreadyInit = true;
			} catch (Exception ex) {
				System.out.println(ex);
				this.alreadyInit = false;
			}
		}
	}

//	public static void main(String[] agrs){
//		try {
//			ArrayList list =TradeList.getPubliccodes();
//			System.out.println(list.size());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public String getPublicValue(String codes) {
		return (String) publicvalue.get(codes);
	}

	// 初始化公共数据
	private void initpublicvalue() throws Exception {
		ArrayList list = TradeList.getPubliccodes();
		String code;
		String value;
		publicvalue.clear();
		for (int i = 0; i < list.size(); i++) {

			code = (String) TradeList.getvalue(list.get(i), "codes");
			value = (String) TradeList.getvalue(list.get(i), "value");
			publicvalue.put(code, value);
		}
	}
}
