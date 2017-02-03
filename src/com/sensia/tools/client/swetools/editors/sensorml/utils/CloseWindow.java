package com.sensia.tools.client.swetools.editors.sensorml.utils;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.LayoutSpacer;

public class CloseWindow extends Window {
		
	public CloseWindow(String title,boolean autoSize) {
		this(title,autoSize,"dialog-main");
	}
	
	public CloseWindow(String title,boolean autoSize,String customCss) {
		setTitle(title);  
        setCanDragResize(true);  
        setShowResizer(true);
        setAutoCenter(true);
        setShowShadow(true);
        setAutoSize(autoSize);
        setShowMaximizeButton(true);
        setIsModal(true);
        if(!autoSize) {
			resizeTo(660, 550);
		} 
		
		// ensure the window is destroyed
		addCloseClickHandler(new CloseClickHandler() {			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				closeDialog();
			}
		});
		
	}
	
	protected void closeDialog() {
		hide();
		markForDestroy();
	}
	
	private void centerTitle() {
		// center title
		Label headerLabel = new Label(getTitle());                                                                  
		headerLabel.setWidth100();                                                                        
		headerLabel.setAlign(Alignment.CENTER);                                                           
		setAutoChildProperties("headerLabel", headerLabel);                                        
		LayoutSpacer l = new LayoutSpacer();                                                              
		l.setWidth(15);                                                                                   
		setHeaderControls(l, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
	}
	
	public void addCloseHandler(ClickHandler handler) {
	}
	
	public void setContent(Panel panel) {
		panel.setWidth("100%");
	    Canvas c = new Canvas();
	    c.addChild(panel);
		c.adjustForContent(true);
		c.setWidth100();
		c.setHeight100();
		c.setOverflow(Overflow.AUTO);
	    addItem(c);
		adjustForContent(true);
	}
	
	public void setContent(Widget panel) {
		addItem(panel);
        adjustForContent(true);
	}
	
	public void center() {
	}
	
	public void redrawDialog() {
		redraw();
	}
}
