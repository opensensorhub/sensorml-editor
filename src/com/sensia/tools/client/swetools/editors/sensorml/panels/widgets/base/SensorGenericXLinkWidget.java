/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

/**
 * The Class SensorGenericXLinkWidget is corresponding to the "xlink" attribute.
 */
public class SensorGenericXLinkWidget extends AbstractSensorElementWidget{

	/** The container. */
	private Panel container;
	
	/** The anchor href. */
	private Anchor anchorHref;
	
	/** Defines if the title is provided */
	private boolean isTitleProvided = false;
	
	/**
	 * Instantiates a new sensor generic x link widget.
	 *
	 * @param name the name
	 * @param def the def
	 */
	public SensorGenericXLinkWidget(String name, TAG_DEF def) {
		super(name, def, TAG_TYPE.ATTRIBUTE);
		container = new HorizontalPanel();
		
		//build xlink:href givent an element
		anchorHref = new Anchor();
		container.add(anchorHref);
		//display the href as a nice label
		anchorHref.setText("("+toNiceLabel(getName())+")");
		anchorHref.addStyleName("xlink");
		//opens in a new tab
		anchorHref.setTarget("_blank");
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
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#refresh()
	 */
	@Override
	public void refresh() {
		anchorHref.setText("("+toNiceLabel(getName())+")");
		container.clear();
		container.add(anchorHref);
		for(ISensorWidget child : getElements()) {
			addSensorWidget(child);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getName().equals("name")) {
			if(!isTitleProvided) {
				anchorHref.setText(widget.getValue("name", true));
			}
		} else if(widget.getName().equals("title")) {
			anchorHref.setText(widget.getValue("title", true));
			isTitleProvided = true;
		} else if (widget.getName().equals("href")) {
			anchorHref.setHref(widget.getValue("href", true));
		} else {
			container.add(widget.getPanel());
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorGenericXLinkWidget(getName(), getDef());
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#appendTo()
	 */
	@Override
	public APPENDER appendTo() {
		return APPENDER.HORIZONTAL;
	}

}
