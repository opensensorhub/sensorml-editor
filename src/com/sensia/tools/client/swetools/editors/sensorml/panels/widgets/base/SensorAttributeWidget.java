/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base;



/**
 * The Class SensorAttributeWidget is corresponding to any RNG attributes.
 */
public class SensorAttributeWidget extends SensorGenericHorizontalContainerWidget{

	/**
	 * Instantiates a new sensor attribute widget.
	 *
	 * @param name the name of the attribute
	 */
	public SensorAttributeWidget(String name) {
		super(name, TAG_DEF.RNG, TAG_TYPE.ATTRIBUTE);
	}
	
	/**
	 * Instantiates a new sensor attribute widget.
	 *
	 * @param name the name
	 * @param def the def
	 * @param type the type
	 */
	public SensorAttributeWidget(String name,TAG_DEF def, TAG_TYPE type) {
		super(name, def, type);
	}
}
