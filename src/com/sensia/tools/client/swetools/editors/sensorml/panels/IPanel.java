/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels;

import java.util.List;

import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGTag;

/**
 * This interface represents a single Node of the Sensor Widget Tree.
 * A node is defined by a definition and a type. Some methods are defined 
 * to get and change the value of the element or one of the sub element.
 *
 */
public interface IPanel<T extends RNGTag> {

	/**
	 * 
	 * The mode allows to make out the state of the node.
	 *
	 */
	public enum MODE {
		VIEW,
		EDIT
	}
	
	/**
	 * 
	 * The appender is in charge of defining the behavior of a current node
	 * compared to its parent. Thus the parent will add the node depending of the appender position.
	 *
	 */
	public enum APPENDER {
		VERTICAL,
		HORIZONTAL,
		VERTICAL_STRICT,
		HORIZONTAL_STRICT,
		NONE,
		OVERRIDE_LINE
	}
	
	/**
	 * Switch between different modes.
	 * @param mode the new mode to apply.
	 */
	void switchMode(MODE mode);
	
	
	/**
	 * Gets the list of the children of the widget.
	 * @return List<ISensorWidget> the list of the children.
	 */
	List<IPanel<? extends RNGTag>> getElements();
	
	/**
	 * Adds a child to the list of the children.
	 * @param element the child to add.
	 */
	void addElement(IPanel<? extends RNGTag> element);
	
	/**
	 * The result panel. The panel is the result of the content of the widget and its children.
	 * @return Panel The result panel.
	 */
	Panel getPanel();
	
	/**
	 * Clones the widget and gets a new independent one.
	 * @return ISensorWidget the cloned widget.
	 */
	IPanel<T> cloneSensorWidget();
	
	/**
	 * Refreshes the current node. Most of cases when a value is edited the refresh method is called. 
	 * Thus each widget can intercept the refresh event and do any action.
	 */
	void refresh();
	
	/**
	 * Gets the APPENDER state.
	 * @return the APPENDER value
	 */
	APPENDER appendTo();
	
	/**
	 * Sets the APPENDER value.
	 * @param appender The APPENDER to set
	 */
	void setAppender(APPENDER appender);
	
	void getAdvancedPanel(Panel container);
	
	/**
	 * Defines if the widget is represented as an icon.
	 * @return a boolean to define if the widget is reprenseted as an icon.
	 */
	boolean isIcon();
	
	T getTag();
	
	String getName();
	
}
