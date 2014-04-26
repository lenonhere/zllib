package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.qmx.dateutils.DateUtils;
import com.web.form.ProductionPlanForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.OptionUtils;

public class ProductionPlanAction extends CriterionAction {

	private static final Log log = LogFactory
			.getLog(ProductionPlanAction.class);

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

	public ActionForward initjg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ProductionPlanForm ppForm = (ProductionPlanForm) form;
		ppForm.setPlanDate(DateUtils.getDate(1));
		ppForm.setUnitId("1");

		request.setAttribute("unit.list", OptionUtils.getUnitList());

		return mapping.findForward("initjg");
	}

	public ActionForward showjg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("getJgPlan", form, request,
				false);
		helper.execute();

		// request.setAttribute("caption.list", helper.getResult("captions"));
		request.setAttribute("result.list", helper.getResult("results"));

		return mapping.findForward("showjg");
	}

	public ActionForward savejg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("saveJgPlan", form, request,
				false);
		helper.execute();

		request.setAttribute("message", helper.getOutput("message"));
		return mapping.findForward("savejg");
	}

	public ActionForward submitjg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("submitjg");
	}

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ProductionPlanForm ppForm = (ProductionPlanForm) form;
		ppForm.setPlanDate(DateUtils.getDate(1));
		ppForm.setUnitId("1");

		request.setAttribute("unit.list", OptionUtils.getUnitList());

		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("getProductionPlan", form,
				request, false);
		helper.execute();

		// request.setAttribute("caption.list", helper.getResult("captions"));
		request.setAttribute("result.list", helper.getResult("results"));

		return mapping.findForward("show");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("saveProductionPlan", form,
				request, false);
		helper.execute();

		request.setAttribute("message", helper.getOutput("message"));
		return mapping.findForward("save");
	}

	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("submit");
	}
}
