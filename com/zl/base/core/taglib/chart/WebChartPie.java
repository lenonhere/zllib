package com.zl.base.core.taglib.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.servlet.http.HttpSession;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardLegend;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.DefaultPieDataset;

public class WebChartPie
{
  private DefaultPieDataset data = new DefaultPieDataset();

  public void setValue(String paramString, double paramDouble)
  {
    this.data.setValue(paramString, paramDouble);
  }

  public String generatePieChart(String paramString, HttpSession paramHttpSession, PrintWriter paramPrintWriter)
  {
    String str = null;
    try
    {
      JFreeChart localJFreeChart = ChartFactory.createPie3DChart("饼型图", this.data, true, true, false);
      localJFreeChart.setBackgroundPaint(Color.white);
      Font localFont1 = new Font("黑体", 1, 20);
      TextTitle localTextTitle = new TextTitle(paramString);
      localTextTitle.setFont(localFont1);
      localJFreeChart.setTitle(localTextTitle);
      Font localFont2 = new Font("黑体", 1, 12);
      PiePlot localPiePlot = (PiePlot)localJFreeChart.getPlot();
      localPiePlot.setSectionLabelFont(localFont2);
      localPiePlot.setInsets(new Insets(5, 5, 5, 5));
      localPiePlot.setURLGenerator(new StandardPieURLGenerator("link.jsp", "section"));
      localPiePlot.setSectionLabelType(4);
      localPiePlot.setDefaultOutlinePaint(new Color(153, 153, 255));
      localPiePlot.setDefaultOutlineStroke(new BasicStroke(0.0F));
      localPiePlot.setRadius(0.7D);
      localPiePlot.setStartAngle(270.0D);
      localPiePlot.setPaint(1, new Color(153, 153, 255));
      localPiePlot.setForegroundAlpha(0.5F);
      localPiePlot.setToolTipGenerator(new StandardPieToolTipGenerator());
      Font localFont3 = new Font("黑体", 1, 12);
      StandardLegend localStandardLegend = (StandardLegend)localJFreeChart.getLegend();
      localStandardLegend.setItemFont(localFont3);
      ChartRenderingInfo localChartRenderingInfo = new ChartRenderingInfo(new StandardEntityCollection());
      str = ServletUtilities.saveChartAsPNG(localJFreeChart, 500, 300, localChartRenderingInfo, paramHttpSession);
      ChartUtilities.writeImageMap(paramPrintWriter, str, localChartRenderingInfo);
      paramPrintWriter.flush();
    }
    catch (Exception localException)
    {
      System.out.println("Exception - " + localException.toString());
      localException.printStackTrace(System.out);
      str = "public_error_500x300.png";
    }
    return str;
  }

  public String generatePieChart2(String paramString1, String paramString2, String paramString3, HttpSession paramHttpSession, PrintWriter paramPrintWriter, DefaultPieDataset paramDefaultPieDataset, int paramInt1, int paramInt2, Color paramColor1, Color paramColor2, boolean paramBoolean)
  {
    String str = null;
    try
    {
      if (!paramBoolean)
        paramString1 = null;
      JFreeChart localJFreeChart = ChartFactory.createPie3DChart(paramString1, paramDefaultPieDataset, true, true, false);
      localJFreeChart.setBackgroundPaint(paramColor1);
      localJFreeChart.getPlot().setBackgroundPaint(paramColor2);
      if (paramString1 != null)
      {
        localJFreeChart.setTitle(new TextTitle(paramString1, new Font("黑体", 1, 15)));
        localJFreeChart.setBorderVisible(true);
      }
      else
      {
        localJFreeChart.setBorderVisible(false);
      }
      ChartRenderingInfo localChartRenderingInfo = new ChartRenderingInfo(new StandardEntityCollection());
      str = ServletUtilities.saveChartAsPNG(localJFreeChart, paramInt1, paramInt2, localChartRenderingInfo, paramHttpSession);
      ChartUtilities.writeImageMap(paramPrintWriter, str, localChartRenderingInfo);
      paramPrintWriter.flush();
    }
    catch (Exception localException)
    {
      System.out.println("Exception - " + localException.toString());
      localException.printStackTrace(System.out);
      str = "public_error_500x300.png";
    }
    return str;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.taglib.chart.WebChartPie
 * JD-Core Version:    0.6.1
 */