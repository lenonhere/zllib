package com.zl.base.core.compress;

public class ByteOperator
{
  public static byte GetBit(byte paramByte, int paramInt)
  {
    byte b = 1;
    b <<= 7 - paramInt;
    if ((paramByte & b) != 0)
      return 1;
    return 0;
  }

  public static byte SetBit(byte byte0, int i, byte byte1)
  {
      byte byte2 = byte0;
      if(byte1 != 0)
          byte2 |= 1 << 7 - i;
      else
          byte2 &= ~(1 << 7 - i);
      return byte2;
  }
//  public static byte SetBit(byte paramByte1, int paramInt, byte paramByte2)
//  {
//    int i = paramByte1;
//    if (paramByte2 != 0)
//      i = (byte)(i | 1 << 7 - paramInt);
//    else
//      i = (byte)(i & (1 << 7 - paramInt ^ 0xFFFFFFFF));
//    return i;
//  }

  public static byte CopyBits(byte paramByte1, int paramInt1, byte paramByte2, int paramInt2, int paramInt3)
  {
    byte b = 0;
    short s1 = BytesToShort.bytesToshort(b, paramByte1, 2);
    short s2 = BytesToShort.bytesToshort(b, paramByte2, 2);
    short s3 = CopyBitsEx(s1, paramInt1, s2, paramInt2, paramInt3);
    byte[] arrayOfByte = ShortToBytes.shortToBytesWay(s3);
    return arrayOfByte[1];
  }

  public static short CopyBitsEx(short word0, int i, short word1, int j, int k)
  {
      short word2 = word0;
      short word3 = word1;
      word3 <<= j;
      word3 = IntOperator.UpperShortZero(word3);
      word3 >>= 8 - k;
      word3 <<= 8 - k - i;
      word3 = IntOperator.UpperShortZero(word3);
      word2 |= word3;
      short word4 = 255;
      word4 <<= 8 - i;
      word4 = IntOperator.UpperShortZero(word4);
      word3 |= word4;
      word4 = 255;
      word4 >>= i + k;
      word3 |= word4;
      word2 &= word3;
      return word2;
  }
//  public static short CopyBitsEx(short paramShort1, int paramInt1, short paramShort2, int paramInt2, int paramInt3)
//  {
//    int i = paramShort1;
//    int j = paramShort2;
//    j = (short)(j << paramInt2);
//    j = IntOperator.UpperShortZero(j);
//    j = (short)(j >> 8 - paramInt3);
//    j = (short)(j << 8 - paramInt3 - paramInt1);
//    j = IntOperator.UpperShortZero(j);
//    i = (short)(i | j);
//    int k = 255;
//    k = (short)(k << 8 - paramInt1);
//    int m = IntOperator.UpperShortZero(k);
//    j = (short)(j | m);
//    m = 255;
//    m = (short)(m >> paramInt1 + paramInt3);
//    j = (short)(j | m);
//    i = (short)(i & j);
//    return i;
//  }

  public static short CopyBits(short word0, int i, short word1, int j, int k)
  {
      short word2 = word0;
      short word3 = word1;
      word3 <<= j;
      word3 >>= 8 - k;
      word3 <<= 8 - k - i;
      word2 |= word3;
      short word4 = 255;
      word4 <<= 8 - i;
      word3 |= word4;
      word4 = 255;
      word4 >>= i + k;
      word3 |= word4;
      word2 &= word3;
      return word2;
  }
//  public static short CopyBits(short paramShort1, int paramInt1, short paramShort2, int paramInt2, int paramInt3)
//  {
//    int i = paramShort1;
//    int j = paramShort2;
//    j = (short)(j << paramInt2);
//    j = (short)(j >> 8 - paramInt3);
//    j = (short)(j << 8 - paramInt3 - paramInt1);
//    i = (short)(i | j);
//    int k = 255;
//    k = (short)(k << 8 - paramInt1);
//    j = (short)(j | k);
//    k = 255;
//    k = (short)(k >> paramInt1 + paramInt3);
//    j = (short)(j | k);
//    i = (short)(i & j);
//    return i;
//  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.compress.ByteOperator
 * JD-Core Version:    0.6.1
 */