package com.web.action;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.qmx.dateutils.DateUtils;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.taglib.flashchart.FlashChartUtil;
import com.zl.base.core.taglib.flashchart.PieChart;
import com.zl.base.core.taglib.flashchart.Title;
import com.zl.base.core.taglib.flashchart.XYChart;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;
import com.zl.util.TradeList;

public class CompanyDistributionChartQueryAction extends CriterionAction {

	static Logger logger = Logger
			.getLogger(CompanyDistributionChartQueryAction.class);

	public ActionForward cxbinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DynaActionForm initForm = (DynaActionForm) form;

		int t = Integer.parseInt(DateUtils.getDate().substring(8, 10));
		initForm.set("endDate", DateUtils.getDate(-t));

		request.setAttribute("unit.list", OptionUtils.getUnitList());

		return mapping.findForward("cxbinit");

	}

	public ActionForward cxbshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String companyName = request.getParameter("companyName");
		String tobaccoName = request.getParameter("tobaccoName");
		String unitName = request.getParameter("unitName");
		String queryId = request.getParameter("queryId");

		String detail = companyName + tobaccoName + ".bmp";
		String title = companyName + tobaccoName;

		String personId = getPersonId(request);
		CallHelper helper = initializeCallHelper("QueryCXBChart", form,
				request, false);
		helper.setParam("userId", personId);
		helper.setParam("queryId", queryId);
		helper.execute();

		request.setAttribute("item.list", helper.getResult(0));
		request.setAttribute("serie.list", helper.getResult(1));
		request.setAttribute("result.list", helper.getResult(2));
		request.setAttribute("imageName", detail);
		request.setAttribute("title", title);
		request.setAttribute("unitName", unitName);

		return mapping.findForward("cxbshow");
	}

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// 初始化页面数据
		DynaActionForm initForm = (DynaActionForm) form;

		Calendar calendar = Calendar.getInstance();
		String nowYear = calendar.get(Calendar.YEAR) + "";
		String nowHalf = "";
		int month = calendar.get(Calendar.MONTH);
		if (month >= Calendar.JANUARY && month < Calendar.JULY) {
			nowHalf = "0";
		} else if (month >= Calendar.JULY && month <= Calendar.DECEMBER) {
			nowHalf = "1";
		} else {
			nowHalf = "0";
		}

		initForm.set("beginDate", DateUtils.getDate(-12));
		initForm.set("endDate", DateUtils.getDate());
		// initForm.set("companyId","-1");

		String companyId = "1100";// (String)initForm.get("companyId");

		String personId = getPersonId(request);
		List<BasicDynaBean> companyList = null;
		List<BasicDynaBean> tobaccoList = null;
		try {
			companyList = TradeList.getPersonManComp(personId);
			// TradeList.getDistributionCompanyByPerson(personId);

			if (companyId == null || "".equals(companyId)) {
				if (companyList.size() > 0) {
					BasicDynaBean bean = (BasicDynaBean) companyList.get(0);
					companyId = MethodFactory.getThisString(bean
							.get("sycompsystid"));
				}
			}

			// tobaccoList =
			// TradeList.getDistributionTobaccoByCompany(companyId,nowYear,nowHalf);
			tobaccoList = TradeList.getDistributionTobaccoByCompany("1100",
					nowYear, nowHalf);

		} catch (Exception ex) {

		}

		request.setAttribute("company.list", companyList);
		request.setAttribute("tobacco.list", tobaccoList);
		request.setAttribute("unit.list", OptionUtils.getUnitList());

		return mapping.findForward("init");

	}

	public ActionForward saleinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DynaActionForm initForm = (DynaActionForm) form;

		int t = Integer.parseInt(DateUtils.getDate().substring(8, 10));
		initForm.set("endDate", DateUtils.getDate(-t));

		request.setAttribute("unit.list", OptionUtils.getUnitList());

		return mapping.findForward("saleinit");

	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// DynaActionForm initForm = (DynaActionForm) form;
		String companyName = request.getParameter("companyName");
		String tobaccoName = request.getParameter("tobaccoName");
		String unitName = request.getParameter("unitName");
		String queryId = request.getParameter("queryId");

		String detail = companyName + tobaccoName + "各月销量及变化图.bmp";
		String title = companyName + tobaccoName + "各月销量及变化图";

		String personId = getPersonId(request);
		CallHelper helper = initializeCallHelper("querySaleChart", form,
				request, false);
		helper.setParam("userId", personId);
		helper.setParam("queryId", queryId);
		helper.execute();

		request.setAttribute("item.list", helper.getResult(0));
		request.setAttribute("serie.list", helper.getResult(1));
		request.setAttribute("result.list", helper.getResult(2));
		request.setAttribute("imageName", detail);
		request.setAttribute("title", title);
		request.setAttribute("unitName", unitName);

		return mapping.findForward("saleshow");
	}

	public ActionForward areasaleinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DynaActionForm initForm = (DynaActionForm) form;

		int t = Integer.parseInt(DateUtils.getDate().substring(8, 10));
		initForm.set("endDate", DateUtils.getDate(-t));

		request.setAttribute("unit.list", OptionUtils.getUnitList());

		return mapping.findForward("areasaleinit");

	}

	public ActionForward areashow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String companyName = request.getParameter("companyName");
		String tobaccoName = request.getParameter("tobaccoName");
		String unitName = request.getParameter("unitName");
		String queryId = request.getParameter("queryId");
		String month = request.getParameter("endDate").substring(5, 7);
		if (!("10".equalsIgnoreCase(month)))
			month = month.replace("0", "");

		String detail = companyName + tobaccoName + "1-" + month
				+ "月销量及变化图.bmp";
		String title = companyName + tobaccoName + "1-" + month + "月销量及变化图";

		String personId = getPersonId(request);
		CallHelper helper = initializeCallHelper("queryAreaSaleChart", form,
				request, false);
		helper.setParam("userId", personId);
		helper.setParam("queryId", queryId);
		helper.execute();

		request.setAttribute("item.list", helper.getResult(0));
		request.setAttribute("serie.list", helper.getResult(1));
		request.setAttribute("result.list", helper.getResult(2));
		request.setAttribute("imageName", detail);
		request.setAttribute("title", title);
		request.setAttribute("unitName", unitName);

		return mapping.findForward("areasaleshow");
	}

	public ActionForward avgpriceinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DynaActionForm initForm = (DynaActionForm) form;

		int t = Integer.parseInt(DateUtils.getDate().substring(8, 10));
		initForm.set("endDate", DateUtils.getDate(-t));

		request.setAttribute("unit.list", OptionUtils.getUnitList());

		return mapping.findForward("avgpriceinit");

	}

	public ActionForward avgpriceshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// DynaActionForm sform = (DynaActionForm) form;
		String companyName = request.getParameter("companyName");
		String tobaccoName = request.getParameter("tobaccoName");
		String unitName = request.getParameter("unitName");
		String queryId = request.getParameter("queryId");

		String detail = companyName + tobaccoName + ".bmp";
		String title = companyName + tobaccoName;

		String personId = getPersonId(request);
		CallHelper helper = initializeCallHelper("QueryAvgPriceChart", form,
				request, false);
		helper.setParam("userId", personId);
		helper.setParam("queryId", queryId);
		helper.execute();

		request.setAttribute("item.list", helper.getResult(0));
		request.setAttribute("serie.list", helper.getResult(1));
		request.setAttribute("result.list", helper.getResult(2));
		request.setAttribute("imageName", detail);
		request.setAttribute("title", title);
		request.setAttribute("unitName", unitName);

		return mapping.findForward("avgpriceshow");
	}

	public ActionForward flashinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DynaActionForm initForm = (DynaActionForm) form;

		int t = Integer.parseInt(DateUtils.getDate().substring(8, 10));
		initForm.set("endDate", DateUtils.getDate(-t));

		request.setAttribute("unit.list", OptionUtils.getUnitList());

		return mapping.findForward("flashinit");

	}

	/**
	 * ͨ通过flashchart显示图标
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward flashshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// DynaActionForm initForm = (DynaActionForm) form;
		String companyName = request.getParameter("companyName");
		String tobaccoName = request.getParameter("tobaccoName");
		// String unitName = request.getParameter("unitName");
		String queryId = request.getParameter("queryId");

		String title = companyName + tobaccoName + "各月销量及变化图";

		String personId = getPersonId(request);
		CallHelper helper = initializeCallHelper("querySaleFlashChart", form,
				request, false);
		helper.setParam("userId", personId);
		helper.setParam("queryId", queryId);
		helper.setParam("unitId", "0");
		helper.execute();

		try {
			XYChart chart = FlashChartUtil.getXYChart("my_chart",
					helper.getResult(0), helper.getResult(1),
					helper.getResult(2), helper.getResult(3), "");
			chart.setTitle(new Title(title));
			request.setAttribute("chartHtml",
					chart.toHtml(request.getContextPath()));
		} catch (Exception e) {
			logger.error(e);
		}
		response.setCharacterEncoding("utf-8");

		return mapping.findForward("flashshow");
	}

	public ActionForward pieinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DynaActionForm initForm = (DynaActionForm) form;

		int t = Integer.parseInt(DateUtils.getDate().substring(8, 10));
		initForm.set("endDate", DateUtils.getDate(-t));

		request.setAttribute("unit.list", OptionUtils.getUnitList());
		request.setAttribute("year.list", OptionUtils.getYearList(2005));

		return mapping.findForward("pieinit");

	}

	/**
	 * ͨ通过flashchart显示饼图
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward pieshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// DynaActionForm initForm = (DynaActionForm) form;
		// String companyName = request.getParameter("companyName");
		String tobaccoName = request.getParameter("tobaccoName");
		// String unitName = request.getParameter("unitName");
		String queryId = request.getParameter("queryId");

		String title = tobaccoName + "各月销量图";

		String personId = getPersonId(request);
		CallHelper helper = initializeCallHelper("querySaleFlashPieChart",
				form, request, false);
		helper.setParam("userId", personId);
		helper.setParam("queryId", queryId);
		helper.setParam("unitId", "0");
		helper.execute();

		try {
			List data = helper.getResult(0);
			if (data == null || data.size() == 0)
				logger.info("数据集为空");
			PieChart chart = FlashChartUtil.getPieChart("my_chart", title,
					data, "箱");
			request.setAttribute("chartHtml",
					chart.toHtml(request.getContextPath()));
		} catch (Exception e) {
			logger.error(e);
		}
		response.setCharacterEncoding("utf-8");

		return mapping.findForward("pieshow");
	}

	/**
	 * 通过flashchart饼图钻取某月份卷烟销售数据
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showMonthSale(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		logger.debug("URI=" + request.getRequestURI());
		String queryId = request.getParameter("queryId");
		logger.debug("queryId=" + queryId);
		if (queryId == null)
			queryId = "1";
		String tobaccoName = "";
		if (Integer.parseInt(queryId) == 1) {
			tobaccoName = "湖北卷烟";
		} else if (Integer.parseInt(queryId) == 2) {
			tobaccoName = "黄鹤楼系列";
		} else {
			tobaccoName = "红金龙系列";
		}
		String year = request.getParameter("year") != null ? request
				.getParameter("year") : "2008";
		String month = request.getParameter("month") != null ? request
				.getParameter("month") : "1";

		String title = year + "年" + month + "月" + tobaccoName + "销量表";

		String personId = getPersonId(request);
		CallHelper helper = initializeCallHelper("queryMonthSaleDetail", form,
				request, false);
		helper.setParam("userId", personId);
		helper.setParam("queryId", queryId);
		helper.setParam("year", year);
		helper.setParam("month", month);
		helper.setParam("unitId", "0");
		helper.execute();

		request.setAttribute("title", title);
		request.setAttribute("captions", helper.getResult(0));
		request.setAttribute("results", helper.getResult(1));

		try {
			PieChart chart = FlashChartUtil.getPieChart("my_chart", title
					+ "(前十位)", helper.getResult(2), "箱");
			request.setAttribute("charthtml",
					chart.toHtml(request.getContextPath()));
		} catch (Exception e) {

		}

		return mapping.findForward("showMonthSale");
	}

	public ActionForward avgpriceflashshow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// DynaActionForm initForm = (DynaActionForm) form;
		String companyName = request.getParameter("companyName");
		String tobaccoName = request.getParameter("tobaccoName");
		// String unitName = request.getParameter("unitName");

		String title = companyName + tobaccoName; // +"各月销售均价变化图"

		CallHelper helper = initializeCallHelper("QueryAvgPriceFlash", form,
				request, false);
		helper.setParam("unitId", "1");
		helper.execute();

		try {
			XYChart chart = FlashChartUtil.getXYChart("my_chart",
					helper.getResult(0), helper.getResult(1),
					helper.getResult(2), helper.getResult(3), "");
			chart.setTitle(new Title(title));
			request.setAttribute("chartHtml",
					chart.toHtml(request.getContextPath()));
		} catch (Exception e) {
			logger.error(e);
		}
		response.setCharacterEncoding("utf-8");

		return mapping.findForward("flashshow");
	}

	public ActionForward areaflashshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// DynaActionForm initForm = (DynaActionForm) form;
		String companyName = request.getParameter("companyName");
		String tobaccoName = request.getParameter("tobaccoName");

		String month = request.getParameter("endDate").substring(5, 7);
		if (!("10".equalsIgnoreCase(month)))
			month = month.replace("0", "");

		String title = companyName + tobaccoName + "1-" + month + "月销量及变化图";

		CallHelper helper = initializeCallHelper("QueryAreaSaleFlash", form,
				request, false);
		helper.setParam("unitId", "1");
		helper.execute();

		try {
			XYChart chart = FlashChartUtil.getXYChart("my_chart",
					helper.getResult(0), helper.getResult(1),
					helper.getResult(2), helper.getResult(3), "");
			chart.setTitle(new Title(title));
			request.setAttribute("chartHtml",
					chart.toHtml(request.getContextPath()));
		} catch (Exception e) {
			logger.error(e);
		}
		response.setCharacterEncoding("utf-8");

		return mapping.findForward("flashshow");
	}

}
