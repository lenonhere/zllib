package com.web.form.business.daily;

import org.apache.struts.action.ActionForm;

public class ExpensesClaimForm extends ActionForm {
	private static final long serialVersionUID = -8456162432717653744L;
	private String date = "";
	private String beginDate = "";
	private String endDate = "";
	private String expensesid = "";
	private String crtdate = "";
	private String authorid = "";
	private String userId = "";
	private String personname = "";
	private String departid = "";
	private String receivepersonid = "";
	private String receivepersonname = "";
	private String auditorid = "";
	private String auditor = "";
	private String audit = "";
	private String auditdate = "";
	private String checkinfo = "";
	private String auditinfo = "";
	private String memoid = "";
	private String memo = "";
	private String money = "";
	private String infomation = "";
	private String querytype = "";

	public String getInfomation() {
		return infomation;
	}

	public void setInfomation(String infomation) {
		this.infomation = infomation;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public String getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(String auditdate) {
		this.auditdate = auditdate;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getAuditorid() {
		return auditorid;
	}

	public void setAuditorid(String auditorid) {
		this.auditorid = auditorid;
	}

	public String getAuthorid() {
		return authorid;
	}

	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}

	public String getCrtdate() {
		return crtdate;
	}

	public void setCrtdate(String crtdate) {
		this.crtdate = crtdate;
	}

	public String getDepartid() {
		return departid;
	}

	public void setDepartid(String departid) {
		this.departid = departid;
	}

	public String getExpensesid() {
		return expensesid;
	}

	public void setExpensesid(String expensesid) {
		this.expensesid = expensesid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMemoid() {
		return memoid;
	}

	public void setMemoid(String memoid) {
		this.memoid = memoid;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReceivepersonid() {
		return receivepersonid;
	}

	public void setReceivepersonid(String receivepersonid) {
		this.receivepersonid = receivepersonid;
	}

	public String getReceivepersonname() {
		return receivepersonname;
	}

	public void setReceivepersonname(String receivepersonname) {
		this.receivepersonname = receivepersonname;
	}

	public String getCheckinfo() {
		return checkinfo;
	}

	public void setCheckinfo(String checkinfo) {
		this.checkinfo = checkinfo;
	}

	public String getAuditinfo() {
		return auditinfo;
	}

	public void setAuditinfo(String auditinfo) {
		this.auditinfo = auditinfo;
	}

	public String getQuerytype() {
		return querytype;
	}

	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}

}
