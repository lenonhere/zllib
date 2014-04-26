package com.zl.base.core.fileserver;

import com.zl.base.core.fileserver.zip.FileEx;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

public class singleFileServer
{
  private String orginPath = null;
  protected String tempPath = null;

  public singleFileServer()
  {
  }

  public singleFileServer(String paramString)
  {
    this.orginPath = paramString;
  }

  public String GetOrginPath()
  {
    return this.orginPath;
  }

  public void SetOrginPath(String paramString)
  {
    this.orginPath = paramString;
  }

  public String GetTempPath()
  {
    return this.tempPath;
  }

  public void SetTempPath(String paramString)
  {
    this.tempPath = paramString;
  }

  public void service(InputStream paramInputStream, OutputStream paramOutputStream)
    throws Exception
  {
    while (true)
    {
      ObjectInputStream localObjectInputStream = new ObjectInputStream(paramInputStream);
      FileTCommand localFileTCommand = (FileTCommand)localObjectInputStream.readObject();
      if (localFileTCommand != null)
        if (localFileTCommand.GetType() == 1001)
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
    if (paramFileTCommand.GetOperator().equals("connect"))
    {
      Response(1, null, 0L, paramOutputStream);
      paramOutputStream.flush();
    }
    String str = this.orginPath;
    Properties localProperties = paramFileTCommand.GetParam();
    if ((localProperties != null) && (localProperties.getProperty("path.label") != null) && (!"".equals(localProperties.getProperty("path.label"))))
    {
      pathclass localpathclass = new pathclass();
      str = localpathclass.getPath(localProperties.getProperty("path.label"), this.orginPath);
    }
    File localFile;
    Object localObject;
    byte[] arrayOfByte;
    int i;
    if (paramFileTCommand.GetOperator().equals("upload"))
    {
      long l = paramFileTCommand.GetFileCount();
      localFile = new File(str, (String)paramFileTCommand.GetList().get(0));
      FileEx.CreatePath(localFile);
      localObject = new FileOutputStream(localFile);
      arrayOfByte = new byte[1024];
      for (i = paramInputStream.read(arrayOfByte); i >= 0; i = paramInputStream.read(arrayOfByte))
      {
        ((FileOutputStream)localObject).write(arrayOfByte, 0, i);
        l -= i;
        if (l <= 0L)
          break;
      }
      ((FileOutputStream)localObject).close();
      Response(3, null, 0L, paramOutputStream);
      paramOutputStream.flush();
      System.out.println("上传文件" + (String)paramFileTCommand.GetList().get(0) + "成功!");
    }
    if (paramFileTCommand.GetOperator().equals("download"))
    {
      ArrayList localArrayList = FileEx.GetCurrentPath(paramFileTCommand.GetList(), str);
      Calendar localCalendar = Calendar.getInstance();
      localFile = new File((String)localArrayList.get(0));
      if (!localFile.exists())
        Response(6, "文件" + (String)localArrayList.get(0) + "不存在！", localFile.length(), paramOutputStream);
      else
        Response(5, null, localFile.length(), paramOutputStream);
      paramOutputStream.flush();
      if (localFile.exists())
      {
        localObject = new FileInputStream(localFile);
        arrayOfByte = new byte[1024];
        for (i = ((FileInputStream)localObject).read(arrayOfByte); i >= 0; i = ((FileInputStream)localObject).read(arrayOfByte))
          paramOutputStream.write(arrayOfByte, 0, i);
        ((FileInputStream)localObject).close();
        paramOutputStream.flush();
        System.out.println("下载文件" + (String)paramFileTCommand.GetList().get(0) + "成功!");
      }
    }
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
 * Qualified Name:     com.zl.base.core.fileserver.singleFileServer
 * JD-Core Version:    0.6.1
 */