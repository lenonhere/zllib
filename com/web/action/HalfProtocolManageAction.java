package com.web.action;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.common.tackle.UserView;
import com.qmx.dateutils.DateUtils;
import com.web.CoreDispatchAction;
import com.web.form.HalfProtocolForm;
import com.ws.common.ImportDateFromGJ;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.Executer;
import com.zl.base.core.db.SqlReturn;
import com.zl.common.Constants;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;

public class HalfProtocolManageAction extends CoreDispatchAction {
	static Logger logger = Logger.getLogger(HalfProtocolManageAction.class
			.getName());

	public ActionForward importXYOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StringBuffer messageA = new StringBuffer();
		ImportDateFromGJ importUtil = ImportDateFromGJ.getInstance();
		try {
			if (importUtil.importProtocol == true) {
				request.setAttribute("message", "有人正在导协议，请稍候再试");
				return mapping.findForward("showDetail");
			} else {
				HalfProtocolForm hForm = (HalfProtocolForm) form;
				String begDate = hForm.getBegDate();
				String endDate = hForm.getEndDate();
				String sql = "";
				importUtil.importProtocol = true;
				String predate = OptionUtils.getDateByOffset(begDate, 0);
				String nextdate = OptionUtils.getDateByOffset(begDate, 1);
				Date start = DateUtils.parseDate(predate, "yyyy-MM-dd");
				Date end = DateUtils.parseDate(endDate, "yyyy-MM-dd");
				String opname = "GetCigaXYDataForJAVA";
				while (end.compareTo(start) != -1) {
					String outstr = importUtil.getXMLStr(opname, predate
							+ " 00:00:00", nextdate + " 00:00:00");
					importUtil.HalfProtocolImport(outstr, predate, begDate,
							endDate);
					CallHelper helper = initializeCallHelper(
							"i_xyorder_import", form, request, false);
					helper.setParam("startDate", predate);
					helper.setParam("endDate", nextdate);
					helper.execute();
					String message = (String) helper.getOutput("message");
					if (message != null) {
						messageA.append(predate + "：" + message + "<br>");
						logger.debug(messageA);
					}
					logger.debug(predate + "协议数据导入临时表成功！");

					predate = OptionUtils.getDateByOffset(predate, 1);
					nextdate = OptionUtils.getDateByOffset(nextdate, 1);
					start = DateUtils.parseDate(predate, "yyyy-MM-dd");
				}
				request.setAttribute("message", messageA.toString());
			}
		} catch (Exception e) {
			logger.debug("导入失败");
		} finally {
			importUtil.importProtocol = false;
		}
		return this.importProtocolInit(mapping, form, request, response);
	}

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// 初始化页面数据：默认当前年份、半年
		HalfProtocolForm initForm = (HalfProtocolForm) form;
		String nowYear = Calendar.getInstance().get(Calendar.YEAR) + "";
		String nowHalf = "0";
		if (Calendar.getInstance().get(Calendar.MONTH) > Calendar.JUNE) {
			nowHalf = "1";
		}
		if (initForm.getPlanYear() == null) {
			initForm.setPlanYear(nowYear);
		}
		if (initForm.getPlanHalf() == null) {
			initForm.setPlanHalf(nowHalf);
		}

		// 设置默认计量单位
		initForm.setUnitId("1");

		ArrayList unitList = OptionUtils.getUnitList();
		request.setAttribute("unit.list", unitList);
		request.setAttribute("year.list", OptionUtils.getYearList(-3, 3));
		request.setAttribute("half.list", OptionUtils.getHalfList2());

		String personId = getPersonId(request);
		CallHelper caller = new CallHelper("getAreaCompanyTreeInRight");
		caller.setParam("areaCode", "999999");
		caller.setParam("userId", personId);
		caller.execute();
		List provinceList = caller.getResult(0);
		request.setAttribute("province.list", provinceList);

		return mapping.findForward("init");
	}

	/** 查询省外合同的基本信息 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HalfProtocolForm myForm = (HalfProtocolForm) form;
		String provinceInfo = myForm.getProvinceInfo();
		String PlanYear = myForm.getPlanYear();
		String PlanHalf = myForm.getPlanHalf();
		String halfprotocolCode = myForm.gethalfprotocolCode();
		String unitId = myForm.getUnitId();

		if ((provinceInfo == null || provinceInfo.equals(""))
				&& (halfprotocolCode == null || halfprotocolCode.trim()
						.length() == 0)) {
			return mapping.findForward("list");
		}

		CallHelper caller = new CallHelper("getHalfProtocolBaseInfo");
		caller.autoCopy(myForm);
		caller.execute();

		if (caller.getResultCount() == 1) {
			List halfprotocolList = caller.getResult(0);
			request.setAttribute("halfprotocol.list", halfprotocolList);
		}

		return mapping.findForward("list");
	}

	/**
	 * 保存合同修改信息 更新合同基本信息：合同号、批号 画面：合同列表页面
	 */
	public ActionForward saveBase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		UserView userinfo = (UserView) session
				.getAttribute(Constants.SESSION_USER);
		String personId = userinfo.getPersonId();

		HalfProtocolForm myForm = (HalfProtocolForm) form;
		String halfprotocolInfo = myForm.gethalfprotocolInfo();

		CallHelper caller = new CallHelper("updateContractBaseInfo");
		// caller.autoCopy(myForm);
		caller.setParam("personId", personId);
		caller.setParam("planYear", myForm.getPlanYear());
		caller.setParam("planHalf", myForm.getPlanHalf());
		caller.setParam("halfprotocolInfo", halfprotocolInfo);
		caller.execute();

		String msgInfo = (String) caller.getOutput(0);
		String msgCode = (String) caller.getOutput(1);
		request.setAttribute("message.information", msgInfo);
		request.setAttribute("message.code", msgCode);

		return query(mapping, form, request, response);
	}

	/**
	 * 取得公司列表
	 */
	public ActionForward corp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HalfProtocolForm myForm = (HalfProtocolForm) form;
		String planYear = myForm.getPlanYear();
		String planHalf = myForm.getPlanHalf();
		String unitId = myForm.getUnitId();
		String unitName = MethodFactory.toGb2312(myForm.getUnitName());

		CallHelper caller = new CallHelper("getAreaCompanyTreeInRight");
		caller.setParam("areaCode", "900000");
		caller.setParam("userId", this.getPersonId(request));
		caller.execute();
		List corpList = caller.getResult(0);

		// 设置每个公司的链接地址
		String url = "../contractmanage.do?method=addInit";
		url = url + "&contractYear=" + planYear + "&contractHalf=" + planHalf;
		url = url + "&unitId=" + unitId + "&unitName=" + unitName;
		for (int i = 0; i < corpList.size(); i++) {
			BasicDynaBean bean = (BasicDynaBean) corpList.get(i);
			String parent_id = bean.get("parentcode").toString();
			if ("1".equals(bean.get("ifcompany"))) {
				String sn = url + "&companyId=" + bean.get("code")
						+ "&companyName=" + bean.get("shortname")
						+ "&provinceId=" + parent_id;
				bean.set("ordertag", sn);
			} else {
				bean.set("ordertag", "");
			}
		}
		request.setAttribute("corplist", corpList);

		return mapping.findForward("corp");
	}

	/**
	 * 获取待修改合同的详细信息：卷烟、数量 显示合同修改画面
	 */
	public ActionForward editInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HalfProtocolForm myForm = (HalfProtocolForm) form;
		String halfprotocolId = myForm.gethalfprotocolId();
		String unitId = myForm.getUnitId();
		String unitName = myForm.getUnitName();
		String tobaFlag = request.getParameter("tobaFlag");

		request.setAttribute("year.list", OptionUtils.getYearList(-3, 3));
		request.setAttribute("half.list", OptionUtils.getHalfList2());

		CallHelper caller = new CallHelper("getHalfProtocolInfo");
		caller.setParam("halfprotocolId", halfprotocolId);
		caller.setParam("unitId", unitId);
		caller.setParam("tobaFlag", tobaFlag);
		caller.execute();

		if (caller.getResultCount() == 2) {
			List baseList = caller.getResult(0);
			if (baseList.size() > 0) {
				BasicDynaBean bean = (BasicDynaBean) baseList.get(0);
				String halfprotocolCode = MethodFactory.getThisString(bean
						.get("halfprotocol_code"));
				myForm.sethalfprotocolCode(halfprotocolCode);
				String planYear = MethodFactory.getThisString(bean
						.get("plan_year"));
				myForm.setPlanYear(planYear);
				String planHalf = MethodFactory.getThisString(bean
						.get("plan_half"));
				myForm.setPlanHalf(planHalf);
				String companyId = MethodFactory.getThisString(bean
						.get("company_id"));
				myForm.setCompanyId(companyId);
				String companyName = MethodFactory.getThisString(bean
						.get("company_name"));
				myForm.setCompanyName(companyName);

				String totalAmount = MethodFactory.getThisString(bean
						.get("total_ctamount"));
				String totalMoney = MethodFactory.getThisString(bean
						.get("total_ctmoney"));

				request.setAttribute("totalAmount", totalAmount);
				request.setAttribute("totalMoney", totalMoney);
			}

			List detailList = caller.getResult(1);
			request.setAttribute("detail.list", detailList);
		}

		return mapping.findForward("edit");
	}

	/**
	 * 编辑合同数据的保存
	 */
	public ActionForward editSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String personId = getPersonId(request);
		HalfProtocolForm myForm = (HalfProtocolForm) form;
		/*
		 * String halfprotocolId = myForm.gethalfprotocolId(); String
		 * halfprotocolCode = myForm.gethalfprotocolCode(); String companyId =
		 * myForm.getCompanyId(); String planYear = myForm.getPlanYear(); String
		 * planHalf = myForm.getPlanHalf(); String unitId = myForm.getUnitId();
		 * String halfprotocolInfo = myForm.gethalfprotocolInfo();
		 */

		logger.info("合同修改保存:" + myForm.getPlanInfo());

		CallHelper caller = initializeCallHelper("updateHalfProtocolInfo",
				myForm, request, false);
		// caller.autoCopy(myForm);
		caller.setParam("personId", personId);
		caller.execute();

		String msgInfo = (String) caller.getOutput(0);
		String msgCode = (String) caller.getOutput(1);
		request.setAttribute("message.info", msgInfo);
		request.setAttribute("message.code", msgCode);
		return mapping.findForward("post");
	}

	/** 打开数据页面 */
	public ActionForward post(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("post");
	}

	/**
	 * 取出合同详细信息,(add by zlq)
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getContractDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// 取得合同信息
		CallHelper caller = new CallHelper("getContractInfoforHY");
		caller.setParam("contractId",
				(String) request.getParameter("contractid"));
		caller.setParam("unitId", "1");
		caller.execute();

		List base = caller.getResult(0);
		BasicDynaBean bean = (BasicDynaBean) base.get(0);
		HalfProtocolForm orderForm = (HalfProtocolForm) form;
		// orderForm.setContractId(MethodFactory.getThisString(bean.get("contract_id")));
		// orderForm.setContractCode(MethodFactory.getThisString(bean.get("contract_code")));
		// orderForm.setCompanyId(MethodFactory.getThisString(bean.get("company_id")));
		// orderForm.setCompanyName(MethodFactory.getThisString(bean.get("company_name")));
		// orderForm.setConsignDate(OptionUtils.getSystemDate());
		// orderForm.setConfirmWeekPlanDate(OptionUtils.getSystemDate());
		// orderForm.setReceiveDate(OptionUtils.getSystemDate());
		// orderForm.setDest(MethodFactory.getThisString(bean.get("tfcompany_id")));
		// orderForm.setTerm(MethodFactory.getThisString(bean.get("term")));
		// orderForm.setContractType(MethodFactory.getThisString(bean.get("contract_type")));
		// orderForm.setBpcsdb(MethodFactory.getThisString(bean.get("databaseid")));
		request.setAttribute("detail", caller.getResult(1));
		request.setAttribute("type", request.getParameter("type"));

		// 取得投放地
		caller = new CallHelper("getDest");
		caller.setParam("companyID", bean.get("company_id").toString());
		caller.execute();
		request.setAttribute("dest", caller.getResult(0));

		// if(request.getParameter("type") != null &&
		// request.getParameter("type").toString().equals("1"))
		return mapping.findForward("contractdetail");
		// else
		// return mapping.findForward("nbcontractdetail");
	}

	/**
	 * 预下单时更新合同(add by zlq)
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateContract(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HalfProtocolForm orderForm = (HalfProtocolForm) form;
		CallHelper caller = new CallHelper("updateContractWhenOrder");
		// String contractId = orderForm.getContractId();
		// caller.setParam("contractID", orderForm.getContractId());
		// caller.setParam("consignDate", orderForm.getConsignDate());
		// caller.setParam("confirmWeekPlanDate",
		// orderForm.getConfirmWeekPlanDate());
		// caller.setParam("receiveDate", orderForm.getReceiveDate());
		// caller.setParam("tfcompanyId", orderForm.getDest());
		// caller.setParam("dest", orderForm.getDestname());
		// caller.setParam("contractInfo", orderForm.getContractInfo());
		// caller.setParam("unitID", "1");
		// caller.setParam("message", "");
		caller.execute();
		request.setAttribute("msg", caller.getOutput(0));

		caller = new CallHelper("getDest");
		caller.setParam("companyID", orderForm.getCompanyId());
		caller.execute();
		request.setAttribute("dest", caller.getResult(0));

		// 更新预警信息
		/*
		 * caller = new CallHelper("flashCondownAlert");
		 * caller.setParam("companyID", orderForm.getCompanyId());
		 * caller.setParam("cycle", "14"); caller.execute();
		 */
		return mapping.findForward("contractdetail");
	}

	/**
	 * 取得公司(只有杭烟合同的)列表
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getCompList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper caller = new CallHelper("getAreaCompanyTreeHYPlace");
		caller.setParam("areaCode", "900000");
		caller.setParam("userId", this.getPersonId(request));
		caller.execute();
		request.setAttribute("companyTree", caller.getResult(0));
		return mapping.findForward("listcorp");
	}

	public ActionForward getNBCompList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper caller = new CallHelper("getAreaCompanyTreeNBPlace");
		caller.setParam("areaCode", "900000");
		caller.setParam("userId", this.getPersonId(request));
		caller.execute();
		request.setAttribute("companyTree", caller.getResult(0));
		return mapping.findForward("listcorp");
	}

	public ActionForward getCompListInRight(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper caller = new CallHelper("getAreaCompanyTreeInRight");
		caller.setParam("areaCode", "900000");
		caller.setParam("userId", this.getPersonId(request));
		caller.execute();
		request.setAttribute("companyTree", caller.getResult(0));
		return mapping.findForward("listcorp");
	}

	/**
	 * 查询要报送的合同(add by zlq)
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryTransported(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HalfProtocolForm orderForm = (HalfProtocolForm) form;
		CallHelper caller = new CallHelper("getContractToPlace");
		// String contractId = orderForm.getContractId();
		caller.setParam("companyID", orderForm.getCompanyId());
		caller.setParam("type", (String) request.getParameter("type"));
		// caller.setParam("beginDate", orderForm.getBeginDate());
		// caller.setParam("endDate", orderForm.getEndDate());
		// caller.setParam("contractCode", orderForm.getContractCode());
		caller.setParam("message", "");
		caller.execute();

		request.setAttribute("caption.list", caller.getResult(0));
		request.setAttribute("result.list", caller.getResult(1));

		return mapping.findForward("hzcontract");
	}

	/**
	 * 按合同号查询要下单的合同(add by zlq)
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initUnTransported(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HalfProtocolForm orderForm = (HalfProtocolForm) form;
		// orderForm.setBeginDate(OptionUtils.getSystemDate());
		// orderForm.setEndDate(OptionUtils.getSystemDate());

		return mapping.findForward("initUnTransported");
	}

	/**
	 * 按合同号查询要下单的合同(add by zlq)
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryUnTransported(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HalfProtocolForm orderForm = (HalfProtocolForm) form;
		CallHelper caller = new CallHelper("getContractUnTransported");
		caller.setParam("companyId", orderForm.getCompanyId());
		// caller.setParam("beginDate", orderForm.getBeginDate());
		// caller.setParam("endDate", orderForm.getEndDate());
		// caller.setParam("contractCode", orderForm.getContractCode());
		// caller.setParam("message", "");
		caller.execute();
		request.setAttribute("caption.list", caller.getResult(0));
		request.setAttribute("result.list", caller.getResult(1));

		return mapping.findForward("unTransported");
	}

	/**
	 * 按合同号查询要下单的合同明细(add by zlq)
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryUnTransportedDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// 取得合同信息
		CallHelper caller = new CallHelper("getContractInfoforHY");
		caller.setParam("contractId",
				(String) request.getParameter("contractid"));
		caller.setParam("unitId", "1");
		caller.execute();

		List base = caller.getResult(0);
		BasicDynaBean bean = (BasicDynaBean) base.get(0);
		// HalfProtocolForm orderForm = (HalfProtocolForm) form;
		// orderForm.setContractId(MethodFactory.getThisString(bean.get("contract_id")));
		// orderForm.setContractCode(MethodFactory.getThisString(bean.get("contract_code")));
		// orderForm.setCompanyId(MethodFactory.getThisString(bean.get("company_id")));
		// orderForm.setCompanyName(MethodFactory.getThisString(bean.get("company_name")));
		// //String consignDate =
		// MethodFactory.getThisString(bean.get("consign_date"));//modified by
		// zlq 2006.2.22 23:26:00
		// orderForm.setConsignDate(OptionUtils.getSystemDate());
		// //String confirmWeekPlanDate =
		// MethodFactory.getThisString(bean.get("confirmweekplan_date"));
		// orderForm.setConfirmWeekPlanDate(OptionUtils.getSystemDate());
		// //String receiveDate =
		// MethodFactory.getThisString(bean.get("receive_date"));
		// orderForm.setReceiveDate(OptionUtils.getSystemDate());
		// orderForm.setDest(MethodFactory.getThisString(bean.get("tfcompany_id")));
		// orderForm.setTerm(MethodFactory.getThisString(bean.get("term")));
		// orderForm.setContractType(MethodFactory.getThisString(bean.get("contract_type")));
		// orderForm.setBpcsdb(MethodFactory.getThisString(bean.get("databaseid")));
		// request.setAttribute("detail", caller.getResult(1));
		request.setAttribute("type", request.getParameter("type"));

		// 取得投放地
		caller = new CallHelper("getDest");
		caller.setParam("companyID", bean.get("company_id").toString());
		caller.execute();
		request.setAttribute("dest", caller.getResult(0));

		return mapping.findForward("unTransportedDetail");
	}

	/**
	 * 预下单时更新合同(add by zlq)
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateContractUnTransported(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HalfProtocolForm orderForm = (HalfProtocolForm) form;
		CallHelper caller = new CallHelper("updateContractWhenOrder");
		// String contractId = orderForm.getContractId();
		// caller.setParam("contractID", orderForm.getContractId());
		// caller.setParam("consignDate", orderForm.getConsignDate());
		// caller.setParam("confirmWeekPlanDate",
		// orderForm.getConfirmWeekPlanDate());
		// caller.setParam("receiveDate", orderForm.getReceiveDate());
		// caller.setParam("tfcompanyId", orderForm.getDest());
		// caller.setParam("dest", orderForm.getDestname());
		// caller.setParam("contractInfo", orderForm.getContractInfo());
		// caller.setParam("unitID", "1");
		// caller.setParam("message", "");
		caller.execute();
		request.setAttribute("msg", caller.getOutput(0));

		caller = new CallHelper("getDest");
		caller.setParam("companyID", orderForm.getCompanyId());
		caller.execute();
		request.setAttribute("dest", caller.getResult(0));

		// 更新预警信息
		/*
		 * caller = new CallHelper("flashCondownAlert");
		 * caller.setParam("companyID", orderForm.getCompanyId());
		 * caller.setParam("cycle", "14"); caller.execute();
		 */
		return mapping.findForward("unTransportedDetail");
	}

	/**
	 * 杭烟统一报送
	 */
	public ActionForward placeOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HalfProtocolForm orderForm = (HalfProtocolForm) form;
		// String contractId = orderForm.getContractId();
		// String [] contractIdSet = contractId.split(",");
		// for(int i=0;i<contractIdSet.length;i++)
		// {
		// CallHelper caller1 = new CallHelper("placeContract");
		// caller1.setParam("contractId", contractIdSet[i]);
		// caller1.setParam("message", "");
		// caller1.execute();
		//
		// // 下单到杭烟ERP数据库
		// caller1 = new CallHelper("getContractInfoforHY");
		// caller1.setParam("contractId", contractIdSet[i]);
		// caller1.setParam("unitId", "1");
		// caller1.execute();
		//
		// List contract = caller1.getResult(0);
		// List contractdetail = caller1.getResult(1);
		// int csize = contractdetail.size();
		//
		// BasicDynaBean bean = (BasicDynaBean) contract.get(0);
		// StringBuffer sb = new StringBuffer();
		// OracleBean ob = new OracleBean();
		// // DataSet ds = new DataSet();
		// for (int j = 1; csize > 0; csize--, j++) {
		// BasicDynaBean beandetail = (BasicDynaBean) contractdetail.get(j - 1);
		// sb.delete(0, sb.length());
		// sb.append("insert into teemserp.tzhyxsw(ID,CUSTOMER,VOUCHERID,PAYERCUSTOMER,RECTTIME,SHIPMODE,PAYMODE,DEST,FLAG,ITEM,PRICE,UOM,QUANTITY,PLAN_YC,oprdate,INVOICE_DATE,RECEIVE_DATE) ");
		// sb.append(" values(");
		// sb.append(beandetail.get("seq"));
		// sb.append(",'");
		// sb.append(bean.get("hy_eas_code"));
		// sb.append("','");
		// sb.append(bean.get("contract_code"));
		// sb.append("','");
		// sb.append(bean.get("hy_eas_code"));
		// sb.append("',to_date('");
		// sb.append(bean.get("confirmweekplan_date"));
		// sb.append("','yyyy-mm-dd'),'");
		// sb.append("联运");
		// sb.append("','");
		// sb.append("托收承付");
		// sb.append("','");
		// sb.append(bean.get("dest"));
		// sb.append("','");
		// sb.append("1");
		// sb.append("','");
		// // 明细开始
		// sb.append(beandetail.get("jg_code"));
		// sb.append("','");
		// sb.append(beandetail.get("tobacco_price"));
		// sb.append("','");
		// sb.append("1");
		// sb.append("','");
		// sb.append(beandetail.get("confirm_week_amount"));
		// sb.append("','");
		// sb.append("2006");
		// sb.append("',to_date('");
		// sb.append(OptionUtils.getSystemDate());
		// sb.append("','yyyy-mm-dd'),to_date('");
		// // 明细结束
		// sb.append(bean.get("consign_date"));
		// sb.append("','yyyy-mm-dd'),to_date('");
		// sb.append(bean.get("receive_date"));
		// sb.append("','yyyy-mm-dd'))");
		// ob.executeSQL(sb.toString());
		// }
		// }
		request.setAttribute("message", "报送成功！！");
		return mapping.findForward("hzcontract");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		UserView userinfo = (UserView) session
				.getAttribute(Constants.SESSION_USER);
		String personId = userinfo.getPersonId();

		HalfProtocolForm deleteForm = (HalfProtocolForm) form;
		String halfprotocolInfo = deleteForm.gethalfprotocolInfo();

		CallHelper caller = new CallHelper("deleteHp");
		caller.setParam("personId", personId);
		caller.setParam("halfprotocolInfo", halfprotocolInfo);
		caller.execute();
		request.setAttribute("message.info", caller.getOutput(0));
		request.setAttribute("message.code", caller.getOutput(1));

		return query(mapping, form, request, response);
	}

	public ActionForward changeStateInit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HalfProtocolForm initForm = (HalfProtocolForm) form;
		// initForm.setBeginDate(MethodFactory.getCurrentMonthFirst());
		// initForm.setEndDate(MethodFactory.getDate());

		request.setAttribute("order.list", OptionUtils.getOptions("order_list"));
		request.setAttribute("place.list", OptionUtils.getOptions("place_list"));

		return mapping.findForward("changeState");
	}

	/**
	 * 查询单个合同(add by zby)
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryContract(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// 取得合同信息
		HalfProtocolForm orderForm = (HalfProtocolForm) form;
		CallHelper caller = new CallHelper("getContractToChangeState");
		// String contractId = orderForm.getContractId();
		caller.setParam("companyId", orderForm.getCompanyId());
		// caller.setParam("type", (String)request.getParameter("type"));
		// caller.setParam("beginDate", orderForm.getBeginDate());
		// caller.setParam("endDate", orderForm.getEndDate());
		// caller.setParam("contractCode", orderForm.getContractCode());
		// caller.setParam("message", "");
		caller.execute();

		request.setAttribute("caption.list", caller.getResult(0));
		request.setAttribute("result.list", caller.getResult(1));

		/*
		 * CallHelper caller = new CallHelper("getContractInfoByCode");
		 * caller.setParam("contractCode", contractCode);
		 * caller.setParam("unitId", "1"); caller.execute();
		 *
		 * List base = caller.getResult(0); BasicDynaBean bean =
		 * (BasicDynaBean)base.get(0); HalfProtocolForm orderForm =
		 * (HalfProtocolForm) form;
		 * orderForm.setContractId(MethodFactory.getThisString
		 * (bean.get("contractid")));
		 * orderForm.setIsTransported(MethodFactory.getThisString
		 * (bean.get("istransported")));
		 * orderForm.setIsPlaced(MethodFactory.getThisString
		 * (bean.get("isplaced")));
		 * orderForm.setDest(MethodFactory.getThisString(bean.get("dest")));
		 * myForm
		 * .setCompanyName(MethodFactory.getThisString(bean.get("company_name"
		 * )));
		 *
		 * List detailList = caller.getResult(1);
		 * request.setAttribute("detail.list", detailList);
		 */
		request.setAttribute("order.list", OptionUtils.getOptions("order_list"));
		request.setAttribute("place.list", OptionUtils.getOptions("place_list"));

		return mapping.findForward("listState");
	}

	public ActionForward changeContractState(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		HalfProtocolForm myForm = (HalfProtocolForm) form;
		// String contractId = myForm.getContractId();
		// String isTransported = myForm.getIsTransported();
		// String isPlaced = myForm.getIsPlaced();

		String personId = getPersonId(request);
		CallHelper caller = new CallHelper("updateContractState");
		caller.setParam("personId", personId);
		// caller.setParam("contractId", contractId);
		// caller.setParam("isTransported", isTransported);
		// caller.setParam("isPlaced", isPlaced);
		caller.execute();

		String msgInfo = (String) caller.getOutput(0);
		String msgCode = (String) caller.getOutput(1);
		request.setAttribute("message.info", msgInfo);
		request.setAttribute("message.code", msgCode);

		request.setAttribute("order.list", OptionUtils.getOptions("order_list"));
		request.setAttribute("place.list", OptionUtils.getOptions("place_list"));

		return mapping.findForward("listState");
	}

	// 离线导入协议初始化
	public ActionForward importProtocolInit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HalfProtocolForm hform = (HalfProtocolForm) form;
		Calendar c = Calendar.getInstance();
		String date = c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-"
				+ c.get(c.DATE);
		hform.setBegDate(date);
		hform.setEndDate(date);
		return mapping.findForward("importProtocolInit");
	}

	// 离线导入协议
	public ActionForward importProtocol(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Executer execute = Executer.getInstance();

		String strSql = "delete from I_HalfProtocol ";
		SqlReturn result = execute.ExecUpdateSQL(strSql);

		// 协议
		try {
			HalfProtocolForm halfPForm = (HalfProtocolForm) form;
			if (halfPForm.getInfomation() == null
					|| halfPForm.getInfomation().equals("")) {
				return mapping.findForward("showUpdatedProtocol");
			}
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT>");
			sb.append(halfPForm.getInfomation());
			sb.append("</ROOT>");

			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			StringReader sd = new StringReader(sb.toString());
			InputSource inp = new InputSource(sd);
			Document document = builder.parse(inp);

			NodeList ndblist = document.getChildNodes().item(0).getChildNodes();
			sb = new StringBuffer();
			for (int i = 0; i < ndblist.getLength(); i++) {
				NodeList ndblisttemp = ndblist.item(i).getChildNodes();
				String order_id = "";
				String bmem_id = "";
				String bname = "";
				String deli_date = "";
				String pkind = "";
				String smem_id = "";
				String year = "";
				String half = "";
				String behalfid = "";
				for (int k = 0; k < ndblisttemp.getLength(); k++) {
					if (ndblisttemp.item(k).getChildNodes().item(0)
							.getNodeType() == 3) {
						String nodeName = ndblisttemp.item(k).getNodeName();
						nodeName = nodeName.toLowerCase();
						if (nodeName.equals("order_id"))
							order_id = ndblisttemp.item(k).getChildNodes()
									.item(0).getNodeValue();
						if (nodeName.equals("bmem_id"))
							bmem_id = ndblisttemp.item(k).getChildNodes()
									.item(0).getNodeValue();
						if (nodeName.equals("bname"))
							bname = ndblisttemp.item(k).getChildNodes().item(0)
									.getNodeValue();
						if (nodeName.equals("deli_date")) {
							deli_date = ndblisttemp.item(k).getChildNodes()
									.item(0).getNodeValue();
							year = deli_date.substring(0, 4);
							half = deli_date.substring(5, 6);
						}
						if (nodeName.equals("pkind"))
							pkind = ndblisttemp.item(k).getChildNodes().item(0)
									.getNodeValue();
						if (nodeName.equals("smem_id"))
							smem_id = ndblisttemp.item(k).getChildNodes()
									.item(0).getNodeValue();
					}
				}
				behalfid = "1";
				if (smem_id.equals("12420101"))
					behalfid = "3"; // 武汉烟草(集团)有限公司

				if (half.equals("下"))
					half = "1";
				else
					half = "0";
				strSql = "insert into I_HalfProtocol(ProtocolCode,ProtocolYear,ProtocolHalf,SycompanyCode,SycompanyName,BehalfId,Memo) "
						+ " values ('"
						+ order_id
						+ "','"
						+ year
						+ "','"
						+ half
						+ "','"
						+ bmem_id
						+ "','"
						+ bname
						+ "',"
						+ behalfid
						+ ",'" + pkind + "') ";
				execute.ExecUpdateSQL(strSql);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		// 明细
		strSql = "delete from I_HalfProtocolDetail ";
		result = execute.ExecUpdateSQL(strSql);

		try {
			HalfProtocolForm halfPForm = (HalfProtocolForm) form;
			if (halfPForm.getInfomation1() == null
					|| halfPForm.getInfomation1().equals("")) {
				return mapping.findForward("showUpdatedProtocol");
			}
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT>");
			sb.append(halfPForm.getInfomation1());
			sb.append("</ROOT>");

			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			StringReader sd = new StringReader(sb.toString());
			InputSource inp = new InputSource(sd);
			Document document = builder.parse(inp);

			NodeList ndblist = document.getChildNodes().item(0).getChildNodes();
			sb = new StringBuffer();
			for (int i = 0; i < ndblist.getLength(); i++) {
				NodeList ndblisttemp = ndblist.item(i).getChildNodes();
				String order_id = "";
				String product_id = "";
				String quantity = "";
				String price = "";
				String plate = "";
				for (int k = 0; k < ndblisttemp.getLength(); k++) {
					if (ndblisttemp.item(k).getChildNodes().item(0)
							.getNodeType() == 3) {
						String nodeName = ndblisttemp.item(k).getNodeName();
						nodeName = nodeName.toLowerCase();
						if (nodeName.equals("order_id"))
							order_id = ndblisttemp.item(k).getChildNodes()
									.item(0).getNodeValue();
						if (nodeName.equals("product_id"))
							product_id = ndblisttemp.item(k).getChildNodes()
									.item(0).getNodeValue();
						if (nodeName.equals("quantity"))
							quantity = ndblisttemp.item(k).getChildNodes()
									.item(0).getNodeValue();
						if (nodeName.equals("price"))
							price = ndblisttemp.item(k).getChildNodes().item(0)
									.getNodeValue();
						if (nodeName.equals("plate"))
							plate = ndblisttemp.item(k).getChildNodes().item(0)
									.getNodeValue();
					}
				}
				strSql = "insert into I_HalfProtocolDetail(ProtocolCode,TobaccoCode,Plate,quantity,Price) "
						+ " values ('"
						+ order_id
						+ "','"
						+ product_id
						+ "','"
						+ plate + "'," + quantity + "," + price + " ) ";
				execute.ExecUpdateSQL(strSql);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		CallHelper caller = new CallHelper("importHalfProtocol");
		caller.setParam("personId", getPersonId(request));
		caller.execute();

		request.setAttribute("caption.list", caller.getResult(0));
		request.setAttribute("result.list", caller.getResult(1));

		return mapping.findForward("showUpdatedProtocol");
	}

	// 离线导入公司基本信息初始化
	public ActionForward importCompInfoInit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("importCompInfoInit");
	}

	// 离线导入公司基本信息 :税号，银行帐号，银行名称，投放地对应
	public ActionForward importCompinfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Executer execute = Executer.getInstance();

		// //公司银行信息
		String strSql = "delete from I_Bank ";
		SqlReturn result = execute.ExecUpdateSQL(strSql);

		try {
			HalfProtocolForm halfPForm = (HalfProtocolForm) form;
			if (halfPForm.getInfomation() == null
					|| halfPForm.getInfomation().equals("")) {
				return mapping.findForward("importCompInfoShow");
			}
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT>");
			sb.append(halfPForm.getInfomation());
			sb.append("</ROOT>");

			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			StringReader sd = new StringReader(sb.toString());
			InputSource inp = new InputSource(sd);
			Document document = builder.parse(inp);

			NodeList ndblist = document.getChildNodes().item(0).getChildNodes();
			sb = new StringBuffer();
			for (int i = 0; i < ndblist.getLength(); i++) {
				NodeList ndblisttemp = ndblist.item(i).getChildNodes();
				String mem_id = "";
				String id = "";
				String bank_id = "";
				String bank = "";
				for (int k = 0; k < ndblisttemp.getLength(); k++) {
					if (ndblisttemp.item(k).getChildNodes().item(0)
							.getNodeType() == 3) {
						String nodeName = ndblisttemp.item(k).getNodeName();
						nodeName = nodeName.toLowerCase();
						if (nodeName.equals("mem_id"))
							mem_id = ndblisttemp.item(k).getChildNodes()
									.item(0).getNodeValue();
						if (nodeName.equals("id"))
							id = ndblisttemp.item(k).getChildNodes().item(0)
									.getNodeValue();
						if (nodeName.equals("bank_id"))
							bank_id = ndblisttemp.item(k).getChildNodes()
									.item(0).getNodeValue();
						if (nodeName.equals("bank"))
							bank = ndblisttemp.item(k).getChildNodes().item(0)
									.getNodeValue();
					}
				}
				strSql = "insert into I_Bank(mem_id, id, bank_id, bank) "
						+ " values ('" + mem_id + "','" + id + "','" + bank_id
						+ "','" + bank + "') ";
				execute.ExecUpdateSQL(strSql);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		// 公司税号信息
		strSql = "delete from I_Tax ";
		result = execute.ExecUpdateSQL(strSql);

		try {
			HalfProtocolForm halfPForm = (HalfProtocolForm) form;
			if (halfPForm.getInfomation1() == null
					|| halfPForm.getInfomation1().equals("")) {
				return mapping.findForward("importCompInfoShow");
			}
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT>");
			sb.append(halfPForm.getInfomation1());
			sb.append("</ROOT>");

			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			StringReader sd = new StringReader(sb.toString());
			InputSource inp = new InputSource(sd);
			Document document = builder.parse(inp);

			NodeList ndblist = document.getChildNodes().item(0).getChildNodes();
			sb = new StringBuffer();
			for (int i = 0; i < ndblist.getLength(); i++) {
				NodeList ndblisttemp = ndblist.item(i).getChildNodes();
				String mem_id = "";
				String id = "";
				String tax_id = "";
				for (int k = 0; k < ndblisttemp.getLength(); k++) {
					if (ndblisttemp.item(k).getChildNodes().item(0)
							.getNodeType() == 3) {
						String nodeName = ndblisttemp.item(k).getNodeName();
						nodeName = nodeName.toLowerCase();
						if (nodeName.equals("mem_id"))
							mem_id = ndblisttemp.item(k).getChildNodes()
									.item(0).getNodeValue();
						if (nodeName.equals("id"))
							id = ndblisttemp.item(k).getChildNodes().item(0)
									.getNodeValue();
						if (nodeName.equals("tax_id"))
							tax_id = ndblisttemp.item(k).getChildNodes()
									.item(0).getNodeValue();
					}
				}
				strSql = "insert into I_Tax(mem_id, id, tax_id) "
						+ " values ('" + mem_id + "','" + id + "','" + tax_id
						+ " ') ";
				execute.ExecUpdateSQL(strSql);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		// 公司投放地信息
		strSql = "delete from I_To ";
		result = execute.ExecUpdateSQL(strSql);

		try {
			HalfProtocolForm halfPForm = (HalfProtocolForm) form;
			if (halfPForm.getInfomation2() == null
					|| halfPForm.getInfomation2().equals("")) {
				return mapping.findForward("importCompInfoShow");
			}
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT>");
			sb.append(halfPForm.getInfomation2());
			sb.append("</ROOT>");

			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			StringReader sd = new StringReader(sb.toString());
			InputSource inp = new InputSource(sd);
			Document document = builder.parse(inp);

			NodeList ndblist = document.getChildNodes().item(0).getChildNodes();
			sb = new StringBuffer();
			for (int i = 0; i < ndblist.getLength(); i++) {
				NodeList ndblisttemp = ndblist.item(i).getChildNodes();
				String mem_id = "";
				String id = "";
				String dest = "";
				String area_id = "";
				for (int k = 0; k < ndblisttemp.getLength(); k++) {
					if (ndblisttemp.item(k).getChildNodes().item(0)
							.getNodeType() == 3) {
						String nodeName = ndblisttemp.item(k).getNodeName();
						nodeName = nodeName.toLowerCase();
						if (nodeName.equals("mem_id"))
							mem_id = ndblisttemp.item(k).getChildNodes()
									.item(0).getNodeValue();
						if (nodeName.equals("id"))
							id = ndblisttemp.item(k).getChildNodes().item(0)
									.getNodeValue();
						if (nodeName.equals("dest"))
							dest = ndblisttemp.item(k).getChildNodes().item(0)
									.getNodeValue();
						if (nodeName.equals("area_id"))
							area_id = ndblisttemp.item(k).getChildNodes()
									.item(0).getNodeValue();
					}
				}
				strSql = "insert into I_To(mem_id, id, dest, area_id) "
						+ " values ('" + mem_id + "','" + id + "','" + dest
						+ "','" + area_id + " ') ";
				execute.ExecUpdateSQL(strSql);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		CallHelper caller = new CallHelper("importCompInfo");
		caller.setParam("personId", getPersonId(request));
		caller.execute();

		request.setAttribute("caption.list", caller.getResult(0));
		request.setAttribute("result.list", caller.getResult(1));

		return mapping.findForward("importCompInfoShow");
	}

	/**
	 * 取得合同类型列表（计划内；计划外） G_PUBLICCODES.PARENTID=63
	 */
	private List getCTTypeList() {
		return getPublicCodes("63");
	}

	/**
	 * 取得合同执行状态列表（未下单，已下单） G_PUBLICCODES.PARENTID=605
	 */
	private List getExecStateList() {
		return getPublicCodes("605");
	}

	/**
	 * 协议启用、弃用
	 */
	public ActionForward inuse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// 初始化页面数据：默认当前年份、半年
		HalfProtocolForm initForm = (HalfProtocolForm) form;
		String nowYear = Calendar.getInstance().get(Calendar.YEAR) + "";
		String nowHalf = "0";
		if (Calendar.getInstance().get(Calendar.MONTH) > Calendar.JUNE) {
			nowHalf = "1";
		}
		if (initForm.getPlanYear() == null) {
			initForm.setPlanYear(nowYear);
		}
		if (initForm.getPlanHalf() == null) {
			initForm.setPlanHalf(nowHalf);
		}

		// 设置默认计量单位
		initForm.setUnitId("1");

		ArrayList unitList = OptionUtils.getUnitList();
		request.setAttribute("unit.list", unitList);
		request.setAttribute("year.list", OptionUtils.getYearList(-3, 3));
		request.setAttribute("half.list", OptionUtils.getHalfList2());

		String personId = getPersonId(request);
		CallHelper caller = new CallHelper("getAreaCompanyTreeInRight");
		caller.setParam("areaCode", "999999");
		caller.setParam("userId", personId);
		caller.execute();
		List provinceList = caller.getResult(0);
		request.setAttribute("province.list", provinceList);

		return mapping.findForward("inuse");
	}

	public ActionForward queryInuse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HalfProtocolForm myForm = (HalfProtocolForm) form;
		String provinceInfo = myForm.getProvinceInfo();
		String PlanYear = myForm.getPlanYear();
		String PlanHalf = myForm.getPlanHalf();
		String halfprotocolCode = myForm.gethalfprotocolCode();
		String unitId = myForm.getUnitId();

		if ((provinceInfo == null || provinceInfo.equals(""))
				&& (halfprotocolCode == null || halfprotocolCode.trim()
						.length() == 0)) {
			return mapping.findForward("inuselist");
		}

		CallHelper caller = new CallHelper("HalfProtocolInuseInfo");// getHalfProtocolBaseInfo
		caller.autoCopy(myForm);
		caller.execute();

		if (caller.getResultCount() == 1) {
			List halfprotocolList = caller.getResult(0);
			request.setAttribute("halfprotocol.list", halfprotocolList);
		}

		return mapping.findForward("inuselist");// list
	}

	public ActionForward saveInuse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		UserView userinfo = (UserView) session
				.getAttribute(Constants.SESSION_USER);
		String personId = userinfo.getPersonId();

		HalfProtocolForm myForm = (HalfProtocolForm) form;
		String halfprotocolInfo = myForm.gethalfprotocolInfo();
		// System.out.println(halfprotocolInfo);

		CallHelper caller = new CallHelper("SaveProtocolInuse");

		// caller.setParam("personId", personId);
		// caller.setParam("planYear", myForm.getPlanYear());
		// caller.setParam("planHalf", myForm.getPlanHalf());
		caller.setParam("halfprotocolInfo", halfprotocolInfo);
		caller.execute();

		String msgInfo = (String) caller.getOutput(0);
		request.setAttribute("message.information", msgInfo);

		return queryInuse(mapping, form, request, response);
	}

}
