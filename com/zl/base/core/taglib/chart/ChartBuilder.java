package com.zl.base.core.taglib.chart;

import com.zl.util.MethodFactory;

import java.awt.BasicStroke;
import java.awt.Font;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.PropertyUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.Legend;
import org.jfree.chart.StandardLegend;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.Pie3DPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.BarRenderer;
import org.jfree.chart.renderer.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.CategoryDataset;
import org.jfree.data.DefaultCategoryDataset;
import org.jfree.data.DefaultPieDataset;
import org.jfree.data.PieDataset;
import org.jfree.util.Rotation;

public class ChartBuilder {
	private String fontname = "SansSerif";
	public static final int IMAGE_PNG = 0;
	public static final int IMAGE_JEPG = 1;
	private int imagetype = 0;

	public int getImagetype() {
		return this.imagetype;
	}

	public void setImagetype(int paramInt) {
		this.imagetype = paramInt;
	}

	public String generateChart(chartstruct paramchartstruct,
			ArrayList paramArrayList, HttpSession paramHttpSession,
			PrintWriter paramPrintWriter) {
		int i = paramchartstruct.getcharttype();
		JFreeChart localJFreeChart = null;
		String str = null;
		switch (i) {
		case 0:
			localJFreeChart = generateLineChart(paramchartstruct,
					createDataset(paramchartstruct, paramArrayList));
			break;
		case 1:
			localJFreeChart = generateBarChart(paramchartstruct,
					createDataset(paramchartstruct, paramArrayList));
			break;
		case 2:
			if (paramchartstruct.getmutipievisible())
				localJFreeChart = generateMutiPieChart(paramchartstruct,
						createDataset(paramchartstruct, paramArrayList));
			else
				localJFreeChart = generatePieChart(paramchartstruct,
						createPieDataset(paramchartstruct, paramArrayList));
			break;
		case 4:
			localJFreeChart = generateOverlaidBarChart(paramchartstruct,
					createOverlaidBarDataset(paramchartstruct, paramArrayList));
			break;
		case 5:
			localJFreeChart = generateStackBarChart(paramchartstruct,
					createDataset(paramchartstruct, paramArrayList));
			break;
		case 3:
			localJFreeChart = generateLineChart(paramchartstruct,
					createDataset(paramchartstruct, paramArrayList));
		}
		if (localJFreeChart != null)
			str = saveToImage(localJFreeChart, paramchartstruct,
					paramHttpSession, paramPrintWriter);
		return str;
	}

	private String saveToImage(JFreeChart paramJFreeChart,
			chartstruct paramchartstruct, HttpSession paramHttpSession,
			PrintWriter paramPrintWriter) {
		String str = null;
		try {
			ChartRenderingInfo localChartRenderingInfo = new ChartRenderingInfo(
					new StandardEntityCollection());
			if (this.imagetype == 0)
				str = ServletUtilities.saveChartAsPNG(paramJFreeChart,
						paramchartstruct.getwidth(),
						paramchartstruct.getheight(), localChartRenderingInfo,
						paramHttpSession);
			else
				str = ServletUtilities.saveChartAsJPEG(paramJFreeChart,
						paramchartstruct.getwidth(),
						paramchartstruct.getheight(), localChartRenderingInfo,
						paramHttpSession);
			ChartUtilities.writeImageMap(paramPrintWriter, str,
					localChartRenderingInfo);
			paramPrintWriter.flush();
		} catch (Exception localException) {
			System.out.println("Exception - " + localException.toString());
			localException.printStackTrace(System.out);
			str = "public_error_500x300.png";
		}
		return str;
	}

