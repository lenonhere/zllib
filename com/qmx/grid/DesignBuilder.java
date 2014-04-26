package com.qmx.grid;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRException;
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

public abstract class DesignBuilder {
	protected static final byte ALIGN_TOP = 1;
	protected static final byte ALIGN_MIDDLE = 2;
	protected static final byte ALIGN_BOTTOM = 3;
	protected static final byte ALIGN_CENTER = 2;
	protected static final byte ALIGN_LEFT = 1;
	protected static final byte ALIGN_RIGHT = 3;
	protected static final byte PEN_POINT = 1;
	protected static final byte PEN_NONE = 0;
	protected DesignPaper paper = null;

	protected JasperDesign jdesign = new JasperDesign();

	protected List results = null;

	protected List captions = null;

	protected Map parameters = null;

	protected int[] widths = null;

	protected List fieldNames = new ArrayList();

	protected List ignoreFields = new ArrayList();

	public DesignBuilder(String name) {
		this.jdesign.setName(name);
	}

	public DesignBuilder(String name, List ignoreFields) {
		this.jdesign.setName(name);
		if (ignoreFields != null)
			this.ignoreFields = ignoreFields;
	}

	public void init(Map parameters, List captions, List results) {
		parameters.put("_InnnerEmmptyVal_", "");
		this.parameters = parameters;
		this.captions = DesignUtil.filter(captions, this.ignoreFields);
		this.results = results;
		try {
			pickPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract JasperReport build(Map paramMap, List paramList1,
			List paramList2);

	protected void generateDesign() {
		int pageWidth = this.paper.getPageWidth();
		int pageHeight = this.paper.getPageHeight();

		int columnWidth = this.paper.getColumnWidth();
		int columnHeight = this.paper.getColumnHeight();

		byte orientation = this.paper.getOrientation();

		int horizontal = this.paper.getHorizontalMargin();
		int vartical = this.paper.getVerticalMargin();

		String loc_paper = (String) this.parameters.get("paper");

		if ((loc_paper != null) && (!"".equals(loc_paper))) {
			this.widths = recalculateWidths(columnWidth);
			if (this.widths.length > 0) {
				int realColumnWidth = this.widths[(this.widths.length - 1)];
				columnWidth = realColumnWidth;
				horizontal = (pageWidth - realColumnWidth) / 2;
			}
		} else {
			this.widths = DesignUtil.calculateFieldWidths(this.captions,
					columnWidth, columnHeight);

			if (this.widths.length > 0) {
				int realColumnWidth = this.widths[(this.widths.length - 1)];

				boolean inverse = (realColumnWidth > columnWidth)
						&& (columnWidth < columnHeight);

				if (inverse) {
					orientation = 2;
					int temp = pageWidth;
					pageWidth = pageHeight;
					pageHeight = temp;
					vartical = horizontal;
				}

				columnWidth = realColumnWidth;
				horizontal = (pageWidth - realColumnWidth) / 2;
			}
		}

		DesignMaker.setPageArea(this.jdesign, pageWidth, pageHeight,
				columnWidth, 0);
		DesignMaker.setPageStyle(this.jdesign, orientation, true);
		DesignMaker.setPageMargin(this.jdesign, horizontal, vartical);
	}

	protected void generateFonts() {
		JRDesignReportFont[] loc_fonts = DesignFont.getFonts();

		for (int i = 0; i < loc_fonts.length; i++)
			try {
				this.jdesign.addFont(loc_fonts[i]);
			} catch (JRException ex) {
				ex.printStackTrace();
			}
	}

	protected void generateParameters() {
		Set keys = this.parameters.keySet();

		int size = this.parameters.size();
		JRDesignParameter[] params = DesignMaker.makeParameters(size);

		int i = 0;
		for (Iterator iter = keys.iterator(); iter.hasNext(); i++) {
			String key = (String) iter.next();
			Object value = this.parameters.get(key);
			if (value == null) {
				DesignMaker.setNameAndClass(params[i], key, String.class);
			} else {
				DesignMaker.setNameAndClass(params[i], key, value.getClass());
			}
		}

		DesignMaker.setParameters(this.jdesign, params);
	}

	protected void generateFields() {
		DynaProperty[] properties = DesignUtil.getDynaProperties(this.results);

		JRDesignField[] fields = DesignMaker.makeFields(properties.length);

		for (int i = 0; i < properties.length; i++) {
			String name = DesignUtil.wrapProperty(properties[i].getName());
			Class clazz = properties[i].getType();
			DesignMaker.setNameAndClass(fields[i], name, clazz);
			this.fieldNames.add(name);
		}

		DesignMaker.setFields(this.jdesign, fields);
	}

	protected void generateTitle() {
		JRDesignBand band = DesignMaker.makeBand();
		band.setHeight(0);
		this.jdesign.setTitle(band);
	}

	protected void generateColumnHeader() {
		if ((this.captions == null) || (this.captions.size() == 0)) {
			return;
		}

		int size = this.captions.size();

		String[] subCaptions = new String[size];
		String[] superCaptions = new String[size];

		int textCount = size;

		String head = null;

		for (int i = 0; i < size; i++) {
			DynaBean bean = (DynaBean) this.captions.get(i);
			String caption = (String) bean.get("caption");

			if ((caption == null) || ("".equals(caption))) {
				caption = (String) bean.get("property");
			}

			String[] tokens = caption.split("##");

			if (tokens.length <= 1) {
				superCaptions[i] = null;
				subCaptions[i] = tokens[0];
			} else {
				superCaptions[i] = tokens[0];
				subCaptions[i] = tokens[1];

				if ((head == null) || (!head.equals(tokens[0]))) {
					textCount++;
					head = tokens[0];
				}
			}
		}

		JRDesignStaticText[] staticTexts = DesignMaker
				.makeStaticTexts(textCount);
		JRBaseBox[] boxes = DesignMaker.makeBoxes(textCount);

		int heightScale = textCount > size ? 2 : 1;

		JRDesignBand band = DesignMaker.makeBand();
		band.setHeight(30 * heightScale);

		int index = 0;
		int x = 0;
		int width = 0;
		int superWidth = 0;

		for (int i = 0; i < size; i++) {
			x += width;
			width = this.widths[i];

			byte right_pen = (byte) (i == size - 1 ? 1 : 0);
			if (superCaptions[i] == null) {
				DesignMaker.setElementDimension(staticTexts[index], x, 0,
						width, 30 * heightScale);
				staticTexts[index].setText(subCaptions[i]);
				DesignMaker.setBoxBorder(boxes[index], (byte) 1, right_pen,
						(byte) 1, (byte) 1);

				index++;
			} else {
				DesignMaker.setElementDimension(staticTexts[index], x, 30,
						width, 30);
				staticTexts[index].setText(subCaptions[i]);

				DesignMaker.setBoxBorder(boxes[index], (byte) 1, right_pen,
						(byte) 0, (byte) 1);

				index++;

				superWidth += width;

				if ((i == size - 1)
						|| (!superCaptions[i].equals(superCaptions[(i + 1)]))) {
					DesignMaker.setElementDimension(staticTexts[index], x
							+ width - superWidth, 0, superWidth, 30);
					staticTexts[index].setText(superCaptions[i]);

					DesignMaker.setBoxBorder(boxes[index], (byte) 1, right_pen,
							(byte) 1, (byte) 1);
					index++;

					superWidth = 0;
				}
			}
		}

		DesignMaker.setBoxPaddings(boxes, 1, 1, 1, 1);

		DesignMaker.setTextBoxes(staticTexts, boxes);

		DesignMaker.setTextAlignments(staticTexts, (byte) 2, (byte) 2);

		DesignMaker.setTextColors(staticTexts, Color.black, Color.lightGray);
		DesignMaker.setTextFonts(staticTexts, DesignFont.COLUMN_HEADER);

		DesignMaker.setElements(band, staticTexts);
		this.jdesign.setColumnHeader(band);
	}

	protected void generateDetail() {
		if ((this.results == null) || (this.results.size() == 0)) {
			return;
		}

		if ((this.captions == null) || (this.captions.size() == 0)) {
			return;
		}

		JRDesignBand band = DesignMaker.makeBand();
		band.setHeight(22);

		int size = this.captions.size();
		JRDesignTextField[] textFields = DesignMaker.makeTextFields(size);
		JRBaseBox[] boxes = DesignMaker.makeBoxes(size);
		int x = 0;
		int width = 0;

		for (int i = 0; i < size; i++) {
			x += width;
			width = this.widths[i];
			DesignMaker.setElementDimension(textFields[i], x, 0, width, 22);

			DynaBean bean = (DynaBean) this.captions.get(i);

			String align = (String) bean.get("align");
			byte horizontalAlign = 1;
			if ("right".equalsIgnoreCase(align)) {
				horizontalAlign = 3;
			} else if ("center".equalsIgnoreCase(align)) {
				horizontalAlign = 2;
			}
			DesignMaker.setTextAlignment(textFields[i], horizontalAlign,
					(byte) 2);

			String property = ((String) bean.get("property")).trim();
			Class clazz = DesignUtil.getValueClass(this.results, property);

			String format = (String) bean.get("format");
			if ((!"".equals(format)) && (clazz != String.class)) {
				textFields[i].setPattern(format);
			}

			if (!"".equals(property)) {
				if (this.fieldNames.contains(DesignUtil.wrapProperty(property)))
					DesignMaker.setExpression(textFields[i],
							"$F{" + DesignUtil.wrapProperty(property) + "}",
							clazz);
				else {
					DesignMaker.setExpression(textFields[i],
							"$P{_InnnerEmmptyVal_}", clazz);
				}
			}

			byte right_pen = 0;
			if (i + 1 == size) {
				right_pen = 1;
			}
			DesignMaker.setBoxBorder(boxes[i], (byte) 1, right_pen, (byte) 0,
					(byte) 1);
			DesignMaker.setBoxPadding(boxes[i], 1, 1, 1, 1);
			textFields[i].setBox(boxes[i]);
			textFields[i].setStretchType((byte) 1);
			textFields[i].setStretchWithOverflow(true);
			textFields[i].setPrintWhenDetailOverflows(true);
		}

		DesignMaker.setTextFonts(textFields, DesignFont.DETAIL);
		DesignMaker.setTextNullBlanks(textFields, true);

		DesignMaker.setElements(band, textFields);
		this.jdesign.setDetail(band);
	}

	protected void generateColumnFooter() {
		JRDesignBand band = DesignMaker.makeBand();
		band.setHeight(25);

		JRDesignStaticText[] staticTexts = DesignMaker.makeStaticTexts(2);
		JRDesignTextField[] textFields = DesignMaker.makeTextFields(2);

		DesignMaker.setElementDimension(staticTexts[0], 0, 5, 50, 20);
		staticTexts[0].setText("制表人:");

		Object obj = this.parameters.get("creator");
		Class clazz = obj == null ? String.class : obj.getClass();
		DesignMaker.setElementDimension(textFields[0], 50, 5, 80, 20);
		DesignMaker.setExpression(textFields[0], "$P{creator}", clazz);

		int width = this.jdesign.getColumnWidth();

		DesignMaker.setElementDimension(staticTexts[1], width - 140, 5, 60, 20);
		staticTexts[1].setText("制表日期:");

		obj = this.parameters.get("createdDate");
		clazz = obj == null ? String.class : obj.getClass();
		DesignMaker.setElementDimension(textFields[1], width - 80, 5, 80, 20);
		DesignMaker.setExpression(textFields[1], "$P{createdDate}", clazz);

		DesignMaker.setTextAlignments(staticTexts, (byte) 2, (byte) 1);
		DesignMaker.setTextAlignments(textFields, (byte) 2, (byte) 1);

		DesignMaker.setTextFonts(staticTexts, DesignFont.COLUMN_FOOTER);
		DesignMaker.setTextFonts(textFields, DesignFont.COLUMN_FOOTER);

		DesignMaker.setElements(band, staticTexts);
		DesignMaker.setElements(band, textFields);

		this.jdesign.setColumnFooter(band);
	}

	protected void generatePageFooter() {
		JRDesignBand band = DesignMaker.makeBand();
		band.setHeight(30);

		JRDesignStaticText[] staticTexts = DesignMaker.makeStaticTexts(1);
		JRDesignTextField[] textFields = DesignMaker.makeTextFields(2);

		int x = this.jdesign.getColumnWidth() / 2 - 30;
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
		textFields[1].setEvaluationTime((byte) 2);

		DesignMaker.setTextAlignments(staticTexts, (byte) 2, (byte) 3);
		DesignMaker.setTextAlignments(textFields, (byte) 2, (byte) 3);

		DesignMaker.setTextFonts(staticTexts, DesignFont.PAGE_FOOTER);
		DesignMaker.setTextFonts(textFields, DesignFont.PAGE_FOOTER);

		DesignMaker.setElements(band, staticTexts);
		DesignMaker.setElements(band, textFields);

		this.jdesign.setPageFooter(band);
	}

	protected void generateSummary() {
		JRDesignBand band = DesignMaker.makeBand();
		band.setHeight(0);
		this.jdesign.setSummary(band);
	}

	protected JasperReport compile() {
		JasperReport report = null;
		try {
			report = JasperCompileManager.compileReport(this.jdesign);
		} catch (JRException ex) {
			ex.printStackTrace();
		}

		return report;
	}

	public int initTitle(JRDesignBand band) {
		int height = 40;
		JRDesignTextField textField = new JRDesignTextField();

		int width = this.jdesign.getColumnWidth();
		DesignMaker.setElementDimension(textField, 0, 0, width, height);
		DesignMaker.setTextAlignment(textField, (byte) 2, (byte) 1);

		Object obj = this.parameters.get("title");
		Class clazz = obj == null ? String.class : obj.getClass();
		DesignMaker.setExpression(textField, "$P{title}", clazz);
		textField.setFont(DesignFont.TITLE);
		textField.setForecolor(Color.red);
		band.addElement(textField);

		return height;
	}

	protected void setTextFieldAll(JRDesignTextField[] textFields, int pos,
			int x, int y, int width, int height, String property) {
		Object obj = this.parameters.get(property);
		Class clazz = obj == null ? String.class : obj.getClass();
		DesignMaker.setElementDimension(textFields[pos], x, y, width, height);
		DesignMaker.setExpression(textFields[pos], "$P{" + property + "}",
				clazz);
	}

	protected void setStaticTextAll(JRDesignStaticText[] staticTexts, int pos,
			int x, int y, int width, int height, String text) {
		DesignMaker.setElementDimension(staticTexts[pos], x, y, width, height);
		staticTexts[pos].setText(text);
	}

	protected void pickPage() throws Exception {
		String loc_paper = (String) this.parameters.get("paper");

		if ((loc_paper != null) && (!"".equals(loc_paper))) {
			this.paper = DesignPaper.getPaper(loc_paper);
		} else {
			int total = 0;

			for (int i = 0; i < this.captions.size(); i++) {
				Object obj = ((DynaBean) this.captions.get(i)).get("width");

				if ((obj instanceof Integer))
					total += ((Integer) obj).intValue();
				else {
					total += Integer.parseInt(obj.toString());
				}
			}

			this.paper = DesignPaper.getPaper(total);
		}
	}

	protected int[] recalculateWidths(int limit) {
		int[] loc_widths = new int[this.captions.size() + 1];

		int total = 0;

		for (int i = 0; i < loc_widths.length - 1; i++) {
			DynaBean bean = (DynaBean) this.captions.get(i);
			Object obj = bean.get("width");

			if (obj == null)
				loc_widths[i] = 0;
			else if ((obj instanceof Integer))
				loc_widths[i] = ((Integer) obj).intValue();
			else {
				loc_widths[i] = Integer.parseInt(obj.toString());
			}

			total += loc_widths[i];
		}

		double factor = total == 0 ? 1.0D : limit / total;

		total = 0;

		for (int i = 0; i < loc_widths.length - 1; i++) {
			loc_widths[i] = (int) (loc_widths[i] * factor);
			total += loc_widths[i];
		}
		loc_widths[(loc_widths.length - 1)] = total;

		return loc_widths;
	}
}