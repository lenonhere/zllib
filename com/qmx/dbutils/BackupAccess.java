package com.qmx.dbutils;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

public class BackupAccess {

	private static HSSFWorkbook wb = null; // 得到一张数据表的对象
	private static HSSFSheet sheet = null; // 得到一个工作区的对象
	private static HSSFRow row = null; // 得到一行的对象
	private static HSSFCell cell = null; // 得到一列的对象
	private static HSSFCellStyle style = null; // 得到样式的对象
	private static HSSFFont font = null; // 得到字体的对象

	public void test() {

		ExcelInfo("商品信息");

		ResultSet rs = null;
		String[] cellName = { "ID", "名称", "密码", "权限" };
		String titleName = "商品信息列表";

		// boolean flag = downExcel(cellName, titleName, rs, response);
		// System.out.println("flag:" + flag);
	}

	public void test1() {
		// 源路径
		String strDataBaseFilePath = "e:\\ChangeList\\";
		String strSourceFile = "2005-07-28.mdb";
		// dest路径
		String strBackupDataBaseFilePath = "C:\\sennkyo\\";
		String strDestFile = "VetoDB20050630.mdb";
		try {
			Process process = Runtime.getRuntime().exec(
					"cmd.exe     /c     start     del       "
							+ strBackupDataBaseFilePath + strDestFile);
			process.waitFor();
			process = Runtime.getRuntime().exec(
					"cmd.exe     /c     start     copy   "
							+ strDataBaseFilePath + strSourceFile + "       "
							+ strBackupDataBaseFilePath);
			process.waitFor();
		} catch (Exception e) {
			System.out.println(e);
		}
		// rename filename
		java.io.File file = new java.io.File(strBackupDataBaseFilePath
				+ strSourceFile);
		file.renameTo(new java.io.File(strBackupDataBaseFilePath + strDestFile));
	}

	/**
	 * 带参数构造方法
	 *
	 * @param sheetName
	 *            设置工作区对象的名字
	 */
	public static void ExcelInfo(String sheetName) {
		wb = new HSSFWorkbook();
		sheet = wb.createSheet(sheetName);
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
	}

	/**
	 * 此方法用于新建一张数据表
	 *
	 * @param sheetName
	 *            设置工作区对象的名字
	 */
	public void createWorkbook(String sheetName) {
		wb = new HSSFWorkbook();
		sheet = wb.createSheet(sheetName);
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
	}

	/**
	 * 设置表头的标题名和列名
	 *
	 * @param cellName
	 *            列的名字
	 * @param titleName
	 *            标题的名字
	 */
	private void createRow(String[] cellName, String titleName) {
		int maxCell = cellName.length;// 得到列的长度
		System.out.println("maxCell:" + maxCell);
		row = sheet.createRow(0);// o代表第一行
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (maxCell - 1)));
		cell = row.createCell((short) 0);// o代表第一列
		cell.setCellStyle(style);
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(titleName);
		row = sheet.createRow(1); // 创建另一行
		for (int i = 0; i < maxCell; i++) {
			cell = row.createCell((short) i);
			cell.setCellStyle(style);
			// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(cellName[i]);
		}
	}

	/**
	 *
	 * @param cellName
	 *            列的名字
	 * @param titleName
	 *            标题的名字
	 * @param list
	 *            数据
	 * @param response
	 */
	public boolean downExcel(String[] cellName, String titleName, ResultSet rs,
			HttpServletResponse response) {
		boolean flag = false;
		int temp = 2;
		if (wb == null) {
			this.createWorkbook("sheet1.0");
		} else {
			try {

				String fileName = new StringBuffer().append(UUID.randomUUID())
						.append(".xls").toString();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment;fileName=" + response.encodeURL(fileName));

				this.createRow(cellName, titleName);

				while (rs.next()) {
					row = sheet.createRow(temp);
					for (int i = 0; i < cellName.length; i++) {
						cell = row.createCell((short) i);
						cell.setCellStyle(style);
						// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(rs.getString(i + 1));
						System.out.println("rs.getString(i)"
								+ rs.getString(i + 1));
					}
					temp++;
				}
				OutputStream out = response.getOutputStream();
				wb.write(out);
				out.close();
				flag = true;
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
			} finally {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
}