	private CategoryDataset createDataset(chartstruct paramchartstruct,
			ArrayList paramArrayList) {
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		BaseSeries localBaseSeries = null;
		Object localObject1 = null;
		String str1 = null;
		String str2 = null;
		String str3 = null;
		Object localObject2 = null;
		double d1 = 0.0D;
		double d2 = 0.0D;
		String str4 = "";
		ArrayList localArrayList = paramchartstruct.getseries();
		if ((localArrayList != null) && (paramArrayList != null))
			for (int i = 0; i < localArrayList.size(); i++) {
				localBaseSeries = (BaseSeries) localArrayList.get(i);
				str1 = localBaseSeries.getcaption();
				str2 = localBaseSeries.getvalueproperty();
				str3 = localBaseSeries.getseriesproperty();
				d2 = 0.0D;
				str4 = "";
				for (int j = 0; j < paramArrayList.size(); j++) {
					str4 = str4 + " ";
					localObject1 = paramArrayList.get(j);
					if (localObject1 != null) {
						localObject2 = ReadValue(localObject1, str3);
						if (ReadValue(localObject1, str2) != null) {
							d1 = Double.parseDouble(ReadValue(localObject1,
									str2));
							if (paramchartstruct.getdataprogress())
								d2 += d1;
							else
								d2 = d1;
							if (((String) localObject2).equals(""))
								localObject2 = str4;
							localDefaultCategoryDataset.addValue(d2, str1,
									(Comparable) localObject2);
						}
					}
				}
			}
		return localDefaultCategoryDataset;
	}

	private PieDataset createPieDataset(chartstruct paramchartstruct,
			ArrayList paramArrayList) {
		DefaultPieDataset localDefaultPieDataset = new DefaultPieDataset();
		BaseSeries localBaseSeries = null;
		Object localObject = null;
		String str1 = null;
		String str2 = null;
		String str3 = null;
		String str4 = null;
		double d1 = 0.0D;
		double d2 = 0.0D;
		ArrayList localArrayList = paramchartstruct.getseries();
		if (localArrayList != null)
			for (int i = 0; i < localArrayList.size(); i++) {
				localBaseSeries = (BaseSeries) localArrayList.get(i);
				str1 = localBaseSeries.getcaption();
				str2 = localBaseSeries.getvalueproperty();
				str3 = localBaseSeries.getseriesproperty();
				d2 = 0.0D;
				for (int j = 0; j < paramArrayList.size(); j++) {
					localObject = paramArrayList.get(j);
					if (localObject != null) {
						str4 = ReadValue(localObject, str3);
						if (ReadValue(localObject, str2) != null) {
							d1 = Double
									.parseDouble(ReadValue(localObject, str2));
							if (paramchartstruct.getdataprogress())
								d2 += d1;
							else
								d2 = d1;
							localDefaultPieDataset.setValue(str4, d2);
						}
					}
				}
			}
		return localDefaultPieDataset;
	}

	private ArrayList createOverlaidBarDataset(chartstruct paramchartstruct,
			ArrayList paramArrayList) {
		ArrayList localArrayList1 = new ArrayList();
		DefaultCategoryDataset localDefaultCategoryDataset1 = new DefaultCategoryDataset();
		DefaultCategoryDataset localDefaultCategoryDataset2 = null;
		BaseSeries localBaseSeries = null;
		Object localObject = null;
		String str1 = null;
		String str2 = null;
		String str3 = null;
		String str4 = null;
		double d1 = 0.0D;
		double d2 = 0.0D;
		ArrayList localArrayList2 = paramchartstruct.getseries();
		if (localArrayList2 != null)
			for (int i = 0; i < localArrayList2.size(); i++) {
				localBaseSeries = (BaseSeries) localArrayList2.get(i);
				str1 = localBaseSeries.getcaption();
				str2 = localBaseSeries.getvalueproperty();
				str3 = localBaseSeries.getseriesproperty();
				d2 = 0.0D;
				int j;
				if (localBaseSeries.getcharttype() == 1)
					for (j = 0; j < paramArrayList.size(); j++) {
						localObject = paramArrayList.get(j);
						if (localObject != null) {
							str4 = ReadValue(localObject, str3);
							if (ReadValue(localObject, str2) != null) {
								d1 = Double.parseDouble(ReadValue(localObject,
										str2));
								if (paramchartstruct.getdataprogress())
									d2 += d1;
								else
									d2 = d1;
								localDefaultCategoryDataset1.addValue(d2, str1,
										str4);
							}
						}
					}
				if (localBaseSeries.getcharttype() == 0) {
					localDefaultCategoryDataset2 = new DefaultCategoryDataset();
					for (j = 0; j < paramArrayList.size(); j++) {
						localObject = paramArrayList.get(j);
						if (localObject != null) {
							str4 = ReadValue(localObject, str3);
							if (ReadValue(localObject, str2) != null) {
								d1 = Double.parseDouble(ReadValue(localObject,
										str2));
								if (paramchartstruct.getdataprogress())
									d2 += d1;
								else
									d2 = d1;
								localDefaultCategoryDataset2.addValue(d2, str1,
										str4);
							}
						}
					}
				}
			}
		localArrayList1.add(localDefaultCategoryDataset1);
		localArrayList1.add(localDefaultCategoryDataset2);
		return localArrayList1;
	}

