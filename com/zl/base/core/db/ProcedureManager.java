package com.zl.base.core.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zl.base.core.db.config.ProcedureConfig;
import com.zl.base.core.db.config.ProcedureConfig.ProcedureWrapper;
import com.zl.exception.DbException;

public class ProcedureManager {
	private static final Log log = LogFactory.getLog(ProcedureManager.class);

	/**
	 * object holding configuration of procedures
	 */
	private static ProcedureConfig config = ProcedureConfig.getInstance();

	/**
	 * prevent public instantiation
	 */
	private ProcedureManager() {
	}

	/**
	 * singleton
	 */
	private static ProcedureManager manager = new ProcedureManager();

	/**
	 * create a instance of CallHelper
	 *
	 * @param name
	 *            String
	 * @return CallHelper
	 */
	public static CallHelper getCallHelper(String name) {
		return new CallHelper(name);
	}

	public static Connection getConnection() {
		return ProcedureLauncher.getPooledConnection();
	}

	public static void closeConnection(Connection conn) {
		ProcedureLauncher.closePooledConnection(conn);
	}

	/**
	 * execute a procedure that is indexed by helper name
	 *
	 * @param helper
	 *            CallHelper
	 * @return CallHelper
	 * @throws Exception
	 */
	public static CallHelper call(CallHelper helper) throws Exception {
		return call(helper, null);
	}

	/**
	 * execute a procedure that is indexed by helper name if conn is null, use
	 * the internal pooled connections
	 *
	 * @param helper
	 *            CallHelper
	 * @param conn
	 *            Connection
	 * @return CallHelper
	 * @throws Exception
	 */
	public static CallHelper call(CallHelper helper, Connection conn)
			throws Exception {
		ProcedureWrapper wrapper = config.getProcedure(helper.getName());
		log.info(wrapper.toString());
		List inParams = manager.makeInParams(helper, wrapper);
		List outParams = manager.makeOutParams(helper, wrapper);

		// db2 jdbc type 4 need java type and sql type match, so we
		// add the following line to patch this mismatch, and make sure
		// every type is correct registered
		// (need ProcedureLauncher class support)
		List inParamTypes = manager.makeInParamTypes(helper, wrapper);

		SqlReturn results = null;
		try {
			if (conn == null) {
				results = ProcedureLauncher.callProcedure(wrapper.getTarget(),
						inParams, inParamTypes, outParams);
			} else {
				results = ProcedureLauncher.callProcedure(wrapper.getTarget(),
						inParams, inParamTypes, outParams, conn);
			}
		} catch (DbException e) {
			String errMsg = "execute procedure " + helper.getName() + " error";
			helper.setErrorLevel(CallHelper.EXE_WITH_ERROR);
			log.error(errMsg);
			helper.setErrorMessage(errMsg);
			throw new RuntimeException(e);
		}

		int state = Integer.parseInt(results.getOutputParam(0));

		helper.setState(state);
		helper.setSqlCode(state);

		if (state != 0) {
			String errMsg = "procedure name=" + wrapper.getName() + " target="
					+ wrapper.getTarget() + " returned in abnormal state="
					+ state;

			log.error(errMsg);
			helper.setErrorLevel(CallHelper.EXE_WITH_WARNING);
			helper.setErrorMessage(errMsg);
		}

		manager.processOutputs(helper, wrapper, results);
		manager.processResults(helper, wrapper, results);

		return helper;
	}

	private List makeInParamTypes(CallHelper helper, ProcedureWrapper wrapper) {

		return new ArrayList(wrapper.getInParamTypes());
	}

	private List makeInParams(CallHelper helper, ProcedureWrapper wrapper) {

		List inParamNames = wrapper.getInParamNames();
		List inParams = new ArrayList();

		for (int i = 0, n = inParamNames.size(); i < n; i++) {
			String name = inParamNames.get(i).toString();
			String value = wrapper.getParamValue(name);
			String source = wrapper.getParamSource(name);
			inParams.add(value == null ? helper.getParam(source) : value);
		}
		return inParams;
	}

