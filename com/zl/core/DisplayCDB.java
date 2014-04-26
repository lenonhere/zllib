package com.zl.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qmx.dbutils.MyDBUtils;

public class DisplayCDB extends HttpServlet {

	private static final long serialVersionUID = 1084867890237600404L;

	/**
	 * Default constructor.
	 */
	public DisplayCDB() {
		super();
	}

	/**
	 * Init method.
	 *
	 * @throws ServletException
	 *             never.
	 */
	public void init() throws ServletException {
		return;
	}

	private static byte[] getbyte(byte[] sbyte, int b, int length) {
		byte[] returnvalue = new byte[length];
		for (int i = b; i < b + length; i++) {
			returnvalue[i - b] = sbyte[i];
		}
		return returnvalue;
	}

	private static String getString(byte[] sbyte, int b, int length) {
		byte[] bytetemp = getbyte(sbyte, b, length);
		return toGb2312(bytetemp);
	}

	private static String toGb2312(byte b[]) {
		String retStr = "";
		try {
			retStr = new String(b, "GB2312");
			for (int i = 0; i < b.length; i++) {
				byte b1 = b[i];
				if (b1 == 0) {
					retStr = new String(b, 0, i, "GB2312");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retStr;
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service(request, response, true);
	}

	public void service(HttpServletRequest request,
			HttpServletResponse response, boolean blnsyn)
			throws ServletException, IOException {
		Connection conn = null;
		String type = "";
		try {
			String attacheid = request.getParameter("attacheid");
			String filename = "";
			// if (filename == null) {
			// throw new ServletException("没有发现对应的文件名称！");
			// }
			// Connection conn = null;
			// conn = DBConnectionFactory.openConnection();
			// xianet 2005-11-29
			conn = MyDBUtils.getMyDB().getConn();
			PreparedStatement preparedStatement = conn
					.prepareStatement("SELECT AttacheContent, AttacheType,AttacheTile FROM C_CONTRACTATTACHE WHERE attacheid=?");
			preparedStatement.setString(1, attacheid);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Blob blob = resultSet.getBlob(1);
				type = resultSet.getString(2);
				InputStream inputStream = blob.getBinaryStream();
				filename = resultSet.getString(3);
				sendFile(inputStream, response, filename, type, "view");
				break;
			}
		} catch (Exception ex) {
			throw new ServletException(ex.toString());
		} finally {
			// DBConnectionFactory.closeConnection(conn);
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}

		return;
	}

	/*
	 * 原来黄彦成的写法 public void service(HttpServletRequest request,
	 * HttpServletResponse response,boolean blnsyn) throws ServletException,
	 * IOException { Connection conn = null; //HttpSession session =
	 * request.getSession(); String type =request.getParameter("type"); if
	 * (("excel").equals(type)){ String filename =
	 * request.getParameter("filename");
	 * downexcelFile(request,response,filename); }else if
	 * (("hwm").equals(type)){ try{ String SID = request.getParameter("SID");
	 * //输入为记录的ID String PicType = request.getParameter("PicType"); //输入为记录的ID
	 * //conn = DBConnectionFactory.openConnection(); //xianet 2005-11-29 conn =
	 * newDealFile.getConn(); //PreparedStatement preparedStatement
	 * =conn.prepareStatement
	 * ("SELECT goodspicdata FROM g_goodsser WHERE goodssid=?"); //xianet
	 * 2005-11-29 PreparedStatement preparedStatement =conn.prepareStatement(
	 * "SELECT AttacheContent FROM C_CONTRACTATTACHE WHERE AttacheID=?");
	 * preparedStatement.setString(1, SID); ResultSet resultSet =
	 * preparedStatement.executeQuery(); while (resultSet.next()) { Blob blob =
	 * resultSet.getBlob(1); InputStream inputStream = blob.getBinaryStream();
	 * sendFile(inputStream,response,"hwn_filename",PicType,"hwm_view"); break;
	 * } }catch(Exception ex){ throw new ServletException(ex.getMessage());
	 * }finally{ //DBConnectionFactory.closeConnection(conn); try{ conn.close();
	 * }catch(Exception e){ System.out.println(e.toString()); } } }else{ try{
	 * String filename = request.getParameter("filesuuid");
	 * if(filename==null){throw new ServletException("没有发现对应的文件名称！");}
	 * //Connection conn = null; //conn = DBConnectionFactory.openConnection();
	 * //xianet 2005-11-29 conn = newDealFile.getConn(); PreparedStatement
	 * preparedStatement =conn.prepareStatement(
	 * "SELECT FILECONTENT,FILEID FROM G_STATICMESSAGE WHERE SUUID=?");
	 * preparedStatement.setString(1, filename); ResultSet resultSet =
	 * preparedStatement.executeQuery(); while (resultSet.next()) { Blob blob =
	 * resultSet.getBlob(1); type=resultSet.getString(2); InputStream
	 * inputStream = blob.getBinaryStream();
	 * sendFile(inputStream,response,filename,type,"view"); break; }
	 * }catch(Exception ex){ throw new ServletException(ex.toString());
	 * }finally{ //DBConnectionFactory.closeConnection(conn); try{ conn.close();
	 * }catch(Exception e){ System.out.println(e.toString()); } } } return; }
	 */
	public static void downexcelFile(HttpServletRequest request,
			HttpServletResponse response, String filename) throws IOException {
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition", "attachment; filename="
				+ filename);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(request
					.getSession().getServletContext()
					.getRealPath("//temp//" + filename)));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while ((bytesRead = bis.read(buff, 0, buff.length)) != -1) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
		} catch (Exception e) {
			System.out.println("IOException." + e);
		} finally {
			if (bos != null)
				bos.close();
			if (bis != null)
				bis.close();
		}
		return;
	}

	public static void sendFile(InputStream bis, HttpServletResponse response,
			String filename, String mimeType, String OperaType)
			throws IOException {
		response.setContentType(mimeType);
		// Logger.error(mimeType);
		if (mimeType.indexOf("application") >= 0 || mimeType == null) {
			response.addHeader("Content-Disposition", "attachment; filename="
					+ filename);
		}
		// Logger.error("5");
		BufferedOutputStream bos = new BufferedOutputStream(
				response.getOutputStream());
		// Logger.error("6");
		byte[] input = new byte[1024];
		boolean eof = false;
		while (!eof) {
			int length = bis.read(input);
			if (length == -1) {
				eof = true;
			} else {
				bos.write(input, 0, length);
			}
		}
		// Logger.error("7");
		bos.flush();
		bis.close();
		bos.close();
		return;
	}
}