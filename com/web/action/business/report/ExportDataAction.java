package com.web.action.business.report;


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
import com.ws.basedata.ECS_NAT_BPM_MonthPlanRecvInd;
import com.ws.basedata.ECS_NAT_BPM_MonthPlanRecvIndBatch;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.Executer;
import com.zl.util.TradeList;

public class ExportDataAction extends CriterionAction {

	private static final Log log = LogFactory.getLog(ExportDataAction.class);

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
	 * 导出国家局数据_初始化页面_工业可供量
	 */
	public ActionForward gjExportInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		setCriteria("plan.month.release.init", form, request);

		dynaForm.set("month", DateUtils.getMonth(1).substring(4, 6));
		dynaForm.set("year", DateUtils.getMonth(1).substring(0, 4));

		dynaForm.set("userId", getPersonId(request));
		dynaForm.set("areaCode", "999999"); // 省外

		// getAreaCompanyTreeInRight
		CallHelper helper = initializeCallHelper(
				"getAreaCompanyTreeIsActiveTrue", form, request, false);
		helper.execute();
		request.setAttribute("company.tree", helper.getResult(0));
		request.setAttribute("message", null);

		return mapping.findForward("gjExportInit");
	}

	public ActionForward gjExportShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("getMonthPlanRecvInd", form,
				request, false);
		helper.execute();

		request.setAttribute("caption.list", helper.getResult("captions"));
		request.setAttribute("result.list", helper.getResult("results"));

		return mapping.findForward("gjExportShow");
	}

	public ActionForward gjExportSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("saveMonthPlanRecvInd", form,
				request, false);
		helper.execute();

		request.setAttribute("message.code", "0");
		request
				.setAttribute("message.information", helper
						.getOutput("message"));

		return gjExportShow(mapping, form, request, response);
	}

	// 分公司发送
	public ActionForward gjExportExe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DynaActionForm dynaForm = (DynaActionForm) form;
		String sendFlag = dynaForm.get("flags").toString();
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String compIdSet = request.getParameter("companyIdSet");

		String msgs = "";
		// 发送数据到国家局 -- 开始
		try {
			// 国家局编码gj_code
			List gjCodeList = TradeList.GetCompanyGJCodeByids(compIdSet);

			StringBuffer sql = new StringBuffer();
			sql
					.append("select distinct comcode,plan_year,plan_month,brand_code,brand_name,");
			if ("1".equals(sendFlag)) {
				sql.append("supply_amount from ECS_MonthPlanRecvInd_Supply ");
			}
			if ("2".equals(sendFlag)) {
				sql.append("join_amount from ECS_MonthPlanRecvInd_Join ");
			}
			if ("3".equals(sendFlag)) {
				sql.append("adjust_amount from ECS_MonthPlanRecvInd_Adjust ");
			}
			sql.append("where sendflag='0' ");
			sql.append("  and plan_year='" + year + "' ");
			sql.append("  and plan_month='" + month + "' ");

			String yes = "";
			String no = "";
			for (int i = 0; i < gjCodeList.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) gjCodeList.get(i);
				String comCode = (String) bean.get("gj_code");
				String comCodeSql = " and comcode = '" + comCode + "'";

				String endSql = sql.toString() + comCodeSql;
				// System.out.println("ExportDataAction >>:" + comCode);

				// 导出数据
				List results = Executer.getInstance().ExecSeletSQL(endSql)
						.getResultSet();
				if (results.size() > 0) {
					// 发送
					String msg = ECS_NAT_BPM_MonthPlanRecvInd.getInstance()
							.MonthPlanRecvInd(sendFlag, year, month, comCode,
									results);
					yes += comCode + "-" + msg + "\n";
				} else {
					no += comCode + ".";
				}
			}
			if (!"".equals(no)) {
				no += "数据已上传或没有可上传的数据!\n";
			}
			msgs = yes + no;
		} catch (Exception e) {
			log.debug("发送数据到国家局失败! " + e);
			msgs = "发送数据到国家局失败! ";
		}
		// 发送数据到国家局 -- 结束

		request.setAttribute("message.code", "0");
		request.setAttribute("message.information", msgs);

		return gjExportShow(mapping, form, request, response);
	}

	// 批量发送
	public ActionForward gjExportBatchExe(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		DynaActionForm dynaForm = (DynaActionForm) form;
		String sendFlag = dynaForm.get("flags").toString();
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String compIdSet = request.getParameter("companyIdSet");

		String msgs = "";
		// 发送数据到国家局 -- 开始
		try {
			// 国家局编码gj_code
			String comCodeSet = "";
			List gjCodeList = TradeList.GetCompanyGJCodeByids(compIdSet);
			for (int i = 0; i < gjCodeList.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) gjCodeList.get(i);
				String comCode = (String) bean.get("comcode");
				if (null != comCode && "" != comCode) {
					comCodeSet += "'" + comCode + "',";
				}
			}
			comCodeSet = comCodeSet.substring(0, comCodeSet.length() - 1);
			System.out.println("ExportDataAction.ComCodeSet >>:" + comCodeSet);

			StringBuffer sql = new StringBuffer();
			sql
					.append("select distinct comcode,plan_year,plan_month,brand_code,brand_name,");
			if ("1".equals(sendFlag)) {
				sql.append("supply_amount from ECS_MonthPlanRecvInd_Supply ");
			}
			if ("2".equals(sendFlag)) {
				sql.append("join_amount from ECS_MonthPlanRecvInd_Join ");
			}
			if ("3".equals(sendFlag)) {
				sql.append("adjust_amount from ECS_MonthPlanRecvInd_Adjust ");
			}
			sql.append("where sendflag='0' ");
			sql.append("  and plan_year='" + year + "' ");
			sql.append("  and plan_month='" + month + "' ");
			sql.append("  and comcode in (" + comCodeSet + ")");

			// 导出数据
			List results = Executer.getInstance().ExecSeletSQL(sql.toString())
					.getResultSet();

			if (results.size() > 0) {
				// 发送
				msgs = ECS_NAT_BPM_MonthPlanRecvIndBatch.getInstance()
						.MonthPlanRecvInd(sendFlag, year, month, comCodeSet,
								results);
			} else {
				msgs = "数据已上传或没有可上传的数据!";
			}

		} catch (Exception e) {
			log.debug("发送数据到国家局失败! " + e);
			msgs = "批量发送数据到国家局失败! ";
		}
		// 发送数据到国家局 -- 结束

		request.setAttribute("message.code", "0");
		request.setAttribute("message.information", msgs);

		return gjExportShow(mapping, form, request, response);
	}

	/*
	 * 导出国家局数据_初始化页面_工业衔接量
	 */
	public ActionForward gjExportJoinInit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
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

		return mapping.findForward("gjExportJoinInit");
	}

	/*
	 * 导出国家局数据_初始化页面_工业调整量
	 */
	public ActionForward gjExportAdjustInit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
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

		return mapping.findForward("gjExportAdjustInit");
	}

}

