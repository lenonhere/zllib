package com.zl.base.core.taglib.chart.vml;

public class VMLStrokeTag extends VMLTag
{
  public static final String VML_STROKE_DASHTYPE_SOLID = "solid";
  public static final String VML_STROKE_DASHTYPE_SHORTDASH = "shortdash";
  public static final String VML_STROKE_DASHTYPE_SHORTDOT = "shortdot";
  public static final String VML_STROKE_DASHTYPE_SHORTDASHDOTDOT = "shortdashdotdot";
  public static final String VML_STROKE_DASHTYPE_DOT = "dot";
  public static final String VML_STROKE_DASHTYPE_DASH = "dash";
  public static final String VML_STROKE_DASHTYPE_LONGDASH = "longdash";
  public static final String VML_STROKE_DASHTYPE_DASHDOT = "dashdot";
  public static final String VML_STROKE_DASHTYPE_LONGDASHDOT = "longdashdot";
  public static final String VML_STROKE_DASHTYPE_LONGDASHDOTDOT = "longdashdotdot";
  public static final String VML_STROKE_ARROW_NULL = null;
  public static final String VML_STROKE_ARROW_BLOCK = "block";
  public static final String VML_STROKE_ARROW_CLASSIC = "classic";
  public static final String VML_STROKE_ARROW_DIAMOND = "diamond";
  public static final String VML_STROKE_ARROW_OVAL = "oval";
  public static final String VML_STROKE_ARROW_OPEN = "open";
  protected String vmlDashType = "solid";
  protected String vmlStartArrow = VML_STROKE_ARROW_NULL;
  protected String vmlEndArrow = VML_STROKE_ARROW_NULL;

  public VMLStrokeTag()
  {
    this.vmlElementTag = "v:stroke";
    this.m_bStyle = false;
  }

  public void AddAttribute(String paramString1, String paramString2, String paramString3)
  {
    this.vmlDashType = paramString1;
    this.vmlStartArrow = paramString2;
    this.vmlEndArrow = paramString3;
  }

  protected String doContent()
  {
    StringBuffer localStringBuffer = new StringBuffer(super.doContent());
    if (this.vmlDashType != null)
    {
      localStringBuffer.append(" dashstyle=\"");
      localStringBuffer.append(this.vmlDashType);
      localStringBuffer.append("\"");
    }
    if (this.vmlStartArrow != null)
    {
      localStringBuffer.append(" startarrow=\"");
      localStringBuffer.append(this.vmlStartArrow);
      localStringBuffer.append("\"");
    }
    if (this.vmlEndArrow != null)
    {
      localStringBuffer.append(" endarrow=\"");
      localStringBuffer.append(this.vmlEndArrow);
      localStringBuffer.append("\"");
    }
    return localStringBuffer.toString();
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.VMLStrokeTag
 * JD-Core Version:    0.6.1
 */