/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets;

import java.util.List;

import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGTag;

/**
 * This interface represents a single Node of the Sensor Widget Tree.
 * A node is defined by a definition and a type. Some methods are defined 
 * to get and change the value of the element or one of the sub element.
 *
 */
public interface ISensorWidget {

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
	 * This defines the definition of the node.
	 *
	 */
	public enum TAG_DEF {
		SML,
		GML,
		SWE,
		GMD,
		GCO,
		RNG
	}
	
	/**
	 * 
	 * This defines the type of the node.
	 *
	 */
	public enum TAG_TYPE {
		ATTRIBUTE,
		VALUE,
		ELEMENT,
		ZERO_OR_MORE,
		DATA,
		CHOICE,
		OPTIONAL
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
	 * Gets the name of the node.
	 * @return The name of the node.
	 */
	String getName();
	
	/**
	 * Sets the name of the node.
	 * @param name the node of the node.
	 */
	void setName(String name);
	
	/**
	 * Switch between different modes.
	 * @param mode the new mode to apply.
	 */
	void switchMode(MODE mode);
	
	/**
	 * Gets the definition of the node.
	 * @return TAG_DEF the definition of the node.
	 */
	TAG_DEF getDef();
	
	/**
	 * Gets the type of the node.
	 * @return TAG_TYPE the type of the node.
	 */
	TAG_TYPE getType();
	
	/**
	 * Gets the list of the children of the widget.
	 * @return List<ISensorWidget> the list of the children.
	 */
	List<ISensorWidget> getElements();
	
	/**
	 * Adds a child to the list of the children.
	 * @param element the child to add.
	 */
	void addElement(ISensorWidget element);
	
	/**
	 * The result panel. The panel is the result of the content of the widget and its children.
	 * @return Panel The result panel.
	 */
	Panel getPanel();
	
	/**
	 * Clones the widget and gets a new independent one.
	 * @return ISensorWidget the cloned widget.
	 */
	ISensorWidget cloneSensorWidget();
	
	/**
	 * Gets the parent of the widget. If the widget has no parent, this will return null.
	 * @return ISensorWidget the parent of the widget, null otherwise.
	 */
	ISensorWidget getParent();
	
	/**
	 * Sets the parent of the widget.
	 * @param parent The parent of the widget.
	 */
	void setParent(ISensorWidget parent);
	
	/**
	 * @deprecated Use {@link #getValue(String,boolean)} instead
	 */
	String getValue(String parentName);

	/**
	 * Gets the value of the widget. The value is got from the parent element name.
	 * The first TAG_TYPE VALUE found which matches with the parent element name will return.
	 * @param parentName the parent element name
	 * @param recursive define if the only the first sub elements or every sub elements are taken into account.
	 * @return The first VALUE found.
	 */
	String getValue(String parentName, boolean recursive);
	
	/**
	 * Sets the value of the widget. The value is got from the parent element name.
	 * The first TAG_TYPE VALUE found which matches with the parent element name will be set.
	 * @param elementName the parent element name
	 * @param value the value to set
	 */
	void setValue(String elementName,String value);
	
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
	
	/**
	 * Gets the advanced panel of the widget. The corresponding advanced panel will append its content into 
	 * the container given as parameter.
	 * @param container The container to append the content.
	 */
	void getAdvancedPanel(Panel container);
	
	/**
	 * Defines if the widget is represented as an icon.
	 * @return a boolean to define if the widget is reprenseted as an icon.
	 */
	boolean isIcon();
	
	void setRNGTag(RNGTag tag);
	
	RNGTag getRNGTag();
	
}
