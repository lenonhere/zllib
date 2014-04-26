package com.web.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.web.form.CompanyToForm;
import com.zl.base.core.db.CallHelper;

public class CompanyToAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(CompanyToAction.class);

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

		setAreaCompanyTree(form, request);
		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CompanyToForm aForm = (CompanyToForm) form;
		setCompanyDest(aForm, request);
		return mapping.findForward("show");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CompanyToForm aForm = (CompanyToForm) form;
		// String userid = getPersonId(request);
		// aForm.setUserId(userid);
		// String userid = getPersonId(request);
		// aForm.setUserId(userid);
		// aForm.setCompsystid(request.getParameter("compsystid"));
		CallHelper caller = new CallHelper("saveCompanyDest");
		caller.setParam("companyid", aForm.getCompsystid());
		caller.setParam("destid", aForm.getDestid());
		caller.setParam("whouseid", aForm.getWhouseid());
		caller.execute();

		String msgInfo = (String) caller.getOutput(0); // message
		int msgCode = caller.getState();
		request.setAttribute("message.information", msgInfo);
		request.setAttribute("message.code", String.valueOf(msgCode));
		setCompanyDest(aForm, request);
		return mapping.findForward("show");
	}

	// 获得区域树
	protected void setAreaCompanyTree(ActionForm form,
			HttpServletRequest request) {
		CallHelper caller = initializeCallHelper(
				"getAreaCompanyTreeOutProvGatherInRight", form, request, false);
		// caller.setParam("areaCode", "900000");
		// caller.setParam("userId",this.getPersonId(request));
		caller.execute();
		request.setAttribute("companyTree", caller.getResult(0));

	}

	// 显示卷烟
	protected void setCompanyDest(ActionForm form, HttpServletRequest request) {

		CompanyToForm aForm = (CompanyToForm) form;
		CallHelper helper = initializeCallHelper("get_CompanyDest", aForm,
				request, false);
		helper.setParam("compsystid", aForm.getCompsystid());
		helper.execute();
		ArrayList destlist = (ArrayList) helper.getResult(0);
		request.setAttribute("companydest.list", destlist);
		request.setAttribute("whouse.list", helper.getResult(1));

	}

	public ActionForward subdetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CompanyToForm aForm = (CompanyToForm) form;
		CallHelper helper = initializeCallHelper("get_CompToErpQuery", aForm,
				request, false);
		helper.setParam("destid", request.getParameter("destid"));
		helper.execute();
		ArrayList destlist = (ArrayList) helper.getResult(0);
		request.setAttribute("compdest.list", destlist);
		return mapping.findForward("subdetail");
	}

	public ActionForward subsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CompanyToForm aForm = (CompanyToForm) form;
		CallHelper caller = new CallHelper("saveComperprel");
		caller.setParam("destid", request.getParameter("destid"));
		caller.setParam("infomation", request.getParameter("infomation"));
		caller.execute();

		String msgInfo = (String) caller.getOutput(0); // message
		int msgCode = caller.getState();
		request.setAttribute("message.information", msgInfo);
		request.setAttribute("message.code", String.valueOf(msgCode));
		setCompanyDest(aForm, request);
		return subdetail(mapping, form, request, response);
	}
}
