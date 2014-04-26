/**
 * @author: 朱忠南
 * @date: Jul 4, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe:
 * @modify_author:
 * @modify_time:
 */
package com.zl.base.core.taglib.flashchart;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jokin
 * @date Jul 4, 2008
 */
public abstract class Shape {

	/**
	 * 颜色自动设置
	 */
	private boolean autoColor = false;

	/**
	 * 柱状图
	 */
	public static final String BAR = "bar";

	/**
	 * 柱状3D图
	 */
	public static final String BAR_3D = "bar_3d";

	/**
	 * 渐变柱状图
	 */
	public static final String BAR_FADE = "bar_fade";

	/**
	 * 玻璃状柱状图
	 */
	public static final String BAR_GLASS = "bar_glass";

	/**
	 * 填充状柱状图，包含边框
	 */
	public static final String BAR_FILLED = "filled_bar";

	/**
	 * 折线图
	 */
	// public static final String LINE = "line";

	/**
	 * 折线实心点图
	 */
	public static final String LINE_DOT = "line_dot";

	/**
	 * 折线空心点图
	 */
	public static final String LINE_HOLLOW = "line_hollow";

	/**
	 * 折线区域图
	 */
	public static final String AREA_HOLLOW = "area_hollow";

	/**
	 * 饼图
	 */
	public static final String PIE = "pie";

	/**
	 * 3d饼图
	 */
	// public static final String PIE_3D = "pie_3d";

	/**
	 * 蜡烛图
	 */
	public static final String CANDLE = "candle";

	/**
	 * 离散图
	 */
	public static final String SCATTER = "scatter";

	/**
	 * 数据集index，即指标index，大于等于1
	 */
	private int index = 0;

	/**
     *
     */
	public Shape() {
		super();
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		if (index <= 0)
			return;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	protected String getRandomColor(int i) {
		Map colorMap = new HashMap();
		colorMap.put("1", "#6600FF");// blue
		colorMap.put("2", "#CC0000");// red
		colorMap.put("3", "#FFFF33");// yellow
		colorMap.put("4", "#00FF00");// green
		colorMap.put("5", "#AABBCC");
		colorMap.put("6", "#DFEA03");
		colorMap.put("7", "#77DD33");
		colorMap.put("8", "#456789");
		colorMap.put("9", "#CCAA22");
		colorMap.put("0", "#6611FF");

		return (String) colorMap.get(String.valueOf(i % 10));
	}

	public abstract String toText();

	public abstract String toHtml();

	/**
	 * @return the autoColor
	 */
	public boolean isAutoColor() {
		return autoColor;
	}

	/**
	 * @param autoColor
	 *            the autoColor to set
	 */
	public void setAutoColor(boolean autoColor) {
		this.autoColor = autoColor;
	}

}
