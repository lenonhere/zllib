package com.web.action.business.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.qmx.dateutils.DateUtils;
import com.web.action.CriterionAction;
import com.ws.basedata.ECS_NAT_BPM_MonthPlanQueryInd;
import com.zl.base.core.db.CallHelper;
import com.zl.util.TradeList;

public class ImportDataAction extends CriterionAction {

	private static final Log log = LogFactory.getLog(ImportDataAction.class);

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

	/*
	 * 导入国家局数据_初始化页面
	 */
	public ActionForward gjImportInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		setCriteria("plan.month.release.init", form, request);

		dynaForm.set("month", DateUtils.getMonth(1).substring(4, 6));
		dynaForm.set("year", DateUtils.getMonth(1).substring(0, 4));

		dynaForm.set("userId", getPersonId(request));
		dynaForm.set("areaCode", "999999"); // 省外

		CallHelper helper = initializeCallHelper(
				"getAreaCompanyTreeIsActiveTrue", form, request, false);
		helper.execute();
		request.setAttribute("company.tree", helper.getResult(0));
		request.setAttribute("message", null);

		return mapping.findForward("gjImportInit");
	}

	public ActionForward gjImportShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("getMonthPlanQueryInd", form,
				request, false);
		helper.execute();

		request.setAttribute("caption.list", helper.getResult("captions"));
		request.setAttribute("result.list", helper.getResult("results"));

		return mapping.findForward("gjImportShow");
	}

	/*
	 * 导入数据 ECS_MonthPlanQueryInd
	 */
	public ActionForward ECS_MonthPlanQueryInd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String compIdSet = request.getParameter("companyIdSet");

		// 锁定表
		dynaForm.set("userId", getPersonId(request));
		dynaForm.set("queryName", "ECS_MonthPlanQueryInd");
		dynaForm.set("execState", "1");
		CallHelper callper = initializeCallHelper("modifylocktablestatus",
				form, request, false);
		callper.execute();

		String msg = "";
		try {
			List gjCodeList = TradeList.GetCompanyGJCodeByids(compIdSet);
			for (int i = 0; i < gjCodeList.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) gjCodeList.get(i);
				String comCode = (String) bean.get("gj_code");
				// System.out.println("gjCode >>:" + comCode + "--开始");
				if (null != comCode && !"".equals(comCode)) {
					// 导入数据
					msg = ECS_NAT_BPM_MonthPlanQueryInd.getInstance()
							.importMonthPlanQueryIndData(year, month, comCode);
				}
				// System.out.println("gjCode >>:" + comCode + "--结束");
			}
			request.setAttribute("msg", msg);
		} catch (Exception e) {
			request.setAttribute("msg", "导入数据失败. " + msg);
			log.debug(e);
		}

		// 解锁表
		dynaForm.set("execState", "0");
		callper = initializeCallHelper("modifylocktablestatus", form, request,
				false);
		callper.execute();
		return mapping.findForward("close");
	}

	// myCheckImp
	public ActionForward myCheckImp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			DynaActionForm dynaForm = (DynaActionForm) form;
			CallHelper helper = initializeCallHelper(
					"getLockStatusByPersonCorp", form, request, false);
			helper.execute();
			List list = helper.getResult("results");
			int n = list.size();
			String msg = "0";
			// System.out.println("n================="+n);
			if (n > 0) {
				for (int i = 0; i < n; i++) {
					BasicDynaBean bean = (BasicDynaBean) list.get(i);
					String corpname = bean.get("corpname").toString();
					String mname = bean.get("modifyusername").toString();
					msg += mname + "> 正在导入 [" + corpname + "] 的数据,请稍后再试!\r\n";
				}
			}
			PrintWriter out = response.getWriter();
			out.print(msg);
			out.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		return null;
	}

	/*
	 * 导入数据 ECS_MonthPlanQueryInd
	 */
	public ActionForward gjImportExec(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String compIdSet = request.getParameter("companyIdSet");

		// 锁定表
		dynaForm.set("userId", getPersonId(request));
		dynaForm.set("companyIdSet", compIdSet);
		dynaForm.set("execState", "1");
		CallHelper callper = initializeCallHelper("setLockStatusByPersonCorp",
				form, request, false);
		callper.execute();

		String msg = "";
		try {
			List gjCodeList = TradeList.GetCompanyGJCodeByids(compIdSet);
			for (int i = 0; i < gjCodeList.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) gjCodeList.get(i);
				String comCode = (String) bean.get("gj_code");
				// System.out.println("gjCode >>:" + comCode + "--开始");
				if (null != comCode && !"".equals(comCode)) {
					// 导入数据
					msg = ECS_NAT_BPM_MonthPlanQueryInd.getInstance()
							.importMonthPlanQueryIndData(year, month, comCode);
				}
				// System.out.println("gjCode >>:" + comCode + "--结束");
			}
			request.setAttribute("msg", msg);
		} catch (Exception e) {
			request.setAttribute("msg", "导入数据失败. " + msg);
			System.out.println("导入数据失败:" + e);
		}

		// 解锁表
		dynaForm.set("execState", "0");
		callper = initializeCallHelper("setLockStatusByPersonCorp", form,
				request, false);
		callper.execute();
		return mapping.findForward("close");
	}
}
