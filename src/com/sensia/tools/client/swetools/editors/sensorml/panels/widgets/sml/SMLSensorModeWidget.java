/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

/**
 * The Class SMLSensorModeWidget is corresponding to the <sml:Mode> element.
 */
public class SMLSensorModeWidget extends AbstractSensorElementWidget{

	/** The Constant CSS_CLASS. */
	private static final String CSS_CLASS = "sml-mode-panel";
	
	/** The hide panel. */
	private DisclosurePanel hidePanel;
	
	/** The content panel. */
	private VerticalPanel contentPanel;
	
	/** The container. */
	private Panel container;
	
	/** The current header. */
	private HasText currentHeader;
	
	/**
	 * Instantiates a new SML sensor mode widget.
	 */
	public SMLSensorModeWidget() {
		super("Mode",TAG_DEF.SML,TAG_TYPE.ELEMENT);
		
		hidePanel = new DisclosurePanel("Mode");
        hidePanel.setAnimationEnabled(true);
        hidePanel.setOpen(true);
        hidePanel.addStyleName(CSS_CLASS);
        
        contentPanel = new VerticalPanel();	
        hidePanel.setContent(contentPanel);
        
        container = new FlowPanel();
        container.add(hidePanel);
        
        contentPanel.addStyleName("sml-mode-inner-panel");
        
        currentHeader = hidePanel.getHeaderTextAccessor();
        hidePanel.getHeader().addStyleName("sml-mode-title");
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
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		//if id attribute is found, set the value into the header
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("id")) {
			currentHeader.setText(toNiceLabel(widget.getValue(widget.getName(), true)));
		} else if(widget.getName().equals("characteristics")) {
			//special case of characteristics
			//skip and add its children
			for(ISensorWidget child : widget.getElements()) {
				addSensorWidget(child);
			}
		} else {
			contentPanel.add(widget.getPanel());
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLSensorModeWidget();
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#appendTo()
	 */
	public APPENDER appendTo() {
		return APPENDER.OVERRIDE_LINE;
	}

}
