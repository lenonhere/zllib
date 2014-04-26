package com.zl.base.core.predict;

import com.zl.util.MethodFactory;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

public class predict {
	int Default_Count = 12;
	double exponent = 0.0D;

	public predict() {
		this.Default_Count = 12;
	}

	public predict(int paramInt) {
		this.Default_Count = paramInt;
	}

	private ArrayList createDynaBean(ArrayList paramArrayList) {
		DynaProperty[] arrayOfDynaProperty = {
				new DynaProperty("data", String.class),
				new DynaProperty("realval", String.class),
				new DynaProperty("predictval", String.class),
				new DynaProperty("pointval", String.class),
				new DynaProperty("trendval", String.class) };
		ArrayList localArrayList = new ArrayList();
		BasicDynaClass localBasicDynaClass = new BasicDynaClass("employee",
				null, arrayOfDynaProperty);
		for (int i = 0; i < paramArrayList.size(); i++) {
			preData localpreData = (preData) paramArrayList.get(i);
			try {
				DynaBean localDynaBean = localBasicDynaClass.newInstance();
				localDynaBean.set("data", localpreData.getData());
				localDynaBean.set("realval", localpreData.getRealval());
				localDynaBean.set("predictval", localpreData.getPredictval());
				localDynaBean.set("pointval", localpreData.getPointval());
				localDynaBean.set("trendval", localpreData.getTrendval());
				localArrayList.add(localDynaBean);
			} catch (Exception localException) {
			}
		}
		return localArrayList;
	}

	public double getExponent() {
		return this.exponent;
	}

	public ArrayList GetData(ArrayList arraylist, int i, String s, String s1) {
		if (arraylist.isEmpty() || arraylist == null) {
			// System.out.println("\u8F93\u5165\u53C2\u6570\u4E3A\u7A7A");
			System.out.println("输入参数为空");
			return null;
		}
		ArrayList arraylist1 = new ArrayList();
		ArrayList arraylist2 = new ArrayList();
		int j = arraylist.size();
		if (j < Default_Count)
			// System.out.println("\u4F20\u5165\u53C2\u6570\u4E0D\u591F");
			System.out.println("传入参数不够");
		for (int k = 0; k < j; k++) {
			BasicDynaBean basicdynabean = (BasicDynaBean) arraylist.get(k);
			try {
				String s2 = MethodFactory.getThisString(PropertyUtils
						.getProperty(basicdynabean, s1));
				double d2 = Double.parseDouble(s2);
				String s3 = MethodFactory.getThisString(PropertyUtils
						.getProperty(basicdynabean, s));
				tempData tempdata = new tempData();
				tempdata.str = s3;
				tempdata.account = d2;
				arraylist2.add(tempdata);
			} catch (Exception exception) {
				// System.out.println("\u4F20\u5165\u53C2\u6570\u9519\u8BEF");
				System.out.println("传入参数错误");
				return null;
			}
		}

		double d3 = getSumDt(arraylist2);
		double d4 = getNDt(arraylist2);
		double d5 = getSumtt(j);
		double d6 = getNtt(j);
		double d7 = getAvgD(arraylist2);
		double d8 = (j * (1 + j)) / 2;
		d8 /= j;
		double d1 = (d3 - d4) / (d5 - d6);
		exponent = d1;
		double d = d7 - d1 * d8;
		for (int l = 1; l <= j; l++) {
			preData predata = new preData();
			tempData tempdata1 = (tempData) arraylist2.get(l - 1);
			double d9 = d + d1 * (double) l;
			double d11 = tempdata1.account / d9;
			predata.setData(tempdata1.str);
			predata.setRealval(Double.toString(tempdata1.account));
			if (l == j)
				predata.setPredictval(Double.toString(tempdata1.account));
			else
				predata.setPredictval(null);
			predata.setTrendval(Double.toString(d9));
			predata.setPointval(Double.toString(d11));
			arraylist1.add(predata);
		}

		System.out.println("a=" + d + "\n" + "b=" + d1 + "\n");
		// System.out.println("\u5404\u70B9\u9884\u6D4B\u503C");
		System.out.println("各点预测值");
		for (int i1 = 1; i1 <= j + i; i1++)
			System.out.println(d + d1 * (double) i1);

		System.out.println("");
		for (int j1 = 1; j1 <= i; j1++) {
			int k1 = j1 + j;
			int l1 = k1 / Default_Count;
			int i2 = k1 % Default_Count;
			if (i2 == 0)
				l1--;
			if (l1 == 0) {
				// System.out.println("\u4F20\u5165\u6570\u636E\u4E0D\u8DB3\uFF0C\u65E0\u6CD5\u9884\u6D4B");
				System.out.println("传入数据不足，无法预测");
				return null;
			}
			double d10 = 0.0D;
			for (int j2 = 1; j2 <= l1; j2++) {
				int k2 = k1 - j2 * Default_Count;
				tempData tempdata3 = new tempData();
				tempdata3 = (tempData) arraylist2.get(k2 - 1);
				double d13 = d + d1 * (double) k2;
				double d15 = tempdata3.account / d13;
				d10 += d15;
			}

			d10 /= l1;
			tempData tempdata2 = new tempData();
			double d12 = d + d1 * (double) k1;
			double d14 = d10;
			tempdata2.account = d12 * d10;
			getNextMonth(getRecentMonth(arraylist2), tempdata2);
			arraylist2.add(tempdata2);
			preData predata1 = new preData();
			predata1.setData(tempdata2.str);
			predata1.setRealval(null);
			predata1.setPredictval(Double.toString(tempdata2.account));
			predata1.setTrendval(Double.toString(d12));
			predata1.setPointval(Double.toString(d14));
			arraylist1.add(predata1);
		}

		return createDynaBean(arraylist1);
	}

