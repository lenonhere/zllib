package com.qmx.netutils;

/**
 * 发送邮件需要使用的基本信息
 <p>
 163邮箱
 POP3服务器：pop3.163.com
 SMTP服务器：smtp.163.com
 Yahoo邮箱
 POP3服务器：pop.mail.yahoo.com.cn
 SMTP服务器：smtp.mail.yahoo.com
 搜狐邮箱
 POP3服务器：pop3.sohu.com
 SMTP服务器：smtp.sohu.com
 新浪邮箱
 POP3服务器：pop.sina.com.cn 或pop3.sina.com.cn
 SMTP服务器：smtp.sina.com.cn
 请选择smtp服务器要求身份验证选项
 139邮箱
 POP3服务器：pop3.139.com
 SMTP服务器：smtp.139.com
 Gmail邮箱
 POP3服务器：pop.gmail.com  端口:995  开启ssl
 SMTP服务器：smtp.gmail.com  端口:465 或者587  开启ssl
 帐号即用户名，密码相同，邮件地址为username@gmail.com
 QQ邮箱
 POP3服务器：pop.qq.com
 SMTP服务器：smtp.qq.com
 SMTP服务器需要身份验证
 tom邮箱
 POP3服务器：pop.tom.com.cn
 SMTP服务器：smtp.tom.com.cn
 </p>
 */
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailSenderInfo {
	// 发送邮件的服务器的IP和端口
	private String mailServerHost;
	private String mailServerPort = "25";
	// 邮件发送者的地址
	private String fromAddress;
	// 邮件接收者的地址
	private String toAddress;
	// 邮件抄送者的地址
	private String ccAddress;
	// 邮件暗送者的地址
	private String bccAddress;
	// 登陆邮件发送服务器的用户名和密码
	private String userName;
	private String password;
	// 是否需要身份验证
	private boolean validate = false;
	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;
	// 邮件附件的文件名
	private String[] attachFileNames;

	/**
	 * 获得邮件会话属性
	 */
	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		return p;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] fileNames) {
		this.attachFileNames = fileNames;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getUserName() {
		return userName;
	}

	public String getCcAddress() {
		return ccAddress;
	}

	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}

	public String getBccAddress() {
		return bccAddress;
	}

	public void setBccAddress(String bccAddress) {
		this.bccAddress = bccAddress;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String textContent) {
		this.content = textContent;
	}
}

class MyAuthenticator extends Authenticator {
	String userName = null;
	String password = null;

	public MyAuthenticator() {
	}

	public MyAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
