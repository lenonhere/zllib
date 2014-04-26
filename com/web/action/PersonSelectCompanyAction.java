package com.web.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.form.PersonSelectCompanyForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.TradeList;

public class PersonSelectCompanyAction extends CriterionAction {

	private static final Log log = LogFactory
			.getLog(PersonSelectCompanyAction.class);

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

		setPersonTree(form, request);
		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		setAreaCompanyTree(form, request);
		return mapping.findForward("show");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("savePersonCompany", form,
				request, false);
		helper.setParam("giverId", getPersonId(request));
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		setAreaCompanyTree(form, request);
//      记录保存人员选公司操作日志
		PersonSelectCompanyForm dynaForm = (PersonSelectCompanyForm) form;
        String personId = getPersonId(request);
		String ip = request.getRemoteHost();
		String userId = dynaForm.getUserId();
		TradeList.operLog(personId,99030401,userId,ip);  

		return mapping.findForward("show");
	}

	protected void setPersonTree(ActionForm form, HttpServletRequest request) {

		//CallHelper helper = new CallHelper("getPersonTree");
		//helper.execute();
		
		CallHelper helper = initializeCallHelper("getDepareAreaPersonTreeAll",
				form, request, false);
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
	}

	protected void setAreaCompanyTree(ActionForm form, HttpServletRequest request) {
		PersonSelectCompanyForm psaForm = (PersonSelectCompanyForm) form;
		
		psaForm.setAreaCode("");
		
		CallHelper helper = initializeCallHelper("getAreaCompanyTreeUseRightAll",
				psaForm, request, false);
		helper.execute();

		request.setAttribute("areacompany.tree", helper.getResult("results"));
	}
}

