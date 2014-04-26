package com.common;

import org.apache.log4j.Logger;

public class Pagination {
	private static final Logger logger = Logger.getLogger(Pagination.class);
	private int curPage;// 当前是第几页
	private int rowsPerPage;// 每页有多少记录
	private int maxPage;// 一共有多少页

	public Pagination() {

	}

	public Pagination(int curPage, int rowsPerPage, int maxPage) {
		this.curPage = curPage;
		this.rowsPerPage = rowsPerPage;
		this.maxPage = maxPage;
	}

	// 设定当前页面值
	public void setCurPage(String page) {
		try {
			if (page == null) {
				curPage = 1;
			} else {
				curPage = Integer.parseInt(page);
			}
		} catch (Exception e) {
			logger.error("request page is not invalid!", e);
		}
	}

	// 取得当前页面值
	public int getCurPage() {
		return curPage;
	}

	// 设定每页显示的记录数
	public void setRowsPerPage(int rows) {
		rowsPerPage = rows;
	}

	// 取得每页显示的记录数
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	// 设定最大页数
	public void setMaxPage(int totalRows) {
		maxPage = (totalRows + rowsPerPage - 1) / rowsPerPage;
	}

	// 取得最大页数
	public int getMaxPage() {
		return maxPage;
	}
}
