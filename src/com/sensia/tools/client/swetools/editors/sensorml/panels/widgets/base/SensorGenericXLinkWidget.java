package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.APPENDER;

public class SensorGenericXLinkWidget extends AbstractSensorElementWidget{

	private Panel container;
	private Anchor anchorHref;
	
	private boolean isTitleProvided = false;
	
	public SensorGenericXLinkWidget(String name, TAG_DEF def) {
		super(name, def, TAG_TYPE.ATTRIBUTE);
		container = new HorizontalPanel();
		
		//build xlink:href givent an element
		anchorHref = new Anchor();
		container.add(anchorHref);
		anchorHref.setText("("+toNiceLabel(getName())+")");
		anchorHref.addStyleName("xlink");
		anchorHref.setTarget("_blank");
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
	public void refresh() {
		anchorHref.setText("("+toNiceLabel(getName())+")");
		container.clear();
		container.add(anchorHref);
		for(ISensorWidget child : getElements()) {
			addSensorWidget(child);
		}
		
	}
	
	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getName().equals("name")) {
			if(!isTitleProvided) {
				anchorHref.setText(widget.getValue("name", true));
			}
		} else if(widget.getName().equals("title")) {
			anchorHref.setText(widget.getValue("title", true));
			isTitleProvided = true;
		} else if (widget.getName().equals("href")) {
			anchorHref.setHref(widget.getValue("href", true));
		} else {
			container.add(widget.getPanel());
		}
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SensorGenericXLinkWidget(getName(), getDef());
	}
	
	@Override
	public APPENDER appendTo() {
		return APPENDER.HORIZONTAL;
	}

}
