package com.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.web.form.TobaccoInfoForm;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.SqlReturn;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;
import com.zl.util.TradeList;

//卷烟信息维护
public class TobaccoPriceAction extends CoreDispatchAction {
	static Log logger = LogFactory.getLog(TobaccoPriceAction.class);

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
		try {
			TobaccoInfoForm aForm = (TobaccoInfoForm) form;
			List list = TradeList.Getpriceid();
			request.setAttribute("level.list", list);
			// if(aForm.getQuickCode()==null)aForm.setQuickCode("1");
			return query(actionMapping, form, request, response);
		} catch (Exception e) {

			return null;
		}
	}

	public ActionForward initlevel1(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			TobaccoInfoForm aForm = (TobaccoInfoForm) form;
			List list = TradeList.Getpriceid();
			request.setAttribute("level.list", list);
			// if(aForm.getQuickCode()==null)aForm.setQuickCode("1");
			return querylevel1(actionMapping, form, request, response);
		} catch (Exception e) {

			return null;
		}
	}

	// wangp 09.2.3 呼叫中心的查询iframe
	public ActionForward init_iframe(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			TobaccoInfoForm aForm = (TobaccoInfoForm) form;
			List list = TradeList.Getpriceid();
			request.setAttribute("level.list", list);
			CallHelper caller = initializeCallHelper("getTobaccoPriceList",
					form, request, false);
			caller.setParam("userId", getPersonId(request));
			caller.execute();
			request.setAttribute("tobaccocaption.list", caller.getResult(0));
			request.setAttribute("tobaccoinfo.list", caller.getResult(1));

			return actionMapping.findForward("init_iframe");
		} catch (Exception e) {

			return null;
		}
	}

	public ActionForward query(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		getInitList(request);
		CallHelper caller = initializeCallHelper("getTobaccoPriceList", form,
				request, false);
		caller.setParam("userId", getPersonId(request));
		caller.execute();
		request.setAttribute("tobaccocaption.list", caller.getResult(0));
		request.setAttribute("tobaccoinfo.list", caller.getResult(1));

		return actionMapping.findForward("init");
	}

	public ActionForward querylevel1(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		getInitList(request);
		CallHelper caller = initializeCallHelper("getTobaccoPriceListlevel1",
				form, request, false);
		caller.setParam("userId", getPersonId(request));
		caller.execute();
		request.setAttribute("tobaccocaption.list", caller.getResult(0));
		request.setAttribute("tobaccoinfo.list", caller.getResult(1));

		return actionMapping.findForward("initlevel1");
	}

	public ActionForward add(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		getInitList(request);

		return actionMapping.findForward("add");
	}

	// 保存新增
	public ActionForward saveadd(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			TobaccoInfoForm tobaccoInfoForm = (TobaccoInfoForm) form;
			SqlReturn spReturn = TradeList.g_saveTobacco("1", tobaccoInfoForm,
					"");
			request.setAttribute("message.code", spReturn.getOutputParam(0));
			request.setAttribute("message.information",
					spReturn.getOutputParam(1));
			getInitList(request);
			// 记录规格新增系统操作日志
			String success = spReturn.getOutputParam(3);
			if (success != null && "1".equals(success.trim())) {
				String personId = getPersonId(request);
				String ip = request.getRemoteHost();
				String tobasystid = spReturn.getOutputParam(2);
				TradeList.operLog(personId, 99050301, tobasystid, ip);
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return actionMapping.findForward("saveadd");
	}

	public ActionForward modify(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TobaccoInfoForm tobaccoInfoForm = (TobaccoInfoForm) form;
		getInitList(request);
		List<BasicDynaBean> tobacco = TradeList.g_getTobaccoInfo(
				tobaccoInfoForm.getTobaSystId()).getResultSet(0);
		int i = 0;
		if (tobacco.size() > i) {
			BasicDynaBean rowBean = (BasicDynaBean) tobacco.get(i);
			try {
				MethodFactory.copyProperties(tobaccoInfoForm, rowBean);
				tobaccoInfoForm.setFactoryCode((String) PropertyUtils
						.getProperty(rowBean, "factorycode"));
				tobaccoInfoForm.setBanddish((String) PropertyUtils.getProperty(
						rowBean, "baddish"));
				tobaccoInfoForm.setStyle((String) PropertyUtils.getProperty(
						rowBean, "wpjc"));
				tobaccoInfoForm.setSpec((String) PropertyUtils.getProperty(
						rowBean, "cpdm"));
			} catch (Exception e) {
				logger.error(e);
				return actionMapping.findForward("error");
			}
		}
		return actionMapping.findForward("modify");
	}

	public ActionForward modifylevel1(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TobaccoInfoForm tobaccoInfoForm = (TobaccoInfoForm) form;
		getInitList(request);
		List<BasicDynaBean> tobacco = TradeList.g_getTobaccoInfolevel1(
				tobaccoInfoForm.getTobaSystId()).getResultSet(0);
		int i = 0;
		if (tobacco.size() > i) {
			BasicDynaBean rowBean = (BasicDynaBean) tobacco.get(i);
			try {
				MethodFactory.copyProperties(tobaccoInfoForm, rowBean);
				tobaccoInfoForm.setFactoryCode((String) PropertyUtils
						.getProperty(rowBean, "factorycode"));
				tobaccoInfoForm.setBanddish((String) PropertyUtils.getProperty(
						rowBean, "baddish"));
				tobaccoInfoForm.setStyle((String) PropertyUtils.getProperty(
						rowBean, "wpjc"));
				tobaccoInfoForm.setSpec((String) PropertyUtils.getProperty(
						rowBean, "cpdm"));
			} catch (Exception e) {
				logger.error(e);
				return actionMapping.findForward("error");
			}
		}
		return actionMapping.findForward("modifylevel1");
	}

	// 保存修改
	public ActionForward savemodify(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TobaccoInfoForm tobaccoInfoForm = (TobaccoInfoForm) form;
		SqlReturn spReturn = TradeList.g_saveTobacco("2", tobaccoInfoForm, "");
		request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", spReturn.getOutputParam(1));
		getInitList(request);

		// 记录规格修改系统操作日志
		String success = spReturn.getOutputParam(3);
		if (success != null && "1".equals(success.trim())) {
			String personId = getPersonId(request);
			String ip = request.getRemoteHost();
			String tobasystid = spReturn.getOutputParam(2);
			TradeList.operLog(personId, 99050302, tobasystid, ip);
		}

		return actionMapping.findForward("savemodify");
	}

	// 保存修改
	public ActionForward savemodifylevel1(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TobaccoInfoForm tobaccoInfoForm = (TobaccoInfoForm) form;
		SqlReturn spReturn = TradeList.g_saveTobaccolevel1("2", tobaccoInfoForm,
				"");
		request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", spReturn.getOutputParam(1));
		getInitList(request);

		// 记录规格修改系统操作日志
		// String success = spReturn.getOutputParam(3);
		// if(success != null && "1".equals(success.trim())){
		// String personId = getPersonId(request);
		// String ip = request.getRemoteHost();
		// String tobasystid = spReturn.getOutputParam(2);
		// TradeList.operLog(personId,99050302,tobasystid,ip);
		// }

		return actionMapping.findForward("savemodifylevel1");
	}

	public ActionForward tobasetShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TobaccoInfoForm aForm = (TobaccoInfoForm) form;
		CallHelper call = initializeCallHelper("setTobaccoPriceList", aForm,
				request, false);
		call.execute();
		request.setAttribute("select.tree", call.getResult(0));
		request.setAttribute("unselect.tree", call.getResult(1));
		return mapping.findForward("tobasetshow");
	}

	public ActionForward tobasetSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TobaccoInfoForm aForm = (TobaccoInfoForm) form;
		CallHelper call = initializeCallHelper("setTobaccoSave", aForm,
				request, false);
		call.setParam("userId", getPersonId(request));
		call.execute();
		String message = (String) call.getOutput("message");
		request.setAttribute("message", message);
		return mapping.findForward("tobasetSubmit");
	}

	public ActionForward tobasetSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("tobasetSubmit");
	}

	// 取得列表信息(烟厂,品牌,价格档次,卷烟等级,卷烟类型,卷烟规格,状态)
	private void getInitList(HttpServletRequest request) {
		try {
			List list = TradeList.g_factoryQuery().getResultSet(0);
			request.setAttribute("factory.list", list);
			list = TradeList.g_getBrands().getResultSet(1);
			request.setAttribute("brand.list", list);
			// SqlReturn spReturn = TradeList.g_getAllPriceLevels();
			// request.setAttribute("level.list", spReturn.getResultSet(1));
			list = TradeList.Getpriceid();
			request.setAttribute("level.list", list);
			// list = getPublicCodes("501");
			// request.setAttribute("rank.list", list);
			list = getPublicCodes("503");
			request.setAttribute("style.list", list);
			list = getPublicCodes("502");
			request.setAttribute("spec.list", list);
			list = getPublicCodes("505");
			request.setAttribute("dish.list", list);
			request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		} catch (Exception e) {
		}
		// list=new ArrayList();
		// try {
		// list=TradeList.getTobaSerialList();
		// request.setAttribute("serial.list",list);
		// } catch (Exception e) {
		// }

	}
}
