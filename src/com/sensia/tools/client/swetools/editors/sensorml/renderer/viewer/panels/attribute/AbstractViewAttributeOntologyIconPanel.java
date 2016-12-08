package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute;

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
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value.ViewValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public abstract class AbstractViewAttributeOntologyIconPanel<T extends RNGTag> extends AbstractPanel<T>{

	/** The def image. */
	private Image defImage;
	
	private IPanel<? extends RNGTag> valuePanel;
	
	public AbstractViewAttributeOntologyIconPanel(T tag) {
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
	}
	
	@Override
	public String getName() {
		return getTag().toString();
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getTag() instanceof RNGValue) {
			valuePanel =  element;
			((ViewValuePanel)valuePanel).setNiceLabel(false);
			defImage.setTitle(((RNGValue)valuePanel.getTag()).getText());
		} 
	}

	@Override
	protected AbstractPanel<T> newInstance() {
		return null;
	}
}
