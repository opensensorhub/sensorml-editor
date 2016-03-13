package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class SMLComponentWidget extends AbstractSensorElementWidget{

	private HorizontalPanel container;
	
	private Anchor anchorHref;
	private boolean isTitleProvided = false;
	
	public SMLComponentWidget() {
		super("component", TAG_DEF.SML, TAG_TYPE.ELEMENT);
		container = new HorizontalPanel();
		anchorHref = new Anchor();
		
		container.add(anchorHref);
	}

	@Override
	public Panel getPanel() {
		return container;
	}

	@Override
	protected void activeMode(MODE mode) {
		
	}

	@Override
	protected void addSensorWidget(ISensorWidget widget) {
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("name") && !isTitleProvided) {
			anchorHref.setText(widget.getValue("name", true));
		} else if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("title")) {
			anchorHref.setText(widget.getValue("title", true));
			isTitleProvided = true;
		} else if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("href")) {
			anchorHref.setHref(Utils.getCurrentURL(widget.getValue("href", true)));
		} else {
			container.add(widget.getPanel());
		}
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLComponentWidget();
	}

}
