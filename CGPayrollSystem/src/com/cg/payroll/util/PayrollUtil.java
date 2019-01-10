package com.cg.payroll.util;

import java.util.HashMap;
import java.util.Map;

import com.cg.payroll.beans.Associate;

public class PayrollUtil {
	public static int ASSOCIATE_ID_COUNTER= 100;

	public static Map<Integer, Associate> associates= new HashMap<>();

	public static int getASSOCIATE_ID_COUNTER() {
		return ++ASSOCIATE_ID_COUNTER;
	}

}
