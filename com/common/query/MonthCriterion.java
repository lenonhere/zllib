package com.common.query;

import com.qmx.dateutils.DateUtils;
import com.zl.util.OptionUtils;

public class MonthCriterion extends Criterion {
    public MonthCriterion() {}

    private static Object[] innerStaticArgs = new Object[3];

    static {
        innerStaticArgs[0] = "month.list";
        innerStaticArgs[1] = "month";
        innerStaticArgs[2] = "0";
    }

    public void resolve(Object[] staticArgs, Object[] dynaArgs) {

        Object[] remedyArgs = remedyStaticArguments(staticArgs, innerStaticArgs);
        key = remedyArgs[0].toString();
        value = OptionUtils.getOptions("month_list");
        name = remedyArgs[1].toString();
        int offset = Integer.parseInt(remedyArgs[2].toString());
        property = DateUtils.getMonth(offset).substring(4, 6);
    }
}
