package com.zl.base.core.compress;

public class CBaseSCParse {
	public static final int NO_SRC_FILE = -1;
	public static final int NO_DEST_FILE = -2;
	public static final int SRC_FILE_LONG = -3;
	public static final int DEST_FILE_LONG = -4;
	public static final int PACK_INVALID = -5;
	public static final int MEMORY_OUT = -6;
	public static final int FORCE_STOP = -9;

	public static void _InitSortTable(byte abyte0[], int i,
			STIDX_CLUST stidx_clust) {
		stidx_clust.pWnd = new byte[i];
		for (int j = 0; j < i; j++)
			stidx_clust.pWnd[j] = abyte0[j];

		for (int k = 0; k < 0x10000; k++)
			stidx_clust.SortTable[k] = 0;

		for (int l = 0; l < 0x10000; l++) {
			STIDXNODE stidxnode = new STIDXNODE();
			stidx_clust.SortHeap[l] = stidxnode;
		}

		stidx_clust.CurByte = 0;
		stidx_clust.CurBit = 0;
		stidx_clust.nWndSize = 0;
		stidx_clust.HeapPos = 1;
	}

	// public static void _InitSortTable(byte[] paramArrayOfByte, int paramInt,
	// STIDX_CLUST paramSTIDX_CLUST) {
	// paramSTIDX_CLUST.pWnd = new byte[paramInt];
	// for (int i = 0; i < paramInt; i++)
	// paramSTIDX_CLUST.pWnd[i] = paramArrayOfByte[i];
	// for (i = 0; i < 65536; i++)
	// paramSTIDX_CLUST.SortTable[i] = 0;
	// for (i = 0; i < 65536; i++) {
	// STIDXNODE localSTIDXNODE = new STIDXNODE();
	// paramSTIDX_CLUST.SortHeap[i] = localSTIDXNODE;
	// }
	// paramSTIDX_CLUST.CurByte = 0;
	// paramSTIDX_CLUST.CurBit = 0;
	// paramSTIDX_CLUST.nWndSize = 0;
	// paramSTIDX_CLUST.HeapPos = 1;
	// }

	public static void _MovePos(int paramInt, STIDX_CLUST paramSTIDX_CLUST) {
		paramInt += paramSTIDX_CLUST.CurBit;
		paramSTIDX_CLUST.CurByte += paramInt / 8;
		paramSTIDX_CLUST.CurBit = (paramInt % 8);
	}

	public static void _InsertIndexItem(int paramInt,
			STIDX_CLUST paramSTIDX_CLUST) {
		int i = 0;
		int j = BytesToShort.bytesToshort((byte) 0,
				paramSTIDX_CLUST.pWnd[paramInt], 2);
		int k = BytesToShort.bytesToshort((byte) 0,
				paramSTIDX_CLUST.pWnd[(paramInt + 1)], 2);
		if (j != k) {
			i = paramSTIDX_CLUST.HeapPos;
			paramSTIDX_CLUST.HeapPos += 1;
			paramSTIDX_CLUST.SortHeap[i].off1 = paramInt;
			paramSTIDX_CLUST.SortHeap[i].next = paramSTIDX_CLUST.SortTable[(j * 256 + k)];
			paramSTIDX_CLUST.SortTable[(j * 256 + k)] = i;
		} else {
			i = paramSTIDX_CLUST.SortTable[(j * 256 + k)];
			if ((i != 0) && (paramInt == paramSTIDX_CLUST.SortHeap[i].off2 + 1)) {
				paramSTIDX_CLUST.SortHeap[i].off2 = paramInt;
			} else {
				i = paramSTIDX_CLUST.HeapPos;
				paramSTIDX_CLUST.HeapPos += 1;
				paramSTIDX_CLUST.SortHeap[i].off1 = paramInt;
				paramSTIDX_CLUST.SortHeap[i].off2 = paramInt;
				paramSTIDX_CLUST.SortHeap[i].next = paramSTIDX_CLUST.SortTable[(j * 256 + k)];
				paramSTIDX_CLUST.SortTable[(j * 256 + k)] = i;
			}
		}
	}

