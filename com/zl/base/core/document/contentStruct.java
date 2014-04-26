package com.zl.base.core.document;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class contentStruct
{
  private String tablename;
  private String fieldname;
  private String type;
  private Integer rowIndex = new Integer(0);
  private ArrayList child = new ArrayList();

  public String getFieldname()
  {
    if (this.fieldname == null)
      return "";
    return this.fieldname;
  }

  public static String getdatestring()
  {
    String str1 = "";
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    String str2 = String.valueOf(localGregorianCalendar.get(1));
    String str3 = "00" + String.valueOf(localGregorianCalendar.get(2) + 1);
    str3 = str3.substring(str3.length() - 2, str3.length());
    String str4 = "00" + String.valueOf(localGregorianCalendar.get(5));
    str4 = str4.substring(str4.length() - 2, str4.length());
    String str5 = "00" + String.valueOf(localGregorianCalendar.get(11));
    str5 = str5.substring(str5.length() - 2, str5.length());
    String str6 = "00" + String.valueOf(localGregorianCalendar.get(12));
    str6 = str6.substring(str6.length() - 2, str6.length());
    String str7 = "00" + String.valueOf(localGregorianCalendar.get(13));
    str7 = str7.substring(str7.length() - 2, str7.length());
    String str8 = "000000" + String.valueOf(localGregorianCalendar.get(14));
    str8 = str8.substring(str8.length() - 6, str8.length());
    str1 = str2 + str3 + str4 + str5 + str6 + str7 + str8;
    return str1;
  }

  public void setFieldname(String paramString)
  {
    try
    {
      if ((paramString.length() >= 3) && (paramString.substring(0, 2).equals("@[")) && (paramString.substring(paramString.length() - 1, paramString.length()).equals("]")))
      {
        paramString = paramString.substring(2, paramString.length());
        paramString = paramString.substring(0, paramString.length() - 1);
      }
      String[] arrayOfString = split(paramString, ".");
      if (arrayOfString.length > 1)
      {
        setTablename(arrayOfString[0]);
        paramString = arrayOfString[1];
      }
      else
      {
        paramString = arrayOfString[0];
      }
      arrayOfString = split(paramString, ":");
      if (arrayOfString.length > 1)
      {
        this.fieldname = arrayOfString[0];
      }
      else
      {
        this.fieldname = arrayOfString[0];
        try
        {
          this.rowIndex = new Integer(arrayOfString[1]);
        }
        catch (Exception localException2)
        {
          this.rowIndex = new Integer(0);
        }
      }
    }
    catch (Exception localException1)
    {
      this.fieldname = paramString;
    }
  }

  public String getTablename()
  {
    return this.tablename;
  }

  public void setTablename(String paramString)
  {
    this.tablename = paramString;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }

  public ArrayList getChild()
  {
    return this.child;
  }

  public void setChild(ArrayList paramArrayList)
  {
    this.child = paramArrayList;
  }

  public static String replace(String paramString1, String paramString2, String paramString3, int paramInt)
    throws Exception
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
      return str;
    }
    catch (Exception localException)
    {
      throw localException;
    }
  }

  public static String replace(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    return replace(paramString1, paramString2, paramString3, 0);
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
    {
      arrayOfString[k] = ((String)localArrayList.get(k));
      arrayOfString[k] = arrayOfString[k].trim();
    }
    return arrayOfString;
  }

  public Integer getRowIndex()
  {
    return this.rowIndex;
  }

  public void setRowIndex(Integer paramInteger)
  {
    this.rowIndex = paramInteger;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.document.contentStruct
 * JD-Core Version:    0.6.1
 */