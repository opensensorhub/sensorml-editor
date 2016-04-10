/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.APPENDER;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_DEF;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_TYPE;

/**
 * The Class SWESensorDataRecordWidget is corresponding to <swe:DataRecord>.
 */
public class SWESensorDataRecordWidget extends AbstractSensorElementWidget{

	/** The container. */
	private Panel container;
	
	/** The line panel. */
	private Panel linePanel;
	
	/** The label panel. */
	private Panel labelPanel;
	
	/** The def panel. */
	private Panel defPanel;
	
	/** The inner container. */
	private Panel innerContainer;
	
	/** The has label. */
	private boolean hasLabel= false;
	
	/**
	 * Instantiates a new SWE sensor data record widget.
	 */
	public SWESensorDataRecordWidget() {
		super("DataRecord", TAG_DEF.SWE, TAG_TYPE.ELEMENT);
		container = new VerticalPanel();
		linePanel = new HorizontalPanel();
		defPanel = new HorizontalPanel();
		labelPanel = new HorizontalPanel();
		innerContainer = new VerticalPanel();
		
		linePanel.add(labelPanel);
		linePanel.add(defPanel);
		
		container.add(linePanel);
		container.add(innerContainer);
		
		linePanel.setVisible(false);
		//innerContainer.addStyleName("swe-dataRecord-vertical-panel");
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
		if(widget.getName().equals("label") && widget.getDef() == TAG_DEF.SWE) {
			if(!widget.getElements().isEmpty()) {
				widget.getElements().get(0).getPanel().addStyleName("font-bold");
			}
			labelPanel.add(widget.getPanel());
			List<String> advancedTags = new ArrayList<String>();
			advancedTags.add("label");
			advancedTags.add("name");
			advancedTags.add("definition");
			
			//add advanced panel on Title
			Panel advancedPanel = getSimpleEditPanel(new IButtonCallback() {
				@Override
				public void onClick() {
					refreshChildren(getElements());
					refreshParents(getParent());
				}
			},advancedTags);
			linePanel.add(advancedPanel);
			linePanel.setVisible(true);
			hasLabel = true;
		} else if(widget.getName().equals("definition") && widget.getType() == TAG_TYPE.ATTRIBUTE) { 
			defPanel.add(widget.getPanel());
			linePanel.setVisible(true);
		} else if(widget.getName().equals("field") || widget.getName().equals("coordinate") || widget.getName().equals("time")){
			//container.add(widget.getPanel());
			//find every fields/coordinate/time and display them
			List<ISensorWidget> children = widget.getElements();
			
			//case where DataRecord is contained into DataRecord
			boolean foundDataRecord = false;
			for(ISensorWidget child : children){
				if(child.getName().equals("DataRecord")) {
					foundDataRecord = true;
					break;
				} 
			}
			
			if(!foundDataRecord) {
				innerContainer.add(widget.getPanel());
			} else {
				//DataRecord has been found, skips it and add its children to this widget
				for(ISensorWidget child : children){
					if(child.getName().equals("DataRecord")) {
						//skip DataRecord element
						for(ISensorWidget dataRecordChild : child.getElements()) {
							addSensorWidget(dataRecordChild);
						}
					} else {
						//TBD: add intermediate DataRecord name?
						//innerContainer.add(child.getPanel());
					}
				}
			}
			
			if(hasLabel) {
				innerContainer.addStyleName("swe-dataRecord-vertical-panel");
			} else {
				innerContainer.addStyleName("swe-dataRecord-nolabel-vertical-panel");
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SWESensorDataRecordWidget();
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#appendTo()
	 */
	public APPENDER appendTo() {
		return APPENDER.OVERRIDE_LINE;
	}
}
