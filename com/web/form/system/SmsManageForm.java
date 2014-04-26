package com.web.form.system;

import org.apache.struts.action.ActionForm;

public class SmsManageForm extends ActionForm {

	private static final long serialVersionUID = -4585301100061985809L;
	private String receiverIds;
	private String receiverNames;
	private String mobilePhones;
	private String smsTitle;
	private String smsContent;
	private String msgType;
	private String switchid;

	public String getReceiverNames() {
		return receiverNames;
	}

	public void setReceiverNames(String receiverNames) {
		this.receiverNames = receiverNames;
	}

	public String getSwitchid() {
		return switchid;
	}

	public void setSwitchid(String switchid) {
		this.switchid = switchid;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getSmsTitle() {
		return smsTitle;
	}

	public void setSmsTitle(String smsTitle) {
		this.smsTitle = smsTitle;
	}

	public String getMobilePhones() {
		return mobilePhones;
	}

	public void setMobilePhones(String mobilePhone) {
		this.mobilePhones = mobilePhone;
	}

	public String getReceiverIds() {
		return receiverIds;
	}

	public void setReceiverIds(String receiverIds) {
		this.receiverIds = receiverIds;
	}

	public String getReceiverName() {
		return receiverNames;
	}

	public void setReceiverName(String receiverName) {
		this.receiverNames = receiverName;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	String personIdSet;
	String companyIdSet;
	String msgContent;
	String userPwd;
	String comment;
	String personInfo;
	String menuInfo;
	String settype;
	String groupPersonIds;
	String groupPersonNames;
	String begindate;
	String enddate;
	String rPersonId;
	String msgTime;
	String msgkey;
	String personName;

	public String getCompanyIdSet() {
		return companyIdSet;
	}

	public void setCompanyIdSet(String companyIdSet) {
		this.companyIdSet = companyIdSet;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getMsgkey() {
		return msgkey;
	}

	public void setMsgkey(String msgkey) {
		this.msgkey = msgkey;
	}

	public String getGroupPersonIds() {
		return groupPersonIds;
	}

	public void setGroupPersonIds(String groupPersonIds) {
		this.groupPersonIds = groupPersonIds;
	}

	public String getGroupPersonNames() {
		return groupPersonNames;
	}

	public void setGroupPersonNames(String groupPersonNames) {
		this.groupPersonNames = groupPersonNames;
	}

	public String getSettype() {
		return settype;
	}

	public void setSettype(String settype) {
		this.settype = settype;
	}

	public String getPersonIdSet() {
		return personIdSet;
	}

	public String getPersonInfo() {
		return personInfo;
	}

	public String getMenuInfo() {
		return menuInfo;
	}

	public String getComment() {
		return comment;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setPersonIdSet(String personIdSet) {
		this.personIdSet = personIdSet;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setPersonInfo(String personInfo) {
		this.personInfo = personInfo;
	}

	public void setMenuInfo(String menuInfo) {
		this.menuInfo = menuInfo;
	}

	public void setUserPwd(String pwd) {
		this.userPwd = pwd;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
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

	public String getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}

	public String getRPersonId() {
		return rPersonId;
	}

	public void setRPersonId(String personId) {
		rPersonId = personId;
	}

}
