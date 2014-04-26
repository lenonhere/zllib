package com.zl.base.core.taglib.html;

import javax.servlet.jsp.JspException;

import com.common.tackle.UserView;
import com.zl.base.core.db.CallHelper;
import com.zl.common.Constants;


/**
 * 公司树标签
 *
 * @author jokin
 *
 */
public class CompanyTreeTag extends BaseTree {

	private static final long serialVersionUID = -4916357815596915372L;
	/**
	 * 结果collection的atrribute名
	 */
	public static final String COLLECTION_NAME = "companyTreeData";
	/**
	 * 节点ID
	 */
	public static final String NODE_ID = "code";
	/**
	 * 节点类型
	 */
	public static final String NODE_TYPE = "ifcompany";
	/**
	 * 节点文字
	 */
	public static final String NODE_TEXT = "shortname";
	/**
	 * 父节点ID
	 */
	public static final String NODE_PARENTID = "parentcode";

	/**
	 * 根区域代码,默认全国
	 */
	private String areaCode = "999999";
	/**
	 * 是否使用权限，true or false 或 1 or 0
	 */
	private String useRight = "true";
	/**
	 * 公司级别，1：市公司，2：县级公司
	 */
	private String type = "1";

	public CompanyTreeTag() {
		super();
	}

	public int doStartTag() throws JspException {
		setCollection();// 将数据放到pageContext对象中
		return super.doStartTag();
	}

	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	private void setCollection() {
		CallHelper helper = new CallHelper("getAreaCompanyTree");
		String personId = getPersonId();
		if (this.useRight.toLowerCase().equals("false")
				|| this.useRight.equals("0"))
			personId = "1";// 如果不是用权限，则用管理员id
		helper.setParam("userId", personId);
		helper.setParam("areaCode", this.areaCode);
		helper.setParam("type", this.type);
		helper.execute();
		super.setcollection(COLLECTION_NAME);
		super.setnodeid(NODE_ID);
		super.setnodetype(NODE_TYPE);
		super.setnodetext(NODE_TEXT);
		super.setnodeparentid(NODE_PARENTID);
		super.setshowroot(false);// 不显示根节点
		pageContext.setAttribute(COLLECTION_NAME, helper.getResult(0));
	}

	private UserView getUser() {
		return (UserView) pageContext.getSession().getAttribute(
				Constants.SESSION_USER);
	}

	private String getPersonId() {
		return getUser().getPersonId();
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getUseRight() {
		return useRight;
	}

	public void setUseRight(String useRight) {
		this.useRight = useRight;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
