/**
 * @author jokin-朱忠南
 * @date 2008-9-16
 * @deprecated: MD5加密程序
 */
package com.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * MD5加密程序
 *
 * @author jokin-朱忠南
 * @date Sep 16, 2008
 */
public class MD5 {

	/**
	 * digest string
	 */
	private String digestString;

	/**
	 * 构造函数
	 *
	 * @param str
	 *            String, 字符串
	 */
	public MD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] digestByte = md.digest();

			digestString = byte2hex(digestByte);
		} catch (java.security.NoSuchAlgorithmException e) {
			System.err.println("非法摘要算法");
			e.printStackTrace();
		}
	}

	/**
	 * 构造函数
	 *
	 * @param str
	 *            String, 字符串
	 * @param encoding
	 *            String 字符串转成字节时的字符集
	 */
	public MD5(String str, String encoding) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			try {
				md.update(str.getBytes(encoding));
			} catch (UnsupportedEncodingException e) {
				md.update(str.getBytes());
			}
			byte[] digestByte = md.digest();

			digestString = byte2hex(digestByte);
		} catch (java.security.NoSuchAlgorithmException e) {
			System.err.println("非法摘要算法");
			e.printStackTrace();
		}
	}

	/**
	 * 构造函数
	 *
	 * @param file
	 *            File, 文件对象
	 */
	public MD5(File file) {
		try {
			long fileSize = file.length();
			byte[] fileBytes = new byte[(int) fileSize];
			FileInputStream fis = new FileInputStream(file);
			fis.read(fileBytes);

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(fileBytes);
			byte[] digestByte = md.digest();

			digestString = byte2hex(digestByte);
		} catch (java.security.NoSuchAlgorithmException e) {
			System.err.println("非法摘要算法");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("catch IOException.");
			e.printStackTrace();
		}
	}

	/**
	 * 获得digest string
	 *
	 * @return String
	 */
	public String getDigestString() {
		return digestString;
	}

	/**
	 * 二行制转字符串
	 *
	 * @param b
	 *            byte[]
	 * @return String
	 */
	public String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * only for test
	 *
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		MD5 md5 = new MD5("db2admin");
		System.out.println(md5.getDigestString());
	}
}
