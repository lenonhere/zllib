package com.zl.base.core.fileserver;

import java.io.Serializable;

public class FileTResult
  implements Serializable
{
  public static final int FILE_TRANSLATE_NORMAL = 0;
  public static final int FILE_TRANSLATE_CONNECT = 1;
  public static final int FILE_TRANSLATE_CLOSE = 2;
  public static final int FILE_TRANSLATE_UPLOAD = 3;
  public static final int FILE_TRANSLATE_UPLOADERROR = 4;
  public static final int FILE_TRANSLATE_DOWNLOAD = 5;
  public static final int FILE_TRANSLATE_DOWNLOADERROR = 6;
  public static final int WORD_TRANSLATE_SUCCESS = 7;
  public static final int WORD_TRANSLATE_ERROR = 8;
  private int returncode = 0;
  private String message = null;
  private long filecount = 0L;

  public synchronized int GetCode()
  {
    return this.returncode;
  }

  public synchronized void SetCode(int paramInt)
  {
    this.returncode = paramInt;
  }

  public synchronized String GetMessage()
  {
    return this.message;
  }

  public synchronized void SetMessage(String paramString)
  {
    this.message = paramString;
  }

  public synchronized long GetFileCount()
  {
    return this.filecount;
  }

  public synchronized void SetFileCount(long paramLong)
  {
    this.filecount = paramLong;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.fileserver.FileTResult
 * JD-Core Version:    0.6.1
 */