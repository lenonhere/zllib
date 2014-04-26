package com.zl.base.core.fileserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

public class FileTCommand
  implements Serializable
{
  public static final String FILE_OPERATOE_CONNECT = "connect";
  public static final String FILE_OPERATOR_UPLOAD = "upload";
  public static final String FILE_OPERATOR_DOWNLOAD = "download";
  public static final String FILE_OPERATOR_DISCONNECT = "disconnect";
  public static final String FILE_OPERATOR_WORD = "word";
  public static final int FILE_TYPE_STRING = 801;
  public static final int FILE_TYPE_FILES = 1001;
  public static final int FILE_TYPE_WORD = 2001;
  private String Operator = "download1";
  private int Type = 1001;
  private String fileList = null;
  private long filecount = 0L;
  private Properties Parmeter = null;

  public synchronized String GetOperator()
  {
    return this.Operator;
  }

  public synchronized void SetOperator(String paramString)
  {
    this.Operator = paramString;
  }

  public synchronized int GetType()
  {
    return this.Type;
  }

  public synchronized void SetType(int paramInt)
  {
    this.Type = paramInt;
  }

  public static String[] split(String paramString1, String paramString2)
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = 0;
    String str = paramString1;
    for (i = str.indexOf(paramString2); i >= 0; i = str.indexOf(paramString2))
    {
      j++;
      if (i > 0)
        localArrayList.add(str.substring(0, i));
      else
        localArrayList.add("");
      str = str.substring(i + paramString2.length());
    }
    localArrayList.add(str);
    String[] arrayOfString = new String[localArrayList.size()];
    for (int k = 0; k < localArrayList.size(); k++)
      arrayOfString[k] = ((String)localArrayList.get(k));
    return arrayOfString;
  }

  public synchronized ArrayList GetList()
  {
    String[] arrayOfString = split(this.fileList, "*");
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < arrayOfString.length; i++)
      if (!arrayOfString[i].equals(""))
        localArrayList.add(arrayOfString[i]);
    return localArrayList;
  }

  public synchronized void SetList(ArrayList paramArrayList)
  {
    this.fileList = "";
    if (paramArrayList != null)
      for (int i = 0; i < paramArrayList.size(); i++)
        if (i == 0)
          this.fileList = ((String)paramArrayList.get(i));
        else
          this.fileList = (this.fileList + "*" + (String)paramArrayList.get(i));
  }

  public synchronized long GetFileCount()
  {
    return this.filecount;
  }

  public synchronized void SetFileCount(long paramLong)
  {
    this.filecount = paramLong;
  }

  public synchronized Properties GetParam()
  {
    return this.Parmeter;
  }

  public synchronized void SetParmeter(Properties paramProperties)
  {
    this.Parmeter = paramProperties;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.fileserver.FileTCommand
 * JD-Core Version:    0.6.1
 */