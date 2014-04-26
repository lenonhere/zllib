package com.zl.base.core.taglib.grid;

import javax.servlet.jsp.JspException;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

public abstract class BaseFieldTag extends BaseInputTag
{
  protected String accept = null;
  protected String name = "org.apache.struts.taglib.html.BEAN";
  protected boolean redisplay = true;
  protected String type = null;

  public String getAccept()
  {
    return this.accept;
  }

  public void setAccept(String paramString)
  {
    this.accept = paramString;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public boolean getRedisplay()
  {
    return this.redisplay;
  }

  public void setRedisplay(boolean paramBoolean)
  {
    this.redisplay = paramBoolean;
  }

  public int doStartTag()
    throws JspException
  {
    StringBuffer localStringBuffer = new StringBuffer("<input type=\"");
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
    if (this.accept != null)
    {
      localStringBuffer.append(" accept=\"");
      localStringBuffer.append(this.accept);
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
    if (this.value != null)
    {
      localStringBuffer.append(ResponseUtils.filter(this.value));
    }
    else if ((this.redisplay) || (!"password".equals(this.type)))
    {
      Object localObject = RequestUtils.lookup(this.pageContext, this.name, this.property, null);
      if (localObject == null)
        localObject = "";
      localStringBuffer.append(ResponseUtils.filter(localObject.toString()));
    }
    localStringBuffer.append("\"");
    localStringBuffer.append(prepareEventHandlers());
    localStringBuffer.append(prepareStyles());
    localStringBuffer.append(">");
    ResponseUtils.write(this.pageContext, localStringBuffer.toString());
    return 2;
  }

  public void release()
  {
    super.release();
    this.accept = null;
    this.name = "org.apache.struts.taglib.html.BEAN";
    this.redisplay = true;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.grid.BaseFieldTag
 * JD-Core Version:    0.6.1
 */