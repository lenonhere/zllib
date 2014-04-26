package com.zl.base.core.taglib.chart.vml;

import com.zl.base.core.util.DoubleRectangle;

public class VMLTextBoxTag extends VMLTag
{
  protected String vmlTextHtml = null;
  protected DoubleRectangle vmlTextSet = new DoubleRectangle(0.0D, 0.0D, 0.0D, 0.0D);

  public VMLTextBoxTag()
  {
    this.vmlElementTag = "v:textbox";
    this.m_bStyle = false;
  }

  public void AddAttribute(DoubleRectangle paramDoubleRectangle, String paramString)
  {
    this.vmlTextSet = paramDoubleRectangle;
    this.vmlTextHtml = paramString;
  }

  protected String doContent()
  {
    StringBuffer localStringBuffer = new StringBuffer(super.doContent());
    localStringBuffer.append(" inset=\"");
    localStringBuffer.append((int)this.vmlTextSet.x);
    localStringBuffer.append(",");
    localStringBuffer.append((int)this.vmlTextSet.y);
    localStringBuffer.append(",");
    localStringBuffer.append((int)this.vmlTextSet.width);
    localStringBuffer.append(",");
    localStringBuffer.append((int)this.vmlTextSet.height);
    localStringBuffer.append("\"");
    return localStringBuffer.toString();
  }

  public String doTag()
  {
    StringBuffer localStringBuffer = new StringBuffer(doStartTag());
    if (this.vmlTextHtml != null)
      localStringBuffer.append(this.vmlTextHtml);
    localStringBuffer.append(doEndTag());
    return localStringBuffer.toString();
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.VMLTextBoxTag
 * JD-Core Version:    0.6.1
 */