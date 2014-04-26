package com.zl.common;

import java.util.ArrayList;

public class PageControl {
	private int curPage;
	private int maxPage;
	private int maxRowCount;
	private int rowsPerPage = 10;
	private ArrayList result;
	public static final int PAGESIZE = 10;
	// -----Condition Begin
	private int start = -1;
	private int end = -1;
	private String Order = null;
	private String Group = null;

	public void setStart(int paramInt) {
		this.start = paramInt;
	}

	public void setEnd(int paramInt) {
		this.end = paramInt;
	}

	public int getStart() {
		return this.start;
	}

	public int getEnd() {
		return this.end;
	}

	public void setOrder(String paramString) {
		this.Order = paramString;
	}

	public void setGroup(String paramString) {
		this.Group = paramString;
	}

	public String getOrder() {
		return this.Order;
	}

	public String getGroup() {
		return this.Group;
	}

	// -----Condition End

	public PageControl(int paramInt, ArrayList paramArrayList) {
		this.maxRowCount = paramInt;
		this.result = paramArrayList;
		countMaxPage();
	}

	public PageControl(int paramInt1, ArrayList paramArrayList, int paramInt2,
			int paramInt3) {
		this.maxRowCount = paramInt1;
		this.result = paramArrayList;
		this.curPage = paramInt2;
		this.rowsPerPage = paramInt3;
		countMaxPage();
	}

	public PageControl() {
	}

	public void countMaxPage() {
		if (this.maxRowCount % this.rowsPerPage == 0)
			this.maxPage = (this.maxRowCount / this.rowsPerPage);
		else
			this.maxPage = (this.maxRowCount / this.rowsPerPage + 1);
	}

	public int getCurPage() {
		return this.curPage;
	}

	public int getMaxPage() {
		return this.maxPage;
	}

	public int getMaxRowCount() {
		return this.maxRowCount;
	}

	public ArrayList getResult() {
		return this.result;
	}

	public int getRowsPerPage() {
		return this.rowsPerPage;
	}

	public void setCurPage(int paramInt) {
		this.curPage = paramInt;
	}

	public void setMaxPage(int paramInt) {
		this.maxPage = paramInt;
	}

	public void setMaxRowCount(int paramInt) {
		this.maxRowCount = paramInt;
	}

	public void setResult(ArrayList paramArrayList) {
		this.result = paramArrayList;
	}

	public void setRowsPerPage(int paramInt) {
		this.rowsPerPage = paramInt;
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name: com.zl.base.core.PageControl
 * JD-Core Version: 0.6.1
 */