	private String ReadValue(Object paramObject, String paramString) {
		String str = null;
		Object localObject = null;
		try {
			localObject = PropertyUtils.getProperty(paramObject, paramString);
			if (localObject == null)
				return null;
			str = MethodFactory.getThisString(localObject);
		} catch (Exception localException) {
			System.out.println("Exception - " + localException.toString());
			localException.printStackTrace(System.out);
			str = "0";
		}
		return str;
	}

	private JFreeChart generateBarChart(chartstruct paramchartstruct,
			CategoryDataset paramCategoryDataset) {
		try {
			String str1 = null;
			String str2 = null;
			String str3 = null;
			if (paramchartstruct.getcaptionvisible())
				str1 = paramchartstruct.getcaption();
			if (paramchartstruct.getvcaptionvisible())
				str2 = paramchartstruct.getvcaption();
			if (paramchartstruct.gethcaptionvisible())
				str3 = paramchartstruct.gethcaption();
			PlotOrientation localPlotOrientation;
			if (paramchartstruct.getchartdirection() == 0)
				localPlotOrientation = PlotOrientation.HORIZONTAL;
			else
				localPlotOrientation = PlotOrientation.VERTICAL;
			boolean bool = paramchartstruct.getlegendvisible();
			JFreeChart localJFreeChart = null;
			if (paramchartstruct.getis3D())
				localJFreeChart = ChartFactory.createBarChart3D(str1, str3,
						str2, paramCategoryDataset, localPlotOrientation, bool,
						true, false);
			else
				localJFreeChart = ChartFactory.createBarChart(str1, str3, str2,
						paramCategoryDataset, localPlotOrientation, bool, true,
						false);
			if (str1 != null)
				localJFreeChart.setTitle(new TextTitle(str1,
						new Font(this.fontname, 1, paramchartstruct
								.getcaptionfontsize())));
			localJFreeChart.setBorderVisible(true);
			localJFreeChart.setBackgroundPaint(paramchartstruct.getbgcolor());
			localJFreeChart.getPlot().setBackgroundPaint(
					paramchartstruct.getplotbgcolor());

			if (bool == true) {
				StandardLegend standardlegend = (StandardLegend) localJFreeChart
						.getLegend();
				standardlegend.setAnchor(paramchartstruct.getlegendlocation());
				standardlegend.setDisplaySeriesShapes(true);
				standardlegend.setShapeScaleX(1.5D);
				standardlegend.setShapeScaleY(1.5D);
				standardlegend.setDisplaySeriesLines(true);
				Font font = new Font(this.fontname, 1,
						paramchartstruct.getlegenfontsize());
				standardlegend.setItemFont((Font) font);
			}

			Object localObject1 = localJFreeChart.getCategoryPlot();
			Object localObject2 = CategoryLabelPositions.STANDARD;
			if (paramchartstruct.getserieslabeldirection() == 1)
				localObject2 = CategoryLabelPositions.DOWN_90;
			CategoryAxis localCategoryAxis = ((CategoryPlot) localObject1)
					.getDomainAxis();
			localCategoryAxis
					.setCategoryLabelPositions((CategoryLabelPositions) localObject2);
			localCategoryAxis.setMaxCategoryLabelWidthRatio(5.0F);
			localCategoryAxis.setAxisLineVisible(false);
			localCategoryAxis.setmaxlabelcount(paramchartstruct
					.getmaxlabelcount());
			localCategoryAxis.setLabelFont(new Font(this.fontname, 1,
					paramchartstruct.getlabelfontsize()));
			localCategoryAxis.setTickLabelFont(new Font(this.fontname,
					localCategoryAxis.getTickLabelFont().getStyle(),
					paramchartstruct.getaxiscaptionfontsize()));
			ValueAxis localValueAxis = ((CategoryPlot) localObject1)
					.getRangeAxis();
			localValueAxis.setLabelFont(new Font(this.fontname, 1,
					paramchartstruct.getlabelfontsize()));
			localValueAxis.setTickLabelFont(new Font(this.fontname,
					localValueAxis.getTickLabelFont().getStyle(),
					paramchartstruct.getaxiscaptionfontsize()));
			NumberAxis localNumberAxis = (NumberAxis) ((CategoryPlot) localObject1)
					.getRangeAxis();
			localNumberAxis.setStandardTickUnits(NumberAxis
					.createIntegerTickUnits());
			BarRenderer localBarRenderer = (BarRenderer) ((CategoryPlot) localObject1)
					.getRenderer();
			localBarRenderer.setDrawBarOutline(false);
			localBarRenderer.setItemMargin(0.0D);
			return localJFreeChart;
		} catch (Exception localException) {
			System.out.println("Exception - " + localException.toString());
			localException.printStackTrace(System.out);
		}
		return null;
	}

