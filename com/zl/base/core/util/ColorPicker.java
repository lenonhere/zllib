package com.zl.base.core.util;

import java.util.ArrayList;

public class ColorPicker
{
  public static final String COLOR_BLACK = "";
  public static final String COLOR_WHIT = "#FFFFFF";
  public static final String COLOR_GRAY = "#CCCCCC";
  public static final String COLOR_RED = "#FF0000";
  public static final String COLOR_BLUE = "#0000FF";
  public static final String COLOR_GREEN = "#00FF00";
  public static final String COLOR_YELLOW = "#FFFF00";
  public static final String COLOR_CYAN = "#00FFFF";
  public static final String COLOR_PURPLE = "#FF00FF";
  private ArrayList m_colors = new ArrayList();
  private ArrayList m_mcolors = new ArrayList();
  private ArrayList m_scolors = new ArrayList();

  public ColorPicker()
  {
    InitColor();
  }

  public ColorPicker(String paramString)
  {
    InitColor(paramString);
  }

  public void InitColor()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("#FF0000,#FFD1D1,#BF0000;");
    localStringBuffer.append("#0000FF,#D1D1FF,#0000A8;");
    localStringBuffer.append("#800080,#FFAAFF,#6F006F;");
    localStringBuffer.append("#008000,#B1FFB1,#006000;");
    localStringBuffer.append("#FF00FF,#FFDDFF,#840084;");
    localStringBuffer.append("#00FF00,#D1FFD1,#006C00;");
    localStringBuffer.append("#6600FF,#DBC4FF,#3E009B;");
    localStringBuffer.append("#FF9933,#FFE4CA,#934900;");
    localStringBuffer.append("#0099FF,#B9E3FF,#005288;");
    localStringBuffer.append("#808080,#FFFFFF,#4B4B4B");
    InitColor(String.valueOf(localStringBuffer));
  }

  public void InitColor(String paramString)
  {
    this.m_colors.clear();
    this.m_mcolors.clear();
    this.m_scolors.clear();
    String[] arrayOfString1 = paramString.split(";");
    for (int i = 0; i < arrayOfString1.length; i++)
    {
      String[] arrayOfString2 = arrayOfString1[i].split(",");
      if (arrayOfString2.length >= 3)
      {
        this.m_colors.add(arrayOfString2[0]);
        this.m_mcolors.add(arrayOfString2[1]);
        this.m_scolors.add(arrayOfString2[2]);
      }
    }
  }

  public String GetColor(int paramInt)
  {
    if (this.m_colors.size() < 1)
      return null;
    int i = paramInt - (paramInt + 1) / this.m_colors.size() * this.m_colors.size();
    if (i < 0)
      i = this.m_colors.size() - 1;
    return (String)this.m_colors.get(i);
  }

  public String GetMaskColor(int paramInt)
  {
    if (this.m_mcolors.size() < 1)
      return null;
    int i = paramInt - (paramInt + 1) / this.m_mcolors.size() * this.m_mcolors.size();
    if (i < 0)
      i = this.m_mcolors.size() - 1;
    return (String)this.m_mcolors.get(i);
  }

  public String GetShadowColor(int paramInt)
  {
    if (this.m_scolors.size() < 1)
      return null;
    int i = paramInt - (paramInt + 1) / this.m_scolors.size() * this.m_scolors.size();
    if (i < 0)
      i = this.m_scolors.size() - 1;
    return (String)this.m_scolors.get(i);
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.util.ColorPicker
 * JD-Core Version:    0.6.1
 */