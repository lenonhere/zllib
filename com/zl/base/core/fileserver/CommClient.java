package com.zl.base.core.fileserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Properties;

public class CommClient {
	private static pathclass DBTypeTemp = pathclass.getInstance();
	protected String serverAddress = null;
	protected int serverPort = 0;

	public CommClient(String paramString) throws Exception {
		Properties localProperties = new Properties();
		String str1 = null;
		try {
			InputStream localInputStream = getClass().getResourceAsStream(
					"/server.properties");
			localProperties.load(localInputStream);
		} catch (Exception localException) {
			throw new Exception(
					"no properties file found found for server.properties");
		}
		String str2 = paramString;
		if (paramString == null)
			str2 = "";
		if (!str2.equals("")) {
			str2 = paramString.trim();
			str2 = "cugeneralservice_" + str2;
		} else {
			str2 = "cugeneralservice";
		}
		str1 = localProperties.getProperty(str2 + ".ip");
		if (str1 != null)
			this.serverAddress = str1;
		else
			throw new Exception("在配置文件server.properties中没有发现" + str2 + ".ip");
		str1 = localProperties.getProperty(str2 + ".port");
		if (str1 != null)
			this.serverPort = Integer.parseInt(str1);
		else
			throw new Exception("在配置文件server.properties中没有发现" + str2 + ".port");
	}

	public CommClient(String paramServerAddress, int paramServerPort)
			throws IOException {
		this.serverAddress = paramServerAddress;
		this.serverPort = paramServerPort;
	}

	public String GetInetAddress() {
		return this.serverAddress;
	}

	public void SetInetAddress(String paramString) {
		this.serverAddress = paramString;
	}

	public int GetInetPort() {
		return this.serverPort;
	}

	public void SetInetPort(int paramInt) {
		this.serverPort = paramInt;
	}

	public String WordFile(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5)
			throws Exception {
		String str = null;
		WordClient localWordClient = null;
		try {
			localWordClient = new WordClient(this.serverAddress,
					this.serverPort);
			str = localWordClient.GetWord(paramString1, paramString2,
					paramString3, paramString4, paramString5);
			localWordClient.Close();
			return str;
		} catch (InterruptedIOException localInterruptedIOException) {
			if (localWordClient != null)
				localWordClient.Close();
		}
		return "";
	}

	public boolean singleUpLoadFile(ArrayList paramArrayList,
			String paramString1, String paramString2) throws Exception {
		boolean bool = true;
		singleFileClient localsingleFileClient = null;
		try {
			localsingleFileClient = new singleFileClient(this.serverAddress,
					this.serverPort, paramString1);
			localsingleFileClient.SetTempPath(pathclass.getFILEtempdir());
			bool = localsingleFileClient.UpLoadFile(paramArrayList,
					paramString2);
			localsingleFileClient.Close();
			return bool;
		} catch (InterruptedIOException localInterruptedIOException) {
			if (localsingleFileClient != null)
				localsingleFileClient.Close();
		}
		return false;
	}

	public boolean singleDownLoadFile(ArrayList paramArrayList,
			String paramString) throws Exception {
		return singleDownLoadFile(paramArrayList, paramString, null);
	}

	public boolean singleDownLoadFile(ArrayList paramArrayList,
			String paramString1, String paramString2) throws Exception {
		boolean bool = true;
		singleFileClient localsingleFileClient = null;
		try {
			localsingleFileClient = new singleFileClient(this.serverAddress,
					this.serverPort, paramString1);
			localsingleFileClient.SetTempPath(pathclass.getFILEtempdir());
			bool = localsingleFileClient.DownLoadFile(paramArrayList,
					paramString2);
			localsingleFileClient.Close();
			return bool;
		} catch (InterruptedIOException localInterruptedIOException) {
			if (localsingleFileClient != null)
				localsingleFileClient.Close();
		}
		return false;
	}

	public static void main(String[] paramArrayOfString) {
		try {
			CommClient client = new CommClient("192.168.100.93", 9999);
			String str2 = "qmx.log";
			String str3 = "E:\\logs\\";
			String str1 = "<?xml version=\"1.0\" encoding=\"gb2312\"?><root><CONTENT_RECORDSET><row><PageCount>ABC</PageCount><ZoneName>ASE</ZoneName><CaseYear>1</CaseYear><WritCode>010221</WritCode><CaseKindName>测试</CaseKindName><PartyName>组织</PartyName><UnderTaker>执行人</UnderTaker><DisposeResult>处理结果</DisposeResult><RegisterDate>020301</RegisterDate><CompleteDate>030201</CompleteDate><Pigeonhole>归档日期</Pigeonhole><AuditorName>姓名</AuditorName><yy>20041231150610</yy><jj>3312</jj></row></CONTENT_RECORDSET></root>";
			String str4 = client.WordFile(str1, str2, "", str3, "test");
			System.out.println(str4);
		} catch (InterruptedIOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.fileserver.CommClient JD-Core Version: 0.6.1
 */