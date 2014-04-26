package com.zl.base.core.taglib.grid;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import org.apache.struts.util.ResponseUtils;

import com.zl.base.core.util.StringBufferTool;

public class BaseTitleForGrid extends BaseHandlerTag {
	private static final long serialVersionUID = -1359212784146533099L;
	private int showrowcount = -1;
	private String caption = null;
	private String width = "100%";
	private String height = "100%";
	private String styleclass = null;
	private String lcstyleclass = "mod_tl";
	private String rcstyleclass = "mod_tr";
	private String headstyleclass = "mod_head";
	private String image = null;
	private String contentstyleclass = "mod_main";
	private String type = "top";
	private String collection = null;
	private String property = null;
	private String lbstyleclass = "mod_l";
	private String rbstyleclass = "mod_r";

	public int getshowrowcount() {
		return this.showrowcount;
	}

	public void setshowrowcount(int paramInt) {
		this.showrowcount = paramInt;
	}

	public String getcaption() {
		if (this.caption == null)
			return "";
		return this.caption;
	}

	public void setcaption(String paramString) {
		this.caption = paramString;
	}

	public String getwidth() {
		return this.width;
	}

	public void setwidth(String paramString) {
		this.width = paramString;
	}

	public String getheight() {
		return this.height;
	}

	public void setheight(String paramString) {
		this.height = paramString;
	}

	public String getstyleclass() {
		return this.styleclass;
	}

	public void setstyleclass(String paramString) {
		this.styleclass = paramString;
	}

	public String getlcstyleclass() {
		return this.lcstyleclass;
	}

	public void setlcstyleclass(String paramString) {
		this.lcstyleclass = paramString;
	}

	public String getrcstyleclass() {
		return this.rcstyleclass;
	}

	public void setrcstyleclass(String paramString) {
		this.rcstyleclass = paramString;
	}

	public String getheadstyleclass() {
		return this.headstyleclass;
	}

	public void setheadstyleclass(String paramString) {
		this.headstyleclass = paramString;
	}

	public String getimage() {
		return this.image;
	}

	public void setimage(String paramString) {
		this.image = paramString;
	}

	public String getcontentstyleclass() {
		return this.contentstyleclass;
	}

	public void setcontentstyleclass(String paramString) {
		this.contentstyleclass = paramString;
	}

	public String gettype() {
		if ((!this.type.trim().toUpperCase().equals("TOP"))
				&& (!this.type.trim().toUpperCase().equals("BOTTOM")))
			return "top";
		return this.type.toLowerCase().trim();
	}

	public void settype(String paramString) {
		this.type = paramString;
	}

	public String getcollection() {
		return this.collection;
	}

	public void setcollection(String paramString) {
		this.collection = paramString;
	}

	public String getproperty() {
		return this.property;
	}

	public void setproperty(String paramString) {
		this.property = paramString;
	}

	public String getlbstyleclass() {
		return this.lbstyleclass;
	}

	public void setlbstyleclass(String paramString) {
		this.lbstyleclass = paramString;
	}

	public String getrbstyleclass() {
		return this.rbstyleclass;
	}

	public void setrbstyleclass(String paramString) {
		this.rbstyleclass = paramString;
	}

	public int doEndTag() throws JspException {
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		if (!gettype().equals("top"))
			writetitle(localStringBufferTool);
		localStringBufferTool.appendln("</div></td></tr></table>");
		ResponseUtils.write(this.pageContext, localStringBufferTool.toString());
		release();
		return 6;
	}

	public int doStartTag() throws JspException {
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		writehead(localStringBufferTool);
		if (gettype().equals("top"))
			writetitle(localStringBufferTool);
		wrietcontent(localStringBufferTool);
		ResponseUtils.write(this.pageContext, localStringBufferTool.toString());
		return 1;
	}

	public int readrow() {
		try {
			Object localObject = this.pageContext
					.findAttribute(getcollection());
			ArrayList localArrayList = (ArrayList) localObject;
			return localArrayList.size();
		} catch (Exception localException) {
		}
		return 0;
	}

