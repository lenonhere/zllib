package com.zl.base.core.fileserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerProperties
{
  public static final String PROPERTIESFILE = "/fileserver.properties";
  public static final String WORD_TMPLPATH = "template";
  public static final String WORD_OUTPATH = "temppath";
  public static final String FILE_ORGINPATN = "file.orginpath";
  public static final String COMM_PORT = "comm.port";
  protected Properties properties = null;

  public Properties GetProperties()
  {
    return this.properties;
  }

  public void SetProperties(Properties paramProperties)
  {
    this.properties = paramProperties;
  }

  public ServerProperties(String paramString)
    throws IOException
  {
    InputStream localInputStream = getClass().getResourceAsStream(paramString);
    this.properties.load(localInputStream);
  }

  public ServerProperties(InputStream paramInputStream)
    throws IOException
  {
    this.properties.load(paramInputStream);
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.fileserver.ServerProperties
 * JD-Core Version:    0.6.1
 */