	private JFreeChart generateStackBarChart(chartstruct paramchartstruct,
			CategoryDataset paramCategoryDataset) {
		try {
			String str1 = null;
			String str2 = null;
			String str3 = null;
			if (paramchartstruct.getcaptionvisible())
				str1 = paramchartstruct.getcaption();
			if (paramchartstruct.getvcaptionvisible())
				str2 = paramchartstruct.getvcaption();
			if (paramchartstruct.gethcaptionvisible())
				str3 = paramchartstruct.gethcaption();
			PlotOrientation localPlotOrientation;
			if (paramchartstruct.getchartdirection() == 0)
				localPlotOrientation = PlotOrientation.HORIZONTAL;
			else
				localPlotOrientation = PlotOrientation.VERTICAL;
			boolean bool = paramchartstruct.getlegendvisible();
			JFreeChart localJFreeChart = null;
			if (paramchartstruct.getis3D())
				localJFreeChart = ChartFactory.createStackedBarChart3D(str1,
						str3, str2, paramCategoryDataset, localPlotOrientation,
						bool, true, false);
			else
				localJFreeChart = ChartFactory.createStackedBarChart(str1,
						str3, str2, paramCategoryDataset, localPlotOrientation,
						bool, true, false);
			localJFreeChart.setBorderVisible(true);
			localJFreeChart.setBackgroundPaint(paramchartstruct.getbgcolor());
			localJFreeChart.getPlot().setBackgroundPaint(
					paramchartstruct.getplotbgcolor());

			if (bool == true) {
				StandardLegend standardlegend = (StandardLegend) localJFreeChart
						.getLegend();
				standardlegend.setAnchor(paramchartstruct.getlegendlocation());
				standardlegend.setDisplaySeriesShapes(true);
				standardlegend.setShapeScaleX(1.5D);
				standardlegend.setShapeScaleY(1.5D);
				standardlegend.setDisplaySeriesLines(true);
				Font font = new Font(this.fontname, 1,
						paramchartstruct.getlegenfontsize());
				standardlegend.setItemFont((Font) font);
			}

			Object localObject1 = localJFreeChart.getCategoryPlot();
			Object localObject2 = CategoryLabelPositions.STANDARD;
			if (paramchartstruct.getserieslabeldirection() == 1)
				localObject2 = CategoryLabelPositions.DOWN_90;
			CategoryAxis localCategoryAxis = ((CategoryPlot) localObject1)
					.getDomainAxis();
			localCategoryAxis
					.setCategoryLabelPositions((CategoryLabelPositions) localObject2);
			localCategoryAxis.setMaxCategoryLabelWidthRatio(5.0F);
			localCategoryAxis.setAxisLineVisible(false);
			localCategoryAxis.setLabelFont(new Font(this.fontname, 1,
					paramchartstruct.getlabelfontsize()));
			localCategoryAxis.setTickLabelFont(new Font(this.fontname,
					localCategoryAxis.getTickLabelFont().getStyle(),
					paramchartstruct.getaxiscaptionfontsize()));
			ValueAxis localValueAxis = ((CategoryPlot) localObject1)
					.getRangeAxis();
			localValueAxis.setLabelFont(new Font(this.fontname, 1,
					paramchartstruct.getlabelfontsize()));
			localValueAxis.setTickLabelFont(new Font(this.fontname,
					localValueAxis.getTickLabelFont().getStyle(),
					paramchartstruct.getaxiscaptionfontsize()));
			NumberAxis localNumberAxis = (NumberAxis) ((CategoryPlot) localObject1)
					.getRangeAxis();
			localNumberAxis.setStandardTickUnits(NumberAxis
					.createIntegerTickUnits());
			BarRenderer localBarRenderer = (BarRenderer) ((CategoryPlot) localObject1)
					.getRenderer();
			localBarRenderer.setDrawBarOutline(false);
			localBarRenderer.setItemMargin(0.0D);
			return localJFreeChart;
		} catch (Exception localException) {
			System.out.println("Exception - " + localException.toString());
			localException.printStackTrace(System.out);
		}
		return null;
	}

