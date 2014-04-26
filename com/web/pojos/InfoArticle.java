package com.web.pojos;

import java.io.Serializable;
import java.util.List;

public class InfoArticle  implements Serializable{
	
	private String articleId;
	
	private String shorttitle;
	
	private String title;
	
	private String brief;
	
	private String keyword;
	
	private List columnContents;
	
	private String publish_time;
	
	private String viewcount;

	public String getViewcount() {
		return viewcount;
	}

	public void setViewcount(String viewcount) {
		this.viewcount = viewcount;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public List getColumnContents() {
		return columnContents;
	}

	public void setColumnContents(List columnContents) {
		this.columnContents = columnContents;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShorttitle() {
		return shorttitle;
	}

	public void setShorttitle(String shorttitle) {
		this.shorttitle = shorttitle;
	}
	
	

}
