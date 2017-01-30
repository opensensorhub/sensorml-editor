/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.generic;

import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;

/**
 * The Class SensorGenericVerticalContainerWidget is a vertical generic container.
 */
public class GenericVerticalContainerPanel extends AbstractPanel<RNGTag>{

	/**
	 * Instantiates a new sensor generic vertical container widget.
	 *
	 * @param name the name
	 * @param def the def
	 * @param type the type
	 */
	public GenericVerticalContainerPanel(RNGTag tag) {
		super(tag);
		container = new SMLVerticalPanel();
	}

	public GenericVerticalContainerPanel() {
		container = new SMLVerticalPanel();
	}
	
	public GenericVerticalContainerPanel(boolean spacing) {
		container = new SMLVerticalPanel(spacing);
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	public void addInnerElement(IPanel<? extends RNGTag> widget) {
		container.add(widget.getPanel());
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractPanel<RNGTag> newInstance() {
		return new GenericVerticalContainerPanel(getTag());
	}
	
	@Override
	public String getName() {
		if(getTag() == null) {
			return "Generic vertical container";
		} else {
			return getTag().toString();
		}
	}
}
