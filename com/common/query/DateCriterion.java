package com.common.query;

import com.qmx.dateutils.DateUtils;
import com.zl.util.MethodFactory;
import com.zl.util.OptionUtils;

public class DateCriterion extends Criterion {

    public DateCriterion() {}

    private static Object[] innerStaticArgs = new Object[5];

    static {
        innerStaticArgs[0] = "date.list";
        innerStaticArgs[1] = "-15";
        innerStaticArgs[2] = "15";
        innerStaticArgs[3] = "date";
        innerStaticArgs[4] = "0";
    }

    public void resolve(Object[] staticArgs, Object[] dynaArgs) {
        Object[] remedyArgs = remedyStaticArguments(staticArgs, innerStaticArgs);

        int pre  = Integer.parseInt(remedyArgs[1].toString());
        int post = Integer.parseInt(remedyArgs[2].toString());

        key = remedyArgs[0].toString();
        value = OptionUtils.getDateList(pre, post);

        name = remedyArgs[3].toString();
        int offset = Integer.parseInt(remedyArgs[4].toString());
        property = DateUtils.getDate(offset);
    }
}
