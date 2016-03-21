package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.map.SensorMapWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.map.geojson.GeoJsonBuilder;

public abstract class AbstractSWESensorPositionByWidget extends AbstractSensorElementWidget{

	protected Panel container;
	
	protected AbstractSWESensorPositionByWidget() {
		super("position",TAG_DEF.SWE,TAG_TYPE.ELEMENT);
		container = new HorizontalPanel();
	}

	protected Panel buildCoordinatesPanel(ISensorWidget widget,HTML locationHtmlLabel) {
		//build inner content
		List<ISensorWidget> coordinates = findWidgets(widget, "coordinate", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
		
		Panel vInnerPanel = new VerticalPanel();
		for(ISensorWidget coordinate : coordinates) {
			String value = coordinate.getValue("value", true);
			locationHtmlLabel.setHTML(locationHtmlLabel.getHTML()+SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE+value+SensorConstants.HTML_SPACE);
			vInnerPanel.add(coordinate.getPanel());
		}
		
		//add styles
		vInnerPanel.addStyleName("swe-dataRecord-vertical-panel");
		
		//add to result panel
		return vInnerPanel;
	}
	
	protected Panel buildLabel(final ISensorWidget widget,final String defaultLabel, boolean recursiveName,HTML locationHtmlLabel) {
		//build title
		//Build: Position : Location (EPSG/0/4326 http://www.opengis.net/def/crs/EPSG/0/4326): 47.8 88.56 [mapIcon] 
		//String locationLabel = toNiceLabel(field.getValue("name"));
		
		String locationLabel = defaultLabel;
		String referenceFrame = "";
		
		//name of field
		String name = widget.getValue("name", recursiveName);
		
		//referenceFrame from Vector
		String refFrame = widget.getValue("referenceFrame", true);
		
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
		
		locationHtmlLabel.setHTML(locationLabel);
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
	
	protected Panel buildMapIconPanel() {
		SimplePanel mapIconPanel = new SimplePanel();
		Image mapImage = new Image(GWT.getModuleBaseURL()+"images/maps-icon.png");
		mapImage.setTitle("Map");
		
		FocusPanel mapImageWrapper = new FocusPanel(mapImage);
		
		//add icons
		mapImageWrapper.addStyleName("map-icon");
		
		//add listeners
		mapImageWrapper.addClickHandler(new MapIconImageWrapperHandler());
		
		mapIconPanel.add(mapImageWrapper);
		
		return mapIconPanel;
	}
	
	@Override
	public Panel getPanel() {
		return container;
	}
	
	public Coordinates getCoordinates() {
		Coordinates coordinates = new Coordinates();
		//get Lat, Lon coordinates
		if(getElements().size() > 0) {
			final ISensorWidget widget = getElements().get(0);
			List<ISensorWidget> coordinatesWidget = findWidgets(widget, "coordinate", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
			
			String lat = null;
			String lon = null;
			
			if(coordinatesWidget != null) {
				for(final ISensorWidget coordinate : coordinatesWidget) {
					String name = coordinate.getValue("name",false);
					String value = coordinate.getValue("value",true);
					
					if(name != null) {
						if(name.equals("Lat")) {
							lat = value;
						} else if(name.equals("Lon")) {
							lon = value;
						}
					}
					//TODO: handle UOM
				}
			}
			//get EPSG code
			String defaultCRS = widget.getValue("referenceFrame",true);
			if(defaultCRS == null) {
				Window.alert("The CRS name is not defined, the map cannot be displayed");
			} else if (lat == null || lon == null){
				Window.alert("The Lat and/or Lon are not defined, the map cannot be displayed");
			} else {
				String epsgCode = "";
				String []split = defaultCRS.split("/");
				epsgCode = split[split.length-1];
				coordinates.epsgCode = epsgCode;
				
				Coordinate point = new Coordinate();
				point.lat = Double.parseDouble(lat);
				point.lon = Double.parseDouble(lon);
				
				coordinates.coordinates.add(point);
			}
		}
		return coordinates;
	}
	
	public class MapIconImageWrapperHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			SensorMapWidget mapWidget = new SensorMapWidget();
			Coordinates c = getCoordinates();
			
			//is a point (Point/Vector/DataRecord)
			if(c != null && c.coordinates.size() == 1) {
				Coordinate point = c.coordinates.get(0);
				String geoJson = GeoJsonBuilder.buildPointGeoJSon(point.lat,point.lon,c.epsgCode);
				Panel mapPanel = mapWidget.getMapPanelWithPoint(point.lat,point.lon,c.epsgCode);
				//Panel mapPanel = mapWidget.getMapPanel(geoJson);
				
				displayEditPanel(mapPanel, "Position", null);
			} else {
				//build coordinates
				double [][] points = new double[c.coordinates.size()][2];
				int i=0;
				for(Coordinate coordinate : c.coordinates) {
					points[i][0] = coordinate.lat;
					points[i][1] = coordinate.lon;
					i++;
				}
				displayEditPanel(mapWidget.getMapPanelWithTrajectory(points,c.epsgCode), "Position", null);
			}
		}
	}
	
	protected class Coordinates {
		public String epsgCode;
		public List<Coordinate> coordinates= new ArrayList<AbstractSWESensorPositionByWidget.Coordinate>();
	}
	
	
	protected class Coordinate {
		public double lat;
		public double lon;
	}
}
