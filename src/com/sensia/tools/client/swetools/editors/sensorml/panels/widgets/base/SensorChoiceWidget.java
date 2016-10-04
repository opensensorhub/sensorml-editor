/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

/**
 * The Class SensorChoiceWidget is corresponding to any "choice" element.
 */
public class SensorChoiceWidget extends AbstractSensorElementWidget{

	/** The choices. */
	private ListBox choices;
	
	/** The container. */
	private VerticalPanel container;
	
	/**
	 * Instantiates a new sensor choice widget.
	 */
	public SensorChoiceWidget(final RNGChoice choice) {
		super("choice", TAG_DEF.RNG, TAG_TYPE.CHOICE,choice);
		
		choices = new ListBox();
		container = new VerticalPanel();
		
		container.add(choices);
		
		container.setSpacing(5);
		
		choices.addItem("");
		
		List<RNGTag> children = choice.getItems();
		if(children != null){
			for(RNGTag child : children) {
				String label = findLabel(child);
				choices.addItem(label);
			}
		}
		
		if(choice.getSelectedIndex() != -1) {
			choices.setSelectedIndex(choice.getSelectedIndex()+1);
		}
		
		choices.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(choices.getSelectedIndex() == 0) {
					choice.setSelectedIndex(-1);
				} else {
					choice.setSelectedIndex(choices.getSelectedIndex()-1);
				}
			}
		});
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
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#addSensorWidget(com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget)
	 */
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.addStyleName("tabular-4x");
		hPanel.add(widget.getPanel());
		container.add(hPanel);
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorChoiceWidget((RNGChoice) getRNGTag());
	}
}
