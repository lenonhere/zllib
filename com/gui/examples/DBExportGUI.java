package com.gui.examples;

import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.gui.utils.GuiUtils;
import com.qmx.dbutils.DBInformation;
import com.qmx.dbutils.MetaDataUtils;
import com.qmx.dbutils.MyDBUtils;
import com.qmx.sysutils.SysConstants;

public class DBExportGUI {
	protected Shell shlsql;
	private Text sqlFileText;
	private Combo dbTypCombo;
	private String dbTypes = SysConstants.DB_TYPES;

	private Text userText;
	private Text passText;
	private Button backBtn;
	private Combo schemaCombo;
	private Button connectionbutton;
	private org.eclipse.swt.widgets.List dbTablesList;
	private Button btnGbk;
	private Button btnUtf8;
	private Button button;
	private CLabel label_7;
	private Button btnSql;
	private Button btnCsv;
	private Button btnExcel;
	private Button storeDbButton;
	private Combo dbUrlCombo;

	public static void main(String[] args) {
		try {
			DBExportGUI window = new DBExportGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents();
		this.shlsql.open();
		this.shlsql.layout();
		while (!this.shlsql.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
	}

	protected void createContents() {
		this.shlsql = new Shell();
		// this.shlsql.setImage(SWTResourceManager.getImage(MainInterface.class,
		// "/com/gui/imgs/sql_cn_48.ico"));
		this.shlsql.setSize(600, 400);
		this.shlsql.setText("批量获取数据库表数据");
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		this.shlsql.setLocation(
				(screenWidth - this.shlsql.getBounds().width) / 2,
				(screenHeight - this.shlsql.getBounds().height) / 2);

		this.dbTypCombo = new Combo(this.shlsql, 0);
		this.dbTypCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				String dbType = DBExportGUI.this.dbTypCombo.getText();

				List<String> urlList = MyDBUtils.readDBInfo(dbType);
				String[] urlArray = new String[urlList.size()];
				urlList.toArray(urlArray);
				if (urlArray.length > 0) {
					DBExportGUI.this.dbUrlCombo.setItems(urlArray);
				} else if (dbType.equals(SysConstants.DB_MYSQL)) {
					DBExportGUI.this.dbUrlCombo
							.setText(SysConstants.DB_MYSQL_URL);
				} else if (dbType.equals(SysConstants.DB_MSSQL_2000)) {
					DBExportGUI.this.dbUrlCombo
							.setText(SysConstants.DB_MSSQL_2000_URL);
				} else if (dbType.equals(SysConstants.DB_MSSQL_2005)) {
					DBExportGUI.this.dbUrlCombo
							.setText(SysConstants.DB_MSSQL_2005_URL);
				} else if (dbType.equals(SysConstants.DB_ORACLE_10G)) {
					DBExportGUI.this.dbUrlCombo
							.setText(SysConstants.DB_ORACLE_10G_URL);
				} else if (dbType.equals(SysConstants.DB_ORACLE_11G)) {
					DBExportGUI.this.dbUrlCombo
							.setText(SysConstants.DB_ORACLE_11G_URL);
				} else if (dbType.equals(SysConstants.DB_DB2)) {
					DBExportGUI.this.dbUrlCombo
							.setText(SysConstants.DB_DB2_TYPE4_URL);
				}

				DBExportGUI.this.dbUrlCombo.setEnabled(true);
				DBExportGUI.this.connectionbutton.setEnabled(true);
				DBExportGUI.this.userText.setText("");
				DBExportGUI.this.passText.setText("");
				String[] tmp = new String[0];
				DBExportGUI.this.schemaCombo.setItems(tmp);
				DBExportGUI.this.dbTablesList.setItems(tmp);
			}
		});
		//
		this.dbTypCombo.setItems(dbTypes.split(","));
		this.dbTypCombo.setBounds(104, 9, 106, 21);
		this.dbTypCombo.setText("选择数据库类型");

		this.dbUrlCombo = new Combo(this.shlsql, 0);
		this.dbUrlCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String url = DBExportGUI.this.dbUrlCombo.getText();

