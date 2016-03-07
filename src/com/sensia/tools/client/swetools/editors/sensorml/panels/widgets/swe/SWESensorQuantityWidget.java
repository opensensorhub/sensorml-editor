package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.APPENDER;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_DEF;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_TYPE;

public class SWESensorQuantityWidget extends AbstractSensorElementWidget{

	protected HorizontalPanel quantityPanel;
	protected HorizontalPanel container;
	protected HorizontalPanel defPanel;
	protected HorizontalPanel uomPanel;
	protected HorizontalPanel constraintPanel;
	
	public SWESensorQuantityWidget() {
		this("Quantity");
	}

	public SWESensorQuantityWidget(String name) {
		super(name,TAG_DEF.SWE,TAG_TYPE.ELEMENT);
		
		container = new HorizontalPanel();
		defPanel = new HorizontalPanel();
		quantityPanel = new HorizontalPanel();
		uomPanel = new HorizontalPanel();
		constraintPanel = new HorizontalPanel();
		//advancedPanel = new HorizontalPanel();
		
		container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		defPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		uomPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		quantityPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		constraintPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		container.add(quantityPanel);
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
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && (widget.getName().equals("definition") || widget.getName().equals("referenceFrame"))){
			defPanel.add(widget.getPanel());
		} else if(widget.getType() == TAG_TYPE.ELEMENT && widget.getName().equals("uom")){
			uomPanel.add(widget.getPanel());
		} else if(widget.getType() == TAG_TYPE.ELEMENT && widget.getName().equals("value")){
			quantityPanel.add(widget.getPanel());
		} else if(widget.getType() == TAG_TYPE.ELEMENT && widget.getName().equals("constraint")){
			constraintPanel.add(widget.getPanel());
		}else {
			uomPanel.add(widget.getPanel());
		}
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorQuantityWidget();
	}

	@Override
	public APPENDER appendTo() {
		return APPENDER.HORIZONTAL;
	}
}
