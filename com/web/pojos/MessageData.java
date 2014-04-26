package com.web.pojos;

import java.io.Serializable;

public class MessageData implements Serializable{

	private String msgId;
	
	private String receiveId;
	
	private String msgType;
	
	private String senderName;
	
	private String receiverName;
	
	private String messageContent;
	
	private String messageTitle;
	
	private String sendTime;
	
	private String msgTypeName;
	
	

	public String getMsgTypeName() {
		return msgTypeName;
	}


	public void setMsgTypeName(String msgTypeName) {
		this.msgTypeName = msgTypeName;
	}


	public String getMessageContent() {
		return messageContent;
	}

	
	public String getReceiveId() {
		return receiveId;
	}



	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}



	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	
	
	
}
