package com.zl.base.core.taglib.chart.vml;

import com.zl.base.core.util.DoubleRectangle;
import java.text.DecimalFormat;

public abstract class VMLTag
{
  public static final String VML_HTML_DEFINE_NAMESPACE = "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">";
  public static final String VML_HTML_DEFINE_STYLE = "<!--[if !mso]> <STYLE>v\\:* { BEHAVIOR: url(#default#VML) } o\\:* { BEHAVIOR: url(#default#VML) } .shape { BEHAVIOR: url(#default#VML) } </STYLE> <![endif]-->";
  protected DecimalFormat vmldf = new DecimalFormat("0.##");
  protected String vmlElementTag = null;
  protected String vmlID = null;
  protected String vmlALT = null;
  protected String vmlStyle = "style=\"position:relative\"";
  protected DoubleRectangle vmlRect = null;
  protected double vmlStrokeWidth = 1.0D;
  protected String vmlStrokeColor = null;
  protected String vmlFillColor = null;
  protected String vmlMaskColor = null;
  protected String vmlShadowColor = null;
  protected String vmlScrpit = null;
  protected String vmlHref = null;
  protected int vmlz_index = 0;
  protected boolean m_bStyle = true;

  public void Clear()
  {
    this.vmlID = null;
    this.vmlALT = null;
    this.vmlStyle = "style=\"position:relative\"";
    this.vmlRect = null;
    this.vmlStrokeWidth = 1.0D;
    this.vmlStrokeColor = null;
    this.vmlFillColor = null;
    this.vmlMaskColor = null;
    this.vmlShadowColor = null;
    this.vmlScrpit = null;
    this.vmlHref = null;
  }

  public String doStartTag()
  {
    if (this.vmlElementTag == null)
      return "";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<");
    localStringBuffer.append(this.vmlElementTag);
    localStringBuffer.append(doContent());
    localStringBuffer.append(">");
    return localStringBuffer.toString();
  }

  public String doEndTag()
  {
    if (this.vmlElementTag == null)
      return "";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("</");
    localStringBuffer.append(this.vmlElementTag);
    localStringBuffer.append(">");
    return localStringBuffer.toString();
  }

  public String doTag()
  {
    if (this.vmlElementTag == null)
      return "";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<");
    localStringBuffer.append(this.vmlElementTag);
    localStringBuffer.append(doContent());
    localStringBuffer.append("/>");
    return localStringBuffer.toString();
  }

  protected String doContent()
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    if (this.vmlID != null)
    {
      localStringBuffer1.append(" id=\"");
      localStringBuffer1.append(this.vmlID);
      localStringBuffer1.append("\"");
    }
    StringBuffer localStringBuffer2;
    if (this.vmlRect != null)
    {
      localStringBuffer2 = new StringBuffer();
      localStringBuffer2.append("left:");
      localStringBuffer2.append((int)this.vmlRect.x);
      localStringBuffer2.append(";");
      localStringBuffer2.append("top:");
      localStringBuffer2.append((int)this.vmlRect.y);
      localStringBuffer2.append(";");
      localStringBuffer2.append("width:");
      localStringBuffer2.append((int)this.vmlRect.width);
      localStringBuffer2.append(";");
      localStringBuffer2.append("height:");
      localStringBuffer2.append((int)this.vmlRect.height);
      AddVMLStyle(localStringBuffer2.toString());
    }
    if (this.vmlz_index != 0)
    {
      localStringBuffer2 = new StringBuffer();
      localStringBuffer2.append("z-index:");
      localStringBuffer2.append(this.vmlz_index);
      AddVMLStyle(localStringBuffer2.toString());
    }
    if (this.m_bStyle)
    {
      localStringBuffer1.append(" ");
      localStringBuffer1.append(this.vmlStyle);
    }
    if (this.vmlALT != null)
    {
      localStringBuffer1.append(" title=\"");
      localStringBuffer1.append(this.vmlALT);
      localStringBuffer1.append("\"");
    }
    if (this.vmlStrokeWidth != 1.0D)
    {
      localStringBuffer1.append(" strokeweight=\"");
      localStringBuffer1.append(this.vmldf.format(this.vmlStrokeWidth));
      localStringBuffer1.append("\"");
    }
    if (this.vmlStrokeColor != null)
    {
      localStringBuffer1.append(" strokecolor=\"");
      localStringBuffer1.append(this.vmlStrokeColor);
      localStringBuffer1.append("\"");
    }
    if (this.vmlFillColor != null)
    {
      localStringBuffer1.append(" fillcolor=\"");
      localStringBuffer1.append(this.vmlFillColor);
      localStringBuffer1.append("\"");
    }
    if (this.vmlHref != null)
    {
      localStringBuffer1.append(" href=\"");
      localStringBuffer1.append(this.vmlHref);
      localStringBuffer1.append("\"");
    }
    if (this.vmlScrpit != null)
    {
      localStringBuffer1.append(" onclick=\"");
      localStringBuffer1.append(this.vmlScrpit);
      localStringBuffer1.append("\"");
    }
    return localStringBuffer1.toString();
  }

  public void AddVMLStyle(String paramString)
  {
    if (paramString != null)
    {
      StringBuffer localStringBuffer = new StringBuffer(this.vmlStyle.substring(0, this.vmlStyle.length() - 1));
      localStringBuffer.append(";");
      localStringBuffer.append(paramString);
      localStringBuffer.append("\"");
      this.vmlStyle = localStringBuffer.toString();
    }
  }

  public void InitVMLStyle(boolean paramBoolean)
  {
    if (paramBoolean)
      this.vmlStyle = "style=\"position:absolute\"";
    else
      this.vmlStyle = "style=\"position:relative\"";
  }

  public void setID(String paramString)
  {
    this.vmlID = paramString;
  }

  public void setAlt(String paramString)
  {
    this.vmlALT = paramString;
  }

  public void setRectangle(DoubleRectangle paramDoubleRectangle)
  {
    this.vmlRect = paramDoubleRectangle;
  }

  public void setStrokeWidth(double paramDouble)
  {
    this.vmlStrokeWidth = paramDouble;
  }

  public void setStrokeColor(String paramString)
  {
    this.vmlStrokeColor = paramString;
  }

  public void setFillColor(String paramString)
  {
    this.vmlFillColor = paramString;
  }

  public void setMaskColor(String paramString)
  {
    this.vmlMaskColor = paramString;
  }

  public void setShadowColor(String paramString)
  {
    this.vmlShadowColor = paramString;
  }

  public void setScript(String paramString)
  {
    this.vmlScrpit = paramString;
  }

  public void setHref(String paramString)
  {
    this.vmlHref = paramString;
  }

  public void setZ_index(int paramInt)
  {
    this.vmlz_index = paramInt;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.VMLTag
 * JD-Core Version:    0.6.1
 */