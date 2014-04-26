package com.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;

import com.common.tackle.UserView;
import com.common.util.Endecrypt;
import com.surekam.portal.apputil.AppLoginHelp;
import com.web.CoreDispatchAction;
import com.zl.base.core.db.CallHelper;
import com.zl.common.Constants;

/**
 * @Title: TransAppLoginHelper.java
 * @Package com.web.action
 * @Description: TODO(web.xml---->SsoFilter)
 * @author qmx
 * @date 2013/02/25 13:43:51
 * @version V1.0
 */
public class TransAppLoginHelper extends CoreDispatchAction implements
		AppLoginHelp {

	public boolean isAppUserLoged(String userId, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserView user = (UserView) session.getAttribute(Constants.SESSION_USER);
		if (user == null) {
			return false;
		}

		String sessionUid = user.getPersonCode();
		if (userId.equalsIgnoreCase(sessionUid)) {
			Endecrypt crypto = new Endecrypt();
			request.getSession().setAttribute(
					"personCode",
					java.net.URLEncoder.encode(crypto.get3DESEncrypt(user
							.getPersonCode().trim().intern(), "zl")));
			return true;
		} else {
			session.invalidate();
			request.getSession(true);
			return false;
		}
	}

	public boolean processAppLogin(String userId, HttpServletRequest request,
			HttpServletResponse response, String remoteIp) {
		boolean result = true;

		// System.out.println("processAppLogin begin ");
		CallHelper helper = new CallHelper("SSO_LOGIN");
		helper.setParam("personCode", userId);
		helper.setParam("logintype", "1");
		helper.execute();

		// System.out.println("helper.getState() ==" +helper.getState());
		if (helper.getState() == 0) {
			List results = helper.getResult("results");
			UserView user = new UserView();
			this.copyProperties(user, results, 0, true, true);
			user.setPersonCode(userId);
			user.setPassword("");

			CallHelper roleHelper = new CallHelper("getRolesByUser");
			roleHelper.setParam("personId", user.getPersonId());
			roleHelper.execute();
			List roleResults = roleHelper.getResult("results");
			if (roleResults != null && roleResults.size() > 0) {
				for (int i = 0; i < roleResults.size(); i++) {
					DynaBean bean = (DynaBean) roleResults.get(i);
					String roleCode = (String) bean.get("rolecode");
					if (roleCode != null) {
						user.addRoleCode(roleCode.trim());
					}
				}
			}
			request.getSession().removeAttribute(Constants.SESSION_USER);
			request.getSession().setAttribute(Constants.SESSION_USER, user);
			request.getSession().setMaxInactiveInterval(10 * 3600);
			Endecrypt crypto = new Endecrypt();
			request.getSession().setAttribute(
					"personCode",
					java.net.URLEncoder.encode(crypto.get3DESEncrypt(user
							.getPersonCode().trim().intern(), "zl")));
			result = true;
		} else {
			request.getSession().setAttribute("uid", userId);
			String mess = (String) helper.getOutput("message");
			response.setContentType("text/html");
			response.setCharacterEncoding("GBK");
			try {
				response.getWriter().write(mess);
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;

		}

		return result;
	}

}
