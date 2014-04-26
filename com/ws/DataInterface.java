package com.ws;


public class DataInterface {

	ContractList contract = new ContractList();

	public String getContractDetail(String userid, String password,
			String shippingdate) {
		String retString;
		synchronized (contract) {
			retString = contract.getContractDetail(userid, password,
					shippingdate);
		}
		return retString;
	}

}
