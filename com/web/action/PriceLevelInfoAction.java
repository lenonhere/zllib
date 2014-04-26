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
import com.web.form.PriceLevelInfoForm;
import com.zl.base.core.db.SqlReturn;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;
import com.zl.util.TradeList;

//价格档次信息维护
public class PriceLevelInfoAction extends CoreDispatchAction {
	static Logger logger = Logger.getLogger(PriceLevelInfoAction.class
			.getName());

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
		SqlReturn spReturn = TradeList.g_getAllPriceLevels();
		request.setAttribute("pricelevelcaption.list", spReturn.getResultSet(0));
		request.setAttribute("pricelevelinfo.list", spReturn.getResultSet(1));
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
		PriceLevelInfoForm priceLevelInfoForm = (PriceLevelInfoForm) form;

		SqlReturn spReturn = TradeList.g_savePriceLevel("1", "0",
				priceLevelInfoForm.getPriceLevelName(),
				priceLevelInfoForm.getPriceLevelCode(),
				priceLevelInfoForm.getLowLimitPrice(),
				priceLevelInfoForm.getTopLimitPrice(),
				priceLevelInfoForm.getIsActive(), "");
		request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", spReturn.getOutputParam(1));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());

		return actionMapping.findForward("saveadd");
	}

	public ActionForward modify(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String priceLevelId = MethodFactory.getThisString(request
				.getParameter("priceLevelId"));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());

		SqlReturn spReturn = TradeList.g_getPriceLevelInfo(priceLevelId);
		List<BasicDynaBean> priceLevel = spReturn.getResultSet(0);
		PriceLevelInfoForm priceLevelInfoForm = (PriceLevelInfoForm) form;
		int i = 0;
		if (priceLevel.size() > i) {
			BasicDynaBean rowBean = (BasicDynaBean) priceLevel.get(i);
			priceLevelInfoForm.setPriceLevelId(MethodFactory
					.getThisString(rowBean.get("pricelevelid")));
			priceLevelInfoForm.setPriceLevelName(MethodFactory
					.getThisString(rowBean.get("pricelevelname")));
			priceLevelInfoForm.setPriceLevelCode(MethodFactory
					.getThisString(rowBean.get("pricelevelcode")));
			priceLevelInfoForm.setLowLimitPrice(MethodFactory
					.getThisString(rowBean.get("lowlimitprice")));
			priceLevelInfoForm.setTopLimitPrice(MethodFactory
					.getThisString(rowBean.get("toplimitprice")));
			priceLevelInfoForm.setIsActive(MethodFactory.getThisString(rowBean
					.get("isactive")));
		}

		return actionMapping.findForward("modify");
	}

	// 保存修改
	public ActionForward savemodify(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PriceLevelInfoForm priceLevelInfoForm = (PriceLevelInfoForm) form;
		SqlReturn spReturn = TradeList.g_savePriceLevel("2",
				priceLevelInfoForm.getPriceLevelId(),
				priceLevelInfoForm.getPriceLevelName(),
				priceLevelInfoForm.getPriceLevelCode(),
				priceLevelInfoForm.getLowLimitPrice(),
				priceLevelInfoForm.getTopLimitPrice(),
				priceLevelInfoForm.getIsActive(), "");
		request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", spReturn.getOutputParam(1));
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());

		return actionMapping.findForward("savemodify");
	}

}
