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
public class PieChart extends Chart {

	/**
	 * @param divName
	 */
	public PieChart(String divName) {
		super(divName);
		this.setToolTip("#x_label#：#val#");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see flashchart.Chart#toText()
	 */
	public String toText() {
		StringBuffer sb = new StringBuffer(super.toText());
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see flashchart.Chart#toHtml()
	 */
	public String toHtml(String contextPath) {
		StringBuffer sb = new StringBuffer();

		sb.append("<script type='text/javascript'>\n");

		sb.append(super.toHtml(contextPath));

		sb.append("mychart.write('" + getDivName() + "');\n");

		sb.append("</script>\n");

		return sb.toString();
	}

	public static void main(String[] args) {
		PieChart chart = new PieChart("mychart");
		chart.setTitle(new Title("这里是标题"));
		PieShape shape = new PieShape();
		shape.setLabels("一月,二月,三月");
		shape.setValues("10,20,30");
		shape.setLabelFont(new Font());
		chart.addShape(shape);
		System.out.println(chart.toHtml("/hbsale"));
	}

}
