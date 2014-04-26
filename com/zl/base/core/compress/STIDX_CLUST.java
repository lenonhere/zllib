package com.zl.base.core.compress;

public class STIDX_CLUST
{
  public static final int _MAX_WINDOW_SIZE = 65536;
  public static final int _SORT_TABLE_SIZE = 65536;
  public byte[] pWnd = null;
  public int nWndSize = 0;
  public int HeapPos = 1;
  public int[] SortTable = new int[65536];
  public STIDXNODE[] SortHeap = new STIDXNODE[65536];
  int CurByte = 0;
  int CurBit = 0;
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.compress.STIDX_CLUST
 * JD-Core Version:    0.6.1
 */