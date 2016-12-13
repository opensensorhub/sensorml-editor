package com.sensia.tools.client.swetools.editors.sensorml.utils;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DialogBox.Caption;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CloseDialog extends DialogBox {
	
	private ClickHandler closeHandler;
	private ClickHandler saveHandler;
	private Panel centerPanel;
	
	public CloseDialog(String title) {
		super(new ButtonCaption(title));
		setGlassEnabled(true);
		setAnimationEnabled(true);
		addStyleName("dialog");
		
		ButtonCaption ref = (ButtonCaption) this.getCaption();
		PushButton closeButton = ref.getCloseButton();
		// apply button face here closeButton;
		closeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				CloseDialog.this.hide(true);
				if(closeHandler != null) {
					closeHandler.onClick(event);
				}
			}
		} );
		
		// build main panel
		DockLayoutPanel mainPanel = new DockLayoutPanel(Unit.PX);
		
		centerPanel = new VerticalPanel();
		Panel bottomPanel = new VerticalPanel();
		
		ScrollPanel sPanel = new ScrollPanel(centerPanel);
		
		mainPanel.addStyleName("dialog-main");
		bottomPanel.addStyleName("dialog-bottom");
		
		// build bottom
		Button save = new Button("Save");
		save.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
        	   CloseDialog.this.hide();
        	   if(saveHandler != null) {
        		   saveHandler.onClick(event);
        	   }
           }
        });
		bottomPanel.add(save);
		
		mainPanel.addSouth(bottomPanel,40); //40px
		mainPanel.add(sPanel);
		
		this.add(mainPanel);
	}
	
	public void addCloseHandler(ClickHandler handler) {
		closeHandler = handler;
	}
	
	public void addSaveHandler(ClickHandler handler) {
		saveHandler = handler;
	}
	
	public void setContent(Panel panel) {
		this.centerPanel.clear();
		this.centerPanel.add(panel);
	}
}


/**
 * @author Cristiano Sumariva
 */
class ButtonCaption extends HorizontalPanel implements Caption {
	protected InlineLabel text;
	protected PushButton closeDialog;

	/**
	 * @return the button at caption
	 */
	public PushButton getCloseButton() {
		return closeDialog;
	}

	public ButtonCaption(String label) {
		super();
		setWidth("100%");
		setStyleName("Caption"); // so you have same styling as standard caption
									// widget
		closeDialog = new PushButton();
		
		closeDialog.addStyleName("dialog-close");
		add(text = new InlineLabel(label));
		add(closeDialog);
		setCellWidth(closeDialog, "1px"); // to make button cell minimal enough
											// to it
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.HasMouseDownHandlers#addMouseDownHandler(
	 * com.google.gwt.event.dom.client.MouseDownHandler)
	 */
	@Override
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		return addMouseDownHandler(handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.HasMouseUpHandlers#addMouseUpHandler(com.
	 * google.gwt.event.dom.client.MouseUpHandler)
	 */
	@Override
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		return addMouseUpHandler(handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.HasMouseOutHandlers#addMouseOutHandler(
	 * com.google.gwt.event.dom.client.MouseOutHandler)
	 */
	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addMouseOutHandler(handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.HasMouseOverHandlers#addMouseOverHandler(
	 * com.google.gwt.event.dom.client.MouseOverHandler)
	 */
	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addMouseOverHandler(handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.HasMouseMoveHandlers#addMouseMoveHandler(
	 * com.google.gwt.event.dom.client.MouseMoveHandler)
	 */
	@Override
	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		return addMouseMoveHandler(handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.event.dom.client.HasMouseWheelHandlers#
	 * addMouseWheelHandler(com.google.gwt.event.dom.client.MouseWheelHandler)
	 */
	@Override
	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		return addMouseWheelHandler(handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.HasHTML#getHTML()
	 */
	@Override
	public String getHTML() {
		return getElement().getInnerHTML();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.HasHTML#setHTML(java.lang.String)
	 */
	@Override
	public void setHTML(String html) {
		remove(text);
		insert(text, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.HasText#getText()
	 */
	@Override
	public String getText() {
		return text.getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.HasText#setText(java.lang.String)
	 */
	@Override
	public void setText(String text) {
		this.text.setText(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.safehtml.client.HasSafeHtml#setHTML(com.google.gwt.
	 * safehtml.shared.SafeHtml)
	 */
	@Override
	public void setHTML(SafeHtml html) {
		setHTML(html.asString());
	}
}
