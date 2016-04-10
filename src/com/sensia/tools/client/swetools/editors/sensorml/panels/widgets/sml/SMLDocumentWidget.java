/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

/**
 * The Class SMLDocumentWidget is corresponding to the <sml:document> element.
 */
public class SMLDocumentWidget extends AbstractSensorElementWidget{

	/** The container. */
	private Panel container;
	
	/** The linkage. */
	private Anchor linkage;
	
	/** The description panel. */
	private Panel descriptionPanel;
	
	/** The extra panel. */
	private Panel extraPanel;
	
	/** The edit panel. */
	private Panel editPanel;
	
	/**
	 * Instantiates a new SML document widget.
	 */
	public SMLDocumentWidget() {
		super("document", TAG_DEF.SML, TAG_TYPE.ELEMENT);
		
		container = new HorizontalPanel();
		linkage = new Anchor();
		extraPanel = new HorizontalPanel();
		descriptionPanel = new SimplePanel();
		
		//adds advanced panel
		editPanel = getEditPanel(new IButtonCallback() {
			
			@Override
			public void onClick() {
				descriptionPanel.clear();
				for(ISensorWidget child : getElements()) {
					addSensorWidget(child);
				}
				
			}
		});

		//adds link
		container.add(linkage);
		//adds spaces
		container.add(new HTML(SensorConstants.HTML_SPACE));
		container.add(new HTML(SensorConstants.HTML_SPACE));
		
		//adds description
		container.add(descriptionPanel);
		//adds extra stuff
		container.add(extraPanel);
		//adds advanced panel
		container.add(editPanel);
		
		activeMode(getMode());
		
		//default name
		linkage.setText("Link");
		//opens in a new tab
		linkage.setTarget("_blank");
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
		//extraPanel.add(widget.getPanel());
		if(widget.getName().equals("URL")) {
			linkage.setHref(widget.getValue("URL", true));
		} else if(widget.getName().equals("description")) {
			//try CharacterString
			String value = widget.getValue("CharacterString", true);
			if(value == null || value.isEmpty()){
				value = widget.getValue("description", true);
			}
			descriptionPanel.add(new HTML(value));
		} else if(widget.getName().equals("name")) {
			String value = widget.getValue("CharacterString", true);
			if(value == null || value.isEmpty()){
				value = widget.getValue("name", true);
			}
			linkage.setText(value);
			
		}  else if(widget.getName().equals("onlineResource")) {
			//get href
			String href = widget.getValue("href", true);
			if(href != null) {
				linkage.setHref(href);
			}
		} else {
			extraPanel.add(widget.getPanel());
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLDocumentWidget();
	}
}
