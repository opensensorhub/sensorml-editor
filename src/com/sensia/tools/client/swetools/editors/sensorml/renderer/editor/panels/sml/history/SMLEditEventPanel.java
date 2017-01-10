package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.history;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;

public class SMLEditEventPanel extends EditSubSectionElementPanel{

	protected Panel timePanel;
	
	public SMLEditEventPanel(RNGElement element) {
		super(element);
		
		// add label header
		container.clear();
		timePanel = new HorizontalPanel();
		
		Panel hPanel = new HorizontalPanel();
		hPanel.add(label);
		hPanel.add(timePanel);
		hPanel.add(definition);
		hPanel.add(description);
		
		container.add(hPanel);
		container.add(innerContainer);
		
		timePanel.setVisible(false);
		timePanel.addStyleName("Event-panel");
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("time")) {
			SimplePanel linkIconPanel = new SimplePanel();
			linkIconPanel.addStyleName("link-icon-panel");
			timePanel.add(linkIconPanel);
			timePanel.add(element.getPanel());
			timePanel.setVisible(true);
		} else {
			super.addInnerElement(element);
		}
	}
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