	private JFreeChart generatePieChart(chartstruct paramchartstruct,
			PieDataset paramPieDataset) {
		try {
			String str1 = null;
			String str2 = null;
			String str3 = null;
			if (paramchartstruct.getcaptionvisible())
				str1 = paramchartstruct.getcaption();
			if (paramchartstruct.getvcaptionvisible())
				str2 = paramchartstruct.getvcaption();
			if (paramchartstruct.gethcaptionvisible())
				str3 = paramchartstruct.gethcaption();
			PlotOrientation localPlotOrientation;
			if (paramchartstruct.getchartdirection() == 0)
				localPlotOrientation = PlotOrientation.HORIZONTAL;
			else
				localPlotOrientation = PlotOrientation.VERTICAL;
			boolean bool = paramchartstruct.getlegendvisible();
			JFreeChart localJFreeChart = null;
			if (paramchartstruct.getis3D())
				localJFreeChart = ChartFactory.createPieChart3D(str1,
						paramPieDataset, bool, true, false);
			else
				localJFreeChart = ChartFactory.createPieChart(str1,
						paramPieDataset, bool, true, false);
			if (str1 != null)
				localJFreeChart.setTitle(new TextTitle(str1,
						new Font(this.fontname, 1, paramchartstruct
								.getcaptionfontsize())));
			localJFreeChart.setBorderVisible(true);
			localJFreeChart.setBackgroundPaint(paramchartstruct.getbgcolor());
			localJFreeChart.getPlot().setBackgroundPaint(
					paramchartstruct.getplotbgcolor());
			Object localObject;
			if (bool == true) {
				localObject = (StandardLegend) localJFreeChart.getLegend();
				((StandardLegend) localObject).setAnchor(paramchartstruct
						.getlegendlocation());
				Font localFont = new Font(this.fontname, 1,
						paramchartstruct.getlegenfontsize());
				((StandardLegend) localObject).setItemFont(localFont);
			}
			if (paramchartstruct.getis3D()) {
				localObject = (Pie3DPlot) localJFreeChart.getPlot();
				((Pie3DPlot) localObject).setStartAngle(270.0D);
				((Pie3DPlot) localObject).setDirection(Rotation.CLOCKWISE);
				((Pie3DPlot) localObject).setForegroundAlpha(0.5F);
				((Pie3DPlot) localObject).setSectionLabelType(paramchartstruct
						.getsectionlabeltype());
				((Pie3DPlot) localObject).setNoDataMessage("不存在显示数据");
			} else {
				localObject = (PiePlot) localJFreeChart.getPlot();
				((PiePlot) localObject).setSectionLabelType(paramchartstruct
						.getsectionlabeltype());
				((PiePlot) localObject).setNoDataMessage("N不存在显示数据");
			}
			return localJFreeChart;
		} catch (Exception localException) {
			System.out.println("Exception - " + localException.toString());
			localException.printStackTrace(System.out);
		}
		return null;
	}

