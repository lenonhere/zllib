package com.zl.base.core.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class CompressManager
  implements Compressor
{
  public int fnComFile(String paramString1, String paramString2)
  {
    int i = 0;
    try
    {
      File localFile1 = new File(paramString1);
      File localFile2 = new File(paramString2);
      FileInputStream localFileInputStream = new FileInputStream(localFile1);
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
      int j = (int)localFile1.length();
      int k = j;
      byte[] arrayOfByte1 = new byte[j];
      byte[] arrayOfByte2 = new byte[k + 16];
      i = localFileInputStream.read(arrayOfByte1);
      int m = Compress(arrayOfByte1, arrayOfByte2);
      localFileOutputStream.write(arrayOfByte2, 0, m);
      i = m;
      localFileInputStream.close();
      localFileOutputStream.close();
    }
    catch (Exception localException)
    {
      i = -99;
    }
    return i;
  }

  public int fnDecFile(String paramString1, String paramString2)
  {
    int i = 0;
    try
    {
      File localFile1 = new File(paramString1);
      File localFile2 = new File(paramString2);
      FileInputStream localFileInputStream = new FileInputStream(localFile1);
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
      int j = (int)localFile1.length();
      byte[] arrayOfByte1 = new byte[j];
      i = localFileInputStream.read(arrayOfByte1);
      int k = CBaseSCParse.OriginLendth(arrayOfByte1, arrayOfByte1.length);
      byte[] arrayOfByte2 = new byte[k];
      i = DeCompress(arrayOfByte1, arrayOfByte2);
      localFileOutputStream.write(arrayOfByte2);
      localFileInputStream.close();
      localFileOutputStream.close();
    }
    catch (Exception localException)
    {
      i = -99;
    }
    return i;
  }

  public int Compress(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return CBaseSCParse.Compress(paramArrayOfByte1, paramArrayOfByte1.length, paramArrayOfByte2);
  }

  public int DeCompress(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return CBaseSCParse.DCompress(paramArrayOfByte1, paramArrayOfByte1.length, paramArrayOfByte2);
  }

  public static void main(String[] paramArrayOfString)
  {
    CompressManager localCompressManager = new CompressManager();
    localCompressManager.fnComFile("C:\\savemmray.exe", "c:\\savemmray.cps");
    System.out.print("over");
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.compress.CompressManager
 * JD-Core Version:    0.6.1
 */