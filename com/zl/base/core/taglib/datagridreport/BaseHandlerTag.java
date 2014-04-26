package com.zl.base.core.taglib.datagridreport;

import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.util.MessageResources;

public abstract class BaseHandlerTag extends BodyTagSupport {
	protected static MessageResources messages = MessageResources
			.getMessageResources("org.apache.struts.taglib.html.LocalStrings");
	protected String accesskey = null;
	protected String tabindex = null;
	protected String onclick = null;
	private String ondblclick = null;
	private String onmouseover = null;
	private String onmouseout = null;
	private String onmousemove = null;
	private String onmousedown = null;
	private String onmouseup = null;
	private String onkeydown = null;
	private String onkeyup = null;
	private String onkeypress = null;
	private String onselect = null;
	private String onchange = null;
	private String onblur = null;
	private String onfocus = null;
	private boolean disabled = false;
	private boolean readonly = false;
	protected String style = null;
	protected String styleClass = null;
	private String styleId = null;
	protected String width = null;
	private String title = null;
	private String gridname = "";
	protected String property = null;

	public void setgridname(String paramString) {
		this.gridname = paramString;
	}

	public String getproperty() {
		return this.property;
	}

	public void setproperty(String paramString) {
		if (paramString == null)
			this.property = paramString;
		else
			this.property = paramString.toLowerCase();
	}

	public void setWidth(String paramString) {
		this.width = paramString;
	}

	public String getWidth() {
		if (this.width == null)
			return "0";
		return this.width;
	}

	public void setAccesskey(String paramString) {
		this.accesskey = paramString;
	}

	public String getAccesskey() {
		return this.accesskey;
	}

	public void setTabindex(String paramString) {
		this.tabindex = paramString;
	}

	public String getTabindex() {
		return this.tabindex;
	}

	public void setOnclick(String paramString) {
		this.onclick = paramString;
	}

	public String getOnclick() {
		return this.onclick;
	}

	public void setOndblclick(String paramString) {
		this.ondblclick = paramString;
	}

	public String getOndblclick() {
		return this.ondblclick;
	}

	public void setOnmousedown(String paramString) {
		this.onmousedown = paramString;
	}

	public String getOnmousedown() {
		return this.onmousedown;
	}

	public void setOnmouseup(String paramString) {
		this.onmouseup = paramString;
	}

	public String getOnmouseup() {
		return this.onmouseup;
	}

	public void setOnmousemove(String paramString) {
		this.onmousemove = paramString;
	}

	public String getOnmousemove() {
		return this.onmousemove;
	}

	public void setOnmouseover(String paramString) {
		this.onmouseover = paramString;
	}

	public String getOnmouseover() {
		return this.onmouseover;
	}

	public void setOnmouseout(String paramString) {
		this.onmouseout = paramString;
	}

	public String getOnmouseout() {
		return this.onmouseout;
	}

	public void setOnkeydown(String paramString) {
		this.onkeydown = paramString;
	}

	public String getOnkeydown() {
		return this.onkeydown;
	}

	public void setOnkeyup(String paramString) {
		this.onkeyup = paramString;
	}

	public String getOnkeyup() {
		return this.onkeyup;
	}

	public void setOnkeypress(String paramString) {
		this.onkeypress = paramString;
	}

	public String getOnkeypress() {
		return this.onkeypress;
	}

	public void setOnchange(String paramString) {
		this.onchange = paramString;
	}

	public String getOnchange() {
		return this.onchange;
	}

	public void setOnselect(String paramString) {
		this.onselect = paramString;
	}

	public String getOnselect() {
		return this.onselect;
	}

	public void setOnblur(String paramString) {
		this.onblur = paramString;
	}

	public String getOnblur() {
		return this.onblur;
	}

	public void setOnfocus(String paramString) {
		this.onfocus = paramString;
	}

	public String getOnfocus() {
		return this.onfocus;
	}

	public void setDisabled(boolean paramBoolean) {
		this.disabled = paramBoolean;
	}

	public boolean getDisabled() {
		return this.disabled;
	}

	public void setReadonly(boolean paramBoolean) {
		this.readonly = paramBoolean;
	}

	public boolean getReadonly() {
		return this.readonly;
	}

	public void setStyle(String paramString) {
		this.style = paramString;
	}

	public String getStyle() {
		if (this.style == null)
			return "";
		return this.style;
	}

	public void setStyleClass(String paramString) {
		this.styleClass = paramString;
	}

	public String getStyleClass() {
		return this.styleClass;
	}

	public void setStyleId(String paramString) {
		this.styleId = paramString;
	}

	public String getStyleId() {
		return this.styleId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String paramString) {
		this.title = paramString;
	}

