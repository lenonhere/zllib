package com.zl.base.core.fileserver;

import com.zl.base.core.fileserver.socket.Service;
import com.zl.base.core.fileserver.zip.Compress;
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

public class FileServer
  implements Service
{
  private String orginPath = null;
  protected String tempPath = null;

  public FileServer()
  {
  }

  public FileServer(String paramString)
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
    System.out.println(paramFileTCommand.GetOperator());
    if (paramFileTCommand.GetOperator().equals("connect"))
    {
      Response(1, null, 0L, paramOutputStream);
      paramOutputStream.flush();
    }
    Object localObject1;
    Object localObject2;
    Object localObject3;
    Object localObject4;
    if (paramFileTCommand.GetOperator().equals("upload"))
    {
      long l = paramFileTCommand.GetFileCount();
      localObject1 = Calendar.getInstance();
      localObject2 = this.tempPath;
      if (this.tempPath == null)
        localObject2 = this.orginPath;
      localObject3 = new File((String)localObject2, "crea.save.tmp" + String.valueOf(((Calendar)localObject1).get(14)));
      localObject4 = new FileOutputStream((File)localObject3);
      byte[] arrayOfByte = new byte[1024];
      for (int j = paramInputStream.read(arrayOfByte); j >= 0; j = paramInputStream.read(arrayOfByte))
      {
        ((FileOutputStream)localObject4).write(arrayOfByte, 0, j);
        l -= j;
        if (l <= 0L)
          break;
      }
      ((FileOutputStream)localObject4).close();
      Compress.InflaterFile(((File)localObject3).getPath(), this.orginPath);
      ((File)localObject3).delete();
      Response(3, null, 0L, paramOutputStream);
      paramOutputStream.flush();
    }
    if (paramFileTCommand.GetOperator().equals("download"))
    {
      ArrayList localArrayList = FileEx.GetCurrentPath(paramFileTCommand.GetList(), this.orginPath);
      Calendar localCalendar = Calendar.getInstance();
      localObject1 = this.tempPath;
      if (this.tempPath == null)
        localObject1 = this.orginPath;
      localObject2 = new File((String)localObject1, "crea.send.tmp" + String.valueOf(localCalendar.get(14)));
      Compress.DeflaterFile(localArrayList, this.orginPath, ((File)localObject2).getPath());
      Response(3, null, ((File)localObject2).length(), paramOutputStream);
      paramOutputStream.flush();
      localObject3 = new FileInputStream((File)localObject2);
      localObject4 = new byte[1024];
      for (int i = ((FileInputStream)localObject3).read((byte[])localObject4); i >= 0; i = ((FileInputStream)localObject3).read((byte[])localObject4))
        paramOutputStream.write((byte[])localObject4, 0, i);
      ((FileInputStream)localObject3).close();
      paramOutputStream.flush();
      ((File)localObject2).delete();
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
 * Qualified Name:     com.zl.base.core.fileserver.FileServer
 * JD-Core Version:    0.6.1
 */