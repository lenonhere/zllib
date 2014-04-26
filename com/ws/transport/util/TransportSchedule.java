package com.ws.transport.util;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TransportSchedule {

	private static final Log logger = LogFactory.getLog(TransportSchedule.class);

	private Map defaultsMap = new HashMap();
	private Map tasksMap = new HashMap();
	private Map defaultEvalMap = new HashMap();

	private Map jdbcTypesMap;

	private Timer timer;

	public TransportSchedule() {
		jdbcTypesMap = new HashMap(6);
		jdbcTypesMap.put("java.sql.Types.VARCHAR", new Integer(
				java.sql.Types.VARCHAR));
		jdbcTypesMap.put("java.sql.Types.DECIMAL", new Integer(
				java.sql.Types.DECIMAL));
		jdbcTypesMap.put("java.sql.Types.DATE",
				new Integer(java.sql.Types.DATE));
		defaultEvalMap.put("start", new TimeEval());
		defaultEvalMap.put("cycle", new CycleEval());
	}

	public TransportTimerTask getTask(String task) {
		return (TransportTimerTask) tasksMap.get(task);
	}

	public void readSchedule(String fileName) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(fileName));
		Element root = document.getRootElement();
		Iterator i = root.elementIterator();
		while (i.hasNext()) {
			Element e = (Element) i.next();
			String eName = e.getQName().getName();
			if (eName.equals("default")) {
				parseDefault(e);
			} else if (eName.equals("task")) {
				TransportTimerTask task = new TransportTimerTask();
				parseTask(e, task);
				tasksMap.put(task.getName(), task);
			} else
				throw new UnsupportedOperationException();
		}
	}

	public void readSchedule() throws Exception {
		readSchedule("transport.xml");
	}

	private static void evalElement(Map map) {
		Object obj = map.get("start");
		if (!(obj instanceof Date)) {
			TimeEval te = new TimeEval();
			te.setTime(obj.toString());
			map.put("start", te.eval());
		}

		obj = map.get("cycle").toString();
		if (!(obj instanceof Long)) {
			CycleEval ce = new CycleEval();
			ce.setCycle(obj.toString());
			map.put("cycle", ce.eval());
		}
	}

	private void parseDefault(Element defaultElement) {
		Iterator i = defaultElement.elementIterator();
		while (i.hasNext()) {
			Element e = (Element) i.next();
			String eName = e.getQName().getName();
			String val = e.getTextTrim();
			Eval eval = IntradayEval.getInstance();
			if (val.equals("${system}"))
				defaultsMap.put(eName, eval);
			else
				defaultsMap.put(eName, val);
		}

		evalElement(defaultsMap);
	}

	private void parseTask(Element taskElement, TransportTimerTask task)
			throws Exception {
		task.setName(taskElement.attributeValue("name"));
		Iterator i = taskElement.elementIterator();
		Map tmp = new HashMap();
		while (i.hasNext()) {
			Element e = (Element) i.next();
			String eName = e.getQName().getName();
			if (eName.equals("method")) {
				parseMethod(e, task);
			} else if (eName.equals("actions")) {
				Iterator j = e.elementIterator();
				while (j.hasNext()) {
					Element e_a = (Element) j.next();
					parseAction(e_a, task);
				}
			} else
				tmp.put(eName, e.getTextTrim());
		}

		Iterator keyI = task.getInsteadOfDefaultsKeys().iterator();
		while (keyI.hasNext()) {
			Object key = keyI.next();
			if (tmp.get(key) == null || tmp.get(key).equals("${default}"))
				tmp.put(key, defaultsMap.get(key));
		}

		evalElement(tmp);
		task.setInsteadOfDefaultsMap(tmp);

		String s = taskElement.attributeValue("filter");
		if (s != null && s.length() > 0) {
			RowFilter filter = (RowFilter) Class.forName(s).newInstance();
			task.setRowFilter((filter));
		}
	}

	private void parseMethod(Element methodElement, TransportTimerTask task) {
		Iterator i = methodElement.elementIterator();
		task.setMethod(methodElement.attributeValue("name"));
		List params = new ArrayList();
		while (i.hasNext()) {
			Element e = (Element) i.next();
			if (e.getTextTrim().equals("${default}")
					|| e.getTextTrim().equals("${system}")) {
				Object obj = defaultsMap.get(e.getQName().getName());
				params.add(obj);
			} else
				params.add(e.getTextTrim());
		}
		task.setParams(params.toArray());
	}

	private void parseAction(Element actionElement, TransportTimerTask task) {
		Iterator i = actionElement.elementIterator();
		String eName = null;
		String content = null;
		LinkedHashMap jdbcTypeMap = new LinkedHashMap();
		Map digitsMap = new HashMap();
		while (i.hasNext()) {
			Element e = (Element) i.next();
			eName = e.getQName().getName();
			if (eName.equals("content")) {
				content = e.getTextTrim();
			} else if (eName.equals("param")) {
				jdbcTypeMap.put(e.attributeValue("key"),
						jdbcTypesMap.get(e.attributeValue("jdbcType")));
				digitsMap.put(e.attributeValue("key"),
						e.attributeValue("digits"));
			} else
				throw new UnsupportedOperationException();
		}

		if (actionElement.attributeValue("type").equals("query")) {
			task.setQuery_content(content);
			task.setQuery_jdbcTypeMap(jdbcTypeMap);
			task.setQuery_digitsMap(digitsMap);
		} else if (actionElement.attributeValue("type").equals("insert")) {
			task.setInsert_content(content);
			task.setInsert_jdbcTypeMap(jdbcTypeMap);
			task.setInsert_digitsMap(digitsMap);
		} else
			throw new UnsupportedOperationException();
	}

	public void startTask(String taskName) {
		TransportTimerTask task = (TransportTimerTask) tasksMap.get(taskName);
		startTask(task);
	}

	public void startTask(TransportTimerTask task) {
		if (timer == null)
			timer = new Timer();
		Date start = (Date) task.getInsteadOfDefaultsMap().get("start");
		long delay = ((Long) task.getInsteadOfDefaultsMap().get("cycle"))
				.longValue();
		timer.scheduleAtFixedRate(task, start, delay);
		logger.debug("Transport Task -- " + task.getName() + " started");
	}

	public void startAllTasks() {
		Iterator i = tasksMap.entrySet().iterator();
		while (i.hasNext()) {
			Entry entry = (Entry) i.next();
			TransportTimerTask task = (TransportTimerTask) entry.getValue();
			startTask(task);
		}
	}

	public static void main(String[] args) throws Exception {
		TransportSchedule shedule = new TransportSchedule();
		shedule.readSchedule();
		TransportTimerTask task = shedule.getTask("getSyGXCData");
		/*
		 * for (Object obj : task.getParams()) logger.debug(obj);
		 * logger.debug(task.getInsert_content());
		 * logger.debug(task.getQuery_content());
		 * logger.debug(task.getMethod());
		 * logger.debug(task.getInsert_digitsMap().get("INVENTORY_QUANTITY"));
		 * logger.debug(task.getInsert_jdbcTypeMap().get("COMPANY_NAME"));
		 * logger.debug(task.getInsteadOfDefaultsMap().get("webservice-url"));
		 * logger.debug(task.getInsteadOfDefaultsMap().get("start"));
		 * logger.debug(task.getInsteadOfDefaultsMap().get("cycle"));
		 * logger.debug(task.getParams()[0]); logger.debug(task.getParams()[1]);
		 * logger.debug(((Eval)task.getParams()[2]).eval());
		 * logger.debug(((Date)shedule.defaultsMap.get("start")).getTime());
		 * logger.debug(shedule.defaultsMap.get("cycle"));
		 */
		task.runTest("2008-03-16", "2008-03-31");
		logger.debug("************************************************************************************");
		logger.debug("Over");
	}
}

