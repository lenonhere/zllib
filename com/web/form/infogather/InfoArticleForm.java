package com.web.form.infogather;

import org.apache.struts.action.ActionForm;

public class InfoArticleForm extends ActionForm{
	
	private String articleId;
	
	private String title;
	
	private String brief;
	
	private String channelId;
	
	private String keyword;
	
	private String[] columnIds;
	
	private String[] columnContents[];
	
	private String infomation;
	
	private String inputDate;
	
	private String publishDate;
	
	private String inputPersonId;
	
	private String publishPersonId;
	
	private String queryType;	
	
	private String status;
	
	private String areacode;
	
	private String areaname;
	
	private String personIdSet;
	
	private String userPwd;
	
	private String msgContent;
	
	private String receivepersonid;
	
	private String receiverName;
	
	private String begindate;
	
	private String enddate;
	
	private String titlecontent;
	

	public String getTitlecontent() {
		return titlecontent;
	}

	public void setTitlecontent(String titlecontent) {
		this.titlecontent = titlecontent;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getReceivepersonid() {
		return receivepersonid;
	}

	public void setReceivepersonid(String receivepersonid) {
		this.receivepersonid = receivepersonid;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String[][] getColumnContents() {
		return columnContents;
	}

	public void setColumnContents(String[][] columnContents) {
		this.columnContents = columnContents;
	}

	public String[] getColumnIds() {
		return columnIds;
	}

	public void setColumnIds(String[] columnIds) {
		this.columnIds = columnIds;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getInputPersonId() {
		return inputPersonId;
	}

	public void setInputPersonId(String inputPersonId) {
		this.inputPersonId = inputPersonId;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getPublishPersonId() {
		return publishPersonId;
	}

	public void setPublishPersonId(String publishPersonId) {
		this.publishPersonId = publishPersonId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInfomation() {
		return infomation;
	}

	public void setInfomation(String infomation) {
		this.infomation = infomation;
	}

	public String getPersonIdSet() {
		return personIdSet;
	}

	public void setPersonIdSet(String personIdSet) {
		this.personIdSet = personIdSet;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
	

}
