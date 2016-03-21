package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public class SWESensorPositionByDescriptionWidget extends AbstractSWESensorPositionByWidget{

	private Panel editPanel;
	
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		container.add(new HTML("Description: "));
		container.add(widget.getPanel());
		
		editPanel = getEditPanel(null);
		container.add(editPanel);
		
		activeMode(getMode());
	}
	
	@Override
	protected void activeMode(MODE mode) {
		editPanel.setVisible(getMode() == MODE.EDIT);
	}
	
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionByDescriptionWidget();
	}
	
}
