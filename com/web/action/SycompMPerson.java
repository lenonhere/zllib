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

public class SycompMPerson extends CriterionAction {

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


		PersonSelectCompanyForm aform = (PersonSelectCompanyForm) form;
		aform.setDepartId(getDepartId(request));
		CallHelper helper = initializeCallHelper("SycompMPersonTreeQuery",
				aform, request, false);
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		PersonSelectCompanyForm aform = (PersonSelectCompanyForm) form;
		CallHelper helper = initializeCallHelper("SycompMcompanyTreeQuery",
				aform, request, false);
		helper.setParam("managerId", getPersonId(request));

		helper.execute();

		request.setAttribute("areacompany.tree", helper.getResult("results"));
		return mapping.findForward("show");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PersonSelectCompanyForm aform = (PersonSelectCompanyForm) form;
		CallHelper helper = initializeCallHelper("savePersonCompany", aform,
				request, false);
		helper.setParam("giverId", getPersonId(request));
		helper.setParam("managerId", getPersonId(request));
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		return show(mapping,aform,request,response);

	}

	protected void setAreaCompanyTree(ActionForm form, HttpServletRequest request) {
		PersonSelectCompanyForm psaForm = (PersonSelectCompanyForm) form;

		psaForm.setAreaCode("");

		CallHelper helper = initializeCallHelper("getAreaCompanyTreeUseRight",
				psaForm, request, false);
		helper.execute();

		request.setAttribute("areacompany.tree", helper.getResult("results"));
	}
	//分县公司关系维护
	public ActionForward companysinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {


		PersonSelectCompanyForm aform = (PersonSelectCompanyForm) form;
		aform.setDepartId(getDepartId(request));
		//CallHelper helper = initializeCallHelper("SycompMPersonTreeQuery",
		CallHelper helper = initializeCallHelper("getAreaCompanyTreeInRight",
				aform, request, false);
		//helper.execute();
		helper.setParam("areaCode","");
    	helper.setParam("userId",getPersonId(request));
		helper.execute();
		request.setAttribute("areacompany.tree", helper.getResult(0));
		//request.setAttribute("person.tree", helper.getResult("results"));
		return mapping.findForward("companysinit");
	}

	public ActionForward companysshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		PersonSelectCompanyForm aform = (PersonSelectCompanyForm) form;
		String SyCompSystId=request.getParameter("compsystid");
		aform.setAreaCode("999999"); //set("areaCode", "999999");
		CallHelper helper = initializeCallHelper("SycompScompanyTreeQuery",
				aform, request, false);//SycompScompanyTreeQuery--getAreaCompanyReportedInRight
		helper.setParam("compsystid", SyCompSystId);
		helper.setParam("userId",getPersonId(request));
		helper.execute();

		request.setAttribute("areacompany.tree", helper.getResult(0));
		return mapping.findForward("companysshow");
	}

	public ActionForward companyssave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PersonSelectCompanyForm aform = (PersonSelectCompanyForm) form;
		CallHelper helper = initializeCallHelper("saveSCompany", aform,
				request, false);
		helper.setParam("giverId", getPersonId(request));
		helper.setParam("managerId", getPersonId(request));
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		return companysshow(mapping,aform,request,response);

	}

	//总监选区域
	public ActionForward majorareainit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		PersonSelectCompanyForm aform = (PersonSelectCompanyForm) form;

		CallHelper helper = initializeCallHelper("majorTreeQuery",
				aform, request, false);

		helper.execute();

		request.setAttribute("person.tree", helper.getResult(0));
		return mapping.findForward("majorareainit");
	}

	public ActionForward majorareashow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		PersonSelectCompanyForm aform = (PersonSelectCompanyForm) form;
		String compsystid=request.getParameter("compsystid");

		CallHelper helper = initializeCallHelper("majorAreaTreeQuery",
				aform, request, false);
		//aform.setPersonid(personId);
		helper.setParam("compsystid",compsystid);
		helper.execute();

		request.setAttribute("arealist.tree", helper.getResult(0));
		return mapping.findForward("majorareashow");
	}

	public ActionForward majorareasave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PersonSelectCompanyForm aform = (PersonSelectCompanyForm) form;


		CallHelper helper = initializeCallHelper("saveMajorArea", aform,
				request, false);

		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));
		return majorareashow(mapping,aform,request,response);
	}
}

