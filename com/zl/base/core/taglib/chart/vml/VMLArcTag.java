package com.zl.base.core.taglib.chart.vml;

import java.text.DecimalFormat;

public class VMLArcTag extends VMLTag
{
  protected double vmlStartArc = 0.0D;
  protected double vmlEndArc = 90.0D;

  public VMLArcTag()
  {
    this.vmlElementTag = "v:arc";
  }

  public void AddAttribute(double paramDouble1, double paramDouble2)
  {
    this.vmlStartArc = paramDouble1;
    this.vmlEndArc = paramDouble2;
  }

  protected String doContent()
  {
    StringBuffer localStringBuffer = new StringBuffer(super.doContent());
    localStringBuffer.append(" filled=\"False\"");
    localStringBuffer.append(" startangle=\"");
    localStringBuffer.append(this.vmldf.format(this.vmlStartArc));
    localStringBuffer.append("\"");
    localStringBuffer.append(" endangle=\"");
    localStringBuffer.append(this.vmldf.format(this.vmlEndArc));
    localStringBuffer.append("\"");
    return localStringBuffer.toString();
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.VMLArcTag
 * JD-Core Version:    0.6.1
 */