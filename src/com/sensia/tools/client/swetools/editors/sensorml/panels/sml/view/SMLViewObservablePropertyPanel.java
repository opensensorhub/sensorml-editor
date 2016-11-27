package com.sensia.tools.client.swetools.editors.sensorml.panels.sml.view;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.ViewAbstractGenericLinePanel;


public class SMLViewObservablePropertyPanel extends ViewAbstractGenericLinePanel<RNGElement>{

	private boolean isLabel = false;
	
	public SMLViewObservablePropertyPanel(RNGElement tag) {
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
		} else if(element.getName().equals("description")) {
			afterDotsPanel.add(element.getPanel());
		}
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}
