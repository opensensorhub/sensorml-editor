package com.sensia.tools.client.swetools.editors.sensorml.utils;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;

public class SaveCloseWindow extends CloseWindow {
	
	private HLayout buttonsPanel = new HLayout();
	private ClickHandler saveHandler;
	private ClickHandler uploadHandler;
	private boolean exitOnSave = true;
	private boolean allowUpload = false;
	
	public SaveCloseWindow(String title,boolean autoSize) {
		this(title, autoSize,"dialog-main");
	}
	
	public SaveCloseWindow(String title,boolean autoSize,String customCss) {
		super(title,autoSize,customCss);
	}
	
	public void setExitOnSave(boolean exitOnSave) {
		this.exitOnSave = exitOnSave;
	}
    
    public void setAllowUpload(boolean allowUpload) {
        this.allowUpload = allowUpload;
    }
	
	@Override
	public void setContent(Widget panel) {
		super.setContent(panel);
		initSaveButtons();
	}
	
	@Override
	public void setContent(Panel panel) {
		super.setContent(panel);
		initSaveButtons();
	}
	
	
	private void initSaveButtons() {
		draw();
		
		buttonsPanel.clear();
		buttonsPanel.addStyleName("smartgwt-dialog-save-button");
		buttonsPanel.setMembersMargin(10);
		
		final com.smartgwt.client.widgets.Button saveButton = new com.smartgwt.client.widgets.Button("Save");
		saveButton.setHeight(30);
		saveButton.setWidth(60);
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
		buttonsPanel.addMember(saveButton);
		
		if (allowUpload) {
    		final com.smartgwt.client.widgets.Button uploadButton = new com.smartgwt.client.widgets.Button("Upload to Registry");
    		uploadButton.setHeight(30);
    		uploadButton.setWidth(150);
    		uploadButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {            
                @Override
                public void onClick(ClickEvent event) {
                    if(uploadHandler != null) {
                        uploadHandler.onClick(null);
                        if(exitOnSave){
                            closeDialog();
                        }
                    }
                }
            });
            buttonsPanel.addMember(uploadButton);
		}
        
        // add to dialog footer
		setShowFooter(true);
		Canvas [] members = getFooter().getMembers();
		Canvas [] newMembers = new Canvas[members.length+1];
		
		newMembers[0] = buttonsPanel;
		
		int i=1;
		for(Canvas member:members){
			newMembers[i++] = member;
		}
		
		getFooter().setMembers(newMembers);
	}
    
    public void showSaveButton() {
        
    }
	
	public void addCloseHandler(ClickHandler handler) {
	}
	
	public void addSaveHandler(ClickHandler handler) {
		this.saveHandler = handler;
	}
    
    public void addUploadHandler(ClickHandler handler) {
        this.uploadHandler = handler;
    }
}
