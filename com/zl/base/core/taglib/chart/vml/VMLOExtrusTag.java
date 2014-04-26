package com.zl.base.core.taglib.chart.vml;

import java.text.DecimalFormat;

public class VMLOExtrusTag extends VMLTag
{
  public static final double VML_DEFAULT_SHADOWDEPT = 15.0D;
  public static final String VML_DEFAULT_SHADOWVEXT = "view";
  protected double vmlShadowDept = 15.0D;
  protected String vmlShadowVext = "view";

  public VMLOExtrusTag()
  {
    this.vmlElementTag = "o:extrusion";
    this.m_bStyle = false;
  }

  public void AddAttribute(String paramString1, double paramDouble, String paramString2)
  {
    this.vmlFillColor = paramString1;
    this.vmlShadowDept = paramDouble;
    this.vmlShadowVext = paramString2;
  }

  protected String doContent()
  {
    StringBuffer localStringBuffer = new StringBuffer(super.doContent());
    localStringBuffer.append(" v:ext=\"");
    localStringBuffer.append(this.vmlShadowVext);
    localStringBuffer.append("\"");
    localStringBuffer.append(" backdepth=\"");
    localStringBuffer.append(this.vmldf.format(this.vmlShadowDept));
    localStringBuffer.append("\"");
    if (this.vmlFillColor != null)
    {
      localStringBuffer.append(" color=\"");
      localStringBuffer.append(this.vmlFillColor);
      localStringBuffer.append("\"");
    }
    localStringBuffer.append(" on=\"t\"");
    return localStringBuffer.toString();
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.VMLOExtrusTag
 * JD-Core Version:    0.6.1
 */