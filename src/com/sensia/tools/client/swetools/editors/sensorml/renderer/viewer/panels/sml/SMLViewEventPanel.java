package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.element.ViewSectionElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;

public class SMLViewEventPanel extends ViewSectionElementPanel{

	protected Panel timePanel;
	
	public SMLViewEventPanel(RNGElement element) {
		super(element,null);
		
		// add label header
		timePanel = new SMLHorizontalPanel();
		
		Widget currentHeader = sectionPanel.getHeader();
		SMLHorizontalPanel hPanel = new SMLHorizontalPanel();
		hPanel.add(currentHeader);
		hPanel.add(labelPanel);
		hPanel.add(timePanel);
		hPanel.add(definitionPanel);
		hPanel.add(descriptionPanel);
		hPanel.add(advancedButtonPanel);
		sectionPanel.setHeader(hPanel);
		
		timePanel.setVisible(false);
		timePanel.addStyleName("Event-panel");
		
		sectionPanel.setOpen(false);
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
