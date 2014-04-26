package com.zl.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author hgc
 *
 */
public class MyBeanUtil {

	private static final Log log = LogFactory.getLog(MyBeanUtil.class);

	public MyBeanUtil() {
		super();
	}

	public static void copyFormbeanFromDBbean(Object formBean, Object dbBean) {
		HashMap mp;
		try {
			mp = (HashMap) BeanUtils.describe(formBean);
			Object[] fa = mp.keySet().toArray();
			int asize = (fa == null) ? 0 : fa.length;
			BasicDynaBean dbbean = (BasicDynaBean) dbBean;
			HashMap dm = (HashMap) PropertyUtils.describe(dbbean);
			// DynaProperty[] p = dbBean.getDynaClass().getDynaProperties();
			for (int i = 0; i < asize; i++) {
				if (dm.containsKey(((String) fa[i]).toLowerCase())) {
					BeanUtils.setProperty(formBean, (String) fa[i],
							dbbean.get(((String) fa[i]).toLowerCase()));
				}
			}
		} catch (IllegalAccessException e) {
			log.error(e);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			log.error(e);
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	public static String describeRs(ResultSet rs) {
		StringBuffer sb = new StringBuffer();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			int i = 0;
			sb.append("rsmd.getColumnCount()=" + numberOfColumns + "\n  ");
			// System.out.println(sb);
			for (int j = 1; j <= numberOfColumns; j++) {
				sb.append(rsmd.getColumnName(j) + "\t");
				// System.out.println(sb);
			}
			sb.append("\n");
			while (rs.next()) {
				sb.append(i + ":");
				for (int j = 1; j <= numberOfColumns; j++) {
					sb.append(rs.getString(j) + "\t");
					// System.out.println(sb);
				}
				sb.append("\n");
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 在非grid控件下生成辅助性简单表格：风格于grid相似的普通表格。 该表格代码比较少，无附加功能，不能拖动排序折叠。
	 *
	 * @param captions
	 *            表头数据集
	 * @param results
	 *            表内容数据集
	 * @param isCheck
	 *            false只包含可见列（宽度为0不显示）true包含所有列
	 * @return
	 */
	public static String getCaptionResult(List captions, List results,
			boolean isCheck) {
		StringBuffer sb = new StringBuffer();
		sb.append("<table class=\"mytable\" width=\"100%\" cellspacing=0 style=\"border: 1px solid #000000;\"><tr>");
		int size = captions.size();
		List caps = new ArrayList();
		for (int i = 0; i < size; i++) {
			// caption, width, property, format, fixcol, align,
			// datatype,isreturn
			BasicDynaBean bean = (BasicDynaBean) captions.get(i);
			String width = MethodFactory.getThisString(bean.get("width"));
			String caption = MethodFactory.getThisString(bean.get("caption"));
			String property = MethodFactory.getThisString(bean.get("property"));
			String align = MethodFactory.getThisString(bean.get("align"));
			if (isCheck == false && "0".equalsIgnoreCase(width)) {
				continue;
			}
			sb.append("<td width=\"" + width);
			sb.append("\" align=\"" + align + "\">");
			sb.append(caption + "</td>");
			caps.add(property);
		}
		sb.append("</tr>");
		int sizeR = results.size();
		int sizeC = caps.size();
		for (int i = 0; i < sizeR; i++) {
			// caption, width, property, format, fixcol, align,
			// datatype,isreturn
			sb.append("<tr>");
			BasicDynaBean bean = (BasicDynaBean) results.get(i);
			for (int j = 0; j < sizeC; j++) {
				sb.append("<td>"
						+ MethodFactory.getThisString(bean.get((String) caps
								.get(j))) + "&nbsp;</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		log.debug(sb);
		return sb.toString();
	}

	public static String[][] getTableFromStr(String information) {
		return getTableFromStr(information, ",", ";");
	}

	public static String[][] getTableFromStr(String information, String colC,
			String rowC) {
		String[] rowStrs = information.split(rowC);
		String[][] strss = null;
		int rows = rowStrs.length;
		if (rows == 0)
			return null;
		int cols = 0;
		int tempcols = 0;
		String[] colStrs = rowStrs[0].split(colC);
		cols = colStrs.length;
		strss = new String[rows][cols + 1];
		for (int i = 0; i < rows; i++) {
			colStrs = rowStrs[i].split(colC, cols + 1);
			strss[i] = colStrs;
			// System.out.print(colStrs);
			/*
			 * tempcols = colStrs.length; for(int j=0;j<tempcols;j++ ) {
			 * strss[i][j] = colStrs[j]; }
			 */
		}
		return strss;
	}

	public static String getDbBeanCheckList(String name, String defaultValue,
			List list, boolean mutilChecked) {

		StringBuffer sb = new StringBuffer();
		if (mutilChecked) {
			sb.append("<input type=hidden name=" + name + " value=\""
					+ defaultValue + "\">");
		}
		int size = (list == null) ? 0 : list.size();
		for (int i = 0; i < size; i++) {
			BasicDynaBean bean = (BasicDynaBean) list.get(i);
			String opid = MethodFactory.getThisString(bean.get("id"));
			String opname = MethodFactory.getThisString(bean.get("name"));
			sb.append(getOptionCheck(name, opid, opname, defaultValue,
					mutilChecked));
		}
		return sb.toString();
	}

	/**
	 * 自动生成数组checkbox.例如常用的查询参数. 查询选项:[ 销区(1)田 省份(2)田 公司(3)口
	 * ]对应defaultValue:1,2,
	 *
	 * @param name
	 *            checkbox的名字
	 * @param defaultValue
	 *            默认值通常为""
	 * @param list
	 *            对应的选项,长度与defaultValue字数应相同
	 * @param mutilChecked
	 *            是否是checkbox多选框否则为radio单选框.
	 * @return
	 */
	public static String getOptionCheckList(String name, String defaultValue,
			List list, boolean mutilChecked) {
		if (defaultValue == null)
			defaultValue = "";
		StringBuffer sb = new StringBuffer();
		int size = (list == null) ? 0 : list.size();
		// 是否多选checkbox 单选radio
		if (size == 0) {
			return "";
		} else {
			sb.append("<input width=" + size + " type=hidden name=" + name
					+ " value=\"" + defaultValue + "\">");
		}

		for (int i = 0; i < size; i++) {
			OptionHold oh = (OptionHold) list.get(i);
			String opid = oh.getId();
			String opname = oh.getName();
			sb.append(getOptionCheck(name, opid, opname, defaultValue,
					mutilChecked));
		}
		return sb.toString();
	}

	/**
	 * 自动生成顺序数组checkbox.例如常用的查询参数. 查询选项:[ 销区田 省份田 公司口 ]对应defaultValue:110
	 *
	 * @param name
	 *            checkbox的名字
	 * @param defaultValue
	 *            默认值通常为0000~1111
	 * @param list
	 *            对应的选项,长度与defaultValue字数应相同
	 * @param mutilChecked
	 *            是否是checkbox多选框否则为radio单选框.
	 * @return
	 */
	public static String getOptionCheckOrderList(String name,
			String defaultValue, List list, boolean mutilChecked) {
		if (defaultValue == null)
			defaultValue = "";
		StringBuffer sb = new StringBuffer();
		int size = (list == null) ? 0 : list.size();
		// 是否多选checkbox 单选radio
		if (size == 1) {

			OptionHold oh = (OptionHold) list.get(0);
			return getSigleCheckbox(name, oh.getId(), defaultValue,
					mutilChecked);
		}
		if (mutilChecked) {
			sb.append("<input width=" + size + " type=hidden name=" + name
					+ " value=\"" + defaultValue + "\">");
		}

		for (int i = 0; i < size; i++) {
			OptionHold oh = (OptionHold) list.get(i);
			String opid = "1";// oh.getId();
			String opname = oh.getName();
			String currV = defaultValue.substring(i, i + 1);
			sb.append(getOptionCheck(name, opid, opname, currV, mutilChecked));
		}
		return sb.toString();
	}

	/**
	 * 获取单个checkbox或者radio框。可以默认checked，没有别的用处
	 *
	 * @param name
	 * @param value
	 * @param defaultValue
	 * @param mutilChecked
	 * @return
	 */
	public static String getSigleCheckbox(String name, String value,
			String defaultValue, boolean mutilChecked) {

		StringBuffer sb = new StringBuffer();
		if (mutilChecked) {
			sb.append(" <input type=checkbox");
		} else {
			sb.append(" <input type=radio");
		}
		sb.append(" name=\"" + name + "\"");
		sb.append(" value=\"" + value + "\"");
		if (defaultValue.equals(value))
			sb.append(" checked");
		sb.append(">");
		return sb.toString();
	}

	/**
	 * 生成单个checkbox/radio对象。
	 *
	 * @param name
	 *            对象控件名称
	 * @param opid
	 *            值
	 * @param opname
	 *            名称
	 * @param defaultValue
	 *            缺省值:当defaultValue = opid是，被选择
	 * @param mutilChecked
	 *            是否可以多选：ture-checkbox false-radio
	 * @return
	 */
	private static StringBuffer getOptionCheck(String name, String opid,
			String opname, String defaultValue, boolean mutilChecked) {
		StringBuffer sb = new StringBuffer();
		if (mutilChecked) {
			sb.append(" <input type=checkbox");
			sb.append(" name=_" + name);
		} else {
			sb.append(" <input type=radio");
			sb.append(" name=_" + name);
		}
		sb.append(" id=" + name + opid);
		sb.append(" value=\"" + opid + "\"");
		if (defaultValue != null && defaultValue.trim().length() > 0) {
			if (mutilChecked
					&& ("," + defaultValue + ",").indexOf("," + opid + ",") >= 0)
				sb.append(" checked");
			else if (!mutilChecked && defaultValue.equals(opid))
				sb.append(" checked");
		}
		sb.append(" onclick=\"setCheckValue(this)\">");
		sb.append(opname);
		sb.append(" ");
		return sb;
	}

	public static ArrayList getBeanResultList(List detail, String idname,
			String codename, String name) {
		ArrayList list = new ArrayList();
		for (int i = 0; detail != null && i < detail.size(); i++) {
			BasicDynaBean bean = (BasicDynaBean) detail.get(i);
			String opid = MethodFactory.getThisString(bean.get(idname));
			String opcode = "";
			if (codename.trim().length() > 0)
				opcode = MethodFactory.getThisString(bean.get(codename));
			String opname = MethodFactory.getThisString(bean.get(name));
			list.add((new OptionHold(opid, opname, opcode)));
		}
		return list;
	}

	public static void main(String args[]) {
		String s = "1,2,;,,6;7,8,9;,";
		System.out.println(s);
		getTableFromStr(s);
	}

}
