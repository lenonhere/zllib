package com.zl.base.core.taglib.chart;

public class BaseSeries
{
  private String caption = "";
  private String seriesproperty = null;
  private String valueproperty = null;
  private int charttype = 0;
  private int shapetype = 0;

  public BaseSeries()
  {
  }

  public BaseSeries(String paramString1, String paramString2)
  {
    this.caption = paramString1;
    this.valueproperty = paramString2;
  }

  public String getcaption()
  {
    return this.caption;
  }

  public void setcaption(String paramString)
  {
    this.caption = paramString;
  }

  public String getseriesproperty()
  {
    return this.seriesproperty;
  }

  public void setseriesproperty(String paramString)
  {
    this.seriesproperty = paramString;
  }

  public String getvalueproperty()
  {
    return this.valueproperty;
  }

  public void setvalueproperty(String paramString)
  {
    this.valueproperty = paramString;
  }

  public String getproperty()
  {
    return this.valueproperty;
  }

  public void setproperty(String paramString)
  {
    this.valueproperty = paramString;
  }

  public int getcharttype()
  {
    return this.charttype;
  }

  public void setcharttype(int paramInt)
  {
    this.charttype = paramInt;
  }

  public int getshapetype()
  {
    return this.shapetype;
  }

  public void setshapetype(int paramInt)
  {
    this.shapetype = paramInt;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.BaseSeries
 * JD-Core Version:    0.6.1
 */