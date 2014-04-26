/**
 * @author: 朱忠南
 * @date: Jul 9, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe: 坐标属性，包括坐标名称，坐标刻度线属性，坐标刻度值等等
 * @modify_author:
 * @modify_time:
 */
package com.zl.base.core.taglib.flashchart;

/**
 * @author jokin
 * @date Jul 9, 2008
 */
public class Axis {

	/**
	 * 表示X轴
	 */
	protected static final String X = "x";

	/**
	 * 表示Y左轴
	 */
	protected static final String Y = "y";

	/**
	 * 表示Y右轴
	 */
	protected static final String Y2 = "y2";

	/**
	 * 坐标轴类型
	 */
	private String axisType = Axis.X;
	/**
	 * 坐标说明
	 */
	private Legend legend;

	private int lableSize = 15;

	/**
	 * 刻度（XY）轴颜色
	 */
	private String axisColor = "#818D9D";

	/**
	 * 最大值
	 */
	private double max = 0;

	/**
	 * 最小值
	 */
	private double min = 0;

	/**
	 * 刻度线字体
	 */
	private Font lableStyle = new Font("", 10, "#164166");

	public Axis() {

	}

	public Axis(String axisType) {
		setAxisType(axisType);
	}

	/**
	 * @return the axisType
	 */
	public String getAxisType() {
		return axisType;
	}

	/**
	 * @param axisType
	 *            the axisType to set
	 */
	public void setAxisType(String axisType) {
		if (axisType.toLowerCase().equals(Axis.X)
				|| axisType.toLowerCase().equals(Axis.Y)
				|| axisType.toLowerCase().equals(Axis.Y2))
			this.axisType = axisType;
	}

	/**
	 * @return the legend
	 */
	public Legend getLegend() {
		return legend;
	}

	/**
	 * @param legend
	 *            the legend to set
	 */
	public void setLegend(Legend legend) {
		this.legend = legend;
	}

	/**
	 * @return the lableSize
	 */
	public int getLableSize() {
		return lableSize;
	}

	/**
	 * @param lableSize
	 *            the lableSize to set
	 */
	public void setLableSize(int lableSize) {
		this.lableSize = lableSize;
	}

	/**
	 * @return the axisColor
	 */
	public String getAxisColor() {
		return axisColor;
	}

	/**
	 * @param axisColor
	 *            the axisColor to set
	 */
	public void setAxisColor(String axisColor) {
		this.axisColor = axisColor;
	}

	/**
	 * @return the max
	 */
	public double getMax() {
		return max;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(double max) {
		this.max = max;
	}

	/**
	 * @return the min
	 */
	public double getMin() {
		return min;
	}

	/**
	 * @param min
	 *            the min to set
	 */
	public void setMin(double min) {
		this.min = min;
	}

	/**
	 * @return the lableStyle
	 */
	public Font getLableStyle() {
		return lableStyle;
	}

	/**
	 * @param lableStyle
	 *            the lableStyle to set
	 */
	public void setLableStyle(Font lableStyle) {
		this.lableStyle = lableStyle;
	}

	/**
	 * @return the x
	 */
	public static String getX() {
		return X;
	}

	/**
	 * @return the y
	 */
	public static String getY() {
		return Y;
	}

	/**
	 * @return the y2
	 */
	public static String getY2() {
		return Y2;
	}

	public String toText() {
		if (axisType == null
				|| !(axisType.toLowerCase().equals(Axis.X)
						|| axisType.toLowerCase().equals(Axis.Y) || axisType
						.toLowerCase().equals(Axis.Y2)))
			return "";

		StringBuffer sb = new StringBuffer();

		if (legend != null)
			sb.append(legend.toText(axisType));

		sb.append("&" + axisType + "_label_size=" + lableSize + "&\n");

		sb.append("&" + axisType + "_axis_colour=" + axisColor + "&\n");

		if (max != min) {
			sb.append("&" + axisType + "_max=" + max + "&\n");
			sb.append("&" + axisType + "_min=" + min + "&\n");
		}

		sb.append("&" + axisType + "_label_style=" + lableStyle.getFontSize()
				+ "," + lableStyle.getFontColor() + "&\n");

		return sb.toString();

	}

	public String toHtml() {
		if (axisType == null
				|| !(axisType.toLowerCase().equals(Axis.X)
						|| axisType.toLowerCase().equals(Axis.Y) || axisType
						.toLowerCase().equals(Axis.Y2)))
			return "";

		StringBuffer sb = new StringBuffer();

		if (legend != null)
			sb.append(legend.toHtml(axisType));

		sb.append("mychart.addVariable('" + axisType + "_label_size','"
				+ lableSize + "');\n");

		sb.append("mychart.addVariable('" + axisType + "_axis_colour','"
				+ axisColor + "');\n");

		if (max != min) {
			sb.append("mychart.addVariable('" + axisType + "_max','" + max
					+ "');\n");
			sb.append("mychart.addVariable('" + axisType + "_min','" + min
					+ "');\n");
		}

		sb.append("mychart.addVariable('" + axisType + "_label_style','"
				+ lableStyle.getFontSize() + "," + lableStyle.getFontColor()
				+ "');\n");

		return sb.toString();
	}

}
