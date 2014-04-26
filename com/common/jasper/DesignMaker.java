package com.common.jasper;

import java.awt.Color;

import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.base.JRBaseBox;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignReportFont;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;

public class DesignMaker {

	protected static int max(int cand1, int cand2) {
		return (cand1 > cand2) ? cand1 : cand2;
	}

	// -----------------------------------------------------
	// Maker for Design Elements and Data Elements
	// -----------------------------------------------------
	/**
	 * 创建并返回一个JRDesignReportFont
	 */
	public static JRDesignReportFont makeReportFont() {
		return new JRDesignReportFont();
	}

	/**
	 * 创建并返回一个JRDesignReportFont数组
	 */
	public static JRDesignReportFont[] makeReportFonts(int size) {
		size = max(0, size);

		JRDesignReportFont[] fonts = new JRDesignReportFont[size];

		for (int i = 0; i < size; i++) {
			fonts[i] = new JRDesignReportFont();
		}

		return fonts;
	}

	/**
	 * 设置jdesign的字体
	 */
	public static void setReportFonts(JasperDesign jdesign,
			JRDesignReportFont[] fonts) {
		if (jdesign == null || fonts == null) {
			return;
		}

		for (int i = 0; i < fonts.length; i++) {
			try {
				jdesign.addFont(fonts[i]);
			} catch (JRException ex) {
				// Execption handling
			}
		}
	}

	/**
	 * 创建并返回一个JRDesignParameter
	 */
	public static JRDesignParameter makeParameter() {
		return new JRDesignParameter();
	}

	/**
	 * 创建并返回一个JRDesignParameter数组
	 */
	public static JRDesignParameter[] makeParameters(int size) {

		size = max(0, size);

		JRDesignParameter[] parameters = new JRDesignParameter[size];

		for (int i = 0; i < size; i++) {
			parameters[i] = new JRDesignParameter();
		}

		return parameters;
	}

	/**
	 * 设置jdesign的参数
	 */
	public static void setParameters(JasperDesign jdesign,
			JRDesignParameter[] parameters) {
		if (jdesign == null || parameters == null) {
			return;
		}

		for (int i = 0; i < parameters.length; i++) {
			try {
				jdesign.addParameter(parameters[i]);
			} catch (JRException ex) {
				// Execption handling
			}
		}
	}

	/**
	 * 创建并返回一个JRDesignField
	 */
	public static JRDesignField makeField() {
		return new JRDesignField();
	}

	/**
	 * 创建并返回一个JRDesignField数组
	 */
	public static JRDesignField[] makeFields(int size) {

		size = max(0, size);

		JRDesignField[] fields = new JRDesignField[size];

		for (int i = 0; i < size; i++) {
			fields[i] = new JRDesignField();
		}

		return fields;
	}

	/**
	 * 设置jdesign的字段
	 */
	public static void setFields(JasperDesign jdesign, JRDesignField[] fields) {
		if (jdesign == null || fields == null) {
			return;
		}

		for (int i = 0; i < fields.length; i++) {
			try {
				jdesign.addField(fields[i]);
			} catch (JRException ex) {
				// Execption handling
			}
		}
	}

	/**
	 * 创建并返回一个JRDesignBand
	 */
	public static JRDesignBand makeBand() {
		return new JRDesignBand();
	}

	/**
	 * 创建并返回一个JRDesignStaticText
	 */
	public static JRDesignStaticText makeStaticText() {
		return new JRDesignStaticText();
	}

	/**
	 * 创建并返回一个JRDesignStaticText数组
	 */
	public static JRDesignStaticText[] makeStaticTexts(int size) {
		size = max(0, size);

		JRDesignStaticText[] staticTexts = new JRDesignStaticText[size];

		for (int i = 0; i < size; i++) {
			staticTexts[i] = new JRDesignStaticText();
		}

		return staticTexts;
	}

	/**
	 * 创建并返回一个JRDesignTextField
	 */
	public static JRDesignTextField makeTextField() {
		return new JRDesignTextField();
	}

	/**
	 * 创建并返回一个JRDesignTextField数组
	 */
	public static JRDesignTextField[] makeTextFields(int size) {
		size = max(0, size);

		JRDesignTextField[] textFields = new JRDesignTextField[size];

		for (int i = 0; i < size; i++) {
			textFields[i] = new JRDesignTextField();
		}

		return textFields;
	}

