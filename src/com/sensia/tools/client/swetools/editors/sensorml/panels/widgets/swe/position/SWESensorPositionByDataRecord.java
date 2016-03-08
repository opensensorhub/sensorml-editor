package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

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
public class SWESensorPositionByDataRecord extends AbstractSensorElementWidget{

	protected Panel container;
	
	public SWESensorPositionByDataRecord() {
		super("position",TAG_DEF.SWE,TAG_TYPE.ELEMENT);
		container = new HorizontalPanel();
	}

	@Override
	public Panel getPanel() {
		return container;
	}

	private Panel buildSection(ISensorWidget field) {
		//the result panel which contains title + inner content
		Panel vLocationResultPanel = new VerticalPanel();
		
		//build title
		//Build: Position : Location (EPSG/0/4326 http://www.opengis.net/def/crs/EPSG/0/4326): 47.8 88.56 [mapIcon] 
		//String locationLabel = toNiceLabel(field.getValue("name"));
		String locationLabel = toNiceLabel(field.getValue("name"));
		String referenceFrame = field.getValue("referenceFrame");
		if(referenceFrame != null) {
			locationLabel+= " ("+referenceFrame+")";
		}
		locationLabel += ":";
		
		HTML locationHtmlLabel = new HTML(locationLabel);
		
		//build inner content
		List<ISensorWidget> coordinates = findWidgets(field, "coordinate", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
		
		Panel vInnerPanel = new VerticalPanel();
		for(ISensorWidget coordinate : coordinates) {
			String value = coordinate.getValue("value");
			locationHtmlLabel.setHTML(locationHtmlLabel.getHTML()+SensorConstants.HTML_SPACE+SensorConstants.HTML_SPACE+value+SensorConstants.HTML_SPACE);
			vInnerPanel.add(coordinate.getPanel());
		}
		
		//add styles
		vInnerPanel.addStyleName("swe-dataRecord-vertical-panel");
		locationHtmlLabel.addStyleName("font-bold");
		
		//add to result panel
		vLocationResultPanel.add(locationHtmlLabel);
		vLocationResultPanel.add(vInnerPanel);
		return vLocationResultPanel;
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
			for(ISensorWidget field : fields) {
				vPanel.add(buildSection(field));
			}
		}
		container.add(vPanel);
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionByDataRecord();
	}
}
