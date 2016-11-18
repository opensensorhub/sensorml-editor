/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml;

import java.io.Serializable;

import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;

/**
 * An asynchronous update interface for receiving notifications
 * about IParsing information as the IParsing is constructed.
 */
public interface IParsingObserver extends Serializable{

	/**
	 * Callback the tree of created Widgets .
	 *
	 * @param topElement the top element
	 */
	void parseDone(IPanel topElement);
}
