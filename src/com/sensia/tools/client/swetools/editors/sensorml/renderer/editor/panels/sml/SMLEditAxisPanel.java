package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.google.gwt.core.shared.GWT;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.AbstractGenericLinePanel;

public class SMLEditAxisPanel extends AbstractGenericLinePanel<RNGElement>{

	public SMLEditAxisPanel(RNGElement tag) {
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
