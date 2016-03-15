package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.SimplePager;
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
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.APPENDER;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.map.SensorMapWidget;

/**
 * Position : Location (EPSG/0/4326 http://www.opengis.net/def/crs/EPSG/0/4326): 47.8 88.56 [mapIcon] 
 * Latitude: 47.8 deg 
 * Longitude: 88.56 deg 
 * Altitude: 0.00 deg
 * 
 * Orientation (NED <http://www.opengis.net/def/crs/NED>): 
 * About X: 6.8 deg
 * About Y: 0.3 deg 
 * About Z: 0.0 deg
 * 
 * @author mathieu.dhainaut
 *
 */
public class SWESensorPositionByDataRecordWidget extends AbstractSensorElementWidget{

	protected Panel container;
	protected HTML locationHtmlLabel;
	protected Panel contentPanel;
	
	public SWESensorPositionByDataRecordWidget() {
		super("position",TAG_DEF.SWE,TAG_TYPE.ELEMENT);
		container = new HorizontalPanel();
		contentPanel = new HorizontalPanel();
		
		container.add(contentPanel);
	}

	protected Panel buildMapIconPanel(double lat,double lon) {
		SimplePanel mapIconPanel = new SimplePanel();
		Image mapImage = new Image(GWT.getModuleBaseURL()+"images/maps-icon.png");
		mapImage.setTitle("Map");
		
		FocusPanel mapImageWrapper = new FocusPanel(mapImage);
		
		//add icons
		mapImageWrapper.addStyleName("map-icon");
		
		//add listeners
		mapImageWrapper.addClickHandler(new MapIconImageWrapperHandler(lat,lon));
		
		mapIconPanel.add(mapImageWrapper);
		
		return mapIconPanel;
	}
	
	@Override
	public Panel getPanel() {
		return container;
	}

	protected Panel buildCoordinatesPanel(ISensorWidget widget) {
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

	@Override
	protected void activeMode(MODE mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		//---- Position panel part
		Panel vPanel = new VerticalPanel();
		List<ISensorWidget> fields = AbstractSensorElementWidget.findWidgets(widget, "field", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
		if(fields != null) {
			//the result panel which contains title + inner content
			for(final ISensorWidget field : fields) {
				vPanel.add(buildLabel(field,"Location",true));
				vPanel.add(buildCoordinatesPanel(field));
			}
		}
		contentPanel.add(vPanel);
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
	public void refresh() {
		container.clear();
		for(ISensorWidget child : getElements()) {
			addSensorWidget(child);
		}
	}
	
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionByDataRecordWidget();
	}
	
	public APPENDER appendTo() {
		return APPENDER.OVERRIDE_LINE;
	}
	
	public class MapIconImageWrapperHandler implements ClickHandler{

		private double lat;
		private double lon;
		
		public MapIconImageWrapperHandler(double lat,double lon) {
			this.lat = lat;
			this.lon = lon;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			SensorMapWidget mapWidget = new SensorMapWidget();
			displayEditPanel(mapWidget.getMapPanel(lat,lon), "Position", null);
		}
	}
}
