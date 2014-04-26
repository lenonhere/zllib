package com.zl.base.core.compress;

public class BytesToInt
{
  public static int bytesToShortWay(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt2 != 4) || (paramArrayOfByte == null) || (paramArrayOfByte.length < paramInt1 + paramInt2 - 1))
      return 0;
    return bytesToshort(paramArrayOfByte[(paramInt1 + 0)], paramArrayOfByte[(paramInt1 + 1)], paramArrayOfByte[(paramInt1 + 2)], paramArrayOfByte[(paramInt1 + 3)], paramInt2);
  }

  public static int bytesToshort(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, int paramInt)
  {
    if (paramInt != 4)
      return 0;
    int i = 0;
    int j = 0;
    if (paramByte4 < 0)
      i = 256 + paramByte4;
    else
      i = paramByte4;
    j += i;
    if (paramByte3 < 0)
      i = 256 + paramByte3;
    else
      i = paramByte3;
    j += (i << 8);
    if (paramByte2 < 0)
      i = 256 + paramByte2;
    else
      i = paramByte2;
    j += (i << 16);
    if (paramByte1 < 0)
      i = 256 + paramByte1;
    else
      i = paramByte1;
    j |= i << 24;
    return j;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.compress.BytesToInt
 * JD-Core Version:    0.6.1
 */