package com.zl.opt;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.zl.base.core.db.CallHelper;
import com.zl.core.HTDispatchAction;
import com.zl.util.TradeList;

public class ModifyAddress extends HTDispatchAction {
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("company.list", TradeList.getcompany());
		CallHelper helper = initializeCallHelper("prc_getModifycomp", form,
				request, false);
		helper.setParam("userId", this.getPersonId(request));
		helper.execute();
		request.setAttribute("results.list", helper.getResult(0));
		return mapping.findForward("init");
	}

	public ActionForward check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String pass = "1";
		DynaActionForm aform = (DynaActionForm) form;
		String companyIdSet = "" + aform.get("companyIdSet");
		if (companyIdSet != null && !"".equals(companyIdSet.trim())) {
			String[] id = companyIdSet.split(",");
			CallHelper helper = initializeCallHelper("prc_checkModifycomp",
					form, request, false);
			helper.setParam("userId", this.getPersonId(request));
			helper.setParam("pass", pass);
			for (int i = 0; i < id.length; i++) {
				helper.setParam("billid", id[i]);
				helper.execute();
			}
		}
		return init(mapping, form, request, response);
	}

	public ActionForward init2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("prc_getModifycomp", form,
				request, false);
		helper.setParam("userId", this.getPersonId(request));
		helper.execute();
		request.setAttribute("results.list", helper.getResult(0));
		return mapping.findForward("init2");
	}

	public ActionForward check2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String pass = request.getParameter("pass");
		DynaActionForm aform = (DynaActionForm) form;
		String companyIdSet = "" + aform.get("companyIdSet");
		if (companyIdSet != null && !"".equals(companyIdSet.trim())) {
			String[] id = companyIdSet.split(",");
			CallHelper helper = initializeCallHelper("prc_checkModifycomp",
					form, request, false);
			helper.setParam("userId", this.getPersonId(request));
			helper.setParam("pass", pass);
			for (int i = 0; i < id.length; i++) {
				helper.setParam("billid", id[i]);
				helper.execute();
			}
		}
		return init2(mapping, form, request, response);
	}

	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("companyIdSet");
		List list = TradeList.getcompanyInfo(id);
		if (list != null && list.size() > 0) {
			this.copyProperties(form, list, 0, true, true);
		}
		return mapping.findForward("modify");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("prc_saveModifycomp", form,
				request, false);
		helper.setParam("userId", this.getPersonId(request));
		helper.execute();
		return init(mapping, form, request, response);
	}
}
