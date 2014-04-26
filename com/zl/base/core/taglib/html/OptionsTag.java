package com.zl.base.core.taglib.html;

import com.zl.util.MethodFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.util.IteratorAdapter;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;

public class OptionsTag extends TagSupport
{
  protected static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.html.LocalStrings");
  protected String collection = null;
  protected String labelName = null;
  protected String labelProperty = null;
  protected String name = null;
  protected String property = null;
  private String style = null;
  private String styleClass = null;
  protected String value1;
  protected String value2;
  private String group = null;

  public String getCollection()
  {
    return this.collection;
  }

  public void setCollection(String paramString)
  {
    this.collection = paramString;
  }

  public String getLabelName()
  {
    return this.labelName;
  }

  public void setLabelName(String paramString)
  {
    this.labelName = paramString;
  }

  public String getLabelProperty()
  {
    return this.labelProperty;
  }

  public void setLabelProperty(String paramString)
  {
    this.labelProperty = paramString;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public String getProperty()
  {
    return this.property;
  }

  public void setProperty(String paramString)
  {
    this.property = paramString;
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

  public String getgroup()
  {
    return this.group;
  }

  public void setgroup(String paramString)
  {
    this.group = paramString;
  }

  public int doStartTag()
    throws JspException
  {
    return 0;
  }

  private String getmatchvalue(SelectTag paramSelectTag, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    String str1 = "";
    String str2 = paramSelectTag.getmatchtype();
    if (str2 == null)
      str2 = "property";
    str2 = str2.toLowerCase();
    if (str2 == "property")
      str1 = paramString1;
    else if (str2 == "labelProperty")
      str1 = paramString2;
    else if (str2 == "value1")
      str1 = paramString3;
    else if (str2 == "value2")
      str1 = paramString4;
    else
      str1 = paramString1;
    return str1;
  }

  public int doEndTag()
    throws JspException
  {
    String str1 = null;
    int i = 0;
    int j = 0;
    if (this.group != null)
      j = 1;
    SelectTag localSelectTag = (SelectTag)this.pageContext.getAttribute("org.apache.struts.taglib.html.SELECT");
    if (localSelectTag == null)
      throw new JspException(messages.getMessage("optionsTag.select"));
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator;
    Object localObject1;
    Object localObject2;
    Object localObject3;
    if (this.collection != null)
    {
      if (localSelectTag.getaddspacerow() == "true")
        if (localSelectTag.getspacerowvalue() == null)
        {
          str1 = getmatchvalue(localSelectTag, "", "", "", "");
          addOption(localStringBuffer, "", localSelectTag.getspacerowtext(), "", "", localSelectTag.isMatched(""));
        }
        else
        {
          addOption(localStringBuffer, localSelectTag.getspacerowvalue(), localSelectTag.getspacerowtext(), "", "", localSelectTag.isMatched(""));
          str1 = getmatchvalue(localSelectTag, localSelectTag.getspacerowvalue(), "", "", "");
        }
      localIterator = getIterator(this.collection, null);
      localObject1 = "";
      while (localIterator.hasNext())
      {
        localObject2 = localIterator.next();
        localObject3 = null;
        Object localObject4 = null;
        String str2 = null;
        String str3 = null;
        String str4 = "";
        Throwable localThrowable;
        try
        {
          if (this.group != null)
          {
            str4 = MethodFactory.getThisString(PropertyUtils.getProperty(localObject2, this.group)).toString().trim();
            if (str4 == null)
              str4 = "";
          }
        }
        catch (IllegalAccessException localIllegalAccessException1)
        {
          throw new JspException(messages.getMessage("getter.access", this.group, this.collection));
        }
        catch (InvocationTargetException localInvocationTargetException1)
        {
          localThrowable = localInvocationTargetException1.getTargetException();
          throw new JspException(messages.getMessage("getter.result", this.group, localThrowable.toString()));
        }
        catch (NoSuchMethodException localNoSuchMethodException1)
        {
          throw new JspException(messages.getMessage("getter.method", this.group, this.collection));
        }
        try
        {
          localObject3 = MethodFactory.getThisString(PropertyUtils.getProperty(localObject2, this.property)).toString().trim();
          if (localObject3 == null)
            localObject3 = "";
        }
        catch (IllegalAccessException localIllegalAccessException2)
        {
          throw new JspException(messages.getMessage("getter.access", this.property, this.collection));
        }
        catch (InvocationTargetException localInvocationTargetException2)
        {
          localThrowable = localInvocationTargetException2.getTargetException();
          throw new JspException(messages.getMessage("getter.result", this.property, localThrowable.toString()));
        }
        catch (NoSuchMethodException localNoSuchMethodException2)
        {
          throw new JspException(messages.getMessage("getter.method", this.property, this.collection));
        }
        try
        {
          if (this.labelProperty != null)
            localObject4 = MethodFactory.getThisString(PropertyUtils.getProperty(localObject2, this.labelProperty)).toString().trim();
          else
            localObject4 = localObject3;
          if (localObject4 == null)
            localObject4 = "";
        }
        catch (IllegalAccessException localIllegalAccessException3)
        {
          throw new JspException(messages.getMessage("getter.access", this.labelProperty, this.collection));
        }
        catch (InvocationTargetException localInvocationTargetException3)
        {
          localThrowable = localInvocationTargetException3.getTargetException();
          throw new JspException(messages.getMessage("getter.result", this.labelProperty, localThrowable.toString()));
        }
        catch (NoSuchMethodException localNoSuchMethodException3)
        {
          throw new JspException(messages.getMessage("getter.method", this.labelProperty, this.collection));
        }
        try
        {
          if (this.value1 != null)
            str2 = MethodFactory.getThisString(PropertyUtils.getProperty(localObject2, this.value1)).toString().trim();
          else
            str2 = "";
          if (str2 == null)
            str2 = "";
        }
        catch (IllegalAccessException localIllegalAccessException4)
        {
          throw new JspException(messages.getMessage("getter.access", this.value1, this.collection));
        }
        catch (InvocationTargetException localInvocationTargetException4)
        {
          localThrowable = localInvocationTargetException4.getTargetException();
          throw new JspException(messages.getMessage("getter.result", this.value1, localThrowable.toString()));
        }
        catch (NoSuchMethodException localNoSuchMethodException4)
        {
          throw new JspException(messages.getMessage("getter.method", this.value1, this.collection));
        }
        try
        {
          if (this.value2 != null)
            str3 = MethodFactory.getThisString(PropertyUtils.getProperty(localObject2, this.value2)).toString().trim();
          else
            str3 = "";
          if (str3 == null)
            str3 = "";
        }
        catch (IllegalAccessException localIllegalAccessException5)
        {
          throw new JspException(messages.getMessage("getter.access", this.value2, this.collection));
        }
        catch (InvocationTargetException localInvocationTargetException5)
        {
          localThrowable = localInvocationTargetException5.getTargetException();
          throw new JspException(messages.getMessage("getter.result", this.value2, localThrowable.toString()));
        }
        catch (NoSuchMethodException localNoSuchMethodException5)
        {
          throw new JspException(messages.getMessage("getter.method", this.value2, this.collection));
        }
        String str5 = localObject3.toString().trim();
        str1 = getmatchvalue(localSelectTag, str5, localObject4.toString(), str2.toString(), str3.toString());
        if ((j == 1) && (!((String)localObject1).equals(str4)))
        {
          if (i != 0)
            addGroupFooter(localStringBuffer);
          addGroupHeader(localStringBuffer, str4);
          localObject1 = str4;
          i += 1;
        }
        addOption(localStringBuffer, str5, localObject4.toString(), str2.toString(), str3.toString(), localSelectTag.isMatched(str1));
      }
      if (i > 0)
        addGroupFooter(localStringBuffer);
    }
    else
    {
      localIterator = getIterator(this.name, this.property);
      localObject1 = null;
      if ((this.labelName == null) && (this.labelProperty == null))
        localObject1 = getIterator(this.name, this.property);
      else
        localObject1 = getIterator(this.labelName, this.labelProperty);
      while (localIterator.hasNext())
      {
        localObject2 = localIterator.next().toString();
        localObject3 = localObject2;
        if (((Iterator)localObject1).hasNext())
          localObject3 = ((Iterator)localObject1).next().toString();
        addOption(localStringBuffer, (String)localObject2, (String)localObject3, localSelectTag.isMatched((String)localObject2));
      }
    }
    ResponseUtils.write(this.pageContext, localStringBuffer.toString());
    return 6;
  }

  public void release()
  {
    super.release();
    this.collection = null;
    this.labelName = null;
    this.labelProperty = null;
    this.name = null;
    this.property = null;
    this.style = null;
    this.styleClass = null;
  }

  protected void addGroupHeader(StringBuffer paramStringBuffer, String paramString)
  {
    paramStringBuffer.append("<OPTGROUP LABEL=\"");
    paramStringBuffer.append(paramString);
    paramStringBuffer.append("\">");
  }

  protected void addGroupFooter(StringBuffer paramStringBuffer)
  {
    paramStringBuffer.append("</OPTGROUP>");
  }

  protected void addOption(StringBuffer paramStringBuffer, String paramString1, String paramString2, boolean paramBoolean)
  {
    paramStringBuffer.append("<option value=\"");
    paramStringBuffer.append(paramString1);
    paramStringBuffer.append("\"");
    if (paramBoolean)
      paramStringBuffer.append(" selected=\"selected\"");
    if (this.style != null)
    {
      paramStringBuffer.append(" style=\"");
      paramStringBuffer.append(this.style);
      paramStringBuffer.append("\"");
    }
    if (this.styleClass != null)
    {
      paramStringBuffer.append(" class=\"");
      paramStringBuffer.append(this.styleClass);
      paramStringBuffer.append("\"");
    }
    paramStringBuffer.append(">");
    paramStringBuffer.append(ResponseUtils.filter(paramString2));
    paramStringBuffer.append("</option>\r\n");
  }

  protected void addOption(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
  {
    paramStringBuffer.append("<option value=\"");
    paramStringBuffer.append(paramString1);
    paramStringBuffer.append("\"");
    if (paramBoolean)
      paramStringBuffer.append(" selected=\"selected\"");
    if (this.style != null)
    {
      paramStringBuffer.append(" style=\"");
      paramStringBuffer.append(this.style);
      paramStringBuffer.append("\"");
    }
    if (this.styleClass != null)
    {
      paramStringBuffer.append(" class=\"");
      paramStringBuffer.append(this.styleClass);
      paramStringBuffer.append("\"");
    }
    if (paramString3 != null)
    {
      paramStringBuffer.append(" value1=\"");
      paramStringBuffer.append(paramString3);
      paramStringBuffer.append("\"");
    }
    if (paramString4 != null)
    {
      paramStringBuffer.append(" value2=\"");
      paramStringBuffer.append(paramString4);
      paramStringBuffer.append("\"");
    }
    paramStringBuffer.append(">");
    paramStringBuffer.append(ResponseUtils.filter(paramString2));
    paramStringBuffer.append("</option>\r\n");
  }

  protected Iterator getIterator(String paramString1, String paramString2)
    throws JspException
  {
    String str = paramString1;
    if (str == null)
      str = "org.apache.struts.taglib.html.BEAN";
    Object localObject1 = this.pageContext.findAttribute(str);
    if (localObject1 == null)
      throw new JspException(messages.getMessage("getter.bean", str));
    Object localObject2 = localObject1;
    if (paramString2 != null)
      try
      {
        localObject2 = PropertyUtils.getProperty(localObject1, paramString2);
        if (localObject2 == null)
          throw new JspException(messages.getMessage("getter.property", paramString2));
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new JspException(messages.getMessage("getter.access", paramString2, paramString1));
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        Throwable localThrowable = localInvocationTargetException.getTargetException();
        throw new JspException(messages.getMessage("getter.result", paramString2, localThrowable.toString()));
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        throw new JspException(messages.getMessage("getter.method", paramString2, paramString1));
      }
    if (localObject2.getClass().isArray())
      localObject2 = Arrays.asList((Object[])localObject2);
    if ((localObject2 instanceof Collection))
      return ((Collection)localObject2).iterator();
    if ((localObject2 instanceof Iterator))
      return (Iterator)localObject2;
    if ((localObject2 instanceof Map))
      return ((Map)localObject2).entrySet().iterator();
    if ((localObject2 instanceof Enumeration))
      return new IteratorAdapter((Enumeration)localObject2);
    throw new JspException(messages.getMessage("optionsTag.iterator", localObject2.toString()));
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.html.OptionsTag
 * JD-Core Version:    0.6.1
 */