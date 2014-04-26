package com.common.query;

import com.qmx.dateutils.DateUtils;
import com.zl.util.OptionUtils;

public class YearCriterion extends Criterion {

	public YearCriterion() {
	}

	private static Object[] innerStaticArgs = new Object[5];

	static {
		innerStaticArgs[0] = "year.list";
		innerStaticArgs[1] = "-5";
		innerStaticArgs[2] = "5";
		innerStaticArgs[3] = "year";
		innerStaticArgs[4] = "0";
	}

	public void resolve(Object[] staticArgs, Object[] dynaArgs) {

		Object[] remedyArgs = remedyStaticArguments(staticArgs, innerStaticArgs);

		int pre = Integer.parseInt(remedyArgs[1].toString());
		int post = Integer.parseInt(remedyArgs[2].toString());

		key = remedyArgs[0].toString();
		value = OptionUtils.getYearList(pre, post);

		name = remedyArgs[3].toString();
		String year = DateUtils.getNextYearCurrent().substring(0, 4);
		int offset = Integer.parseInt(remedyArgs[4].toString()) - 1;
		property = String.valueOf(Integer.parseInt(year) + offset);
	}
}
