package com.web.action.infogather;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.common.SystemConfig;
import com.msg027.SMS;
import com.qmx.dateutils.DateUtils;
import com.web.CoreDispatchAction;
import com.web.form.infogather.InfoArticleForm;
import com.web.pojos.ChannelColumn;
import com.web.pojos.InfoArticle;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.db.Executer;
import com.zl.base.core.db.SqlReturn;
import com.zl.util.MethodFactory;
import com.zl.util.TradeList;

public class InfoArticleAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(InfoArticleAction.class);

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoArticleForm aForm = (InfoArticleForm) form;

		if (aForm.getQueryType() == null
				|| aForm.getQueryType().trim().length() == 0)
			aForm.setQueryType("1");

		CallHelper call = initializeCallHelper("getInfoChannelTree4Deal", form,
				request, false);

		call.setParam("userId", getPersonId(request));
		call.setParam("queryType", new Integer(aForm.getQueryType()));
		call.execute();
		request.setAttribute("result.list", call.getResult("results"));
		return mapping.findForward("init");
	}

	public ActionForward pubinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		InfoArticleForm aForm = (InfoArticleForm) form;

		if (aForm.getQueryType() == null
				|| aForm.getQueryType().trim().length() == 0)
			aForm.setQueryType("3");
		CallHelper call = initializeCallHelper("getInfoChannelTree4Deal", form,
				request, false);

		call.setParam("userId", getPersonId(request));
		call.setParam("queryType", new Integer(aForm.getQueryType()));
		call.execute();
		request.setAttribute("result.list", call.getResult("results"));
		return mapping.findForward("pubinit");
	}

	public ActionForward list4pub(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoArticleForm aForm = (InfoArticleForm) form;

		if (aForm.getChannelId() != null
				&& aForm.getChannelId().trim().length() > 0) {
			// if(aForm.getStatus()==null||aForm.getStatus().trim().length()==0)
			// aForm.setStatus("1");
			CallHelper call = initializeCallHelper("getInfoArticles", form,
					request, false);
			call.setParam("userId", getPersonId(request));
			// call.setParam("status",new Integer(aForm.getStatus()));
			call.execute();
			request.setAttribute("caption.list", call.getResult("captions"));
			request.setAttribute("result.list", call.getResult("results"));
		}

		return mapping.findForward("list4pub");
	}

	public ActionForward publish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoArticleForm aForm = (InfoArticleForm) form;

		if (aForm.getChannelId() != null
				&& aForm.getChannelId().trim().length() > 0) {
			CallHelper call = initializeCallHelper("publishInfoArticle", form,
					request, false);
			call.setParam("userId", getPersonId(request));
			call.setParam("status", new Integer(2));
			call.execute();
			String message = (String) call.getOutput("message");
			request.setAttribute("message", message);
		}
		if ("1".equals(aForm.getQueryType()))
			return list(mapping, form, request, response);
		return list4pub(mapping, form, request, response);
	}

	public ActionForward unpublish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoArticleForm aForm = (InfoArticleForm) form;

		if (aForm.getChannelId() != null
				&& aForm.getChannelId().trim().length() > 0) {
			CallHelper call = initializeCallHelper("publishInfoArticle", form,
					request, false);
			call.setParam("userId", getPersonId(request));
			call.setParam("status", new Integer(1));
			call.execute();
			String message = (String) call.getOutput("message");
			request.setAttribute("message", message);
		}
		if ("1".equals(aForm.getQueryType()))
			return list(mapping, form, request, response);
		return list4pub(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoArticleForm aForm = (InfoArticleForm) form;
		if (aForm.getArticleId() != null
				&& aForm.getArticleId().trim().length() > 0) {

			CallHelper call = initializeCallHelper("deleteInfoArticle", form,
					request, false);
			call.setParam("userId", getPersonId(request));
			call.execute();
			request.setAttribute("msg", call.getOutput("message"));
		}
		return list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoArticleForm aForm = (InfoArticleForm) form;

		if (aForm.getChannelId() != null
				&& aForm.getChannelId().trim().length() > 0) {
			CallHelper call = initializeCallHelper("getInfoArticles", form,
					request, false);
			call.setParam("userId", getPersonId(request));
			call.execute();
			request.setAttribute("caption.list", call.getResult("captions"));
			request.setAttribute("result.list", call.getResult("results"));
			request.setAttribute("message", call.getOutput("message"));
		}
		return mapping.findForward("list");

	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoArticleForm aForm = (InfoArticleForm) form;
		String channelId = aForm.getChannelId();
		if (channelId != null && channelId.trim().length() > 0) {
			CallHelper call = initializeCallHelper("getInfoChannelColumnList",
					form, request, false);
			call.execute();
			List results = call.getResult("results");
			List ret = new ArrayList();
			if (results != null && results.size() > 0) {
				for (int i = 0; i < results.size(); i++) {
					BasicDynaBean bean = (BasicDynaBean) results.get(i);
					ChannelColumn column = new ChannelColumn();
					column.setColumnId(MethodFactory.getThisString(bean
							.get("columnid")));
					column.setColumnName(MethodFactory.getThisString(bean
							.get("columnname")));
					column.setColumnType(MethodFactory.getThisString(bean
							.get("columntype2")));
					column.setSortOrder(MethodFactory.getThisString(bean
							.get("sortorder")));
					ret.add(column);
				}

			}
			request.setAttribute("ColumnList", ret);
		}
		return mapping.findForward("add");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoArticleForm aForm = (InfoArticleForm) form;
		try {
			String channelId = aForm.getChannelId();
			if (channelId != null && channelId.trim().length() > 0) {
				String title = aForm.getTitle();
				String brief = aForm.getBrief();
				String keyword = aForm.getKeyword();
				String areacode = aForm.getAreacode();
				CallHelper call2 = initializeCallHelper("saveInfoArticle",
						form, request, false);
				call2.setParam("userId", getPersonId(request));
				call2.setParam("title", title);
				// brief 需要删除 从http::开始到/hbsale中间的字符串，未完成
				call2.setParam("brief", brief);
				call2.setParam("keyword", keyword);
				// call2.setParam("areacode",areacode);
				call2.execute();
				Integer articleId = new Integer(
						(String) call2.getOutput("retarticleId"));
				if (articleId != null) {
					CallHelper call = initializeCallHelper(
							"getInfoChannelColumnList", form, request, false);
					call.execute();
					List results = call.getResult("results");
					if (results != null && results.size() > 0) {
						CallHelper call3 = initializeCallHelper(
								"saveInfoColumnContent", form, request, false);
						call3.setParam("userId", getPersonId(request));
						for (int i = 0; i < results.size(); i++) {
							BasicDynaBean bean = (BasicDynaBean) results.get(i);
							String columnid = MethodFactory.getThisString(bean
									.get("columnid"));
							String columnvalue = request
									.getParameter("columnvalue" + columnid);
							call3.setParam("articleId", articleId);
							call3.setParam("columnId", columnid);
							call3.setParam("content", columnvalue);
							call3.execute();
						}
					}
				}

			}
		} catch (NumberFormatException e) {

			log.error("processing request error:::" + e.toString());
		}
		return list(mapping, form, request, response);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoArticleForm aForm = (InfoArticleForm) form;
		String articleId = aForm.getArticleId();
		if (articleId != null) {
			CallHelper call = initializeCallHelper("getInfoArticleById", form,
					request, false);
			call.setParam("userId", getPersonId(request));
			call.execute();

			String personid = (String) call.getOutput("personid");
			String personname = (String) call.getOutput("personname");
			List ret = call.getResult("article");
			if (ret != null && ret.size() > 0) {
				BasicDynaBean bean = (BasicDynaBean) ret.get(0);
				aForm.setTitle(MethodFactory.getThisString(bean.get("title")));
				aForm.setBrief(MethodFactory.getThisString(bean.get("brief")));
				aForm.setKeyword(MethodFactory.getThisString(bean
						.get("keyword")));
				aForm.setAreacode(MethodFactory.getThisString(bean
						.get("areacode")));
				aForm.setAreaname(MethodFactory.getThisString(bean
						.get("areaname")));

				aForm.setReceivepersonid(personid);
				aForm.setReceiverName(personname);

				List ret2 = call.getResult("results");
				List retColumn = new ArrayList();
				// add 2007-12-26 增加附件
				ChannelColumn column = new ChannelColumn();
				column.setColumnId("1");
				column.setColumnName("附件内容");
				column.setColumnType("3");// add end

				if (ret2 != null && ret2.size() > 0) {
					for (int i = 0; i < ret2.size(); i++) {
						BasicDynaBean bean2 = (BasicDynaBean) ret2.get(i);
						// ChannelColumn column=new ChannelColumn();
						column.setColumnId(MethodFactory.getThisString(bean2
								.get("columnid")));
						column.setColumnName(MethodFactory.getThisString(bean2
								.get("columnname")));
						column.setColumnType(MethodFactory.getThisString(bean2
								.get("columntype")));
						// zlq 2006-10-07 在存储过程中把clob转为字符串
						Clob coluContent = (Clob) bean2.get("columncontent");
						String columncontent = this
								.getStringFromClob(coluContent);
						column.setColumnContent(columncontent);

						column.setColumncontentId(MethodFactory
								.getThisString(bean2.get("contentid")));
						// retColumn.add(column);
					}
				}
				retColumn.add(column);
				request.setAttribute("ColumnList", retColumn);
			}
			request.setAttribute("articleId", articleId);
		}
		return mapping.findForward("edit");

	}

	public ActionForward areatree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			CallHelper call = initializeCallHelper("getAreaTree", form,
					request, false);
			call.setParam("type", "2");
			call.setParam("userId", getPersonId(request));
			call.setParam("areaCode", "");
			call.execute();
			List ret = call.getResult(0);
			request.setAttribute("area.tree", ret);
		} catch (Exception e) {
			log.error("processing request error:::" + e.toString());
		}
		return mapping.findForward("areatree");
	}

	public ActionForward viewcontent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String contentId = request.getParameter("contentId");
		if (contentId != null) {
			List ret = TradeList.getColumnContentById(contentId);
			if (ret != null && ret.size() > 0) {
				BasicDynaBean bean = (BasicDynaBean) ret.get(0);
				String columnContent = (getStringFromClob((Clob) bean
						.get("columncontent")));
				request.setAttribute("ColumnContent", columnContent);
			}

		}
		return mapping.findForward("viewcontent");
	}

	public ActionForward viewarticle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String contentId = request.getParameter("contentId");
		if (contentId != null) {
			List ret = TradeList.getArticleBriefById(contentId);
			if (ret != null && ret.size() > 0) {
				BasicDynaBean bean = (BasicDynaBean) ret.get(0);
				String columnContent = MethodFactory.getThisString(bean
						.get("brief"));
				request.setAttribute("ColumnContent", columnContent);
			}

		}
		return mapping.findForward("viewcontent");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoArticleForm aForm = (InfoArticleForm) form;
		String articleId = aForm.getArticleId();
		if (articleId != null) {
			CallHelper call = initializeCallHelper("getInfoArticleById", form,
					request, false);
			call.setParam("userId", getPersonId(request));
			call.execute();

			List ret = call.getResult("article");
			if (ret != null && ret.size() > 0) {
				BasicDynaBean bean = (BasicDynaBean) ret.get(0);
				InfoArticle article = new InfoArticle();
				article.setTitle(MethodFactory.getThisString(bean.get("title")));
				// article.setBrief(MethodFactory.replace(MethodFactory.getThisString(bean.get("brief")),"\n","<br>"));
				request.setAttribute("brief", MethodFactory.replace(
						MethodFactory.getThisString(bean.get("brief")), "\n",
						"<br>"));
				article.setKeyword(MethodFactory.getThisString(bean
						.get("keyword")));
				List ret2 = call.getResult("results");
				List retColumn = new ArrayList();
				if (ret2 != null && ret2.size() > 0) {
					for (int i = 0; i < ret2.size(); i++) {
						BasicDynaBean bean2 = (BasicDynaBean) ret2.get(i);
						ChannelColumn column = new ChannelColumn();
						column.setColumnId(MethodFactory.getThisString(bean2
								.get("columnid")));
						column.setColumnName(MethodFactory.getThisString(bean2
								.get("columnname")));
						column.setColumnType(MethodFactory.getThisString(bean2
								.get("columntype")));
						// zlq 2006-10-07 在存储过程中把clob转为字符串
						Clob coluContent = (Clob) bean2.get("columncontent");
						String columncontent = this
								.getStringFromClob(coluContent);
						// String columncontent =
						// MethodFactory.getThisString(bean2.get("columncontent"));
						// columncontent = columncontent ==
						// null?"":columncontent.substring(columncontent.lastIndexOf("/")+1);

						column.setColumnContent(columncontent);
						column.setColumncontentId(MethodFactory
								.getThisString(bean2.get("contentid")));
						retColumn.add(column);
					}
					article.setColumnContents(retColumn);
				}
				request.setAttribute("ArticleInfo", article);

			}

			// 检查是否已经标记为已读
			Integer readed = new Integer(0);
			String sql = "select readed from info_view_history where personid="
					+ getPersonId(request) + " and articleid = "
					+ aForm.getArticleId();
			SqlReturn sr = Executer.getInstance().ExecSeletSQL(sql);
			if (!sr.getResultSet().isEmpty()) {
				readed = (Integer) ((BasicDynaBean) sr.getResultSet().get(0))
						.get("readed");
			}
			request.setAttribute("readed", readed.intValue() + "");// 已读标记
			/** 更新浏览记录 **/
			CallHelper call2 = initializeCallHelper("updateArticleViewHistory",
					form, request, false);
			call2.execute();
		}
		return mapping.findForward("view");
	}

	public ActionForward sendsmsinit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CallHelper helper = new CallHelper("getDepareAreaPersonTreeByArticle");
		helper.setParam("type", "1");
		helper.setParam("articleId", request.getParameter("articleId"));
		helper.execute();

		request.setAttribute("person.tree", helper.getResult("results"));
		String contentId = request.getParameter("articleId");
		if (contentId != null) {
			List ret = TradeList.getArticleBriefById(contentId);
			if (ret != null && ret.size() > 0) {
				BasicDynaBean bean = (BasicDynaBean) ret.get(0);
				String columnContent = MethodFactory.getThisString(bean
						.get("brief"));
				columnContent = StripHT(columnContent);
				request.setAttribute("ColumnContent", columnContent);
			}

		}
		return mapping.findForward("sendsmsinit");
	}

	/* wangp editor 08.12.25 */
	public ActionForward sendMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		InfoArticleForm aForm = (InfoArticleForm) form;
		// 发送短信前先判断余额是否足够
		byte state;
		// 错误短信个数
		int errnum = 0;
		// 余额
		int balance;
		// 更新短信状态
		int updatestate = 0;

		// 登陆短信平台账号
		String userid = SystemConfig.getString("MSG_USER");
		String password = SystemConfig.getString("MSG_PWD");
		SMS sms = null;
		try {
			sms = new SMS();
			balance = sms.login(userid, password);
			if (balance < 0) {
				request.setAttribute("message.information", "短信平台目前无法登陆！");
			}
			;

			CallHelper helper = initializeCallHelper("getSendInfo", aForm,
					request, false);
			helper.setParam("userId", getPersonId(request));
			helper.setParam("userPwd", getPersonPwd(request));
			helper.execute();
			int rtState = helper.getState();
			switch (rtState) {
			case 2: // 密码不对
				request.setAttribute("message.code", String.valueOf(rtState));
				request.setAttribute("message.information",
						helper.getOutput("message"));
				break;
			case 5:
				request.setAttribute("message.information",
						helper.getOutput("message"));
			case 0:
				List mobileList = helper.getResult(0);
				if (mobileList.size() == 0) {
					request.setAttribute("message.information", "列表中无手机号码可发送！");
				} else {
					if (balance < mobileList.size() + 10) {
						request.setAttribute("message.information",
								"短信平台目前余额不足，请续费！");
					}
					;
					for (int i = 0; i < mobileList.size(); i++) {
						BasicDynaBean bean = (BasicDynaBean) mobileList.get(i);
						String msgid = String.valueOf(bean.get("msgid"));
						String rMobile = String.valueOf(bean.get("rmobile"));
						String msgContent = String.valueOf(bean
								.get("msgcontent"))
								+ "   --"
								+ String.valueOf(bean.get("spersonname"));
						try {
							// 每次发送前查询余额
							// if (sms.login(userid, password)==0){
							// request.setAttribute("message.information","短信平台目前余额为0！");
							// };
							// 增加发送号码
							sms.addPhone(rMobile);
							// 增加发送内容
							sms.addMsg(msgContent, null);
							// 开始发送
							state = sms.send();
							// 休眠100毫秒
							Thread.sleep(100);
							// 如果短信发送失败
							if (state < 0) {
								errnum = errnum + 1;
							} else {
								// 更新已经成功发送的短信状态到数据库
								CallHelper updatehelper = initializeCallHelper(
										"updatesmsrecord", aForm, request,
										false);
								updatehelper.setParam("msgid", msgid);
								updatehelper.execute();
							}
						} catch (Exception ex) {
							updatestate = -1;
						}
					}
					if (errnum > 0)
						request.setAttribute("message.information",
								"有部分短信发送失败，信息已保存，请查询后重新发送！");
					else if (updatestate < 0)
						request.setAttribute("message.information",
								"发送失败！信息已保存，请查询后重新发送");
					else
						request.setAttribute("message.information", "发送成功！");
					//

				}
				break;
			}
			return sendsmsinit(mapping, form, request, response);

		} catch (Exception e) {
			log.error(e);
			request.setAttribute("message.information", "发送失败！");
			return null;
		} finally {
			if (sms != null)
				sms.logout();
		}

	}

	public ActionForward sendMsg11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		InfoArticleForm aform = (InfoArticleForm) form;
		CallHelper helper = new CallHelper("getSendInfo");
		helper.setParam("userId", getPersonId(request));
		helper.setParam("userPwd", getPersonPwd(request));
		helper.setParam("personIdSet", aform.getPersonIdSet());
		helper.setParam("msgContent", aform.getMsgContent());
		helper.execute();
		int rtState = helper.getState();
		switch (rtState) {
		case 2: // 密码不对
			request.setAttribute("message.code", String.valueOf(rtState));
			request.setAttribute("message.information",
					helper.getOutput("message"));
			break;
		case 5:
			request.setAttribute("message.information",
					helper.getOutput("message"));
		case 0:
			List mobileList = helper.getResult(0);
			if (mobileList.size() == 0) {
				request.setAttribute("message.information", "列表中无手机号码可发送！");
			} else {
				request.setAttribute("message.information", "发送成功！");
			}
			break;
		}

		try {
			return sendsmsinit(mapping, form, request, response);
		} catch (Exception e) {
			return null;
		}
	}

	public String getStringFromClob(Clob clobV) {
		Clob clob = clobV;
		String returnValue = "";
		try {
			if (clob != null) {
				Reader is = clob.getCharacterStream();
				BufferedReader br = new BufferedReader(is);
				StringBuffer sb = new StringBuffer();
				String line = br.readLine();
				while (line != null) {
					sb.append(line);
					line = br.readLine();
				}

				returnValue = sb.toString();
			}
		} catch (Exception ex) {
			log.error(ex);
		}
		return returnValue;
	}

	private String StripHT(String element) {
		try {
			// String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
			// Pattern p = Pattern.compile("<.+?>",Pattern.CASE_INSENSITIVE);
			Pattern p = Pattern.compile("<.+?>|[&nbsp;]",
					Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(element);
			String result = m.replaceAll(""); // 过滤html标签
			return result; // .replaceAll("?","");
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ActionForward querylog_init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		InfoArticleForm aForm = (InfoArticleForm) form;
		aForm.setBegindate(DateUtils.getDate().replace("/", "-"));
		aForm.setEnddate(DateUtils.getDate().replace("/", "-"));
		return mapping.findForward("querylog_init");
	}

	public ActionForward querylog_show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("infoGetArticleLog", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("results.list", helper.getResult(0));
		return mapping.findForward("querylog_show");
	}

	public ActionForward querylog_detail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("infoGetArticleLogDetail",
				form, request, false);
		helper.setParam("article_id", request.getParameter("articleid"));
		helper.execute();
		request.setAttribute("caption.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));
		return mapping.findForward("querylog_detail");
	}

	public ActionForward setRead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InfoArticleForm aForm = (InfoArticleForm) form;
		String readed = request.getParameter("readed");
		String sql = "update info_view_history set readed = " + readed
				+ " where personid=" + getPersonId(request)
				+ " and articleid = " + aForm.getArticleId();
		SqlReturn sr = Executer.getInstance().ExecUpdateSQL(sql);
		return null;
	}
}
