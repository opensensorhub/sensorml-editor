/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.ontology.property;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Property is used to populate ontology tables.
 */
public class Property {

	/** The properties. */
	public List<String> properties;
	
	/**
	 * Instantiates a new property.
	 *
	 * @param size the size
	 */
	public Property(final int size) {
		properties=new ArrayList<String>(size);
		for(int i=0;i< size;i++) {
			properties.add(null);
		}
	}
}
