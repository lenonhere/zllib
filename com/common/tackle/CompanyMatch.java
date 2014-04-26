package com.common.tackle;

import java.util.HashMap;
import java.util.Map;

import com.common.util.PropertiesUtil;

/**
 * 匹配省内商业公司，控制页面上Grid中TextTag的显示与否 用于控制75家商业公司，每家公司由其ID标志（924-1003）
 */
public class CompanyMatch {

	public static Map generateMatches(String companies) {
		Map matches = new HashMap();

		if (companies == null || "".equals(companies)) {
			return matches;
		}

		companies = companies.replace(';', ',');

		String[] tokens = PropertiesUtil.split(companies, ",");

		for (int i = 0; i < tokens.length; i++) {
			matches.put(tokens[i], NOT_NULL);
		}
		return matches;
	}

	protected static final String NOT_NULL = "this_company_is_in_map";
}
