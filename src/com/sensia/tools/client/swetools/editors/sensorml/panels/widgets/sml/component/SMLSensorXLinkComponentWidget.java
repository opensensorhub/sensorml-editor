package com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.sml.component;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.tools.client.swetools.editors.sensorml.listeners.IButtonCallback;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.AbstractSensorElementWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class SMLSensorXLinkComponentWidget extends AbstractSensorElementWidget{
	private HorizontalPanel container;
	private HorizontalPanel extrasPanel;
	
	private Panel editPanel;
	
	private Anchor anchorHref;
	private boolean isTitleProvided = false;
	
	public SMLSensorXLinkComponentWidget() {
		super("component", TAG_DEF.SML, TAG_TYPE.ELEMENT);
		container = new HorizontalPanel();
		extrasPanel = new HorizontalPanel();
		
		anchorHref = new Anchor();
		anchorHref.setTarget("_blank");
		container.add(anchorHref);
		container.add(extrasPanel);
		
		editPanel = getEditPanel(new IButtonCallback() {
			
			@Override
			public void onClick() {
				isTitleProvided = false;
				extrasPanel.clear();
				for(ISensorWidget child : getElements()) {
					processWidget(child);
				}
			}
		});
		container.add(editPanel);
		
		activeMode(getMode());
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
		processWidget(widget);
	}

	private void processWidget(ISensorWidget widget) {
		if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("name") && !isTitleProvided) {
			anchorHref.setText(widget.getValue("name", true));
		} else if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("title")) {
			anchorHref.setText(widget.getValue("title", true));
			isTitleProvided = true;
		} else if(widget.getType() == TAG_TYPE.ATTRIBUTE && widget.getName().equals("href")) {
			anchorHref.setHref(Utils.getCurrentURL(widget.getValue("href", true)));
		} else {
			extrasPanel.add(widget.getPanel());
		}
	}

	@Override
	protected AbstractSensorElementWidget newInstance() {
		return new SMLSensorXLinkComponentWidget();
	}
}