	private List makeOutParams(CallHelper helper, ProcedureWrapper wrapper) {
		List outParamNames = wrapper.getOutParamNames();
		List outParams = new ArrayList();
		for (int i = 0, n = outParamNames.size(); i < n; i++) {
			// the value is not needed for calling a procedure
			outParams.add("?");
		}
		return outParams;
	}

	private void processOutputs(CallHelper helper, ProcedureWrapper wrapper,
			SqlReturn ruturn) {
		List outputs = new ArrayList();
		List outputNames = wrapper.getOutParamNames();

		// start with 1 to skip the procedure's state code
		for (int i = 1, n = outputNames.size() + 1; i < n; i++) {
			outputs.add(ruturn.getOutputParam(i));
		}
		helper.setOutputNames(outputNames);
		helper.setOutputs(outputs);
	}

	private void processResults(CallHelper helper, ProcedureWrapper wrapper,
			SqlReturn ruturn) throws Exception {
		List results = new ArrayList();
		List resultNames = wrapper.getResultSetNames();

		boolean wrongNumber = false;

		if (resultNames.size() != ruturn.getResultCount()) {
			log.error("procedure name=" + wrapper.getName() + " target="
					+ wrapper.getTarget()
					+ " number of result set mismatch: expected="
					+ resultNames.size() + " returned="
					+ ruturn.getResultCount());
			wrongNumber = true;
		}

		for (int i = 0, n = resultNames.size(); i < n; i++) {

			List resultSet = wrongNumber ? new ArrayList() : ruturn
					.getResultSet(i);

			if (resultSet.size() == 0) {
				results.add(resultSet);
				continue; // no result to be processed
			}

			String resultName = (String) resultNames.get(i);

			resultSet = processIgnoresAndRenames(wrapper, resultName, resultSet);

			resultSet = processJoint(wrapper, resultName, resultSet);

			results.add(resultSet);
		}
		helper.setResultNames(resultNames);
		helper.setResults(results);
	}

	private List processIgnoresAndRenames(ProcedureWrapper wrapper,
			String resultName, List resultSet) {

		List ignores = wrapper.getResultIgnores(resultName);
		List renames = wrapper.getResultRenames(resultName);
		List replaces = wrapper.getResultReplaces(resultName);

		if (ignores.size() == 0 && renames.size() == 0) {
			return resultSet;
		}

		DynaClass clazz = ((DynaBean) resultSet.get(0)).getDynaClass();
		DynaProperty[] dynaProperties = clazz.getDynaProperties();

		List oldNames = new ArrayList();
		List newNames = new ArrayList();
		List properties = new ArrayList();

		// obtain old/new property name mapping & constuct new properties
		// be care! if new property duplicaed, no special action will take
		for (int i = 0; i < dynaProperties.length; i++) {
			Class type = dynaProperties[i].getType();
			String oldName = dynaProperties[i].getName();
			// skip ignore property
			if (ignores.contains(oldName)) {
				continue;
			}

			// check renames
			boolean found = false;
			int count = 0;
			for (int limit = renames.size(); count < limit; count++) {
				if (oldName.equals(renames.get(count))) {
					String newName = (String) replaces.get(count);
					oldNames.add(oldName);
					newNames.add(newName);
					properties.add(new DynaProperty(newName, type));
					found = true;
				}
			}
			if (!found) { // no rename needed
				oldNames.add(oldName);
				newNames.add(oldName);
				properties.add(new DynaProperty(oldName, type));
			}
		}

		// create new dyanClass
		DynaProperty[] newProperties = new DynaProperty[properties.size()];
		for (int i = 0; i < properties.size(); i++) {
			newProperties[i] = (DynaProperty) properties.get(i);
		}
		BasicDynaClass newClazz = new BasicDynaClass(null, null, newProperties);

		// copy data
		List newResultSet = new ArrayList();
		for (int i = 0; i < resultSet.size(); i++) {
			DynaBean oldBean = (DynaBean) resultSet.get(i);
			DynaBean newBean = new BasicDynaBean(newClazz);

			for (int j = 0; j < oldNames.size(); j++) {
				Object value = oldBean.get((String) oldNames.get(j));
				newBean.set((String) newNames.get(j), value);
			}
			newResultSet.add(newBean);
		}

		return newResultSet;
	}

