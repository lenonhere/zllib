package com.zl.base.core.util;

public class DoubleRectangle
{
  public double x = 0.0D;
  public double y = 0.0D;
  public double width = 0.0D;
  public double height = 0.0D;

  public DoubleRectangle()
  {
    this.x = (this.y = this.width = this.height = 0.0D);
  }

  public DoubleRectangle(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    this.x = paramDouble1;
    this.y = paramDouble2;
    this.width = paramDouble3;
    this.height = paramDouble4;
  }

  public DoubleRectangle(DoublePoint paramDoublePoint, DoubleSize paramDoubleSize)
  {
    this.x = paramDoublePoint.x;
    this.y = paramDoublePoint.y;
    this.width = paramDoubleSize.cx;
    this.height = paramDoubleSize.cy;
  }

  public DoubleRectangle(DoubleRectangle paramDoubleRectangle)
  {
    this.x = paramDoubleRectangle.x;
    this.y = paramDoubleRectangle.y;
    this.width = paramDoubleRectangle.width;
    this.height = paramDoubleRectangle.height;
  }

  public void Set(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    this.x = paramDouble1;
    this.y = paramDouble2;
    this.width = paramDouble3;
    this.height = paramDouble4;
  }

  public void Set(DoublePoint paramDoublePoint, DoubleSize paramDoubleSize)
  {
    this.x = paramDoublePoint.x;
    this.y = paramDoublePoint.y;
    this.width = paramDoubleSize.cx;
    this.height = paramDoubleSize.cy;
  }

  public void Set(DoubleRectangle paramDoubleRectangle)
  {
    this.x = paramDoubleRectangle.x;
    this.y = paramDoubleRectangle.y;
    this.width = paramDoubleRectangle.width;
    this.height = paramDoubleRectangle.height;
  }

  public boolean isEmpty()
  {
    return (this.width == 0.0D) && (this.height == 0.0D);
  }

  public boolean equals(Object paramObject)
  {
    DoubleRectangle localDoubleRectangle = (DoubleRectangle)paramObject;
    return (localDoubleRectangle.x == this.x) && (localDoubleRectangle.y == this.y) && (localDoubleRectangle.width == this.width) && (localDoubleRectangle.height == this.height);
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.util.DoubleRectangle
 * JD-Core Version:    0.6.1
 */