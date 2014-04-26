package com.zl.opt.system;

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

public class MenuAction extends HTDispatchAction {

	private static Log log = LogFactory.getLog(MenuAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {
			log.error(e);
			return mapping.findForward("error");
		}
	}

	// ��ȡѡ�е��¼��˵�
	public ActionForward queryright(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		switchMenu(request.getParameter("menucode"), request);

		return mapping.findForward("query");
	}

	// ��ȡѡ�е��¼��˵�
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		switchMenu(request.getParameter("menucode"), request);

		return mapping.findForward("query");
	}

	public ActionForward defaultQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper helper = new CallHelper("getModulesByUser");
		helper.setParam("personId", getPersonId(request));
		helper.execute();

		List codeList = helper.getResult("results");

		if (codeList.size() > 0) {
			BasicDynaBean rowBean = (BasicDynaBean) codeList.get(0);
			switchMenu(rowBean.get("menucode"), request);
		}
		// request.setAttribute("personName", getPersonName(request));

		return mapping.findForward("query");
	}

	public void switchMenu(Object menuCode, HttpServletRequest request) {

		CallHelper helper = new CallHelper("getMenuQuery");
		helper.setParam("menuCode", menuCode);
		helper.setParam("personId", getPersonId(request));
		helper.execute();

		request.setAttribute("menu.list", helper.getResult("results"));
		// request.setAttribute("personName", getPersonName(request));
	}

	public ActionForward SystemMenuAddLink(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setSystemMenuTree(form, request);
		return mapping.findForward("SystemMenuSub");
	}

	public ActionForward SystemMenuInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		setSystemMenuTree(form, request);
		return mapping.findForward("SystemMenuInit");
	}

	public ActionForward SystemMenuSave(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		MenuForm aForm = (MenuForm) form;
		String showType = (String) request.getParameter("showType");
		String procName = "";

		if ("1".equals(showType)) {// AddLink
			procName = "MenuInfoSave";
		} else if ("2".equals(showType)) {// AddSub
			procName = "MenuInfoLink";
		} else if ("3".equals(showType)) {// Move
			procName = "MenuInfoMove";
		} else if ("4".equals(showType)) {// Delete
			procName = "MenuInfoDelete";
		} else {
			request.setAttribute("message", "����״̬");
			request.setAttribute("closeFlag", "0");
			// return mapping.findForward("SystemMenuShow");
		}
		if (procName.length() > 0) {
			CallHelper helper = initializeCallHelper(procName, form, request, false);
			helper.setParam("personid", this.getPersonId(request));
			//helper.setParam("parentcode", aForm.getParentCode());
			//helper.setParam("ismenu", aForm.getIsMenu());
			//helper.setParam("isactive", aForm.getIsActive());
			//helper.setParam("operatesn", aForm.getOperatesn());
			//helper.setParam("description", aForm.getDescription());
			helper.execute();
			request.setAttribute("message", (String) helper.getOutput("message"));
			request.setAttribute("reloadFlag", "1");
			request.setAttribute("type", "");
			request.setAttribute("closeFlag", "0");
		}

		request.setAttribute("isList", OptionUtils.getIsOrNot(false));
		return mapping.findForward("SystemMenuShow");
		// return SystemMenuShow(mapping, form, request, response);
	}

	public ActionForward SystemMenuShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("MenuInfoGet", form, request, false);
		helper.execute();
		List list = helper.getResult(0);
		// ȡ���صĽ��(ȡ���ؼ�¼�ĵ�i����¼)
		String type = (String)request.getParameter("type");
		int i = 0;
		MenuForm aForm = (MenuForm) form;
		if (list.size() > i) {
			BasicDynaBean rowBean = (BasicDynaBean) list.get(i);
			MyBeanUtil.copyFormbeanFromDBbean(aForm, rowBean);
		}
		request.setAttribute("type", type);
		request.setAttribute("showType", "1");
		request.setAttribute("reloadFlag", "0");
		request.setAttribute("isList", OptionUtils.getIsOrNot(false));
		return mapping.findForward("SystemMenuShow");
	}

	protected void setSystemMenuTree(ActionForm form, HttpServletRequest request) {

		CallHelper helper = new CallHelper("getSystemMenuTree");

		helper.execute();

		request.setAttribute("menu.tree", helper.getResult("results"));
	}
}