	private JFreeChart generateMutiPieChart(chartstruct paramchartstruct,
			CategoryDataset paramCategoryDataset) {
		try {
			String str1 = null;
			String str2 = null;
			String str3 = null;
			if (paramchartstruct.getcaptionvisible())
				str1 = paramchartstruct.getcaption();
			if (paramchartstruct.getvcaptionvisible())
				str2 = paramchartstruct.getvcaption();
			if (paramchartstruct.gethcaptionvisible())
				str3 = paramchartstruct.gethcaption();
			PlotOrientation localPlotOrientation;
			if (paramchartstruct.getchartdirection() == 0)
				localPlotOrientation = PlotOrientation.HORIZONTAL;
			else
				localPlotOrientation = PlotOrientation.VERTICAL;
			boolean bool = paramchartstruct.getlegendvisible();
			JFreeChart localJFreeChart = null;
			localJFreeChart = ChartFactory.createPieChart(str1,
					paramCategoryDataset, 0, bool, true, false);
			if (str1 != null)
				localJFreeChart.setTitle(new TextTitle(str1,
						new Font(this.fontname, 1, paramchartstruct
								.getcaptionfontsize())));
			localJFreeChart.setBorderVisible(true);
			localJFreeChart.setBackgroundPaint(paramchartstruct.getbgcolor());
			localJFreeChart.getPlot().setBackgroundPaint(
					paramchartstruct.getplotbgcolor());

			if (bool == true) {
				StandardLegend standardlegend = (StandardLegend) localJFreeChart
						.getLegend();
				standardlegend.setAnchor(paramchartstruct.getlegendlocation());
				Font localFont = new Font(this.fontname, 1,
						paramchartstruct.getlegenfontsize());
				standardlegend.setItemFont(localFont);
			}

			Object localObject = (PiePlot) localJFreeChart.getPlot();
			((PiePlot) localObject).setSectionLabelType(6);
			return localJFreeChart;
		} catch (Exception localException) {
			System.out.println("Exception - " + localException.toString());
			localException.printStackTrace(System.out);
		}
		return null;
	}

