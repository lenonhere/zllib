package com.zl.base.core.fileserver.zip;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class IniFile
{
  public static String getProfileString(String paramString1, String paramString2, String paramString3, String paramString4)
    throws IOException
  {
    String str2 = "";
    BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString1));
    int i = 0;
    try
    {
      String str1;
      while ((str1 = localBufferedReader.readLine()) != null)
      {
        str1 = str1.trim();
        str1 = str1.split("[;]")[0];
        Pattern localPattern = Pattern.compile("\\[\\s*.*\\s*\\]");
        Matcher localMatcher = localPattern.matcher(str1);
        if (localMatcher.matches())
        {
          localPattern = Pattern.compile("\\[\\s*" + paramString2 + "\\s*\\]");
          localMatcher = localPattern.matcher(str1);
          if (localMatcher.matches())
            i = 1;
          else
            i = 0;
        }
        if (i == 1)
        {
          str1 = str1.trim();
          String[] arrayOfString = str1.split("=");
          String str3;
          if (arrayOfString.length == 1)
          {
            str2 = arrayOfString[0].trim();
            if (str2.equalsIgnoreCase(paramString3))
            {
              str2 = "";
              str3 = str2;
              return str3;
            }
          }
          else if (arrayOfString.length == 2)
          {
            str2 = arrayOfString[0].trim();
            if (str2.equalsIgnoreCase(paramString3))
            {
              str2 = arrayOfString[1].trim();
              str3 = str2;
              return str3;
            }
          }
          else if (arrayOfString.length > 2)
          {
            str2 = arrayOfString[0].trim();
            if (str2.equalsIgnoreCase(paramString3))
            {
              str2 = str1.substring(str1.indexOf("=") + 1).trim();
              str3 = str2;
              return str3;
            }
          }
        }
      }
    }
    finally
    {
      localBufferedReader.close();
    }
    return paramString4;
  }

  public static boolean setProfileString(String paramString1, String paramString2, String paramString3, String paramString4)
    throws IOException
  {
    BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString1));
    int i = 0;
    String str1 = "";
    try
    {
      String str2;
      while ((str2 = localBufferedReader.readLine()) != null)
      {
        str2 = str2.trim();
        String str5;
        if (str2.split("[;]").length > 1)
          str5 = ";" + str2.split(";")[1];
        else
          str5 = "";
        String str3 = str2.split(";")[0];
        Pattern localPattern = Pattern.compile("\\[\\s*.*\\s*\\]");
        Matcher localMatcher = localPattern.matcher(str3);
        if (localMatcher.matches())
        {
          localPattern = Pattern.compile("\\[\\s*" + paramString2 + "\\s*\\]");
          localMatcher = localPattern.matcher(str3);
          if (localMatcher.matches())
            i = 1;
          else
            i = 0;
        }
        if (i == 1)
        {
          str3 = str3.trim();
          String[] arrayOfString = str3.split("=");
          String str6 = arrayOfString[0].trim();
          if (str6.equalsIgnoreCase(paramString3))
          {
            String str4 = str6 + " = " + paramString4 + " " + str5;
            for (str1 = str1 + str4 + "\r\n"; (str2 = localBufferedReader.readLine()) != null; str1 = str1 + str2 + "\r\n");
            localBufferedReader.close();
            BufferedWriter localBufferedWriter = new BufferedWriter(new FileWriter(paramString1, false));
            localBufferedWriter.write(str1);
            localBufferedWriter.flush();
            localBufferedWriter.close();
            boolean bool = true;
            return bool;
          }
        }
        str1 = str1 + str2 + "\r\n";
      }
    }
    catch (IOException localIOException)
    {
      throw localIOException;
    }
    finally
    {
      localBufferedReader.close();
    }
    return false;
  }

  public static void main(String[] paramArrayOfString)
  {
    try
    {
      System.out.println(setProfileString("e:/1.ini", "Settings", "SampSize", "111"));
    }
    catch (IOException localIOException)
    {
      System.out.println(localIOException.toString());
    }
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.fileserver.zip.IniFile
 * JD-Core Version:    0.6.1
 */