package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public class SMLDocumentWidget extends AbstractSensorElementWidget{

	private Panel container;
	private Anchor linkage;
	private Panel descriptionPanel;
	private Panel extraPanel;
	private Panel editPanel;
	
	public SMLDocumentWidget() {
		super("document", TAG_DEF.SML, TAG_TYPE.ELEMENT);
		
		container = new HorizontalPanel();
		linkage = new Anchor();
		extraPanel = new HorizontalPanel();
		descriptionPanel = new SimplePanel();
		
		editPanel = getEditPanel(new IButtonCallback() {
			
			@Override
			public void onClick() {
				descriptionPanel.clear();
				for(ISensorWidget child : getElements()) {
					addSensorWidget(child);
				}
				
			}
		});
		
		container.add(linkage);
		container.add(new HTML(SensorConstants.HTML_SPACE));
		container.add(new HTML(SensorConstants.HTML_SPACE));
		container.add(descriptionPanel);
		container.add(extraPanel);
		container.add(editPanel);
		
		activeMode(getMode());
		
		//default name
		linkage.setText("Link");
	}

	@Override
	public Panel getPanel() {
		return container;
	}

	@Override
	protected void activeMode(MODE mode) {
		editPanel.setVisible(getMode() == MODE.EDIT);
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		//extraPanel.add(widget.getPanel());
		if(widget.getName().equals("URL")) {
			linkage.setHref(widget.getValue("URL", true));
		} else if(widget.getName().equals("description")) {
			//try CharacterString
			String value = widget.getValue("CharacterString", true);
			if(value == null || value.isEmpty()){
				value = widget.getValue("description", true);
			}
			descriptionPanel.add(new HTML(value));
		} else if(widget.getName().equals("name")) {
			String value = widget.getValue("CharacterString", true);
			if(value == null || value.isEmpty()){
				value = widget.getValue("name", true);
			}
			linkage.setText(value);
			
		}  else if(widget.getName().equals("onlineResource")) {
			//get href
			String href = widget.getValue("href", true);
			if(href != null) {
				linkage.setHref(href);
			}
		} else {
			extraPanel.add(widget.getPanel());
		}
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLDocumentWidget();
	}
}