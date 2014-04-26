package com.ws.transport.util;

import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;

public class TransportTimerTask extends TimerTask {

	private static final Log logger = LogFactory.getLog(TransportTimerTask.class);

	private static final Set insteadOfDefaultsKeys;

	static {
		insteadOfDefaultsKeys = new HashSet();
		insteadOfDefaultsKeys.add("db-url");
		insteadOfDefaultsKeys.add("db-user");
		insteadOfDefaultsKeys.add("db-password");
		insteadOfDefaultsKeys.add("jdbc-driver");
		insteadOfDefaultsKeys.add("webservice-url");
		insteadOfDefaultsKeys.add("start");
		insteadOfDefaultsKeys.add("cycle");
	}

	private String name;
	/*
	private String db_url;
	private String user;
	private String password;
	private String jdbc_driver;
	private String webservice_url;
	private Date start;
	private long cycle;
	 */

	private Map insteadOfDefaultsMap = new HashMap(12);

    private RowFilter rowFilter = DefaultRowFilter.INSTANCE;
	private String method;
	private Object[] params;

	private String query_content;
	private LinkedHashMap query_jdbcTypeMap;
	private Map query_digitsMap;


	private String insert_content;
	private LinkedHashMap insert_jdbcTypeMap;
	private Map insert_digitsMap;


	private static Logger log = Logger.getLogger(TransportTimerTask.class);


	public static Set getInsteadOfDefaultsKeys() {
		return insteadOfDefaultsKeys;
	}




