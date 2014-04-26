package com.zl.base.core.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MessageFormatter
{
  public static String getMessage(String paramString1, String paramString2, Object[] paramArrayOfObject)
  {
    ResourceBundle localResourceBundle = ResourceBundle.getBundle(paramString1);
    String str = localResourceBundle.getString(paramString2);
    return MessageFormat.format(str, paramArrayOfObject);
  }

  public static String getMessage(String paramString, Object[] paramArrayOfObject)
  {
    String str = "errormessage.properties";
    return getMessage(str, paramString, paramArrayOfObject);
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.util.MessageFormatter
 * JD-Core Version:    0.6.1
 */