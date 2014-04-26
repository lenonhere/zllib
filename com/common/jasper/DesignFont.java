package com.common.jasper;

import net.sf.jasperreports.engine.design.JRDesignReportFont;

public class DesignFont {

	public static final JRDesignReportFont DEFAULT;
	public static final JRDesignReportFont TITLE;
	public static final JRDesignReportFont PAGE_HEADER;
	public static final JRDesignReportFont COLUMN_HEADER;
	public static final JRDesignReportFont DETAIL;
	public static final JRDesignReportFont COLUMN_FOOTER;
	public static final JRDesignReportFont PAGE_FOOTER;
	public static final JRDesignReportFont SUMMARY_FOOTER;

	protected static JRDesignReportFont[] fonts;

	static {

		fonts = makeReportFonts(8);

		JRDesignReportFont font = fonts[0];
		// Default Font
		font.setName("SIMSUN_DEFAULT");
		DesignMaker.setFontInfo(font, true, "SIMSUN", 12);
		DesignMaker.setFontStyle(font, false, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		DEFAULT = font;

		font = fonts[1];
		// Title Font
		font.setName("SIMSUN_TITLE");
		DesignMaker.setFontInfo(font, false, "SIMSUN", 20);
		DesignMaker.setFontStyle(font, true, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		TITLE = font;

		font = fonts[2];
		// Page Header Font
		font.setName("SIMSUN_PAGE_HEADER");
		DesignMaker.setFontInfo(font, false, "SIMSUN", 12);
		DesignMaker.setFontStyle(font, true, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		PAGE_HEADER = font;

		font = fonts[3];
		// Column Header Font
		font.setName("SIMSUN_COLUMN_HEADER");
		DesignMaker.setFontInfo(font, false, "SIMSUN", 12);
		DesignMaker.setFontStyle(font, true, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		COLUMN_HEADER = font;

		font = fonts[4];
		// Detail Font
		font.setName("SIMSUN_DETAIL");
		DesignMaker.setFontInfo(font, false, "SIMSUN", 12);
		DesignMaker.setFontStyle(font, false, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		DETAIL = font;

		font = fonts[5];
		// Column Footer Font
		font.setName("SIMSUN_COLUMN_FOOTER");
		DesignMaker.setFontInfo(font, false, "SIMSUN", 12);
		DesignMaker.setFontStyle(font, true, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		COLUMN_FOOTER = font;

		font = fonts[6];
		// Page Footer Font
		font.setName("SIMSUN_PAGE_FOOTER");
		DesignMaker.setFontInfo(font, false, "SIMSUN", 12);
		DesignMaker.setFontStyle(font, false, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		PAGE_FOOTER = font;

		font = fonts[7];
		// Summary Font
		font.setName("SIMSUN_SUMMARY_FOOTER");
		DesignMaker.setFontInfo(font, false, "SIMSUN", 12);
		DesignMaker.setFontStyle(font, true, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		SUMMARY_FOOTER = font;
	}

	/**
	 * 创建并返回一个JRDesignReportFont数组
	 */
	public static JRDesignReportFont[] makeReportFonts(int size) {

		JRDesignReportFont[] loc_fonts = new JRDesignReportFont[size];

		for (int i = 0; i < size; i++) {
			loc_fonts[i] = new JRDesignReportFont();
		}

		return loc_fonts;
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

	public static JRDesignReportFont[] getFonts() {
		return fonts;
	}
}
