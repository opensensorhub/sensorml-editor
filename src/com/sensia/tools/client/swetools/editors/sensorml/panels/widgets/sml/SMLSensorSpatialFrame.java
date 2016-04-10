/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorGenericVerticalContainerWidget;

/**
 * The Class SMLSensorSpatialFrame is corresponding to <sml:SpatialFrame> element.
 */
public class SMLSensorSpatialFrame extends SensorGenericVerticalContainerWidget{

	/** The label panel. */
	private HorizontalPanel labelPanel;
	
	/** The description panel. */
	private HorizontalPanel descriptionPanel;
	
	/** The id. */
	private String id = "";
	
	/**
	 * Instantiates a new SML sensor spatial frame.
	 */
	public SMLSensorSpatialFrame() {
		super("SpatialFrame", TAG_DEF.SML, TAG_TYPE.ELEMENT);
		
		labelPanel = new HorizontalPanel();
		labelPanel.add(new HTML("Spatial Frame"));
		descriptionPanel = new HorizontalPanel();
		
		descriptionPanel.setVisible(false);
		
		container.add(labelPanel);
		container.add(descriptionPanel);
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorGenericVerticalContainerWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("id")) {
			id = widget.getValue("id", true);
			labelPanel.add(new HTML("&nbsp;("+id+")"));
		} else if(widget.getDef() == TAG_DEF.SWE) {
			if(widget.getName().equals("label")) {
				labelPanel.clear();
				labelPanel.add(widget.getPanel());
				if(!id.isEmpty()) {
					labelPanel.add(new HTML("&nbsp;("+id+")"));
				}
			} else if(widget.getName().equals("description")) {
				descriptionPanel.add(widget.getPanel());
				descriptionPanel.setVisible(true);
			}
		} else {
			super.addSensorWidget(widget);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorGenericVerticalContainerWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLSensorSpatialFrame();
	}

}