	// public ArrayList GetData(ArrayList paramArrayList, int paramInt,
	// String paramString1, String paramString2) {
	// if ((paramArrayList.isEmpty() == true) || (paramArrayList == null)) {
	// System.out.println("输入参数为空");
	// return null;
	// }
	// ArrayList localArrayList1 = new ArrayList();
	// ArrayList localArrayList2 = new ArrayList();
	// int i = paramArrayList.size();
	// if (i < this.Default_Count)
	// System.out.println("传入参数不够");
	// for (int j = 0; j < i; j++) {
	// BasicDynaBean localBasicDynaBean = (BasicDynaBean) paramArrayList
	// .get(j);
	// try {
	// String str1 = MethodFactory.getThisString(PropertyUtils
	// .getProperty(localBasicDynaBean, paramString2));
	// double d3 = Double.parseDouble(str1);
	// String str2 = MethodFactory.getThisString(PropertyUtils
	// .getProperty(localBasicDynaBean, paramString1));
	// tempData localtempData1 = new tempData();
	// localtempData1.str = str2;
	// localtempData1.account = d3;
	// localArrayList2.add(localtempData1);
	// } catch (Exception localException) {
	// System.out.println("传入参数错误");
	// return null;
	// }
	// }
	// double d4 = getSumDt(localArrayList2);
	// double d5 = getNDt(localArrayList2);
	// double d6 = getSumtt(i);
	// double d7 = getNtt(i);
	// double d8 = getAvgD(localArrayList2);
	// double d9 = i * (1 + i) / 2;
	// d9 /= i;
	// double d2 = (d4 - d5) / (d6 - d7);
	// this.exponent = d2;
	// double d1 = d8 - d2 * d9;
	// for (int k = 1; k <= i; k++) {
	// preData localpreData1 = new preData();
	// tempData localtempData2 = (tempData) localArrayList2.get(k - 1);
	// double d10 = d1 + d2 * k;
	// double d12 = localtempData2.account / d10;
	// localpreData1.setData(localtempData2.str);
	// localpreData1.setRealval(Double.toString(localtempData2.account));
	// if (k == i)
	// localpreData1.setPredictval(Double
	// .toString(localtempData2.account));
	// else
	// localpreData1.setPredictval(null);
	// localpreData1.setTrendval(Double.toString(d10));
	// localpreData1.setPointval(Double.toString(d12));
	// localArrayList1.add(localpreData1);
	// }
	// System.out.println("a=" + d1 + "\n" + "b=" + d2 + "\n");
	// System.out.println("各点预测值");
	// for (k = 1; k <= i + paramInt; k++)
	// System.out.println(d1 + d2 * k);
	// System.out.println("");
	// for (k = 1; k <= paramInt; k++) {
	// int m = k + i;
	// int n = m / this.Default_Count;
	// int i1 = m % this.Default_Count;
	// if (i1 == 0)
	// n--;
	// if (n == 0) {
	// System.out.println("传入数据不足，无法预测");
	// return null;
	// }
	// double d11 = 0.0D;
	// for (int i2 = 1; i2 <= n; i2++) {
	// int i3 = m - i2 * this.Default_Count;
	// tempData localtempData4 = new tempData();
	// localtempData4 = (tempData) localArrayList2.get(i3 - 1);
	// d14 = d1 + d2 * i3;
	// double d15 = localtempData4.account / d14;
	// d11 += d15;
	// }
	// d11 /= n;
	// tempData localtempData3 = new tempData();
	// double d13 = d1 + d2 * m;
	// double d14 = d11;
	// localtempData3.account = (d13 * d11);
	// getNextMonth(getRecentMonth(localArrayList2), localtempData3);
	// localArrayList2.add(localtempData3);
	// preData localpreData2 = new preData();
	// localpreData2.setData(localtempData3.str);
	// localpreData2.setRealval(null);
	// localpreData2
	// .setPredictval(Double.toString(localtempData3.account));
	// localpreData2.setTrendval(Double.toString(d13));
	// localpreData2.setPointval(Double.toString(d14));
	// localArrayList1.add(localpreData2);
	// }
	// return createDynaBean(localArrayList1);
	// }

