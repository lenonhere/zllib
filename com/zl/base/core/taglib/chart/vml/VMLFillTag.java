package com.zl.base.core.taglib.chart.vml;

public class VMLFillTag extends VMLTag
{
  public static final String VML_DEFAULT_MASKCOLOR = "#FFFFFF";
  public static final String VML_DEFALUT_FILLROTATE = "t";
  public static final String VML_DEFALUT_FILLTYPE = "gradient";
  protected String vmlFillRotate = "t";
  protected String vmlFillType = "gradient";
  protected String vmlFillAngle = null;

  public VMLFillTag()
  {
    this.vmlElementTag = "v:fill";
    this.vmlMaskColor = "#FFFFFF";
    this.m_bStyle = false;
  }

  public void AddAttribute(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.vmlMaskColor = paramString1;
    this.vmlFillRotate = paramString2;
    this.vmlFillType = paramString3;
    this.vmlFillAngle = paramString4;
  }

  protected String doContent()
  {
    if (this.vmlMaskColor == null)
      return "";
    if (this.vmlMaskColor.length() > 0)
    {
      StringBuffer localStringBuffer = new StringBuffer(super.doContent());
      localStringBuffer.append(" color2=\"");
      localStringBuffer.append(this.vmlMaskColor);
      localStringBuffer.append("\"");
      localStringBuffer.append(" rotate=\"");
      localStringBuffer.append(this.vmlFillRotate);
      localStringBuffer.append("\"");
      localStringBuffer.append(" type=\"");
      localStringBuffer.append(this.vmlFillType);
      localStringBuffer.append("\"");
      if (this.vmlFillAngle != null)
      {
        localStringBuffer.append(" angle=\"");
        localStringBuffer.append(this.vmlFillAngle);
        localStringBuffer.append("\"");
      }
      return localStringBuffer.toString();
    }
    return "";
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.VMLFillTag
 * JD-Core Version:    0.6.1
 */