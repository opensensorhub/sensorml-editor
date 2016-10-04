/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRenderer;

/**
 * The Class SensorZeroOrMoreWidget.
 */
public class SensorOptionalWidget extends AbstractSensorElementWidget{

	/** The container. */
	private VerticalPanel container;
	
	/** The zero or more. */
	private RNGOptional optional;
	
	/**
	 * Instantiates a new sensor zero or more widget.
	 *
	 * @param zeroOrMore the zero or more
	 */
	public SensorOptionalWidget(final RNGOptional optional) {
		super("optional", TAG_DEF.RNG, TAG_TYPE.OPTIONAL,optional);
		
		this.optional = optional;
		container = new VerticalPanel();
		
		final CheckBox checkbox = new CheckBox(findLabel(optional));
		checkbox.setValue(optional.isSelected());
		container.add(checkbox);
		//container.add(getAddButtonPanel(zeroOrMore.getAnnotation(),findLabel(zeroOrMore)));
		activeMode(getMode());
		
		checkbox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				optional.setSelected(checkbox.getValue());
				
			}
		});
	}	

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		//do nothing
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.addStyleName("tabular-4x");
		hPanel.add(widget.getPanel());
		container.add(hPanel);
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
		if(mode == MODE.EDIT){
			container.setVisible(true);
		} else {
			container.setVisible(false);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorOptionalWidget(optional.clone());
	}
}
