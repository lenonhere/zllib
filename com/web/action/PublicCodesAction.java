package com.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.web.form.PublicCodesForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.MethodFactory;

public class PublicCodesAction extends CoreDispatchAction {

	private static Log log = LogFactory.getLog(PublicCodesAction.class);

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
		String parentId = initOptions(request, form);
		initGrid(request, form, parentId);

		return mapping.findForward("init");
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		PublicCodesForm aform = (PublicCodesForm) form;
		initOptions(request, form);
		initGrid(request, form, aform.getFormparentclassid());

		return mapping.findForward("query");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String formparentclassid = request.getParameter("formparentclassid");
		PublicCodesForm aform = (PublicCodesForm) form;
		aform.setFormparentclassid(formparentclassid);
		initOptions(request, form);

		return mapping.findForward("add");
	}

	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String formparentclassid = request.getParameter("formparentclassid");
		String formclassid = request.getParameter("formclassid");
		PublicCodesForm aForm = (PublicCodesForm) form;
		initOptions(request, form);

		CallHelper helper = new CallHelper("maintPublicCodes");
		helper.setParam("maintType", "4");
		helper.setParam("parentId", formparentclassid);
		helper.setParam("classId", formclassid);
		helper.execute();

		List rows = helper.getResult("results");
		if (rows.size() > 0) {
			BasicDynaBean rowBean = (BasicDynaBean) rows.get(0);
			aForm.setFormparentclassid(MethodFactory.getThisString(rowBean
					.get("parentid")));
			aForm.setFormclassid(MethodFactory.getThisString(rowBean
					.get("classid")));
			aForm.setFormclasscode(MethodFactory.getThisString(rowBean
					.get("classcode")));
			aForm.setFormclassname(MethodFactory.getThisString(rowBean
					.get("classname")));
			aForm.setFormparentclassname(MethodFactory.getThisString(rowBean
					.get("parentname")));
			aForm.setFormcomment(MethodFactory.getThisString(rowBean
					.get("comment")));
		}

		return mapping.findForward("add");
	}

	public ActionForward saveadd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		PublicCodesForm aForm = (PublicCodesForm) form;

		CallHelper helper = new CallHelper("maintPublicCodes");
		helper.setParam("maintType", "1");
		helper.setParam("parentId", aForm.getFormparentclassid());
		helper.setParam("classCode", aForm.getFormclasscode());
		helper.setParam("className", aForm.getFormclassname());
		helper.setParam("comment", aForm.getFormcomment());
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));
		initOptions(request, form);

		return mapping.findForward("add");
	}

	public ActionForward savemodify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		PublicCodesForm aForm = (PublicCodesForm) form;

		CallHelper helper = new CallHelper("maintPublicCodes");
		helper.setParam("maintType", "2");
		helper.setParam("parentId", aForm.getFormparentclassid());
		helper.setParam("classId", aForm.getFormclassid());
		helper.setParam("classCode", aForm.getFormclasscode());
		helper.setParam("className", aForm.getFormclassname());
		helper.setParam("comment", aForm.getFormcomment());
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));
		initOptions(request, form);

		return mapping.findForward("savemodify");
	}

	public ActionForward delete(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String formparentclassid = request.getParameter("formparentclassid");
		String formclassid = request.getParameter("formclassid");

		CallHelper helper = new CallHelper("maintPublicCodes");
		helper.setParam("maintType", "3");
		helper.setParam("parentId", formparentclassid);
		helper.setParam("classId", formclassid);
		helper.execute();

		initOptions(request, form);

		return query(actionMapping, form, request, response);
	}

	public ActionForward save(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return actionMapping.findForward("save");
	}

	private String initOptions(HttpServletRequest request, ActionForm form) {
		String returnvalue = "-1";
		List list = getPublicCodes("0");
		request.setAttribute("parentclass.list", list);

		if (list.size() > 0) {
			BasicDynaBean rowBean = (BasicDynaBean) list.get(0);
			returnvalue = MethodFactory.getThisString(rowBean.get("id"));
		}
		return returnvalue;
	}

	private void initGrid(HttpServletRequest request, ActionForm form,
			String parentId) {

		CallHelper helper = new CallHelper("maintPublicCodes");
		helper.setParam("maintType", "4");
		helper.setParam("parentId", parentId);
		helper.execute();

		if (helper.getResultCount() >= 2) {
			request.setAttribute("publiccodes.captionlist",
					helper.getResult("captions"));
			request.setAttribute("publiccodes.list",
					helper.getResult("results"));
		}
	}
}