	private List processJoint(ProcedureWrapper wrapper, String resultName,
			List resultSet) throws Exception {

		List horizons = wrapper.getResultHorizons(resultName);
		List verticals = wrapper.getResultVerticals(resultName);
		List contents = wrapper.getResultContents(resultName);

		DynaClass clazz = ((DynaBean) resultSet.get(0)).getDynaClass();
		DynaProperty[] dynaProperties = clazz.getDynaProperties();

		List newHorizons = new ArrayList();
		List newVerticals = new ArrayList();
		List newContents = new ArrayList();
		List newClazzes = new ArrayList();

		// if list of contents contains no properties, then using the
		// original dynabean's properrties which are not in list of
		// horizons and list of verticals as contents
		// if list of contents has some properties, using these properties
		// as contents
		// all horizons, verticals and contents should have at least one
		// property, or no jonit operation will be done

		if (horizons.size() == 0 || verticals.size() == 0) {
			// log.info("procedure/accessor=" + wrapper.getName() +
			// " result=" + resultName + " has no horizons or" +
			// " vetticals defined in configuration, no jonit was taken");
			return resultSet;
		}

		boolean hasContents = (contents.size() > 0);
		for (int i = 0; i < dynaProperties.length; i++) {
			Class type = dynaProperties[i].getType();
			String propertyName = dynaProperties[i].getName();

			boolean used = false;
			if (horizons.contains(propertyName)) {
				newHorizons.add(propertyName);
				used = true;
			}
			if (verticals.contains(propertyName)) {
				newVerticals.add(propertyName);
				used = true;
			}
			if (!hasContents) {
				if (!used) {
					newContents.add(propertyName);
					newClazzes.add(type);
				}
			} else if (contents.contains(propertyName)) {
				newContents.add(propertyName);
				newClazzes.add(type);
			}
		}

		if (newHorizons.size() == 0 || newVerticals.size() == 0
				|| newContents.size() == 0) {
			log.error("procedure/accessor=" + wrapper.getName() + " result="
					+ resultName + " has no horizons or "
					+ " vetticals or contents available, no jonit was taken");
			return resultSet;
		}

		String separator = wrapper.getResultSeparator(resultName);
		// all horizons, verticals and contents are ready
		// find out all properties for new dyna class
		List properties = getDynaProperties(newVerticals, newContents,
				newClazzes, separator, resultSet);

		for (int i = 0; i < dynaProperties.length; i++) {
			if (newHorizons.contains(dynaProperties[i].getName())) {
				properties.add(dynaProperties[i]);
			}
		}

		// create new dyanClass
		DynaProperty[] newProperties = new DynaProperty[properties.size()];
		for (int i = 0; i < properties.size(); i++) {
			newProperties[i] = (DynaProperty) properties.get(i);
		}
		BasicDynaClass newClazz = new BasicDynaClass(null, null, newProperties);

		// copy data
		List newResultSet = copyDynaBeanValues(newClazz, newHorizons,
				newVerticals, newContents, separator, resultSet);

		return newResultSet;
	}

