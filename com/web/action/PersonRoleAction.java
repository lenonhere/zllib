package com.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.form.PermissionAssignForm;
import com.zl.base.core.db.CallHelper;

public class PersonRoleAction extends CriterionAction {

	private static final Log log = LogFactory.getLog(PersonRoleAction.class);
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

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("getDepareAreaPersonTreeAll",
				form, request, false);
		helper.execute();
		request.setAttribute("person.tree", helper.getResult("results"));
		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PermissionAssignForm permForm = (PermissionAssignForm) form;
		String userId = permForm.getUserId();
		CallHelper helper = new CallHelper("PersonRolesTreeQuery");
		helper.setParam("personId", userId);
		helper.execute();
		List results = helper.getResult("results");
		request.setAttribute("personrole.tree", results);
		// RoleMaintainForm maintForm = (RoleMaintainForm) form;
		// String roleId = "1";//maintForm.getRoleId();
		// CallHelper helper = new CallHelper("getPersonTree");
		// helper.setParam("roleId", roleId);
		// helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
		// request.setAttribute("message", "Just message!!!!");
		return mapping.findForward("show");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("savePersonRole", form,
				request, false);
		helper.execute();
		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));
		this.show(mapping, form, request, response);
		return mapping.findForward("show");
	}
}
