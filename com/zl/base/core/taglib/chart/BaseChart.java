package com.zl.base.core.taglib.chart;

import java.awt.Color;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.ResponseUtils;

import com.zl.base.core.db.Executer;
import com.zl.base.core.taglib.ReadTaglibProperty;
import com.zl.base.core.taglib.chart.vml.chart.VMLChart;
import com.zl.base.core.xml.XMLConvert;
import com.zl.util.MethodFactory;

public class BaseChart extends BaseHandlerTag {
	static Logger logger = Logger.getLogger(Executer.class);
	private ReadTaglibProperty taglibproperty = ReadTaglibProperty
			.getInstance();
	private float heightscale = 1.0F;
	private float widthscale = 1.0F;
	private String bgcolor = "#FFFFFF";
	private String sharptype = "0";
	private boolean is3d = true;
	private int maxlabelcount = 15;
	private int sectionlabeltype = 5;
	private boolean tablevisible = true;
	private boolean border = false;
	private String plotbgcolor = "#FFFFFF";
	private boolean mutipie = false;
	private boolean poltshapevisible = true;
	private String valuetype = "1";
	private String series = null;
	private String collention = null;
	private String charttype = "line";
	private int captionfontsize = 9;
	private int legendfontsize = 9;
	private int labelfontsize = 9;
	private int axiscaptionfontsize = 7;
	public double ylowermargin = 0.0D;
	private String hcaption = null;
	private String vcaption = null;
	private String item = null;
	private String caption = null;
	private String property = null;
	private int width = 500;
	private int height = 300;
	private boolean captionvisible = true;
	private int chartdirection = 1;
	private boolean hcaptionvisible = true;
	private int legendlocation = 3;
	private boolean legendvisible = true;
	private int serieslabeldirection = 0;
	private boolean vcaptionvisible = true;
	private boolean dataprogress = false;
	private boolean mutipievisible = false;

	/**
	 * 构造函数
	 */
	public BaseChart() {
		if (this.heightscale <= 0.0F)
			this.heightscale = 1.0F;
		if (this.widthscale <= 0.0F)
			this.heightscale = 1.0F;
	}

	private int getIntfromFloat(float paramFloat) {
		Float localFloat = new Float(paramFloat);
		return localFloat.intValue();
	}

	public String getbgcolor() {
		return this.bgcolor;
	}

	public void setbgcolor(String paramString) {
		this.bgcolor = paramString;
	}

	public void setsharptype(String paramString) {
		this.sharptype = paramString;
	}

	public String getsharptype() {
		return this.sharptype;
	}

	public boolean getis3d() {
		return this.is3d;
	}

	public void setis3d(boolean paramBoolean) {
		this.is3d = paramBoolean;
	}

	public int getmaxlabelcount() {
		return this.maxlabelcount;
	}

	public void setmaxlabelcount(int paramInt) {
		this.maxlabelcount = paramInt;
	}

	public int getsectionlabeltype() {
		return this.sectionlabeltype;
	}

	public void setsectionlabeltype(int paramInt) {
		this.sectionlabeltype = paramInt;
	}

	public boolean gettablevisible() {
		return this.tablevisible;
	}

	public void settablevisible(boolean paramBoolean) {
		this.tablevisible = paramBoolean;
	}

	public boolean getborder() {
		return this.border;
	}

	public void setborder(boolean paramBoolean) {
		this.border = paramBoolean;
	}

	public String getplotbgcolor() {
		return this.plotbgcolor;
	}

	public void setplotbgcolor(String paramString) {
		this.plotbgcolor = paramString;
	}

	public boolean getmutipie() {
		return this.mutipie;
	}

	public void setmutipie(boolean paramBoolean) {
		this.mutipie = paramBoolean;
	}

	public boolean getpoltshapevisible() {
		return this.poltshapevisible;
	}

	public void setpoltshapevisible(boolean paramBoolean) {
		this.poltshapevisible = paramBoolean;
	}

	public String getvaluetype() {
		return this.valuetype;
	}

	public void setvaluetype(String paramString) {
		this.valuetype = paramString;
	}

