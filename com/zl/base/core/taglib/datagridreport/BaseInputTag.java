package com.zl.base.core.taglib.datagridreport;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;

import org.apache.struts.util.MessageResources;

import com.zl.base.core.util.StringBufferTool;

public abstract class BaseInputTag extends BaseHandlerTag implements Cloneable {
	protected String cols = null;
	protected String maxlength = null;
	protected static MessageResources messages = MessageResources
			.getMessageResources("org.apache.struts.taglib.html.LocalStrings");
	private String rowspan = null;
	private String fontcolor = null;
	private String format = null;
	protected String rows = null;
	protected String value = null;
	protected String capital = null;
	protected String type = null;
	private String align = null;
	private String caption = null;
	private boolean fixcol = false;
	private String datatype = null;
	public String isReturn = "";

	public String getrowspan() {
		return this.rowspan;
	}

	public void setrowspan(String paramString) {
		this.rowspan = paramString;
	}

	public String getfontcolor() {
		return this.fontcolor;
	}

	public void setfontcolor(String paramString) {
		this.fontcolor = paramString;
	}

	public String getformat() {
		return this.format;
	}

	public void setformat(String paramString) {
		this.format = paramString;
	}

	public String getCapital() {
		return this.capital;
	}

	public void setCapital(String paramString) {
		this.capital = paramString;
		this.caption = paramString;
	}

	public String getalign() {
		return this.align;
	}

	public void setalign(String paramString) {
		this.align = paramString;
	}

	public String getcaption() {
		if (this.caption == null)
			return this.capital;
		return this.caption;
	}

	public void setcaption(String paramString) {
		this.capital = this.capital;
		this.caption = paramString;
	}

	public boolean getfixcol() {
		return this.fixcol;
	}

	public void setfixcol(boolean paramBoolean) {
		this.fixcol = paramBoolean;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String paramString) {
		this.type = paramString;
	}

	public String getdatatype() {
		return this.datatype;
	}

	public void setdatatype(String paramString) {
		this.datatype = paramString;
	}

	public int getDataTypeToInt() {
		if (this.datatype == null)
			return 0;
		String str = this.datatype.toUpperCase();
		if ((str.equals("STRING")) || (this.datatype.equals("0")))
			return 0;
		if ((str.equals("NUMERIC")) || (this.datatype.equals("1")))
			return 1;
		if ((str.equals("DATE")) || (this.datatype.equals("2")))
			return 2;
		if (this.datatype.equals("3"))
			return 3;
		return 0;
	}

	public String getCols() {
		return this.cols;
	}

	public void setCols(String paramString) {
		this.cols = paramString;
	}

	public String getMaxlength() {
		return this.maxlength;
	}

	public void setMaxlength(String paramString) {
		this.maxlength = paramString;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String paramString) {
		this.property = paramString;
	}

	public String getRows() {
		return this.rows;
	}

	public void setRows(String paramString) {
		this.rows = paramString;
	}

	public String getSize() {
		return getCols();
	}

	public void setSize(String paramString) {
		setCols(paramString);
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String paramString) {
		this.value = paramString;
	}

	public String getIsReturn() {
		return this.isReturn;
	}

	public void setIsReturn(String paramString) {
		this.isReturn = paramString;
	}

	public int doStartTag() throws JspException {
		return 2;
	}

	public int doEndTag() throws JspException {
		return 6;
	}

	public void release() {
		super.release();
		this.cols = null;
		this.maxlength = null;
		this.property = null;
		this.rows = null;
		this.value = null;
	}

