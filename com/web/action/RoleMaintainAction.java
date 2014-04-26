package com.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.form.RoleMaintainForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.TradeList;

public class RoleMaintainAction extends CriterionAction {

	private static final Log log = LogFactory.getLog(RoleMaintainAction.class);
	protected static final String NO_ROLE_AVAILABLE_YET = "-1";
	protected static final String MAINT_TYPE_ADD = "1";
	protected static final String MAINT_TYPE_MODIFY = "2";
	protected static final String MAINT_TYPE_DELETE = "3";
	protected static final String MAINT_TYPE_GET = "4";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {
			log.error(e);
			return mapping.findForward("error");
		}
	}

	public ActionForward ccimInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RoleMaintainForm maintForm = (RoleMaintainForm) form;

		CallHelper helper = new CallHelper("maintRole");
		helper.setParam("maintType", MAINT_TYPE_GET);
		helper.execute();

		List results = helper.getResult("results");
		request.setAttribute("role.list", results);

		if (results != null && results.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) results.get(0);
			maintForm.setRoleId(bean.get("roleid").toString());
		} else {
			maintForm.setRoleId(NO_ROLE_AVAILABLE_YET);
		}

		return mapping.findForward("ccimInit");
	}

	public ActionForward ccimShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("maintRole", form, request,
				false);
		helper.setParam("maintType", MAINT_TYPE_GET);
		helper.execute();

		setPersonTree(form, request);
		setMenuTreeCCIM(form, request);

		return mapping.findForward("ccimShow");
	}

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RoleMaintainForm maintForm = (RoleMaintainForm) form;

		CallHelper helper = new CallHelper("maintRole");
		helper.setParam("maintType", MAINT_TYPE_GET);
		helper.execute();

		List results = helper.getResult("results");
		request.setAttribute("role.list", results);

		if (results != null && results.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) results.get(0);
			maintForm.setRoleId(bean.get("roleid").toString());
		} else {
			maintForm.setRoleId(NO_ROLE_AVAILABLE_YET);
		}

		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("maintRole", form, request,
				false);
		helper.setParam("maintType", MAINT_TYPE_GET);
		helper.execute();

		setPersonTree(form, request);
		setMenuTree(form, request);

		return mapping.findForward("show");
	}

	public ActionForward ccimSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("saveRoleRightCCIM", form,
				request, false);
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		RoleMaintainForm rform = (RoleMaintainForm) form;
		// 记录保存角色人员及权限系统操作日志
		String personId = getPersonId(request);
		String ip = request.getRemoteHost();
		String roleid = rform.getRoleId();
		TradeList.operLog(personId, 99030104, roleid, ip);

		return ccimShow(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("add");
	}

	public ActionForward saveadd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("maintRole", form, request,
				false);
		helper.setParam("maintType", MAINT_TYPE_ADD);
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		// 记录角色新增系统操作日志
		String personId = getPersonId(request);
		String ip = request.getRemoteHost();
		String addroleid = (String) helper.getOutput("addroleid");
		TradeList.operLog(personId, 99030101, addroleid, ip);

		return mapping.findForward("saveadd");
	}

	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("maintRole", form, request,
				false);
		helper.setParam("maintType", MAINT_TYPE_GET);
		helper.execute();

		copyProperties(form, helper.getResult("results"), 0, true, true);

		return mapping.findForward("modify");
	}

	public ActionForward savemodify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("maintRole", form, request,
				false);
		helper.setParam("maintType", MAINT_TYPE_MODIFY);
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		// 记录角色修改系统操作日志
		String personId = getPersonId(request);
		String ip = request.getRemoteHost();
		String addroleid = (String) helper.getOutput("addroleid");
		TradeList.operLog(personId, 99030102, addroleid, ip);

		return mapping.findForward("savemodify");
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("maintRole", form, request,
				false);
		helper.setParam("maintType", MAINT_TYPE_DELETE);
		helper.execute();

		// 记录角色删除系统操作日志
		String personId = getPersonId(request);
		String ip = request.getRemoteHost();
		String addroleid = (String) helper.getOutput("addroleid");
		TradeList.operLog(personId, 99030103, addroleid, ip);

		return init(mapping, form, request, response);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("saveRoleRight", form,
				request, false);
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		RoleMaintainForm rform = (RoleMaintainForm) form;
		// 记录保存角色人员及权限系统操作日志
		String personId = getPersonId(request);
		String ip = request.getRemoteHost();
		String roleid = rform.getRoleId();
		TradeList.operLog(personId, 99030104, roleid, ip);

		return show(mapping, form, request, response);
	}

	protected void setPersonTree(ActionForm form, HttpServletRequest request) {
		RoleMaintainForm maintForm = (RoleMaintainForm) form;

		String roleId = maintForm.getRoleId();

		CallHelper helper = new CallHelper("getPersonQueryByRole");
		helper.setParam("roleId", roleId);
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
	}

	protected void setMenuTree(ActionForm form, HttpServletRequest request) {
		RoleMaintainForm maintForm = (RoleMaintainForm) form;

		String roleId = maintForm.getRoleId();

		CallHelper helper = new CallHelper("getMenuTreeRole");
		helper.setParam("roleId", roleId);
		helper.execute();

		request.setAttribute("menu.tree", helper.getResult("results"));
	}

	protected void setMenuTreeCCIM(ActionForm form, HttpServletRequest request) {
		RoleMaintainForm maintForm = (RoleMaintainForm) form;

		String roleId = maintForm.getRoleId();

		CallHelper helper = new CallHelper("getMenuTreeRoleCCIM");
		helper.setParam("roleId", roleId);
		helper.execute();

		request.setAttribute("menu.tree", helper.getResult("results"));
	}

	/**
	 * 角色人员信息查询 11.01.14.am by cdd
	 * */
	public ActionForward queryInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleMaintainForm maintForm = (RoleMaintainForm) form;

		CallHelper helper = initializeCallHelper("gRoleTreeQuery", maintForm,
				request, false);
		helper.execute();
		request.setAttribute("role.tree", helper.getResult("results"));
		return mapping.findForward("queryInit");
	}

	public ActionForward queryShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleMaintainForm maintForm = (RoleMaintainForm) form;

		CallHelper helper = initializeCallHelper("getPersonInfoByRole",
				maintForm, request, false);
		helper.execute();
		request.setAttribute("caption.list", helper.getResult("captions"));
		request.setAttribute("result.list", helper.getResult("results"));

		return mapping.findForward("queryShow");
	}

}