	public static void _ScrollWindow(int paramInt, STIDX_CLUST paramSTIDX_CLUST) {
		for (int i = 0; i < paramInt; i++) {
			paramSTIDX_CLUST.nWndSize += 1;
			if (paramSTIDX_CLUST.nWndSize > 1)
				_InsertIndexItem(paramSTIDX_CLUST.nWndSize - 2,
						paramSTIDX_CLUST);
		}
	}

	public static int _GetSameLen(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2, int paramInt3, STIDX_CLUST paramSTIDX_CLUST) {
		int i = 2;
		int j = Math.min(paramInt1 - paramInt2, paramSTIDX_CLUST.nWndSize
				- paramInt3);
		while ((i < j)
				&& (paramArrayOfByte[(paramInt2 + i)] == paramSTIDX_CLUST.pWnd[(paramInt3 + i)]))
			i++;
		return i;
	}

	public static SeekResult _SeekPhase(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2, STIDX_CLUST paramSTIDX_CLUST) {
		SeekResult localSeekResult = new SeekResult();
		if (paramInt2 < paramInt1 - 1) {
			int m = BytesToShort.bytesToshort((byte) 0,
					paramArrayOfByte[paramInt2], 2);
			int n = BytesToShort.bytesToshort((byte) 0,
					paramArrayOfByte[(paramInt2 + 1)], 2);
			int i1 = paramSTIDX_CLUST.SortTable[(m * 256 + n)];
			if (i1 != 0) {
				int j = 2;
				int k = paramSTIDX_CLUST.SortHeap[i1].off1;
				while (i1 != 0) {
					int i = _GetSameLen(paramArrayOfByte, paramInt1, paramInt2,
							paramSTIDX_CLUST.SortHeap[i1].off1,
							paramSTIDX_CLUST);
					if (i > j) {
						j = i;
						k = paramSTIDX_CLUST.SortHeap[i1].off1;
					}
					i1 = paramSTIDX_CLUST.SortHeap[i1].next;
				}
				localSeekResult.offset = k;
				localSeekResult.len = j;
				localSeekResult.bRet = true;
				return localSeekResult;
			}
		}
		localSeekResult.bRet = false;
		return localSeekResult;
	}

	public static void _CopyBytes(byte[] paramArrayOfByte1, int paramInt1,
			int paramInt2, byte[] paramArrayOfByte2, int paramInt3,
			int paramInt4, int paramInt5) {
		int i = 0;
		int j = 0;
		int k = 0;
		int m = paramInt4;
		int n = 0;
		int i1 = 0;
		while (paramInt5 > 0) {
			int i2;
			if (i != 0)
				i2 = 8;
			else
				i2 = 8 - paramInt2;
			n = Math.min(paramInt5, i2);
			if (i != 0)
				j = 0;
			else
				j = paramInt2;
			i1 = Math.min(n, 8 - m);
			paramArrayOfByte1[(i + paramInt1)] = ByteOperator.CopyBits(
					paramArrayOfByte1[(i + paramInt1)], j,
					paramArrayOfByte2[(k + paramInt3)], m, i1);
			if (n > i1) {
				k++;
				m = 0;
				j += i1;
				paramArrayOfByte1[(i + paramInt1)] = ByteOperator.CopyBits(
						paramArrayOfByte1[(i + paramInt1)], j,
						paramArrayOfByte2[(k + paramInt3)], m, n - i1);
				m += n - i1;
			} else {
				m += i1;
				if (m >= 8) {
					k++;
					m = 0;
				}
			}
			paramInt5 -= n;
			i++;
		}
	}

