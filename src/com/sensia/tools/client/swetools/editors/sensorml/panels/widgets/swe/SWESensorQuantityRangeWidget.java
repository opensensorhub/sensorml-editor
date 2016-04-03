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

public class SWESensorQuantityRangeWidget extends AbstractSensorElementWidget{

	protected HorizontalPanel valuePanel;
	protected HorizontalPanel container;
	protected HorizontalPanel defPanel;
	protected HorizontalPanel uomPanel;
	protected HorizontalPanel constraintPanel;
	
	protected ISensorWidget rangeValueWidget;
	
	protected boolean hasConstraints=false;
	
	public SWESensorQuantityRangeWidget() {
		this("QuantityRange");
	}

	public SWESensorQuantityRangeWidget(final String name) {
		super(name,TAG_DEF.SWE,TAG_TYPE.ELEMENT);
		
		container = new HorizontalPanel();
		init();
	}
	
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
	
	@Override
	public Panel getPanel() {
		return container;
	}

	@Override
	protected void activeMode(MODE mode) {
		
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("definition")){
			defPanel.add(widget.getPanel());
		} else if(widget.getType() == TAG_TYPE.ELEMENT && widget.getName().equals("uom")){
			uomPanel.add(new HTML(SensorConstants.HTML_SPACE));
			uomPanel.add(widget.getPanel());
		} else if(widget.getName().equals("value") && widget.getType() == TAG_TYPE.ELEMENT && widget.getDef() == TAG_DEF.SWE){
			String interval = widget.getElements().get(0).getName();
			String [] spaceSplit = interval.split(" ");
			HTML values = new HTML(spaceSplit[0]+" to "+spaceSplit[1]);
			valuePanel.add(values);
			if(hasConstraints) {
				valuePanel.add(new HTML(SensorConstants.HTML_SPACE+"in"+SensorConstants.HTML_SPACE));
			}
		} else if(widget.getType() == TAG_TYPE.ELEMENT && widget.getName().equals("constraint")){
			hasConstraints = true;
			constraintPanel.add(widget.getPanel());
		}
		
		//add advanced panel
	}

	@Override
	public void refresh() {
		super.refresh();
		container.clear();
		init();
		for(ISensorWidget child : getElements()) {
			addSensorWidget(child);
		}
	}
	
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorQuantityRangeWidget();
	}
	
	@Override
	public APPENDER appendTo() {
		return APPENDER.HORIZONTAL;
	}
}
