package com.common.tackle;

import java.util.*;

public class UserView {

	String sessionId;
    String personId;
    String personName;
    String departId;
    String departName;
    String corpId;
    String corpName;
    String personCode;
    String password;
    String idcode;
    List roleCodes = new ArrayList();

    public UserView() {}

    public String getCorpId() {
        return corpId;
    }

    public String getCorpName() {
        return corpName;
    }

    public String getDepartId() {
        return departId;
    }

    public String getDepartName() {
        return departName;
    }

    public String getPersonId() {
        return personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public void setRoleCodes(List roleCodes) {
        this.roleCodes = roleCodes;
    }

    public List getRoleCodes() {
        return roleCodes;
    }

    public void addRoleCode(Object roleCode) {
        this.roleCodes.add(roleCode);
    }

    public boolean isMemberOf(Object roleCode) {
        if(this.roleCodes == null) {
            return false;
        }
        return this.roleCodes.contains(roleCode);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[UserView >>> ");
        buffer.append("personId=");
        buffer.append(personId);
        buffer.append(", personName=");
        buffer.append(personName);
        buffer.append(", departId=");
        buffer.append(departId);
        buffer.append(", idcode=");
        buffer.append(idcode);
        buffer.append(", departName=");
        buffer.append(departName);
        buffer.append(", corpId=");
        buffer.append(corpId);
        buffer.append(", corpName=");
        buffer.append(corpName);
        buffer.append("]");

        return buffer.toString();
    }

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getIdcode() {
		return idcode;
	}

	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}
}
