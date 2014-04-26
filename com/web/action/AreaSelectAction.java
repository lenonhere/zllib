package com.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.zl.base.core.db.CallHelper;
import com.zl.common.Constants;

public class AreaSelectAction extends CoreDispatchAction {

	static Logger logger = Logger.getLogger(AreaSelectAction.class);

	public ActionForward query(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			CallHelper helper = initializeCallHelper("getAreaTreeAll", form,
					request, false);
			helper.execute();
			request.setAttribute("area.tree", helper.getResult(0));
		} catch (Exception e) {
			logger.error(e);
			ActionError error = new ActionError(Constants.ERROR_GENERAL,
					e.getMessage());
			errors.add(Constants.ERROR_GENERAL, error);
		}
		if (errors.size() > 0) {
			saveErrors(request, errors);
			return actionMapping.findForward("error");
		}

		return actionMapping.findForward("query");
	}

}
