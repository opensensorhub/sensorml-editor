/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd;

import com.sensia.relaxNG.XSDAnyURI;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;

/**
 * The Class SensorXSDAnyURIWidget.
 */
public class SensorXSDAnyURIWidget extends SensorXSDWidget{
	
	/** The data. */
	private XSDAnyURI data;
	
	/**
	 * Instantiates a new sensor xsd any uri widget.
	 *
	 * @param data the data
	 */
	public SensorXSDAnyURIWidget(final XSDAnyURI data) {
		super(data,getLength(data),null);
		this.data = data;
	}
	
	/**
	 * Gets the length.
	 *
	 * @param data the data
	 * @return the length
	 */
	private static int getLength(XSDAnyURI data){
		int length = 60;
        
        int fixedLength = data.getLength();
        if (fixedLength > 0)
            length = fixedLength;
        
        int maxLength = data.getMaxLength();
        if (maxLength > 0)
            length = maxLength;
        
        return length;
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorXSDAnyURIWidget(data);
	}
}
