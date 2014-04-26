/**
 * @author: 朱忠南
 * @date: Jul 9, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe:
 * @modify_author:
 * @modify_time:
 */
package com.zl.base.core.taglib.flashchart;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jokin
 * @date Jul 9, 2008
 */
public class LineShape extends Shape {

	/**
	 * 折线图类型
	 */
	private String lineType = Shape.LINE_DOT;

	/**
	 * 折线图所代表的指标名
	 */
	private String name;

	/**
	 * 指标名字体大小
	 */
	private int nameFontSize = 10;

	/**
	 * 折线图线的颜色
	 */
	private String color = "#7E97A6";

	/**
	 * 线宽
	 */
	private int lineWidth = 3;

	/**
	 * 点宽
	 */
	private int dotWidth = 5;

	/**
	 * 数据
	 */
	private String values;

	/**
	 * 链接
	 */
	private String links;

	/**
     *
     */
	public LineShape() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造函数
	 */
	public LineShape(String lineType, String name, String color) {
		super();
		setLineType(lineType);
		setName(name);
		if (color != null && !color.trim().equals("")) {
			setColor(color);
		} else {
			setAutoColor(true);
		}
	}

	/**
	 * 构造函数
	 */
	public LineShape(String lineType, String name) {
		super();
		setLineType(lineType);
		setName(name);
		setAutoColor(true);
	}

	/**
	 * @return the lineType
	 */
	public String getLineType() {
		return lineType;
	}

	/**
	 * @param lineType
	 *            the lineType to set
	 */
	public void setLineType(String lineType) {
		if (isValidLineType(lineType))
			this.lineType = lineType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the nameFontSize
	 */
	public int getNameFontSize() {
		return nameFontSize;
	}

	/**
	 * @param nameFontSize
	 *            the nameFontSize to set
	 */
	public void setNameFontSize(int nameFontSize) {
		this.nameFontSize = nameFontSize;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		if (isAutoColor())
			return getRandomColor(this.getIndex());
		else
			return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
		setAutoColor(false);
	}

	/**
	 * @return the lineWidth
	 */
	public int getLineWidth() {
		return lineWidth;
	}

	/**
	 * @param lineWidth
	 *            the lineWidth to set
	 */
	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	/**
	 * @return the dotWidth
	 */
	public int getDotWidth() {
		return dotWidth;
	}

	/**
	 * @param dotWidth
	 *            the dotWidth to set
	 */
	public void setDotWidth(int dotWidth) {
		this.dotWidth = dotWidth;
	}

	/**
	 * @return the values
	 */
	public String getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(String values) {
		this.values = values;
	}

	public void setValues(String[] values) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			sb.append(value).append(",");
		}
		if (sb.length() > 0)
			this.values = sb.substring(0, sb.length() - 1);
	}

	/**
	 * @return the links
	 */
	public String getLinks() {
		return links;
	}

	/**
	 * @param links
	 *            the links to set
	 */
	public void setLinks(String links) {
		this.links = links;
	}

	/**
	 * @param links
	 *            the links to set
	 */
	public void setLinks(String[] links) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < links.length; i++) {
			String value = links[i];
			sb.append(value).append(",");
		}
		if (sb.length() > 0)
			this.links = sb.substring(0, sb.length() - 1);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see flashchart.Shape#toHtml()
	 */
	public String toHtml() {
		StringBuffer sb = new StringBuffer();
		if (getIndex() == 0)
			return "";
		else {
			if (getIndex() == 1) {
				sb.append("mychart.addVariable('" + lineType + "','"
						+ lineWidth + "," + getColor() + "," + name + ","
						+ nameFontSize + "," + dotWidth + "');\n");
				sb.append("mychart.addVariable('values','" + values + "');\n");
				if (links != null && links.trim().length() != 0) {
					sb.append("mychart.addVariable('links','"
							+ this.getLinks().replaceAll("\"", "\\\\\'")
							+ "');\n");
				}
			} else {
				sb.append("mychart.addVariable('" + lineType + "_" + getIndex()
						+ "','" + lineWidth + "," + getColor() + "," + name
						+ "," + nameFontSize + "," + dotWidth + "');\n");
				sb.append("mychart.addVariable('values_" + this.getIndex()
						+ "','" + values + "');\n");
				if (links != null && links.trim().length() != 0) {
					sb.append("mychart.addVariable('links_" + getIndex()
							+ "','"
							+ this.getLinks().replaceAll("\"", "\\\\\'")
							+ "');\n");
				}
			}
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see flashchart.Shape#toText()
	 */
	public String toText() {
		StringBuffer sb = new StringBuffer();
		if (getIndex() == 0)
			return "";
		else {
			if (getIndex() == 1) {
				sb.append("&" + lineType + "=" + lineWidth + "," + getColor()
						+ "," + name + "," + nameFontSize + "," + dotWidth
						+ "&\n");
				sb.append("&values=" + values + "&\n");
				if (links != null && links.trim().length() != 0) {
					sb.append("&links=" + links + "&\n");
				}
			} else {
				sb.append("&" + lineType + "_" + getIndex() + "=" + lineWidth
						+ "," + getColor() + "," + name + "," + nameFontSize
						+ "," + dotWidth + "&\n");
				sb.append("&values_" + this.getIndex() + "=" + values + "&\n");
				if (links != null && links.trim().length() != 0) {
					sb.append("&links_" + this.getIndex() + "=" + links + "&\n");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 判断折线图类型是否合法
	 *
	 * @author: 朱忠南
	 * @param lineType
	 * @return
	 */
	public static boolean isValidLineType(String lineType) {
		Set line_type_set = new HashSet();
		// line_type_set.add(LINE);
		line_type_set.add(LINE_DOT);
		line_type_set.add(LINE_HOLLOW);
		return line_type_set.contains(lineType.toLowerCase());
	}

}
