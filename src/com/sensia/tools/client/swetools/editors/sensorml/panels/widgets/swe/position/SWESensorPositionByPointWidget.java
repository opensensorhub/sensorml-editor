/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

// TODO: Auto-generated Javadoc
/**
 * The Class SWESensorPositionByPointWidget.
 */
public class SWESensorPositionByPointWidget extends AbstractSWESensorPositionByWidget{

	/** The edit panel. */
	private Panel editPanel;
	
	/** The location html label. */
	private HTML locationHtmlLabel;
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		
		container.add(buildLabel(widget,"Point",false));
		
		Panel mapIconPanel = buildMapIconPanel();
		if(mapIconPanel != null) {
			container.add(mapIconPanel);
		}
		
		buildCoordinatesPanel(widget);
		
		//add advanced panel
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
	
	/**
	 * Builds the label.
	 *
	 * @param widget the widget
	 * @param defaultLabel the default label
	 * @param recursiveName the recursive name
	 * @return the panel
	 */
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
	
	/**
	 * Builds the coordinates panel.
	 *
	 * @param widget the widget
	 */
	protected void buildCoordinatesPanel(ISensorWidget widget) {
		//build inner content
		String value = widget.getValue("coordinates",true);
		locationHtmlLabel.setHTML(locationHtmlLabel.getHTML()+SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE+value+SensorConstants.HTML_SPACE);
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#activeMode(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE)
	 */
	@Override
	protected void activeMode(MODE mode) {
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionByPointWidget();
	}
	
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.AbstractSWESensorPositionByWidget#getCoordinates()
	 */
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

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.AbstractSWESensorPositionByWidget#updateValues(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.AbstractSWESensorPositionByWidget.Coordinates)
	 */
	@Override
	protected void updateValues(Coordinates coordinates) {
		// TODO Auto-generated method stub
		if(getElements().size() > 0) {
			ISensorWidget pointWidget = getElements().get(0);
			Coordinate coordinate = coordinates.coordinates.get(0);
			
			double lat = ((int)(coordinate.lat*100000))/(double)100000;
			double lon = ((int)(coordinate.lon*100000))/(double)100000;
			pointWidget.setValue("coordinates", lat+" "+lon);
		}
	}
}
