package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.form.PermissionAssignForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.TradeList;

public class PermissionAssignAction extends CriterionAction {

	private static Log log = LogFactory.getLog(PermissionAssignAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return mapping.findForward("error");
		}
	}

	public ActionForward ccimInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		setPersonTree(form, request);
		return mapping.findForward("ccimInit");
	}

	public ActionForward ccimShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		setMenuTreeCCIM(form, request);
		return mapping.findForward("ccimShow");
	}

	public ActionForward ccimSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("savePersonRightCCIM", form,
				request, false);
		helper.setParam("giverId", this.getPersonId(request));
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		setMenuTreeCCIM(form, request);

		// 记录保存人员权限设置系统操作日志
		PermissionAssignForm dynaForm = (PermissionAssignForm) form;
		String personId = getPersonId(request);
		String ip = request.getRemoteHost();
		String userId = dynaForm.getUserId();
		TradeList.operLog(personId, 99030201, userId, ip);

		return mapping.findForward("ccimShow");
	}

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		setPersonTree(form, request);
		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		setMenuTree(form, request);
		return mapping.findForward("show");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("savePersonRight", form,
				request, false);
		helper.setParam("giverId", this.getPersonId(request));
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		setMenuTree(form, request);

		// 记录保存人员权限设置系统操作日志
		PermissionAssignForm dynaForm = (PermissionAssignForm) form;
		String personId = getPersonId(request);
		String ip = request.getRemoteHost();
		String userId = dynaForm.getUserId();
		TradeList.operLog(personId, 99030201, userId, ip);

		return mapping.findForward("show");
	}

	protected void setPersonTree(ActionForm form, HttpServletRequest request) {

		// CallHelper helper = new CallHelper("getPersonTree");
		// helper.execute();

		CallHelper helper = initializeCallHelper("getDepareAreaPersonTreeAll",
				form, request, false);
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
	}

	protected void setMenuTree(ActionForm form, HttpServletRequest request) {
		PermissionAssignForm permForm = (PermissionAssignForm) form;

		String userId = permForm.getUserId();

		CallHelper helper = new CallHelper("getMenuTreePerson");
		helper.setParam("userId", userId);
		helper.execute();

		request.setAttribute("menu.tree", helper.getResult("results"));
	}

	protected void setMenuTreeCCIM(ActionForm form, HttpServletRequest request) {
		PermissionAssignForm permForm = (PermissionAssignForm) form;

		String userId = permForm.getUserId();

		CallHelper helper = new CallHelper("getMenuTreePersonCCIM");
		helper.setParam("userId", userId);
		helper.execute();

		request.setAttribute("menu.tree", helper.getResult("results"));
	}
}
