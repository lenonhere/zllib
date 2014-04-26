/**
 * @author: 朱忠南
 * @date: Feb 19, 2009
 * @company: 杭州州力数据科技有限公司
 * @desribe: 文件上传通用Action，请参考FileUpload.js
 * @attention: 注意在删除记录时，请调用FileUploadUtil.delete(newFileName)方法删除文件，以节约文件系统空间
 * @modify_author:
 * @modify_time:
 */
package com.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.common.util.FileUploadUtil;
import com.web.CoreDispatchAction;
import com.web.form.FileUploadForm;
import com.zl.base.core.db.CallHelper;

/**
 * @author jokin
 * @date Feb 19, 2009
 */
public class FileUploadAction extends CoreDispatchAction {

	private static final Log logger = LogFactory.getLog(FileUploadAction.class);

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("init");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CallHelper caller = initializeCallHelper("getUploadFilesUseless", form,
				request, false);
		caller.execute();
		request.setAttribute("captions.list", caller.getResult(0));
		request.setAttribute("results.list", caller.getResult(1));
		return mapping.findForward("list");
	}

	/**
	 * 上传文件
	 *
	 * @author: 朱忠南
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FileUploadForm aform = (FileUploadForm) form;
		FormFile file = aform.getFile();// 取得上传的文件
		if (file == null || file.getFileSize() == 0) {
			aform.setMessage("上传的文件不存在");
			return list(mapping, form, request, response);
		}
		double allowMaxFileSize = -1;// 文件最大允许值（M）
		if (aform.getAllowMaxFileSize() != null
				&& !aform.getAllowMaxFileSize().trim().equals("")) {
			try {
				allowMaxFileSize = Double.parseDouble(aform
						.getAllowMaxFileSize().trim());
			} catch (NumberFormatException e) {
				allowMaxFileSize = -1;
				logger.info("文件最大允许值allowMaxFileSize="
						+ aform.getAllowMaxFileSize() + "不是数字类型，系统将默认不限制其大小");
			}
		}

		aform.setFileSize(file.getFileSize() + "");

		if (allowMaxFileSize > 0
				&& file.getFileSize() > allowMaxFileSize * 1024 * 1024) {
			aform.setMessage("您上传的文件超出" + allowMaxFileSize + "M大小限制");
			return list(mapping, form, request, response);
		}

		String newFileName = getRandomFileName();
		aform.setNewFileName(newFileName);
		int loc = file.getFileName().lastIndexOf(".");
		String fileSuffix = "";
		String oldFileName = "";
		if (loc > 0) {
			fileSuffix = file.getFileName().substring(loc,
					file.getFileName().length());
			oldFileName = file.getFileName().substring(0, loc);
		} else {
			oldFileName = file.getFileName().substring(0,
					file.getFileName().length());
		}
		logger.debug("oldFileName = " + oldFileName + ",fileSuffix="
				+ fileSuffix);

		aform.setFileSuffix(fileSuffix);
		aform.setOldFileName(oldFileName);

		// 另存到服务器
		FileOutputStream os = null;
		File newFile = null;
		try {
			String filePath = FileUploadUtil.FILE_PATH;
			File path = new File(filePath);
			if (!path.exists())
				path.mkdirs();// 创建文件存放目录

			newFile = new File(filePath + newFileName);
			os = new FileOutputStream(newFile);
			os.write(file.getFileData());

		} catch (IOException e) {
			logger.error("保存文件到服务器失败：", e);
			throw e;
		} finally {
			if (os != null)
				os.close();
			os = null;
		}

		// 记录到数据库中
		CallHelper caller = initializeCallHelper("saveUploadFile", form,
				request, false);
		caller.execute();
		if (caller.getSqlCode() != 0) {
			newFile.deleteOnExit();// 如果保存失败，删除此文件
			logger.info("因为存储过程保存失败，所以文件" + newFile.getPath() + "已经删除");
		}
		// aform.setMessage((String)caller.getOutput(0));
		return list(mapping, form, request, response);
	}

	/**
	 * 下载文件
	 *
	 * @author: 朱忠南
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CallHelper caller = initializeCallHelper("downUploadFile", form,
				request, false);
		caller.execute();
		List resultSet = caller.getResult(0);
		if (resultSet.isEmpty()) {
			logger.info("文件已被删除");
			return mapping.findForward("fileNotFound");
		}

		FileUploadForm aform = (FileUploadForm) form;
		setToForm(aform, resultSet);

		String mimeType = request.getParameter("mimeType");
		response.setContentType(mimeType);
		String tempfilename = java.net.URLEncoder.encode(
				aform.getOldFileName(), "UTF-8");
		if (tempfilename.length() > 130) {
			tempfilename = new String(
					aform.getOldFileName().getBytes("gb2312"), "ISO8859-1");
		}
		response.setHeader("Content-Disposition", "attachment; filename="
				+ tempfilename + aform.getFileSuffix());

		File file = new File(FileUploadUtil.FILE_PATH + aform.getNewFileName());
		if (!file.exists()) {
			logger.info("文件" + file.getPath() + "不存在");
			return mapping.findForward("fileNotFound");
		}
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					response.getOutputStream());
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			byte[] input = new byte[1024];
			boolean eof = false;
			while (!eof) {
				int length = bis.read(input);
				if (length == -1) {
					eof = true;
				} else {
					bos.write(input, 0, length);
				}
			}
			bos.flush();
			bis.close();
			bos.close();
		} catch (Exception ex) {
			logger.error("下载发生异常：", ex);
		}
		return null;
	}

	/**
	 * @author: 朱忠南
	 * @param aform
	 * @param resultSet
	 */
	private void setToForm(FileUploadForm aform, List resultSet)
			throws Exception {
		BasicDynaBean bean = (BasicDynaBean) resultSet.get(0);
		aform.setNewFileName((String) bean.get("newfilename"));
		aform.setOldFileName((String) bean.get("oldfilename"));
		aform.setFileSuffix((String) bean.get("filesuffix"));
		aform.setFileSize(((Double) bean.get("filesize")).toString());
		aform.setIsActive(((Integer) bean.get("isactive")).toString());
		aform.setIsDelete(((Integer) bean.get("isdelete")).toString());
		aform.setDownCount(((Integer) bean.get("downcount")).toString());
		aform.setUploadTime(((Timestamp) bean.get("uploadtime")).toString());
		aform.setUserId(((Integer) bean.get("personid")).toString());
		aform.setUserName((String) bean.get("personname"));
	}

	/**
	 * 删除文件
	 *
	 * @author: 朱忠南
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FileUploadForm aform = (FileUploadForm) form;
		try {
			FileUploadUtil.delete(aform.getNewFileName());
		} catch (Exception e) {
			aform.setMessage("删除时发生异常错误");
			logger.error("删除附件出错：", e);
		}
		// aform.setMessage("删除文件成功");
		return list(mapping, form, request, response);
	}

	private String getRandomFileName() {
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
		returnvalue = stryear + strmonth + strday + strhour + strminute
				+ strsecond + strmsecond;
		return returnvalue;
	}

}
