package com.zl.base.core.taglib.chart;

import java.awt.Color;
import java.awt.Font;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.servlet.http.HttpSession;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardLegend;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.CategoryDataset;

public class WebChartBar
{
  public String generateBar3dChart(String paramString1, String paramString2, String paramString3, HttpSession paramHttpSession, PrintWriter paramPrintWriter, CategoryDataset paramCategoryDataset, int paramInt1, int paramInt2, Color paramColor1, Color paramColor2)
  {
    String str = null;
    try
    {
      JFreeChart localJFreeChart = ChartFactory.createBarChart3D(paramString1, paramString3, paramString2, paramCategoryDataset, PlotOrientation.VERTICAL, true, true, false);
      localJFreeChart.setBackgroundPaint(paramColor1);
      localJFreeChart.getPlot().setBackgroundPaint(paramColor2);
      localJFreeChart.setBorderVisible(true);
      if (paramString1 != null)
        localJFreeChart.setTitle(new TextTitle(paramString1, new Font("黑体", 1, 15)));
      Font localFont1 = new Font("黑体", 1, 12);
      Font localFont2 = new Font("黑体", 1, 12);
      StandardLegend localStandardLegend1 = (StandardLegend)localJFreeChart.getLegend();
      localStandardLegend1.setItemFont(localFont2);
      StandardLegend localStandardLegend2 = (StandardLegend)localJFreeChart.getLegend();
      localStandardLegend2.setDisplaySeriesShapes(true);
      localStandardLegend2.setShapeScaleX(1.5D);
      localStandardLegend2.setShapeScaleY(1.5D);
      localStandardLegend2.setDisplaySeriesLines(true);
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
 * Qualified Name:     com.zl.base.core.taglib.chart.WebChartBar
 * JD-Core Version:    0.6.1
 */