package com.zl.message.pojos;

import java.io.Serializable;

public class MessageFileVO implements Serializable {

	private static final long serialVersionUID = 2354138675733217889L;

	private String fileId;

	private String fileName;

	private String fileSaveName;

	private String fileType;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSaveName() {
		return fileSaveName;
	}

	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
