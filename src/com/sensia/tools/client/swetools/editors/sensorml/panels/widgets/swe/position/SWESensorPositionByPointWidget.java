package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public class SWESensorPositionByPointWidget extends AbstractSWESensorPositionByWidget{

	private Panel editPanel;
	private HTML locationHtmlLabel;
	
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		
		container.add(buildLabel(widget,"Point",false));
		
		Panel mapIconPanel = buildMapIconPanel();
		if(mapIconPanel != null) {
			container.add(mapIconPanel);
		}
		
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
	
	protected void buildCoordinatesPanel(ISensorWidget widget) {
		//build inner content
		String value = widget.getValue("coordinates",true);
		locationHtmlLabel.setHTML(locationHtmlLabel.getHTML()+SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE+value+SensorConstants.HTML_SPACE);
	}
	
	@Override
	protected void activeMode(MODE mode) {
		editPanel.setVisible(getMode() == MODE.EDIT);
	}
	
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionByPointWidget();
	}
	
	
	//Point coordinates is defined by coordinates tag
	@Override
	public Coordinates getCoordinates() {
		Coordinates coordinates = new Coordinates();
		
		if(getElements().size() > 0) {
			ISensorWidget pointWidget = getElements().get(0);
			//get Lat, Lon coordinates
			String value = pointWidget.getValue("coordinates",true);
			String[] coordinatesSplit = value.split(" ");
					
			//get EPSG code
			String defaultCRS = pointWidget.getValue("srsName",false);
			if(defaultCRS == null) {
				Window.alert("The SRS name is not defined, the map cannot be displayed");
			} else {
				String epsgCode = "";
				String []split = defaultCRS.split("/");
				epsgCode = split[split.length-1];
				coordinates.epsgCode = epsgCode;
				Coordinate point = new Coordinate();
				point.lat = Double.parseDouble(coordinatesSplit[0]);
				point.lon = Double.parseDouble(coordinatesSplit[1]);
				
				coordinates.coordinates.add(point);
			}
		}
		return coordinates;
	}
}
