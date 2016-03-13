package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.SWESensorDataArrayWidget;

public class SWESensorPositionByTrajectoryWidget extends AbstractSensorElementWidget{

	private Panel container;
	
	public SWESensorPositionByTrajectoryWidget() {
		super("position",TAG_DEF.SWE,TAG_TYPE.ELEMENT);
		
		container= new HorizontalPanel();
		container.add(new Label("Trajectory: "));
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
		ISensorWidget dataArrayWidget = new SWESensorDataArrayWidget();
		dataArrayWidget.addElement(widget);
		container.add(dataArrayWidget.getPanel());
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionByTrajectoryWidget();
	}

}
