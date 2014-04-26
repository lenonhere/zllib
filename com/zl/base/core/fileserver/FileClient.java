package com.zl.base.core.fileserver;

import com.zl.base.core.fileserver.socket.Client;
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
import java.util.ArrayList;
import java.util.Calendar;

public class FileClient extends Client
{
  public FileClient(String paramString1, int paramInt, String paramString2)
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
    ArrayList localArrayList = FileEx.GetCurrentPath(paramArrayList, this.orginPath);
    ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(this.out);
    Calendar localCalendar = Calendar.getInstance();
    String str = this.tempPath;
    if (this.tempPath == null)
      str = this.orginPath;
    File localFile = new File(str, "crea.send.tmp" + String.valueOf(localCalendar.get(14)));
    Compress.DeflaterFile(localArrayList, this.orginPath, localFile.getPath());
    FileTCommand localFileTCommand = new FileTCommand();
    localFileTCommand.SetType(1001);
    localFileTCommand.SetOperator("upload");
    localFileTCommand.SetFileCount(localFile.length());
    localObjectOutputStream.writeObject(localFileTCommand);
    this.out.flush();
    FileInputStream localFileInputStream = new FileInputStream(localFile);
    byte[] arrayOfByte = new byte[1024];
    for (int i = localFileInputStream.read(arrayOfByte); i >= 0; i = localFileInputStream.read(arrayOfByte))
      this.out.write(arrayOfByte, 0, i);
    localFileInputStream.close();
    this.out.flush();
    localFile.delete();
    ObjectInputStream localObjectInputStream = new ObjectInputStream(this.in);
    FileTResult localFileTResult = (FileTResult)localObjectInputStream.readObject();
    return localFileTResult.GetCode() == 3;
  }

  public boolean DownLoadFile(ArrayList paramArrayList)
    throws Exception
  {
    ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(this.out);
    FileTCommand localFileTCommand = new FileTCommand();
    localFileTCommand.SetType(1001);
    localFileTCommand.SetOperator("download");
    localFileTCommand.SetList(paramArrayList);
    localFileTCommand.SetFileCount(0L);
    localObjectOutputStream.writeObject(localFileTCommand);
    this.out.flush();
    ObjectInputStream localObjectInputStream = new ObjectInputStream(this.in);
    FileTResult localFileTResult = (FileTResult)localObjectInputStream.readObject();
    if (localFileTResult.GetCode() == 3)
    {
      long l = localFileTResult.GetFileCount();
      Calendar localCalendar = Calendar.getInstance();
      String str = this.tempPath;
      if (this.tempPath == null)
        str = this.orginPath;
      File localFile = new File(str, "crea.save.tmp" + String.valueOf(localCalendar.get(14)));
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
      Compress.InflaterFile(localFile.getPath(), this.orginPath);
      localFile.delete();
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
 * Qualified Name:     com.zl.base.core.fileserver.FileClient
 * JD-Core Version:    0.6.1
 */