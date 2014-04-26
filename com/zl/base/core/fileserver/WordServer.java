package com.zl.base.core.fileserver;

import com.zl.base.core.creaoffice.creaoffice;
import com.zl.base.core.fileserver.socket.Service;
import com.zl.base.core.fileserver.zip.FileEx;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

public class WordServer
  implements Service
{
  protected String tmplPath = null;
  protected String outPath = null;

  public String GetTmplPath()
  {
    return this.tmplPath;
  }

  public void SetTmplPath(String paramString)
  {
    this.tmplPath = paramString;
  }

  public String GetOutPath()
  {
    return this.outPath;
  }

  public void SetOutPath(String paramString)
  {
    this.outPath = paramString;
  }

  public void service(InputStream paramInputStream, OutputStream paramOutputStream)
    throws Exception
  {
    while (true)
    {
      ObjectInputStream localObjectInputStream = new ObjectInputStream(paramInputStream);
      FileTCommand localFileTCommand = (FileTCommand)localObjectInputStream.readObject();
      if (localFileTCommand != null)
        if (localFileTCommand.GetType() == 2001)
        {
          service(paramInputStream, paramOutputStream, localFileTCommand);
          if (localFileTCommand.GetOperator().equals("disconnect"))
            break;
        }
    }
  }

  public void service(InputStream paramInputStream, OutputStream paramOutputStream, FileTCommand paramFileTCommand)
    throws Exception
  {
    if (paramFileTCommand.GetOperator().equals("word"))
    {
      Properties localProperties = paramFileTCommand.GetParam();
      if (localProperties != null)
      {
        String str1 = localProperties.getProperty("word.data");
        String str2 = localProperties.getProperty("word.tmpl");
        System.out.println("文书生成模板" + str2);
        String str3 = this.tmplPath;
        String str4 = "";
        if ((localProperties.getProperty("path.label") != null) && (!"".equals(localProperties.getProperty("path.label"))))
        {
          pathclass localpathclass = new pathclass();
          str3 = localpathclass.getPath(localProperties.getProperty("path.label"), this.tmplPath);
        }
        try
        {
          str4 = creaoffice.setData(str1, str2, str3, this.outPath);
          if (str4.equals(""))
            System.out.println("生成失败！");
          else
            System.out.println("文书生成:" + str4);
        }
        catch (Exception localException)
        {
          str4 = "";
        }
        if (str4.length() > 0)
        {
          String str5 = FileEx.GetCurrentFilePath(this.outPath, str4);
          File localFile = new File(str5);
          Thread.sleep(100L);
          Response(7, localFile.getAbsoluteFile().getName(), localFile.length(), paramOutputStream);
          paramOutputStream.flush();
          FileInputStream localFileInputStream = new FileInputStream(localFile);
          byte[] arrayOfByte = new byte[1024];
          for (int i = localFileInputStream.read(arrayOfByte); i >= 0; i = localFileInputStream.read(arrayOfByte))
            paramOutputStream.write(arrayOfByte, 0, i);
          localFileInputStream.close();
          paramOutputStream.flush();
        }
        else
        {
          Response(8, "WORD文档处理出错", 0L, paramOutputStream);
          paramOutputStream.flush();
        }
      }
      else
      {
        Response(8, "参数不存在", 0L, paramOutputStream);
        paramOutputStream.flush();
      }
    }
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

  private void Response(int paramInt, String paramString, long paramLong, OutputStream paramOutputStream)
    throws IOException
  {
    FileTResult localFileTResult = new FileTResult();
    ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(paramOutputStream);
    localFileTResult.SetCode(paramInt);
    localFileTResult.SetMessage(paramString);
    localFileTResult.SetFileCount(paramLong);
    localObjectOutputStream.writeObject(localFileTResult);
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.fileserver.WordServer
 * JD-Core Version:    0.6.1
 */