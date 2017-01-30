package com.sensia.tools.client.swetools.editors.sensorml.utils;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class SMLVerticalPanel extends FlowPanel{

	protected boolean isSpacing;
	
	public SMLVerticalPanel(boolean addSpacing) {
		this();
		this.isSpacing = addSpacing;
	}
	
	public SMLVerticalPanel() {
		addStyleName("panel-smlvertical");
	}
	@Override
	public void add(Widget widget) {
		FlowPanel child = new FlowPanel();
		child.add(widget);
		if(isSpacing){
			child.addStyleName("spacing");
		}
		super.add(child);
	}
}
