package com.web.form.infogather;

import org.apache.struts.action.ActionForm;

public class InfoChannelAuthenForm extends ActionForm{
	
	private String userId;
	
	private String channelid;
	
	private String infomation;
	
	private String queryType;
	
	private String type;	
	
	private String personName;
	
	/**
     * @return the personName
     */
    public String getPersonName() {
    	return personName;
    }

	/**
     * @param personName the personName to set
     */
    public void setPersonName(String personName) {
    	this.personName = personName;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public String getInfomation() {
		return infomation;
	}

	public void setInfomation(String infomation) {
		this.infomation = infomation;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
