/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2016 DHAINAUT.
 All Rights Reserved.
 
 Contributor(s): 
    Mathieu DHAINAUT <mathieu.dhainaut@gmail.com>
 
 ******************************* END LICENSE BLOCK ***************************/


package com.sensia.tools.client.swetools.editors.sensorml.panels;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value.ViewValuePanel;

/**
 * The Class AbstractSensorElementWidget.
 */
public abstract class AbstractPanel<T extends RNGTag> implements IPanel<T>{

	private T tag;
	protected Panel container;
	
	protected List<IPanel<? extends RNGTag>> children;
	protected boolean isNiceLabel;
	
	protected AbstractPanel() {
		container = new VerticalPanel();
		children = new ArrayList<IPanel<? extends RNGTag>>();
		isNiceLabel = true;
	}
	
	protected AbstractPanel(T tag) {
		this.tag = tag;
		container = new VerticalPanel();
		children = new ArrayList<IPanel<? extends RNGTag>>();
		isNiceLabel = true;
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
		for(IPanel<? extends RNGTag> child : root.getElements()) {
			if(child instanceof ViewValuePanel) {
				((ViewValuePanel)child).setNiceLabel(isNiceLabel);
			} else {
				setValueNiceLabel(child,isNiceLabel);
			}
		}
	}
}