interface Eval {
	Object eval();
}

class IntradayEval implements Eval {
	private static final IntradayEval instance = new IntradayEval();

	private IntradayEval() {
	}

	public Object eval() {
		return new Date(Calendar.getInstance().getTimeInMillis()).toString();
	}

	public static IntradayEval getInstance() {
		return instance;
	}
}

class TimeEval implements Eval {
	private String time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Object eval() {
		// TODO Auto-generated method stub
		StringTokenizer stz = new StringTokenizer(time, ": ");
		if (stz.countTokens() != 3)
			throw new UnsupportedOperationException(
					"should be hours:minutes:seconds");
		int hours = Integer.parseInt(stz.nextToken());
		int minutes = Integer.parseInt(stz.nextToken());
		int seconds = Integer.parseInt(stz.nextToken());
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, hours);
		c.set(Calendar.MINUTE, minutes);
		c.set(Calendar.SECOND, seconds);
		return new Date(c.getTimeInMillis());
	}
}

class CycleEval implements Eval {
	private String cycle;

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public Object eval() {
		StringTokenizer stz = new StringTokenizer(cycle, "* ");
		long seconds = 1;
		while (stz.hasMoreTokens())
			seconds *= Long.parseLong(stz.nextToken());
		return new Long(seconds);
	}
}
