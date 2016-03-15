package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.dataarray.SWESensorDataArrayWidget;

public class SWESensorPositionByTrajectoryWidget extends AbstractSensorElementWidget{

	private Panel container;
	private SWESensorDataArrayWidget dataArrayWidget;
	private Panel editPanel;
	private Panel dataArrayPanel;
	
	public SWESensorPositionByTrajectoryWidget() {
		super("position",TAG_DEF.SWE,TAG_TYPE.ELEMENT);
		
		container= new HorizontalPanel();
		container.add(new Label("Trajectory: "));
		
		dataArrayPanel = new SimplePanel();
		
		container.add(dataArrayPanel);
		
		editPanel = getEditPanel(new IButtonCallback() {
			
			@Override
			public void onClick() {
				if(dataArrayWidget != null) {
					dataArrayWidget.refresh();
				}
			}
		});
		container.add(editPanel);
		
		activeMode(getMode());
	}

	@Override
	public Panel getPanel() {
		return container;
	}

	@Override
	protected void activeMode(MODE mode) {
		if(dataArrayWidget!= null) {
			dataArrayWidget.switchMode(mode);
		}
		editPanel.setVisible(getMode() == MODE.EDIT);
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		dataArrayWidget = new SWESensorDataArrayWidget();
		//dataArrayWidget.setDisplayGraph(false);
		//dataArrayWidget.setDisplayTitle(false);
		
		for(ISensorWidget child : widget.getElements()) {
			dataArrayWidget.addElement(child);
		}
		dataArrayPanel.add(dataArrayWidget.getPanel());
	}

	@Override
	public void refresh() {
		dataArrayPanel.clear();
		for(ISensorWidget child : getElements()) {
			addSensorWidget(child);
		}
	}
	
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionByTrajectoryWidget();
	}

}
