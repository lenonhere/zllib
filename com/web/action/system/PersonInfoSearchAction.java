package com.web.action.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.web.pojos.PersonInfo;
import com.zl.base.core.db.CallHelper;
import com.zl.util.MethodFactory;

public class PersonInfoSearchAction extends CoreDispatchAction {

	private static final Log log = LogFactory
			.getLog(PersonInfoSearchAction.class);

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("init");
	}

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 商业公司人员阻止看到组织结构树
		CallHelper Helper = new CallHelper("getparentdeparidbyuser");
		Helper.setParam("personId", getPersonId(request));
		Helper.execute();
		List issy = Helper.getResult(0);
		String issycount = "";
		if (issy.size() > 0) {
			for (int i = 0; i < issy.size(); i++) {
				DynaBean bean = (DynaBean) issy.get(i);
				issycount = MethodFactory.getThisString(bean.get("issy"));
			}
		}
		if ("1".equals(issycount))
			return null;

		String keyword = request.getParameter("keyword");
		CallHelper call = initializeCallHelper("searchPersonInfo", form,
				request, false);
		call.setParam("keyword", keyword);
		call.execute();
		List list = call.getResult("results");

		if (list != null && list.size() > 0) {
			List ret = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				PersonInfo personInfo = new PersonInfo();
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				log.info(bean.get("personname"));
				personInfo.setPersonName(MethodFactory.getThisString(bean
						.get("personname")));
				personInfo.setEmail(MethodFactory.getThisString(bean
						.get("email")));
				personInfo.setMobile(MethodFactory.getThisString(bean
						.get("mobile")));
				personInfo.setMobile2(MethodFactory.getThisString(bean
						.get("mobile2")));
				personInfo.setRoleName(MethodFactory.getThisString(bean
						.get("rolename")));
				personInfo.setDepartName(MethodFactory.getThisString(bean
						.get("departname")));
				personInfo.setOfficePhone(MethodFactory.getThisString(bean
						.get("officephone")));
				personInfo.setOfficeNumber(MethodFactory.getThisString(bean
						.get("officenumber")));
				ret.add(personInfo);
			}
			request.setAttribute("PersonInfos", ret);
		}

		return mapping.findForward("search");
	}

}