	public String getseries() {
		return this.series;
	}

	public void setseries(String paramString) {
		this.series = paramString;
	}

	public String getcollention() {
		return this.collention;
	}

	public void setcollention(String paramString) {
		this.collention = paramString;
	}

	public String getcharttype() {
		return this.charttype;
	}

	public void setcharttype(String paramString) {
		this.charttype = paramString;
	}

	public int getcaptionfontsize() {
		return this.captionfontsize;
	}

	public void setcaptionfontsize(int paramInt) {
		this.captionfontsize = paramInt;
	}

	public int getlegendfontsize() {
		return this.legendfontsize;
	}

	public void setlegendfontsize(int paramInt) {
		this.legendfontsize = paramInt;
	}

	public int getlabelfontsize() {
		return this.labelfontsize;
	}

	public void setlabelfontsize(int paramInt) {
		this.labelfontsize = paramInt;
	}

	public int getaxiscaptionfontsize() {
		return this.axiscaptionfontsize;
	}

	public void setaxiscaptionfontsize(int paramInt) {
		this.axiscaptionfontsize = paramInt;
	}

	public double getYlowermargin() {
		return this.ylowermargin;
	}

	public void setYlowermargin(double paramDouble) {
		this.ylowermargin = paramDouble;
	}

	/**
	 * 设置JFreeChart的类型 [ 0.line 1.bar 2.pie 3.sline 5.sbar ]
	 *
	 * @param paramString
	 * @return
	 */
	private int readcharttype(String paramString) {
		int i = 0;
		if (paramString == null) {
			i = 0;
		} else {
			paramString = paramString.toLowerCase().trim();
			if (paramString == "line")
				i = 0;
			else if (paramString == "bar")
				i = 1;
			else if (paramString == "pie")
				i = 2;
			else if (paramString == "sline")
				i = 3;
			else if (paramString == "sbar")
				i = 5;
		}
		return i;
	}

	public String gethcaption() {
		return this.hcaption;
	}

	public void sethcaption(String paramString) {
		this.hcaption = paramString;
	}

	public String getvcaption() {
		return this.vcaption;
	}

	public void setvcaption(String paramString) {
		this.vcaption = paramString;
	}

	public String getitem() {
		return this.item;
	}

	public void setitem(String paramString) {
		this.item = paramString;
	}

	public String getcaption() {
		return this.caption;
	}

	public void setcaption(String paramString) {
		this.caption = paramString;
	}

	public String getproperty() {
		return this.property;
	}

	public void setproperty(String paramString) {
		this.property = paramString;
	}

	public int getwidth() {
		return this.width;
	}

	public void setwidth(int paramInt) {
		this.width = paramInt;
	}

	public int getheight() {
		return this.height;
	}

	public void setheight(int paramInt) {
		this.height = paramInt;
	}

	public boolean getcaptionvisible() {
		return this.captionvisible;
	}

	public void setcaptionvisible(boolean paramBoolean) {
		this.captionvisible = paramBoolean;
	}

	public int getchartdirection() {
		return this.chartdirection;
	}

	public void setchartdirection(int paramInt) {
		this.chartdirection = paramInt;
	}

	public boolean gethcaptionvisible() {
		return this.hcaptionvisible;
	}

	public void sethcaptionvisible(boolean paramBoolean) {
		this.hcaptionvisible = paramBoolean;
	}

	public int getlegendlocation() {
		return this.legendlocation;
	}

	public void setlegendlocation(int paramInt) {
		this.legendlocation = paramInt;
	}

	public boolean getlegendvisible() {
		return this.legendvisible;
	}

	public void setlegendvisible(boolean paramBoolean) {
		this.legendvisible = paramBoolean;
	}

	public int getserieslabeldirection() {
		return this.serieslabeldirection;
	}

	public void setserieslabeldirection(int paramInt) {
		this.serieslabeldirection = paramInt;
	}

	public boolean getvcaptionvisible() {
		return this.vcaptionvisible;
	}

	public void setvcaptionvisible(boolean paramBoolean) {
		this.vcaptionvisible = paramBoolean;
	}

