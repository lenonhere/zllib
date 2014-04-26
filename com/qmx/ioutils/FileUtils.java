package com.qmx.ioutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import com.qmx.sysutils.SysConstants;

/**
 * @author NTSD
 *
 */
/**
 * @author NTSD
 *
 */
public class FileUtils {

	private String message = "";
	private static List<String> list = null;

	public static List<String> readFile(File file) throws Exception {
		list = new ArrayList<String>();
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), SysConstants.ENCODE_UTF8);
		BufferedReader reader = new BufferedReader(read);
		String line = "";
		while ((line = reader.readLine()) != null) {
			list.add(line);
		}
		reader.close();
		read.close();
		return list;
	}

	public static StringBuilder readFileRtnString(File file, String encodeType)
			throws Exception {
		StringBuilder strB = new StringBuilder();
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), encodeType);
		BufferedReader reader = new BufferedReader(read);
		String line = "";
		while ((line = reader.readLine()) != null) {
			strB.append(line + "\n");
		}
		reader.close();
		read.close();
		return strB;
	}

	public static String[] getLineArrayFromFile(String filePath)
			throws Exception {
		return getLineArrayFromFile(new File(filePath));
	}

	public static String[] getLineArrayFromFile(File file) throws Exception {
		list = readFile(file);
		String[] lines = (String[]) list.toArray(new String[list.size()]);
		return lines;
	}

	public static void writeFile(String filePath, String content, String encode)
			throws IOException {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}
		FileOutputStream outer = new FileOutputStream(file);
		OutputStreamWriter outWriter = new OutputStreamWriter(outer, encode);
		outWriter.write(content);
		outWriter.flush();
		outWriter.close();
		outer.close();
	}

	public static void mkDirByPath(String folderPath) {
		File file = new File(folderPath);
		file.mkdirs();
	}
	public static boolean checkdirexits(String s)
	  {
	    boolean flag = false;
	    File file = new File(s);
	    if (file.isDirectory())
	      flag = true;
	    else
	      flag = false;
	    return flag;
	  }

	  public static boolean createdir(String s) {
	    boolean flag = false;
	    try {
	      File file = new File(s);
	      file.mkdirs();
	      if (file.isDirectory())
	        flag = true;
	      else
	        flag = false;
	    } catch (Exception exception) {
	      flag = true;
	    }
	    return flag;
	  }

	/**
	 * 读取文本文件内容
	 *
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param encoding
	 *            文本文件打开的编码方式
	 * @return 返回文本文件的内容
	 */
	public String readTxt(String filePathAndName, String encoding)
			throws IOException {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;
			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data + " ");
				}
			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
		} catch (IOException es) {
			st = "";
		}
		return st;
	}

	/**
	 * 新建目录
	 *
	 * @param folderPath
	 *            目录
	 * @return 返回目录创建后的路径
	 */
	public String createFolder(String folderPath) {
		String txt = folderPath;
		try {
			java.io.File myFilePath = new java.io.File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			message = "创建目录操作出错";
		}
		return txt;
	}

	/**
	 * 多级目录创建
	 *
	 * @param folderPath
	 *            准备要在本级目录下创建新目录的目录路径 例如 c:myf
	 * @param paths
	 *            无限级目录参数，各级目录以单数线区分 例如 a|b|c
	 * @return 返回创建文件后的路径 例如:
	 */
	public String createFolders(String folderPath, String paths) {
		String txts = folderPath;
		try {
			String txt;
			txts = folderPath;
			StringTokenizer st = new StringTokenizer(paths, "|");
			for (int i = 0; st.hasMoreTokens(); i++) {
				txt = st.nextToken().trim();
				if (txts.lastIndexOf("/") != -1) {
					txts = createFolder(txts + txt);
				} else {
					txts = createFolder(txts + txt + "/");
				}
			}
		} catch (Exception e) {
			message = "创建目录操作出错！";
		}
		return txts;
	}

	/**
	 * 新建文件
	 *
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @return
	 */
	public void createFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
			resultFile.close();
		} catch (Exception e) {
			message = "创建文件操作出错";
		}
	}

	/**
	 * 有编码方式的文件创建
	 *
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @param encoding
	 *            编码方式 例如 GBK 或者 UTF-8
	 * @return
	 */
	public void createFile(String filePathAndName, String fileContent,
			String encoding) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			PrintWriter myFile = new PrintWriter(myFilePath, encoding);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
		} catch (Exception e) {
			message = "创建文件操作出错";
		}
	}

	/**
	 * 删除文件
	 *
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @return Boolean 成功删除返回true遭遇异常返回false
	 */
	public boolean delFile(String filePathAndName) {
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				myDelFile.delete();
				bea = true;
			} else {
				bea = false;
				message = (filePathAndName + "删除文件操作出错");
			}
		} catch (Exception e) {
			message = e.toString();
		}
		return bea;
	}

	/**
	 * 删除文件夹
	 *
	 * @param folderPath
	 *            文件夹完整绝对路径
	 * @return
	 */
	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			message = ("删除文件夹操作出错");
		}
	}

	/**
	 * 删除指定文件夹下所有文件
	 *
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 * @return
	 */
	public boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * 复制单个文件
	 *
	 * @param oldPathFile
	 *            准备复制的文件源
	 * @param newPathFile
	 *            拷贝到新绝对路径带文件名
	 * @return
	 */
	public void copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			message = ("复制单个文件操作出错");
		}
	}

	/**
	 * 复制整个文件夹的内容
	 *
	 * @param oldPath
	 *            准备拷贝的目录
	 * @param newPath
	 *            指定绝对路径的新目录
	 * @return
	 */
	public void copyFolder(String oldPath, String newPath) {
		try {
			new File(newPath).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			message = "复制整个文件夹内容操作出错";
		}
	}

	/**
	 * 移动文件
	 *
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动目录
	 *
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	public String getMessage() {
		return this.message;
	}

	/**
	 * @param input
	 * @param to
	 * @return
	 * @throws IOException
	 */
	public static String copyFile2(String input, String to) throws IOException {
		File fromFile;
		File toFile;

		int lineIndex = input.lastIndexOf("/");
		int len = input.length();
		String output = to + "/" + input.substring(lineIndex + 1, len);

		fromFile = new File(input);
		System.out.println("file name is " + fromFile.getName());

		toFile = new File(output);

		if (toFile.exists()) {
			return "fileexist";
		}

		String dirStr = toFile.getAbsolutePath();
		File dirFile;
		dirStr = dirStr.substring(0, dirStr.lastIndexOf("\\"));
		dirFile = new File(dirStr);
		if (dirFile.exists() && dirFile.isDirectory())
			System.out.println("dir exist!");
		else {
			if (!dirFile.mkdirs())
				return "error";
		}

		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(fromFile);
			fos = new FileOutputStream(toFile);
			int bytesRead;
			byte[] buf = new byte[4 * 1024]; // 4K buffer
			while ((bytesRead = fis.read(buf)) != -1) {
				fos.write(buf, 0, bytesRead);
			}
			fos.flush();
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		} finally {
			if (fos != null)
				fos.close();
			if (fis != null)
				fis.close();
		}
		toFile.setLastModified(fromFile.lastModified());
		return "ok";
	}

	public static String getRandomShippingFile(String databaseId) {

		String nowYear = Calendar.getInstance().get(Calendar.YEAR) + "";
		String nowMonth = String
				.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 101)
				.trim().substring(1, 3);
		String nowDay = String
				.valueOf(
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 100)
				.trim().substring(1, 3);
		return "0" + nowYear + nowMonth + nowDay
				+ String.valueOf(databaseId + 1) + ".sds";
	}

	public static String getRandomMessageFile(String databaseId) {
		String nowYear = Calendar.getInstance().get(Calendar.YEAR) + "";
		String nowMonth = String
				.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 100)
				.trim().substring(1, 3);
		String nowDay = String
				.valueOf(
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 100)
				.trim().substring(1, 3);
		return "SMG0" + nowYear + nowMonth + nowDay
				+ String.valueOf(databaseId + 1) + ".txt";
	}

	/**
	 * 文件COPY
	 *
	 * @author: 朱忠南
	 * @param in
	 * @param out
	 * @throws Exception
	 */
	public static void copyFile(File in, File out) throws Exception {
		FileInputStream fis = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);
		byte[] buf = new byte[1024];
		int i = 0;
		while ((i = fis.read(buf)) != -1) {
			fos.write(buf, 0, i);
		}
		fis.close();
		fos.close();
	}

	/**
	 * 删除文件或文件夹
	 *
	 * @author: 朱忠南
	 * @param file
	 * @throws Exception
	 */
	public static void deleteFile(File file) throws Exception {
		if (!file.exists())
			return;
		if (file.isDirectory()) {
			File f[] = file.listFiles();
			for (int i = 0; i < f.length; i++) {
				deleteFile(f[i]);
			}
			file.delete();
		} else
			file.delete();
	}
}
