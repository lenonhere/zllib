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
public class YAxis extends Axis {

	private YTicks ticks = new YTicks();

	public YAxis() {
		super(Axis.Y);
	}

	public String toText() {
		if (ticks != null)
			return ticks.toText() + super.toText();
		return super.toText();
	}

	public String toHtml() {
		if (ticks != null)
			return ticks.toHtml() + super.toHtml();
		return super.toHtml();
	}

	/**
	 * 获取Y轴刻度
	 *
	 * @return the ticks
	 */
	public YTicks getTicks() {
		return ticks;
	}

	/**
	 * 设置Y轴刻度
	 *
	 * @param ticks
	 *            the ticks to set
	 */
	public void setTicks(YTicks ticks) {
		this.ticks = ticks;
	}
}
