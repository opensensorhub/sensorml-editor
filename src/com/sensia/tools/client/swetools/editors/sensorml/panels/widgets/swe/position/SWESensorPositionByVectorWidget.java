package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public class SWESensorPositionByVectorWidget extends AbstractSWESensorPositionByWidget{

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		Panel vPanel = new VerticalPanel();
		//the result panel which contains title + inner content
		//Label part
		HorizontalPanel labelPanel = new HorizontalPanel();
		labelPanel.add(buildLabel(widget,"Location",false, new HTML("")));
		
		//get map icon
		Panel mapIconPanel = buildMapIconPanel();
		if(mapIconPanel != null) {
			labelPanel.add(mapIconPanel);
		}
		
		vPanel.add(labelPanel);
		vPanel.add(buildCoordinatesPanel(widget,new HTML("")));
		
		container.add(vPanel);
	}
	
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionByVectorWidget();
	}

	@Override
	protected void activeMode(MODE mode) {
		
	}
}
