package com.zl.base.core.compress;

public class IntToBytes
{
  public static byte[] intToBytesWay(int paramInt)
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = (byte)(paramInt >>> 24 & 0xFF);
    arrayOfByte[1] = (byte)(paramInt >>> 16 & 0xFF);
    arrayOfByte[2] = (byte)(paramInt >>> 8 & 0xFF);
    arrayOfByte[3] = (byte)(paramInt & 0xFF);
    return arrayOfByte;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.compress.IntToBytes
 * JD-Core Version:    0.6.1
 */