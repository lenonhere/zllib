package com.zl.opt.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zl.base.core.db.CallHelper;
import com.zl.core.HTDispatchAction;
import com.zl.util.MyBeanUtil;
import com.zl.util.OptionUtils;

public class PersonRoleRightAction extends HTDispatchAction {

	private static Log log = LogFactory.getLog(PersonRoleRightAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {
			log.error(e);
			return mapping.findForward("error");
		}
	}

	public ActionForward PersonInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		CallHelper helper = this.initializeCallHelper("G_PersonList", pform, request, false);
		helper.setParam("personId", "0");
		helper.execute();
		request.setAttribute("captions", helper.getResult("captions"));
		request.setAttribute("results", helper.getResult("results"));
		request.setAttribute("departList", this.getDepart(pform));
		// log.debug(this.getDepart());
		return mapping.findForward("PersonInit");
	}

	public ActionForward RoleInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		CallHelper helper = this.initializeCallHelper("G_RoleList", pform, request, false);
		helper.setParam("roleId", "0");
		helper.execute();
		request.setAttribute("captions", helper.getResult("captions"));
		request.setAttribute("results", helper.getResult("results"));
		return mapping.findForward("RoleInit");
	}

	public ActionForward RolePersonInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		request.setAttribute("roleTree", this.getRoleTree(pform));
		return mapping.findForward("RolePersonInit");
	}

	public ActionForward PersonRoleInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		request.setAttribute("personTree", this.getPersonTree(pform));
		return mapping.findForward("PersonRoleInit");
	}

	public ActionForward PersonShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		if (!"0".equals(pform.getPersonId())) {
			CallHelper helper = initializeCallHelper("G_PersonList", form, request, false);
			helper.execute();
			List rs = helper.getResult("results");
			if (rs != null && rs.size() > 0) {
				BasicDynaBean bean = (BasicDynaBean) rs.get(0);
				MyBeanUtil.copyFormbeanFromDBbean(pform, bean);
			}
		}
		request.setAttribute("departList", this.getDepart(pform));
		request.setAttribute("form", pform);
		request.setAttribute("isActiveList", OptionUtils.getIsOrNot(false));

		return mapping.findForward("PersonShow");
	}

	public ActionForward PersonSave(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		PersonForm pform = (PersonForm) form;
		log.debug("pform.getDepartId()=" + pform.getDepartId());
		CallHelper helper = initializeCallHelper("G_PersonSave", form, request, false);
		helper.execute();
		request.setAttribute("msgCode", helper.getOutput("msgCode"));
		request.setAttribute("message", helper.getOutput("message"));
		request.setAttribute("isActiveList", new ArrayList());
		request.setAttribute("departList", new ArrayList());
		request.setAttribute("reloadFlag", "1");
		request.setAttribute("closeFlag", "1");
		return mapping.findForward("PersonShow");
	}

	public ActionForward RoleShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		log.debug("pform.getRoleId()=" + pform.getRoleId());
		if (!"0".equals(pform.getRoleId())) {
			CallHelper helper = initializeCallHelper("G_RoleList", form, request, false);
			helper.execute();
			List rs = helper.getResult("results");
			if (rs != null && rs.size() > 0) {
				BasicDynaBean bean = (BasicDynaBean) rs.get(0);
				MyBeanUtil.copyFormbeanFromDBbean(pform, bean);
			}
		}
		request.setAttribute("form", pform);
		request.setAttribute("isActiveList", OptionUtils.getIsOrNot(false));

		return mapping.findForward("RoleShow");
	}

	public ActionForward RoleSave(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("G_RoleSave", form, request, false);
		helper.execute();
		request.setAttribute("msgCode", helper.getOutput("msgCode"));
		request.setAttribute("message", helper.getOutput("message"));
		request.setAttribute("isActiveList", new ArrayList());
		request.setAttribute("reloadFlag", "1");
		request.setAttribute("closeFlag", "1");

		return mapping.findForward("RoleShow");
	}

	public ActionForward PersonRoleShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		String personId = pform.getPersonId();
		CallHelper helper = this.initializeCallHelper("G_RoleByPersonTree", pform, request, false);
		// helper.setParam("personId", personId);
		helper.execute();

		request.setAttribute("roleTree", helper.getResult("results"));

		return mapping.findForward("PersonRoleShow");
	}

	public ActionForward RolePersonShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		PersonForm pform = (PersonForm) form;
		CallHelper helper = this.initializeCallHelper("G_PersonByRoleTree", pform, request, false);
		helper.execute();
		request.setAttribute("personTree", helper.getResult("results"));
		return mapping.findForward("RolePersonShow");
	}

	public ActionForward RolePersonSave(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		PersonForm pform = (PersonForm) form;
		CallHelper helper = this.initializeCallHelper("G_RolePersonSave", pform, request, false);
		helper.execute();
		return this.RolePersonShow(mapping, pform, request, response);
	}

	public ActionForward PersonRoleSave(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String personId = this.getPersonId(request);
		CallHelper helper = initializeCallHelper("G_PersonRoleSave", form, request, false);
		helper.setParam("userId", personId);
		helper.execute();
		request.setAttribute("msgCode", String.valueOf(helper.getState()));
		request.setAttribute("message", helper.getOutput("message"));
		return this.PersonRoleShow(mapping, form, request, response);
	}

	protected List getPersonTree(ActionForm form) {
		CallHelper helper = new CallHelper("G_PersonTree");
		helper.setParam("personId", "0");
		helper.setParam("departId", "0");
		helper.execute();
		return helper.getResult("results");
	}

	protected List getRoleTree(ActionForm form) {
		CallHelper helper = new CallHelper("G_RoleList");
		helper.setParam("roleId", "0");
		helper.execute();
		return helper.getResult("results");
	}

	protected List getMenuTree(ActionForm form) {
		PersonForm pform = (PersonForm) form;
		String personId = pform.getPersonId();
		CallHelper helper = new CallHelper("G_MenuByRightTree");
		helper.setParam("rightId", personId);
		helper.setParam("typeId", "2");
		helper.execute();
		return helper.getResult("results");
	}

	protected List getMenuTreeWithRight(ActionForm form) {
		PersonForm pform = (PersonForm) form;
		CallHelper helper = new CallHelper("getMenuTreeWithRight");
		if ("1".equals(pform.getTypeId())) {
			helper.setParam("rightId", pform.getRoleId());
		} else if ("2".equals(pform.getTypeId())) {
			helper.setParam("rightId", pform.getPersonId());
		}
		helper.setParam("typeId", pform.getTypeId());
		helper.execute();
		return helper.getResult("results");
	}

	public ActionForward DepartInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		CallHelper helper = new CallHelper("G_DepartList");
		helper.setParam("userId", this.getPersonId(request));
		helper.setParam("departId", "0");
		helper.execute();
		request.setAttribute("captions", helper.getResult("captions"));
		request.setAttribute("results", helper.getResult("results"));
		return mapping.findForward("DepartInit");
	}

	protected List getDepart(ActionForm form) {
		CallHelper helper = new CallHelper("G_DepartList");
		helper.setParam("userId", "0");
		helper.setParam("departId", "0");
		helper.execute();
		return helper.getResult("results");

	}

	public ActionForward DepartSave(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		PersonForm pform = (PersonForm) form;
		// String personId = this.getPersonId(request);
		String personId = pform.getPersonId();
		log.debug("departId=" + pform.getDepartId());
		CallHelper helper = initializeCallHelper("G_DepartSave", form, request, false);
		helper.setParam("userId", personId);
		helper.execute();
		request.setAttribute("msgCode", helper.getOutput("msgCode"));
		request.setAttribute("message", helper.getOutput("message"));
		request.setAttribute("departList", new ArrayList());
		request.setAttribute("isActiveList", new ArrayList());
		request.setAttribute("reloadFlag", "1");
		request.setAttribute("closeFlag", "1");
		return mapping.findForward("DepartShow");
	}

	public ActionForward DepartShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		if (!"0".equals(pform.getDepartId())) {
			CallHelper helper = initializeCallHelper("G_DepartList", form, request, false);
			helper.execute();
			List rs = helper.getResult("results");
			if (rs != null && rs.size() > 0) {
				BasicDynaBean bean = (BasicDynaBean) rs.get(0);
				MyBeanUtil.copyFormbeanFromDBbean(pform, bean);
			}
		}
		request.setAttribute("departList", this.getDepart(pform));
		log.debug("pform.departId=" + pform.getDepartId());
		request.setAttribute("form", pform);
		request.setAttribute("isActiveList", OptionUtils.getIsOrNot(false));
		return mapping.findForward("DepartShow");
	}

	public ActionForward RightInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		String typeId = pform.getTypeId();
		if (typeId == null || typeId.trim().length() == 0) {
			pform.setTypeId("1");
		}
		request.setAttribute("roleTree", this.getRoleTree(pform));
		request.setAttribute("personTree", this.getPersonTree(pform));
		request.setAttribute("form", pform);
		return mapping.findForward("RightInit");
	}

	public ActionForward RightShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		CallHelper helper = initializeCallHelper("G_MenuByRightTree", form, request, false);
		helper.setParam("userId", this.getPersonId(request));
		if ("1".equals(pform.getTypeId())) {
			helper.setParam("rightId", pform.getRoleId());
		} else if ("2".equals(pform.getTypeId())) {
			helper.setParam("rightId", pform.getPersonId());
		}
		helper.execute();
		request.setAttribute("msgCode", String.valueOf(helper.getState()));
		request.setAttribute("message", helper.getOutput("message"));
		request.setAttribute("menuTree", helper.getResult(0));
		return mapping.findForward("RightShow");
	}

	public ActionForward RightSave(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		CallHelper helper = initializeCallHelper("G_RightMenuSave", form, request, false);
		helper.setParam("userId", this.getPersonId(request));
		if ("1".equals(pform.getTypeId())) {
			helper.setParam("rightId", pform.getRoleId());
		} else if ("2".equals(pform.getTypeId())) {
			helper.setParam("rightId", pform.getPersonId());
		}
		// log.debug("getMenuCode==>"+pform.getMenuCode());
		helper.execute();
		// log.debug("message==>"+helper.getOutput("message"));
		request.setAttribute("msgCode", String.valueOf(helper.getState()));
		request.setAttribute("message", helper.getOutput("message"));
		return this.RightShow(mapping, pform, request, response);
	}

	public ActionForward MenuRightInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		request.setAttribute("menuTree", this.getMenuTree(pform));
		return mapping.findForward("MenuRightInit");
	}

	public ActionForward MenuRightSave(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		CallHelper helper = initializeCallHelper("G_MenuRightSave", form, request, false);
		helper.setParam("userId", this.getPersonId(request));
		if ("1".equals(pform.getTypeId())) {
			helper.setParam("rightId", pform.getRoleId());
		} else if ("2".equals(pform.getTypeId())) {
			helper.setParam("rightId", pform.getPersonId());
		}
		helper.execute(); 
		request.setAttribute("msgCode", String.valueOf(helper.getState()));
		request.setAttribute("message", helper.getOutput("message"));
		return this.MenuRightShow(mapping, pform, request, response);
	}

	public ActionForward MenuRightShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PersonForm pform = (PersonForm) form;
		String typeId = pform.getTypeId();
		if (typeId == null || typeId.trim().length() == 0) {
			pform.setTypeId("1");
		}
		CallHelper helper = initializeCallHelper("G_RoleByMenuTree", form, request, false);
		helper.execute();
		request.setAttribute("roleTree", helper.getResult(0));
		CallHelper helper2 = initializeCallHelper("G_PersonByMenuTree", form, request, false);
		helper2.execute();
		request.setAttribute("personTree", helper2.getResult(0)); 
		request.setAttribute("form", pform); 
		return mapping.findForward("MenuRightShow");
	}
}
