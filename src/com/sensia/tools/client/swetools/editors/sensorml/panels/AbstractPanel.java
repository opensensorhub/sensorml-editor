/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/


package com.sensia.tools.client.swetools.editors.sensorml.panels;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.Renderer;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value.ViewValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.CloseWindow;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLVerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SaveCloseWindow;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

/**
 * The Class AbstractSensorElementWidget.
 */
public abstract class AbstractPanel<T extends RNGTag> implements IPanel<T>{

    protected T tag;
	protected Panel container;
	
	protected List<IPanel<? extends RNGTag>> children;
	protected boolean isNiceLabel;
	
	protected boolean isInLine= false;
	
	protected IRefreshHandler refreshHandler;
	
	protected AbstractPanel() {
		container = new SMLVerticalPanel();
		children = new ArrayList<IPanel<? extends RNGTag>>();
		isNiceLabel = true;
	}
	
	protected AbstractPanel(T tag) {
		this();
		this.tag = tag;
	}
	
	protected AbstractPanel(T tag,IRefreshHandler refreshHandler) {
		this(tag);
		this.refreshHandler = refreshHandler;
	}
	
	@Override
	public void addElement(IPanel<? extends RNGTag> element) {
		children.add(element);
		this.addInnerElement(element);
	}
	
	protected abstract void addInnerElement(IPanel<? extends RNGTag> element);
	
	@Override
	public List<IPanel<? extends RNGTag>> getElements() {
		return children;
	}

	@Override
	public Panel getPanel() {
		return container;
	}

	@Override
	public IPanel<T> cloneSensorWidget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAppender(APPENDER appender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAdvancedPanel(Panel container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isIcon() {
		return false;
	}

	public T getTag() {
		return this.tag;
	}
	
	protected abstract AbstractPanel<T> newInstance();
	
	protected static void setValueNiceLabel(IPanel<? extends RNGTag> root, boolean isNiceLabel) {
		if(root instanceof ViewValuePanel) {
			((ViewValuePanel)root).setNiceLabel(isNiceLabel);
		} else {
			for(IPanel<? extends RNGTag> child : root.getElements()) {
				setValueNiceLabel(child,isNiceLabel);
			}
		}
	}
	
	public boolean isInLine() {
		return isInLine;
	}
	
	protected Label buildAdvancedButton(final Renderer renderer) {
		// support only RNGElement
		if(!(getTag() instanceof RNGElement)) {
			return null;
		}
		
		Label advancedButton= new Label("");
		advancedButton.addStyleName("icons-advanced");
		final Panel rootPanel = new SMLVerticalPanel();
		
		advancedButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				advancedButtonClickHandler((RNGElement) getTag(),renderer,rootPanel);
			}
		});
		return advancedButton;
	}
	
	protected void advancedButtonClickHandler(final RNGElement element,final Renderer renderer,final Panel rootPanel) {
		// create a new Renderer
		renderer.reset();
		rootPanel.clear();
		final CloseWindow dialogBox = getAndDisplayAdvancedCloseDialog(rootPanel, element);
		
		renderer.setRefreshHandler(new IRefreshHandler() {
			
			@Override
			public void refresh() {
				renderer.reset();
				rootPanel.clear();
				renderer.visitChildren(element.getChildren());
				rootPanel.add(renderer.getRoot().getPanel());
				
				if(refreshHandler != null) {
					refreshHandler.refresh();
				}
				dialogBox.redrawDialog();
			}
		});

		renderer.visitChildren(element.getChildren());
		rootPanel.add(renderer.getRoot().getPanel());
		
		renderer.getRoot().getPanel().addStyleName("advanced-panel");
		dialogBox.redrawDialog();
		
		
	}
	
	protected CloseWindow getAndDisplayAdvancedCloseDialog(Panel rootPanel, RNGElement element) {
		final SaveCloseWindow dialogBox = Utils.displaySaveDialogBox(rootPanel, "Edit "+element.getName());
		dialogBox.addSaveHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				advancedButtonSaveHandler();
				dialogBox.redraw();
			}
		});
		return dialogBox;
	}
	
	protected void advancedButtonSaveHandler() {
		if(refreshHandler != null) {
			refreshHandler.refresh();
		}
	}
}
