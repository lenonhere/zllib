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
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.common.SystemConfig;
import com.web.form.CommonFileUploadForm;
import com.zl.base.core.file.directory;
import com.zl.common.PFValue;

public class CommonFileUploadAction extends DispatchAction {

	private static final Log log = LogFactory
			.getLog(CommonFileUploadAction.class);

	public ActionForward upload(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			CommonFileUploadForm theForm = (CommonFileUploadForm) form;
			FormFile file = theForm.getuploadFormFile();// 取得上传的文件
			// String filepath =
			// request.getSession().getServletContext().getRealPath("/content/images/");
			String filepath = SystemConfig.getFilesUploadPath();
			String filename = "";
			String filename_all = "";
			directory.createdir(filepath);
			int loc = file.getFileName().indexOf(".");
			String type = "";
			if (loc > 0) {
				type = file.getFileName().substring(loc,
						file.getFileName().length());
			}
			filename = PFValue.getPathname() + type;
			filename_all = filepath + filename;// filename_all = filepath + "/"
												// + filename;
			File filesave = new File(filename_all);
			OutputStream bos = null;
			bos = new FileOutputStream(filesave);
			bos.write(file.getFileData());
			bos.close();
			theForm.setFilename(filename);
		} catch (Exception ex) {
			request.setAttribute("err.info", "上传失败！");
			log.debug(ex);
		}
		return actionMapping.findForward("upload");
	}

	public ActionForward init(ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return actionMapping.findForward("init");
	}

}
