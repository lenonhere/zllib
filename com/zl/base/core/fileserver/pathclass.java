package com.zl.base.core.fileserver;

import java.io.InputStream;
import java.util.Properties;

public class pathclass
{
  private static pathclass instance;
  private static InputStream is;
  private static String fileTempDir;

  public static synchronized pathclass getInstance()
  {
    if (instance == null)
      instance = new pathclass();
    return instance;
  }

  public static String getFILEtempdir()
  {
    return fileTempDir;
  }

  public String getPath(String paramString1, String paramString2)
  {
    String str = "";
    Properties localProperties = new Properties();
    try
    {
      InputStream localInputStream = getClass().getResourceAsStream("/fileserver.properties");
      localProperties.load(localInputStream);
      str = localProperties.getProperty(paramString1);
      if (str == null)
        str = paramString2;
    }
    catch (Exception localException)
    {
    }
    return str;
  }

  private void loadproperties()
  {
    Properties localProperties = new Properties();
    try
    {
      InputStream localInputStream = getClass().getResourceAsStream("/fileserver.properties");
      localProperties.load(localInputStream);
      fileTempDir = localProperties.getProperty("FILE.tempdir");
    }
    catch (Exception localException)
    {
    }
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.fileserver.pathclass
 * JD-Core Version:    0.6.1
 */