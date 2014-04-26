/**
 * @author: 朱忠南
 * @date: Jul 9, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe: X轴刻度线属性
 * @modify_author:
 * @modify_time:
 */
package com.zl.base.core.taglib.flashchart;

/**
 * @author jokin
 * @date Jul 9, 2008
 */
public class XAxis extends Axis {

	/**
	 * X轴刻度
	 */
	private String labels;

	private XLabelStyle style = new XLabelStyle();

	/**
	 * 3d类型X轴宽度 非3d类型，X轴宽度默认为0
	 */
	private int axis3d = 0;

	private XTicks ticks = new XTicks();

	/**
	 * @param axisType
	 */
	public XAxis() {
		super(Axis.X);
	}

	/**
	 * @return the labels
	 */
	public String getLabels() {
		return labels;
	}

	/**
	 * 设置数据，数据以逗号（,）分隔
	 *
	 * @param labels
	 *            the labels to set
	 */
	public void setLabels(String labels) {
		this.labels = labels;
	}

	/**
	 * 设置数据
	 *
	 * @author: 朱忠南
	 * @param labels
	 */
	public void setLabels(String[] labels) {
		for (int i = 0, n = labels.length; i < n; i++) {
			this.labels += labels[i] + ",";
			labels.toString();
		}
		if (this.labels.length() > 0)
			this.labels = this.labels.substring(0, this.labels.length() - 1);
	}

	/**
	 * 获取3d类型X轴刻度线的线宽
	 *
	 * @return the axis3d
	 */
	public int getAxis3d() {
		return axis3d;
	}

	/**
	 * @return the ticks
	 */
	public XTicks getTicks() {
		return ticks;
	}

	/**
	 * @param ticks
	 *            the ticks to set
	 */
	public void setTicks(XTicks ticks) {
		this.ticks = ticks;
	}

	/**
	 * 设置3d类型X轴刻度线的线宽
	 *
	 * @param axis3d
	 *            the axis3d to set
	 */
	public void setAxis3d(int axis3d) {
		this.axis3d = axis3d;
	}

	/**
	 * @return the style
	 */
	public XLabelStyle getStyle() {
		return style;
	}

	/**
	 * @param style
	 *            the style to set
	 */
	public void setStyle(XLabelStyle style) {
		this.style = style;
	}

	public String toText() {
		StringBuffer sb = new StringBuffer();

		sb.append(super.toText());

		if (ticks != null)
			sb.append(ticks.toText());

		if (labels != null)
			sb.append("&x_labels=" + labels + "&\n");

		if (axis3d != 0)
			sb.append("&x_axis_3d=" + axis3d + "&\n");

		if (style != null)
			sb.append(style.toText());

		return sb.toString();
	}

	public String toHtml() {
		StringBuffer sb = new StringBuffer();

		sb.append(super.toHtml());

		if (ticks != null)
			sb.append(ticks.toHtml());

		if (labels != null)
			sb.append("mychart.addVariable('x_labels','" + labels + "');\n");

		if (axis3d != 0)
			sb.append("mychart.addVariable('x_axis_3d','" + axis3d + "');\n");

		if (style != null)
			sb.append(style.toHTML());

		return sb.toString();
	}

}
