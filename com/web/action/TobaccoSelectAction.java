package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.zl.base.core.db.CallHelper;

public class TobaccoSelectAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(TobaccoSelectAction.class);

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

	public ActionForward query(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String rdbSelectMethod = request.getParameter("rdbSelectMethod");
		if ("1".equals(rdbSelectMethod)) {
			CallHelper helper = initializeCallHelper(
					"getTobaccoDailyQueryInProv", form, request, false);
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
		} else if ("2".equals(rdbSelectMethod)) {
			CallHelper helper = initializeCallHelper(
					"getTobaccoTreePriceReport", form, request, false);
			helper.setParam("userId", getPersonId(request));
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
		}

		return actionMapping.findForward("query" + rdbSelectMethod);
	}

	public ActionForward query2(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String rdbSelectMethod = request.getParameter("rdbSelectMethod");
		String forward = "query";
		if ("9".equals(rdbSelectMethod)) {
			// CallHelper helper = initializeCallHelper("getTobaccoTreeCD",
			// form, request, false);
			CallHelper helper = initializeCallHelper("getTobaccoTree2", form,
					request, false);
			helper.setParam("userId", getPersonId(request));
			helper.setParam("systemId", request.getParameter("systemId"));
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
			forward = "queryall";
		} else {
			CallHelper helper = initializeCallHelper(
					"getTobaccoTreeByFactoryId", form, request, false);
			helper.setParam("userId", getPersonId(request));
			helper.setParam("systemId", request.getParameter("systemId"));
			helper.setParam("factoryId", rdbSelectMethod);
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
		}
		// 查询湖南中烟包括长沙和常德
		/*
		 * else if ("0".equals(rdbSelectMethod)){ CallHelper helper =
		 * initializeCallHelper("getTobaccoTreeHN", form, request, false);
		 * helper.setParam("userId",getPersonId(request));
		 * helper.setParam("systemId", request.getParameter("systemId"));
		 *
		 * helper.execute(); request.setAttribute("tobacco.tree",
		 * helper.getResult(0)); } //只查询长沙的 else if
		 * ("4".equals(rdbSelectMethod)){ CallHelper helper =
		 * initializeCallHelper("getTobaccoTreeCS", form, request, false);
		 * helper.setParam("userId",getPersonId(request));
		 * helper.setParam("systemId", request.getParameter("systemId"));
		 * helper.execute(); request.setAttribute("tobacco.tree",
		 * helper.getResult(0)); }
		 */

		return actionMapping.findForward(forward);

	}

	public ActionForward query3(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String rdbSelectMethod = request.getParameter("rdbSelectMethod");
		String forward = "query";
		if ("9".equals(rdbSelectMethod) || "8".equals(rdbSelectMethod)) {
			// CallHelper helper = initializeCallHelper("getTobaccoTreeCD",
			// form, request, false);
			CallHelper helper = initializeCallHelper("getTobaccoTree2New",
					form, request, false);
			helper.setParam("userId", getPersonId(request));
			helper.setParam("systemId", request.getParameter("systemId"));
			helper.setParam("factoryId", rdbSelectMethod);
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
			forward = "queryall";
		} else if ("1".equals(rdbSelectMethod) || "0".equals(rdbSelectMethod)) {
			CallHelper helper = initializeCallHelper(
					"getTobaccoTreeByFactoryIdNew", form, request, false);
			helper.setParam("userId", getPersonId(request));
			helper.setParam("systemId", request.getParameter("systemId"));
			helper.setParam("factoryId", rdbSelectMethod);
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
		} else {
			CallHelper helper = initializeCallHelper("getCompareTobaccoTree",
					form, request, false);
			helper.setParam("userId", getPersonId(request));
			helper.setParam("systemId", request.getParameter("systemId"));
			helper.setParam("factoryId", rdbSelectMethod);
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
		}
		return actionMapping.findForward(forward);
	}

	public ActionForward queryall(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String rdbSelectMethod = request.getParameter("rdbSelectMethod");
		String forward = "query";
		if ("9".equals(rdbSelectMethod) || "8".equals(rdbSelectMethod)) {
			// CallHelper helper = initializeCallHelper("getTobaccoTreeCD",
			// form, request, false);
			CallHelper helper = initializeCallHelper("getTobaccoTreeAll", form,
					request, false);
			helper.setParam("userId", getPersonId(request));
			helper.setParam("systemId", request.getParameter("systemId"));
			helper.setParam("factoryId", rdbSelectMethod);
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
			forward = "queryall";
		} else if ("1".equals(rdbSelectMethod) || "0".equals(rdbSelectMethod)) {
			CallHelper helper = initializeCallHelper(
					"getTobaccoTreeByFactoryId", form, request, false);
			helper.setParam("userId", getPersonId(request));
			helper.setParam("systemId", request.getParameter("systemId"));
			helper.setParam("factoryId", rdbSelectMethod);
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
		} else {
			CallHelper helper = initializeCallHelper("getCompareTobaccoTree",
					form, request, false);
			helper.setParam("userId", getPersonId(request));
			helper.setParam("systemId", request.getParameter("systemId"));
			helper.setParam("factoryId", rdbSelectMethod);
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
		}
		return actionMapping.findForward(forward);
	}

	public ActionForward queryPrice(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String rdbSelectMethod = request.getParameter("rdbSelectMethod");
		String forward = "query";
		if ("9".equals(rdbSelectMethod) || "8".equals(rdbSelectMethod)) {

			CallHelper helper = initializeCallHelper(
					"getTobaccoTree2NewForPrice", form, request, false);
			helper.setParam("userId", getPersonId(request));
			helper.setParam("systemId", request.getParameter("systemId"));
			helper.setParam("factoryId", rdbSelectMethod);
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
			forward = "queryall";
		} else if ("1".equals(rdbSelectMethod) || "0".equals(rdbSelectMethod)) {
			CallHelper helper = initializeCallHelper(
					"getTobaccoTreeByFactoryForPrice", form, request, false);
			helper.setParam("userId", getPersonId(request));
			helper.setParam("systemId", request.getParameter("systemId"));
			helper.setParam("factoryId", rdbSelectMethod);
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
		} else {
			CallHelper helper = initializeCallHelper("getCompareTobaccoTree",
					form, request, false);
			helper.setParam("userId", getPersonId(request));
			helper.setParam("systemId", request.getParameter("systemId"));
			helper.setParam("factoryId", rdbSelectMethod);
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
		}
		return actionMapping.findForward(forward);
	}

	public ActionForward emphasisTobaSelect(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CallHelper call = initializeCallHelper("emphasisTobaSelect", form,
				request, false);
		call.execute();
		request.setAttribute("tobacco.tree", call.getResult(0));

		return mapping.findForward("emphasisTobaSelect");
	}

	public ActionForward query2new(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String rdbSelectMethod = request.getParameter("rdbSelectMethod");
		String forward = "query";
		if ("9".equals(rdbSelectMethod)) {
			// CallHelper helper = initializeCallHelper("getTobaccoTreeCD",
			// form, request, false);
			CallHelper helper = initializeCallHelper("getTobaccoTree2ForC",
					form, request, false);
			helper.setParam("userId", getPersonId(request));
			helper.setParam("systemId", request.getParameter("systemId"));
			// helper.setParam("factoryId","8");
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
			forward = "queryall";
		} else {
			CallHelper helper = initializeCallHelper(
					"getTobaTreeByFacIdForcheck", form, request, false);
			helper.setParam("userId", getPersonId(request));
			helper.setParam("systemId", request.getParameter("systemId"));
			helper.setParam("factoryId", rdbSelectMethod);
			helper.execute();
			request.setAttribute("tobacco.tree", helper.getResult(0));
		}
		// 查询湖南中烟包括长沙和常德
		/*
		 * else if ("0".equals(rdbSelectMethod)){ CallHelper helper =
		 * initializeCallHelper("getTobaccoTreeHN", form, request, false);
		 * helper.setParam("userId",getPersonId(request));
		 * helper.setParam("systemId", request.getParameter("systemId"));
		 *
		 * helper.execute(); request.setAttribute("tobacco.tree",
		 * helper.getResult(0)); } //只查询长沙的 else if
		 * ("4".equals(rdbSelectMethod)){ CallHelper helper =
		 * initializeCallHelper("getTobaccoTreeCS", form, request, false);
		 * helper.setParam("userId",getPersonId(request));
		 * helper.setParam("systemId", request.getParameter("systemId"));
		 * helper.execute(); request.setAttribute("tobacco.tree",
		 * helper.getResult(0)); }
		 */

		return actionMapping.findForward(forward);

	}

	public ActionForward emphasisTobaSelect1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CallHelper call = initializeCallHelper("emphasisTobaSelect1", form,
				request, false);
		call.execute();
		request.setAttribute("tobacco.tree", call.getResult(0));

		return mapping.findForward("emphasisTobaSelect");
	}

}
