package com.common.jasper;

import net.sf.jasperreports.engine.JRReport;

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

    public DesignPaper() {}

    public DesignPaper(int pWidth, int pHeight, int hMargin,
                       int vMargin) {

        this.pageWidth = pWidth;
        this.pageHeight = pHeight;
        this.horizontalMargin = hMargin;
        this.verticalMargin = vMargin;

        columnWidth  = pWidth - 2 * hMargin;
        columnHeight = pHeight - 2 * vMargin;

        if (pWidth < pHeight) {
            orientation = JRReport.ORIENTATION_PORTRAIT;
        }
        else {
            orientation = JRReport.ORIENTATION_LANDSCAPE;
        }
    }

    public int getColumnHeight() {
        return columnHeight;
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public int getHorizontalMargin() {
        return horizontalMargin;
    }

    public byte getOrientation() {
        return orientation;
    }

    public int getPageHeight() {
        return pageHeight;
    }

    public int getPageWidth() {
        return pageWidth;
    }

    public int getVerticalMargin() {
        return verticalMargin;
    }

    public String getPaperId() {
        return paperId;
    }

    public String getPaperName() {
        return paperName;
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

    /**
     * 根据名称获取纸张，如果指定名称的纸张不存在，则返回A4
     */
    public static DesignPaper getPaper(String name) {
        CallHelper helper = new CallHelper("getPrintPaper");
        helper.setParam("name", name);
        helper.setParam("type", "0");
        helper.execute();

        return makePaper(helper);
    }

    /**
     * 根据列长获取纸张，如果指定列长的纸张太长，返回VARY_LARGE
     */
    public static DesignPaper getPaper(int length) {

        CallHelper helper = new CallHelper("getPrintPaper");
        helper.setParam("width", String.valueOf(length));
        helper.setParam("type", "1");
        helper.execute();

        return makePaper(helper);
    }

    protected static DesignPaper makePaper(CallHelper helper) {
        DesignPaper paper = new DesignPaper();

        String paperId = (String)helper.getOutput("paperId");
        String paperName = (String)helper.getOutput("paperName");

        if(paperName != null) {
            paperName = paperName.trim();
        }

        int pageWidth = Integer.parseInt((String)helper.getOutput("pageWidth"));
        int pageHeight = Integer.parseInt((String)helper.getOutput("pageHeight"));
        int columnWidth = Integer.parseInt((String)helper.getOutput("columnWidth"));
        int columnHeight = Integer.parseInt((String)helper.getOutput("columnHeight"));
        int leftMargin = Integer.parseInt((String)helper.getOutput("leftMargin"));
        int rightMargin = Integer.parseInt((String)helper.getOutput("rightMargin"));
        int topMargin = Integer.parseInt((String)helper.getOutput("topMargin"));
        int bottomMargin = Integer.parseInt((String)helper.getOutput("bottomMargin"));
        int orientation = Integer.parseInt((String)helper.getOutput("orientation"));

        paper.setPaperId(paperId);
        paper.setPaperName(paperName);

        paper.setPageWidth(pageWidth);
        paper.setPageHeight(pageHeight);

        if(columnWidth > 0) {
            paper.setColumnWidth(columnWidth);
        } else {
            paper.setColumnWidth(pageWidth - leftMargin - rightMargin);
        }

        if(columnHeight > 0) {
            paper.setColumnHeight(columnHeight);
        } else {
            paper.setColumnHeight(pageHeight - topMargin - bottomMargin);
        }

        paper.setHorizontalMargin((leftMargin + rightMargin) / 2 );
        paper.setVerticalMargin((topMargin + bottomMargin) / 2 );

        byte orien =
            (orientation == 0) ? JRReport.ORIENTATION_PORTRAIT : JRReport.ORIENTATION_LANDSCAPE;
        paper.setOrientation(orien);

        return paper;
    }
}
