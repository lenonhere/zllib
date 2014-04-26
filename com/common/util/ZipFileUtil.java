/**
 * @author: 朱忠南
 * @date: Dec 10, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe:
 * @modify_author:
 * @modify_time:
 */
package com.common.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import common.Logger;

/**
 * @author jokin
 * @date Dec 10, 2008
 */
public class ZipFileUtil {

	private final static Logger logger = Logger.getLogger(ZipFileUtil.class);

	/**
	 * 将文件inputFileUri压缩到zipFileName中
	 *
	 * @author: 朱忠南
	 * @param zipFileName
	 *            压缩文件（zip）
	 * @param inputFileUri
	 *            压缩的文件路径
	 * @param fileName
	 *            压缩的文件名字
	 * @throws Exception
	 */
	public static void zip(String zipFileName, String inputFileUri)
			throws Exception {
		try {
			File inputFile = new File(inputFileUri);
			if (!inputFile.exists())
				throw new Exception("文件" + inputFileUri + "不存在");
			FileOutputStream f = new FileOutputStream(zipFileName);
			CheckedOutputStream ch = new CheckedOutputStream(f, new CRC32());
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					ch));
			zip(out, inputFile, inputFileUri);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw e;
		}

	}

	public static void zip(ZipOutputStream out, File inputFile, String base)
			throws Exception {
		if (inputFile.isDirectory()) {
			File f[] = inputFile.listFiles();
			for (int i = 0; i < f.length; i++)
				zip(out, f[i], base);
		} else {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						new FileInputStream(inputFile), "ISO8859_1"));
				int c;
				String filePath = inputFile.getAbsolutePath()
						.replace('\\', '/');
				logger.debug("filePath=" + filePath);
				logger.debug("base=" + base);
				String subPath = filePath.substring(filePath.indexOf(base)
						+ base.length());
				logger.debug("subPath=" + subPath);
				out.putNextEntry(new ZipEntry(subPath));
				while ((c = in.read()) != -1)
					out.write(c);
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				throw e;
			}
		}

	}

}
