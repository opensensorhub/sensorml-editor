/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.dataarray.SWESensorDataArrayWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.AbstractSWESensorPositionByWidget.Coordinates;

/**
 * The Class SWESensorPositionByTrajectoryWidget.
 */
public class SWESensorPositionByTrajectoryWidget extends AbstractSWESensorPositionByWidget{

	/** The container. */
	private Panel container;
	
	/** The data array widget. */
	private SWESensorDataArrayWidget dataArrayWidget;
	
	/** The edit panel. */
	private Panel editPanel;
	
	/** The data array panel. */
	private Panel dataArrayPanel;
	
	/**
	 * Instantiates a new SWE sensor position by trajectory widget.
	 */
	public SWESensorPositionByTrajectoryWidget() {
		super();
		container= new HorizontalPanel();
		container.add(new Label("Trajectory: "));
		
		dataArrayPanel = new HorizontalPanel();
		
		container.add(dataArrayPanel);
		
		editPanel = getEditPanel(new IButtonCallback() {
			
			@Override
			public void onClick() {
				if(dataArrayWidget != null) {
					dataArrayWidget.refresh();
				}
			}
		});
		container.add(editPanel);
		
		activeMode(getMode());
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.AbstractSWESensorPositionByWidget#getPanel()
	 */
	@Override
	public Panel getPanel() {
		return container;
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.AbstractSWESensorPositionByWidget#getCoordinates()
	 */
	@Override
	public Coordinates getCoordinates() {
		Coordinates coordinates = new Coordinates();
		
		//build data array object from String values
		Object[][] values = dataArrayWidget.getObjectValues();
		List<String> headers = dataArrayWidget.getHeaders();
		
		int indexLat=0;
		int indexLon = 0;
		
		int i=0;
		for(String header : headers) {
			if(header.toLowerCase().contains("lat")) {
				indexLat=i;
			} else if(header.toLowerCase().contains("lon")) {
				indexLon=i;
			}
			i++;
		}
		
		for(i=0;i < values.length;i++) {
			Coordinate c = new Coordinate();
			c.lat = Double.parseDouble(values[i][indexLat].toString());
			c.lon = Double.parseDouble(values[i][indexLon].toString());
			
			coordinates.coordinates.add(c);
		}
		
		//get EPSG code
		String defaultCRS = this.getValue("referenceFrame",true);
		if(defaultCRS == null) {
			Window.alert("The CRS name is not defined, the map cannot be displayed correctly");
		} else {
			String epsgCode = "";
			String []split = defaultCRS.split("/");
			epsgCode = split[split.length-1];
			coordinates.epsgCode = epsgCode;
		}
		return coordinates;
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#activeMode(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE)
	 */
	@Override
	protected void activeMode(MODE mode) {
		if(dataArrayWidget!= null) {
			dataArrayWidget.switchMode(mode);
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		dataArrayWidget = new SWESensorDataArrayWidget();
		dataArrayWidget.setDisplayGraph(false);
		dataArrayWidget.setDisplayTitle(false);
		
		for(ISensorWidget child : widget.getElements()) {
			dataArrayWidget.addElement(child);
		}
		dataArrayPanel.add(dataArrayWidget.getPanel());
		
		//get map icon
		Panel mapIconPanel = buildMapIconPanel();
		if(mapIconPanel != null) {
			dataArrayPanel.add(mapIconPanel);
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.AbstractSWESensorPositionByWidget#refresh()
	 */
	@Override
	public void refresh() {
		dataArrayPanel.clear();
		for(ISensorWidget child : getElements()) {
			addSensorWidget(child);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionByTrajectoryWidget();
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.AbstractSWESensorPositionByWidget#updateValues(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.AbstractSWESensorPositionByWidget.Coordinates)
	 */
	@Override
	protected void updateValues(Coordinates coordinates) {
		/*List<String> headers = dataArrayWidget.getHeaders();
		
		int indexLat=0;
		int indexLon = 0;
		
		int i=0;
		for(String header : headers) {
			if(header.toLowerCase().contains("lat")) {
				indexLat=i;
			} else if(header.toLowerCase().contains("lon")) {
				indexLon=i;
			}
			i++;
		}
		
		for(Coordinate coordinate : coordinates.coordinates) {
			
		}*/
	}

}
