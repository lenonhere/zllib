package com.web.form;

import org.apache.struts.action.ActionForm;

/**
 * @author: 朱忠南
 * @date: Jun 25, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe:
 * @modify_author:
 * @modify_time:
 */
public class CompanyToInfoForm extends ActionForm {
	private static final long serialVersionUID = 6334445891774337525L;
	/**
	 * 公司ID集合
	 */
	private String compsystids;
	/**
	 * 业务员
	 */
	private String staffname;
	/**
	 * 联系人
	 */
	private String linkman;

	/**
     *
     */
	public CompanyToInfoForm() {
		super();
	}

	/**
	 * @return the compsystids
	 */
	public String getCompsystids() {
		return compsystids;
	}

	/**
	 * @param compsystids
	 *            the compsystids to set
	 */
	public void setCompsystids(String compsystids) {
		this.compsystids = compsystids;
	}

	/**
	 * @return the staffname
	 */
	public String getStaffname() {
		return staffname;
	}

	/**
	 * @param staffname
	 *            the staffname to set
	 */
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	/**
	 * @return the linkman
	 */
	public String getLinkman() {
		return linkman;
	}

	/**
	 * @param linkman
	 *            the linkman to set
	 */
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

}
