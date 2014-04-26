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
public class PieShape extends Shape {

	/**
	 * 柱状图类型
	 */
	private String pieType = Shape.PIE;

	/**
	 * 指标名字体
	 */
	private Font labelFont = new Font();

	/**
	 * 指标是否显示
	 */
	private boolean labelDisplay = true;

	/**
	 * 边缘线颜色
	 */
	private String borderColor = "#7E97A6";

	/**
	 * 边缘线宽
	 */
	private int borderSize = 2;

	/**
	 * 透明度（1-100）
	 */
	private int clarity = 60;

	/**
	 * 各饼图块名称，以（,）分隔
	 */
	private String labels;

	/**
	 * 各饼图块数据，以（,）分隔
	 */
	private String values;

	/**
	 * 各饼图块颜色，以（,）分隔
	 */
	private String colors;

	/**
	 * 链接
	 */
	private String links;

	/**
	 * 构造函数
	 */
	public PieShape() {
		setAutoColor(true);
	}

	/**
	 * @return the pieType
	 */
	public String getPieType() {
		return pieType;
	}

	/**
	 * @param barType
	 *            the barType to set
	 */
	public void setPieType(String pieType) {
		if (isValidPieType(pieType))
			this.pieType = pieType.toLowerCase();
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
	 * @return the labelFont
	 */
	public Font getLabelFont() {
		return labelFont;
	}

	/**
	 * @param labelFont
	 *            the labelFont to set
	 */
	public void setLabelFont(Font labelFont) {
		this.labelFont = labelFont;
	}

	/**
	 * @return the labelDisplay
	 */
	public boolean isLabelDisplay() {
		return labelDisplay;
	}

	/**
	 * @param labelDisplay
	 *            the labelDisplay to set
	 */
	public void setLabelDisplay(boolean labelDisplay) {
		this.labelDisplay = labelDisplay;
	}

	/**
	 * @return the borderSize
	 */
	public int getBorderSize() {
		return borderSize;
	}

	/**
	 * @param borderSize
	 *            the borderSize to set
	 */
	public void setBorderSize(int borderSize) {
		this.borderSize = borderSize;
	}

	/**
	 * @return the labels
	 */
	public String getLabels() {
		return labels;
	}

	/**
	 * @param labels
	 *            the labels to set
	 */
	public void setLabels(String labels) {
		this.labels = labels;
	}

	public void setLabels(String[] values) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			sb.append(value).append(",");
		}
		if (sb.length() > 0)
			this.labels = sb.substring(0, sb.length() - 1);
	}

	/**
	 * @return the colors
	 */
	public String getColors() {
		if (this.isAutoColor() && this.labels != null) {
			String colours = "";
			String[] items = this.labels.split(",");
			for (int i = 0; i < items.length; i++) {
				colours += this.getRandomColor(i % 10) + ",";
			}
			if (colours.length() > 0)
				colours = colours.substring(0, colours.length() - 1);
			return colours;
		}
		return colors;
	}

	/**
	 * @param colors
	 *            the colors to set
	 */
	public void setColors(String colors) {
		this.colors = colors;
	}

	public void setColors(String[] values) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			sb.append(value).append(",");
		}
		if (sb.length() > 0)
			this.colors = sb.substring(0, sb.length() - 1);
	}

	/**
	 * @return the links
	 */
	public String getLinks() {
		return links;
	}

	/**
	 * 设置链接，以逗号分隔，注意链接中不能有逗号（即href里面部分，如：javascript:alert(),www.baidu.com）
	 *
	 * @param links
	 *            the links to set
	 */
	public void setLinks(String links) {
		this.links = links;
	}

	/**
	 * 设置链接，以逗号分隔，注意链接中不能有逗号（即href里面部分，如：javascript:alert(),www.baidu.com）
	 *
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
		String font = "";
		if (this.labelDisplay) {
			font = "{font-size:" + this.labelFont.getFontSize() + "; color:"
					+ this.labelFont.getFontColor() + "}";
		} else {
			font = "{display:none;font-size:" + this.labelFont.getFontSize()
					+ "; color:" + this.labelFont.getFontColor() + "}";
		}
		sb.append("mychart.addVariable('" + pieType + "','" + clarity + ","
				+ borderColor + "," + font + ",true," + this.borderSize
				+ "');\n");
		if (this.getLabels() != null && !this.getLabels().trim().equals(""))
			sb.append("mychart.addVariable('pie_labels','" + this.getLabels()
					+ "');\n");
		if (this.getValues() != null && !this.getValues().trim().equals(""))
			sb.append("mychart.addVariable('values','" + this.getValues()
					+ "');\n");
		if (this.getColors() != null && !this.getColors().trim().equals(""))
			sb.append("mychart.addVariable('colours','" + this.getColors()
					+ "');\n");
		if (this.getLinks() != null && !this.getLinks().trim().equals(""))
			sb.append("mychart.addVariable('links','"
					+ this.getLinks().replaceAll("\"", "\\\\\'") + "');\n");

		return sb.toString();
	}

	/**
	 * 转化成数据格式
	 *
	 * @author: 朱忠南
	 * @return
	 */
	public String toText() {
		StringBuffer sb = new StringBuffer();
		String font = "";
		if (this.labelDisplay) {
			font = "{font-size:" + this.labelFont.getFontSize() + "; color:"
					+ this.labelFont.getFontColor() + "}";
		} else {
			font = "{display:none;font-size:" + this.labelFont.getFontSize()
					+ "; color:" + this.labelFont.getFontColor() + "}";
		}
		sb.append("&" + pieType + "=" + clarity + "," + borderColor + ","
				+ font + ",true," + this.borderSize + "&\n");
		if (this.getLabels() != null && !this.getLabels().trim().equals(""))
			sb.append("&pie_labels=" + this.getLabels() + "&\n");
		if (this.getValues() != null && !this.getValues().trim().equals(""))
			sb.append("&values=" + this.getValues() + "&\n");
		if (this.getColors() != null && !this.getColors().trim().equals(""))
			sb.append("&colors=" + this.getColors() + "&\n");
		if (this.getLinks() != null && !this.getLinks().trim().equals(""))
			sb.append("&links=" + this.getLinks() + "&");

		return sb.toString();
	}

	/**
	 * 判断柱状图类型是否合法
	 *
	 * @author: 朱忠南
	 * @param pieType
	 * @return
	 */
	public static boolean isValidPieType(String pieType) {
		Set pie_type_set = new HashSet();
		pie_type_set.add(PIE);
		return pie_type_set.contains(pieType.toLowerCase());
	}

}
