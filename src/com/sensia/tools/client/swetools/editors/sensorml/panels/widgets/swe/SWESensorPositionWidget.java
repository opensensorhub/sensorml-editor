/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.SWESensorPositionByDataRecordWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.SWESensorPositionByDescriptionWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.SWESensorPositionByPointWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.SWESensorPositionByTrajectoryWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.SWESensorPositionByVectorWidget;

/**
 * The Class SWESensorPositionWidget is corresponding to <swe:position>.
 */
public class SWESensorPositionWidget extends AbstractSensorElementWidget{

	/** The sensor position panel. */
	private ISensorWidget sensorPositionPanel;
	
	/** The container. */
	private Panel container;
	
	/**
	 * Instantiates a new SWE sensor position widget.
	 */
	public SWESensorPositionWidget() {
		super("position", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
		container = new HorizontalPanel();
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getPanel()
	 */
	@Override
	public Panel getPanel() {
		if(sensorPositionPanel != null) {
			return sensorPositionPanel.getPanel();
		} else {
			return container;
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		//container.add(widget.getPanel());
		//We have to handle many cases:
		//Position by description
		//Position by Point (gml:point)
		//Position by Location (swe:Vector)
		//Position by Position (swe:DataRecord)
		//Position by Trajectory (swe:DataArray)
		//Position by Process (sml:SimpleProcess or sml:AggregateProcess or sml:PhysicalComponent or sml:PhysicalSystem)
		
		if(widget.getName().equals("DataRecord")) {
			sensorPositionPanel = new SWESensorPositionByDataRecordWidget();
		} else if(widget.getName().equals("Vector")) {
			sensorPositionPanel = new SWESensorPositionByVectorWidget();
		} else if(widget.getName().equals("Text")) {
			sensorPositionPanel = new SWESensorPositionByDescriptionWidget();
		} else if(widget.getName().equals("Point")) {
			sensorPositionPanel = new SWESensorPositionByPointWidget();
		} else if(widget.getName().equals("DataArray")) {
			sensorPositionPanel = new SWESensorPositionByTrajectoryWidget();
		}
		
		if(sensorPositionPanel != null) {
			sensorPositionPanel.addElement(widget);
		}
		
		if(sensorPositionPanel == null) {
			GWT.log("unsuported Position, take a default container");
			container.add(widget.getPanel());
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#refresh()
	 */
	@Override
	public void refresh () {
		if(sensorPositionPanel != null) {
			sensorPositionPanel.refresh();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionWidget();
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#activeMode(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE)
	 */
	@Override
	protected void activeMode(MODE mode) {
		if(sensorPositionPanel != null) {
			sensorPositionPanel.switchMode(mode);
		}
	}
}
