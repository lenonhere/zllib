/*
 * Created on Apr 25, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.zl.base.core.taglib.html;

import java.util.Calendar;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.ResponseUtils;

/**
 * @author lhd
 *
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class YearTag extends BaseHandlerTag {

	private static final long serialVersionUID = -2741448790023056892L;
	private String name;
	private String startYear;
	private String endYear;
	private String selectedYear;
	private String style;

	public int doEndTag() throws JspException {

		int iNowYear = Calendar.getInstance().get(Calendar.YEAR);
		int iStartYear = 2000;
		if (getStartYear() != null && getStartYear().trim().length() > 0)
			iStartYear = Integer.parseInt(getStartYear());
		int iEndYear = iNowYear + 1;
		if (getEndYear() != null && getEndYear().trim().length() > 0)
			iEndYear = Integer.parseInt(getEndYear());
		int iSelectedYear = iNowYear;
		if (getSelectedYear() != null && getSelectedYear().trim().length() > 0)
			iSelectedYear = Integer.parseInt(getSelectedYear());

		StringBuffer strResults = new StringBuffer("<select name='");
		strResults.append(getName());
		strResults.append("' style='");
		strResults.append(getStyle());
		strResults.append("'>");
		StringBuffer strSelected = new StringBuffer("");
		iSelectedYear = -1;
		if (getSelectedYear() != null && getSelectedYear().trim().length() > 0) {
			iSelectedYear = Integer.parseInt(getSelectedYear());
		}
		for (int i = iStartYear; i <= iEndYear; i++) {
			strSelected.replace(0, strSelected.length(), "");
			if (iSelectedYear != -1 && i == iSelectedYear)
				strSelected.append(" selected");
			strResults.append("<option value=");
			strResults.append(i);
			strResults.append(strSelected);
			strResults.append(">");
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
	public String getEndYear() {
		return endYear;
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
	public String getSelectedYear() {
		return selectedYear;
	}

	/**
	 * @return
	 */
	public String getStartYear() {
		return startYear;
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
	public void setEndYear(String string) {
		endYear = string;
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
	public void setSelectedYear(String string) {
		selectedYear = string;
	}

	/**
	 * @param string
	 */
	public void setStartYear(String string) {
		startYear = string;
	}

	/**
	 * @param string
	 */
	public void setStyle(String string) {
		style = string;
	}

}
