package com.common.util;

import java.util.*;
import java.sql.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.BasicDynaBean;
import com.zl.base.core.*;
import com.zl.util.MethodFactory;

import java.io.*;

import javax.servlet.http.HttpServletResponse;
public class exportExcel {
	private static String getfilename(){
		String returnvalue="";
		GregorianCalendar calendar = new GregorianCalendar();
		//年
		String stryear=String.valueOf(calendar.get(Calendar.YEAR));
		//月
		String strmonth ="00"+String.valueOf(calendar.get(Calendar.MONTH)+1);
		strmonth=strmonth.substring(strmonth.length()-2,strmonth.length());
		//日
		String strday ="00"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		strday=strday.substring(strday.length()-2,strday.length());
		//时
		String strhour ="00"+String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		strhour=strhour.substring(strhour.length()-2,strhour.length());
		//分
		String strminute ="00"+String.valueOf(calendar.get(Calendar.MINUTE));
		strminute=strminute.substring(strminute.length()-2,strminute.length());
		//秒
		String strsecond ="00"+String.valueOf(calendar.get(Calendar.SECOND));
		strsecond=strsecond.substring(strsecond.length()-2,strsecond.length());
		//毫秒
		String strmsecond ="00"+String.valueOf(calendar.get(Calendar.MILLISECOND));
		strmsecond=strmsecond.substring(strmsecond.length()-2,strmsecond.length());
		returnvalue =stryear
			+strmonth
			+strday
			+strhour
			+strminute
			+strsecond
			+strmsecond;
		return returnvalue;
	}
	public static void exportDataToExcel(ArrayList caption,
			ArrayList rst,String Title,String condition,String inputDate,HttpServletResponse response){
		//String returnvalue="";
		int intShowColCount= caption.size();
		if (intShowColCount==0||rst ==null){
			//returnvalue="";
		}else{
			OutputStream bos =null;
			try{
				//deleteDir(filepath);
				//String filename=getfilename()+".csv";
				//File file = new File(filepath, filename);
				 response.setContentType("application/msexcel");
		            response.setHeader("Content-disposition","attachment; filename="+getfilename()+".xls");
				bos =response.getOutputStream();//  new FileOutputStream(file);
				writeheader(bos);
				writedata(caption,rst,Title,condition,inputDate,bos,intShowColCount);
				writefooter(bos);
				//returnvalue=filename;
			}catch (Exception ex){
				//returnvalue="";
			}finally{
				try{
					bos.close();
				}catch (Exception ex){}
			}
		}
		return;
	}
	private static void deleteDir(String DirName){
		try{
			GregorianCalendar calendar = new GregorianCalendar();
			//年
			String stryear=String.valueOf(calendar.get(Calendar.YEAR));
			//月
			String strmonth ="00"+String.valueOf(calendar.get(Calendar.MONTH)+1);
			strmonth=strmonth.substring(strmonth.length()-2,strmonth.length());
			//日
			String strday ="00"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			strday=strday.substring(strday.length()-2,strday.length());
			String strdatetoday=stryear+strmonth+strday;
			int intdatetoday= Integer.parseInt(strdatetoday);
		    File file = new File(DirName);
		    if (file.exists()) {
		      File[] fe = file.listFiles();
		      for (int i = 0; i < fe.length; i++) {
		    	 String filename=fe[i].getName();
		    	 if(filename.length()==20){
		    		 String datetemp= filename.substring(0,8);
		    		 String  type=filename.substring(17,20);
		    		 if (type.equals("xls")&&Integer.parseInt(datetemp)<intdatetoday){
		    			 fe[i].delete(); //删除已经是空的子目录
		    		 }
		    	 }
		      }
		    }
		}catch(Exception ex){

		}

	}
	private static String replaceStringXml(String value){
		try{
			value=MethodFactory.replace(value,"&","&amp;");
			value=MethodFactory.replace(value,"<","&lt;");
			value=MethodFactory.replace(value,">","&gt;");
		}catch (Exception ex){
		}
		return value;
	}
	private static void writedata(ArrayList caption,
			ArrayList rst,String Title,String condition,String inputDate,
			OutputStream bos,int colcount) throws Exception{
		try{
			int i=0;
			String strCaption="";
			bos.write(("<Worksheet ss:Name=\""+inputDate+"\">\n").getBytes());
			bos.write("<Table>\n".getBytes());
			//bos.write("<Row ss:Height=\"31.5\">\n".getBytes());
			//bos.write(("<Cell ss:MergeAcross=\""+
			//		String.valueOf(colcount-1)+"\" ss:StyleID=\"s01\">" +
			//		"<Data ss:Type=\"String\">" +
			//		replaceStringXml(Title)+"</Data></Cell>\n").getBytes());
			//bos.write("</Row>\n".getBytes());
			//bos.write("<Row>\n".getBytes());
			//bos.write(("<Cell ss:MergeAcross=\""+
			//		String.valueOf(colcount-1)+"\" ss:StyleID=\"s02\">\n").getBytes());
			//bos.write(("<Data ss:Type=\"String\">" +
			//	replaceStringXml(condition)+"</Data></Cell>\n").getBytes());
			//bos.write("</Row>\n".getBytes());
			bos.write("<Row>\n".getBytes());
			for(i=0;i<caption.size();i++){
				 Object rows = caption.get(i);
				try{
					strCaption=MethodFactory.getThisString(PropertyUtils.getProperty(rows, "caption"));
					strCaption=replaceStringXml(strCaption);
				}catch (Exception ex){
					strCaption="";
				}
				bos.write(("<Cell ss:StyleID=\"s03\"><Data ss:Type=\"String\">" +
						replaceStringXml(strCaption)+"</Data></Cell>\n").getBytes());

			}
			bos.write("</Row>\n".getBytes());

			String strvalue="";
			String strProperty="";

			for(int j =0 ;j<rst.size();j++){
				bos.write("<Row>\n".getBytes());
				for(i=0;i<caption.size();i++){
					Object rows = caption.get(i);
					try{
						strProperty=MethodFactory.getThisString(PropertyUtils.getProperty(rows, "property"));
					}catch (Exception ex){
						strProperty="";
					}
					try{
						System.out.println(strvalue);
						strvalue=MethodFactory.getThisString(PropertyUtils.getProperty(rst.get(j),strProperty.trim().toLowerCase()));
						strvalue=replaceStringXml(strvalue);
					}catch(Exception Ex){
						System.out.println(strProperty+"::"+Ex.toString());
						strvalue="";
					}
					bos.write(("<Cell ss:StyleID=\"s05\"><Data ss:Type=\"String\">" +
							strvalue+"</Data></Cell>\n").getBytes());
				}
				bos.write("</Row>\n".getBytes());
			}
			bos.write("</Table>\n".getBytes());
		}catch (Exception ex){
			throw ex;
		}
	}
	private static void writeheader(OutputStream bos) throws Exception{
		try{
			bos.write("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n".getBytes());
			bos.write("<?mso-application progid=\"Excel.Sheet\"?>\n".getBytes());
			bos.write("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"\n".getBytes());
			bos.write("	xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n".getBytes());
			bos.write("	xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n".getBytes());
			bos.write("	xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"\n".getBytes());
			bos.write("	xmlns:html=\"http://www.w3.org/TR/REC-html40\">\n".getBytes());
			bos.write("<ExcelWorkbook xmlns=\"urn:schemas-microsoft-com:office:excel\">\n".getBytes());
			bos.write("<WindowHeight>9000</WindowHeight>\n".getBytes());
			bos.write("<WindowWidth>14940</WindowWidth>\n".getBytes());
			bos.write("<WindowTopX>240</WindowTopX>\n".getBytes());
			bos.write("<WindowTopY>15</WindowTopY>\n".getBytes());
			bos.write("<ProtectStructure>False</ProtectStructure>\n".getBytes());
			bos.write("<ProtectWindows>False</ProtectWindows>\n".getBytes());
			bos.write("</ExcelWorkbook>\n".getBytes());
			bos.write("<Styles>\n".getBytes());
			bos.write("<Style ss:ID=\"Default\" ss:Name=\"Normal\">\n".getBytes());
			bos.write("<Alignment ss:Vertical=\"Center\"/>\n".getBytes());
			bos.write("<Borders/>\n".getBytes());
			bos.write("<Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"12\"/>\n".getBytes());
			bos.write("<Interior/>\n".getBytes());
			bos.write("<NumberFormat/>\n".getBytes());
			bos.write("<Protection/>\n".getBytes());
			bos.write("</Style>\n".getBytes());
			bos.write("<Style ss:ID=\"s01\">\n".getBytes());
			bos.write("<Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>\n".getBytes());
			bos.write("<Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"24\"/>\n".getBytes());
			bos.write("</Style>\n".getBytes());
			bos.write("<Style ss:ID=\"s02\">\n".getBytes());
			bos.write("<Alignment ss:Horizontal=\"Left\" ss:Vertical=\"Center\"/>\n".getBytes());
			bos.write("</Style>\n".getBytes());
			bos.write("<Style ss:ID=\"s03\">\n".getBytes());
			bos.write("<Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>\n".getBytes());
			bos.write("<Borders>\n".getBytes());
			bos.write("<Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>\n".getBytes());
			bos.write("<Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>\n".getBytes());
			bos.write("<Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>\n".getBytes());
			bos.write("<Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>\n".getBytes());
			bos.write("</Borders>\n".getBytes());
			bos.write("</Style>\n".getBytes());
			bos.write("<Style ss:ID=\"s04\">\n".getBytes());
			bos.write("<Alignment ss:Horizontal=\"Left\" ss:Vertical=\"Center\"/>\n".getBytes());
			bos.write("<Borders>\n".getBytes());
			bos.write("<Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>\n".getBytes());
			bos.write("<Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>\n".getBytes());
			bos.write("<Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>\n".getBytes());
			bos.write("<Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>\n".getBytes());
			bos.write("</Borders>\n".getBytes());
			bos.write("</Style>\n".getBytes());
			bos.write("<Style ss:ID=\"s05\">\n".getBytes());
			bos.write("<Alignment ss:Horizontal=\"Left\" ss:Vertical=\"Center\"/>\n".getBytes());
			bos.write("<Borders>\n".getBytes());
			bos.write("<Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>\n".getBytes());
			bos.write("<Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>\n".getBytes());
			bos.write("<Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>\n".getBytes());
			bos.write("<Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>\n".getBytes());
			bos.write("</Borders>\n".getBytes());
			bos.write("</Style>\n".getBytes());
			bos.write("</Styles>\n".getBytes());
		}catch (Exception ex){
			throw ex;
		}
	}
	private static void writefooter(OutputStream bos) throws Exception{
		try{
			bos.write("<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">\n".getBytes());
			bos.write("<Print>\n".getBytes());
			bos.write("<ValidPrinterInfo/>\n".getBytes());
			bos.write("<PaperSizeIndex>9</PaperSizeIndex>\n".getBytes());
			bos.write("<HorizontalResolution>180</HorizontalResolution>\n".getBytes());
			bos.write("<VerticalResolution>180</VerticalResolution>\n".getBytes());
			bos.write("</Print>\n".getBytes());
			bos.write("<Selected/>\n".getBytes());
			bos.write("<Panes>\n".getBytes());
			bos.write("<Pane>\n".getBytes());
			bos.write("<Number>3</Number>\n".getBytes());
			bos.write("<ActiveRow>2</ActiveRow>\n".getBytes());
			bos.write("<ActiveCol>2</ActiveCol>\n".getBytes());
			bos.write("<RangeSelection>C3</RangeSelection>\n".getBytes());
			bos.write("</Pane>\n".getBytes());
			bos.write("</Panes>\n".getBytes());
			bos.write("<ProtectObjects>False</ProtectObjects>\n".getBytes());
			bos.write("<ProtectScenarios>False</ProtectScenarios>\n".getBytes());
			bos.write("</WorksheetOptions>\n".getBytes());
			bos.write("</Worksheet>\n".getBytes());
			bos.write("</Workbook>\n".getBytes());
		}catch (Exception ex){
			throw ex;
		}
	}
	private static int getShowColCount(ArrayList caption){
		int returnValue=0;
		int i=0;
		String strWidth="";
		String strisexport="";
		if (caption==null){
			returnValue=0;
		}else{
			for(i=0;i<caption.size();i++){
				BasicDynaBean rows =(BasicDynaBean)caption.get(i);
				try{
					strWidth=MethodFactory.getThisString(PropertyUtils.getProperty(rows, "width"));
				}catch (Exception ex){
					strWidth="0";
				}
				try{
					strisexport=MethodFactory.getThisString(PropertyUtils.getProperty(rows, "isexport"));
				}catch (Exception ex){
					strisexport="0";
				}
				if (!(strWidth.trim().equals("0")||strisexport.trim().equals("0"))){
					returnValue=returnValue+1;
				}
			}
		}
		System.out.println(returnValue);
		return returnValue;
	}
	public static void main(String[] args) {
		//deleteDir("E:\\Program Files\\WebSphere\\AppServer\\installedApps\\hyc-computer\\DBMarket_war.ear\\DBMarket.war\\temp\\");
	}
}
