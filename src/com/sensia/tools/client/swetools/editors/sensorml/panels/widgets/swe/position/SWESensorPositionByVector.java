package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public class SWESensorPositionByVector extends SWESensorPositionByDataRecord{

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		Panel vPanel = new VerticalPanel();
		//the result panel which contains title + inner content
		vPanel.add(buildLabel(widget,false));
		vPanel.add(buildCoordinatesPanel(widget));
		
		container.add(vPanel);
	}
	
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionByVector();
	}
}
