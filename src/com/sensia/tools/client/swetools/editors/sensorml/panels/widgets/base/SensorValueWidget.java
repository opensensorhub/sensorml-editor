package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;


public class SensorValueWidget extends AbstractSensorElementWidget{

	private Panel container;
	private RNGValue rngValue;
	private TextBox valueBox;
	
	public SensorValueWidget(String value,RNGValue rngValue) {
		super(value, TAG_DEF.RNG, TAG_TYPE.VALUE);
		
		this.rngValue = rngValue;
		container = new HorizontalPanel();
		container.add(new HTML(value.trim()));
		
		valueBox = new TextBox();
		valueBox.setText(value.replaceAll("\\s+", " ").trim());
	}

	@Override
	public Panel getPanel() {
		return container;
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) { 
		container.add(widget.getPanel());
	}

	@Override
	protected void activeMode(MODE mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorValueWidget(getName(),rngValue);
	}
	
	@Override
	public void setValue(String elementName,String value) {
		if(elementName.equals(getParent().getName())) {
			setValue(value);
		}
	}
	
	@Override
	public void getAdvancedPanel(Panel container) {
		HorizontalPanel hPanel = new HorizontalPanel();
		HTML hlabel = new HTML(getParent().getName().trim());
		hlabel.setWidth("100px");
		hPanel.add(hlabel);
		
		valueBox.setWidth("500px");
		
		hPanel.add(valueBox);
		
		container.add(hPanel);
	}
	
	@Override
	public void refresh() {
		String value = valueBox.getText();
		container.clear();
		container.add(new HTML(value.trim()));
		setName(value.trim());
		this.rngValue.setText(value.trim());
	}
	
	private void setValue(String value) {
		container.clear();
		container.add(new HTML(value.trim()));
		setName(value.trim());
		valueBox.setText(value.replaceAll("\\s+", " ").trim());
		this.rngValue.setText(value.trim());
	}
}
