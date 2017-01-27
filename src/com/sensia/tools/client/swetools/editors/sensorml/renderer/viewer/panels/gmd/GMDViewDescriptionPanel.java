package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.gmd;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;

public class GMDViewDescriptionPanel extends AbstractPanel<RNGElement>{

	private Panel valuePanel;

	public GMDViewDescriptionPanel(RNGElement element) {
		super(element);
		
		container = new SMLHorizontalPanel();
		valuePanel = new SimplePanel();
		
		// ensure element order
		container.add(valuePanel);
	}
	
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("CharacterString")) {
			valuePanel.add(element.getPanel());
		} 
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}