	public static void _OutCode(byte abyte0[], int i, int j, boolean flag,
			STIDX_CLUST stidx_clust) {
		if (flag) {
			int j1 = i - 1;
			int k1 = IntOperator.LowerLog2(j1);
			if (k1 > 0) {
				byte byte0 = -1;
				byte abyte1[] = IntToBytes.intToBytesWay(byte0);
				_CopyBytes(abyte0, stidx_clust.CurByte, stidx_clust.CurBit,
						abyte1, 0, 0, k1);
				_MovePos(k1, stidx_clust);
			}
			int l = 0;
			byte abyte2[] = IntToBytes.intToBytesWay(l);
			_CopyBytes(abyte0, stidx_clust.CurByte, stidx_clust.CurBit, abyte2,
					3, 7, 1);
			_MovePos(1, stidx_clust);
			if (k1 > 0) {
				int l1 = 1;
				l1 <<= k1;
				int i1 = j1 - l1;
				byte abyte3[] = IntToBytes.intToBytesWay(i1);
				_CopyBytes(abyte0, stidx_clust.CurByte, stidx_clust.CurBit,
						abyte3, (32 - k1) / 8, (32 - k1) % 8, k1);
				_MovePos(k1, stidx_clust);
			}
		} else {
			int k = i;
			byte abyte4[] = IntToBytes.intToBytesWay(k);
			_CopyBytes(abyte0, stidx_clust.CurByte, stidx_clust.CurBit, abyte4,
					(32 - j) / 8, (32 - j) % 8, j);
			_MovePos(j, stidx_clust);
		}
	}

	// public static void _OutCode(byte[] paramArrayOfByte, int paramInt1,
	// int paramInt2, boolean paramBoolean, STIDX_CLUST paramSTIDX_CLUST) {
	// if (paramBoolean) {
	// int k = paramInt1 - 1;
	// int m = IntOperator.LowerLog2(k);
	// if (m > 0) {
	// j = -1;
	// arrayOfByte1 = IntToBytes.intToBytesWay(j);
	// _CopyBytes(paramArrayOfByte, paramSTIDX_CLUST.CurByte,
	// paramSTIDX_CLUST.CurBit, arrayOfByte1, 0, 0, m);
	// _MovePos(m, paramSTIDX_CLUST);
	// }
	// int j = 0;
	// byte[] arrayOfByte1 = IntToBytes.intToBytesWay(j);
	// _CopyBytes(paramArrayOfByte, paramSTIDX_CLUST.CurByte,
	// paramSTIDX_CLUST.CurBit, arrayOfByte1, 3, 7, 1);
	// _MovePos(1, paramSTIDX_CLUST);
	// if (m > 0) {
	// int n = 1;
	// n <<= m;
	// j = k - n;
	// arrayOfByte1 = IntToBytes.intToBytesWay(j);
	// _CopyBytes(paramArrayOfByte, paramSTIDX_CLUST.CurByte,
	// paramSTIDX_CLUST.CurBit, arrayOfByte1, (32 - m) / 8,
	// (32 - m) % 8, m);
	// _MovePos(m, paramSTIDX_CLUST);
	// }
	// } else {
	// int i = paramInt1;
	// byte[] arrayOfByte2 = IntToBytes.intToBytesWay(i);
	// _CopyBytes(paramArrayOfByte, paramSTIDX_CLUST.CurByte,
	// paramSTIDX_CLUST.CurBit, arrayOfByte2,
	// (32 - paramInt2) / 8, (32 - paramInt2) % 8, paramInt2);
	// _MovePos(paramInt2, paramSTIDX_CLUST);
	// }
	// }

	public static int CompressProcess(byte[] paramArrayOfByte1, int paramInt,
			byte[] paramArrayOfByte2) {
		if (paramInt > 65536)
			return -1;
		STIDX_CLUST localSTIDX_CLUST = new STIDX_CLUST();
		_InitSortTable(paramArrayOfByte1, paramInt, localSTIDX_CLUST);
		try {
			for (int i = 0; i < paramInt; i++) {
				if (localSTIDX_CLUST.CurByte >= paramInt)
					return 0;
				SeekResult localSeekResult = _SeekPhase(paramArrayOfByte1,
						paramInt, i, localSTIDX_CLUST);
				if (localSeekResult.bRet) {
					_OutCode(paramArrayOfByte2, 1, 1, false, localSTIDX_CLUST);
					_OutCode(paramArrayOfByte2, localSeekResult.len, 0, true,
							localSTIDX_CLUST);
					_OutCode(paramArrayOfByte2, localSeekResult.offset,
							IntOperator.UpperLog2(localSTIDX_CLUST.nWndSize),
							false, localSTIDX_CLUST);
					_ScrollWindow(localSeekResult.len, localSTIDX_CLUST);
					i += localSeekResult.len - 1;
				} else {
					_OutCode(paramArrayOfByte2, 0, 1, false, localSTIDX_CLUST);
					_OutCode(paramArrayOfByte2,
							IntOperator.ByteToInt(paramArrayOfByte1[i]), 8,
							false, localSTIDX_CLUST);
					_ScrollWindow(1, localSTIDX_CLUST);
				}
			}
		} catch (Exception localException) {
			return 0;
		}
		int j = localSTIDX_CLUST.CurByte;
		if (localSTIDX_CLUST.CurBit != 0)
			j++;
		if (j >= paramInt)
			return 0;
		return j;
	}

