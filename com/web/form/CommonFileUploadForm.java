package com.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class CommonFileUploadForm extends ActionForm {
	
	protected FormFile uploadFormFile;

	String filename;

	public FormFile getuploadFormFile() {
		return uploadFormFile;
	}

	public void setuploadFormFile(FormFile uploadFormFile) {
		this.uploadFormFile = uploadFormFile;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
