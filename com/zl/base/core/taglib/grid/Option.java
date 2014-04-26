package com.zl.base.core.taglib.grid;

import java.util.Vector;
import org.apache.struts.util.ResponseUtils;

public class Option
  implements Ioption
{
  private Vector optionValue = null;

  public void setOptionValue(Vector paramVector)
  {
    if (this.optionValue != null)
      this.optionValue = null;
    this.optionValue = paramVector;
  }

  public String generateInnerHTML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < this.optionValue.size(); i++)
    {
      Struct localStruct = (Struct)this.optionValue.get(i);
      addOption(localStringBuffer, localStruct, "");
    }
    return localStringBuffer.toString();
  }

  public String generateRows(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < this.optionValue.size(); i++)
    {
      Struct localStruct = (Struct)this.optionValue.get(i);
      addOption(localStringBuffer, localStruct, paramString);
    }
    return localStringBuffer.toString();
  }

  protected void addOption(StringBuffer paramStringBuffer, Struct paramStruct, boolean paramBoolean)
  {
    paramStringBuffer.append("<option value=\"");
    paramStringBuffer.append(paramStruct.value.trim());
    paramStringBuffer.append("\"");
    if (paramBoolean)
      paramStringBuffer.append(" selected=\"selected\"");
    if (paramStruct.Style != null)
    {
      paramStringBuffer.append(" style=\"");
      paramStringBuffer.append(paramStruct.Style);
      paramStringBuffer.append("\"");
    }
    if (paramStruct.StyleClass != null)
    {
      paramStringBuffer.append(" class=\"");
      paramStringBuffer.append(paramStruct.StyleClass);
      paramStringBuffer.append("\"");
    }
    paramStringBuffer.append(">");
    paramStringBuffer.append(ResponseUtils.filter(paramStruct.lable).trim());
    paramStringBuffer.append("</option>");
  }

  protected void addOption(StringBuffer paramStringBuffer, Struct paramStruct, String paramString)
  {
    boolean bool = false;
    if (paramString.trim().equals(paramStruct.value.trim()))
      bool = true;
    else
      bool = false;
    addOption(paramStringBuffer, paramStruct, bool);
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.grid.Option
 * JD-Core Version:    0.6.1
 */