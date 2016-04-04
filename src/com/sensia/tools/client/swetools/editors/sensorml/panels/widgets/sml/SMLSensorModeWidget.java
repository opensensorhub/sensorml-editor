package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;

public class SMLSensorModeWidget extends AbstractSensorElementWidget{

	private static final String CSS_CLASS = "sml-mode-panel";
	
	private DisclosurePanel hidePanel;
	private VerticalPanel contentPanel;
	private Panel container;
	
	private HasText currentHeader;
	
	public SMLSensorModeWidget() {
		super("Mode",TAG_DEF.SML,TAG_TYPE.ELEMENT);
		
		hidePanel = new DisclosurePanel("Mode");
        hidePanel.setAnimationEnabled(true);
        hidePanel.setOpen(true);
        hidePanel.addStyleName(CSS_CLASS);
        
        contentPanel = new VerticalPanel();	
        hidePanel.setContent(contentPanel);
        
        container = new FlowPanel();
        container.add(hidePanel);
        
        contentPanel.addStyleName("sml-mode-inner-panel");
        
        currentHeader = hidePanel.getHeaderTextAccessor();
        hidePanel.getHeader().addStyleName("sml-mode-title");
	}

	@Override
	public Panel getPanel() {
		return container;
	}

	@Override
	protected void activeMode(MODE mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("id")) {
			currentHeader.setText(toNiceLabel(widget.getValue(widget.getName(), true)));
		} else if(widget.getName().equals("characteristics")) {
			//skip and add its children
			for(ISensorWidget child : widget.getElements()) {
				addSensorWidget(child);
			}
		} else {
			contentPanel.add(widget.getPanel());
		}
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLSensorModeWidget();
	}
	
	public APPENDER appendTo() {
		return APPENDER.OVERRIDE_LINE;
	}

}
