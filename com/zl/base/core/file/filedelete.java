package com.zl.base.core.file;

import java.io.File;

public class filedelete
{
  public void deleteFile(String paramString)
  {
    File localFile = new File(paramString);
    if (localFile.exists())
      localFile.delete();
  }

  public void deleteDir(String paramString)
  {
    deleteDir(paramString, true);
  }

  public void deleteChildDir(String paramString)
  {
    deleteDir(paramString, false);
  }

  private void deleteDir(String paramString, boolean paramBoolean)
  {
    File localFile = new File(paramString);
    if (localFile.exists())
    {
      if (localFile.isDirectory())
      {
        File[] arrayOfFile = localFile.listFiles();
        for (int i = 0; i < arrayOfFile.length; i++)
        {
          deleteDir(arrayOfFile[i].toString());
          arrayOfFile[i].delete();
        }
      }
      if (paramBoolean == true)
        localFile.delete();
    }
  }

  public static void main(String[] paramArrayOfString)
  {
    filedelete localfiledelete = new filedelete();
    localfiledelete.deleteChildDir("c:\\upload\\");
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.file.filedelete
 * JD-Core Version:    0.6.1
 */