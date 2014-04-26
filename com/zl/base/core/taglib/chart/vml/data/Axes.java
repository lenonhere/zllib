package com.zl.base.core.taglib.chart.vml.data;

import java.util.ArrayList;

public class Axes
{
  public ArrayList m_VCaption = new ArrayList();
  public ArrayList m_HCaption = new ArrayList();
  public boolean m_rotate = false;
  private int m_DefineMax = 0;
  private int m_DefineMin = 0;

  public Axes(DataSet paramDataSet, int paramInt, double paramDouble1, double paramDouble2, boolean paramBoolean)
  {
    Init(paramDataSet, paramInt, paramDouble1, paramDouble2, paramBoolean);
  }

  public int GetMaxValue()
  {
    return this.m_DefineMax;
  }

  public int GetMinValue()
  {
    return this.m_DefineMin;
  }

  private void Init(DataSet paramDataSet, int paramInt, double paramDouble1, double paramDouble2, boolean paramBoolean)
  {
    this.m_VCaption.clear();
    this.m_HCaption.clear();
    if (paramBoolean)
      this.m_VCaption.addAll(paramDataSet.m_Caption);
    else
      this.m_HCaption.addAll(paramDataSet.m_Caption);
    double d1;
    if (paramDouble1 > 0.0D)
    {
      d1 = paramDouble1;
      int i = (int)paramDouble2;
      for (int j = paramInt; j >= 0; j--)
      {
        int k = (int)(i + d1 * j);
        if (paramBoolean)
          this.m_HCaption.add(String.valueOf(k));
        else
          this.m_VCaption.add(String.valueOf(k));
      }
      this.m_DefineMin = i;
      this.m_DefineMax = (i + (int)d1 * paramInt);
    }
    else
    {
      d1 = paramDataSet.GetMaxValue();
      double d2 = paramDataSet.GetMinValue();
      double d3 = (d1 - d2) / paramInt;
      if (d3 <= 1.0D)
        d3 = 1.0D;
      else if ((d3 > 1.0D) && (d3 < 10.0D))
      {
        if (d3 < 2.0D)
          d3 = 2.0D;
        else if ((d3 >= 2.0D) && (d3 < 3.0D))
          d3 = 3.0D;
        else if ((d3 >= 3.0D) && (d3 < 4.0D))
          d3 = 4.0D;
        else if ((d3 >= 4.0D) && (d3 < 5.0D))
          d3 = 5.0D;
        else if ((d3 >= 5.0D) && (d3 < 6.0D))
          d3 = 6.0D;
        else if ((d3 >= 6.0D) && (d3 < 7.0D))
          d3 = 7.0D;
        else if ((d3 >= 7.0D) && (d3 < 8.0D))
          d3 = 8.0D;
        else if ((d3 >= 8.0D) && (d3 < 9.0D))
          d3 = 9.0D;
        else
          d3 = 10.0D;
      }
      else if ((d3 >= 10.0D) && (d3 < 100.0D))
      {
        if (d3 < 25.0D)
          d3 = 25.0D;
        else if ((d3 >= 25.0D) && (d3 < 50.0D))
          d3 = 50.0D;
        else if ((d3 >= 50.0D) && (d3 < 75.0D))
          d3 = 75.0D;
        else if ((d3 >= 75.0D) && (d3 < 100.0D))
          d3 = 100.0D;
      }
      else if ((d3 >= 100.0D) && (d3 < 1000.0D))
      {
        if (d3 < 250.0D)
          d3 = 250.0D;
        else if ((d3 >= 250.0D) && (d3 < 500.0D))
          d3 = 500.0D;
        else if ((d3 >= 500.0D) && (d3 < 750.0D))
          d3 = 750.0D;
        else if ((d3 >= 750.0D) && (d3 < 1000.0D))
          d3 = 1000.0D;
      }
      else if ((d3 >= 1000.0D) && (d3 < 10000.0D))
      {
        if (d3 < 2500.0D)
          d3 = 2500.0D;
        else if ((d3 >= 2500.0D) && (d3 < 5000.0D))
          d3 = 5000.0D;
        else if ((d3 >= 5000.0D) && (d3 < 7500.0D))
          d3 = 7500.0D;
        else if ((d3 >= 7500.0D) && (d3 < 10000.0D))
          d3 = 10000.0D;
      }
      else if ((d3 >= 10000.0D) && (d3 < 100000.0D))
      {
        if (d3 < 25000.0D)
          d3 = 25000.0D;
        else if ((d3 >= 25000.0D) && (d3 < 50000.0D))
          d3 = 50000.0D;
        else if ((d3 >= 50000.0D) && (d3 < 75000.0D))
          d3 = 75000.0D;
        else if ((d3 >= 75000.0D) && (d3 < 100000.0D))
          d3 = 100000.0D;
      }
      else if ((d3 >= 100000.0D) && (d3 < 1000000.0D))
      {
        if (d3 < 250000.0D)
          d3 = 250000.0D;
        else if ((d3 >= 250000.0D) && (d3 < 500000.0D))
          d3 = 500000.0D;
        else if ((d3 >= 500000.0D) && (d3 < 750000.0D))
          d3 = 750000.0D;
        else if ((d3 >= 750000.0D) && (d3 < 1000000.0D))
          d3 = 1000000.0D;
      }
      else if ((d3 >= 1000000.0D) && (d3 < 10000000.0D))
      {
        if (d3 < 2500000.0D)
          d3 = 2500000.0D;
        else if ((d3 >= 2500000.0D) && (d3 < 5000000.0D))
          d3 = 5000000.0D;
        else if ((d3 >= 5000000.0D) && (d3 < 7500000.0D))
          d3 = 7500000.0D;
        else if ((d3 >= 7500000.0D) && (d3 < 10000000.0D))
          d3 = 10000000.0D;
      }
      else
        d3 = 10000000.0D;
      int m = (int)((int)(d2 / d3) * d3);
      for (int n = paramInt; n >= 0; n--)
      {
        int i1 = (int)(m + d3 * n);
        if (paramBoolean)
          this.m_HCaption.add(String.valueOf(i1));
        else
          this.m_VCaption.add(String.valueOf(i1));
      }
      this.m_DefineMin = m;
      this.m_DefineMax = (m + (int)d3 * paramInt);
    }
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.data.Axes
 * JD-Core Version:    0.6.1
 */