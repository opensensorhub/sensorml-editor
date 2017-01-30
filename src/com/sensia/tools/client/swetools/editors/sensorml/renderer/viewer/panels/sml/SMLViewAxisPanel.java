package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.line.ViewGenericLinePanel;

public class SMLViewAxisPanel extends ViewGenericLinePanel<RNGElement>{

	public SMLViewAxisPanel(RNGElement tag) {
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
		} else {
			// should be RNGValue or RNGData
			afterDotsPanel.add(element.getPanel());
		}
	}
}
