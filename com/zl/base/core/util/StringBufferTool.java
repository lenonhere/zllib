package com.zl.base.core.util;

public class StringBufferTool
{
  private StringBuffer sb;

  public StringBufferTool(StringBuffer paramStringBuffer)
  {
    this.sb = paramStringBuffer;
  }

  public StringBuffer getStringBuffer()
  {
    return this.sb;
  }

  public void appendln(String paramString)
  {
    this.sb.append(paramString);
    this.sb.append("\r\n");
  }

  public void appendln(boolean paramBoolean)
  {
    this.sb.append(paramBoolean);
    this.sb.append("\r\n");
  }

  public void appendln(char[] paramArrayOfChar)
  {
    this.sb.append(paramArrayOfChar);
    this.sb.append("\r\n");
  }

  public void appendln(Object paramObject)
  {
    this.sb.append(paramObject);
    this.sb.append("\r\n");
  }

  public void appendln(int paramInt)
  {
    this.sb.append(paramInt);
    this.sb.append("\r\n");
  }

  public void appendln(double paramDouble)
  {
    this.sb.append(paramDouble);
    this.sb.append("\r\n");
  }

  public void appendln(float paramFloat)
  {
    this.sb.append(paramFloat);
    this.sb.append("\r\n");
  }

  public void appendln(long paramLong)
  {
    this.sb.append(paramLong);
    this.sb.append("\r\n");
  }

  public void append(String paramString)
  {
    this.sb.append(paramString);
  }

  public void append(boolean paramBoolean)
  {
    this.sb.append(paramBoolean);
  }

  public void append(char[] paramArrayOfChar)
  {
    this.sb.append(paramArrayOfChar);
  }

  public void append(Object paramObject)
  {
    this.sb.append(paramObject);
  }

  public void append(int paramInt)
  {
    this.sb.append(paramInt);
  }

  public void append(double paramDouble)
  {
    this.sb.append(paramDouble);
  }

  public void append(float paramFloat)
  {
    this.sb.append(paramFloat);
  }

  public void append(long paramLong)
  {
    this.sb.append(paramLong);
  }

  public String toString()
  {
    return this.sb.toString();
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.util.StringBufferTool
 * JD-Core Version:    0.6.1
 */