package com.web.action.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.zl.base.core.db.CallHelper;

public class MenuAction extends CoreDispatchAction {

	private static Log log = LogFactory.getLog(MenuAction.class);

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

	// 读取选中的下级菜单
	public ActionForward queryright(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		switchMenu(request.getParameter("menucode"), request);
		CallHelper caller = new CallHelper("getisdriver");
		caller.setParam("personId", getPersonId(request));
		caller.execute();
		String isdriver = caller.getOutput(0).toString();
		request.setAttribute("isdriver", isdriver);
		if ("iframe".equals(request.getParameter("iframe"))) {
			request.getSession().setAttribute("https", "https");
		}
		return mapping.findForward("query");
	}

	// 读取选中的下级菜单
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		switchMenu(request.getParameter("menucode"), request);
		CallHelper caller = new CallHelper("getisdriver");
		caller.setParam("personId", getPersonId(request));
		caller.execute();
		String isdriver = caller.getOutput(0).toString();
		request.setAttribute("isdriver", isdriver);

		return mapping.findForward("query");
	}

	/**
	 * 初始化_左侧菜单树
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward defaultQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = new CallHelper("getModulesByUser");
		helper.setParam("personId", getPersonId(request));
		helper.execute();

		List codeList = helper.getResult("results");

		if (codeList.size() > 0) {
			BasicDynaBean rowBean = (BasicDynaBean) codeList.get(0);
			switchMenu(rowBean.get("menucode"), request);
		}
		CallHelper caller = new CallHelper("getisdriver");
		caller.setParam("personId", getPersonId(request));
		caller.execute();
		String isdriver = caller.getOutput(0).toString();
		request.setAttribute("isdriver", isdriver);

		return mapping.findForward("query");
	}

	public void switchMenu(Object menuCode, HttpServletRequest request) {

		CallHelper helper = new CallHelper("getMenuQuery");
		helper.setParam("menuCode", menuCode);
		helper.setParam("personId", getPersonId(request));
		helper.execute();

		request.setAttribute("menu.list", helper.getResult("results"));
	}

	public ActionForward setMiddlePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = new CallHelper("getMsgReceiveList");
		helper.setParam("personId", getPersonId(request));
		helper.setParam("state", "-1");
		helper.execute();
		CallHelper caller = new CallHelper("getisdriver");
		caller.setParam("personId", getPersonId(request));
		caller.execute();
		String isdriver = caller.getOutput(0).toString();
		request.setAttribute("isdriver", isdriver);
		Integer mailCount = new Integer((String) helper.getOutput("msgCount"));
		request.setAttribute("mailCount", mailCount);

		return mapping.findForward("middlePage");
	}

}
