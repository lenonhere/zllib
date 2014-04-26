package com.qmx.grid;

import com.zl.base.core.db.CallHelper;

public class DesignPaper {
	private String paperId;
	private String paperName;
	private int pageWidth;
	private int pageHeight;
	private int columnWidth;
	private int columnHeight;
	private int horizontalMargin;
	private int verticalMargin;
	private byte orientation;

	public DesignPaper() {
	}

	public DesignPaper(int pWidth, int pHeight, int hMargin, int vMargin) {
		this.pageWidth = pWidth;
		this.pageHeight = pHeight;
		this.horizontalMargin = hMargin;
		this.verticalMargin = vMargin;

		this.columnWidth = (pWidth - 2 * hMargin);
		this.columnHeight = (pHeight - 2 * vMargin);

		if (pWidth < pHeight) {
			this.orientation = 1;
		} else
			this.orientation = 2;
	}

	public int getColumnHeight() {
		return this.columnHeight;
	}

	public int getColumnWidth() {
		return this.columnWidth;
	}

	public int getHorizontalMargin() {
		return this.horizontalMargin;
	}

	public byte getOrientation() {
		return this.orientation;
	}

	public int getPageHeight() {
		return this.pageHeight;
	}

	public int getPageWidth() {
		return this.pageWidth;
	}

	public int getVerticalMargin() {
		return this.verticalMargin;
	}

	public String getPaperId() {
		return this.paperId;
	}

	public String getPaperName() {
		return this.paperName;
	}

	public void setVerticalMargin(int verticalMargin) {
		this.verticalMargin = verticalMargin;
	}

	public void setPageWidth(int pageWidth) {
		this.pageWidth = pageWidth;
	}

	public void setPageHeight(int pageHeight) {
		this.pageHeight = pageHeight;
	}

	public void setOrientation(byte orientation) {
		this.orientation = orientation;
	}

	public void setHorizontalMargin(int horizontalMargin) {
		this.horizontalMargin = horizontalMargin;
	}

	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}

	public void setColumnHeight(int columnHeight) {
		this.columnHeight = columnHeight;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public static DesignPaper getPaper(String name) throws Exception {
		CallHelper helper = new CallHelper("getPrintPaper");
		helper.setParam("name", name);
		helper.setParam("type", "0");
		helper.execute();

		return makePaper(helper);
	}

	public static DesignPaper getPaper(int length) throws Exception {
		CallHelper helper = new CallHelper("getPrintPaper");
		helper.setParam("width", String.valueOf(length));
		helper.setParam("type", "1");
		helper.execute();

		return makePaper(helper);
	}

	protected static DesignPaper makePaper(CallHelper helper) {
		DesignPaper paper = new DesignPaper();

		String paperId = (String) helper.getOutput("paperId");
		String paperName = (String) helper.getOutput("paperName");

		if (paperName != null) {
			paperName = paperName.trim();
		}

		int pageWidth = Integer
				.parseInt((String) helper.getOutput("pageWidth"));
		int pageHeight = Integer.parseInt((String) helper
				.getOutput("pageHeight"));
		int columnWidth = Integer.parseInt((String) helper
				.getOutput("columnWidth"));
		int columnHeight = Integer.parseInt((String) helper
				.getOutput("columnHeight"));
		int leftMargin = Integer.parseInt((String) helper
				.getOutput("leftMargin"));
		int rightMargin = Integer.parseInt((String) helper
				.getOutput("rightMargin"));
		int topMargin = Integer
				.parseInt((String) helper.getOutput("topMargin"));
		int bottomMargin = Integer.parseInt((String) helper
				.getOutput("bottomMargin"));
		int orientation = Integer.parseInt((String) helper
				.getOutput("orientation"));

		paper.setPaperId(paperId);
		paper.setPaperName(paperName);

		paper.setPageWidth(pageWidth);
		paper.setPageHeight(pageHeight);

		if (columnWidth > 0)
			paper.setColumnWidth(columnWidth);
		else {
			paper.setColumnWidth(pageWidth - leftMargin - rightMargin);
		}

		if (columnHeight > 0)
			paper.setColumnHeight(columnHeight);
		else {
			paper.setColumnHeight(pageHeight - topMargin - bottomMargin);
		}

		paper.setHorizontalMargin((leftMargin + rightMargin) / 2);
		paper.setVerticalMargin((topMargin + bottomMargin) / 2);

		byte orien = (byte) (orientation == 0 ? 1 : 2);
		paper.setOrientation(orien);

		return paper;
	}
}