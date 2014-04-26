package com.common.query;

import com.zl.base.core.db.CallHelper;

public class DynaCriterion extends Criterion {

    public DynaCriterion() {}

    private static Object[] innerStaticArgs = new Object[3];

    static {
        innerStaticArgs[0] = "none.default";
        innerStaticArgs[1] = "<blank procedure>";
        innerStaticArgs[2] = "results";
    }

    public void resolve(Object[] staticArgs, Object[] dynaArgs) {
        Object[] remedyArgs = remedyStaticArguments(staticArgs, innerStaticArgs);

        key = remedyArgs[0].toString();

        CallHelper helper = new CallHelper(remedyArgs[1].toString());
        if(dynaArgs != null && dynaArgs.length > 0) {
            for(int i=1; i<dynaArgs.length; i+=2) {
                helper.setParam(dynaArgs[i-1], dynaArgs[i]);
            }
        }

        helper.execute();
        value = helper.getResult(remedyArgs[2].toString());
    }
}
