package com.zl.base.core.file;

import java.io.File;
import java.io.PrintStream;

public class directory
{
  public static boolean checkdirexits(String paramString)
  {
    boolean bool = false;
    File localFile = new File(paramString);
    if (localFile.isDirectory())
      bool = true;
    else
      bool = false;
    return bool;
  }

  public static boolean createdir(String paramString)
  {
    boolean bool = false;
    try
    {
      File localFile = new File(paramString);
      localFile.mkdirs();
      if (localFile.isDirectory())
        bool = true;
      else
        bool = false;
    }
    catch (Exception localException)
    {
      bool = true;
    }
    return bool;
  }

  public static void main(String[] paramArrayOfString)
  {
    System.out.println(createdir("c:\\\\temp\\abc\\abc\\abc\\"));
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.file.directory
 * JD-Core Version:    0.6.1
 */