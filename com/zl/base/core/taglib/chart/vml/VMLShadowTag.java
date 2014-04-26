package com.zl.base.core.taglib.chart.vml;

import com.zl.base.core.util.DoubleSize;
import java.text.DecimalFormat;

public class VMLShadowTag extends VMLTag
{
  public static final String VML_SHADOW_TYPE_SINGLE = "single";
  public static final String VML_SHADOW_TYPE_DOUBLE = "double";
  public static final String VML_SHADOW_TYPE_PERSPECTIVE = "perspective";
  public static final String VML_SHADOW_TYPE_SHAPERELATIVE = "shaperelative";
  public static final String VML_SHADOW_TYPE_DRAWINGRELATIVE = "drawingrelative";
  public static final String VML_SHADOW_TYPE_EMBOSS = "emboss";
  protected String vmlShadowType = "single";
  protected DoubleSize vmlShadowOffsize = new DoubleSize(5.0D, 5.0D);

  public VMLShadowTag()
  {
    this.vmlElementTag = "v:shadow";
    this.m_bStyle = false;
  }

  public void AddAttribut(String paramString1, String paramString2, DoubleSize paramDoubleSize)
  {
    this.vmlShadowColor = paramString1;
    this.vmlShadowType = paramString2;
    this.vmlShadowOffsize = paramDoubleSize;
  }

  protected String doContent()
  {
    StringBuffer localStringBuffer = new StringBuffer(super.doContent());
    localStringBuffer.append(" on=\"true\"");
    localStringBuffer.append(" type=\"");
    localStringBuffer.append(this.vmlShadowType);
    localStringBuffer.append("\"");
    localStringBuffer.append(" color=\"");
    localStringBuffer.append(this.vmlShadowColor);
    localStringBuffer.append("\"");
    localStringBuffer.append(" offset=\"");
    localStringBuffer.append(this.vmldf.format(this.vmlShadowOffsize.cx));
    localStringBuffer.append(",");
    localStringBuffer.append(this.vmldf.format(this.vmlShadowOffsize.cy));
    localStringBuffer.append("\"");
    return localStringBuffer.toString();
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.VMLShadowTag
 * JD-Core Version:    0.6.1
 */