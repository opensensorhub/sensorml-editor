/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

/**
 * The Class SWESensorAllowedValuesWidget is corresponding to <swe:ALlowedValues>.
 */
public class SWESensorAllowedValuesWidget extends AbstractSensorElementWidget{

	/** The container. */
	private Panel container;
	
	/** The inner container. */
	private Panel innerContainer;
	
	/** The values panel. */
	//handle <swe:value>
	private Panel valuesPanel;
	
	/** The interval panel. */
	//handle <swe:interval>
	private Panel intervalPanel;
	
	
	/** The values. */
	private HTML values;
	
	/**
	 * Instantiates a new SWE sensor allowed values widget.
	 */
	public SWESensorAllowedValuesWidget() {
		super("AllowedValues",TAG_DEF.SWE,TAG_TYPE.ELEMENT);
		
		this.container = new HorizontalPanel();
	
		init();
	}

	/**
	 * Inits the widget.
	 */
	private void init() {
		//add [] to encapsulates values representation
		HTML b = new HTML("[");
		HTML a = new HTML("]");
		innerContainer = new HorizontalPanel();
		
		this.container.add(b);
		this.container.add(innerContainer);
		this.container.add(a);
		
		//add styles
		a.addStyleName("constraint-values");
		b.addStyleName("constraint-values");
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
		if(widget.getName().equals("value") && widget.getType() == TAG_TYPE.ELEMENT) {
			//if a single value is found
			String value = widget.getElements().get(0).getName();
			if(valuesPanel == null) {
				valuesPanel = new HorizontalPanel();
				values = new HTML(value);
				values.addStyleName("constraint-values");
				valuesPanel.add(values);
				innerContainer.add(valuesPanel);
			} else {
				values.setHTML(values.getHTML()+" / "+value);
			}
			
			
		} else if(widget.getName().equals("interval") && widget.getType() == TAG_TYPE.ELEMENT) {
			//if an interval of values is found
			String interval = widget.getElements().get(0).getName();
			String [] spaceSplit = interval.split(" ");
			if(intervalPanel == null) {
				intervalPanel = new HorizontalPanel();
				innerContainer.add(intervalPanel);
			}
			HTML values = new HTML(spaceSplit[0]+";"+spaceSplit[1]);
			values.addStyleName("constraint-values");
			intervalPanel.add(values);
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#refresh()
	 */
	@Override
	public void refresh() {
		//clears all
		container.clear();
		intervalPanel = null;
		valuesPanel = null;
		
		//adds elements again
		init();
		for(ISensorWidget child : getElements()) {
			addSensorWidget(child);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorAllowedValuesWidget();
	}

}
