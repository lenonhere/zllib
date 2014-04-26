package com.zl.base.core.compress;

public abstract interface Compressor
{
  public abstract int fnComFile(String paramString1, String paramString2);

  public abstract int fnDecFile(String paramString1, String paramString2);

  public abstract int Compress(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  public abstract int DeCompress(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.compress.Compressor
 * JD-Core Version:    0.6.1
 */