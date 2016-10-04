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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRenderer;

/**
 * The Class SensorZeroOrMoreWidget.
 */
public class SensorZeroOrMoreWidget extends AbstractSensorElementWidget{

	/** The container. */
	private VerticalPanel container;
	
	/** The zero or more. */
	private RNGZeroOrMore zeroOrMore;
	
	/**
	 * Instantiates a new sensor zero or more widget.
	 *
	 * @param zeroOrMore the zero or more
	 */
	public SensorZeroOrMoreWidget(final RNGZeroOrMore zeroOrMore) {
		super("zeroOrMore", TAG_DEF.RNG, TAG_TYPE.ZERO_OR_MORE,zeroOrMore);
		
		this.zeroOrMore = zeroOrMore;
		container = new VerticalPanel();
		
		Label addButton = new Label(zeroOrMore.getAnnotation());
		addButton.addStyleName("rng-optional-select");
		
		addButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				RNGZeroOrMore cloneZeroOrMore = zeroOrMore.clone();
				zeroOrMore.addPatternInstance(cloneZeroOrMore.getChildren());
			}
		});
		
		final HorizontalPanel panel = new HorizontalPanel();
		panel.add(addButton);
		panel.add(new HTML(findLabel(zeroOrMore)));
		
		//container.add(getAddButtonPanel(zeroOrMore.getAnnotation(),findLabel(zeroOrMore)));
		container.add(panel);
		activeMode(getMode());
	}	

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		//do nothing
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
		return new SensorZeroOrMoreWidget(zeroOrMore.clone());
	}
}
