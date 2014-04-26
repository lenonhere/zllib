package com.zl.base.core.taglib.chart.vml.chart;

import com.zl.base.core.taglib.chart.vml.VMLFillTag;
import com.zl.base.core.taglib.chart.vml.VMLGroupTag;
import com.zl.base.core.taglib.chart.vml.VMLLineTag;
import com.zl.base.core.taglib.chart.vml.VMLOExtrusTag;
import com.zl.base.core.taglib.chart.vml.VMLRectTag;
import com.zl.base.core.taglib.chart.vml.VMLShapeTag;
import com.zl.base.core.taglib.chart.vml.VMLSign;
import com.zl.base.core.taglib.chart.vml.VMLStrokeTag;
import com.zl.base.core.taglib.chart.vml.VMLTextBoxTag;
import com.zl.base.core.taglib.chart.vml.data.Axes;
import com.zl.base.core.taglib.chart.vml.data.Column;
import com.zl.base.core.taglib.chart.vml.data.DataSet;
import com.zl.base.core.util.ColorPicker;
import com.zl.base.core.util.DoublePoint;
import com.zl.base.core.util.DoubleRectangle;
import com.zl.base.core.util.DoubleSize;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class VMLChartBuilder {
	public static final int Z_INDEX_NULL = 0;
	public static final int DEFAULT_STROKE_WIDTH = 1;
	public static final double DEFAULT_CAPTION_HEIGHT = 30.0D;
	public static final double DEFAULT_FONT_SINGLE = 12.0D;
	public static final double DEFAULT_FONT_MERGE = 2.0D;
	public static final double DEFAULT_FONT_WIDTH = 40.0D;
	public static final double DEFAULT_FONT_HEIGHT = 18.0D;
	public static final double DEFAULT_BARSIGN_WIDTH = 8.0D;
	public static final double DEFAULT_LINESIGN_WIDTH = 36.0D;
	public static final double DEFAULT_LEGEND_HEIGHT = 30.0D;
	public static final double DEFAULT_LEGEND_WIDTH = 20.0D;

	public static String createStartGroup(DoubleRectangle paramDoubleRectangle,
			String paramString1, String paramString2, int paramInt) {
		VMLGroupTag localVMLGroupTag = new VMLGroupTag();
		localVMLGroupTag.InitVMLStyle(false);
		localVMLGroupTag.setID(paramString1);
		localVMLGroupTag.setAlt(paramString2);
		localVMLGroupTag.setZ_index(paramInt);
		localVMLGroupTag.AddAttribute(paramDoubleRectangle, new DoubleSize(
				paramDoubleRectangle.width, paramDoubleRectangle.height));
		return localVMLGroupTag.doStartTag();
	}

	public static String createEndGroup() {
		VMLGroupTag localVMLGroupTag = new VMLGroupTag();
		return localVMLGroupTag.doEndTag();
	}

	public static String createBackground(DoubleRectangle paramDoubleRectangle,
			String paramString1, String paramString2, boolean paramBoolean,
			int paramInt) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<!--背景信息输出开始-->\n");
		VMLRectTag localVMLRectTag = new VMLRectTag();
		VMLLineTag localVMLLineTag = new VMLLineTag();
		if (paramBoolean)
			localVMLRectTag.AddAttribute(paramDoubleRectangle, paramString1,
					paramString1);
		else
			localVMLRectTag.AddAttribute(paramDoubleRectangle, paramString2,
					paramString1);
		localVMLRectTag.setZ_index(paramInt);
		localStringBuffer.append(localVMLRectTag.doTag());
		if (paramBoolean) {
			ArrayList localArrayList = new ArrayList();
			DoublePoint localDoublePoint1 = new DoublePoint(
					paramDoubleRectangle.x, paramDoubleRectangle.y);
			DoublePoint localDoublePoint2 = new DoublePoint(
					paramDoubleRectangle.x, paramDoubleRectangle.y
							+ paramDoubleRectangle.height);
			localArrayList.add(localDoublePoint1);
			localArrayList.add(localDoublePoint2);
			localVMLLineTag.AddAttribute(localArrayList, 1.0D, null);
			localStringBuffer.append(localVMLLineTag.doTag());
			localArrayList.clear();
			localDoublePoint1.Set(paramDoubleRectangle.x,
					paramDoubleRectangle.y + paramDoubleRectangle.height);
			localDoublePoint2.Set(paramDoubleRectangle.x
					+ paramDoubleRectangle.width, paramDoubleRectangle.y
					+ paramDoubleRectangle.height);
			localVMLLineTag.AddAttribute(localArrayList, 1.0D, null);
			localStringBuffer.append(localVMLLineTag.doTag());
		}
		localStringBuffer.append("\n<!--背景信息输出结束-->\n");
		return localStringBuffer.toString();
	}

	public static String createCaption(DoubleRectangle paramDoubleRectangle,
			String paramString, int paramInt) {
		return createString(paramDoubleRectangle, paramString, 3, 14, true,
				"center", paramInt);
	}

	public static String createVAxes(DoubleRectangle doublerectangle,
			DataSet dataset, int i, double d, double d1, boolean flag, int j) {
		Axes axes = new Axes(dataset, i, d, d1, false);
		VMLLineTag vmllinetag = new VMLLineTag();
		StringBuffer stringbuffer = new StringBuffer();
		// stringbuffer.append("<!--\u5750\u6807\u8F74\u4FE1\u606F\u8F93\u51FA\u5F00\u59CB-->\n");
		stringbuffer.append("<!--坐标轴信息输出开始-->\n");
		double d2 = 0.0D;
		double d3 = 0.0D;
		double d4 = 0.0D;
		double d5 = 0.0D;
		d2 = doublerectangle.height / (double) (axes.m_VCaption.size() - 1);
		d3 = 0.0D;
		d4 = doublerectangle.y - 9D;
		d5 = doublerectangle.y;
		for (int k = 0; k < axes.m_VCaption.size(); k++) {
			DoubleRectangle doublerectangle1 = new DoubleRectangle(d3, d4,
					doublerectangle.x, 18D);
			stringbuffer.append(createString(doublerectangle1,
					(String) axes.m_VCaption.get(k), 3, 0, false, "right", j));
			if (k > 0 && k < axes.m_VCaption.size() - 1) {
				ArrayList arraylist1 = new ArrayList();
				arraylist1.add(new DoublePoint(doublerectangle.x + 1.0D, d5));
				arraylist1.add(new DoublePoint(doublerectangle.x
						+ doublerectangle.width, d5));
				vmllinetag.AddAttribute(arraylist1, 1.0D, "#CCCCCC");
				vmllinetag.setZ_index(j);
				stringbuffer.append(vmllinetag.doTag());
			}
			d4 += d2;
			d5 += d2;
		}

		d2 = doublerectangle.width / (double) axes.m_HCaption.size();
		d3 = doublerectangle.x;
		d4 = doublerectangle.y + doublerectangle.height;
		for (int l = 0; l < axes.m_HCaption.size(); l++) {
			if (flag) {
				ArrayList arraylist = new ArrayList();
				arraylist.add(new DoublePoint(d3, d4));
				arraylist.add(new DoublePoint(d3, d4 + 18D));
				vmllinetag.AddAttribute(arraylist, 1.0D, null);
				vmllinetag.setZ_index(j);
				stringbuffer.append(vmllinetag.doTag());
				if (l == axes.m_HCaption.size() - 1) {
					arraylist.clear();
					arraylist.add(new DoublePoint(d3 + d2, d4));
					arraylist.add(new DoublePoint(d3 + d2, d4 + 18D));
					vmllinetag.AddAttribute(arraylist, 1.0D, null);
					vmllinetag.setZ_index(j);
					stringbuffer.append(vmllinetag.doTag());
				}
			}
			DoubleRectangle doublerectangle2 = new DoubleRectangle(d3, d4, d2,
					18D);
			stringbuffer.append(createString(doublerectangle2,
					(String) axes.m_HCaption.get(l), 3, 0, false, "center", j));
			d3 += d2;
		}

		// stringbuffer.append("\n<!--\u5750\u6807\u8F74\u4FE1\u606F\u8F93\u51FA\u7ED3\u675F-->\n");
		stringbuffer.append("\n<!--坐标轴信息输出结束-->\n");
		return stringbuffer.toString();
	}

	// public static String createVAxes(DoubleRectangle paramDoubleRectangle,
	// DataSet paramDataSet, int paramInt1, double paramDouble1, double
	// paramDouble2, boolean paramBoolean, int paramInt2)
	// {
	// Axes localAxes = new Axes(paramDataSet, paramInt1, paramDouble1,
	// paramDouble2, false);
	// VMLLineTag localVMLLineTag = new VMLLineTag();
	// StringBuffer localStringBuffer = new StringBuffer();
	// localStringBuffer.append("<!--坐标轴信息输出开始-->\n");
	// double d1 = 0.0D;
	// double d2 = 0.0D;
	// double d3 = 0.0D;
	// double d4 = 0.0D;
	// d1 = paramDoubleRectangle.height / localAxes.m_VCaption.size() - 1;
	// d2 = 0.0D;
	// d3 = paramDoubleRectangle.y - 9.0D;
	// d4 = paramDoubleRectangle.y;
	// Object localObject;
	// for (int i = 0; i < localAxes.m_VCaption.size(); i++)
	// {
	// localObject = new DoubleRectangle(d2, d3, paramDoubleRectangle.x, 18.0D);
	// localStringBuffer.append(createString((DoubleRectangle)localObject,
	// (String)localAxes.m_VCaption.get(i), 3, 0, false, "right", paramInt2));
	// if ((i > 0) && (i < localAxes.m_VCaption.size() - 1))
	// {
	// ArrayList localArrayList = new ArrayList();
	// localArrayList.add(new DoublePoint(paramDoubleRectangle.x + 1.0D, d4));
	// localArrayList.add(new DoublePoint(paramDoubleRectangle.x +
	// paramDoubleRectangle.width, d4));
	// localVMLLineTag.AddAttribute(localArrayList, 1.0D, "#CCCCCC");
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// }
	// d3 += d1;
	// d4 += d1;
	// }
	// d1 = paramDoubleRectangle.width / localAxes.m_HCaption.size();
	// d2 = paramDoubleRectangle.x;
	// d3 = paramDoubleRectangle.y + paramDoubleRectangle.height;
	// for (i = 0; i < localAxes.m_HCaption.size(); i++)
	// {
	// if (paramBoolean)
	// {
	// localObject = new ArrayList();
	// ((ArrayList)localObject).add(new DoublePoint(d2, d3));
	// ((ArrayList)localObject).add(new DoublePoint(d2, d3 + 18.0D));
	// localVMLLineTag.AddAttribute((ArrayList)localObject, 1.0D, null);
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// if (i == localAxes.m_HCaption.size() - 1)
	// {
	// ((ArrayList)localObject).clear();
	// ((ArrayList)localObject).add(new DoublePoint(d2 + d1, d3));
	// ((ArrayList)localObject).add(new DoublePoint(d2 + d1, d3 + 18.0D));
	// localVMLLineTag.AddAttribute((ArrayList)localObject, 1.0D, null);
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// }
	// }
	// localObject = new DoubleRectangle(d2, d3, d1, 18.0D);
	// localStringBuffer.append(createString((DoubleRectangle)localObject,
	// (String)localAxes.m_HCaption.get(i), 3, 0, false, "center", paramInt2));
	// d2 += d1;
	// }
	// localStringBuffer.append("\n<!--坐标轴信息输出结束-->\n");
	// return localStringBuffer.toString();
	// }
	public static String createHAxes(DoubleRectangle doublerectangle,
			DataSet dataset, int i, double d, double d1, int j) {
		Axes axes = new Axes(dataset, i, d, d1, true);
		VMLLineTag vmllinetag = new VMLLineTag();
		StringBuffer stringbuffer = new StringBuffer();
		// stringbuffer.append("<!--\u5750\u6807\u8F74\u4FE1\u606F\u8F93\u51FA\u5F00\u59CB-->\n");
		stringbuffer.append("<!--坐标轴信息输出开始-->\n");
		double d2 = 0.0D;
		double d3 = 0.0D;
		double d4 = 0.0D;
		double d5 = 0.0D;
		d2 = doublerectangle.height / (double) axes.m_VCaption.size();
		d3 = 0.0D;
		d4 = (doublerectangle.y - 9D) + d2 / 2D;
		d5 = doublerectangle.y;
		for (int k = 0; k < axes.m_VCaption.size(); k++) {
			DoubleRectangle doublerectangle1 = new DoubleRectangle(d3, d4,
					doublerectangle.x, 18D);
			stringbuffer.append(createString(doublerectangle1,
					(String) axes.m_VCaption.get(k), 3, 0, false, "right", j));
			if (k > 0) {
				ArrayList arraylist = new ArrayList();
				arraylist.add(new DoublePoint(doublerectangle.x, d5));
				arraylist.add(new DoublePoint(doublerectangle.x + 4D, d5));
				vmllinetag.AddAttribute(arraylist, 1.0D, null);
				vmllinetag.setZ_index(j);
				stringbuffer.append(vmllinetag.doTag());
			}
			d4 += d2;
			d5 += d2;
		}

		d2 = doublerectangle.width / (double) (axes.m_HCaption.size() - 1);
		d3 = doublerectangle.x;
		d4 = doublerectangle.y + doublerectangle.height;
		for (int l = 0; l < axes.m_HCaption.size(); l++) {
			DoubleRectangle doublerectangle2 = new DoubleRectangle(
					d3 - d2 / 2D, d4, d2, 18D);
			stringbuffer
					.append(createString(
							doublerectangle2,
							(String) axes.m_HCaption.get(axes.m_HCaption.size()
									- l - 1), 3, 0, false, "center", j));
			if (l > 0 && l < axes.m_HCaption.size() - 1) {
				ArrayList arraylist1 = new ArrayList();
				arraylist1.add(new DoublePoint(d3, d4));
				arraylist1.add(new DoublePoint(d3, d4 - 4D));
				vmllinetag.AddAttribute(arraylist1, 1.0D, null);
				vmllinetag.setZ_index(j);
				stringbuffer.append(vmllinetag.doTag());
			}
			d3 += d2;
		}

		// stringbuffer.append("\n<!--\u5750\u6807\u8F74\u4FE1\u606F\u8F93\u51FA\u7ED3\u675F-->\n");
		stringbuffer.append("\n<!--坐标轴信息输出结束-->\n");
		return stringbuffer.toString();
	}

	// public static String createHAxes(DoubleRectangle paramDoubleRectangle,
	// DataSet paramDataSet, int paramInt1, double paramDouble1,
	// double paramDouble2, int paramInt2) {
	// Axes localAxes = new Axes(paramDataSet, paramInt1, paramDouble1,
	// paramDouble2, true);
	// VMLLineTag localVMLLineTag = new VMLLineTag();
	// StringBuffer localStringBuffer = new StringBuffer();
	// localStringBuffer.append("<!--坐标轴信息输出开始-->\n");
	// double d1 = 0.0D;
	// double d2 = 0.0D;
	// double d3 = 0.0D;
	// double d4 = 0.0D;
	// d1 = paramDoubleRectangle.height / localAxes.m_VCaption.size();
	// d2 = 0.0D;
	// d3 = paramDoubleRectangle.y - 9.0D + d1 / 2.0D;
	// d4 = paramDoubleRectangle.y;
	// DoubleRectangle localDoubleRectangle;
	// ArrayList localArrayList;
	// for (int i = 0; i < localAxes.m_VCaption.size(); i++) {
	// localDoubleRectangle = new DoubleRectangle(d2, d3,
	// paramDoubleRectangle.x, 18.0D);
	// localStringBuffer.append(createString(localDoubleRectangle,
	// (String) localAxes.m_VCaption.get(i), 3, 0, false, "right",
	// paramInt2));
	// if (i > 0) {
	// localArrayList = new ArrayList();
	// localArrayList.add(new DoublePoint(paramDoubleRectangle.x, d4));
	// localArrayList.add(new DoublePoint(
	// paramDoubleRectangle.x + 4.0D, d4));
	// localVMLLineTag.AddAttribute(localArrayList, 1.0D, null);
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// }
	// d3 += d1;
	// d4 += d1;
	// }
	// d1 = paramDoubleRectangle.width / localAxes.m_HCaption.size() - 1;
	// d2 = paramDoubleRectangle.x;
	// d3 = paramDoubleRectangle.y + paramDoubleRectangle.height;
	// for (i = 0; i < localAxes.m_HCaption.size(); i++) {
	// localDoubleRectangle = new DoubleRectangle(d2 - d1 / 2.0D, d3, d1,
	// 18.0D);
	// localStringBuffer
	// .append(createString(localDoubleRectangle,
	// (String) localAxes.m_HCaption
	// .get(localAxes.m_HCaption.size() - i - 1),
	// 3, 0, false, "center", paramInt2));
	// if ((i > 0) && (i < localAxes.m_HCaption.size() - 1)) {
	// localArrayList = new ArrayList();
	// localArrayList.add(new DoublePoint(d2, d3));
	// localArrayList.add(new DoublePoint(d2, d3 - 4.0D));
	// localVMLLineTag.AddAttribute(localArrayList, 1.0D, null);
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// }
	// d2 += d1;
	// }
	// localStringBuffer.append("\n<!--坐标轴信息输出结束-->\n");
	// return localStringBuffer.toString();
	// }
	public static String create3DVAxes(DoubleRectangle doublerectangle,
			DataSet dataset, int i, double d, double d1, String s,
			boolean flag, int j) {
		Axes axes = new Axes(dataset, i, d, d1, false);
		VMLLineTag vmllinetag = new VMLLineTag();
		VMLRectTag vmlrecttag = new VMLRectTag();
		double d2 = doublerectangle.width / (double) axes.m_HCaption.size()
				/ (double) (dataset.m_Caption.size() + 2);
		double d3 = d2 * 0.68400000000000005D;
		StringBuffer stringbuffer = new StringBuffer();
		// stringbuffer.append("<!--\u5750\u6807\u8F74\u4FE1\u606F\u8F93\u51FA\u5F00\u59CB-->\n");
		stringbuffer.append("<!--坐标轴信息输出开始-->\n");
		double d4 = 0.0D;
		double d5 = 0.0D;
		double d6 = 0.0D;
		double d7 = 0.0D;
		d4 = doublerectangle.height / (double) (axes.m_VCaption.size() - 1);
		d5 = 0.0D;
		d6 = doublerectangle.y - 9D;
		d7 = doublerectangle.y;
		vmlrecttag.AddAttribute(new DoubleRectangle(doublerectangle.x,
				doublerectangle.y - d3 * Math.sin(0.78539816339744828D),
				doublerectangle.width, d3 * Math.sin(0.78539816339744828D)), s,
				s);
		vmlrecttag.setZ_index(j);
		stringbuffer.append(vmlrecttag.doTag());
		ArrayList arraylist = new ArrayList();
		arraylist.add(new DoublePoint(doublerectangle.x + 1.0D + d3
				* Math.sin(0.78539816339744828D), doublerectangle.y - d3
				* Math.sin(0.78539816339744828D)));
		arraylist.add(new DoublePoint(doublerectangle.x + 1.0D + d3
				* Math.sin(0.78539816339744828D),
				(doublerectangle.y + doublerectangle.height) - d3
						* Math.sin(0.78539816339744828D)));
		vmllinetag.AddAttribute(arraylist, 1.0D, "#CCCCCC");
		vmllinetag.setZ_index(j);
		stringbuffer.append(vmllinetag.doTag());
		arraylist.clear();
		arraylist.add(new DoublePoint(doublerectangle.x, doublerectangle.y
				+ doublerectangle.height));
		arraylist.add(new DoublePoint(
				doublerectangle.x + doublerectangle.width, doublerectangle.y
						+ doublerectangle.height));
		vmllinetag.AddAttribute(arraylist, 1.0D, null);
		vmllinetag.setZ_index(j);
		stringbuffer.append(vmllinetag.doTag());
		for (int k = 0; k < axes.m_VCaption.size(); k++) {
			DoubleRectangle doublerectangle1 = new DoubleRectangle(d5, d6,
					doublerectangle.x, 18D);
			stringbuffer.append(createString(doublerectangle1,
					(String) axes.m_VCaption.get(k), 3, 0, false, "right", j));
			String s1 = "#CCCCCC";
			if (k == 0)
				s1 = null;
			ArrayList arraylist2 = new ArrayList();
			arraylist2.add(new DoublePoint(doublerectangle.x + 1.0D, d7));
			arraylist2.add(new DoublePoint(doublerectangle.x + 1.0D + d3
					* Math.sin(0.78539816339744828D), d7 - d3
					* Math.sin(0.78539816339744828D)));
			vmllinetag.AddAttribute(arraylist2, 1.0D, s1);
			vmllinetag.setZ_index(j);
			stringbuffer.append(vmllinetag.doTag());
			arraylist2.clear();
			arraylist2.add(new DoublePoint(doublerectangle.x + 1.0D + d3
					* Math.sin(0.78539816339744828D), d7 - d3
					* Math.sin(0.78539816339744828D)));
			arraylist2.add(new DoublePoint(doublerectangle.x
					+ doublerectangle.width, d7 - d3
					* Math.sin(0.78539816339744828D)));
			vmllinetag.AddAttribute(arraylist2, 1.0D, s1);
			vmllinetag.setZ_index(j);
			stringbuffer.append(vmllinetag.doTag());
			d6 += d4;
			d7 += d4;
		}

		d4 = doublerectangle.width / (double) axes.m_HCaption.size();
		d5 = doublerectangle.x;
		d6 = doublerectangle.y + doublerectangle.height;
		for (int l = 0; l < axes.m_HCaption.size(); l++) {
			if (flag) {
				ArrayList arraylist1 = new ArrayList();
				arraylist1.add(new DoublePoint(d5, d6));
				arraylist1.add(new DoublePoint(d5, d6 + 18D));
				vmllinetag.AddAttribute(arraylist1, 1.0D, null);
				vmllinetag.setZ_index(j);
				stringbuffer.append(vmllinetag.doTag());
				if (l == axes.m_HCaption.size() - 1) {
					arraylist1.clear();
					arraylist1.add(new DoublePoint(d5 + d4, d6));
					arraylist1.add(new DoublePoint(d5 + d4, d6 + 18D));
					vmllinetag.AddAttribute(arraylist1, 1.0D, null);
					vmllinetag.setZ_index(j);
					stringbuffer.append(vmllinetag.doTag());
				}
			}
			DoubleRectangle doublerectangle2 = new DoubleRectangle(d5, d6, d4,
					18D);
			stringbuffer.append(createString(doublerectangle2,
					(String) axes.m_HCaption.get(l), 3, 0, false, "center", j));
			d5 += d4;
		}

		// stringbuffer.append("\n<!--\u5750\u6807\u8F74\u4FE1\u606F\u8F93\u51FA\u7ED3\u675F-->\n");
		stringbuffer.append("\n<!--坐标轴信息输出结束-->\n");
		return stringbuffer.toString();
	}

	// public static String create3DVAxes(DoubleRectangle paramDoubleRectangle,
	// DataSet paramDataSet, int paramInt1, double paramDouble1,
	// double paramDouble2, String paramString, boolean paramBoolean,
	// int paramInt2) {
	// Axes localAxes = new Axes(paramDataSet, paramInt1, paramDouble1,
	// paramDouble2, false);
	// VMLLineTag localVMLLineTag = new VMLLineTag();
	// VMLRectTag localVMLRectTag = new VMLRectTag();
	// double d1 = paramDoubleRectangle.width / localAxes.m_HCaption.size()
	// / paramDataSet.m_Caption.size() + 2;
	// double d2 = d1 * 0.6840000000000001D;
	// StringBuffer localStringBuffer = new StringBuffer();
	// localStringBuffer.append("<!--坐标轴信息输出开始-->\n");
	// double d3 = 0.0D;
	// double d4 = 0.0D;
	// double d5 = 0.0D;
	// double d6 = 0.0D;
	// d3 = paramDoubleRectangle.height / localAxes.m_VCaption.size() - 1;
	// d4 = 0.0D;
	// d5 = paramDoubleRectangle.y - 9.0D;
	// d6 = paramDoubleRectangle.y;
	// localVMLRectTag.AddAttribute(
	// new DoubleRectangle(paramDoubleRectangle.x,
	// paramDoubleRectangle.y - d2
	// * Math.sin(0.7853981633974483D),
	// paramDoubleRectangle.width, d2
	// * Math.sin(0.7853981633974483D)), paramString,
	// paramString);
	// localVMLRectTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLRectTag.doTag());
	// ArrayList localArrayList1 = new ArrayList();
	// localArrayList1.add(new DoublePoint(paramDoubleRectangle.x + 1.0D + d2
	// * Math.sin(0.7853981633974483D), paramDoubleRectangle.y - d2
	// * Math.sin(0.7853981633974483D)));
	// localArrayList1.add(new DoublePoint(paramDoubleRectangle.x + 1.0D + d2
	// * Math.sin(0.7853981633974483D), paramDoubleRectangle.y
	// + paramDoubleRectangle.height - d2
	// * Math.sin(0.7853981633974483D)));
	// localVMLLineTag.AddAttribute(localArrayList1, 1.0D, "#CCCCCC");
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// localArrayList1.clear();
	// localArrayList1.add(new DoublePoint(paramDoubleRectangle.x,
	// paramDoubleRectangle.y + paramDoubleRectangle.height));
	// localArrayList1.add(new DoublePoint(paramDoubleRectangle.x
	// + paramDoubleRectangle.width, paramDoubleRectangle.y
	// + paramDoubleRectangle.height));
	// localVMLLineTag.AddAttribute(localArrayList1, 1.0D, null);
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// Object localObject;
	// for (int i = 0; i < localAxes.m_VCaption.size(); i++) {
	// localObject = new DoubleRectangle(d4, d5, paramDoubleRectangle.x,
	// 18.0D);
	// localStringBuffer.append(createString(
	// (DoubleRectangle) localObject,
	// (String) localAxes.m_VCaption.get(i), 3, 0, false, "right",
	// paramInt2));
	// String str = "#CCCCCC";
	// if (i == 0)
	// str = null;
	// ArrayList localArrayList2 = new ArrayList();
	// localArrayList2.add(new DoublePoint(paramDoubleRectangle.x + 1.0D,
	// d6));
	// localArrayList2.add(new DoublePoint(paramDoubleRectangle.x + 1.0D
	// + d2 * Math.sin(0.7853981633974483D), d6 - d2
	// * Math.sin(0.7853981633974483D)));
	// localVMLLineTag.AddAttribute(localArrayList2, 1.0D, str);
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// localArrayList2.clear();
	// localArrayList2.add(new DoublePoint(paramDoubleRectangle.x + 1.0D
	// + d2 * Math.sin(0.7853981633974483D), d6 - d2
	// * Math.sin(0.7853981633974483D)));
	// localArrayList2.add(new DoublePoint(paramDoubleRectangle.x
	// + paramDoubleRectangle.width, d6 - d2
	// * Math.sin(0.7853981633974483D)));
	// localVMLLineTag.AddAttribute(localArrayList2, 1.0D, str);
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// d5 += d3;
	// d6 += d3;
	// }
	// d3 = paramDoubleRectangle.width / localAxes.m_HCaption.size();
	// d4 = paramDoubleRectangle.x;
	// d5 = paramDoubleRectangle.y + paramDoubleRectangle.height;
	// for (i = 0; i < localAxes.m_HCaption.size(); i++) {
	// if (paramBoolean) {
	// localObject = new ArrayList();
	// ((ArrayList) localObject).add(new DoublePoint(d4, d5));
	// ((ArrayList) localObject).add(new DoublePoint(d4, d5 + 18.0D));
	// localVMLLineTag.AddAttribute((ArrayList) localObject, 1.0D,
	// null);
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// if (i == localAxes.m_HCaption.size() - 1) {
	// ((ArrayList) localObject).clear();
	// ((ArrayList) localObject).add(new DoublePoint(d4 + d3, d5));
	// ((ArrayList) localObject).add(new DoublePoint(d4 + d3,
	// d5 + 18.0D));
	// localVMLLineTag.AddAttribute((ArrayList) localObject, 1.0D,
	// null);
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// }
	// }
	// localObject = new DoubleRectangle(d4, d5, d3, 18.0D);
	// localStringBuffer.append(createString(
	// (DoubleRectangle) localObject,
	// (String) localAxes.m_HCaption.get(i), 3, 0, false,
	// "center", paramInt2));
	// d4 += d3;
	// }
	// localStringBuffer.append("\n<!--坐标轴信息输出结束-->\n");
	// return localStringBuffer.toString();
	// }
	public static String create3DHAxes(DoubleRectangle doublerectangle,
			DataSet dataset, int i, double d, double d1, String s, int j) {
		Axes axes = new Axes(dataset, i, d, d1, true);
		VMLLineTag vmllinetag = new VMLLineTag();
		VMLRectTag vmlrecttag = new VMLRectTag();
		double d2 = doublerectangle.height / (double) axes.m_VCaption.size()
				/ (double) (dataset.m_Caption.size() + 2);
		double d3 = d2 * 0.68400000000000005D;
		StringBuffer stringbuffer = new StringBuffer();
		// stringbuffer.append("<!--\u5750\u6807\u8F74\u4FE1\u606F\u8F93\u51FA\u5F00\u59CB-->\n");
		stringbuffer.append("<!--坐标轴信息输出开始-->\n");
		double d4 = 0.0D;
		double d5 = 0.0D;
		double d6 = 0.0D;
		double d7 = 0.0D;
		d4 = doublerectangle.height / (double) axes.m_VCaption.size();
		d5 = 0.0D;
		d6 = (doublerectangle.y - 9D) + d4 / 2D;
		d7 = doublerectangle.y;
		vmlrecttag.AddAttribute(new DoubleRectangle(doublerectangle.x,
				doublerectangle.y - d3 * Math.sin(0.78539816339744828D),
				doublerectangle.width, d3 * Math.sin(0.78539816339744828D)), s,
				s);
		vmlrecttag.setZ_index(j);
		stringbuffer.append(vmlrecttag.doTag());
		ArrayList arraylist = new ArrayList();
		arraylist.add(new DoublePoint(doublerectangle.x + 1.0D + d3
				* Math.sin(0.78539816339744828D), doublerectangle.y - d3
				* Math.sin(0.78539816339744828D)));
		arraylist.add(new DoublePoint(doublerectangle.x + 1.0D + d3
				* Math.sin(0.78539816339744828D),
				(doublerectangle.y + doublerectangle.height) - d3
						* Math.sin(0.78539816339744828D)));
		vmllinetag.AddAttribute(arraylist, 1.0D, "#CCCCCC");
		vmllinetag.setZ_index(j);
		stringbuffer.append(vmllinetag.doTag());
		arraylist.clear();
		arraylist.add(new DoublePoint(doublerectangle.x + 1.0D + d3
				* Math.sin(0.78539816339744828D),
				(doublerectangle.y + doublerectangle.height) - d3
						* Math.sin(0.78539816339744828D)));
		arraylist.add(new DoublePoint(
				doublerectangle.x + doublerectangle.width,
				(doublerectangle.y + doublerectangle.height) - d3
						* Math.sin(0.78539816339744828D)));
		vmllinetag.AddAttribute(arraylist, 1.0D, "#CCCCCC");
		vmllinetag.setZ_index(j);
		stringbuffer.append(vmllinetag.doTag());
		arraylist.clear();
		arraylist.add(new DoublePoint(doublerectangle.x + 1.0D + d3
				* Math.sin(0.78539816339744828D), doublerectangle.y - d3
				* Math.sin(0.78539816339744828D)));
		arraylist.add(new DoublePoint(
				doublerectangle.x + doublerectangle.width, doublerectangle.y
						- d3 * Math.sin(0.78539816339744828D)));
		vmllinetag.AddAttribute(arraylist, 1.0D, null);
		vmllinetag.setZ_index(j);
		stringbuffer.append(vmllinetag.doTag());
		for (int k = 0; k < axes.m_VCaption.size(); k++) {
			DoubleRectangle doublerectangle1 = new DoubleRectangle(d5, d6,
					doublerectangle.x, 18D);
			stringbuffer.append(createString(doublerectangle1,
					(String) axes.m_VCaption.get(k), 3, 0, false, "right", j));
			String s1 = "#CCCCCC";
			if (k == 0)
				s1 = null;
			ArrayList arraylist2 = new ArrayList();
			arraylist2.add(new DoublePoint(doublerectangle.x + 1.0D, d7));
			arraylist2.add(new DoublePoint(doublerectangle.x + 1.0D + d3
					* Math.sin(0.78539816339744828D), d7 - d3
					* Math.sin(0.78539816339744828D)));
			vmllinetag.AddAttribute(arraylist2, 1.0D, s1);
			vmllinetag.setZ_index(j);
			stringbuffer.append(vmllinetag.doTag());
			d6 += d4;
			d7 += d4;
		}

		d4 = doublerectangle.width / (double) (axes.m_HCaption.size() - 1);
		d5 = doublerectangle.x;
		d6 = doublerectangle.y + doublerectangle.height;
		for (int l = 0; l < axes.m_HCaption.size(); l++) {
			DoubleRectangle doublerectangle2 = new DoubleRectangle(
					d5 - d4 / 2D, d6, d4, 18D);
			stringbuffer
					.append(createString(
							doublerectangle2,
							(String) axes.m_HCaption.get(axes.m_HCaption.size()
									- l - 1), 3, 0, false, "center", j));
			if (l < axes.m_HCaption.size() - 1) {
				ArrayList arraylist1 = new ArrayList();
				arraylist1.add(new DoublePoint(d5, d6));
				arraylist1.add(new DoublePoint(d5 + d3
						* Math.sin(0.78539816339744828D), d6 - d3
						* Math.sin(0.78539816339744828D)));
				vmllinetag.AddAttribute(arraylist1, 1.0D, "#CCCCCC");
				vmllinetag.setZ_index(j);
				stringbuffer.append(vmllinetag.doTag());
			}
			d5 += d4;
		}

		// stringbuffer.append("\n<!--\u5750\u6807\u8F74\u4FE1\u606F\u8F93\u51FA\u7ED3\u675F-->\n");
		stringbuffer.append("\n<!--坐标轴信息输出结束-->\n");
		return stringbuffer.toString();
	}

	// public static String create3DHAxes(DoubleRectangle paramDoubleRectangle,
	// DataSet paramDataSet, int paramInt1, double paramDouble1,
	// double paramDouble2, String paramString, int paramInt2) {
	// Axes localAxes = new Axes(paramDataSet, paramInt1, paramDouble1,
	// paramDouble2, true);
	// VMLLineTag localVMLLineTag = new VMLLineTag();
	// VMLRectTag localVMLRectTag = new VMLRectTag();
	// double d1 = paramDoubleRectangle.height / localAxes.m_VCaption.size()
	// / paramDataSet.m_Caption.size() + 2;
	// double d2 = d1 * 0.6840000000000001D;
	// StringBuffer localStringBuffer = new StringBuffer();
	// localStringBuffer.append("<!--坐标轴信息输出开始-->\n");
	// double d3 = 0.0D;
	// double d4 = 0.0D;
	// double d5 = 0.0D;
	// double d6 = 0.0D;
	// d3 = paramDoubleRectangle.height / localAxes.m_VCaption.size();
	// d4 = 0.0D;
	// d5 = paramDoubleRectangle.y - 9.0D + d3 / 2.0D;
	// d6 = paramDoubleRectangle.y;
	// localVMLRectTag.AddAttribute(
	// new DoubleRectangle(paramDoubleRectangle.x,
	// paramDoubleRectangle.y - d2
	// * Math.sin(0.7853981633974483D),
	// paramDoubleRectangle.width, d2
	// * Math.sin(0.7853981633974483D)), paramString,
	// paramString);
	// localVMLRectTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLRectTag.doTag());
	// ArrayList localArrayList1 = new ArrayList();
	// localArrayList1.add(new DoublePoint(paramDoubleRectangle.x + 1.0D + d2
	// * Math.sin(0.7853981633974483D), paramDoubleRectangle.y - d2
	// * Math.sin(0.7853981633974483D)));
	// localArrayList1.add(new DoublePoint(paramDoubleRectangle.x + 1.0D + d2
	// * Math.sin(0.7853981633974483D), paramDoubleRectangle.y
	// + paramDoubleRectangle.height - d2
	// * Math.sin(0.7853981633974483D)));
	// localVMLLineTag.AddAttribute(localArrayList1, 1.0D, "#CCCCCC");
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// localArrayList1.clear();
	// localArrayList1.add(new DoublePoint(paramDoubleRectangle.x + 1.0D + d2
	// * Math.sin(0.7853981633974483D), paramDoubleRectangle.y
	// + paramDoubleRectangle.height - d2
	// * Math.sin(0.7853981633974483D)));
	// localArrayList1.add(new DoublePoint(paramDoubleRectangle.x
	// + paramDoubleRectangle.width, paramDoubleRectangle.y
	// + paramDoubleRectangle.height - d2
	// * Math.sin(0.7853981633974483D)));
	// localVMLLineTag.AddAttribute(localArrayList1, 1.0D, "#CCCCCC");
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// localArrayList1.clear();
	// localArrayList1.add(new DoublePoint(paramDoubleRectangle.x + 1.0D + d2
	// * Math.sin(0.7853981633974483D), paramDoubleRectangle.y - d2
	// * Math.sin(0.7853981633974483D)));
	// localArrayList1.add(new DoublePoint(paramDoubleRectangle.x
	// + paramDoubleRectangle.width, paramDoubleRectangle.y - d2
	// * Math.sin(0.7853981633974483D)));
	// localVMLLineTag.AddAttribute(localArrayList1, 1.0D, null);
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// DoubleRectangle localDoubleRectangle;
	// Object localObject;
	// for (int i = 0; i < localAxes.m_VCaption.size(); i++) {
	// localDoubleRectangle = new DoubleRectangle(d4, d5,
	// paramDoubleRectangle.x, 18.0D);
	// localStringBuffer.append(createString(localDoubleRectangle,
	// (String) localAxes.m_VCaption.get(i), 3, 0, false, "right",
	// paramInt2));
	// localObject = "#CCCCCC";
	// if (i == 0)
	// localObject = null;
	// ArrayList localArrayList2 = new ArrayList();
	// localArrayList2.add(new DoublePoint(paramDoubleRectangle.x + 1.0D,
	// d6));
	// localArrayList2.add(new DoublePoint(paramDoubleRectangle.x + 1.0D
	// + d2 * Math.sin(0.7853981633974483D), d6 - d2
	// * Math.sin(0.7853981633974483D)));
	// localVMLLineTag.AddAttribute(localArrayList2, 1.0D,
	// (String) localObject);
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// d5 += d3;
	// d6 += d3;
	// }
	// d3 = paramDoubleRectangle.width / localAxes.m_HCaption.size() - 1;
	// d4 = paramDoubleRectangle.x;
	// d5 = paramDoubleRectangle.y + paramDoubleRectangle.height;
	// for (i = 0; i < localAxes.m_HCaption.size(); i++) {
	// localDoubleRectangle = new DoubleRectangle(d4 - d3 / 2.0D, d5, d3,
	// 18.0D);
	// localStringBuffer
	// .append(createString(localDoubleRectangle,
	// (String) localAxes.m_HCaption
	// .get(localAxes.m_HCaption.size() - i - 1),
	// 3, 0, false, "center", paramInt2));
	// if (i < localAxes.m_HCaption.size() - 1) {
	// localObject = new ArrayList();
	// ((ArrayList) localObject).add(new DoublePoint(d4, d5));
	// ((ArrayList) localObject).add(new DoublePoint(d4 + d2
	// * Math.sin(0.7853981633974483D), d5 - d2
	// * Math.sin(0.7853981633974483D)));
	// localVMLLineTag.AddAttribute((ArrayList) localObject, 1.0D,
	// "#CCCCCC");
	// localVMLLineTag.setZ_index(paramInt2);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// }
	// d4 += d3;
	// }
	// localStringBuffer.append("\n<!--坐标轴信息输出结束-->\n");
	// return localStringBuffer.toString();
	// }
	public static String createTable(DoubleRectangle doublerectangle, double d,
			DataSet dataset, boolean flag, ColorPicker colorpicker,
			VMLSign vmlsign, int i) {
		StringBuffer stringbuffer = new StringBuffer();
		// stringbuffer.append("<!--\u660E\u7EC6\u8868\u683C\u4FE1\u606F\u8F93\u51FA\u5F00\u59CB-->\n");
		stringbuffer.append("<!--明细表格信息输出开始-->\n");
		VMLLineTag vmllinetag = new VMLLineTag();
		double d1 = doublerectangle.x - d;
		double d3 = doublerectangle.y + doublerectangle.height + 18D;
		double d4 = doublerectangle.width / (double) dataset.m_Caption.size();
		for (int j = 0; j <= dataset.m_Column.size(); j++) {
			ArrayList arraylist1 = new ArrayList();
			arraylist1.add(new DoublePoint(doublerectangle.x - d, d3));
			arraylist1.add(new DoublePoint(doublerectangle.x
					+ doublerectangle.width, d3));
			vmllinetag.AddAttribute(arraylist1, 1.0D, null);
			vmllinetag.setZ_index(i);
			stringbuffer.append(vmllinetag.doTag());
			d3 += 18D;
		}

		d1 = doublerectangle.x - d;
		ArrayList arraylist = new ArrayList();
		arraylist.add(new DoublePoint(d1, doublerectangle.y
				+ doublerectangle.height + 18D));
		arraylist.add(new DoublePoint(d1, doublerectangle.y
				+ doublerectangle.height + 18D
				* (double) (dataset.m_Column.size() + 1)));
		vmllinetag.AddAttribute(arraylist, 1.0D, null);
		vmllinetag.setZ_index(i);
		stringbuffer.append(vmllinetag.doTag());
		d1 = doublerectangle.x;
		for (int k = 0; k <= dataset.m_Caption.size(); k++) {
			ArrayList arraylist2 = new ArrayList();
			arraylist2.add(new DoublePoint(d1, doublerectangle.y
					+ doublerectangle.height + 18D));
			arraylist2.add(new DoublePoint(d1, doublerectangle.y
					+ doublerectangle.height + 18D
					* (double) (dataset.m_Column.size() + 1)));
			vmllinetag.AddAttribute(arraylist2, 1.0D, null);
			vmllinetag.setZ_index(i);
			stringbuffer.append(vmllinetag.doTag());
			d1 += d4;
		}

		d3 = doublerectangle.y + doublerectangle.height + 18D;
		for (int l = 0; l < dataset.m_Column.size(); l++) {
			double d2 = (doublerectangle.x - d) + 2D;
			Column column = (Column) dataset.m_Column.get(l);
			String s = column.GetID();
			String s1 = null;
			String s2 = column.GetCaption();
			String s3 = colorpicker.GetColor(l);
			String s4 = s3;
			String s5 = column.GetHref();
			String s6 = column.GetScript();
			int i1 = column.GetShapeType();
			String s7 = vmlsign.GetPath(l);
			DoubleRectangle doublerectangle1 = null;
			if (flag) {
				doublerectangle1 = new DoubleRectangle(d2, d3, 36D, 18D);
				stringbuffer.append(createLineSign(doublerectangle1, i1, s3,
						s4, s7, s, s1, s5, s6, i));
			} else {
				doublerectangle1 = new DoubleRectangle(d2, (d3 + 9D) - 4D, 8D,
						8D);
				stringbuffer.append(createSign(doublerectangle1, s3, s4, s, s1,
						s5, s6, i));
			}
			d2 = d2 + doublerectangle1.width + 2D;
			DoubleRectangle doublerectangle2 = new DoubleRectangle(d2, d3,
					doublerectangle.x - 2D - d2, 18D);
			stringbuffer.append(createString(doublerectangle2, s2, 2, 0, false,
					"left", i));
			d2 = doublerectangle.x;
			for (int j1 = 0; j1 < dataset.m_Row.size(); j1++) {
				ArrayList arraylist3 = (ArrayList) dataset.m_Row.get(j1);
				String s8 = (String) arraylist3.get(l);
				DoubleRectangle doublerectangle3 = new DoubleRectangle(d2, d3,
						d4, 18D);
				stringbuffer.append(createString(doublerectangle3, s8, 3, 0,
						false, "center", i));
				d2 += d4;
			}

			d3 += 18D;
		}

		// stringbuffer.append("\n<!--\u660E\u7EC6\u8868\u683C\u4FE1\u606F\u8F93\u51FA\u7ED3\u675F-->\n");
		stringbuffer.append("\n<!--明细表格信息输出结束-->\n");
		return stringbuffer.toString();
	}

	// public static String createTable(DoubleRectangle paramDoubleRectangle,
	// double paramDouble, DataSet paramDataSet, boolean paramBoolean,
	// ColorPicker paramColorPicker, VMLSign paramVMLSign, int paramInt) {
	// StringBuffer localStringBuffer = new StringBuffer();
	// localStringBuffer.append("<!--明细表格信息输出开始-->\n");
	// VMLLineTag localVMLLineTag = new VMLLineTag();
	// double d1 = paramDoubleRectangle.x - paramDouble;
	// double d2 = paramDoubleRectangle.y + paramDoubleRectangle.height
	// + 18.0D;
	// double d3 = paramDoubleRectangle.width / paramDataSet.m_Caption.size();
	// for (int i = 0; i <= paramDataSet.m_Column.size(); i++) {
	// ArrayList localArrayList2 = new ArrayList();
	// localArrayList2.add(new DoublePoint(paramDoubleRectangle.x
	// - paramDouble, d2));
	// localArrayList2.add(new DoublePoint(paramDoubleRectangle.x
	// + paramDoubleRectangle.width, d2));
	// localVMLLineTag.AddAttribute(localArrayList2, 1.0D, null);
	// localVMLLineTag.setZ_index(paramInt);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// d2 += 18.0D;
	// }
	// d1 = paramDoubleRectangle.x - paramDouble;
	// ArrayList localArrayList1 = new ArrayList();
	// localArrayList1.add(new DoublePoint(d1, paramDoubleRectangle.y
	// + paramDoubleRectangle.height + 18.0D));
	// localArrayList1.add(new DoublePoint(d1, paramDoubleRectangle.y
	// + paramDoubleRectangle.height + 18.0D
	// * paramDataSet.m_Column.size() + 1));
	// localVMLLineTag.AddAttribute(localArrayList1, 1.0D, null);
	// localVMLLineTag.setZ_index(paramInt);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// d1 = paramDoubleRectangle.x;
	// Object localObject;
	// for (int j = 0; j <= paramDataSet.m_Caption.size(); j++) {
	// localObject = new ArrayList();
	// ((ArrayList) localObject).add(new DoublePoint(d1,
	// paramDoubleRectangle.y + paramDoubleRectangle.height
	// + 18.0D));
	// ((ArrayList) localObject).add(new DoublePoint(d1,
	// paramDoubleRectangle.y + paramDoubleRectangle.height
	// + 18.0D * paramDataSet.m_Column.size() + 1));
	// localVMLLineTag.AddAttribute((ArrayList) localObject, 1.0D, null);
	// localVMLLineTag.setZ_index(paramInt);
	// localStringBuffer.append(localVMLLineTag.doTag());
	// d1 += d3;
	// }
	// d2 = paramDoubleRectangle.y + paramDoubleRectangle.height + 18.0D;
	// for (j = 0; j < paramDataSet.m_Column.size(); j++) {
	// d1 = paramDoubleRectangle.x - paramDouble + 2.0D;
	// localObject = (Column) paramDataSet.m_Column.get(j);
	// String str1 = ((Column) localObject).GetID();
	// String str2 = null;
	// String str3 = ((Column) localObject).GetCaption();
	// String str4 = paramColorPicker.GetColor(j);
	// String str5 = str4;
	// String str6 = ((Column) localObject).GetHref();
	// String str7 = ((Column) localObject).GetScript();
	// int k = ((Column) localObject).GetShapeType();
	// String str8 = paramVMLSign.GetPath(j);
	// DoubleRectangle localDoubleRectangle1 = null;
	// if (paramBoolean) {
	// localDoubleRectangle1 = new DoubleRectangle(d1, d2, 36.0D,
	// 18.0D);
	// localStringBuffer.append(createLineSign(localDoubleRectangle1,
	// k, str4, str5, str8, str1, str2, str6, str7, paramInt));
	// } else {
	// localDoubleRectangle1 = new DoubleRectangle(d1,
	// d2 + 9.0D - 4.0D, 8.0D, 8.0D);
	// localStringBuffer.append(createSign(localDoubleRectangle1,
	// str4, str5, str1, str2, str6, str7, paramInt));
	// }
	// d1 = d1 + localDoubleRectangle1.width + 2.0D;
	// DoubleRectangle localDoubleRectangle2 = new DoubleRectangle(d1, d2,
	// paramDoubleRectangle.x - 2.0D - d1, 18.0D);
	// localStringBuffer.append(createString(localDoubleRectangle2, str3,
	// 2, 0, false, "left", paramInt));
	// d1 = paramDoubleRectangle.x;
	// for (int m = 0; m < paramDataSet.m_Row.size(); m++) {
	// ArrayList localArrayList3 = (ArrayList) paramDataSet.m_Row
	// .get(m);
	// String str9 = (String) localArrayList3.get(j);
	// DoubleRectangle localDoubleRectangle3 = new DoubleRectangle(d1,
	// d2, d3, 18.0D);
	// localStringBuffer.append(createString(localDoubleRectangle3,
	// str9, 3, 0, false, "center", paramInt));
	// d1 += d3;
	// }
	// d2 += 18.0D;
	// }
	// localStringBuffer.append("\n<!--明细表格信息输出结束-->\n");
	// return localStringBuffer.toString();
	// }

	public static String createVLegend(DoubleRectangle paramDoubleRectangle,
			DataSet paramDataSet, ColorPicker paramColorPicker,
			double paramDouble, int paramInt) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<!--图例信息输出开始-->\n");
		VMLGroupTag localVMLGroupTag = new VMLGroupTag();
		VMLRectTag localVMLRectTag = new VMLRectTag();
		localVMLGroupTag.InitVMLStyle(true);
		localVMLGroupTag.AddAttribute(paramDoubleRectangle, new DoubleSize(
				paramDoubleRectangle.width, paramDoubleRectangle.height));
		localVMLGroupTag.setZ_index(paramInt);
		localStringBuffer.append(localVMLGroupTag.doStartTag());
		localVMLRectTag.AddAttribute(new DoubleRectangle(0.0D, 0.0D,
				paramDoubleRectangle.width, paramDoubleRectangle.height), null,
				null);
		localStringBuffer.append(localVMLRectTag.doTag());
		double d = 0.0D;
		for (int i = 0; i < paramDataSet.m_Column.size(); i++) {
			Column localColumn = (Column) paramDataSet.m_Column.get(i);
			String str = localColumn.GetCaption();
			DoubleRectangle localDoubleRectangle1 = new DoubleRectangle(2.0D,
					d, paramDouble + 2.0D, 30.0D);
			localStringBuffer.append(createString(localDoubleRectangle1, str,
					2, 0, false, "left", 1));
			DoubleRectangle localDoubleRectangle2 = new DoubleRectangle(
					paramDoubleRectangle.width
							- (paramDoubleRectangle.width - 4.0D - paramDouble)
							/ 2.0D - 15.0D - 2.0D, d + 15.0D - 10.0D, 30.0D,
					20.0D);
			localStringBuffer.append(createSign(localDoubleRectangle2,
					paramColorPicker.GetColor(i), null, null, str, null, null,
					1));
			d += 30.0D;
		}
		localStringBuffer.append(localVMLGroupTag.doEndTag());
		localStringBuffer.append("\n<!--图例信息输出结束-->\n");
		return localStringBuffer.toString();
	}

	public static String createHLegend(DoubleRectangle paramDoubleRectangle,
			DataSet paramDataSet, ColorPicker paramColorPicker,
			VMLSign paramVMLSign, double paramDouble, boolean paramBoolean,
			int paramInt) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<!--图例信息输出开始-->\n");
		VMLGroupTag localVMLGroupTag = new VMLGroupTag();
		VMLRectTag localVMLRectTag = new VMLRectTag();
		localVMLGroupTag.InitVMLStyle(true);
		localVMLGroupTag.AddAttribute(paramDoubleRectangle, new DoubleSize(
				paramDoubleRectangle.width, paramDoubleRectangle.height));
		localVMLGroupTag.setZ_index(paramInt);
		localStringBuffer.append(localVMLGroupTag.doStartTag());
		localVMLRectTag.AddAttribute(new DoubleRectangle(0.0D, 0.0D,
				paramDoubleRectangle.width, paramDoubleRectangle.height), null,
				null);
		localStringBuffer.append(localVMLRectTag.doTag());
		double d1 = 0.0D;
		double d2 = 0.0D;
		double d3 = 14.0D + paramDouble;
		if (paramBoolean)
			d3 = 42.0D + paramDouble;
		int i = (int) (paramDoubleRectangle.width / d3);
		if (i < 1)
			i = 1;
		int j = 0;
		while (j < paramDataSet.m_Column.size()) {
			d1 = 0.0D;
			for (int k = 0; (k < i) && (j + k < paramDataSet.m_Column.size()); k++) {
				Column localColumn = (Column) paramDataSet.m_Column.get(j + k);
				String str1 = localColumn.GetCaption();
				int m = localColumn.GetShapeType();
				String str2 = paramVMLSign.GetPath(j + k);
				DoubleRectangle localDoubleRectangle1 = null;
				if (paramBoolean) {
					localDoubleRectangle1 = new DoubleRectangle(d1 + 2.0D, d2,
							36.0D, 18.0D);
					localStringBuffer.append(createLineSign(
							localDoubleRectangle1, m,
							paramColorPicker.GetColor(j + k),
							paramColorPicker.GetColor(j + k), str2, null, str1,
							null, null, 1));
				} else {
					localDoubleRectangle1 = new DoubleRectangle(d1 + 2.0D,
							d2 + 9.0D - 4.0D, 8.0D, 8.0D);
					localStringBuffer.append(createSign(localDoubleRectangle1,
							paramColorPicker.GetColor(j + k), null, null, str1,
							null, null, 1));
				}
				DoubleRectangle localDoubleRectangle2 = null;
				if (paramBoolean)
					localDoubleRectangle2 = new DoubleRectangle(
							d1 + 4.0D + 36.0D, d2, paramDouble, 18.0D);
				else
					localDoubleRectangle2 = new DoubleRectangle(
							d1 + 4.0D + 8.0D, d2, paramDouble, 18.0D);
				localStringBuffer.append(createString(localDoubleRectangle2,
						str1, 3, 0, false, "left", 1));
				d1 += d3;
			}
			d2 += 18.0D;
			j += i;
		}
		localStringBuffer.append(localVMLGroupTag.doEndTag());
		localStringBuffer.append("\n<!--图例信息输出结束-->\n");
		return localStringBuffer.toString();
	}

	public static String createLine(DoubleRectangle paramDoubleRectangle,
			DataSet paramDataSet, double paramDouble1, double paramDouble2,
			ColorPicker paramColorPicker, VMLSign paramVMLSign, int paramInt) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<!--折线数据输出开始-->\n");
		VMLLineTag localVMLLineTag = new VMLLineTag();
		VMLStrokeTag localVMLStrokeTag = new VMLStrokeTag();
		VMLShapeTag localVMLShapeTag = new VMLShapeTag();
		double d1 = paramDoubleRectangle.width / paramDataSet.m_Caption.size();
		double d2 = paramDoubleRectangle.x + d1 / 2.0D;
		double d3 = paramDoubleRectangle.y + paramDoubleRectangle.height;
		for (int i = 0; i < paramDataSet.m_Column.size(); i++) {
			Column localColumn = (Column) paramDataSet.m_Column.get(i);
			String str1 = localColumn.GetID();
			String str2 = localColumn.GetCaption();
			String str3 = localColumn.GetHref();
			String str4 = localColumn.GetScript();
			int j = localColumn.GetShapeType();
			boolean bool = localColumn.GetSign();
			String str5 = paramColorPicker.GetColor(i);
			String str6 = paramVMLSign.GetPath(i);
			d2 = paramDoubleRectangle.x + d1 / 2.0D;
			ArrayList localArrayList1 = new ArrayList();
			for (int k = 0; k < paramDataSet.m_Row.size(); k++) {
				ArrayList localArrayList2 = (ArrayList) paramDataSet.m_Row
						.get(k);
				String str7 = (String) localArrayList2.get(i);
				if (str7.trim().length() > 0) {
					double d4 = Double.valueOf(str7).doubleValue();
					d3 = paramDoubleRectangle.y + paramDoubleRectangle.height
							* (paramDouble1 - d4)
							/ (paramDouble1 - paramDouble2);
					DoublePoint localDoublePoint = new DoublePoint(d2, d3);
					localArrayList1.add(localDoublePoint);
					if (bool) {
						DoubleRectangle localDoubleRectangle = new DoubleRectangle(
								localDoublePoint.x, localDoublePoint.y, 8.0D,
								8.0D);
						localVMLShapeTag.AddAttribute(localDoubleRectangle,
								str5, str5, str6);
						localVMLShapeTag.setAlt(new DecimalFormat("0.##")
								.format(d4));
						localVMLShapeTag.setZ_index(paramInt + 1);
						localStringBuffer.append(localVMLShapeTag.doTag());
					}
				}
				d2 += d1;
			}
			localVMLLineTag.AddAttribute(localArrayList1, 1.5D, str5);
			localVMLLineTag.setZ_index(paramInt);
			localStringBuffer.append(localVMLLineTag.doStartTag());
			if (j > 0) {
				localVMLStrokeTag.AddAttribute("dot",
						VMLStrokeTag.VML_STROKE_ARROW_NULL,
						VMLStrokeTag.VML_STROKE_ARROW_NULL);
				localStringBuffer.append(localVMLStrokeTag.doTag());
			}
			localStringBuffer.append(localVMLLineTag.doEndTag());
		}
		localStringBuffer.append("\n<!--折线数据输出结束-->\n");
		return localStringBuffer.toString();
	}

	public static String createVBar(DoubleRectangle paramDoubleRectangle,
			DataSet paramDataSet, double paramDouble1, double paramDouble2,
			ColorPicker paramColorPicker, int paramInt) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<!--柱图数据输出开始-->\n");
		double d1 = paramDoubleRectangle.width / paramDataSet.m_Caption.size();
		double d2 = d1 / paramDataSet.m_Column.size() + 2;
		double d3 = paramDoubleRectangle.x;
		double d4 = paramDoubleRectangle.y + paramDoubleRectangle.height;
		for (int i = 0; i < paramDataSet.m_Caption.size(); i++) {
			ArrayList localArrayList = (ArrayList) paramDataSet.m_Row.get(i);
			for (int j = 0; j < paramDataSet.m_Column.size(); j++) {
				Column localColumn = (Column) paramDataSet.m_Column.get(j);
				String str1 = localColumn.GetID();
				String str2 = localColumn.GetCaption();
				String str3 = localColumn.GetHref();
				String str4 = localColumn.GetScript();
				String str5 = (String) localArrayList.get(j);
				if (str5.trim().length() > 0) {
					double d5 = Double.valueOf(str5).doubleValue();
					d4 = paramDoubleRectangle.y + paramDoubleRectangle.height
							* (paramDouble1 - d5)
							/ (paramDouble1 - paramDouble2);
					DoubleRectangle localDoubleRectangle = new DoubleRectangle(
							d3 + j + 1 * d2, d4, d2, paramDoubleRectangle.y
									+ paramDoubleRectangle.height - d4);
					String str6 = paramColorPicker.GetColor(j);
					String str7 = paramColorPicker.GetMaskColor(j);
					localStringBuffer.append(createCube(localDoubleRectangle,
							"-90", str6, str7, null, str1, new DecimalFormat(
									"0.##").format(d5), str3, str4, paramInt));
				}
			}
			d3 += d1;
		}
		localStringBuffer.append("\n<!--柱图数据输出结束-->\n");
		return localStringBuffer.toString();
	}

	public static String createHBar(DoubleRectangle paramDoubleRectangle,
			DataSet paramDataSet, double paramDouble1, double paramDouble2,
			ColorPicker paramColorPicker, boolean paramBoolean, int paramInt) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<!--柱图数据输出开始-->\n");
		double d1 = paramDoubleRectangle.height / paramDataSet.m_Caption.size();
		double d2 = d1 / paramDataSet.m_Column.size() + 2;
		double d3 = paramDoubleRectangle.x;
		double d4 = paramDoubleRectangle.y;
		for (int i = 0; i < paramDataSet.m_Caption.size(); i++) {
			ArrayList localArrayList = (ArrayList) paramDataSet.m_Row.get(i);
			for (int j = 0; j < paramDataSet.m_Column.size(); j++) {
				Column localColumn = (Column) paramDataSet.m_Column.get(j);
				String str1 = localColumn.GetID();
				String str2 = localColumn.GetCaption();
				String str3 = localColumn.GetHref();
				String str4 = localColumn.GetScript();
				String str5 = (String) localArrayList.get(j);
				if (str5.trim().length() > 0) {
					double d5 = Double.valueOf(str5).doubleValue();
					double d6 = paramDoubleRectangle.width
							* (d5 - paramDouble2)
							/ (paramDouble1 - paramDouble2);
					DoubleRectangle localDoubleRectangle1 = new DoubleRectangle(
							d3, d4 + j + 1 * d2, d6, d2);
					String str6 = paramColorPicker.GetColor(j);
					String str7 = paramColorPicker.GetMaskColor(j);
					localStringBuffer.append(createCube(localDoubleRectangle1,
							null, str6, str7, null, str1, new DecimalFormat(
									"0.##").format(d5), str3, str4, paramInt));
					if (paramBoolean) {
						DoubleRectangle localDoubleRectangle2 = new DoubleRectangle(
								d3 + d6, d4 + j + 1 * d2, d6, d2);
						localStringBuffer.append(createString(
								localDoubleRectangle2,
								new DecimalFormat("0.##").format(d5), 0, 0,
								false, "left", paramInt));
					}
				}
			}
			d4 += d1;
		}
		localStringBuffer.append("\n<!--柱图数据输出结束-->\n");
		return localStringBuffer.toString();
	}

	public static String create3DVBar(DoubleRectangle paramDoubleRectangle,
			DataSet paramDataSet, double paramDouble1, double paramDouble2,
			ColorPicker paramColorPicker, VMLSign paramVMLSign, int paramInt) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<!--柱图数据输出开始-->\n");
		double d1 = paramDoubleRectangle.width / paramDataSet.m_Caption.size();
		double d2 = d1 / paramDataSet.m_Column.size() + 2;
		double d3 = paramDoubleRectangle.x;
		double d4 = paramDoubleRectangle.y + paramDoubleRectangle.height;
		for (int i = 0; i < paramDataSet.m_Caption.size(); i++) {
			ArrayList localArrayList = (ArrayList) paramDataSet.m_Row.get(i);
			for (int j = 0; j < paramDataSet.m_Column.size(); j++) {
				Column localColumn = (Column) paramDataSet.m_Column.get(j);
				String str1 = localColumn.GetID();
				String str2 = localColumn.GetCaption();
				String str3 = localColumn.GetHref();
				String str4 = localColumn.GetScript();
				String str5 = (String) localArrayList.get(j);
				if (str5.trim().length() > 0) {
					double d5 = Double.valueOf(str5).doubleValue();
					d4 = paramDoubleRectangle.y + paramDoubleRectangle.height
							* (paramDouble1 - d5)
							/ (paramDouble1 - paramDouble2);
					DoubleRectangle localDoubleRectangle = new DoubleRectangle(
							d3 + j + 1 * d2, d4, d2, paramDoubleRectangle.y
									+ paramDoubleRectangle.height - d4);
					String str6 = paramColorPicker.GetColor(j);
					String str7 = paramColorPicker.GetMaskColor(j);
					String str8 = paramColorPicker.GetShadowColor(j);
					localStringBuffer.append(createCube(localDoubleRectangle,
							"-90", str6, str7, str8, str1, new DecimalFormat(
									"0.##").format(d5), str3, str4, paramInt));
				}
			}
			d3 += d1;
		}
		localStringBuffer.append("\n<!--柱图数据输出结束-->\n");
		return localStringBuffer.toString();
	}

	public static String create3DHBar(DoubleRectangle paramDoubleRectangle,
			DataSet paramDataSet, double paramDouble1, double paramDouble2,
			ColorPicker paramColorPicker, boolean paramBoolean, int paramInt) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<!--柱图数据输出开始-->\n");
		double d1 = paramDoubleRectangle.height / paramDataSet.m_Caption.size();
		double d2 = d1 / paramDataSet.m_Column.size() + 2;
		double d3 = paramDoubleRectangle.x;
		double d4 = paramDoubleRectangle.y;
		for (int i = 0; i < paramDataSet.m_Caption.size(); i++) {
			ArrayList localArrayList = (ArrayList) paramDataSet.m_Row.get(i);
			for (int j = 0; j < paramDataSet.m_Column.size(); j++) {
				Column localColumn = (Column) paramDataSet.m_Column.get(j);
				String str1 = localColumn.GetID();
				String str2 = localColumn.GetCaption();
				String str3 = localColumn.GetHref();
				String str4 = localColumn.GetScript();
				String str5 = (String) localArrayList.get(j);
				if (str5.trim().length() > 0) {
					double d5 = Double.valueOf(str5).doubleValue();
					double d6 = paramDoubleRectangle.width
							* (d5 - paramDouble2)
							/ (paramDouble1 - paramDouble2);
					DoubleRectangle localDoubleRectangle1 = new DoubleRectangle(
							d3, d4 + j + 1 * d2, d6, d2);
					String str6 = paramColorPicker.GetColor(j);
					String str7 = paramColorPicker.GetMaskColor(j);
					String str8 = paramColorPicker.GetShadowColor(j);
					localStringBuffer.append(createCube(localDoubleRectangle1,
							null, str6, str7, str8, str1, new DecimalFormat(
									"0.##").format(d5), str3, str4, paramInt
									+ paramDataSet.m_Column.size() - j));
					if (paramBoolean) {
						DoubleRectangle localDoubleRectangle2 = new DoubleRectangle(
								d3 + d6 + d2 * 0.6840000000000001D
										* Math.sin(0.7853981633974483D), d4 + j
										+ 1 * d2, d6, d2);
						localStringBuffer.append(createString(
								localDoubleRectangle2,
								new DecimalFormat("0.##").format(d5), 0, 0,
								false, "left", paramInt));
					}
				}
			}
			d4 += d1;
		}
		localStringBuffer.append("\n<!--柱图数据输出结束-->\n");
		return localStringBuffer.toString();
	}

	public static String createPie(DoubleRectangle doublerectangle,
			DataSet dataset, ColorPicker colorpicker, boolean flag, int i) {
		StringBuffer stringbuffer = new StringBuffer();
		// stringbuffer.append("<!--\u997C\u56FE\u6570\u636E\u8F93\u51FA\u5F00\u59CB-->\n");
		stringbuffer.append("<!--饼图数据输出开始-->\n");
		ArrayList arraylist = (ArrayList) dataset.m_Row.get(0);
		double d = 0.0D;
		for (int j = 0; j < arraylist.size(); j++) {
			String s = (String) arraylist.get(j);
			double d3 = Double.valueOf(s).doubleValue();
			d += d3;
		}

		double d1 = 0.0D;
		double d4 = 0.0D;
		double d5 = 6000D;
		DoublePoint doublepoint = new DoublePoint(6000D, 6000D);
		doublepoint.x = 6000D;
		doublepoint.y = 6000D;
		DoublePoint doublepoint1 = new DoublePoint(12000D, 6000D);
		doublepoint1.x = 12000D;
		doublepoint1.y = 6000D;
		DoublePoint doublepoint2 = new DoublePoint(12000D, 5999D);
		for (int k = 0; k < arraylist.size(); k++) {
			Column column = (Column) dataset.m_Column.get(k);
			String s1 = column.GetCaption();
			String s2 = column.GetID();
			String s3 = column.GetHref();
			String s4 = column.GetScript();
			String s5 = (String) arraylist.get(k);
			double d6 = Double.valueOf(s5).doubleValue();
			StringBuffer stringbuffer1 = new StringBuffer();
			stringbuffer1.append("[");
			stringbuffer1.append(s1);
			stringbuffer1.append("]");
			stringbuffer1.append("\u6570\u91CF:");
			stringbuffer1.append((new DecimalFormat("0.##")).format(d6));
			stringbuffer1.append(" \u767E\u5206\u6BD4:");
			stringbuffer1.append((new DecimalFormat("0.##"))
					.format((d6 / d) * 100D));
			stringbuffer1.append("%");
			double d2 = d6 / d;
			d4 += d2;
			double d7 = 6.2831853071795862D * d4;
			if (d4 <= 0.25D) {
				doublepoint2.x = d5 + d5 * Math.cos(d7);
				doublepoint2.y = d5 - d5 * Math.sin(d7);
			} else if (d4 > 0.25D && d4 <= 0.5D) {
				d7 = 3.1415926535897931D - d7;
				doublepoint2.x = d5 - d5 * Math.cos(d7);
				doublepoint2.y = d5 - d5 * Math.sin(d7);
			} else if (d4 > 0.5D && d4 <= 0.75D) {
				d7 -= 3.1415926535897931D;
				doublepoint2.x = d5 - d5 * Math.cos(d7);
				doublepoint2.y = d5 + d5 * Math.sin(d7);
			} else {
				d7 = 6.2831853071795862D - d7;
				doublepoint2.x = d5 + d5 * Math.cos(d7);
				doublepoint2.y = d5 + d5 * Math.sin(d7);
			}
			stringbuffer.append(createArc(doublerectangle, doublepoint,
					doublepoint1, doublepoint2, colorpicker.GetColor(k),
					colorpicker.GetMaskColor(k), null, s2,
					stringbuffer1.toString(), s3, s4, i));
			doublepoint1.Set(doublepoint2);
		}

		// stringbuffer.append("\n<!--\u997C\u56FE\u6570\u636E\u8F93\u51FA\u7ED3\u675F-->\n");
		stringbuffer.append("\n<!--饼图数据输出结束-->\n");
		return stringbuffer.toString();
	}

	// public static String createPie(DoubleRectangle paramDoubleRectangle,
	// DataSet paramDataSet, ColorPicker paramColorPicker,
	// boolean paramBoolean, int paramInt) {
	// StringBuffer localStringBuffer1 = new StringBuffer();
	// localStringBuffer1.append("<!--饼图数据输出开始-->\n");
	// ArrayList localArrayList = (ArrayList) paramDataSet.m_Row.get(0);
	// double d1 = 0.0D;
	// for (int i = 0; i < localArrayList.size(); i++) {
	// String str1 = (String) localArrayList.get(i);
	// d3 = Double.valueOf(str1).doubleValue();
	// d1 += d3;
	// }
	// double d2 = 0.0D;
	// double d3 = 0.0D;
	// double d4 = 6000.0D;
	// DoublePoint localDoublePoint1 = new DoublePoint(6000.0D, 6000.0D);
	// localDoublePoint1.x = 6000.0D;
	// localDoublePoint1.y = 6000.0D;
	// DoublePoint localDoublePoint2 = new DoublePoint(12000.0D, 6000.0D);
	// localDoublePoint2.x = 12000.0D;
	// localDoublePoint2.y = 6000.0D;
	// DoublePoint localDoublePoint3 = new DoublePoint(12000.0D, 5999.0D);
	// for (int j = 0; j < localArrayList.size(); j++) {
	// Column localColumn = (Column) paramDataSet.m_Column.get(j);
	// String str2 = localColumn.GetCaption();
	// String str3 = localColumn.GetID();
	// String str4 = localColumn.GetHref();
	// String str5 = localColumn.GetScript();
	// String str6 = (String) localArrayList.get(j);
	// double d5 = Double.valueOf(str6).doubleValue();
	// StringBuffer localStringBuffer2 = new StringBuffer();
	// localStringBuffer2.append("[");
	// localStringBuffer2.append(str2);
	// localStringBuffer2.append("]");
	// localStringBuffer2.append("数量:");
	// localStringBuffer2.append(new DecimalFormat("0.##").format(d5));
	// localStringBuffer2.append(" 百分比:");
	// localStringBuffer2.append(new DecimalFormat("0.##").format(d5 / d1
	// * 100.0D));
	// localStringBuffer2.append("%");
	// d2 = d5 / d1;
	// d3 += d2;
	// double d6 = 6.283185307179586D * d3;
	// if (d3 <= 0.25D) {
	// localDoublePoint3.x = (d4 + d4 * Math.cos(d6));
	// localDoublePoint3.y = (d4 - d4 * Math.sin(d6));
	// } else if ((d3 > 0.25D) && (d3 <= 0.5D)) {
	// d6 = 3.141592653589793D - d6;
	// localDoublePoint3.x = (d4 - d4 * Math.cos(d6));
	// localDoublePoint3.y = (d4 - d4 * Math.sin(d6));
	// } else if ((d3 > 0.5D) && (d3 <= 0.75D)) {
	// d6 -= 3.141592653589793D;
	// localDoublePoint3.x = (d4 - d4 * Math.cos(d6));
	// localDoublePoint3.y = (d4 + d4 * Math.sin(d6));
	// } else {
	// d6 = 6.283185307179586D - d6;
	// localDoublePoint3.x = (d4 + d4 * Math.cos(d6));
	// localDoublePoint3.y = (d4 + d4 * Math.sin(d6));
	// }
	// localStringBuffer1.append(createArc(paramDoubleRectangle,
	// localDoublePoint1, localDoublePoint2, localDoublePoint3,
	// paramColorPicker.GetColor(j),
	// paramColorPicker.GetMaskColor(j), null, str3,
	// localStringBuffer2.toString(), str4, str5, paramInt));
	// localDoublePoint2.Set(localDoublePoint3);
	// }
	// localStringBuffer1.append("\n<!--饼图数据输出结束-->\n");
	// return localStringBuffer1.toString();
	// }
	public static String create3DPie(DoubleRectangle doublerectangle,
			DataSet dataset, ColorPicker colorpicker, boolean flag, int i) {
		StringBuffer stringbuffer = new StringBuffer();
		// stringbuffer.append("<!--\u997C\u56FE\u6570\u636E\u8F93\u51FA\u5F00\u59CB-->\n");
		stringbuffer.append("<!--饼图数据输出开始-->\n");
		double d = 15D;
		double d1 = 12D;
		DoubleRectangle doublerectangle1 = new DoubleRectangle(
				doublerectangle.x + d, doublerectangle.y + d,
				doublerectangle.width - d * 2D, doublerectangle.height - d * 2D);
		ArrayList arraylist = (ArrayList) dataset.m_Row.get(0);
		double d2 = 0.0D;
		for (int j = 0; j < arraylist.size(); j++) {
			String s = (String) arraylist.get(j);
			double d5 = Double.valueOf(s).doubleValue();
			d2 += d5;
		}

		double d3 = 0.0D;
		double d6 = 0.0D;
		double d7 = 0.0D;
		double d9 = 6000D;
		DoublePoint doublepoint = new DoublePoint(6000D, 6000D);
		doublepoint.x = 6000D;
		doublepoint.y = 6000D;
		DoublePoint doublepoint1 = new DoublePoint(12000D, 6000D);
		doublepoint1.x = 12000D;
		doublepoint1.y = 6000D;
		DoublePoint doublepoint2 = new DoublePoint(12000D, 5999D);
		double d10 = 0.0D;
		double d18 = 0.0D;
		for (int k = 0; k < arraylist.size(); k++) {
			doublerectangle1.Set(doublerectangle.x + d, doublerectangle.y + d,
					doublerectangle.width - d * 2D, doublerectangle.height - d
							* 2D);
			Column column = (Column) dataset.m_Column.get(k);
			String s1 = column.GetCaption();
			String s2 = column.GetID();
			String s3 = column.GetHref();
			String s4 = column.GetScript();
			String s5 = (String) arraylist.get(k);
			double d26 = Double.valueOf(s5).doubleValue();
			StringBuffer stringbuffer1 = new StringBuffer();
			stringbuffer1.append("[");
			stringbuffer1.append(s1);
			stringbuffer1.append("]");
			stringbuffer1.append("\u6570\u91CF:");
			stringbuffer1.append((new DecimalFormat("0.##")).format(d26));
			stringbuffer1.append(" \u767E\u5206\u6BD4:");
			stringbuffer1.append((new DecimalFormat("0.##"))
					.format((d26 / d2) * 100D));
			stringbuffer1.append("%");
			double d4 = d26 / d2;
			d6 += d4;
			double d8 = d6 - d4 / 2D;
			double d27 = 6.2831853071795862D * d6;
			if (d6 <= 0.25D) {
				doublepoint2.x = d9 + d9 * Math.cos(d27);
				doublepoint2.y = d9 - d9 * Math.sin(d27);
				double d28 = d8 * 3.1415926535897931D * 2D;
				double d11 = Math.cos(d28) * d1;
				double d19 = Math.sin(d28) * d1;
				doublerectangle1.x = doublerectangle1.x + d11;
				doublerectangle1.y = doublerectangle1.y - d19;
			} else if (d6 > 0.25D && d6 <= 0.5D) {
				d27 = 3.1415926535897931D - d27;
				doublepoint2.x = d9 - d9 * Math.cos(d27);
				doublepoint2.y = d9 - d9 * Math.sin(d27);
				double d29 = d8 * 3.1415926535897931D * 2D;
				if (d8 <= 0.25D) {
					double d30 = d8 * 3.1415926535897931D * 2D;
					double d12 = Math.cos(d30) * d1;
					double d20 = Math.sin(d30) * d1;
					doublerectangle1.x = doublerectangle1.x + d12;
					doublerectangle1.y = doublerectangle1.y - d20;
				} else {
					double d31 = 3.1415926535897931D - d8 * 3.1415926535897931D * 2D;
					double d13 = Math.cos(d31) * d1;
					double d21 = Math.sin(d31) * d1;
					doublerectangle1.x = doublerectangle1.x - d13;
					doublerectangle1.y = doublerectangle1.y - d21;
				}
			} else if (d6 > 0.5D && d6 <= 0.75D) {
				d27 -= 3.1415926535897931D;
				doublepoint2.x = d9 - d9 * Math.cos(d27);
				doublepoint2.y = d9 + d9 * Math.sin(d27);
				double d32 = d8 * 3.1415926535897931D * 2D;
				if (d8 <= 0.5D) {
					double d33 = 3.1415926535897931D - d8 * 3.1415926535897931D * 2D;
					double d14 = Math.cos(d33) * d1;
					double d22 = Math.sin(d33) * d1;
					doublerectangle1.x = doublerectangle1.x - d14;
					doublerectangle1.y = doublerectangle1.y - d22;
				} else {
					double d34 = d8 * 3.1415926535897931D * 2D - 3.1415926535897931D;
					double d15 = Math.cos(d34) * d1;
					double d23 = Math.sin(d34) * d1;
					doublerectangle1.x = doublerectangle1.x - d15;
					doublerectangle1.y = doublerectangle1.y + d23;
				}
			} else {
				d27 = 6.2831853071795862D - d27;
				doublepoint2.x = d9 + d9 * Math.cos(d27);
				doublepoint2.y = d9 + d9 * Math.sin(d27);
				double d35 = d8 * 3.1415926535897931D * 2D;
				if (d8 <= 0.75D) {
					double d36 = d8 * 3.1415926535897931D * 2D - 3.1415926535897931D;
					double d16 = Math.cos(d36) * d1;
					double d24 = Math.sin(d36) * d1;
					doublerectangle1.x = doublerectangle1.x - d16;
					doublerectangle1.y = doublerectangle1.y + d24;
				} else {
					double d37 = 6.2831853071795862D - d8 * 3.1415926535897931D * 2D;
					double d17 = Math.cos(d37) * d1;
					double d25 = Math.sin(d37) * d1;
					doublerectangle1.x = doublerectangle1.x + d17;
					doublerectangle1.y = doublerectangle1.y + d25;
				}
			}
			stringbuffer.append(createArc(doublerectangle1, doublepoint,
					doublepoint1, doublepoint2, colorpicker.GetColor(k),
					colorpicker.GetMaskColor(k), colorpicker.GetShadowColor(k),
					s2, stringbuffer1.toString(), s3, s4, i));
			doublepoint1.Set(doublepoint2);
		}

		// stringbuffer.append("\n<!--\u997C\u56FE\u6570\u636E\u8F93\u51FA\u7ED3\u675F-->\n");
		stringbuffer.append("\n<!--饼图数据输出结束-->\n");
		return stringbuffer.toString();
	}

	// public static String create3DPie(DoubleRectangle paramDoubleRectangle,
	// DataSet paramDataSet, ColorPicker paramColorPicker,
	// boolean paramBoolean, int paramInt) {
	// StringBuffer localStringBuffer1 = new StringBuffer();
	// localStringBuffer1.append("<!--饼图数据输出开始-->\n");
	// double d1 = 15.0D;
	// double d2 = 12.0D;
	// DoubleRectangle localDoubleRectangle = new DoubleRectangle(
	// paramDoubleRectangle.x + d1, paramDoubleRectangle.y + d1,
	// paramDoubleRectangle.width - d1 * 2.0D,
	// paramDoubleRectangle.height - d1 * 2.0D);
	// ArrayList localArrayList = (ArrayList) paramDataSet.m_Row.get(0);
	// double d3 = 0.0D;
	// for (int i = 0; i < localArrayList.size(); i++) {
	// String str1 = (String) localArrayList.get(i);
	// d5 = Double.valueOf(str1).doubleValue();
	// d3 += d5;
	// }
	// double d4 = 0.0D;
	// double d5 = 0.0D;
	// double d6 = 0.0D;
	// double d7 = 6000.0D;
	// DoublePoint localDoublePoint1 = new DoublePoint(6000.0D, 6000.0D);
	// localDoublePoint1.x = 6000.0D;
	// localDoublePoint1.y = 6000.0D;
	// DoublePoint localDoublePoint2 = new DoublePoint(12000.0D, 6000.0D);
	// localDoublePoint2.x = 12000.0D;
	// localDoublePoint2.y = 6000.0D;
	// DoublePoint localDoublePoint3 = new DoublePoint(12000.0D, 5999.0D);
	// double d8 = 0.0D;
	// double d9 = 0.0D;
	// for (int j = 0; j < localArrayList.size(); j++) {
	// localDoubleRectangle.Set(paramDoubleRectangle.x + d1,
	// paramDoubleRectangle.y + d1, paramDoubleRectangle.width
	// - d1 * 2.0D, paramDoubleRectangle.height - d1
	// * 2.0D);
	// Column localColumn = (Column) paramDataSet.m_Column.get(j);
	// String str2 = localColumn.GetCaption();
	// String str3 = localColumn.GetID();
	// String str4 = localColumn.GetHref();
	// String str5 = localColumn.GetScript();
	// String str6 = (String) localArrayList.get(j);
	// double d10 = Double.valueOf(str6).doubleValue();
	// StringBuffer localStringBuffer2 = new StringBuffer();
	// localStringBuffer2.append("[");
	// localStringBuffer2.append(str2);
	// localStringBuffer2.append("]");
	// localStringBuffer2.append("数量:");
	// localStringBuffer2.append(new DecimalFormat("0.##").format(d10));
	// localStringBuffer2.append(" 百分比:");
	// localStringBuffer2.append(new DecimalFormat("0.##").format(d10 / d3
	// * 100.0D));
	// localStringBuffer2.append("%");
	// d4 = d10 / d3;
	// d5 += d4;
	// d6 = d5 - d4 / 2.0D;
	// double d11 = 6.283185307179586D * d5;
	// double d12;
	// if (d5 <= 0.25D) {
	// localDoublePoint3.x = (d7 + d7 * Math.cos(d11));
	// localDoublePoint3.y = (d7 - d7 * Math.sin(d11));
	// d12 = d6 * 3.141592653589793D * 2.0D;
	// d8 = Math.cos(d12) * d2;
	// d9 = Math.sin(d12) * d2;
	// localDoubleRectangle.x += d8;
	// localDoubleRectangle.y -= d9;
	// } else if ((d5 > 0.25D) && (d5 <= 0.5D)) {
	// d11 = 3.141592653589793D - d11;
	// localDoublePoint3.x = (d7 - d7 * Math.cos(d11));
	// localDoublePoint3.y = (d7 - d7 * Math.sin(d11));
	// d12 = d6 * 3.141592653589793D * 2.0D;
	// if (d6 <= 0.25D) {
	// d12 = d6 * 3.141592653589793D * 2.0D;
	// d8 = Math.cos(d12) * d2;
	// d9 = Math.sin(d12) * d2;
	// localDoubleRectangle.x += d8;
	// localDoubleRectangle.y -= d9;
	// } else {
	// d12 = 3.141592653589793D - d6 * 3.141592653589793D * 2.0D;
	// d8 = Math.cos(d12) * d2;
	// d9 = Math.sin(d12) * d2;
	// localDoubleRectangle.x -= d8;
	// localDoubleRectangle.y -= d9;
	// }
	// } else if ((d5 > 0.5D) && (d5 <= 0.75D)) {
	// d11 -= 3.141592653589793D;
	// localDoublePoint3.x = (d7 - d7 * Math.cos(d11));
	// localDoublePoint3.y = (d7 + d7 * Math.sin(d11));
	// d12 = d6 * 3.141592653589793D * 2.0D;
	// if (d6 <= 0.5D) {
	// d12 = 3.141592653589793D - d6 * 3.141592653589793D * 2.0D;
	// d8 = Math.cos(d12) * d2;
	// d9 = Math.sin(d12) * d2;
	// localDoubleRectangle.x -= d8;
	// localDoubleRectangle.y -= d9;
	// } else {
	// d12 = d6 * 3.141592653589793D * 2.0D - 3.141592653589793D;
	// d8 = Math.cos(d12) * d2;
	// d9 = Math.sin(d12) * d2;
	// localDoubleRectangle.x -= d8;
	// localDoubleRectangle.y += d9;
	// }
	// } else {
	// d11 = 6.283185307179586D - d11;
	// localDoublePoint3.x = (d7 + d7 * Math.cos(d11));
	// localDoublePoint3.y = (d7 + d7 * Math.sin(d11));
	// d12 = d6 * 3.141592653589793D * 2.0D;
	// if (d6 <= 0.75D) {
	// d12 = d6 * 3.141592653589793D * 2.0D - 3.141592653589793D;
	// d8 = Math.cos(d12) * d2;
	// d9 = Math.sin(d12) * d2;
	// localDoubleRectangle.x -= d8;
	// localDoubleRectangle.y += d9;
	// } else {
	// d12 = 6.283185307179586D - d6 * 3.141592653589793D * 2.0D;
	// d8 = Math.cos(d12) * d2;
	// d9 = Math.sin(d12) * d2;
	// localDoubleRectangle.x += d8;
	// localDoubleRectangle.y += d9;
	// }
	// }
	// localStringBuffer1.append(createArc(localDoubleRectangle,
	// localDoublePoint1, localDoublePoint2, localDoublePoint3,
	// paramColorPicker.GetColor(j),
	// paramColorPicker.GetMaskColor(j),
	// paramColorPicker.GetShadowColor(j), str3,
	// localStringBuffer2.toString(), str4, str5, paramInt));
	// localDoublePoint2.Set(localDoublePoint3);
	// }
	// localStringBuffer1.append("\n<!--饼图数据输出结束-->\n");
	// return localStringBuffer1.toString();
	// }

	private static String createString(DoubleRectangle paramDoubleRectangle,
			String paramString1, int paramInt1, int paramInt2,
			boolean paramBoolean, String paramString2, int paramInt3) {
		StringBuffer localStringBuffer1 = new StringBuffer();
		VMLShapeTag localVMLShapeTag = new VMLShapeTag();
		VMLTextBoxTag localVMLTextBoxTag = new VMLTextBoxTag();
		StringBuffer localStringBuffer2 = new StringBuffer();
		localStringBuffer2.append("<table");
		if (paramInt1 > 0) {
			localStringBuffer2.append(" cellspacing=\"");
			localStringBuffer2.append(paramInt1);
			localStringBuffer2.append("\"");
		}
		localStringBuffer2
				.append(" cellpadding=\"0\" width=\"100%\" height=\"100%\"><tr><td align=\"");
		localStringBuffer2.append(paramString2);
		localStringBuffer2.append("\">");
		if (paramInt2 > 0) {
			localStringBuffer2.append("<font style=\"FONT-SIZE: ");
			localStringBuffer2.append(paramInt2);
			localStringBuffer2.append("\">");
		}
		if (paramBoolean)
			localStringBuffer2.append("<b>");
		localStringBuffer2.append(paramString1);
		if (paramBoolean)
			localStringBuffer2.append("</b>");
		if (paramInt2 > 0)
			localStringBuffer2.append("</font>");
		localStringBuffer2.append("</td></tr></table>");
		localVMLShapeTag.AddAttribute(paramDoubleRectangle, null, null, null);
		localVMLShapeTag.setZ_index(paramInt3);
		localStringBuffer1.append(localVMLShapeTag.doStartTag());
		localVMLTextBoxTag.AddAttribute(new DoubleRectangle(0.0D, 0.0D, 0.0D,
				0.0D), localStringBuffer2.toString());
		localVMLTextBoxTag.setZ_index(paramInt3);
		localStringBuffer1.append(localVMLTextBoxTag.doTag());
		localStringBuffer1.append(localVMLShapeTag.doEndTag());
		return localStringBuffer1.toString();
	}

	private static String createSign(DoubleRectangle paramDoubleRectangle,
			String paramString1, String paramString2, String paramString3,
			String paramString4, String paramString5, String paramString6,
			int paramInt) {
		VMLRectTag localVMLRectTag = new VMLRectTag();
		localVMLRectTag.AddAttribute(paramDoubleRectangle, paramString2,
				paramString1);
		localVMLRectTag.setID(paramString3);
		localVMLRectTag.setAlt(paramString4);
		localVMLRectTag.setHref(paramString5);
		localVMLRectTag.setScript(paramString6);
		localVMLRectTag.setZ_index(paramInt);
		return localVMLRectTag.doTag();
	}

	private static String createLineSign(DoubleRectangle paramDoubleRectangle,
			int paramInt1, String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5,
			String paramString6, String paramString7, int paramInt2) {
		StringBuffer localStringBuffer = new StringBuffer();
		VMLShapeTag localVMLShapeTag = new VMLShapeTag();
		VMLLineTag localVMLLineTag = new VMLLineTag();
		VMLStrokeTag localVMLStrokeTag = new VMLStrokeTag();
		ArrayList localArrayList = new ArrayList();
		localArrayList.add(new DoublePoint(paramDoubleRectangle.x,
				paramDoubleRectangle.y + paramDoubleRectangle.height / 2.0D));
		localArrayList.add(new DoublePoint(paramDoubleRectangle.x
				+ paramDoubleRectangle.width, paramDoubleRectangle.y
				+ paramDoubleRectangle.height / 2.0D));
		localVMLLineTag.AddAttribute(localArrayList, 1.2D, paramString2);
		localVMLLineTag.setID(paramString4);
		localVMLLineTag.setAlt(paramString5);
		localVMLLineTag.setHref(paramString6);
		localVMLLineTag.setScript(paramString7);
		localVMLLineTag.setZ_index(paramInt2);
		localStringBuffer.append(localVMLLineTag.doStartTag());
		if (paramInt1 > 0) {
			localVMLStrokeTag.AddAttribute("dot",
					VMLStrokeTag.VML_STROKE_ARROW_NULL,
					VMLStrokeTag.VML_STROKE_ARROW_NULL);
			localStringBuffer.append(localVMLStrokeTag.doTag());
		}
		localStringBuffer.append(localVMLLineTag.doEndTag());
		DoubleRectangle localDoubleRectangle = new DoubleRectangle(
				paramDoubleRectangle.x + paramDoubleRectangle.width / 2.0D,
				paramDoubleRectangle.y + paramDoubleRectangle.height / 2.0D,
				8.0D, 8.0D);
		localVMLShapeTag.AddAttribute(localDoubleRectangle, paramString2,
				paramString1, paramString3);
		localVMLShapeTag.setID(paramString4);
		localVMLShapeTag.setAlt(paramString5);
		localVMLShapeTag.setHref(paramString6);
		localVMLShapeTag.setScript(paramString7);
		localVMLShapeTag.setZ_index(paramInt2);
		localStringBuffer.append(localVMLShapeTag.doTag());
		return localStringBuffer.toString();
	}

	private static String createCube(DoubleRectangle paramDoubleRectangle,
			String paramString1, String paramString2, String paramString3,
			String paramString4, String paramString5, String paramString6,
			String paramString7, String paramString8, int paramInt) {
		StringBuffer localStringBuffer = new StringBuffer();
		VMLRectTag localVMLRectTag = new VMLRectTag();
		VMLFillTag localVMLFillTag = new VMLFillTag();
		VMLOExtrusTag localVMLOExtrusTag = new VMLOExtrusTag();
		localVMLRectTag.AddAttribute(paramDoubleRectangle, null, paramString2);
		localVMLRectTag.setID(paramString5);
		localVMLRectTag.setAlt(paramString6);
		localVMLRectTag.setHref(paramString7);
		localVMLRectTag.setScript(paramString8);
		localVMLRectTag.setZ_index(paramInt);
		localStringBuffer.append(localVMLRectTag.doStartTag());
		if (paramString3 != null) {
			localVMLFillTag.AddAttribute(paramString3, "t", "gradient",
					paramString1);
			localStringBuffer.append(localVMLFillTag.doTag());
		}
		if (paramString4 != null) {
			localVMLOExtrusTag.AddAttribute(paramString4, 15.0D, "view");
			localStringBuffer.append(localVMLOExtrusTag.doTag());
		}
		localStringBuffer.append(localVMLRectTag.doEndTag());
		return localStringBuffer.toString();
	}

	private static String createArc(DoubleRectangle paramDoubleRectangle,
			DoublePoint paramDoublePoint1, DoublePoint paramDoublePoint2,
			DoublePoint paramDoublePoint3, String paramString1,
			String paramString2, String paramString3, String paramString4,
			String paramString5, String paramString6, String paramString7,
			int paramInt) {
		StringBuffer localStringBuffer1 = new StringBuffer();
		VMLShapeTag localVMLShapeTag = new VMLShapeTag();
		VMLFillTag localVMLFillTag = new VMLFillTag();
		VMLOExtrusTag localVMLOExtrusTag = new VMLOExtrusTag();
		DoubleRectangle localDoubleRectangle = new DoubleRectangle(
				paramDoubleRectangle.x + paramDoubleRectangle.width / 2.0D,
				paramDoubleRectangle.y + paramDoubleRectangle.height / 2.0D,
				paramDoubleRectangle.width, paramDoubleRectangle.height);
		DoublePoint localDoublePoint = new DoublePoint(paramDoubleRectangle.x
				+ paramDoubleRectangle.width / 2.0D, paramDoubleRectangle.y
				+ paramDoubleRectangle.height / 2.0D);
		StringBuffer localStringBuffer2 = new StringBuffer();
		localStringBuffer2.append("m ");
		localStringBuffer2.append((int) paramDoublePoint1.x);
		localStringBuffer2.append(",");
		localStringBuffer2.append((int) paramDoublePoint1.y);
		localStringBuffer2.append(" l ");
		localStringBuffer2.append((int) paramDoublePoint2.x);
		localStringBuffer2.append(",");
		localStringBuffer2.append((int) paramDoublePoint2.y);
		localStringBuffer2.append(" ar 0,0,12000,12000,");
		localStringBuffer2.append((int) paramDoublePoint2.x);
		localStringBuffer2.append(",");
		localStringBuffer2.append((int) paramDoublePoint2.y);
		localStringBuffer2.append(",");
		localStringBuffer2.append((int) paramDoublePoint3.x);
		localStringBuffer2.append(",");
		localStringBuffer2.append((int) paramDoublePoint3.y);
		localStringBuffer2.append(" l ");
		localStringBuffer2.append((int) paramDoublePoint1.x);
		localStringBuffer2.append(",");
		localStringBuffer2.append((int) paramDoublePoint1.y);
		localStringBuffer2.append(" x e");
		localVMLShapeTag.AddAttribute(localDoubleRectangle, "#FFFFFF",
				paramString1, localStringBuffer2.toString());
		localVMLShapeTag.setID(paramString4);
		localVMLShapeTag.setAlt(paramString5);
		localVMLShapeTag.setHref(paramString6);
		localVMLShapeTag.setScript(paramString7);
		localVMLShapeTag.setZ_index(paramInt);
		localStringBuffer1.append(localVMLShapeTag.doStartTag());
		if (paramString2 != null) {
			localVMLFillTag.AddAttribute(paramString2, "t", "gradient", null);
			localStringBuffer1.append(localVMLFillTag.doTag());
		}
		if (paramString3 != null) {
			localVMLOExtrusTag.AddAttribute(paramString3, 8.0D, "view");
			localStringBuffer1.append(localVMLOExtrusTag.doTag());
		}
		localStringBuffer1.append(localVMLShapeTag.doEndTag());
		return localStringBuffer1.toString();
	}

	private static double calcCaptionSize(int paramInt, double paramDouble,
			boolean paramBoolean) {
		double d = paramDouble * paramInt + 6.0D;
		if (paramBoolean)
			d += 36.0D;
		else
			d += 8.0D;
		return d;
	}

	public static void main(String[] paramArrayOfString) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
		localStringBuffer.append("<root>");
		localStringBuffer.append("<CONTENT_RECORDSET>");
		localStringBuffer
				.append("<row caption=\"1.得到付坚峰\" property=\"c1\"  shapetype=\"0\" onclick=\"alert('nenen')\" />");
		localStringBuffer
				.append("<row caption=\"2.分分德国队\"  property=\"c2\" shapetype=\"1\"/>");
		localStringBuffer
				.append("<row caption=\"3.交易山东队\"  property=\"c3\" shapetype=\"1\"/>");
		localStringBuffer.append("</CONTENT_RECORDSET>");
		localStringBuffer.append("<CONTENT1_RECORDSET>");
		localStringBuffer
				.append("<row caption=\"第一消毒法\" c1=\"167\" c2=\"87\" c3=\"89\"/>");
		localStringBuffer
				.append("<row caption=\"第尔大师傅个到\" c1=\"667\" c2=\"607\" c3=\"567\" />");
		localStringBuffer
				.append("<row caption=\"第上斯多夫固定法\" c1=\"66\" c2=\"77\" c3=\"454\" />");
		localStringBuffer
				.append("<row caption=\"第豆腐干大发市\" c1=\"345\" c2=\"34\" c3=\"67\" />");
		localStringBuffer.append("</CONTENT1_RECORDSET>");
		localStringBuffer.append("</root>");
		DataSet localDataSet = new DataSet();
		localDataSet.LoadDataSet(localStringBuffer.toString());
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.chart.vml.chart.VMLChartBuilder JD-Core Version:
 * 0.6.1
 */