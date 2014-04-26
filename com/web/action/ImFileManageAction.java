package com.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.web.CoreDispatchAction;
import com.web.form.ImFileForm;
import com.zl.base.core.db.CallHelper;

public class ImFileManageAction extends CoreDispatchAction {

	private static final Log log = LogFactory.getLog(ImFileManageAction.class);

	private static final String fileFolder = "/im/files/";

	private static final double filesize = 5;

	public ActionForward filemultisendinit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("isAdmin", isSuperAdmin(request));
		return mapping.findForward("filemultisendinit");
	}

	public ActionForward filesend(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ImFileForm fileForm = (ImFileForm) form;

		FormFile file = fileForm.getSendFile();
		int fileSize = file.getFileSize();
		try {
			// double fileSize=file.getFileSize()
			if (fileSize > 5 * 1024 * 1024) {
				request.setAttribute("message", "你要传送的文件太大啦，超过限制");
				return mapping.findForward("filemultisendinit");
			}
			String rootfilePath = request.getSession().getServletContext()
					.getRealPath(fileFolder);

			File homeDir = new File(rootfilePath);

			if (!homeDir.exists()) {
				homeDir.mkdirs();
			}

			File selfDir = new File(homeDir, getPersonName(request).trim());

			if (!selfDir.exists()) {
				selfDir.mkdir();
			}
			File[] childFiles = selfDir.listFiles();
			int count = 0;
			if (childFiles != null && childFiles.length > 0) {
				for (int i = 0; i < childFiles.length; i++) {
					if (childFiles[i].getName().indexOf(file.getFileName()) != -1) {
						count++;
					}
				}
			}
			String saveFileName = file.getFileName();

			int pix = file.getFileName().lastIndexOf(".");
			if (count == 0)
				saveFileName = file.getFileName().substring(0, pix)
						+ file.getFileName().substring(pix,
								file.getFileName().length());
			else
				saveFileName = file.getFileName().substring(0, pix)
						+ "["
						+ count
						+ "]"
						+ file.getFileName().substring(pix,
								file.getFileName().length());

			File filesave = new File(selfDir, saveFileName);

			OutputStream bos = null;
			bos = new FileOutputStream(filesave);
			bos.write(file.getFileData());
			bos.close();
			fileForm.setFilename(saveFileName);

			CallHelper helper = initializeCallHelper("sendImMsg", form,
					request, false);

			String receiverIds = fileForm.getReceiverIds();

			helper.setParam("senderId", getPersonId(request));
			helper.setParam("receiveType", "1");
			helper.setParam("receierIds", receiverIds);
			helper.setParam("msgContent", getPersonName(request) + "/"
					+ saveFileName);
			helper.setParam("receiveType", "1");
			helper.setParam("msgType", "2");
			helper.setParam("msgTitle", "title");
			helper.execute();
			String message = (String) helper.getOutput("message");
			request.setAttribute("message", message);

		} catch (Exception e) {
			log.error("保存文件发生错误::::" + e.toString());
		}

		return mapping.findForward("filesendsuccess");

	}

}
