package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public class SMLSensorModeChoiceWidget extends AbstractSensorElementWidget{

	private Panel container;
	
	public SMLSensorModeChoiceWidget() {
		super("ModeChoice",TAG_DEF.SML,TAG_TYPE.ELEMENT);
		
		container = new VerticalPanel();
	}

	@Override
	public Panel getPanel() {
		return container;
	}

	@Override
	protected void activeMode(MODE mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		//skip attributes
		if(widget.getType() == TAG_TYPE.ATTRIBUTE){
			return;
		}
		
		//handle <sml:Mode>
		container.add(widget.getPanel());
		
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLSensorModeChoiceWidget();
	}

}
