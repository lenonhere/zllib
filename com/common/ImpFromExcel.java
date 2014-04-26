package com.common;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.upload.FormFile;

import com.web.action.CriterionAction;

/*
 导入电子表格公共方法，
 电子表格模版必须有表头，用以确定电子表格的列数
 导入数据的格式为字符串，可以自定义行列的结束标志
 */
public class ImpFromExcel extends CriterionAction {
	private static Log log = LogFactory.getLog(ImpFromExcel.class);

	public String MakestrFromExcel(FormFile file, String rowsplitstr,
			String colsplitstr, HttpServletRequest request) {
		String path = null;
		String myFileName = null;
		// String filename=form.get("file").toString();
		// FormFile file = (FormFile)form.get("file");//取得上传的文件
		// System.out.println("11111111111111111111111111111111FormFile----111"+file);
		try {
			DiskFileUpload diskFileUpload = new DiskFileUpload();
			// 允许文件最大长度,设置上传文件最大为 100M
			diskFileUpload.setSizeMax(100 * 1024 * 1024);
			// 设置内存缓冲大小
			diskFileUpload.setSizeThreshold(4096);
			// 设置临时目录,该目录是上传数据流超过上面内存定义的大小的流存放在下面的路径上
			diskFileUpload.setRepositoryPath(request.getRealPath("/")
					+ "ExcelInput");
			diskFileUpload.setRepositoryPath(request.getRealPath("/")
					+ "ReportInput");
			java.io.InputStream stream = file.getInputStream();// 把文件读入
			String filePath = request.getRealPath("/");// 取当前系统路径
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputStream bos = new FileOutputStream(filePath + "ExcelInput");// 建立一个上传文件的输出流
			myFileName = file.getFileName();// 上传的文件名
			path = filePath + "ExcelInput";// 可以自己起一个文件名保存到指定的目录，现在用原文件名

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);// 将文件写入服务器
			}
			bos.close();
			stream.close();
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		// ImpFromExcel imp =new ImpFromExcel();
		return readExcelToSTR(path, rowsplitstr, colsplitstr);
	}

	public String readExcelToSTR(String filename, String rowsplitstr,
			String colsplitstr) {
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		String result = "";
		String Trowsplitstr = "";
		String Tcolsplitstr = "";
		if (rowsplitstr.equals(""))
			Trowsplitstr = ";";
		else
			Trowsplitstr = rowsplitstr;
		if (colsplitstr.equals(""))
			Tcolsplitstr = ",";
		else
			Tcolsplitstr = colsplitstr;
		try {
			fs = new POIFSFileSystem(new FileInputStream(filename));
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		String name = "";
		int id = 0;
		int rowNum, cellNum;
		int i, k, j;
		String Thead = "";
		try {
			rowNum = sheet.getLastRowNum();
			// 必须有表头 根据表头判断列数 目前设定40个
			for (k = 0; k < 40; k++) {
				if (sheet.getRow(0).getCell(k) == null)
					break;
				switch (sheet.getRow(0).getCell(k).getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC:
					Thead = String.valueOf(sheet.getRow(0).getCell(k)
							.getNumericCellValue());
					// getNumericCellValue()会回传double值，若不希望出现小数点，请自行转型为int
					break;
				case HSSFCell.CELL_TYPE_STRING:
					Thead = sheet.getRow(0).getCell(k).getStringCellValue();
					break;
				case HSSFCell.CELL_TYPE_FORMULA:
					Thead = String.valueOf(sheet.getRow(0).getCell(k)
							.getNumericCellValue());
					// 读出公式储存格计算後的值
					// 若要读出公式内容，可用cell.getCellFormula()
					break;
				default:
					Thead = "";
					break;
				}
				// Thead=sheet.getRow(0).getCell(k).getStringCellValue()==null?"":sheet.getRow(0).getCell(k).getStringCellValue();
				Thead = Thead.replace(" ", "");
				if (Thead.equals(""))
					break;
			}
			cellNum = k;
			DecimalFormat myFormatter = new DecimalFormat("####.##");

			for (i = 1; i <= rowNum; i++) {
				row = sheet.getRow(i);// cellNum = row.getLastCellNum();
				for (j = 0; j < cellNum; j++) {
					// result=result+sheet.getRow(i).getCell(j).getStringCellValue()+Tcolsplitstr;
					if (sheet.getRow(i).getCell(j) == null)
						result = result + Tcolsplitstr;
					else
						switch (sheet.getRow(i).getCell(j).getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC:
							result = result
									+ myFormatter.format(sheet.getRow(i)
											.getCell(j).getNumericCellValue())
									+ Tcolsplitstr;
							// Thead=String.valueOf(sheet.getRow(0).getCell(k).getNumericCellValue())
							// ;
							// getNumericCellValue()会回传double值，若不希望出现小数点，请自行转型为int
							break;
						case HSSFCell.CELL_TYPE_STRING:
							result = result
									+ sheet.getRow(i).getCell(j)
											.getStringCellValue()
									+ Tcolsplitstr;
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							result = result
									+ myFormatter.format(sheet.getRow(i)
											.getCell(j).getNumericCellValue())
									+ Tcolsplitstr;
							// 读出公式储存格计算後的值
							// 若要读出公式内容，可用cell.getCellFormula()
							break;
						default:
							result = result + Tcolsplitstr;
							break;
						}
				}
				result = result + Trowsplitstr;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
		return result;
	}
}