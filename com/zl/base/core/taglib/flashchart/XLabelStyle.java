/**
 * @author: 朱忠南
 * @date: Aug 8, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe:
 * @modify_author:
 * @modify_time:
 */
package com.zl.base.core.taglib.flashchart;

/**
 * @author jokin
 * @date Aug 8, 2008
 */
public class XLabelStyle {

	/**
	 * X轴标签0度显示
	 */
	public static final int DEGREE_0 = 0;

	/**
	 * X轴标签45度显示
	 */
	public static final int DEGREE_45 = 2;

	/**
	 * X轴标签90度显示
	 */
	public static final int DEGREE_90 = 1;

	/**
	 * 字体
	 */
	private Font font;

	/**
	 * 字体显示角度
	 */
	private int degree = DEGREE_0;// 暂时此flash只支持0度显示中文

	/**
	 * 标签显示的间隔，0表示每个都显示，1表示每隔1个显示，一次类推
	 */
	private int interval = 0;

	/**
	 * 间隔线颜色
	 */
	private String lineColor = "#ADB5C7";

	/**
     *
     */
	public XLabelStyle() {
		super();
		font = new Font();
	}

	public XLabelStyle(Font font) {
		this.font = font;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font
	 *            the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * @return the degree
	 */
	public int getDegree() {
		return degree;
	}

	/**
	 * @param degree
	 *            the degree to set
	 */
	public void setDegree(int degree) {
		this.degree = degree;
	}

	/**
	 * @return the interval
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * @param interval
	 *            the interval to set
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}

	/**
	 * @return the lineColor
	 */
	public String getLineColor() {
		return lineColor;
	}

	/**
	 * @param lineColor
	 *            the lineColor to set
	 */
	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}

	public String toHTML() {
		return "mychart.addVariable('x_label_style','"
				+ this.font.getFontSize() + "," + this.font.getFontColor()
				+ "," + this.degree + "," + this.interval + ","
				+ this.lineColor + "');\n";
	}

	public String toText() {
		return "&x_label_style=" + this.font.getFontSize() + ","
				+ this.font.getFontColor() + "," + this.degree + ","
				+ this.interval + "," + this.lineColor + "&\n";
	}

}
