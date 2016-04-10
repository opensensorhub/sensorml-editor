/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe;

import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

/**
 * The Class SWESensorTimeRangeWidget is corresponding to <swe:TimeRange> element.
 */
public class SWESensorTimeRangeWidget extends SWESensorQuantityRangeWidget{

	/**
	 * Instantiates a new SWE sensor time range widget.
	 */
	public SWESensorTimeRangeWidget() {
		super("TimeRange");
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.SWESensorQuantityRangeWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getType() == TAG_TYPE.ATTRIBUTE &&  widget.getName().equals("referenceFrame")){
			defPanel.add(widget.getPanel());
		} 
		super.addSensorWidget(widget);
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.SWESensorQuantityRangeWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorTimeRangeWidget();
	}
}