	public boolean getdataprogress() {
		return this.dataprogress;
	}

	public void setdataprogress(boolean paramBoolean) {
		this.dataprogress = paramBoolean;
	}

	public boolean getmutipievisible() {
		return this.mutipievisible;
	}

	public void setmutipievisible(boolean paramBoolean) {
		this.mutipievisible = paramBoolean;
	}

	/* (非 Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		//TODO doStartTag()
		logger.debug(((HttpServletRequest) this.pageContext.getRequest())
				.getContextPath());
		//设置高
		String str2 = this.pageContext.getRequest().getParameter("heightscale");
		String str1 = MethodFactory.getThisString(str2);//去前后空格
		try {
			this.heightscale = Float.parseFloat(str1);
			if (this.heightscale <= 0.0F)
				this.heightscale = 1.0F;
		} catch (Exception localException1) {
		}
		//设置宽
		str2 = this.pageContext.getRequest().getParameter("widthscale");
		str1 = MethodFactory.getThisString(str2);
		try {
			this.widthscale = Float.parseFloat(str1);
			if (this.widthscale <= 0.0F)
				this.widthscale = 1.0F;
		} catch (Exception localException2) {
		}
		return 2;
	}

	/* (非 Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	public int doEndTag() throws JspException {
		//TODO doEndTag()
		logger.debug(((HttpServletRequest) this.pageContext.getRequest())
				.getContextPath());
		StringBuffer localStringBuffer = new StringBuffer();
		try {
			logger.debug("begin create chart");
			if (this.sharptype == null){
				this.sharptype = "0";}
			if (this.sharptype.equals("0")){
				localStringBuffer = createChartType0();}
			else{
				localStringBuffer = createChartType1();}
		} catch (Exception e) {
			throw new JspException(e.toString());
		}
		ResponseUtils.write(this.pageContext, localStringBuffer.toString());
		return 2;
	}

	/**
	 * sharptype=='0'
	 * @return
	 * @throws JspException
	 */
	public StringBuffer createChartType0() throws JspException {
		StringBuffer stringbuffer = new StringBuffer();
		chartstruct chartstruct1 = new chartstruct();
//		Object obj = null;
//		Object obj2 = null;
		try {
			logger.debug("set chart attribute begin");
			chartstruct1.setbgcolor(createcolor(bgcolor, "#FFFFFFF"));
			chartstruct1.setcaption(caption);
			chartstruct1.setcaptionvisible(captionvisible);
			chartstruct1.setchartdirection(chartdirection);
			chartstruct1.sethcaption(hcaption);
			chartstruct1.sethcaptionvisible(hcaptionvisible);
			chartstruct1
					.setheight(getIntfromFloat((float) height * widthscale));
			chartstruct1.setlegendlocation(legendlocation);
			chartstruct1.setlegendvisible(legendvisible);
			chartstruct1.setplotbgcolor(createcolor(plotbgcolor, "#FFFFFF"));
			chartstruct1.setserieslabeldirection(serieslabeldirection);
			chartstruct1.setvcaption(vcaption);
			chartstruct1.setvcaptionvisible(vcaptionvisible);
			chartstruct1.setwidth(getIntfromFloat((float) width * widthscale));
			chartstruct1.setcharttype(readcharttype(charttype));
			chartstruct1.setis3D(is3d);
			chartstruct1.setmutipievisible(getmutipievisible());
			chartstruct1.setpoltshapevisible(poltshapevisible);
			chartstruct1
					.setcaptionfontsize(getIntfromFloat((float) getcaptionfontsize()
							* heightscale));
			chartstruct1
					.setlegendfontsize(getIntfromFloat((float) getcaptionfontsize()
							* heightscale));
			chartstruct1
					.setlabelfontsize(getIntfromFloat((float) getlabelfontsize()
							* heightscale));
			chartstruct1
					.setaxiscaptionfontsize(getIntfromFloat((float) getaxiscaptionfontsize()
							* heightscale));
			chartstruct1.setdataprogress(getdataprogress());
			chartstruct1.setmaxlabelcount(maxlabelcount);
			chartstruct1.setsectionlabeltype(sectionlabeltype);
			chartstruct1.setYLowerMargin(ylowermargin);
			Object obj1 = readcollention(series);
			ArrayList arraylist = (ArrayList) obj1;
			Object obj3 = null;
			ArrayList arraylist1 = new ArrayList();
			logger.debug("set chart attribute end");
			logger.debug("create chart data begin");
			for (int i = 0; i < arraylist.size(); i++) {
				BaseSeries baseseries = new BaseSeries();
				Object obj4 = arraylist.get(i);
				Object obj5 = readvalue(obj4, "caption");
				baseseries.setcaption(obj5.toString());
				obj5 = readvalue(obj4, "property");
				if (obj5 == "0")
					obj5 = readvalue(obj4, "valueproperty");
				baseseries.setvalueproperty(obj5.toString());
				if (charttype != null)
					obj5 = String.valueOf(readcharttype(charttype));
				else
					try {
						Object obj6 = PropertyUtils.getProperty(obj4,
								"charttype");
						obj5 = MethodFactory.getThisString(obj6);
						obj5 = Integer.getInteger(String
								.valueOf(readcharttype(obj5.toString())));
					} catch (Exception exception1) {
						obj5 = Integer.getInteger(String.valueOf(0));
					}
				baseseries.setcharttype(Integer.parseInt(obj5.toString()));
				obj5 = readvalue(obj4, "property");
				baseseries.setseriesproperty(item);
				arraylist1.add(baseseries);
			}

			logger.debug("create chart data end");
			chartstruct1.setseries(arraylist1);
			Object obj7 = null;
			ArrayList arraylist2 = null;
			obj7 = readcollention(collention);
			arraylist2 = (ArrayList) obj7;
			ChartBuilder chartbuilder = new ChartBuilder();
			String s = "";
			logger.debug("create chart bitmap begin");
			s = chartbuilder.generateChart(chartstruct1, arraylist2,
					pageContext.getSession(),
					new PrintWriter(pageContext.getOut()));
			logger.debug("create chart bitmap end");
			stringbuffer.append("<img id =\""
					+ property
					+ "\" src=\""
					+ ((HttpServletRequest) pageContext.getRequest())
							.getContextPath() + "/pic.chart?filename=" + s
					+ "\"");
			stringbuffer.append(" width=\""
					+ getIntfromFloat((float) width * widthscale) + "\"");
			stringbuffer.append(" height=\""
					+ getIntfromFloat((float) height * heightscale) + "\"");
			stringbuffer.append("border=0 usemap=\"#" + s + "\">");
		} catch (Exception exception) {
			throw new JspException(exception.toString());
		}
		return stringbuffer;
	}

