package com.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ImFileForm extends ActionForm {

	private static final long serialVersionUID = 504671983518333335L;

	protected FormFile sendFile;

	private String filename;

	private String receiverIds;

	private String receiverNames;

	private String pageNo;

	private String pageSize;

	public String getFilename() {

		return filename;

	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public FormFile getSendFile() {

		return sendFile;

	}

	public void setSendFile(FormFile sendFile) {
		System.out.println("开始测试");
		this.sendFile = sendFile;
		System.out.println("测试结束");
	}

	public String getReceiverIds() {

		return receiverIds;

	}

	public void setReceiverIds(String receiverIds) {

		this.receiverIds = receiverIds;

	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getReceiverNames() {
		return receiverNames;
	}

	public void setReceiverNames(String receiverNames) {
		this.receiverNames = receiverNames;
	}

}
