package com.zl.base.core.taglib.chart;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.ResponseUtils;

import com.zl.base.core.db.Executer;
import com.zl.base.core.taglib.ReadTaglibProperty;
import com.zl.util.MethodFactory;

public class AppletChart extends BaseHandlerTag {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(Executer.class.getName());

	private ReadTaglibProperty taglibproperty = ReadTaglibProperty
			.getInstance();

	private String type = "all"; // "line","bar","all"
	private String height = "300";
	private String width = "800";
	private String align = "middle";
	// private String alt = "...\u6B63\u5728\u52A0\u8F7DApplet\u56FE\u8868";
	private String alt = "...正在加载Applet图表";
	private String title = "";

	private String captionLine = "";
	private String captionBar = "";

	private String unitX = "";
	private String unitY = ""; // 只用于单图种
	private String unitLine = "";
	private String unitBar = "";
	private String saveFile = "image.bmp";

	private int itemNum = 1;

	private String collection = null; // 数据集 ["item","serie","type","value"]
	private String series = null; // 组内数据元集合 ["property","type"]
	private String items = null; // X轴点集合 ["property"]

	private ArrayList itemList = null;
	private ArrayList lineSerieList = null;
	private ArrayList barSerieList = null;

	public String getAlign() {
		return align;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getunitBar() {
		return unitBar;
	}

	public void setunitBar(String unitBar) {
		this.unitBar = unitBar;
	}

	public String getCaptionBar() {
		return captionBar;
	}

	public void setCaptionBar(String captionBar) {
		this.captionBar = captionBar;
	}

	public String getCaptionLine() {
		return captionLine;
	}

	public void setCaptionLine(String captionLine) {
		this.captionLine = captionLine;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getunitLine() {
		return unitLine;
	}

	public void setunitLine(String unitLine) {
		this.unitLine = unitLine;
	}

	public ReadTaglibProperty getTaglibproperty() {
		return taglibproperty;
	}

	public void setTaglibproperty(ReadTaglibProperty taglibproperty) {
		this.taglibproperty = taglibproperty;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getSaveFile() {
		return saveFile;
	}

	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}

	public String getUnitBar() {
		return unitBar;
	}

	public void setUnitBar(String unitBar) {
		this.unitBar = unitBar;
	}

	public String getUnitLine() {
		return unitLine;
	}

	public void setUnitLine(String unitLine) {
		this.unitLine = unitLine;
	}

	public String getUnitX() {
		return unitX;
	}

	public void setUnitX(String unitX) {
		this.unitX = unitX;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUnitY() {
		return unitY;
	}

	public void setUnitY(String unitY) {
		this.unitY = unitY;
	}

	public AppletChart() {

	}

	public int doStartTag() throws JspException {
		// 读取生成需要的数据
		// 处理宽度，如果设置错误时为500

		logger.error(((HttpServletRequest) pageContext.getRequest())
				.getContextPath());
		/*
		 * String valuetemp; Object objecttemp;
		 *
		 * objecttemp=pageContext.getRequest().getParameter("heightscale");
		 * valuetemp=MethodFactory.getThisString( objecttemp); try{
		 * this.heightscale =Float.parseFloat( valuetemp);
		 * if(this.heightscale<=0) this.heightscale =1; }catch (Exception ex){}
		 * objecttemp=pageContext.getRequest().getParameter("widthscale");
		 * valuetemp=MethodFactory.getThisString( objecttemp); try{
		 * this.widthscale =Float.parseFloat(valuetemp); if(this.widthscale<=0)
		 * this.widthscale =1; }catch (Exception ex){}
		 */
		// return (EVAL_BODY_TAG);
		return (EVAL_BODY_BUFFERED);

	}

	public int doEndTag() throws JspException {
		logger.debug(((HttpServletRequest) pageContext.getRequest())
				.getContextPath());
		StringBuffer sb = null;
		try {
			logger.debug("begin create chart");
			sb = createChart();

		} catch (Exception ex) {
			throw new JspException(ex.toString());
		}

		ResponseUtils.write(pageContext, sb.toString());
		return (EVAL_PAGE);
	}

	public StringBuffer createChart() throws JspException {
		StringBuffer sb = new StringBuffer();
		Object bean = null;
		Object value = null;

		Object unitbean = pageContext.findAttribute(unitLine);
		if (unitbean != null)
			unitLine = String.valueOf(unitbean);

		unitbean = pageContext.findAttribute(unitBar);
		if (unitbean != null)
			unitBar = String.valueOf(unitbean);

		unitbean = pageContext.findAttribute(unitX);
		if (unitbean != null)
			unitX = String.valueOf(unitbean);

		unitbean = pageContext.findAttribute(unitY);
		if (unitbean != null)
			unitY = String.valueOf(unitbean);

		unitbean = pageContext.findAttribute(title);
		if (unitbean != null)
			title = String.valueOf(unitbean);

		if ("all".equals(type)) {
			// 折线与柱形混合
			String appcode = "DoubleAxisChart.class";
			if (unitLine != null && unitLine.equals(unitBar)) {
				appcode = "MChart.class";
			}
			sb.append("<applet id=\"myapplet\" ARCHIVE=\""
					+ ((HttpServletRequest) pageContext.getRequest())
							.getContextPath()
					+ "/component/monitor.jar\" codebase=\"\" code=\""
					+ appcode + "\" align=\"" + align + "\"");
			sb.append(" height=\"" + height + "\" width=\"" + width
					+ "\" alt=\"" + alt + "\">");
			sb.append(" <param name=\"show_button\" value=\"0\">");
			sb.append(" <param name=\"title\" value=\"" + title + "\">");

			if (unitLine != null && !unitLine.equals(unitBar)) {
				sb.append(" <param name=\"unitLine\" value=\"" + unitLine
						+ "\">");
				sb.append(" <param name=\"unitBar\" value=\"" + unitBar + "\">");

				sb.append(" <param name=\"captionLine\" value=\"" + captionLine
						+ "\">");
				sb.append(" <param name=\"captionBar\" value=\"" + captionBar
						+ "\">");

			} else {
				sb.append(" <param name=\"unitY\" value=\"" + unitLine + "\">");
			}

			sb.append(" <param name=\"unitX\" value=\"" + unitX + "\">");

			Object bean1 = pageContext.findAttribute(saveFile);
			if (bean1 != null)
				saveFile = String.valueOf(bean1);
			sb.append(" <param name=\"saveFile\" value=\"" + saveFile + "\">");

			if (items != null) {
				ArrayList colItems = getIterator(items, null);
				if (colItems != null) {
					itemList = new ArrayList();
					sb.append(" <param name=\"pointnum\" value=\""
							+ colItems.size() + "\">");
					for (int i = 0; i < colItems.size(); i++) {
						bean = colItems.get(i);

						try {
							value = PropertyUtils.getProperty(bean, "property");

							if (value != null) {
								itemList.add(String.valueOf(value));
								sb.append("<param name=\"label" + i
										+ "\" value=\"" + String.valueOf(value)
										+ "\">");
							}

						} catch (Exception e) {
							logger.error("property属性获取失败!AppletChart.332行\n"
									+ e.toString());
						}
					}
				}
			}

			if (series != null) {
				// System.out.println("series="+series);
				ArrayList colSeries = getIterator(series, null);
				// System.out.println("colSeries.size()="+colSeries.size());
				if (colSeries != null) {
					lineSerieList = new ArrayList();
					barSerieList = new ArrayList();
					// sb.append(" <param name=\"pointnum\" value=\""+colSeries.size()+"\">");
					int lineNum = 0;
					int barNum = 0;
					for (int i = 0; i < colSeries.size(); i++) {
						bean = colSeries.get(i);

						try {
							value = PropertyUtils.getProperty(bean, "type");
							// System.out.println("type="+value);
							if ("1".equals(String.valueOf(value))
									|| "2".equals(String.valueOf(value))) {
								value = PropertyUtils.getProperty(bean,
										"property");
								lineSerieList.add(String.valueOf(value));
								sb.append("<param name=\"t" + lineNum
										+ "\" value=\"" + String.valueOf(value)
										+ "\">");
								lineNum++;
							} else {
								value = PropertyUtils.getProperty(bean,
										"property");
								barSerieList.add(String.valueOf(value));
								sb.append("<param name=\"b" + barNum
										+ "\" value=\"" + String.valueOf(value)
										+ "\">");
								barNum++;
							}

						} catch (Exception e) {
						}
					}
					sb.append(" <param name=\"linenum\" value=\"" + lineNum
							+ "\">");
					sb.append(" <param name=\"barnum\" value=\"" + barNum
							+ "\">");
				}
			}
			// System.out.println("sb="+sb);

			if (collection != null) {
				ArrayList colCollection = getIterator(collection, null);
				if (colCollection != null) {

					// sb.append(" <param name=\"pointnum\" value=\""+colSeries.size()+"\">");

					for (int i = 0; i < colCollection.size(); i++) {
						bean = colCollection.get(i);

						try {
							Object type = PropertyUtils.getProperty(bean,
									"type");
							Object item = PropertyUtils.getProperty(bean,
									"item");
							Object serie = PropertyUtils.getProperty(bean,
									"serie");
							value = PropertyUtils.getProperty(bean, "value");
							if ("1".equals(String.valueOf(type))
									|| "2".equals(String.valueOf(type))) {
								sb.append("<param name=\"value"
										+ indexOfArray(lineSerieList,
												String.valueOf(serie))
										+ "_"
										+ indexOfArray(itemList,
												String.valueOf(item))
										+ "\" value=\"" + String.valueOf(value)
										+ "\">");
							} else {
								sb.append("<param name=\"bvalue"
										+ indexOfArray(barSerieList,
												String.valueOf(serie))
										+ "_"
										+ indexOfArray(itemList,
												String.valueOf(item))
										+ "\" value=\"" + String.valueOf(value)
										+ "\">");
							}

						} catch (Exception e) {
						}
					}

				}
			}
			sb.append(" </applet>");

		} else {
			// 单图形
			Object bean1 = pageContext.findAttribute(saveFile);
			if (bean1 != null)
				saveFile = String.valueOf(bean1);

			sb.append("<applet  id=\"myapplet\"  ARCHIVE=\""
					+ ((HttpServletRequest) pageContext.getRequest())
							.getContextPath()
					+ "/component/monitor.jar\" codebase=\"\"  embed type=\"application/x-java-applet;version=1.6\"  code=\"Chart.class\" align=\""
					+ align + "\"");
			sb.append(" height=\"" + height + "\" width=\"" + width
					+ "\" alt=\"" + alt + "\">");

			sb.append(" <param name=\"type\" value=\"" + type + "\">");
			sb.append(" <param name=\"show_button\" value=\"0\">");
			sb.append(" <param name=\"title\" value=\"" + title + "\">");

			sb.append(" <param name=\"unitX\" value=\"" + unitX + "\">");
			sb.append(" <param name=\"unitY\" value=\"" + unitY + "\">");
			sb.append(" <param name=\"saveFile\" value=\"" + saveFile + "\">");

			if (items != null) {
				ArrayList colItems = getIterator(items, null);
				if (colItems != null) {
					itemList = new ArrayList();
					sb.append(" <param name=\"pointnum\" value=\""
							+ colItems.size() + "\">");
					for (int i = 0; i < colItems.size(); i++) {
						bean = colItems.get(i);

						try {
							value = PropertyUtils.getProperty(bean, "property");

							if (value != null) {
								itemList.add(String.valueOf(value));
								sb.append("<param name=\"label" + i
										+ "\" value=\"" + String.valueOf(value)
										+ "\">");
							}

						} catch (Exception e) {
							logger.error("property属性获取失败!AppletChart.469行\n"
									+ e.toString());
						}
					}
				}
			}

			if (series != null) {
				ArrayList colSeries = getIterator(series, null);
				if (colSeries != null) {
					lineSerieList = new ArrayList();

					int lineNum = 0;

					for (int i = 0; i < colSeries.size(); i++) {
						bean = colSeries.get(i);

						try {

							value = PropertyUtils.getProperty(bean, "property");
							lineSerieList.add(String.valueOf(value));
							sb.append("<param name=\"t" + lineNum
									+ "\" value=\"" + String.valueOf(value)
									+ "\">");
							lineNum++;

						} catch (Exception e) {
							logger.error("property属性获取失败!AppletChart.495行\n"
									+ e.toString());
						}
					}

					sb.append(" <param name=\"linenum\" value=\"" + lineNum
							+ "\">");

				}
			}

			if (collection != null) {
				ArrayList colCollection = getIterator(collection, null);
				if (colCollection != null) {

					// sb.append(" <param name=\"pointnum\" value=\""+colSeries.size()+"\">");

					for (int i = 0; i < colCollection.size(); i++) {
						bean = colCollection.get(i);

						try {

							Object item = PropertyUtils.getProperty(bean,
									"item");
							Object serie = PropertyUtils.getProperty(bean,
									"serie");
							value = PropertyUtils.getProperty(bean, "value");

							sb.append("<param name=\"value"
									+ indexOfArray(lineSerieList,
											String.valueOf(serie))
									+ "_"
									+ indexOfArray(itemList,
											String.valueOf(item))
									+ "\" value=\"" + String.valueOf(value)
									+ "\">");

						} catch (Exception e) {
							logger.error("value属性获取失败!AppletChart.534行\n"
									+ e.toString());
						}
					}

				}
			}
			sb.append(" </applet>");
		}
		// System.out.println("sb:"+sb);
		return sb;
	}

	private int indexOfArray(ArrayList list, String val) {
		if (list == null || list.size() == 0)
			return 0;
		else
			return list.indexOf(val);
	}

	// 更具名称读取对象中的值
	private String readvalue(Object bean, String propertyname) {
		String returnvalue = null;
		Object objtemp = null;
		try {
			if (bean != null) {
				objtemp = PropertyUtils.getProperty(bean, propertyname);
				returnvalue = MethodFactory.getThisString(objtemp);
				// returnvalue =(String)objtemp;
			}
		} catch (Exception ex) {
			returnvalue = "0";
		}
		return returnvalue;
	}

	// 读取指定名称的对象
	private Object readcollention(String collentionname) {
		Object returnobject = null;
		if (collentionname != null)
			returnobject = pageContext.getRequest()
					.getAttribute(collentionname);
		return returnobject;
	}

	// 转换数字
	private int readnumber(String numberstring) {
		int returnvalue = 0;
		int firstint = 0;
		int secondint = 0;
		numberstring = numberstring.toUpperCase();
		firstint = readnumber2(numberstring.substring(0, 1));
		secondint = readnumber2(numberstring.substring(1, 2));
		returnvalue = firstint * 16 + secondint;
		return returnvalue;
	}

	private int readnumber2(String numberstring) {
		int returnvalue = 0;
		try {
			returnvalue = Integer.parseInt(numberstring);
		} catch (Exception ex) {
			if (numberstring.equals("A")) {
				returnvalue = 10;
			} else if (numberstring.equals("B")) {
				returnvalue = 11;
			} else if (numberstring.equals("C")) {
				returnvalue = 12;
			} else if (numberstring.equals("D")) {
				returnvalue = 13;
			} else if (numberstring.equals("E")) {
				returnvalue = 14;
			} else if (numberstring.equals("F")) {
				returnvalue = 15;
			} else {
				returnvalue = 0;
			}
		}
		return returnvalue;
	}

	protected ArrayList getIterator(String name, String property) {
		// 模拟数据
		// Identify the bean containing our collection
		String beanName = name;

		Object bean = pageContext.findAttribute(beanName);
		ArrayList beanTemp = (ArrayList) bean;
		if (bean == null)
			return null;
		else
			return beanTemp;
	}

}
