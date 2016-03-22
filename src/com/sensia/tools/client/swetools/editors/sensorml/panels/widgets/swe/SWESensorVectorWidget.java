package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe;

import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.APPENDER;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorGenericVerticalContainerWidget;

public class SWESensorVectorWidget extends SensorGenericVerticalContainerWidget{

	public SWESensorVectorWidget() {
		super("Vector", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
		
		container.addStyleName("swe-generic-vertical-panel");
	}

	@Override
	public APPENDER appendTo() {
		return APPENDER.OVERRIDE_LINE;
	}
}
