package com.qmx.grid;

import java.awt.Color;
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

public class DesignMaker
{
  protected static int max(int cand1, int cand2)
  {
    return cand1 > cand2 ? cand1 : cand2;
  }

  public static JRDesignReportFont makeReportFont()
  {
    return new JRDesignReportFont();
  }

  public static JRDesignReportFont[] makeReportFonts(int size)
  {
    size = max(0, size);

    JRDesignReportFont[] fonts = new JRDesignReportFont[size];

    for (int i = 0; i < size; i++) {
      fonts[i] = new JRDesignReportFont();
    }

    return fonts;
  }

  public static void setReportFonts(JasperDesign jdesign, JRDesignReportFont[] fonts)
  {
    if ((jdesign == null) || (fonts == null)) {
      return;
    }

    for (int i = 0; i < fonts.length; i++)
      try {
        jdesign.addFont(fonts[i]);
      }
      catch (JRException localJRException)
      {
      }
  }

  public static JRDesignParameter makeParameter()
  {
    return new JRDesignParameter();
  }

  public static JRDesignParameter[] makeParameters(int size)
  {
    size = max(0, size);

    JRDesignParameter[] parameters = new JRDesignParameter[size];

    for (int i = 0; i < size; i++) {
      parameters[i] = new JRDesignParameter();
    }

    return parameters;
  }

  public static void setParameters(JasperDesign jdesign, JRDesignParameter[] parameters)
  {
    if ((jdesign == null) || (parameters == null)) {
      return;
    }

    for (int i = 0; i < parameters.length; i++)
      try {
        jdesign.addParameter(parameters[i]);
      }
      catch (JRException localJRException)
      {
      }
  }

  public static JRDesignField makeField()
  {
    return new JRDesignField();
  }

  public static JRDesignField[] makeFields(int size)
  {
    size = max(0, size);

    JRDesignField[] fields = new JRDesignField[size];

    for (int i = 0; i < size; i++) {
      fields[i] = new JRDesignField();
    }

    return fields;
  }

  public static void setFields(JasperDesign jdesign, JRDesignField[] fields)
  {
    if ((jdesign == null) || (fields == null)) {
      return;
    }

    for (int i = 0; i < fields.length; i++)
      try {
        jdesign.addField(fields[i]);
      }
      catch (JRException localJRException)
      {
      }
  }

  public static JRDesignBand makeBand()
  {
    return new JRDesignBand();
  }

  public static JRDesignStaticText makeStaticText()
  {
    return new JRDesignStaticText();
  }

  public static JRDesignStaticText[] makeStaticTexts(int size)
  {
    size = max(0, size);

    JRDesignStaticText[] staticTexts = new JRDesignStaticText[size];

    for (int i = 0; i < size; i++) {
      staticTexts[i] = new JRDesignStaticText();
    }

    return staticTexts;
  }

  public static JRDesignTextField makeTextField()
  {
    return new JRDesignTextField();
  }

  public static JRDesignTextField[] makeTextFields(int size)
  {
    size = max(0, size);

    JRDesignTextField[] textFields = new JRDesignTextField[size];

    for (int i = 0; i < size; i++) {
      textFields[i] = new JRDesignTextField();
    }

    return textFields;
  }

  public static void setElements(JRDesignBand band, JRDesignElement[] elements)
  {
    if ((band == null) || (elements == null)) {
      return;
    }

    for (int i = 0; i < elements.length; i++)
      band.addElement(elements[i]);
  }

  public static JRDesignExpression makeExpression()
  {
    return new JRDesignExpression();
  }

  public static JRDesignExpression[] makeExpressions(int size)
  {
    size = max(0, size);

    JRDesignExpression[] expressions = new JRDesignExpression[size];

    for (int i = 0; i < size; i++) {
      expressions[i] = new JRDesignExpression();
    }

    return expressions;
  }

  public static JRBaseBox makeBox()
  {
    return new JRBaseBox();
  }

  public static JRBaseBox[] makeBoxes(int size)
  {
    size = max(0, size);

    JRBaseBox[] boxes = new JRBaseBox[size];

    for (int i = 0; i < size; i++) {
      boxes[i] = new JRBaseBox();
    }

    return boxes;
  }

  public static void setPageArea(JasperDesign jdesign, int pageWidth, int pageHeight, int columnWidth, int columnSpacing)
  {
    jdesign.setPageWidth(pageWidth);
    jdesign.setPageHeight(pageHeight);
    jdesign.setColumnWidth(columnWidth);
    jdesign.setColumnSpacing(columnSpacing);
  }

  public static void setPageMargin(JasperDesign jdesign, int horizontal, int vartical)
  {
    jdesign.setLeftMargin(horizontal);
    jdesign.setRightMargin(horizontal);
    jdesign.setTopMargin(vartical);
    jdesign.setBottomMargin(vartical);
  }