	public void run() {
		try {
    		String data = importDataFromWebService();
    		saveData(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void runTest(String beginDate, String endDate)
			throws Exception {
		Service service = new Service();
		Call call = (Call)service.createCall();
		call.setTargetEndpointAddress(insteadOfDefaultsMap
				.get("webservice-url").toString());
		call.setOperation(method);
		Object[] params_copy = (Object[])params.clone();
		if (params_copy.length > 2) {
    		params_copy[2] = beginDate;
    		params_copy[3] = endDate;
		}
		String data = call.invoke(params_copy).toString();
		logger.debug(data);

		saveData(data);
		logger.debug("data saved");
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}



	public Map getInsteadOfDefaultsMap() {
		return insteadOfDefaultsMap;
	}
	public void setInsteadOfDefaultsMap(Map insteadOfDefaultsMap) {
		this.insteadOfDefaultsMap = insteadOfDefaultsMap;
	}


    public RowFilter getRowFilter() {
        return  rowFilter;
    }
    public void setRowFilter(RowFilter filter) {
        this.rowFilter = filter;
    }



	/*
	public String getDb_url() {
		return db_url;
	}
	public void setDb_url(String db_url) {
		this.db_url = db_url;
	}



	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}



	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}



	public String getJdbc_driver() {
		return jdbc_driver;
	}
	public void setJdbc_driver(String jdbc_driver) {
		this.jdbc_driver = jdbc_driver;
	}



	public String getWebservice_url() {
		return webservice_url;
	}
	public void setWebservice_url(String webservice_url) {
		this.webservice_url = webservice_url;
	}



	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}



	public long getCycle() {
		return cycle;
	}
	public void setCycle(long cycle) {
		this.cycle = cycle;
	}
     */


	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}



	public Object[] getParams() {
		return params;
	}
	public void setParams(Object[] params) {
		this.params = params;
	}



	public String getQuery_content() {
		return query_content;
	}
	public void setQuery_content(String query_content) {
		this.query_content = query_content;
	}



	public LinkedHashMap getQuery_jdbcTypeMap() {
		return query_jdbcTypeMap;
	}
	public void setQuery_jdbcTypeMap(LinkedHashMap query_jdbcTypeMap) {
		this.query_jdbcTypeMap = query_jdbcTypeMap;
	}



	public Map getQuery_digitsMap() {
		return query_digitsMap;
	}
	public void setQuery_digitsMap(Map query_digitsMap) {
		this.query_digitsMap = query_digitsMap;
	}



	public String getInsert_content() {
		return insert_content;
	}
	public void setInsert_content(String insert_content) {
		this.insert_content = insert_content;
	}



	public LinkedHashMap getInsert_jdbcTypeMap() {
		return insert_jdbcTypeMap;
	}
	public void setInsert_jdbcTypeMap(LinkedHashMap insert_jdbcTypeMap) {
		this.insert_jdbcTypeMap = insert_jdbcTypeMap;
	}



	public Map getInsert_digitsMap() {
		return insert_digitsMap;
	}
	public void setInsert_digitsMap(Map insert_digitsMap) {
		this.insert_digitsMap = insert_digitsMap;
	}



	private void bindParamForPstmt(Map rowMap, Map jdbcTypeMap, Map digitsMap,
			PreparedStatement pstmt)  throws SQLException {
		Iterator iterator = jdbcTypeMap.entrySet().iterator();
		int paramIndex = 1;
		while (iterator.hasNext()) {
			Entry entryTmp = (Entry)iterator.next();
			Object key = entryTmp.getKey();
			int jdbcType = Integer.parseInt(entryTmp.getValue().toString());
			Object digits_obj = digitsMap.get(key);
			if (digits_obj != null && digits_obj.toString().length() > 0) {
				int digits_int = Integer.parseInt(digits_obj.toString());
				pstmt.setObject(paramIndex ++, rowMap.get(key),
						jdbcType, digits_int);
			} else {
				pstmt.setObject(paramIndex ++, rowMap.get(key), jdbcType);
			}
		}
	}


	private String importDataFromWebService() throws Exception {
		Service service = new Service();
		Call call = (Call)service.createCall();
		call.setTargetEndpointAddress(insteadOfDefaultsMap
				.get("webservice-url").toString());
		call.setOperation(method);
		Object[] params_copy = (Object[])params.clone();
		for (int i = 0; i < params.length; i ++) {
			if (params[i] instanceof Eval)
				params_copy[i] = ((Eval)params[i]).eval();
		}
		return call.invoke(params_copy).toString();
	}


	private void saveData(String data) throws Exception {
		SAXReader reader = new SAXReader();
		Reader in = new StringReader(data);
		Document doc = reader.read(in);
		MyVisitorSupport visitor = new MyVisitorSupport();
		doc.accept(visitor);
		Class.forName(insteadOfDefaultsMap.get("jdbc-driver").toString());
		Connection con = DriverManager.getConnection(
				insteadOfDefaultsMap.get("db-url").toString(),
				insteadOfDefaultsMap.get("db-user").toString(),
				insteadOfDefaultsMap.get("db-password").toString()
				);
		ResultSet rs = null;
		int batchCount = 0;
		PreparedStatement query_pstmt = con.prepareStatement(query_content);
		PreparedStatement insert_pstmt = con.prepareStatement(insert_content);
		try {
			for (int i = 0; i < visitor.getRows().size(); i ++) {
				Map tmpMap = (Map)visitor.getRows().get(i);
                tmpMap = rowFilter.filter(tmpMap);

				boolean isContinued = false;
				Iterator iterator = query_jdbcTypeMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next().toString();
					if (tmpMap.get(key).equals("")) {
						isContinued = true;
						break;
					}
				}
				if (isContinued)
					continue;

				bindParamForPstmt(tmpMap, query_jdbcTypeMap, query_digitsMap, query_pstmt);
				rs = query_pstmt.executeQuery();
				rs.next();
				if (rs.getInt(1) == 0) {
					bindParamForPstmt(tmpMap, insert_jdbcTypeMap, insert_digitsMap, insert_pstmt);
					insert_pstmt.addBatch();
					batchCount ++;
				}
				rs.close();
			}

			if (batchCount > 0) {
				insert_pstmt.executeBatch();
				con.commit();
			}
		} catch (Exception se) {
			se.printStackTrace();
			//log.warn(se);
			con.rollback();
			throw se;
		} finally {
			con.close();
		}
	}




	private static class MyVisitorSupport extends VisitorSupport {
		private Map tmpRow;
		private static final Set unusefulEles;
		private List rows = new ArrayList();
		private boolean transResult;
		private String transResultDesc;

		static {
			unusefulEles = new HashSet(4);
			unusefulEles.add("DataSets");
			unusefulEles.add("DataSet");
			unusefulEles.add("status");
		}

		public void visit(Element e) {
			String eName = e.getQName().getName();
			if (!unusefulEles.contains(eName)) {
				tmpRow.put(eName, e.getTextTrim());
				/*
				if (e.getTextTrim() == null)
					logger.debug("string is null");
				else if (e.getTextTrim().length() < 1)
					logger.debug("string is empty");
				 */
			} else if (eName.equals("DataSet")) {
				tmpRow = new HashMap();
				rows.add(tmpRow);
			} else if (eName.equals("status")) {
				transResultDesc = e.getTextTrim();
			}

			/*
			if (e.getQName().getName().equals("status")) {
				transResultDesc = e.getTextTrim();
			} else if (e.getQName().getName().equals("DataSet")) {
				tmpRow = new ArrayList();
				rows.add(tmpRow);
			} else if (!unusefulEles.contains(e.getQName().getName()))
				tmpRow.add(e.getTextTrim());
			 */
		}

		public void visit(Attribute attr) {
			transResult = Boolean.parseBoolean(attr.getValue());
		}




		public List getRows() {
			return rows;
		}

		public boolean isTransResult() {
			return transResult;
		}

		public String getTransResultDesc() {
			return transResultDesc;
		}
	}
}




class DefaultRowFilter implements RowFilter {
    public Map filter(Map row) {
        return row;
    }

    private DefaultRowFilter() {}

    public static DefaultRowFilter INSTANCE = new DefaultRowFilter();
}



