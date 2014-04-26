package com.zl.util;

import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.common.Pagination;
import com.web.form.NewsForm;
import com.web.form.SycompanyInfoForm;
import com.web.form.TobaccoInfoForm;
import com.zl.base.core.db.Executer;
import com.zl.base.core.db.SqlParam;
import com.zl.base.core.db.SqlReturn;
import com.zl.base.core.db.SqlReturn;

public class TradeList {

	private static final Logger log = Logger.getLogger(TradeList.class);

	public static String getSelectListBy(List<Object> list, int styleWidth,
			String id, String name, String methodStr) {
		String returnStr = "";
		try {
			returnStr = "<select style='width:" + styleWidth + "px;' id='" + id
					+ "' name ='" + name + "' " + methodStr + " >";

			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean obj = (BasicDynaBean) list.get(i);
				returnStr = returnStr + "<option value='" + obj.get("name")
						+ "'>" + obj.get("name") + "</option>";
			}
			returnStr = returnStr + "</select>";
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return returnStr;
	}

	public static String getSelectContentListBy(List<Object> list) {
		String returnStr = "";
		try {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean obj = (BasicDynaBean) list.get(i);
				returnStr = returnStr + "<option value='" + obj.get("id")
						+ "'>" + obj.get("name") + "</option>";
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return returnStr;
	}

	public static String getSelectListByTabName(List<Object> list,
			int styleWidth, String id, String name, String methodStr) {
		String returnStr = "";
		try {
			returnStr = "<select style='width:" + styleWidth + "px;' id='" + id
					+ "' name ='" + name + "' " + methodStr + " >";
			String schema = "";
			String tabname = "";
			String str = "";
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean obj = (BasicDynaBean) list.get(i);
				// schema = (String) obj.get("tabschema");
				tabname = (String) obj.get("tabname");
				// tabname = schema.trim() + "." + tabname.trim();
				returnStr = returnStr + "<option value='" + tabname + "'>"
						+ tabname + "</option>";
			}
			returnStr = returnStr + "</select>";
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return returnStr;
	}

	/* com.nb.adv.common.TradeList 2013-01-23am Begin */
	/*-----------------------------------------------------------
	 * a new way to call a procedure
	 *-----------------------------------------------------------*/
	protected static HashMap typesMap = null;

	static {
		typesMap = new HashMap();
		typesMap.put(java.lang.String.class, new Integer(Types.CHAR));
		typesMap.put(java.lang.Integer.class, new Integer(Types.INTEGER));
		typesMap.put(java.math.BigDecimal.class, new Integer(Types.DECIMAL));
		typesMap.put(java.lang.Double.class, new Integer(Types.DOUBLE));
		typesMap.put(java.lang.Float.class, new Integer(Types.FLOAT));
	}

	/**
	 * null safe toString wrapper
	 *
	 * @param obj
	 *            Object
	 * @return String
	 */
	protected static String toString(Object obj) {
		return (obj == null) ? null : obj.toString();
	}

	/**
	 * allocate *length* parameter holders
	 *
	 * @param length
	 *            int
	 * @return SqlParam[]
	 */
	protected static SqlParam[] createParams(final int length) {
		SqlParam[] params = new SqlParam[length];
		for (int i = 0; i < length; i++) {
			params[i] = new SqlParam();
		}
		return params;
	}

	/**
	 * call procedure with procedure name and input parameters
	 *
	 * @param procName
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn callProcedure(String procName) {

		return callProcedure(procName, null, null);
	}

	/**
	 * call procedure with procedure name and input parameters
	 *
	 * @param procName
	 *            String
	 * @param inParams
	 *            ArrayList
	 * @return SqlReturn
	 */
	public static SqlReturn callProcedure(String procName, List inParams) {

		return callProcedure(procName, inParams, null);
	}

	/**
	 * call procedure with procedure name and input/output parameters
	 *
	 * @param procName
	 *            String
	 * @param inParams
	 *            ArrayList
	 * @param outParams
	 *            ArrayList
	 * @return SqlReturn
	 */
	public static SqlReturn callProcedure(String procName, List inParams,
			List outParams) {

		int inSize = (inParams == null) ? 0 : inParams.size();
		int outSize = (outParams == null) ? 0 : outParams.size();

		if (procName == null || procName.equals("")) {
			log.error("call procedure with empty name" + " with " + inSize
					+ " input parameters and " + outSize + " output parameters");
			throw new NullPointerException("procedure name is null or empty");
		}

		log.debug("call procedure " + procName + " with " + inSize
				+ " input parameters and " + outSize + " output parameters");

		SqlParam[] params = createParams(inSize + outSize);

		// collect input parameters
		for (int i = 0; i < inSize; i++) {
			Object obj = inParams.get(i);
			Integer type = (obj == null) ? null : (Integer) typesMap.get(obj
					.getClass());

			if (type == null) {
				// use Types.CHAR as default sql type for unspecified parameter
				params[i].setAll(0, Types.CHAR, toString(obj));
			} else {
				params[i].setAll(0, type.intValue(), toString(obj));
			}

			log.debug("parameter " + i + " : " + toString(obj));
		}

		// collect output parameters
		for (int i = 0; i < outSize; i++) {
			Object obj = outParams.get(i);
			Integer type = (obj == null) ? null : (Integer) typesMap.get(obj
					.getClass());

			if (type == null) {
				// use Types.CHAR as default sql type for unspecified parameter
				params[i + inSize].setAll(1, Types.CHAR, toString(obj));
			} else {
				params[i + inSize].setAll(1, type.intValue(), toString(obj));
			}
			log.debug("parameter " + (i + inSize) + " : " + toString(obj));
		}

		return executeProcedure(procName, params);
	}

	/**
	 * call procedure and log exceptions if any caused during execution
	 *
	 * @param procName
	 *            String
	 * @param params
	 *            SqlParam[]
	 * @return SqlReturn
	 */
	protected static SqlReturn executeProcedure(String procName, SqlParam[] params) {
		SqlReturn result = null;

		try {
			result = Executer.getInstance()
					.ExecStoreProcedure(procName, params);
			int returnValue = Integer.parseInt(result.getOutputParam(0));
			if (returnValue < 0) {
				log.debug("The value returned by procedure " + procName
						+ " is less than 0: " + returnValue);
			}
		} catch (Exception e) {
			log.debug("Execute procedure " + procName
					+ " failed, the causing exception is as below:  ");
			log.error(e);
		}
		return result;
	}

	/*----------------------------------------------------------
	 * 存储过程
	 *----------------------------------------------------------*/

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn G_DistrictTreeQuery(String intPersonID) {

		ArrayList inParams = new ArrayList();
		inParams.add(intPersonID);

		return callProcedure("P_DistrictTreeQuery", inParams);
	}

	/**
	 *
	 * @param strQuickCode
	 *            String
	 * @param strIsAllToba
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn GP_SelectTobacco(String strQuickCode,
			String strIsAllToba) {

		ArrayList inParams = new ArrayList();
		inParams.add(strQuickCode);
		inParams.add(strIsAllToba);

		return callProcedure("GP_SelectTobacco", inParams);
	}

	/**
	 * 获得所有部门列表
	 *
	 * @return
	 */
	public static SqlReturn g_getDeparts(String departName, String isActive,
			String isduty) {
		ArrayList inParams = new ArrayList();
		inParams.add(departName);
		inParams.add(isActive);
		inParams.add(isduty);
		return callProcedure("g_getDeparts", inParams);
	}

	/**
	 * 获得要修改的部门的信息
	 *
	 * @return
	 */
	public static SqlReturn g_getDepartInfo(String departId) {
		ArrayList inParams = new ArrayList();
		inParams.add(departId);
		return callProcedure("g_getDepartInfo", inParams);
	}

	/**
	 * 保存部门信息
	 *
	 * @return
	 */
	public static SqlReturn g_saveDepart(String type, String departId,
			String departCode, String departName, String parentId,
			String isActive, String isduty, String pinyin, String message) {
		ArrayList inParams = new ArrayList();
		inParams.add(type);
		inParams.add(departId);
		inParams.add(departCode);
		inParams.add(departName);
		inParams.add(parentId);
		inParams.add(isActive);
		inParams.add(isduty);
		inParams.add(pinyin);

		ArrayList outParams = new ArrayList();
		outParams.add(message);

		return callProcedure("g_saveDepart", inParams, outParams);
	}

	/**
	 * 获得所有卷烟系列列表
	 *
	 * @return
	 */
	public static SqlReturn g_getBrands() {
		return callProcedure("g_getBrands");
	}

	/**
	 * 获得要修改的卷烟系列的信息
	 *
	 * @return
	 */
	public static SqlReturn g_getBrandInfo(String brandCode) {
		ArrayList inParams = new ArrayList();
		inParams.add(brandCode);
		return callProcedure("g_getBrandInfo", inParams);
	}

	/**
	 * 保存卷烟系列信息
	 *
	 * @return
	 */
	public static SqlReturn g_saveBrand(String type, String brandCode,
			String brandName, String isActive, String message) {
		ArrayList inParams = new ArrayList();
		inParams.add(type);
		inParams.add(brandCode);
		inParams.add(brandName);
		inParams.add(isActive);

		ArrayList outParams = new ArrayList();
		outParams.add(message);

		return callProcedure("g_saveBrand", inParams, outParams);
	}

	/**
	 * 获得所有卷烟列表
	 *
	 * @return
	 */
	public static SqlReturn g_getAllTobaccos(String factoryCode) {
		ArrayList inParams = new ArrayList();
		inParams.add(factoryCode);
		return callProcedure("g_getAllTobaccos", inParams);
	}

	/**
	 * 朱忠南修改,增加2个模糊查询字段，卷烟编号和卷烟名称
	 *
	 * @param Province
	 * @param factoryCode
	 * @param tbcode
	 * @param tbname
	 * @return
	 */
	public static SqlReturn G_getTobaccoByFact(String Province,
			String factoryCode, String tbcode, String tbname) {
		ArrayList inParams = new ArrayList();
		inParams.add(Province);
		inParams.add(factoryCode);
		inParams.add(tbcode);
		inParams.add(tbname);
		return callProcedure("G_getTobaccoByFact", inParams);
	}

	public static SqlReturn G_getTobaccoByFactlevel1(String Province,
			String factoryCode, String tbcode, String tbname) {
		ArrayList inParams = new ArrayList();
		inParams.add(Province);
		inParams.add(factoryCode);
		inParams.add(tbcode);
		inParams.add(tbname);
		return callProcedure("G_getTobaccoByFactlevel1", inParams);
	}

	/**
	 * 获得要修改的卷烟的信息
	 *
	 * @return
	 */
	public static SqlReturn g_getTobaccoInfo(String tobaccoSystId) {
		ArrayList inParams = new ArrayList();
		inParams.add(tobaccoSystId);
		return callProcedure("g_getTobaccoInfo", inParams);
	}

	public static SqlReturn g_getTobaccoInfolevel1(String tobaccoSystId) {
		ArrayList inParams = new ArrayList();
		inParams.add(tobaccoSystId);
		return callProcedure("g_getTobaccoInfolevel1", inParams);
	}

	/**
	 * 获得要修改的竞争品牌的信息
	 *
	 * @return
	 */
	public static SqlReturn g_getCompeteTobaccoInfo(String tobaccoSystId) {
		ArrayList inParams = new ArrayList();
		inParams.add(tobaccoSystId);
		return callProcedure("g_getCompeteTobaccoInfo", inParams);
	}

	/**
	 * 获得可用卷烟厂名列表
	 *
	 * @return
	 */
	public static SqlReturn g_factoryQuery() {
		return callProcedure("g_factoryQuery");
	}

	/**
	 * 获得所有卷烟厂名列表
	 *
	 * @return
	 */
	public static SqlReturn g_allFactoryQuery() {
		return callProcedure("g_allfactoryQuery");
	}

	/**
	 * 获得省份列表
	 *
	 * @return
	 */
	public static SqlReturn g_provinceQuery() {
		return callProcedure("g_provinceQuery");
	}

	/**
	 * 获得可用卷烟厂名列表
	 *
	 * @return
	 */
	public static SqlReturn g_districtQuery() {
		return callProcedure("g_districtQuery");
	}

	/**
	 * 获得区域列表
	 *
	 * @return
	 */
	public static SqlReturn g_areaQuery() {
		return callProcedure("g_areaQuery");
	}

	/**
	 * 保存卷烟信息
	 *
	 * @return
	 */
	public static SqlReturn g_saveTobacco(String type,
			TobaccoInfoForm tobaInfoForm, String message) {
		final int length = 21;
		SqlParam[] params = createParams(length);
		// ArrayList inParams = new ArrayList();
		// params[0].setAll(0, Types.INTEGER, intAdsGoClSytID);
		params[0].setAll(0, Types.CHAR, type);
		params[1].setAll(0, Types.INTEGER, tobaInfoForm.getTobaSystId());
		params[2].setAll(0, Types.VARCHAR, tobaInfoForm.getTobaCode());
		// params[3].setAll(0,Types.VARCHAR,tobaInfoForm.getDataCentCode());
		params[3].setAll(0, Types.VARCHAR, tobaInfoForm.getTobaName());
		// params[5].setAll(0,Types.VARCHAR,"");//tobaInfoForm.getQuickCode());
		params[4].setAll(0, Types.CHAR, tobaInfoForm.getIsActive());
		// params[7].setAll(0,Types.VARCHAR,tobaInfoForm.getCrmTobaCode());
		params[5].setAll(0, Types.VARCHAR, tobaInfoForm.getgj_code());
		params[6].setAll(0, Types.VARCHAR, tobaInfoForm.getBrandCode());
		params[7].setAll(0, Types.VARCHAR, tobaInfoForm.getFactoryCode());
		params[8].setAll(0, Types.VARCHAR, tobaInfoForm.getOrderTag());
		// params[12].setAll(0,Types.CHAR,tobaInfoForm.getBpcsDatabase());
		params[9].setAll(0, Types.INTEGER, tobaInfoForm.getPriceLevelId());
		params[10].setAll(0, Types.DECIMAL, tobaInfoForm.getTradePrice());
		params[11].setAll(0, Types.DECIMAL, tobaInfoForm.getRetailPrice());
		params[12].setAll(0, Types.DECIMAL, tobaInfoForm.getincludePrice());
		params[13].setAll(0, Types.VARCHAR, tobaInfoForm.getBandcode());

		params[14].setAll(0, Types.VARCHAR, tobaInfoForm.getBanddish());
		params[15].setAll(0, Types.VARCHAR, tobaInfoForm.getStyle());
		params[16].setAll(0, Types.VARCHAR, tobaInfoForm.getSpec());

		params[17].setAll(0, Types.DECIMAL, tobaInfoForm.getContractPrice());

		params[18].setAll(1, Types.VARCHAR, message);
		params[19].setAll(1, Types.INTEGER, "0");
		params[20].setAll(1, Types.INTEGER, "0");
		// params[18].setAll(0,Types.INTEGER,tobaInfoForm.getSpec());
		// params[22].setAll(0,Types.VARCHAR,tobaInfoForm.getDwdm());
		// params[23].setAll(0,Types.INTEGER,tobaInfoForm.getSerialcode());
		// params[24].setAll(1,Types.VARCHAR,tobaInfoForm.getSpec());
		// ArrayList outParams = new ArrayList();
		// outParams.add(message);
		// return executeProcedure("A_AdsGoodsMaint", params);

		return executeProcedure("g_saveTobacco", params);
	}

	/**
	 * 保存卷烟信息
	 *
	 * @return
	 */
	public static SqlReturn g_saveTobaccolevel1(String type,
			TobaccoInfoForm tobaInfoForm, String message) {
		final int length = 21;
		SqlParam[] params = createParams(length);
		// ArrayList inParams = new ArrayList();
		// params[0].setAll(0, Types.INTEGER, intAdsGoClSytID);
		params[0].setAll(0, Types.CHAR, type);
		params[1].setAll(0, Types.INTEGER, tobaInfoForm.getTobaSystId());
		params[2].setAll(0, Types.VARCHAR, tobaInfoForm.getTobaCode());
		// params[3].setAll(0,Types.VARCHAR,tobaInfoForm.getDataCentCode());
		params[3].setAll(0, Types.VARCHAR, tobaInfoForm.getTobaName());
		// params[5].setAll(0,Types.VARCHAR,"");//tobaInfoForm.getQuickCode());
		params[4].setAll(0, Types.CHAR, tobaInfoForm.getIsActive());
		// params[7].setAll(0,Types.VARCHAR,tobaInfoForm.getCrmTobaCode());
		params[5].setAll(0, Types.VARCHAR, tobaInfoForm.getgj_code());
		params[6].setAll(0, Types.VARCHAR, tobaInfoForm.getBrandCode());
		params[7].setAll(0, Types.VARCHAR, tobaInfoForm.getFactoryCode());
		params[8].setAll(0, Types.VARCHAR, tobaInfoForm.getOrderTag());
		// params[12].setAll(0,Types.CHAR,tobaInfoForm.getBpcsDatabase());
		params[9].setAll(0, Types.INTEGER, tobaInfoForm.getPriceLevelId());
		params[10].setAll(0, Types.DECIMAL, tobaInfoForm.getTradePrice());
		params[11].setAll(0, Types.DECIMAL, tobaInfoForm.getRetailPrice());
		params[12].setAll(0, Types.DECIMAL, tobaInfoForm.getincludePrice());
		params[13].setAll(0, Types.VARCHAR, tobaInfoForm.getBandcode());

		params[14].setAll(0, Types.VARCHAR, tobaInfoForm.getBanddish());
		params[15].setAll(0, Types.VARCHAR, tobaInfoForm.getStyle());
		params[16].setAll(0, Types.VARCHAR, tobaInfoForm.getSpec());

		params[17].setAll(0, Types.DECIMAL, tobaInfoForm.getContractPrice());

		params[18].setAll(1, Types.VARCHAR, message);
		params[19].setAll(1, Types.INTEGER, "0");
		params[20].setAll(1, Types.INTEGER, "0");
		// params[18].setAll(0,Types.INTEGER,tobaInfoForm.getSpec());
		// params[22].setAll(0,Types.VARCHAR,tobaInfoForm.getDwdm());
		// params[23].setAll(0,Types.INTEGER,tobaInfoForm.getSerialcode());
		// params[24].setAll(1,Types.VARCHAR,tobaInfoForm.getSpec());
		// ArrayList outParams = new ArrayList();
		// outParams.add(message);
		// return executeProcedure("A_AdsGoodsMaint", params);

		return executeProcedure("g_saveTobaccolevel1", params);
	}

	/**
	 * 获得所有卷烟厂列表
	 *
	 * @return
	 */
	public static SqlReturn g_getAllFactories() {
		return callProcedure("g_getAllFactories");
	}

	/**
	 * 获得要修改的烟厂的信息
	 *
	 * @return
	 */
	public static SqlReturn g_getFactoryInfo(String factorySystId) {
		ArrayList inParams = new ArrayList();
		inParams.add(factorySystId);
		return callProcedure("g_getFactoryInfo", inParams);
	}

	// 去掉IsActive = '1' 得到按省份所有烟厂 2007-09-19
	public static SqlReturn G_QueryFactoryByProvince(String provinceid) {
		ArrayList inParams = new ArrayList();
		inParams.add(provinceid);
		return callProcedure("G_QueryFactoryByProvince", inParams);
	}

	public static SqlReturn G_FactoryQueryByProvince(String provinceid) {
		ArrayList inParams = new ArrayList();
		inParams.add(provinceid);
		return callProcedure("G_FactoryQueryByProvince", inParams);
	}

	/**
	 * 保存卷烟厂信息
	 *
	 * @return
	 */
	public static SqlReturn g_saveFactory(String type, String factorySystId,
			String factoryCode, String factoryName, String factoryAlias,
			String isActive, String factoryOldCode, String provSystID,
			String message) {
		ArrayList inParams = new ArrayList();
		inParams.add(type);
		inParams.add(factorySystId);
		inParams.add(factoryCode);
		inParams.add(factoryName);
		inParams.add(factoryAlias);
		inParams.add(isActive);
		inParams.add(factoryOldCode);
		inParams.add(provSystID);

		ArrayList outParams = new ArrayList();
		outParams.add(message);

		return callProcedure("g_saveFactory", inParams, outParams);
	}

	/**
	 * 获得所有商业公司列表
	 *
	 * @return
	 */
	public static SqlReturn g_getAllSycompanies(List inparams) {
		return callProcedure("g_getAllSycompanies", inparams);
	}

	/**
	 * 获得所有商业公司选择列表
	 *
	 * @return
	 */
	public static SqlReturn g_getCompaniesSingleSelect(List inparams) {
		return callProcedure("g_getAllSycompaniesSimple", inparams);
	}

	/**
	 * 获得所有商业公司选择列表
	 *
	 * @return
	 */
	public static SqlReturn g_getCompaniesSingleSelectbyduty(List inparams) {
		return callProcedure("g_getAllSycompaniesSimplebyduty", inparams);
	}

	/**
	 * 获得所有erp商业公司列表
	 *
	 * @return
	 */
	public static SqlReturn g_getAllCUSTOMERFROMERP(SycompanyInfoForm form) {

		ArrayList params = new ArrayList();
		params.add(form.getPersonName());
		return callProcedure("G_GETALLCUSTOMERFROMERP", params);
	}

	/**
	 * 获得所有有加工烟的卷烟列表
	 *
	 * @return
	 */
	public static SqlReturn g_getAllTobaccojg() {
		return callProcedure("g_getAllTobaccojg");
	}

	/**
	 * 获得要修改的商业公司的信息
	 *
	 * @return
	 */
	public static SqlReturn g_getSycompanyInfo(String sycompSystId) {
		ArrayList inParams = new ArrayList();
		inParams.add(sycompSystId);
		return callProcedure("g_getSycompanyInfo", inParams);
	}

	/**
	 * 保存商业公司信息
	 *
	 * @return
	 */
	public static SqlReturn g_saveSycompany(String type, SycompanyInfoForm form,
			String message) {
		/*
		 * ArrayList inParams = new ArrayList(); inParams.add(type);
		 * inParams.add(form.getSycompSystId());
		 * inParams.add(form.getSycompCode());
		 * inParams.add(form.getSycompName());
		 * //inParams.add(form.getDistSystId());
		 * inParams.add(form.getSycompAlias());
		 * inParams.add(form.getIsActive());
		 * //inParams.add(form.getIsProvince());
		 * inParams.add(form.getSycompGradeId());
		 * inParams.add(form.getAddress()); inParams.add(form.getCompPhone());
		 * inParams.add(form.getLinkman());
		 * inParams.add(form.getLinkmanPhone());
		 *
		 * inParams.add(form.getBankSystid());
		 * inParams.add(form.getBankAccount()); inParams.add(form.getTaxNo());
		 * inParams.add(form.getMemberNo()); inParams.add(form.getFax());
		 * inParams.add(form.getMailAddress());
		 * inParams.add(form.getArriveStation());
		 * inParams.add(form.getTelegraph()); inParams.add(form.getBpcsCode());
		 *
		 * inParams.add(form.getTransType());
		 * inParams.add(form.getBalanceType());
		 * inParams.add(form.getOrderTag());
		 * inParams.add(form.getDataCent_Code());
		 *
		 * inParams.add(form.getPostcode()); inParams.add(form.getBankName());
		 * inParams.add(form.getContractCode());
		 * inParams.add(form.getArea_Code()); inParams.add(form.getTransDays());
		 * ArrayList outParams = new ArrayList(); outParams.add(message);
		 *
		 * return callProcedure("g_saveSycompany", inParams, outParams);
		 */
		// xianet 2006-02-27 修改，为了去掉数据转换错误
		SqlParam[] params = createParams(33);// 放出是否省内byhgc-->31->32
		int i = 0;
		params[i++].setAll(0, Types.CHAR, type);// 0
		params[i++].setAll(0, Types.INTEGER, form.getSycompSystId());
		params[i++].setAll(0, Types.VARCHAR, form.getSycompCode());
		params[i++].setAll(0, Types.VARCHAR, form.getSycompName());
		params[i++].setAll(0, Types.VARCHAR, form.getSycompAlias());
		params[i++].setAll(0, Types.CHAR, form.getIsActive());// 5
		params[i++].setAll(0, Types.CHAR, form.getIsProvince());// +byhgc20060626
		params[i++].setAll(0, Types.INTEGER, form.getSycompGradeId());
		params[i++].setAll(0, Types.VARCHAR, form.getAddress());
		params[i++].setAll(0, Types.VARCHAR, form.getCompPhone());
		params[i++].setAll(0, Types.VARCHAR, form.getLinkman()); // 10
		params[i++].setAll(0, Types.VARCHAR, form.getLinkmanPhone());
		params[i++].setAll(0, Types.INTEGER, form.getBankSystid());
		params[i++].setAll(0, Types.VARCHAR, form.getBankAccount());
		params[i++].setAll(0, Types.VARCHAR, form.getTaxNo());
		params[i++].setAll(0, Types.VARCHAR, form.getMemberNo()); // 15
		params[i++].setAll(0, Types.VARCHAR, form.getFax());
		params[i++].setAll(0, Types.VARCHAR, form.getMailAddress());
		params[i++].setAll(0, Types.VARCHAR, form.getArriveStation());
		params[i++].setAll(0, Types.VARCHAR, form.getTelegraph());
		params[i++].setAll(0, Types.VARCHAR, form.getBpcsCode()); // 20
		params[i++].setAll(0, Types.INTEGER, form.getTransType());
		params[i++].setAll(0, Types.INTEGER, form.getBalanceType());
		params[i++].setAll(0, Types.VARCHAR, form.getOrderTag());
		params[i++].setAll(0, Types.INTEGER, form.getDataCent_Code());
		params[i++].setAll(0, Types.VARCHAR, form.getPostcode());// 25
		params[i++].setAll(0, Types.VARCHAR, form.getBankName());
		params[i++].setAll(0, Types.VARCHAR, form.getContractCode());
		params[i++].setAll(0, Types.VARCHAR, form.getArea_Code());
		params[i++].setAll(0, Types.INTEGER, form.getTransDays());
		params[i++].setAll(0, Types.VARCHAR, form.getsycompany_Code());// 30
		// params[i++].setAll(0, Types.VARCHAR, form.getCserpcode());
		params[i++].setAll(0, Types.VARCHAR, form.getinkeycity());
		params[i++].setAll(1, Types.VARCHAR, message);
		return executeProcedure("g_saveSycompany", params);

	}

	/**
	 * erp商业公司内码对照
	 *
	 * @return
	 */
	public static SqlReturn g_impSycomperpcode(SycompanyInfoForm form,
			String message) {
		SqlParam[] params = createParams(3);
		int i = 0;
		params[i++].setAll(0, Types.INTEGER, form.getSycompSystId());
		params[i++].setAll(0, Types.VARCHAR, form.getSycompCode());
		params[i++].setAll(1, Types.VARCHAR, message);
		return executeProcedure("G_impSycomperpcode", params);

	}

	/**
	 * 获得所有价格档次列表
	 *
	 * @return
	 */
	public static SqlReturn g_getAllPriceLevels() {
		return callProcedure("g_getAllPriceLevels");
	}

	/**
	 * 获得要修改的价格档次的信息
	 *
	 * @return
	 */
	public static SqlReturn g_getPriceLevelInfo(String priceLevelId) {
		ArrayList inParams = new ArrayList();
		inParams.add(priceLevelId);
		return callProcedure("g_getPriceLevelInfo", inParams);
	}

	/**
	 * 保存价格档次信息
	 *
	 * @return
	 */
	public static SqlReturn g_savePriceLevel(String type, String priceLevelId,
			String priceLevelName, String priceLevelCode, String lowLimitPrice,
			String topLimitPrice, String isActive, String message) {
		ArrayList inParams = new ArrayList();
		inParams.add(type);
		inParams.add(priceLevelId);
		inParams.add(priceLevelName);
		inParams.add(priceLevelCode);
		inParams.add(lowLimitPrice);
		inParams.add(topLimitPrice);
		inParams.add(isActive);

		ArrayList outParams = new ArrayList();
		outParams.add(message);

		return callProcedure("g_savePriceLevel", inParams, outParams);
	}

	/**
	 *
	 * @param strQuickCode
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn GP_SelectAdsGoods(String strQuickCode) {

		ArrayList inParams = new ArrayList();
		inParams.add(strQuickCode);
		return callProcedure("GP_SelectAdsGoods", inParams);
	}

	/**
	 *
	 * @param intStationTypeID
	 *            String
	 * @param intStationID
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn G_StationTreeQuery(String intStationTypeID,
			String intStationID) {

		ArrayList inParams = new ArrayList();
		inParams.add(intStationTypeID);
		inParams.add(intStationID);

		return callProcedure("G_StationTreeQuery", inParams);
	}

	/**
	 *
	 * @param intStationID
	 *            String
	 * @param strErrorMsg
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn G_StationPersonTreeQuery(String intStationID,
			String strErrorMsg) {

		ArrayList inParams = new ArrayList();
		inParams.add(intStationID);

		ArrayList outParams = new ArrayList();
		outParams.add(strErrorMsg);

		return callProcedure("G_StationPersonTreeQuery", inParams, outParams);
	}

	/**
	 *
	 * @param intStationID
	 *            String
	 * @param strPersonString
	 *            String
	 * @param strErrorMsg
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn G_StationPersonSave(String intStationID,
			String strPersonString, String strErrorMsg) {

		ArrayList inParams = new ArrayList();
		inParams.add(intStationID);
		inParams.add(strPersonString);

		ArrayList outParams = new ArrayList();
		outParams.add(strErrorMsg);

		return callProcedure("G_StationPersonSave", inParams, outParams);
	}

	/**
	 *
	 * @param year
	 *            String
	 * @param feetotal
	 *            String
	 * @param district
	 *            String
	 * @param feeplan
	 *            String
	 * @param comment
	 *            String
	 * @param type
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_FeePlan(String year, String feetotal,
			String district, String feeplan, String comment, String type) {

		ArrayList inParams = new ArrayList();
		inParams.add(year);
		inParams.add(feetotal);
		inParams.add(district);
		inParams.add(feeplan);
		inParams.add(comment);
		inParams.add(type);

		return callProcedure("P_ProFeePlan", inParams);
	}

	/**
	 *
	 * @param year
	 *            String
	 * @param strDistIDRelate
	 *            String
	 * @param strDistNameRelate
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_FeePlanUp(String year, String strDistIDRelate,
			String strDistNameRelate) {

		ArrayList inParams = new ArrayList();
		inParams.add(year);
		inParams.add(strDistIDRelate);
		inParams.add(strDistNameRelate);

		return callProcedure("P_FeePlanInit", inParams);
	}

	/**
	 *
	 * @param userid
	 *            String
	 * @param feeplaninfo
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_FeePlanSave(String userid, String feeplaninfo,
			String strMessage) {

		ArrayList inParams = new ArrayList();
		inParams.add(userid);
		inParams.add(feeplaninfo);

		ArrayList outParams = new ArrayList();
		outParams.add(strMessage);

		return callProcedure("P_FeePlanSave", inParams, outParams);
	}

	/**
	 *
	 * @param year1
	 *            String
	 * @param year2
	 *            String
	 * @param strDistRelate
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_FeePlanQuery(String year1, String year2,
			String strDistRelate) {
		ArrayList inParams = new ArrayList();
		inParams.add(year1);
		inParams.add(year2);
		inParams.add(strDistRelate);
		return callProcedure("P_FeePlanQuery", inParams);
	}

	/**
	 *
	 * @param year1
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_FeePlanCheck(String year1) {

		ArrayList inParams = new ArrayList();
		inParams.add(year1);

		return callProcedure("P_FeeCheck", inParams);
	}

	/**
	 *
	 * @param type
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_FeeDistSelect(String type) {

		ArrayList inParams = new ArrayList();
		inParams.add(type);

		return callProcedure("P_FeeDistSelect", inParams);
	}

	/**
	 *
	 * @param year1
	 *            String
	 * @param year2
	 *            String
	 * @param type
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_Feetotal(String year1, String year2, String type) {

		ArrayList inParams = new ArrayList();
		inParams.add(year1);
		inParams.add(year2);
		inParams.add(type);

		return callProcedure("P_FeeTotal", inParams);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param strProjectCode
	 *            String
	 * @param strProjectName
	 *            String
	 * @param strPjBeginDate
	 *            String
	 * @param strPjEndDate
	 *            String
	 * @param intProjectType
	 *            String
	 * @param strPjCityName
	 *            String
	 * @param intPjPersonNum
	 *            String
	 * @param strMarketStatus
	 *            String
	 * @param strProjectPurpose
	 *            String
	 * @param strExpectGoal
	 *            String
	 * @param strProjectInstrument
	 *            String
	 * @param strProjectContent
	 *            String
	 * @param strPjCigaretteInfo
	 *            String
	 * @param strPjLocalInfo
	 *            String
	 * @param strPjFeeBudgetInfo
	 *            String
	 * @param type
	 *            String
	 * @param totalcharge
	 *            String
	 * @param <any>
	 *            unknown
	 * @return SqlReturn
	 */
	public static SqlReturn P_ProjectAdd(String intPersonID, String intDepartID,
			String strProjectCode, String strProjectName,
			String strPjBeginDate, String strPjEndDate, String intProjectType,
			String strPjCityName, String intPjPersonNum,
			String strMarketStatus, String strProjectPurpose,
			String strExpectGoal, String strProjectInstrument,
			String strProjectContent, String strPjCigaretteInfo,
			String strPjLocalInfo, String strPjFeeBudgetInfo, String type,
			String totalcharge, String strMessage) {

		ArrayList inParams = new ArrayList();
		inParams.add(intPersonID);
		inParams.add(intDepartID);
		inParams.add(strProjectCode);
		inParams.add(strProjectName);
		inParams.add(strPjBeginDate);
		inParams.add(strPjEndDate);
		inParams.add(intProjectType);
		inParams.add(strPjCityName);
		inParams.add(intPjPersonNum);
		inParams.add(strMarketStatus);
		inParams.add(strProjectPurpose);
		inParams.add(strExpectGoal);
		inParams.add(strProjectInstrument);
		inParams.add(strProjectContent);
		inParams.add(strPjCigaretteInfo);
		inParams.add(strPjLocalInfo);
		inParams.add(strPjFeeBudgetInfo);
		inParams.add(type);
		inParams.add(totalcharge);

		ArrayList outParams = new ArrayList();
		outParams.add(strMessage);

		return callProcedure("P_ProjectAdd", inParams, outParams);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param strProjectCode
	 *            String
	 * @param strProjectName
	 *            String
	 * @param strInputBeginDate
	 *            String
	 * @param strInputEndDate
	 *            String
	 * @param intProjectType
	 *            String
	 * @param strPjCityName
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_ProjectQuery(String intPersonID,
			String intDepartID, String strProjectCode, String strProjectName,
			String strInputBeginDate, String strInputEndDate,
			String intProjectType, String strPjCityName, String strMessage) {

		ArrayList inParams = new ArrayList();
		inParams.add(intPersonID);
		inParams.add(intDepartID);
		inParams.add(strProjectCode);
		inParams.add(strProjectName);
		inParams.add(strInputBeginDate);
		inParams.add(strInputEndDate);
		inParams.add(intProjectType);
		inParams.add(strPjCityName);

		ArrayList outParams = new ArrayList();
		outParams.add(strMessage);

		return callProcedure("P_ProjectQuery", inParams, outParams);
	}

	/**
	 *
	 * @return SqlReturn
	 */
	public static SqlReturn G_DepartQuery() {

		return callProcedure("G_DepartQuery");
	}

	/**
	 *
	 * @param intAdsGoClSytID
	 *            String
	 * @param intAdsGoodsID
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_AdsGoodsQuery(String intAdsGoClSytID,
			String intAdsGoodsID) {
		final int length = 2;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.INTEGER, intAdsGoClSytID);
		params[1].setAll(0, Types.INTEGER, intAdsGoodsID);
		return executeProcedure("A_AdsGoodsQuery", params);
	}

	/**
	 *
	 * @param intMaintType
	 *            String
	 * @param intAdsGoClSytID
	 *            String
	 * @param intAdsGoodsID
	 *            String
	 * @param strAdsGoodsName
	 *            String
	 * @param strAdsGoodsPrice
	 *            String
	 * @param strAdsGoodsUnit
	 *            String
	 * @param strQuickName
	 *            String
	 * @param strMemo
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_AdsGoodsMaint(String intMaintType,
			String intAdsGoClSytID, String intAdsGoodsID,
			String strAdsGoodsName, String strAdsGoodsPrice,
			String strAdsGoodsUnit, String strQuickName, String strMemo,
			String strMessage) {
		final int length = 9;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.INTEGER, intMaintType);
		params[1].setAll(0, Types.INTEGER, intAdsGoClSytID);
		params[2].setAll(0, Types.INTEGER, intAdsGoodsID);
		params[3].setAll(0, Types.CHAR, strAdsGoodsName);
		params[4].setAll(0, Types.CHAR, strAdsGoodsPrice);
		params[5].setAll(0, Types.CHAR, strAdsGoodsUnit);
		params[6].setAll(0, Types.CHAR, strQuickName);
		params[7].setAll(0, Types.VARCHAR, strMemo);
		params[8].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("A_AdsGoodsMaint", params);

	}

	/**
	 *
	 * @param intAdsCompID
	 *            String
	 * @param strAdsCompQuickName
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_AdsCompQuery(String intAdsCompID,
			String strAdsCompQuickName) {

		ArrayList inParams = new ArrayList();
		inParams.add(intAdsCompID);
		inParams.add(strAdsCompQuickName);

		return callProcedure("A_AdsCompQuery", inParams);
	}

	/**
	 *
	 * @param intMaintType
	 *            String
	 * @param intAdsCompID
	 *            String
	 * @param strAdsCompName
	 *            String
	 * @param strAdsCompQuickName
	 *            String
	 * @param strAdsCompPrincipal
	 *            String
	 * @param strAdsCompAccount
	 *            String
	 * @param strAdsCompCharacter
	 *            String
	 * @param strAdsCompPhone
	 *            String
	 * @param strAdsCompBank
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_AdsCompMaint(String intMaintType,
			String intAdsCompID, String strAdsCompName,
			String strAdsCompQuickName, String strAdsCompPrincipal,
			String strAdsCompAccount, String strAdsCompCharacter,
			String strAdsCompPhone, String strAdsCompBank, String strMessage) {

		ArrayList inParams = new ArrayList();
		inParams.add(intMaintType);
		inParams.add(intAdsCompID);
		inParams.add(strAdsCompName);
		inParams.add(strAdsCompQuickName);
		inParams.add(strAdsCompPrincipal);
		inParams.add(strAdsCompAccount);
		inParams.add(strAdsCompCharacter);
		inParams.add(strAdsCompPhone);
		inParams.add(strAdsCompBank);

		ArrayList outParams = new ArrayList();
		outParams.add(strMessage);

		return callProcedure("A_AdsCompMaint", inParams, outParams);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intProjectID
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_ProjectDetailInfoGet(String intPersonID,
			String intProjectID, String strMessage) {

		ArrayList inParams = new ArrayList();
		inParams.add(intPersonID);
		inParams.add(intProjectID);

		ArrayList outParams = new ArrayList();
		outParams.add(strMessage);

		return callProcedure("P_ProjectDetailInfoGet", inParams, outParams);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_ProjectAuditQuery(String intPersonID,
			String intDepartID, String strMessage) {

		ArrayList inParams = new ArrayList();
		inParams.add(intPersonID);
		inParams.add(intDepartID);

		ArrayList outParams = new ArrayList();
		outParams.add(strMessage);

		return callProcedure("P_ProjectAuditQuery", inParams, outParams);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param intProjectID
	 *            String
	 * @param strProjectCode
	 *            String
	 * @param strProjectName
	 *            String
	 * @param strPjBeginDate
	 *            String
	 * @param strPjEndDate
	 *            String
	 * @param intProjectType
	 *            String
	 * @param strPjCityName
	 *            String
	 * @param intPjPersonNum
	 *            String
	 * @param strMarketStatus
	 *            String
	 * @param strProjectPurpose
	 *            String
	 * @param strExpectGoal
	 *            String
	 * @param strProjectInstrument
	 *            String
	 * @param strProjectContent
	 *            String
	 * @param strPjCigaretteInfo
	 *            String
	 * @param strPjLocalInfo
	 *            String
	 * @param strPjFeeBudgetInfo
	 *            String
	 * @param intIsReport
	 *            String
	 * @param <any>
	 *            unknown
	 * @return SqlReturn
	 */
	public static SqlReturn P_ProjectModify(String intPersonID,
			String intDepartID, String intProjectID, String strProjectCode,
			String strProjectName, String strPjBeginDate, String strPjEndDate,
			String intProjectType, String strPjCityName, String intPjPersonNum,
			String strMarketStatus, String strProjectPurpose,
			String strExpectGoal, String strProjectInstrument,
			String strProjectContent, String strPjCigaretteInfo,
			String strPjLocalInfo, String strPjFeeBudgetInfo,
			String intIsReport, String totalcharge, String strMessage) {

		ArrayList inParams = new ArrayList();
		inParams.add(intPersonID);
		inParams.add(intDepartID);
		inParams.add(intProjectID);
		inParams.add(strProjectCode);
		inParams.add(strProjectName);
		inParams.add(strPjBeginDate);
		inParams.add(strPjEndDate);
		inParams.add(intProjectType);
		inParams.add(strPjCityName);
		inParams.add(intPjPersonNum);
		inParams.add(strMarketStatus);
		inParams.add(strProjectPurpose);
		inParams.add(strExpectGoal);
		inParams.add(strProjectInstrument);
		inParams.add(strProjectContent);
		inParams.add(strPjCigaretteInfo);
		inParams.add(strPjLocalInfo);
		inParams.add(strPjFeeBudgetInfo);
		inParams.add(intIsReport);
		inParams.add(totalcharge);

		ArrayList outParams = new ArrayList();
		outParams.add(strMessage);

		return callProcedure("P_ProjectModify", inParams, outParams);
	}

	/**
	 *
	 * @param strQuickCode
	 *            String
	 * @param StorageID
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_SelectAdsGoods(String strQuickCode,
			String StorageID) {

		SqlParam[] params = createParams(2);
		params[0].setAll(0, Types.CHAR, strQuickCode);
		params[1].setAll(0, Types.CHAR, StorageID);
		return executeProcedure("A_SelectAdsGoods", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param intProjectID
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_ProjectFlowQuery(String intPersonID,
			String intDepartID, String intProjectID, String strMessage) {

		SqlParam[] params = createParams(4);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(0, Types.INTEGER, intProjectID);
		params[3].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("P_ProjectFlowQuery", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param intProjectID
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_ProjectUp(String intPersonID, String intDepartID,
			String intProjectID, String strMessage) {

		SqlParam[] params = createParams(4);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(0, Types.INTEGER, intProjectID);
		params[3].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("P_ProjectUp", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param intProjectID
	 *            String
	 * @param intStationID
	 *            String
	 * @param intAuditIdea
	 *            String
	 * @param strComment
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_ProjectInfoAudit(String intPersonID,
			String intDepartID, String intProjectID, String intStationID,
			String intAuditIdea, String strComment, String strMessage) {

		SqlParam[] params = createParams(7);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(0, Types.INTEGER, intProjectID);
		params[3].setAll(0, Types.INTEGER, intStationID);
		params[4].setAll(0, Types.INTEGER, intAuditIdea);
		params[5].setAll(0, Types.VARCHAR, strComment);
		params[6].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("P_ProjectInfoAudit", params);
	}

	/**
	 *
	 * @param strQuickName
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_SelectAdsCompName(String strQuickName) {

		String procName = "A_SelectAdsCompName";

		SqlParam[] params = createParams(1);
		params[0].setAll(0, Types.CHAR, strQuickName);
		return executeProcedure(procName, params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param strBeginDate
	 *            String
	 * @param strEndDate
	 *            String
	 * @param intProjectID
	 *            String
	 * @param strProjectCode
	 *            String
	 * @param strProjectName
	 *            String
	 * @param intEvaluateTerm
	 *            String
	 * @param intEvaluateType
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_ProjectEvaluateQuery(String intPersonID,
			String strBeginDate, String strEndDate, String intProjectID,
			String strProjectCode, String strProjectName,
			String intEvaluateTerm, String intEvaluateType, String strMessage) {

		SqlParam[] params = createParams(9);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.CHAR, strBeginDate);
		params[2].setAll(0, Types.CHAR, strEndDate);
		params[3].setAll(0, Types.INTEGER, intProjectID);
		params[4].setAll(0, Types.CHAR, strProjectCode);
		params[5].setAll(0, Types.VARCHAR, strProjectName);
		params[6].setAll(0, Types.INTEGER, intEvaluateTerm);
		params[7].setAll(0, Types.INTEGER, intEvaluateType);
		params[8].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("P_ProjectEvaluateQuery", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intPjEvaluateID
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_ProjectEvaluateDetailInfoGet(String intPersonID,
			String intPjEvaluateID, String strMessage) {

		SqlParam[] params = createParams(3);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intPjEvaluateID);
		params[2].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("P_ProjectEvaluateDetailInfoGet", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param intProjectID
	 *            String
	 * @param intEvaluateTerm
	 *            String
	 * @param intEvaluateType
	 *            String
	 * @param strContent
	 *            String
	 * @param strComment
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_ProjectEvaluateAdd(String intPersonID,
			String intDepartID, String intProjectID, String intEvaluateTerm,
			String intEvaluateType, String strContent, String strComment,
			String strMessage) {

		SqlParam[] params = createParams(8);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(0, Types.INTEGER, intProjectID);
		params[3].setAll(0, Types.INTEGER, intEvaluateTerm);
		params[4].setAll(0, Types.INTEGER, intEvaluateType);
		params[5].setAll(0, Types.VARCHAR, strContent);
		params[6].setAll(0, Types.VARCHAR, strComment);
		params[7].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("P_ProjectEvaluateAdd", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param intPjEvaluateID
	 *            String
	 * @param intProjectID
	 *            String
	 * @param intEvaluateTerm
	 *            String
	 * @param intEvaluateType
	 *            String
	 * @param strContent
	 *            String
	 * @param strComment
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_ProjectEvaluateModify(String intPersonID,
			String intDepartID, String intPjEvaluateID, String intProjectID,
			String intEvaluateTerm, String intEvaluateType, String strContent,
			String strComment, String strMessage) {

		SqlParam[] params = createParams(9);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(0, Types.INTEGER, intPjEvaluateID);
		params[3].setAll(0, Types.INTEGER, intProjectID);
		params[4].setAll(0, Types.INTEGER, intEvaluateTerm);
		params[5].setAll(0, Types.INTEGER, intEvaluateType);
		params[6].setAll(0, Types.VARCHAR, strContent);
		params[7].setAll(0, Types.VARCHAR, strComment);
		params[8].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("P_ProjectEvaluateModify", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param intPjEvaluateID
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn P_ProjectEvaluateDelete(String intPersonID,
			String intDepartID, String intPjEvaluateID, String strMessage) {

		SqlParam[] params = createParams(4);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(0, Types.INTEGER, intPjEvaluateID);
		params[3].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("P_ProjectEvaluateDelete", params);
	}

	public static SqlReturn A_Adsindentquery(String type, String strBeginDate,
			String strEndDate, String provincecode, String indentcomp,
			String goodsname) {

		SqlParam[] params = createParams(6);
		params[0].setAll(0, Types.CHAR, type);
		params[1].setAll(0, Types.CHAR, strBeginDate);
		params[2].setAll(0, Types.CHAR, strEndDate);
		params[3].setAll(0, Types.VARCHAR, provincecode);
		params[4].setAll(0, Types.VARCHAR, indentcomp);
		params[5].setAll(0, Types.VARCHAR, goodsname);
		return executeProcedure("A_AdsGoodsIndentQuery", params);
	}

	public static SqlReturn GP_SelectIndentComp(String provincecode) {

		SqlParam[] params = createParams(1);
		params[0].setAll(0, Types.CHAR, provincecode);

		return executeProcedure("GP_SelectIndentComp", params);

	}

	public static SqlReturn G_SelectProvince() {

		return callProcedure("G_SelectProvince");
	}

	/**
	 *
	 * @param strSYCompCode
	 *            String
	 * @param strProvinceCode
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_SelectSYCompName(String strSYCompCode,
			String strProvinceCode) {

		SqlParam[] params = createParams(2);
		params[0].setAll(0, Types.VARCHAR, strSYCompCode);
		params[1].setAll(0, Types.VARCHAR, strProvinceCode);
		return executeProcedure("A_SelectSYCompName", params);
	}

	/**
	 *
	 * @param goodsname
	 *            String
	 * @param store
	 *            String
	 * @param type
	 *            String
	 * @param amount
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_AdsSearchQuery(String goodsname, String store,
			String type, String amount) {

		SqlParam[] params = createParams(4);
		params[0].setAll(0, Types.VARCHAR, goodsname);
		params[1].setAll(0, Types.VARCHAR, store);
		params[2].setAll(0, Types.VARCHAR, type);
		params[3].setAll(0, Types.VARCHAR, amount);
		return executeProcedure("A_AdsSearchQuery", params);
	}

	/**
	 * @return SqlReturn
	 */
	public static SqlReturn A_SelectStorage() {

		return callProcedure("A_SelectStorage");
	}

	public static SqlReturn A_SelectStore() {

		return callProcedure("A_SelectStorage");
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intProductModel
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn G_TobaccoTreeQuery(String intPersonID,
			String intProductModel) {

		SqlParam[] params = createParams(2);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intProductModel);
		return executeProcedure("G_TobaccoTreeQuery", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param strCtCode
	 *            String
	 * @param strCtName
	 *            String
	 * @param intCtType
	 *            String
	 * @param intMediaID
	 *            String
	 * @param intCtPartnerID
	 *            String
	 * @param strCtCharges
	 *            String
	 * @param intPayCount
	 *            String
	 * @param intPayType
	 *            String
	 * @param strCtDate
	 *            String
	 * @param strExecStartDate
	 *            String
	 * @param strExecEndDate
	 *            String
	 * @param strCtBody
	 *            String
	 * @param strMemo
	 *            String
	 * @param strCtTobaRelate
	 *            String
	 * @param strCtPjRelate
	 *            String
	 * @param strCtMediaDetail
	 *            String
	 * @param strCtAdsGoodsDetail
	 *            String
	 * @param <any>
	 *            unknown
	 * @return SqlReturn
	 */
	public static SqlReturn C_ContractAdd(String intPersonID,
			String intDepartID, String strCtCode, String strCtName,
			String intCtType, String intMediaID, String intCtPartnerID,
			String strCtCharges, String intPayCount, String intPayType,
			String strCtDate, String strExecStartDate, String strExecEndDate,
			String strCtBody, String strMemo, String strCtTobaRelate,
			String strCtPjRelate, String strCtMediaDetail,
			String strCtAdsGoodsDetail, String strCtPrearrange, String type,
			String strMessage) {

		SqlParam[] params = createParams(22);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(0, Types.CHAR, strCtCode);
		params[3].setAll(0, Types.VARCHAR, strCtName);
		params[4].setAll(0, Types.INTEGER, intCtType);
		params[5].setAll(0, Types.INTEGER, intMediaID);
		params[6].setAll(0, Types.INTEGER, intCtPartnerID);
		params[7].setAll(0, Types.VARCHAR, strCtCharges);
		params[8].setAll(0, Types.INTEGER, intPayCount);
		params[9].setAll(0, Types.INTEGER, intPayType);
		params[10].setAll(0, Types.CHAR, strCtDate);
		params[11].setAll(0, Types.CHAR, strExecStartDate);
		params[12].setAll(0, Types.CHAR, strExecEndDate);
		params[13].setAll(0, Types.VARCHAR, strCtBody);
		params[14].setAll(0, Types.VARCHAR, strMemo);
		params[15].setAll(0, Types.VARCHAR, strCtTobaRelate);
		params[16].setAll(0, Types.VARCHAR, strCtPjRelate);
		params[17].setAll(0, Types.VARCHAR, strCtMediaDetail);
		params[18].setAll(0, Types.VARCHAR, strCtAdsGoodsDetail);
		params[19].setAll(0, Types.VARCHAR, strCtPrearrange);
		params[20].setAll(0, Types.VARCHAR, type);
		params[21].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("C_ContractAdd", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_ContractAuditQuery(String intPersonID,
			String intDepartID, String strMessage) {

		SqlParam[] params = createParams(3);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("C_ContractAuditQuery", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param strContractCode
	 *            String
	 * @param strContractName
	 *            String
	 * @param intContractType
	 *            String
	 * @param intContractStatus
	 *            String
	 * @param strPartnerName
	 *            String
	 * @param strInputBeginDate
	 *            String
	 * @param strInputEndDate
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_ContractBaseQuery(String intPersonID,
			String intDepartID, String strContractCode, String strContractName,
			String intContractType, String intContractStatus,
			String strPartnerName, String strInputBeginDate,
			String strInputEndDate, String strMessage) {

		SqlParam[] params = createParams(10);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(0, Types.CHAR, strContractCode);
		params[3].setAll(0, Types.VARCHAR, strContractName);
		params[4].setAll(0, Types.INTEGER, intContractType);
		params[5].setAll(0, Types.INTEGER, intContractStatus);
		params[6].setAll(0, Types.VARCHAR, strPartnerName);
		params[7].setAll(0, Types.CHAR, strInputBeginDate);
		params[8].setAll(0, Types.CHAR, strInputEndDate);
		params[9].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("C_ContractBaseQuery", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intCtSystID
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_ContractDetailInfoGet(String intPersonID,
			String intCtSystID, String strMessage) {

		SqlParam[] params = createParams(3);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intCtSystID);
		params[2].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("C_ContractDetailInfoGet", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param intCtSytID
	 *            String
	 * @param strCtCode
	 *            String
	 * @param strCtName
	 *            String
	 * @param intCtType
	 *            String
	 * @param intMediaID
	 *            String
	 * @param intCtPartnerID
	 *            String
	 * @param strCtCharges
	 *            String
	 * @param intPayCount
	 *            String
	 * @param intPayType
	 *            String
	 * @param strCtDate
	 *            String
	 * @param strExecStartDate
	 *            String
	 * @param strExecEndDate
	 *            String
	 * @param strCtBody
	 *            String
	 * @param strMemo
	 *            String
	 * @param strCtTobaRelate
	 *            String
	 * @param strCtPjRelate
	 *            String
	 * @param strCtMediaDetail
	 *            String
	 * @param <any>
	 *            unknown
	 * @return SqlReturn
	 */
	public static SqlReturn C_ContractModify(String intPersonID,
			String intDepartID, String intCtSytID, String strCtCode,
			String strCtName, String intCtType, String intMediaID,
			String intCtPartnerID, String strCtCharges, String intPayCount,
			String intPayType, String strCtDate, String strExecStartDate,
			String strExecEndDate, String strCtBody, String strMemo,
			String strCtTobaRelate, String strCtPjRelate,
			String strCtMediaDetail, String strCtAdsGoodsDetail,
			String strCtPrearrange, String intIsReport, String strMessage) {

		SqlParam[] params = createParams(23);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(0, Types.INTEGER, intCtSytID);
		params[3].setAll(0, Types.CHAR, strCtCode);
		params[4].setAll(0, Types.VARCHAR, strCtName);
		params[5].setAll(0, Types.INTEGER, intCtType);
		params[6].setAll(0, Types.INTEGER, intMediaID);
		params[7].setAll(0, Types.INTEGER, intCtPartnerID);
		params[8].setAll(0, Types.VARCHAR, strCtCharges);
		params[9].setAll(0, Types.INTEGER, intPayCount);
		params[10].setAll(0, Types.INTEGER, intPayType);
		params[11].setAll(0, Types.CHAR, strCtDate);
		params[12].setAll(0, Types.CHAR, strExecStartDate);
		params[13].setAll(0, Types.CHAR, strExecEndDate);
		params[14].setAll(0, Types.VARCHAR, strCtBody);
		params[15].setAll(0, Types.VARCHAR, strMemo);
		params[16].setAll(0, Types.VARCHAR, strCtTobaRelate);
		params[17].setAll(0, Types.VARCHAR, strCtPjRelate);
		params[18].setAll(0, Types.VARCHAR, strCtMediaDetail);
		params[19].setAll(0, Types.VARCHAR, strCtAdsGoodsDetail);
		params[20].setAll(0, Types.VARCHAR, strCtPrearrange);
		params[21].setAll(0, Types.INTEGER, intIsReport);
		params[22].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("C_ContractModify", params);
	}

	/**
	 * @param SystemID
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn G_SystemUsingTobaGet(String SystemID) {

		SqlParam[] params = createParams(1);
		params[0] = new SqlParam();
		params[0].setAll(0, Types.VARCHAR, SystemID);
		return executeProcedure("G_SystemUsingTobaGet", params);
	}

	/**
	 *
	 * @param companyId
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn G_CompanyUsingTobaGet(String companyId) {

		SqlParam[] params = createParams(1);
		params[0].setAll(0, Types.INTEGER, companyId);
		return executeProcedure("G_CompanyUsingTobaGet", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param intSystemID
	 *            String
	 * @param strTobaSystIDString
	 *            String
	 * @param strErrorMsg
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn G_SystemUsingTobaSave(String intPersonID,
			String intDepartID, String intSystemID, String strTobaSystIDString,
			String strErrorMsg) {

		SqlParam[] params = createParams(5);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(0, Types.INTEGER, intSystemID);
		params[3].setAll(0, Types.VARCHAR, strTobaSystIDString);
		params[4].setAll(1, Types.VARCHAR, strErrorMsg);
		return executeProcedure("G_SystemUsingTobaSave", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intCompanyID
	 *            String
	 * @param strTobaSystIDString
	 *            String
	 * @param strErrorMsg
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn G_CompanyUsingTobaSave(String intPersonID,
			String intCompanyID, String strTobaSystIDString, String strErrorMsg) {

		SqlParam[] params = createParams(4);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intCompanyID);
		params[2].setAll(0, Types.VARCHAR, strTobaSystIDString);
		params[3].setAll(1, Types.VARCHAR, strErrorMsg);
		return executeProcedure("G_CompanyUsingTobaSave", params);
	}

	/**
	 *
	 * @return SqlReturn
	 */
	public static SqlReturn A_AdsExamineQuery() {

		return callProcedure("A_AdsExamineQuery");
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param strCtCode
	 *            String
	 * @param strCtName
	 *            String
	 * @param intCtType
	 *            String
	 * @param intCtPartnerID
	 *            String
	 * @param strCtCharges
	 *            String
	 * @param strCtSignDate
	 *            String
	 * @param strCtValidDate
	 *            String
	 * @param strProvideSample
	 *            String
	 * @param strCheckInterval
	 *            String
	 * @param strMemo
	 *            String
	 * @param strCtTobaRelate
	 *            String
	 * @param strCtPjRelate
	 *            String
	 * @param strCtMediaDetail
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_MediaContractAdd(String intPersonID,
			String intDepartID, String strCtCode, String strCtName,
			String intCtType, String intCtPartnerID, String strCtCharges,
			String strCtSignDate, String strCtValidDate,
			String strProvideSample, String strCheckInterval, String strMemo,
			String strCtTobaRelate, String strCtPjRelate,
			String strCtMediaDetail, String strMessage) {

		SqlParam[] params = createParams(16);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(0, Types.CHAR, strCtCode);
		params[3].setAll(0, Types.VARCHAR, strCtName);
		params[4].setAll(0, Types.INTEGER, intCtType);
		params[5].setAll(0, Types.INTEGER, intCtPartnerID);
		params[6].setAll(0, Types.VARCHAR, strCtCharges);
		params[7].setAll(0, Types.CHAR, strCtSignDate);
		params[8].setAll(0, Types.CHAR, strCtValidDate);
		params[9].setAll(0, Types.VARCHAR, strProvideSample);
		params[10].setAll(0, Types.VARCHAR, strCheckInterval);
		params[11].setAll(0, Types.VARCHAR, strMemo);
		params[12].setAll(0, Types.VARCHAR, strCtTobaRelate);
		params[13].setAll(0, Types.VARCHAR, strCtPjRelate);
		params[14].setAll(0, Types.VARCHAR, strCtMediaDetail);
		params[15].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("C_MediaContractAdd", params);

	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intCtSystID
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_MediaContractDetailInfoGet(String intPersonID,
			String intCtSystID, String strMessage) {

		SqlParam[] params = createParams(3);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intCtSystID);
		params[2].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("C_MediaContractDetailInfoGet", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param intCtSytID
	 *            String
	 * @param strCtCode
	 *            String
	 * @param strCtName
	 *            String
	 * @param intCtType
	 *            String
	 * @param intCtPartnerID
	 *            String
	 * @param strCtCharges
	 *            String
	 * @param strCtSignDate
	 *            String
	 * @param strCtValidDate
	 *            String
	 * @param strProvideSample
	 *            String
	 * @param strCheckInterval
	 *            String
	 * @param strMemo
	 *            String
	 * @param strCtTobaRelate
	 *            String
	 * @param strCtPjRelate
	 *            String
	 * @param strCtMediaDetail
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_MediaContractModify(String intPersonID,
			String intDepartID, String intCtSytID, String strCtCode,
			String strCtName, String intCtType, String intCtPartnerID,
			String strCtCharges, String strCtSignDate, String strCtValidDate,
			String strProvideSample, String strCheckInterval, String strMemo,
			String strCtTobaRelate, String strCtPjRelate,
			String strCtMediaDetail, String strMessage) {

		SqlParam[] params = createParams(17);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(0, Types.INTEGER, intCtSytID);
		params[3].setAll(0, Types.CHAR, strCtCode);
		params[4].setAll(0, Types.VARCHAR, strCtName);
		params[5].setAll(0, Types.INTEGER, intCtType);
		params[6].setAll(0, Types.INTEGER, intCtPartnerID);
		params[7].setAll(0, Types.VARCHAR, strCtCharges);
		params[8].setAll(0, Types.CHAR, strCtSignDate);
		params[9].setAll(0, Types.CHAR, strCtValidDate);
		params[10].setAll(0, Types.VARCHAR, strProvideSample);
		params[11].setAll(0, Types.VARCHAR, strCheckInterval);
		params[12].setAll(0, Types.VARCHAR, strMemo);
		params[13].setAll(0, Types.VARCHAR, strCtTobaRelate);
		params[14].setAll(0, Types.VARCHAR, strCtPjRelate);
		params[15].setAll(0, Types.VARCHAR, strCtMediaDetail);
		params[16].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("C_MediaContractModify", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param CtCode
	 *            String
	 * @param CtName
	 *            String
	 * @param CtType
	 *            String
	 * @param CtPartnerID
	 *            String
	 * @param CtCharges
	 *            String
	 * @param CtSignDate
	 *            String
	 * @param CtValidDate
	 *            String
	 * @param CtAttach1
	 *            String
	 * @param CtAttach2
	 *            String
	 * @param CtAttach3
	 *            String
	 * @param CtAttach4
	 *            String
	 * @param CtAttach5
	 *            String
	 * @param CtAttach6
	 *            String
	 * @param CtAttach7
	 *            String
	 * @param Memo
	 *            String
	 * @param CtTobaRelate
	 *            String
	 * @param CtPjRelate
	 *            String
	 * @param <any>
	 *            unknown
	 * @return SqlReturn
	 */
	public static SqlReturn C_WildContractAdd(String PersonID, String DepartID,
			String CtCode, String CtName, String CtType, String CtPartnerID,
			String CtCharges, String CtSignDate, String CtValidDate,
			String CtAttach1, String CtAttach2, String CtAttach3,
			String CtAttach4, String CtAttach5, String CtAttach6,
			String CtAttach7, String Memo, String CtTobaRelate,
			String CtPjRelate, String CtWildDetail, String Message) {

		SqlParam[] params = createParams(21);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.CHAR, CtCode);
		params[3].setAll(0, Types.VARCHAR, CtName);
		params[4].setAll(0, Types.INTEGER, CtType);
		params[5].setAll(0, Types.INTEGER, CtPartnerID);
		params[6].setAll(0, Types.VARCHAR, CtCharges);
		params[7].setAll(0, Types.CHAR, CtSignDate);
		params[8].setAll(0, Types.CHAR, CtValidDate);
		params[9].setAll(0, Types.VARCHAR, CtAttach1);
		params[10].setAll(0, Types.VARCHAR, CtAttach2);
		params[11].setAll(0, Types.VARCHAR, CtAttach3);
		params[12].setAll(0, Types.VARCHAR, CtAttach4);
		params[13].setAll(0, Types.VARCHAR, CtAttach5);
		params[14].setAll(0, Types.VARCHAR, CtAttach6);
		params[15].setAll(0, Types.VARCHAR, CtAttach7);
		params[16].setAll(0, Types.VARCHAR, Memo);
		params[17].setAll(0, Types.VARCHAR, CtTobaRelate);
		params[18].setAll(0, Types.VARCHAR, CtPjRelate);
		params[19].setAll(0, Types.VARCHAR, CtWildDetail);
		params[20].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_WildContractAdd", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param CtSystID
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_WildContractDetailInfoGet(String PersonID,
			String CtSystID, String Message) {

		SqlParam[] params = createParams(3);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, CtSystID);
		params[2].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_WildContractDetailInfoGet", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param strCtCode
	 *            String
	 * @param strCtName
	 *            String
	 * @param intCtType
	 *            String
	 * @param intCtPartnerID
	 *            String
	 * @param strCtCharges
	 *            String
	 * @param strCtSignDate
	 *            String
	 * @param strCtValidDate
	 *            String
	 * @param strPjAddress
	 *            String
	 * @param strPercent
	 *            String
	 * @param strLeftPercent
	 *            String
	 * @param strCheckdate
	 *            String
	 * @param strMemo
	 *            String
	 * @param strCtTobaRelate
	 *            String
	 * @param strCtPjRelate
	 *            String
	 * @param strCtHouseFitDetail
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_HouseFitContractAdd(String intPersonID,
			String intDepartID, String strCtCode, String strCtName,
			String intCtType, String intCtPartnerID, String strCtCharges,
			String strCtSignDate, String strCtValidDate, String strPjAddress,
			String strPercent, String strLeftPercent, String strCheckdate,
			String strMemo, String strCtTobaRelate, String strCtPjRelate,
			String strCtHouseFitDetail, String strMessage) {

		SqlParam[] params = createParams(18);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(0, Types.CHAR, strCtCode);
		params[3].setAll(0, Types.VARCHAR, strCtName);
		params[4].setAll(0, Types.INTEGER, intCtType);
		params[5].setAll(0, Types.INTEGER, intCtPartnerID);
		params[6].setAll(0, Types.VARCHAR, strCtCharges);
		params[7].setAll(0, Types.CHAR, strCtSignDate);
		params[8].setAll(0, Types.CHAR, strCtValidDate);
		params[9].setAll(0, Types.VARCHAR, strPjAddress);
		params[10].setAll(0, Types.VARCHAR, strPercent);
		params[11].setAll(0, Types.VARCHAR, strLeftPercent);
		params[12].setAll(0, Types.VARCHAR, strCheckdate);
		params[13].setAll(0, Types.VARCHAR, strMemo);
		params[14].setAll(0, Types.VARCHAR, strCtTobaRelate);
		params[15].setAll(0, Types.VARCHAR, strCtPjRelate);
		params[16].setAll(0, Types.VARCHAR, strCtHouseFitDetail);
		params[17].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("C_HouseFitContractAdd", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intCtSystID
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_HouseFitContractDetailInfoGet(String intPersonID,
			String intCtSystID, String strMessage) {

		SqlParam[] params = createParams(3);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intCtSystID);
		params[2].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("C_HouseFitContractDetailInfoGet", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param CtCode
	 *            String
	 * @param CtName
	 *            String
	 * @param CtType
	 *            String
	 * @param CtPartnerID
	 *            String
	 * @param CtCharges
	 *            String
	 * @param CtSignDate
	 *            String
	 * @param CtValidDate
	 *            String
	 * @param CtImprest
	 *            String
	 * @param CtPayMentMode
	 *            String
	 * @param CtForfeitMoney
	 *            String
	 * @param Memo
	 *            String
	 * @param CtTobaRelate
	 *            String
	 * @param CtPjRelate
	 *            String
	 * @param CtGoodsDetail
	 *            String
	 * @param CtGoodsAttachDetail
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_GoodsContractAdd(String PersonID, String DepartID,
			String CtCode, String CtName, String CtType, String CtPartnerID,
			String CtCharges, String CtSignDate, String CtValidDate,
			String CtImprest, String CtPayMentMode, String CtForfeitMoney,
			String Memo, String CtTobaRelate, String CtPjRelate,
			String CtGoodsDetail, String CtGoodsAttachDetail, String Message) {

		SqlParam[] params = createParams(18);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.CHAR, CtCode);
		params[3].setAll(0, Types.VARCHAR, CtName);
		params[4].setAll(0, Types.INTEGER, CtType);
		params[5].setAll(0, Types.INTEGER, CtPartnerID);
		params[6].setAll(0, Types.VARCHAR, CtCharges);
		params[7].setAll(0, Types.CHAR, CtSignDate);
		params[8].setAll(0, Types.CHAR, CtValidDate);
		params[9].setAll(0, Types.VARCHAR, CtImprest);
		params[10].setAll(0, Types.VARCHAR, CtPayMentMode);
		params[11].setAll(0, Types.VARCHAR, CtForfeitMoney);
		params[12].setAll(0, Types.VARCHAR, Memo);
		params[13].setAll(0, Types.VARCHAR, CtTobaRelate);
		params[14].setAll(0, Types.VARCHAR, CtPjRelate);
		params[15].setAll(0, Types.VARCHAR, CtGoodsDetail);
		params[16].setAll(0, Types.VARCHAR, CtGoodsAttachDetail);
		params[17].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_GoodsContractAdd", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param CtSystID
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_GoodsContractDetailInfoGet(String PersonID,
			String CtSystID, String Message) {

		SqlParam[] params = createParams(3);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, CtSystID);
		params[2].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_GoodsContractDetailInfoGet", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param CtSytID
	 *            String
	 * @param CtCode
	 *            String
	 * @param CtName
	 *            String
	 * @param CtType
	 *            String
	 * @param CtPartnerID
	 *            String
	 * @param CtCharges
	 *            String
	 * @param CtSignDate
	 *            String
	 * @param CtValidDate
	 *            String
	 * @param CtImprest
	 *            String
	 * @param CtPayMentMode
	 *            String
	 * @param CtForfeitMoney
	 *            String
	 * @param Memo
	 *            String
	 * @param CtTobaRelate
	 *            String
	 * @param CtPjRelate
	 *            String
	 * @param CtGoodsDetail
	 *            String
	 * @param CtGoodsAttachDetail
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_GoodsContractModify(String PersonID,
			String DepartID, String CtSytID, String CtCode, String CtName,
			String CtType, String CtPartnerID, String CtCharges,
			String CtSignDate, String CtValidDate, String CtImprest,
			String CtPayMentMode, String CtForfeitMoney, String Memo,
			String CtTobaRelate, String CtPjRelate, String CtGoodsDetail,
			String CtGoodsAttachDetail, String Message) {

		SqlParam[] params = createParams(19);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.INTEGER, CtSytID);
		params[3].setAll(0, Types.CHAR, CtCode);
		params[4].setAll(0, Types.VARCHAR, CtName);
		params[5].setAll(0, Types.INTEGER, CtType);
		params[6].setAll(0, Types.INTEGER, CtPartnerID);
		params[7].setAll(0, Types.VARCHAR, CtCharges);
		params[8].setAll(0, Types.CHAR, CtSignDate);
		params[9].setAll(0, Types.CHAR, CtValidDate);
		params[10].setAll(0, Types.VARCHAR, CtImprest);
		params[11].setAll(0, Types.VARCHAR, CtPayMentMode);
		params[12].setAll(0, Types.VARCHAR, CtForfeitMoney);
		params[13].setAll(0, Types.VARCHAR, Memo);
		params[14].setAll(0, Types.VARCHAR, CtTobaRelate);
		params[15].setAll(0, Types.VARCHAR, CtPjRelate);
		params[16].setAll(0, Types.VARCHAR, CtGoodsDetail);
		params[17].setAll(0, Types.VARCHAR, CtGoodsAttachDetail);
		params[18].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_GoodsContractModify", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param CtCode
	 *            String
	 * @param CtName
	 *            String
	 * @param CtType
	 *            String
	 * @param CtPartnerID
	 *            String
	 * @param CtCharges
	 *            String
	 * @param CtSignDate
	 *            String
	 * @param CtValidDate
	 *            String
	 * @param CtReleaseTerm
	 *            String
	 * @param Memo
	 *            String
	 * @param CtTobaRelate
	 *            String
	 * @param CtPjRelate
	 *            String
	 * @param CtAdsSupportDetail
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_AdsSupportContractAdd(String PersonID,
			String DepartID, String CtCode, String CtName, String CtType,
			String CtPartnerID, String CtCharges, String CtSignDate,
			String CtValidDate, String CtReleaseTerm, String Memo,
			String CtTobaRelate, String CtPjRelate, String CtAdsSupportDetail,
			String Message) {

		SqlParam[] params = createParams(15);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.CHAR, CtCode);
		params[3].setAll(0, Types.VARCHAR, CtName);
		params[4].setAll(0, Types.INTEGER, CtType);
		params[5].setAll(0, Types.INTEGER, CtPartnerID);
		params[6].setAll(0, Types.VARCHAR, CtCharges);
		params[7].setAll(0, Types.CHAR, CtSignDate);
		params[8].setAll(0, Types.CHAR, CtValidDate);
		params[9].setAll(0, Types.VARCHAR, CtReleaseTerm);
		params[10].setAll(0, Types.VARCHAR, Memo);
		params[11].setAll(0, Types.VARCHAR, CtTobaRelate);
		params[12].setAll(0, Types.VARCHAR, CtPjRelate);
		params[13].setAll(0, Types.VARCHAR, CtAdsSupportDetail);
		params[14].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_AdsSupportContractAdd", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param CtSystID
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_AdsSupportContractDetailInfoGet(String PersonID,
			String CtSystID, String Message) {

		SqlParam[] params = createParams(3);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, CtSystID);
		params[2].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_AdsSupportContractDetailInfoGet", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param CtSytID
	 *            String
	 * @param CtCode
	 *            String
	 * @param CtName
	 *            String
	 * @param CtType
	 *            String
	 * @param CtPartnerID
	 *            String
	 * @param CtCharges
	 *            String
	 * @param CtSignDate
	 *            String
	 * @param CtValidDate
	 *            String
	 * @param CtReleaseTerm
	 *            String
	 * @param Memo
	 *            String
	 * @param CtTobaRelate
	 *            String
	 * @param CtPjRelate
	 *            String
	 * @param CtAdsSupportDetail
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_AdsSupportContractModify(String PersonID,
			String DepartID, String CtSytID, String CtCode, String CtName,
			String CtType, String CtPartnerID, String CtCharges,
			String CtSignDate, String CtValidDate, String CtReleaseTerm,
			String Memo, String CtTobaRelate, String CtPjRelate,
			String CtAdsSupportDetail, String Message) {

		SqlParam[] params = createParams(16);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.INTEGER, CtSytID);
		params[3].setAll(0, Types.CHAR, CtCode);
		params[4].setAll(0, Types.VARCHAR, CtName);
		params[5].setAll(0, Types.INTEGER, CtType);
		params[6].setAll(0, Types.INTEGER, CtPartnerID);
		params[7].setAll(0, Types.VARCHAR, CtCharges);
		params[8].setAll(0, Types.CHAR, CtSignDate);
		params[9].setAll(0, Types.CHAR, CtValidDate);
		params[10].setAll(0, Types.VARCHAR, CtReleaseTerm);
		params[11].setAll(0, Types.VARCHAR, Memo);
		params[12].setAll(0, Types.VARCHAR, CtTobaRelate);
		params[13].setAll(0, Types.VARCHAR, CtPjRelate);
		params[14].setAll(0, Types.VARCHAR, CtAdsSupportDetail);
		params[15].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_AdsSupportContractModify", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param CtCode
	 *            String
	 * @param CtName
	 *            String
	 * @param CtType
	 *            String
	 * @param CtPartnerID
	 *            String
	 * @param CtCharges
	 *            String
	 * @param CtSignDate
	 *            String
	 * @param CtValidDate
	 *            String
	 * @param Memo
	 *            String
	 * @param CtTobaRelate
	 *            String
	 * @param CtPjRelate
	 *            String
	 * @param CtDetail
	 *            String
	 * @param leaseholdBegin
	 *            String
	 * @param leaseholdEnd
	 *            String
	 * @param leaseholdYear
	 *            String
	 * @param leaseholdMonth
	 *            String
	 * @param UseBeginDate
	 *            String
	 * @param RentPrice
	 *            String
	 * @param <any>
	 *            unknown
	 * @return SqlReturn
	 */
	public static SqlReturn C_adsCounterContractAdd(String PersonID,
			String DepartID, String CtCode, String CtName, String CtType,
			String CtPartnerID, String CtCharges, String CtSignDate,
			String CtValidDate, String Memo, String CtTobaRelate,
			String CtPjRelate, String CtDetail, String leaseholdBegin,
			String leaseholdEnd, String leaseholdYear, String leaseholdMonth,
			String UseBeginDate, String RentPrice, String RentArea,
			String PayDate, String Message) {

		SqlParam[] params = createParams(22);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.CHAR, CtCode);
		params[3].setAll(0, Types.VARCHAR, CtName);
		params[4].setAll(0, Types.INTEGER, CtType);
		params[5].setAll(0, Types.INTEGER, CtPartnerID);
		params[6].setAll(0, Types.VARCHAR, CtCharges);
		params[7].setAll(0, Types.CHAR, CtSignDate);
		params[8].setAll(0, Types.CHAR, CtValidDate);
		params[9].setAll(0, Types.VARCHAR, Memo);
		params[10].setAll(0, Types.VARCHAR, CtTobaRelate);
		params[11].setAll(0, Types.VARCHAR, CtPjRelate);
		params[12].setAll(0, Types.VARCHAR, CtDetail);
		params[13].setAll(0, Types.CHAR, leaseholdBegin);
		params[14].setAll(0, Types.CHAR, leaseholdEnd);
		params[15].setAll(0, Types.CHAR, leaseholdYear);
		params[16].setAll(0, Types.CHAR, leaseholdMonth);
		params[17].setAll(0, Types.CHAR, UseBeginDate);
		params[18].setAll(0, Types.CHAR, RentPrice);
		params[19].setAll(0, Types.CHAR, RentArea);
		params[20].setAll(0, Types.CHAR, PayDate);
		params[21].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_adsCounterContractAdd", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param CtSystID
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_AdsCounterContractDetailInfoGet(String PersonID,
			String CtSystID, String Message) {

		SqlParam[] params = createParams(3);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, CtSystID);
		params[2].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_AdsCounterContractDetailInfoGet", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param CtSytID
	 *            String
	 * @param CtCode
	 *            String
	 * @param CtName
	 *            String
	 * @param CtType
	 *            String
	 * @param CtPartnerID
	 *            String
	 * @param CtCharges
	 *            String
	 * @param CtSignDate
	 *            String
	 * @param CtValidDate
	 *            String
	 * @param Memo
	 *            String
	 * @param CtTobaRelate
	 *            String
	 * @param CtPjRelate
	 *            String
	 * @param CtDetail
	 *            String
	 * @param leaseholdBegin
	 *            String
	 * @param leaseholdEnd
	 *            String
	 * @param leaseholdYear
	 *            String
	 * @param leaseholdMonth
	 *            String
	 * @param UseBeginDate
	 *            String
	 * @param <any>
	 *            unknown
	 * @return SqlReturn
	 */
	public static SqlReturn C_adsCounterContractModify(String PersonID,
			String DepartID, String CtSytID, String CtCode, String CtName,
			String CtType, String CtPartnerID, String CtCharges,
			String CtSignDate, String CtValidDate, String Memo,
			String CtTobaRelate, String CtPjRelate, String CtDetail,
			String leaseholdBegin, String leaseholdEnd, String leaseholdYear,
			String leaseholdMonth, String UseBeginDate, String RentPrice,
			String RentArea, String PayDate, String Message) {

		SqlParam[] params = createParams(23);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.INTEGER, CtSytID);
		params[3].setAll(0, Types.CHAR, CtCode);
		params[4].setAll(0, Types.VARCHAR, CtName);
		params[5].setAll(0, Types.INTEGER, CtType);
		params[6].setAll(0, Types.INTEGER, CtPartnerID);
		params[7].setAll(0, Types.VARCHAR, CtCharges);
		params[8].setAll(0, Types.CHAR, CtSignDate);
		params[9].setAll(0, Types.CHAR, CtValidDate);
		params[10].setAll(0, Types.VARCHAR, Memo);
		params[11].setAll(0, Types.VARCHAR, CtTobaRelate);
		params[12].setAll(0, Types.VARCHAR, CtPjRelate);
		params[13].setAll(0, Types.VARCHAR, CtDetail);
		params[14].setAll(0, Types.CHAR, leaseholdBegin);
		params[15].setAll(0, Types.CHAR, leaseholdEnd);
		params[16].setAll(0, Types.CHAR, leaseholdYear);
		params[17].setAll(0, Types.CHAR, leaseholdMonth);
		params[18].setAll(0, Types.CHAR, UseBeginDate);
		params[19].setAll(0, Types.CHAR, RentPrice);
		params[20].setAll(0, Types.CHAR, RentArea);
		params[21].setAll(0, Types.CHAR, PayDate);
		params[22].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_adsCounterContractModify", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param CtCode
	 *            String
	 * @param CtName
	 *            String
	 * @param CtType
	 *            String
	 * @param CtPartnerID
	 *            String
	 * @param CtCharges
	 *            String
	 * @param CtSignDate
	 *            String
	 * @param CtValidDate
	 *            String
	 * @param Memo
	 *            String
	 * @param CtTobaRelate
	 *            String
	 * @param CtPjRelate
	 *            String
	 * @param adscompname
	 *            String
	 * @param adsname
	 *            String
	 * @param enddate
	 *            String
	 * @param startdate
	 *            String
	 * @param totalday
	 *            String
	 * @param percent
	 *            String
	 * @param leftday
	 *            String
	 * @param <any>
	 *            unknown
	 * @return SqlReturn
	 */
	public static SqlReturn C_InTitleContractAdd(String PersonID,
			String DepartID, String CtCode, String CtName, String CtType,
			String CtPartnerID, String CtCharges, String CtSignDate,
			String CtValidDate, String Memo, String CtTobaRelate,
			String CtPjRelate, String adscompname, String adsname,
			String enddate, String startdate, String totalday, String percent,
			String leftday, String totalday1, String totaltimes,
			String leasttimes, String someday, String location,
			String countgirl, String countticket, String medianame,
			String Message) {

		SqlParam[] params = createParams(28);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.CHAR, CtCode);
		params[3].setAll(0, Types.VARCHAR, CtName);
		params[4].setAll(0, Types.INTEGER, CtType);
		params[5].setAll(0, Types.INTEGER, CtPartnerID);
		params[6].setAll(0, Types.VARCHAR, CtCharges);
		params[7].setAll(0, Types.CHAR, CtSignDate);
		params[8].setAll(0, Types.CHAR, CtValidDate);
		params[9].setAll(0, Types.VARCHAR, Memo);
		params[10].setAll(0, Types.VARCHAR, CtTobaRelate);
		params[11].setAll(0, Types.VARCHAR, CtPjRelate);
		params[12].setAll(0, Types.VARCHAR, adscompname);
		params[13].setAll(0, Types.VARCHAR, adsname);
		params[14].setAll(0, Types.CHAR, enddate);
		params[15].setAll(0, Types.CHAR, startdate);
		params[16].setAll(0, Types.CHAR, totalday);
		params[17].setAll(0, Types.CHAR, percent);
		params[18].setAll(0, Types.CHAR, leftday);
		params[19].setAll(0, Types.CHAR, totalday1);
		params[20].setAll(0, Types.CHAR, totaltimes);
		params[21].setAll(0, Types.CHAR, leasttimes);
		params[22].setAll(0, Types.CHAR, someday);
		params[23].setAll(0, Types.CHAR, location);
		params[24].setAll(0, Types.CHAR, countgirl);
		params[25].setAll(0, Types.CHAR, countticket);
		params[26].setAll(0, Types.CHAR, medianame);
		params[27].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_InTitleContractAdd", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param CtSytID
	 *            String
	 * @param CtCode
	 *            String
	 * @param CtName
	 *            String
	 * @param CtType
	 *            String
	 * @param CtPartnerID
	 *            String
	 * @param CtCharges
	 *            String
	 * @param CtSignDate
	 *            String
	 * @param CtValidDate
	 *            String
	 * @param Memo
	 *            String
	 * @param CtTobaRelate
	 *            String
	 * @param CtPjRelate
	 *            String
	 * @param adscompname
	 *            String
	 * @param adsname
	 *            String
	 * @param enddate
	 *            String
	 * @param startdate
	 *            String
	 * @param totalday
	 *            String
	 * @param percent
	 *            String
	 * @param <any>
	 *            unknown
	 * @return SqlReturn
	 */
	public static SqlReturn C_InTitleContractModify(String PersonID,
			String DepartID, String CtSytID, String CtCode, String CtName,
			String CtType, String CtPartnerID, String CtCharges,
			String CtSignDate, String CtValidDate, String Memo,
			String CtTobaRelate, String CtPjRelate, String adscompname,
			String adsname, String enddate, String startdate, String totalday,
			String percent, String leftday, String totalday1,
			String totaltimes, String leasttimes, String someday,
			String location, String countgirl, String countticket,
			String medianame, String Message) {

		SqlParam[] params = createParams(29);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.INTEGER, CtSytID);
		params[3].setAll(0, Types.CHAR, CtCode);
		params[4].setAll(0, Types.VARCHAR, CtName);
		params[5].setAll(0, Types.INTEGER, CtType);
		params[6].setAll(0, Types.INTEGER, CtPartnerID);
		params[7].setAll(0, Types.VARCHAR, CtCharges);
		params[8].setAll(0, Types.CHAR, CtSignDate);
		params[9].setAll(0, Types.CHAR, CtValidDate);
		params[10].setAll(0, Types.VARCHAR, Memo);
		params[11].setAll(0, Types.VARCHAR, CtTobaRelate);
		params[12].setAll(0, Types.VARCHAR, CtPjRelate);
		params[13].setAll(0, Types.VARCHAR, adscompname);
		params[14].setAll(0, Types.VARCHAR, adsname);
		params[15].setAll(0, Types.CHAR, enddate);
		params[16].setAll(0, Types.CHAR, startdate);
		params[17].setAll(0, Types.CHAR, totalday);
		params[18].setAll(0, Types.CHAR, percent);
		params[19].setAll(0, Types.CHAR, leftday);
		params[20].setAll(0, Types.CHAR, totalday1);
		params[21].setAll(0, Types.CHAR, totaltimes);
		params[22].setAll(0, Types.CHAR, leasttimes);
		params[23].setAll(0, Types.CHAR, someday);
		params[24].setAll(0, Types.CHAR, location);
		params[25].setAll(0, Types.CHAR, countgirl);
		params[26].setAll(0, Types.CHAR, countticket);
		params[27].setAll(0, Types.CHAR, medianame);
		params[28].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_InTitleContractModify", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param CtSystID
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_InTitleContractDetailInfoGet(String PersonID,
			String CtSystID, String Message) {

		SqlParam[] params = createParams(3);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, CtSystID);
		params[2].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_InTitleContractDetailInfoGet", params);
	}

	/**
	 *
	 * @param MenuCode
	 *            String
	 * @return ArrayList
	 */
	public static ArrayList G_PersonInfoQuery(String MenuCode) {

		ArrayList list = new ArrayList();

		SqlParam[] params = createParams(1);
		params[0].setAll(0, Types.CHAR, MenuCode);
		SqlReturn myReturn = executeProcedure("G_PersonInfoQuery", params);
		list.add(myReturn.getResultSet(0));
		list.add(myReturn.getResultSet(1));
		return list;
	}

	public static SqlReturn G_OtherPersonInfoQuery(String rang,
			String PersonInfoID) {

		SqlParam[] params = createParams(2);
		params[0].setAll(0, Types.CHAR, rang);
		params[1].setAll(0, Types.INTEGER, PersonInfoID);
		return executeProcedure("G_OtherPersonInfoQuery", params);
	}

	public static SqlReturn G_PersonInfoModifySave(String intMaintType,
			String intpersonid, String intpersoncode, String strpersonname,
			String strpersonusername, String strpassword,
			String intdepartmentid, String intisactive, String intiswork,
			String intworkarea, String strworkno, String strmobile,
			String strmobile2, String strMessage) {

		SqlParam[] params = createParams(14);
		params[0].setAll(0, Types.INTEGER, intMaintType);
		params[1].setAll(0, Types.INTEGER, intpersonid);
		params[2].setAll(0, Types.CHAR, intpersoncode);
		params[3].setAll(0, Types.VARCHAR, strpersonname);
		params[4].setAll(0, Types.VARCHAR, strpersonusername);
		params[5].setAll(0, Types.VARCHAR, strpassword);
		params[6].setAll(0, Types.INTEGER, intdepartmentid);
		params[7].setAll(0, Types.CHAR, intisactive);
		params[8].setAll(0, Types.CHAR, intiswork);
		params[9].setAll(0, Types.INTEGER, intworkarea);
		params[10].setAll(0, Types.VARCHAR, strworkno);
		params[11].setAll(0, Types.VARCHAR, strmobile);
		params[12].setAll(0, Types.VARCHAR, strmobile2);
		params[13].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("G_PersonInfoModifySave", params);
	}

	/**
	 *
	 * @param intMaintType
	 *            String
	 * @param intpersonid
	 *            String
	 * @param intpersoncode
	 *            String
	 * @param strpersonname
	 *            String
	 * @param strpersonusername
	 *            String
	 * @param strpassword
	 *            String
	 * @param intdepartmentid
	 *            String
	 * @param intisactive
	 *            String
	 * @param intiswork
	 *            String
	 * @param intworkarea
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn G_PersonInfoSave(String intMaintType,
			String intpersonid, String intpersoncode, String strpersonname,
			String strpersonusername, String strpassword,
			String intdepartmentid, String intisactive, String intiswork,
			String intworkarea, String strworkno, String strMobile,
			String strMobile2, String strBirthday, String strIdcode,
			String strOfficephone, String strEmail, String strAddress,
			String strPersontype, String strIsduty, String pinyin,
			String belong, String userId,// 增加操作人ID，add by 朱忠南
			String strMessage) {
		SqlParam[] params = createParams(24);
		params[0].setAll(0, Types.INTEGER, intMaintType);
		params[1].setAll(0, Types.INTEGER, intpersonid);
		params[2].setAll(0, Types.CHAR, intpersoncode);
		params[3].setAll(0, Types.VARCHAR, strpersonname);
		params[4].setAll(0, Types.VARCHAR, strpersonusername);
		params[5].setAll(0, Types.VARCHAR, strpassword);
		params[6].setAll(0, Types.INTEGER, intdepartmentid);
		params[7].setAll(0, Types.CHAR, intisactive);
		params[8].setAll(0, Types.CHAR, intiswork);
		params[9].setAll(0, Types.INTEGER, intworkarea);
		params[10].setAll(0, Types.VARCHAR, strworkno);
		params[11].setAll(0, Types.VARCHAR, strMobile);
		params[12].setAll(0, Types.VARCHAR, strMobile2);
		params[13].setAll(0, Types.VARCHAR, strBirthday);
		params[14].setAll(0, Types.VARCHAR, strIdcode);
		params[15].setAll(0, Types.VARCHAR, strOfficephone);
		params[16].setAll(0, Types.VARCHAR, strEmail);
		params[17].setAll(0, Types.VARCHAR, strAddress);
		params[18].setAll(0, Types.INTEGER, strPersontype);
		params[19].setAll(0, Types.INTEGER, strIsduty);
		params[20].setAll(0, Types.VARCHAR, pinyin);
		params[21].setAll(0, Types.INTEGER, belong);
		params[22].setAll(0, Types.INTEGER, userId);
		params[23].setAll(1, Types.VARCHAR, strMessage);

		return executeProcedure("G_PersonInfoSave", params);
	}

	public static SqlReturn G_OtherPersonInfoSave(String intMaintType,
			String intpersonid, String intpersoncode, String strpersonname,
			String strpersonusername, String strpassword, String intisactive,
			String intcompanyInfoId, String intrange, String strMessage) {

		SqlParam[] params = createParams(10);
		params[0].setAll(0, Types.INTEGER, intMaintType);
		params[1].setAll(0, Types.INTEGER, intpersonid);
		params[2].setAll(0, Types.CHAR, intpersoncode);
		params[3].setAll(0, Types.VARCHAR, strpersonname);
		params[4].setAll(0, Types.VARCHAR, strpersonusername);
		params[5].setAll(0, Types.VARCHAR, strpassword);
		params[6].setAll(0, Types.CHAR, intisactive);
		params[7].setAll(0, Types.CHAR, intrange);
		params[8].setAll(0, Types.INTEGER, intcompanyInfoId);
		params[9].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("G_OtherPersonInfoSave", params);
	}

	/**
	 *
	 * @return SqlReturn
	 */
	public static SqlReturn G_WorkAreaQuery() {

		return callProcedure("G_WorkAreaQuery");
	}

	/**
	 *
	 * @return SqlReturn
	 */

	public static SqlReturn i_impfromzj(String date) {

		SqlParam[] params = createParams(1);
		params[0].setAll(0, Types.VARCHAR, date);
		return executeProcedure("i_impfromzj", params);
	}

	public static SqlReturn i_impfromzjyw(String date) {

		SqlParam[] params = createParams(1);
		params[0].setAll(0, Types.VARCHAR, date);
		return executeProcedure("i_impfromzjyw", params);
	}

	public static SqlReturn condownalert(String company_id) {

		SqlParam[] params = createParams(2);
		params[0].setAll(0, Types.VARCHAR, company_id);
		params[1].setAll(0, Types.VARCHAR, "7");
		return executeProcedure("h_condownalert", params);
	}

	/**
	 *
	 * @param StationTypeID
	 *            String
	 * @param StationID
	 *            String
	 * @return ArrayList
	 */
	public static ArrayList G_PersonInfoOpen(String StationTypeID,
			String StationID) {
		ArrayList list = new ArrayList();

		SqlParam[] params = createParams(2);
		params[0].setAll(0, Types.INTEGER, StationTypeID);
		params[1].setAll(0, Types.INTEGER, StationID);
		SqlReturn myReturn = executeProcedure("G_PersonInfoOpen", params);

		list.add(myReturn.getResultSet(0));
		list.add(myReturn.getResultSet(1));

		return list;
	}

	/**
	 *
	 * @param tpye
	 *            String
	 * @param personid
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn G_CompTreeQuery(String tpye, String personid) {

		SqlParam[] params = createParams(2);
		params[0].setAll(0, Types.INTEGER, tpye);
		params[1].setAll(0, Types.INTEGER, personid);
		return executeProcedure("G_CompTreeQuery", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param CompCode
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn G_SelectCompSave(String PersonID, String CompCode,
			String Message) {

		SqlParam[] params = createParams(3);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.VARCHAR, CompCode);
		params[2].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("G_SelectCompSave", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param intCtSytID
	 *            String
	 * @param strCtCode
	 *            String
	 * @param strCtName
	 *            String
	 * @param intCtType
	 *            String
	 * @param intCtPartnerID
	 *            String
	 * @param strCtCharges
	 *            String
	 * @param strCtSignDate
	 *            String
	 * @param strCtValidDate
	 *            String
	 * @param strPjAddress
	 *            String
	 * @param strPercent
	 *            String
	 * @param strLeftPercent
	 *            String
	 * @param strCheckdate
	 *            String
	 * @param strMemo
	 *            String
	 * @param strCtTobaRelate
	 *            String
	 * @param strCtPjRelate
	 *            String
	 * @param strCtHouseFitDetail
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_HouseFitContractModify(String intPersonID,
			String intDepartID, String intCtSytID, String strCtCode,
			String strCtName, String intCtType, String intCtPartnerID,
			String strCtCharges, String strCtSignDate, String strCtValidDate,
			String strPjAddress, String strPercent, String strLeftPercent,
			String strCheckdate, String strMemo, String strCtTobaRelate,
			String strCtPjRelate, String strCtHouseFitDetail, String strMessage) {

		SqlParam[] params = createParams(19);
		params[0].setAll(0, Types.INTEGER, intPersonID);
		params[1].setAll(0, Types.INTEGER, intDepartID);
		params[2].setAll(0, Types.INTEGER, intCtSytID);
		params[3].setAll(0, Types.CHAR, strCtCode);
		params[4].setAll(0, Types.VARCHAR, strCtName);
		params[5].setAll(0, Types.INTEGER, intCtType);
		params[6].setAll(0, Types.INTEGER, intCtPartnerID);
		params[7].setAll(0, Types.VARCHAR, strCtCharges);
		params[8].setAll(0, Types.CHAR, strCtSignDate);
		params[9].setAll(0, Types.CHAR, strCtValidDate);
		params[10].setAll(0, Types.VARCHAR, strPjAddress);
		params[11].setAll(0, Types.VARCHAR, strPercent);
		params[12].setAll(0, Types.VARCHAR, strLeftPercent);
		params[13].setAll(0, Types.VARCHAR, strCheckdate);
		params[14].setAll(0, Types.VARCHAR, strMemo);
		params[15].setAll(0, Types.VARCHAR, strCtTobaRelate);
		params[16].setAll(0, Types.VARCHAR, strCtPjRelate);
		params[17].setAll(0, Types.VARCHAR, strCtHouseFitDetail);
		params[18].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("C_HouseFitContractModify", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param CtCode
	 *            String
	 * @param CtName
	 *            String
	 * @param CtType
	 *            String
	 * @param CtPartnerID
	 *            String
	 * @param CtSignDate
	 *            String
	 * @param Memo
	 *            String
	 * @param CtTobaRelate
	 *            String
	 * @param CtPjRelate
	 *            String
	 * @param DistrictName
	 *            String
	 * @param StartDate
	 *            String
	 * @param EndDate
	 *            String
	 * @param ShopNames
	 *            String
	 * @param Fee
	 *            String
	 * @param PayDate
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_MarketPromotContractAdd(String PersonID,
			String DepartID, String CtCode, String CtName, String CtType,
			String CtPartnerID, String CtSignDate, String Memo,
			String CtTobaRelate, String CtPjRelate, String DistrictName,
			String StartDate, String EndDate, String ShopNames, String Fee,
			String PayDate, String Message) {

		SqlParam[] params = createParams(17);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.CHAR, CtCode);
		params[3].setAll(0, Types.VARCHAR, CtName);
		params[4].setAll(0, Types.INTEGER, CtType);
		params[5].setAll(0, Types.INTEGER, CtPartnerID);
		params[6].setAll(0, Types.CHAR, CtSignDate);
		params[7].setAll(0, Types.VARCHAR, Memo);
		params[8].setAll(0, Types.VARCHAR, CtTobaRelate);
		params[9].setAll(0, Types.VARCHAR, CtPjRelate);
		params[10].setAll(0, Types.VARCHAR, DistrictName);
		params[11].setAll(0, Types.CHAR, StartDate);
		params[12].setAll(0, Types.CHAR, EndDate);
		params[13].setAll(0, Types.VARCHAR, ShopNames);
		params[14].setAll(0, Types.CHAR, Fee);
		params[15].setAll(0, Types.CHAR, PayDate);
		params[16].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_MarketPromotContractAdd", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param CtSystID
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_MarketPromotContractDetailInfoGet(String PersonID,
			String CtSystID, String Message) {

		SqlParam[] params = createParams(3);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, CtSystID);
		params[2].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_MarketPromotContractDetailInfoGet", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param ConstractID
	 *            String
	 * @param CtCode
	 *            String
	 * @param CtName
	 *            String
	 * @param CtType
	 *            String
	 * @param CtPartnerID
	 *            String
	 * @param CtSignDate
	 *            String
	 * @param Memo
	 *            String
	 * @param CtTobaRelate
	 *            String
	 * @param CtPjRelate
	 *            String
	 * @param DistrictName
	 *            String
	 * @param StartDate
	 *            String
	 * @param EndDate
	 *            String
	 * @param ShopNames
	 *            String
	 * @param Fee
	 *            String
	 * @param PayDate
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_MarketPromotContractModify(String PersonID,
			String DepartID, String ConstractID, String CtCode, String CtName,
			String CtType, String CtPartnerID, String CtSignDate, String Memo,
			String CtTobaRelate, String CtPjRelate, String DistrictName,
			String StartDate, String EndDate, String ShopNames, String Fee,
			String PayDate, String Message) {

		SqlParam[] params = createParams(18);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.INTEGER, ConstractID);
		params[3].setAll(0, Types.CHAR, CtCode);
		params[4].setAll(0, Types.VARCHAR, CtName);
		params[5].setAll(0, Types.INTEGER, CtType);
		params[6].setAll(0, Types.INTEGER, CtPartnerID);
		params[7].setAll(0, Types.CHAR, CtSignDate);
		params[8].setAll(0, Types.VARCHAR, Memo);
		params[9].setAll(0, Types.VARCHAR, CtTobaRelate);
		params[10].setAll(0, Types.VARCHAR, CtPjRelate);
		params[11].setAll(0, Types.VARCHAR, DistrictName);
		params[12].setAll(0, Types.CHAR, StartDate);
		params[13].setAll(0, Types.CHAR, EndDate);
		params[14].setAll(0, Types.VARCHAR, ShopNames);
		params[15].setAll(0, Types.CHAR, Fee);
		params[16].setAll(0, Types.CHAR, PayDate);
		params[17].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_MarketPromotContractModify", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param CtSytID
	 *            String
	 * @param CtCode
	 *            String
	 * @param CtName
	 *            String
	 * @param CtType
	 *            String
	 * @param CtPartnerID
	 *            String
	 * @param CtCharges
	 *            String
	 * @param CtSignDate
	 *            String
	 * @param CtValidDate
	 *            String
	 * @param CtAttach1
	 *            String
	 * @param CtAttach2
	 *            String
	 * @param CtAttach3
	 *            String
	 * @param CtAttach4
	 *            String
	 * @param CtAttach5
	 *            String
	 * @param CtAttach6
	 *            String
	 * @param CtAttach7
	 *            String
	 * @param Memo
	 *            String
	 * @param CtTobaRelate
	 *            String
	 * @param <any>
	 *            unknown
	 * @return SqlReturn
	 */
	public static SqlReturn C_WildContractModify(String PersonID,
			String DepartID, String CtSytID, String CtCode, String CtName,
			String CtType, String CtPartnerID, String CtCharges,
			String CtSignDate, String CtValidDate, String CtAttach1,
			String CtAttach2, String CtAttach3, String CtAttach4,
			String CtAttach5, String CtAttach6, String CtAttach7, String Memo,
			String CtTobaRelate, String CtPjRelate, String CtWildDetail,
			String Message) {

		SqlParam[] params = createParams(22);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.INTEGER, CtSytID);
		params[3].setAll(0, Types.CHAR, CtCode);
		params[4].setAll(0, Types.VARCHAR, CtName);
		params[5].setAll(0, Types.INTEGER, CtType);
		params[6].setAll(0, Types.INTEGER, CtPartnerID);
		params[7].setAll(0, Types.VARCHAR, CtCharges);
		params[8].setAll(0, Types.CHAR, CtSignDate);
		params[9].setAll(0, Types.CHAR, CtValidDate);
		params[10].setAll(0, Types.VARCHAR, CtAttach1);
		params[11].setAll(0, Types.VARCHAR, CtAttach2);
		params[12].setAll(0, Types.VARCHAR, CtAttach3);
		params[13].setAll(0, Types.VARCHAR, CtAttach4);
		params[14].setAll(0, Types.VARCHAR, CtAttach5);
		params[15].setAll(0, Types.VARCHAR, CtAttach6);
		params[16].setAll(0, Types.VARCHAR, CtAttach7);
		params[17].setAll(0, Types.VARCHAR, Memo);
		params[18].setAll(0, Types.VARCHAR, CtTobaRelate);
		params[19].setAll(0, Types.VARCHAR, CtPjRelate);
		params[20].setAll(0, Types.VARCHAR, CtWildDetail);
		params[21].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_WildContractModify", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param CtSytID
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_ContractPaymentInfoGet(String PersonID,
			String CtSytID, String Message) {

		SqlParam[] params = createParams(3);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, CtSytID);
		params[2].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_ContractPaymentInfoGet", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param CtSytID
	 *            String
	 * @param RealPaid
	 *            String
	 * @param PaymentDate
	 *            String
	 * @param Memo
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_ContractPaymentAdd(String PersonID,
			String DepartID, String CtSytID, String RealPaid,
			String PaymentDate, String Memo, String Message) {

		SqlParam[] params = createParams(7);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.INTEGER, CtSytID);
		params[3].setAll(0, Types.VARCHAR, RealPaid);
		params[4].setAll(0, Types.VARCHAR, PaymentDate);
		params[5].setAll(0, Types.VARCHAR, Memo);
		params[6].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_ContractPaymentAdd", params);
	}

	/**
	 *
	 * @param intStorageID
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_StorageQuery(String intStorageID) {

		SqlParam[] params = createParams(1);
		params[0].setAll(0, Types.INTEGER, intStorageID);
		return executeProcedure("A_StorageQuery", params);
	}

	/**
	 *
	 * @param intStorageID
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_StorageQuery1(String intStorageID) {

		SqlParam[] params = createParams(1);
		params[0].setAll(0, Types.INTEGER, intStorageID);
		return executeProcedure("A_StorageQuery1", params);
	}

	/**
	 *
	 * @param intMaintType
	 *            String
	 * @param intStorageID
	 *            String
	 * @param strStorageName
	 *            String
	 * @param intDepartID
	 *            String
	 * @param strMemo
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_StorageMaint(String intMaintType,
			String intStorageID, String strStorageName, String intDepartID,
			String strMemo, String strMessage) {

		SqlParam[] params = createParams(6);
		params[0].setAll(0, Types.INTEGER, intMaintType);
		params[1].setAll(0, Types.INTEGER, intStorageID);
		params[2].setAll(0, Types.CHAR, strStorageName);
		params[3].setAll(0, Types.INTEGER, intDepartID);
		params[4].setAll(0, Types.VARCHAR, strMemo);
		params[5].setAll(1, Types.VARCHAR, strMessage);
		return executeProcedure("A_StorageMaint", params);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param CtSytID
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn C_ContractStatusUpdate(String PersonID,
			String DepartID, String CtSytID, String Message) {

		SqlParam[] params = createParams(4);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.INTEGER, CtSytID);
		params[3].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("C_ContractStatusUpdate", params);
	}

	/**
	 *
	 * @param intPersonID
	 *            String
	 * @param intDepartID
	 *            String
	 * @param intMvOutStorageID
	 *            String
	 * @param intMvInStorageID
	 *            String
	 * @param strAddDate
	 *            String
	 * @param strMemo
	 *            String
	 * @param strAdsGoodsInfo
	 *            String
	 * @param strMessage
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_AdsGoodsMove(String intPersonID,
			String intDepartID, String intMvOutStorageID,
			String intMvInStorageID, String strAddDate, String strMemo,
			String strAdsGoodsInfo, String strMessage) {

		// SqlParam[] params = createParams(8);
		// params[0].setAll(0, Types.INTEGER, intPersonID);
		// params[1].setAll(0, Types.INTEGER, intDepartID);
		// params[2].setAll(0, Types.INTEGER, intMvOutStorageID);
		// params[3].setAll(0, Types.INTEGER, intMvInStorageID);
		// params[4].setAll(0, Types.CHAR, strAddDate);
		// params[5].setAll(0, Types.VARCHAR, strMemo);
		// params[6].setAll(0, Types.VARCHAR, strAdsGoodsInfo);
		// params[7].setAll(1, Types.VARCHAR, strMessage);
		// return executeProcedure("A_AdsGoodsMove", params);

		List inParams = new ArrayList();
		List outParams = new ArrayList();
		inParams.add(intPersonID);
		inParams.add(intDepartID);
		inParams.add(intMvOutStorageID);
		inParams.add(intMvInStorageID);
		inParams.add(strAddDate);
		inParams.add(strMemo);
		inParams.add(strAdsGoodsInfo);
		outParams.add(strMessage);
		return callProcedure("A_AdsGoodsMove", inParams, outParams);
	}

	/**
	 *
	 * @param PersonID
	 *            String
	 * @param DepartID
	 *            String
	 * @param BeginDate
	 *            String
	 * @param EndDate
	 *            String
	 * @param StorageID
	 *            String
	 * @param SYCompCode
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_AdsGoodsTakeStoreAuditQuery(String PersonID,
			String DepartID, String BeginDate, String EndDate,
			String StorageID, String SYCompCode, String Message) {

		SqlParam[] params = createParams(7);
		params[0].setAll(0, Types.INTEGER, PersonID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.CHAR, BeginDate);
		params[3].setAll(0, Types.CHAR, EndDate);
		params[4].setAll(0, Types.INTEGER, StorageID);
		params[5].setAll(0, Types.VARCHAR, SYCompCode);
		params[6].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("A_AdsGoodsTakeStoreAuditQuery", params);
	}

	/**
	 *
	 * @param UserID
	 *            String
	 * @param DepartID
	 *            String
	 * @param BeginDate
	 *            String
	 * @param EndDate
	 *            String
	 * @param StorageID
	 *            String
	 * @param SYCompSystID
	 *            String
	 * @param PersonID
	 *            String
	 * @param AdsTakeMan
	 *            String
	 * @param AdsAimID
	 *            String
	 * @param AdsTakeModeID
	 *            String
	 * @param AdsTakeID
	 *            String
	 * @param TotalMode
	 *            String
	 * @param AdsGoodsID
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_AdsTakeInfoQuery(String UserID, String DepartID,
			String BeginDate, String EndDate, String StorageID,
			String SYCompSystID, String PersonID, String AdsTakeMan,
			String AdsAimID, String AdsTakeModeID, String AdsTakeID,
			String TotalMode, String AdsGoodsID, String status, String Message) {

		SqlParam[] params = createParams(15);
		params[0].setAll(0, Types.INTEGER, UserID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.CHAR, BeginDate);
		params[3].setAll(0, Types.CHAR, EndDate);
		params[4].setAll(0, Types.INTEGER, StorageID);
		params[5].setAll(0, Types.VARCHAR, SYCompSystID);
		params[6].setAll(0, Types.INTEGER, PersonID);
		params[7].setAll(0, Types.VARCHAR, AdsTakeMan);
		params[8].setAll(0, Types.INTEGER, AdsAimID);
		params[9].setAll(0, Types.INTEGER, AdsTakeModeID);
		params[10].setAll(0, Types.VARCHAR, AdsTakeID);
		params[11].setAll(0, Types.INTEGER, TotalMode);
		params[12].setAll(0, Types.INTEGER, AdsGoodsID);
		params[13].setAll(0, Types.INTEGER, status);
		params[14].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("A_AdsTakeInfoQuery", params);
	}

	/**
	 *
	 * @param UserID
	 *            String
	 * @param DepartID
	 *            String
	 * @param BeginDate
	 *            String
	 * @param EndDate
	 *            String
	 * @param StorageID
	 *            String
	 * @param AdsCompID
	 *            String
	 * @param PersonID
	 *            String
	 * @param AdsStoreID
	 *            String
	 * @param TotalMode
	 *            String
	 * @param AdsGoodsID
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_AdsStoreInfoQuery(String UserID, String DepartID,
			String BeginDate, String EndDate, String StorageID,
			String AdsCompID, String PersonID, String AdsStoreID,
			String TotalMode, String AdsGoodsID, String status, String Message) {

		SqlParam[] params = createParams(12);
		params[0].setAll(0, Types.INTEGER, UserID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.CHAR, BeginDate);
		params[3].setAll(0, Types.CHAR, EndDate);
		params[4].setAll(0, Types.INTEGER, StorageID);
		params[5].setAll(0, Types.VARCHAR, AdsCompID);
		params[6].setAll(0, Types.INTEGER, PersonID);
		params[7].setAll(0, Types.VARCHAR, AdsStoreID);
		params[8].setAll(0, Types.INTEGER, TotalMode);
		params[9].setAll(0, Types.INTEGER, AdsGoodsID);
		params[10].setAll(0, Types.INTEGER, status);
		params[11].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("A_AdsStoreInfoQuery", params);
	}

	/**
	 *
	 * @param UserID
	 *            String
	 * @param DepartID
	 *            String
	 * @param BeginDate
	 *            String
	 * @param EndDate
	 *            String
	 * @param MvOutStorageID
	 *            String
	 * @param MvInStorageID
	 *            String
	 * @param PersonID
	 *            String
	 * @param AdsMoveID
	 *            String
	 * @param TotalMode
	 *            String
	 * @param AdsGoodsID
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_AdsMoveInfoQuery(String UserID, String DepartID,
			String BeginDate, String EndDate, String MvOutStorageID,
			String MvInStorageID, String PersonID, String AdsMoveID,
			String TotalMode, String AdsGoodsID, String Message) {

		SqlParam[] params = createParams(11);
		params[0].setAll(0, Types.INTEGER, UserID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.CHAR, BeginDate);
		params[3].setAll(0, Types.CHAR, EndDate);
		params[4].setAll(0, Types.INTEGER, MvOutStorageID);
		params[5].setAll(0, Types.INTEGER, MvInStorageID);
		params[6].setAll(0, Types.INTEGER, PersonID);
		params[7].setAll(0, Types.VARCHAR, AdsMoveID);
		params[8].setAll(0, Types.INTEGER, TotalMode);
		params[9].setAll(0, Types.INTEGER, AdsGoodsID);
		params[10].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("A_AdsMoveInfoQuery", params);
	}

	/**
	 *
	 * @param UserID
	 *            String
	 * @param DepartID
	 *            String
	 * @param StorageIDString
	 *            String
	 * @param TotalMode
	 *            String
	 * @param StorageType
	 *            String
	 * @param Message
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn A_AdsGoodsStorageQuery(String UserID,
			String DepartID, String StorageIDString, String TotalMode,
			String StorageType, String Message) {

		SqlParam[] params = createParams(6);
		params[0].setAll(0, Types.INTEGER, UserID);
		params[1].setAll(0, Types.INTEGER, DepartID);
		params[2].setAll(0, Types.VARCHAR, StorageIDString);
		params[3].setAll(0, Types.INTEGER, TotalMode);
		params[4].setAll(0, Types.INTEGER, StorageType);
		params[5].setAll(1, Types.VARCHAR, Message);
		return executeProcedure("A_AdsGoodsStorageQuery", params);
	}

	/**
	 *
	 * @param IsProvince
	 *            String
	 * @return SqlReturn
	 */
	public static SqlReturn G_SYCompGroupListQuery(String IsProvince) {

		SqlParam[] params = createParams(1);
		params[0].setAll(0, Types.CHAR, IsProvince);
		return executeProcedure("G_SYCompGroupListQuery", params);
	}

	/*************** 月报相关数据库操作 **************************/

	/**
	 * 根据烟草公司的ID,月报日期和烟草产地,返回该公司该月的销售毛利月报
	 *
	 * @param companyId
	 * @param reportDate
	 * @param region
	 * @return
	 */
	public static SqlReturn R_getMonthlyReport(String companyId,
			String reportYear, String reportMonth, String region) {

		final int length = 4;
		SqlParam[] params = createParams(length);

		params[0].setAll(0, Types.INTEGER, companyId);
		params[1].setAll(0, Types.CHAR, reportYear);
		params[2].setAll(0, Types.CHAR, reportMonth);
		params[3].setAll(0, Types.CHAR, region);

		return executeProcedure("R_getMonthlyReport", params);
	}

	/**
	 * 将指定ID的月报记录用传入的相应数据更新, 如果不想更新某个字段,传入null参数即可，
	 *
	 * @param id
	 * @param salesQuantity
	 * @param grossProfit
	 * @return
	 */
	public static SqlReturn R_saveMonthlyReport(String id, String salesQuantity,
			String grossProfit) {
		final int length = 3;
		SqlParam[] params = createParams(length);

		params[0].setAll(0, Types.INTEGER, id);
		params[1].setAll(0, Types.DECIMAL, salesQuantity);
		params[2].setAll(0, Types.DECIMAL, grossProfit);

		return executeProcedure("R_saveMonthlyReport", params);
	}

	/**
	 * 根据烟草公司的ID,月报日期和烟草产地,返回该公司该月的经济指标（1）月报
	 *
	 * @param companyId
	 * @param reportDate
	 * @param region
	 * @return
	 */
	public static SqlReturn R_getIndicatorReport(String companyId,
			String reportYear, String reportMonth, String area) {

		final int length = 4;
		SqlParam[] params = createParams(length);

		params[0].setAll(0, Types.INTEGER, companyId);
		params[1].setAll(0, Types.CHAR, reportYear);
		params[2].setAll(0, Types.CHAR, reportMonth);
		params[3].setAll(0, Types.CHAR, area);

		return executeProcedure("R_getIndicatorReport", params);
	}

	/**
	 * 将指定ID的月报记录用传入的相应数据更新, 如果不想更新某个字段,传入null参数即可，
	 *
	 * @param id
	 * @param salesQuantity
	 * @param grossProfit
	 * @return
	 */
	public static SqlReturn R_saveIndicatorReport(String id, String area,
			String salesQuantity, String grossProfit) {
		final int length = 4;
		SqlParam[] params = createParams(length);

		params[0].setAll(0, Types.INTEGER, id);
		params[1].setAll(0, Types.CHAR, area);
		params[2].setAll(0, Types.DECIMAL, salesQuantity);
		params[3].setAll(0, Types.DECIMAL, grossProfit);

		return executeProcedure("R_saveIndicatorReport", params);
	}

	/**
	 * 根据烟草公司的ID,月报日期和烟草产地,返回该公司该月的经济指标（2）月报
	 *
	 * @param companyId
	 * @param reportDate
	 * @param region
	 * @return
	 */
	public static SqlReturn R_getGradeReport(String companyId,
			String reportYear, String reportMonth, String area) {

		final int length = 4;
		SqlParam[] params = createParams(length);

		params[0].setAll(0, Types.INTEGER, companyId);
		params[1].setAll(0, Types.CHAR, reportYear);
		params[2].setAll(0, Types.CHAR, reportMonth);
		params[3].setAll(0, Types.CHAR, area);

		return executeProcedure("R_getGradeReport", params);
	}

	/**
	 * 将指定ID的月报记录用传入的相应数据更新, 如果不想更新某个字段,传入null参数即可，
	 *
	 * @param id
	 * @param salesQuantity
	 * @param grossProfit
	 * @return
	 */
	public static SqlReturn R_saveGradeReport(String id, String area,
			String nbSalesQuantity, String otherSalesQuantity) {
		final int length = 4;
		SqlParam[] params = createParams(length);

		params[0].setAll(0, Types.INTEGER, id);
		params[1].setAll(0, Types.CHAR, area);
		params[2].setAll(0, Types.DECIMAL, nbSalesQuantity);
		params[3].setAll(0, Types.DECIMAL, otherSalesQuantity);

		return executeProcedure("R_saveGradeReport", params);
	}

	/**
	 * 根据烟草公司的ID,月报日期和烟草产地,返回该公司该月销售态势月报
	 *
	 * @param companyId
	 * @param reportDate
	 * @param region
	 * @return
	 */
	public static SqlReturn R_getSituationReport(String companyID,
			String isCompany, String reportYear, String reportMonth) {

		final int length = 4;
		SqlParam[] params = createParams(length);

		params[0].setAll(0, Types.INTEGER, companyID);
		params[1].setAll(0, Types.CHAR, isCompany);
		params[2].setAll(0, Types.CHAR, reportYear);
		params[3].setAll(0, Types.CHAR, reportMonth);

		return executeProcedure("R_getSituationReport", params);
	}

	/**
	 * 将指定ID的月报记录用传入的相应数据更新, 如果不想更新某个字段,传入null参数即可，
	 *
	 * @param id
	 * @param salesQuantity
	 * @param grossProfit
	 * @return
	 */
	public static SqlReturn R_saveSituationReport(String analysisID,
			String brandStructure, String priceTrend,
			String supplyMarketingCondition, String promotionPropaganda,
			String individualAnalysis, String suggestion, String firstRemark,
			String secondRemark, String thirdRemark) {
		final int length = 10;
		SqlParam[] params = createParams(length);

		params[0].setAll(0, Types.INTEGER, analysisID);
		params[1].setAll(0, Types.VARCHAR, brandStructure);
		params[2].setAll(0, Types.VARCHAR, priceTrend);
		params[3].setAll(0, Types.VARCHAR, supplyMarketingCondition);
		params[4].setAll(0, Types.VARCHAR, promotionPropaganda);
		params[5].setAll(0, Types.VARCHAR, individualAnalysis);
		params[6].setAll(0, Types.VARCHAR, suggestion);
		params[7].setAll(0, Types.VARCHAR, firstRemark);
		params[8].setAll(0, Types.VARCHAR, secondRemark);
		params[9].setAll(0, Types.VARCHAR, thirdRemark);

		return executeProcedure("R_saveSituationReport", params);
	}

	// 通知列表
	public static SqlReturn G_GetAllNews() {
		final int length = 1;
		SqlParam[] params = createParams(length);
		params[0].setAll(1, Types.VARCHAR, "0");
		return executeProcedure("G_GetAllNews", params);
	}

	// 通知添加
	public static SqlReturn G_NewsAdd(NewsForm newsaddform) {
		final int length = 4;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.VARCHAR, newsaddform.getTitle());
		params[1].setAll(0, Types.VARCHAR, newsaddform.getContent());
		params[2].setAll(0, Types.VARCHAR, newsaddform.getInputperson());
		params[3].setAll(0, Types.VARCHAR, newsaddform.getEnddate());
		return executeProcedure("G_NewsAdd", params);
	}

	// 查看通知
	public static SqlReturn G_GetNewsInfo(String newsid) {
		final int length = 1;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.INTEGER, newsid);
		// params[1].setAll(1, Types.VARCHAR, "0");
		return executeProcedure("G_GetNewsInfo", params);
	}

	// 通知修改
	public static SqlReturn G_NewsModify(NewsForm newsform) {
		final int length = 4;
		SqlParam[] params = createParams(length);
		// System.out.println(newsform.getEnddate());
		params[0].setAll(0, Types.INTEGER, newsform.getNewsid());
		params[1].setAll(0, Types.VARCHAR, newsform.getTitle());
		params[2].setAll(0, Types.VARCHAR, newsform.getContent());
		params[3].setAll(0, Types.VARCHAR, newsform.getEnddate());
		return executeProcedure("G_NewsModify", params);
	}

	// 通知删除
	public static SqlReturn G_NewsDelete(String newsid) {
		final int length = 1;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.INTEGER, newsid);
		return executeProcedure("G_NewsDelete", params);
	}

	// 设置投放地
	public static SqlReturn G_DealCompanyToDest(String IN_COMPANYSYSTID,
			String IN_destid, String IN_TYPE) {
		final int length = 3;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.INTEGER, IN_COMPANYSYSTID);
		params[1].setAll(0, Types.VARCHAR, IN_destid);
		params[2].setAll(0, Types.INTEGER, IN_TYPE);
		return executeProcedure("G_DealCompanyToDest", params);
	}

	// 预置套牌烟
	public static SqlReturn G_Getjgtobaccos(String v_TOBASYSTID,
			String SyCompSystId, String personId) {
		final int length = 4;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.VARCHAR, v_TOBASYSTID);
		params[1].setAll(0, Types.INTEGER, SyCompSystId);
		params[2].setAll(0, Types.INTEGER, personId);
		params[3].setAll(1, Types.VARCHAR, "");
		return executeProcedure("G_Getjgtobaccos", params);
	}

	// 预置套牌烟(单选)
	public static SqlReturn G_Getjgtobaccossingle(String v_TOBASYSTID,
			String SyCompSystId, String personId) {
		final int length = 4;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.VARCHAR, v_TOBASYSTID);
		params[1].setAll(0, Types.INTEGER, SyCompSystId);
		params[2].setAll(0, Types.INTEGER, personId);
		params[3].setAll(1, Types.VARCHAR, "");
		return executeProcedure("G_Getjgtobaccossingle", params);
	}

	/**
	 * 获得要修改的部门的信息
	 *
	 * 系统设置_(部门)销售区域维护[获得单个区域]
	 *
	 * @return
	 */
	public static SqlReturn G_getAreaByCode(String areaCode) {
		ArrayList inParams = new ArrayList();
		inParams.add(areaCode);
		return callProcedure("G_getAreaByCode", inParams);
	}

	public static SqlReturn O_GetPersonCorpList(String v_personid) {
		final int length = 1;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.INTEGER, v_personid);
		return executeProcedure("O_GetPersonCorpList", params);
	}

	public static SqlReturn O_GetPersonCorpList(String v_personid,
			String v_departid) {
		final int length = 2;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.INTEGER, v_personid);
		params[1].setAll(0, Types.INTEGER, v_departid);
		return executeProcedure("O_GetPersonCorpListed", params);
	}

	public static SqlReturn O_getOutProvDemandHistory(String v_companyId,
			String v_beginDate, String v_enddate) {
		final int length = 3;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.VARCHAR, v_companyId);
		params[1].setAll(0, Types.VARCHAR, v_beginDate);
		params[2].setAll(0, Types.VARCHAR, v_enddate);
		return executeProcedure("O_getOutProvDemandHistory", params);
	}

	public static SqlReturn G_CompanyContractPlan(String p_intPersonID,
			String IN_planYear, String IN_planHalf, String IN_UpTag,
			String IN_CONFIRM) {
		final int length = 5;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.INTEGER, p_intPersonID);
		params[1].setAll(0, Types.VARCHAR, IN_planYear);
		params[2].setAll(0, Types.VARCHAR, IN_planHalf);
		params[3].setAll(0, Types.INTEGER, IN_UpTag);
		params[4].setAll(0, Types.INTEGER, IN_CONFIRM);
		return executeProcedure("G_CompanyContractPlan", params);
	}

	public static SqlReturn H_CompanyContractPlan(String p_intPersonID,
			String IN_planYear, String IN_planHalf, String IN_UpTag,
			String IN_CONFIRM) {
		final int length = 5;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.INTEGER, p_intPersonID);
		params[1].setAll(0, Types.VARCHAR, IN_planYear);
		params[2].setAll(0, Types.VARCHAR, IN_planHalf);
		params[3].setAll(0, Types.INTEGER, IN_UpTag);
		params[4].setAll(0, Types.INTEGER, IN_CONFIRM);
		return executeProcedure("H_CompanyContractPlan", params);
	}

	public static SqlReturn H_GetComplist(String p_intPersonID,
			String IN_planYear, String IN_planHalf) {
		final int length = 3;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.INTEGER, p_intPersonID);
		params[1].setAll(0, Types.VARCHAR, IN_planYear);
		params[2].setAll(0, Types.VARCHAR, IN_planHalf);
		return executeProcedure("H_GetComplist", params);
	}

	public static SqlReturn O_queryDailyReportsHistory(String v_companyId,
			String v_beginDate, String v_enddate) {
		final int length = 3;
		SqlParam[] params = createParams(length);
		params[0].setAll(0, Types.VARCHAR, v_companyId);
		params[1].setAll(0, Types.VARCHAR, v_beginDate);
		params[2].setAll(0, Types.VARCHAR, v_enddate);
		return executeProcedure("O_queryDailyReportsHistory", params);
	}

	public static void send(String optname, String destmobile, String message)
			throws Exception {
		String sql = "insert into T_clubsms_info (sender," + "destmobile,"
				+ "message," + "priority," + "delivery_flag," + "dt_starttime,"
				+ "dt_endtime," + "mobiletype," + "AppCode) values(" + "'"
				+ optname + "'," + "'" + destmobile + "'," + "'"
				+ MethodFactory.replace(message, "'", "''") + "'," + "'1',"
				+ "'0'," + "current timestamp," + "current timestamp," + "'',"
				+ "'00')";
		Executer.getInstance().ExecUpdateSQL(sql);
	}

	public static List queryHalfProcotolInfoByHPCode(String halfprotocolCode)
			throws Exception {
		String sql = "select a.halfprotocolcode,a.halfprotocolyear as year,case when a.halfprotocolhalf = '0' then '上半年' else '下半年' end as half,b.sycompalias from h_halfprotocol a "
				+ " left join g_sycompany b on a.sycompanyid=b.sycompsystid"
				+ " where a.halfprotocolcode like '" + halfprotocolCode + "%'";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();

	}

	public static List<BasicDynaBean> getPersonInfoList() throws Exception {
		String sql = "select a.personid,a.personname,b.departname from g_personinfo a "
				+ " left join g_departinfo b on a.departid=b.departid "
				+ " where a.isactive='1' "
				+ " and a.personid<>2500 "
				+ " order by a.departid";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List<BasicDynaBean> getPersonInfoList2() throws Exception {
		String sql = "select a.personid,a.personname,a.mobile,b.departname from g_personinfo a "
				+ " left join g_departinfo b on a.departid=b.departid "
				+ " where a.isactive='1' "
				// +" and a.personid<>2500 "
				+ " and a.mobile is not null" + " order by a.departid";
		log.info("sql:::" + sql);
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List<BasicDynaBean> getProvinceByPerson(String PersonId)
			throws Exception {
		String sql = "SELECT DISTINCT C.ProvSystId,C.ProvName FROM G_PersonManComp A, G_SyCompany B, G_Province C"
				+ " WHERE A.SyCompSystId=B.SyCompSystId AND LEFT(B.SyCompCode,2)=C.ProvinceCode "
				+ " AND A.PersonId=" + PersonId + " ORDER BY C.ProvSystId";
		log.info("sql:::" + sql);
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List<BasicDynaBean> getPersonManComp(String personid)
			throws Exception {
		String sql = "select a.sycompsystid,b.sycompname,sycompalias from g_personmancomp a,g_sycompany b "
				+ " where a.sycompsystid = b.sycompsystid and a.personid="
				+ personid + " order by b.sycompcode";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static String getMsgContentById(String msgId) throws Exception {
		String sql = "select content from sms_msg_receive where receiveid="
				+ msgId;
		List list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		if (list != null && list.size() > 0) {
			DynaBean bean = (DynaBean) list.get(0);
			String content = MethodFactory.getThisString(bean.get("content"));
			return content;
		} else
			return null;
	}

	public static List<BasicDynaBean> getMonthlyPlanMemoById(String id)
			throws Exception {
		String sql = "select memo from R_MONTHLYPLAN_MEMO where id=" + id;
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List<BasicDynaBean> getAllPriceType() throws Exception {
		String sql = "select a.*,case when a.inuse ='1' then '已启用' else '停用' end as useflag from price_pricetype a order by ordertag";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static BasicDynaBean getPriceTypeById(Integer priceId)
			throws Exception {
		String sql = "select a.*,case when a.inuse ='1' then '已启用' else '停用' end as useflag from price_pricetype a  where priceid="
				+ priceId;
		List list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		if (list != null && list.size() > 0)
			return (BasicDynaBean) list.get(0);
		else
			return null;
	}

	public static void deletePriceTypeById(Integer priceId) throws Exception {
		String sql = "delete from price_pricetype where priceid=" + priceId;
		Executer.getInstance().ExecUpdateSQL(sql);
	}

	public static String getPersonManageAreaType(String personid)
			throws Exception {
		String sql = "select * from G_PERSONMANAREA a,g_area b where a.areacode =b.area_code and left(areacode,2)='43' "
				+ " and  personid=" + personid;
		List list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		if (list != null && list.size() > 0)
			return "in";
		else
			return "out";
	}

	public static List<BasicDynaBean> getInfoChannelById(String channelid)
			throws Exception {

		String sql = "select a.*,coalesce(b.channelname,'根目录') as parentname from info_channel a left join info_channel b on a.parentid=b.channelid where a.channelid="
				+ channelid;
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static void deteleInfoChannelColumnById(String columnId)
			throws Exception {
		String sql = "delete from info_channel_column where columnid ="
				+ columnId;
		Executer.getInstance().ExecUpdateSQL(sql);
	}

	public static List<BasicDynaBean> getInfoChannelColumnById(String columnId)
			throws Exception {
		String sql = "select * from info_channel_column where columnid ="
				+ columnId;
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List<BasicDynaBean> getArticleBriefById(String articleid)
			throws Exception {
		String sql = "select brief from info_article where articleid="
				+ articleid;
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();

	}

	public static List<BasicDynaBean> getColumnContentById(String contentid)
			throws Exception {
		String sql = "select columncontent from info_column_content where contentid="
				+ contentid;
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List getTobaSerialList() throws Exception {
		String sql = "select distinct classid,classcode,classname from g_publiccodes where parentclassid=805";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List getTobaListByBandCode(String jgtobaSystid)
			throws Exception {
		String sql = "select HY_EAS_Code AS erp_code,tobasystid,tobaname, CASE WHEN TobaSystId = "
				+ jgtobaSystid
				+ " THEN 1 ELSE 0 END AS CHECKED "
				+ " FROM G_Tobacco WHERE (bandcode,dwdm) IN (select bandcode,dwdm from g_tobacco WHERE TobaSystId="
				+ jgtobaSystid + ") AND ISACTIVE='1'";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List getTobaListLevel2(String level2, String level3)
			throws Exception {
		String strProc = "Call O_TobaccoStocks2('" + level2 + "','" + level3
				+ "')";
		return Executer.getInstance().ExecSeletSQL(strProc).getResultSet();
		/*
		 * String sql=
		 * "select HY_EAS_Code AS erp_code,tobasystid,tobaname, CASE WHEN TobaSystId = "
		 * +level3+" THEN 1 ELSE 0 END AS CHECKED "
		 * +" FROM G_Tobacco WHERE level2="
		 * +level2+" AND level3 IS NOT NULL AND ISACTIVE='1'";
		 *
		 * return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		 */
	}

	public static List getTobaListLevel2new(String level2, String level3)
			throws Exception {
		String strProc = "Call O_TobaccoStocks2new('" + level2 + "','" + level3
				+ "')";
		return Executer.getInstance().ExecSeletSQL(strProc).getResultSet();
		/*
		 * String sql=
		 * "select HY_EAS_Code AS erp_code,tobasystid,tobaname, CASE WHEN TobaSystId = "
		 * +level3+" THEN 1 ELSE 0 END AS CHECKED "
		 * +" FROM G_Tobacco WHERE level2="
		 * +level2+" AND level3 IS NOT NULL AND ISACTIVE='1'";
		 *
		 * return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		 */
	}

	public static List getTobaListLevel1(String level1, String level2)
			throws Exception {
		// String strProc="Call O_TobaccoStocks2('"+ level1 +"','" + level2 +
		// "')";
		// return Executer.getInstance().ExecSeletSQL(strProc).getResultSet();

		String sql = "select tobasystid,tobaname, CASE WHEN TobaSystId = "
				+ level2 + " THEN 1 ELSE 0 END AS CHECKED "
				+ " FROM G_Tobacco WHERE level1=" + level1
				+ " AND level2 IS NOT NULL AND level3 IS NULL AND ISACTIVE='1'";

		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List getDemandDate(String demandid) throws Exception {
		// String strProc="Call O_TobaccoStocks2('"+ level1 +"','" + level2 +
		// "')";
		// return Executer.getInstance().ExecSeletSQL(strProc).getResultSet();

		String sql = "select distinct char_iso(report_date) as report_date"
				+ " FROM o_demand WHERE report_date > current date and demandid = "
				+ demandid;

		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List getTobaListByCompany(String tobaSystid, String SyCompany)
			throws Exception {
		String strProc = "Call O_TobaccoStocks('" + tobaSystid + "','"
				+ SyCompany + "')";
		return Executer.getInstance().ExecSeletSQL(strProc).getResultSet();
		/*
		 * String sql="select erp_code,tobasystid,CHECKED, " +
		 * "CASE WHEN SCCJ00='WY' THEN " +
		 * "tobaname || ' <FONT COLOR=#FF0000>' || CASE WHEN SUBSTR(ERPDM0,3,1)='1' THEN ' WH:' "
		 * + "WHEN SUBSTR(ERPDM0,3,1) ='2' THEN ' HA:' " +
		 * "WHEN SUBSTR(ERPDM0,3,1) ='3' THEN ' SX:' " +
		 * "ELSE ' WH:' END ||   CASE WHEN QMZMKC-DCL000<0 THEN '-' ELSE '' END || "
		 * + "CASE WHEN ABS(QMZMKC-DCL000)<1 THEN '0' ELSE '' END || " +
		 * "CASE WHEN " +
		 * "RIGHT(COALESCE(REPLACE(TRIM(REPLACE(TRIM(CHAR(ABS(QMZMKC-DCL000))),'0','   ')),'   ','0'),''),1)='.' "
		 * +
		 * "THEN REPLACE(COALESCE(REPLACE(TRIM(REPLACE(TRIM(CHAR(ABS(QMZMKC-DCL000))),'0','   ')),'   ','0'),''),'.','') "
		 * +
		 * "ELSE COALESCE(REPLACE(TRIM(REPLACE(TRIM(CHAR(ABS(QMZMKC-DCL000))),'0','   ')),'   ','0'),'') END "
		 * + "||'</FONT>' " + "WHEN SCCJ00='ZY' THEN " +
		 * "tobaname || ' <FONT COLOR=#FF0000>' || CASE WHEN SUBSTR(ERPDM0,3,1)='4' THEN ' XF:' "
		 * + "WHEN SUBSTR(ERPDM0,3,1) IN ('5','6','7') THEN 'LC:' " +
		 * "WHEN SUBSTR(ERPDM0,3,1) ='8' THEN ' GS:' " +
		 * "WHEN SUBSTR(ERPDM0,3,1) ='A' THEN ' HEB:' " +
		 * "WHEN SUBSTR(ERPDM0,3,1) ='B' THEN ' SC:' " +
		 * "ELSE ' QT:' END ||   CASE WHEN QMZMKC-DCL000<0 THEN '-' ELSE '' END || "
		 * + "CASE WHEN ABS(QMZMKC-DCL000)<1 THEN '0' ELSE '' END || " +
		 * "CASE WHEN " +
		 * "RIGHT(COALESCE(REPLACE(TRIM(REPLACE(TRIM(CHAR(ABS(QMZMKC-DCL000))),'0','   ')),'   ','0'),''),1)='.' "
		 * +
		 * "THEN REPLACE(COALESCE(REPLACE(TRIM(REPLACE(TRIM(CHAR(ABS(QMZMKC-DCL000))),'0','   ')),'   ','0'),''),'.','') "
		 * +
		 * "ELSE COALESCE(REPLACE(TRIM(REPLACE(TRIM(CHAR(ABS(QMZMKC-DCL000))),'0','   ')),'   ','0'),'') END "
		 * +
		 * "ELSE tobaname ||' <FONT COLOR=#00FF00> 0</FONT>' END AS tobaname from "
		 * +
		 * " (select HY_EAS_Code AS erp_code,A.tobasystid,A.tobaname,CASE WHEN b.JgTobaccoid IS NULL THEN 0 ELSE 1 END CHECKED"
		 * +
		 * " FROM G_Tobacco  A left join g_JgTobacco B ON A.TobaSystId=B.JgTobaccoid AND B.SyCompSystId= "
		 * +SyCompany +
		 * " WHERE (bandcode,dwdm) IN (select bandcode,dwdm from g_tobacco WHERE TobaSystId="
		 * +tobaSystid+") AND ISACTIVE='1' "
		 * +" AND nullif(Hy_Eas_Code,'') IS NOT NULL) M LEFT JOIN " +
		 * " (SELECT * FROM i_pitzhrb02 N  WHERE rq0000=(SELECT MAX(rq0000) FROM i_pitzhrb02)  AND ((N.SCCJ00='WY') OR (N.SCCJ00='ZY' AND SUBSTR(N.ERPDM0,3,1) IN ('4','5','6','7','8','9','A','B')))) O "
		 * +" ON M.erp_code=O.spdm00";
		 */
		// return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List getPhoneEnquiryTobaList() throws Exception {
		String sql = "select tobasystid ,tobaname,1 as tobatype,999999 as parentid from g_tobacco where tobasystid in (select tobasystid from g_systemusingtoba where systemid=1048)";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List GetDestBySyCompany(String SyCompSystId) throws Exception {
		String sql = "SELECT destid,destname FROM ( SELECT DISTINCT SyCompSystId,DestName,b.DestId FROM g_sycompany a,g_dest b WHERE A.GJ_CODE =B.INITCODE "
				+ " AND a.SyCompsystId="
				+ SyCompSystId
				+ " ) s "
				+ " union "
				+ " SELECT destid,destname FROM ( SELECT DISTINCT c.sycompsystid, b.DestName, b.DestId FROM g_sycompany a, g_dest b, G_COMPANYINFO c WHERE A.sycompsystid = c.areacompid and A.GJ_CODE =B.INITCODE"
				+ " AND c.SyCompsystId=" + SyCompSystId + " ) t";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List getCallPriceTobaList() throws Exception {
		String sql = "select tobasystid,tobaname from g_tobacco where tobasystid in (select tobaccoid from r_callprice) and isactive='1' order by ordertag";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List GetDestBySyCompany2(String SyCompSystId)
			throws Exception {
		String sql = " SELECT destname,destcode as erp_code FROM yc_erpdest A,g_sycompany B where A.hy_eas_code=B.hy_eas_code"
				+ " AND b.SyCompsystId="
				+ SyCompSystId
				+ " order by ischecked desc";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List GetWhouse() throws Exception {
		String sql = "SELECT * FROM G_Whouse";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List Getpriceid() throws Exception {
		String sql = "SELECT distinct pricelevelid,"
				+ " case"
				+ " when pricelevelid=0 then '零类烟'"
				+ " when pricelevelid=1 then '一类烟'"
				+ " when pricelevelid=2 then '二类烟'"
				+ " when pricelevelid=3 then '三类烟'"
				+ " when pricelevelid=4 then '四类烟'"
				+ " when pricelevelid=5 then '五类烟' end as pricelevelname FROM G_tobacco"
				+ " where brandcode like '42%' and pricelevelid is not null order by pricelevelid";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static ArrayList GetModiBatchs(String year, String half) {
		String sql = "SELECT batchid,'第'||GF_GETCHARFROMREAL(batchid)||'次' as batchname FROM O_CONPLANMODIBATCH where planhalf = '"
				+ half + "'and planyear = '" + year + "' order by batchid desc";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {

			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static ArrayList GetReportBatchs(String year, String half) {
		String sql = "SELECT batchid,'第'||GF_GETCHARFROMREAL(batchid)||'次' as batchname FROM O_CONPLANREPORTBATCH where planhalf = '"
				+ half + "'and planyear = '" + year + "' order by batchid desc";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {

			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static ArrayList GetAdjustBatchs(String year, String half) {
		String sql = "SELECT batchid,'第'||GF_GETCHARFROMREAL(batchid)||'次' as batchname FROM O_CONPLANADJUSTBATCH where planhalf = '"
				+ half + "'and planyear = '" + year + "' order by batchid desc";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {

			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static ArrayList GetFixBatchs(String year, String half) {
		String sql = "SELECT batchid,'第'||GF_GETCHARFROMREAL(batchid)||'次' as batchname FROM O_CONPLANFIXBATCH where planhalf = '"
				+ half + "'and planyear = '" + year + "' order by batchid desc";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {

			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static ArrayList GetReportBatchsNew(String year, String half) {
		String sql = "SELECT 0 AS batchid,'请选择批次' as batchname from SYSIBM.SYSDUMMY1 union SELECT batchid,'第'||GF_GETCHARFROMREAL(batchid)||'次' as batchname FROM O_CONPLANREPORTBATCH where lockflag=0 and planhalf = '"
				+ half + "'and planyear = '" + year + "'";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {

			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 得到personid所有下属职位列表 zyh@2010-12-21
	public static ArrayList GetJobState(int personid) {
		String sql = "SELECT 0 AS id,'全部' as name from SYSIBM.SYSDUMMY1 union select distinct b.persontype as id,c.jobname as name  from PD_MANRELATIONSHIP A ,g_personinfo b ,v_pd_job c"
				+ " where a.lead_personid ="
				+ personid
				+ " and a.sub_personid = b.personid and b.persontype = c.jobid order by id";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {

			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 得到personid所有下属职位列表 zyh@2010-12-21
	public static ArrayList GetJobState1(int personid) {
		String sql = "SELECT 0 AS id,'全部' as name from SYSIBM.SYSDUMMY1 union select distinct b.persontype as id,c.jobname as name  from PD_MANRELATIONSHIPBYSCORE A ,g_personinfo b ,v_pd_job c"
				+ " where a.lead_personid ="
				+ personid
				+ " and a.sub_personid = b.personid and b.persontype = c.jobid order by id";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {

			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static ArrayList GetAdjustBatchsNew(String year, String half) {
		String sql = "SELECT 0 AS batchid,'请选择批次' as batchname from SYSIBM.SYSDUMMY1 union SELECT batchid,'第'||GF_GETCHARFROMREAL(batchid)||'次' as batchname FROM O_CONPLANADJUSTBATCH where lockflag=0 and planhalf = '"
				+ half + "'and planyear = '" + year + "'";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {

			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static ArrayList GetFixBatchsNew(String year, String half) {
		String sql = "SELECT 0 AS batchid,'请选择批次' as batchname from SYSIBM.SYSDUMMY1 union SELECT batchid,'第'||GF_GETCHARFROMREAL(batchid)||'次' as batchname FROM O_CONPLANFIXBATCH where lockflag=0 and planhalf = '"
				+ half + "'and planyear = '" + year + "'";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {

			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static ArrayList GetComporderBatchs(String year, String half) {
		String sql = "SELECT batchid,'第'||GF_GETCHARFROMREAL(batchid)||'次' as batchname FROM O_CONPLANREPORTBATCH where planhalf = '"
				+ half + "'and planyear = '" + year + "' order by batchid desc";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {

			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static ArrayList GetLevel2ListByCom(String companyId) {
		String sql = "select a.level2 as tobaccoid,b.tobaname as tobaname from G_COMPUSINGLEVEL2_M a,g_tobacco b where  a.level2 = b.tobasystid and sycompsystid ="
				+ companyId + " order by b.includeprice desc ,a.level2";

		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {

			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static String GetCompanyNameByid(String companyId) {
		String sql = "select SYCOMPALIAS from G_SYCOMPANY where SYCOMPSYSTID ="
				+ companyId;
		String SYCOMPALIAS = "";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
			SYCOMPALIAS = (String) ((BasicDynaBean) list.get(0))
					.get("sycompalias");
			// System.out.println(SYCOMPALIAS);
		} catch (Exception e) {

			SYCOMPALIAS = "";
			log.error(e.getMessage());
		}
		return SYCOMPALIAS;
	}

	// 根据商业公司id获得对应的国家局编码
	public static List GetCompanyGJCodeByids(String companyIdSet) {
		ArrayList list = null;
		try {
			StringBuffer sql = new StringBuffer(
					"select DISTINCT gj_code from g_sycompany where isactive='1' ");
			if (null == companyIdSet || "".equals(companyIdSet)
					|| "-1".equals(companyIdSet)) {
				list = Executer.getInstance().ExecSeletSQL(sql.toString())
						.getResultSet();
			} else if (companyIdSet.indexOf(",") < 0) {
				sql.append(" and sycompsystid = " + companyIdSet);
				list = Executer.getInstance().ExecSeletSQL(sql.toString())
						.getResultSet();
			} else {
				sql.append(" and sycompsystid in ( ");
				sql.append(" select int(col0) from table(gf_getTableFromStr('"
						+ companyIdSet + "',',',';')) t ");
				sql.append("  where t.col0 is not null )");
				list = Executer.getInstance().ExecSeletSQL(sql.toString())
						.getResultSet();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list;
	}

	public static ArrayList GetMaterialTypeAll() {
		String sql = "select t.* from (SELECT -1 AS id,'所有' as name from SYSIBM.SYSDUMMY1 union SELECT mrrelid as id,mrrelname as name FROM MP_MATERIALREL where mrreltype='0' and isactive='1') as t order by t.id";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static ArrayList GetMaterialType() {
		String sql = "SELECT mrrelid as id,mrrelname as name FROM MP_MATERIALREL where mrreltype='0' and isactive='1'";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static ArrayList GetMaterialSpec() {
		String sql = "SELECT mrrelid as id,mrrelname as name FROM MP_MATERIALREL where mrreltype='1' and isactive='1'";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static ArrayList GetMaterialClass() {
		String sql = "SELECT mrrelid as id,mrrelname as name FROM MP_MATERIALREL where mrreltype='2' and isactive='1'";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static List GetOutList() throws Exception {
		String sql = "select OTID as id,OTNAME as name from MP_OUTTARGET where ISACTIVE = '1'";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List GetMPscape() throws Exception {
		String sql = "select classid as id,classname as name from g_publiccodes where parentclassid = 71 order by classid desc";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List GetMPtype() throws Exception {
		String sql = "select classid as id,classname as name from g_publiccodes where  parentclassid = 72";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List GetMPstate() throws Exception {
		String sql = "select classid as id,classname as name from g_publiccodes where parentclassid = 73";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	// 营销物资活动申报_审批状态列表
	public static List getFestivalAuditStateList() throws Exception {
		String sql = "select classid as id,classname as name from g_publiccodes where parentclassid = 73 and classid in(-1,1,2,3) order by id";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List getPersonBelongTypes() throws Exception {
		String sql = "select classid as id,classname as name from g_publiccodes where parentclassid = 606";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List GetMPSupplementState() throws Exception {
		String sql = "select classid as id,classname as name from g_publiccodes where parentclassid = 74";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List getMpStateAll() throws Exception {
		String sql = "select classid as id,classname as name from g_publiccodes "
				+ " where fieldname='state' and parentclassid in (73,74) and classid <> -1";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	// 领用单相关仓库信息
	public static ArrayList GetTinvaivCK() {
		String sql = "select c.erp_code,c.whousename from ("
				+ " SELECT 0 as whouseid,'0' AS erp_code,'全部' as whousename,'1' as flags from SYSIBM.SYSDUMMY1 "
				+ " union all SELECT whouseid,erp_code,whousealias as whousename,flags FROM g_whousedetail "
				+ " ) c order by flags desc,whouseid";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {

			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 领用单相关仓库信息(不要全部)
	public static ArrayList GetTinvaivCK2() {
		String sql = " SELECT whouseid,erp_code,whousealias as whousename FROM g_whousedetail "
				+ " order by flags desc,whouseid";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();

		} catch (Exception e) {

			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 领用单相关领用原因信息
	public static ArrayList GetTinvaivReason() {
		// String sql=" SELECT id,toid,tname FROM MP_APPLYITEMREASON " +
		// " where toid <> 'TEEMS000006HGQ000B' order by id desc";
		String sql = " SELECT id,toid,tname FROM MP_APPLYITEMREASON  order by id desc";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
			// System.out.println(sql);
		} catch (Exception e) {

			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 领用单相关领用人
	public static ArrayList GetTinvaivSaffoid() {
		String sql = " SELECT id,toid,tname FROM MP_TSYSCOREPERSON "
				+ " order by id";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
			// System.out.println(sql);
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 领用单相关部门
	public static ArrayList GetTinvaivDeptoid() {
		String sql = " SELECT id,toid,tname FROM MP_TSYSCOREDEPT "
				+ " order by id";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
			// System.out.println(sql);
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 领用单相关部门
	public static ArrayList GetTinvaivTACCTITEMTYPEOID() {
		String sql = " SELECT 0 as id ,'' as toid,'' as tname from SYSIBM.SYSDUMMY1 union all select id,toid,tname FROM MP_tglracctitemtype order by id ";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
			// System.out.println(sql);
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 领用单相关部门
	public static ArrayList GetTinvaivTACCTITEMOID() {
		String sql = " SELECT 0 as id ,'' as toid,'' as tname from SYSIBM.SYSDUMMY1 union all select id,toid,tname FROM mp_tglrproject order by id ";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
			// System.out.println(sql);
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 费用管理关键操作日志
	public static int FeemanageLog(String userid, int subjectid, String ip) {
		String sql = "insert into fm_logs (personid,subjectid,ip) values ("
				+ Integer.parseInt(userid) + "," + subjectid + ",'" + ip + "')";
		int revaule = 0;
		try {
			Executer.getInstance().ExecUpdateSQL(sql);
			// System.out.println(sql);
		} catch (Exception e) {
			revaule = -1;
			log.error(e.getMessage());
		}
		return revaule;
	}

	// 系统操作日志记录
	public static int operLog(String personid, int operid, String objectid,
			String ip) {
		String[] objectids = objectid.split(";");
		int revaule = 0;
		try {
			for (int i = 0; i < objectids.length && objectids[i] != null
					&& !objectids[i].trim().equals(""); i++) {
				String sql = "insert into g_operlogs (personid,operid,objectid,ip) values ("
						+ Integer.parseInt(personid)
						+ ","
						+ operid
						+ ",'"
						+ objectids[i].trim() + "','" + ip + "')";
				Executer.getInstance().ExecUpdateSQL(sql);
			}
		} catch (Exception e) {
			revaule = -1;
			log.error(e.getMessage());
		}
		return revaule;
	}

	public static String GetTinvaivToidByid(String id) {
		String sql = "select toid from MP_TINVAIV where ID =" + id;
		String toid = "";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
			toid = (String) ((BasicDynaBean) list.get(0)).get("toid");
			// System.out.println(SYCOMPALIAS);
		} catch (Exception e) {
			toid = "";
			log.error(e.getMessage());
		}
		return toid;
	}

	public static List<BasicDynaBean> getDistributionTobaccoByCompany(
			String companyId, String year, String half) throws Exception {
		String sql = "SELECT  c.tobasystid,c.tobaname from  G_Tobacco C  where c.tobasystid = 1944";
		log.info("sql:::" + sql);
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List<BasicDynaBean> GetLXtype() throws Exception {
		String sql = "select classid as id,classname as name from g_publiccodes where parentclassid = 81";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List<BasicDynaBean> GetZTBZtype() throws Exception {
		String sql = "select classid as id,classname as name from g_publiccodes where parentclassid = 82";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	// 凭证是否存在
	public static boolean ifFeebillExitst(String feeid) {
		String sql = "select count(*) as num from fm_billmain where feeid ='"
				+ feeid + "'";
		int num = 0;
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
			num = ((Integer) ((BasicDynaBean) list.get(0)).get("num"))
					.intValue();
			// System.out.println(num);
		} catch (Exception e) {
			num = 0;
			log.error(e.getMessage());
		}

		if (num == 0) {
			return false;
		} else {
			return true;
		}

	}

	// 人员选报销单位列表
	public static ArrayList getSectionsByPersonid(String personid) {
		String sql = "SELECT a.sectionid,a.section_code,a.section_name,b.sectiondetailid,b.sectiondetail_name,b.sectiondetail_code "
				+ " FROM g_sections a ,g_sectionsdetail b,g_personmansection c "
				+ " where a.sectionid = b.sectionid "
				+ " and b.sectiondetailid = c.sectiondetailid "
				+ " and c.personid = " + personid + " order by b.ordertag ";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 费用使用类型
	public static ArrayList get_fm_usetypes() {
		String sql = " select  usetypeid,usetypename from ("
				+ " select 0 as usetypeid , '通用+品牌' as usetypename from SYSIBM.SYSDUMMY1 "
				+ " union all "
				+ " select usetypeid,usetypename from FM_TYPE_USETYPE where isactive =1 "
				+ ") v order by usetypeid";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 转入仓库列表
	public static ArrayList getAllTrans_inWhouseList(String personid) {
		String sql = " select whouseid,whousename,whousealias "
				+ "	from "
				+ "	( "
				+ "	select 1 as whouseid,'全部' as whousename,'全部' as whousealias from SYSIBM.SYSDUMMY1 "
				+ "	union all "
				+ "	select whouseid,whousename,whousealias from G_WHOUSEDETAIL where is_trans_in =1"
				+ "	) v " + "	order by v.whouseid ";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 转出仓库列表
	public static ArrayList getAllTrans_outWhouseList(String personid) {
		String sql = " select whouseid,whousename,whousealias "
				+ "	from "
				+ "	( "
				+ "	select 1 as whouseid,'全部' as whousename,'全部' as whousealias from SYSIBM.SYSDUMMY1 "
				+ "	union all "
				+ "	select whouseid,whousename,whousealias from G_WHOUSEDETAIL where is_trans_out =1"
				+ "	) v " + "	order by v.whouseid ";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 转入仓库列表
	public static ArrayList getTrans_inWhouseList(String personid) {
		String sql = " select whouseid,whousename,whousealias from G_WHOUSEDETAIL where is_trans_in =1 "
				+ " order by whouseid ";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 转出仓库列表
	public static ArrayList getTrans_outWhouseList(String personid) {
		String sql = "select whouseid,whousename,whousealias from G_WHOUSEDETAIL ,g_persondest where is_trans_in =1 and whouseid = destid and personid = "
				+ personid + " order by whouseid ";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	// 转库原因
	public static ArrayList getTransReasonList(String personid) {
		String sql = " select reasonid,tshiftreasonoid,tshiftreasonname from MP_TRANS_REASON where isactive =1 "
				+ " order by reasonid ";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static ArrayList getcompbyproList(String prov, String personid) {
		String sql = "select a.sycompsystid,sycompalias sycompname from g_sycompany a,G_PERSONMANCOMP b where a.sycompsystid=b.sycompsystid and b.personid="
				+ personid
				+ " and a.isactive='1' and area_code like'"
				+ prov
				+ "%' order by sycompcode";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static ArrayList getPBContractList() {
		String sql = "select a.oid as id,a.type_name as name,a.description,a.order_tag from PB_CONTRACT_TYPE a where a.ISACTIVE=1 order by a.ORDER_TAG";
		ArrayList list;
		try {
			list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		} catch (Exception e) {
			list = new ArrayList();
			log.error(e.getMessage());
		}
		return list;
	}

	public static List getPubliccodes(int _classid) throws Exception {
		String sql = "select distinct classid,classcode,classname from g_publiccodes where parentclassid="
				+ String.valueOf(_classid);
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List getoareportstatus() throws Exception {
		String sql = "select distinct classid,classcode,classname from g_publiccodes where parentclassid=607 and classid not in (0,1) order by classcode";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List getoaplanstatus() throws Exception {
		return getPubliccodes(607);
	}

	public static List GetPutstate() throws Exception {
		String sql = "select id,name from R_PUTSTATE";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	// 配送方式
	public static List GetPutMethod() throws Exception {
		String sql = "select id,method as name from R_putMethod ORDER BY id";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	public static List getMpputGatherStateAll() throws Exception {
		String sql = "select  id,name from R_PUTSTATE   where   id <> -1";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	// 日常业务_考勤管理
	public static List getAttendanceStatusList() throws Exception {
		String sql = "select statusid as id,statusname as name from g_AttendanceStatus where isactive=1 order by ordertag";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	// 日常业务_留言板
	public static List GetDept() throws Exception {
		String sql = "select deptid as id,deptname as name from MP_FEEDBACKDEPT r order by r.id ";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	// 日常业务_留言板
	public static List GetDeptMain() throws Exception {
		String sql = "select mainid as id,mainname as name from MP_FEEDBACKDEPTMAIN r order by r.id ";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	// 日常业务_留言板
	public static List GetDeptByMainid(String mainId) throws Exception {
		String sql = "select deptid as id,deptname as name from MP_FEEDBACKDEPT r where r.mainid="
				+ mainId + " order by r.id ";
		return Executer.getInstance().ExecSeletSQL(sql).getResultSet();
	}

	/* com.nb.adv.common.TradeList 2013-01-23am End */
	/* com.zl.common.TradeList 2012-10-26pm Begin */
	// 从行记录中读取数据
	public static String getvalue(Object obj, String property) throws Exception {
		log.debug("读取：" + property);
		return String.valueOf(PropertyUtils.getProperty(obj, property));
	}

	private static String getSaveString(String value) {
		try {
			return "'" + MethodFactory.replace(value, "'", "''") + "'";
		} catch (Exception ex) {
			return "'" + value + "'";
		}
	}

	// 得到公共数据
	public static ArrayList getPubliccodes() throws Exception {
		String sql = "select * from g_publiccodes";
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	/*---------------------人员信息------------------------*/

	// 得到人员信息
	public static ArrayList<BasicDynaBean> getPersonInfo() throws Exception {
		return getPersonInfo("0");
	}

	// 得到人员信息
	public static ArrayList<BasicDynaBean> getPersonInfo(String personid)
			throws Exception {
		String sql = "select personid,logincode,password,personname,persontype,phonecode,isactive,"
				+ "case when persontype =1 then '超级用户' else '' end as persontypetitle,"
				+ "case when isactive =0 then '停用' else '' end isactivetitle from cms_g_personinfo";
		if (!personid.equals("0")) {
			sql = sql + " where personid=" + personid;
		}
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	// 判断编码是否存在
	public static ArrayList<BasicDynaBean> CheckPersonInfoForSave(
			String personid, String logincode) throws Exception {
		String sql = "select * from cms_g_personinfo WHERE UCASE(logincode)=UCASE("
				+ getSaveString(logincode) + ")";
		if (!personid.equals("0")) {
			sql = sql + " and personid <> " + personid;
		}
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	// 判断编码登陆信息
	public static ArrayList CheckPersonLogin(String logincode, String password)
			throws Exception {
		String sql = "select  *  from cms_g_personinfo WHERE UCASE(logincode)=UCASE("
				+ getSaveString(logincode)
				+ ") and password ="
				+ getSaveString(logincode) + "";
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	// 保存人员修改信息
	public static void SavePersonInfo(String personid, String logincode,
			String password, String personname, String persontype,
			String phonecode, String isactive) throws Exception {
		String sql = "update cms_g_personinfo set logincode="
				+ getSaveString(logincode) + "," + "password="
				+ getSaveString(logincode) + "," + "personname="
				+ getSaveString(personname) + "," + "persontype=" + persontype
				+ "," + "phonecode=" + getSaveString(phonecode) + ","
				+ "isactive =" + isactive + " where personid=" + personid;
		Executer.getInstance().ExecUpdateSQL(sql);
	}

	// 增加人员修改信息
	public static void AddPersonInfo(String logincode, String password,
			String personname, String persontype, String phonecode,
			String isactive) throws Exception {
		String sql = "insert into cms_g_personinfo (logincode,password,personname,persontype,phonecode,isactive) values("
				+ getSaveString(logincode)
				+ ","
				+ getSaveString(password)
				+ ","
				+ getSaveString(personname)
				+ ","
				+ persontype
				+ ","
				+ getSaveString(phonecode) + "," + isactive + ")";
		Executer.getInstance().ExecUpdateSQL(sql);
	}

	/*-------------------模板------------------------------*/
	// 得到模板信息
	public static ArrayList getTemplate() throws Exception {
		return getTemplate("0");
	}

	// 得到模板信息
	public static ArrayList getTemplate(String systemid) throws Exception {
		String sql = "select systemid,templatename,templatetype,templatepath,"
				+ "case when templatetype ='1' then '栏目模板' else '内容模板' end as templatetypetitle"
				+ " from CMS_template ";
		if (!systemid.equals("0")) {
			sql = sql + " where systemid=" + systemid;
		}
		log.debug(sql);
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	// 得到模板信息
	public static ArrayList getTemplateBytype(String templatetype)
			throws Exception {
		String sql = "select systemid,templatename,templatetype,templatepath,"
				+ "case when templatetype ='1' then '栏目模板' else '内容模板' end as templatetypetitle"
				+ " from CMS_template ";
		sql = sql + " where templatetype=" + "'" + templatetype + "'";
		log.debug(sql);
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	// 保存模板
	public static void saveTemplate(String systemid, String templatename,
			String templatetype, String templatepath) throws Exception {
		String sql = "";
		sql = "update CMS_template set templatename="
				+ getSaveString(templatename) + ",templatetype="
				+ getSaveString(templatetype) + ",templatepath="
				+ getSaveString(templatepath) + " where systemid =" + systemid;
		log.debug(sql);
		Executer.getInstance().ExecUpdateSQL(sql);
	}

	// 添加模板
	public static void addTemplate(String templatename, String templatetype,
			String templatepath) throws Exception {
		String sql = "insert into CMS_template (templatename,templatetype,templatepath) value("
				+ getSaveString(templatename)
				+ ","
				+ getSaveString(templatetype)
				+ ","
				+ getSaveString(templatepath) + ")";
		log.debug(sql);
		Executer.getInstance().ExecUpdateSQL(sql);
	}

	/*-----------------栏目维护---------------------*/
	public static ArrayList<BasicDynaBean> getChannelMenu(int level)
			throws Exception {
		String sql = "select * from cms_channel where OP_TOP='1' ";

		if (level >= 2) {
			sql = sql + " UNION "
					+ " select *   from cms_channel where  PARENTID in( "
					+ " select SYSTEMID from cms_channel where OP_TOP='1') ";
		}
		if (level >= 3) {
			sql = sql + " UNION "
					+ " select *   from cms_channel where  PARENTID in( "
					+ " select SYSTEMID from cms_channel where  PARENTID in( "
					+ " select SYSTEMID from cms_channel where OP_TOP='1'))";
		}

		log.debug(sql);
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	public static ArrayList<BasicDynaBean> getChannelforSelect(int level)
			throws Exception {

		String sql = "select -1 as systemid,'(顶级栏目)' as channel_name, -1 as parentid from (values(1)) as t union select systemid,channel_name,parentid from cms_channel where OP_TOP='1'";
		if (level >= 2) {
			sql = sql
					+ " UNION"
					+ " select a.systemid,CONCAT(CONCAT(b.channel_name ,'--') , a.channel_name) as channel_name,a.parentid from cms_channel a ,cms_channel b where a.parentid=b.systemid and a.PARENTID in("
					+ "select SYSTEMID from cms_channel where OP_TOP='1')";
		}
		if (level >= 3) {
			sql = sql
					+ "UNION"
					+ "select a.systemid,CONCAT(CONCAT(b.channel_name ,'--') , a.channel_name) as channel_name,a.parentid from cms_channel a,cms_channel b where a.parentid=b.systemid and a.PARENTID in( "
					+ "select SYSTEMID from cms_channel where OP_TOP='1' and PARENTID in( "
					+ "select SYSTEMID from cms_channel where OP_TOP='1'))";
		}
		log.debug(sql);
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	/*-----------------栏目维护---------------------*/
	public static ArrayList<BasicDynaBean> getChannel(String systemid)
			throws Exception {
		String sql = "select * from cms_channel where  systemid =" + systemid;
		log.debug(sql);
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	public static void saveChannel(String systemid, String channel_name,
			String desciption, String opindex, String op_top, String sortorder,
			String parentid) throws Exception {
		String sql = "update  cms_channel set  channel_name ="
				+ getSaveString(channel_name) + "," + "desciption="
				+ getSaveString(desciption) + "," + "opindex="
				+ getSaveString(opindex) + "," + "op_top=" + "'" + op_top + "'"
				+ "," + "sortorder=" + sortorder + "," + "parentid=" + parentid
				+ " where  systemid =" + systemid;
		log.debug(sql);
		Executer.getInstance().ExecUpdateSQL(sql);
	}

	public static boolean deleteChannel(String systemid) throws Exception {
		String sql = "";

		sql = "select * from cms_channel where parentid = " + systemid;
		log.debug(sql);
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		if (sqlReturn.getResultSet().size() != 0) {
			return true;
		} else {
			sql = "delete FROM cms_channel where systemid = " + systemid;
			log.debug(sql);
			Executer.getInstance().ExecUpdateSQL(sql);

			sql = "delete from cms_channel_content where channelid = "
					+ systemid;
			log.debug(sql);
			Executer.getInstance().ExecUpdateSQL(sql);

			sql = "update cms_article set channelid =0 where channelid = "
					+ systemid;
			log.debug(sql);
			Executer.getInstance().ExecUpdateSQL(sql);
			return false;
		}
	}

	public static void addChannel(String channel_name, String desciption,
			String opindex, String op_top, String sortorder, String parentid)
			throws Exception {
		String sql = "insert into cms_channel (channel_name,desciption,opindex,op_top,sortorder,parentid) values("
				+ getSaveString(channel_name)
				+ ","
				+ getSaveString(desciption)
				+ ","
				+ getSaveString(opindex)
				+ ","
				+ "'"
				+ op_top
				+ "'"
				+ ","
				+ sortorder + "," + parentid + ")";
		log.debug(sql);
		Executer.getInstance().ExecUpdateSQL(sql);
	}

	/*-------读取栏目中置顶的文章----------*/
	public static ArrayList getChannelTopArticle(String channelid)
			throws Exception {
		String sql = "select b.title,b.content,templateid ,a.articleid from  cms_channel_content a ,cms_article b where "
				+ " a.articleid= b.systemid and a.channelid ="
				+ channelid
				+ " and a.op_top='1' order by a.sortorder";
		log.debug(sql);
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	/*-------读取栏目中文章----------*/
	public static ArrayList getChannelArticle(String channelid)
			throws Exception {
		String sql = "select b.title,b.content,templateid ,a.articleid from  cms_channel_content a ,cms_article b where "
				+ " a.articleid= b.systemid and a.channelid ="
				+ channelid
				+ " order by a.op_top desc,a.sortorder";
		log.debug(sql);
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	/*-------读取栏目中显示在的子栏目----------*/
	public static ArrayList getChannelbytype(String channelid, String opindex)
			throws Exception {
		String sql = "select a.* from cms_channel a,cms_channel b where a.parentid =b.systemid and  "
				+ " b.systemid= " + channelid;
		if (channelid.equals("0")) {
			sql = "select a.* from cms_channel a  where 1=1 ";
		}
		if (!opindex.equals("")) {
			sql = sql + "  and a.opindex= " + opindex;
		}
		log.debug(sql);
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	/*------------内容维护--------------------*/
	public static ArrayList getarticle() throws Exception {
		return getarticle("0");
	}

	public static ArrayList getarticle(String systemid) throws Exception {
		String sql = "select * from cms_article";
		if (!systemid.equals("0")) {
			sql = sql + " where systemid=" + systemid;
		}
		log.debug(sql);
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	public static ArrayList getarticlebychannel(String systemid, String personid)
			throws Exception {
		String sql = "select * from cms_article";
		if (!systemid.equals("0")) {
			sql = "select a.* from cms_article a,cms_channel_content b where a.systemid=b.articleid and b.channelid ="
					+ systemid
					+ " and exists (select * from cms_articleright where articleid = a.systemid and personid = '"
					+ personid
					+ "')"
					+ " order by b.op_top desc,b.sortorder,a.dt_create desc";
		}

		log.debug(sql);
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	public static void deletearticle(String systemid) throws Exception {
		String sql = "";
		sql = "delete FROM cms_channel_content where articleid = " + systemid;

		log.debug(sql);
		Executer.getInstance().ExecUpdateSQL(sql);

		sql = "delete FROM cms_article where systemid = " + systemid;

		log.debug(sql);
		Executer.getInstance().ExecUpdateSQL(sql);

		sql = "delete FROM cms_articleright where articleid = " + systemid;

		log.debug(sql);
		Executer.getInstance().ExecUpdateSQL(sql);
	}

	public static void addAtical(String title, String remark, String u_creater,
			String r_media, String content, String templateid,
			String dt_create, String op_commit, String channelid)
			throws Exception {
		String sql = "";
		sql = "insert into  CMS_article (title,remark,u_creater,r_media,content,templateid,dt_create,dt_update,op_commit,channelid) values("
				+ getSaveString(title)
				+ ","
				+ getSaveString(remark)
				+ ","
				+ u_creater
				+ ","
				+ getSaveString(r_media)
				+ ","
				+ getSaveString(content)
				+ ","
				+ templateid
				+ ","
				+ getSaveString(dt_create)
				+ ","
				+ getSaveString(dt_create)
				+ "," + getSaveString(op_commit) + "," + channelid + ")";
		log.debug(sql);
		Executer.getInstance().ExecUpdateSQL(sql);

	}

	public static void saveAtical(String systemid, String title, String remark,
			String u_creater, String r_media, String content,
			String templateid, String dt_create, String op_commit,
			String channelid) throws Exception {
		String sql = "";
		sql = "update CMS_article set title=" + getSaveString(title)
				+ ",remark =" + getSaveString(remark) + ",r_media="
				+ getSaveString(r_media) + ",content ="
				+ getSaveString(content) + ",templateid =" + templateid
				+ ",dt_update=" + getSaveString(dt_create) + ",channelid="
				+ channelid + " where systemid=" + systemid;
		log.debug(sql);
		Executer.getInstance().ExecUpdateSQL(sql);
	}

	public static ArrayList getarticlechannel(String systemid) throws Exception {
		String sql = "select "
				+ " case WHEN b.parent_name is null then '' else b.parent_name ||'--' end"
				+ " ,case WHEN a.parent_name is null then '' else a.parent_name ||'--' end"
				+ " || a.channel_name as channel_name ,case when c.op_top ='1' then '是' else '否' end as op_top from  cms_channel_content c,cms_channel_view a "
				+ " LEFT join  cms_channel_view b  on a.parentid=b.systemid "
				+ " where a.systemid = c.CHANNELID and ARTICLEID=" + systemid;
		log.debug(sql);
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	/*-------------内容分配到栏目---------*/
	public static ArrayList getarticlechannel2(String systemid)
			throws Exception {
		String sql = "SELECT * FROM ( "
				+ " select A.SYSTEMID as channelid,"
				+ " case WHEN b.parent_name is null then '' else b.parent_name || '--' end "
				+ " ,case WHEN a.parent_name is null then '' else a.parent_name ||'--' end ||  a.channel_name as channel_name "
				+ " ,C.SORTORDER "
				+ " ,C.OP_TOP "
				+ " ,CASE WHEN C.SYSTEMID IS NULL THEN '0' ELSE '1' END CHECKED "
				+ " from cms_channel_view a "
				+ " LEFT join  cms_channel_view b  on a.parentid=b.systemid "
				+ " LEFT JOIN cms_channel_content C ON A.SYSTEMID =C.CHANNELID and c.ARTICLEID="
				+ systemid + " ) AS BB ORDER BY BB.CHANNEL_NAME ";
		log.debug(sql);
		SqlReturn sqlReturn = Executer.getInstance().ExecSeletSQL(sql);
		return sqlReturn.getResultSet();
	}

	public static void delcontentissue(Connection con, String systemid)
			throws Exception {
		String sql = "delete from cms_channel_content where ARTICLEID ="
				+ systemid;
		log.debug(sql);
		con.createStatement().execute(sql);
	}

	public static void saveissue(Connection con, String ARTICLEID,
			String CHANNELID, String SORTORDER, String OP_TOP) throws Exception {
		String sql = "insert into cms_channel_content(ARTICLEID,CHANNELID,SORTORDER,OP_TOP) values("
				+ ARTICLEID
				+ ","
				+ CHANNELID
				+ ","
				+ SORTORDER
				+ ",'"
				+ OP_TOP
				+ "')";
		con.createStatement().execute(sql);
	}

	// 项目管理模块
	public static ArrayList<BasicDynaBean> getAllArticle(String reqPage,
			int rowsPerPage, HttpServletRequest request) throws Exception {
		int curPage = Integer.parseInt((reqPage == null) ? "1" : reqPage);
		int rows = (rowsPerPage == 0) ? 20 : rowsPerPage;
		int maxPage = 1;
		String sql = null;
		SqlReturn sp2 = null;
		int num = 0;
		try {
			sql = "select title,articleid,filename,parentid ,operatdatetime,articleid as orderid, a.personid,b.personname from PRJ_ARTICLE a,g_personinfo b where a.personid=b.personid and parentid =0 and isdelete = 0 "
					+ " union select a.title,a.articleid,a.filename,a.parentid,a.operatdatetime,a.parentid as orderid,a.personid,c.personname from  PRJ_ARTICLE  a,PRJ_ARTICLE b,g_personinfo c where a.personid=c.personid and  a.parentid=b.articleid and b.isdelete = 0  and a.isdelete=0 ";
			System.out.println(sql);
			SqlReturn sp = Executer.getInstance().ExecSeletSQL(
					"select count(*) as count1 from (" + sql + ") as c");
			Object row = sp.getResultSet().get(0);
			String numtemp = String.valueOf(PropertyUtils.getProperty(row,
					"count1"));
			num = (new Integer(numtemp)).intValue();
			maxPage = (num + rows - 1) / rows;
			request.setAttribute("system.rst.count", String.valueOf(num));
			if (maxPage == 0)
				maxPage = 1;
			if (curPage > maxPage)
				curPage = maxPage;
			sql = "select * from (select rownumber() over(order by articleid desc,parentid) as id ,title,articleid,filename,parentid,articleid,personname,orderid,operatdatetime from ( "
					+ sql
					+ ") as d ) e where id between "
					+ ((curPage - 1) * rowsPerPage + 1)
					+ " and "
					+ (curPage * rowsPerPage)
					+ " order by orderid desc,parentid";
			System.out.println(sql);
			sp2 = Executer.getInstance().ExecSeletSQL(sql);
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
		Pagination pagination = new Pagination(curPage, rowsPerPage, maxPage);
		request.setAttribute("pagination", pagination);
		return sp2.getResultSet();
	}

	public static ArrayList<BasicDynaBean> getArticle(String articleid)
			throws Exception {
		String sql = "select title,articleid,filename,parentid ,operatdatetime,articleid as orderid, a.personid,b.personname from PRJ_ARTICLE a,g_personinfo b where a.personid=b.personid and a.articleid="
				+ articleid;
		SqlReturn sp2 = Executer.getInstance().ExecSeletSQL(sql);
		return sp2.getResultSet();
	}

	public static void DeleteArticle(String Articleid) throws Exception {
		String sql = "update PRJ_ARTICLE set isdelete =1 where articleid="
				+ Articleid;
		Executer.getInstance().ExecUpdateSQL(sql);
	}

	public static void AddArticle(String title, String filename,
			String personid, String time, String parentid) throws Exception {
		String sql = "";
		sql = "insert into PRJ_ARTICLE (title,parentid,filename,personid,operatdatetime,istop,isdelete) values('"
				+ MethodFactory.replace(title, "'", "''")
				+ "',"
				+ parentid
				+ ",'" + filename + "'," + personid + ",'" + time + "',0,0)";
		System.out.println(sql);
		Executer.getInstance().ExecUpdateSQL(sql);
	}

	public static void modifyArticle(String title, String time, String articleid)
			throws Exception {
		String sql = "";
		sql = "update PRJ_ARTICLE  set title ='"
				+ MethodFactory.replace(title, "'", "''")
				+ "',operatdatetime = '" + time + "' where articleid="
				+ articleid;
		System.out.println(sql);
		Executer.getInstance().ExecUpdateSQL(sql);
	}


	/* com.zl.util.OptionUtil 2013-01-23am Begin */
	public static List getcompany() {
		try {
			String sql = "select area_name as group,b.sycompalias  as name,sycompsystid as id ,a.area_code from g_area a,g_sycompany b "
					+ "where if_province='1' and gj_code is not null and a.area_code=b.area_code and a.isactive='1' and b.isactive='1' order by a.area_code ";
			ArrayList list = Executer.getInstance().ExecSeletSQL(sql)
					.getResultSet();
			return list;
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return null;
	}

	public static List getcompanyInfo(String id) {
		try {
			String sql = "select sycompalias as compname,address,compphone,bankaccount,taxno from  g_sycompany where sycompsystid="
					+ id;
			ArrayList list = Executer.getInstance().ExecSeletSQL(sql)
					.getResultSet();
			return list;
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return null;
	}
	/* com.zl.util.OptionUtil 2013-01-23am End */

}
