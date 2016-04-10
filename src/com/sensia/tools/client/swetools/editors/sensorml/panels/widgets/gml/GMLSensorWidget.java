/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.gml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_DEF;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_TYPE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorGenericHorizontalContainerWidget;

/**
 * Corresponding to : <element name="gml:description">.
 *
 * @author mathieu dhainaut
 */
public class GMLSensorWidget extends SensorGenericHorizontalContainerWidget{

	/**
	 * Instantiates a new GML sensor widget.
	 *
	 * @param elt the elt
	 */
	public GMLSensorWidget(final RNGElement elt) {
		super(elt.getName(), TAG_DEF.GML, TAG_TYPE.ELEMENT);
	}
}
