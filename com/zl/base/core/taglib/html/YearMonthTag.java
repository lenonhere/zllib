package com.zl.base.core.taglib.html;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.ResponseUtils;

import com.zl.base.core.util.StringBufferTool;

public class YearMonthTag extends BaseHandlerTag {

	private static final long serialVersionUID = -6591638104273332400L;
	protected String name = "org.apache.struts.taglib.html.BEAN";
	private String onchange = null;
	private String property = null;
	private String value = null;
	private String cols = null;
	private String rows = null;
	private String splitstring = "";
	protected String yearStyle = "";
	protected String start = "";
	protected String size = "10";

	public String getcols() {
		return this.cols;
	}

	public void setcols(String paramString) {
		this.cols = paramString;
	}

	public String getrows() {
		return this.rows;
	}

	public void setrows(String paramString) {
		this.rows = paramString;
	}

	public String getvalue() {
		return this.value;
	}

	public void setvalue(String paramString) {
		this.value = paramString;
	}

	public String getproperty() {
		return this.property;
	}

	public void setproperty(String paramString) {
		this.property = paramString;
	}

	public String getsplitstring() {
		return this.splitstring;
	}

	public void setsplitstring(String paramString) {
		this.splitstring = paramString;
	}

	public String getonchange() {
		return this.onchange;
	}

