package com.zl.base.core.taglib.chart.vml;

import com.zl.base.core.util.DoubleRectangle;

public class VMLRectTag extends VMLTag
{
  public VMLRectTag()
  {
    this.vmlElementTag = "v:rect";
  }

  public VMLRectTag(boolean paramBoolean)
  {
    if (paramBoolean)
      this.vmlElementTag = "v:roundrect";
    else
      this.vmlElementTag = "v:rect";
  }

  public void AddAttribute(DoubleRectangle paramDoubleRectangle, String paramString1, String paramString2)
  {
    this.vmlRect = paramDoubleRectangle;
    this.vmlStrokeColor = paramString1;
    this.vmlFillColor = paramString2;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.VMLRectTag
 * JD-Core Version:    0.6.1
 */