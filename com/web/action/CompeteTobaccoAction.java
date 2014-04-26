package com.web.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.form.TobaccoInfoForm;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.SqlReturn;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;
import com.zl.util.TradeList;

public class CompeteTobaccoAction extends PrintAction {
	static Logger logger = Logger.getLogger(CompeteTobaccoAction.class
			.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {
			logger.error(e);
			return mapping.findForward("error");
		}
	}

	public ActionForward init(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// SqlReturn spReturn = TradeList.g_getAllTobaccos("");

		return query(actionMapping, form, request, response);
	}

	public ActionForward query(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		TobaccoInfoForm aForm = (TobaccoInfoForm) form;
		aForm.setProvincecode("");
		aForm.setFactoryCode(""); /*
								 * if (aForm.getProvincecode()==null){
								 * CallHelper helper =
								 * initializeCallHelper("queryProvinces",form,
								 * request, false);
								 * helper.setParam("provinceCode","");
								 * helper.execute(); List list
								 * =helper.getResult(0);
								 * request.setAttribute("province.list", list);
								 *
								 * aForm.setProvinceid("1");
								 * aForm.setProvincecode("11");
								 *
								 * SqlReturn
								 * spreturn=TradeList.G_QueryFactoryByProvince
								 * (aForm.getProvinceid()); ArrayList
								 * list2=spreturn.getResultSet(0);
								 * request.setAttribute("factory.list",list2);
								 * aForm.setFactoryCode(""); }else{ CallHelper
								 * helper =
								 * initializeCallHelper("queryProvinces",aForm,
								 * request, false); helper.execute(); List list
								 * =helper.getResult(0);
								 * request.setAttribute("province.list", list);
								 *
								 * SqlReturn
								 * spreturn=TradeList.G_QueryFactoryByProvince
								 * (aForm.getProvinceid()); ArrayList
								 * list2=spreturn.getResultSet(0);
								 * request.setAttribute("factory.list",list2);
								 * aForm.setFactoryCode(""); }
								 */
		CallHelper caller = initializeCallHelper("getCompeteTobaccoByFact",
				aForm, request, false);

		caller.execute();
		request.setAttribute("tobaccocaption.list", caller.getResult(0));
		request.setAttribute("tobaccoinfo.list", caller.getResult(1));

		return actionMapping.findForward("init");
	}

	public ActionForward change(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		TobaccoInfoForm aForm = (TobaccoInfoForm) form;
		// SqlReturn spretur =
		// TradeList.G_getTobaccoByFact(aForm.getProvincecode(),aForm.getFactoryCode());
		String tobaCode = aForm.getTobaCode() != null ? aForm.getTobaCode()
				.trim() : "";
		String tobaName = aForm.getTobaName() != null ? aForm.getTobaName()
				.trim() : "";
		SqlReturn spretur = TradeList.G_getTobaccoByFact(
				aForm.getProvincecode(), aForm.getFactoryCode(), tobaCode,
				tobaName);

		request.setAttribute("tobaccocaption.list", spretur.getResultSet(0));
		request.setAttribute("tobaccoinfo.list", spretur.getResultSet(1));

		return query(actionMapping, aForm, request, response);
	}

	public ActionForward add(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		getInitList(request);
		return actionMapping.findForward("add");
	}

	// 保存新增
	public ActionForward saveadd(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		TobaccoInfoForm tobaccoInfoForm = (TobaccoInfoForm) form;

		CallHelper helper = initializeCallHelper("SaveCompeteTobaccoAdd", form,
				request, false);

		helper.execute();
		request.setAttribute("message", helper.getOutput(0));

		return add(actionMapping, tobaccoInfoForm, request, response);
	}

