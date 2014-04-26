package com.common.query;

public abstract class Criterion {

    protected String reference;

    protected String key;

    protected Object value;

    protected String name;

    protected Object property;

    public Criterion() {}

    void setReference(String reference) {
        this.reference = reference;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Object getProperty() {
        return property;
    }

    public abstract void resolve(Object[] staticArgs, Object[] dynaArgs);

    protected Object[] remedyStaticArguments(Object[] staticArgs, Object[] innerStaticArgs) {

        if(staticArgs == null || staticArgs.length == 0) {
            return innerStaticArgs;
        }

        Object[] args = new Object[innerStaticArgs.length];
        int n = staticArgs.length;
        for(int i=0; i<innerStaticArgs.length; i++) {
            if(i < n && staticArgs[i] != null) {
                args[i] = staticArgs[i];
            } else {
                args[i] = innerStaticArgs[i];
            }
        }
        return args;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[Criterion >>> key=");
        buffer.append(key);
        buffer.append(", value=");
        buffer.append(value);
        buffer.append(", name=");
        buffer.append(name);
        buffer.append(", property=");
        buffer.append(property);
        buffer.append(", reference=");
        buffer.append(reference);
        buffer.append("]");
        return buffer.toString();
    }
}
