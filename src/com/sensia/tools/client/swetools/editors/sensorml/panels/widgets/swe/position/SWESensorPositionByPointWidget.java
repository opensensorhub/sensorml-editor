package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public class SWESensorPositionByPointWidget extends SWESensorPositionByDataRecordWidget{

	private Panel editPanel;
	
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		
		container.add(buildLabel(widget,"Point",false));
		buildCoordinatesPanel(widget);
		editPanel = getEditPanel(new IButtonCallback() {
			
			@Override
			public void onClick() {
				container.clear();
				for(final ISensorWidget child : getElements()) {
					addSensorWidget(child);
				}
			}
		});
		container.add(editPanel);
		
		activeMode(getMode());
	}
	
	@Override
	protected Panel buildLabel(final ISensorWidget widget,final String defaultLabel, boolean recursiveName) {
		//build title
		//Build: Position : Location (EPSG/0/4326 http://www.opengis.net/def/crs/EPSG/0/4326): 47.8 88.56 [mapIcon] 
		//String locationLabel = toNiceLabel(field.getValue("name"));
		
		String locationLabel = defaultLabel;
		String referenceFrame = "";
		
		//name of field
		String name = widget.getValue("name", recursiveName);
		
		//referenceFrame from Vector
		String refFrame = widget.getValue("srsName", true);
		
		if(name != null && !name.trim().isEmpty()) {
			locationLabel = toNiceLabel(name);
		}
		
		if(refFrame != null) {
			referenceFrame = refFrame;
		}
		
		if(referenceFrame != null && !referenceFrame.isEmpty()) {
			locationLabel+= " ("+referenceFrame+")";
		}
		locationLabel += ":";
		
		locationHtmlLabel = new HTML(locationLabel);
		
		//concatenate label and definition if it exists
		Panel hPanel = new HorizontalPanel();
		hPanel.add(locationHtmlLabel);
		
		for(ISensorWidget child : widget.getElements()) {
			if(child.getName().equals("definition")) {
				hPanel.add(child.getPanel());
				break;
			}
		}
		
		//add styles
		locationHtmlLabel.addStyleName("font-bold");
		
		return hPanel;
	}
	
	@Override
	protected Panel buildCoordinatesPanel(ISensorWidget widget) {
		//build inner content
		String value = widget.getValue("coordinates",true);
		locationHtmlLabel.setHTML(locationHtmlLabel.getHTML()+SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE+value+SensorConstants.HTML_SPACE);
		//add to result panel
		return null;
	}
	
	@Override
	protected void activeMode(MODE mode) {
		editPanel.setVisible(getMode() == MODE.EDIT);
	}
	
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionByPointWidget();
	}
}
