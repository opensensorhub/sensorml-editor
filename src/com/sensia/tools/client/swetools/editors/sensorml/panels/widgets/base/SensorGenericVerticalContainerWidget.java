package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_DEF;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_TYPE;

/**
 * Generic horizontal container
 * @author mathieu dhainaut
 *
 */
public class SensorGenericVerticalContainerWidget extends AbstractSensorElementWidget{

	protected VerticalPanel container;
	protected VerticalPanel innerContainer;
	
	protected HorizontalPanel defPanel;
	
	public SensorGenericVerticalContainerWidget(String name, TAG_DEF def, TAG_TYPE type) {
		super(name, def, type);
		container = new VerticalPanel();
		//container.addStyleName("swe-generic-vertical-panel");
		//container.setSpacing(10);
		//container.addStyleName("swe-property-panel");
	}

	@Override
	public Panel getPanel() {
		return container;
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		container.add(widget.getPanel());
	}

	@Override
	protected void activeMode(MODE mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorGenericVerticalContainerWidget(getName(),getDef(),getType());
	}
}
