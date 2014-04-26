package com.zl.base.core.taglib.chart.vml.chart;

import com.zl.base.core.taglib.chart.vml.VMLSign;
import com.zl.base.core.taglib.chart.vml.data.Axes;
import com.zl.base.core.taglib.chart.vml.data.Column;
import com.zl.base.core.taglib.chart.vml.data.DataSet;
import com.zl.base.core.util.ColorPicker;
import com.zl.base.core.util.DoublePoint;
import com.zl.base.core.util.DoubleRectangle;
import com.zl.base.core.util.DoubleSize;
import java.io.PrintStream;
import java.util.ArrayList;

public class VMLChart {
	public static final int CHART_TYPE_LINE = 1;
	public static final int CHART_TYPE_BAR = 2;
	public static final int CHART_TYPE_PIE = 3;
	public DoubleRectangle m_dbound = null;
	public DoubleSize m_marge = null;
	public DoubleSize m_chartsize = null;
	public double m_offsize = 0.0D;
	public String m_bgcolor = "#FFFF99";
	public String m_caption = "VML Chart 图例";
	public int m_legend = 0;
	public boolean m_btable = true;
	public boolean m_bcaption = true;
	public boolean m_bborder = true;
	public int m_degree = 10;
	public double m_degreevalue = 0.0D;
	public DataSet m_DataSet = null;
	public ColorPicker m_colorpicker = new ColorPicker();
	public VMLSign m_vmlsign = new VMLSign();
	private DoubleRectangle m_captionbounds = new DoubleRectangle(0.0D, 0.0D,
			0.0D, 0.0D);
	private DoubleRectangle m_chartbounds = new DoubleRectangle(0.0D, 0.0D,
			0.0D, 0.0D);
	private DoubleRectangle m_legendbounds = new DoubleRectangle(0.0D, 0.0D,
			0.0D, 0.0D);
	private double m_minfontwidth = 0.0D;
	private double m_maxvalue = 1.7976931348623157E+308D;
	private double m_minvalue = 4.9E-324D;