  public static void setPageStyle(JasperDesign jdesign, byte orientation, boolean floatColumnFooter)
  {
    jdesign.setOrientation(orientation);
    jdesign.setFloatColumnFooter(floatColumnFooter);
  }

  public static void setFontInfo(JRDesignReportFont font, boolean isDefault, String fontName, int size)
  {
    font.setDefault(isDefault);
    font.setFontName(fontName);
    font.setSize(size);
  }

  public static void setFontStyle(JRDesignReportFont font, boolean bold, boolean italic, boolean underline, boolean strikeThrough)
  {
    font.setBold(bold);
    font.setItalic(italic);
    font.setUnderline(underline);
    font.setStrikeThrough(strikeThrough);
  }

  public static void setFontPdf(JRDesignReportFont font, String pdfFontName, String pdfEncoding, boolean pdfEmbedded)
  {
    font.setPdfFontName(pdfFontName);
    font.setPdfEncoding(pdfEncoding);
    font.setPdfEmbedded(pdfEmbedded);
  }

  public static void setNameAndClass(JRDesignParameter parameter, String name, Class valueClass)
  {
    parameter.setName(name);
    parameter.setValueClass(valueClass);
  }

  public static void setNameAndClass(JRDesignField field, String name, Class valueClass)
  {
    field.setName(name);
    field.setValueClass(valueClass);
  }

  public static void setTextAndClass(JRDesignExpression expression, String text, Class valueClass)
  {
    expression.setText(text);
    expression.setValueClass(valueClass);
  }

  public static void setElementDimension(JRDesignElement element, int x, int y, int width, int height)
  {
    element.setX(x);
    element.setY(y);
    element.setWidth(width);
    element.setHeight(height);
  }

  public static void setTextAlignment(JRDesignTextElement text, byte horizontalAlignment, byte verticalAlignment)
  {
    text.setHorizontalAlignment(horizontalAlignment);
    text.setVerticalAlignment(verticalAlignment);
  }

  public static void setTextAlignments(JRDesignTextElement[] texts, byte horizontalAlignment, byte verticalAlignment)
  {
    for (int i = 0; i < texts.length; i++) {
      texts[i].setHorizontalAlignment(horizontalAlignment);
      texts[i].setVerticalAlignment(verticalAlignment);
    }
  }

  public static void setTextBoxes(JRDesignTextElement[] texts, JRBaseBox[] boxes)
  {
    int length = texts.length <= boxes.length ? texts.length : boxes.length;
    for (int i = 0; i < length; i++)
      texts[i].setBox(boxes[i]);
  }

  public static void setBoxBorder(JRBaseBox box, byte left, byte right, byte top, byte bottom)
  {
    box.setLeftBorder(left);
    box.setRightBorder(right);
    box.setTopBorder(top);
    box.setBottomBorder(bottom);
  }

  public static void setBoxBorders(JRBaseBox[] boxes, byte left, byte right, byte top, byte bottom)
  {
    for (int i = 0; i < boxes.length; i++) {
      boxes[i].setLeftBorder(left);
      boxes[i].setRightBorder(right);
      boxes[i].setTopBorder(top);
      boxes[i].setBottomBorder(bottom);
    }
  }

  public static void setBoxPadding(JRBaseBox box, int left, int right, int top, int bottom)
  {
    box.setLeftPadding(left);
    box.setRightPadding(right);
    box.setTopPadding(top);
    box.setBottomPadding(bottom);
  }

  public static void setBoxPaddings(JRBaseBox[] boxes, int left, int right, int top, int bottom)
  {
    for (int i = 0; i < boxes.length; i++) {
      boxes[i].setLeftPadding(left);
      boxes[i].setRightPadding(right);
      boxes[i].setTopPadding(top);
      boxes[i].setBottomPadding(bottom);
    }
  }

  public static void setExpression(JRDesignTextField field, String text, Class valueClass)
  {
    JRDesignExpression expression = makeExpression();
    setTextAndClass(expression, text, valueClass);
    field.setExpression(expression);
  }

  public static void setTextFonts(JRDesignTextElement[] texts, JRFont font)
  {
    for (int i = 0; i < texts.length; i++) {
      texts[i].setFont(font);
      texts[i].setFont(font);
    }
  }

  public static void setTextNullBlanks(JRDesignTextField[] texts, boolean isBlank)
  {
    for (int i = 0; i < texts.length; i++)
      texts[i].setBlankWhenNull(isBlank);
  }

  public static void setTextColors(JRDesignTextElement[] texts, Color forecolor, Color backcolor)
  {
    for (int i = 0; i < texts.length; i++) {
      texts[i].setMode((byte)1);
      texts[i].setForecolor(forecolor);
      texts[i].setBackcolor(backcolor);
    }
  }
}