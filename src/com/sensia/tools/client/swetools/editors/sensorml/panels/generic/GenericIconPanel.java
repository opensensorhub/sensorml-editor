package com.sensia.tools.client.swetools.editors.sensorml.panels.generic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;

public abstract class GenericIconPanel<T extends RNGTag> extends AbstractPanel<T>{

	private Image icon;
	
	protected GenericIconPanel(T tag,String path) {
		super(tag);
		icon = new Image(GWT.getModuleBaseURL()+path);
		
		icon.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				onClickHandler();
			}
		});
		container.add(icon);
	}
	
	@Override
	public String getName() {
		return getTag().toString();
	}

	@Override
	protected AbstractPanel<T> newInstance() {
		return null;
	}

	protected abstract void onClickHandler();
}