	// public StringBuffer createChartType0()
	// throws JspException
	// {
	// StringBuffer localStringBuffer = new StringBuffer();
	// chartstruct localchartstruct = new chartstruct();
	// Object localObject1 = null;
	// ArrayList localArrayList1 = null;
	// try
	// {
	// logger.debug("set chart attribute begin");
	// localchartstruct.setbgcolor(createcolor(this.bgcolor, "#FFFFFFF"));
	// localchartstruct.setcaption(this.caption);
	// localchartstruct.setcaptionvisible(this.captionvisible);
	// localchartstruct.setchartdirection(this.chartdirection);
	// localchartstruct.sethcaption(this.hcaption);
	// localchartstruct.sethcaptionvisible(this.hcaptionvisible);
	// localchartstruct.setheight(getIntfromFloat(this.height *
	// this.widthscale));
	// localchartstruct.setlegendlocation(this.legendlocation);
	// localchartstruct.setlegendvisible(this.legendvisible);
	// localchartstruct.setplotbgcolor(createcolor(this.plotbgcolor,
	// "#FFFFFF"));
	// localchartstruct.setserieslabeldirection(this.serieslabeldirection);
	// localchartstruct.setvcaption(this.vcaption);
	// localchartstruct.setvcaptionvisible(this.vcaptionvisible);
	// localchartstruct.setwidth(getIntfromFloat(this.width * this.widthscale));
	// localchartstruct.setcharttype(readcharttype(this.charttype));
	// localchartstruct.setis3D(this.is3d);
	// localchartstruct.setmutipievisible(getmutipievisible());
	// localchartstruct.setpoltshapevisible(this.poltshapevisible);
	// localchartstruct.setcaptionfontsize(getIntfromFloat(getcaptionfontsize()
	// * this.heightscale));
	// localchartstruct.setlegendfontsize(getIntfromFloat(getcaptionfontsize() *
	// this.heightscale));
	// localchartstruct.setlabelfontsize(getIntfromFloat(getlabelfontsize() *
	// this.heightscale));
	// localchartstruct.setaxiscaptionfontsize(getIntfromFloat(getaxiscaptionfontsize()
	// * this.heightscale));
	// localchartstruct.setdataprogress(getdataprogress());
	// localchartstruct.setmaxlabelcount(this.maxlabelcount);
	// localchartstruct.setsectionlabeltype(this.sectionlabeltype);
	// localchartstruct.setYLowerMargin(this.ylowermargin);
	// localObject1 = readcollention(this.series);
	// localArrayList1 = (ArrayList)localObject1;
	// Object localObject2 = null;
	// ArrayList localArrayList2 = new ArrayList();
	// logger.debug("set chart attribute end");
	// logger.debug("create chart data begin");
	// for (int i = 0; i < localArrayList1.size(); i++)
	// {
	// localObject6 = new BaseSeries();
	// localObject2 = localArrayList1.get(i);
	// Object localObject3 = readvalue(localObject2, "caption");
	// ((BaseSeries)localObject6).setcaption(localObject3.toString());
	// localObject3 = readvalue(localObject2, "property");
	// if (localObject3 == "0")
	// localObject3 = readvalue(localObject2, "valueproperty");
	// ((BaseSeries)localObject6).setvalueproperty(localObject3.toString());
	// if (this.charttype != null)
	// localObject3 = String.valueOf(readcharttype(this.charttype));
	// else
	// try
	// {
	// Object localObject4 = PropertyUtils.getProperty(localObject2,
	// "charttype");
	// localObject3 = MethodFactory.getThisString(localObject4);
	// localObject3 =
	// Integer.getInteger(String.valueOf(readcharttype(localObject3.toString())));
	// }
	// catch (Exception localException2)
	// {
	// localObject3 = Integer.getInteger(String.valueOf(0));
	// }
	// ((BaseSeries)localObject6).setcharttype(Integer.parseInt(localObject3.toString()));
	// localObject3 = readvalue(localObject2, "property");
	// ((BaseSeries)localObject6).setseriesproperty(this.item);
	// localArrayList2.add(localObject6);
	// }
	// logger.debug("create chart data end");
	// localchartstruct.setseries(localArrayList2);
	// Object localObject5 = null;
	// Object localObject6 = null;
	// localObject5 = readcollention(this.collention);
	// localObject6 = (ArrayList)localObject5;
	// ChartBuilder localChartBuilder = new ChartBuilder();
	// String str = "";
	// logger.debug("create chart bitmap begin");
	// str = localChartBuilder.generateChart(localchartstruct,
	// (ArrayList)localObject6, this.pageContext.getSession(), new
	// PrintWriter(this.pageContext.getOut()));
	// logger.debug("create chart bitmap end");
	// localStringBuffer.append("<img id =\"" + this.property + "\" src=\"" +
	// ((HttpServletRequest)this.pageContext.getRequest()).getContextPath() +
	// "/pic.chart?filename=" + str + "\"");
	// localStringBuffer.append(" width=\"" + getIntfromFloat(this.width *
	// this.widthscale) + "\"");
	// localStringBuffer.append(" height=\"" + getIntfromFloat(this.height *
	// this.heightscale) + "\"");
	// localStringBuffer.append("border=0 usemap=\"#" + str + "\">");
	// }
	// catch (Exception localException1)
	// {
	// throw new JspException(localException1.toString());
	// }
	// return localStringBuffer;
	// }

