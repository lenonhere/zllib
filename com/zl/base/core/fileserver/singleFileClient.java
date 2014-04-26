package com.zl.base.core.fileserver;

import com.zl.base.core.fileserver.socket.Client;
import com.zl.base.core.fileserver.zip.FileEx;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

public class singleFileClient extends Client
{
  public singleFileClient(String paramString1, int paramInt, String paramString2)
    throws IOException
  {
    super(paramString1, paramInt, paramString2);
  }

  public boolean Ping()
    throws Exception
  {
    ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(this.out);
    FileTCommand localFileTCommand = new FileTCommand();
    localFileTCommand.SetType(1001);
    localFileTCommand.SetOperator("connect");
    localObjectOutputStream.writeObject(localFileTCommand);
    this.out.flush();
    ObjectInputStream localObjectInputStream = new ObjectInputStream(this.in);
    FileTResult localFileTResult = (FileTResult)localObjectInputStream.readObject();
    return localFileTResult.GetCode() == 1;
  }

  public boolean UpLoadFile(ArrayList paramArrayList)
    throws Exception
  {
    return UpLoadFile(paramArrayList, null);
  }

  public boolean UpLoadFile(ArrayList paramArrayList, String paramString)
    throws Exception
  {
    if (paramArrayList == null)
      return false;
    if (paramArrayList.size() == 0)
      return false;
    ArrayList localArrayList = FileEx.GetCurrentPath(paramArrayList, this.orginPath);
    File localFile = new File((String)localArrayList.get(0));
    if (!localFile.exists())
      return false;
    FileTCommand localFileTCommand = new FileTCommand();
    localFileTCommand.SetType(1001);
    localFileTCommand.SetOperator("upload");
    localFileTCommand.SetFileCount(localFile.length());
    localFileTCommand.SetList(paramArrayList);
    Properties localProperties = new Properties();
    localProperties.setProperty("path.label", paramString);
    ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(this.out);
    localObjectOutputStream.writeObject(localFileTCommand);
    this.out.flush();
    FileInputStream localFileInputStream = new FileInputStream((String)localArrayList.get(0));
    byte[] arrayOfByte = new byte[1024];
    for (int i = localFileInputStream.read(arrayOfByte); i >= 0; i = localFileInputStream.read(arrayOfByte))
      this.out.write(arrayOfByte, 0, i);
    localFileInputStream.close();
    this.out.flush();
    ObjectInputStream localObjectInputStream = new ObjectInputStream(this.in);
    FileTResult localFileTResult = (FileTResult)localObjectInputStream.readObject();
    return localFileTResult.GetCode() == 3;
  }

  public boolean DownLoadFile(ArrayList paramArrayList)
    throws Exception
  {
    return DownLoadFile(paramArrayList, null);
  }

  public boolean DownLoadFile(ArrayList paramArrayList, String paramString)
    throws Exception
  {
    if (paramArrayList == null)
      return false;
    if (paramArrayList.size() == 0)
      return false;
    ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(this.out);
    FileTCommand localFileTCommand = new FileTCommand();
    localFileTCommand.SetType(1001);
    localFileTCommand.SetOperator("download");
    localFileTCommand.SetList(paramArrayList);
    localFileTCommand.SetFileCount(1L);
    Properties localProperties = new Properties();
    localProperties.setProperty("path.label", paramString);
    localObjectOutputStream.writeObject(localFileTCommand);
    this.out.flush();
    ObjectInputStream localObjectInputStream = new ObjectInputStream(this.in);
    FileTResult localFileTResult = (FileTResult)localObjectInputStream.readObject();
    if (localFileTResult.GetCode() == 5)
    {
      long l = localFileTResult.GetFileCount();
      Calendar localCalendar = Calendar.getInstance();
      File localFile = new File(this.orginPath, (String)paramArrayList.get(0));
      FileEx.CreatePath(localFile);
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
      byte[] arrayOfByte = new byte[1024];
      for (int i = this.in.read(arrayOfByte); i >= 0; i = this.in.read(arrayOfByte))
      {
        localFileOutputStream.write(arrayOfByte, 0, i);
        l -= i;
        if (l <= 0L)
          break;
      }
      localFileOutputStream.close();
      return true;
    }
    return false;
  }

  public void Close()
    throws Exception
  {
    ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(this.out);
    FileTCommand localFileTCommand = new FileTCommand();
    localFileTCommand.SetType(1001);
    localFileTCommand.SetOperator("disconnect");
    localObjectOutputStream.writeObject(localFileTCommand);
    this.out.flush();
    Thread.sleep(1000L);
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.fileserver.singleFileClient
 * JD-Core Version:    0.6.1
 */