/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.component;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

/**
 * The Class SMLSensorXLinkComponentWidget is corresponding to the "<sml:component>" element.
 */
public class SMLSensorXLinkComponentWidget extends AbstractSensorElementWidget{
	
	/** The container. */
	private HorizontalPanel container;
	
	/** The extras panel. */
	private HorizontalPanel extrasPanel;
	
	/** The edit panel. */
	private Panel editPanel;
	
	/** The anchor href. */
	private Anchor anchorHref;
	
	/** The is title provided. */
	private boolean isTitleProvided = false;
	
	/**
	 * Instantiates a new SML sensor x link component widget.
	 */
	public SMLSensorXLinkComponentWidget() {
		super("component", TAG_DEF.SML, TAG_TYPE.ELEMENT);
		container = new HorizontalPanel();
		extrasPanel = new HorizontalPanel();
		
		anchorHref = new Anchor();
		
		//opens in a new tab
		anchorHref.setTarget("_blank");
		
		//add anchor and extras stuff into the container
		container.add(anchorHref);
		container.add(extrasPanel);
		
		//add advanced panel
		editPanel = getEditPanel(new IButtonCallback() {
			
			@Override
			public void onClick() {
				isTitleProvided = false;
				extrasPanel.clear();
				for(ISensorWidget child : getElements()) {
					processWidget(child);
				}
			}
		});
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
		processWidget(widget);
	}

	/**
	 * Process widget.
	 *
	 * @param widget the widget
	 */
	private void processWidget(ISensorWidget widget) {
		//if the title attribute is not provided, gets the name attribute instead
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("name") && !isTitleProvided) {
			anchorHref.setText(widget.getValue("name", true));
		} else if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("title")) {
			//set the text value with the value of the title attribute
			anchorHref.setText(widget.getValue("title", true));
			isTitleProvided = true;
		} else if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("href")) {
			//use the href attribute as the href value of the anchor
			anchorHref.setHref(Utils.getCurrentURL(widget.getValue("href", true)));
		} else {
			//add any extra stuff
			extrasPanel.add(widget.getPanel());
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLSensorXLinkComponentWidget();
	}
}
