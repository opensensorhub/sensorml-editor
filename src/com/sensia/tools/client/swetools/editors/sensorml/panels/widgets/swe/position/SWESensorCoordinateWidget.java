package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.APPENDER;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.line.SensorGenericLineWidget;

public class SWESensorCoordinateWidget extends SensorGenericLineWidget{

	/*private Panel namePanel;
	private Panel contentPanel;
	private Panel container;*/
	
	private static Map<String,String> nameMapping = new HashMap<String,String>();
	
	static {
		nameMapping.put("Lat", "Latitude");
		nameMapping.put("Lon", "Longitute");
		nameMapping.put("Alt", "Altitude");
		nameMapping.put("trueHeading", "About Z");
	}
	private String getMappedName(String nameToMap) {
		if(nameMapping.containsKey(nameToMap)) {
			return nameMapping.get(nameToMap);
		} else {
			return toNiceLabel(nameToMap);
		}
	}
	
	public SWESensorCoordinateWidget() {
		super("coordinate", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
		
		/*container = new HorizontalPanel();
		contentPanel = new HorizontalPanel();
		namePanel = new HorizontalPanel();
		
		container.add(namePanel);
		container.add(contentPanel);*/
	}


	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("name")){
			//provide name as label only if label does not exist
			if(!isLabelProvided) {
				//get the associated value
				titleValueWidget = widget.getElements().get(0);
				labelPanel.add(new HTML(getMappedName(widget.getValue("name", true)+"")));
				hasTitle = true;
			}
		} else {
			super.addSensorWidget(widget);
		}
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorCoordinateWidget();
	}
	
	@Override
	public APPENDER appendTo() {
		return APPENDER.OVERRIDE_LINE;
	}
}
