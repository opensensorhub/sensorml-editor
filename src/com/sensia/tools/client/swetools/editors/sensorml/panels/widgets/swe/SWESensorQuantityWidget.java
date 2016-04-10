/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.APPENDER;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_DEF;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_TYPE;

/**
 * The Class SWESensorQuantityWidget is corresponding to <swe:Quantity> element.
 */
public class SWESensorQuantityWidget extends AbstractSensorElementWidget{

	/** The quantity panel. */
	protected HorizontalPanel quantityPanel;
	
	/** The container. */
	protected HorizontalPanel container;
	
	/** The def panel. */
	protected HorizontalPanel defPanel;
	
	/** The uom panel. */
	protected HorizontalPanel uomPanel;
	
	/** The constraint panel. */
	protected HorizontalPanel constraintPanel;
	
	/** The has constraints. */
	protected boolean hasConstraints=false;
	
	/**
	 * Instantiates a new SWE sensor quantity widget.
	 */
	public SWESensorQuantityWidget() {
		this("Quantity");
	}

	/**
	 * Instantiates a new SWE sensor quantity widget.
	 *
	 * @param name the name
	 */
	public SWESensorQuantityWidget(String name) {
		super(name,TAG_DEF.SWE,TAG_TYPE.ELEMENT);
		
		container = new HorizontalPanel();
		init();
	}
	
	/**
	 * Inits the.
	 */
	private void init() {
		hasConstraints = false;
		defPanel = new HorizontalPanel();
		quantityPanel = new HorizontalPanel();
		uomPanel = new HorizontalPanel();
		constraintPanel = new HorizontalPanel();
		
		container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		defPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		uomPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		quantityPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		constraintPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		container.add(defPanel);
		container.add(quantityPanel);
		container.add(constraintPanel);
		container.add(uomPanel);
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
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && (widget.getName().equals("definition") || widget.getName().equals("referenceFrame"))){
			defPanel.add(widget.getPanel());
		} else if(widget.getType() == TAG_TYPE.ELEMENT && widget.getName().equals("uom")){
			uomPanel.add(widget.getPanel());
		} else if(widget.getType() == TAG_TYPE.ELEMENT && widget.getName().equals("value")){
			quantityPanel.add(widget.getPanel());
			if(hasConstraints) {
				quantityPanel.add(new HTML("in"+SensorConstants.HTML_SPACE));
			}
		} else if(widget.getType() == TAG_TYPE.ELEMENT && widget.getName().equals("constraint")){
			hasConstraints = true;
			constraintPanel.add(widget.getPanel());
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#refresh()
	 */
	@Override
	public void refresh() {
		//reset values
		for(ISensorWidget child : getElements()) {
	 		if(child.getType() == TAG_TYPE.ELEMENT && child.getName().equals("uom")){
	 			uomPanel.clear();
				uomPanel.add(child.getPanel());
			} else if(child.getType() == TAG_TYPE.ELEMENT && child.getName().equals("value")){
				quantityPanel.clear();
				quantityPanel.add(child.getPanel());
				if(hasConstraints) {
					quantityPanel.add(new HTML("in"+SensorConstants.HTML_SPACE));
				}
			} else if(child.getType() == TAG_TYPE.ELEMENT && child.getName().equals("constraint")){
				hasConstraints = true;
				constraintPanel.clear();
				constraintPanel.add(child.getPanel());
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorQuantityWidget();
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#appendTo()
	 */
	@Override
	public APPENDER appendTo() {
		return APPENDER.HORIZONTAL;
	}
}
