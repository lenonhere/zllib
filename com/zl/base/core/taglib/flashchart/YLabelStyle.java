/**
 * @author: 朱忠南
 * @date: Aug 8, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe:
 * @modify_author:
 * @modify_time:
 */
package com.zl.base.core.taglib.flashchart;

/**
 * @author jokin
 * @date Aug 8, 2008
 */
public class YLabelStyle {

	/**
	 * 字体
	 */
	private Font font;

	/**
     *
     */
	public YLabelStyle() {
		super();
		font = new Font();
	}

	public YLabelStyle(Font font) {
		this.font = font;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font
	 *            the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	public String toHTML() {
		return "mychart.addVariable('y_label_style','"
				+ this.font.getFontSize() + "," + this.font.getFontColor()
				+ "');\n";
	}

	public String toText() {
		return "&y_label_style=" + this.font.getFontSize() + ","
				+ this.font.getFontColor() + "&\n";
	}

	/**
	 * @author: 朱忠南
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