	/**
	 * 生成页脚
	 * 
	 * @param paramStringBuffer
	 * @param paramString
	 */
	public void genetateFoot(StringBuffer paramStringBuffer, String paramString) {
		if (this.type == null)
			this.type = "";
		if (this.type.equals("hidden")) {
			paramStringBuffer.append("<th>");
			paramStringBuffer.append("</th>");
		} else {
			paramStringBuffer.append("<!-- 生成Foot Begin -->\n");
			paramStringBuffer.append("<th ");
			this.styleClass = "tableData";
			paramStringBuffer.append(prepareStyles());
			paramStringBuffer.append(">");
			paramStringBuffer.append("<input name ='total_" + this.property
					+ "' style=\"");
			paramStringBuffer.append(this.style);
			paramStringBuffer.append("\" value=\"");
			paramStringBuffer.append(paramString);
			paramStringBuffer.append("\" class=tableData");
			paramStringBuffer.append(" readonly=\"true\"");
			paramStringBuffer.append(" tabindex=\"-1\"");
			paramStringBuffer.append(" />");
			paramStringBuffer.append("</th>");
			paramStringBuffer.append("<!-- 生成Foot End -->\n");
		}
		paramStringBuffer.append("<input type=hidden name=\"");
		paramStringBuffer.append(this.property);
		paramStringBuffer.append("\" />");
	}

	public void generateRows(StringBuffer paramStringBuffer, String paramString) {
	}

	public void generateAddLine(StringBuffer paramStringBuffer,
			String paramString) {
	}

	public void generateDeleteLine(StringBuffer paramStringBuffer,
			String paramString) {
	}

	protected String getFormatValue(String paramString) {
		if (paramString == null)
			paramString = "";
		String str = paramString;
		if (getdatatype() == null)
			setdatatype("");
		if (getformat() == null)
			setformat("");
		Object localObject;
		if (getdatatype().toLowerCase().equals("date")) {
			if (!getformat().trim().equals(""))
				try {
					if (paramString.trim().length() == 8)
						paramString = paramString.substring(0, 4)
								+ "-"
								+ paramString.substring(4, 6)
								+ "-"
								+ paramString
										.substring(6, paramString.length())
								+ " 00:00:00";
					SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					localObject = new Date();
					SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat(
							getformat());
					Date localDate = localSimpleDateFormat1.parse(paramString);
					str = localSimpleDateFormat2.format(localDate);
				} catch (ParseException localParseException) {
					str = paramString;
				}
		} else
			try {
				if (!getformat().trim().equals("")) {
					DecimalFormat localDecimalFormat = new DecimalFormat(
							getformat());
					localObject = Double.valueOf(paramString);
					if (!((Double) localObject).isNaN())
						str = localDecimalFormat.format(((Double) localObject)
								.doubleValue());
				}
			} catch (Exception localException) {
				str = paramString;
			}
		return str;
	}

	public abstract String generateInnerHTML();

	public String generateReturnValue() {
		if (!this.isReturn.equalsIgnoreCase("true"))
			return "";
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		localStringBufferTool.append("\"<");
		localStringBufferTool.append(this.property);
		localStringBufferTool.append(">\"");
		localStringBufferTool.append("+");
		localStringBufferTool.append("document.all(\"");
		localStringBufferTool.append(this.property);
		localStringBufferTool.append("\")");
		localStringBufferTool.append("[i]");
		if ((this instanceof BaseCheck))
			localStringBufferTool.append(".checked");
		else
			localStringBufferTool.append(".value");
		localStringBufferTool.append("+");
		localStringBufferTool.append("\"</");
		localStringBufferTool.append(this.property);
		localStringBufferTool.append(">\"");
		return localStringBufferTool.toString();
	}

	public String generateReturnDetail() {
		if (!this.isReturn.equalsIgnoreCase("true"))
			return "";
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		localStringBufferTool.append("document.all(\"");
		localStringBufferTool.append(this.property);
		localStringBufferTool.append("\")");
		localStringBufferTool.append("[i]");
		localStringBufferTool.append(".value");
		return localStringBufferTool.toString();
	}

	public String generateReturnSQL() {
		if (!this.isReturn.equalsIgnoreCase("true"))
			return "";
		StringBufferTool localStringBufferTool = new StringBufferTool(
				new StringBuffer());
		localStringBufferTool.append("document.all(\"");
		localStringBufferTool.append(this.property);
		localStringBufferTool.append("\")");
		localStringBufferTool.append("[i]");
		localStringBufferTool.append(".value");
		return localStringBufferTool.toString();
	}

	public abstract String generateLightOn(String paramString1,
			String paramString2);
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.datagridreport.BaseInputTag JD-Core Version: 0.6.1
 */