	public ActionForward modify(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		TobaccoInfoForm tobaccoInfoForm = (TobaccoInfoForm) form;
		getInitList(request);
		List<BasicDynaBean> tobacco = TradeList.g_getCompeteTobaccoInfo(
				tobaccoInfoForm.getTobaSystId()).getResultSet(0);
		int i = 0;
		if (tobacco.size() > i) {
			BasicDynaBean rowBean = (BasicDynaBean) tobacco.get(i);
			try {
				MethodFactory.copyProperties(tobaccoInfoForm, rowBean);
				tobaccoInfoForm.setFactoryCode((String) PropertyUtils
						.getProperty(rowBean, "factorycode"));
			} catch (Exception e) {
				logger.error(e);
				return actionMapping.findForward("error");
			}
		}
		return actionMapping.findForward("add");
	}

	private void getInitList(HttpServletRequest request) {
		try {
			// List list = TradeList.g_factoryQuery().getResultSet(0);
			List list = TradeList.g_allFactoryQuery().getResultSet(0);
			request.setAttribute("factory.list", list);
			// list = TradeList.g_getBrands().getResultSet(1);
			// request.setAttribute("brand.list", list);
			// SqlReturn spReturn = TradeList.g_getAllPriceLevels();
			// request.setAttribute("level.list", spReturn.getResultSet(1));
			list = TradeList.Getpriceid();
			request.setAttribute("level.list", list);
			// list = getPublicCodes("501");
			// request.setAttribute("rank.list", list);
			// list = getPublicCodes("502");
			// request.setAttribute("style.list", list);
			// list = getPublicCodes("503");
			// request.setAttribute("spec.list", list);
			request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		} catch (Exception e) {
		}
	}

	public ActionForward reportqueryinit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		TobaccoInfoForm dynaForm = (TobaccoInfoForm) form;
		CallHelper caller = initializeCallHelper(
				"getAreaCompanyTreeDailyReport", dynaForm, request, false);
		caller.setParam("areaCode", "900000");
		caller.setParam("userId", getPersonId(request));
		caller.execute();
		request.setAttribute("companyTree", caller.getResult(0));

		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.MONTH, -1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String year = formatter.format(rightNow.getTime()).substring(0, 4);
		String month = formatter.format(rightNow.getTime()).substring(5, 7);

		dynaForm.setYear(year);
		dynaForm.setMonth(month);
		// dynaForm.set("unitId", "1");
		request.setAttribute("unit.list", OptionUtils.getUnitList());
		request.setAttribute("year.list", OptionUtils.getYearList(-3, 3));
		request.setAttribute("month.list", OptionUtils.getMonthList(1, 12));
		request.setAttribute("reportstatu.list", OptionUtils.getReportStatus());
		request.setAttribute("years.list", OptionUtils.getYearList(-3, 3));
		request.setAttribute("months.list", OptionUtils.getMonthList(1, 12));

		return mapping.findForward("reportqueryinit");
	}

	public ActionForward reportqueryshow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String qid = (String) request.getParameter("queryId");
		String style = (String) request.getParameter("style");
		TobaccoInfoForm infoForm = (TobaccoInfoForm) form;
		String cmpid = infoForm.getSpec();

		CallHelper helper = initializeCallHelper("getCompeteTobaReport", form,
				request, false);
		helper.setParam("personId", getPersonId(request));
		helper.setParam("spec", cmpid);
		helper.setParam("queryType", qid);

		helper.execute();
		request.setAttribute("caption.list", helper.getResult("captions"));
		request.setAttribute("result.list", helper.getResult("results"));

		String head = "";
		String detail = "";

		if ("0".equalsIgnoreCase(style)) {
			head = "主要竞争品牌";
		} else {
			head = "分价位竞争品牌";
		}
		detail = "时间【" + infoForm.getYear() + "年" + infoForm.getMonth() + "月】";
		if ("1".equalsIgnoreCase(qid)) {
			head = head + "已报单位查询";
		} else if ("2".equalsIgnoreCase(qid)) {
			head = head + "未报单位查询";
		}
		request.setAttribute("head", head);
		request.setAttribute("detail", detail);
		return mapping.findForward("reportqueryshow");
	}

}
