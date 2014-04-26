package com.qmx.dbutils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Title: BackupMySQL.java
 * @Package com.qmx.dbutils
 * @Description: <p>
 *               MySQL本身的系统命令：
 *
 *               --opt –h localhost --user=root --password=admin
 *               --lock-all-tables=true --result-file=E://oes//2221.sql
 *               --default-character-set=utf8 oes
 *
 *               解析：主机–h，用户名--user，密码—password，锁定所有表--lock-all-tables=true，
 *
 *               目标文件--result-file，编码--default-character-set=utf8，数据源oes
 *               </p>
 * @author qmx
 * @date 2013/04/11 9:25:20
 * @version V1.0
 */
public class BackupMySQL {
	private static String str = null;

	public static String backup(String dbName, String user, String password,
			String toFilePath) {
		String result = null;
		// 获取操作系统名字
		String osName = System.getProperty("os.name");
		// 判断是否是windows操作系统
		boolean isWindows = osName == null ? false : osName
				.startsWith("Windows");
		// 判断是否是Linux操作系统
		boolean isLinux = osName == null ? false : osName.startsWith("Linux");

		String command = null;
		if (isWindows) {
			command = String.format("cmd /c mysqldump -u%s -p%s %s", user,
					password, dbName);
		} else if (isLinux) {
			command = String.format("sh /c mysqldump -u%s -p%s %s", user,
					password, dbName);
		} else {
			result = "不支持的操作系统!";
			return result;
		}
		if (command != null) {
			Runtime rt = Runtime.getRuntime();
			InputStream in = null;
			FileOutputStream fout = null;
			InputStreamReader reader = null;
			BufferedReader br = null;
			OutputStreamWriter writer = null;
			try {
				Process child = rt.exec(command);
				// 把进程中执行的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
				in = child.getInputStream();
				reader = new InputStreamReader(in, "utf-8");// 这是输出流编码为utf-8，否则从流中读入的是乱码
				String inStr = null;
				String outStr = null;
				StringBuilder sb = new StringBuilder();
				br = new BufferedReader(reader);
				while ((inStr = br.readLine()) != null) {
					sb.append(inStr).append("\r\n");
				}
				outStr = sb.toString();

				// 写到文件中去
				fout = new FileOutputStream(toFilePath);
				writer = new OutputStreamWriter(fout, "utf-8");
				writer.write(outStr);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
				result = "发生异常：" + e.getClass().getName();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
						result = "发生异常：" + e.getClass().getName();
					}
				}
				if (fout != null) {
					try {
						fout.close();
					} catch (IOException e) {
						e.printStackTrace();
						result = "发生异常：" + e.getClass().getName();
					}
				}
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
						result = "发生异常：" + e.getClass().getName();
					}
				}
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
						result = "发生异常：" + e.getClass().getName();
					}
				}
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
						result = "发生异常：" + e.getClass().getName();
					}
				}
			}
		}

		return result;
	}

	public static String load(String dbName, String user, String password,
			String source) {
		String result = null;
		// 获取操作系统名字
		String osName = System.getProperty("os.name");
		// 判断是否是windows操作系统
		boolean isWindows = osName == null ? false : osName
				.startsWith("Windows");
		// 判断是否是Linux操作系统
		boolean isLinux = osName == null ? false : osName.startsWith("Linux");

		String command = "";
		String prix = "";
		if (isWindows) {
			prix = "cmd /c ";
			command = String.format("cmd /c mysql.exe -u%s -p%s %s", user,
					password, dbName);
		} else if (isLinux) {
			prix = "sh /c ";
			command = String.format("sh /c mysql.exe -u%s -p%s %s", user,
					password, dbName);
		} else {
			result = "不支持的操作系统!";
			return result;
		}
		if (command != null) {
			OutputStream out = null;
			BufferedReader br = null;
			OutputStreamWriter writer = null;
			try {
				Runtime rt = Runtime.getRuntime();
				rt.exec(String.format(prix
						+ "mysqladmin -u%s -p%s drop %s if exits", user,
						password, dbName));
				rt.exec(String.format(prix + "mysqladmin -u%s -p%s create %s",
						user, password, dbName));
				// 调用 mysql 的 cmd
				Process child = rt.exec(command);
				out = child.getOutputStream();// 控制台的输入信息作为输出流
				String inStr;
				StringBuffer sb = new StringBuffer("");
				String outStr;
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(source), "utf8"));
				while ((inStr = br.readLine()) != null) {
					sb.append(inStr + "\r\n");
				}
				outStr = sb.toString();
				writer = new OutputStreamWriter(out, "utf8");
				writer.write(outStr);
				// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
				result = "发生异常：" + e.getClass().getName();
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
						result = "发生异常：" + e.getClass().getName();
					}
				}
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
						result = "发生异常：" + e.getClass().getName();
					}
				}
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
						result = "发生异常：" + e.getClass().getName();
					}
				}
			}
		}
		return result;
	}

	/**
	 * 备份MySQL数据库
	 */
	private static String expMySQLDB() {
		Calendar now = Calendar.getInstance();
		String name = now.getTime() + "" + (now.getTime().getMonth() + 1) + ""
				+ now.getTime().getDate();
		String filename = name.substring(24) + " " + name.substring(11, 13)
				+ "" + name.substring(14, 16) + "" + name.substring(17, 19);
		try {
			String filePath = "e:/project" + filename + ".sql";
			Runtime rt = Runtime.getRuntime();
			// 调用 mysql 的 cmd:
			Process child = rt
					.exec("C:/Program Files/MySQL/MySQL Server 5.0/bin/mysqldump.exe -uroot -p8095longchun project");// 设置导出编码为utf8。这里必须是utf8
			// 注意这一句，是指运行mysqldump命令，后面跟的是登录名和登录的密码，接着后面的是指备份的数据库的名字，到此结束，以此生成一个执行的进程，取得此进程的输出流到我们要备份的文件
			// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
			InputStream in = child.getInputStream();// 控制台的输出信息作为输入流

			InputStreamReader xx = new InputStreamReader(in, "utf-8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码

			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			// 组合控制台输出信息字符串
			BufferedReader br = new BufferedReader(xx);
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");

			}
			outStr = sb.toString();// 备份出来的内容是一个字条串

			// 要用来做导入用的sql目标文件：
			FileOutputStream fout = new FileOutputStream(filePath);
			OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
			writer.write(outStr);// 写文件
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();

			// 别忘记关闭输入输出流
			in.close();
			xx.close();
			br.close();
			writer.close();
			fout.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 还原MySQL数据库
	 */
	private static String impMySQLDB() {
		try {
			String fPath = "e:/aa.sql";
			Runtime rt = Runtime.getRuntime();
			Process child = rt
					.exec("C:/Program Files/MySQL/MySQL Server 5.0/bin/mysqladmin.exe -uroot -p8095longchun create project");
			Process child1 = rt
					.exec("C:/Program Files/MySQL/MySQL Server 5.0/bin/mysql.exe -uroot -p8095longchun project");
			OutputStream out = child1.getOutputStream();// 控制台的输入信息作为输出流
			String inStr;

			StringBuffer sb = new StringBuffer("");
			String outStr;

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(fPath), "utf-8"));

			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");

			}
			outStr = sb.toString();

			OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
			writer.write(outStr);

			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();
			out.close();
			br.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void backup() {
		// 使用mysqldump来备份数据库，格式"mysqldump -u username -ppassword --opt database_name > direction/backup_name.sql"
		str = "mysqldump -u root -proot --opt   hjh >  d:/test.sql";
		try {
			Runtime rt = Runtime.getRuntime();
			rt.exec("cmd /c" + str);
			// runtime.getruntime().exec( )这个方法可以实现对命令的调用。具体内容看api
			// 上面可以cmd调用控制台，然后执行str中的字符串表示的命令。
			System.out.println("successly!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("something wrong!");

		}

	}

	public static void load() {
		str = "mysql -u root -proot j2603  <  d:/test.sql";
		// mysql命令可以实现数据库的还原。格式"mysql -u username  -ppassword   database_name     <     back_up_dir  "
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec("cmd /c" + str);
			System.out.println("restore successly!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("restore fail!");
		}
	}

	/**
	 * 备份检验一个sql文件是否可以做导入文件用的一个判断方法：把该sql文件分别用记事本和ultra
	 * edit打开，如果看到的中文均正常没有乱码，则可以用来做导入的源文件（不管sql文件的编码格式如何，也不管db的编码格式如何）
	 */
	public static void backup2() {
		try {
			Runtime rt = Runtime.getRuntime();

			// 调用 mysql 的 cmd:
			Process child = rt
					.exec("mysqldump -uroot -proot --set-charset=utf8 demo");// 设置导出编码为utf8。这里必须是utf8

			// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
			InputStream in = child.getInputStream();// 控制台的输出信息作为输入流

			InputStreamReader xx = new InputStreamReader(in, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码

			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			// 组合控制台输出信息字符串
			BufferedReader br = new BufferedReader(xx);
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();

			// 要用来做导入用的sql目标文件：
			FileOutputStream fout = new FileOutputStream("e:/demo.sql");
			OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
			writer.write(outStr);
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();

			// 别忘记关闭输入输出流
			in.close();
			xx.close();
			br.close();
			writer.close();
			fout.close();

			System.out.println("/* Output OK! */");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 导入 导入的时候需要数据库已经建好。
	 */
	public static void load2() {
		try {
			String fPath = "e:/demo.sql";
			Runtime rt = Runtime.getRuntime();

			// 调用 mysql 的 cmd:
			// rt.exec("create database demo");
			Process child = rt.exec("mysql -uroot -proot demo");
			OutputStream out = child.getOutputStream();// 控制台的输入信息作为输出流
			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(fPath), "utf8"));
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();

			OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
			writer.write(outStr);
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();
			// 别忘记关闭输入输出流
			out.close();
			br.close();
			writer.close();

			System.out.println("/* Load OK! */");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * mysqldump -uroot -p1234 -B abc >
	 * %date:~0,4%%date:~5,2%%date:~8,2%_%time:~
	 * 0,2%%time:~3,2%%time:~6,2%.sql.bak
	 *
	 * @param args
	 */
	public static void backup3(String args[]) {
		Runtime runtime = Runtime.getRuntime();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentTime = dateFormat.format(calendar.getTime());
		Process p = null;
		PrintStream print = null;
		StringBuilder buf = new StringBuilder();
		for (String a : args) {
			buf.append(a);
			buf.append(" ");
		}
		String databases = buf.toString();
		System.out.println(databases);
		try {
			p = runtime.exec("cmd /c mysqldump -uroot -p1234 -B " + databases
					+ ">" + currentTime + ".sql.bak");
		} catch (IOException e) {
			if (p != null) {
				p.destroy();
			}
			try {
				print = new PrintStream(currentTime + "_backup_err.log");
				dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
				currentTime = dateFormat.format(calendar.getTime());
				print.println(currentTime + "  backup failed.");
				e.printStackTrace(print);
				print.flush();
			} catch (IOException e2) {

			} finally {
				if (print != null) {
					print.close();
				}
			}
		}
	}

	// TODO Main
	public static void main(String[] args) throws IOException {

		test();

	}

	private static void test() {
		String dbName = "gdoucwc";
		String user = "root";
		String password = "root";
		String toFilePath = "c:/backup.sql";
		String error = backup(dbName, user, password, toFilePath);
		System.out.println(error);
	}
}
