package com.zl.base.core.taglib.datagridreport;

import javax.servlet.jsp.JspException;

import com.zl.base.core.util.StringBufferTool;

public class BaseCheck extends BaseInputTag {
	private String id = null;
	protected String checked = "";

	public BaseCheck() {
		this.type = "checkbox";
	}

	public String getid() {
		return this.id;
	}

	public void setid(String paramString) {
		this.id = paramString;
	}

	public String getChecked() {
		return this.checked;
	}

	public void setChecked(String paramString) {
		this.checked = paramString;
	}

	public int doStartTag() throws JspException {
		this.type = "checkbox";
		return 0;
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
		paramStringBuffer.append("<td align='center' valign ='middle'>");
		paramStringBuffer.append("<input type=\"");
		paramStringBuffer.append(this.type);
		paramStringBuffer.append("\" ");
		if (this.id != null) {
			paramStringBuffer.append(" id=\"");
			paramStringBuffer.append(this.id);
			paramStringBuffer.append("\"");
		}
		if (paramString == null)
			paramString = "0";
		if (paramString.equals(""))
			paramString = "0";
		paramStringBuffer.append(" value=\"");
		paramStringBuffer.append(paramString);
		paramStringBuffer.append("\"");
		if (paramString == null)
			paramString = "";
		if ((paramString.trim().equalsIgnoreCase("true"))
				|| (paramString.equalsIgnoreCase("yes"))
				|| (paramString.equalsIgnoreCase("on"))
				|| (paramString.equalsIgnoreCase("1")))
			paramStringBuffer.append(" checked=\"checked\"");
		paramStringBuffer.append(prepareEventHandlers());
		paramStringBuffer.append(prepareStyles());
		paramStringBuffer.append(">");
		paramStringBuffer.append("</td>");
	}

	public String generateInnerHTML() {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<input type='");
		localStringBuffer.append(this.type);
		localStringBuffer.append("' ");
		if (this.id != null) {
			localStringBuffer.append(" id='");
			localStringBuffer.append(this.id);
			localStringBuffer.append("'");
		}
		if (this.accesskey != null) {
			localStringBuffer.append(" accesskey='");
			localStringBuffer.append(this.accesskey);
			localStringBuffer.append("'");
		}
		if (this.maxlength != null) {
			localStringBuffer.append(" maxlength='");
			localStringBuffer.append(this.maxlength);
			localStringBuffer.append("'");
		}
		if (this.cols != null) {
			localStringBuffer.append(" size='");
			localStringBuffer.append(this.cols);
			localStringBuffer.append("'");
		}
		if (this.tabindex != null) {
			localStringBuffer.append(" tabindex='");
			localStringBuffer.append(this.tabindex);
			localStringBuffer.append("'");
		}
		if (this.checked.trim().equalsIgnoreCase("true")) {
			localStringBuffer.append(" checked='");
			localStringBuffer.append(this.checked);
			localStringBuffer.append("'");
		}
		if (this.checked.trim().equalsIgnoreCase("true")) {
			localStringBuffer.append(" value='");
			localStringBuffer.append(1);
			localStringBuffer.append("'");
		} else {
			localStringBuffer.append(" value='");
			localStringBuffer.append(0);
			localStringBuffer.append("'");
		}
		localStringBuffer.append(prepareEventHandlers());
		localStringBuffer.append(prepareStyles());
		localStringBuffer.append(">");
		return localStringBuffer.toString();
	}

	public String generateLightOn(String paramString1, String paramString2) {
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		localStringBufferTool.append("item=document.all(\"");
		localStringBufferTool.append(this.property);
		localStringBufferTool.append("\")");
		localStringBufferTool.append("[");
		localStringBufferTool.append(paramString2);
		localStringBufferTool.appendln("]");
		localStringBufferTool.appendln("item = eval(item)");
		localStringBufferTool.append("item.className = \"");
		localStringBufferTool.append(paramString1);
		localStringBufferTool.appendln("\"");
		return localStringBufferTool.toString();
	}

	public void release() {
		super.release();
		this.capital = null;
		this.property = null;
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.datagridreport.BaseCheck JD-Core Version: 0.6.1
 */