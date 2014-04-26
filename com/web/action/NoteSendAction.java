package com.web.action;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.web.form.NoteSendForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.MethodFactory;
import com.zl.util.TradeList;

public class NoteSendAction extends CoreDispatchAction {
	static Logger logger = Logger.getLogger(NoteSendAction.class.getName());

	public ActionForward cxdcinit(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			NoteSendForm aForm = (NoteSendForm) form;
			aForm.setPhonecode("13905715522;13906513688;13906681268;13905813498;13906518668;13906681128;13905813318;13905818485;13605716048;13905812910;13958101512;13805871677;13605747902;13588177568;13958066680;13858090811;13758105718;13515812658;13906539335;13906539337");
		} catch (Exception e) {
			logger.error(e);
		}
		return actionMapping.findForward("send");
	}

	public ActionForward getcontent(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			NoteSendForm aForm = (NoteSendForm) form;
			aForm.setUserId(getPersonId(request));
			aForm.setContent("");
			request.setAttribute("contenttype", aForm.getContenttype());
			GregorianCalendar calendar = new GregorianCalendar();
			java.sql.Date newdate = new java.sql.Date(calendar.getTime()
					.getTime());
			newdate.setDate(newdate.getDate() - 1);
			calendar.setTimeInMillis(newdate.getTime());
			aForm.setEndDate(calendar.get(Calendar.YEAR) + "-"
					+ (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH));
			aForm.setBeginDate(calendar.get(Calendar.YEAR) + "-"
					+ (calendar.get(Calendar.MONTH) + 1) + "-1");
			aForm.setUnitId("1");
			CallHelper helper = null;
			if (aForm.getContenttype().equals("1")
					|| aForm.getContenttype().equals("2")) {
				helper = initializeCallHelper("totalDailyReport", form,
						request, false);
				helper.execute();
			} else {
				helper = initializeCallHelper(
						"getCountryWideWholesailingDailyReport", aForm,
						request, false);
				helper.execute();
			}
			List list = helper.getResult("results");
			if (list.size() > 0) {
				BasicDynaBean obj = (BasicDynaBean) (list.get(0));
				String content = "";
				if (aForm.getContenttype().equals("1")) {
					content = "浙产烟今日库存"
							+ getFormatValue("0.##", PropertyUtils.getProperty(
									obj, "stock_current"))
							+ ",工业存销比"
							+ getFormatValue("0.#", PropertyUtils.getProperty(
									obj, "industry_stocksalerate"))
							+ ",本月生产"
							+ getFormatValue("0.##", PropertyUtils.getProperty(
									obj, "producequantity_month"))
							+ ",本年"
							+ getFormatValue("0.##", PropertyUtils.getProperty(
									obj, "producequantity_year"))
							+ ",本月加工烟"
							+ getFormatValue("0.##", PropertyUtils.getProperty(
									obj, "processquantity_month"))
							+ ",本年"
							+ getFormatValue("0.##", PropertyUtils.getProperty(
									obj, "processquantity_year"));
				} else if (aForm.getContenttype().equals("2")) {
					content = "浙产烟本月调出"
							+ getFormatValue("0.#", PropertyUtils.getProperty(
									obj, "sjdc_month"))
							+ "(省内"
							+ getFormatValue("0.#", PropertyUtils.getProperty(
									obj, "sjdc_inprov_month"))
							+ "省外"
							+ getFormatValue("0.#", PropertyUtils.getProperty(
									obj, "sjdc_outprov_month"))
							+ ");本年"
							+ getFormatValue("0.#",
									PropertyUtils.getProperty(obj, "sjdc_year"))
							+ "(省内"
							+ getFormatValue("0.#", PropertyUtils.getProperty(
									obj, "sjdc_inprov_year"))
							+ ",省外"
							+ getFormatValue("0.#", PropertyUtils.getProperty(
									obj, "sjdc_outprov_year")) + ")";
				}
				/*
				 * else{ content="浙产烟省内本月商业销售"
				 * +getFormatValue("0.##",PropertyUtils.getProperty(obj,
				 * "fmonthnumb")) +",本年"
				 * +getFormatValue("0.##",PropertyUtils.getProperty(obj,
				 * "fyearnumb")) +",上周日均"
				 * +getFormatValue("0.##",PropertyUtils.getProperty(obj,
				 * "fweeknumb")) +",移动7天"
				 * +getFormatValue("0.##",PropertyUtils.getProperty(obj,
				 * "flastcyclenumb")) +",商业库存"
				 * +getFormatValue("0.##",PropertyUtils.getProperty(obj,
				 * "fdaykc")) +",存销比"
				 * +getFormatValue("0.#",PropertyUtils.getProperty(obj,
				 * "fxs_kcrate")); }
				 */
				aForm.setContent(content);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return actionMapping.findForward("send");
	}

	private static String getFormatValue(String formate, Object value) {
		String returnvalue = "";
		try {// 如果非日期时
			returnvalue = String.valueOf(value);
			DecimalFormat df1 = new DecimalFormat(formate);
			Double valuetemp = Double.valueOf(returnvalue);
			if (valuetemp.isNaN() == false)
				returnvalue = df1.format(valuetemp.doubleValue());
		} catch (Exception ex) {
			returnvalue = "0";
		}
		return returnvalue;
	}

	public ActionForward sendnote(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			NoteSendForm aForm = (NoteSendForm) form;
			if (aForm.getContent() != null
					&& !aForm.getContent().trim().equals("")) {
				String[] phonecode = MethodFactory.split(aForm.getPhonecode(),
						";");
				for (int i = 0; i < phonecode.length; i++) {
					TradeList
							.send((String) request.getSession().getAttribute(
									"login"), phonecode[i], aForm.getContent());
				}
				aForm.setContent("");
				request.setAttribute("info", "发送成功!");
			} else {
				request.setAttribute("info", "短信内容不能为空!");
			}

		} catch (Exception e) {
			logger.error(e);
		}
		return actionMapping.findForward("send");
	}
}
