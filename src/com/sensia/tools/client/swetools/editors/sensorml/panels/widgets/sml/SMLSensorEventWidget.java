package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.ICallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.APPENDER;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_DEF;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_TYPE;

public class SMLSensorEventWidget extends AbstractSensorElementWidget{

	private Panel container;
	private Panel labelPanel;
	private Panel defPanel;
	
	private Panel timePanel;
	
	public SMLSensorEventWidget() {
		super("Event", TAG_DEF.SML, TAG_TYPE.ELEMENT);
		container = new HorizontalPanel();
		init();
	}

	private void init() {
		defPanel = new HorizontalPanel();
		labelPanel = new HorizontalPanel();
		timePanel = new HorizontalPanel();
		
		container.add(labelPanel);
		container.add(defPanel);
		container.add(new HTML("-->"+SensorConstants.HTML_SPACE));
		container.add(timePanel);
		
		Panel advancedPanel = getEditPanel(new IButtonCallback() {
			@Override
			public void onClick() {
				refreshChildren(getElements());
				refreshParents(getParent());
			}
		});
		
		container.add(advancedPanel);
	}
	
	@Override
	public Panel getPanel() {
		return container;
	}

	@Override
	protected void activeMode(MODE mode) {
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getName().equals("label")) {
			labelPanel.add(widget.getPanel());
		} else if(widget.getName().equals("timePosition")) {
			timePanel.add(widget.getPanel());
		} else if(widget.getName().equals("definition")) {
			defPanel.add(widget.getPanel());
		} 
		//skip others elements
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLSensorEventWidget();
	}
}
