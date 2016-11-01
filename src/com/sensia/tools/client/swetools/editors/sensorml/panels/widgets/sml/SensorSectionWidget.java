/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import java.util.List;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

/**
 * The Class SensorSectionWidget is corresponding to a root section.
 */
public class SensorSectionWidget extends AbstractSensorElementWidget{

	/** The Constant CSS_CLASS. */
	private static final String CSS_CLASS = "swe-section-panel";
	
	/** The hide panel. */
	private DisclosurePanel hidePanel;
	
	/** The content panel. */
	private VerticalPanel contentPanel;
	
	/** The container. */
	private Panel container;
	
	/** The current header. */
	private HasText currentHeader;
	
	/** The link name. */
	//for href section Link to Base
	private String linkName="";
	
	/** The href. */
	private String href="";
	
	/** The anchor. */
	private Anchor anchor;
	
	/** The nice name. */
	private String niceName;
	
	/**
	 * Instantiates a new sensor section widget.
	 *
	 * @param name the name
	 * @param niceName the nice name
	 */
	public SensorSectionWidget(String name,String niceName,RNGElement tag) {
		super(name,TAG_DEF.SML ,TAG_TYPE.ELEMENT,tag);
		this.niceName = niceName;
		
		//add disclosure panel
		hidePanel = new DisclosurePanel(niceName);
        hidePanel.setAnimationEnabled(true);
        hidePanel.setOpen(true);
        hidePanel.addStyleName(CSS_CLASS);
        hidePanel.getHeader().addStyleName("swe-section-title");
        
        //add vertical panel as a content of the disclosure panel
        contentPanel = new VerticalPanel();	
        hidePanel.setContent(contentPanel);
        
        container = new FlowPanel();
        container.add(hidePanel);
        
        contentPanel.addStyleName("swe-property-panel");
        
        currentHeader = hidePanel.getHeaderTextAccessor();
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getPanel()
	 */
	@Override
	public Panel getPanel() {
		return container;
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		//handle special cases
		if(getName().equals("characteristics") || getName().equals("capabilities")) {
			handleCharacteristic(widget);
		} else if(getName().equals("typeOf")) {
			handleTypeOf(widget);
		} else if(widget.getType() == TAG_TYPE.ATTRIBUTE) {
			handleAttribute(widget);
		} else {
			Panel panel = widget.getPanel();
			panel.addStyleName("swe-generic-vertical-panel");
			contentPanel.add(panel);
		}
	}

	/**
	 * Handle characteristic.
	 *
	 * @param widget the widget
	 */
	private void handleCharacteristic(ISensorWidget widget) {
		// 3 cases:
		// 1) it's the name attribute
		// 2) it's the CharacteristicList/CapabilityList with/without definition
		// 3) the List contains a label
		
		String valueHeader = getName();
		//case 1 : it's the name attribute
		
		//TODO:May be handled by a separate ISensorWidget?
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("name")) {
			valueHeader += " ("+toNiceLabel(widget.getRNGValue(widget.getName(), true).getText())+")";
			currentHeader.setText(valueHeader);
		} else if(widget.getName().equals("CharacteristicList") || widget.getName().equals("CapabilityList")) {
			//looking for definition and label
			List<ISensorWidget> children = widget.getElements();
			for(ISensorWidget child : children) {
				if(child.getType() == TAG_TYPE.ATTRIBUTE && child.getName().equals("definition")) {
					
					child.getPanel().removeFromParent();
					
					HorizontalPanel headerPanel = new HorizontalPanel();
					Widget currentHeader = hidePanel.getHeader();
					headerPanel.add(currentHeader);
					headerPanel.add(child.getPanel());
					
					child.getPanel().addStyleName("icon-def-section-header");
					hidePanel.setHeader(headerPanel);
				} else if(child.getType() == TAG_TYPE.ELEMENT && child.getName().equals("label")) {
					valueHeader += " ("+toNiceLabel(child.getRNGValue(child.getName(), true).getText())+")";
					currentHeader.setText(valueHeader);
					child.getPanel().removeFromParent();
					break;
				}
			}
			Panel panel = widget.getPanel();
			panel.addStyleName("swe-generic-vertical-panel");
			contentPanel.add(panel);
		} else {
			Panel panel = widget.getPanel();
			panel.addStyleName("swe-generic-vertical-panel");
			contentPanel.add(panel);
		}
	}

	/**
	 * Handle type of.
	 *
	 * @param widget the widget
	 */
	private void handleTypeOf(ISensorWidget widget) {
		if(widget.getType() == TAG_TYPE.ATTRIBUTE) {
			if(widget.getName().equals("title")) {
				linkName = widget.getRNGValue("title", true).getText();
			} else if(widget.getName().equals("href")) {
				href = widget.getRNGValue("href", true).getText();
			}
		}
		if(getElements().isEmpty()) {
			if(anchor == null) {
				anchor = new Anchor("","");
				HorizontalPanel hPanel = new HorizontalPanel();
				
				hPanel.add(anchor);
				
				VerticalPanel vPanel = new VerticalPanel();
				vPanel.add(hPanel);
				vPanel.addStyleName("swe-generic-vertical-panel");
				contentPanel.add(vPanel);
				
				//add advanced panel for typeOf element
				Panel advancedPanel = getEditPanel(new IButtonCallback() {
					@Override
					public void onClick() {
						//refreshChildren(getElements());
						//refreshParents(getParent());
						
						
						for(ISensorWidget widget : getElements()) {
							if(getName().equals("typeOf")) {
								handleTypeOf(widget);
							}
						}
					}
				});
				hPanel.add(advancedPanel);
			} else {
				anchor.setText("");
				anchor.setHref("");
			}
			
		} else {
			//generate client link url
			anchor.setHref(Utils.getCurrentURL(href));
			anchor.setText(linkName);
		}
		
		anchor.setTarget("_blank");
	}
	
	/**
	 * Handle attribute.
	 *
	 * @param widget the widget
	 */
	private void handleAttribute(ISensorWidget widget) {
		//get header from child value
		if(!widget.getElements().isEmpty()) {
			//the first one should be a value widget
			String value = widget.getElements().get(0).getName();
			//set header
			hidePanel.getHeaderTextAccessor().setText(value);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#activeMode(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE)
	 */
	@Override
	protected void activeMode(MODE mode) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorSectionWidget(getName(),niceName,(RNGElement) getRNGTag());
	}
}
