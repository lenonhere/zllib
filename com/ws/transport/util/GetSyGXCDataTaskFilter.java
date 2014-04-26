package com.ws.transport.util;

import java.util.Map;

class GetSyGXCDataTaskFilter implements RowFilter {
	public Map filter(Map row) {
		float val = 1.0f;

		val = Float.parseFloat(row.get("PURCHASE_QUANTITY").toString()) * 10000;
		row.put("PURCHASE_QUANTITY", new Float(val));

		val = Float.parseFloat(row.get("SALE_QUANTITY").toString()) * 10000;
		row.put("SALE_QUANTITY", new Float(val));

		val = Float.parseFloat(row.get("INVENTORY_QUANTITY").toString()) * 10000;
		row.put("INVENTORY_QUANTITY", new Float(val));

		return row;
	}
}
