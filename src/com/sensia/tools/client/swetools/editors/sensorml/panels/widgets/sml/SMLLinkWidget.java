/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

/**
 * The Class SMLLinkWidget is corresponding to the <sml:Link> element.
 */
public class SMLLinkWidget extends AbstractSensorElementWidget{

	/** The container. */
	private HorizontalPanel container;
	
	/** The source panel. */
	private Panel sourcePanel;
	
	/** The destination panel. */
	private Panel destinationPanel;
	
	/** The edit panel. */
	private Panel editPanel;
	
	/**
	 * Instantiates a new SML link widget.
	 */
	public SMLLinkWidget() {
		super("Link", TAG_DEF.SML, TAG_TYPE.ELEMENT);
		
		container = new HorizontalPanel();
		sourcePanel = new SimplePanel();
		destinationPanel = new SimplePanel();
		
		SimplePanel linkIconPanel = new SimplePanel();
		linkIconPanel.addStyleName("link-icon-panel");
		
		//add line
		container.add(sourcePanel);
		container.add(linkIconPanel);
		container.add(destinationPanel);
		
		editPanel = getEditPanel(null);
		
		container.add(editPanel);
		activeMode(getMode());
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
		if(widget.getName().equals("source")) {
			sourcePanel.add(widget.getPanel());
		} else if(widget.getName().equals("destination")) {
			destinationPanel.add(widget.getPanel());
		} else {
			//container.add(widget.getPanel());
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLLinkWidget();
	}

}
