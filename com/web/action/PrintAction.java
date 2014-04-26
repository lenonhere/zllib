package com.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import com.common.jasper.DefaultDesignBuilder;
import com.common.jasper.DesignBuilder;
import com.qmx.dateutils.DateUtils;
import com.zl.base.core.db.CallHelper;

public class PrintAction
    extends CriterionAction {

    private static Log log = LogFactory.getLog(PrintAction.class);

    protected void preparePrint(ActionForm form, HttpServletRequest request,
                               CallHelper helper, Map parameters,
                               String exportFormat, String title) {

        request.setAttribute("caption.list", helper.getResult("captions"));
        request.setAttribute("result.list", helper.getResult("results"));

        String paper = (String)helper.getOutput("paper");

        if(paper == null || "".equals(paper.trim())) {
            paper = request.getParameter("paper");
        } else {
            paper = paper.trim();
        }

        parameters.put("paper", paper);
        parameters.put("title", title);
        parameters.put("createdDate", DateUtils.getDate().replace('/', '-'));
        parameters.put("creator",     getPersonName(request));

        DesignBuilder builder =
            new DefaultDesignBuilder("DefaultDesignBuilder");

        request.setAttribute("parameter.map", parameters);
        request.setAttribute("design.template", builder);
        request.setAttribute("exportFormat", exportFormat);
    }
}