	private JFreeChart generateLineChart(chartstruct paramchartstruct,
			CategoryDataset paramCategoryDataset) {
		try {
			String str1 = null;
			String str2 = null;
			String str3 = null;
			if (paramchartstruct.getcaptionvisible())
				str1 = paramchartstruct.getcaption();
			if (paramchartstruct.getvcaptionvisible())
				str2 = paramchartstruct.getvcaption();
			if (paramchartstruct.gethcaptionvisible())
				str3 = paramchartstruct.gethcaption();
			PlotOrientation localPlotOrientation;
			if (paramchartstruct.getchartdirection() == 0)
				localPlotOrientation = PlotOrientation.HORIZONTAL;
			else
				localPlotOrientation = PlotOrientation.VERTICAL;
			boolean bool = paramchartstruct.getlegendvisible();
			JFreeChart localJFreeChart = null;
			localJFreeChart = ChartFactory.createLineChart(str1, str3, str2,
					paramCategoryDataset, localPlotOrientation, bool, true,
					false);
			if (str1 != null)
				localJFreeChart.setTitle(new TextTitle(str1, new Font("宋体", 1,
						paramchartstruct.getcaptionfontsize())));
			localJFreeChart.setBorderVisible(true);
			localJFreeChart.setBackgroundPaint(paramchartstruct.getbgcolor());
			localJFreeChart.getPlot().setBackgroundPaint(
					paramchartstruct.getplotbgcolor());

			if (bool == true) {
				StandardLegend standardlegend = (StandardLegend) localJFreeChart
						.getLegend();
				standardlegend.setAnchor(paramchartstruct.getlegendlocation());
				standardlegend.setDisplaySeriesShapes(true);
				standardlegend.setShapeScaleX(1.5D);
				standardlegend.setShapeScaleY(1.5D);
				standardlegend.setDisplaySeriesLines(true);
				Font font = new Font(this.fontname, 1,
						paramchartstruct.getlegenfontsize());
				standardlegend.setItemFont((Font) font);
			}

			Object localObject1 = localJFreeChart.getCategoryPlot();
			Object localObject2 = CategoryLabelPositions.STANDARD;
			if (paramchartstruct.getserieslabeldirection() == 1)
				localObject2 = CategoryLabelPositions.DOWN_90;
			CategoryAxis localCategoryAxis = ((CategoryPlot) localObject1)
					.getDomainAxis();
			localCategoryAxis
					.setCategoryLabelPositions((CategoryLabelPositions) localObject2);
			localCategoryAxis.setMaxCategoryLabelWidthRatio(5.0F);
			localCategoryAxis.setAxisLineVisible(false);
			localCategoryAxis.setmaxlabelcount(paramchartstruct
					.getmaxlabelcount());
			
			localCategoryAxis.setLabelFont(new Font(this.fontname, 1,
					paramchartstruct.getlabelfontsize()));
			localCategoryAxis.setTickLabelFont(new Font(this.fontname,
					localCategoryAxis.getTickLabelFont().getStyle(),
					paramchartstruct.getaxiscaptionfontsize()));
			ValueAxis localValueAxis = ((CategoryPlot) localObject1)
					.getRangeAxis();
			localValueAxis.setLabelFont(new Font(this.fontname, 1,
					paramchartstruct.getlabelfontsize()));
			localValueAxis.setTickLabelFont(new Font(this.fontname,
					localValueAxis.getTickLabelFont().getStyle(),
					paramchartstruct.getaxiscaptionfontsize()));
			NumberAxis localNumberAxis = (NumberAxis) ((CategoryPlot) localObject1)
					.getRangeAxis();
			if (paramchartstruct.getYLowerMargin() != 0.0D)
				localNumberAxis.setLowerMargin(paramchartstruct
						.getYLowerMargin());
			LineAndShapeRenderer localLineAndShapeRenderer = (LineAndShapeRenderer) ((CategoryPlot) localObject1)
					.getRenderer();
			localLineAndShapeRenderer.setDrawShapes(paramchartstruct
					.getpoltshapevisible());
			BaseSeries localBaseSeries = null;
			ArrayList localArrayList = paramchartstruct.getseries();
			for (int i = 0; i < localArrayList.size(); i++) {
				localBaseSeries = (BaseSeries) localArrayList.get(i);
				if (localBaseSeries.getshapetype() != 0)
					localLineAndShapeRenderer.setSeriesStroke(i,
							new BasicStroke(2.0F, 1, 1, 1.0F, new float[] {
									10.0F, 6.0F }, 0.0F));
			}
			return localJFreeChart;
		} catch (Exception localException) {
			System.out.println("Exception - " + localException.toString());
			localException.printStackTrace(System.out);
		}
		return null;
	}

