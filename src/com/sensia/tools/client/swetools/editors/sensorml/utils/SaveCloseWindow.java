package com.sensia.tools.client.swetools.editors.sensorml.utils;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;

public class SaveCloseWindow extends CloseWindow {
	
	private Canvas buttonsPanel = new Canvas();
	private ClickHandler saveHandler;
	private boolean exitOnSave = true;
	
	public SaveCloseWindow(String title,boolean autoSize) {
		this(title, autoSize,"dialog-main");
	}
	
	public SaveCloseWindow(String title,boolean autoSize,String customCss) {
		super(title,autoSize,customCss);
	}
	
	public void setExitOnSave(boolean exitOnSave) {
		this.exitOnSave = exitOnSave;
	}
	
	@Override
	public void setContent(Widget panel) {
		super.setContent(panel);
		initSaveButton();
	}
	
	@Override
	public void setContent(Panel panel) {
		super.setContent(panel);
		initSaveButton();
	}
	
	
	private void initSaveButton() {
		draw();
		buttonsPanel.clear();
		
		final com.smartgwt.client.widgets.Button saveButton = new com.smartgwt.client.widgets.Button("Save");
		
		saveButton.setHeight(30);
		saveButton.setWidth(60);
		buttonsPanel.addChild(saveButton);
		buttonsPanel.addStyleName("smartgwt-dialog-save-button");
		
		setShowFooter(true);
		Canvas [] members = getFooter().getMembers();
		Canvas [] newMembers = new Canvas[members.length+1];
		
		newMembers[0] = buttonsPanel;
		
		int i=1;
		for(Canvas member:members){
			newMembers[i++] = member;
		}
		
		getFooter().setMembers(newMembers);
		
		saveButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(saveHandler != null) {
					saveHandler.onClick(null);
					if(exitOnSave){
						closeDialog();
					}
				}
			}
		});
	}
	
	public void showSaveButton() {
		
	}
	
	public void addCloseHandler(ClickHandler handler) {
	}
	
	public void addSaveHandler(ClickHandler handler) {
		this.saveHandler = handler;
	}
}
