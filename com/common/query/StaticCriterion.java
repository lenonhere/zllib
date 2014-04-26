package com.common.query;

import com.zl.util.OptionUtils;


public class StaticCriterion extends Criterion {

    public StaticCriterion() {}

    private static Object[] innerStaticArgs = new Object[1];

    static {
        innerStaticArgs[0] = "none.list";
    }

    public void resolve(Object[] staticArgs, Object[] dynaArgs) {

        Object[] remedyArgs = remedyStaticArguments(staticArgs, innerStaticArgs);
        key = remedyArgs[0].toString();
        value = OptionUtils.getOptions(this.reference);

        if(staticArgs != null && staticArgs.length > 1) {
            name = (String)staticArgs[1];
            if(staticArgs.length > 2) {
                property = (String)staticArgs[2];
            }
        }
    }
}
