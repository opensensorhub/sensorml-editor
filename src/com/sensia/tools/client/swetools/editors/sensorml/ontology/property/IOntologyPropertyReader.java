/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.ontology.property;

/**
 * The Interface IOntologyPropertyReader loads an ontology and callback the result.
 */
public interface IOntologyPropertyReader {

	/**
	 * Load ontology.
	 *
	 * @param url the url
	 * @param callback the callback
	 */
	void loadOntology(String url,ILoadOntologyCallback callback);
}
