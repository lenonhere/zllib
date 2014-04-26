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
public class Font {

	private String fontName;

	private int fontSize = 10;

	private String fontColor = "#FF0000";

	/**
     *
     */
	public Font() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param fontName
	 * @param fontSize
	 * @param fontColor
	 */
	public Font(String fontName, int fontSize, String fontColor) {
		super();
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.fontColor = fontColor;
	}

	/**
	 * @return the fontName
	 */
	public String getFontName() {
		return fontName;
	}

	/**
	 * @param fontName
	 *            the fontName to set
	 */
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	/**
	 * @return the fontSize
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @return the fontColor
	 */
	public String getFontColor() {
		return fontColor;
	}

	/**
	 * @param fontColor
	 *            the fontColor to set
	 */
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

}
