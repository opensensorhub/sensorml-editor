package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.time;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;

public class GMLEditTimePeriodPanel extends EditSimpleElementPanel{

	private Panel beginPanel;
	private Panel endPanel;
	private Panel durationPanel;
	private Panel timeIntervalPanel;
	
	public GMLEditTimePeriodPanel(RNGElement element) {
		super(element);
		container = new HorizontalPanel();
		
		beginPanel = new HorizontalPanel();
		endPanel = new HorizontalPanel();
		durationPanel = new SimplePanel();
		timeIntervalPanel = new SimplePanel();
		
		SimplePanel linkIconPanel = new SimplePanel();
		linkIconPanel.addStyleName("link-icon-panel");
		
		container.add(beginPanel);
		container.add(linkIconPanel);
		container.add(endPanel);
		
		container.add(durationPanel);
		container.add(timeIntervalPanel);
		
		durationPanel.setVisible(false);
		timeIntervalPanel.setVisible(false);
	}
	
	@Override
	public void addInnerElement(IPanel element) {
		if(element.getName().equals("begin") || element.getName().equals("beginPosition")){
			beginPanel.add(element.getPanel());
		} else if(element.getName().equals("end") || element.getName().equals("endPosition")){
			endPanel.add(element.getPanel());
		} else if(element.getName().equals("duration")) {
			durationPanel.add(element.getPanel());
			durationPanel.setVisible(true);
		} else if(element.getName().equals("timeInterval")) {
			timeIntervalPanel.add(element.getPanel());
			timeIntervalPanel.setVisible(true);
		}
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
