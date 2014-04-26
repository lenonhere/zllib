package com.zl.base.core.taglib.html;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

public class CheckboxTag extends BaseHandlerTag {

	private static final long serialVersionUID = 3061169686005400816L;
	protected static MessageResources messages = MessageResources
			.getMessageResources("org.apache.struts.taglib.html.LocalStrings");
	protected String name = "org.apache.struts.taglib.html.BEAN";
	private String label = null;
	protected String property = null;
	protected String text = null;
	protected String value = null;

	public String getName() {
		return this.name;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public String getlabel() {
		return this.label;
	}

	public void setlabel(String paramString) {
		this.label = paramString;
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

	@SuppressWarnings("deprecation")
	public int doStartTag() throws JspException {
		StringBuffer localStringBuffer = new StringBuffer(
				"<input type=\"checkbox\"");
		if (this.label != null) {
			localStringBuffer.append(" id=\"");
			localStringBuffer.append(this.property);
			localStringBuffer.append("\"");
		}
		localStringBuffer.append(" name=\"");
		localStringBuffer.append(this.property);
		localStringBuffer.append("\"");
		if (this.accesskey != null) {
			localStringBuffer.append(" accesskey=\"");
			localStringBuffer.append(this.accesskey);
			localStringBuffer.append("\"");
		}
		if (this.tabindex != null) {
			localStringBuffer.append(" tabindex=\"");
			localStringBuffer.append(this.tabindex);
			localStringBuffer.append("\"");
		}
		Object localObject = RequestUtils.lookup(this.pageContext, this.name,
				this.property, null);
		if (localObject == null)
			localObject = "";
		if (!(localObject instanceof String))
			localObject = localObject.toString();
		String str = (String) localObject;
		if ((str.trim().equalsIgnoreCase("true"))
				|| (str.equalsIgnoreCase("yes"))
				|| (str.equalsIgnoreCase("on")) || (str.equalsIgnoreCase("1")))
			localStringBuffer.append(" checked=\"checked\"");
		localStringBuffer.append(" value=\"");
		if (str.equalsIgnoreCase("0"))
			localStringBuffer.append("0");
		else
			localStringBuffer.append("1");
		localStringBuffer.append("\"");
		localStringBuffer.append(prepareEventHandlers());
		localStringBuffer.append(prepareStyles());
		localStringBuffer.append(">");
		if (this.label != null)
			localStringBuffer.append("<label for=\"" + this.property + "\">"
					+ this.label + "</label>");
		ResponseUtils.write(this.pageContext, localStringBuffer.toString());
		this.text = null;
		return 2;
	}

	public int doAfterBody() throws JspException {
		if (this.bodyContent != null) {
			String str = this.bodyContent.getString().trim();
			if (str.length() > 0)
				this.text = str;
		}
		return 0;
	}

	@SuppressWarnings("deprecation")
	public int doEndTag() throws JspException {
		if (this.text != null)
			ResponseUtils.write(this.pageContext, this.text);
		return 6;
	}

	// private void generateCheckBoxOnclick(StringBuffer paramStringBuffer) {
	// StringBufferTool localStringBufferTool = new StringBufferTool(
	// paramStringBuffer);
	// localStringBufferTool.appendln("<SCRIPT LANGUAGE=javascript>");
	// localStringBufferTool.append("function checkbox_onclick(obj){");
	// localStringBufferTool.append("");
	// localStringBufferTool.append("\tif( parseInt(obj.value) == 0){");
	// localStringBufferTool.append("\t\tobj.value = 1;");
	// localStringBufferTool.append("\t\t}");
	// localStringBufferTool.append("\telse{");
	// localStringBufferTool.append("\t\tobj.value = 0;");
	// localStringBufferTool.append("\t}");
	// localStringBufferTool.append("}");
	// localStringBufferTool.appendln("</script>");
	// }

	public void release() {
		super.release();
		this.name = "org.apache.struts.taglib.html.BEAN";
		this.property = null;
		this.text = null;
		this.value = null;
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.html.CheckboxTag JD-Core Version: 0.6.1
 */