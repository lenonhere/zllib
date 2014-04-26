package com.zl.base.core.taglib.grid;

import java.util.Vector;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class OptionTag extends BodyTagSupport
  implements Ioption, Cloneable
{
  protected String text = null;
  protected boolean disabled = false;
  protected String key = null;
  private String style = null;
  private String styleClass = null;
  protected String value = null;
  protected String value1;
  protected String value2;

  public boolean getDisabled()
  {
    return this.disabled;
  }

  public void setDisabled(boolean paramBoolean)
  {
    this.disabled = paramBoolean;
  }

  public String getKey()
  {
    return this.key;
  }

  public void setKey(String paramString)
  {
    this.key = paramString;
  }

  public String getStyle()
  {
    return this.style;
  }

  public void setStyle(String paramString)
  {
    this.style = paramString;
  }

  public String getStyleClass()
  {
    return this.styleClass;
  }

  public void setStyleClass(String paramString)
  {
    this.styleClass = paramString;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String paramString)
  {
    this.value = paramString;
  }

  public String getValue1()
  {
    return this.value1;
  }

  public void setValue1(String paramString)
  {
    this.value1 = paramString;
  }

  public String getValue2()
  {
    return this.value2;
  }

  public void setValue2(String paramString)
  {
    this.value2 = paramString;
  }

  public int doStartTag()
    throws JspException
  {
    this.text = null;
    return 2;
  }

  public int doAfterBody()
    throws JspException
  {
    String str = this.bodyContent.getString();
    if (str != null)
    {
      str = str.trim();
      if (str.length() > 0)
        this.text = str;
    }
    return 0;
  }

  public int doEndTag()
    throws JspException
  {
    SelectTag localSelectTag = (SelectTag)this.pageContext.getAttribute("Constants.SELECT_KEY");
    if (localSelectTag == null)
    {
      JspException localJspException1 = new JspException("no select");
      throw localJspException1;
    }
    try
    {
      localSelectTag.options.add(clone());
    }
    catch (Exception localException)
    {
      JspException localJspException2 = new JspException("clone error!");
      throw localJspException2;
    }
    return 6;
  }

  public void release1()
  {
    super.release();
    this.disabled = false;
    this.key = null;
    this.style = null;
    this.styleClass = null;
    this.text = null;
    this.value = null;
  }

  protected String text()
    throws JspException
  {
    if (this.text != null)
      return this.text;
    return "";
  }

  public String generateInnerHTML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<option value=\"");
    localStringBuffer.append(this.value);
    localStringBuffer.append("\"");
    if (this.disabled)
      localStringBuffer.append(" disabled=\"disabled\"");
    if (this.style != null)
    {
      localStringBuffer.append(" style=\"");
      localStringBuffer.append(this.style);
      localStringBuffer.append("\"");
    }
    if (this.styleClass != null)
    {
      localStringBuffer.append(" class=\"");
      localStringBuffer.append(this.styleClass);
      localStringBuffer.append("\"");
    }
    localStringBuffer.append(">");
    String str = this.text;
    if (str == null)
      localStringBuffer.append(this.value);
    else
      localStringBuffer.append(str);
    localStringBuffer.append("</option>");
    return localStringBuffer.toString();
  }

  public String generateRows(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<option value=\"");
    localStringBuffer.append(paramString);
    localStringBuffer.append("\"");
    if (this.disabled)
      localStringBuffer.append(" disabled=\"disabled\"");
    if (this.value.equals(paramString))
      localStringBuffer.append(" selected=\"selected\"");
    if (this.style != null)
    {
      localStringBuffer.append(" style=\"");
      localStringBuffer.append(this.style);
      localStringBuffer.append("\"");
    }
    if (this.styleClass != null)
    {
      localStringBuffer.append(" class=\"");
      localStringBuffer.append(this.styleClass);
      localStringBuffer.append("\"");
    }
    localStringBuffer.append(">");
    String str = this.text;
    if (str == null)
      localStringBuffer.append(paramString);
    else
      localStringBuffer.append(str);
    localStringBuffer.append("</option>");
    return localStringBuffer.toString();
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.grid.OptionTag
 * JD-Core Version:    0.6.1
 */