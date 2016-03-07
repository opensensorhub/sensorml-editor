package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe;

import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public class SWESensorTimeWidget extends SWESensorQuantityWidget{

	public SWESensorTimeWidget() {
		super("Time");
	}
	
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getType() == TAG_TYPE.ATTRIBUTE &&  widget.getName().equals("referenceFrame")){
			defPanel.add(widget.getPanel());
		} else {
			super.addSensorWidget(widget);
		}
	}
	
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorTimeWidget();
	}
}
