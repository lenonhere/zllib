package com.zl.base.core.compress;

public class IntOperator
{
  public static int InvertDWord(int paramInt)
  {
    byte[] arrayOfByte1 = IntToBytes.intToBytesWay(paramInt);
    byte[] arrayOfByte2 = new byte[4];
    arrayOfByte2[0] = arrayOfByte1[3];
    arrayOfByte2[1] = arrayOfByte1[2];
    arrayOfByte2[2] = arrayOfByte1[1];
    arrayOfByte2[3] = arrayOfByte1[0];
    return BytesToInt.bytesToShortWay(arrayOfByte2, 0, 4);
  }

  public static byte[] InvertDWordByte(int paramInt)
  {
    byte[] arrayOfByte1 = IntToBytes.intToBytesWay(paramInt);
    byte[] arrayOfByte2 = new byte[4];
    arrayOfByte2[0] = arrayOfByte1[3];
    arrayOfByte2[1] = arrayOfByte1[2];
    arrayOfByte2[2] = arrayOfByte1[1];
    arrayOfByte2[3] = arrayOfByte1[0];
    return arrayOfByte2;
  }

  public static int ByteToInt(byte paramByte)
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = 0;
    arrayOfByte[1] = 0;
    arrayOfByte[2] = 0;
    arrayOfByte[3] = paramByte;
    return BytesToInt.bytesToShortWay(arrayOfByte, 0, 4);
  }

  public static int LowerLog2(int paramInt)
  {
    int i = 0;
    if (paramInt > 0)
    {
      int j = 1;
      while (true)
      {
        if (j == paramInt)
          return i;
        if (j > paramInt)
          return i - 1;
        j <<= 1;
        i++;
      }
    }
    return -1;
  }

  public static int UpperLog2(int paramInt)
  {
    int i = 0;
    if (paramInt > 0)
    {
      int j = 1;
      while (true)
      {
        if (j >= paramInt)
          return i;
        j <<= 1;
        i++;
      }
    }
    return -1;
  }

  public static short UpperShortZero(short paramShort)
  {
    byte[] arrayOfByte = ShortToBytes.shortToBytesWay(paramShort);
    arrayOfByte[0] = 0;
    return BytesToShort.bytesToShortWay(arrayOfByte, 0, 2);
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.compress.IntOperator
 * JD-Core Version:    0.6.1
 */