	public void setonchange(String paramString) {
		this.onchange = paramString;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public String getYearStyle() {
		return this.yearStyle;
	}

	public void setYearStyle(String paramString) {
		this.yearStyle = paramString;
	}

	public String getStart() {
		return this.start;
	}

	public void setStart(String paramString) {
		this.start = paramString;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String paramString) {
		this.size = paramString;
	}

	public int doStartTag() throws JspException {
		String s = "";
		StringBuffer strBuffer = new StringBuffer("<input type=hidden ");
		strBuffer.append(" name=\"");
		strBuffer.append(property);
		strBuffer.append("\"");
		if (accesskey != null) {
			strBuffer.append(" accesskey=\"");
			strBuffer.append(accesskey);
			strBuffer.append("\"");
		}
		if (tabindex != null) {
			strBuffer.append(" tabindex=\"");
			strBuffer.append(tabindex);
			strBuffer.append("\"");
		}
		if (cols != null) {
			strBuffer.append(" cols=\"");
			strBuffer.append(cols);
			strBuffer.append("\"");
		}
		if (rows != null) {
			strBuffer.append(" rows=\"");
			strBuffer.append(rows);
			strBuffer.append("\"");
		}
		if (value != null) {
			strBuffer.append(" value=\"");
			strBuffer.append(ResponseUtils.filter(value));
			strBuffer.append("\"");
			s = value;
		} else {
			Object obj = pageContext.findAttribute(name);
			strBuffer.append(name);
			if (obj == null)
				throw new JspException(messages.getMessage("getter.bean", name));
			try {
				String s2 = BeanUtils.getProperty(obj, property);
				if (s2 == null || "".equals(s2.trim())) {
					s2 = "";
				}
				s = s2;
				strBuffer.append(" value=\"");
				strBuffer.append(ResponseUtils.filter(s2));
				strBuffer.append("\"");
			} catch (IllegalAccessException iae) {
				throw new JspException(messages.getMessage("getter.access",
						property, name));
			} catch (InvocationTargetException ite) {
				Throwable throwable = ite.getTargetException();
				throw new JspException(messages.getMessage("getter.result",
						property, throwable.toString()));
			} catch (NoSuchMethodException nsme) {
				throw new JspException(messages.getMessage("getter.method",
						property, name));
			}
		}
		strBuffer.append(prepareStyles());
		strBuffer.append(">");
		if (s == null) {
			s = "";
		}
		if (s.trim().length() < 6) {
			s = "       ";
		}
		String year = "2002";
		String month = "01";
		try {
			if (s.trim().equalsIgnoreCase("")) {
				year = getSystemDate().substring(0, 4);
				month = String.valueOf(Integer.parseInt(getSystemDate()
						.substring(5, 7)));
			} else {
				year = s.substring(0, 4);
				month = s.substring(4, 6);
			}
		} catch (Exception exception) {
			// throw new JspException(
			// "\u65F6\u95F4\u683C\u5F0F\u9519\u8BEF\uFF01\u5E94\u8BE5\u4E3A\u5E744\u4F4D+\u67082\u4F4D\uFF0C\uFF08200208\uFF09\uFF01");
			throw new JspException("时间格式错误！应该为年4位+月2位，（200208）！");
		}
		generateYeah(strBuffer, year);
		generateMonth(strBuffer, month);
		generateJavaScript(strBuffer);
		ResponseUtils.write(pageContext, strBuffer.toString());
		return 2;
	}

	public void release() {
		super.release();
		this.name = "org.apache.struts.taglib.html.BEAN";
	}

	/**
	 * 年份
	 *
	 * @param paramStrBuffer
	 * @param paramString
	 */
	private void generateYeah(StringBuffer paramStrBuffer, String year) {
		paramStrBuffer.append("<select");
		paramStrBuffer.append(" name=\"yeah_");
		paramStrBuffer.append(this.property);
		paramStrBuffer.append("\"");
		paramStrBuffer.append(" onchange=\"_");
		paramStrBuffer.append(this.property);
		paramStrBuffer.append("_onchange()");
		paramStrBuffer.append("\"");
		if (this.yearStyle != null) {
			paramStrBuffer.append(" style=\"");
			paramStrBuffer.append(this.yearStyle);
			paramStrBuffer.append("\"");
		}
		paramStrBuffer.append(">");
		addOptions(paramStrBuffer, year);
		paramStrBuffer.append("</select>");
	}

	/**
	 * 月份
	 *
	 * @param paramStrBuffer
	 * @param paramString
	 */
	private void generateMonth(StringBuffer paramStrBuffer, String paramString) {
		paramStrBuffer.append("<select");
		paramStrBuffer.append(" name=\"month_");
		paramStrBuffer.append(this.property);
		paramStrBuffer.append("\"");
		paramStrBuffer.append(" onchange=\"_");
		paramStrBuffer.append(this.property);
		paramStrBuffer.append("_onchange()");
		paramStrBuffer.append("\"");
		if (this.yearStyle != null) {
			paramStrBuffer.append(" style=\"");
			paramStrBuffer.append(this.yearStyle);
			paramStrBuffer.append("\"");
		}
		paramStrBuffer.append(">");
		String[] arrayOfString = { "01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12" };
		for (int j = 1; j <= 12; j++)
			addOption(paramStrBuffer, arrayOfString[(j - 1)],
					arrayOfString[(j - 1)] + "月", paramString);
		paramStrBuffer.append("</select>");
	}

	/**
	 * @param paramStrBuffer
	 * @param paramString
	 */
	private void generateJavaScript(StringBuffer paramStrBuffer) {
		StringBufferTool strBufferTool = new StringBufferTool(paramStrBuffer);
		strBufferTool.appendln("<script language=JavaScript>");
		strBufferTool.append("function _");
		strBufferTool.append(this.property);
		strBufferTool.appendln("_onchange(){");
		strBufferTool.append("document.all(\"");
		strBufferTool.append(this.property);
		strBufferTool.append("\").value=document.all(\"yeah_");
		strBufferTool.append(this.property);
		strBufferTool.append("\").value");
		strBufferTool.append("+\"");
		strBufferTool.append(this.splitstring);
		strBufferTool.append("\"+");
		strBufferTool.append("document.all(\"month_");
		strBufferTool.append(this.property);
		strBufferTool.appendln("\").value;");
		if (this.onchange != null)
			strBufferTool.appendln(this.onchange);
		strBufferTool.appendln("}");
		// Init JavaScript Function
		strBufferTool.append("_");
		strBufferTool.append(this.property);
		strBufferTool.appendln("_onchange();");
		strBufferTool.appendln("</script>");
	}

	/**
	 * @param paramStrBuffer
	 * @param paramString
	 */
	private void addOptions(StringBuffer paramStrBuffer, String year) {
		int i = 0, j = 0;
		if (this.start == null)
			this.start = "";
		int listSize = Integer.parseInt(this.size);
		int m = listSize % 2;
		int n = (m > 0 ? m + 1 : listSize / 2);
		int currentYear = Integer.parseInt(getSystemDate().substring(0, 4));
		int selectYear = Integer.parseInt(year);

		if (currentYear <= selectYear) {
			i = currentYear - n;// Start Year
			j = selectYear + n;// End Year
		}
		if (currentYear > selectYear) {
			i = selectYear - n;// Start Year
			j = currentYear + n;// End Year
		}

		for (int k = i; k <= j; k++) {
			addOption(paramStrBuffer, String.valueOf(k), String.valueOf(k)
					+ "年", year);
		}
	}

	/**
	 * @param paramStrBuffer
	 * @param value
	 * @param label
	 * @param year
	 */
	private void addOption(StringBuffer paramStrBuffer, String value,
			String label, String year) {
		paramStrBuffer.append("<option value=\"");
		paramStrBuffer.append(value);
		paramStrBuffer.append("\"");
		if (value.equals(year))
			paramStrBuffer.append(" selected=\"selected\"");
		paramStrBuffer.append(">");
		if (label == null)
			paramStrBuffer.append(value);
		else
			paramStrBuffer.append(label);
		paramStrBuffer.append("</option>");
	}

	/**
	 * 获取当前系统时间 [yyyy/MM/dd]
	 *
	 * @return
	 */
	private static String getSystemDate() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(date).trim();
	}

}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.date.YearMonthTag JD-Core Version: 0.6.1
 */