package com.common.query;

public class TextCriterion extends Criterion {

    private static Object[] innerStaticArgs = new Object[2];

    static {
        innerStaticArgs[0] = "text_tag";
        innerStaticArgs[1] = "";
    }

    public void resolve(Object[] staticArgs, Object[] dynaArgs) {

        Object[] remedyArgs = remedyStaticArguments(staticArgs, innerStaticArgs);

        key = remedyArgs[0].toString();
        value = remedyArgs[1].toString();
        name = key;
        property = value;
    }
}
