package com.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
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
public class TobaccoInfoAction extends CoreDispatchAction {
	static Logger logger = Logger.getLogger(TobaccoInfoAction.class.getName());

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
		// SqlReturn spReturn = TradeList.g_getAllTobaccos("");

		return query(actionMapping, form, request, response);
	}

	public ActionForward initlevel1(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// SqlReturn spReturn = TradeList.g_getAllTobaccos("");

		return querylevel1(actionMapping, form, request, response);
	}

	public ActionForward query(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			CallHelper helper = initializeCallHelper("queryProvinces", form,
					request, false);
			helper.setParam("provinceCode", "");
			helper.execute();
			List list = helper.getResult(0);
			request.setAttribute("province.list", list);
			TobaccoInfoForm aForm = (TobaccoInfoForm) form;
			String type = request.getParameter("type");
			if ("query".equals(type)) {
				SqlReturn spreturn = TradeList.G_FactoryQueryByProvince("-1");
				List<BasicDynaBean> list2 = spreturn.getResultSet(0);
				request.setAttribute("factory.list", list2);
			} else {
				if (list.size() > 0 && aForm.getProvincecode() == null) {
					aForm.setProvinceid("17");
					aForm.setProvincecode("42");
				}
				if (aForm.getProvinceid() == null) {
					aForm.setProvinceid("");
					aForm.setProvincecode("");
				}
				SqlReturn spreturn = TradeList.G_FactoryQueryByProvince("-1");
				List<BasicDynaBean> list2 = spreturn.getResultSet(0);
				request.setAttribute("factory.list", list2);
				aForm.setFactoryCode("");
			}
			// 朱忠南修改，增加两个，模糊查询字段
			String tobaCode = aForm.getTobaCode() != null ? aForm.getTobaCode()
					.trim() : "";
			String tobaName = aForm.getTobaName() != null ? aForm.getTobaName()
					.trim() : "";
			SqlReturn spreturn = TradeList.G_getTobaccoByFact(
					aForm.getProvincecode(), aForm.getFactoryCode(), tobaCode,
					tobaName);

			request.setAttribute("tobaccocaption.list",
					spreturn.getResultSet(0));
			request.setAttribute("tobaccoinfo.list", spreturn.getResultSet(1));
		} catch (Exception ex) {
		}
		return actionMapping.findForward("init");
	}

	public ActionForward querylevel1(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			CallHelper helper = initializeCallHelper("queryProvinces", form,
					request, false);
			helper.setParam("provinceCode", "");
			helper.execute();
			List list = helper.getResult(0);
			request.setAttribute("province.list", list);
			TobaccoInfoForm aForm = (TobaccoInfoForm) form;
			String type = request.getParameter("type");
			if ("query".equals(type)) {
				SqlReturn spreturn = TradeList.G_FactoryQueryByProvince("-1");
				List<BasicDynaBean> list2 = spreturn.getResultSet(0);
				request.setAttribute("factory.list", list2);
			} else {
				if (list.size() > 0 && aForm.getProvincecode() == null) {
					aForm.setProvinceid("17");
					aForm.setProvincecode("42");
				}
				if (aForm.getProvinceid() == null) {
					aForm.setProvinceid("");
					aForm.setProvincecode("");
				}
				SqlReturn spreturn = TradeList.G_FactoryQueryByProvince("-1");
				List<BasicDynaBean> list2 = spreturn.getResultSet(0);
				request.setAttribute("factory.list", list2);
				aForm.setFactoryCode("");
			}
			// 朱忠南修改，增加两个，模糊查询字段
			String tobaCode = aForm.getTobaCode() != null ? aForm.getTobaCode()
					.trim() : "";
			String tobaName = aForm.getTobaName() != null ? aForm.getTobaName()
					.trim() : "";
			SqlReturn spreturn = TradeList.G_getTobaccoByFactlevel1(
					aForm.getProvincecode(), aForm.getFactoryCode(), tobaCode,
					tobaName);

			request.setAttribute("tobaccocaption.list",
					spreturn.getResultSet(0));
			request.setAttribute("tobaccoinfo.list", spreturn.getResultSet(1));
		} catch (Exception ex) {
		}
		return actionMapping.findForward("initlevel1");
	}

	public ActionForward add(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		getInitList(request);
		return actionMapping.findForward("add");
	}

	// 保存新增
	public ActionForward saveadd(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TobaccoInfoForm tobaccoInfoForm = (TobaccoInfoForm) form;
		SqlReturn spReturn = TradeList.g_saveTobacco("1", tobaccoInfoForm, "");
		request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", spReturn.getOutputParam(1));
		getInitList(request);
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
			} catch (Exception e) {
				logger.error(e);
				return actionMapping.findForward("error");
			}
		}
		return actionMapping.findForward("modify");
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

		// 记录系统操作日志

		// String personId = getPersonId(request);
		// String ip = request.getRemoteHost();
		// String tobasystid = tobaccoInfoForm.getTobaSystId();
		// TradeList.operLog(personId,99050302,tobasystid,ip);

		return actionMapping.findForward("savemodify");
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
			// list = getPublicCodes("502");
			// request.setAttribute("style.list", list);
			// list = getPublicCodes("503");
			// request.setAttribute("spec.list", list);
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
