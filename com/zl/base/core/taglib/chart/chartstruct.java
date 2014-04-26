package com.zl.base.core.taglib.chart;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

public class chartstruct
  implements Serializable
{
  public static final int Chart_LINE = 0;
  public static final int Chart_BAR = 1;
  public static final int Chart_PIE = 2;
  public static final int Chart_SLINE = 3;
  public static final int Chart_LBAR = 4;
  public static final int Chart_SBAR = 5;
  public static final int Chart_LINE2LINE = 6;
  public static final int Chart_LINE2BAR = 7;
  public static final int Shape_Solid = 0;
  public static final int Shape_Dash = 1;
  public static final int Chart_Direction_Horizontal = 0;
  public static final int Chart_Direction_Vertical = 1;
  public static final int SeriesLabel_Direction_Horizontal = 0;
  public static final int SeriesLabel_Direction_Vertical = 1;
  public static final int Lend_Location_Left = 0;
  public static final int Lend_Location_Top = 1;
  public static final int Lend_Location_Right = 2;
  public static final int Lend_Location_Bottom = 3;
  private int charttype = 1;
  private boolean is3D = false;
  private Color bgcolor = new Color(255, 255, 255);
  private Color plotbgcolor = new Color(255, 255, 255);
  private String caption = null;
  private String hcaption = null;
  private String vcaption = "";
  private ArrayList series = new ArrayList();
  private int chartdirection = 0;
  private int serieslabeldirection = 0;
  private int legendlocation = 3;
  private boolean legendvisible = true;
  private boolean poltshapevisible = false;
  private boolean dataprogress = false;
  private boolean mutipievisible = false;
  private boolean captionvisible = true;
  private boolean hcaptionvisible = true;
  private boolean vcaptionvisible = true;
  private int width = 500;
  private int height = 300;
  private int captionfontsize = 15;
  private int legendfontsize = 12;
  private int labelfontsize = 12;
  private int axiscaptionfontsize = 12;
  private int sectionlabeltype = 5;
  private int maxlabelcount = 0;
  public double YLowerMargin = 0.0D;

  public int getcharttype()
  {
    return this.charttype;
  }

  public void setcharttype(int paramInt)
  {
    this.charttype = paramInt;
  }

  public boolean getis3D()
  {
    return this.is3D;
  }

  public void setis3D(boolean paramBoolean)
  {
    this.is3D = paramBoolean;
  }

  public Color getbgcolor()
  {
    return this.bgcolor;
  }

  public void setbgcolor(Color paramColor)
  {
    this.bgcolor = paramColor;
  }

  public Color getplotbgcolor()
  {
    return this.plotbgcolor;
  }

  public void setplotbgcolor(Color paramColor)
  {
    this.plotbgcolor = paramColor;
  }

  public String getcaption()
  {
    return this.caption;
  }

  public void setcaption(String paramString)
  {
    this.caption = paramString;
  }

  public String gethcaption()
  {
    return this.hcaption;
  }

  public void sethcaption(String paramString)
  {
    this.hcaption = paramString;
  }

  public String getvcaption()
  {
    return this.vcaption;
  }

  public void setvcaption(String paramString)
  {
    this.vcaption = paramString;
  }

  public ArrayList getseries()
  {
    return this.series;
  }

  public void setseries(ArrayList paramArrayList)
  {
    this.series = paramArrayList;
  }

  public int getchartdirection()
  {
    return this.chartdirection;
  }

  public void setchartdirection(int paramInt)
  {
    this.chartdirection = paramInt;
  }

  public int getserieslabeldirection()
  {
    return this.serieslabeldirection;
  }

  public void setserieslabeldirection(int paramInt)
  {
    this.serieslabeldirection = paramInt;
  }

  public int getlegendlocation()
  {
    return this.legendlocation;
  }

  public void setlegendlocation(int paramInt)
  {
    this.legendlocation = paramInt;
  }

  public boolean getlegendvisible()
  {
    return this.legendvisible;
  }

  public void setlegendvisible(boolean paramBoolean)
  {
    this.legendvisible = paramBoolean;
  }

  public boolean getpoltshapevisible()
  {
    return this.poltshapevisible;
  }

  public void setpoltshapevisible(boolean paramBoolean)
  {
    this.poltshapevisible = paramBoolean;
  }

  public boolean getdataprogress()
  {
    return this.dataprogress;
  }

  public void setdataprogress(boolean paramBoolean)
  {
    this.dataprogress = paramBoolean;
  }

  public boolean getmutipievisible()
  {
    return this.mutipievisible;
  }

  public void setmutipievisible(boolean paramBoolean)
  {
    this.mutipievisible = paramBoolean;
  }

  public boolean getcaptionvisible()
  {
    return this.captionvisible;
  }

  public void setcaptionvisible(boolean paramBoolean)
  {
    this.captionvisible = paramBoolean;
  }

  public boolean gethcaptionvisible()
  {
    return this.hcaptionvisible;
  }

  public void sethcaptionvisible(boolean paramBoolean)
  {
    this.hcaptionvisible = paramBoolean;
  }

  public boolean getvcaptionvisible()
  {
    return this.vcaptionvisible;
  }

  public void setvcaptionvisible(boolean paramBoolean)
  {
    this.vcaptionvisible = paramBoolean;
  }

  public int getwidth()
  {
    return this.width;
  }

  public void setwidth(int paramInt)
  {
    this.width = paramInt;
  }

  public int getheight()
  {
    return this.height;
  }

  public void setheight(int paramInt)
  {
    this.height = paramInt;
  }

  public int getcaptionfontsize()
  {
    return this.captionfontsize;
  }

  public void setcaptionfontsize(int paramInt)
  {
    this.captionfontsize = paramInt;
  }

  public int getlegenfontsize()
  {
    return this.legendfontsize;
  }

  public void setlegendfontsize(int paramInt)
  {
    this.legendfontsize = paramInt;
  }

  public int getlabelfontsize()
  {
    return this.labelfontsize;
  }

  public void setlabelfontsize(int paramInt)
  {
    this.labelfontsize = paramInt;
  }

  public int getaxiscaptionfontsize()
  {
    return this.axiscaptionfontsize;
  }

  public void setaxiscaptionfontsize(int paramInt)
  {
    this.axiscaptionfontsize = paramInt;
  }

  public int getsectionlabeltype()
  {
    return this.sectionlabeltype;
  }

  public void setsectionlabeltype(int paramInt)
  {
    this.sectionlabeltype = paramInt;
  }

  public int getmaxlabelcount()
  {
    return this.maxlabelcount;
  }

  public void setmaxlabelcount(int paramInt)
  {
    this.maxlabelcount = paramInt;
  }

  public double getYLowerMargin()
  {
    return this.YLowerMargin;
  }

  public void setYLowerMargin(double paramDouble)
  {
    this.YLowerMargin = paramDouble;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.chartstruct
 * JD-Core Version:    0.6.1
 */