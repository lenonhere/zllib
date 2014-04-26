package com.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.common.tackle.UserView;
import com.web.CoreDispatchAction;
import com.web.form.SycompanyInfoForm;
import com.zl.base.core.db.Executer;
import com.zl.base.core.db.SqlReturn;
import com.zl.common.Constants;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;
import com.zl.util.TradeList;

public class SycompanyInfoAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(SycompanyInfoAction.class);

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

	// 取得公司列表
	public ActionForward init(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SycompanyInfoForm sForm = (SycompanyInfoForm) form;
		List inparams = new ArrayList();
		inparams.add(sForm.getSycompAlias() == null ? "" : sForm
				.getSycompAlias().trim());
		inparams.add(sForm.getAreaName() == null ? "" : sForm.getAreaName()
				.trim());
		inparams.add(sForm.getIsActive() == null ? "" : sForm.getIsActive());
		SqlReturn spReturn = TradeList.g_getAllSycompanies(inparams);
		request.setAttribute("sycompanycaption.list", spReturn.getResultSet(0));
		request.setAttribute("sycompanyinfo.list", spReturn.getResultSet(1));
		return actionMapping.findForward("init");
	}

	// 初始化新增页面
	public ActionForward add(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		getInitList(request);
		return actionMapping.findForward("add");
	}

	// 保存新增
	public ActionForward saveadd(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SycompanyInfoForm sycompInfoForm = (SycompanyInfoForm) form;
		SqlReturn spReturn = TradeList.g_saveSycompany("1", sycompInfoForm, "");
		request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", spReturn.getOutputParam(1));
		getInitList(request);
		return actionMapping.findForward("saveadd");
	}

	// 初始化修改页面
	public ActionForward modify(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SycompanyInfoForm sycompanyInfoForm = (SycompanyInfoForm) form;
		getInitList(request);
		List<BasicDynaBean> sycompany = TradeList.g_getSycompanyInfo(
				sycompanyInfoForm.getSycompSystId()).getResultSet(0);
		if (sycompany != null && sycompany.size() > 0) {
			BasicDynaBean rowBean = (BasicDynaBean) sycompany.get(0);
			try {
				MethodFactory.copyProperties(sycompanyInfoForm, rowBean);
			} catch (Exception e) {
				log.error(e);
				return actionMapping.findForward("error");
			}
		}
		return actionMapping.findForward("modify");
	}

	// 保存修改
	public ActionForward savemodify(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SycompanyInfoForm sycompInfoForm = (SycompanyInfoForm) form;
		SqlReturn spReturn = TradeList.g_saveSycompany("2", sycompInfoForm, "");
		request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", spReturn.getOutputParam(1));
		getInitList(request);
		return actionMapping.findForward("savemodify");
	}

	// 取得页面初始化列表
	private void getInitList(HttpServletRequest request) {
		SqlReturn spReturn = TradeList.g_areaQuery();
		request.setAttribute("area.list", spReturn.getResultSet(0));
		request.setAttribute("sycompgradeid.list", getPublicCodes("2"));
		request.setAttribute("transtype.list", getPublicCodes("61"));
		request.setAttribute("balancetype.list", getPublicCodes("62"));
		request.setAttribute("isprovince.list", OptionUtils.getIsProvinceList());
		request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		request.setAttribute("inkeycity.list", OptionUtils.getinkeycityList());
	}

	// 取得公司列表
	public ActionForward erpcode(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SqlReturn spReturn = TradeList
				.g_getAllCUSTOMERFROMERP((SycompanyInfoForm) form);
		request.setAttribute("sycompanycaption.list", spReturn.getResultSet(0));
		request.setAttribute("sycompanyinfo.list", spReturn.getResultSet(1));
		return actionMapping.findForward("erpcode");
	}

	// 保存修改
	public ActionForward imperpcode(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SycompanyInfoForm sycompInfoForm = (SycompanyInfoForm) form;
		SqlReturn spReturn = TradeList.g_impSycomperpcode(sycompInfoForm, "");
		request.setAttribute("message.code", spReturn.getOutputParam(0));
		request.setAttribute("message.information", spReturn.getOutputParam(1));

		return actionMapping.findForward("erpcode");
	}

	public ActionForward getCompAlias(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			String text = request.getParameter("text").trim().toUpperCase();// 注意，这个参数都使用text
			text = java.net.URLDecoder.decode(text, "utf-8");
			SqlReturn sqlRuturn = Executer.getInstance().ExecSeletSQL(
					"SELECT distinct sycompalias FROM G_Sycompany	where pinyin like '%"
							+ text + "%' or sycompalias like '%" + text + "%'");
			List comps = sqlRuturn.getResultSet();
			int size = comps.size();
			PrintWriter out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < size; i++) {
				BasicDynaBean bean = (BasicDynaBean) comps.get(i);
				String name = (String) bean.get("sycompalias");
				if (name == null || name.trim().equals(""))
					continue;
				sb.append(name).append("^");
			}
			if (size > 0)
				out.print(sb.substring(0, sb.length() - 1).trim());
			out.close();
		} catch (Exception e) {
			log.error("发现错误：");
			log.error(e);
		}

		return mapping.findForward("show");
	}

	// 取得公司列表选择页面（通用）
	public ActionForward getCompaniesSingleSelect(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserView user = (UserView) request.getSession().getAttribute(
				Constants.SESSION_USER);
		SycompanyInfoForm sForm = (SycompanyInfoForm) form;
		List inparams = new ArrayList();
		inparams.add(sForm.getSycompAlias() == null ? "" : sForm
				.getSycompAlias().trim());
		inparams.add(sForm.getAreaName() == null ? "" : sForm.getAreaName()
				.trim());
		inparams.add(user.getPersonId());

		// System.out.println("user:"+user.getPersonId());
		SqlReturn spReturn = TradeList.g_getCompaniesSingleSelect(inparams);
		request.setAttribute("sycompanycaption.list", spReturn.getResultSet(0));
		request.setAttribute("sycompanyinfo.list", spReturn.getResultSet(1));
		return actionMapping.findForward("companySelect");
	}

	// 取得公司列表选择页面(责任主体限制)（通用）
	public ActionForward getCompaniesSinglebyduty(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserView user = (UserView) request.getSession().getAttribute(
				Constants.SESSION_USER);
		SycompanyInfoForm sForm = (SycompanyInfoForm) form;
		List inparams = new ArrayList();
		inparams.add(sForm.getSycompAlias() == null ? "" : sForm
				.getSycompAlias().trim());
		inparams.add(sForm.getAreaName() == null ? "" : sForm.getAreaName()
				.trim());
		inparams.add(user.getPersonId());
		inparams.add(sForm.getDutyleadid());
		inparams.add(sForm.getDutyleadtype());
		inparams.add((sForm.getCrtdate() == null || sForm.getCrtdate().equals(
				"")) ? OptionUtils.getSystemDate() : sForm.getCrtdate());

		// System.out.println("user:"+user.getPersonId());
		SqlReturn spReturn = TradeList
				.g_getCompaniesSingleSelectbyduty(inparams);
		request.setAttribute("sycompanycaption.list", spReturn.getResultSet(0));
		request.setAttribute("sycompanyinfo.list", spReturn.getResultSet(1));
		return actionMapping.findForward("companySelectbyduty");
	}

}
