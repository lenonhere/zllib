package com.web.action.business.daily;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.web.form.business.daily.CustonLineForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.OptionUtils;

public class CustonLineAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(CustonLineAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return mapping.findForward("error");
		}
	}

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CustonLineForm bform = (CustonLineForm) form;

		String personId = getPersonId(request);
		CallHelper caller = initializeCallHelper(
				"getAreaCompanyTreeOutProvGatherInRight", bform, request, false);
		caller.setParam("areaCode", "999999");
		caller.setParam("userId", personId);
		caller.execute();
		List companyList = caller.getResult(0);
		request.setAttribute("company.tree", companyList);
		// bform.setBeginDate(MethodFactory.getDate().replace("/", "-"));
		// CallHelper helper = initializeCallHelper("getPhoneDepartGradeList",
		// form, request, false);
		// helper.setParam("personid", getPersonId(request));
		// helper.execute();
		// request.setAttribute("depart.list", helper.getResult("results"));
		// getdepartlist(request,form);
		return mapping.findForward("init");
	}

	public ActionForward init_monitor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CustonLineForm bform = (CustonLineForm) form;
		ArrayList list = OptionUtils.getSeatno();
		request.setAttribute("seatno.list", list);
		return mapping.findForward("init_monitor");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CustonLineForm aform = (CustonLineForm) form;
		CallHelper caller = initializeCallHelper("queryShopInfo", aform,
				request, false);
		caller.execute();
		request.setAttribute("caption.list", caller.getResult(0));
		request.setAttribute("result.list", caller.getResult(1));
		return mapping.findForward("list");
	}

}
