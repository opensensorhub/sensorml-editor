/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.source;

import com.google.gwt.user.client.ui.Panel;

/**
 * The Interface ISourcePanel defines a kind of source to load document.
 */
public interface ISourcePanel {

	/**
	 * Parses the content.
	 */
	public void parseContent();
	
	/**
	 * Gets the panel.
	 *
	 * @return the panel
	 */
	public Panel getPanel();
}
