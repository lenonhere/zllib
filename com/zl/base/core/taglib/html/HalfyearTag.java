/*
 * Created on Apr 26, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.zl.base.core.taglib.html;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.ResponseUtils;

/**
 * @author lhd
 *
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HalfyearTag extends BaseHandlerTag {

	private static final long serialVersionUID = 2068804600923981653L;
	private String name;
	private String selectedHalf;
	private String style;

	public int doEndTag() throws JspException {
		int iSelectedHalf = 0;
		if (getSelectedHalf() != null && getSelectedHalf().trim().length() > 0)
			iSelectedHalf = Integer.parseInt(getSelectedHalf());

		StringBuffer strResults = new StringBuffer("<select name='");
		strResults.append(getName());
		strResults.append("' style='");
		strResults.append(getStyle());
		strResults.append("'>");
		if (iSelectedHalf == 0) {
			strResults.append("<option value=0 selected>上半年</option>");
			strResults.append("<option value=1>下半年</option>");
		} else {
			strResults.append("<option value=0>上半年</option>");
			strResults.append("<option value=1 selected>下半年</option>");
		}
		strResults.append("</select>");
		ResponseUtils.write(pageContext, strResults.toString());
		return EVAL_PAGE;
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
	public String getStyle() {
		return style;
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
	public void setStyle(String string) {
		style = string;
	}

	/**
	 * @return
	 */
	public String getSelectedHalf() {
		return selectedHalf;
	}

	/**
	 * @param string
	 */
	public void setSelectedHalf(String string) {
		selectedHalf = string;
	}

}
