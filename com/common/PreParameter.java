package com.common;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PreParameter {
	static Logger log = Logger.getLogger(PreParameter.class);
	private ArrayList ParameterArrayList = null;

	public PreParameter() {
		ParameterArrayList = new ArrayList();
	}

	public void register(int sqlType, String szValue) {
		ParameterArrayList.add("" + sqlType);
		try {
			ParameterArrayList.add(szValue);
		} catch (Exception ex) {
			ParameterArrayList.add("");
			log.info("注册预处理参数时转换字节流失败");
		}
	}

	public int getCount() {
		if (ParameterArrayList.size() > 0)
			return ParameterArrayList.size() / 2;
		else
			return 0;
	}

	public int getSqlType(int index) {
		int sqlType = Integer.parseInt((String) ParameterArrayList
				.get((index - 1) * 2));
		return sqlType;
	}

	public String getValue(int index) {
		String szValue = "";
		try {
			if (ParameterArrayList.get((index - 1) * 2 + 1) != null) {
				szValue = ParameterArrayList.get((index - 1) * 2 + 1)
						.toString();
			}
		} catch (Exception ex) {
			log.debug("获取参数值失败，下标：" + index + " 原因：" + ex.getMessage());
		}
		return szValue;
	}

	public void setValue(int index, String szValue) {
		try {
			ParameterArrayList.set((index - 1) * 2 + 1, szValue);
		} catch (Exception ex) {
			log.info("设置参数值失败，下标：" + index + " 原因：" + ex.getMessage());
		}
	}

	public void clear() {
		ParameterArrayList.clear();
	}

	public String toString() {
		return ("Parameter Class Version 2.1 By hugj 2005.04");
	}

}
