package com.zl.manage;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.web.CoreDispatchAction;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.DBConnectionManager;
import com.zl.common.PFValue;
import com.zl.util.MethodFactory;
import com.zl.util.TradeList;

public class ContentAction extends CoreDispatchAction {
	private static Log log = LogFactory.getLog(ContentAction.class);

	public ActionForward contentlist(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			ArrayList list = TradeList.getarticle();
			request.setAttribute("list.atticel", list);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return actionMapping.findForward("contentlist");

	}

	public ActionForward init(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return actionMapping.findForward("init");

	}

	public ActionForward filelist(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("systemid");
		String personid = getPersonId(request);

		try {

			if (id != null) {
				ArrayList list = TradeList.getarticlebychannel(id, personid);
				request.setAttribute("list.atticel", list);
			} else {
				ArrayList list = TradeList.getarticle();
				request.setAttribute("list.atticel", list);
			}
		} catch (Exception ex) {
			log.error(ex);
		}
		return actionMapping.findForward("filelist");
	}

	public ActionForward channellist(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (request.getParameter("systemid") != null) {
				ArrayList list = TradeList.getarticlechannel2(request
						.getParameter("systemid"));
				ContentForm aForm = (ContentForm) form;
				aForm.setSystemid(Integer.valueOf(
						request.getParameter("systemid")).intValue());
				request.setAttribute("list.channel", list);
			}
		} catch (Exception ex) {
			log.error(ex);
		}
		return actionMapping.findForward("channellist");
	}

	public ActionForward save(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ContentForm aForm = (ContentForm) form;

			String filecontent = aForm.getContentstring();
			if (aForm.getSystemid() == 0) {
				TradeList.addAtical(aForm.getTitle(), aForm.getRemark(),
						String.valueOf(aForm.getU_creater()),
						aForm.getR_media(), filecontent,
						String.valueOf(aForm.getTemplateid()),
						String.valueOf(PFValue.getSQLDate()), "Y",
						String.valueOf(aForm.getChannelid()));
			} else {
				TradeList.saveAtical(String.valueOf(aForm.getSystemid()),
						aForm.getTitle(), aForm.getRemark(),
						String.valueOf(aForm.getU_creater()),
						aForm.getR_media(), filecontent,
						String.valueOf(aForm.getTemplateid()),
						String.valueOf(PFValue.getSQLDate()), "Y",
						String.valueOf(aForm.getChannelid()));
			}

			request.setAttribute("list.template",
					TradeList.getTemplateBytype("0"));
			request.setAttribute("list.channel",
					TradeList.getChannelbytype("0", ""));
			request.setAttribute("info", "保存成功！");
			request.setAttribute("return", "Y");
		} catch (Exception ex) {
			log.error(ex);
		}
		return actionMapping.findForward("modify");
	}

	public ActionForward modify(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ContentForm aForm = (ContentForm) form;
		try {
			request.setAttribute("list.template",
					TradeList.getTemplateBytype("0"));
			request.setAttribute("list.channel",
					TradeList.getChannelbytype("0", ""));
			if (request.getParameter("systemid") != null) {
				ArrayList list = TradeList.getarticle(request
						.getParameter("systemid"));
				aForm.setTitle(TradeList.getvalue(list.get(0), "title"));
				aForm.setRemark(TradeList.getvalue(list.get(0), "remark"));
				aForm.setR_media(TradeList.getvalue(list.get(0), "r_media"));
				request.setAttribute("contentfilename",
						TradeList.getvalue(list.get(0), "content"));
				request.setAttribute("fileid", request.getParameter("systemid"));
				aForm.setContentstring(TradeList.getvalue(list.get(0),
						"content"));
				request.setAttribute("contentstring",
						TradeList.getvalue(list.get(0), "content"));
				aForm.setSystemid((Integer.valueOf(TradeList.getvalue(
						list.get(0), "systemid"))).intValue());
				aForm.setTemplateid((Integer.valueOf(TradeList.getvalue(
						list.get(0), "templateid"))).intValue());
				aForm.setChannelid((Integer.valueOf(TradeList.getvalue(
						list.get(0), "channelid"))).intValue());

			}
		} catch (Exception ex) {
			log.error(ex);
		}

		return actionMapping.findForward("modify");
	}

	public ActionForward delete(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String message = "";
		try {

			if (request.getParameter("systemid") != null) {
				TradeList.deletearticle(request.getParameter("systemid"));
			}
			message = "删除成功！";
		} catch (Exception ex) {
			log.error(ex);
			message = "删除失败！";
		}
		request.setAttribute("message", message);
		return actionMapping.findForward("delete");
	}

	public ActionForward read(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ContentForm aForm = (ContentForm) form;
		try {
			request.setAttribute("list.template",
					TradeList.getTemplateBytype("0"));
			request.setAttribute("list.channel",
					TradeList.getChannelbytype("0", ""));
			if (request.getParameter("systemid") != null) {
				ArrayList list = TradeList.getarticle(request
						.getParameter("systemid"));
				aForm.setTitle(TradeList.getvalue(list.get(0), "title"));
				aForm.setRemark(TradeList.getvalue(list.get(0), "remark"));
				aForm.setR_media(TradeList.getvalue(list.get(0), "r_media"));
				request.setAttribute("contentfilename",
						TradeList.getvalue(list.get(0), "content"));
				request.setAttribute("fileid", request.getParameter("systemid"));
				aForm.setContent(TradeList.getvalue(list.get(0), "content"));
				aForm.setSystemid((Integer.valueOf(TradeList.getvalue(
						list.get(0), "systemid"))).intValue());
				aForm.setTemplateid((Integer.valueOf(TradeList.getvalue(
						list.get(0), "templateid"))).intValue());
				aForm.setChannelid((Integer.valueOf(TradeList.getvalue(
						list.get(0), "channelid"))).intValue());
			}
		} catch (Exception ex) {
			log.error(ex);
		}

		return actionMapping.findForward("read");
	}

	public ActionForward issue(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ContentForm aForm = (ContentForm) form;
			if (!"".equals(aForm.getContentstring())) {
				String col[] = MethodFactory.split(aForm.getContentstring(),
						";");
				String row1[] = MethodFactory.split(col[0], ","); // channelid
				String row2[] = MethodFactory.split(col[1], ","); // 是否发布
				String row3[] = MethodFactory.split(col[2], ","); // 顺序
				String row4[] = MethodFactory.split(col[3], ","); // 是否置顶
				Connection con = DBConnectionManager.getInstance()
						.getConnection("idb");
				try {
					con.setAutoCommit(false);
					TradeList.delcontentissue(con,
							String.valueOf(aForm.getSystemid()));
					for (int i = 0; i < row1.length; i++) {
						int SORTORDER = 0;
						try {
							SORTORDER = Integer.valueOf(row3[i]).intValue();
						} catch (Exception ex1) {
						}
						if (row2[i].equals("1")) {
							TradeList
									.saveissue(
											con,
											String.valueOf(aForm.getSystemid()),
											row1[i], String.valueOf(SORTORDER),
											row4[i]);
						}
					}
					con.commit();
				} catch (Exception ex) {
					con.rollback();
				} finally {
					try {
						DBConnectionManager.getInstance().freeConnection("idb",
								con);
					} catch (Exception e) {
					}
					;
				}
			}
			ArrayList list = TradeList.getarticlechannel2(String.valueOf(aForm
					.getSystemid()));
			request.setAttribute("list.channel", list);
		} catch (Exception ex) {
			log.error(ex);
		}
		return actionMapping.findForward("channellist");
	}

	public ActionForward rightset_init(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ContentForm conForm = (ContentForm) form;

		CallHelper helper = new CallHelper("articleQuery");
		helper.execute();

		List results = helper.getResult("results");
		request.setAttribute("article.list", results);

		if (results != null && results.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) results.get(0);
			conForm.setArticleId(bean.get("articleid").toString());
		} else {
			conForm.setArticleId("-1");
		}

		return actionMapping.findForward("rightset_init");
	}

	public ActionForward rightset_show(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("articleQuery", form, request,
				false);
		helper.execute();

		setPersonTree(form, request);

		return actionMapping.findForward("rightset_show");
	}

	public ActionForward rightset_save(ActionMapping actionMapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("saveArticlePerson", form,
				request, false);
		helper.execute();

		request.setAttribute("message.code", String.valueOf(helper.getState()));
		request.setAttribute("message.information", helper.getOutput("message"));

		return rightset_show(actionMapping, form, request, response);
	}

	protected void setPersonTree(ActionForm form, HttpServletRequest request) {
		ContentForm conForm = (ContentForm) form;

		String articleId = conForm.getArticleId();

		CallHelper helper = new CallHelper("getArticlePerson");
		helper.setParam("articleId", articleId);
		helper.execute();

		request.setAttribute("articleperson.tree", helper.getResult("results"));
	}
}
