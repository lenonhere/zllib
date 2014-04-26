package com.zl.base.core.db.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.common.SystemConfig;
import com.zl.exception.ConfigurationException;
import com.zl.exception.ObjectNotFoundException;

public class ProcedureConfig extends BaseConfig {

	private static final Log log = LogFactory.getLog(ProcedureConfig.class);

	/**
	 * map containing procedures. use procedure's name to index procedure itself
	 */
	private Map<String, Object> procedureMap = new HashMap<String, Object>();

	/**
	 * list containing procedures, for temporary purpose only, cleared after
	 * building the configuration
	 */
	private List<Procedure> procedureList = new ArrayList<Procedure>();

	private ProcedureConfig() {
	}

	/**
	 * default digeste rules' file name
	 */
	private static final String PROC_RULES_FILE = "procedure-rules.xml";

	private static ProcedureConfig config = new ProcedureConfig();

	/**
	 * all procedure config file name should with prefix "procedure-"
	 */
	private static final String PREFIX = "procedure-";

	/**
	 * all procedure config file name should with suffix ".xml"
	 */
	private static final String SUFFIX = ".xml";

	/**
	 * wrapper of procedure, making accessors look like procedures
	 */
	public static class ProcedureWrapper {

		private String name;
		private String target;
		private List<String> inParamNames;
		private List<String> inParamTypes;
		private List<String> outParamNames;
		private List<String> outParamTypes;
		private List<String> resultSetNames;
		private Map<String, String> paramValues;
		private Map<String, Object> paramSources;
		private Map<String, Object> resultSeparators;
		private Map<String, Object> resultIgnores;
		private Map<String, Object> resultRenames;
		private Map<String, Object> resultReplaces;
		private Map<String, Object> resultHorizons;
		private Map<String, Object> resultVerticals;
		private Map<String, Object> resultContents;
		private static final List EMPTY_LIST = new ArrayList();

		/**
		 * get procedure/accessor name
		 *
		 * @return String
		 */
		public String getName() {
			return name;
		}

		/**
		 * get target procedure in database
		 *
		 * @return String
		 */
		public String getTarget() {
			return target;
		}

		/**
		 * get input parameter names
		 *
		 * @return List
		 */
		public List getInParamNames() {
			return inParamNames;
		}

		/**
		 * get input parameter types
		 *
		 * @return List
		 */
		public List getInParamTypes() {
			return inParamTypes;
		}

		/**
		 * get output parameter names
		 *
		 * @return List
		 */
		public List getOutParamNames() {
			return outParamNames;
		}

		/**
		 * get output parameter types
		 *
		 * @return List
		 */
		public List getOutParamTypes() {
			return outParamTypes;
		}

		/**
		 * get result set names
		 *
		 * @return List
		 */
		public List getResultSetNames() {
			return resultSetNames;
		}

		/**
		 * get permanent (unreplacable) value for parameter
		 *
		 * @param paramName
		 *            String
		 * @return String
		 */
		public String getParamValue(String paramName) {
			if (paramValues == null) {
				return null;
			}
			Object value = paramValues.get(paramName);
			return value == null ? null : value.toString();
		}

		/**
		 * get source property name for parameter if not found, return paramName
		 * itself
		 *
		 * @param paramName
		 *            String
		 * @return String
		 */
		public String getParamSource(String paramName) {
			if (paramSources == null) {
				return paramName;
			}
			Object source = paramSources.get(paramName);
			return source == null ? paramName : source.toString();
		}

		/**
		 * get result set joint separator
		 *
		 * @param resultName
		 *            String
		 * @return String
		 */
		public String getResultSeparator(String resultName) {
			if (resultSeparators == null) {
				return null;
			}
			Object value = resultSeparators.get(resultName);
			return value == null ? null : value.toString();
		}

		/**
		 * get list of contents
		 *
		 * @param resultName
		 *            String
		 * @return List
		 */
		public List getResultContents(String resultName) {
			if (resultContents == null) {
				return EMPTY_LIST;
			}
			Object contents = resultContents.get(resultName);
			return contents == null ? EMPTY_LIST : (List) contents;
		}

		/**
		 * get list of horizons
		 *
		 * @param resultName
		 *            String
		 * @return List
		 */
		public List getResultHorizons(String resultName) {
			if (resultHorizons == null) {
				return EMPTY_LIST;
			}
			Object horizons = resultHorizons.get(resultName);
			return horizons == null ? EMPTY_LIST : (List) horizons;
		}

		/**
		 * get list of ignores
		 *
		 * @param resultName
		 *            String
		 * @return List
		 */
		public List getResultIgnores(String resultName) {
			if (resultIgnores == null) {
				return EMPTY_LIST;
			}
			Object ignores = resultIgnores.get(resultName);
			return ignores == null ? EMPTY_LIST : (List) ignores;
		}

