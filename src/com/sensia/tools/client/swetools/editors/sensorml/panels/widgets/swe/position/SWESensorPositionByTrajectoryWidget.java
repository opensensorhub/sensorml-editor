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

public class SWESensorPositionByTrajectoryWidget extends AbstractSWESensorPositionByWidget{

	private Panel container;
	private SWESensorDataArrayWidget dataArrayWidget;
	private Panel editPanel;
	private Panel dataArrayPanel;
	
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

	@Override
	public Panel getPanel() {
		return container;
	}

	@Override
	public Coordinates getCoordinates() {
		Coordinates coordinates = new Coordinates();
		
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
	
	@Override
	protected void activeMode(MODE mode) {
		if(dataArrayWidget!= null) {
			dataArrayWidget.switchMode(mode);
		}
	}

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

	@Override
	public void refresh() {
		dataArrayPanel.clear();
		for(ISensorWidget child : getElements()) {
			addSensorWidget(child);
		}
	}
	
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionByTrajectoryWidget();
	}

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
