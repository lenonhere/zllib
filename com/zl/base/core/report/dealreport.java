package com.zl.base.core.report;

import com.zl.base.core.compress.CompressManager;
import com.zl.base.core.xml.XMLConvert;
import java.io.FileReader;
import java.util.ArrayList;
import sun.misc.BASE64Encoder;

public class dealreport
{
  public String getXmlForReport(ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    String str = "";
    XMLConvert localxmlconvert = new XMLConvert();
    for (int i = 0; i < paramArrayList1.size(); i++)
      str = str + localxmlconvert.RowSetDynatoXmlString((ArrayList)paramArrayList1.get(i), new StringBuffer().append((String)paramArrayList2.get(i)).append("_RECORDSET").toString());
    str = "<?xml version=\"1.0\" encoding=\"gb2312\"?> <root> " + str + "</root> ";
    byte[] arrayOfByte1 = str.getBytes();
    CompressManager localCompressManager = new CompressManager();
    byte[] arrayOfByte2 = new byte[arrayOfByte1.length];
    int j = 0;
    j = localCompressManager.Compress(arrayOfByte1, arrayOfByte2);
    if (j > 0)
    {
      arrayOfByte1 = new byte[j];
      for (int k = 0; k < j; k++)
        arrayOfByte1[k] = arrayOfByte2[k];
      BASE64Encoder localBASE64Encoder = new BASE64Encoder();
      str = localBASE64Encoder.encode(arrayOfByte1);
    }
    else
    {
      str = "";
    }
    return str;
  }

  public static String readprintstyle(String paramString1, String paramString2)
  {
    String str1 = "";
    try
    {
      String str2 = paramString2 + paramString1 + ".xml";
      StringBuffer localStringBuffer = new StringBuffer();
      FileReader localFileReader = new FileReader(str2);
      char[] arrayOfChar = new char[100];
      int i;
      while ((i = localFileReader.read(arrayOfChar)) != -1)
        localStringBuffer.append(arrayOfChar, 0, i);
      localFileReader.close();
      str1 = localStringBuffer.toString();
      byte[] arrayOfByte1 = str1.getBytes();
      byte[] arrayOfByte2 = new byte[arrayOfByte1.length + 16];
      int j = 0;
      CompressManager localCompressManager = new CompressManager();
      j = localCompressManager.Compress(arrayOfByte1, arrayOfByte2);
      if (j > 0)
      {
        arrayOfByte1 = new byte[j];
        for (int k = 0; k < j; k++)
          arrayOfByte1[k] = arrayOfByte2[k];
        BASE64Encoder localBASE64Encoder = new BASE64Encoder();
        str1 = localBASE64Encoder.encode(arrayOfByte1);
      }
      else
      {
        str1 = "";
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str1;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.report.dealreport
 * JD-Core Version:    0.6.1
 */