	public static boolean DecompressProcess(byte abyte0[], int i, byte abyte1[]) {
		if (i > 0x10000)
			return false;
		STIDX_CLUST stidx_clust = new STIDX_CLUST();
		_InitSortTable(abyte0, i, stidx_clust);
		int j = 0;
		for (int k = 0; k < i; k++) {
			byte byte0 = ByteOperator.GetBit(abyte1[stidx_clust.CurByte],
					stidx_clust.CurBit);
			_MovePos(1, stidx_clust);
			if (byte0 == 0) {
				_CopyBytes(abyte0, k, 0, abyte1, stidx_clust.CurByte,
						stidx_clust.CurBit, 8);
				_MovePos(8, stidx_clust);
				stidx_clust.nWndSize++;
			} else {
				int l = -1;
				while (byte0 != 0) {
					l++;
					byte0 = ByteOperator.GetBit(abyte1[stidx_clust.CurByte],
							stidx_clust.CurBit);
					_MovePos(1, stidx_clust);
				}
				int i1 = 0;
				int j1 = 0;
				if (l > 0) {
					byte abyte2[] = IntToBytes.intToBytesWay(0);
					_CopyBytes(abyte2, (32 - l) / 8, (32 - l) % 8, abyte1,
							stidx_clust.CurByte, stidx_clust.CurBit, l);
					_MovePos(l, stidx_clust);
					i1 = BytesToInt.bytesToShortWay(abyte2, 0, 4);
					j1 = 1;
					j1 <<= l;
					j1 += i1;
					j1++;
				} else {
					j1 = 2;
				}
				byte abyte3[] = IntToBytes.intToBytesWay(0);
				int k1 = IntOperator.UpperLog2(stidx_clust.nWndSize);
				_CopyBytes(abyte3, (32 - k1) / 8, (32 - k1) % 8, abyte1,
						stidx_clust.CurByte, stidx_clust.CurBit, k1);
				_MovePos(k1, stidx_clust);
				i1 = BytesToInt.bytesToShortWay(abyte3, 0, 4);
				int l1 = i1;
				for (int i2 = 0; i2 < j1; i2++)
					abyte0[k + i2] = abyte0[j + l1 + i2];

				stidx_clust.nWndSize += j1;
				k += j1 - 1;
			}
			if (stidx_clust.nWndSize > 0x10000) {
				j = stidx_clust.nWndSize - 0x10000;
				stidx_clust.nWndSize = 0x10000;
			}
		}

		return true;
	}

