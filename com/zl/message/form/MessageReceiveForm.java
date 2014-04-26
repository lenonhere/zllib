package com.zl.message.form;

import org.apache.struts.action.ActionForm;

public class MessageReceiveForm extends ActionForm {

	private Integer pageNo;

	private Integer pageSize;

	private String msgId;

	private String updateType;

	private String title;

	private String content;

	private String receivepersonid;

	private String startDate;

	private String endDate;

	private String state;

	private String searchType;

	private String keywords;

	private String[] msgids;

	private String receiverName;

	private String sendPersonId;

	private String box;// 1：收件箱

	public String getBox() {
		return box;
	}

	public void setBox(String box) {
		this.box = box;
	}

	public String getSendPersonId() {
		return sendPersonId;
	}

	public void setSendPersonId(String sendPersonId) {
		this.sendPersonId = sendPersonId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String[] getMsgids() {
		return msgids;
	}

	public void setMsgids(String[] msgids) {
		this.msgids = msgids;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getReceivepersonid() {
		return receivepersonid;
	}

	public void setReceivepersonid(String receivepersonid) {
		this.receivepersonid = receivepersonid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

}
