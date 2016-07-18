/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Mapping;


/**
 * The Class SensorValueWidget is corresponding of the RNG value element. 
 * Any value tag will be instantiated through that class.
 */
public class SensorValueWidget extends AbstractSensorElementWidget{

	/** The container. */
	private Panel container;
	
	/** The rng value. */
	private RNGValue rngValue;
	
	/** The value box. */
	private TextBox valueBox;
	
	/**
	 * Instantiates a new sensor value widget.
	 *
	 * @param value the value
	 * @param rngValue the rng value
	 */
	public SensorValueWidget(String value,RNGValue rngValue) {
		super(value, TAG_DEF.RNG, TAG_TYPE.VALUE);
		
		this.rngValue = rngValue;
		container = new HorizontalPanel();
		container.add(new HTML(value));
		
		valueBox = new TextBox();
		
		if(value != null) {
			//remove blank space, return lines
			valueBox.setText(value.replaceAll("\\s+", " "));
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getPanel()
	 */
	@Override
	public Panel getPanel() {
		return container;
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) { 
		container.add(widget.getPanel());
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#activeMode(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE)
	 */
	@Override
	protected void activeMode(MODE mode) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorValueWidget(getName(),rngValue);
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#setValue(java.lang.String, java.lang.String)
	 */
	@Override
	public void setValue(String elementName,String value) {
		if(elementName.equals(getParent().getName())) {
			setValue(value);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#getAdvancedPanel(com.google.gwt.user.client.ui.Panel)
	 */
	@Override
	public void getAdvancedPanel(Panel container) {
		HorizontalPanel hPanel = new HorizontalPanel();
		
		//get the mapped value if any
		HTML hlabel = new HTML(Mapping.getCorrespondingValue(getParent().getName()));
		hlabel.setWidth("100px");
		hPanel.add(hlabel);
		
		valueBox.setWidth("500px");
		
		hPanel.add(valueBox);
		//enable the textfield if the mode is in EDIT
		valueBox.setEnabled(getMode() == MODE.EDIT);
		container.add(hPanel);
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#refresh()
	 */
	@Override
	public void refresh() {
		//clears the content and add the new value
		String value = valueBox.getText();
		container.clear();
		container.add(new HTML(value));
		setName(value);
		//set the value to the RNG model
		this.rngValue.setText(value);
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	private void setValue(String value) {
		container.clear();
		container.add(new HTML(value));
		setName(value);
		valueBox.setText(value.replaceAll("\\s+", " "));
		this.rngValue.setText(value);
	}
}
