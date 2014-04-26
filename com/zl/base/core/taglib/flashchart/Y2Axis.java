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
public class Y2Axis extends Axis {

	private YTicks ticks = new YTicks(true);

	public Y2Axis() {
		super(Axis.Y2);
	}

	public String toText() {
		if (ticks != null)
			return ticks.toText() + super.toText() + "&show_y2=true&\n";
		return super.toText() + "&show_y2=true&\n";
	}

	public String toHtml() {
		if (ticks != null)
			return ticks.toHtml() + super.toHtml()
					+ "mychart.addVariable('show_y2','true');\n";
		return super.toHtml() + "mychart.addVariable('show_y2','true');\n";
	}

	/**
	 * 获取Y2轴刻度线
	 *
	 * @return the ticks
	 */
	public YTicks getTicks() {
		return ticks;
	}

	/**
	 * 设置Y2轴刻度线
	 *
	 * @param ticks
	 *            the ticks to set
	 */
	public void setTicks(YTicks ticks) {
		this.ticks = ticks;
	}
}
