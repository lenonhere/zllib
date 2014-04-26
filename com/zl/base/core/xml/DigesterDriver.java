package com.zl.base.core.xml;

import org.apache.commons.digester.Digester;

public abstract class DigesterDriver
{
  protected Digester digester;

  public void xmlToJavaObjects(Object paramObject1, Object paramObject2)
    throws Exception
  {
    try
    {
      this.digester = new Digester();
      this.digester.setValidating(false);
      paramObject1 = "<?xml version=\"1.0\" encoding=\"GB2312\" ?>" + (String)paramObject1;
      applyPatternsAndRules(paramObject1, paramObject2);
    }
    catch (Exception localException)
    {
      throw localException;
    }
  }

  protected abstract void applyPatternsAndRules(Object paramObject1, Object paramObject2)
    throws Exception;
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.xml.DigesterDriver
 * JD-Core Version:    0.6.1
 */