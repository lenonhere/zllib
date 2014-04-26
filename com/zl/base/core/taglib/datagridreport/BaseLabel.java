package com.zl.base.core.taglib.datagridreport;

import javax.servlet.jsp.JspException;

public class BaseLabel extends BaseInputTag implements Cloneable {
	private String fontcolor = null;

	public void setfontcolor(String paramString) {
		this.fontcolor = paramString;
	}

	public String getfontcolor() {
		return this.fontcolor;
	}

	public void setid(String paramString) {
		this.id = paramString;
	}

	public String getid() {
		return this.id;
	}

	public void setstyle(String paramString) {
		this.style = paramString;
	}

	public String getstyle() {
		if (this.style == null)
			return "";
		return this.style;
	}

	public void setwidth(String paramString) {
		this.width = paramString;
	}

	public String getwidth() {
		if (this.width == null)
			return "0";
		return this.width;
	}

	public int doEndTag() throws JspException {
		BaseGridReport localBaseGridReport = (BaseGridReport) this.pageContext
				.getAttribute("GridReport");
		if (localBaseGridReport == null)
			throw new JspException("在建立BaseLabel时，没有发现GridReport");
		try {
			localBaseGridReport.columns.add(clone());
		} catch (Exception localException) {
			System.out.println(localException.toString());
		}
		return 6;
	}

	public void generateRows(StringBuffer paramStringBuffer, String paramString) {
		paramStringBuffer.append("<td ");
		if (getformat() != null)
			paramString = super.getFormatValue(paramString);
		if (getrowspan() != null)
			paramStringBuffer.append("rowspan=\"" + getrowspan() + "\"");
		if (getid() != null)
			paramStringBuffer.append(" id=\"selectid\" ");
		if (paramString == null) {
			paramString = "&nbsp;";
		} else {
			paramString = paramString.trim();
			if (paramString.equals(""))
				paramString = "&nbsp;";
		}
		paramStringBuffer.append(">");
		if (this.fontcolor != null) {
			paramStringBuffer.append("<font color=\"");
			paramStringBuffer.append(this.fontcolor);
			paramStringBuffer.append("\">");
			paramStringBuffer.append(paramString);
			paramStringBuffer.append("</font>");
		} else {
			paramStringBuffer.append(paramString);
		}
		paramStringBuffer.append("</td>\n");
	}

	public String generateInnerHTML() {
		return "&nbsp;";
	}

	public String generateLightOn(String paramString1, String paramString2) {
		return "";
	}

	public static void main(String[] paramArrayOfString) {
		try {
			String str1 = "3601";
			int i = 0;
			String str2 = "";
			String str3 = "00";
			String str4 = "00";
			String str5 = "00";
			str2 = str2.toLowerCase();
			i = Integer.valueOf(str1).intValue();
			str5 = String.valueOf(i % 3600 % 60);
			str4 = String.valueOf(i / 60 % 60);
			str3 = String.valueOf(i / 3600);
			if (Integer.valueOf(str3).intValue() != 0)
				str2 = str3 + "小时";
			if (Integer.valueOf(str4).intValue() != 0)
				str2 = str2 + str4 + "分";
			if (Integer.valueOf(str5).intValue() != 0)
				str2 = str2 + str5 + "秒";
			System.out.print(str2);
		} catch (Exception localException) {
		}
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.datagridreport.BaseLabel JD-Core Version: 0.6.1
 */