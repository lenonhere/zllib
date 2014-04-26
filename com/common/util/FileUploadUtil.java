/**
 * @author: 朱忠南
 * @date: Feb 19, 2009
 * @company: 杭州州力数据科技有限公司
 * @desribe:
 * @modify_author:
 * @modify_time:
 */
package com.common.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.common.SystemConfig;
import com.zl.base.core.db.Executer;

/**
 * @author jokin
 * @date Feb 19, 2009
 */
public class FileUploadUtil {

	public static final Log logger = LogFactory.getLog(FileUploadUtil.class);
	public static final String FILE_PATH = SystemConfig.getFilesUploadPath();

	/**
	 * 使用该附件的时候调用此方法，将此附件标志为已被使用
	 *
	 * @author: 朱忠南
	 * @param newFileName
	 * @throws Exception
	 */
	public static void use(String newFileName) throws Exception {
		try {
			String sql = "update G_UploadFiles set isactive = 1 where newfilename = '"
					+ newFileName + "'";
			Executer.getInstance().ExecUpdateSQL(sql);
		} catch (Exception e) {
			logger.error("使用此文件时发生错误：", e);
			throw e;
		}
	}

	/**
	 * 删除该附件的时候调用此方法，删除此附件，并将此附件标记为已被删除
	 *
	 * @author: 朱忠南
	 * @param newFileName
	 * @throws Exception
	 */
	public static void delete(String newFileName) throws Exception {
		try {
			File file = new File(FILE_PATH + newFileName);
			logger.debug("文件路径：" + FILE_PATH + newFileName);
			logger.debug("文件是否存在：" + file.exists());
			file.delete();

			String sql = "update G_UploadFiles set isdelete = 1 where newfilename = '"
					+ newFileName + "'";
			Executer.getInstance().ExecUpdateSQL(sql);
		} catch (Exception e) {
			logger.error("删除文件时发生错误：", e);
			throw e;
		}
	}

}
