/**
 * @author: 朱忠南
 * @date: Jul 9, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe:
 * @modify_author:
 * @modify_time:
 */
package com.zl.base.core.taglib.flashchart;

/**
 * @author jokin
 * @date Jul 9, 2008
 */
public class XYChart extends Chart {

	/**
	 * X轴
	 */
	private XAxis xAxis;

	/**
	 * Y左轴
	 */
	private YAxis yAxis;

	/**
	 * Y右轴
	 */
	private Y2Axis y2Axis;

	/**
	 * 表格竖线颜色，默认#ADB5C7
	 */
	private String xGridColor = "#ADB5C7";

	/**
	 * 表格横线颜色，默认#ADB5C7
	 */
	private String yGridColor = "#ADB5C7";

	/**
	 * Y右轴对应的数据index，大于等于1
	 */
	private String y2DataIndexes;

	/**
     *
     */
	public XYChart(String divName) {
		super(divName);
		this.setToolTip("#key#(#x_legend#:#x_label#)<br>#val#");
	}

	/**
	 * @return the xAxis
	 */
	public XAxis getXAxis() {
		return xAxis;
	}

	/**
	 * @param axis
	 *            the xAxis to set
	 */
	public void setXAxis(XAxis axis) {
		xAxis = axis;
	}

	/**
	 * @return the yAxis
	 */
	public YAxis getYAxis() {
		return yAxis;
	}

	/**
	 * @param axis
	 *            the yAxis to set
	 */
	public void setYAxis(YAxis axis) {
		yAxis = axis;
	}

	/**
	 * @return the y2Axis
	 */
	public Y2Axis getY2Axis() {
		return y2Axis;
	}

	/**
	 * @param axis
	 *            the y2Axis to set
	 */
	public void setY2Axis(Y2Axis axis) {
		y2Axis = axis;
	}

	/**
	 * @return the xGridColor
	 */
	public String getXGridColor() {
		return xGridColor;
	}

	/**
	 * @param gridColor
	 *            the xGridColor to set
	 */
	public void setXGridColor(String gridColor) {
		xGridColor = gridColor;
	}

	/**
	 * @return the yGridColor
	 */
	public String getYGridColor() {
		return yGridColor;
	}

	/**
	 * @param gridColor
	 *            the yGridColor to set
	 */
	public void setYGridColor(String gridColor) {
		yGridColor = gridColor;
	}

	/**
	 * @return the y2lines
	 */
	public String getY2DataIndexes() {
		return this.y2DataIndexes;
	}

	/**
	 * @param y2lines
	 *            the y2lines to set
	 */
	public void setY2DataIndexes(int[] y2DataIndexes) {
		for (int i = 0, n = y2DataIndexes.length; i < n; i++) {
			this.y2DataIndexes += y2DataIndexes[i] + ",";
		}
		if (this.y2DataIndexes.length() > 0)
			this.y2DataIndexes = this.y2DataIndexes.substring(0,
					this.y2DataIndexes.length() - 1);
	}

	public void setY2DataIndexes(String y2DataIndexes) {
		this.y2DataIndexes = y2DataIndexes;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see flashchart.Chart#toText()
	 */
	public String toText() {
		StringBuffer sb = new StringBuffer(super.toText());
		if (xAxis != null)
			sb.append(xAxis.toText());
		if (yAxis != null)
			sb.append(yAxis.toText());
		if (y2Axis != null) {
			sb.append(y2Axis.toText());
			sb.append("&y2_lines=" + y2DataIndexes + "&\n");
		}
		sb.append("&x_grid_colour=" + xGridColor + "&\n");
		sb.append("&y_grid_colour=" + yGridColor + "&\n");

		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see flashchart.Chart#toHtml()
	 */
	public String toHtml(String contextPath) {
		StringBuffer sb = new StringBuffer();

		// sb.append("<script type='text/javascript' src='js/swfobject.js'></script>\n");
		sb.append("<script type='text/javascript'>\n");

		sb.append(super.toHtml(contextPath));
		if (xAxis != null)
			sb.append(xAxis.toHtml());
		if (yAxis != null)
			sb.append(yAxis.toHtml());
		if (y2Axis != null) {
			sb.append(y2Axis.toHtml());
			sb.append("mychart.addVariable('y2_lines','" + y2DataIndexes
					+ "');\n");
		}
		sb.append("mychart.addVariable('x_grid_colour','" + xGridColor
				+ "');\n");
		sb.append("mychart.addVariable('y_grid_colour','" + yGridColor
				+ "');\n");

		sb.append("mychart.write('" + getDivName() + "');\n");

		sb.append("</script>\n");

		return sb.toString();
	}

	public static void main(String[] args) {
		XYChart xyChart = new XYChart("my_chart");

		// 标题
		xyChart.setTitle(new Title("这里是标题"));
		// X轴
		XAxis xAxis = new XAxis();
		xAxis.setLegend(new Legend("月份"));
		xAxis.setLabels("1月,2月,3月,4月,5月,6月");
		xAxis.setAxis3d(5);
		xAxis.setTicks(new XTicks());
		xyChart.setXAxis(xAxis);
		// y轴
		YAxis yAxis = new YAxis();
		yAxis.setLegend(new Legend("单位（箱）"));
		yAxis.setMax(100);
		yAxis.setMin(0);
		yAxis.setTicks(new YTicks(true, 10));
		xyChart.setYAxis(yAxis);
		// y2轴
		Y2Axis y2Axis = new Y2Axis();
		y2Axis.setLegend(new Legend("单位（万箱）"));
		y2Axis.setMax(0);
		y2Axis.setMin(-100);
		y2Axis.setTicks(new YTicks(true, 10));
		xyChart.setY2DataIndexes("2");
		xyChart.setY2Axis(y2Axis);
		// 假如图表数据
		BarShape shape = new BarShape(Shape.BAR_3D, "产量", "#AABBCC");
		shape.setValues("10.01,32.009,25.03,12,90.001,23");
		xyChart.addShape(shape);
		shape = new BarShape(Shape.BAR_GLASS, "销量", "#FF0000");
		shape.setValues("-17,-31,-55,-17,-50.02,-43");
		xyChart.addShape(shape);
		LineShape lshape = new LineShape(Shape.LINE_DOT, "库存", "#000000");
		lshape.setValues("23,20,25,87,56.02,90");
		xyChart.addShape(lshape);

		// xyChart.setToolTip("#x_legend#:#x_label#<br>#val#箱");

		System.out.println(xyChart.toText());
		System.out.println(xyChart.toHtml("/hbsale"));
	}

}
