package com.web.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.zl.base.core.db.SqlReturn;
import com.zl.common.Constants;
import com.zl.util.TradeList;

public class DistSelectaction extends CoreDispatchAction {
    static Logger logger = Logger.getLogger(DistSelectaction.class.getName());

    public ActionForward view(ActionMapping actionMapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            String userid = getPersonId(request);
            SqlReturn SqlReturnTemp = TradeList.G_DistrictTreeQuery(userid);

            ArrayList list1 = (ArrayList) SqlReturnTemp.getResultSet(0);

            request.setAttribute("district.list", list1);

        } catch (Exception e) {
            logger.error(e);
            ActionError error = new ActionError(Constants.ERROR_GENERAL, e
                    .getMessage());
            errors.add(Constants.ERROR_GENERAL, error);
        }
        if (errors.size() > 0) {
            saveErrors(request, errors);
            return actionMapping.findForward("error");
        }
        return actionMapping.findForward("view");
    }

}
