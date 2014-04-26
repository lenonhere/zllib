package com.zl.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.common.util.PropertiesUtil;
import com.zl.base.core.db.Executer;
import com.zl.base.core.db.SqlReturn;

/**
 * @author Administrator
 *
 */
public class OptionUtils {
	private static final Logger log = Logger.getLogger(OptionUtils.class);
	private static ArrayList<OptionHold> optionList = null;
	private static final int BEGINYEAR = 2007;

	private static OptionUtils optionUtils = new OptionUtils();
	private static ArrayList<OptionHold> list = null;

	private static Map<String, List<OptionHold>> optionsMap = new HashMap<String, List<OptionHold>>();
	private static final List<OptionHold> EMPTY_OPTIONS = new ArrayList<OptionHold>();
	static {
		Properties properties = PropertiesUtil.load("/options.properties");
		makeOptions(properties);
	}

	public static OptionUtils getInstance() {
		if (optionUtils == null) {
			optionUtils = new OptionUtils();
		}
		return optionUtils;
	}

	// OptionManager.java Begin
	private static void makeOptions(Properties properties) {

		Enumeration<?> propNames = properties.propertyNames();

		while (propNames.hasMoreElements()) {
			String key = (String) propNames.nextElement();

			if (key.endsWith(".ids")) {

				String ids = properties.getProperty(key);
				String[] idTokens = PropertiesUtil.split(ids);

				if (idTokens.length == 0) {
					continue;
				}

				String subkey = PropertiesUtil.subkey(key, 0, -2);

				if (PropertiesUtil.isEmpty(subkey)) {
					continue;
				}

				String prefix_name = subkey + ".name.";

				List<OptionHold> optionList = new ArrayList<OptionHold>();

				for (int j = 0; j < idTokens.length; j++) {

					String origName = properties.getProperty(prefix_name
							+ idTokens[j]);

					if (PropertiesUtil.isEmpty(origName)) {
						continue;
					}

					String name = PropertiesUtil.encode(origName);
					String id = PropertiesUtil.encode(idTokens[j]);
					optionList.add(new OptionHold(id, name));
				}
				if (optionList.size() > 0) {
					optionsMap.put(subkey, optionList);
				}
			}
		}
	}

	public static List<OptionHold> getOptions(String name) {
		List<OptionHold> options = optionsMap.get(name);
		return options == null ? EMPTY_OPTIONS : options;
	}

	// OptionManager.java End

	public static String getSystemDate() {
		String time = "";
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		time = formatter.format(date).trim();
		return time;
	}

