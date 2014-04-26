package com.zl.base.core.taglib;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.zl.base.core.taglib.grid.TextTag;

public class MyText extends TextTag {
	private static final Logger log = Logger.getLogger(MyText.class);

	protected static final BigDecimal ZERO_DECIMAL = new BigDecimal("0");
	protected String nozero;

	public String getNozero() {
		return nozero;
	}

	public void setNozero(String nozero) {
		this.nozero = nozero;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zl.base.core.taglib.grid.TextTag#generateRows(java.lang.StringBuffer,
	 * java.lang.String)
	 */
	public void generateRows(StringBuffer sb, String value) {
		sb.append("<td>");
		sb.append("<input type=\"");
		sb.append(type);
		sb.append("\" name=\"");
		sb.append(property);
		sb.append("\"");
		if (accesskey != null) {
			sb.append(" accesskey=\"");
			sb.append(accesskey);
			sb.append("\"");
		}
		if (maxlength != null) {
			sb.append(" maxlength=\"");
			sb.append(maxlength);
			sb.append("\"");
		}
		if (cols != null) {
			sb.append(" size=\"");
			sb.append(cols);
			sb.append("\"");
		}
		if (getReadonly()) {
			sb.append(" size=\"");
			sb.append(cols);
			sb.append("\"");
		}
		if (super.getalt() != null) {
			sb.append(" alt=\"");
			sb.append(super.getalt());
			sb.append("\"");
		}

		String fmtValue = value;

		if (value != null && !"".equals(value)) {
			if ("true".equalsIgnoreCase(nozero)) {
				if (value.indexOf(".") >= 0) {
					fmtValue = value.replace('0', ' ').trim().replace(' ', '0');
				}

				if (fmtValue.startsWith(".")) {
					fmtValue = "0" + fmtValue;
				}

				if (fmtValue.endsWith(".")) {
					fmtValue = fmtValue.substring(0, fmtValue.length() - 1);
				}
			}

			String format = getformat();
			if (format != null && !"".equals(format)) {
				BigDecimal decimal = new BigDecimal(value);
				if (decimal.compareTo(ZERO_DECIMAL) == 0) {
					fmtValue = "";
				} else {
					DecimalFormat formatter = new DecimalFormat(format);
					fmtValue = formatter.format(decimal);
				}
			}
		}

		sb.append(" value=\"");
		sb.append(fmtValue);
		sb.append("\"");
		sb.append(prepareEventHandlers());
		sb.append(prepareStyles());
		sb.append(">");
		sb.append("</td>");
	}

}
