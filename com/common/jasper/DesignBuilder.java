package com.common.jasper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGraphicElement;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseBox;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignReportFont;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;

abstract public class DesignBuilder {

	protected static final byte ALIGN_TOP = JRAlignment.VERTICAL_ALIGN_TOP;
	protected static final byte ALIGN_MIDDLE = JRAlignment.VERTICAL_ALIGN_MIDDLE;
	protected static final byte ALIGN_BOTTOM = JRAlignment.VERTICAL_ALIGN_BOTTOM;

	protected static final byte ALIGN_CENTER = JRAlignment.HORIZONTAL_ALIGN_CENTER;
	protected static final byte ALIGN_LEFT = JRAlignment.HORIZONTAL_ALIGN_LEFT;
	protected static final byte ALIGN_RIGHT = JRAlignment.HORIZONTAL_ALIGN_RIGHT;

	protected static final byte PEN_POINT = JRGraphicElement.PEN_1_POINT;
	protected static final byte PEN_NONE = JRGraphicElement.PEN_NONE;

	protected DesignPaper paper = null; // 打印纸张类型

	protected JasperDesign jdesign = new JasperDesign(); // 报表类

	protected List results = null; // 查询结果

	protected List captions = null; // 查询标题

	protected Map parameters = null; // 参数

	protected int[] widths = null; // 各列宽度

	protected List fieldNames = new ArrayList(); // 结果中存在的列

	protected List ignoreFields = new ArrayList(); // 标题中需要忽略的列

	public DesignBuilder(String name) {
		jdesign.setName(name);
	}

	public DesignBuilder(String name, List ignoreFields) {
		jdesign.setName(name);
		if (ignoreFields != null) {
			this.ignoreFields = ignoreFields;
		}
	}

	public void init(Map parameters, List captions, List results) {
		parameters.put("_InnnerEmmptyVal_", "");
		this.parameters = parameters;
		this.captions = DesignUtil.filter(captions, this.ignoreFields);
		this.results = results;
		pickPage();
	}

	/**
	 * 每个子类需实现这个类以确保调用正确的generate*系列的方法
	 */
	public abstract JasperReport build(Map parameters, List captions,
			List results);

	/**
	 * 设置报表的基本尺寸
	 */
	protected void generateDesign() {
		int pageWidth = paper.getPageWidth();
		int pageHeight = paper.getPageHeight();

		int columnWidth = paper.getColumnWidth();
		int columnHeight = paper.getColumnHeight();

		byte orientation = paper.getOrientation();

		int horizontal = paper.getHorizontalMargin();
		int vartical = paper.getVerticalMargin();

		// 如果存在纸张的参数，则根据参数选取纸张，否则计算列头的长度以选取合适的纸张
		String loc_paper = (String) parameters.get("paper");

		// 如果指定纸张类型，则使用指定类型（也不进行纸张转置）
		if (loc_paper != null && !"".equals(loc_paper)) {
			widths = recalculateWidths(columnWidth);
			if (widths.length > 0) {
				int realColumnWidth = widths[widths.length - 1];
				columnWidth = realColumnWidth;
				horizontal = (pageWidth - realColumnWidth) / 2;
			}
		} else {
			widths = DesignUtil.calculateFieldWidths(captions, columnWidth,
					columnHeight);

			if (widths.length > 0) {
				int realColumnWidth = widths[widths.length - 1];

				// 检查column的是否宽度过大，需要转置纸张
				boolean inverse = (realColumnWidth > columnWidth && columnWidth < columnHeight);

				if (inverse) {
					orientation = JRReport.ORIENTATION_LANDSCAPE;
					int temp = pageWidth;
					pageWidth = pageHeight;
					pageHeight = temp;
					vartical = horizontal;
				}

				columnWidth = realColumnWidth;
				horizontal = (pageWidth - realColumnWidth) / 2;
			}
		}

		DesignMaker.setPageArea(jdesign, pageWidth, pageHeight, columnWidth, 0);
		DesignMaker.setPageStyle(jdesign, orientation, true);
		DesignMaker.setPageMargin(jdesign, horizontal, vartical);
	}

