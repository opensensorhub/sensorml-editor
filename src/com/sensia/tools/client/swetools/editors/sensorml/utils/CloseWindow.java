package com.sensia.tools.client.swetools.editors.sensorml.utils;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Panel;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;

public class CloseWindow extends Window {
	Canvas c = new Canvas();
	
	public CloseWindow(String title,boolean autoSize) {
		setTitle(title);  
        setCanDragResize(true);  
        setShowResizer(true);
        setAutoCenter(true);
        setShowShadow(true);
        setAutoSize(autoSize);
        //setIsModal(true);  
        //setShowModalMask(true);  
        
		if(!autoSize) {
			c.addStyleName("dialog-main");
			resizeTo(660, 550);
		} 
	}
	
	public void showSaveButton() {
	}
	
	public void addCloseHandler(ClickHandler handler) {
	}
	
	public void addSaveHandler(ClickHandler handler) {
	}
	
	public void setContent(Panel panel) {
		c.addStyleName("dialog-main");
		c.addChild(panel);
		c.adjustForContent(true);
		addItem(c);
		adjustForContent(true);
	}
	
	public void center() {
	}
	
	@Override
	public void redraw() {
		c.adjustForContent(true);
		//c.redraw();
		GWT.log("redrawing");
	}
}
