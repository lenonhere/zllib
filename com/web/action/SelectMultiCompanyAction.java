package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.web.form.SelectMultiCompanyForm;
import com.zl.base.core.db.CallHelper;
import com.zl.common.Constants;

public class SelectMultiCompanyAction extends CoreDispatchAction {
	static Logger logger = Logger.getLogger(SelectMultiCompanyAction.class);

	public ActionForward query(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			SelectMultiCompanyForm smForm = (SelectMultiCompanyForm) form;
			// String areaCode = "";
			String type = smForm.getType();
			if ("3".equals(type)) {
				// 省内
				smForm.setAreaCode("420000");
				smForm.setType("1");
			} else if ("4".equals(type)) {
				// 省外
				smForm.setAreaCode("900000");
				smForm.setType("1");
			} else if ("5".equals(type)) {
				// 全国
				smForm.setAreaCode("");
				smForm.setType("1");
			} else {
				smForm.setAreaCode("");
				smForm.setType("1");
			}

			// HttpSession session = request.getSession();
			// String flag = (String)session.getAttribute("flag");
			// System.out.println(flag);
			smForm.setUserId(getPersonId(request));
			String showone = (String) request.getParameter("showone");
			String flag = (String) request.getParameter("flag");
			System.out.println(flag);
			String helpername = "getAreaCompanyTreeInRight";//

			if ("1".equals(flag)) {
				helpername = "getAreaCompanyTree111";
			}
			if ("3".equals(showone)) {
				helpername = "getAllCompanyTree1";// ----getAreaCompanyTreeDailyReport
			}
			CallHelper helper = initializeCallHelper(helpername, form, request,
					false);

			// helper.setParam("flag", flag);
			// session.removeAttribute("flag");
			helper.execute();
			request.setAttribute("company.tree", helper.getResult(0));
		} catch (Exception e) {
			logger.error(e);
			ActionError error = new ActionError(Constants.ERROR_GENERAL,
					e.getMessage());
			errors.add(Constants.ERROR_GENERAL, error);
		}
		if (errors.size() > 0) {
			saveErrors(request, errors);
			return actionMapping.findForward("error");
		}

		return actionMapping.findForward("query");
	}

	public ActionForward queryWithoutRight(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			SelectMultiCompanyForm smForm = (SelectMultiCompanyForm) form;
			// String areaCode = "";
			String type = smForm.getType();
			if ("3".equals(type)) {
				// 省内
				smForm.setAreaCode("420000");
				smForm.setType("1");
			} else if ("4".equals(type)) {
				// 省外
				smForm.setAreaCode("900000");
				smForm.setType("1");
			} else if ("5".equals(type)) {
				// 全国
				smForm.setAreaCode("");
				smForm.setType("1");
			} else {
				smForm.setAreaCode("");
				smForm.setType("1");
			}

			smForm.setUserId(getPersonId(request));
			CallHelper helper = initializeCallHelper(
					"getAreaCompanyTreeUnSelected", form, request, false);
			helper.execute();
			request.setAttribute("company.tree", helper.getResult(0));
		} catch (Exception e) {
			logger.error(e);
			ActionError error = new ActionError(Constants.ERROR_GENERAL,
					e.getMessage());
			errors.add(Constants.ERROR_GENERAL, error);
		}
		if (errors.size() > 0) {
			saveErrors(request, errors);
			return actionMapping.findForward("error");
		}

		return actionMapping.findForward("query");
	}

	public ActionForward queryLock(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			SelectMultiCompanyForm smForm = (SelectMultiCompanyForm) form;
			// String areaCode = "";
			String type = smForm.getType();
			if ("3".equals(type)) {
				// 省内
				smForm.setAreaCode("420000");
			} else if ("4".equals(type)) {
				// 省外
				smForm.setAreaCode("900000");
			} else if ("5".equals(type)) {
				// 全国
				smForm.setAreaCode("");
			} else {
				smForm.setAreaCode("");
			}
			smForm.setUserId(getPersonId(request));
			CallHelper helper = initializeCallHelper("getAreaCompanyTreeLock",
					form, request, false);
			helper.setParam("day", request.getParameter("day"));
			helper.execute();
			request.setAttribute("company.tree", helper.getResult(0));
		} catch (Exception e) {
			logger.error(e);
			ActionError error = new ActionError(Constants.ERROR_GENERAL,
					e.getMessage());
			errors.add(Constants.ERROR_GENERAL, error);
		}
		if (errors.size() > 0) {
			saveErrors(request, errors);
			return actionMapping.findForward("error");
		}

		return actionMapping.findForward("querylock");
	}
}
