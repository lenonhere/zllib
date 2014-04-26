package com.zl.base.core.taglib.list;

import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.struts.taglib.html.SelectTag;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

public class OptionTag extends BodyTagSupport {
	protected static final Locale defaultLocale = Locale.getDefault();
	protected static MessageResources messages = MessageResources
			.getMessageResources("org.apache.struts.taglib.html.LocalStrings");
	protected String text = null;
	protected String bundle = "org.apache.struts.action.MESSAGE";
	protected boolean disabled = false;
	protected String key = null;
	protected String locale = "org.apache.struts.action.LOCALE";
	private String style = null;
	private String styleClass = null;
	protected String value = null;
	protected String value1;
	protected String value2;

	public String getBundle() {
		return this.bundle;
	}

	public void setBundle(String paramString) {
		this.bundle = paramString;
	}

	public boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(boolean paramBoolean) {
		this.disabled = paramBoolean;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String paramString) {
		this.key = paramString;
	}

	public String getLocale() {
		return this.locale;
	}

	public void setLocale(String paramString) {
		this.locale = paramString;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String paramString) {
		this.style = paramString;
	}

	public String getStyleClass() {
		return this.styleClass;
	}

	public void setStyleClass(String paramString) {
		this.styleClass = paramString;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String paramString) {
		this.value = paramString;
	}

	public String getValue1() {
		return this.value1;
	}

	public void setValue1(String paramString) {
		this.value1 = paramString;
	}

	public String getValue2() {
		return this.value2;
	}

	public void setValue2(String paramString) {
		this.value2 = paramString;
	}

	public int doStartTag() throws JspException {
		this.text = null;
		return 2;
	}

	public int doAfterBody() throws JspException {
		String str = this.bodyContent.getString();
		if (str != null) {
			str = str.trim();
			if (str.length() > 0)
				this.text = str;
		}
		return 0;
	}

	public int doEndTag() throws JspException {
		SelectTag selecttag = (SelectTag) pageContext
				.getAttribute("org.apache.struts.taglib.html.SELECT");
		if (selecttag == null) {
			JspException jspexception = new JspException(
					messages.getMessage("optionTag.select"));
			RequestUtils.saveException(pageContext, jspexception);
			throw jspexception;
		}
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append("<option value=\"");
		stringbuffer.append(value);
		stringbuffer.append("\"");
		if (disabled)
			stringbuffer.append(" disabled=\"disabled\"");
		if (selecttag.isMatched(value))
			stringbuffer.append(" selected=\"selected\"");
		if (style != null) {
			stringbuffer.append(" style=\"");
			stringbuffer.append(style);
			stringbuffer.append("\"");
		}
		if (value1 != null) {
			stringbuffer.append(" value1=\"");
			stringbuffer.append(value1);
			stringbuffer.append("\"");
		}
		if (value2 != null) {
			stringbuffer.append(" value2=\"");
			stringbuffer.append(value2);
			stringbuffer.append("\"");
		}
		if (styleClass != null) {
			stringbuffer.append(" class=\"");
			stringbuffer.append(styleClass);
			stringbuffer.append("\"");
		}
		stringbuffer.append(">");
		String s = text();
		if (s == null)
			stringbuffer.append(value);
		else
			stringbuffer.append(s);
		stringbuffer.append("</option>");
		ResponseUtils.write(pageContext, stringbuffer.toString());
		return 6;
	}

	// public int doEndTag()
	// throws JspException
	// {
	// SelectTag localSelectTag =
	// (SelectTag)this.pageContext.getAttribute("org.apache.struts.taglib.html.SELECT");
	// if (localSelectTag == null)
	// {
	// localObject = new JspException(messages.getMessage("optionTag.select"));
	// RequestUtils.saveException(this.pageContext, (Throwable)localObject);
	// throw ((Throwable)localObject);
	// }
	// Object localObject = new StringBuffer();
	// ((StringBuffer)localObject).append("<option value=\"");
	// ((StringBuffer)localObject).append(this.value);
	// ((StringBuffer)localObject).append("\"");
	// if (this.disabled)
	// ((StringBuffer)localObject).append(" disabled=\"disabled\"");
	// if (localSelectTag.isMatched(this.value))
	// ((StringBuffer)localObject).append(" selected=\"selected\"");
	// if (this.style != null)
	// {
	// ((StringBuffer)localObject).append(" style=\"");
	// ((StringBuffer)localObject).append(this.style);
	// ((StringBuffer)localObject).append("\"");
	// }
	// if (this.value1 != null)
	// {
	// ((StringBuffer)localObject).append(" value1=\"");
	// ((StringBuffer)localObject).append(this.value1);
	// ((StringBuffer)localObject).append("\"");
	// }
	// if (this.value2 != null)
	// {
	// ((StringBuffer)localObject).append(" value2=\"");
	// ((StringBuffer)localObject).append(this.value2);
	// ((StringBuffer)localObject).append("\"");
	// }
	// if (this.styleClass != null)
	// {
	// ((StringBuffer)localObject).append(" class=\"");
	// ((StringBuffer)localObject).append(this.styleClass);
	// ((StringBuffer)localObject).append("\"");
	// }
	// ((StringBuffer)localObject).append(">");
	// String str = text();
	// if (str == null)
	// ((StringBuffer)localObject).append(this.value);
	// else
	// ((StringBuffer)localObject).append(str);
	// ((StringBuffer)localObject).append("</option>");
	// ResponseUtils.write(this.pageContext,
	// ((StringBuffer)localObject).toString());
	// return 6;
	// }

	public void release() {
		super.release();
		this.bundle = "org.apache.struts.action.MESSAGE";
		this.disabled = false;
		this.key = null;
		this.locale = "org.apache.struts.action.LOCALE";
		this.style = null;
		this.styleClass = null;
		this.text = null;
		this.value = null;
	}

	protected String text() throws JspException {
		if (this.text != null)
			return this.text;
		return RequestUtils.message(this.pageContext, this.bundle, this.locale,
				this.key);
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.list.OptionTag JD-Core Version: 0.6.1
 */