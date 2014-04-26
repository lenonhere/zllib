package com.zl.base.core.taglib.grid;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.util.IteratorAdapter;
import org.apache.struts.util.MessageResources;

public class OptionsTag extends BodyTagSupport
  implements Cloneable
{
  private Iterator iterator;
  protected static MessageResources messages = MessageResources.getMessageResources("Constants.Package.LocalStrings");
  protected String collection = null;
  protected String labelName = null;
  protected String labelProperty = null;
  protected String name = null;
  protected String property = null;
  private String style = null;
  private String styleClass = null;
  protected String value = null;
  protected String value1;
  protected String value2;
  protected boolean disabled = false;
  protected String text = null;
  private Object valueObject = null;
  private Object label = null;
  private Object value1Temp = null;
  private Object value2Temp = null;

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

  public boolean getdisabled()
  {
    return this.disabled;
  }

  public void setdisable(boolean paramBoolean)
  {
    this.disabled = paramBoolean;
  }

  public String gettext()
  {
    return this.text;
  }

  public void settext(String paramString)
  {
    this.text = paramString;
  }

  public int doStartTag()
    throws JspException
  {
    this.iterator = null;
    return 0;
  }

  public int doEndTag()
    throws JspException
  {
    SelectTag localSelectTag = (SelectTag)this.pageContext.getAttribute("Constants.SELECT_KEY");
    localSelectTag.options.clear();
    if (localSelectTag == null)
      throw new JspException(messages.getMessage("optionsTag.select"));
    Object localObject;
    if (this.collection != null)
    {
      this.iterator = getIterator(this.collection, null);
      localObject = new Option();
      ((Option)localObject).setOptionValue(getOptionsValue());
      try
      {
        localSelectTag.options.add(localObject);
      }
      catch (Exception localException)
      {
        JspException localJspException = new JspException("clone error!");
        throw localJspException;
      }
    }
    else
    {
      localObject = new JspException("no data collection!");
    }
    return 6;
  }

  public void release1()
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

  protected Iterator getIterator(String paramString1, String paramString2)
    throws JspException
  {
    String str = paramString1;
    if (str == null)
      str = "Constants.BEAN_KEY";
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

  private Vector getOptionsValue()
  {
    Vector localVector = new Vector();
    while (this.iterator.hasNext())
    {
      Struct localStruct = new Struct();
      Object localObject = this.iterator.next();
      try
      {
        this.valueObject = PropertyUtils.getProperty(localObject, this.property);
        if (this.valueObject == null)
          this.valueObject = "";
      }
      catch (IllegalAccessException localIllegalAccessException1)
      {
        System.out.println("error in optionsTag");
      }
      catch (InvocationTargetException localInvocationTargetException1)
      {
        System.out.println("error in optionsTag");
      }
      catch (NoSuchMethodException localNoSuchMethodException1)
      {
        System.out.println("error in optionsTag");
      }
      try
      {
        if (this.labelProperty != null)
          this.label = PropertyUtils.getProperty(localObject, this.labelProperty);
        else
          this.label = this.value;
        if (this.label == null)
          this.label = "";
      }
      catch (IllegalAccessException localIllegalAccessException2)
      {
        System.out.println("error in optionsTag");
      }
      catch (InvocationTargetException localInvocationTargetException2)
      {
        System.out.println("error in optionsTag");
      }
      catch (NoSuchMethodException localNoSuchMethodException2)
      {
        System.out.println("error in optionsTag");
      }
      try
      {
        if (this.value1 != null)
          this.value1Temp = PropertyUtils.getProperty(localObject, this.value1);
        else
          this.value1Temp = "";
        if (this.value1Temp == null)
          this.value1Temp = "";
      }
      catch (IllegalAccessException localIllegalAccessException3)
      {
      }
      catch (InvocationTargetException localInvocationTargetException3)
      {
      }
      catch (NoSuchMethodException localNoSuchMethodException3)
      {
      }
      try
      {
        if (this.value2 != null)
          this.value2Temp = PropertyUtils.getProperty(localObject, this.value2);
        else
          this.value2Temp = "";
        if (this.value2Temp == null)
          this.value2Temp = "";
      }
      catch (IllegalAccessException localIllegalAccessException4)
      {
      }
      catch (InvocationTargetException localInvocationTargetException4)
      {
      }
      catch (NoSuchMethodException localNoSuchMethodException4)
      {
      }
      localStruct.value = this.valueObject.toString();
      localStruct.value1 = this.value1Temp.toString();
      localStruct.value2 = this.value2Temp.toString();
      localStruct.lable = this.label.toString();
      localVector.add(localStruct);
    }
    return localVector;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.grid.OptionsTag
 * JD-Core Version:    0.6.1
 */