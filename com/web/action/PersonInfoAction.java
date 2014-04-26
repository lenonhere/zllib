package com.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.common.tackle.UserView;
import com.web.CoreDispatchAction;
import com.web.form.PersonInfoForm;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.SqlReturn;
import com.zl.common.Constants;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;
import com.zl.util.TradeList;

//人员信息维护
public class PersonInfoAction extends CoreDispatchAction {
	static Logger logger = Logger.getLogger(PersonInfoAction.class);

	public ActionForward init(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			department(request, form);
			// CallHelper helper = initializeCallHelper("getPersonsQuery", form,
			// request, false);
			// helper.setParam("departid", "-1");
			// helper.execute();

			// request.setAttribute("personinfo.captionlist",
			// helper.getResult("captions"));
			// request.setAttribute("personinfo.list",
			// helper.getResult("results"));
			PersonInfoForm aForm = (PersonInfoForm) form;
			String isactive = request.getParameter("isactive");
			aForm.setisactive("on");
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
		return actionMapping.findForward("init");
	}

	public ActionForward query(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			department(request, form);
			CallHelper helper = initializeCallHelper("getPersonsQuery", form,
					request, false);
			helper.execute();

			request.setAttribute("personinfo.captionlist",
					helper.getResult("captions"));
			request.setAttribute("personinfo.list", helper.getResult("results"));

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

	public ActionForward add(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			department(request, form);
			// workarea(request, form);
			isactive(request);
			// isworker(request);
			persontype(request, form);
			isduty(request);
			getBelongs(request);
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
		return actionMapping.findForward("add");
	}

	// 保存新增
	public ActionForward saveadd(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// logger.info("增加人员测试:::::::::::::::::");
		try {

			PersonInfoForm aform = (PersonInfoForm) form;
			SqlReturn r = new SqlReturn();
			r = TradeList.G_PersonInfoSave("1", "-1",
			// aform.getpersonid(),
					aform.getpersoncode(), aform.getpersonname(), aform
							.getpersonusername(), aform.getpassword(), aform
							.getdepartid(), aform.getisactive(), aform
							.getisworker(), aform.getworkarea().trim(), aform
							.getWorkno().trim(), aform.getMobile(), aform
							.getMobile2(), aform.getBirthday(), aform
							.getIdcode(), aform.getOfficephone(), aform
							.getEmail(), aform.getAddress(), aform
							.getPersontype(), aform.getIsduty(), aform
							.getPinyin(), aform.getBelong(), getUser(request)
							.getPersonId(),// 新增操作人ID，add by 朱忠南
					"");
			request.setAttribute("message.code", r.getOutputParam(0));
			request.setAttribute("message.information", r.getOutputParam(1));

			department(request, form);
			// workarea(request, form);
			isactive(request);
			// isworker(request);
			persontype(request, form);
			isduty(request);
			getBelongs(request);

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
		return actionMapping.findForward("saveadd");
	}

	public ActionForward modify(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String personid = request.getParameter("personid");
			if (personid == null) {
				HttpSession session = request.getSession();
				UserView userinfo = (UserView) session
						.getAttribute(Constants.SESSION_USER);
				personid = userinfo.getPersonId();

				request.setAttribute("isReadOnly", "True");

			}
			PersonInfoForm aForm = (PersonInfoForm) form;
			department(request, form);
			// workarea(request, form);
			isactive(request);
			// isworker(request);
			persontype(request, form);
			isduty(request);
			getBelongs(request);
			ArrayList list = new ArrayList();

			list = TradeList.G_PersonInfoOpen("-1", personid);
			list = (ArrayList) list.get(1);
			// 取返回的结果集(取返回纪录的第i条纪录)
			int i = 0;
			if (list.size() > i) {
				BasicDynaBean rowBean = (BasicDynaBean) list.get(i);
				MethodFactory.copyProperties(aForm, rowBean, true); // 推荐使用此方法，请务必使你的ActionFrom结构良好
				aForm.setpersonid(MethodFactory.getThisString(rowBean
						.get("personid")));
				aForm.setpersoncode(MethodFactory.getThisString(rowBean
						.get("personcode")));
				aForm.setpersonname(MethodFactory.getThisString(rowBean
						.get("personname")));
				aForm.setPinyin(MethodFactory.getThisString(rowBean
						.get("pinyin")));
				aForm.setpersonusername(MethodFactory.getThisString(rowBean
						.get("personusername")));
				aForm.setpassword(MethodFactory.getThisString(rowBean
						.get("password")));
				aForm.setdepartid(MethodFactory.getThisString(rowBean
						.get("departid")));
				aForm.setisactive(MethodFactory.getThisString(rowBean
						.get("isactive")));
				aForm.setisworker(MethodFactory.getThisString(rowBean
						.get("isworker")));
				aForm.setworkarea(MethodFactory.getThisString(rowBean
						.get("workarea")) + "  ");
				aForm.setWorkno(MethodFactory.getThisString(rowBean
						.get("workno")));
				aForm.setMobile(MethodFactory.getThisString(rowBean
						.get("mobile")));
				aForm.setMobile2(MethodFactory.getThisString(rowBean
						.get("mobile2")));
				aForm.setBirthday(MethodFactory.getThisString(rowBean
						.get("birthday")));
				aForm.setIdcode(MethodFactory.getThisString(rowBean
						.get("idcode")));
				aForm.setOfficephone(MethodFactory.getThisString(rowBean
						.get("officephone")));
				aForm.setEmail(MethodFactory.getThisString(rowBean.get("email")));
				aForm.setAddress(MethodFactory.getThisString(rowBean
						.get("address")));
				aForm.setPersontype(MethodFactory.getThisString(rowBean
						.get("persontype")));
				aForm.setIsduty(MethodFactory.getThisString(rowBean
						.get("isduty")));
				aForm.setBelong(MethodFactory.getThisString(rowBean
						.get("belong")));
			}
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
		return actionMapping.findForward("modify");
	}

	// 保存修改
	public ActionForward savemodify(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {

			PersonInfoForm aform = (PersonInfoForm) form;
			logger.debug("pinyin=" + aform.getPinyin());
			SqlReturn r = new SqlReturn();
			r = TradeList.G_PersonInfoSave("2", aform.getpersonid(), aform
					.getpersoncode(), aform.getpersonname(), aform
					.getpersonusername(), aform.getpassword(), aform
					.getdepartid().trim(), aform.getisactive().trim(), aform
					.getisworker().trim(), aform.getworkarea().trim(), aform
					.getWorkno().trim(), aform.getMobile(), aform.getMobile2(),
					aform.getBirthday(), aform.getIdcode(), aform
							.getOfficephone(), aform.getEmail(), aform
							.getAddress(), aform.getPersontype(), aform
							.getIsduty(), aform.getPinyin(), aform.getBelong(),
					getUser(request).getPersonId(), "");
			request.setAttribute("message.code", r.getOutputParam(0));
			request.setAttribute("message.information", r.getOutputParam(1));
			department(request, form);
			// workarea(request, form);
			isactive(request);
			persontype(request, form);
			isduty(request);
			getBelongs(request);
			// isworker(request);
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
		return actionMapping.findForward("savemodify");
	}

	public ActionForward init2(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			personrang(request);
			getcompanyinfo(request, "1");
			SqlReturn r = new SqlReturn();
			r = TradeList.G_OtherPersonInfoQuery("1", "-1");
			if (r.getResultCount() >= 2) {
				request.setAttribute("personinfo.captionlist",
						r.getResultSet(0));
				request.setAttribute("personinfo.list", r.getResultSet(1));
			}
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
		return actionMapping.findForward("init2");
	}

	public ActionForward query2(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			PersonInfoForm aForm = (PersonInfoForm) form;
			personrang(request);
			getcompanyinfo(request, aForm.getrang());
			SqlReturn r = new SqlReturn();
			r = TradeList.G_OtherPersonInfoQuery(aForm.getrang(), "-1");
			if (r.getResultCount() >= 2) {
				request.setAttribute("personinfo.captionlist",
						r.getResultSet(0));
				request.setAttribute("personinfo.list", r.getResultSet(1));
			}
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
		return actionMapping.findForward("query2");
	}

	public ActionForward add2(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String rang = request.getParameter("rang");
			if (rang == null) {
				rang = "1";
			}
			getcompanyinfo(request, rang);
			isactive(request);
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
		return actionMapping.findForward("add2");
	}

	// 保存新增
	public ActionForward saveadd2(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			PersonInfoForm aform = (PersonInfoForm) form;
			getcompanyinfo(request, aform.getrang());
			isactive(request);
			SqlReturn r = new SqlReturn();
			r = TradeList.G_OtherPersonInfoSave("1", "-1",
					aform.getpersoncode(), aform.getpersonname(),
					aform.getpersonusername(), aform.getpassword(),
					aform.getisactive(), aform.getcompanyinfoid(),
					aform.getrang(), "");
			request.setAttribute("message.code", r.getOutputParam(0));
			request.setAttribute("message.information", r.getOutputParam(1));

			department(request, form);
			workarea(request, form);
			isactive(request);
			isworker(request);
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

		return actionMapping.findForward("saveadd2");
	}

	public ActionForward modify2(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String rang = request.getParameter("rang");
			if (rang == null) {
				rang = "1";
			}
			getcompanyinfo(request, rang);
			isactive(request);
			String personid = request.getParameter("personid");
			PersonInfoForm aForm = (PersonInfoForm) form;
			SqlReturn r = new SqlReturn();
			r = TradeList.G_OtherPersonInfoQuery(rang, personid);
			List<BasicDynaBean> list = r.getResultSet(1);
			// 取返回的结果集(取返回纪录的第i条纪录)
			int i = 0;
			if (list.size() > i) {
				BasicDynaBean rowBean = (BasicDynaBean) list.get(i);
				MethodFactory.copyProperties(aForm, rowBean, true); // 推荐使用此方法，请务必使你的ActionFrom结构良好
				aForm.setpersonid(MethodFactory.getThisString(rowBean
						.get("personid")));
				aForm.setpersoncode(MethodFactory.getThisString(rowBean
						.get("personcode")));
				aForm.setpersonname(MethodFactory.getThisString(rowBean
						.get("personname")));
				aForm.setpersonusername(MethodFactory.getThisString(rowBean
						.get("personusername")));
				aForm.setpassword(MethodFactory.getThisString(rowBean
						.get("password")));
				aForm.setcompanyinfoid(MethodFactory.getThisString(rowBean
						.get("sycompsystid")));
				aForm.setisactive(MethodFactory.getThisString(rowBean
						.get("isactive")));
			}
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
		return actionMapping.findForward("modify2");
	}

	// 保存修改
	public ActionForward savemodify2(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			PersonInfoForm aform = (PersonInfoForm) form;
			getcompanyinfo(request, aform.getrang());
			isactive(request);
			SqlReturn r = new SqlReturn();
			r = TradeList.G_OtherPersonInfoSave("2", aform.getpersonid(),
					aform.getpersoncode(), aform.getpersonname(),
					aform.getpersonusername(), aform.getpassword(),
					aform.getisactive(), aform.getcompanyinfoid(),
					aform.getrang(), "");
			request.setAttribute("message.code", r.getOutputParam(0));
			request.setAttribute("message.information", r.getOutputParam(1));

			department(request, form);
			workarea(request, form);
			isactive(request);
			isworker(request);

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
		return actionMapping.findForward("savemodify2");
	}

	public ActionForward resetInit(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		return actionMapping.findForward("resetInit");
	}

	public ActionForward resetPassword(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			department(request, form);
			CallHelper helper = initializeCallHelper("saveModifyPassword",
					form, request, false);
			helper.setParam("userId", request.getParameter("personid"));
			helper.setParam("oldPasswd", request.getParameter("oldpassword"));
			helper.setParam("newPasswd", request.getParameter("password"));
			helper.execute();

			request.setAttribute("msgCode", helper.getOutput("msgCode"));
			request.setAttribute("message", helper.getOutput("message"));
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
		return actionMapping.findForward("resetInit");
	}

	private void department(HttpServletRequest request, ActionForm form) {

		CallHelper helper = initializeCallHelper("getDepartGradeList", form,
				request, false);
		helper.execute();

		request.setAttribute("department.list", helper.getResult("results"));
	}

	private void persontype(HttpServletRequest request, ActionForm form) {

		CallHelper helper = initializeCallHelper("getPersonTypeList", form,
				request, false);
		helper.execute();

		request.setAttribute("persontype.list", helper.getResult("results"));
	}

	private void workarea(HttpServletRequest request, ActionForm form) {
		SqlReturn spruturn;
		spruturn = TradeList.G_WorkAreaQuery();
		if (spruturn.getResultCount() > 0) {
			request.setAttribute("workarea.list", spruturn.getResultSet(0));
		}
	}

	private void getcompanyinfo(HttpServletRequest request, String rang) {
		SqlReturn myRuturn = TradeList.G_SYCompGroupListQuery(rang);
		request.setAttribute("companyinfo.list", myRuturn.getResultSet(0));
	}

	private void personrang(HttpServletRequest request) {
		request.setAttribute("rang.list", OptionUtils.getOptions("prov_list"));
	}

	private void isworker(HttpServletRequest request) {
		request.setAttribute("isworker.list",
				OptionUtils.getOptions("worker_list"));
	}

	private void isactive(HttpServletRequest request) {
		request.setAttribute("isactive.list",
				OptionUtils.getOptions("active_list"));
	}

	private void isduty(HttpServletRequest request) {
		request.setAttribute("isduty.list",
				OptionUtils.getOptions("active_list"));
	}

	private void getBelongs(HttpServletRequest request) throws Exception {
		request.setAttribute("belong.list", TradeList.getPersonBelongTypes());
	}

	public ActionForward modifypassword(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String personid = "";
			HttpSession session = request.getSession();
			UserView userinfo = (UserView) session
					.getAttribute(Constants.SESSION_USER);
			personid = userinfo.getPersonId();
			PersonInfoForm aForm = (PersonInfoForm) form;
			String type = request.getParameter("type");
			/* 如果修改密码时 */
			if ("save".equals(type)) {
				CallHelper helper = initializeCallHelper(
						"modifypersonpassword", form, request, false);
				helper.execute();
				request.setAttribute("message.information", helper.getOutput(0));
				aForm.setPersonid(personid);
				aForm.setPassword("");
			} else {
				ArrayList list = new ArrayList();
				list = TradeList.G_PersonInfoOpen("-1", personid);
				list = (ArrayList) list.get(1);
				int i = 0;
				if (list.size() > i) {
					BasicDynaBean rowBean = (BasicDynaBean) list.get(i);
					aForm.setpersonid(MethodFactory.getThisString(rowBean
							.get("personid")));
					aForm.setMobile(MethodFactory.getThisString(rowBean
							.get("mobile")));
					aForm.setMobile2(MethodFactory.getThisString(rowBean
							.get("mobile2")));
					aForm.setOfficenumber(MethodFactory.getThisString(rowBean
							.get("officenumber")));
					aForm.setOfficephone(MethodFactory.getThisString(rowBean
							.get("officephone")));
				}
			}
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
		return actionMapping.findForward("modifypassword");
	}

	public ActionForward initquery(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			department(request, form);
			// CallHelper helper = initializeCallHelper("getPersonsQuery", form,
			// request, false);
			// helper.setParam("departid", "-1");
			// helper.execute();

			// request.setAttribute("personinfo.captionlist",
			// helper.getResult("captions"));
			// request.setAttribute("personinfo.list",
			// helper.getResult("results"));
			PersonInfoForm aForm = (PersonInfoForm) form;
			String isactive = request.getParameter("isactive");
			aForm.setisactive("on");
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
		return actionMapping.findForward("initquery");
	}

	public ActionForward justquery(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			department(request, form);
			CallHelper helper = initializeCallHelper("getPersonsQuery", form,
					request, false);
			helper.execute();

			request.setAttribute("personinfo.captionlist",
					helper.getResult("captions"));
			request.setAttribute("personinfo.list", helper.getResult("results"));

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
		return actionMapping.findForward("justquery");
	}
}
