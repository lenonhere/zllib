/**
 * @author: 朱忠南
 * @date: Jul 30, 2008
 * @company: 杭州州力数据科技有限公司
 * @desribe:
 * @modify_author:
 * @modify_time:
 */
package com.zl.base.core.taglib.flashchart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;

import common.Logger;

/**
 * @author jokin
 * @date Jul 30, 2008
 */
public class FlashChartUtil {

	private static Logger logger = Logger.getLogger(FlashChartUtil.class);

	/**
	 * 获取XY坐标系的图表
	 *
	 * @author: 朱忠南
	 * @param divName
	 *            显示flash图表使用的div的ID或NAME
	 * @param chartSys
	 *            系统参数，包括“title”标题，“xlegend”x轴名称，“ylegend”y轴名称，“y2legend”y右轴名称，“
	 *            min1”y轴最小值，“max1”y轴最大值，“min2”y右轴最小值，“max2”y右轴最大值
	 * @param xAxises
	 *            x轴数据集，包括“xaria_key”x轴关键值（或真实值） 和 “xaria_value”x轴显示值
	 * @param bars
	 *            数据项集，即柱多个状图或多个线图，包含“serie_key”数据项关键名称，“serie_value”数据项显示名称，“
	 *            serie_y”显示与y轴还是y2轴，“serie_charttype”图形类型
	 * @param data
	 *            数据集，包括“serie_key”表示属于哪个数据项，“xaria_key”表示属于哪个x轴点，“data_value”
	 *            表示数据，“data_link”表示链接（可以使用javascript:开头，并不能含有逗号）
	 * @param unitName
	 *            数据单位，当两个Y坐标都显示，并且单位不一致时最好不要设置
	 * @return XYChart对象
	 * @throws Exception
	 */
	public static XYChart getXYChart(String divName, List chartSys,
			List xAxises, List bars, List data, String unitName)
			throws Exception {

		// 显示flashchart的div名称不能为空
		if (divName == null || divName.trim().equals("")) {
			logger.error("显示FlashChart需要DIV，divName 不能为空");
			throw new Exception("显示FlashChart需要DIV，divName 不能为空");
		}
		if (chartSys == null) {
			logger.error("第一个结果集chartSys（flash标题等基本参数集）为null");
			throw new Exception("第一个结果集chartSys（flash标题等基本参数集）为null");
		}
		if (xAxises == null) {
			logger.error("第二个结果集xAxises（x轴数据集）为null");
			throw new Exception("第二个结果集xAxises（x轴数据集）为null");
		}
		if (bars == null) {
			logger.error("第三个结果集bars（指标集）为null");
			throw new Exception("第三个结果集bars（指标集）为null");
		}
		if (data == null) {
			logger.error("第四个结果集data（数据集）为null");
			throw new Exception("第四个结果集data（数据集）为null");
		}

		XYChart xyChart = new XYChart(divName);

		BasicDynaBean bdb = (BasicDynaBean) chartSys.get(0);
		// 获取并设置标题
		String title = (String) bdb.get("title");
		if (title != null && !title.trim().equals(""))
			xyChart.setTitle(new Title(title));
		else
			logger.info("标题为空");

		// 获取并设置X轴
		String xlegend = (String) bdb.get("xlegend");
		StringBuffer temp = new StringBuffer();
		String xAxisLabel = "";
		for (int i = 0, n = xAxises.size(); i < n; i++) {
			BasicDynaBean bean = (BasicDynaBean) xAxises.get(i);
			temp.append(bean.get("xaria_value")).append(",");
		}
		if (temp.length() > 0)
			xAxisLabel = temp.substring(0, temp.length() - 1);
		XAxis xAxis = new XAxis();
		if (xlegend != null && !xlegend.trim().equals(""))
			xAxis.setLegend(new Legend(xlegend));
		else
			logger.info("x轴名称为空");
		xAxis.setLabels(xAxisLabel);
		// xAxis.setAxis3d(5);
		xAxis.setTicks(new XTicks());
		xyChart.setXAxis(xAxis);

		// 设置y轴
		String ylegend = (String) bdb.get("ylegend");
		YAxis yAxis = new YAxis();
		if (ylegend != null && !ylegend.trim().equals(""))
			yAxis.setLegend(new Legend(ylegend));
		else
			logger.info("y轴坐标名为空");
		Object min1 = bdb.get("min1");
		Object max1 = bdb.get("max1");
		if (min1 != null && !min1.toString().trim().equals("") && max1 != null
				&& !max1.toString().trim().equals("")) {
			double min, max;
			try {
				min = Double.parseDouble(min1.toString().trim());
				max = Double.parseDouble(max1.toString().trim());
			} catch (NumberFormatException e) {
				logger.error("min1=" + min1 + ",max1=" + max1 + "不是数字类型");
				throw new Exception("min1=" + min1 + ",max1=" + max1 + "不是数字类型");
			}
			if (min > max) {
				logger.error("min1(" + min1 + ") 不能大于 max1(" + max1 + ")");
				throw new Exception("min1(" + min1 + ") 不能大于 max1(" + max1
						+ ")");
			}
			min = getMin(min, max);
			max = getMax(min, max);
			yAxis.setMax(max);
			yAxis.setMin(min);
		} else {
			logger.error("最小值min1和最大值max1是必须的");
			throw new Exception("最小值min1和最大值max1是必须的");
		}
		yAxis.setTicks(new YTicks(false, 10));
		xyChart.setYAxis(yAxis);

		// y右轴
		String indexes = "";
		for (int i = 0, n = bars.size(); i < n; i++) {
			BasicDynaBean bean = (BasicDynaBean) bars.get(i);
			String yType = (String) bean.get("serie_y");// 表示此指标是针对月y左轴还是y右轴
			if (yType != null
					&& (yType.trim().equals("2") || yType.trim().toLowerCase()
							.equals("y2"))) {
				indexes += (i + 1) + ",";
			}
		}
		if (indexes.length() > 0) {// 表示有Y右轴数据
			indexes = indexes.substring(0, indexes.length() - 1);
			String y2legend = (String) bdb.get("y2legend");
			Y2Axis y2Axis = new Y2Axis();
			if (y2legend != null && !y2legend.trim().equals(""))
				y2Axis.setLegend(new Legend(y2legend));
			else
				logger.info("y右轴坐标名为空");
			Object min2 = bdb.get("min2");
			Object max2 = bdb.get("max2");
			if (min2 != null && !min2.toString().trim().equals("")
					&& max2 != null && !max2.toString().trim().equals("")) {
				double min, max;
				try {
					min = Double.parseDouble(min2.toString().trim());
					max = Double.parseDouble(max2.toString().trim());
				} catch (NumberFormatException e) {
					logger.error("min2=" + min2 + ",max2=" + max2 + "不是数字类型");
					throw new Exception("min2=" + min2 + ",max2=" + max2
							+ "不是数字类型");
				}
				if (min > max) {
					logger.error("min2(" + min2 + ") 不能大于 max2(" + max2 + ")");
					throw new Exception("min2(" + min2 + ") 不能大于 max2(" + max2
							+ ")");
				}
				min = getMin(min, max);
				max = getMax(min, max);
				y2Axis.setMax(max);
				y2Axis.setMin(min);
			} else {
				logger.error("最小值min1和最大值max1是必须的");
				throw new Exception("最小值min1和最大值max1是必须的");
			}
			y2Axis.setTicks(new YTicks(true, 10));
			xyChart.setY2Axis(y2Axis);
			xyChart.setY2DataIndexes(indexes);// 设置指向Y右轴的指标数据
		}

		// for循环构造数据字符串
		Map values = new HashMap();// 存放数据字符串[key:指标,value:以“,”分隔的值串]
		Map links = new HashMap();// 存放链接
		Map map1 = new HashMap();// map中存放数据[key:指标,value:[key:x轴指标,value:数值]]
		Map map2 = new HashMap();// map中存放链接[key:指标,value:[key:x轴指标,value:链接]]
		for (int j = 0, m = data.size(); j < m; j++) {
			BasicDynaBean dBean = (BasicDynaBean) data.get(j);
			Object type = dBean.get("serie_key");
			Object key = dBean.get("xaria_key");

			Object value = dBean.get("data_value");
			Object link = dBean.get("data_link");

			Map tmp = (Map) map1.get(type);
			if (tmp == null)
				tmp = new HashMap();
			tmp.put(key, value);
			map1.put(type, tmp);

			tmp = (Map) map2.get(type);
			if (tmp == null)
				tmp = new HashMap();
			tmp.put(key, link);
			map2.put(type, tmp);
		}
		// 将values中的map集改成以逗号分隔字符串集
		Iterator it = map1.keySet().iterator();
		while (it.hasNext()) {
			Object type = it.next();
			Map tmp = (Map) map1.get(type);
			StringBuffer sb = new StringBuffer();
			for (int i = 0, n = xAxises.size(); i < n; i++) {
				BasicDynaBean bean = (BasicDynaBean) xAxises.get(i);
				Object key = bean.get("xaria_key");
				Object value = tmp.get(key);
				sb.append(value).append(",");
			}
			values.put(type, sb.substring(0, sb.length() - 1));
		}
		// 将links中的map集改成以逗号分隔字符串集
		it = map2.keySet().iterator();
		while (it.hasNext()) {
			Object type = it.next();
			Map tmp = (Map) map2.get(type);
			StringBuffer sb = new StringBuffer();
			for (int i = 0, n = xAxises.size(); i < n; i++) {
				BasicDynaBean bean = (BasicDynaBean) xAxises.get(i);
				Object key = bean.get("xaria_key");
				Object link = tmp.get(key);
				sb.append(link).append(",");
			}
			links.put(type, sb.substring(0, sb.length() - 1));
		}

		// for循环将指标加入图表
		temp = new StringBuffer();
		for (int i = 0, n = bars.size(); i < n; i++) {
			BasicDynaBean bean = (BasicDynaBean) bars.get(i);
			Object type = bean.get("serie_key");
			String property = (String) bean.get("serie_value");
			String chartType = (String) bean.get("serie_charttype");// 图形类型
			if (chartType == null) {
				logger.info(property + "没有指定图形类型，默认为柱状图");
				chartType = "bar";// 默认为柱状图
			}
			// 这里暂时规定图形类型为bar和line
			if (chartType.trim().toLowerCase().indexOf("bar") != -1) {
				BarShape shape = new BarShape(Shape.BAR_GLASS, property);
				String valuesStr = (String) values.get(type);
				String linksStr = (String) links.get(type);
				shape.setValues(valuesStr);
				shape.setLinks(linksStr);
				xyChart.addShape(shape);
			} else if (chartType.trim().toLowerCase().indexOf("line") != -1) {
				LineShape shape = new LineShape(Shape.LINE_DOT, property);
				String valuesStr = (String) values.get(type);
				String linksStr = (String) links.get(type);
				shape.setValues(valuesStr);
				shape.setLinks(linksStr);
				xyChart.addShape(shape);
			} else {
				logger.error("不能识别类型为" + chartType + "的图形");
				throw new Exception("不能识别类型为" + chartType + "的图形");
			}
		}

		if (unitName != null && !unitName.trim().equals(""))
			xyChart.setToolTip(xyChart.getToolTip() + unitName);

		return xyChart;

	}

