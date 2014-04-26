package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zl.base.core.db.CallHelper;

public class PersonRightQueryAction extends CriterionAction {

	private static Log log = LogFactory.getLog(PersonRightQueryAction.class);

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

	/**
	 * 单人权限查询
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("getPersonRightMenuList",
				form, request, false);
		helper.execute();

		request.setAttribute("rightcaption.list", helper.getResult("captions"));
		request.setAttribute("personright.list", helper.getResult("results"));
		return mapping.findForward("show");
	}

	/**
	 * 多人权限查询
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initMulti(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("initMulti");
	}

	/**
	 * 人员树
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showPersonTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String personName = request.getParameter("personName");
		if (personName == null)
			personName = "";
		CallHelper helper = initializeCallHelper("getDepareAreaPersonTreeAll",
				form, request, false);
		helper.setParam("personName", personName.trim());
		helper.execute();

		request.setAttribute("personName", personName);
		request.setAttribute("person.tree", helper.getResult("results"));
		return mapping.findForward("showPersonTree");
	}

	/**
	 * 菜单树
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showMenuTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("getMenuRightTree", form,
				request, false);
		helper.execute();

		request.setAttribute("menu.tree", helper.getResult("results"));
		return mapping.findForward("showMenuTree");
	}

	/**
	 * 人员树
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showMenuPeople(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("getMenuRightPerson", form,
				request, false);
		helper.execute();
		request.setAttribute("caption.list", helper.getResult("captions"));
		request.setAttribute("result.list", helper.getResult("results"));
		return mapping.findForward("showMenuPeople");
	}

	/**
	 * 展示人员权限
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showMulti(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper(
				"getMultiPersonRightMenuJoint", form, request, false);
		helper.execute();

		request.setAttribute("rightcaption.list", helper.getResult("captions"));
		request.setAttribute("personright.list", helper.getResult("results"));
		return mapping.findForward("showMulti");
	}

	/**
	 * 显示角色树
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showRoleTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("maintRole", form, request,
				false);
		helper.setParam("maintType", "4");
		helper.execute();

		request.setAttribute("role.tree", helper.getResult("results"));
		return mapping.findForward("showRoleTree");
	}

	/**
	 * 显示角色权限
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showMultiRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("getMultiRoleRightMenuJoint",
				form, request, false);
		helper.execute();

		request.setAttribute("rightcaption.list", helper.getResult("captions"));
		request.setAttribute("personright.list", helper.getResult("results"));
		return mapping.findForward("showMulti");
	}
}
