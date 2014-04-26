package com.web.form.infogather;

import org.apache.struts.action.ActionForm;

public class InfoChannelForm extends ActionForm{
	
	private String personid;
	
	private String channelId;
	
	private String channelName;
	
	private String sortOrder;
   
	private String parentId;
	
	private String isActive;
	
	private String isOptional;
	
	private String cycledays;
	
	private String rpt_templateId;
	
	private String view_templateid;
	
	private String parentName;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getCycledays() {
		return cycledays;
	}

	public void setCycledays(String cycledays) {
		this.cycledays = cycledays;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsOptional() {
		return isOptional;
	}

	public void setIsOptional(String isOptional) {
		this.isOptional = isOptional;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRpt_templateId() {
		return rpt_templateId;
	}

	public void setRpt_templateId(String rpt_templateId) {
		this.rpt_templateId = rpt_templateId;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getView_templateid() {
		return view_templateid;
	}

	public void setView_templateid(String view_templateid) {
		this.view_templateid = view_templateid;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}
	
	
	
}