	/**
	 * 获取饼图
	 *
	 * @author: 朱忠南
	 * @param divName
	 *            显示flash图表的div的ID或NAME
	 * @param title
	 *            图表的标题
	 * @param data
	 *            结果集，包含“label”和“value”
	 * @param unitName
	 *            单位名称
	 * @return PieChart对象
	 * @throws Exception
	 */
	public static PieChart getPieChart(String divName, String title, List data,
			String unitName) throws Exception {
		if (data == null) {
			logger.error("第一个结果集data（数据集）为null");
			throw new Exception("第一个结果集data（数据集）为null");
		}

		PieChart chart = new PieChart(divName);
		if (title != null && !title.trim().equals(""))
			chart.setTitle(new Title(title));
		PieShape shape = new PieShape();
		StringBuffer labels = new StringBuffer();
		StringBuffer values = new StringBuffer();
		StringBuffer links = new StringBuffer();
		for (int i = 0, n = data.size(); i < n; i++) {
			BasicDynaBean bean = (BasicDynaBean) data.get(i);
			Object label = bean.get("label");// 名称
			Object value = bean.get("value");// 数量
			Object link = bean.get("link");// 链接
			labels.append(label).append(",");
			values.append(value).append(",");
			links.append(link).append(",");
		}
		if (labels.length() > 0)
			shape.setLabels(labels.substring(0, labels.length() - 1));
		if (values.length() > 0)
			shape.setValues(values.substring(0, values.length() - 1));
		if (links.length() > 0)
			shape.setLinks(links.substring(0, links.length() - 1));
		chart.addShape(shape);

		if (unitName != null && !unitName.trim().equals(""))
			chart.setToolTip(chart.getToolTip() + unitName);

		return chart;

	}

	public static double getMin(double min, double max) {
		if (min >= 0)
			return 0;
		else {
			double d = (max - min) / 9;
			return min - d;
		}
	}

	public static double getMax(double min, double max) {
		double d = (max - min) / 9;
		return max + d;
	}

}
