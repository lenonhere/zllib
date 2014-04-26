package com.web.action.business.daily;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.common.SystemConfig;
import com.qmx.dateutils.DateUtils;
import com.web.action.CriterionAction;
import com.web.form.WorkLogForm;
import com.zl.base.core.db.CallHelper;
import com.zl.base.core.file.directory;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;

public class WorkLogAction extends CriterionAction {

	private static final Log log = LogFactory.getLog(WorkLogAction.class);

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

	// 初始化
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		dynaForm.set("beginDate", DateUtils.getCurrentMonthFirst());
		dynaForm.set("endDate", DateUtils.getDate());
		request.setAttribute("class.list", OptionUtils.getWorkLogClassListNew());

		return mapping.findForward("init");
	}

	// 查询显示_工作日志
	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("queryWorkLog", form, request,
				false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("captions.list", helper.getResult(0));
		request.setAttribute("results.list", helper.getResult(1));
		return mapping.findForward("show");
	}

	// 新增工作日志
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm dynaForm = (DynaActionForm) form;

		request.setAttribute("date",
				MethodFactory.replace(DateUtils.getDate(), "/", "-"));// 事发日期

		request.setAttribute("class.list", OptionUtils.getWorkLogClassListNew());// 岗位

		request.setAttribute("files.list", new ArrayList());// 电子附件

		return mapping.findForward("add");
	}

	// 删除工作日志by serialid
	public ActionForward delrow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CallHelper helper = initializeCallHelper("delWorkLogByid", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message.code", "0");
		request.setAttribute("message.information", helper.getOutput(0));

		return show(mapping, form, request, response);
	}

	// 工作日志_修改
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		CallHelper helper = initializeCallHelper("queryWorkLogByid", form,
				request, false);
		helper.execute();
		List worklogs = helper.getResult("worklog");
		if (worklogs.size() > 0) {
			BasicDynaBean rowBean = (BasicDynaBean) worklogs.get(0);
			MethodFactory.copyProperties(dynaForm, rowBean);
		}
		List tasks = null;
		List worklogtasks = helper.getResult("worklogtask");
		if (worklogtasks.size() > 0) {
			tasks = new ArrayList();
			for (int i = 0; i < worklogtasks.size(); i++) {
				BasicDynaBean rowBean = (BasicDynaBean) worklogtasks.get(i);
				WorkLogForm taskvo = new WorkLogForm();
				MethodFactory.copyProperties(taskvo, rowBean);
				tasks.add(taskvo);
			}
		}
		List worklogfiles = helper.getResult("worklogfile");
		request.setAttribute("tasks", tasks);
		request.setAttribute("files.list", worklogfiles);
		request.setAttribute("class.list", OptionUtils.getWorkLogClassListNew());
		return mapping.findForward("modify");
	}

	// 工作日志_记录查看
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		CallHelper helper = initializeCallHelper("queryWorkLogByid", form,
				request, false);
		helper.execute();
		List worklogs = helper.getResult("worklog");
		if (worklogs.size() > 0) {
			BasicDynaBean rowBean = (BasicDynaBean) worklogs.get(0);
			MethodFactory.copyProperties(dynaForm, rowBean);
		}
		List tasks = null;
		List worklogtasks = helper.getResult("worklogtask");
		if (worklogtasks.size() > 0) {
			tasks = new ArrayList();
			for (int i = 0; i < worklogtasks.size(); i++) {
				BasicDynaBean rowBean = (BasicDynaBean) worklogtasks.get(i);
				WorkLogForm taskvo = new WorkLogForm();
				MethodFactory.copyProperties(taskvo, rowBean);
				tasks.add(taskvo);
			}
		}
		List worklogfiles = helper.getResult("worklogfile");
		request.setAttribute("tasks", tasks);
		request.setAttribute("files.list", worklogfiles);
		request.setAttribute("class.list", OptionUtils.getWorkLogClassListNew());
		return mapping.findForward("view");
	}

	// 新增工作日志_新增保存
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm theForm = (DynaActionForm) form;
		// 工作日志主体内容
		CallHelper helper = initializeCallHelper("saveWorkLogAdd", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		String serialid = (String) helper.getOutput(0);
		request.setAttribute("message.code", "0");
		request.setAttribute("message.information", helper.getOutput(1));
		// 工作日志的工作任务
		String[] memos = request.getParameterValues("memo");
		for (int i = 0; i < memos.length; i++) {
			helper = initializeCallHelper("saveWorkLogTaskAdd", form, request,
					false);
			helper.setParam("serialid", serialid);
			helper.setParam("memo", memos[i]);
			helper.execute();
		}

		// 工作日志的附件
		String uploadPath = SystemConfig.getFilesUploadPath();
		uploadPath = MethodFactory.replace(uploadPath, "\\", "/");
		uploadPath = MethodFactory.replace(uploadPath, "//", "/");

		String filename = "";
		String allfilename = "";
		String strSaveFileName = "";

		Hashtable fileh = theForm.getMultipartRequestHandler()
				.getFileElements();
		for (Enumeration e = fileh.keys(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			FormFile file = (FormFile) fileh.get(key);

			if (file.getFileName().trim().length() > 0) {
				try {
					int loc = file.getFileName().lastIndexOf(".");
					String type = "";
					if (loc > 0) {
						type = file.getFileName().substring(loc,
								file.getFileName().trim().length());
					}
					filename = "file" + getPersonId(request) + "/"
							+ getfilename() + type;
					allfilename = uploadPath + filename;
					strSaveFileName = filename + "|"
							+ file.getFileName().trim() + "|"
							+ file.getContentType();
					directory.createdir(uploadPath + "file"
							+ getPersonId(request) + "/");
					File savedFile = new File(allfilename);
					OutputStream bos = null;
					bos = new FileOutputStream(savedFile);
					bos.write(file.getFileData());
					bos.close();

					// 保存电子附件
					helper = initializeCallHelper("saveWorkLogFileAdd", form,
							request, false);
					helper.setParam("serialid", serialid);
					helper.setParam("filename", filename);
					helper.setParam("savefilename", file.getFileName().trim());
					helper.setParam("filetype", file.getContentType());
					helper.execute();

				} catch (Exception ex) {
					log.error(ex);
					System.out.println("新增工作日志附件上传失败！");
				}
			}
		}
		return add(mapping, form, request, response);
	}

	// 新增工作日志_新增保存
	public ActionForward savemodify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm theForm = (DynaActionForm) form;
		// 工作日志主体内容
		CallHelper helper = initializeCallHelper("saveWorkLogModify", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message.code", "0");
		request.setAttribute("message.information", helper.getOutput(0));
		// 工作日志的工作任务
		String[] memos = request.getParameterValues("memo");
		for (int i = 0; i < memos.length; i++) {
			helper = initializeCallHelper("saveWorkLogTaskModify", form,
					request, false);
			helper.setParam("serialid", theForm.get("code"));
			helper.setParam("userId", getPersonId(request));
			helper.setParam("memo", memos[i]);
			helper.execute();
		}

		// 工作日志的附件
		String uploadPath = SystemConfig.getFilesUploadPath();
		uploadPath = MethodFactory.replace(uploadPath, "\\", "/");
		uploadPath = MethodFactory.replace(uploadPath, "//", "/");

		String filename = "";
		String allfilename = "";
		String strSaveFileName = "";

		Hashtable fileh = theForm.getMultipartRequestHandler()
				.getFileElements();
		for (Enumeration e = fileh.keys(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			FormFile file = (FormFile) fileh.get(key);

			if (file.getFileName().trim().length() > 0) {
				try {
					int loc = file.getFileName().lastIndexOf(".");
					String type = "";
					if (loc > 0) {
						type = file.getFileName().substring(loc,
								file.getFileName().trim().length());
					}
					filename = "file" + getPersonId(request) + "/"
							+ getfilename() + type;
					allfilename = uploadPath + filename;
					strSaveFileName = filename + "|"
							+ file.getFileName().trim() + "|"
							+ file.getContentType();
					directory.createdir(uploadPath + "file"
							+ getPersonId(request) + "/");
					File savedFile = new File(allfilename);
					OutputStream bos = null;
					bos = new FileOutputStream(savedFile);
					bos.write(file.getFileData());
					bos.close();

					// 保存电子附件
					helper = initializeCallHelper("saveWorkLogFileModify",
							form, request, false);
					helper.setParam("serialid", theForm.get("code"));
					helper.setParam("userId", getPersonId(request));
					helper.setParam("filename", filename);
					helper.setParam("savefilename", file.getFileName().trim());
					helper.setParam("filetype", file.getContentType());
					helper.execute();

				} catch (Exception ex) {
					log.error(ex);
					System.out.println("新增工作日志附件上传失败！");
				}
			}
		}
		return modify(mapping, form, request, response);
	}

	// 查看电子附件
	public ActionForward viewAnnex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			CallHelper helper = initializeCallHelper("getWorkLogAnnex", form,
					request, false);
			helper.execute();
			String strAnnexAliasInServer = (String) helper.getOutput(0);

			String strFullPath = "";
			String strFileName = "";
			strFileName = (String) helper.getOutput(1);

			String uploadPath = SystemConfig.getFilesUploadPath();
			uploadPath = MethodFactory.replace(uploadPath, "\\", "/");
			uploadPath = MethodFactory.replace(uploadPath, "//", "/");
			if (uploadPath.endsWith("/"))
				strFullPath = uploadPath + strAnnexAliasInServer;
			else
				strFullPath = uploadPath + strAnnexAliasInServer;

			response.setHeader("Cache-Control", "max-age=3153600");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(strFileName.getBytes("GBK"), "ISO8859_1"));

			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(strFullPath));
			BufferedOutputStream bos = new BufferedOutputStream(
					response.getOutputStream());

			byte[] buff = new byte[4096];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
			bis.close();
			bos.close();
			bos = null;
		} catch (Exception e) {
			log.error(e);
			return mapping.findForward("error");
		}

		return null;
	}

	// 删除附件文件
	public ActionForward delAnnex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CallHelper helper = initializeCallHelper("delWorkLogAnnex", form,
				request, false);
		helper.setParam("userId", getPersonId(request));
		helper.execute();
		request.setAttribute("message.code", "0");
		request.setAttribute("message.information", helper.getOutput(0));

		return add(mapping, form, request, response);
	}
}
