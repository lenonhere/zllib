package com.zl.base.core.taglib.chart.vml;

import com.zl.base.core.util.DoublePoint;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class VMLLineTag extends VMLTag
{
  protected ArrayList vmlPoints = new ArrayList();

  public void AddAttribute(ArrayList paramArrayList, double paramDouble, String paramString)
  {
    this.vmlPoints.clear();
    this.vmlPoints.addAll(paramArrayList);
    this.vmlStrokeWidth = paramDouble;
    this.vmlStrokeColor = paramString;
    if (this.vmlPoints.size() < 2)
      this.vmlElementTag = null;
    else if (this.vmlPoints.size() == 2)
      this.vmlElementTag = "v:line";
    else
      this.vmlElementTag = "v:polyline";
  }

  protected String doContent()
  {
    StringBuffer localStringBuffer = new StringBuffer(super.doContent());
    DoublePoint localDoublePoint2;
    if (this.vmlElementTag.equals("v:line"))
    {
      DoublePoint localDoublePoint1 = (DoublePoint)this.vmlPoints.get(0);
      localDoublePoint2 = (DoublePoint)this.vmlPoints.get(1);
      localStringBuffer.append(" from=\"");
      localStringBuffer.append(this.vmldf.format(localDoublePoint1.x));
      localStringBuffer.append(",");
      localStringBuffer.append(this.vmldf.format(localDoublePoint1.y));
      localStringBuffer.append("\"");
      localStringBuffer.append(" to=\"");
      localStringBuffer.append(this.vmldf.format(localDoublePoint2.x));
      localStringBuffer.append(",");
      localStringBuffer.append(this.vmldf.format(localDoublePoint2.y));
      localStringBuffer.append("\"");
    }
    if (this.vmlElementTag.equals("v:polyline"))
    {
      localStringBuffer.append(" filled=\"false\"");
      localStringBuffer.append(" points=\"");
      for (int i = 0; i < this.vmlPoints.size(); i++)
      {
        localDoublePoint2 = (DoublePoint)this.vmlPoints.get(i);
        localStringBuffer.append(this.vmldf.format(localDoublePoint2.x));
        localStringBuffer.append(",");
        localStringBuffer.append(this.vmldf.format(localDoublePoint2.y));
        localStringBuffer.append(" ");
      }
      localStringBuffer.append("\"");
    }
    return localStringBuffer.toString();
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.VMLLineTag
 * JD-Core Version:    0.6.1
 */