package com.common;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;

import com.zl.base.core.db.Executer;
import com.zl.base.core.db.SqlReturn;
import common.Logger;

public class IpAddressFormater {

	private static final Logger logger = Logger
			.getLogger(IpAddressFormater.class);

	private double formatedIp;

	/**
	 * 省份
	 */
	private String province = "";
	/**
	 * 城市
	 */
	private String city = "";
	/**
	 * ISP服务商
	 */
	private String isp = "";

	public IpAddressFormater(String ip) {
		try {
			this.formatedIp = ipToNumber(ip);
			SqlReturn sr = Executer
					.getInstance()
					.ExecSeletSQL(
							"select province,city,isp from g_ipinfo where "
									+ this.formatedIp
									+ " between start_ip and end_ip fetch first 1 rows only");
			List rt = sr.getResultSet();
			if (rt != null && !rt.isEmpty()) {
				DynaBean bean = (DynaBean) rt.get(0);
				this.province = (String) bean.get("province");
				this.city = (String) bean.get("city");
				this.isp = (String) bean.get("isp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ip地址错误(IP:" + ip + "):", e);
		}
	}

	/**
	 * 获取省份
	 *
	 * @return
	 */
	public String getProvince() {
		return this.province;
	}

	/**
	 * 获取城市
	 *
	 * @return
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * ISP服务商
	 *
	 * @return
	 */
	public String getIsp() {
		return this.isp;
	}

	public double getFormatedIp() {
		return this.formatedIp;
	}

	public String getFullAddress() {
		if (this.province.equals(""))
			return "未知";
		else
			return this.province + this.city + "(" + this.isp + ")";
	}

	public static double ipToNumber(String ipAddress) {
		String[] startIP = ipAddress.split("[.]");
		// System.out.println(startIP[0]);
		String a1 = startIP[0];
		String a2 = startIP[1];
		String a3 = startIP[2];
		String a4 = startIP[3];
		int U1 = Integer.parseInt(a1);
		int U2 = Integer.parseInt(a2);
		int U3 = Integer.parseInt(a3);
		int U4 = Integer.parseInt(a4);

		java.lang.Double U = new java.lang.Double(U1 << 24);
		// System.out.println(""+U1);
		// System.out.println(""+U);
		U += new java.lang.Double(U2 << 16);
		U += new java.lang.Double(U3 << 8);
		U += new java.lang.Double(U4);
		// U += Math.pow(2,32);
		if (U < 0) {
			U += new java.lang.Double(Math.pow(2, 32));
		}
		return U.doubleValue();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double i = ipToNumber("221.232.137.5");
		System.out.println(i + "");
	}

}
