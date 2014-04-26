package com.zl.base.core.compress;

public class ShortToBytes
{
  public static byte[] shortToBytesWay(short paramShort)
  {
    byte[] arrayOfByte = new byte[2];
    arrayOfByte[0] = (byte)(paramShort >>> 8 & 0xFF);
    arrayOfByte[1] = (byte)(paramShort & 0xFF);
    return arrayOfByte;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.compress.ShortToBytes
 * JD-Core Version:    0.6.1
 */