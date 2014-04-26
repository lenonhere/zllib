package com.qmx.grid;

import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JasperDesign;

public class DefaultDesignBuilder extends DesignBuilder
{
  public DefaultDesignBuilder(String name)
  {
    super(name);
  }

  public DefaultDesignBuilder(String name, List ignoreFields) {
    super(name, ignoreFields);
  }

  public JasperReport build(Map parameters, List captions, List results)
  {
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

    if (this.parameters.get("position_left") != null) {
      String text = (String)this.parameters.get("position_left_text");
      int width = Integer.parseInt((String)this.parameters.get("position_left"));
      setStaticTextAll(staticTexts, 0, 0, y, width, 20, text);
      DesignMaker.setTextAlignment(staticTexts[0], (byte)1, (byte)1);
    }

    if (this.parameters.get("position_center") != null) {
      String text = (String)this.parameters.get("position_center_text");
      int width = Integer.parseInt((String)this.parameters.get("position_center"));
      int columnWidth = this.paper.getColumnWidth();
      setStaticTextAll(staticTexts, 1, (columnWidth - width) / 2, y, width, 20, text);
      DesignMaker.setTextAlignment(staticTexts[1], (byte)2, (byte)1);
    }

    if (this.parameters.get("position_right") != null) {
      String text = (String)this.parameters.get("position_right_text");
      int width = Integer.parseInt((String)this.parameters.get("position_right"));
      int columnWidth = this.paper.getColumnWidth();
      setStaticTextAll(staticTexts, 2, columnWidth - width, y, width, 20, text);
      DesignMaker.setTextAlignment(staticTexts[2], (byte)3, (byte)1);
    }

    DesignMaker.setTextFonts(staticTexts, DesignFont.PAGE_HEADER);

    DesignMaker.setElements(band, staticTexts);

    this.jdesign.setPageHeader(band);
  }
}