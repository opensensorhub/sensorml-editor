/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

/**
 * The Class SMLSensorEventWidget is corresponding to the <sml:Event> element.
 */
public class SMLSensorEventWidget extends AbstractSensorElementWidget{

	/** The container. */
	private Panel container;
	
	/** The label panel. */
	private Panel labelPanel;
	
	/** The def panel. */
	private Panel defPanel;
	
	/** The time panel. */
	private Panel timePanel;
	
	/**
	 * Instantiates a new SML sensor event widget.
	 */
	public SMLSensorEventWidget() {
		super("Event", TAG_DEF.SML, TAG_TYPE.ELEMENT);
		container = new HorizontalPanel();
		init();
	}

	/**
	 * Inits the widget.
	 */
	private void init() {
		defPanel = new HorizontalPanel();
		labelPanel = new HorizontalPanel();
		timePanel = new HorizontalPanel();
		
		container.add(labelPanel);
		container.add(defPanel);
		container.add(new HTML("-->"+SensorConstants.HTML_SPACE));
		container.add(timePanel);
		
		//add advanced panel
		Panel advancedPanel = getEditPanel(new IButtonCallback() {
			@Override
			public void onClick() {
				refreshChildren(getElements());
				refreshParents(getParent());
			}
		});
		
		container.add(advancedPanel);
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getPanel()
	 */
	@Override
	public Panel getPanel() {
		return container;
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#activeMode(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE)
	 */
	@Override
	protected void activeMode(MODE mode) {
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getName().equals("label")) {
			labelPanel.add(widget.getPanel());
		} else if(widget.getName().equals("timePosition")) {
			timePanel.add(widget.getPanel());
		} else if(widget.getName().equals("definition")) {
			defPanel.add(widget.getPanel());
		} 
		//skip others elements
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLSensorEventWidget();
	}
}
