package com.web.form.system;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class BBSForm extends ActionForm {

	private static final long serialVersionUID = -2525209822693962955L;
	protected FormFile uploadFormFile;
	int articleid;
	String title;
	int parentid;
	String filename;
	int personid;
	String operatdatetime;
	String contentstring;
	String oldfilename;
	String mimeType;

	public int getArticleid() {
		return articleid;
	}

	public void setArticleid(int articleid) {
		this.articleid = articleid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getOperatdatetime() {
		return operatdatetime;
	}

	public void setOperatdatetime(String operatdatetime) {
		this.operatdatetime = operatdatetime;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public int getPersonid() {
		return personid;
	}

	public void setPersonid(int personid) {
		this.personid = personid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentstring() {
		return contentstring;
	}

	public void setContentstring(String contentstring) {
		this.contentstring = contentstring;
	}

	public FormFile getUploadFormFile() {
		return uploadFormFile;
	}

	public void setUploadFormFile(FormFile uploadFormFile) {
		this.uploadFormFile = uploadFormFile;
	}

	public String getOldfilename() {
		return oldfilename;
	}

	public void setOldfilename(String oldfilename) {
		this.oldfilename = oldfilename;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
}
