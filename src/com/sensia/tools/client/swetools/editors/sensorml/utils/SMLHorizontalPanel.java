package com.sensia.tools.client.swetools.editors.sensorml.utils;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class SMLHorizontalPanel extends FlowPanel{

	public enum SPACING {
		TOP,
		BOTTOM,
		RIGHT,
		LEFT
	}
	protected boolean isSpacing;
	protected SPACING[] spacing;
	
	public SMLHorizontalPanel(SPACING...spacing) {
		this();
		this.spacing = spacing;
	}
	
	public SMLHorizontalPanel(boolean addSpacing) {
		this();
		this.isSpacing = addSpacing;
	}
	
	public SMLHorizontalPanel() {
		addStyleName("panel-smlhorizontal");
	}
	
	@Override
	public void add(Widget widget) {
		/*FlowPanel child = new FlowPanel();
		child.add(widget);
		child.addStyleName("panel-smlhorizontal-child");
		if(isSpacing){
			child.addStyleName("spacing");
		}
		if(spacing != null) {
			for(SPACING currentSpacing:spacing){
				child.addStyleName("spacing-"+currentSpacing.name().toLowerCase());
			}
		}
		super.add(child);*/
	    
	    widget.addStyleName("panel-smlhorizontal-child");
        if(isSpacing){
            widget.addStyleName("spacing");
        }
        if(spacing != null) {
            for(SPACING currentSpacing:spacing){
                widget.addStyleName("spacing-"+currentSpacing.name().toLowerCase());
            }
        }
        super.add(widget);
	}
	
	public void addNoSpacing(Widget widget) {
	    widget.addStyleName("panel-smlhorizontal-child");
	    super.add(widget);
	}
	
}
