package com.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.web.form.FactoryInfoForm;
import com.zl.base.core.db.SqlReturn;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;
import com.zl.util.TradeList;

//烟厂信息维护
public class FactoryInfoAction extends CoreDispatchAction {
	static Logger logger = Logger.getLogger(FactoryInfoAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {
			logger.error(e);
			return mapping.findForward("error");
		}
	}

	public ActionForward init(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SqlReturn spReturn = TradeList.g_getAllFactories();
		request.setAttribute("factorycaption.list", spReturn.getResultSet(0));
		request.setAttribute("factoryinfo.list", spReturn.getResultSet(1));
		return actionMapping.findForward("init");
	}

	public ActionForward add(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SqlReturn spReturn = TradeList.g_provinceQuery();
		request.setAttribute("province.list", spReturn.getResultSet(0));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		return actionMapping.findForward("add");
	}

	// 保存新增
	public ActionForward saveadd(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FactoryInfoForm factoryInfoForm = (FactoryInfoForm) form;
		SqlReturn spReturn = TradeList.g_saveFactory("1", "0",
				factoryInfoForm.getFactoryCode(),
				factoryInfoForm.getFactoryName(),
				factoryInfoForm.getFactoryAlias(),
				factoryInfoForm.getIsActive(),
				factoryInfoForm.getFactoryOldCode(),
				factoryInfoForm.getProvSystId(), "");
		request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", spReturn.getOutputParam(1));
		spReturn = TradeList.g_provinceQuery();
		request.setAttribute("province.list", spReturn.getResultSet(0));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());

		return actionMapping.findForward("saveadd");
	}

	public ActionForward modify(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String factSystId = MethodFactory.getThisString(request
				.getParameter("factSystId"));
		SqlReturn spReturn = TradeList.g_provinceQuery();
		request.setAttribute("province.list", spReturn.getResultSet(0));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());

		spReturn = TradeList.g_getFactoryInfo(factSystId);
		List<BasicDynaBean> factory = spReturn.getResultSet(0);
		FactoryInfoForm factoryInfoForm = (FactoryInfoForm) form;
		int i = 0;
		if (factory.size() > i) {
			BasicDynaBean rowBean = (BasicDynaBean) factory.get(i);
			factoryInfoForm.setFactSystId(MethodFactory.getThisString(rowBean
					.get("factsystid")));
			factoryInfoForm.setFactoryCode(MethodFactory.getThisString(rowBean
					.get("factorycode")));
			factoryInfoForm.setFactoryName(MethodFactory.getThisString(rowBean
					.get("factoryname")));
			factoryInfoForm.setFactoryAlias(MethodFactory.getThisString(rowBean
					.get("factoryalias")));
			factoryInfoForm.setIsActive(MethodFactory.getThisString(rowBean
					.get("isactive")));
			factoryInfoForm.setFactoryOldCode(MethodFactory
					.getThisString(rowBean.get("factoryoldcode")));
			factoryInfoForm.setProvSystId(MethodFactory.getThisString(rowBean
					.get("provsystid")));
		}

		return actionMapping.findForward("modify");
	}

	// 保存修改
	public ActionForward savemodify(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		FactoryInfoForm factoryInfoForm = (FactoryInfoForm) form;
		SqlReturn spReturn = TradeList.g_saveFactory("2",
				factoryInfoForm.getFactSystId(),
				factoryInfoForm.getFactoryCode(),
				factoryInfoForm.getFactoryName(),
				factoryInfoForm.getFactoryAlias(),
				factoryInfoForm.getIsActive(),
				factoryInfoForm.getFactoryOldCode(),
				factoryInfoForm.getProvSystId(), "");

		request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", spReturn.getOutputParam(1));
		spReturn = TradeList.g_provinceQuery();
		request.setAttribute("province.list", spReturn.getResultSet(0));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());

		return actionMapping.findForward("savemodify");
	}

}
