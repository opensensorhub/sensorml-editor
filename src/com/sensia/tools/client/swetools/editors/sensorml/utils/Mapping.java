/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class Mapping.
 */
public final class Mapping {

	/** The map. */
	private static Map<String,String> map = new HashMap<String,String>();
	
	static {
		map.put("code", "unit");
	}
	
	/**
	 * Instantiates a new mapping.
	 */
	//protect the object
	private Mapping() {}
	
	/**
	 * Gets the corresponding value.
	 *
	 * @param value the value
	 * @return the corresponding value
	 */
	public static String getCorrespondingValue(String value) {
		if(map.containsKey(value)) {
			return map.get(value);
		} else {
			return value;
		}
	}
	
}
