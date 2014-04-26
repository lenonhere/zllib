package com.web.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zl.base.core.db.CallHelper;
import com.zl.core.HTDispatchAction;
import com.zl.util.OptionHold;

public class ProjectHelpAction extends HTDispatchAction {

	private static final Log log = LogFactory.getLog(ProjectHelpAction.class);

	private String getfilename() {
		String returnvalue = "";
		GregorianCalendar calendar = new GregorianCalendar();
		// 年
		String stryear = String.valueOf(calendar.get(Calendar.YEAR));
		// 月
		String strmonth = "00"
				+ String.valueOf(calendar.get(Calendar.MONTH) + 1);
		strmonth = strmonth.substring(strmonth.length() - 2, strmonth.length());
		// 日
		String strday = "00"
				+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		strday = strday.substring(strday.length() - 2, strday.length());
		// 时
		String strhour = "00"
				+ String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		strhour = strhour.substring(strhour.length() - 2, strhour.length());
		// 分
		String strminute = "00" + String.valueOf(calendar.get(Calendar.MINUTE));
		strminute = strminute.substring(strminute.length() - 2,
				strminute.length());
		// 秒
		String strsecond = "00" + String.valueOf(calendar.get(Calendar.SECOND));
		strsecond = strsecond.substring(strsecond.length() - 2,
				strsecond.length());
		// 毫秒
		String strmsecond = "000000"
				+ String.valueOf(calendar.get(Calendar.MILLISECOND));
		strmsecond = strmsecond.substring(strmsecond.length() - 6,
				strmsecond.length());
		String[] ychar = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
				"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
				"Y", "Z" };
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = ychar[random.nextInt(36)];
			sRand += rand;
		}
		returnvalue = stryear + strmonth + strday + strhour + strminute
				+ strsecond + strmsecond + sRand;
		return returnvalue;
	}

	public ActionForward ViewWorkFlow2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("typeList", getBMPWorkFlowType());
		return mapping.findForward("ViewWorkFlow2");
	}

	public static ArrayList<OptionHold> getBMPWorkFlowType() {
		ArrayList<OptionHold> list = new ArrayList<OptionHold>();
		list.add(new OptionHold("1", "申请修改通讯地址流程"));
		list.add(new OptionHold("2", "订单分解流程"));
		return list;
	}

	public ActionForward View(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// DynaActionForm dynaForm = (DynaActionForm) form;
		CallHelper call = this.initializeCallHelper("getBMPQueryList", form,
				request, false);
		call.setParam("userId", "0");
		call.execute();
		// request.setAttribute("captions.list", call.getResult(0));
		request.setAttribute("flowlist", call.getResult(1));
		return mapping.findForward("View");
	}

	public ActionForward ViewForDeal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// DynaActionForm dynaForm = (DynaActionForm) form;
		CallHelper call = this.initializeCallHelper("getBMPQueryList", form,
				request, false);
		call.setParam("userId", this.getPersonId(request));
		call.execute();
		// request.setAttribute("captions.list", call.getResult(0));
		request.setAttribute("flowlist", call.getResult(1));
		return mapping.findForward("View");
	}
}
