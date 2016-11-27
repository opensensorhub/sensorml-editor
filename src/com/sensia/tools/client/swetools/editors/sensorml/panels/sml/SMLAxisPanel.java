package com.sensia.tools.client.swetools.editors.sensorml.panels.sml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.ViewAbstractGenericLinePanel;

public class SMLAxisPanel extends ViewAbstractGenericLinePanel<RNGElement>{

	public SMLAxisPanel(RNGElement tag) {
		super(tag);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("definition")) {
			defPanel.add(element.getPanel());
		} else if(element.getName().equals("name")){
			labelPanel.add(element.getPanel());
		} else if(element.getName().equals("label")){
			labelPanel.add(element.getPanel());
		}  else {
			afterDotsPanel.add(element.getPanel());
		}
	}
}
