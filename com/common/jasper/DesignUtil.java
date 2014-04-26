package com.common.jasper;

import org.apache.commons.beanutils.DynaBean;
import java.util.List;
import org.apache.commons.beanutils.DynaProperty;
import java.util.ArrayList;
import java.util.Map;

public class DesignUtil {

    /**
     * 计算captions中每个字段指定的宽度，根据总长度调整各字段的实际宽度。
     * 如果总长度小于columnWidth，将每列扩大为columnWidth / total倍，
     * 如果总长度大于columnWidth，且columnWidth<columnHeight，则将每
     * 列调整为columnHeight / total倍。否则将每列调整为
     * columnWidth / total倍
     * 返回的数组为调整后每列的长度，最后一个整数是原始字段的总长度
     */
    public static int[] calculateFieldWidths(List captions, int columnWidth,
                                                int columnHeight) {

        if(captions == null) {
            return new int[0];
        }

        //最后一位用于保存原始的总长度
        int[] widths = new int[captions.size() + 1];

        int total = 0;

        for(int i=0; i<widths.length-1; i++) {
            DynaBean bean = (DynaBean)captions.get(i);
            Object obj = bean.get("width");

            if(obj == null) {
                widths[i] = 0;
            } else if(obj instanceof Integer) {
                widths[i] = ((Integer)(obj)).intValue();
            } else {
                widths[i] = Integer.parseInt(obj.toString());
            }

            total += widths[i];
        }

        widths[widths.length-1] = total;

        //总长度为0或columnWidth刚好能够容纳所有字段
        if(total == 0 || total == columnWidth) {
            return widths;
        }

        //根据原始总长度、columnWidth和columnHeight之间的关系
        //确定调整字段宽度的比例系数
        int availWidth =
            (columnWidth >= columnHeight) ? columnWidth : columnHeight;

        if(total < columnWidth) {
            availWidth = columnWidth;
        }
        double factor = availWidth / (double)total;

        total = 0;
        //调整字段宽度
        for(int i=0; i<widths.length-1; i++) {
            if(widths[i] == 0) {
                continue;
            }

            widths[i] = (int)(widths[i] * factor);
            total += widths[i];
        }
        widths[widths.length-1] = total;

        return widths;
    }

    /**
     * 返回beans的属性数组
     */
    public static DynaProperty[] getDynaProperties(List beans) {
        if (beans == null || beans.size() < 1) {
            return new DynaProperty[0];
        }

        return ((DynaBean) beans.get(0)).getDynaClass().getDynaProperties();
    }

    public static Class getValueClass(List beans, String name) {

        if (beans == null || beans.size() < 1 || name == null) {
            return String.class;
        }

        Class clazz = String.class;
        DynaProperty[] properties = getDynaProperties(beans);

        for(int i=0; i<properties.length; i++) {
           if(name.equalsIgnoreCase(properties[i].getName())) {
               clazz = properties[i].getType();
               break;
           }
        }
        return clazz;
    }

    public static List filter(List captions, List ignoreFields) {
        if(captions == null || captions.size() == 0 || ignoreFields == null) {
            return captions;
        }
        List newCaptions = new ArrayList();
        for(int i=0; i<captions.size(); i++) {
            DynaBean bean = (DynaBean)captions.get(i);
            if(! ignoreFields.contains(bean.get("property"))) {
                newCaptions.add(bean);
            }
        }
        return newCaptions;
    }

    /**
     * 返回beans的属性数组
     */
    public static String[] getPropertyNames(List captions) {
        if (captions == null || captions.size() == 0) {
            return new String[0];
        }

        String[] names = new String[captions.size()];
        for(int i=0; i<captions.size(); i++) {
            names[i] = wrapProperty((String)((DynaBean)captions.get(i)).get("property"));
        }

        return names;
    }

    public static String wrapProperty(String property) {
        if(property == null || "".equals(property)) {
            return "";
        }
        return FIELD_PREFIX + property.replaceAll(MINUS, DOLLAR).replaceAll(AT, SUBAT);
    }

    public static String dewrapProperty(String property) {
        if(property == null || "".equals(property)) {
            return "";
        }
        return property.replaceAll(SUBAT, AT).replaceAll(DOLLAR, MINUS).substring(FIELD_PREFIX.length());
    }

    public static final String MINUS = "-";
    public static final String DOLLAR = "UoU";
    public static final String FIELD_PREFIX = "f";
    public static final String AT = "@";
    public static final String SUBAT = "ZMZ";

    /*---------------------------------------------------------
     * 报表日期和静态文本设置
     *---------------------------------------------------------*/

    public static String makeYearHalf_sub(String year, String half) {
        String halfName =
            ("0".equals(half) ? "上半年" : ("1".equals(half) ? "下半年" : "全年"));
        return year + halfName;
    }

    public static String makeYearHalf(String year, String half) {
        return "统计年份:" + makeYearHalf_sub(year, half);
    }

    public static String makeYearHalf(String startYear, String startHalf,
                                      String endYear, String endHalf) {
        if(startYear.equals(endYear) && startHalf.equals(endHalf)) {
            return makeYearHalf(startYear, startHalf);
        }
        return makeYearHalf(startYear, startHalf) + "~" + makeYearHalf_sub(endYear, endHalf);
    }

    public static String makeYearMonth_sub(String year, String month) {
            return year + "年" + month + "月";
    }

    public static String makeYearMonth(String year, String month) {
        return "统计月份:" + makeYearMonth_sub(year, month);
    }

    public static String makeYearMonth(String startYear, String startMonth,
                                      String endYear, String endMonth) {
        if(startYear.equals(endYear) && startMonth.equals(endMonth)) {
            return makeYearMonth(startYear, startMonth);
        }
        return makeYearMonth(startYear, startMonth) + "~" + makeYearMonth_sub(endYear, endMonth);
    }

    public static String makeDate(String date) {
        return "统计日期:" + date;
    }

    public static String makeDateNonTitle(String date){
    	return date;
    }
    public static String makeNameCompany(String companyName) {
        return "公司:" + companyName;
    }

    public static String makeNameDistrictOnly(String districtName) {
        return "地区:" + districtName;
    }

    public static String makeNameDistrict(String districtName) {
        return "地区/公司:" + districtName;
    }

    public static String makeNameTobacco(String tobaccoName) {
        return "卷烟:" + tobaccoName;
    }

    public static String makeNameUnit(String unitName) {
        return "计量单位:" + unitName;
    }

    public static String makeDate(String startDate, String endDate) {
        if(startDate.equals(endDate)) {
            return makeDate(startDate);
        }
        return makeDate(startDate) + "~" + endDate;
    }

    public static String makeDateNoTitle(String startDate, String endDate) {
        if(startDate.equals(endDate)) {
            return makeDateNonTitle(startDate);
        }
        return makeDateNonTitle(startDate) + "~" + endDate;
    }

    public static void setLeftText(Map params, String length, String text) {
        params.put("position_left_text", text);
        params.put("position_left", length);
    }

    public static void setRightText(Map params, String length, String text) {
        params.put("position_right_text", text);
        params.put("position_right", length);
    }

    public static void setCenterText(Map params, String length, String text) {
        params.put("position_center_text", text);
        params.put("position_center", length);
    }

    public static void setLeftTextNull(Map params) {
        params.put("position_left_text", "");
        params.put("position_left", "0");
    }

    public static void setRightTextNull(Map params) {
        params.put("position_right_text", "");
        params.put("position_right", "0");
    }

    public static void setCenterTextNull(Map params) {
        params.put("position_center_text", "");
        params.put("position_center", "0");
    }

}
