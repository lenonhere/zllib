package com.zl.base.core.taglib.html;

import java.lang.reflect.InvocationTargetException;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.taglib.html.BaseInputTag;
import org.apache.struts.util.ResponseUtils;

public class DateTag extends BaseInputTag {

	private static final long serialVersionUID = 1L;
	private boolean disabled = false;
	private String splitstring = "/";
	private int showstyle = 0;
	protected String name = "org.apache.struts.taglib.html.BEAN";

	public boolean getdisabled() {
		return this.disabled;
	}

	public void setdisabled(boolean paramBoolean) {
		this.disabled = paramBoolean;
	}

	public String getsplitstring() {
		return this.splitstring;
	}

	public void setsplitstring(String paramString) {
		if (paramString == null)
			this.splitstring = "/";
		else
			this.splitstring = paramString;
	}

	public int getshowstyle() {
		return this.showstyle;
	}

	public void setshowstyle(int paramInt) {
		this.showstyle = paramInt;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see org.apache.struts.taglib.html.BaseInputTag#doStartTag()
	 */
	public int doStartTag() throws JspException {
		// TODO doStartTag

		String date = "";
		StringBuffer strBuffer = new StringBuffer();
		Object obj = this.pageContext.findAttribute(this.name);
		if (obj == null)
			throw new JspException(
					messages.getMessage("getter.bean", this.name));
		try {
			String str = BeanUtils.getProperty(obj, this.property);
			if (str == null || "".equals(str.trim())) {
				str = "1990" + this.splitstring + "01" + this.splitstring
						+ "01";
			}
			date = str;
		} catch (IllegalAccessException iae) {
			throw new JspException(messages.getMessage("getter.access",
					this.property, this.name));
		} catch (InvocationTargetException ite) {
			throw new JspException(messages.getMessage("getter.result",
					this.property, ite.getTargetException().toString()));
		} catch (NoSuchMethodException nsme) {
			throw new JspException(messages.getMessage("getter.method",
					this.property, this.name));
		}
		generateJavaScript(strBuffer, date);
		ResponseUtils.write(this.pageContext, strBuffer.toString());
		return 2;
	}

	public void release() {
		super.release();
		this.name = "org.apache.struts.taglib.html.BEAN";
	}

	public void generateJavaScript(StringBuffer paramStringBuffer,
			String paramString) {
		String str1 = "1990";
		String str2 = "01";
		String str3 = "01";
		int i = 0;
		try {
			i = Integer.parseInt(paramString);
		} catch (Exception localException) {
		}
		char c = '-';
		if (this.splitstring.length() > 0)
			c = this.splitstring.charAt(0);
		int j = paramString.trim().length();
		if (j > 10)
			j = 10;
		if ((j == 8) && (i != 0)) {
			str1 = paramString.substring(0, 4);
			str2 = paramString.substring(4, 6);
			str3 = paramString.substring(6, 8);
		} else {
			paramString = paramString.replace('-', '/');
			StringTokenizer localStringTokenizer = new StringTokenizer(
					paramString.replace(c, '/').substring(0, j), "/");
			if (localStringTokenizer.hasMoreElements())
				str1 = localStringTokenizer.nextToken();
			else
				str1 = "1990";
			if (localStringTokenizer.hasMoreElements())
				str2 = localStringTokenizer.nextToken();
			else
				str2 = "01";
			if (localStringTokenizer.hasMoreElements())
				str3 = localStringTokenizer.nextToken();
			else
				str3 = "01";
		}
		paramStringBuffer.append("<script language=JavaScript>");
		if (this.showstyle == 0) {
			paramStringBuffer.append("var dt" + this.property
					+ "=createDatePicker(\"");
			paramStringBuffer.append("\"");
			paramStringBuffer.append(",");
			paramStringBuffer.append(str1);
			paramStringBuffer.append(",");
			paramStringBuffer.append(str2);
			paramStringBuffer.append(",");
			paramStringBuffer.append(str3);
			paramStringBuffer.append(",\"");
			paramStringBuffer.append(this.splitstring);
			paramStringBuffer.append("\")\n");
			if (this.disabled)
				paramStringBuffer.append("dt" + this.property
						+ ".setEnabled(false);");
		} else {
			paramStringBuffer.append("var dtlist" + this.property
					+ "=createDatelist(\"");
			paramStringBuffer.append(this.property);
			paramStringBuffer.append("\"");
			paramStringBuffer.append(",");
			paramStringBuffer.append(str1);
			paramStringBuffer.append(",");
			paramStringBuffer.append(str2);
			paramStringBuffer.append(",");
			paramStringBuffer.append(str3);
			paramStringBuffer.append(",\"");
			paramStringBuffer.append(this.splitstring);
			paramStringBuffer.append("\",");
			if (this.disabled == true)
				paramStringBuffer.append("true");
			else
				paramStringBuffer.append("false");
			paramStringBuffer.append(")\n");
		}
		paramStringBuffer.append("</script>");
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.date.DateTag JD-Core Version: 0.6.1
 */