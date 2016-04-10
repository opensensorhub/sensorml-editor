/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position;

import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
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
public class SWESensorPositionByDataRecordWidget extends AbstractSWESensorPositionByWidget{

	/** The content panel. */
	protected Panel contentPanel;
	
	/** The edit panel. */
	protected Panel editPanel;
	
	/**
	 * Instantiates a new SWE sensor position by data record widget.
	 */
	public SWESensorPositionByDataRecordWidget() {
		super();
		contentPanel = new HorizontalPanel();
		
		container.add(contentPanel);
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#activeMode(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE)
	 */
	@Override
	protected void activeMode(MODE mode) {
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		//---- Position panel part
		Panel vPanel = new VerticalPanel();
		List<ISensorWidget> fields = AbstractSensorElementWidget.findWidgets(widget, "field", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
		if(fields != null) {
			//the result panel which contains title + inner content
			int i=0;
			for(final ISensorWidget field : fields) {
				HorizontalPanel labelPanel = new HorizontalPanel();
				HTML locationHtmlLabel = new HTML();
				labelPanel.add(buildLabel(field,"Location",true,locationHtmlLabel));
				if((i++) == 0) {
					//add map icon
					Panel mapIconPanel = buildMapIconPanel();
					if(mapIconPanel != null) {
						labelPanel.add(mapIconPanel);
					}
					
					//add edit panel
					editPanel = getEditPanel(new IButtonCallback() {
						
						@Override
						public void onClick() {
							contentPanel.clear();
							for(ISensorWidget child : getElements()) {
								addSensorWidget(child);
							}
						}
					});
					labelPanel.add(new HTML(SensorConstants.HTML_SPACE));
					labelPanel.add(editPanel);
					
				}
				vPanel.add(labelPanel);
				vPanel.add(buildCoordinatesPanel(field,locationHtmlLabel));
			}
		}
		contentPanel.add(vPanel);
		
		activeMode(getMode());
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.AbstractSWESensorPositionByWidget#refresh()
	 */
	@Override
	public void refresh() {
		contentPanel.clear();
		for(ISensorWidget child : getElements()) {
			addSensorWidget(child);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorPositionByDataRecordWidget();
	}
}
