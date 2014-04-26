package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.web.form.CompStationForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.OptionUtils;

public class CompStationAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(CompStationAction.class);

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
		request.setAttribute("type", request.getParameter("type") == null ? "0"
				: "1");
		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CompStationForm aForm = (CompStationForm) form;
		aForm.setSycompsystid(request.getParameter("compsystid"));
		request.setAttribute("type", request.getParameter("type"));
		// aForm.setType("1");
		// CallHelper caller =
		// initializeCallHelper("querycompstation", form, request, false);
		//
		// CallHelper caller2 =
		// initializeCallHelper("querycompstation", form, request, false);
		// caller2.setParam("sycompsystid", aForm.getSycompsystid());
		// caller2.execute();
		// request.setAttribute("captions.list", caller2.getResult(0));
		// request.setAttribute("results.list", caller2.getResult(1));
		//
		// CallHelper caller3 =
		// initializeCallHelper("getoptcompstation", form, request, false);
		// caller3.setParam("sycompsystid", aForm.getSycompsystid());
		// caller3.execute();
		// request.setAttribute("captions2.list", caller3.getResult(0));
		// request.setAttribute("results2.list", caller3.getResult(1));
		return mapping.findForward("show");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CompStationForm aForm = (CompStationForm) form;
		setCompanyDest(aForm, request);
		/*
		 * CallHelper caller = initializeCallHelper("querysaleperson", form,
		 * request, false); caller.execute(); CallHelper caller1 =
		 * initializeCallHelper("querytradeperson", form, request, false);
		 * caller1.execute(); request.setAttribute("captions.list",
		 * caller.getResult(0)); request.setAttribute("results.list",
		 * caller.getResult(1)); request.setAttribute("captions1.list",
		 * caller1.getResult(0)); request.setAttribute("results1.list",
		 * caller1.getResult(1));
		 */
		return mapping.findForward("add");
	}

	public ActionForward addsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CompStationForm aForm = (CompStationForm) form;
		CallHelper caller = new CallHelper("savecompstation");
		caller.setParam("stationname", aForm.getStationname());
		caller.setParam("linkname", aForm.getLinkname());
		caller.setParam("tel", aForm.getTel());
		caller.setParam("addrdec", aForm.getAddrdec());
		caller.setParam("memo", aForm.getMemo());
		caller.setParam("salepersonid", aForm.getSalepersonid());
		caller.setParam("tradepersonname", aForm.getTradepersonname());
		caller.setParam("sycompsystid", aForm.getSycompsystid());
		caller.setParam("isactive", aForm.getIsactive());
		caller.execute();
		String message = caller.getOutput("message").toString();
		request.setAttribute("message", message);
		return mapping.findForward("add");
	}

	public ActionForward qryisstation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CompStationForm aForm = (CompStationForm) form;
		// setCompanyDest(aForm, request);

		if (!(aForm.getSycompsystid() != null)
				|| !(aForm.getSycompsystid() != ""))
			aForm.setSycompsystid(request.getParameter("companyid"));
		CallHelper caller = initializeCallHelper("querycompstation", form,
				request, false);
		caller.setParam("sycompsystid", aForm.getSycompsystid());
		caller.execute();
		request.setAttribute("captions.list", caller.getResult(0));
		request.setAttribute("results.list", caller.getResult(1));
		return mapping.findForward("qryisstation");
	}

	public ActionForward qrynostation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CompStationForm aForm = (CompStationForm) form;
		setCompanyDest(aForm, request);
		if (!request.getParameter("companyid").equals("")
				|| !request.getParameter("companyid").equals(null)) {
			aForm.setSycompsystid(request.getParameter("companyid"));
		}
		CallHelper caller = initializeCallHelper("getoptcompstation", form,
				request, false);
		caller.setParam("sycompsystid", aForm.getSycompsystid());
		caller.execute();
		request.setAttribute("captions2.list", caller.getResult(0));
		request.setAttribute("results2.list", caller.getResult(1));
		return mapping.findForward("qrynostation");
	}

	public ActionForward custlinker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CompStationForm aForm = (CompStationForm) form;
		request.setAttribute("custlink", "custlink");
		CallHelper caller =

		initializeCallHelper("querysaleperson", aForm, request, false);
		// caller.setParam("sycompsystid", request.getParameter("companyid"));

		CallHelper caller1 =

		initializeCallHelper("querytradeperson", aForm, request, false);
		// caller1.setParam("sycompsystid", request.getParameter("companyid"));
		if (request.getParameter("companyid") == null
				| "".equals(request.getParameter("companyid"))) {
			caller.setParam("sycompsystid", aForm.getSycompsystid());
			caller1.setParam("sycompsystid", aForm.getSycompsystid());
		} else {
			caller.setParam("sycompsystid", request.getParameter("companyid"));
			caller1.setParam("sycompsystid", request.getParameter("companyid"));
		}
		caller.execute();
		caller1.execute();
		request.setAttribute("captions.list", caller.getResult(0));
		request.setAttribute("results.list", caller.getResult(1));
		request.setAttribute("captions1.list", caller1.getResult(0));
		request.setAttribute("results1.list", caller1.getResult(1));
		return mapping.findForward("custlinker");
	}

	public ActionForward salecustlinker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CompStationForm aForm = (CompStationForm) form;
		request.setAttribute("custlink", "salecustlink");
		CallHelper caller =

		initializeCallHelper("querysaleperson", aForm, request, false);

		CallHelper caller1 =

		initializeCallHelper("querytradeperson", aForm, request, false);
		if (request.getParameter("companyid") == null
				| "".equals(request.getParameter("companyid"))) {
			caller.setParam("sycompsystid", aForm.getSycompsystid());
			caller1.setParam("sycompsystid", aForm.getSycompsystid());
		} else {
			caller.setParam("sycompsystid", request.getParameter("companyid"));
			caller1.setParam("sycompsystid", request.getParameter("companyid"));
		}
		caller.execute();

		caller1.execute();
		request.setAttribute("captions.list", caller.getResult(0));
		request.setAttribute("results.list", caller.getResult(1));
		request.setAttribute("captions1.list", caller1.getResult(0));
		request.setAttribute("results1.list", caller1.getResult(1));
		return mapping.findForward("salecustlinker");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

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

	// 显示公司
	protected void setCompanyDest(ActionForm form, HttpServletRequest request) {

		// companytoForm aForm = (companytoForm) form;
		// CallHelper helper = initializeCallHelper("get_CompanyDest",
		// aForm, request, false);
		// helper.setParam("compsystid",aForm.getCompsystid());
		// helper.execute();
		// ArrayList destlist = (ArrayList)helper.getResult(0);
		// request.setAttribute("companydest.list", destlist);
		// request.setAttribute("whouse.list", helper.getResult(1));

	}

	public ActionForward blankinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("blankinit");
	}

	public ActionForward salepersonselect(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("getsaleperson", form,
				request, false);
		// helper.setParam("personname", aForm.getPersonname());
		helper.execute();
		request.setAttribute("captions.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));
		return mapping.findForward("salepersonselect");
	}

	// 商业公司责任人
	public ActionForward tradeperson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("persontype.list",
				OptionUtils.getcompersontypeList());
		return mapping.findForward("tradeperson");
	}

	public ActionForward complinkersave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CompStationForm aForm = (CompStationForm) form;

		if ("salemat".equals(aForm.getInfomation())) {

			return salecustlinker(mapping, aForm, request, response);

		} else {
			CallHelper caller = initializeCallHelper("savecomplinker", aForm,
					request, false);
			caller.execute();
			String message = caller.getOutput("message").toString();
			request.setAttribute("message", message);
			return custlinker(mapping, aForm, request, response);
		}
	}

	public ActionForward updateisdefault(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CompStationForm aForm = (CompStationForm) form;

		CallHelper caller = new CallHelper("updatecompstadefault");
		caller.setParam("sycompsystid", request.getParameter("sycompsystid"));
		caller.setParam("stationid", request.getParameter("stationid"));
		caller.setParam("stationname", request.getParameter("stationname"));
		caller.execute();
		String message = caller.getOutput("message").toString();
		request.setAttribute("message", message);
		aForm.setSycompsystid(request.getParameter("companyid"));
		CallHelper caller2 = initializeCallHelper("querycompstation", form,
				request, false);
		caller2.setParam("sycompsystid", aForm.getSycompsystid());
		caller2.execute();
		request.setAttribute("captions.list", caller2.getResult(0));
		request.setAttribute("results.list", caller2.getResult(1));
		return mapping.findForward("qryisstation");

	}

	public ActionForward editsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CompStationForm aForm = (CompStationForm) form;
		CallHelper caller = new CallHelper("editcompstation");
		caller.setParam("stationid", aForm.getStationid());
		caller.setParam("stationname", aForm.getStationname());
		caller.setParam("linkname", aForm.getLinkname());
		caller.setParam("tel", aForm.getTel());
		caller.setParam("addrdec", aForm.getAddrdec());
		caller.setParam("memo", aForm.getMemo());
		caller.setParam("salepersonid", aForm.getSalepersonid());
		caller.setParam("tradepersonname", aForm.getTradepersonname());
		caller.setParam("sycompsystid", aForm.getSycompsystid());
		caller.setParam("isactive", aForm.getIsactive());
		caller.execute();
		String message = caller.getOutput("message").toString();
		request.setAttribute("message", message);
		return mapping.findForward("edit");
	}

	// 查询修改信息
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CompStationForm aForm = (CompStationForm) form;
		// String stationid = request.getParameter("stationid");
		CallHelper helper = initializeCallHelper("getmodifycompstation", aForm,
				request, false);
		helper.setParam("stationid", request.getParameter("stationid"));
		helper.execute();
		copyProperties(aForm, helper.getResult(0), 0, true, true);
		// System.out.println(stationid);
		return mapping.findForward("edit");
	}

	public ActionForward qrynostationsave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CompStationForm aForm = (CompStationForm) form;
		// aForm.setSycompsystid(request.getParameter("companyid"));
		CallHelper call = initializeCallHelper("getoptcompstationsave", form,
				request, false);
		call.setParam("infomation", aForm.getInfomation());
		call.setParam("sycompsystid", aForm.getSycompsystid());
		call.execute();
		CallHelper caller = initializeCallHelper("getoptcompstation", form,
				request, false);
		caller.setParam("sycompsystid", aForm.getSycompsystid());
		caller.execute();
		request.setAttribute("captions2.list", caller.getResult(0));
		request.setAttribute("results2.list", caller.getResult(1));
		String message = (String) call.getOutput("message");
		request.setAttribute("message", message);
		return mapping.findForward("qrynostationsave");
	}

}
