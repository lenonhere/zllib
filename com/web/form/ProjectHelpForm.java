package com.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ProjectHelpForm extends ActionForm {

	public ProjectHelpForm() {
		super();
	}

	private FormFile uploadFormFile0;
	private FormFile uploadFormFile1;
	private FormFile uploadFormFile2;
	private FormFile uploadFormFile3;
	private FormFile uploadFormFile4;

	private String id;
	private String projectid;
	private String projectname;
	private String departid;
	private String departname;
	private String name;
	private String year;
	private String type;
	private String state;
	private String saveType;
	private String showType;
	private String pageState;
	private String workFlowId;
	private String information;

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartid() {
		return departid;
	}

	public void setDepartid(String departid) {
		this.departid = departid;
	}

	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getWorkFlowId() {
		return workFlowId;
	}

	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}

	public String getPageState() {
		return pageState;
	}

	public void setPageState(String pageState) {
		this.pageState = pageState;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public FormFile getUploadFormFile0() {
		return uploadFormFile0;
	}

	public void setUploadFormFile0(FormFile uploadFormFile0) {
		this.uploadFormFile0 = uploadFormFile0;
	}

	public FormFile getUploadFormFile1() {
		return uploadFormFile1;
	}

	public void setUploadFormFile1(FormFile uploadFormFile1) {
		this.uploadFormFile1 = uploadFormFile1;
	}

	public FormFile getUploadFormFile2() {
		return uploadFormFile2;
	}

	public void setUploadFormFile2(FormFile uploadFormFile2) {
		this.uploadFormFile2 = uploadFormFile2;
	}

	public FormFile getUploadFormFile3() {
		return uploadFormFile3;
	}

	public void setUploadFormFile3(FormFile uploadFormFile3) {
		this.uploadFormFile3 = uploadFormFile3;
	}

	public FormFile getUploadFormFile4() {
		return uploadFormFile4;
	}

	public void setUploadFormFile4(FormFile uploadFormFile4) {
		this.uploadFormFile4 = uploadFormFile4;
	}

}
