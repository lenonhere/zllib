package com.zl.base.core.file;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;

import com.common.SystemConfig;
import com.zl.base.core.fileserver.CommClient;
import com.zl.util.MethodFactory;

public class fileupload extends HttpServlet {

	private static final long serialVersionUID = 194371320920958395L;

	public static filestruct upload(HttpServletRequest request,
			HttpServletResponse response, FormFile formFile,
			String paramString1, String paramString2, boolean paramBoolean,
			String paramString3) {
		return pupload(request, response, formFile, paramString1, paramString2,
				paramBoolean, false, "", 0, 0, "", paramString3);
	}

	public static filestruct upload(HttpServletRequest request,
			HttpServletResponse response, FormFile formFile,
			String paramString1, String paramString2, boolean paramBoolean,
			String paramString3, String paramString4) {
		return pupload(request, response, formFile, paramString1, paramString2,
				paramBoolean, false, "", 0, 0, paramString3, paramString4);
	}

	public static filestruct upload(HttpServletRequest request,
			HttpServletResponse response, FormFile formFile,
			String paramString1, String paramString2, boolean paramBoolean,
			int paramInt, String paramString3) {
		return pupload(request, response, formFile, paramString1, paramString2,
				paramBoolean, false, "", 0, paramInt, "", paramString3);
	}

	public static filestruct upload(HttpServletRequest request,
			HttpServletResponse response, FormFile formFile,
			String paramString1, String paramString2, boolean paramBoolean,
			int paramInt, String paramString3, String paramString4) {
		return pupload(request, response, formFile, paramString1, paramString2,
				paramBoolean, false, "", 0, paramInt, paramString3,
				paramString4);
	}

	public static filestruct upload(HttpServletRequest request,
			HttpServletResponse response, FormFile formFile,
			String paramString1, String paramString2, boolean paramBoolean,
			String paramString3, int paramInt, String paramString4) {
		return pupload(request, response, formFile, paramString1, paramString2,
				paramBoolean, true, paramString3, paramInt, 0, "", paramString4);
	}

	public static filestruct upload(HttpServletRequest request,
			HttpServletResponse response, FormFile formFile,
			String paramString1, String paramString2, boolean paramBoolean,
			String paramString3, int paramInt, String paramString4,
			String paramString5) {
		return pupload(request, response, formFile, paramString1, paramString2,
				paramBoolean, true, paramString3, paramInt, 0, paramString4,
				paramString5);
	}

	public static filestruct upload(HttpServletRequest request,
			HttpServletResponse response, FormFile formFile,
			String paramString1, String paramString2, boolean paramBoolean,
			String paramString3, int paramInt1, int paramInt2,
			String paramString4) {
		return pupload(request, response, formFile, paramString1, paramString2,
				paramBoolean, true, paramString3, paramInt1, paramInt2, "",
				paramString4);
	}

	public static filestruct upload(HttpServletRequest request,
			HttpServletResponse response, FormFile formFile,
			String paramString1, String paramString2, boolean paramBoolean,
			String paramString3, int paramInt1, int paramInt2,
			String paramString4, String paramString5) {
		return pupload(request, response, formFile, paramString1, paramString2,
				paramBoolean, true, paramString3, paramInt1, paramInt2,
				paramString4, paramString5);
	}

