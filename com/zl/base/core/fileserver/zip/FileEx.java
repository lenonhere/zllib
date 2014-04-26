package com.zl.base.core.fileserver.zip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class FileEx
{
  public static String replace(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    String str = null;
    int i = 0;
    int j = 0;
    try
    {
      str = new String(paramString1);
      i = str.indexOf(paramString2);
      while ((i >= 0) && ((paramInt == 0) || (paramInt > j)))
      {
        str = str.substring(0, i) + paramString3 + str.substring(i + paramString2.length());
        i += paramString3.length();
        i = str.indexOf(paramString2, i);
        j += 1;
      }
    }
    catch (Exception localException)
    {
    }
    return str;
  }

  public static String replaceAll(String paramString1, String paramString2, String paramString3)
  {
    return replace(paramString1, paramString2, paramString3, 0);
  }

  public static synchronized ArrayList GetCurrentPath(ArrayList paramArrayList, String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    String str1 = new File(paramString).getPath();
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      String str2 = (String)paramArrayList.get(i);
      str2 = replaceAll(str2, "/", "\\");
      if (str2.trim().indexOf(str1.trim()) == 0)
        localArrayList.add(str2.trim());
      else
        localArrayList.add(GetCurrentFilePath(str1.trim(), str2.trim()));
    }
    return localArrayList;
  }

  public static synchronized String GetCurrentFilePath(String paramString1, String paramString2)
  {
    String str = null;
    paramString1 = replaceAll(paramString1, "\\", "/").trim();
    paramString2 = replaceAll(paramString2, "\\", "/").trim();
    if (paramString1.substring(paramString1.length() - 1, paramString1.length()).equals("/"))
      paramString1 = paramString1.substring(0, paramString1.length() - 1);
    if (paramString2.substring(0, 1).equals("/"))
      paramString2 = paramString2.substring(1, paramString2.length());
    str = paramString1 + "/" + paramString2;
    return str;
  }

  public static synchronized String GetAbstractPath(File paramFile1, File paramFile2)
  {
    String str1 = paramFile1.getName().trim();
    if ((paramFile2.isDirectory()) && (!paramFile1.isDirectory()))
    {
      String str2 = paramFile2.getPath().trim();
      String str3 = paramFile1.getPath().trim();
      String str4 = paramFile1.getName().trim();
      str1 = str3.substring(str2.length(), str3.length());
    }
    return str1;
  }

  public static synchronized boolean CreatePath(File paramFile)
    throws IOException
  {
    if (paramFile.exists())
      return true;
    File localFile = new File(paramFile.getParent());
    boolean bool = localFile.exists();
    if (!bool)
      return localFile.mkdirs();
    return bool;
  }

  public static synchronized Date GetFileTime(File paramFile)
    throws IOException
  {
    return new Date(paramFile.lastModified());
  }

  public static synchronized int GetFileCount(ArrayList paramArrayList)
    throws IOException
  {
    int i = 0;
    for (int j = 0; j < paramArrayList.size(); j++)
    {
      File localFile = new File((String)paramArrayList.get(j));
      if (localFile.exists())
        i += GetFileCount(localFile);
    }
    return i;
  }

  public static synchronized int GetFileCount(File paramFile)
    throws IOException
  {
    int i = 0;
    if (paramFile.isFile())
      i++;
    if (paramFile.isDirectory())
    {
      File[] arrayOfFile = Compress.listFiles(paramFile);
      for (int j = 0; j < arrayOfFile.length; j++)
      {
        File localFile = arrayOfFile[j];
        i += GetFileCount(localFile);
      }
    }
    return i;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.fileserver.zip.FileEx
 * JD-Core Version:    0.6.1
 */