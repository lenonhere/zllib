package com.common;

import java.util.*;

import com.zl.base.core.db.CallHelper;

public class JGTobaccoManager {
	public static List getJGTobaccoById(String tobaccoid){
        CallHelper caller = new CallHelper("getJGTobaccoById");
        caller.setParam("tobaccoID", tobaccoid);
        caller.execute();
		return caller.getResult(0);
	}
}
