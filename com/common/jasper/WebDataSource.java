package com.common.jasper;

import java.util.Collection;
import java.util.Iterator;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;

import org.apache.commons.beanutils.PropertyUtils;

public class WebDataSource
    extends JRAbstractBeanDataSource {

    private Collection data = null;
    private Iterator iterator = null;
    private Object currentBean = null;

    public WebDataSource(Collection beanCollection) {
        this(beanCollection, true);
    }

    public WebDataSource(Collection beanCollection,
                         boolean isUseFieldDescription) {
        super(isUseFieldDescription);

        this.data = beanCollection;

        if (this.data != null) {
            this.iterator = this.data.iterator();
        }
    }

    public boolean next() {
        boolean hasNext = false;

        if (this.iterator != null) {
            hasNext = this.iterator.hasNext();

            if (hasNext) {
                this.currentBean = this.iterator.next();
            }
        }

        return hasNext;
    }

    public Object getFieldValue(JRField field) throws JRException {
        Object value = null;

        if (currentBean != null) {
            String propertyName = DesignUtil.dewrapProperty(field.getName());

            try {
                value = PropertyUtils.getProperty(currentBean, propertyName);
                if(value != null && String.class.equals(value.getClass())) {
                	value = ((String)value).replaceAll("(<[^<>]*>)|(&nbsp;)", "");
                }
            }
            catch (java.lang.IllegalAccessException e) {
                throw new JRException(
                    "Error retrieving field value from bean : " + propertyName,
                    e);
            }
            catch (java.lang.reflect.InvocationTargetException e) {
                throw new JRException(
                    "Error retrieving field value from bean : " + propertyName,
                    e);
            }
            catch (java.lang.NoSuchMethodException e) {
                throw new JRException(
                    "Error retrieving field value from bean : " + propertyName,
                    e);
            }
        }

        return value;
    }

    public void moveFirst() {
        if (this.data != null) {
            this.iterator = this.data.iterator();
        }
    }
}