	// public static boolean DecompressProcess(byte[] paramArrayOfByte1,
	// int paramInt, byte[] paramArrayOfByte2) {
	// if (paramInt > 65536)
	// return false;
	// STIDX_CLUST localSTIDX_CLUST = new STIDX_CLUST();
	// _InitSortTable(paramArrayOfByte1, paramInt, localSTIDX_CLUST);
	// int i = 0;
	// for (int j = 0; j < paramInt; j++) {
	// int k = ByteOperator.GetBit(
	// paramArrayOfByte2[localSTIDX_CLUST.CurByte],
	// localSTIDX_CLUST.CurBit);
	// _MovePos(1, localSTIDX_CLUST);
	// if (k == 0) {
	// _CopyBytes(paramArrayOfByte1, j, 0, paramArrayOfByte2,
	// localSTIDX_CLUST.CurByte, localSTIDX_CLUST.CurBit, 8);
	// _MovePos(8, localSTIDX_CLUST);
	// localSTIDX_CLUST.nWndSize += 1;
	// } else {
	// int m = -1;
	// while (k != 0) {
	// m++;
	// k = ByteOperator.GetBit(
	// paramArrayOfByte2[localSTIDX_CLUST.CurByte],
	// localSTIDX_CLUST.CurBit);
	// _MovePos(1, localSTIDX_CLUST);
	// }
	// int n = 0;
	// int i1 = 0;
	// if (m > 0) {
	// arrayOfByte = IntToBytes.intToBytesWay(0);
	// _CopyBytes(arrayOfByte, (32 - m) / 8, (32 - m) % 8,
	// paramArrayOfByte2, localSTIDX_CLUST.CurByte,
	// localSTIDX_CLUST.CurBit, m);
	// _MovePos(m, localSTIDX_CLUST);
	// n = BytesToInt.bytesToShortWay(arrayOfByte, 0, 4);
	// i1 = 1;
	// i1 <<= m;
	// i1 += n;
	// i1++;
	// } else {
	// i1 = 2;
	// }
	// byte[] arrayOfByte = IntToBytes.intToBytesWay(0);
	// int i2 = IntOperator.UpperLog2(localSTIDX_CLUST.nWndSize);
	// _CopyBytes(arrayOfByte, (32 - i2) / 8, (32 - i2) % 8,
	// paramArrayOfByte2, localSTIDX_CLUST.CurByte,
	// localSTIDX_CLUST.CurBit, i2);
	// _MovePos(i2, localSTIDX_CLUST);
	// n = BytesToInt.bytesToShortWay(arrayOfByte, 0, 4);
	// int i3 = n;
	// for (int i4 = 0; i4 < i1; i4++)
	// paramArrayOfByte1[(j + i4)] = paramArrayOfByte1[(i + i3 + i4)];
	// localSTIDX_CLUST.nWndSize += i1;
	// j += i1 - 1;
	// }
	// if (localSTIDX_CLUST.nWndSize > 65536) {
	// i = localSTIDX_CLUST.nWndSize - 65536;
	// localSTIDX_CLUST.nWndSize = 65536;
	// }
	// }
	// return true;
	// }

	public static void CopyMemory(byte[] paramArrayOfByte1, int paramInt1,
			byte[] paramArrayOfByte2, int paramInt2, int paramInt3) {
		for (int i = 0; i < paramInt3; i++)
			paramArrayOfByte1[(paramInt1 + i)] = paramArrayOfByte2[(paramInt2 + i)];
	}

	public static int Compress(byte[] paramArrayOfByte1, int paramInt,
			byte[] paramArrayOfByte2) {
		int i = 0;
		int j = 0;
		int k = 0;
		byte[] arrayOfByte1 = new byte[65536];
		byte[] arrayOfByte2 = new byte[65552];
		int m = 0;
		int n = 0;
		int i1 = paramInt;
		int i2 = 0;
		while ((i1 > 0) && (i == 0)) {
			i2 = Math.min(65536, i1);
			CopyMemory(arrayOfByte1, 0, paramArrayOfByte1, k, i2);
			i1 -= i2;
			k += i2;
			if (i2 == 65536)
				m = 0;
			else
				m = i2;
			byte[] arrayOfByte3 = IntToBytes.intToBytesWay(m);
			CopyMemory(paramArrayOfByte2, j, arrayOfByte3, 3, 1);
			j++;
			CopyMemory(paramArrayOfByte2, j, arrayOfByte3, 2, 1);
			j++;
			int i3 = CompressProcess(arrayOfByte1, i2, arrayOfByte2);
			if (i3 == 0) {
				n = m;
				arrayOfByte3 = IntToBytes.intToBytesWay(n);
				CopyMemory(paramArrayOfByte2, j, arrayOfByte3, 3, 1);
				j++;
				CopyMemory(paramArrayOfByte2, j, arrayOfByte3, 2, 1);
				j++;
				CopyMemory(paramArrayOfByte2, j, arrayOfByte1, 0, i2);
				j += i2;
			} else {
				n = i3;
				arrayOfByte3 = IntToBytes.intToBytesWay(n);
				CopyMemory(paramArrayOfByte2, j, arrayOfByte3, 3, 1);
				j++;
				CopyMemory(paramArrayOfByte2, j, arrayOfByte3, 2, 1);
				j++;
				CopyMemory(paramArrayOfByte2, j, arrayOfByte2, 0, i3);
				j += i3;
			}
		}
		i = j;
		return i;
	}