		/**
		 * get list of renames
		 *
		 * @param resultName
		 *            String
		 * @return List
		 */
		public List getResultRenames(String resultName) {
			if (resultRenames == null) {
				return EMPTY_LIST;
			}
			Object renames = resultRenames.get(resultName);
			return renames == null ? EMPTY_LIST : (List) renames;
		}

		/**
		 * get list of replaces
		 *
		 * @param resultName
		 *            String
		 * @return List
		 */
		public List getResultReplaces(String resultName) {
			if (resultReplaces == null) {
				return EMPTY_LIST;
			}
			Object replaces = resultReplaces.get(resultName);
			return replaces == null ? EMPTY_LIST : (List) replaces;
		}

		/**
		 * get list of verticals
		 *
		 * @param resultName
		 *            String
		 * @return List
		 */
		public List getResultVerticals(String resultName) {
			if (resultVerticals == null) {
				return EMPTY_LIST;
			}
			Object verticals = resultVerticals.get(resultName);
			return verticals == null ? EMPTY_LIST : (List) verticals;
		}

		public String toString() {
			StringBuffer buffer = new StringBuffer();
			buffer.append("[ProcedureWrapper>>> ");
			buffer.append("name=");
			buffer.append(name);
			buffer.append(", target=");
			buffer.append(target);
			buffer.append(", inParamNames=");
			buffer.append(inParamNames);
			buffer.append(", inParamTypes=");
			buffer.append(inParamTypes);
			buffer.append(", outParamNames=");
			buffer.append(outParamNames);
			buffer.append(", outParamTypes=");
			buffer.append(outParamTypes);
			buffer.append(", resultSetNames=");
			buffer.append(resultSetNames);
			buffer.append(", paramValues=");
			buffer.append(paramValues);
			buffer.append(", paramSources=");
			buffer.append(paramSources);
			buffer.append(", resultIgnores=");
			buffer.append(resultIgnores);
			buffer.append(", resultRenames=");
			buffer.append(resultRenames);
			buffer.append(", resultReplaces=");
			buffer.append(resultReplaces);
			buffer.append(", resultSeparators=");
			buffer.append(resultSeparators);
			buffer.append(", resultHorizons=");
			buffer.append(resultHorizons);
			buffer.append(", resultVerticals=");
			buffer.append(resultVerticals);
			buffer.append(", resultContents=");
			buffer.append(resultContents);
			return buffer.toString();
		}

	}

	/**
	 * build procedure config from a set of procedure-*.xml (excluding
	 * procedure-rules.xml), according to rules in procedure-rules.xml
	 */
	public void build() {

		try {
			// get rules form file, and use these rules to initialize a digester
			File rules = getFile(PROC_RULES_FILE);
			Digester digester = DigesterLoader.createDigester(rules.toURL());

			// get all files with name like procedure-*.xml excluding
			// PROC_RULES_FILE
			List<String> fileNames = new ArrayList<String>();
			fileNames.add(PROC_RULES_FILE);
			List<File> files = getFiles(PREFIX, SUFFIX, fileNames);

			// for each file, digeste it & buffer all produced procedures
			// in procedure list
			for (int i = 0, n = files.size(); i < n; i++) {
				// produce all procedures form the given config file
				File file = files.get(i);
				digester.push(new ArrayList());
				List procedures = (List) digester.parse(new FileInputStream(
						file));

				// add all produced procedures into procedure list
				procedureList.addAll(procedures);
			}

			remedy();
		} catch (Exception e) {
			log.error("parsing procedure descriptions: ", e);
			if (e instanceof ConfigurationException) {
				throw (ConfigurationException) e;
			} else {
				throw new ConfigurationException();
			}
		} finally {
			procedureList.clear();
		}
	}

	/**
	 * check constraints on the configuration & remedy ignored properties if the
	 * procedure not satisfies constraints, then throws ConfigurationException
	 * (actually form its submethods)
	 *
	 * @param procedure
	 *            Procedure
	 * @return boolean
	 */
	protected void remedy() {
		if (procedureList.size() > 0) {
			remedyProcedure((Procedure) procedureList.get(0), 1);

			// transfer procedures from List to Map, wrapped
			for (int i = 0, n = procedureList.size(); i < n; i++) {
				Procedure procedure = procedureList.get(i);
				List<Object> accessors = procedure.getAccessors();
				makeWrappers(procedure, accessors);
			}
		}
	}

