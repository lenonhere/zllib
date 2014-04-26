package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.zl.base.core.db.CallHelper;

public class ShippingWarehouseAction extends CriterionAction {

	private static final Log log = LogFactory
			.getLog(ShippingWarehouseAction.class);

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

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.set("userId", getPersonId(request));
		dynaForm.set("flags", "01");
		CallHelper helper = this.initializeCallHelper("getsycompmore", form,
				request, false);
		helper.execute();

		request.setAttribute("warehouse.tree", helper.getResult("results"));

		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.set("userId", getPersonId(request));
		dynaForm.set("companyId", request.getParameter("companyId"));
		dynaForm.set("flags", "03");
		CallHelper helper = initializeCallHelper("getsycompmore", form,
				request, false);
		helper.execute();

		request.setAttribute("company.tree", helper.getResult("results"));

		return mapping.findForward("show");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("saveCompany4Warehouse", form,
				request, false);
		helper.execute();

		return this.show(mapping, form, request, response);
	}

	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper(
				"findCompanyWarehouseRelations", form, request, false);
		helper.execute();

		request.setAttribute("caption.list", helper.getResult("captions"));
		request.setAttribute("result.list", helper.getResult("results"));

		return mapping.findForward("detail");
	}
}
