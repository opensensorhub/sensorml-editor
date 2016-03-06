package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.xsd;

import com.sensia.relaxNG.XSDDouble;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;

public class SensorXSDDoubleWidget extends SensorXSDWidget{

	private static final int LENGTH = 10;
	private static final String ALLOWED_CHARS = ".-+e0123456789";
	private XSDDouble data;
	
	public SensorXSDDoubleWidget(XSDDouble data) {
		super(data,LENGTH,ALLOWED_CHARS);
		this.data = data;
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		// TODO Auto-generated method stub
		return new SensorXSDDoubleWidget(data);
	}
}