	private void makeWrappers(Procedure procedure, List<Object> accessors) {

		List<Object> inParams = procedure.getInParams();
		List<Object> outParams = procedure.getOutParams();
		List<Object> resultSets = procedure.getResultSets();

		// get lists of input params' name & type
		List<String> inParamNames = new ArrayList<String>();
		List<String> inParamTypes = new ArrayList<String>();
		for (int i = 0; i < inParams.size(); i++) {
			Parameter param = (Parameter) inParams.get(i);
			inParamNames.add(param.getName());
			String type = param.getType();
			inParamTypes.add(type == null ? "java.lang.String" : type);
		}

		// get lists of output params' name & type
		List<String> outParamNames = new ArrayList<String>();
		List<String> outParamTypes = new ArrayList<String>();
		for (int i = 0; i < outParams.size(); i++) {
			Parameter param = (Parameter) outParams.get(i);
			outParamNames.add(param.getName());
			outParamTypes.add(param.getType());
		}

		// get lists of result sets' name & type
		List<String> resultSetNames = new ArrayList<String>();
		for (int i = 0; i < resultSets.size(); i++) {
			ResultSet result = (ResultSet) resultSets.get(i);
			resultSetNames.add(result.getName());
		}

		// pre-create ProcedureWrappers & initailize common values
		int length = accessors.size() + 1;
		ProcedureWrapper[] wrapper = new ProcedureWrapper[length];
		for (int i = 0; i < length; i++) {
			wrapper[i] = new ProcedureWrapper();
			wrapper[i].target = procedure.getTarget();
			wrapper[i].inParamNames = inParamNames;
			wrapper[i].inParamTypes = inParamTypes;
			wrapper[i].outParamNames = outParamNames;
			wrapper[i].outParamTypes = outParamTypes;
			wrapper[i].resultSetNames = resultSetNames;
		}

		wrapper[length - 1].name = procedure.getName();
		procedureMap.put(procedure.getName(), wrapper[length - 1]);

		// for each accessors, initialize ProcedureWrappers
		for (int i = 0, n = accessors.size(); i < n; i++) {
			Accessor accessor = (Accessor) accessors.get(i);
			wrapper[i].name = accessor.getName();

			List<Object> consts = accessor.getConsts();
			if (consts.size() > 0) {
				wrapper[i].paramValues = new HashMap<String, String>();
				for (int j = 0; j < consts.size(); j++) {
					Parameter param = (Parameter) consts.get(j);
					wrapper[i].paramValues.put(param.getName(),
							param.getValue());
				}
			}

			List<Object> sources = accessor.getSources();
			if (sources.size() > 0) {
				wrapper[i].paramSources = new HashMap();
				for (int j = 0; j < sources.size(); j++) {
					Parameter param = (Parameter) sources.get(j);
					wrapper[i].paramSources.put(param.getName(),
							param.getSource());
				}
			}

			List<Object> results = accessor.getResultSets();
			if (results.size() > 0) {
				wrapper[i].resultIgnores = new HashMap();
				wrapper[i].resultRenames = new HashMap();
				wrapper[i].resultReplaces = new HashMap();
				wrapper[i].resultSeparators = new HashMap();
				wrapper[i].resultHorizons = new HashMap();
				wrapper[i].resultVerticals = new HashMap();
				wrapper[i].resultContents = new HashMap();

				for (int j = 0; j < results.size(); j++) {
					ResultSet result = (ResultSet) results.get(j);
					if (result.getIgnores().size() > 0) {
						wrapper[i].resultIgnores.put(result.getName(),
								result.getIgnores());
					}
					if (result.getRenames().size() > 0) {
						wrapper[i].resultRenames.put(result.getName(),
								result.getRenames());
						wrapper[i].resultReplaces.put(result.getName(),
								result.getReplaces());
					}
					if (result.getHorizons().size() > 0) {
						wrapper[i].resultSeparators.put(result.getName(),
								result.getSeparator());
						wrapper[i].resultHorizons.put(result.getName(),
								result.getHorizons());
						wrapper[i].resultVerticals.put(result.getName(),
								result.getVerticals());
						wrapper[i].resultContents.put(result.getName(),
								result.getContents());
					}
				}
			}
			procedureMap.put(accessor.getName(), wrapper[i]);
		}
	}

