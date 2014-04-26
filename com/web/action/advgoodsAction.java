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

public class advgoodsAction extends CoreDispatchAction {

    static Logger logger = Logger.getLogger(advgoodsAction.class.getName());

    public ActionForward init(ActionMapping actionMapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            SqlReturn SqlReturnTemp = TradeList.GP_SelectAdsGoods(request
                    .getParameter("goodscode"));
            ArrayList list1 = (ArrayList) SqlReturnTemp.getResultSet(1);
            request.setAttribute("datacount", Integer.toString(list1.size()));
            request.setAttribute("goodslist", list1);
            request.setAttribute("goodscaption", SqlReturnTemp.getResultSet(0));

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
        return actionMapping.findForward("init");
    }
}
