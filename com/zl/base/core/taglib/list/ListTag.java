package com.zl.base.core.taglib.list;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;

public class ListTag extends BaseHandlerTag {
	protected static MessageResources messages = MessageResources
			.getMessageResources("org.apache.struts.taglib.html.LocalStrings");
	protected String name = "org.apache.struts.taglib.html.BEAN";
	protected String multiple = null;
	protected String property = null;
	protected String saveBody = null;
	protected String size = null;
	protected String value = null;
	protected String[] match = null;

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param paramString
	 * @return
	 */
	public boolean isMatched(String paramMatch) {
		if ((this.match == null) || (paramMatch == null))
			return false;
		for (int i = 0; i < this.match.length; i++)
			if (paramMatch.equals(this.match[i]))
				return true;
		return false;
	}

	public int doStartTag() throws JspException {
		StringBuffer strBuffer = new StringBuffer("<select");
		strBuffer.append(" name=\"");
		strBuffer.append(this.property);
		strBuffer.append("\"");
		if (this.accesskey != null) {
			strBuffer.append(" accesskey=\"");
			strBuffer.append(this.accesskey);
			strBuffer.append("\"");
		}
		if (this.multiple != null)
			strBuffer.append(" multiple=\"multiple\"");
		if (this.size != null) {
			strBuffer.append(" size=\"");
			strBuffer.append(this.size);
			strBuffer.append("\"");
		}
		if (this.tabindex != null) {
			strBuffer.append(" tabindex=\"");
			strBuffer.append(this.tabindex);
			strBuffer.append("\"");
		}
		strBuffer.append(prepareEventHandlers());
		strBuffer.append(prepareStyles());
		strBuffer.append(">");
		ResponseUtils.write(this.pageContext, strBuffer.toString());
		this.pageContext.setAttribute("org.apache.struts.taglib.html.SELECT",
				this);
		return 2;
	}

	public int doAfterBody() throws JspException {
		if (this.bodyContent != null) {
			String str = this.bodyContent.getString();
			if (str == null)
				str = "";
			this.saveBody = str.trim();
		}
		return 0;
	}

	public int doEndTag() throws JspException {
		this.pageContext
				.removeAttribute("org.apache.struts.taglib.html.SELECT");
		StringBuffer strBuffer = new StringBuffer();
		if (this.saveBody != null)
			strBuffer.append(this.saveBody);
		strBuffer.append("</select>");
		ResponseUtils.write(this.pageContext, strBuffer.toString());
		return 6;
	}

	public void release() {
		super.release();
		this.match = null;
		this.multiple = null;
		this.name = "org.apache.struts.taglib.html.BEAN";
		this.property = null;
		this.saveBody = null;
		this.size = null;
		this.value = null;
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.list.ListTag JD-Core Version: 0.6.1
 */