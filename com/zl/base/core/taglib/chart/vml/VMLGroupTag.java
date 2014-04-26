package com.zl.base.core.taglib.chart.vml;

import com.zl.base.core.util.DoubleRectangle;
import com.zl.base.core.util.DoubleSize;

public class VMLGroupTag extends VMLTag
{
  protected DoubleSize vmlCoordSize = null;

  public VMLGroupTag()
  {
    this.vmlElementTag = "v:group";
  }

  public void AddAttribute(DoubleRectangle paramDoubleRectangle, DoubleSize paramDoubleSize)
  {
    this.vmlRect = paramDoubleRectangle;
    this.vmlCoordSize = paramDoubleSize;
  }

  protected String doContent()
  {
    StringBuffer localStringBuffer = new StringBuffer(super.doContent());
    if (this.vmlCoordSize != null)
    {
      localStringBuffer.append(" coordsize=\"");
      localStringBuffer.append((int)this.vmlCoordSize.cx);
      localStringBuffer.append(",");
      localStringBuffer.append((int)this.vmlCoordSize.cy);
      localStringBuffer.append("\"");
    }
    return localStringBuffer.toString();
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.VMLGroupTag
 * JD-Core Version:    0.6.1
 */