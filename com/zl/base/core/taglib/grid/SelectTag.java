package com.zl.base.core.taglib.grid;

import com.zl.base.core.util.StringBufferTool;
import java.io.PrintStream;
import java.util.Vector;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.util.MessageResources;

public class SelectTag extends BaseInputTag
{
  protected Vector options;
  private String addspacerow = null;
  private String spacerowvalue = "-1";

  public int doStartTag()
    throws JspException
  {
    this.options = new Vector();
    this.pageContext.setAttribute("Constants.SELECT_KEY", this);
    return 1;
  }

  public String getaddspacerow()
  {
    if (this.addspacerow == null)
      return "false";
    return this.addspacerow;
  }

  public void setaddspacerow(String paramString)
  {
    this.addspacerow = paramString;
  }

  public String getspacerowvalue()
  {
    return this.spacerowvalue;
  }

  public void setspacerowvalue(String paramString)
  {
    this.spacerowvalue = paramString;
  }

  public int doEndTag()
    throws JspException
  {
    this.pageContext.removeAttribute("Constants.SELECT_KEY");
    BaseGrid localBaseGrid = (BaseGrid)this.pageContext.getAttribute("Grid");
    if (localBaseGrid == null)
      throw new JspException(messages.getMessage("optionsTag.select"));
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

  public void generateRows(StringBuffer paramStringBuffer, String paramString)
  {
    paramStringBuffer.append("<td >");
    paramStringBuffer.append("<select type=\"");
    paramStringBuffer.append(this.type);
    paramStringBuffer.append("\" name=\"");
    paramStringBuffer.append(this.property);
    paramStringBuffer.append("\"");
    if (this.accesskey != null)
    {
      paramStringBuffer.append(" accesskey=\"");
      paramStringBuffer.append(this.accesskey);
      paramStringBuffer.append("\"");
    }
    if (this.maxlength != null)
    {
      paramStringBuffer.append(" maxlength=\"");
      paramStringBuffer.append(this.maxlength);
      paramStringBuffer.append("\"");
    }
    if (this.cols != null)
    {
      paramStringBuffer.append(" size=\"");
      paramStringBuffer.append(this.cols);
      paramStringBuffer.append("\"");
    }
    if (this.tabindex != null)
    {
      paramStringBuffer.append(" tabindex=\"");
      paramStringBuffer.append(this.tabindex);
      paramStringBuffer.append("\"");
    }
    paramStringBuffer.append(prepareEventHandlers());
    paramStringBuffer.append(prepareStyles());
    paramStringBuffer.append(">");
    if (getaddspacerow().toLowerCase().equals("true"))
    {
      Option localOption = new Option();
      Struct localStruct = new Struct();
      localStruct.lable = "";
      localStruct.value = this.spacerowvalue;
      Vector localVector = new Vector();
      localVector.add(0, localStruct);
      localOption.setOptionValue(localVector);
      paramStringBuffer.append(localOption.generateRows(""));
    }
    getRowOptions(paramStringBuffer, paramString);
    paramStringBuffer.append("</select>");
    paramStringBuffer.append("</td>");
  }

  public String generateInnerHTML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<select type=\"");
    localStringBuffer.append(this.type);
    localStringBuffer.append("\" name=\"");
    localStringBuffer.append(this.property);
    localStringBuffer.append("\"");
    if (this.accesskey != null)
    {
      localStringBuffer.append(" accesskey=\"");
      localStringBuffer.append(this.accesskey);
      localStringBuffer.append("\"");
    }
    if (this.maxlength != null)
    {
      localStringBuffer.append(" maxlength=\"");
      localStringBuffer.append(this.maxlength);
      localStringBuffer.append("\"");
    }
    if (this.cols != null)
    {
      localStringBuffer.append(" size=\"");
      localStringBuffer.append(this.cols);
      localStringBuffer.append("\"");
    }
    if (this.tabindex != null)
    {
      localStringBuffer.append(" tabindex=\"");
      localStringBuffer.append(this.tabindex);
      localStringBuffer.append("\"");
    }
    localStringBuffer.append(" value=\"");
    localStringBuffer.append("\"");
    localStringBuffer.append(prepareEventHandlers());
    localStringBuffer.append(prepareStyles());
    localStringBuffer.append(">");
    getOptionInnerHtml(localStringBuffer);
    localStringBuffer.append("</select>");
    return localStringBuffer.toString();
  }

  private void getRowOptions(StringBuffer paramStringBuffer, String paramString)
  {
    for (int i = 0; i < this.options.size(); i++)
    {
      Ioption localIoption = (Ioption)this.options.get(i);
      paramStringBuffer.append(localIoption.generateRows(paramString));
    }
  }

  public void getOptionInnerHtml(StringBuffer paramStringBuffer)
  {
    for (int i = 0; i < this.options.size(); i++)
    {
      Ioption localIoption = (Ioption)this.options.get(i);
      paramStringBuffer.append(localIoption.generateInnerHTML());
    }
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
    this.capital = null;
    this.property = null;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.grid.SelectTag
 * JD-Core Version:    0.6.1
 */