package com.zl.base.core.taglib.html;

import java.util.Calendar;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.ResponseUtils;

public class MonthTag extends BaseHandlerTag {

	private static final long serialVersionUID = 6667475536321643863L;
	private String name;
	private String startMonth;
	private String endMonth;
	private String selectedMonth;
	private String style;

	public int doEndTag() throws JspException {
		int iNowMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int iStartMonth = 1;
		int iEndMonth = 12;
		if (getStartMonth() != null && getStartMonth().trim().length() > 0)
			iStartMonth = Integer.parseInt(getStartMonth());
		if (getEndMonth() != null && getEndMonth().trim().length() > 0)

			iEndMonth = Integer.parseInt(getEndMonth());
		int iSelectedMonth = iNowMonth;
		if (getSelectedMonth() != null
				&& getSelectedMonth().trim().length() > 0)
			iSelectedMonth = Integer.parseInt(getSelectedMonth());

		StringBuffer strResults = new StringBuffer("<select name='");
		strResults.append(getName());
		strResults.append("' style='");
		strResults.append(getStyle());
		strResults.append("'>");
		for (int i = iStartMonth; i <= iEndMonth; i++) {
			strResults.append("<option value=");
			strResults.append(i);
			if (i == iSelectedMonth)
				strResults.append(" selected");
			strResults.append(">");
			if (i < 10)
				strResults.append(0);
			strResults.append(i);
			strResults.append("</option>");
		}
		strResults.append("</select>");
		ResponseUtils.write(pageContext, strResults.toString());
		return EVAL_PAGE;
	}

	/**
	 * @return
	 */
	public String getEndMonth() {
		return endMonth;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getSelectedMonth() {
		return selectedMonth;
	}

	/**
	 * @return
	 */
	public String getStartMonth() {
		return startMonth;
	}

	/**
	 * @return
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param string
	 */
	public void setEndMonth(String string) {
		endMonth = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setSelectedMonth(String string) {
		selectedMonth = string;
	}

	/**
	 * @param string
	 */
	public void setStartMonth(String string) {
		startMonth = string;
	}

	/**
	 * @param string
	 */
	public void setStyle(String string) {
		style = string;
	}

}
