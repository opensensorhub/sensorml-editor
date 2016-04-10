/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd;

import com.sensia.relaxNG.XSDDateTime;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;

/**
 * The Class SensorXSDDateTimeWidget.
 */
public class SensorXSDDateTimeWidget extends SensorXSDWidget{

	/** The Constant LENGTH. */
	private static final int LENGTH = 28;
	
	/** The data. */
	private XSDDateTime data;
	
	/**
	 * Instantiates a new sensor xsd date time widget.
	 *
	 * @param data the data
	 */
	public SensorXSDDateTimeWidget(XSDDateTime data) {
		super(data,LENGTH,null);
		this.data = data;
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorXSDDateTimeWidget(data);
	}
}
