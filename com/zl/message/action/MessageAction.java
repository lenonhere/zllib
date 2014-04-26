package com.zl.message.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.common.SystemConfig;
import com.web.action.CriterionAction;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.file.directory;
import com.zl.util.MethodFactory;

public class MessageAction extends CriterionAction {

	private static Log log = LogFactory.getLog(MessageAction.class);

	public class fileinfo {
		public String filename;
		public String savefilename;
		public String filetype;
	}

	public String getfilename() {
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

	/* 信息发送主页面 */
	public ActionForward sendinit(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("repositorypath",
				SystemConfig.getFilesUploadPath());

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
			return actionMapping.findForward("sendsyinit");
		else

			return actionMapping.findForward("sendinit");
	}

	/**
	 * 发送邮件
	 *
	 * @param fileItems
	 * @param request
	 * @return
	 */
	public String sendmessage(List fileItems, HttpServletRequest request) {
		String title = "";
		String receivepersonid = "";
		String content = "";
		String fileid = "";
		String returnvalue = "";
		String uploadPath = SystemConfig.getMailAnnexUploadPath();
		String showreceiver = "";
		boolean blnsave = true;
		ArrayList file = new ArrayList();
		try {
			uploadPath = MethodFactory.replace(uploadPath, "\\", "/");
			uploadPath = MethodFactory.replace(uploadPath, "//", "/");
			directory.createdir(uploadPath + "file" + getPersonId(request)
					+ "/");
			Iterator iterator = fileItems.iterator();
			long totalSize = 0;
			while (iterator.hasNext()) {
				FileItem fileItem = (FileItem) iterator.next();
				// 文件域的表单信息
				if (!fileItem.isFormField()) {
					long size = fileItem.getSize();
					totalSize += size;
					if (totalSize > 20 * 1024 * 1024) {
						returnvalue = "附件超出10M大小限制";
						return returnvalue;
					}
					String strName = fileItem.getName();
					strName = MethodFactory.replace(strName, "\\", "/");
					String[] path = MethodFactory.split(strName, "/");
					strName = path[path.length - 1];
					int loc = strName.lastIndexOf(".");
					String type = "";
					if (loc > 0) {
						type = strName.substring(loc, strName.length());
					}

					if ((strName == null || strName.equals("")) && size == 0)
						continue;
					fileinfo fileinfotemp = new fileinfo();
					String filename = "file" + getPersonId(request) + "/"
							+ getfilename() + type;
					File savedFile = new File(uploadPath + filename);
					fileItem.write(savedFile);
					fileinfotemp.filename = filename;
					fileinfotemp.filetype = fileItem.getContentType();
					fileinfotemp.savefilename = strName;
					file.add(fileinfotemp);
				} else {
					String name = fileItem.getFieldName().toLowerCase();
					if (name.equals("title")) {
						title = fileItem.getString("GBK");
					} else if (name.equals("receivepersonid")) {
						receivepersonid = fileItem.getString("GBK");
					} else if (name.equals("content")) {
						content = fileItem.getString("GBK");
					} else if (name.equals("fileid")) {
						fileid = fileItem.getString("GBK");
					} else if (name.equals("showreceiver")) {
						showreceiver = fileItem.getString("GBK");// 是否密送 0：密送
																	// 1：不是密送
					}
				}
			}
			blnsave = true;
		} catch (Exception ex) {
			returnvalue = "上传文件失败，发送邮件失败！";
			log.error(ex);
			blnsave = false;
			return returnvalue;
		}
		/* 如果保存成功后，清除form中的数据 */
		if (blnsave == true) {
			String savefilename = "";
			for (int i = 0; i < file.size(); i++) {
				fileinfo fileinfotemp = (fileinfo) file.get(i);
				if (i != 0) {
					savefilename = savefilename + "*";
				}
				savefilename = savefilename + fileinfotemp.filename + "|";
				savefilename = savefilename + fileinfotemp.savefilename + "|";
				savefilename = savefilename + fileinfotemp.filetype;
			}
			CallHelper caller = new CallHelper("sendMessage");
			caller.setParam("personId", getPersonId(request));
			caller.setParam("title", title);
			caller.setParam("content", content);
			caller.setParam("fileid", fileid);
			caller.setParam("type", "0");
			caller.setParam("receivePersonid", receivepersonid);
			caller.setParam("savefilename", savefilename);
			caller.setParam("showreceiver", showreceiver);
			caller.execute();
			returnvalue = (String) caller.getOutput(0);
			// returnvalue="信息发送成功！";
		}
		return returnvalue;
	}

	public ActionForward downfile(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String fileid = request.getParameter("fileid");

		return actionMapping.findForward("sendinit");
	}

	public ActionForward personshow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper call = initializeCallHelper("getsendpersonlistforsy", form,
				request, false);
		call.setParam("personid", getPersonId(request));
		call.execute();
		request.setAttribute("results.list", call.getResult(0));

		return mapping.findForward("personshow");
	}

}
