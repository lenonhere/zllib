package com.ws;

import java.util.List;

import com.zl.base.core.db.CallHelper;

public class ContractList {

	private static String contactStr = "";
	private static MakeXML makeXml = new MakeXML();

	public String getContractDetail(String userid, String password,
			String shippingdate) {

		CallHelper caller = new CallHelper("getContractListForWS");
		caller.setParam("userid", "");
		caller.setParam("password", "");
		caller.setParam("shippingdate", shippingdate);
		caller.execute();
		List resultList = caller.getResult(0);
		String[] resultNames = { "contractcode", "sycompany_id",
				"shipping_date", "arrival_date", "revised_quantity",
				"ischecked", "dbdcomment", "qdcomment", "shipmodeoid",
				"sycompalias", "gj_code", "tobaname", "bandcode",
				"includeprice" };
		return contactStr = makeXml
				.makeXml("contract", resultList, resultNames);

	}
}
