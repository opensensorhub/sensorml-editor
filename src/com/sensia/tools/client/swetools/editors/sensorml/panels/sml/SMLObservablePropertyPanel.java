package com.sensia.tools.client.swetools.editors.sensorml.panels.sml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.AbstractGenericLinePanel;


public class SMLObservablePropertyPanel extends AbstractGenericLinePanel<RNGElement>{

	private boolean isLabel = false;
	
	public SMLObservablePropertyPanel(RNGElement tag) {
		super(tag);
	}
	
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("label")) {
			labelPanel.add(element.getPanel());
		} else if(element.getName().equals("definition")) {
			defPanel.add(element.getPanel());
		} else {
			afterDotsPanel.add(element.getPanel());
		}
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}
