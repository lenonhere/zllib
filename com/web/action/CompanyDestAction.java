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
import com.zl.base.core.db.Executer;
import com.zl.base.core.db.SqlReturn;

public class CompanyDestAction extends CriterionAction {

	private static final Log log = LogFactory.getLog(CompanyDestAction.class);

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

		setDestTree(form, request);
		return mapping.findForward("show");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("companyDestSave", form,
				request, false);
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		setDestTree(form, request);

		return mapping.findForward("show");
	}

	// 获得区域树
	protected void setAreaCompanyTree(ActionForm form,
			HttpServletRequest request) {
		CallHelper caller = initializeCallHelper("getAreaCompanyTreeInRight",
				form, request, false);
		caller.setParam("areaCode", "420000");
		caller.setParam("userId", this.getPersonId(request));
		caller.execute();
		request.setAttribute("companyTree", caller.getResult(0));

	}

	protected void setDestTree(ActionForm form, HttpServletRequest request) {
		DynaActionForm DynaForm = (DynaActionForm) form;

		CallHelper helper = initializeCallHelper("getComDestTree", DynaForm,
				request, false);
		helper.execute();

		request.setAttribute("dest.tree", helper.getResult("results"));
	}

	/**
	 * 生产点对仓库（朱忠南 2009-5-14）
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward prodToHouse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SqlReturn sr = Executer
				.getInstance()
				.ExecSeletSQL(
						"select whouseid as nodeid,whousealias as nodetext,0 as parentid from g_whouse");
		request.setAttribute("collection", sr.getResultSet());
		return mapping.findForward("prodToHouse");
	}

	/**
	 * 生产点对仓库（朱忠南 2009-5-14）
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward prodToHouseShow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm DynaForm = (DynaActionForm) form;

		CallHelper helper = initializeCallHelper("getProdToHouseTree",
				DynaForm, request, false);
		helper.execute();

		request.setAttribute("dest.tree", helper.getResult("results"));
		return mapping.findForward("prodToHouseShow");
	}

	public ActionForward prodToHouseSave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CallHelper helper = initializeCallHelper("saveProdToHouse", form,
				request, false);
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		return prodToHouseShow(mapping, form, request, response);
	}

}
