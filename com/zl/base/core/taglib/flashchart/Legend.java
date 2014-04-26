/**
 * @author: 朱忠南
 * @date: Jul 9, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe: 坐标说明
 * @modify_author:
 * @modify_time:
 */
package com.zl.base.core.taglib.flashchart;

/**
 * @author jokin
 * @date Jul 9, 2008
 */
public class Legend {
	/**
	 * 坐标轴说明
	 */
	private String name = "";

	/**
	 * 字体大小
	 */
	private int fontSize = 12;

	/**
	 * 字体颜色
	 */
	private String color = "#164166";

	public Legend(String name) {
		super();
		if (name != null)
			this.name = name;
	}

	/**
	 * @param name
	 *            坐标轴说明
	 * @param fontSize
	 *            坐标轴说明字体大小
	 * @param color
	 *            坐标轴说明字体颜色
	 */
	public Legend(String name, int fontSize, String color) {
		super();
		if (name != null)
			this.name = name;
		this.fontSize = fontSize;
		this.color = color;
	}

	/**
	 * 获取坐标轴说明
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置坐标轴说明
	 *
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取坐标轴说明字体大小
	 *
	 * @return the fontSize
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * 设置坐标轴说明字体大小
	 *
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * 获取坐标轴说明字体颜色
	 *
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * 设置坐标轴说明字体颜色
	 *
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 转化成功数据格式
	 *
	 * @author: 朱忠南
	 * @param axisType
	 *            坐标轴（X，Y，Y2）
	 * @return
	 */
	public String toText(String axisType) {
		return "&" + axisType + "_legend=" + name + "," + fontSize + ","
				+ color + "&\n";
	}

	/**
	 * 转化成js格式
	 *
	 * @author: 朱忠南
	 * @param axisType
	 *            坐标轴（X，Y，Y2）
	 * @return
	 */
	public String toHtml(String axisType) {
		return "mychart.addVariable('" + axisType + "_legend','" + name + ","
				+ fontSize + "," + color + "');\n";
	}

}
