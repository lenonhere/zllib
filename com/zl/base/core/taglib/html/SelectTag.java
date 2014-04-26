package com.zl.base.core.taglib.html;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

public class SelectTag extends BaseHandlerTag {
	protected String[] match = null;
	protected static MessageResources messages = MessageResources
			.getMessageResources("org.apache.struts.taglib.html.LocalStrings");
	private String matchtype = null;
	private String addspacerow = null;
	private String spacerowvalue = null;
	private String spacerowtext = "";
	protected String multiple = null;
	protected String name = "org.apache.struts.taglib.html.BEAN";
	protected String property = null;
	protected String saveBody = null;
	protected String size = null;
	protected String value = null;

	public String getmatchtype() {
		return this.matchtype;
	}

	public void setmatchtype(String paramString) {
		this.matchtype = paramString;
	}

	public String getaddspacerow() {
		if (this.addspacerow == null)
			return "false";
		return this.addspacerow;
	}

	public void setaddspacerow(String paramString) {
		this.addspacerow = paramString;
	}

	public String getspacerowvalue() {
		return this.spacerowvalue;
	}

	public void setspacerowvalue(String paramString) {
		this.spacerowvalue = paramString;
	}

	public String getspacerowtext() {
		if (this.spacerowtext == null)
			return "";
		return this.spacerowtext;
	}

	public void setspacerowtext(String paramString) {
		this.spacerowtext = paramString;
	}

	public String getMultiple() {
		return this.multiple;
	}

	public void setMultiple(String paramString) {
		this.multiple = paramString;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String paramString) {
		this.size = paramString;
	}

	public boolean isMatched(String paramString) {
		if ((this.match == null) || (paramString == null))
			return false;
		for (int i = 0; i < this.match.length; i++)
			if (paramString.equals(this.match[i]))
				return true;
		return false;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String paramString) {
		this.property = paramString;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String paramString) {
		this.value = paramString;
	}

	public int doStartTag() throws JspException {
		StringBuffer localStringBuffer = new StringBuffer("<select");
		localStringBuffer.append(" name=\"");
		localStringBuffer.append(this.property);
		localStringBuffer.append("\"");
		if (this.accesskey != null) {
			localStringBuffer.append(" accesskey=\"");
			localStringBuffer.append(this.accesskey);
			localStringBuffer.append("\"");
		}
		if (this.multiple != null)
			localStringBuffer.append(" multiple=\"multiple\"");
		if (this.size != null) {
			localStringBuffer.append(" size=\"");
			localStringBuffer.append(this.size);
			localStringBuffer.append("\"");
		}
		if (this.tabindex != null) {
			localStringBuffer.append(" tabindex=\"");
			localStringBuffer.append(this.tabindex);
			localStringBuffer.append("\"");
		}
		localStringBuffer.append(prepareEventHandlers());
		localStringBuffer.append(prepareStyles());
		localStringBuffer.append(">");
		ResponseUtils.write(this.pageContext, localStringBuffer.toString());
		this.pageContext.setAttribute("org.apache.struts.taglib.html.SELECT",
				this);
		if (this.value != null) {
			this.match = new String[1];
			this.match[0] = this.value;
		} else {
			Object localObject = this.pageContext.findAttribute(this.name);
			if (localObject == null) {
				JspException localJspException = new JspException(
						messages.getMessage("getter.bean", this.name));
				RequestUtils.saveException(this.pageContext, localJspException);
				throw localJspException;
			}
			try {
				this.match = BeanUtils.getArrayProperty(localObject,
						this.property);
				if (this.match == null)
					this.match = new String[0];
			} catch (IllegalAccessException localIllegalAccessException) {
				RequestUtils.saveException(this.pageContext,
						localIllegalAccessException);
				throw new JspException(messages.getMessage("getter.access",
						this.property, this.name));
			} catch (InvocationTargetException localInvocationTargetException) {
				Throwable localThrowable = localInvocationTargetException
						.getTargetException();
				RequestUtils.saveException(this.pageContext, localThrowable);
				throw new JspException(messages.getMessage("getter.result",
						this.property, localThrowable.toString()));
			} catch (NoSuchMethodException localNoSuchMethodException) {
				RequestUtils.saveException(this.pageContext,
						localNoSuchMethodException);
				throw new JspException(messages.getMessage("getter.method",
						this.property, this.name));
			}
		}
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
		StringBuffer localStringBuffer = new StringBuffer();
		if (this.saveBody != null)
			localStringBuffer.append(this.saveBody);
		localStringBuffer.append("</select>");
		ResponseUtils.write(this.pageContext, localStringBuffer.toString());
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
 * com.zl.base.core.taglib.html.SelectTag JD-Core Version: 0.6.1
 */