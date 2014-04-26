package com.web.action.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.qmx.dateutils.DateUtils;
import com.web.action.CriterionAction;
import com.web.form.system.OnlineListForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.OptionUtils;

public class OnlineListAction extends CriterionAction {

	private static final Log log = LogFactory.getLog(OnlineListAction.class);

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

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OnlineListForm cform = (OnlineListForm) form;
		if (cform.getBeginDate() == null) {
			cform.setBeginDate(DateUtils.getDate(-2).replace('/', '-'));
			cform.setEndDate(DateUtils.getDate().replace('/', '-'));

		}
		if (cform.getPersonName() == null)
			cform.setPersonName("");
		if (cform.getPersonType() == null)
			cform.setPersonType("-1");
		request.setAttribute("personType.list", OptionUtils.getPersonTypeList());

		CallHelper helper = initializeCallHelper("getOnlineList", cform,
				request, false);
		helper.execute();
		request.setAttribute("caption.list", helper.getResult("captions"));
		request.setAttribute("result.list", helper.getResult("results"));

		return mapping.findForward("list");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OnlineListForm cform = (OnlineListForm) form;
		CallHelper helper = initializeCallHelper("getOnlineListDetail", cform,
				request, false);
		helper.execute();
		request.setAttribute("caption.list", helper.getResult("captions"));
		request.setAttribute("result.list", helper.getResult("results"));

		return mapping.findForward("view");

	}
}
