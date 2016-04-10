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
 * The Class SWESensorQuantityRangeWidget is corresponding to <swe:QuantityRange> element.
 */
public class SWESensorQuantityRangeWidget extends AbstractSensorElementWidget{

	/** The value panel. */
	protected HorizontalPanel valuePanel;
	
	/** The container. */
	protected HorizontalPanel container;
	
	/** The def panel. */
	protected HorizontalPanel defPanel;
	
	/** The uom panel. */
	protected HorizontalPanel uomPanel;
	
	/** The constraint panel. */
	protected HorizontalPanel constraintPanel;
	
	/** The range value widget. */
	protected ISensorWidget rangeValueWidget;
	
	/** The has constraints. */
	protected boolean hasConstraints=false;
	
	/**
	 * Instantiates a new SWE sensor quantity range widget.
	 */
	public SWESensorQuantityRangeWidget() {
		this("QuantityRange");
	}

	/**
	 * Instantiates a new SWE sensor quantity range widget.
	 *
	 * @param name the name
	 */
	public SWESensorQuantityRangeWidget(final String name) {
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
		valuePanel = new HorizontalPanel();
		uomPanel = new HorizontalPanel();
		constraintPanel = new HorizontalPanel();
		
		container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		defPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		uomPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		valuePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		container.add(valuePanel);
		container.add(constraintPanel);
		container.add(uomPanel);
		container.add(defPanel);
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
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("definition")){
			defPanel.add(widget.getPanel());
		} else if(widget.getType() == TAG_TYPE.ELEMENT && widget.getName().equals("uom")){
			uomPanel.add(new HTML(SensorConstants.HTML_SPACE));
			uomPanel.add(widget.getPanel());
		} else if(widget.getName().equals("value") && widget.getType() == TAG_TYPE.ELEMENT && widget.getDef() == TAG_DEF.SWE){
			//display value as "min to max"
			String interval = widget.getElements().get(0).getName();
			String [] spaceSplit = interval.split(" ");
			HTML values = new HTML(spaceSplit[0]+" to "+spaceSplit[1]);
			valuePanel.add(values);
			//appends constraints if any
			if(hasConstraints) {
				valuePanel.add(new HTML(SensorConstants.HTML_SPACE+"in"+SensorConstants.HTML_SPACE));
			}
		} else if(widget.getType() == TAG_TYPE.ELEMENT && widget.getName().equals("constraint")){
			hasConstraints = true;
			constraintPanel.add(widget.getPanel());
		}
		
		//add advanced panel
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
				uomPanel.add(new HTML(SensorConstants.HTML_SPACE));
				uomPanel.add(child.getPanel());
			} else if(child.getName().equals("value") && child.getType() == TAG_TYPE.ELEMENT && child.getDef() == TAG_DEF.SWE){
				valuePanel.clear();
				String interval = child.getElements().get(0).getName();
				String [] spaceSplit = interval.split(" ");
				HTML values = new HTML(spaceSplit[0]+" to "+spaceSplit[1]);
				valuePanel.add(values);
				if(hasConstraints) {
					valuePanel.add(new HTML(SensorConstants.HTML_SPACE+"in"+SensorConstants.HTML_SPACE));
				}
			} else if(child.getType() == TAG_TYPE.ELEMENT && child.getName().equals("constraint")){
				constraintPanel.clear();
				hasConstraints = true;
				constraintPanel.add(child.getPanel());
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorQuantityRangeWidget();
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#appendTo()
	 */
	@Override
	public APPENDER appendTo() {
		return APPENDER.HORIZONTAL;
	}
}
