package com.zl.base.core.taglib.html;

import com.zl.base.core.util.StringBufferTool;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.ResponseUtils;

public class BackTag extends BaseHandlerTag
{
  protected String property = null;
  protected String text = null;
  protected String value = null;
  protected String historyUrl = null;

  public String getHistoryUrl()
  {
    return this.historyUrl;
  }

  public void setHistoryUrl(String paramString)
  {
    this.historyUrl = paramString;
  }

  public String getProperty()
  {
    return this.property;
  }

  public void setProperty(String paramString)
  {
    this.property = paramString;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String paramString)
  {
    this.value = paramString;
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
    if (this.bodyContent != null)
    {
      String str = this.bodyContent.getString().trim();
      if (str.length() > 0)
        this.text = str;
    }
    if (this.historyUrl == null)
      try
      {
        this.historyUrl = ((String)this.pageContext.findAttribute("back.url"));
      }
      catch (Exception localException)
      {
        throw new JspException("back.url中无数据");
      }
    return 0;
  }

  public int doEndTag()
    throws JspException
  {
    String str = this.value;
    if ((str == null) && (this.text != null))
      str = this.text;
    if ((str == null) || (str.trim().length() < 1))
      str = "Click";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<input type=\"button\"");
    if (this.property != null)
    {
      localStringBuffer.append(" name=\"");
      localStringBuffer.append(this.property);
      localStringBuffer.append("\"");
    }
    if (this.accesskey != null)
    {
      localStringBuffer.append(" accesskey=\"");
      localStringBuffer.append(this.accesskey);
      localStringBuffer.append("\"");
    }
    if (this.tabindex != null)
    {
      localStringBuffer.append(" tabindex=\"");
      localStringBuffer.append(this.tabindex);
      localStringBuffer.append("\"");
    }
    localStringBuffer.append(" value=\"");
    localStringBuffer.append(str);
    localStringBuffer.append("\"");
    localStringBuffer.append(prepareEventHandlers());
    localStringBuffer.append(prepareStyles());
    localStringBuffer.append(">");
    ResponseUtils.write(this.pageContext, localStringBuffer.toString());
    return 6;
  }

  protected String generateOnClick()
  {
    StringBufferTool localStringBufferTool = new StringBufferTool(new StringBuffer());
    localStringBufferTool.appendln("<script language=\"JavaScript\">");
    localStringBufferTool.appendln("function historyBack(){");
    localStringBufferTool.append("\twindow.location.href =\"");
    localStringBufferTool.append(this.historyUrl);
    localStringBufferTool.append("\"");
    localStringBufferTool.appendln("}");
    localStringBufferTool.appendln("</script>");
    return localStringBufferTool.toString();
  }

  public void release()
  {
    super.release();
    this.property = null;
    this.text = null;
    this.value = null;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.html.BackTag
 * JD-Core Version:    0.6.1
 */