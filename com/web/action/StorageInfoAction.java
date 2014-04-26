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
import com.web.form.WhouseDetailForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;

public class StorageInfoAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(StorageInfoAction.class);

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
		setFactoryTree(form, request);
		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WhouseDetailForm whouseForm = (WhouseDetailForm) form;
		whouseForm.setPfactoryid(request.getParameter("factoryid"));
		CallHelper call = initializeCallHelper("whouseInfoShow", whouseForm,
				request, false);
		call.execute();
		request.setAttribute("captions.list", call.getResult("captions"));
		request.setAttribute("results.list", call.getResult("results"));
		return mapping.findForward("show");
	}

	public ActionForward maintain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WhouseDetailForm whouseForm = (WhouseDetailForm) form;
		request.setAttribute("flag.list", OptionUtils.getFlagList());
		request.setAttribute("locfactory.list", OptionUtils.getLocFactory());
		request.setAttribute("confactory.list", OptionUtils.getConFactory());
		CallHelper helper = initializeCallHelper("whouseMaintain", whouseForm,
				request, false);
		helper.execute();
		List whouse = helper.getResult("results");
		if (whouse.size() > 0) {
			BasicDynaBean rowBean = (BasicDynaBean) whouse.get(0);
			MethodFactory.copyProperties(whouseForm, rowBean);
		}
		return mapping.findForward("maintain");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WhouseDetailForm whouseForm = (WhouseDetailForm) form;
		CallHelper call = initializeCallHelper("whouseSave", whouseForm,
				request, false);
		call.execute();
		request.setAttribute("message", call.getOutput("message").toString());
		return mapping.findForward("show");
	}

	// 获得区域树
	protected void setFactoryTree(ActionForm form, HttpServletRequest request) {
		CallHelper caller = initializeCallHelper("getFactoryAllTree", form,
				request, false);
		caller.execute();
		request.setAttribute("factoryTree", caller.getResult(0));
	}

}