	/**
	 * 设置band的元素
	 */
	public static void setElements(JRDesignBand band, JRDesignElement[] elements) {
		if (band == null || elements == null) {
			return;
		}

		for (int i = 0; i < elements.length; i++) {
			band.addElement(elements[i]);
		}
	}

	/**
	 * 创建并返回一个JRDesignExpression
	 */
	public static JRDesignExpression makeExpression() {
		return new JRDesignExpression();
	}

	/**
	 * 创建并返回一个JRDesignExpression数组
	 */
	public static JRDesignExpression[] makeExpressions(int size) {
		size = max(0, size);

		JRDesignExpression[] expressions = new JRDesignExpression[size];

		for (int i = 0; i < size; i++) {
			expressions[i] = new JRDesignExpression();
		}

		return expressions;
	}

	/**
	 * 创建并返回一个JRBaseBox
	 */
	public static JRBaseBox makeBox() {
		return new JRBaseBox();
	}

	/**
	 * 创建并返回一个JRBaseBox数组
	 */
	public static JRBaseBox[] makeBoxes(int size) {
		size = max(0, size);

		JRBaseBox[] boxes = new JRBaseBox[size];

		for (int i = 0; i < size; i++) {
			boxes[i] = new JRBaseBox();
		}

		return boxes;
	}

	// -----------------------------------------------------
	// Setter for Design Elements and Data Elements
	// -----------------------------------------------------

	/**
	 * 设置JasperDesign的页面宽度和高度，列的宽度和列间空白宽度
	 */
	public static void setPageArea(JasperDesign jdesign, int pageWidth,
			int pageHeight, int columnWidth, int columnSpacing) {
		jdesign.setPageWidth(pageWidth);
		jdesign.setPageHeight(pageHeight);
		jdesign.setColumnWidth(columnWidth);
		jdesign.setColumnSpacing(columnSpacing);
	}

	/**
	 * 设置JasperDesign四周距页面边缘的空白宽度
	 */
	public static void setPageMargin(JasperDesign jdesign, int horizontal,
			int vartical) {
		jdesign.setLeftMargin(horizontal);
		jdesign.setRightMargin(horizontal);
		jdesign.setTopMargin(vartical);
		jdesign.setBottomMargin(vartical);
	}

	/**
	 * 设置JasperDesign内容的朝向和换页时是否打印标题和总结部分
	 */
	public static void setPageStyle(JasperDesign jdesign, byte orientation,
			boolean floatColumnFooter) {
		jdesign.setOrientation(orientation);
		jdesign.setFloatColumnFooter(floatColumnFooter);
	}

	/**
	 * 设置JRDesignReportFont是否作为报表默认字体，以及字体名称和大小
	 */
	public static void setFontInfo(JRDesignReportFont font, boolean isDefault,
			String fontName, int size) {

		font.setDefault(isDefault);
		font.setFontName(fontName);
		font.setSize(size);
	}

	/**
	 * 设置JRDesignReportFont的格式，包括粗体、斜体、下划线和中间划线
	 */
	public static void setFontStyle(JRDesignReportFont font, boolean bold,
			boolean italic, boolean underline, boolean strikeThrough) {
		font.setBold(bold);
		font.setItalic(italic);
		font.setUnderline(underline);
		font.setStrikeThrough(strikeThrough);
	}

	/**
	 * 设置JRDesignReportFont打印成PDF时的字体设置
	 */
	public static void setFontPdf(JRDesignReportFont font, String pdfFontName,
			String pdfEncoding, boolean pdfEmbedded) {
		font.setPdfFontName(pdfFontName);
		font.setPdfEncoding(pdfEncoding);
		font.setPdfEmbedded(pdfEmbedded);
	}

	/**
	 * 设置JRDesignParameter的名称和类型
	 */
	public static void setNameAndClass(JRDesignParameter parameter,
			String name, Class valueClass) {
		parameter.setName(name);
		parameter.setValueClass(valueClass);
	}

	/**
	 * 设置JRDesignField的名称和类型
	 */
	public static void setNameAndClass(JRDesignField field, String name,
			Class valueClass) {
		field.setName(name);
		field.setValueClass(valueClass);
	}

	/**
	 * 设置JRDesignExpression的表达式文本和类型
	 */
	public static void setTextAndClass(JRDesignExpression expression,
			String text, Class valueClass) {
		expression.setText(text);
		expression.setValueClass(valueClass);
	}

