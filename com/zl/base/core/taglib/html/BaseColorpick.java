package com.zl.base.core.taglib.html;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.taglib.html.BaseInputTag;
import org.apache.struts.util.ResponseUtils;

public class BaseColorpick extends BaseInputTag {

	private static final long serialVersionUID = -7279341147084239692L;
	private String property = null;
	private String width = "50";
	private String height = null;
	protected String name = "org.apache.struts.taglib.html.BEAN";

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		if (width != null && !"".equals(width)) {
			this.width = width;
		}
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@SuppressWarnings("deprecation")
	public int doStartTag() throws JspException {
		StringBuffer strBuffer = new StringBuffer();

		Object formBean = this.pageContext.findAttribute(this.name);
		if (formBean == null)
			throw new JspException(
					messages.getMessage("getter.bean", this.name));
		try {
			String str = BeanUtils.getProperty(formBean, this.property);
			if (str == null || "".equals(str.trim())) {
				str = "#FFFFFF";
			}
			// JavaScript文件中定义的 createcolorpick 方法未找到
			strBuffer.append("<script language =\"javascript\">");
			strBuffer.append("var " + this.property + "=");
			strBuffer.append("createcolorpick('" + this.property + "','" + str
					+ "','" + this.width + "')");
			strBuffer.append("</script>");

		} catch (IllegalAccessException iae) {
			throw new JspException(messages.getMessage("getter.access",
					this.property, this.name));
		} catch (InvocationTargetException ite) {
			Throwable localThrowable = ite.getTargetException();
			throw new JspException(messages.getMessage("getter.result",
					this.property, localThrowable.toString()));
		} catch (NoSuchMethodException nsme) {
			throw new JspException(messages.getMessage("getter.method",
					this.property, this.name));
		}
		ResponseUtils.write(this.pageContext, strBuffer.toString());
		return 2;
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.colorpick.BaseColorpick JD-Core Version: 0.6.1
 */