package com.qmx.grid;

import net.sf.jasperreports.engine.design.JRDesignReportFont;

public class DesignFont {

	public DesignFont() {
	}

	public static JRDesignReportFont[] makeReportFonts(int size) {
		JRDesignReportFont loc_fonts[] = new JRDesignReportFont[size];
		for (int i = 0; i < size; i++)
			loc_fonts[i] = new JRDesignReportFont();

		return loc_fonts;
	}

	public static void setFontInfo(JRDesignReportFont font, boolean isDefault,
			String fontName, int size) {
		font.setDefault(isDefault);
		font.setFontName(fontName);
		font.setSize(size);
	}

	public static void setFontStyle(JRDesignReportFont font, boolean bold,
			boolean italic, boolean underline, boolean strikeThrough) {
		font.setBold(bold);
		font.setItalic(italic);
		font.setUnderline(underline);
		font.setStrikeThrough(strikeThrough);
	}

	public static void setFontPdf(JRDesignReportFont font, String pdfFontName,
			String pdfEncoding, boolean pdfEmbedded) {
		font.setPdfFontName(pdfFontName);
		font.setPdfEncoding(pdfEncoding);
		font.setPdfEmbedded(pdfEmbedded);
	}

	public static JRDesignReportFont[] getFonts() {
		return fonts;
	}

	public static final JRDesignReportFont DEFAULT;
	public static final JRDesignReportFont TITLE;
	public static final JRDesignReportFont PAGE_HEADER;
	public static final JRDesignReportFont COLUMN_HEADER;
	public static final JRDesignReportFont DETAIL;
	public static final JRDesignReportFont COLUMN_FOOTER;
	public static final JRDesignReportFont PAGE_FOOTER;
	public static final JRDesignReportFont SUMMARY_FOOTER;
	protected static JRDesignReportFont fonts[];

	static {
		fonts = makeReportFonts(8);
		JRDesignReportFont font = fonts[0];
		font.setName("SIMSUN_DEFAULT");
		DesignMaker.setFontInfo(font, true, "SIMSUN", 12);
		DesignMaker.setFontStyle(font, false, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		DEFAULT = font;
		font = fonts[1];
		font.setName("SIMSUN_TITLE");
		DesignMaker.setFontInfo(font, false, "SIMSUN", 20);
		DesignMaker.setFontStyle(font, true, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		TITLE = font;
		font = fonts[2];
		font.setName("SIMSUN_PAGE_HEADER");
		DesignMaker.setFontInfo(font, false, "SIMSUN", 12);
		DesignMaker.setFontStyle(font, true, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		PAGE_HEADER = font;
		font = fonts[3];
		font.setName("SIMSUN_COLUMN_HEADER");
		DesignMaker.setFontInfo(font, false, "SIMSUN", 12);
		DesignMaker.setFontStyle(font, true, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		COLUMN_HEADER = font;
		font = fonts[4];
		font.setName("SIMSUN_DETAIL");
		DesignMaker.setFontInfo(font, false, "SIMSUN", 12);
		DesignMaker.setFontStyle(font, false, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		DETAIL = font;
		font = fonts[5];
		font.setName("SIMSUN_COLUMN_FOOTER");
		DesignMaker.setFontInfo(font, false, "SIMSUN", 12);
		DesignMaker.setFontStyle(font, true, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		COLUMN_FOOTER = font;
		font = fonts[6];
		font.setName("SIMSUN_PAGE_FOOTER");
		DesignMaker.setFontInfo(font, false, "SIMSUN", 12);
		DesignMaker.setFontStyle(font, false, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		PAGE_FOOTER = font;
		font = fonts[7];
		font.setName("SIMSUN_SUMMARY_FOOTER");
		DesignMaker.setFontInfo(font, false, "SIMSUN", 12);
		DesignMaker.setFontStyle(font, true, false, false, false);
		DesignMaker.setFontPdf(font, "STSong-Light", "UniGB-UCS2-H", false);
		SUMMARY_FOOTER = font;
	}
}
