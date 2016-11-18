/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.generic;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel.MODE;

/**
 * The Class SensorGenericHorizontalContainerWidget is a generic horizontal container.
 */
public class GenericHorizontalContainerPanel extends AbstractPanel<RNGTag>{

	/** The container. */
	protected HorizontalPanel container;
	
	/**
	 * Instantiates a new sensor generic horizontal container widget.
	 *
	 * @param name the name
	 * @param def the def
	 * @param type the type
	 */
	public GenericHorizontalContainerPanel(RNGTag tag) {
		super(tag);
		container = new HorizontalPanel();
		//container.setSpacing(5);
		container.addStyleName("swe-generic-horizontal-panel");
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getPanel()
	 */
	@Override
	public Panel getPanel() {
		return container;
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	public void addInnerElement(IPanel<? extends RNGTag> panel) {
		/*if(widget instanceof SensorGenericHorizontalContainerWidget) {
			for(IPanel child : widget.getElements()) {
				container.add(child.getPanel());
			}
		}else {
			container.add(widget.getPanel());
		}
		container.add(new HTML("&nbsp;&nbsp;"));*/
		container.add(panel.getPanel());
		container.add(new HTML("&nbsp;&nbsp;"));
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#activeMode(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE)
	 */
	@Override
	protected void activeMode(MODE mode) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractPanel<RNGTag> newInstance() {
		return new GenericHorizontalContainerPanel(getTag());
	}

	@Override
	public String getName() {
		return getTag().toString();
	}
}
