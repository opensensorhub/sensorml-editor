package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe;

import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public class SWESensorTimeRangeWidget extends SWESensorQuantityRangeWidget{

	public SWESensorTimeRangeWidget() {
		super("TimeRange");
	}
	
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getType() == TAG_TYPE.ATTRIBUTE &&  widget.getName().equals("referenceFrame")){
			defPanel.add(widget.getPanel());
		} 
		super.addSensorWidget(widget);
	}
	
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorTimeRangeWidget();
	}
}