	private double getSumDt(ArrayList paramArrayList) {
		int i = paramArrayList.size();
		double d1 = 0.0D;
		for (int j = 0; j < i; j++) {
			tempData localtempData = new tempData();
			localtempData = (tempData) paramArrayList.get(j);
			double d2 = j + 1 * localtempData.account;
			d1 += d2;
		}
		return d1;
	}

	private double getNDt(ArrayList paramArrayList) {
		double d1 = 0.0D;
		int i = paramArrayList.size();
		double d2 = 0.0D;
		double d3 = 0.0D;
		double d4 = 0.0D;
		d3 = getAvgD(paramArrayList);
		d4 = i * (1 + i) / 2;
		d4 /= i;
		d1 = i * d3 * d4;
		return d1;
	}

	private double getSumtt(int paramInt) {
		double d = 0.0D;
		for (int i = 1; i <= paramInt; i++)
			d += i * i;
		return d;
	}

	private double getNtt(int paramInt) {
		double d1 = 0.0D;
		double d2 = 0.0D;
		d2 = paramInt * (1 + paramInt) / 2;
		d2 /= paramInt;
		d1 = paramInt * d2 * d2;
		return d1;
	}

	private double getAvgD(ArrayList paramArrayList) {
		double d = 0.0D;
		int i = paramArrayList.size();
		for (int j = 0; j < i; j++) {
			tempData localtempData = new tempData();
			localtempData = (tempData) paramArrayList.get(j);
			d += localtempData.account;
		}
		d /= i;
		return d;
	}

	private int getSum(int paramInt) {
		int i = (1 + paramInt) * paramInt / 2;
		return i;
	}

	private double getWeight(int paramInt1, int paramInt2) {
		int i = getSum(paramInt2);
		double d = paramInt2 - paramInt1 + 1 / i;
		return d;
	}

	private String getRecentMonth(ArrayList paramArrayList) {
		tempData localtempData = new tempData();
		int i = paramArrayList.size();
		localtempData = (tempData) paramArrayList.get(i - 1);
		return localtempData.str;
	}

	private boolean getNextMonth(String paramString, tempData paramtempData) {
		String str = new String(paramString);
		try {
			str = StringUtils.replace(str, "年", "");
			str = StringUtils.replace(str, "月", "");
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
					"yyyyMM");
			GregorianCalendar localGregorianCalendar = new GregorianCalendar();
			localGregorianCalendar.setTime(localSimpleDateFormat.parse(str));
			localGregorianCalendar.add(2, 1);
			str = localSimpleDateFormat
					.format(localGregorianCalendar.getTime());
			paramtempData.str = str;
		} catch (Exception localException) {
			System.out.println("ddfdf");
			System.out.println(localException.toString());
			return false;
		}
		return true;
	}

	public static void main(String[] paramArrayOfString) {
		System.out.println("ok");
	}

	class tempData {
		String str;
		double account;

		tempData() {
		}
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name: com.zl.base.core.predict.predict
 * JD-Core Version: 0.6.1
 */