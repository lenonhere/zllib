package com.zl.base.core.fileserver.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Compress
{
  public static String ZIP_COMMENT = "CREA_UNION software 2005-2008 \nGZip compress data";

  public static File[] listFiles(File paramFile)
  {
    String[] arrayOfString = paramFile.list();
    if (arrayOfString == null)
      return null;
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < arrayOfString.length; i++)
      localArrayList.add(new File(paramFile.getAbsolutePath(), arrayOfString[i]));
    return (File[])localArrayList.toArray(new File[0]);
  }

  public static synchronized void DeflaterFile(ArrayList paramArrayList, String paramString1, String paramString2)
    throws IOException
  {
    File localFile1 = new File(paramString1);
    if (!localFile1.isDirectory())
      return;
    FileOutputStream localFileOutputStream = new FileOutputStream(paramString2);
    CheckedOutputStream localCheckedOutputStream = new CheckedOutputStream(localFileOutputStream, new Adler32());
    ZipOutputStream localZipOutputStream = new ZipOutputStream(localCheckedOutputStream);
    localZipOutputStream.setComment(ZIP_COMMENT);
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      File localFile2 = new File((String)paramArrayList.get(i));
      if (localFile2.exists())
        if (localFile2.isDirectory())
          zipDir(localFile2, localFile1, localZipOutputStream);
        else
          zipFile(localFile2, localFile1, localZipOutputStream);
    }
    localZipOutputStream.close();
  }

  public static synchronized void DeflaterFile(ArrayList paramArrayList, String paramString, OutputStream paramOutputStream)
    throws IOException
  {
    File localFile1 = new File(paramString);
    if (!localFile1.isDirectory())
      return;
    CheckedOutputStream localCheckedOutputStream = new CheckedOutputStream(paramOutputStream, new Adler32());
    ZipOutputStream localZipOutputStream = new ZipOutputStream(localCheckedOutputStream);
    localZipOutputStream.setComment(ZIP_COMMENT);
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      File localFile2 = new File((String)paramArrayList.get(i));
      if (localFile2.exists())
        if (localFile2.isDirectory())
          zipDir(localFile2, localFile1, localZipOutputStream);
        else
          zipFile(localFile2, localFile1, localZipOutputStream);
    }
  }

  private static synchronized void zipFile(File paramFile1, File paramFile2, ZipOutputStream paramZipOutputStream)
    throws IOException
  {
    if (paramFile1.isDirectory())
      return;
    FileInputStream localFileInputStream = new FileInputStream(paramFile1);
    ZipEntry localZipEntry = new ZipEntry(FileEx.GetAbstractPath(paramFile1, paramFile2));
    paramZipOutputStream.putNextEntry(localZipEntry);
    byte[] arrayOfByte = new byte[1024];
    for (int i = localFileInputStream.read(arrayOfByte); i >= 0; i = localFileInputStream.read(arrayOfByte))
      paramZipOutputStream.write(arrayOfByte, 0, i);
    paramZipOutputStream.closeEntry();
    localFileInputStream.close();
  }

  private static synchronized void zipDir(File paramFile1, File paramFile2, ZipOutputStream paramZipOutputStream)
    throws IOException
  {
    if (!paramFile1.isDirectory())
      return;
    if (paramFile2 == null)
      paramFile2 = paramFile1;
    File[] arrayOfFile = listFiles(paramFile1);
    for (int i = 0; i < arrayOfFile.length; i++)
      if (arrayOfFile[i].isDirectory())
      {
        zipDir(arrayOfFile[i], paramFile2, paramZipOutputStream);
      }
      else
      {
        FileInputStream localFileInputStream = new FileInputStream(arrayOfFile[i]);
        ZipEntry localZipEntry = new ZipEntry(FileEx.GetAbstractPath(arrayOfFile[i], paramFile2));
        paramZipOutputStream.putNextEntry(localZipEntry);
        byte[] arrayOfByte = new byte[1024];
        for (int j = localFileInputStream.read(arrayOfByte); j >= 0; j = localFileInputStream.read(arrayOfByte))
          paramZipOutputStream.write(arrayOfByte, 0, j);
        paramZipOutputStream.closeEntry();
        localFileInputStream.close();
      }
  }

  public static synchronized void InflaterFile(String paramString1, String paramString2)
    throws IOException
  {
    FileInputStream localFileInputStream = new FileInputStream(paramString1);
    CheckedInputStream localCheckedInputStream = new CheckedInputStream(localFileInputStream, new Adler32());
    ZipInputStream localZipInputStream = new ZipInputStream(localCheckedInputStream);
    File localFile = new File(paramString2);
    unzipFile(localFile, localZipInputStream);
    localZipInputStream.close();
  }

  public static synchronized void InflaterFile(InputStream paramInputStream, String paramString)
    throws IOException
  {
    CheckedInputStream localCheckedInputStream = new CheckedInputStream(paramInputStream, new Adler32());
    ZipInputStream localZipInputStream = new ZipInputStream(localCheckedInputStream);
    File localFile = new File(paramString);
    unzipFile(localFile, localZipInputStream);
  }

  public static synchronized void InflaterFile(InputStream paramInputStream, String paramString, int paramInt)
    throws IOException
  {
    CheckedInputStream localCheckedInputStream = new CheckedInputStream(paramInputStream, new Adler32());
    ZipInputStream localZipInputStream = new ZipInputStream(localCheckedInputStream);
    File localFile = new File(paramString);
    unzipFile(localFile, localZipInputStream, paramInt);
  }

  private static synchronized void unzipFile(File paramFile, ZipInputStream paramZipInputStream)
    throws IOException
  {
    ZipEntry localZipEntry = null;
    String str = "";
    while ((localZipEntry = paramZipInputStream.getNextEntry()) != null)
    {
      str = FileEx.replaceAll(localZipEntry.getName(), "\\", "/");
      str = FileEx.replaceAll(str, "//", "/");
      File localFile = new File(paramFile, str);
      if (FileEx.CreatePath(localFile))
      {
        FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
        byte[] arrayOfByte = new byte[1024];
        int i = 0;
        while ((i = paramZipInputStream.read(arrayOfByte)) != -1)
          localFileOutputStream.write(arrayOfByte, 0, i);
        localFileOutputStream.close();
      }
      paramZipInputStream.closeEntry();
    }
  }

  private static synchronized void unzipFile(File paramFile, ZipInputStream paramZipInputStream, int paramInt)
    throws IOException
  {
    ZipEntry localZipEntry = null;
    String str = "";
    for (int i = 0; i < paramInt; i++)
    {
      localZipEntry = paramZipInputStream.getNextEntry();
      if (localZipEntry != null)
      {
        str = localZipEntry.getName();
        str = FileEx.replaceAll(str, "\\", "/");
        str = FileEx.replaceAll(str, "//", "/");
        File localFile = new File(paramFile, str);
        if (FileEx.CreatePath(localFile))
        {
          FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
          byte[] arrayOfByte = new byte[1024];
          int j = 0;
          while ((j = paramZipInputStream.read(arrayOfByte)) != -1)
            localFileOutputStream.write(arrayOfByte, 0, j);
          localFileOutputStream.close();
        }
        paramZipInputStream.closeEntry();
      }
    }
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.fileserver.zip.Compress
 * JD-Core Version:    0.6.1
 */