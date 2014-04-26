package com.zl.message.form;

import java.sql.Date;

import org.apache.struts.action.ActionForm;

public class MessageForm extends ActionForm {
	private String title;
	private String content;
	private Date senddatetime;
	private int SendPersonId;
	private String receivepersonid;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceivepersonid() {
		return receivepersonid;
	}

	public void setReceivepersonid(String receivepersonid) {
		this.receivepersonid = receivepersonid;
	}

	public Date getSenddatetime() {
		return senddatetime;
	}

	public void setSenddatetime(Date senddatetime) {
		this.senddatetime = senddatetime;
	}

	public int getSendPersonId() {
		return SendPersonId;
	}

	public void setSendPersonId(int sendPersonId) {
		SendPersonId = sendPersonId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
