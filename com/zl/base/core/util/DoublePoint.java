package com.zl.base.core.util;

public class DoublePoint
{
  public double x = 0.0D;
  public double y = 0.0D;

  public DoublePoint()
  {
    this.x = (this.y = 0.0D);
  }

  public DoublePoint(double paramDouble1, double paramDouble2)
  {
    this.x = paramDouble1;
    this.y = paramDouble2;
  }

  public DoublePoint(DoublePoint paramDoublePoint)
  {
    this.x = paramDoublePoint.x;
    this.y = paramDoublePoint.y;
  }

  public void Set(double paramDouble1, double paramDouble2)
  {
    this.x = paramDouble1;
    this.y = paramDouble2;
  }

  public void Set(DoublePoint paramDoublePoint)
  {
    this.x = paramDoublePoint.x;
    this.y = paramDoublePoint.y;
  }

  public boolean equals(Object paramObject)
  {
    DoublePoint localDoublePoint = (DoublePoint)paramObject;
    return (localDoublePoint.x == this.x) && (localDoublePoint.y == this.y);
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.util.DoublePoint
 * JD-Core Version:    0.6.1
 */