package com.zl.base.core.compress;

public class BytesToShort {
	public static short bytesToShortWay(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2) {
		if ((paramInt1 < 0) || (paramInt2 != 2) || (paramArrayOfByte == null)
				|| (paramArrayOfByte.length < paramInt1 + paramInt2 - 1))
			return 0;
		return bytesToshort(paramArrayOfByte[(paramInt1 + 0)],
				paramArrayOfByte[(paramInt1 + 1)], paramInt2);
	}

	public static short bytesInvertToShortWay(byte[] paramArrayOfByte,
			int paramInt1, int paramInt2) {
		if ((paramInt1 < 0) || (paramInt2 != 2) || (paramArrayOfByte == null)
				|| (paramArrayOfByte.length < paramInt1 + paramInt2 - 1))
			return 0;
		return bytesToshort(paramArrayOfByte[(paramInt1 + 1)],
				paramArrayOfByte[(paramInt1 + 0)], paramInt2);
	}

	public static short bytesToshort(byte paramByte1, byte paramByte2,
			int paramInt) {
		if (paramInt != 2)
			return 0;
		int i = 0;
		int j = 0;
		if (paramByte2 < 0)
			i = 256 + paramByte2;
		else
			i = paramByte2;
		j = (short) (j + i);
		if (paramByte1 < 0)
			i = 256 + paramByte1;
		else
			i = paramByte1;
		j = (short) (j + (i << 8));
		// return j;
		return (short) j;
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.compress.BytesToShort JD-Core Version: 0.6.1
 */