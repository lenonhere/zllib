package com.web.pojos;

import java.io.Serializable;
import java.util.List;

public class InfoChannel implements Serializable{
	
	private String channelName;
	
	private List articleList;
	
	private String channelId;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public List getArticleList() {
		return articleList;
	}

	public void setArticleList(List articleList) {
		this.articleList = articleList;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	

}