	private JFreeChart generateOverlaidBarChart(chartstruct paramchartstruct,
			ArrayList paramArrayList) {
		try {
			String str1 = null;
			String str2 = null;
			String str3 = null;
			if (paramchartstruct.getcaptionvisible())
				str1 = paramchartstruct.getcaption();
			if (paramchartstruct.getvcaptionvisible())
				str2 = paramchartstruct.getvcaption();
			if (paramchartstruct.gethcaptionvisible())
				str3 = paramchartstruct.gethcaption();
			PlotOrientation localPlotOrientation;
			if (paramchartstruct.getchartdirection() == 0)
				localPlotOrientation = PlotOrientation.HORIZONTAL;
			else
				localPlotOrientation = PlotOrientation.VERTICAL;
			CategoryDataset localCategoryDataset1 = (CategoryDataset) paramArrayList
					.get(0);
			CategoryDataset localCategoryDataset2 = (CategoryDataset) paramArrayList
					.get(1);
			BarRenderer localBarRenderer = new BarRenderer();
			localBarRenderer
					.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			CategoryPlot localCategoryPlot = new CategoryPlot();
			localCategoryPlot.setDataset(localCategoryDataset1);
			localCategoryPlot.setRenderer(localBarRenderer);
			localCategoryPlot.setDomainAxis(new CategoryAxis(str3));
			localCategoryPlot.setRangeAxis(new NumberAxis(str2));
			localCategoryPlot.setOrientation(localPlotOrientation);
			localCategoryPlot.setRangeGridlinesVisible(true);
			localCategoryPlot.setDomainGridlinesVisible(true);
			CategoryLabelPositions localCategoryLabelPositions = CategoryLabelPositions.STANDARD;
			if (paramchartstruct.getserieslabeldirection() == 1)
				localCategoryLabelPositions = CategoryLabelPositions.DOWN_90;
			CategoryAxis localCategoryAxis = localCategoryPlot.getDomainAxis();
			localCategoryAxis
					.setCategoryLabelPositions(localCategoryLabelPositions);
			localCategoryAxis.setMaxCategoryLabelWidthRatio(5.0F);
			localCategoryAxis.setAxisLineVisible(false);
			localCategoryAxis.setLabelFont(new Font(this.fontname, 1,
					paramchartstruct.getlabelfontsize()));
			localCategoryAxis.setTickLabelFont(new Font(this.fontname,
					localCategoryAxis.getTickLabelFont().getStyle(),
					paramchartstruct.getaxiscaptionfontsize()));
			ValueAxis localValueAxis = localCategoryPlot.getRangeAxis();
			localValueAxis.setLabelFont(new Font(this.fontname, 1,
					paramchartstruct.getlabelfontsize()));
			localValueAxis.setTickLabelFont(new Font(this.fontname,
					localValueAxis.getTickLabelFont().getStyle(),
					paramchartstruct.getaxiscaptionfontsize()));
			NumberAxis localNumberAxis = (NumberAxis) localCategoryPlot
					.getRangeAxis();
			LineAndShapeRenderer localLineAndShapeRenderer = new LineAndShapeRenderer();
			localCategoryPlot.setSecondaryDataset(0, localCategoryDataset2);
			localCategoryPlot
					.setSecondaryRenderer(0, localLineAndShapeRenderer);
			localCategoryPlot.mapSecondaryDatasetToRangeAxis(1, new Integer(0));
			localCategoryPlot
					.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
			JFreeChart localJFreeChart = new JFreeChart(localCategoryPlot);
			if (str1 != null)
				localJFreeChart.setTitle(str1);
			if (str1 != null)
				localJFreeChart.setTitle(new TextTitle(str1,
						new Font(this.fontname, 1, paramchartstruct
								.getcaptionfontsize())));
			if (paramchartstruct.getlegendvisible()) {
				StandardLegend localStandardLegend = new StandardLegend();
				Font localFont = new Font(this.fontname, 1,
						paramchartstruct.getlegenfontsize());
				localStandardLegend.setItemFont(localFont);
				localJFreeChart.setLegend(localStandardLegend);
				localJFreeChart.getLegend().setAnchor(
						paramchartstruct.getlegendlocation());
			}
		} catch (Exception localException) {
			System.out.println("Exception - " + localException.toString());
			localException.printStackTrace(System.out);
		}
		return null;
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.chart.ChartBuilder JD-Core Version: 0.6.1
 */