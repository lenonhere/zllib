package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class CompanyFinanceAction extends CoreDispatchAction {
	private static Log log = LogFactory.getLog(CompanyFinanceAction.class);

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

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
		try {
			CompanyToInfoForm infoForm = (CompanyToInfoForm) form;
			if (infoForm.getCompsystids() == null)
				infoForm.setCompsystids("");
			if (infoForm.getLinkman() == null)
				infoForm.setLinkman("");

			CallHelper helper = initializeCallHelper("companyFinanceQuery",
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
}