	/**
	 * 设置JRDesignElement的起始位置以及宽度和长度
	 */
	public static void setElementDimension(JRDesignElement element, int x,
			int y, int width, int height) {
		element.setX(x);
		element.setY(y);
		element.setWidth(width);
		element.setHeight(height);
	}

	/**
	 * 设置JRDesignTextElement的水平和垂直方向上的对齐方式
	 */
	public static void setTextAlignment(JRDesignTextElement text,
			byte horizontalAlignment, byte verticalAlignment) {
		text.setHorizontalAlignment(horizontalAlignment);
		text.setVerticalAlignment(verticalAlignment);
	}

	/**
	 * 设置JRDesignTextElement数组的水平和垂直方向上的对齐方式
	 */
	public static void setTextAlignments(JRDesignTextElement[] texts,
			byte horizontalAlignment, byte verticalAlignment) {
		for (int i = 0; i < texts.length; i++) {
			texts[i].setHorizontalAlignment(horizontalAlignment);
			texts[i].setVerticalAlignment(verticalAlignment);
		}
	}

	/**
	 * 设置JRDesignTextElement数组的的Box元素
	 */
	public static void setTextBoxes(JRDesignTextElement[] texts,
			JRBaseBox[] boxes) {
		int length = (texts.length <= boxes.length) ? texts.length
				: boxes.length;
		for (int i = 0; i < length; i++) {
			texts[i].setBox(boxes[i]);
		}
	}

	/**
	 * 设置Box的边框的宽度
	 */
	public static void setBoxBorder(JRBaseBox box, byte left, byte right,
			byte top, byte bottom) {
		box.setLeftBorder(left);
		box.setRightBorder(right);
		box.setTopBorder(top);
		box.setBottomBorder(bottom);
	}

	/**
	 * 设置Box数组的边框的宽度
	 */
	public static void setBoxBorders(JRBaseBox[] boxes, byte left, byte right,
			byte top, byte bottom) {
		for (int i = 0; i < boxes.length; i++) {
			boxes[i].setLeftBorder(left);
			boxes[i].setRightBorder(right);
			boxes[i].setTopBorder(top);
			boxes[i].setBottomBorder(bottom);
		}
	}

	/**
	 * 设置Box边框距内容的距离
	 */
	public static void setBoxPadding(JRBaseBox box, int left, int right,
			int top, int bottom) {
		box.setLeftPadding(left);
		box.setRightPadding(right);
		box.setTopPadding(top);
		box.setBottomPadding(bottom);
	}

	/**
	 * 设置Box数组边框距内容的距离
	 */
	public static void setBoxPaddings(JRBaseBox[] boxes, int left, int right,
			int top, int bottom) {
		for (int i = 0; i < boxes.length; i++) {
			boxes[i].setLeftPadding(left);
			boxes[i].setRightPadding(right);
			boxes[i].setTopPadding(top);
			boxes[i].setBottomPadding(bottom);
		}
	}

	// -----------------------------------------------------------
	// Higher Level Setter for Design Elements and Data Elements
	// -----------------------------------------------------------

	/**
	 * 设置JRDesignTextField的表达式
	 */
	public static void setExpression(JRDesignTextField field, String text,
			Class valueClass) {
		JRDesignExpression expression = DesignMaker.makeExpression();
		DesignMaker.setTextAndClass(expression, text, valueClass);
		field.setExpression(expression);
	}

	/**
	 * 设置JRDesignTextElement数组的字体
	 */
	public static void setTextFonts(JRDesignTextElement[] texts, JRFont font) {
		for (int i = 0; i < texts.length; i++) {
			texts[i].setFont(font);
			texts[i].setFont(font);
		}
	}

	/**
	 * 设置JRDesignTextElement数组在值为null时是否显示为空白
	 */
	public static void setTextNullBlanks(JRDesignTextField[] texts,
			boolean isBlank) {
		for (int i = 0; i < texts.length; i++) {
			texts[i].setBlankWhenNull(isBlank);
		}
	}

	/**
	 * 设置JRDesignTextElement数组的颜色
	 */
	public static void setTextColors(JRDesignTextElement[] texts,
			Color forecolor, Color backcolor) {
		for (int i = 0; i < texts.length; i++) {
			texts[i].setMode(JRElement.MODE_OPAQUE);
			texts[i].setForecolor(forecolor);
			texts[i].setBackcolor(backcolor);
		}
	}
}
