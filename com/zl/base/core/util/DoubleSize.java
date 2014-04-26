package com.zl.base.core.util;

public class DoubleSize
{
  public double cx = 0.0D;
  public double cy = 0.0D;

  public DoubleSize()
  {
    this.cx = (this.cy = 0.0D);
  }

  public DoubleSize(double paramDouble1, double paramDouble2)
  {
    this.cx = paramDouble1;
    this.cy = paramDouble2;
  }

  public DoubleSize(DoubleSize paramDoubleSize)
  {
    this.cx = paramDoubleSize.cx;
    this.cy = paramDoubleSize.cy;
  }

  public void Set(double paramDouble1, double paramDouble2)
  {
    this.cx = paramDouble1;
    this.cy = paramDouble2;
  }

  public void Set(DoubleSize paramDoubleSize)
  {
    this.cx = paramDoubleSize.cx;
    this.cy = paramDoubleSize.cy;
  }

  public boolean equals(Object paramObject)
  {
    DoubleSize localDoubleSize = (DoubleSize)paramObject;
    return (localDoubleSize.cx == this.cx) && (localDoubleSize.cy == this.cy);
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.util.DoubleSize
 * JD-Core Version:    0.6.1
 */