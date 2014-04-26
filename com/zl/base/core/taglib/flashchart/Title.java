/**
 * @author: 朱忠南
 * @date: Jul 9, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe: 图标标题
 * @modify_author:
 * @modify_time:
 */
package com.zl.base.core.taglib.flashchart;

/**
 * @author jokin
 * @date Jul 9, 2008
 */
public class Title {
	/**
	 * 标题名称
	 */
	private String name;

	/**
	 * 字体大小
	 */
	private int fontSize = 14;

	private String fontFamily = "华文楷体";

	/**
	 * 字体颜色
	 */
	private String color = "#FFFFFF";

	/**
	 * 背景色
	 */
	private String backgroundColor = "#505050";

	/**
	 * 与表格最顶端的空白处
	 */
	private int margin = 5;

	/**
	 * 标题单元格内容于左侧边框空白处
	 */
	private int paddingLeft = 20;

	/**
	 * 标题单元格内容于右侧边框空白处
	 */
	private int paddingRight = 20;

	/**
	 * 标题单元格内容于上侧边框空白处
	 */
	private int paddingTop = 5;

	/**
	 * 标题单元格内容于下侧边框空白处
	 */
	private int paddingBottom = 5;

	/**
	 * 构造一个图表标题Title对象
	 *
	 * @param name
	 *            图表标题
	 */
	public Title(String name) {
		super();
		this.name = name;
	}

	/**
	 * 获取标题
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置标题
	 *
	 * @param name
	 *            标题
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取标题字体大小
	 *
	 * @return the fontSize
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * 设置标题字体大小
	 *
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * 获取标题颜色
	 *
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * 设置标题颜色
	 *
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 获取标题框背景色
	 *
	 * @return the backgroundColor
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * 设置标题框背景色
	 *
	 * @param backgroundColor
	 *            the backgroundColor to set
	 */
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * 获取标题框与图标最上方的距离
	 *
	 * @return the margin
	 */
	public int getMargin() {
		return margin;
	}

	/**
	 * 设置标题框与图标最上方的距离
	 *
	 * @param margin
	 *            the margin to set
	 */
	public void setMargin(int margin) {
		this.margin = margin;
	}

	/**
	 * 获取标题与标题框左侧的距离
	 *
	 * @return the paddingLeft
	 */
	public int getPaddingLeft() {
		return paddingLeft;
	}

	/**
	 * 设置标题与标题框左侧的距离
	 *
	 * @param paddingLeft
	 *            the paddingLeft to set
	 */
	public void setPaddingLeft(int paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	/**
	 * 获取标题与标题框右侧的距离
	 *
	 * @return the paddingRight
	 */
	public int getPaddingRight() {
		return paddingRight;
	}

	/**
	 * 设置标题与标题框右侧的距离
	 *
	 * @param paddingRight
	 *            the paddingRight to set
	 */
	public void setPaddingRight(int paddingRight) {
		this.paddingRight = paddingRight;
	}

	/**
	 * 获取标题与标题框上方的距离
	 *
	 * @return the paddingTop
	 */
	public int getPaddingTop() {
		return paddingTop;
	}

	/**
	 * 设置标题与标题框上方的距离
	 *
	 * @param paddingTop
	 *            the paddingTop to set
	 */
	public void setPaddingTop(int paddingTop) {
		this.paddingTop = paddingTop;
	}

	/**
	 * 获取标题与标题框下方的距离
	 *
	 * @return the paddingBottom
	 */
	public int getPaddingBottom() {
		return paddingBottom;
	}

	/**
	 * 设置标题与标题框下方的距离
	 *
	 * @param paddingBottom
	 *            the paddingBottom to set
	 */
	public void setPaddingBottom(int paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	/**
	 * 获取标题字体类型 注：暂时不起作用
	 *
	 * @return the fontFamily
	 */
	public String getFontFamily() {
		return fontFamily;
	}

	/**
	 * 设置标题字体类型 注：暂时不起作用
	 *
	 * @param fontFamily
	 *            the fontFamily to set
	 */
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	/**
	 * 显示成数据格式 如：&title=这里是标题,{font-size:20px; color: #FFFFFF; margin: 5px;
	 * background-color: #505050; padding:5px 20px 5px 20px;}&
	 *
	 * @return 返回数据格式
	 * @author: 朱忠南
	 */
	public String toText() {
		StringBuffer sb = new StringBuffer();
		sb.append("&title=");
		sb.append(name).append(",{");
		sb.append("font-size:").append(fontSize).append("px; ");
		sb.append("font-family:").append(fontFamily).append("; ");
		sb.append("color:").append(color).append("; ");
		sb.append("margin:").append(margin).append("px; ");
		sb.append("background-color:").append(backgroundColor).append("; ");
		sb.append("padding:").append(paddingTop).append("px ");
		sb.append(paddingRight).append("px ");
		sb.append(paddingBottom).append("px ");
		sb.append(paddingLeft).append("px; }&\n");
		return sb.toString();
	}

	/**
	 * 显示成html格式 如：mychart.addVariable('title','这里是标题,{font-size:20px; color:
	 * #FFFFFF; margin: 5px; background-color: #505050; padding:5px 20px 5px
	 * 20px;}');
	 *
	 * @return html格式数据
	 * @author: 朱忠南
	 */
	public String toHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("mychart.addVariable(");
		sb.append("'title','");
		sb.append(name).append(",{");
		sb.append("font-size:").append(fontSize).append("px; ");
		sb.append("font-family:").append(fontFamily).append("; ");
		sb.append("color:").append(color).append("; ");
		sb.append("margin:").append(margin).append("px; ");
		sb.append("background-color:").append(backgroundColor).append("; ");
		sb.append("padding:").append(paddingTop).append("px ");
		sb.append(paddingRight).append("px ");
		sb.append(paddingBottom).append("px ");
		sb.append(paddingLeft).append("px; }');\n");
		return sb.toString();
	}

	public static void main(String[] args) {
		Title title = new Title("这里是标题");
		System.out.println(title.toText());
		System.out.println(title.toHtml());
	}

}
