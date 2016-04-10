/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.SensorConstants;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

/**
 * The Class SMLKeywordsWidget is corresponding to the <sml:KeywordList> element.
 */
public class SMLKeywordsWidget extends AbstractSensorElementWidget {

	/** The container. */
	private HorizontalPanel container;
	
	/** The code space. */
	private HTML codeSpace;
	
	/** The keywords. */
	private HTML keywords;
	
	/** The separator. */
	private HTML separator;
	
	/** The edit panel. */
	private Panel editPanel;
	
	/**
	 * Instantiates a new SML keywords widget.
	 */
	public SMLKeywordsWidget() {
		super("KeywordList", TAG_DEF.SML, TAG_TYPE.ELEMENT);
		
		container = new HorizontalPanel();
		codeSpace = new HTML();
		keywords = new HTML();
		separator = new HTML(":"+SensorConstants.HTML_SPACE);
		
		container.add(new HTML("Keywords &nbsp;"));
		container.add(codeSpace);
		container.add(separator);
		container.add(keywords);
		
		editPanel = getEditPanel(new IButtonCallback() {
			
			@Override
			public void onClick() {
				keywords.setHTML("");
				for(ISensorWidget child : SMLKeywordsWidget.this.getElements()) {
					addSensorWidget(child);
				}
			}
		});
		
		container.add(editPanel);
		codeSpace.setVisible(false);
		
		activeMode(getMode());
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
		if(widget.getType() == TAG_TYPE.ELEMENT && widget.getName().equals("codeSpace")) {
			codeSpace.setVisible(true);
			codeSpace.setHTML("("+widget.getValue("href", true)+")");
		} else if(widget.getName().equals("keyword")) {
			keywords.setHTML(keywords.getHTML()+"&nbsp;&nbsp;"+widget.getValue("keyword", true));
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLKeywordsWidget();
	}

}
