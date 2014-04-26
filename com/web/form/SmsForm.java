package com.web.form;

import org.apache.struts.action.ActionForm;

public class SmsForm extends ActionForm{
	
	private String receiverIds;
	
	private String receiverNames ;
	
	private String mobilePhones;
	
	private String smsTitle;
	
	private String smsContent;
	
	private String msgType;
	
	private String  switchid;

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
	
	
	

}
