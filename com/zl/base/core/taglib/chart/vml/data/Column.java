package com.zl.base.core.taglib.chart.vml.data;

public class Column
{
  protected String Caption = null;
  protected String Property = null;
  protected int ShapeType = 0;
  protected String ID = null;
  protected String Href = null;
  protected String Script = null;
  protected boolean Sign = true;

  public String GetCaption()
  {
    return this.Caption;
  }

  public void SetCaption(String paramString)
  {
    this.Caption = paramString;
  }

  public String GetProperty()
  {
    return this.Property;
  }

  public void SetProperty(String paramString)
  {
    this.Property = paramString;
  }

  public int GetShapeType()
  {
    return this.ShapeType;
  }

  public void SetShapeType(int paramInt)
  {
    this.ShapeType = paramInt;
  }

  public String GetID()
  {
    return this.ID;
  }

  public void SetID(String paramString)
  {
    this.ID = paramString;
  }

  public String GetHref()
  {
    return this.Href;
  }

  public void SetHref(String paramString)
  {
    this.Href = paramString;
  }

  public String GetScript()
  {
    return this.Script;
  }

  public void SetScript(String paramString)
  {
    this.Script = paramString;
  }

  public boolean GetSign()
  {
    return this.Sign;
  }

  public void SetSign(boolean paramBoolean)
  {
    this.Sign = paramBoolean;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.vml.data.Column
 * JD-Core Version:    0.6.1
 */