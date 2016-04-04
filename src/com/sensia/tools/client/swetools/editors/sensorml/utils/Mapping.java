package com.sensia.tools.client.swetools.editors.sensorml.utils;

import java.util.HashMap;
import java.util.Map;

public final class Mapping {

	private static Map<String,String> map = new HashMap<String,String>();
	
	static {
		map.put("code", "unit");
	}
	//protect the object
	private Mapping() {}
	
	public static String getCorrespondingValue(String value) {
		if(map.containsKey(value)) {
			return map.get(value);
		} else {
			return value;
		}
	}
	
}
