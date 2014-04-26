package com.web.action;

import java.io.PrintWriter;
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
import com.web.form.CompanyToInfoForm;
import com.zl.base.core.db.CallHelper;

/**
 * @author: 朱忠南
 * @date: Jun 25, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe: 公司投放信息查询
 * @modify_author:
 * @modify_time:
 */
public class CompanyToInfoAction extends CoreDispatchAction {
	private static Log log = LogFactory.getLog(CompanyToInfoAction.class);

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		log.debug("init");
		return mapping.findForward("init");

	}

	/**
	 * 显示数据
	 *
	 * @author: 朱忠南
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		log.debug("show");
		try {
			CompanyToInfoForm infoForm = (CompanyToInfoForm) form;
			if (infoForm.getCompsystids() == null)
				infoForm.setCompsystids("");
			if (infoForm.getStaffname() == null)
				infoForm.setStaffname("");
			if (infoForm.getLinkman() == null)
				infoForm.setLinkman("");

			CallHelper helper = initializeCallHelper("companyToInfoQuery",
					form, request, false);
			helper.execute();
			request.setAttribute("caption.list", helper.getResult("captions"));
			request.setAttribute("result.list", helper.getResult("results"));
		} catch (Exception e) {
			log.error("执行存储过程getSummary失败");
			log.error(e);
			return mapping.findForward("error");
		}
		return mapping.findForward("show");
	}

	/**
	 * 获取联系人列表
	 *
	 * @author: 朱忠南
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getLinkMan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			CompanyToInfoForm infoForm = (CompanyToInfoForm) form;
			String text = request.getParameter("text").trim();// 注意，这个参数都使用text
			text = java.net.URLDecoder.decode(text, "utf-8");
			infoForm.setLinkman(text);
			CallHelper helper = initializeCallHelper("getLinkman", infoForm,
					request, false);
			helper.execute();
			List linkmans = helper.getResult(0);
			int size = linkmans.size();
			PrintWriter out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < size; i++) {
				BasicDynaBean bean = (BasicDynaBean) linkmans.get(i);
				String name = (String) bean.get("tlinkman");
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

	/**
	 * 获取业务员列表
	 *
	 * @author: 朱忠南
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getStaffName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			CompanyToInfoForm infoForm = (CompanyToInfoForm) form;
			String text = request.getParameter("text").trim();// 注意，这个参数都使用text
			text = java.net.URLDecoder.decode(text, "utf-8");
			infoForm.setStaffname(text);
			CallHelper helper = initializeCallHelper("getStaffName", infoForm,
					request, false);
			helper.execute();
			List staffnames = (List) helper.getResult(0);
			int size = staffnames.size();
			PrintWriter out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < size; i++) {
				BasicDynaBean bean = (BasicDynaBean) staffnames.get(i);
				String name = (String) bean.get("tstaffname");
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
}
