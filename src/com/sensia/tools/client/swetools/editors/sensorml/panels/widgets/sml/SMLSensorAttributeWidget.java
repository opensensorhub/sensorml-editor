/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.OntologyPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorAttributeWidget;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

/**
 * The Class SMLSensorAttributeWidget is corresponding to any <sml ..> attribute elements.
 */
public class SMLSensorAttributeWidget extends SensorAttributeWidget{

	/** The def image. */
	private Image defImage;
	
	/** The attribute. */
	private RNGAttribute attribute;
	
	/** The http url parenthesis. */
	private HTML httpUrlParenthesis;
	
	/** The accepting tag names for def icon. */
	private static Set<String> acceptingTagNamesForDefIcon = new HashSet<String>();
	
	/** The accepting tag names for parenthesis. */
	private static Set<String> acceptingTagNamesForParenthesis = new HashSet<String>();
	
	static {
		acceptingTagNamesForDefIcon.add("definition");
		acceptingTagNamesForDefIcon.add("role");
		acceptingTagNamesForDefIcon.add("arcrole");
		
		acceptingTagNamesForParenthesis.add("referenceFrame");
	}
	
	/**
	 * Instantiates a new SML sensor attribute widget.
	 *
	 * @param attribute the attribute
	 */
	public SMLSensorAttributeWidget(RNGAttribute attribute) {
		super(attribute.getName(),TAG_DEF.SML,TAG_TYPE.ATTRIBUTE);
		
		this.attribute = attribute;
		container = new HorizontalPanel();
		container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorGenericHorizontalContainerWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(final ISensorWidget widget) {
		//if name is identified as an icon
		if(acceptingTagNamesForDefIcon.contains(getName()) && widget.getType() == TAG_TYPE.VALUE) {
			defImage = new Image(GWT.getModuleBaseURL()+"images/icon_info.png");
			defImage.setTitle(widget.getName());
			
			//open a new Window pointing to the name href given by the attribute name
			defImage.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					Window.open(widget.getName(),"","");
				}
			});
			
			defImage.addStyleName("def-icon");
			container.add(defImage);
		} else if(acceptingTagNamesForParenthesis.contains(getName()) && widget.getType() == TAG_TYPE.VALUE) {
			//if name is identified to be into parenthesis
			httpUrlParenthesis  = new HTML("("+widget.getName()+")");
			httpUrlParenthesis.addStyleName("def-icon");
			container.add(httpUrlParenthesis);
		} else {
			super.addSensorWidget(widget);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#getAdvancedPanel(com.google.gwt.user.client.ui.Panel)
	 */
	@Override
	public void getAdvancedPanel(Panel container) {
		//add advanced panel
		//if name is identified as an icon
		if(acceptingTagNamesForDefIcon.contains(getName())) {
			HorizontalPanel hPanel = new HorizontalPanel();
			HTML hlabel = new HTML(getName());
			hlabel.setWidth("100px");
			hPanel.add(hlabel);
			
			final TextBox valueBox = new TextBox();
			//subtract from icon size (16px) and icon style (margin-left:15px)
			valueBox.setWidth("469px");
			valueBox.setText(this.getValue(getName(), true));
			
			hPanel.add(valueBox);
			
			//creates an ontology icon
			Image ontologyImage = new Image(GWT.getModuleBaseURL()+"images/ontology.png");
			ontologyImage.setTitle("Ontology");
			ontologyImage.setStyleName("ontology-icon");
			
			//adds ontology handler and open a new Ontology Window
			ontologyImage.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if(getMode() == MODE.EDIT) {
						final OntologyPanel ontologyPanel = new OntologyPanel();
						final DialogBox dialogBox = Utils.createEditDialogBox(ontologyPanel.getPanel(),"SWE Ontology",new IButtonCallback() {
							
							@Override
							public void onClick() {
								String value = ontologyPanel.getSelectedValue();
								setValue(getName(),value);
								valueBox.setText(value);
							}
						});
						dialogBox.show();
					} 
				}
			});
			
			hPanel.add(ontologyImage);
			valueBox.setEnabled(getMode() == MODE.EDIT);
			
			container.add(hPanel);
		} else {
			super.getAdvancedPanel(container);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#setValue(java.lang.String, java.lang.String)
	 */
	@Override
	public void setValue(String elementName,String value) {
		if(getName().equals(elementName)) {
			defImage.setTitle(value);
			RNGValue rngValue = attribute.getChildValue();
			if(value != null) {
				rngValue.setText(value);
			}
			//should get only 1 element: the widget value
			ISensorWidget valueWidget = getElements().get(0);
			if(valueWidget != null) {
				valueWidget.setValue(getName(), value);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		RNGValue rngValue = attribute.getChildValue();
		if(defImage != null) {
			defImage.setTitle(rngValue.getText());
		} else if(httpUrlParenthesis != null) {
			httpUrlParenthesis.setText("("+rngValue.getText()+")");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#appendTo()
	 */
	@Override
	public APPENDER appendTo() {
		return (acceptingTagNamesForDefIcon.contains(getName()) || acceptingTagNamesForParenthesis.contains(getName())) ? APPENDER.HORIZONTAL:APPENDER.NONE;
	}
}
