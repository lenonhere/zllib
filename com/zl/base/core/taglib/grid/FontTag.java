package com.zl.base.core.taglib.grid;

import com.zl.base.core.util.StringBufferTool;
import java.io.PrintStream;
import java.util.Vector;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.util.MessageResources;

public class FontTag extends BaseInputTag
{
  private String property = null;
  private String size = null;
  private String capital = null;
  private String value = null;

  public String getProperty()
  {
    return this.property;
  }

  public void setProperty(String paramString)
  {
    this.property = paramString;
  }

  public String getCapital()
  {
    return this.capital;
  }

  public void setCapital(String paramString)
  {
    this.capital = paramString;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String paramString)
  {
    this.value = paramString;
  }

  public String getsize()
  {
    return this.size;
  }

  public void setsize(String paramString)
  {
    this.size = paramString;
  }

  public int doStartTag()
    throws JspException
  {
    return 0;
  }

  public int doEndTag()
    throws JspException
  {
    BaseGrid localBaseGrid = (BaseGrid)this.pageContext.getAttribute("Grid");
    if (localBaseGrid == null)
      throw new JspException(messages.getMessage("Grid.Font"));
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localBaseGrid.columns.add(clone());
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return 6;
  }

  public void genetateHead(StringBuffer paramStringBuffer, String paramString)
  {
    paramStringBuffer.append("<th ");
    this.styleClass = "tableDataHead";
    paramStringBuffer.append(prepareStyles());
    this.styleClass = "tableData";
    paramStringBuffer.append(">");
    paramStringBuffer.append("<Font style=");
    paramStringBuffer.append(this.style);
    paramStringBuffer.append(" class=\"tableData\"");
    paramStringBuffer.append(" readonly=true");
    paramStringBuffer.append(" tabindex=-1");
    paramStringBuffer.append(">");
    paramStringBuffer.append(this.capital);
    paramStringBuffer.append("</Font>");
    paramStringBuffer.append("</th>");
  }

  public void genetateFoot(StringBuffer paramStringBuffer, String paramString)
  {
    paramStringBuffer.append("<th ");
    this.styleClass = "tableData";
    paramStringBuffer.append(prepareStyles());
    paramStringBuffer.append(">");
    paramStringBuffer.append("<Font style=\"");
    paramStringBuffer.append(this.style);
    paramStringBuffer.append("\" class=tableData");
    paramStringBuffer.append(" readonly=\"true\"");
    paramStringBuffer.append(" tabindex=\"-1\"");
    paramStringBuffer.append(">");
    paramStringBuffer.append(paramString);
    paramStringBuffer.append("</Font>");
    paramStringBuffer.append("</th>");
  }

  public void generateRows(StringBuffer paramStringBuffer, String paramString)
  {
    paramStringBuffer.append("<td>");
    paramStringBuffer.append("<Font name=\"");
    paramStringBuffer.append(this.property);
    paramStringBuffer.append("\"");
    if (this.accesskey != null)
    {
      paramStringBuffer.append(" accesskey=\"");
      paramStringBuffer.append(this.accesskey);
      paramStringBuffer.append("\"");
    }
    if (this.size != null)
    {
      paramStringBuffer.append(" size=\"");
      paramStringBuffer.append(this.size);
      paramStringBuffer.append("\"");
    }
    if (this.tabindex != null)
    {
      paramStringBuffer.append(" tabindex=\"");
      paramStringBuffer.append(this.tabindex);
      paramStringBuffer.append("\"");
    }
    paramStringBuffer.append("\"");
    paramStringBuffer.append(prepareEventHandlers());
    paramStringBuffer.append(prepareStyles());
    paramStringBuffer.append(">");
    paramStringBuffer.append(paramString);
    paramStringBuffer.append("</Font>");
    paramStringBuffer.append("</td>");
  }

  public String generateInnerHTML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<Font name=\"");
    localStringBuffer.append(this.property);
    localStringBuffer.append("\"");
    if (this.accesskey != null)
    {
      localStringBuffer.append(" accesskey=\"");
      localStringBuffer.append(this.accesskey);
      localStringBuffer.append("\"");
    }
    if (this.size != null)
    {
      localStringBuffer.append(" size=\"");
      localStringBuffer.append(this.size);
      localStringBuffer.append("\"");
    }
    if (this.tabindex != null)
    {
      localStringBuffer.append(" tabindex=\"");
      localStringBuffer.append(this.tabindex);
      localStringBuffer.append("\"");
    }
    if (this.value != null)
    {
      localStringBuffer.append(" value=\"");
      localStringBuffer.append(this.value);
      localStringBuffer.append("\"");
    }
    localStringBuffer.append(prepareEventHandlers());
    localStringBuffer.append(prepareStyles());
    localStringBuffer.append(">");
    return localStringBuffer.toString();
  }

  public String generateLightOn(String paramString1, String paramString2)
  {
    StringBufferTool localStringBufferTool = new StringBufferTool(new StringBuffer());
    localStringBufferTool.append("item=document.all(\"");
    localStringBufferTool.append(this.property);
    localStringBufferTool.append("\")");
    localStringBufferTool.append("[");
    localStringBufferTool.append(paramString2);
    localStringBufferTool.appendln("]");
    localStringBufferTool.appendln("item = eval(item)");
    localStringBufferTool.append("item.className = \"");
    localStringBufferTool.append(paramString1);
    localStringBufferTool.appendln("\"");
    return localStringBufferTool.toString();
  }

  public void release()
  {
    super.release();
    this.property = null;
    this.size = null;
    this.capital = null;
    this.value = null;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.grid.FontTag
 * JD-Core Version:    0.6.1
 */