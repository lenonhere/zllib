package com.common;

import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jokin
 * @date Feb 19, 2009
 */
public class SystemConfig {

	private static final Log logger = LogFactory.getLog(SystemConfig.class);
	public static final String PROPERTIES_FILE = "system";
	public static final String fileTempDir = System
			.getProperty("java.io.tmpdir");
	private static ResourceBundle resource;
	static {
		try {
			resource = ResourceBundle.getBundle(PROPERTIES_FILE);
		} catch (Exception e) {
			logger.error("读取配置文件" + PROPERTIES_FILE + ".properties出错：", e);
		}
	}

	/**
	 * @param propertiesFileName
	 *            [system | db]
	 * @return
	 */
	public static ResourceBundle getResource(String propertiesFileName) {
		try {
			resource = ResourceBundle.getBundle(propertiesFileName);
		} catch (Exception e) {
			logger.error("读取配置文件" + propertiesFileName + ".properties出错：", e);
		}
		return resource;
	}

	/**
	 * @param string
	 * @return
	 */
	public static String getString(String propName) {
		if (resource != null)
			return resource.getString(propName);
		else
			return null;
	}

	/**
	 * 工程WebRoot目录
	 *
	 * @return E:/myutils/WebRoot
	 */
	public static String getDocBasePath() {
		String path = System.getProperty("user.dir");
		return path + "/WebRoot";
	}

	/**
	 * 工程WebRoot目录
	 *
	 * @param webapp
	 *            指定WEB-INF的上级目录名
	 * @return E:/myutils/{webapp}
	 */
	public static String getDocBasePath(String webapp) {
		String path = System.getProperty("user.dir");
		if (webapp == null || "".equals(webapp)) {
			webapp = "WebRoot";
		}
		return path + "/" + webapp;
	}

	/**
	 * 临时文件上传目录
	 *
	 * @return
	 */
	public static String getTempUploadPath() {
		return getDocBasePath() + getString("temp_uploadpath");
	}

	/**
	 * SQL文件生成目录
	 *
	 * @return
	 */
	public static String getSqlScriptUploadPath() {
		return getDocBasePath() + getString("sqls_uploadpath");
	}

	/**
	 * 普通文件上传目录
	 *
	 * @return
	 */
	public static String getFilesUploadPath() {
		return getDocBasePath() + getString("file_uploadpath");
	}

	/**
	 * 图片文件上传目录
	 *
	 * @return
	 */
	public static String getImagesUploadPath() {
		return getDocBasePath() + getString("imgs_uploadpath");
	}

	/**
	 * 邮件附件上传路径
	 *
	 * @return
	 */
	public static String getMailAnnexUploadPath() {
		String uploadFilePath = "/uploads/mail/annexs/";
		if (resource != null) {
			uploadFilePath = getString("mail_annexpath");
		}
		return uploadFilePath;
	}

	/**
	 * dbinfo.properties文件的绝对路径
	 *
	 * @return E:/myutils/WebRoot/WEB-INF/classes/dbinfo.properties
	 */
	public static String getDBInfoFilePath() {
		String path = SystemConfig.class.getResource("/dbinfo.properties")
				.getPath();
		if (path == null) {
			path = "";
		} else {
			path = path.substring(1);
		}
		return path;
	}
}
