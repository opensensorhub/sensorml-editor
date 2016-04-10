/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_DEF;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_TYPE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.line.SensorGenericLineWidget;

/**
 * @deprecated
 * @author Mathieu Dhainaut
 *
 */
public class SWESensorFieldWidget extends AbstractSensorElementWidget{

	private ISensorWidget lineWidget;
	
	public SWESensorFieldWidget(){
		super("field",TAG_DEF.SWE,TAG_TYPE.ELEMENT);
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		//skip embedded DataRecord
		if(widget.getName().equals("DataRecord") && widget.getDef() == TAG_DEF.SWE) {
			for(ISensorWidget child : widget.getElements()) {
				addSensorWidget(child);
			}
		} else {
			if(lineWidget == null) {
				lineWidget = new SensorGenericLineWidget("field", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
			}
			lineWidget.addElement(widget);
		}
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorFieldWidget();
	}

	@Override
	public Panel getPanel() {
		if(lineWidget != null) {
			return lineWidget.getPanel();
		} else {
			return new HorizontalPanel();
		}
	}

	@Override
	protected void activeMode(MODE mode) {
		if(lineWidget != null) {
			lineWidget.switchMode(mode);
		}
	}
}
