package com.zl.base.core.taglib.chart.vml;

import com.zl.base.core.util.DoubleRectangle;

public class VMLOvalTag extends VMLTag
{
  public VMLOvalTag()
  {
    this.vmlElementTag = "v:oval";
  }

  public void AddAttribute(DoubleRectangle paramDoubleRectangle, String paramString)
  {
    this.vmlRect = paramDoubleRectangle;
    this.vmlFillColor = paramString;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.VMLOvalTag
 * JD-Core Version:    0.6.1
 */