	public static int DCompress(byte[] paramArrayOfByte1, int paramInt,
			byte[] paramArrayOfByte2) {
		int i = 0;
		int j = 0;
		int k = 0;
		byte[] arrayOfByte1 = new byte[65536];
		byte[] arrayOfByte2 = new byte[65552];
		int m = 0;
		int n = 0;
		int i1 = 0;
		int i2 = 0;
		m = paramInt;
		while ((m > 0) && (i == 0)) {
			byte[] arrayOfByte3 = new byte[4];
			byte[] arrayOfByte4 = new byte[2];
			CopyMemory(arrayOfByte4, 0, paramArrayOfByte1, k, 2);
			k += 2;
			arrayOfByte3[0] = 0;
			arrayOfByte3[1] = 0;
			arrayOfByte3[2] = arrayOfByte4[1];
			arrayOfByte3[3] = arrayOfByte4[0];
			i1 = BytesToInt.bytesToShortWay(arrayOfByte3, 0, 4);
			CopyMemory(arrayOfByte4, 0, paramArrayOfByte1, k, 2);
			k += 2;
			arrayOfByte3[0] = 0;
			arrayOfByte3[1] = 0;
			arrayOfByte3[2] = arrayOfByte4[1];
			arrayOfByte3[3] = arrayOfByte4[0];
			i2 = BytesToInt.bytesToShortWay(arrayOfByte3, 0, 4);
			m -= 4;
			if (i1 == 0)
				n = 65536;
			else
				n = i1;
			if (i2 != 0)
				m -= i2;
			else
				m -= n;
			if (i2 == i1) {
				CopyMemory(arrayOfByte1, 0, paramArrayOfByte1, k, n);
				k += n;
			} else {
				CopyMemory(arrayOfByte2, 0, paramArrayOfByte1, k, i2);
				k += i2;
				if (!DecompressProcess(arrayOfByte1, n, arrayOfByte2))
					i = -5;
			}
			CopyMemory(paramArrayOfByte2, j, arrayOfByte1, 0, n);
			j += n;
		}
		if (i == 0)
			i = j;
		return i;
	}

	public static int OriginLendth(byte[] paramArrayOfByte, int paramInt) {
		int i = 0;
		int j = 0;
		int k = 0;
		int m = 0;
		int n = 0;
		int i1 = 0;
		int i2 = 0;
		m = paramInt;
		while ((m > 0) && (i == 0)) {
			byte[] arrayOfByte1 = new byte[4];
			byte[] arrayOfByte2 = new byte[2];
			CopyMemory(arrayOfByte2, 0, paramArrayOfByte, k, 2);
			k += 2;
			arrayOfByte1[0] = 0;
			arrayOfByte1[1] = 0;
			arrayOfByte1[2] = arrayOfByte2[1];
			arrayOfByte1[3] = arrayOfByte2[0];
			i1 = BytesToInt.bytesToShortWay(arrayOfByte1, 0, 4);
			CopyMemory(arrayOfByte2, 0, paramArrayOfByte, k, 2);
			k += 2;
			arrayOfByte1[0] = 0;
			arrayOfByte1[1] = 0;
			arrayOfByte1[2] = arrayOfByte2[1];
			arrayOfByte1[3] = arrayOfByte2[0];
			i2 = BytesToInt.bytesToShortWay(arrayOfByte1, 0, 4);
			m -= 4;
			if (i1 == 0)
				n = 65536;
			else
				n = i1;
			if (i2 != 0)
				m -= i2;
			else
				m -= n;
			if (i2 == i1)
				k += n;
			else
				k += i2;
			j += n;
		}
		i = j;
		return i;
	}

	public static class SeekResult {
		public boolean bRet = false;
		public int offset = 0;
		public int len = 0;
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.compress.CBaseSCParse JD-Core Version: 0.6.1
 */