	/**
	 * check constraints
	 *
	 * @param procedure
	 *            Procedure
	 * @param headIndex
	 *            int
	 * @return List
	 */
	private List<String> remedyProcedure(Procedure procedure, int headIndex) {

		List<String> nameList;

		if (headIndex < procedureList.size()) {
			// nameList = new ArrayList();
			nameList = remedyProcedure(
					(Procedure) procedureList.get(headIndex), headIndex + 10);
		} else {
			nameList = new ArrayList<String>(); // last procedure in list
		}

		String procedureName = procedure.getName();
		// check procedure name duplication
		if (nameList.contains(procedureName)) {
			log.error("duplicated procedure/accessor names found: "
					+ "duplicated name = " + procedureName);
			throw new ConfigurationException();
		} else {
			// if not duplicated, add name into name list
			nameList.add(procedureName);
		}

		List<Object> accessors = procedure.getAccessors();
		// check accessor names duplication
		nameList = remedyAccessors(procedureName, accessors, nameList);

		// check parameter names duplication
		List<String> paramNames = remedyParameters(procedureName,
				procedure.getInParams(), procedure.getOutParams());

		// check result sets name duplication
		List<String> resultSetNames = remedyResultSets(procedureName,
				procedure.getResultSets());

		// check every parameter/result set referred by each acessor
		// is exists or not.
		for (int i = 0, n = accessors.size(); i < n; i++) {

			List<Object> paramList = ((Accessor) accessors.get(i)).getConsts();
			for (int j = 0, m = paramList.size(); j < m; j++) {
				String paramName = ((Parameter) paramList.get(j)).getName();
				if (!paramNames.contains(paramName)) {
					log.error("parameter name in accessor not found: "
							+ "parameter name = " + paramName
							+ " in procedure = " + procedureName);
					throw new ConfigurationException();
				}
			}

			List<Object> resultList = ((Accessor) accessors.get(i))
					.getResultSets();
			for (int j = 0, m = resultList.size(); j < m; j++) {
				String resultName = ((ResultSet) resultList.get(j)).getName();
				if (!resultSetNames.contains(resultName)) {
					log.error("result set name in accessor not found: "
							+ "result set name = " + resultName
							+ " in procedure = " + procedureName);
					throw new ConfigurationException();
				}
			}
		}

		return nameList;
	}

	private List<String> remedyAccessors(String procedureName,
			List<Object> accessors, List<String> nameList) {
		for (int i = 0, n = accessors.size(); i < n; i++) {
			String accessorName = ((Accessor) accessors.get(i)).getName();
			if (nameList.contains(accessorName)) {
				log.error("duplicated procedure/accessor names found: "
						+ "duplicated name = " + accessorName);
				throw new ConfigurationException();
			} else {
				// if not duplicated, add name into name list
				nameList.add(accessorName);
			}
		}
		return nameList;
	}

	private List<String> remedyParameters(String procedureName,
			List<Object> inParams, List<Object> outParams) {
		List<String> paramNameList = new ArrayList<String>();
		List<Object> paramList = new ArrayList<Object>(inParams);
		paramList.addAll(outParams);

		for (int i = 0, n = paramList.size(); i < n; i++) {
			String paramName = ((Parameter) paramList.get(i)).getName();
			if (paramNameList.contains(paramName)) {
				log.error("duplicated parameter names found: "
						+ "duplicated name = " + paramName
						+ " in procedure name = " + procedureName);
				throw new ConfigurationException();
			} else {
				// if not duplicated, add name into name list
				paramNameList.add(paramName);
			}
		}
		return paramNameList;
	}

	private List<String> remedyResultSets(String procedureName,
			List<Object> resultSets) {
		List<String> resultNameList = new ArrayList<String>();

		for (int i = 0, n = resultSets.size(); i < n; i++) {
			String resultName = ((ResultSet) resultSets.get(i)).getName();
			if (resultNameList.contains(resultName)) {
				log.error("duplicated result set names found: "
						+ "duplicated name = " + resultName
						+ " in procedure name = " + procedureName);
				throw new ConfigurationException();
			} else {
				// if not duplicated, add name into name list
				resultNameList.add(resultName);
			}
		}
		return resultNameList;
	}

	/**
	 * wrap all accessors, so they can been treated as real procedure
	 */
	@SuppressWarnings("unused")
	private void wrap() {

	}

	/**
	 * get a instance of ProcedureConfig
	 *
	 * @return ProcedureConfig
	 */
	public static ProcedureConfig getInstance() {
		return config;
	}

	/**
	 * get procedure with name as supplied
	 *
	 * @param name
	 *            String
	 * @return Procedure
	 */
	public ProcedureWrapper getProcedure(String name) {
		if (name == null) {
			log.error("try to get procedure with null input");
			throw new NullPointerException();
		}
		Object procedure = procedureMap.get(name);
		if (procedure == null) {
			log.error("no procedure wtih the name=" + name
					+ " as supplied exists");
			throw new ObjectNotFoundException();
		}
		return (ProcedureWrapper) procedure;
	}

	// TODO Main
	public static void main(String[] args) {
		String basePath = SystemConfig.getDocBasePath() + "/WEB-INF/procs";
		config.setBasePath(basePath);
		ProcedureConfig procConfig = ProcedureConfig.getInstance();
		System.out.println("BasePath: " + procConfig.getBasePath());
		procConfig.build();
		//
		ProcedureWrapper wrapper = (ProcedureWrapper) procConfig.procedureMap
				.get("demoShowGrid");
		System.out.println(wrapper.toString());
	}
}
