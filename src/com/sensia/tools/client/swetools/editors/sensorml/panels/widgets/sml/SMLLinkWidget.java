package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE;

public class SMLLinkWidget extends AbstractSensorElementWidget{

	private HorizontalPanel container;
	
	private Panel sourcePanel;
	private Panel destinationPanel;
	private Panel editPanel;
	
	public SMLLinkWidget() {
		super("Link", TAG_DEF.SML, TAG_TYPE.ELEMENT);
		
		container = new HorizontalPanel();
		sourcePanel = new SimplePanel();
		destinationPanel = new SimplePanel();
		
		SimplePanel linkIconPanel = new SimplePanel();
		linkIconPanel.addStyleName("link-icon-panel");
		
		//add line
		container.add(sourcePanel);
		container.add(linkIconPanel);
		container.add(destinationPanel);
		
		editPanel = getEditPanel(null);
		
		container.add(editPanel);
		activeMode(getMode());
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
		if(widget.getName().equals("source")) {
			sourcePanel.add(widget.getPanel());
		} else if(widget.getName().equals("destination")) {
			destinationPanel.add(widget.getPanel());
		} else {
			//container.add(widget.getPanel());
		}
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLLinkWidget();
	}

}