	private List getDynaProperties(List verticals, List contents, List types,
			String separator, List resultSet) {

		List verticalValues = new ArrayList();
		int verticalCount = verticals.size();
		int contentCount = contents.size();
		List properties = new ArrayList();

		// travel over all dyna beans
		for (int i = 0; i < resultSet.size(); i++) {
			DynaBean bean = (DynaBean) resultSet.get(i);

			// assume list verticals has at least one element
			String verticalValue = bean.get(verticals.get(0).toString())
					.toString();
			for (int j = 1; j < verticalCount; j++) {
				verticalValue += separator
						+ bean.get(verticals.get(j).toString()).toString();
			}

			if (!verticalValues.contains(verticalValue)) {
				if (contentCount == 1) {
					properties.add(new DynaProperty(verticalValue,
							(Class) types.get(0)));
				} else {
					// if list of contents has a size greater than one, attach
					// the contents properties name to vertical values
					// case: "1111" + "11" or "111" + "111" ? be careful !
					for (int j = 0; j < contentCount; j++) {
						String value = verticalValue + separator
								+ (String) contents.get(j);
						properties.add(new DynaProperty(value, (Class) types
								.get(j)));
					}
				}
				verticalValues.add(verticalValue);
			}
		}
		return properties;
	}

	private List copyDynaBeanValues(DynaClass dynaClass, List horizons,
			List verticals, List contents, String separator, List resultSet)
			throws Exception {
		List newResultSet = new ArrayList();
		int horizonCount = horizons.size();
		int verticalCount = verticals.size();
		int contentCount = contents.size();

		// travel over all dyna beans make
		for (int i = 0; i < resultSet.size(); i++) {
			DynaBean oldBean = (DynaBean) resultSet.get(i);

			// use a bean created before or create a new bean,
			// based on horizon properties' values
			DynaBean newBean = findDynaBean(newResultSet, oldBean, horizons);

			if (newBean == null) {
				newBean = new BasicDynaBean(dynaClass);
				// copy horizon values
				for (int j = 0; j < horizonCount; j++) {
					try {
						Object value = oldBean.get((String) horizons.get(j));
						newBean.set((String) horizons.get(j), value);
					} catch (Exception ex) {
						log.error((String) horizons.get(j) + ":" + ex);
						throw ex;
					}

				}
				newResultSet.add(newBean);
			}

			// copy content values
			// assume list verticals has at least one element
			String verticalValue = oldBean.get(verticals.get(0).toString())
					.toString();
			for (int j = 1; j < verticalCount; j++) {
				verticalValue += separator
						+ oldBean.get(verticals.get(j).toString()).toString();
			}

			if (contentCount == 1) {
				Object propertyValue = oldBean.get((String) contents.get(0)
						.toString());
				newBean.set(verticalValue, propertyValue);
			} else {
				// has the same problem as in getDynaProperties, ignore it
				// may repair it in future
				for (int j = 0; j < contentCount; j++) {
					String contentName = (String) contents.get(j).toString();
					Object propertyValue = oldBean.get(contentName);
					try {
						newBean.set(verticalValue + separator + contentName,
								propertyValue);
					} catch (Exception ex) {
						log.error(verticalValue + separator + contentName + ":"
								+ ex);
						throw ex;
					}
				}
			}
		}

		return newResultSet;
	}

	private DynaBean findDynaBean(List resultSet, DynaBean bean, List names)
			throws Exception {
		if (resultSet.size() == 0) {
			return null;
		}
		// match all values indicated by names to find a proper bean
		for (int i = 0; i < resultSet.size(); i++) {
			int count = 0;
			DynaBean oldBean = (DynaBean) resultSet.get(i);
			for (int limit = names.size(); count < limit; count++) {
				String name = names.get(count).toString();
				try {
					Object value = bean.get(name);
					if (bean.get(name) == null) {
						value = "";
					} else {
						if (!value.equals(oldBean.get(name))) {
							break;
						}
					}
				} catch (Exception ex) {
					log.error(ex);
					throw ex;
				}

			}
			// if all property values matched, find a bean successfully
			if (count == names.size()) {
				return oldBean;
			}
		}
		// no bean was find, reutrn null
		return null;
	}
}
