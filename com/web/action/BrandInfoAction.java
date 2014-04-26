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
import com.web.form.BrandInfoForm;
import com.zl.base.core.db.SqlReturn;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;
import com.zl.util.TradeList;

//卷烟系列信息维护
public class BrandInfoAction extends CoreDispatchAction {
	static Logger logger = Logger.getLogger(BrandInfoAction.class.getName());

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
		SqlReturn spReturn = TradeList.g_getBrands();
		request.setAttribute("brandcaption.list", spReturn.getResultSet(0));
		request.setAttribute("brandinfo.list", spReturn.getResultSet(1));
		return actionMapping.findForward("init");
	}

	public ActionForward add(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		return actionMapping.findForward("add");
	}

	// 保存新增
	public ActionForward saveadd(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		BrandInfoForm brandInfoForm = (BrandInfoForm) form;
		String brandCode = brandInfoForm.getBrandCode();
		String brandName = brandInfoForm.getBrandName();
		String isActive = brandInfoForm.getIsActive();
		SqlReturn spReturn = TradeList.g_saveBrand("1", brandCode, brandName,
				isActive, "");
		request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", spReturn.getOutputParam(1));
		spReturn = TradeList.g_getBrands();
		request.setAttribute("brand.list", spReturn.getResultSet(0));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());

		return actionMapping.findForward("saveadd");
	}

	public ActionForward modify(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String brandCode = MethodFactory.getThisString(request
				.getParameter("brandCode"));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());

		SqlReturn spReturn = TradeList.g_getBrandInfo(brandCode);
		List<BasicDynaBean> brand = spReturn.getResultSet(0);
		BrandInfoForm brandInfoForm = (BrandInfoForm) form;
		int i = 0;
		if (brand.size() > i) {
			BasicDynaBean rowBean = (BasicDynaBean) brand.get(i);
			brandInfoForm.setBrandCode(MethodFactory.getThisString(rowBean
					.get("brandcode")));
			brandInfoForm.setBrandName(MethodFactory.getThisString(rowBean
					.get("brandname")));
			brandInfoForm.setIsActive(MethodFactory.getThisString(rowBean
					.get("isactive")));
		}

		return actionMapping.findForward("modify");
	}

	// 保存修改
	public ActionForward savemodify(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		BrandInfoForm brandInfoForm = (BrandInfoForm) form;
		String brandCode = brandInfoForm.getBrandCode();
		String brandName = brandInfoForm.getBrandName();
		String isActive = brandInfoForm.getIsActive();
		SqlReturn spReturn = TradeList.g_saveBrand("2", brandCode, brandName,
				isActive, "");
		request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", spReturn.getOutputParam(1));
		spReturn = TradeList.g_getBrands();
		request.setAttribute("brand.list", spReturn.getResultSet(0));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());

		return actionMapping.findForward("savemodify");
	}

}
