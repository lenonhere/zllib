package com.ws.basedata;

public class ErrorStatusInfo {

	/*
	 * 1. 服务状态码
	 */
	private static final String SERVERSTATUS_000 = "000: 传输成功!";
	private static final String SERVERSTATUS_100 = "100: 用户不存在!";
	private static final String SERVERSTATUS_101 = "101: 用户已停用!";
	private static final String SERVERSTATUS_102 = "102: 帐户名密码不正确!";
	private static final String SERVERSTATUS_103 = "103: 调用接口名错误!";
	private static final String SERVERSTATUS_104 = "104: 应用系统错误!";
	private static final String SERVERSTATUS_105 = "105: 服务调用人数已超过最大同时调用人数!";
	//
	private static final String SERVERSTATUS_501 = "501: 必须输入参数中存在空值！";
	private static final String SERVERSTATUS_502 = "502: 参数类型不正确！";
	private static final String SERVERSTATUS_503 = "503: XML参数文件格式错误！";
	private static final String SERVERSTATUS_504 = "504: XML参数XSD验证失败！";
	private static final String SERVERSTATUS_505 = "505: 查询数据不存在！";
	//
	private static final String SERVERSTATUS_601 = "601: 商业未发起月度需求计划!";
	private static final String SERVERSTATUS_602 = "602: 数据状态与操作相冲突！";
	private static final String SERVERSTATUS_603 = "603: 规格量超过省内计划余量！";// <超过剩余协议量了>
	//
	private static final String SERVERSTATUS_651 = "651: 尚未制定半年协议！";
	private static final String SERVERSTATUS_652 = "652: 月度计划存在且不是草稿！";
	private static final String SERVERSTATUS_653 = "653: 请求确认的数据不存在！";
	private static final String SERVERSTATUS_654 = "654: 请求返回的数据不存在！";
	private static final String SERVERSTATUS_655 = "655: 请求的数据当前不可确认！";
	private static final String SERVERSTATUS_656 = "656: 请求的数据当前不可返回！";

	private static String message = "";

	public static boolean printServerStateInfo(String planInfo)
			throws Exception {
		boolean flag = false;
		if ("000".equals(planInfo)) {
			message = SERVERSTATUS_000;
			flag = true;
		} else if ("100".equals(planInfo)) {
			message = SERVERSTATUS_100;
		} else if ("101".equals(planInfo)) {
			message = SERVERSTATUS_101;
		} else if ("102".equals(planInfo)) {
			message = SERVERSTATUS_102;
		} else if ("103".equals(planInfo)) {
			message = SERVERSTATUS_103;
		} else if ("104".equals(planInfo)) {
			message = SERVERSTATUS_104;
		} else if ("105".equals(planInfo)) {
			message = SERVERSTATUS_105;
		} else if ("501".equals(planInfo)) {
			message = SERVERSTATUS_501;
		} else if ("502".equals(planInfo)) {
			message = SERVERSTATUS_502;
		} else if ("503".equals(planInfo)) {
			message = SERVERSTATUS_503;
		} else if ("504".equals(planInfo)) {
			message = SERVERSTATUS_504;
		} else if ("505".equals(planInfo)) {
			message = SERVERSTATUS_505;
		} else if ("601".equals(planInfo)) {
			message = SERVERSTATUS_601;
		} else if ("602".equals(planInfo)) {
			message = SERVERSTATUS_602;
		} else if ("603".equals(planInfo)) {
			message = SERVERSTATUS_603;
		} else if ("651".equals(planInfo)) {
			message = SERVERSTATUS_651;
		} else if ("652".equals(planInfo)) {
			message = SERVERSTATUS_652;
		} else if ("653".equals(planInfo)) {
			message = SERVERSTATUS_653;
		} else if ("654".equals(planInfo)) {
			message = SERVERSTATUS_654;
		} else if ("655".equals(planInfo)) {
			message = SERVERSTATUS_655;
		} else if ("656".equals(planInfo)) {
			message = SERVERSTATUS_656;
		} else {
			int index = planInfo.indexOf("DATASETS");
			if (index > 0) {
				flag = true;
				message = "数据导入成功!";
			} else {
				message = "未知的服务状态码! ";
			}
		}
		System.out.println("--------//" + message + "//--------");
		return flag;
	}

	public static String getServerStateInfo(String planInfo) throws Exception {

		if ("000".equals(planInfo)) {
			message = SERVERSTATUS_000;
		} else if ("100".equals(planInfo)) {
			message = SERVERSTATUS_100;
		} else if ("101".equals(planInfo)) {
			message = SERVERSTATUS_101;
		} else if ("102".equals(planInfo)) {
			message = SERVERSTATUS_102;
		} else if ("103".equals(planInfo)) {
			message = SERVERSTATUS_103;
		} else if ("104".equals(planInfo)) {
			message = SERVERSTATUS_104;
		} else if ("105".equals(planInfo)) {
			message = SERVERSTATUS_105;
		} else if ("501".equals(planInfo)) {
			message = SERVERSTATUS_501;
		} else if ("502".equals(planInfo)) {
			message = SERVERSTATUS_502;
		} else if ("503".equals(planInfo)) {
			message = SERVERSTATUS_503;
		} else if ("504".equals(planInfo)) {
			message = SERVERSTATUS_504;
		} else if ("505".equals(planInfo)) {
			message = SERVERSTATUS_505;
		} else if ("601".equals(planInfo)) {
			message = SERVERSTATUS_601;
		} else if ("602".equals(planInfo)) {
			message = SERVERSTATUS_602;
		} else if ("603".equals(planInfo)) {
			message = SERVERSTATUS_603;
		} else if ("651".equals(planInfo)) {
			message = SERVERSTATUS_651;
		} else if ("652".equals(planInfo)) {
			message = SERVERSTATUS_652;
		} else if ("653".equals(planInfo)) {
			message = SERVERSTATUS_653;
		} else if ("654".equals(planInfo)) {
			message = SERVERSTATUS_654;
		} else if ("655".equals(planInfo)) {
			message = SERVERSTATUS_655;
		} else if ("656".equals(planInfo)) {
			message = SERVERSTATUS_656;
		} else {
			int index = planInfo.indexOf("DATASETS");
			if (index > 0) {
				message = "数据导入成功!";
			} else {
				message = "未知的服务状态码! ";
			}
		}
		return message;
	}
}
