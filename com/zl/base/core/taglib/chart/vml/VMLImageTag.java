package com.zl.base.core.taglib.chart.vml;

import com.zl.base.core.util.DoubleRectangle;

public class VMLImageTag extends VMLTag
{
  protected String vmlSrc = null;

  public VMLImageTag()
  {
    this.vmlElementTag = "v:image";
  }

  public void AddAttribute(DoubleRectangle paramDoubleRectangle, String paramString)
  {
    this.vmlRect = paramDoubleRectangle;
    this.vmlSrc = paramString;
  }

  protected String doContent()
  {
    if (this.vmlSrc == null)
      return null;
    StringBuffer localStringBuffer = new StringBuffer(super.doContent());
    localStringBuffer.append(" src=\"");
    localStringBuffer.append(this.vmlSrc);
    localStringBuffer.append("\"");
    return localStringBuffer.toString();
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.VMLImageTag
 * JD-Core Version:    0.6.1
 */