	public String GetVmlHeader() {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer
				.append("<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">");
		localStringBuffer.append("\n");
		localStringBuffer
				.append("<!--[if !mso]> <STYLE>v\\:* { BEHAVIOR: url(#default#VML) } o\\:* { BEHAVIOR: url(#default#VML) } .shape { BEHAVIOR: url(#default#VML) } </STYLE> <![endif]-->");
		localStringBuffer.append("\n");
		return localStringBuffer.toString();
	}

	public void SetBound(double paramDouble1, double paramDouble2,
			double paramDouble3, double paramDouble4) {
		this.m_dbound.Set(paramDouble1, paramDouble2, paramDouble3,
				paramDouble4);
	}

	public void SetChartSize(double paramDouble1, double paramDouble2) {
		this.m_chartsize.Set(paramDouble1, paramDouble2);
	}

	public int GetLegendVisible() {
		return this.m_legend;
	}

	public void SetLegendVisible(int paramInt) {
		this.m_legend = paramInt;
	}

	public boolean GetTableVisible() {
		return this.m_btable;
	}

	public void SetTableVisible(boolean paramBoolean) {
		this.m_btable = paramBoolean;
	}

	public boolean GetCaptionVisible() {
		return this.m_bcaption;
	}

	public void SetCaptionVisible(boolean paramBoolean) {
		this.m_bcaption = paramBoolean;
	}

	public boolean GetBorderVisible() {
		return this.m_bborder;
	}

	public void SetBorderVisible(boolean paramBoolean) {
		this.m_bborder = paramBoolean;
	}

	public String GetCaption() {
		return this.m_caption;
	}

	public void SetCaption(String paramString) {
		this.m_caption = paramString;
	}

	public String GetBGColor() {
		return this.m_bgcolor;
	}

	public void SetBGColor(String paramString) {
		this.m_bgcolor = paramString;
		if (this.m_bgcolor == null)
			this.m_bgcolor = "#FFFFFF";
	}

	public int GetChartDegree() {
		return this.m_degree;
	}

	public void SetChartDegree(int paramInt) {
		this.m_degree = paramInt;
	}

	public void SetChartDegreeValue(double paramDouble1, double paramDouble2) {
		this.m_degreevalue = paramDouble1;
		this.m_minvalue = paramDouble2;
	}

	public double GetChartOffSize() {
		return this.m_offsize;
	}

	public void SetChartOffSize(double paramDouble) {
		this.m_offsize = paramDouble;
	}

	public void LoadData(String paramString) {
		this.m_DataSet = new DataSet();
		this.m_DataSet.LoadDataSet(paramString);
	}

	public String generateChart(int paramInt, String paramString,
			boolean paramBoolean1, boolean paramBoolean2) {
		StringBuffer localStringBuffer = new StringBuffer();
		Object localObject = null;
		localStringBuffer.append(GetVmlHeader());
		localStringBuffer
				.append("<head><meta http-equiv='Content-Type' content='text/html; charset=gb2312'><style>TD {    FONT-SIZE: 9pt}</style></head><body topmargin=0 leftmargin=0 scroll=AUTO>");
		switch (paramInt) {
		case 0:
			localStringBuffer.append(CreateLine(paramString));
			break;
		case 1:
			if (paramBoolean2 == true)
				localStringBuffer.append(Create3DBar(paramString));
			else
				localStringBuffer.append(CreateBar(paramString));
			break;
		case 2:
			if (paramBoolean2 == true)
				localStringBuffer
						.append(Create3DPie(paramString, paramBoolean1));
			else
				localStringBuffer.append(CreatePie(paramString, paramBoolean1));
			break;
		case 4:
			break;
		case 5:
			if (paramBoolean2 == true)
				localStringBuffer.append(Create3DCross("new", paramBoolean1));
			else
				localStringBuffer.append(CreateCross("new", paramBoolean1));
			break;
		case 3:
		}
		localStringBuffer.append("</body></html>");
		return localStringBuffer.toString();
	}

	public String CreateLine(String paramString) {
		if (this.m_DataSet == null)
			return "";
		PreCalculate(1);
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(VMLChartBuilder.createStartGroup(
				this.m_dbound, paramString, null, 0));
		if (this.m_bborder)
			localStringBuffer.append(VMLChartBuilder.createBackground(
					new DoubleRectangle(0.0D, 0.0D, this.m_dbound.width,
							this.m_dbound.height), null, null, false, 0));
		if (this.m_bgcolor != null)
			localStringBuffer.append(VMLChartBuilder.createBackground(
					this.m_chartbounds, this.m_bgcolor, null, false, 0));
		localStringBuffer.append(VMLChartBuilder.createVAxes(
				this.m_chartbounds, this.m_DataSet, this.m_degree,
				this.m_degreevalue, this.m_minvalue, this.m_btable, 0));
		if (this.m_bcaption)
			localStringBuffer.append(VMLChartBuilder.createCaption(
					this.m_captionbounds, this.m_caption, 0));
		localStringBuffer.append(VMLChartBuilder.createLine(this.m_chartbounds,
				this.m_DataSet, this.m_maxvalue, this.m_minvalue,
				this.m_colorpicker, this.m_vmlsign, 0));
		if (this.m_btable)
			localStringBuffer.append(VMLChartBuilder.createTable(
					this.m_chartbounds, this.m_offsize, this.m_DataSet, true,
					this.m_colorpicker, this.m_vmlsign, 0));
		switch (this.m_legend) {
		case 1:
			localStringBuffer.append(VMLChartBuilder.createHLegend(
					this.m_legendbounds, this.m_DataSet, this.m_colorpicker,
					this.m_vmlsign, this.m_minfontwidth, true, 0));
			break;
		case 2:
			localStringBuffer.append(VMLChartBuilder.createVLegend(
					this.m_legendbounds, this.m_DataSet, this.m_colorpicker,
					this.m_minfontwidth, 0));
		}
		localStringBuffer.append(VMLChartBuilder.createEndGroup());
		return localStringBuffer.toString();
	}

	public String CreateBar(String paramString) {
		if (this.m_DataSet == null)
			return "";
		PreCalculate(2);
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(VMLChartBuilder.createStartGroup(
				this.m_dbound, paramString, null, 0));
		if (this.m_bborder)
			localStringBuffer.append(VMLChartBuilder.createBackground(
					new DoubleRectangle(0.0D, 0.0D, this.m_dbound.width,
							this.m_dbound.height), null, null, false, 0));
		if (this.m_bgcolor != null)
			localStringBuffer.append(VMLChartBuilder.createBackground(
					this.m_chartbounds, this.m_bgcolor, null, false, 0));
		localStringBuffer.append(VMLChartBuilder.createVAxes(
				this.m_chartbounds, this.m_DataSet, this.m_degree,
				this.m_degreevalue, this.m_minvalue, this.m_btable, 0));
		if (this.m_bcaption)
			localStringBuffer.append(VMLChartBuilder.createCaption(
					this.m_captionbounds, this.m_caption, 0));
		localStringBuffer.append(VMLChartBuilder.createVBar(this.m_chartbounds,
				this.m_DataSet, this.m_maxvalue, this.m_minvalue,
				this.m_colorpicker, 0));
		if (this.m_btable)
			localStringBuffer.append(VMLChartBuilder.createTable(
					this.m_chartbounds, this.m_offsize, this.m_DataSet, false,
					this.m_colorpicker, this.m_vmlsign, 0));
		switch (this.m_legend) {
		case 1:
			localStringBuffer.append(VMLChartBuilder.createHLegend(
					this.m_legendbounds, this.m_DataSet, this.m_colorpicker,
					this.m_vmlsign, this.m_minfontwidth, false, 0));
			break;
		case 2:
			localStringBuffer.append(VMLChartBuilder.createVLegend(
					this.m_legendbounds, this.m_DataSet, this.m_colorpicker,
					this.m_minfontwidth, 0));
		}
		return localStringBuffer.toString();
	}

	public String Create3DBar(String paramString) {
		if (this.m_DataSet == null)
			return "";
		PreCalculate(2);
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(VMLChartBuilder.createStartGroup(
				this.m_dbound, paramString, null, 0));
		if (this.m_bborder)
			localStringBuffer.append(VMLChartBuilder.createBackground(
					new DoubleRectangle(0.0D, 0.0D, this.m_dbound.width,
							this.m_dbound.height), null, null, true, 0));
		if (this.m_bgcolor != null)
			localStringBuffer.append(VMLChartBuilder.createBackground(
					this.m_chartbounds, this.m_bgcolor, null, true, 0));
		localStringBuffer.append(VMLChartBuilder.create3DVAxes(
				this.m_chartbounds, this.m_DataSet, this.m_degree,
				this.m_degreevalue, this.m_minvalue, this.m_bgcolor,
				this.m_btable, 0));
		if (this.m_bcaption)
			localStringBuffer.append(VMLChartBuilder.createCaption(
					this.m_captionbounds, this.m_caption, 0));
		localStringBuffer.append(VMLChartBuilder.create3DVBar(
				this.m_chartbounds, this.m_DataSet, this.m_maxvalue,
				this.m_minvalue, this.m_colorpicker, this.m_vmlsign, 0));
		if (this.m_btable)
			localStringBuffer.append(VMLChartBuilder.createTable(
					this.m_chartbounds, this.m_offsize, this.m_DataSet, false,
					this.m_colorpicker, this.m_vmlsign, 0));
		switch (this.m_legend) {
		case 1:
			localStringBuffer.append(VMLChartBuilder.createHLegend(
					this.m_legendbounds, this.m_DataSet, this.m_colorpicker,
					this.m_vmlsign, this.m_minfontwidth, false, 0));
			break;
		case 2:
			localStringBuffer.append(VMLChartBuilder.createVLegend(
					this.m_legendbounds, this.m_DataSet, this.m_colorpicker,
					this.m_minfontwidth, 0));
		}
		return localStringBuffer.toString();
	}

	public String CreateCross(String paramString, boolean paramBoolean) {
		if (this.m_DataSet == null)
			return "";
		PreCalculate(2);
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(VMLChartBuilder.createStartGroup(
				this.m_dbound, paramString, null, 0));
		if (this.m_bborder)
			localStringBuffer.append(VMLChartBuilder.createBackground(
					new DoubleRectangle(0.0D, 0.0D, this.m_dbound.width,
							this.m_dbound.height), null, null, false, 0));
		if (this.m_bgcolor != null)
			localStringBuffer.append(VMLChartBuilder.createBackground(
					this.m_chartbounds, this.m_bgcolor, null, false, 0));
		localStringBuffer.append(VMLChartBuilder.createHAxes(
				this.m_chartbounds, this.m_DataSet, this.m_degree,
				this.m_degreevalue, this.m_minvalue, 0));
		if (this.m_bcaption)
			localStringBuffer.append(VMLChartBuilder.createCaption(
					this.m_captionbounds, this.m_caption, 0));
		localStringBuffer.append(VMLChartBuilder.createHBar(this.m_chartbounds,
				this.m_DataSet, this.m_maxvalue, this.m_minvalue,
				this.m_colorpicker, paramBoolean, 0));
		if (this.m_btable)
			localStringBuffer.append(VMLChartBuilder.createTable(
					this.m_chartbounds, this.m_offsize, this.m_DataSet, false,
					this.m_colorpicker, this.m_vmlsign, 0));
		switch (this.m_legend) {
		case 1:
			localStringBuffer.append(VMLChartBuilder.createHLegend(
					this.m_legendbounds, this.m_DataSet, this.m_colorpicker,
					this.m_vmlsign, this.m_minfontwidth, false, 0));
			break;
		case 2:
			localStringBuffer.append(VMLChartBuilder.createVLegend(
					this.m_legendbounds, this.m_DataSet, this.m_colorpicker,
					this.m_minfontwidth, 0));
		}
		return localStringBuffer.toString();
	}

	public String Create3DCross(String paramString, boolean paramBoolean) {
		if (this.m_DataSet == null)
			return "";
		PreCalculate(2);
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(VMLChartBuilder.createStartGroup(
				this.m_dbound, paramString, null, 0));
		if (this.m_bborder)
			localStringBuffer.append(VMLChartBuilder.createBackground(
					new DoubleRectangle(0.0D, 0.0D, this.m_dbound.width,
							this.m_dbound.height), null, null, false, 0));
		if (this.m_bgcolor != null)
			localStringBuffer.append(VMLChartBuilder.createBackground(
					this.m_chartbounds, this.m_bgcolor, null, true, 0));
		localStringBuffer.append(VMLChartBuilder.create3DHAxes(
				this.m_chartbounds, this.m_DataSet, this.m_degree,
				this.m_degreevalue, this.m_minvalue, this.m_bgcolor, 0));
		if (this.m_bcaption)
			localStringBuffer.append(VMLChartBuilder.createCaption(
					this.m_captionbounds, this.m_caption, 0));
		localStringBuffer.append(VMLChartBuilder.create3DHBar(
				this.m_chartbounds, this.m_DataSet, this.m_maxvalue,
				this.m_minvalue, this.m_colorpicker, paramBoolean, 0));
		if (this.m_btable)
			localStringBuffer.append(VMLChartBuilder.createTable(
					this.m_chartbounds, this.m_offsize, this.m_DataSet, false,
					this.m_colorpicker, this.m_vmlsign, 0));
		switch (this.m_legend) {
		case 1:
			localStringBuffer.append(VMLChartBuilder.createHLegend(
					this.m_legendbounds, this.m_DataSet, this.m_colorpicker,
					this.m_vmlsign, this.m_minfontwidth, false, 0));
			break;
		case 2:
			localStringBuffer.append(VMLChartBuilder.createVLegend(
					this.m_legendbounds, this.m_DataSet, this.m_colorpicker,
					this.m_minfontwidth, 0));
		}
		return localStringBuffer.toString();
	}

	public String CreatePie(String paramString, boolean paramBoolean) {
		if (this.m_DataSet == null)
			return "";
		PreCalculate(3);
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(VMLChartBuilder.createStartGroup(
				this.m_dbound, paramString, null, 0));
		if (this.m_bborder)
			localStringBuffer.append(VMLChartBuilder.createBackground(
					new DoubleRectangle(0.0D, 0.0D, this.m_dbound.width,
							this.m_dbound.height), null, null, false, 0));
		if (this.m_bcaption)
			localStringBuffer.append(VMLChartBuilder.createCaption(
					this.m_captionbounds, this.m_caption, 0));
		localStringBuffer.append(VMLChartBuilder.createPie(this.m_chartbounds,
				this.m_DataSet, this.m_colorpicker, paramBoolean, 0));
		switch (this.m_legend) {
		case 1:
			localStringBuffer.append(VMLChartBuilder.createHLegend(
					this.m_legendbounds, this.m_DataSet, this.m_colorpicker,
					this.m_vmlsign, this.m_minfontwidth, false, 0));
			break;
		case 2:
			localStringBuffer.append(VMLChartBuilder.createVLegend(
					this.m_legendbounds, this.m_DataSet, this.m_colorpicker,
					this.m_minfontwidth, 0));
		}
		localStringBuffer.append(VMLChartBuilder.createEndGroup());
		return localStringBuffer.toString();
	}

	public String Create3DPie(String paramString, boolean paramBoolean) {
		if (this.m_DataSet == null)
			return "";
		PreCalculate(3);
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(VMLChartBuilder.createStartGroup(
				this.m_dbound, paramString, null, 0));
		if (this.m_bborder)
			localStringBuffer.append(VMLChartBuilder.createBackground(
					new DoubleRectangle(0.0D, 0.0D, this.m_dbound.width,
							this.m_dbound.height), null, null, false, 0));
		if (this.m_bcaption)
			localStringBuffer.append(VMLChartBuilder.createCaption(
					this.m_captionbounds, this.m_caption, 0));
		localStringBuffer.append(VMLChartBuilder.create3DPie(
				this.m_chartbounds, this.m_DataSet, this.m_colorpicker,
				paramBoolean, 0));
		switch (this.m_legend) {
		case 1:
			localStringBuffer.append(VMLChartBuilder.createHLegend(
					this.m_legendbounds, this.m_DataSet, this.m_colorpicker,
					this.m_vmlsign, this.m_minfontwidth, false, 0));
			break;
		case 2:
			localStringBuffer.append(VMLChartBuilder.createVLegend(
					this.m_legendbounds, this.m_DataSet, this.m_colorpicker,
					this.m_minfontwidth, 0));
		}
		localStringBuffer.append(VMLChartBuilder.createEndGroup());
		return localStringBuffer.toString();
	}

	private void PreCalculateEx(int i) {
		if (m_DataSet == null)
			return;
		int j = 0;
		for (int k = 0; k < m_DataSet.m_Column.size(); k++) {
			Column column = (Column) m_DataSet.m_Column.get(k);
			String s = column.GetCaption();
			int l = s.length();
			if (l > j)
				j = l;
		}

		m_minfontwidth = (double) j * 12D;
		double d = 0.0D;
		d += m_marge.cx;
		if (m_btable) {
			double d1 = 0.0D;
			if (i == 1) {
				d1 += 2D;
				d1 += 36D;
				d1 += 2D;
				d1 += m_minfontwidth;
				d1 += 2D;
			}
			if (i == 2) {
				d1 += 2D;
				d1 += 8D;
				d1 += 2D;
				d1 += m_minfontwidth;
				d1 += 2D;
			}
			if (i == 3) {
				d1 += 2D;
				d1 += 8D;
				d1 += 2D;
				d1 += m_minfontwidth;
				d1 += 2D;
			}
			d += d1;
		}
		if (d > m_offsize + m_marge.cx)
			m_offsize = d - m_marge.cx;
		if (m_offsize < 40D)
			m_offsize = 40D;
		if (i == 3)
			m_offsize = m_marge.cx;
		double d2 = 0.0D;
		double d3 = 0.0D;
		d2 += m_marge.cx;
		d2 += m_offsize;
		d2 += m_chartsize.cx;
		if (m_legend == 2) {
			d2 += m_marge.cx;
			double d4 = 0.0D;
			d4 += 2D;
			d4 += m_minfontwidth;
			d4 += 2D;
			d4 += 30D;
			d4 += 2D;
			d2 += d4;
		}
		d2 += m_marge.cx;
		d3 += m_marge.cy;
		if (d2 > m_dbound.width)
			m_dbound.width = d2;
		if (m_bcaption) {
			m_captionbounds.Set(m_marge.cx, m_marge.cy, m_dbound.width
					- m_marge.cx * 2D, 30D);
			d3 += 30D;
			d3 += m_marge.cy;
		}
		DoublePoint doublepoint = new DoublePoint(m_offsize + m_marge.cx, d3);
		m_chartbounds.Set(doublepoint, m_chartsize);
		d3 += m_chartbounds.height;
		d3 += 18D;
		if (m_btable && i != 3) {
			int i1 = m_DataSet.m_Column.size();
			d3 += 18D * (double) i1;
		}
		d3 += m_marge.cy;
		if (m_legend == 1) {
			double d5 = 0.0D;
			if (i == 1) {
				d5 += 2D;
				d5 += 36D;
				d5 += 2D;
				d5 += m_minfontwidth;
				d5 += 2D;
			}
			if (i == 2) {
				d5 += 2D;
				d5 += 8D;
				d5 += 2D;
				d5 += m_minfontwidth;
				d5 += 2D;
			}
			if (i == 3) {
				d5 += 2D;
				d5 += 8D;
				d5 += 2D;
				d5 += m_minfontwidth;
				d5 += 2D;
			}
			double d7 = m_dbound.width - m_marge.cx * 2D;
			int k1 = m_DataSet.m_Column.size();
			int l1 = (int) (d7 / d5);
			if (l1 < 1)
				l1 = 1;
			double d11 = (double) k1 / (double) l1;
			int i2 = k1 / l1;
			if (d11 - (double) i2 > 0.0D)
				i2++;
			double d13 = 18D * (double) i2;
			m_legendbounds.Set(m_marge.cx, d3, d7, d13);
			if (i2 == 1) {
				double d8 = d5 * (double) k1;
				double d14 = m_dbound.width / 2D - d8 / 2D;
				m_legendbounds.Set(d14, d3, d8, d13);
			}
			d3 += d13;
			d3 += m_marge.cy;
		}
		if (d3 > m_dbound.height)
			m_dbound.height = d3;
		if (m_legend == 2) {
			double d6 = 0.0D;
			d6 += 2D;
			d6 += m_minfontwidth;
			d6 += 2D;
			d6 += 30D;
			d6 += 2D;
			int j1 = m_DataSet.m_Column.size();
			double d9 = 30D * (double) j1;
			double d10 = m_chartbounds.x + m_chartbounds.width + m_marge.cx;
			double d12 = m_chartbounds.y + m_marge.cy;
			m_legendbounds.Set(d10, d12, d6, d9);
		}
		Axes axes = new Axes(m_DataSet, m_degree, m_degreevalue, m_minvalue,
				false);
		m_maxvalue = axes.GetMaxValue();
		m_minvalue = axes.GetMinValue();
	}

	// private void PreCalculateEx(int paramInt)
	// {
	// if (this.m_DataSet == null)
	// return;
	// int i = 0;
	// for (int j = 0; j < this.m_DataSet.m_Column.size(); j++)
	// {
	// Column localColumn = (Column)this.m_DataSet.m_Column.get(j);
	// String str = localColumn.GetCaption();
	// int k = str.length();
	// if (k > i)
	// i = k;
	// }
	// this.m_minfontwidth = (i * 12.0D);
	// double d1 = 0.0D;
	// d1 += this.m_marge.cx;
	// if (this.m_btable)
	// {
	// d2 = 0.0D;
	// if (paramInt == 1)
	// {
	// d2 += 2.0D;
	// d2 += 36.0D;
	// d2 += 2.0D;
	// d2 += this.m_minfontwidth;
	// d2 += 2.0D;
	// }
	// if (paramInt == 2)
	// {
	// d2 += 2.0D;
	// d2 += 8.0D;
	// d2 += 2.0D;
	// d2 += this.m_minfontwidth;
	// d2 += 2.0D;
	// }
	// if (paramInt == 3)
	// {
	// d2 += 2.0D;
	// d2 += 8.0D;
	// d2 += 2.0D;
	// d2 += this.m_minfontwidth;
	// d2 += 2.0D;
	// }
	// d1 += d2;
	// }
	// if (d1 > this.m_offsize + this.m_marge.cx)
	// this.m_offsize = (d1 - this.m_marge.cx);
	// if (this.m_offsize < 40.0D)
	// this.m_offsize = 40.0D;
	// if (paramInt == 3)
	// this.m_offsize = this.m_marge.cx;
	// double d2 = 0.0D;
	// double d3 = 0.0D;
	// d2 += this.m_marge.cx;
	// d2 += this.m_offsize;
	// d2 += this.m_chartsize.cx;
	// if (this.m_legend == 2)
	// {
	// d2 += this.m_marge.cx;
	// double d4 = 0.0D;
	// d4 += 2.0D;
	// d4 += this.m_minfontwidth;
	// d4 += 2.0D;
	// d4 += 30.0D;
	// d4 += 2.0D;
	// d2 += d4;
	// }
	// d2 += this.m_marge.cx;
	// d3 += this.m_marge.cy;
	// if (d2 > this.m_dbound.width)
	// this.m_dbound.width = d2;
	// if (this.m_bcaption)
	// {
	// this.m_captionbounds.Set(this.m_marge.cx, this.m_marge.cy,
	// this.m_dbound.width - this.m_marge.cx * 2.0D, 30.0D);
	// d3 += 30.0D;
	// d3 += this.m_marge.cy;
	// }
	// DoublePoint localDoublePoint = new DoublePoint(this.m_offsize +
	// this.m_marge.cx, d3);
	// this.m_chartbounds.Set(localDoublePoint, this.m_chartsize);
	// d3 += this.m_chartbounds.height;
	// d3 += 18.0D;
	// if ((this.m_btable) && (paramInt != 3))
	// {
	// int m = this.m_DataSet.m_Column.size();
	// d3 += 18.0D * m;
	// }
	// d3 += this.m_marge.cy;
	// double d5;
	// if (this.m_legend == 1)
	// {
	// d5 = 0.0D;
	// if (paramInt == 1)
	// {
	// d5 += 2.0D;
	// d5 += 36.0D;
	// d5 += 2.0D;
	// d5 += this.m_minfontwidth;
	// d5 += 2.0D;
	// }
	// if (paramInt == 2)
	// {
	// d5 += 2.0D;
	// d5 += 8.0D;
	// d5 += 2.0D;
	// d5 += this.m_minfontwidth;
	// d5 += 2.0D;
	// }
	// if (paramInt == 3)
	// {
	// d5 += 2.0D;
	// d5 += 8.0D;
	// d5 += 2.0D;
	// d5 += this.m_minfontwidth;
	// d5 += 2.0D;
	// }
	// double d6 = this.m_dbound.width - this.m_marge.cx * 2.0D;
	// int i1 = this.m_DataSet.m_Column.size();
	// int i2 = (int)(d6 / d5);
	// if (i2 < 1)
	// i2 = 1;
	// double d9 = i1 / i2;
	// int i3 = i1 / i2;
	// if (d9 - i3 > 0.0D)
	// i3++;
	// double d11 = 18.0D * i3;
	// this.m_legendbounds.Set(this.m_marge.cx, d3, d6, d11);
	// if (i3 == 1)
	// {
	// d6 = d5 * i1;
	// double d12 = this.m_dbound.width / 2.0D - d6 / 2.0D;
	// this.m_legendbounds.Set(d12, d3, d6, d11);
	// }
	// d3 += d11;
	// d3 += this.m_marge.cy;
	// }
	// if (d3 > this.m_dbound.height)
	// this.m_dbound.height = d3;
	// if (this.m_legend == 2)
	// {
	// d5 = 0.0D;
	// d5 += 2.0D;
	// d5 += this.m_minfontwidth;
	// d5 += 2.0D;
	// d5 += 30.0D;
	// d5 += 2.0D;
	// int n = this.m_DataSet.m_Column.size();
	// double d7 = 30.0D * n;
	// double d8 = this.m_chartbounds.x + this.m_chartbounds.width +
	// this.m_marge.cx;
	// double d10 = this.m_chartbounds.y + this.m_marge.cy;
	// this.m_legendbounds.Set(d8, d10, d5, d7);
	// }
	// Axes localAxes = new Axes(this.m_DataSet, this.m_degree,
	// this.m_degreevalue, this.m_minvalue, false);
	// this.m_maxvalue = localAxes.GetMaxValue();
	// this.m_minvalue = localAxes.GetMinValue();
	// }
	private void PreCalculate(int i) {
		if (m_DataSet == null)
			return;
		int j = 0;
		for (int k = 0; k < m_DataSet.m_Column.size(); k++) {
			Column column = (Column) m_DataSet.m_Column.get(k);
			String s = column.GetCaption();
			int l = s.length();
			if (l > j)
				j = l;
		}

		m_minfontwidth = (double) j * 12D;
		double d = 0.0D;
		d += m_marge.cx;
		if (m_btable) {
			double d1 = 0.0D;
			if (i == 1) {
				d1 += 2D;
				d1 += 36D;
				d1 += 2D;
				d1 += m_minfontwidth;
				d1 += 2D;
			}
			if (i == 2) {
				d1 += 2D;
				d1 += 8D;
				d1 += 2D;
				d1 += m_minfontwidth;
				d1 += 2D;
			}
			if (i == 3) {
				d1 += 2D;
				d1 += 8D;
				d1 += 2D;
				d1 += m_minfontwidth;
				d1 += 2D;
			}
			d += d1;
		}
		if (d > m_offsize + m_marge.cx)
			m_offsize = d - m_marge.cx;
		if (m_offsize < 40D)
			m_offsize = 40D;
		if (i == 3)
			m_offsize = m_marge.cx;
		double d2 = 0.0D;
		double d3 = 0.0D;
		d2 += m_marge.cx;
		d2 += m_offsize;
		if (m_legend == 2) {
			d2 += m_marge.cx;
			double d4 = 0.0D;
			d4 += 2D;
			d4 += m_minfontwidth;
			d4 += 2D;
			d4 += 30D;
			d4 += 2D;
			d2 += d4;
		}
		d2 += m_marge.cx;
		d3 += m_marge.cy;
		m_chartsize.cx = m_dbound.width - d2;
		if (m_chartsize.cx < 10D) {
			m_chartsize.cx = 10D;
			m_dbound.width = d2 + 10D;
		}
		if (m_bcaption) {
			m_captionbounds.Set(m_marge.cx, m_marge.cy, m_dbound.width
					- m_marge.cx * 2D, 30D);
			d3 += 30D;
			d3 += m_marge.cy;
		}
		DoublePoint doublepoint = new DoublePoint(m_offsize + m_marge.cx, d3);
		m_chartsize.cy = 0.0D;
		m_chartbounds.Set(doublepoint, m_chartsize);
		d3 += m_chartbounds.height;
		d3 += 18D;
		if (m_btable && i != 3) {
			int i1 = m_DataSet.m_Column.size();
			d3 += 18D * (double) i1;
		}
		d3 += m_marge.cy;
		if (m_legend == 1) {
			double d5 = 0.0D;
			if (i == 1) {
				d5 += 2D;
				d5 += 36D;
				d5 += 2D;
				d5 += m_minfontwidth;
				d5 += 2D;
			}
			if (i == 2) {
				d5 += 2D;
				d5 += 8D;
				d5 += 2D;
				d5 += m_minfontwidth;
				d5 += 2D;
			}
			if (i == 3) {
				d5 += 2D;
				d5 += 8D;
				d5 += 2D;
				d5 += m_minfontwidth;
				d5 += 2D;
			}
			double d7 = m_dbound.width - m_marge.cx * 2D;
			int k1 = m_DataSet.m_Column.size();
			int l1 = (int) (d7 / d5);
			if (l1 < 1)
				l1 = 1;
			double d11 = (double) k1 / (double) l1;
			int i2 = k1 / l1;
			if (d11 - (double) i2 > 0.0D)
				i2++;
			double d13 = 18D * (double) i2;
			m_legendbounds.Set(m_marge.cx, d3, d7, d13);
			if (i2 == 1) {
				double d8 = d5 * (double) k1;
				double d14 = m_dbound.width / 2D - d8 / 2D;
				m_legendbounds.Set(d14, d3, d8, d13);
			}
			d3 += d13;
			d3 += m_marge.cy;
		}
		m_chartsize.cy = m_dbound.height - d3;
		if (m_chartsize.cy < 10D) {
			m_chartsize.cy = 10D;
			m_dbound.height = d3 + 10D;
		}
		m_chartbounds.Set(doublepoint, m_chartsize);
		m_legendbounds.Set(m_legendbounds.x, m_legendbounds.y + m_chartsize.cy,
				m_legendbounds.width, m_legendbounds.height);
		if (m_legend == 2) {
			double d6 = 0.0D;
			d6 += 2D;
			d6 += m_minfontwidth;
			d6 += 2D;
			d6 += 30D;
			d6 += 2D;
			int j1 = m_DataSet.m_Column.size();
			double d9 = 30D * (double) j1;
			double d10 = m_chartbounds.x + m_chartbounds.width + m_marge.cx;
			double d12 = m_chartbounds.y + m_marge.cy;
			m_legendbounds.Set(d10, d12, d6, d9);
		}
		Axes axes = new Axes(m_DataSet, m_degree, m_degreevalue, m_minvalue,
				false);
		m_maxvalue = axes.GetMaxValue();
		m_minvalue = axes.GetMinValue();
	}

	// private void PreCalculate(int paramInt)
	// {
	// if (this.m_DataSet == null)
	// return;
	// int i = 0;
	// for (int j = 0; j < this.m_DataSet.m_Column.size(); j++)
	// {
	// Column localColumn = (Column)this.m_DataSet.m_Column.get(j);
	// String str = localColumn.GetCaption();
	// int k = str.length();
	// if (k > i)
	// i = k;
	// }
	// this.m_minfontwidth = (i * 12.0D);
	// double d1 = 0.0D;
	// d1 += this.m_marge.cx;
	// if (this.m_btable)
	// {
	// d2 = 0.0D;
	// if (paramInt == 1)
	// {
	// d2 += 2.0D;
	// d2 += 36.0D;
	// d2 += 2.0D;
	// d2 += this.m_minfontwidth;
	// d2 += 2.0D;
	// }
	// if (paramInt == 2)
	// {
	// d2 += 2.0D;
	// d2 += 8.0D;
	// d2 += 2.0D;
	// d2 += this.m_minfontwidth;
	// d2 += 2.0D;
	// }
	// if (paramInt == 3)
	// {
	// d2 += 2.0D;
	// d2 += 8.0D;
	// d2 += 2.0D;
	// d2 += this.m_minfontwidth;
	// d2 += 2.0D;
	// }
	// d1 += d2;
	// }
	// if (d1 > this.m_offsize + this.m_marge.cx)
	// this.m_offsize = (d1 - this.m_marge.cx);
	// if (this.m_offsize < 40.0D)
	// this.m_offsize = 40.0D;
	// if (paramInt == 3)
	// this.m_offsize = this.m_marge.cx;
	// double d2 = 0.0D;
	// double d3 = 0.0D;
	// d2 += this.m_marge.cx;
	// d2 += this.m_offsize;
	// if (this.m_legend == 2)
	// {
	// d2 += this.m_marge.cx;
	// double d4 = 0.0D;
	// d4 += 2.0D;
	// d4 += this.m_minfontwidth;
	// d4 += 2.0D;
	// d4 += 30.0D;
	// d4 += 2.0D;
	// d2 += d4;
	// }
	// d2 += this.m_marge.cx;
	// d3 += this.m_marge.cy;
	// this.m_chartsize.cx = (this.m_dbound.width - d2);
	// if (this.m_chartsize.cx < 10.0D)
	// {
	// this.m_chartsize.cx = 10.0D;
	// this.m_dbound.width = (d2 + 10.0D);
	// }
	// if (this.m_bcaption)
	// {
	// this.m_captionbounds.Set(this.m_marge.cx, this.m_marge.cy,
	// this.m_dbound.width - this.m_marge.cx * 2.0D, 30.0D);
	// d3 += 30.0D;
	// d3 += this.m_marge.cy;
	// }
	// DoublePoint localDoublePoint = new DoublePoint(this.m_offsize +
	// this.m_marge.cx, d3);
	// this.m_chartsize.cy = 0.0D;
	// this.m_chartbounds.Set(localDoublePoint, this.m_chartsize);
	// d3 += this.m_chartbounds.height;
	// d3 += 18.0D;
	// if ((this.m_btable) && (paramInt != 3))
	// {
	// int m = this.m_DataSet.m_Column.size();
	// d3 += 18.0D * m;
	// }
	// d3 += this.m_marge.cy;
	// double d5;
	// if (this.m_legend == 1)
	// {
	// d5 = 0.0D;
	// if (paramInt == 1)
	// {
	// d5 += 2.0D;
	// d5 += 36.0D;
	// d5 += 2.0D;
	// d5 += this.m_minfontwidth;
	// d5 += 2.0D;
	// }
	// if (paramInt == 2)
	// {
	// d5 += 2.0D;
	// d5 += 8.0D;
	// d5 += 2.0D;
	// d5 += this.m_minfontwidth;
	// d5 += 2.0D;
	// }
	// if (paramInt == 3)
	// {
	// d5 += 2.0D;
	// d5 += 8.0D;
	// d5 += 2.0D;
	// d5 += this.m_minfontwidth;
	// d5 += 2.0D;
	// }
	// double d6 = this.m_dbound.width - this.m_marge.cx * 2.0D;
	// int i1 = this.m_DataSet.m_Column.size();
	// int i2 = (int)(d6 / d5);
	// if (i2 < 1)
	// i2 = 1;
	// double d9 = i1 / i2;
	// int i3 = i1 / i2;
	// if (d9 - i3 > 0.0D)
	// i3++;
	// double d11 = 18.0D * i3;
	// this.m_legendbounds.Set(this.m_marge.cx, d3, d6, d11);
	// if (i3 == 1)
	// {
	// d6 = d5 * i1;
	// double d12 = this.m_dbound.width / 2.0D - d6 / 2.0D;
	// this.m_legendbounds.Set(d12, d3, d6, d11);
	// }
	// d3 += d11;
	// d3 += this.m_marge.cy;
	// }
	// this.m_chartsize.cy = (this.m_dbound.height - d3);
	// if (this.m_chartsize.cy < 10.0D)
	// {
	// this.m_chartsize.cy = 10.0D;
	// this.m_dbound.height = (d3 + 10.0D);
	// }
	// this.m_chartbounds.Set(localDoublePoint, this.m_chartsize);
	// this.m_legendbounds.Set(this.m_legendbounds.x, this.m_legendbounds.y +
	// this.m_chartsize.cy, this.m_legendbounds.width,
	// this.m_legendbounds.height);
	// if (this.m_legend == 2)
	// {
	// d5 = 0.0D;
	// d5 += 2.0D;
	// d5 += this.m_minfontwidth;
	// d5 += 2.0D;
	// d5 += 30.0D;
	// d5 += 2.0D;
	// int n = this.m_DataSet.m_Column.size();
	// double d7 = 30.0D * n;
	// double d8 = this.m_chartbounds.x + this.m_chartbounds.width +
	// this.m_marge.cx;
	// double d10 = this.m_chartbounds.y + this.m_marge.cy;
	// this.m_legendbounds.Set(d8, d10, d5, d7);
	// }
	// Axes localAxes = new Axes(this.m_DataSet, this.m_degree,
	// this.m_degreevalue, this.m_minvalue, false);
	// this.m_maxvalue = localAxes.GetMaxValue();
	// this.m_minvalue = localAxes.GetMinValue();
	// }

	public static void main(String[] paramArrayOfString) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
		localStringBuffer.append("<root>");
		localStringBuffer.append("<CONTENT_RECORDSET>");
		localStringBuffer
				.append("<row caption=\"1.得到付坚峰\" property=\"c1\"  shapetype=\"0\" onclick=\"alert('nenen')\" />");
		localStringBuffer
				.append("<row caption=\"2.分分德国队\"  property=\"c2\" shapetype=\"1\" href=\"http:\\\\www.sina.com.cn\"/>");
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
		VMLChart localVMLChart = new VMLChart();
		localVMLChart.LoadData(localStringBuffer.toString());
		localVMLChart.SetBound(20.0D, 40.0D, 500.0D, 400.0D);
		localVMLChart.SetChartSize(100.0D, 100.0D);
		localVMLChart.SetTableVisible(true);
		localVMLChart.SetLegendVisible(1);
		localVMLChart.SetChartDegreeValue(0.0D, 0.0D);
		System.out.println(localVMLChart.GetVmlHeader());
		System.out
				.println("<head><meta http-equiv='Content-Type' content='text/html; charset=gb2312'><style>TD {    FONT-SIZE: 9pt}</style></head><body topmargin=0 leftmargin=0 scroll=AUTO>");
		System.out.println(localVMLChart.CreateLine("new1"));
		System.out.println("</body></html>");
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.taglib.chart.vml.chart.VMLChart JD-Core Version: 0.6.1
 */