	/**
	 * sharptype=='1'
	 * @return
	 * @throws JspException
	 */
	public StringBuffer createChartType1() throws JspException {
		StringBuffer localStringBuffer = new StringBuffer();
		try {
			VMLChart localVMLChart = new VMLChart();
			localVMLChart.SetBGColor(this.bgcolor);
			localVMLChart.SetBound(0.0D, 0.0D, this.width, this.height);
			localVMLChart.SetCaption(this.caption);
			localVMLChart.SetCaptionVisible(this.captionvisible);
			localVMLChart.SetChartSize(this.width, this.height);
			if (this.legendvisible == true) {
				if (this.legendlocation == 3)
					localVMLChart.SetLegendVisible(1);
				else
					localVMLChart.SetLegendVisible(2);
			} else
				localVMLChart.SetLegendVisible(0);
			localVMLChart.SetTableVisible(this.tablevisible);
			localVMLChart.SetBorderVisible(this.border);
			String str = "<?xml version=\"1.0\" encoding=\"gb2312\"?><root>";
			ArrayList localArrayList1 = (ArrayList) readcollention(this.series);
			str = str
					+ new XMLConvert().RowSetDynatoXmlString(localArrayList1,
							"CONTENT_RECORDSET");
			ArrayList localArrayList2 = (ArrayList) readcollention(this.collention);
			str = str
					+ new XMLConvert().RowSetDynatoXmlString(localArrayList2,
							"CONTENT1_RECORDSET");
			str = str + "</root>";
			localVMLChart.LoadData(str);
			localStringBuffer.append(localVMLChart.generateChart(
					readcharttype(this.charttype), this.property, false,
					this.is3d));
		} catch (Exception localException) {
			throw new JspException(localException.toString());
		}
		return localStringBuffer;
	}



