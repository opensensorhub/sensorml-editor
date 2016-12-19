package com.sensia.tools.client.swetools.editors.sensorml.panels.generic;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;

public class EditIconPanel<T extends RNGTag> extends AbstractPanel<T>{

	/** The def image. */
	private Image defImage;
	
	private IPanel<? extends RNGTag> valuePanel;
	
	public EditIconPanel(T tag, Image image, String css) {
		this(tag,image,css,true);
	}
	
	public EditIconPanel(T tag, Image image, String css,boolean initHandler) {
		super(tag);
		defImage = image;
		defImage.addStyleName(css);
		container.add(defImage);
		
		if(initHandler) {
			initHandler();
		}
	}
	
	private void initHandler() {
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
	}
	
	@Override
	public String getName() {
		return getTag().toString();
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getTag() instanceof RNGValue) {
			valuePanel =  element;
			defImage.setTitle(((RNGValue)valuePanel.getTag()).getText());
		} else if(element.getTag() instanceof RNGData) {
			valuePanel =  element;
			defImage.setTitle(((RNGData)valuePanel.getTag()).getStringValue());
		} 
	}

	@Override
	protected AbstractPanel<T> newInstance() {
		return null;
	}
}
