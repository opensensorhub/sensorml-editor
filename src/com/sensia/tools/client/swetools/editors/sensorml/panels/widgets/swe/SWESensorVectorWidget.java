/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe;

import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorGenericVerticalContainerWidget;

/**
 * The Class SWESensorVectorWidget is corresponding to <swe:Vector> element.
 */
public class SWESensorVectorWidget extends SensorGenericVerticalContainerWidget{

	/**
	 * Instantiates a new SWE sensor vector widget.
	 */
	public SWESensorVectorWidget() {
		super("Vector", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
		
		container.addStyleName("swe-generic-vertical-panel");
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#appendTo()
	 */
	@Override
	public APPENDER appendTo() {
		return APPENDER.OVERRIDE_LINE;
	}
}
