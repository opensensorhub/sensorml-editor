/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.line.SensorGenericLineWidget;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;
	
/**
 * The Class SWESensorConditionWidget is corresponding to <swe:condition> element.
 */
public class SWESensorConditionWidget extends AbstractSensorElementWidget{

	/** The container. */
	private HorizontalPanel container;
	
	/** The inner container. */
	private SensorGenericLineWidget innerContainer;
	
	/**
	 * Instantiates a new SWE sensor condition widget.
	 */
	public SWESensorConditionWidget() {
		super("condition",TAG_DEF.SWE,TAG_TYPE.ATTRIBUTE);
		//create panels
		container = new HorizontalPanel();
		innerContainer = new SensorGenericLineWidget("conditionInner",TAG_DEF.SWE,TAG_TYPE.ELEMENT);
		
		Image conditionImage = new Image(GWT.getModuleBaseURL()+"images/condition.png");
		conditionImage.setTitle("Condition");
		
		FocusPanel conditionImageWrapper = new FocusPanel(conditionImage);
		
		//add icons
		conditionImageWrapper.addStyleName("graphic-icon");
		
		//add to container
		container.add(conditionImageWrapper);
		
		//add listeners
		conditionImageWrapper.addClickHandler(new ConditionImageWrapperHandler());
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget#getPanel()
	 */
	@Override
	public Panel getPanel() {
		return container;
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
		innerContainer.addElement(widget);
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorConditionWidget();
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#appendTo()
	 */
	@Override
	public APPENDER appendTo() {
		return APPENDER.HORIZONTAL;
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#isIcon()
	 */
	public boolean isIcon() {
		return true;
	}
	
	/**
	 * The Class ConditionImageWrapperHandler.
	 */
	private class ConditionImageWrapperHandler implements ClickHandler{

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
		 */
		@Override
		public void onClick(ClickEvent event) {
			final DialogBox dialogBox = Utils.createDialogBox(innerContainer.getPanel(),"Condition",null);
			dialogBox.show();
		}
	}

}
