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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

/**
 * The Class SMLContactWidget is corresponding to the <sml:contact> element.
 */
public class SMLContactWidget extends AbstractSensorElementWidget{

	/** The Constant PHONE_NAME. */
	private static final String PHONE_NAME = "voice";
	
	/** The Constant ORGANISATION_NAME. */
	private static final String ORGANISATION_NAME = "organisationName";
	
	/** The container. */
	private VerticalPanel container;
	
	/** The organisation panel. */
	private SimplePanel organisationPanel;
	
	/** The voice panel. */
	//phone
	private SimplePanel voicePanel;
	
	/** The delivery point panel. */
	//address
	private HorizontalPanel deliveryPointPanel;
	
	/** The city panel. */
	private HorizontalPanel cityPanel;
	
	/** The postal code panel. */
	private HorizontalPanel postalCodePanel;
	
	/** The country panel. */
	private HorizontalPanel countryPanel;
	
	/** The administrative area panel. */
	private HorizontalPanel administrativeAreaPanel;
	
	/** The electronic mail address panel. */
	private HorizontalPanel electronicMailAddressPanel;
	
	/**
	 * Instantiates a new SML contact widget.
	 */
	public SMLContactWidget() {
		super("contact", TAG_DEF.SML,TAG_TYPE.ELEMENT);
		container = new VerticalPanel();
		init();
	}

	/**
	 * Inits the widget.
	 */
	private void init() { 
		//organize the panel as:
		//1) organization
		//2) delivery point
		//3) city
		//4) administrative area
		//5) postal code
		//6) country
		//7) electronic mail
		organisationPanel = new SimplePanel();
		voicePanel = new SimplePanel();
		
		deliveryPointPanel = new HorizontalPanel();
		cityPanel = new HorizontalPanel();
		postalCodePanel = new HorizontalPanel();
		countryPanel = new HorizontalPanel();
		administrativeAreaPanel = new HorizontalPanel();
		electronicMailAddressPanel = new HorizontalPanel();;
		
		HorizontalPanel subAddress = new HorizontalPanel();
		//3) 4) 5)
		subAddress.add(cityPanel);
		subAddress.add(administrativeAreaPanel);
		subAddress.add(postalCodePanel);
		
		VerticalPanel addressPanel = new VerticalPanel();
		addressPanel.addStyleName("sub-address-panel");

		//2)
		addressPanel.add(deliveryPointPanel);
		//3) 4) 5)
		addressPanel.add(subAddress);
		//6)
		addressPanel.add(countryPanel);
		//7)
		addressPanel.add(electronicMailAddressPanel);
		
		HorizontalPanel hPanel = new HorizontalPanel();
		//1)
		hPanel.add(organisationPanel);
		
		//add advanced panel
		Panel advancedPanel = getEditPanel(new IButtonCallback() {
			@Override
			public void onClick() {
				refreshChildren(getElements());
				refreshParents(getParent());
			}
		});
		
		hPanel.add(advancedPanel);
		
		container.add(hPanel);
		container.add(addressPanel);
		container.add(voicePanel);
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
		if(widget.getType() == TAG_TYPE.ELEMENT && widget.getDef() == TAG_DEF.GMD) {
			if(widget.getName().equals(ORGANISATION_NAME)) {
				organisationPanel.add(widget.getPanel());
			} else if(widget.getName().equals(PHONE_NAME)) {
				voicePanel.add(widget.getPanel());
			} else if(widget.getName().equals("deliveryPoint")) {
				deliveryPointPanel.add(widget.getPanel());
			} else if(widget.getName().equals("city")) {
				cityPanel.add(widget.getPanel());
				
				HTML countrySeparator = new HTML(",");
				countrySeparator.setStyleName("sub-address-country-separator");
				cityPanel.add(countrySeparator);
			} else if(widget.getName().equals("administrativeArea")) {
				administrativeAreaPanel.add(widget.getPanel());
			} else if(widget.getName().equals("postalCode")) {
				postalCodePanel.add(widget.getPanel());
			} else if(widget.getName().equals("country")) {
				countryPanel.add(widget.getPanel());
			} else if(widget.getName().equals("electronicMailAddress")) {
				electronicMailAddressPanel.add(widget.getPanel());
			}
		}
		//skip others
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget#newInstance()
	 */
	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLContactWidget();
	}

}
