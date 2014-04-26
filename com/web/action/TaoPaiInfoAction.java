package com.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.form.TaoPaiInfoForm;
import com.zl.base.core.db.CallHelper;
import com.zl.util.OptionUtils;
import com.zl.util.TradeList;

public class TaoPaiInfoAction extends CriterionAction {

	private static final Log log = LogFactory.getLog(TaoPaiInfoAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {
			log.error(e);
			return mapping.findForward("error");
		}
	}

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = new CallHelper("gettaopaiinfo");
		helper.execute();
		request.setAttribute("caption.list", helper.getResult("caption"));
		request.setAttribute("results.list", helper.getResult("results"));
		return mapping.findForward("init");
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TaoPaiInfoForm tpForm = (TaoPaiInfoForm) form;
		CallHelper helper = new CallHelper("getjginfo");
		if (tpForm.getTobaccoId() == null || tpForm.getTobaccoId().equals(""))
			tpForm.setTobaccoId("0");
		helper.setParam("level2", tpForm.getTobaccoId());
		helper.execute();
		request.setAttribute("caption.list", helper.getResult("caption"));
		request.setAttribute("results.list", helper.getResult("results"));
		return mapping.findForward("jgShow");
	}

	public ActionForward allshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("allShow");
	}

	public ActionForward saveJgInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TaoPaiInfoForm tpForm = (TaoPaiInfoForm) form;
		CallHelper helper = new CallHelper("savejginfo");
		if (tpForm.getTobaccoId() == null || tpForm.getTobaccoId().equals(""))
			tpForm.setTobaccoId("0");
		helper.setParam("level2", tpForm.getTobaccoId());
		helper.setParam("jgTobaIdSet", tpForm.getTobaIdSet());
		helper.execute();
		request.setAttribute("message.information", helper.getOutput("message"));

		// 维护套牌加工烟关系维护操作日志
		String personId = getPersonId(request);
		String ip = request.getRemoteHost();
		String tobasystid = tpForm.getTobaccoId();
		TradeList.operLog(personId, 99050503, tobasystid, ip);

		return show(mapping, form, request, response);
	}

	public ActionForward delTpToba(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TaoPaiInfoForm tpForm = (TaoPaiInfoForm) form;
		CallHelper helper = new CallHelper("delTpToba");
		if (tpForm.getTobaccoId() == null || tpForm.getTobaccoId().equals(""))
			tpForm.setTobaccoId("0");
		helper.setParam("level2", tpForm.getTobaccoId());
		helper.execute();
		request.setAttribute("message.information", helper.getOutput("message"));
		return init(mapping, form, request, response);
	}

	public ActionForward addTpTobaInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			List list = TradeList.Getpriceid();
			request.setAttribute("level.list", list);
		} catch (Exception e) {
		}
		return mapping.findForward("addTaoPai");
	}

	public ActionForward addTpToba(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TaoPaiInfoForm tpForm = (TaoPaiInfoForm) form;
		CallHelper helper = new CallHelper("addTpToba");
		if (tpForm.getTobaccoId() == null || tpForm.getTobaccoId().equals(""))
			tpForm.setTobaccoId("0");
		helper.setParam("level1", tpForm.getTobaccoId());
		helper.setParam("tobaName", tpForm.getTobaName());
		helper.setParam("priceLevelId", tpForm.getPriceLevelId());
		helper.setParam("tradePrice", tpForm.getTradePrice());
		helper.setParam("retailPrice", tpForm.getRetailPrice());
		helper.setParam("includePrice", tpForm.getIncludePrice());
		helper.setParam("contractPrice", tpForm.getContractPrice());
		helper.execute();
		try {
			List list = TradeList.Getpriceid();
			request.setAttribute("level.list", list);
		} catch (Exception e) {
		}
		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		// 添加系统套牌新增操作日志
		String personId = getPersonId(request);
		String ip = request.getRemoteHost();
		String tobasystid = (String) helper.getOutput("level2");
		TradeList.operLog(personId, 99050501, tobasystid, ip);

		return mapping.findForward("addTaoPai");
	}

	public ActionForward modifyTpTobaInit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List list = TradeList.Getpriceid();
			request.setAttribute("level.list", list);
			request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		} catch (Exception e) {
		}
		return mapping.findForward("modifyTaoPai");
	}

	public ActionForward modifyTpToba(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TaoPaiInfoForm tpForm = (TaoPaiInfoForm) form;
		CallHelper helper = new CallHelper("modifyTpToba");
		if (tpForm.getTobaccoId() == null || tpForm.getTobaccoId().equals(""))
			tpForm.setTobaccoId("0");
		helper.setParam("level2", tpForm.getTobaccoId());
		helper.setParam("tobaName", tpForm.getTobaName());
		helper.setParam("priceLevelId", tpForm.getPriceLevelId());
		helper.setParam("tradePrice", tpForm.getTradePrice());
		helper.setParam("retailPrice", tpForm.getRetailPrice());
		helper.setParam("includePrice", tpForm.getIncludePrice());
		helper.setParam("contractPrice", tpForm.getContractPrice());
		helper.setParam("tobaCode", tpForm.getTobaCode());
		helper.setParam("isActive", tpForm.getIsActive());
		helper.setParam("tobaccoAlias", tpForm.getTobaccoAlias());
		helper.execute();
		try {
			List list = TradeList.Getpriceid();
			request.setAttribute("level.list", list);
			request.setAttribute("isactive.list", OptionUtils.getIsActiveList());
		} catch (Exception e) {
		}
		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		// 添加系统套牌修改操作日志
		String personId = getPersonId(request);
		String ip = request.getRemoteHost();
		String tobasystid = tpForm.getTobaccoId();
		TradeList.operLog(personId, 99050502, tobasystid, ip);

		return mapping.findForward("modifyTaoPai");

	}

	// 卷烟名称切换显示
	public ActionForward switchnameinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("switchTobaname", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		String status = (String) helper.getOutput("status");

		request.setAttribute("status", status);

		CallHelper caller = initializeCallHelper("getTobaname", form, request,
				false);
		caller.execute();
		request.setAttribute("caption.list", caller.getResult(0));
		request.setAttribute("result.list", caller.getResult(1));

		return mapping.findForward("switchnameinit");
	}

	public ActionForward switchname(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("switchTobaname", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.setParam("flag", "1");
		helper.execute();
		String status = (String) helper.getOutput("status");
		String mess = (String) helper.getOutput("message");
		request.setAttribute("status", status);
		request.setAttribute("message", mess);

		return switchnameinit(mapping, form, request, response);
	}

	public ActionForward namesave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("saveTobaname", form, request,
				false);
		helper.setParam("userId", getPersonId(request));
		helper.setParam("flag", "1");
		helper.execute();
		String mess = (String) helper.getOutput("message");
		request.setAttribute("message", mess);

		return switchnameinit(mapping, form, request, response);
	}

}
