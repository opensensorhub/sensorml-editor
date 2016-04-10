/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd;

import com.sensia.relaxNG.RNGData;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;

/**
 * The Class SensorXSDDecimalWidget.
 */
public class SensorXSDDecimalWidget extends SensorXSDWidget{

	/** The Constant LENGTH. */
	private static final int LENGTH = 10;
	
	/** The Constant ALLOWED_CHARS. */
	private static final String ALLOWED_CHARS = ".-+0123456789";
	
	/** The data. */
	private RNGData<?> data;
	
	/**
	 * Instantiates a new sensor xsd decimal widget.
	 *
	 * @param data the data
	 */
	public SensorXSDDecimalWidget(RNGData<?> data) {
		super(data,LENGTH,ALLOWED_CHARS);
		this.data = data;
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		// TODO Auto-generated method stub
		return new SensorXSDDecimalWidget(data);
	}
}
