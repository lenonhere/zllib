/**
 * @author: 朱忠南
 * @date: Jul 9, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe:
 * @modify_author:
 * @modify_time:
 */
package com.zl.base.core.taglib.flashchart;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jokin
 * @date Jul 9, 2008
 */
public class Chart {

	/**
	 * 显示chart的div名称（id或name）
	 */
	private String divName;

	/**
	 * 标题
	 */
	private Title title;

	/**
	 * 背景颜色
	 */
	private String bgColor = "#E2E6E0";

	/**
	 * 鼠标指针触发的显示框内容
	 */
	private String toolTip;

	/**
	 * 数据
	 */
	List shapes = new ArrayList();

	int shapeCount = 0;

	/**
     *
     */
	public Chart(String divName) {
		super();
		this.divName = divName;
	}

	/**
	 * @return the bgColor
	 */
	public String getBgColor() {
		return bgColor;
	}

	/**
	 * @param bgColor
	 *            the bgColor to set
	 */
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	/**
	 * @return the toolTip
	 */
	public String getToolTip() {
		return toolTip;
	}

	/**
	 * @param toolTip
	 *            the toolTip to set
	 */
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	/**
	 * @return the title
	 */
	public Title getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(Title title) {
		this.title = title;
	}

	public void addShape(Shape shape) {
		shapeCount++;
		shape.setIndex(shapeCount);
		shapes.add(shape);
	}

	public String toText() {
		StringBuffer sb = new StringBuffer();
		if (title != null)
			sb.append(title.toText());
		sb.append("&bg_colour=" + bgColor + "&\n");
		sb.append("&tool_tip=" + toolTip + "&\n");
		for (int i = 0, n = shapes.size(); i < n; i++) {
			Shape shape = (Shape) shapes.get(i);
			sb.append(shape.toText());
		}
		return sb.toString();
	}

	public String toHtml(String contextPath) {
		StringBuffer sb = new StringBuffer();

		sb.append("mychart = new SWFObject('"
				+ contextPath
				+ "/js/open-flash-chart.swf', 'ofc', '100%', '100%', '9', '#EAEAEA');\n");
		sb.append("mychart.addVariable('variables','true');\n");
		if (title != null)
			sb.append(title.toHtml());
		sb.append("mychart.addVariable('bg_colour','" + bgColor + "');\n");
		sb.append("mychart.addVariable('tool_tip','" + toolTip + "');\n");
		for (int i = 0, n = shapes.size(); i < n; i++) {
			Shape shape = (Shape) shapes.get(i);
			sb.append(shape.toHtml());
		}
		return sb.toString();
	}

	/**
	 * @return the divName
	 */
	public String getDivName() {
		return divName;
	}

	public static void main(String[] args) {
		Chart chart = new Chart("my_chart");
		Title title = new Title("标题");
		chart.setTitle(title);
		chart.addShape(new PieShape());
		System.out.println(chart.toText());
	}

}