	public static String getSystemDate(int Day) {
		String time = "";
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, Day);
		Date date = calendar.getTime();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		time = formatter.format(date).trim();
		return time;
	}

	public static ArrayList<OptionHold> getIsOrNot(boolean addAll) {
		ArrayList<OptionHold> list = new ArrayList<OptionHold>();
		if (addAll)
			list.add(new OptionHold("-1", "全部", "", ""));
		list.add(new OptionHold("1", "是", "", ""));
		list.add(new OptionHold("0", "否", "", ""));
		return list;
	}

	public static ArrayList<OptionHold> getDataYearList(int start, int end) {
		optionList = new ArrayList<OptionHold>();
		for (int i = start; i <= end; i++) {
			optionList.add(new OptionHold(Integer.toString(i), Integer
					.toString(i), "", ""));
		}
		return optionList;
	}

	public static ArrayList<OptionHold> getDataYearList(int suf) {
		int endYear = Integer.parseInt(getSystemDate().substring(0, 4)) + suf;
		return getDataYearList(BEGINYEAR, endYear);
	}

	public static ArrayList<OptionHold> getYearList(int pre, int suf) {
		optionList = new ArrayList<OptionHold>();

		int beginYear = Integer.parseInt(getSystemDate().substring(0, 4)) + pre;

		for (int i = 0; i <= suf - pre; i++) {
			optionList.add(new OptionHold(Integer.toString(beginYear), Integer
					.toString(beginYear), "", ""));
			beginYear++;
		}
		return optionList;
	}

	public static ArrayList<OptionHold> getNumberList(int start, int end) {
		return getNumberList(start, end, 1, 0);
	}

	public static ArrayList<OptionHold> getNumberList(int start, int end,
			int step) {
		return getNumberList(start, end, step, 0);
	}

	public static ArrayList<OptionHold> getNumberList(int start, int end,
			int step, int width) {
		optionList = new ArrayList<OptionHold>();
		for (int i = start; i <= end; i = i + step) {
			String strI = Integer.toString(i);
			while (width > 0 && strI.length() < width) {
				strI = "0" + strI;
			}
			optionList.add(new OptionHold(strI, strI, "", ""));
		}

		return optionList;
	}

	public static ArrayList<OptionHold> getYearList(int pre, int suf,
			int startYear) {
		optionList = new ArrayList<OptionHold>();

		int beginYear = Integer.parseInt(getSystemDate().substring(0, 4)) + pre;

		if (beginYear > startYear) {
			pre -= beginYear - startYear;
			beginYear = startYear;
		}

		for (int i = 0; i <= suf - pre; i++) {
			optionList.add(new OptionHold(Integer.toString(beginYear), Integer
					.toString(beginYear), "", ""));
			beginYear++;
		}

		return optionList;
	}

	public static ArrayList<OptionHold> getMonthList(int pre, int suf) {
		optionList = new ArrayList<OptionHold>();
		for (int i = pre; i <= suf; i++) {
			optionList.add(new OptionHold((i < 10 ? ("0" + i) : "" + i),
					(i < 10 ? ("0" + i) : "" + i), "", ""));
		}
		return optionList;
	}

	/**
	 * 获取日期下拉选项
	 *
	 * @param pre
	 * @param suf
	 * @return
	 */
	public static ArrayList<OptionHold> getDateList(int pre, int suf) {
		list = new ArrayList<OptionHold>();

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, pre);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i <= suf - pre; i++) {
			String date = formatter.format(calendar.getTime()).trim();
			list.add(new OptionHold(date, date));
			calendar.add(Calendar.DATE, 1);
		}
		return list;
	}

	/**
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<OptionHold> getArrayListBy(String sql)
			throws Exception {
		return getArrayListBy(sql, false);
	}

	/**
	 * @param sql
	 * @param showGroup
	 *            值为true时将group的值赋给group,值为false时将group的值赋给value
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<OptionHold> getArrayListBy(String sql,
			boolean showGroup) throws Exception {
		optionList = new ArrayList<OptionHold>();
		ResultSet rs = Executer.getInstance().ExecSeletSQL2(sql);
		if (rs.next()) {
			optionList = getArrayListBy(rs, showGroup);
		}
		return optionList;
	}

	/**
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<OptionHold> getArrayListBy(ResultSet rs)
			throws Exception {
		return getArrayListBy(rs, false);
	}

	/**
	 * @param rs
	 * @param showGroup
	 *            值为true时将group的值赋给group,值为false时将group的值赋给value
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<OptionHold> getArrayListBy(ResultSet rs,
			boolean showGroup) throws Exception {
		optionList = new ArrayList<OptionHold>();
		OptionHold optionHold = null;
		if (rs != null) {
			Object id = "";
			Object name = "";
			Object value = "";
			Object value1 = "";
			Object value2 = "";
			Object group = "";
			ArrayList<Object> rowList = getColumnsNameValueType(rs);
			for (int i = 0; i < rowList.size(); i++) {
				ArrayList<Object[]> colList = (ArrayList<Object[]>) rowList
						.get(i);
				int colCount = colList.size();

				switch (colCount) {
				case 2:
					id = colList.get(0)[1];
					name = colList.get(1)[1];
					optionHold = new OptionHold(id.toString(), name.toString());
					break;
				case 3:
					id = colList.get(0)[1];
					name = colList.get(1)[1];
					value = colList.get(2)[1];
					optionHold = new OptionHold(id.toString(), name.toString(),
							value.toString());
					break;
				case 4:
					id = colList.get(0)[1];
					name = colList.get(1)[1];
					value1 = colList.get(2)[1];
					value2 = colList.get(3)[1];
					optionHold = new OptionHold(id.toString(), name.toString(),
							value1.toString(), value2.toString());
					break;

				case 5:
					id = colList.get(0)[1];
					name = colList.get(1)[1];
					value1 = colList.get(2)[1];
					value2 = colList.get(3)[1];
					group = colList.get(4)[1];
					optionHold = new OptionHold(id.toString(), name.toString(),
							value1.toString(), value2.toString(),
							group.toString(), showGroup);
					break;

				case 6:
					id = colList.get(0)[1];
					name = colList.get(1)[1];
					value = colList.get(2)[1];
					value1 = colList.get(3)[1];
					value2 = colList.get(4)[1];
					group = colList.get(5)[1];
					optionHold = new OptionHold(id.toString(), name.toString(),
							value.toString(), value1.toString(),
							value2.toString(), group.toString());
					break;
				default:
					log.debug("暂时只支持2~6个参数...");
					break;
				}
				System.out.println(optionHold.toString());
				optionList.add(optionHold);
			}
		}
		return optionList;
	}

	/**
	 * @param rs
	 * @return
	 */
	public static ArrayList<Object> getColumnsNameValueType(ResultSet rs) {
		ArrayList<Object> rowList = null;
		try {
			if (rs != null) {
				rowList = new ArrayList<Object>();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();

				ArrayList<Object[]> colList = null;
				while (rs.next()) {
					colList = new ArrayList<Object[]>();
					Object[] objs = null;
					for (int i = 1; i <= columnCount; i++) {
						String label = rsmd.getColumnLabel(i);
						Object value = rs.getObject(i);
						Object type = rsmd.getColumnTypeName(i);

						objs = new Object[3];
						objs[0] = label;
						objs[1] = value;
						objs[2] = type;
						colList.add(objs);
					}
					rowList.add(colList);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e);
		}
		return rowList;
	}

	// OptionUtil.java Begin

	/**
	 * 获取年份下拉选项，年份从起始年份到当前年份
	 *
	 *
	 * @param startYear
	 *            起始年份
	 * @return
	 */
	public static ArrayList<OptionHold> getYearList(int startYear) {
		list = new ArrayList<OptionHold>();

		int endYear = Integer.parseInt(getSystemDate().substring(0, 4));
		for (int i = endYear; i >= startYear; i--) {
			list.add(new OptionHold(Integer.toString(i), Integer.toString(i)));
		}
		return list;
	}

	/**
	 * 获取年份下拉选项，年份从起始年份到当前年下一年
	 *
	 *
	 * @param startYear
	 *            起始年份
	 * @return
	 */
	public static ArrayList<OptionHold> getYearListToNextYear(int startYear) {
		list = new ArrayList<OptionHold>();

		int endYear = Integer.parseInt(getSystemDate().substring(0, 4)) + 1;
		for (int i = endYear; i >= startYear; i--) {
			list.add(new OptionHold(Integer.toString(i), Integer.toString(i)));
		}
		return list;
	}

	public static ArrayList<OptionHold> getHalfList2() {
		list = new ArrayList<OptionHold>();
		list.add(new OptionHold("0", "上半年")); // 请使用"0"，不要修改
		list.add(new OptionHold("1", "下半年")); // 请使用"1"，不要修改
		return list;
	}

	public static ArrayList<OptionHold> getUnitList() {
		list = new ArrayList<OptionHold>();

		list.add(new OptionHold("1", "箱"));
		list.add(new OptionHold("5", "万支"));
		list.add(new OptionHold("250", "条"));

		return list;
	}

	public static ArrayList<OptionHold> getquerytype() {
		list = new ArrayList<OptionHold>();
		list.add(new OptionHold("1", "调拨指令"));
		list.add(new OptionHold("2", "冲红指令"));
		return list;
	}

	public static ArrayList<OptionHold> gettranstype() {
		list = new ArrayList<OptionHold>();
		list.add(new OptionHold("1", "全部"));
		list.add(new OptionHold("2", "航空"));
		list.add(new OptionHold("3", "汽运"));
		return list;
	}

	public static ArrayList<OptionHold> gettranstype2() {
		list = new ArrayList<OptionHold>();
		list.add(new OptionHold("3", "汽运"));
		list.add(new OptionHold("2", "航空"));
		return list;
	}

	public static ArrayList<OptionHold> getdbtype() {
		list = new ArrayList<OptionHold>();

		list.add(new OptionHold("0", "全部"));
		list.add(new OptionHold("1", "是"));
		list.add(new OptionHold("2", "否"));
		return list;
	}

	public static ArrayList<OptionHold> getthdtype() {
		list = new ArrayList<OptionHold>();

		list.add(new OptionHold("0", "全部"));
		list.add(new OptionHold("1", "是"));
		list.add(new OptionHold("2", "否"));
		return list;
	}

	public static ArrayList<OptionHold> getIsActiveList() {
		list = new ArrayList<OptionHold>();
		list.add(new OptionHold("1", "是"));
		list.add(new OptionHold("0", "否"));
		return list;
	}

	public static ArrayList<OptionHold> getIsActiveAllList() {
		list = new ArrayList<OptionHold>();
		list.add(new OptionHold("1", "是"));
		list.add(new OptionHold("0", "否"));
		list.add(new OptionHold("2", "全部"));
		return list;
	}

	public static ArrayList<OptionHold> getinkeycityList() {
		list = new ArrayList<OptionHold>();
		list.add(new OptionHold("1", "是"));
		list.add(new OptionHold("0", "否"));
		return list;
	}

	public static ArrayList<OptionHold> getFlagList() {
		list = new ArrayList<OptionHold>();
		list.add(new OptionHold("1", "是"));
		list.add(new OptionHold("0", "否"));
		return list;
	}

	public static ArrayList<OptionHold> getIsProvinceList() {
		list = new ArrayList<OptionHold>();

		list.add(new OptionHold("1", "省内"));
		list.add(new OptionHold("0", "省外"));
		list.add(new OptionHold("2", "其它"));

		return list;
	}

	public static String getDateByOffset(String baseDate, int offset) {
		String returnTime;
		Date time = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			time = formatter.parse(baseDate);
		} catch (Exception e) {
			System.out.println("日期格式错误");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + offset);
		Date date = calendar.getTime();
		returnTime = formatter.format(date).trim();
		return returnTime;
	}

	public static ArrayList<OptionHold> getReportStatus() {
		list = new ArrayList<OptionHold>();
		list.add(new OptionHold("2", "未报"));
		list.add(new OptionHold("1", "已报"));
		return list;
	}

	public static ArrayList<OptionHold> getExestates() {
		list = new ArrayList<OptionHold>();

		list.add(new OptionHold("0", "所有"));
		list.add(new OptionHold("1", "未处理"));
		list.add(new OptionHold("2", "已处理"));

		return list;
	}

	// 出库状态
	public static ArrayList<OptionHold> getckstatustype() {
		list = new ArrayList<OptionHold>();
		list.add(new OptionHold("0", "全部"));
		list.add(new OptionHold("1", "有效"));
		list.add(new OptionHold("2", "其它"));
		return list;
	}

	public static ArrayList<OptionHold> getSeatno() {
		list = new ArrayList<OptionHold>();
		list.add(new OptionHold("0", "0"));
		list.add(new OptionHold("1", "1"));
		list.add(new OptionHold("2", "2"));
		list.add(new OptionHold("3", "3"));
		list.add(new OptionHold("4", "4"));
		list.add(new OptionHold("5", "5"));
		list.add(new OptionHold("6", "6"));
		list.add(new OptionHold("7", "7"));
		list.add(new OptionHold("8", "8"));
		list.add(new OptionHold("9", "9"));
		list.add(new OptionHold("10", "10"));
		list.add(new OptionHold("11", "11"));
		list.add(new OptionHold("12", "12"));
		list.add(new OptionHold("13", "13"));
		return list;
	}

	public static List<OptionHold> getPersonTypeList() {
		list = new ArrayList<OptionHold>();
		String sql = "select classid,classname||'('||Comment||')'||classname AS ClassName from g_publiccodes where parentclassid=52 and isactive='1' order by classid ";
		try {
			OptionHold optionHold = null;
			SqlReturn sqlreturn = Executer.getInstance().ExecSeletSQL(sql);
			ArrayList<BasicDynaBean> rs = sqlreturn.getResultSet();
			for (int i = 0, m = rs.size(); i < m; i++) {
				BasicDynaBean bean = (BasicDynaBean) rs.get(i);
				optionHold = new OptionHold(bean.get("classid").toString(),
						bean.get("classname").toString());
				list.add(optionHold);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return list;
	}

	// 本地生产点
	public static List<OptionHold> getLocFactory() {
		list = new ArrayList<OptionHold>();
		String sql = "select whouseid,whousealias from g_whouse order by whouseid";
		try {
			OptionHold optionHold = null;
			SqlReturn sqlreturn = Executer.getInstance().ExecSeletSQL(sql);
			ArrayList<BasicDynaBean> rs = sqlreturn.getResultSet();
			for (int i = 0, m = rs.size(); i < m; i++) {
				BasicDynaBean bean = (BasicDynaBean) rs.get(i);
				optionHold = new OptionHold(bean.get("whouseid").toString(),
						bean.get("whousealias").toString());
				list.add(optionHold);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return list;
	}

	// 联营生产点
	public static List<OptionHold> getConFactory() {
		list = new ArrayList<OptionHold>();
		String sql = "select whouseid,whousealias from g_whousedetail where isoutfty = '1' order by whouseid";
		try {
			OptionHold optionHold = null;
			SqlReturn sqlreturn = Executer.getInstance().ExecSeletSQL(sql);
			ArrayList<BasicDynaBean> rs = sqlreturn.getResultSet();
			for (int i = 0, m = rs.size(); i < m; i++) {
				BasicDynaBean bean = (BasicDynaBean) rs.get(i);
				optionHold = new OptionHold(bean.get("whouseid").toString(),
						bean.get("whousealias").toString());
				list.add(optionHold);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return list;
	}

	// 获得商业公司人员职务
	public static List<OptionHold> getcompersontypeList() {
		list = new ArrayList<OptionHold>();
		String sql = "select typeid,typename from g_sycustlinkertype where isactive=1 order by typeid";
		try {
			OptionHold optionHold = null;
			SqlReturn sqlreturn = Executer.getInstance().ExecSeletSQL(sql);
			ArrayList<BasicDynaBean> rs = sqlreturn.getResultSet();
			for (int i = 0, m = rs.size(); i < m; i++) {
				BasicDynaBean bean = (BasicDynaBean) rs.get(i);
				optionHold = new OptionHold(bean.get("typeid").toString(), bean
						.get("typename").toString());
				list.add(optionHold);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return list;
	}

	// 已作废合同查询/开票统计(按备注)_ERP发货仓库列表
	public static List<OptionHold> getshipwhousenew() {
		list = new ArrayList<OptionHold>();
		String sql = "select whouseid,whousealias from V_ShipWhouse  where is_shiphouse=1 union all select 0 whouseid,'所有' whousealias from (values(1)) t order by whouseid";
		try {
			OptionHold optionHold = null;
			SqlReturn sqlreturn = Executer.getInstance().ExecSeletSQL(sql);
			ArrayList<BasicDynaBean> rs = sqlreturn.getResultSet();
			for (int i = 0, m = rs.size(); i < m; i++) {
				BasicDynaBean bean = (BasicDynaBean) rs.get(i);
				optionHold = new OptionHold(bean.get("whouseid").toString(),
						bean.get("whousealias").toString());
				list.add(optionHold);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return list;
	}

	public static ArrayList<OptionHold> getWorkLogClassList() {
		list = new ArrayList<OptionHold>();

		list.add(new OptionHold("1", "综合管理"));
		list.add(new OptionHold("2", "计划管理"));
		list.add(new OptionHold("3", "产销衔接"));
		list.add(new OptionHold("4", "合作生产"));
		list.add(new OptionHold("5", "合同管理"));
		list.add(new OptionHold("6", "准运证管理"));
		list.add(new OptionHold("7", "省内调拨"));
		list.add(new OptionHold("8", "省外调拨1"));
		list.add(new OptionHold("9", "省外调拨2"));

		return list;
	}

	public static ArrayList<OptionHold> getWorkLogClassListNew() {
		list = new ArrayList<OptionHold>();

		list.add(new OptionHold("1", "调拨主任"));
		list.add(new OptionHold("2", "计划主任"));
		list.add(new OptionHold("3", "产销衔接"));
		list.add(new OptionHold("4", "合作生产主任"));
		list.add(new OptionHold("5", "合同管理"));
		list.add(new OptionHold("6", "调拨协助"));
		list.add(new OptionHold("7", "省内调拨"));
		list.add(new OptionHold("8", "省外调拨1"));
		list.add(new OptionHold("9", "省外调拨2"));

		return list;
	}

	// OptionUtil.java End

}
