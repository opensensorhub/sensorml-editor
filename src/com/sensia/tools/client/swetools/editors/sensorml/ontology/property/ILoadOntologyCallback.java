/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.ontology.property;

import java.util.List;

/**
 * The Interface ILoadOntologyCallback callback the result after loading data. 
 */
public interface ILoadOntologyCallback {

	/**
	 * On load.
	 *
	 * @param headers the headers
	 * @param data the data
	 */
	void onLoad(List<String> headers, Object[][] data);
}
