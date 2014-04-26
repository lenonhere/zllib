package com.common.query;

import com.qmx.dateutils.DateUtils;

public class DateTagCriterion extends Criterion {
    public DateTagCriterion() {}

    private static Object[] innerStaticArgs = new Object[2];

    static {
        innerStaticArgs[0] = "date";
        innerStaticArgs[1] = "0";
    }

    public void resolve(Object[] staticArgs, Object[] dynaArgs) {

        Object[] remedyArgs = remedyStaticArguments(staticArgs, innerStaticArgs);
        int offset = Integer.parseInt(remedyArgs[1].toString());
        key = remedyArgs[0].toString();
        value = DateUtils.getDate(offset);
        name  = key;
        property = value;
    }
}