	public void release() {
		super.release();
		this.accesskey = null;
		this.tabindex = null;
		this.onclick = null;
		this.ondblclick = null;
		this.onmouseover = null;
		this.onmouseout = null;
		this.onmousemove = null;
		this.onmousedown = null;
		this.onmouseup = null;
		this.onkeydown = null;
		this.onkeyup = null;
		this.onkeypress = null;
		this.onselect = null;
		this.onchange = null;
		this.onblur = null;
		this.onfocus = null;
		this.disabled = false;
		this.readonly = false;
		this.style = null;
		this.styleClass = null;
		this.styleId = null;
		this.title = null;
	}

	protected String prepareStyles() {
		StringBuffer localStringBuffer = new StringBuffer();
		if (this.style != null) {
			localStringBuffer.append(" style=\"");
			localStringBuffer.append(this.style);
			localStringBuffer.append("\"");
		}
		if (this.styleClass != null) {
			localStringBuffer.append(" class=\"");
			localStringBuffer.append(this.styleClass);
			localStringBuffer.append("\"");
		}
		if (this.styleId != null) {
			localStringBuffer.append(" id=\"");
			localStringBuffer.append(this.styleId);
			localStringBuffer.append("\"");
		}
		if (this.title != null) {
			localStringBuffer.append(" title=\"");
			localStringBuffer.append(this.title);
			localStringBuffer.append("\"");
		}
		return localStringBuffer.toString();
	}

	protected String prepareEventHandlers() {
		StringBuffer localStringBuffer = new StringBuffer();
		prepareMouseEvents(localStringBuffer);
		prepareKeyEvents(localStringBuffer);
		prepareTextEvents(localStringBuffer);
		prepareFocusEvents(localStringBuffer);
		return localStringBuffer.toString();
	}

	private void prepareMouseEvents(StringBuffer paramStringBuffer) {
		if (this.onclick != null) {
			paramStringBuffer.append(" onclick=\"");
			paramStringBuffer.append(this.onclick);
			paramStringBuffer.append("\"");
		}
		if (this.ondblclick != null) {
			paramStringBuffer.append(" ondblclick=\"");
			paramStringBuffer.append(this.ondblclick);
			paramStringBuffer.append("\"");
		}
		if (this.onmouseover != null) {
			paramStringBuffer.append(" onmouseover=\"");
			paramStringBuffer.append(this.onmouseover);
			paramStringBuffer.append("\"");
		}
		if (this.onmouseout != null) {
			paramStringBuffer.append(" onmouseout=\"");
			paramStringBuffer.append(this.onmouseout);
			paramStringBuffer.append("\"");
		}
		if (this.onmousedown != null) {
			paramStringBuffer.append(" onmousedown=\"");
			paramStringBuffer.append(this.onmousedown);
			paramStringBuffer.append("\"");
		}
		if (this.onmouseup != null) {
			paramStringBuffer.append(" onmouseup=\"");
			paramStringBuffer.append(this.onmouseup);
			paramStringBuffer.append("\"");
		}
	}

	private void prepareKeyEvents(StringBuffer paramStringBuffer) {
		if (this.onkeydown != null) {
			paramStringBuffer.append(" onkeydown=\"");
			paramStringBuffer.append(this.onkeydown);
			paramStringBuffer.append("\"");
		}
		if (this.onkeyup != null) {
			paramStringBuffer.append(" onkeyup=\"");
			paramStringBuffer.append(this.onkeyup);
			paramStringBuffer.append("\"");
		}
		if (this.onkeypress != null) {
			paramStringBuffer.append(" onkeypress=\"");
			paramStringBuffer.append(this.onkeypress);
			paramStringBuffer.append("\"");
		}
	}

	private void prepareTextEvents(StringBuffer paramStringBuffer) {
		if (this.onselect != null) {
			paramStringBuffer.append(" onselect=\"");
			paramStringBuffer.append(this.onselect);
			if ((this instanceof BaseText))
				paramStringBuffer.append(";" + this.gridname
						+ "clickselect(this)");
			paramStringBuffer.append("\"");
		} else if ((this instanceof BaseText)) {
			paramStringBuffer.append(" onfocus=\"");
			paramStringBuffer.append(this.gridname + "clickselect(this)");
			paramStringBuffer.append("\"");
		}
		if (this.onchange != null) {
			paramStringBuffer.append(" onchange=\"");
			paramStringBuffer.append(this.onchange);
			paramStringBuffer.append("\"");
		}
	}

	private void prepareFocusEvents(StringBuffer paramStringBuffer) {
		if (this.onblur != null) {
			paramStringBuffer.append(" onblur=\"");
			paramStringBuffer.append(this.onblur);
			paramStringBuffer.append("\"");
		}
		if (this.onfocus != null) {
			paramStringBuffer.append(" onfocus=\"");
			paramStringBuffer.append(this.onfocus);
			paramStringBuffer.append("\"");
		}
		if (this.disabled)
			paramStringBuffer.append(" disabled=\"disabled\"");
		if (this.readonly)
			paramStringBuffer.append(" readonly=\"readonly\"");
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.datagridreport.BaseHandlerTag JD-Core Version: 0.6.1
 */