				DBInformation dbinfo = MyDBUtils
						.getDBInfoByKey(DBExportGUI.this.dbTypCombo.getText()
								+ url);
				if (dbinfo != null) {
					DBExportGUI.this.userText.setText(dbinfo.getUsername());
					DBExportGUI.this.passText.setText(dbinfo.getPassword());
				}
			}
		});
		this.dbUrlCombo.setBounds(300, 9, 271, 21);

		this.userText = new Text(this.shlsql, 2048);
		this.userText.setBounds(104, 55, 106, 19);

		this.passText = new Text(this.shlsql, 2048);
		this.passText.setEchoChar('*');
		this.passText.setBounds(300, 55, 105, 19);

		CLabel label = new CLabel(this.shlsql, 0);
		label.setBounds(10, 10, 78, 19);
		label.setText("数据库类型：");

		CLabel label_1 = new CLabel(this.shlsql, 0);
		label_1.setBounds(216, 10, 78, 19);
		label_1.setText("数据库信息：");

		CLabel label_2 = new CLabel(this.shlsql, 0);
		label_2.setBounds(10, 131, 78, 19);
		label_2.setText("数据表列表：");

		this.storeDbButton = new Button(this.shlsql, 32);
		this.storeDbButton.setBounds(416, 56, 78, 16);
		this.storeDbButton.setText("记住连接");

		this.connectionbutton = new Button(this.shlsql, 0);
		this.connectionbutton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (DBExportGUI.this.userText.getText().equals("")) {
					MessageBox msgBox = new MessageBox(DBExportGUI.this.shlsql);
					msgBox.setText("用户名未输入");
					msgBox.setMessage("请输入用户名！");
					msgBox.open();
					return;
				}
				if (DBExportGUI.this.passText.getText().equals("")) {
					MessageBox msgBox = new MessageBox(DBExportGUI.this.shlsql);
					msgBox.setText("密码未输入");
					msgBox.setMessage("请输入密码！");
					msgBox.open();
					return;
				}
				DBExportGUI.this.dbUrlCombo.setEnabled(false);
				DBExportGUI.this.connectionbutton.setEnabled(false);

				if (DBExportGUI.this.storeDbButton.getSelection()) {

					String dbDriver = "";
					String dbType = DBExportGUI.this.dbTypCombo.getText();
					if (dbType.equals(SysConstants.DB_MYSQL)) {
						dbDriver = SysConstants.DB_MYSQL_DRIVER;
					} else if (dbType.equals(SysConstants.DB_MSSQL_2000)) {
						dbDriver = SysConstants.DB_MSSQL_2000_DRIVER;
					} else if (dbType.equals(SysConstants.DB_MSSQL_2005)) {
						dbDriver = SysConstants.DB_MSSQL_2005_DRIVER;
					} else if (dbType.equals(SysConstants.DB_ORACLE_10G)
							|| dbType.equals(SysConstants.DB_ORACLE_11G)) {
						dbDriver = SysConstants.DB_ORACLE_DRIVER;
					} else if (dbType.equals(SysConstants.DB_DB2)) {
						dbDriver = SysConstants.DB_DB2_TYPE4_DRIVER;
					}
					String dbUrl = DBExportGUI.this.dbUrlCombo.getText();
					String dbUser = DBExportGUI.this.userText.getText();
					String dbPwd = DBExportGUI.this.passText.getText();
					DBInformation dbinfo = new DBInformation(dbType, dbDriver,
							dbUrl, dbUser, dbPwd);
					MyDBUtils.storeDBInfo(dbinfo);
				}
				try {
					List<String> schemasList = MetaDataUtils
							.getSchemaList(DBExportGUI.this.dbTypCombo
									.getText());
					String[] schemas = new String[schemasList.size()];
					schemasList.toArray(schemas);
					DBExportGUI.this.schemaCombo.setItems(schemas);
					DBExportGUI.this.schemaCombo.select(0);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		this.connectionbutton.setBounds(503, 53, 68, 23);
		this.connectionbutton.setText("连  接");

		this.schemaCombo = new Combo(this.shlsql, 0);
		this.schemaCombo.setBounds(104, 97, 380, 21);

		CLabel label_3 = new CLabel(this.shlsql, 0);
		label_3.setBounds(10, 98, 78, 19);
		label_3.setText("数据库模式：");

		Button getTablesButton = new Button(this.shlsql, 0);
		getTablesButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (DBExportGUI.this.connectionbutton.isEnabled()) {
					MessageBox msgBox = new MessageBox(DBExportGUI.this.shlsql);
					msgBox.setText("请先获取连接");
					msgBox.setMessage("请先获取连接！");
					msgBox.open();
					return;
				}

				try {
					List<String> tablesList = MetaDataUtils.getTableList(
							DBExportGUI.this.schemaCombo.getText(),
							DBExportGUI.this.dbTypCombo.getText());
					String[] tables = new String[tablesList.size()];
					tablesList.toArray(tables);
					DBExportGUI.this.dbTablesList.setItems(tables);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		getTablesButton.setBounds(503, 96, 68, 23);
		getTablesButton.setText("获取表列表");

		this.dbTablesList = new org.eclipse.swt.widgets.List(this.shlsql, 2562);
		this.dbTablesList.setBounds(104, 131, 467, 106);

		CLabel lblSql = new CLabel(this.shlsql, 0);
		lblSql.setBounds(10, 248, 78, 19);
		lblSql.setText("存放目录：");

		this.sqlFileText = new Text(this.shlsql, 2048);
		this.sqlFileText.setBounds(104, 248, 380, 19);

		Button dirButton = new Button(this.shlsql, 0);
		dirButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DBExportGUI.this.sqlFileText.setText(GuiUtils.getDirectoryPath(
						DBExportGUI.this.shlsql,
						DBExportGUI.this.sqlFileText.getText()));
			}
		});
		dirButton.setBounds(503, 246, 68, 23);
		dirButton.setText("浏览...");

		CLabel label_4 = new CLabel(this.shlsql, 0);
		label_4.setBounds(10, 284, 78, 19);
		label_4.setText("文件编码：");

		this.btnGbk = new Button(this.shlsql, 32);
		this.btnGbk.setBounds(104, 285, 48, 16);
		this.btnGbk.setText("GBK");

		this.btnUtf8 = new Button(this.shlsql, 32);
		this.btnUtf8.setBounds(152, 285, 54, 16);
		this.btnUtf8.setText("UTF-8");
		this.btnUtf8.setSelection(true);

		CLabel label_5 = new CLabel(this.shlsql, 0);
		label_5.setBounds(10, 55, 78, 19);
		label_5.setText("用    户    名：");

		CLabel label_6 = new CLabel(this.shlsql, 0);
		label_6.setBounds(216, 55, 78, 19);
		label_6.setText("密            码：");

		this.label_7 = new CLabel(this.shlsql, 0);
		this.label_7.setBounds(214, 284, 68, 19);
		this.label_7.setText("文件编码：");

		this.btnSql = new Button(this.shlsql, 32);
		this.btnSql.setBounds(298, 285, 48, 16);
		this.btnSql.setText("SQL");
		this.btnSql.setSelection(true);

		this.btnCsv = new Button(this.shlsql, 32);
		this.btnCsv.setBounds(352, 285, 40, 16);
		this.btnCsv.setText("CSV");

		this.btnExcel = new Button(this.shlsql, 32);
		this.btnExcel.setBounds(399, 285, 85, 16);
		this.btnExcel.setText("Excel");
		this.btnExcel.setVisible(false);

		Button confirmButton = new Button(this.shlsql, 0);
		confirmButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (DBExportGUI.this.sqlFileText.getText().equals("")) {
					MessageBox msgBox = new MessageBox(DBExportGUI.this.shlsql);
					msgBox.setText("请选择sql文件存放目录");
					msgBox.setMessage("请选择sql文件存放目录！");
					msgBox.open();
					return;
				}

				List<String> encodTypes = new ArrayList<String>();
				if (DBExportGUI.this.btnGbk.getSelection()) {
					encodTypes.add("GBK");
				}
				if (DBExportGUI.this.btnUtf8.getSelection()) {
					encodTypes.add("UTF-8");
				}

				List<String> exportFileTypes = new ArrayList<String>();
				if (DBExportGUI.this.btnCsv.getSelection()) {
					exportFileTypes.add("CSV");
				}
				if (DBExportGUI.this.btnSql.getSelection()) {
					exportFileTypes.add("SQL");
				}
				if (DBExportGUI.this.btnExcel.getSelection()) {
					exportFileTypes.add("EXCEL");
				}

				boolean flag = false;
				flag = MetaDataUtils.createFiles(
						DBExportGUI.this.dbTypCombo.getText(),
						DBExportGUI.this.sqlFileText.getText(),
						DBExportGUI.this.dbTablesList.getSelection(),
						encodTypes, DBExportGUI.this.schemaCombo.getText(),
						exportFileTypes);
				MessageBox msgBox = new MessageBox(DBExportGUI.this.shlsql);
				if (flag) {
					msgBox.setText("文件生成成功");
					msgBox.setMessage("文件生成成功，目录为:"
							+ DBExportGUI.this.sqlFileText.getText());
				} else {
					msgBox.setText("文件生成失败");
					msgBox.setMessage("文件生成失败，请重试！");
				}
				msgBox.open();
			}
		});
		confirmButton.setBounds(420, 316, 68, 23);
		confirmButton.setText("确定");

		this.button = new Button(this.shlsql, 0);
		this.button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DBExportGUI.this.shlsql.close();
			}
		});

		button.setBounds(500, 316, 68, 23);
		this.button.setText("取消");
		{
			backBtn = new Button(this.shlsql, 0);
			backBtn.setText("备份");
			backBtn.setBounds(340, 316, 68, 23);
			backBtn.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					System.out.println("backBtn.widgetSelected, event=" + evt);
				}
			});
		}
	}
}
