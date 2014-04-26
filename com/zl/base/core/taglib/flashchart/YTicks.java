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
public class YTicks {

	/**
	 * 是否是右边Y轴的刻度 true：Y2 false:Y
	 */
	private boolean isRightY = false;

	/**
	 * 关键刻度线的长度（如：最大值，最小值，中间值等刻度线）
	 */
	private int maxLength = 10;

	/**
	 * 普通刻度线长度 注：一般比关键刻度线小
	 */
	private int normalLength = 5;

	/**
	 * 刻度份数
	 */
	private int count;

	/**
	 * 构造Y轴刻度线
	 */
	public YTicks() {
		super();
	}

	/**
	 * 构造Y轴刻度线
	 *
	 * @param 是否是Y右轴
	 */
	public YTicks(boolean isRightY) {
		super();
		this.isRightY = isRightY;
	}

	/**
	 * 构造Y轴刻度线
	 *
	 * @param isRightY
	 *            是否是右边Y轴（Y2）
	 * @param count
	 *            刻度份数
	 */
	public YTicks(boolean isRightY, int count) {
		super();
		this.isRightY = isRightY;
		this.count = count;
	}

	/**
	 * 获取关键刻度线长度 如：最大值、中间值、最小值刻度线的长度
	 *
	 * @return the maxLength
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * 设置关键刻度线长度 如：最大值、中间值、最小值刻度线的长度
	 *
	 * @param maxLength
	 *            the maxLength to set
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * 获取普通刻度线长度
	 *
	 * @return the normalLength
	 */
	public int getNormalLength() {
		return normalLength;
	}

	/**
	 * 设置普通刻度线长度
	 *
	 * @param normalLength
	 *            the normalLength to set
	 */
	public void setNormalLength(int normalLength) {
		this.normalLength = normalLength;
	}

	/**
	 * 获取刻度线份数
	 *
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 设置刻度线份数
	 *
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	public String toText() {
		if (isRightY) {
			return "&y2_ticks=" + normalLength + "," + maxLength + "," + count
					+ "&\n";
		} else {
			return "&y_ticks=" + normalLength + "," + maxLength + "," + count
					+ "&\n";
		}
	}

	public String toHtml() {
		if (isRightY) {
			return "mychart.addVariable('y2_ticks','" + normalLength + ","
					+ maxLength + "," + count + "');\n";
		} else {
			return "mychart.addVariable('y_ticks','" + normalLength + ","
					+ maxLength + "," + count + "');\n";
		}
	}

}
