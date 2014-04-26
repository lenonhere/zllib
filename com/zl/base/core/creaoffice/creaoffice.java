package com.zl.base.core.creaoffice;

public class creaoffice
{
  public static native String setData(String paramString1, String paramString2, String paramString3, String paramString4);

  public static native String setDataFile(String paramString1, String paramString2, String paramString3, String paramString4);

  static
  {
    System.loadLibrary("creaoffice");
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.creaoffice.creaoffice
 * JD-Core Version:    0.6.1
 */