	private static filestruct pupload(HttpServletRequest request,
			HttpServletResponse response, FormFile formFile,
			String paramString1, String paramString2, boolean paramBoolean1,
			boolean paramBoolean2, String paramString3, int paramInt1,
			int paramInt2, String paramString4, String paramString5) {
		if (paramString1 == null)
			paramString1 = "";
		if (paramString1.equals(""))
			paramString1 = SystemConfig.fileTempDir;
		paramString1 = paramString1 + "/";
		filestruct localfilestruct = new filestruct();
		if ((paramInt2 > 0) && (formFile.getFileSize() > paramInt2 * 1000)) {
			localfilestruct.setresult(false);
			localfilestruct.setmessage("文件大小大于:" + String.valueOf(paramInt2)
					+ "k,不能上传!");
			return localfilestruct;
		}
		try {
			paramString1 = MethodFactory.replace(paramString1, "\\", "/");
			paramString1 = MethodFactory.replace(paramString1, "//", "/");
			paramString2 = MethodFactory.replace(paramString2, "\\", "/");
			paramString2 = MethodFactory.replace(paramString2, "//", "/");
		} catch (Exception localException1) {
		}
		String str1 = "/";
		String str2 = paramString1 + paramString2 + str1;
		String str3 = request.getCharacterEncoding();
		if ((str3 != null) && (str3.equalsIgnoreCase("utf-8")))
			response.setContentType("text/html; charset=gb2312");
		try {
			InputStream localInputStream = formFile.getInputStream();
			// ByteArrayOutputStream localByteArrayOutputStream = new
			// ByteArrayOutputStream();
			String str4 = "";
			String str5 = "";
			directory.createdir(str2);
			if (paramBoolean1 == true) {
				str5 = formFile.getFileName();
				str4 = str2 + str5;
			} else {
				if (paramString4 == null)
					paramString4 = "";
				if (paramString4.trim().equals("")) {
					int i = formFile.getFileName().indexOf(".");
					String str6 = "";
					if (i > 0)
						str6 = formFile.getFileName().substring(i,
								formFile.getFileName().length());
					str5 = getfilename() + str6;
				} else {
					str5 = paramString4;
				}
				str4 = str2 + str5;
			}
			FileOutputStream localFileOutputStream = new FileOutputStream(str4);
			int j = 0;
			byte[] arrayOfByte = new byte[8192];
			while ((j = localInputStream.read(arrayOfByte, 0, 8192)) != -1)
				localFileOutputStream.write(arrayOfByte, 0, j);
			localFileOutputStream.close();
			localInputStream.close();
			if (paramBoolean2 == true) {
				//
				CommClient localCommClient = new CommClient(paramString3,
						paramInt1);
				ArrayList<Object> localArrayList = new ArrayList<Object>();
				localArrayList.add(paramString2 + str1 + str5);
				if (localCommClient.singleUpLoadFile(localArrayList,
						paramString1, paramString5))
					localfilestruct.setresult(true);
				else
					localfilestruct.setresult(false);
			} else {
				localfilestruct.setresult(true);
			}
			localfilestruct.setfilename(str5);
			localfilestruct.setfilepath(paramString2);
			localfilestruct.setpathfilename(paramString2 + str1 + str5);
		} catch (Exception localException2) {
			localfilestruct.setresult(false);
			localfilestruct.setmessage(localException2.getMessage());
			System.err.print(localException2);
		}
		return localfilestruct;
	}

	public static String getfilename() {
		String str1 = "";
		GregorianCalendar localGregorianCalendar = new GregorianCalendar();
		String str2 = String.valueOf(localGregorianCalendar.get(1));
		String str3 = "00" + String.valueOf(localGregorianCalendar.get(2) + 1);
		str3 = str3.substring(str3.length() - 2, str3.length());
		String str4 = "00" + String.valueOf(localGregorianCalendar.get(5));
		str4 = str4.substring(str4.length() - 2, str4.length());
		String str5 = "00" + String.valueOf(localGregorianCalendar.get(11));
		str5 = str5.substring(str5.length() - 2, str5.length());
		String str6 = "00" + String.valueOf(localGregorianCalendar.get(12));
		str6 = str6.substring(str6.length() - 2, str6.length());
		String str7 = "00" + String.valueOf(localGregorianCalendar.get(13));
		str7 = str7.substring(str7.length() - 2, str7.length());
		String str8 = "000000" + String.valueOf(localGregorianCalendar.get(14));
		str8 = str8.substring(str8.length() - 6, str8.length());
		str1 = str2 + str3 + str4 + str5 + str6 + str7 + str8;
		return str1;
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name: com.zl.base.core.file.fileupload
 * JD-Core Version: 0.6.1
 */