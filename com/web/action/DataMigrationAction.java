package com.web.action;

import static com.zl.util.MethodFactory.print;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

import com.qmx.dbutils.MetaDataUtils;
import com.qmx.dbutils.MyDBUtils;
import com.zl.util.MethodFactory;
import com.zl.util.TradeList;

public class DataMigrationAction extends DispatchAction {
	private static Logger log = Logger.getLogger(DataMigrationAction.class);

	/**
	 * 初始化页面
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		request.setAttribute("fromDB.list", MetaDataUtils.getDBNamesList());
		String dbType = MyDBUtils.getDataSource();
		String dbSchema = MyDBUtils.getSchema(dbType);
		dynaForm.set("fromDataBase", dbType);
		request.setAttribute("tabName.list",
				MetaDataUtils.getTables(dbType, dbSchema));
		return mapping.findForward("init");
	}

	/**
	 * 执行查询操作
	 *
	 * @param request
	 * @param fromDB
	 * @param tabName
	 * @param querySql
	 * @throws Exception
	 */
	private void getResults(HttpServletRequest request, String fromDB,
			String tabName, String querySql) throws Exception {
		List<BasicDynaBean> cs = MetaDataUtils.getColnumsNameListByIsReturn(
				fromDB, tabName);
		List<BasicDynaBean> rs = MyDBUtils.getResultList(fromDB, querySql);

		request.setAttribute("caption.list", cs);
		request.setAttribute("result.list", rs);
	}

	/**
	 * 查询_显示表数据
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String fromDB = dynaForm.get("fromDataBase").toString();
		String querySQL = dynaForm.get("sqlStr").toString();
		String tabName = dynaForm.get("tabName").toString();
		// 清空前一次的结果集
		dynaForm.set("values", "");
		// 开始查询表数据
		getResults(request, fromDB, tabName, querySQL);

		return mapping.findForward("show");
	}

	/**
	 * 保存数据结果集到*.sql文件
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String fromDB = dynaForm.get("fromDataBase").toString();
		String toDB = dynaForm.get("toDataBase").toString();
		String querySql = dynaForm.get("sqlStr").toString();
		String tabName = dynaForm.get("tabName").toString();

		//
		tabName = MethodFactory.getSuffix(tabName);

		String flags = dynaForm.get("flags").toString();
		String autoIncrement = dynaForm.get("autoIncrement").toString();

		boolean autoIncrementFlag = autoIncrement == "1";
		// TODO
		String insertSql = "";
		// insertSql = DataMigrationUtils.generateInsertSQLString(fromDB,
		// toDB, tabName, querySql, autoIncrementFlag);
		print(fromDB + "-->" + toDB + "==>" + tabName);
		print();
		print(querySql);
		String schema = MyDBUtils.getSchema(toDB);

		insertSql = MetaDataUtils.generateInsertSqlStr(fromDB, schema,
				tabName, querySql);

		dynaForm.set("values", insertSql);
		if ("1".equals(flags)) {
			String path = request.getRealPath("/") + "uploads/sqls/" + fromDB
					+ "_" + tabName + "_";
			// 生成*.sql文件
			generateFile(path, insertSql);
		}
		// 返回结果集
		getResults(request, fromDB, tabName, querySql);

		return mapping.findForward("show");
	}

	/**
	 * 生成*.sql文件
	 *
	 * @param path
	 * @param content
	 * @throws Exception
	 */
	private void generateFile(String path, String content) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmm");
		path = path + sdf.format(new Date());
		path = path + ".sql";
		System.out.println("SQL文件生成路径:" + path);
		FileWriter writer = new FileWriter(path);
		writer.write(content);
		writer.flush();
		writer.close();
	}

	/**
	 * 源数据库Change事件_重新加载所属的表信息
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward fromDataBaseChange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dbType = request.getParameter("fromDataBase");
		String dbSchema = MyDBUtils.getSchema(dbType);

		List tabList = MetaDataUtils.getTables(dbType, dbSchema);

		String xmlStr = TradeList.getSelectListByTabName(tabList, 350,
				"tabName", "tabName", "onchange=\"tabNameChange();\"");
		// print(xmlStr);
		response.getWriter().write(xmlStr);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

}
