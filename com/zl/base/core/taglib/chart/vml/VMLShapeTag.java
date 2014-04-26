package com.zl.base.core.taglib.chart.vml;

import com.zl.base.core.util.DoublePoint;
import com.zl.base.core.util.DoubleRectangle;
import com.zl.base.core.util.DoubleSize;

public class VMLShapeTag extends VMLTag
{
  protected String vmlPath = null;
  protected DoublePoint vmlCoordOrigin = new DoublePoint(6000.0D, 6000.0D);
  protected DoubleSize vmlCoordSize = new DoubleSize(12000.0D, 12000.0D);

  public VMLShapeTag()
  {
    this.vmlElementTag = "v:shape";
  }

  public void AddAttribute(DoubleRectangle paramDoubleRectangle, String paramString1, String paramString2, String paramString3)
  {
    this.vmlRect = paramDoubleRectangle;
    this.vmlStrokeColor = paramString1;
    this.vmlFillColor = paramString2;
    this.vmlPath = paramString3;
  }

  protected String doContent()
  {
    StringBuffer localStringBuffer = new StringBuffer(super.doContent());
    if (this.vmlPath != null)
    {
      localStringBuffer.append(" coordorigin=\"");
      localStringBuffer.append((int)this.vmlCoordOrigin.x);
      localStringBuffer.append(",");
      localStringBuffer.append((int)this.vmlCoordOrigin.y);
      localStringBuffer.append("\"");
      localStringBuffer.append(" coordsize=\"");
      localStringBuffer.append((int)this.vmlCoordSize.cx);
      localStringBuffer.append(",");
      localStringBuffer.append((int)this.vmlCoordSize.cy);
      localStringBuffer.append("\"");
      localStringBuffer.append(" path=\"");
      localStringBuffer.append(this.vmlPath);
      localStringBuffer.append("\"");
    }
    return localStringBuffer.toString();
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.VMLShapeTag
 * JD-Core Version:    0.6.1
 */