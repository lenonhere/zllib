/**
 * @author: 朱忠南
 * @date: Feb 19, 2009
 * @company: 杭州州力数据科技有限公司
 * @desribe:
 * @modify_author:
 * @modify_time:
 */
package com.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author jokin
 * @date Feb 19, 2009
 */
public class FileUploadForm extends ActionForm {
	private FormFile file;
	private String newFileName;
	private String oldFileName;
	private String fileSuffix;
	private String fileSize;// 单位kb
	private String allowMaxFileSize;
	private String allowTotalFileSize;
	private String allowMultiFiles;
	private String allowFileSuffix;
	private String message;
	private String isActive;
	private String isDelete;
	private String downCount;
	private String uploadTime;
	private String userId;
	private String userName;

	/**
	 * @return the file
	 */
	public FormFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(FormFile file) {
		this.file = file;
	}

	/**
	 * @return the newFileName
	 */
	public String getNewFileName() {
		return newFileName;
	}

	/**
	 * @param newFileName
	 *            the newFileName to set
	 */
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	/**
	 * @return the oldFileName
	 */
	public String getOldFileName() {
		return oldFileName;
	}

	/**
	 * @param oldFileName
	 *            the oldFileName to set
	 */
	public void setOldFileName(String oldFileName) {
		this.oldFileName = oldFileName;
	}

	/**
	 * @return the fileSuffix
	 */
	public String getFileSuffix() {
		return fileSuffix;
	}

	/**
	 * @param fileSuffix
	 *            the fileSuffix to set
	 */
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	/**
	 * @return the allowMaxFileSize
	 */
	public String getAllowMaxFileSize() {
		return allowMaxFileSize;
	}

	/**
	 * @param allowMaxFileSize
	 *            the allowMaxFileSize to set
	 */
	public void setAllowMaxFileSize(String allowMaxFileSize) {
		this.allowMaxFileSize = allowMaxFileSize;
	}

	/**
	 * @return the allowTotalFileSize
	 */
	public String getAllowTotalFileSize() {
		return allowTotalFileSize;
	}

	/**
	 * @param allowTotalFileSize
	 *            the allowTotalFileSize to set
	 */
	public void setAllowTotalFileSize(String allowTotalFileSize) {
		this.allowTotalFileSize = allowTotalFileSize;
	}

	/**
	 * @return the allowMultiFiles
	 */
	public String getAllowMultiFiles() {
		return allowMultiFiles;
	}

	/**
	 * @param allowMultiFiles
	 *            the allowMultiFiles to set
	 */
	public void setAllowMultiFiles(String allowMultiFiles) {
		this.allowMultiFiles = allowMultiFiles;
	}

	/**
	 * @return the allowFileSuffix
	 */
	public String getAllowFileSuffix() {
		return allowFileSuffix;
	}

	/**
	 * @param allowFileSuffix
	 *            the allowFileSuffix to set
	 */
	public void setAllowFileSuffix(String allowFileSuffix) {
		this.allowFileSuffix = allowFileSuffix;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the fileSize
	 */
	public String getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize
	 *            the fileSize to set
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the isDelete
	 */
	public String getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete
	 *            the isDelete to set
	 */
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the downCount
	 */
	public String getDownCount() {
		return downCount;
	}

	/**
	 * @param downCount
	 *            the downCount to set
	 */
	public void setDownCount(String downCount) {
		this.downCount = downCount;
	}

	/**
	 * @return the uploadTime
	 */
	public String getUploadTime() {
		return uploadTime;
	}

	/**
	 * @param uploadTime
	 *            the uploadTime to set
	 */
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
