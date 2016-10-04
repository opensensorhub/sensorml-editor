package com.sensia.tools.client.swetools.editors.sensorml.listeners;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.sensia.tools.client.swetools.editors.sensorml.RNGProcessorSML;
import com.sensia.tools.client.swetools.editors.sensorml.controller.IObserver;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.MODE;

public class LoadProfileButtonClickListener implements ClickHandler{

	private ListBox profileListBox;
	private RNGProcessorSML smlEditorProcessor;
	private Map<String,String> profiles;
	
	public LoadProfileButtonClickListener(final ListBox profileListBox,final Map<String,String> profiles,final RNGProcessorSML smlEditorProcessor) {
		this.profileListBox = profileListBox;
		this.smlEditorProcessor = smlEditorProcessor;
		this.profiles = profiles;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		if(profileListBox != null) {
			String key = profileListBox.getValue(profileListBox.getSelectedIndex());
			
			if(key != null && !key.isEmpty()){
				smlEditorProcessor.setMode(MODE.EDIT);
				smlEditorProcessor.parse(profiles.get(key));
			}
		} else {
			Window.alert("The content seems empty or invalid");
		}
	}

}
