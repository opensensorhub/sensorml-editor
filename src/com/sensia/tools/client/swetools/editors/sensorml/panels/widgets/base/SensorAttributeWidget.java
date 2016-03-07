package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base;



public class SensorAttributeWidget extends SensorGenericHorizontalContainerWidget{

	public SensorAttributeWidget(String name) {
		super(name, TAG_DEF.RNG, TAG_TYPE.ATTRIBUTE);
	}
	
	public SensorAttributeWidget(String name,TAG_DEF def, TAG_TYPE type) {
		super(name, def, type);
	}
}