	private String readvalue(Object paramObject, String paramString) {
		String str = null;
		Object localObject = null;
		try {
			if (paramObject != null) {
				localObject = PropertyUtils.getProperty(paramObject,
						paramString);
				str = MethodFactory.getThisString(localObject);
			}
		} catch (Exception localException) {
			str = "0";
		}
		return str;
	}

	private Object readcollention(String paramString) {
		Object localObject = null;
		if (paramString != null)
			localObject = this.pageContext.getRequest().getAttribute(
					paramString);
		return localObject;
	}

	private Color createcolor(String paramString1, String paramString2) {
		Color localColor = null;
		int i = 0;
		int j = 0;
		int k = 0;
		String str = null;
		if (paramString1 != null) {
			if (paramString1.length() != 7) {
				if (paramString2 != null)
					localColor = createcolor(paramString2, null);
				else
					localColor = createcolor("#FFFFFF", null);
			} else {
				str = paramString1.substring(1, 3);
				i = readnumber(str);
				str = paramString1.substring(3, 5);
				j = readnumber(str);
				str = paramString1.substring(5, 7);
				k = readnumber(str);
				localColor = new Color(i, j, k);
			}
		} else if (paramString2 != null)
			localColor = createcolor(paramString2, null);
		else
			localColor = createcolor("#FFFFFF", null);
		return localColor;
	}

	private int readnumber(String paramString) {
		int i = 0;
		int j = 0;
		int k = 0;
		paramString = paramString.toUpperCase();
		j = readnumber2(paramString.substring(0, 1));
		k = readnumber2(paramString.substring(1, 2));
		i = j * 16 + k;
		return i;
	}

	private int readnumber2(String paramString) {
		int i = 0;
		try {
			i = Integer.parseInt(paramString);
		} catch (Exception localException) {
			if (paramString.equals("A"))
				i = 10;
			else if (paramString.equals("B"))
				i = 11;
			else if (paramString.equals("C"))
				i = 12;
			else if (paramString.equals("D"))
				i = 13;
			else if (paramString.equals("E"))
				i = 14;
			else if (paramString.equals("F"))
				i = 15;
			else
				i = 0;
		}
		return i;
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.chart.BaseChart JD-Core Version: 0.6.1
 */