/**
 * @author: 朱忠南
 * @date: Jul 29, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe: X轴刻度线
 * @modify_author:
 * @modify_time:
 */
package com.zl.base.core.taglib.flashchart;

/**
 * @author jokin
 * @date Jul 29, 2008
 */
public class XTicks {
	private int length = 5;

	public XTicks() {
		super();
	}

	/**
	 * 刻度线长度
	 *
	 * @param length
	 */
	public XTicks(int length) {
		this.length = length;
	}

	public String toText() {
		return "&x_ticks=" + length + "&\n";
	}

	public String toHtml() {
		return "mychart.addVariable('x_ticks','" + length + "');\n";
	}

}
