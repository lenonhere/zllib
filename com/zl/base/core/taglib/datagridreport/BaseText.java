package com.zl.base.core.taglib.datagridreport;

import javax.servlet.jsp.JspException;

public class BaseText extends BaseInputTag {
	private String fontcolor = null;
	private String value = null;

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

	public int doEndTag() throws JspException {
		BaseGridReport localBaseGridReport = (BaseGridReport) this.pageContext
				.getAttribute("GridReport");
		if (localBaseGridReport == null)
			throw new JspException("在建立BaseText时，没有发现GridReport");
		try {
			localBaseGridReport.columns.add(clone());
		} catch (Exception localException) {
			System.out.println(localException.toString());
		}
		return 6;
	}

	public void generateRows(StringBuffer paramStringBuffer, String paramString) {
		paramStringBuffer.append("<td>");
		if (getformat() != null)
			paramString = super.getFormatValue(paramString);
		paramStringBuffer.append("<input type=\"TEXT\" ");
		if (getid() != null) {
			paramStringBuffer.append(" id=\"selectid\" ");
			paramStringBuffer.append(" name=\"selectid\" ");
		}
		paramStringBuffer.append(" class=\"input_report\"");
		if (paramString == null)
			paramString = "";
		else
			paramString = paramString.trim();
		paramStringBuffer.append(" value =\"" + paramString + "\" ");
		paramStringBuffer.append(prepareEventHandlers());
		if (this.maxlength != null) {
			paramStringBuffer.append(" maxlength=\"");
			paramStringBuffer.append(this.maxlength);
			paramStringBuffer.append("\"");
		}
		if (getalign() != null)
			paramStringBuffer.append("style=\"text-align: right\"");
		paramStringBuffer.append(prepareStyles());
		paramStringBuffer.append(">");
		paramStringBuffer.append("</td>\n");
	}

	public String generateInnerHTML() {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<input type=\\\"TEXT\\\" ");
		if (getid() != null) {
			localStringBuffer.append(" id=\"selectid\" ");
			localStringBuffer.append(" name=\"selectid\" ");
		}
		localStringBuffer.append(" class=\\\"input_report\\\"");
		localStringBuffer.append(" value =''");
		localStringBuffer.append(">");
		return localStringBuffer.toString();
	}

	public String generateLightOn(String paramString1, String paramString2) {
		return "";
	}

	public static void main(String[] paramArrayOfString) {
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.datagridreport.BaseText JD-Core Version: 0.6.1
 */