	/**
	 * 设置预先定义的报表字体
	 */
	protected void generateFonts() {
		JRDesignReportFont[] loc_fonts = DesignFont.getFonts();

		for (int i = 0; i < loc_fonts.length; i++) {
			try {
				jdesign.addFont(loc_fonts[i]);
			} catch (JRException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 设置报表使用的参数
	 */
	protected void generateParameters() {

		Set keys = parameters.keySet();

		int size = parameters.size();
		JRDesignParameter[] params = DesignMaker.makeParameters(size);

		int i = 0;
		for (Iterator iter = keys.iterator(); iter.hasNext(); i++) {
			String key = (String) iter.next();
			Object value = parameters.get(key);
			if (value == null) {
				DesignMaker.setNameAndClass(params[i], key, String.class);
			} else {
				DesignMaker.setNameAndClass(params[i], key, value.getClass());
			}
		}

		DesignMaker.setParameters(jdesign, params);
	}

	/**
	 * 设置报表使用的字段
	 */
	protected void generateFields() {
		DynaProperty[] properties = DesignUtil.getDynaProperties(results);

		JRDesignField[] fields = DesignMaker.makeFields(properties.length);

		for (int i = 0; i < properties.length; i++) {
			String name = DesignUtil.wrapProperty(properties[i].getName());
			Class clazz = properties[i].getType();
			DesignMaker.setNameAndClass(fields[i], name, clazz);
			fieldNames.add(name);
		}

		DesignMaker.setFields(jdesign, fields);
	}

	/**
	 * 设置报表的标题，标题内容来自参数title
	 */
	protected void generateTitle() {

		JRDesignBand band = DesignMaker.makeBand();
		band.setHeight(0);
		jdesign.setTitle(band);
	}

	/**
	 * 设置报表列标题
	 */
	protected void generateColumnHeader() {

		if (captions == null || captions.size() == 0) {
			return;
		}

		int size = captions.size();

		String[] subCaptions = new String[size];
		String[] superCaptions = new String[size];

		// 需要静态文本的总数
		int textCount = size;

		String head = null;
		// 检查分割复合的Captions
		for (int i = 0; i < size; i++) {
			DynaBean bean = (DynaBean) captions.get(i);
			String caption = (String) bean.get("caption");

			if (caption == null || "".equals(caption)) {
				caption = (String) bean.get("property");
			}

			String[] tokens = caption.split("##");

			if (tokens.length <= 1) {
				superCaptions[i] = null;
				subCaptions[i] = tokens[0];
			} else {
				superCaptions[i] = tokens[0];
				subCaptions[i] = tokens[1];

				if (head == null || !head.equals(tokens[0])) {
					textCount++;
					head = tokens[0];
				}
			}
		}

		JRDesignStaticText[] staticTexts = DesignMaker
				.makeStaticTexts(textCount);
		JRBaseBox[] boxes = DesignMaker.makeBoxes(textCount);

		// 如果有符合标题，将其中非复合的标题的高度增加一倍
		int heightScale = (textCount > size) ? 2 : 1;

		JRDesignBand band = DesignMaker.makeBand();
		band.setHeight(30 * heightScale);

		int index = 0;
		int x = 0;
		int width = 0;
		int superWidth = 0;

		for (int i = 0; i < size; i++) {
			x += width;
			width = widths[i];

			byte right_pen = (i == size - 1) ? JRGraphicElement.PEN_1_POINT
					: JRGraphicElement.PEN_NONE;
			if (superCaptions[i] == null) {
				// 本标题不是复合标题的子标题
				DesignMaker.setElementDimension(staticTexts[index], x, 0,
						width, 30 * heightScale);
				staticTexts[index].setText(subCaptions[i]);
				DesignMaker.setBoxBorder(boxes[index], PEN_POINT, right_pen,
						PEN_POINT, PEN_POINT);

				index++;
			} else {
				// 本标题是复合标题的子标题
				DesignMaker.setElementDimension(staticTexts[index], x, 30,
						width, 30);
				staticTexts[index].setText(subCaptions[i]);

				DesignMaker.setBoxBorder(boxes[index], PEN_POINT, right_pen,
						PEN_NONE, PEN_POINT);

				index++;

				superWidth += width;

				if (i == size - 1
						|| !superCaptions[i].equals(superCaptions[i + 1])) {
					// 同属一个父标题的子标题结束
					DesignMaker.setElementDimension(staticTexts[index], x
							+ width - superWidth, 0, superWidth, 30);
					staticTexts[index].setText(superCaptions[i]);

					DesignMaker.setBoxBorder(boxes[index], PEN_POINT,
							right_pen, PEN_POINT, PEN_POINT);
					index++;
					// 父标题的长度清零
					superWidth = 0;
				}
			}
		}

		DesignMaker.setBoxPaddings(boxes, 1, 1, 1, 1);

		DesignMaker.setTextBoxes(staticTexts, boxes);

		DesignMaker.setTextAlignments(staticTexts, ALIGN_CENTER, ALIGN_MIDDLE);

		DesignMaker.setTextColors(staticTexts, Color.black, Color.lightGray);
		DesignMaker.setTextFonts(staticTexts, DesignFont.COLUMN_HEADER);

		DesignMaker.setElements(band, staticTexts);
		jdesign.setColumnHeader(band);
	}

	/**
	 * 设置报表的明细
	 */
	protected void generateDetail() {

		if (results == null || results.size() == 0) {
			return;
		}

		if (captions == null || captions.size() == 0) {
			return;
		}

		JRDesignBand band = DesignMaker.makeBand();
		band.setHeight(22);

		int size = captions.size();
		JRDesignTextField[] textFields = DesignMaker.makeTextFields(size);
		JRBaseBox[] boxes = DesignMaker.makeBoxes(size);
		int x = 0;
		int width = 0;

		for (int i = 0; i < size; i++) {
			// 设置JRDesignText占有的区域
			x += width;
			width = widths[i];
			DesignMaker.setElementDimension(textFields[i], x, 0, width, 22);

			DynaBean bean = (DynaBean) captions.get(i);

			// 设置对齐方式(默认左对齐)
			String align = (String) bean.get("align");
			byte horizontalAlign = ALIGN_LEFT;
			if ("right".equalsIgnoreCase(align)) {
				horizontalAlign = ALIGN_RIGHT;
			} else if ("center".equalsIgnoreCase(align)) {
				horizontalAlign = ALIGN_CENTER;
			}
			DesignMaker.setTextAlignment(textFields[i], horizontalAlign,
					ALIGN_MIDDLE);

			String property = ((String) bean.get("property")).trim();
			Class clazz = DesignUtil.getValueClass(results, property);

			// 设置格式化表达式
			String format = (String) bean.get("format");
			if (!"".equals(format) && clazz != String.class) {
				textFields[i].setPattern(format);
			}

			// 设置求值表达式
			if (!"".equals(property)) {
				if (fieldNames.contains(DesignUtil.wrapProperty(property))) {
					DesignMaker.setExpression(textFields[i],
							"$F{" + DesignUtil.wrapProperty(property) + "}",
							clazz);
				} else {
					DesignMaker.setExpression(textFields[i],
							"$P{_InnnerEmmptyVal_}", clazz);
				}
			}
			// 设置边框
			byte right_pen = JRGraphicElement.PEN_NONE;
			if (i + 1 == size) { // 最后一个字段
				right_pen = JRGraphicElement.PEN_1_POINT;
			}
			DesignMaker.setBoxBorder(boxes[i], PEN_POINT, right_pen, PEN_NONE,
					PEN_POINT);
			DesignMaker.setBoxPadding(boxes[i], 1, 1, 1, 1);
			textFields[i].setBox(boxes[i]);
			textFields[i]
					.setStretchType(JRElement.STRETCH_TYPE_RELATIVE_TO_TALLEST_OBJECT);
			textFields[i].setStretchWithOverflow(true);
			textFields[i].setPrintWhenDetailOverflows(true);
		}

		DesignMaker.setTextFonts(textFields, DesignFont.DETAIL);
		DesignMaker.setTextNullBlanks(textFields, true);

		DesignMaker.setElements(band, textFields);
		jdesign.setDetail(band);
	}

	/**
	 * 设置列脚
	 */
	protected void generateColumnFooter() {
		JRDesignBand band = DesignMaker.makeBand();
		band.setHeight(25);

		JRDesignStaticText[] staticTexts = DesignMaker.makeStaticTexts(2);
		JRDesignTextField[] textFields = DesignMaker.makeTextFields(2);

		DesignMaker.setElementDimension(staticTexts[0], 0, 5, 50, 20);
		staticTexts[0].setText("制表人:");

		Object obj = parameters.get("creator");
		Class clazz = (obj == null) ? String.class : obj.getClass();
		DesignMaker.setElementDimension(textFields[0], 50, 5, 80, 20);
		DesignMaker.setExpression(textFields[0], "$P{creator}", clazz);

		int width = jdesign.getColumnWidth();

		DesignMaker.setElementDimension(staticTexts[1], width - 140, 5, 60, 20);
		staticTexts[1].setText("制表日期:");

		obj = parameters.get("createdDate");
		clazz = (obj == null) ? String.class : obj.getClass();
		DesignMaker.setElementDimension(textFields[1], width - 80, 5, 80, 20);
		DesignMaker.setExpression(textFields[1], "$P{createdDate}", clazz);

		DesignMaker.setTextAlignments(staticTexts, ALIGN_CENTER, ALIGN_TOP);
		DesignMaker.setTextAlignments(textFields, ALIGN_CENTER, ALIGN_TOP);

		DesignMaker.setTextFonts(staticTexts, DesignFont.COLUMN_FOOTER);
		DesignMaker.setTextFonts(textFields, DesignFont.COLUMN_FOOTER);

		DesignMaker.setElements(band, staticTexts);
		DesignMaker.setElements(band, textFields);

		jdesign.setColumnFooter(band);
	}

	/**
	 * 设置页脚
	 */
	protected void generatePageFooter() {
		JRDesignBand band = DesignMaker.makeBand();
		band.setHeight(30);

		JRDesignStaticText[] staticTexts = DesignMaker.makeStaticTexts(1);
		JRDesignTextField[] textFields = DesignMaker.makeTextFields(2);

		int x = (jdesign.getColumnWidth() / 2) - 30;
		int width = 0;

		width = 20;
		DesignMaker.setElementDimension(textFields[0], x, 0, width, 30);
		DesignMaker.setExpression(textFields[0], "$V{PAGE_NUMBER}",
				Integer.class);

		x += width;
		width = 5;
		DesignMaker.setElementDimension(staticTexts[0], x, 0, width, 30);
		staticTexts[0].setText("/");

		x += width;
		width = 20;
		DesignMaker.setElementDimension(textFields[1], x, 0, width, 30);
		DesignMaker.setExpression(textFields[1], "$V{PAGE_NUMBER}",
				Integer.class);
		textFields[1].setEvaluationTime(JRExpression.EVALUATION_TIME_REPORT);

		DesignMaker.setTextAlignments(staticTexts, ALIGN_CENTER, ALIGN_BOTTOM);
		DesignMaker.setTextAlignments(textFields, ALIGN_CENTER, ALIGN_BOTTOM);

		DesignMaker.setTextFonts(staticTexts, DesignFont.PAGE_FOOTER);
		DesignMaker.setTextFonts(textFields, DesignFont.PAGE_FOOTER);

		DesignMaker.setElements(band, staticTexts);
		DesignMaker.setElements(band, textFields);

		jdesign.setPageFooter(band);
	}

	/**
	 * 设置总结
	 */
	protected void generateSummary() {
		JRDesignBand band = DesignMaker.makeBand();
		band.setHeight(0);
		jdesign.setSummary(band);
	}

	/**
	 * 编译JasperDesign并返回编译后的JasperReport
	 */
	protected JasperReport compile() {
		JasperReport report = null;
		try {
			report = JasperCompileManager.compileReport(jdesign);
		} catch (JRException ex) {
			ex.printStackTrace();
		}

		return report;
	}

	/**
	 * 在PageHeader中预设置title
	 */
	public int initTitle(JRDesignBand band) {

		int height = 40;
		JRDesignTextField textField = new JRDesignTextField();

		int width = jdesign.getColumnWidth();
		DesignMaker.setElementDimension(textField, 0, 0, width, height);
		DesignMaker.setTextAlignment(textField, ALIGN_CENTER, ALIGN_TOP);

		Object obj = parameters.get("title");
		Class clazz = (obj == null) ? String.class : obj.getClass();
		DesignMaker.setExpression(textField, "$P{title}", clazz);
		textField.setFont(DesignFont.TITLE);
		textField.setForecolor(Color.red);
		band.addElement(textField);

		return height;
	}

	/**
	 * 设置JRDesignTextField的辅助函数
	 */
	protected void setTextFieldAll(JRDesignTextField[] textFields, int pos,
			int x, int y, int width, int height, String property) {
		Object obj = parameters.get(property);
		Class clazz = (obj == null) ? String.class : obj.getClass();
		DesignMaker.setElementDimension(textFields[pos], x, y, width, height);
		DesignMaker.setExpression(textFields[pos], "$P{" + property + "}",
				clazz);
	}

	/**
	 * 设置JRDesignStaticText的辅助函数
	 */
	protected void setStaticTextAll(JRDesignStaticText[] staticTexts, int pos,
			int x, int y, int width, int height, String text) {
		DesignMaker.setElementDimension(staticTexts[pos], x, y, width, height);
		staticTexts[pos].setText(text);
	}

	/**
	 * 选取报表使用的纸张
	 */
	protected void pickPage() {

		// 如果存在纸张的参数，则根据参数选取纸张，否则计算列头的长度以选取合适的纸张
		String loc_paper = (String) parameters.get("paper");

		if (loc_paper != null && !"".equals(loc_paper)) {
			paper = DesignPaper.getPaper(loc_paper);
		} else {
			int total = 0;
			// 计算各字段长度以及总长度
			for (int i = 0; i < captions.size(); i++) {
				Object obj = ((DynaBean) captions.get(i)).get("width");

				if (obj instanceof Integer) {
					total += ((Integer) (obj)).intValue();
				} else {
					total += Integer.parseInt(obj.toString());
				}
			}

			paper = DesignPaper.getPaper(total);
		}
	}

	// 重计算每个字段的宽度
	protected int[] recalculateWidths(int limit) {
		// 最后一位用于保存原始的总长度
		int[] loc_widths = new int[captions.size() + 1];

		int total = 0;

		// 计算原始宽度
		for (int i = 0; i < loc_widths.length - 1; i++) {
			DynaBean bean = (DynaBean) captions.get(i);
			Object obj = bean.get("width");

			if (obj == null) {
				loc_widths[i] = 0;
			} else if (obj instanceof Integer) {
				loc_widths[i] = ((Integer) (obj)).intValue();
			} else {
				loc_widths[i] = Integer.parseInt(obj.toString());
			}

			total += loc_widths[i];
		}

		double factor = (total == 0) ? 1 : (limit / (double) total);

		total = 0;

		// 重计算宽度
		for (int i = 0; i < loc_widths.length - 1; i++) {
			loc_widths[i] = (int) (loc_widths[i] * factor);
			total += loc_widths[i];
		}
		loc_widths[loc_widths.length - 1] = total;

		return loc_widths;
	}
}