	public void writehead(StringBufferTool paramStringBufferTool) {
		paramStringBufferTool
				.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"");
		if (this.styleclass != null)
			paramStringBufferTool.append(" class=\"" + this.styleclass.trim()
					+ "\"");
		paramStringBufferTool.append(" width =\"" + this.width + "\"");
		paramStringBufferTool.append(" height =\"" + this.height + "\"");
		paramStringBufferTool.appendln(">");
	}

	public void wrietcontent(StringBufferTool paramStringBufferTool) {
		paramStringBufferTool.appendln("<tr><td colspan=\"4\" class=\""
				+ getcontentstyleclass()
				+ "\" width=\"100%\" height=\"100%\" align=center valign=top>");
		paramStringBufferTool
				.appendln("<div style=\"margin:4px 4px 4px 4px;width:100%;height:98%;text-align:left\">");
	}

	public void writetitle(StringBufferTool paramStringBufferTool) {
		String str = "";
		if (this.showrowcount == -1)
			str = "记录数：" + String.valueOf(readrow()) + "条";
		else if (this.showrowcount < readrow())
			str = "记录数：前" + String.valueOf(this.showrowcount) + "条/共"
					+ String.valueOf(readrow()) + "条";
		else
			str = "记录数：" + String.valueOf(readrow()) + "条";
		if (gettype().equals("top")) {
			paramStringBufferTool.appendln(" <tr height=\"6\">");
			paramStringBufferTool
					.appendln("<td class=\""
							+ getlcstyleclass()
							+ "\" width=\"6\" ><img border=\"0\" height=\"6\" width=\"6\" alt=\"\" title=\"\" src=\""
							+ this.image + "\"/></td>");
			paramStringBufferTool
					.appendln("<td width=\"80%\" rowspan=\"2\" nowrap=\"nowrap\" class=\""
							+ getheadstyleclass()
							+ "\">"
							+ getcaption()
							+ "</td>");
			paramStringBufferTool.appendln("<td align=\"right\" class=\""
					+ getheadstyleclass()
					+ "\" rowspan=\"2\" width=\"300\" nowrap=\"nowrap\" >"
					+ str + "</td>");
			paramStringBufferTool
					.appendln("<td class=\""
							+ getrcstyleclass()
							+ "\" width=\"6\"><img border=\"0\" height=\"6\" width=\"6\" alt=\"\" title=\"\" src=\""
							+ this.image + "\" /></td>");
			paramStringBufferTool.appendln("</tr>");
			paramStringBufferTool.appendln("<tr height=\"6\"><td class=\""
					+ this.lbstyleclass + "\">&nbsp;</td><td class=\""
					+ this.rbstyleclass + "\">&nbsp;</td></tr>");
		} else {
			paramStringBufferTool.appendln(" <tr >");
			paramStringBufferTool.appendln("<td class=\"" + getlcstyleclass()
					+ "\" width=\"6\" >&nbsp;</td>");
			paramStringBufferTool
					.appendln("<td width=\"80%\" rowspan=\"2\" nowrap=\"nowrap\" class=\""
							+ getheadstyleclass()
							+ "\">"
							+ getcaption()
							+ "</td>");
			paramStringBufferTool.appendln("<td align=\"right\" class=\""
					+ getheadstyleclass() + "\" rowspan=\"2\" width=\"300\">"
					+ str + "</td>");
			paramStringBufferTool.appendln("<td class=\"" + getrcstyleclass()
					+ "\" width=\"6\">&nbsp;</td>");
			paramStringBufferTool.appendln("</tr>");
			paramStringBufferTool
					.appendln("<tr height=\"6\"><td class=\""
							+ this.lbstyleclass
							+ "\"><img border=\"0\" height=\"6\" width=\"6\" alt=\"\" title=\"\" src=\""
							+ this.image + "\"/></td>");
			paramStringBufferTool
					.appendln("<td class=\""
							+ this.rbstyleclass
							+ "\"><img border=\"0\" height=\"6\" width=\"6\" alt=\"\" title=\"\" src=\""
							+ this.image + "\" /></td></tr>");
		}
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.grid.BaseTitleForGrid JD-Core Version: 0.6.1
 */