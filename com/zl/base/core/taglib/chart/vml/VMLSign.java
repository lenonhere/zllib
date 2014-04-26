package com.zl.base.core.taglib.chart.vml;

import java.util.ArrayList;

public class VMLSign
{
  protected ArrayList m_Paths = new ArrayList();

  public VMLSign()
  {
    InitPath();
  }

  public void InitPath()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("m 3000,0 l 0,3000, 0,9000, 3000,12000, 9000,12000, 12000,9000, 12000,3000, 9000,0 x e;");
    localStringBuffer.append("m 0,0 l 0,12000, 12000,12000, 12000,0  x e;");
    localStringBuffer.append("m 6000,0 l 12000,6000, 6000,12000, 0,6000 x e;");
    localStringBuffer.append("m 6000,0 l 0,12000, 12000,12000, 6000,0 x e;");
    localStringBuffer.append("m 6000,0 l 0,3000, 0,9000, 6000,12000, 12000,9000, 12000,3000 x e;");
    localStringBuffer.append("m 0,0 l 6000,12000,12000,0, 0,0 x e");
    InitPath(localStringBuffer.toString());
  }

  public void InitPath(String paramString)
  {
    this.m_Paths.clear();
    String[] arrayOfString = paramString.split(";");
    for (int i = 0; i < arrayOfString.length; i++)
      this.m_Paths.add(arrayOfString[i]);
  }

  public String GetPath(int paramInt)
  {
    if (this.m_Paths.size() < 1)
      return null;
    int i = paramInt - (paramInt + 1) / this.m_Paths.size() * this.m_Paths.size();
    if (i < 0)
      i = this.m_Paths.size() - 1;
    return (String)this.m_Paths.get(i);
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.VMLSign
 * JD-Core Version:    0.6.1
 */