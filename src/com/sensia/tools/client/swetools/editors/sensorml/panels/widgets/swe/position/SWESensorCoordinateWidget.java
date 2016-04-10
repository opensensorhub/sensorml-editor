/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

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

/**
 * The Class SWESensorCoordinateWidget is corresponding to the <swe:coordinate> element.
 */
public class SWESensorCoordinateWidget extends SensorGenericLineWidget{

	/** The name mapping. */
	private static Map<String,String> nameMapping = new HashMap<String,String>();
	
	static {
		nameMapping.put("Lat", "Latitude");
		nameMapping.put("Lon", "Longitute");
		nameMapping.put("Alt", "Altitude");
		nameMapping.put("trueHeading", "About Z");
	}
	
	/**
	 * Gets the mapped name.
	 *
	 * @param nameToMap the name to map
	 * @return the mapped name
	 */
	private String getMappedName(String nameToMap) {
		if(nameMapping.containsKey(nameToMap)) {
			return nameMapping.get(nameToMap);
		} else {
			return toNiceLabel(nameToMap);
		}
	}
	
	/**
	 * Instantiates a new SWE sensor coordinate widget.
	 */
	public SWESensorCoordinateWidget() {
		super("coordinate", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
	}


	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.line.SensorGenericLineWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
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

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.line.SensorGenericLineWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorCoordinateWidget();
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#appendTo()
	 */
	@Override
	public APPENDER appendTo() {
		return APPENDER.HORIZONTAL;
	}
}
