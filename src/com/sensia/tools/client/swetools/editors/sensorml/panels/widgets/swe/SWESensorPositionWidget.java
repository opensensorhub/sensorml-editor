package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.APPENDER;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.ISensorPositionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.SWESensorPositionByDataRecord;

public class SWESensorPositionWidget extends AbstractSensorElementWidget{

	private ISensorWidget sensorPositionPanel;
	
	private Panel container;
	public SWESensorPositionWidget() {
		super("position", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
		container = new HorizontalPanel();
	}

	@Override
	public Panel getPanel() {
		return sensorPositionPanel.getPanel();
	}

	@Override
	protected void activeMode(MODE mode) {
		
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		//container.add(widget.getPanel());
		//We have to handle many cases:
		//Position by description
		//Position by Point (gml:point)
		//Position by Location (swe:Vector)
		//Position by Position (swe:DataRecord)
		//Position by Trajectory (swe:DataArray)
		//Position by Process (sml:SimpleProcess or sml:AggregateProcess or sml:PhysicalComponent or sml:PhysicalSystem)
		
		if(widget.getName().equals("DataRecord")) {
			sensorPositionPanel = new SWESensorPositionByDataRecord();
		}
		
		if(sensorPositionPanel != null) {
			sensorPositionPanel.addElement(widget);
		}
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionWidget();
	}
}
