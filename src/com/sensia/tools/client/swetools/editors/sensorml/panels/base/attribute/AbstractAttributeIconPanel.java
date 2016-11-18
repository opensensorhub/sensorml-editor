package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.OntologyPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.DataValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.ValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public abstract class AbstractAttributeIconPanel<T extends RNGTag> extends AbstractPanel<T>{

	/** The def image. */
	private Image defImage;
	
	private IPanel<? extends RNGTag> valuePanel;
	private Image ontologyImage;
	
	public AbstractAttributeIconPanel(T tag) {
		super(tag);
		
		defImage = new Image(GWT.getModuleBaseURL()+"images/icon_info.png");
		
		//defImage.setTitle(getValue());
		
		//open a new Window pointing to the name href given by the attribute name
		defImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(valuePanel != null) {
					String url = "";
					if(valuePanel.getTag() instanceof RNGValue) {
						url = ((RNGValue)valuePanel.getTag()).getText();
					} else if(valuePanel.getTag() instanceof RNGData<?>) {
						url = ((RNGData<?>)valuePanel.getTag()).getStringValue();
					}
					Window.open(url,"","");
				}
			}
		});
		
		defImage.addStyleName("def-icon");
		
		container.add(defImage);
		
		//creates an ontology icon
		ontologyImage = new Image(GWT.getModuleBaseURL()+"images/ontology.png");
		ontologyImage.setTitle("Ontology");
		ontologyImage.setStyleName("ontology-icon");
		
		//adds ontology handler and open a new Ontology Window
		ontologyImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
					final OntologyPanel ontologyPanel = new OntologyPanel();
					final DialogBox dialogBox = Utils.createEditDialogBox(ontologyPanel.getPanel(),"SWE Ontology",new IButtonCallback() {
						
						@Override
						public void onClick() {
							String value = ontologyPanel.getSelectedValue();
							if(valuePanel.getTag() instanceof RNGValue) {
								((RNGValue)valuePanel.getTag()).setText(value);
							} else if(valuePanel.getTag() instanceof RNGData<?>) {
								((RNGData<?>)valuePanel.getTag()).setStringValue(value);
							}
							
							ontologyImage.setTitle(value);
						}
					});
					dialogBox.show();
			}
		});
	}
	
	@Override
	public String getName() {
		return getTag().toString();
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getTag() instanceof RNGValue) {
			valuePanel =  element;
			((ValuePanel)valuePanel).setNiceLabel(false);
			defImage.setTitle(((RNGValue)valuePanel.getTag()).getText());
		} else if(element.getTag() instanceof RNGData<?>) {
			valuePanel =  element;
			((DataValuePanel)valuePanel).setNiceLabel(false);
			defImage.setTitle(((RNGData<?>)valuePanel.getTag()).getStringValue());
		}
	}

	@Override
	protected AbstractPanel<T> newInstance() {
		return null;
	}

	@Override
	protected void activeMode(com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel.MODE mode) {
		container.clear();
		
		String title = "";
		if(valuePanel.getTag() instanceof RNGValue) {
			title = ((RNGValue)valuePanel.getTag()).getText();
		} else if(valuePanel.getTag() instanceof RNGData<?>) {
			title = ((RNGData<?>)valuePanel.getTag()).getStringValue();
		}
		
		
		// update html value with edited value
		if(valuePanel != null) {
			defImage.setTitle(title);
		}
		
		if(mode == MODE.EDIT) {
			container.add(ontologyImage);
			ontologyImage.setTitle(title);
		} else if (mode == MODE.VIEW) {
			container.add(defImage);
		}
		
	}
}
