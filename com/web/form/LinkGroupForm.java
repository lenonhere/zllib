package com.web.form;

import org.apache.struts.action.ActionForm;

public class LinkGroupForm extends ActionForm{
	
	private String personId;
	
	private String linkPersonIdSet;
	
	private String unlinkPersonIdSet;
	
	private String  switchid;
	
	

	public String getSwitchid() {
		return switchid;
	}

	public void setSwitchid(String switchid) {
		this.switchid = switchid;
	}

	public String getUnlinkPersonIdSet() {
		return unlinkPersonIdSet;
	}

	public void setUnlinkPersonIdSet(String unlinkPersonIdSet) {
		this.unlinkPersonIdSet = unlinkPersonIdSet;
	}

	public String getLinkPersonIdSet() {
		return linkPersonIdSet;
	}

	public void setLinkPersonIdSet(String linkPersonIdSet) {
		this.linkPersonIdSet = linkPersonIdSet;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	

}
