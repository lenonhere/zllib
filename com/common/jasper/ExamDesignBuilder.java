package com.common.jasper;

import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;

public class ExamDesignBuilder extends DesignBuilder {
	public ExamDesignBuilder(String name) {
		super(name);
	}

	public ExamDesignBuilder(String name, List ignoreFields) {
		super(name, ignoreFields);
	}

	public JasperReport build(Map parameters, List captions, List results) {

		init(parameters, captions, results);
		generateDesign();
		generateFonts();
		generateParameters();
		generateFields();
		generateTitle();
		generatePageHeader();
		generateColumnHeader();
		generateDetail();
		generateColumnFooter();
		generatePageFooter();
		generateSummary();

		return compile();
	}

	protected void generatePageHeader() {
		JRDesignBand band = DesignMaker.makeBand();
		band.setHeight(65);

		JRDesignStaticText[] staticTexts = DesignMaker.makeStaticTexts(3);

		int y = initTitle(band) + 5;

		// position 1
		if (parameters.get("position_left") != null) {
			String text = (String) parameters.get("position_left_text");
			int width = Integer.parseInt((String) parameters
					.get("position_left"));
			setStaticTextAll(staticTexts, 0, 0, y, width, 20, text);
			DesignMaker.setTextAlignment(staticTexts[0], ALIGN_LEFT, ALIGN_TOP);
		}

		// position 2
		if (parameters.get("position_center") != null) {
			String text = (String) parameters.get("position_center_text");
			int width = Integer.parseInt((String) parameters
					.get("position_center"));
			int columnWidth = paper.getColumnWidth();
			setStaticTextAll(staticTexts, 1, (columnWidth - width) / 2, y,
					width, 20, text);
			DesignMaker.setTextAlignment(staticTexts[1], ALIGN_CENTER,
					ALIGN_TOP);
		}

		// position 3
		if (parameters.get("position_right") != null) {
			String text = (String) parameters.get("position_right_text");
			int width = Integer.parseInt((String) parameters
					.get("position_right"));
			int columnWidth = paper.getColumnWidth();
			setStaticTextAll(staticTexts, 2, columnWidth - width, y, width, 20,
					text);
			DesignMaker
					.setTextAlignment(staticTexts[2], ALIGN_RIGHT, ALIGN_TOP);
		}

		DesignMaker.setTextFonts(staticTexts, DesignFont.PAGE_HEADER);

		DesignMaker.setElements(band, staticTexts);

		jdesign.setPageHeader(band);
	}

	protected void generateColumnFooter() {
		JRDesignBand band = DesignMaker.makeBand();
		band.setHeight(90);

		JRDesignStaticText[] staticTexts = DesignMaker.makeStaticTexts(3);
		JRDesignTextField[] textFields = DesignMaker.makeTextFields(3);

		DesignMaker.setElementDimension(staticTexts[0], 0, 5, 50, 20);
		staticTexts[0].setText("制表:");

		Object obj = parameters.get("personName");
		Class clazz = (obj == null) ? String.class : obj.getClass();
		DesignMaker.setElementDimension(textFields[0], 50, 5, 80, 20);
		DesignMaker.setExpression(textFields[0], "$P{personName}", clazz);

		int width = jdesign.getColumnWidth();
		DesignMaker.setElementDimension(staticTexts[1], width / 2 - 80, 5, 60,
				20);
		staticTexts[1].setText("审批:");

		DesignMaker.setElementDimension(staticTexts[2], width - 140, 5, 60, 20);
		staticTexts[2].setText("日期:");

		obj = parameters.get("printDate");
		clazz = (obj == null) ? String.class : obj.getClass();
		DesignMaker.setElementDimension(textFields[1], width - 80, 5, 80, 20);
		DesignMaker.setExpression(textFields[1], "$P{printDate}", clazz);

		obj = parameters.get("footermark");
		clazz = (obj == null) ? new String("").getClass() : obj.getClass();
		DesignMaker.setElementDimension(textFields[2], width - 90, 50, 100, 20);
		DesignMaker.setExpression(textFields[2], "$P{footermark}", clazz);
		/*


        */
		DesignMaker.setTextAlignments(staticTexts, ALIGN_CENTER, ALIGN_TOP);
		DesignMaker.setTextAlignments(textFields, ALIGN_CENTER, ALIGN_TOP);

		DesignMaker.setTextFonts(staticTexts, DesignFont.COLUMN_FOOTER);
		DesignMaker.setTextFonts(textFields, DesignFont.COLUMN_FOOTER);

		DesignMaker.setElements(band, staticTexts);
		DesignMaker.setElements(band, textFields);

		obj = parameters.get("examiner");

		if (obj != null) {
			JRDesignImage image = new JRDesignImage();
			image.setX(width / 2 - 20);
			image.setY(5);
			image.setWidth(100);
			image.setHeight(80);
			image.setScaleImage(JRImage.SCALE_IMAGE_FILL_FRAME);
			JRDesignExpression expression = new JRDesignExpression();
			expression.setValueClass(obj.getClass());
			expression.setText("$P{examiner}");
			image.setExpression(expression);
			band.addElement(image);
		}

		jdesign.setColumnFooter(band);

		/*
		 * JRDesignBand band2 = DesignMaker.makeBand(); band2.setHeight(90);
		 * JRDesignTextField[] textFields2 = DesignMaker.makeTextFields(1); obj
		 * = parameters.get("footermark"); clazz = (obj == null) ? String.class
		 * : obj.getClass(); DesignMaker.setElementDimension(textFields2[0],
		 * width - 80, 5, 80, 20); DesignMaker.setExpression(textFields2[0],
		 * "$P{footerMark}", clazz); DesignMaker.setTextAlignments(textFields2,
		 * ALIGN_CENTER, ALIGN_TOP); DesignMaker.setTextFonts(textFields2,
		 * DesignFont.COLUMN_FOOTER); DesignMaker.setElements(band2,
		 * textFields2); jdesign.setColumnFooter(band2);
		 */
	}
}
