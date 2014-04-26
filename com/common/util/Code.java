package com.common.util;

public class Code {
	private String codeValue;// 代码值
	private String codeName;// 代码名称
	private String codePy;
	private String description;// 描述
	private String otherInfo;
	private boolean leaf;

	/**
		 *
		 */
	public Code() {
		super();
	}

	/**
	 * @return Returns the codeName.
	 */
	public String getCodeName() {
		return codeName;
	}

	/**
	 * @param codeName
	 *            The codeName to set.
	 */
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	/**
	 * @return Returns the codeValue.
	 */
	public String getCodeValue() {
		return codeValue;
	}

	/**
	 * @param codeValue
	 *            The codeValue to set.
	 */
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the codePy.
	 */
	public String getCodePy() {
		return codePy;
	}

	/**
	 * @param codePy
	 *            The codePy to set.
	 */
	public void setCodePy(String codePy) {
		this.codePy = codePy;
	}

	/**
	 * @return Returns the leaf.
	 */
	public boolean isLeaf() {
		return leaf;
	}

	/**
	 * @param leaf
	 *            The leaf to set.
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	/**
	 * @return Returns the otherInfo.
	 */
	public String getOtherInfo() {
		return otherInfo;
	}

	/**
	 * @param otherInfo
	 *            The otherInfo to set.
	 */
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
}
