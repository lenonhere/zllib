package com.common.query;

import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;
import java.util.Properties;
import java.util.Enumeration;

public class CriterionFactory {

    private static Map criterionMap = new HashMap();

    static {
        InputStream is = CriterionFactory.class.getResourceAsStream(
            "/criterion.properties");
        try {
            Properties properties = new Properties();
            properties.load(is);
            loadClasses(properties);
        }
        catch (Exception e) {
            throw new RuntimeException(
                "can't load report properties: /criterion.properties");
        }
    }

    private static void loadClasses(Properties properties) throws
        ClassNotFoundException {
        Enumeration propNames = properties.propertyNames();

        while (propNames.hasMoreElements()) {
            String key = (String) propNames.nextElement();
            Class clazz = Class.forName(properties.getProperty(key));
            criterionMap.put(key, clazz);
        }
    }

    public static Criterion getCriterion(String name) {
        try {
            Criterion criterion =
                (Criterion) ( (Class) criterionMap.get(name)).newInstance();
            criterion.setReference(name);
            return criterion;
        }
        catch (IllegalAccessException ex) {
            return null;
        }
        catch (InstantiationException ex) {
            return null;
        }
    }
}
