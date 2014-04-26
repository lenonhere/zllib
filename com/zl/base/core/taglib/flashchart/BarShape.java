/**
 * @author: 朱忠南
 * @date: Jul 4, 2008
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
 * @date Jul 4, 2008
 */
public class BarShape extends Shape {

	/**
	 * 柱状图类型
	 */
	private String barType = Shape.BAR;

	/**
	 * 柱状图所代表的指标名
	 */
	private String name;

	/**
	 * 指标名字体大小
	 */
	private int nameFontSize = 10;

	/**
	 * 柱状图颜色
	 */
	private String color = "#7E97A6";

	/**
	 * 柱状图边框颜色（仅对bar_glass和filled_bar起作用）
	 */
	private String borderColor = "#7E97A6";

	/**
	 * 透明度（1-100）
	 */
	private int clarity = 70;

	/**
	 * 数据
	 */
	private String values;

	/**
	 * 链接
	 */
	private String links;

	/**
	 * 构造函数
	 */
	public BarShape() {
		super();
	}

	/**
	 * 构造函数
	 */
	public BarShape(String barType, String name, String color) {
		super();
		setBarType(barType);
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
	public BarShape(String barType, String name) {
		super();
		setBarType(barType);
		setName(name);
		setAutoColor(true);
	}

	/**
	 * @return the barType
	 */
	public String getBarType() {
		return barType;
	}

	/**
	 * @param barType
	 *            the barType to set
	 */
	public void setBarType(String barType) {
		if (isValidBarType(barType))
			this.barType = barType.toLowerCase();
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
	 * @return the borderColor
	 */
	public String getBorderColor() {
		return borderColor;
	}

	/**
	 * @param borderColor
	 *            the borderColor to set
	 */
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	/**
	 * @return the clarity
	 */
	public int getClarity() {
		return clarity;
	}

	/**
	 * @param clarity
	 *            the clarity to set
	 */
	public void setClarity(int clarity) {
		if (clarity < 0)
			return;
		if (clarity > 100)
			clarity = 100;
		this.clarity = clarity;
	}

	/**
	 * @return the values
	 */
	public String getValues() {
		return values;
	}

	/**
	 * @param values
	 *            以逗号分隔
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

	/**
	 * 转化成Html格式
	 *
	 * @author: 朱忠南
	 * @return
	 */
	public String toHtml() {
		StringBuffer sb = new StringBuffer();
		if (getIndex() == 0)
			return "";
		else {
			if (barType.equals(Shape.BAR) || barType.equals(Shape.BAR_3D)
					|| barType.equals(Shape.BAR_FADE)) {
				if (getIndex() == 1) {
					sb.append("mychart.addVariable('" + barType + "','"
							+ clarity + "," + getColor() + "," + name + ","
							+ nameFontSize + "');\n");
				} else {
					sb.append("mychart.addVariable('" + barType + "_"
							+ getIndex() + "','" + clarity + "," + getColor()
							+ "," + name + "," + nameFontSize + "');\n");
				}
			} else {
				if (getIndex() == 1) {
					sb.append("mychart.addVariable('" + barType + "','"
							+ clarity + "," + getColor() + "," + borderColor
							+ "," + name + "," + nameFontSize + "');\n");
				} else {
					sb.append("mychart.addVariable('" + barType + "_"
							+ getIndex() + "','" + clarity + "," + getColor()
							+ "," + borderColor + "," + name + ","
							+ nameFontSize + "');\n");
				}
			}

			if (getIndex() == 1) {
				sb.append("mychart.addVariable('values','" + values + "');\n");
				if (links != null && links.trim().length() != 0) {
					sb.append("mychart.addVariable('links','"
							+ this.getLinks().replaceAll("\"", "\\\\\'")
							+ "');\n");
				}
			} else {
				sb.append("mychart.addVariable('values_" + this.getIndex()
						+ "','" + values + "');\n");
				if (links != null && links.trim().length() != 0) {
					sb.append("mychart.addVariable('links_" + getIndex()
							+ "','"
							+ this.getLinks().replaceAll("\"", "\\\\\'")
							+ "');\n");
				}
			}
			return sb.toString();
		}
	}

	/**
	 * 转化成数据格式
	 *
	 * @author: 朱忠南
	 * @return
	 */
	public String toText() {
		StringBuffer sb = new StringBuffer();
		if (getIndex() == 0)
			return "";
		else {
			if (barType.equals(Shape.BAR) || barType.equals(Shape.BAR_3D)
					|| barType.equals(Shape.BAR_FADE)) {
				if (getIndex() == 1) {
					sb.append("&" + barType + "=" + clarity + "," + getColor()
							+ "," + name + "," + nameFontSize + "&\n");
				} else {
					sb.append("&" + barType + "_" + getIndex() + "=" + clarity
							+ "," + getColor() + "," + name + ","
							+ nameFontSize + "&\n");
				}
			} else {
				if (getIndex() == 1) {
					sb.append("&" + barType + "=" + clarity + "," + getColor()
							+ "," + borderColor + "," + name + ","
							+ nameFontSize + "&\n");
				} else {
					sb.append("&" + barType + "_" + getIndex() + "=" + clarity
							+ "," + getColor() + "," + borderColor + "," + name
							+ "," + nameFontSize + "&\n");
				}
			}

			if (getIndex() == 1) {
				sb.append("&values=" + values + "&\n");
				if (links != null && links.trim().length() != 0) {
					sb.append("&links_" + this.getIndex() + "=" + links + "&\n");
				}
			} else {
				sb.append("&values_" + this.getIndex() + "=" + values + "&\n");
				if (links != null && links.trim().length() != 0) {
					sb.append("&links_" + this.getIndex() + "=" + links + "&\n");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 判断柱状图类型是否合法
	 *
	 * @author: 朱忠南
	 * @param barType
	 * @return
	 */
	public static boolean isValidBarType(String barType) {
		Set bar_type_set = new HashSet();
		bar_type_set.add(BAR);
		bar_type_set.add(BAR_3D);
		bar_type_set.add(BAR_FADE);
		bar_type_set.add(BAR_FILLED);
		bar_type_set.add(BAR_GLASS);
		return bar_type_set.contains(barType.toLowerCase());
	}

	public static void main(String[] args) {
		BarShape barShape = new BarShape();
		barShape.setIndex(1);
		barShape.setBarType(Shape.BAR_FILLED);
		barShape.setBorderColor("#FF0000");
		barShape.setClarity(70);
		barShape.setColor("#0000FF");
		barShape.setName("指标");
		barShape.setNameFontSize(12);
		System.out.println(barShape.toText());
		System.out.println(barShape.toHtml());
	}

}
