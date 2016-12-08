package com.sensia.tools.client.swetools.editors.sensorml.panels.sml;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.view.ViewAbstractGenericLinePanel;

public class SMLOriginPanel extends ViewAbstractGenericLinePanel<RNGElement>{

	public SMLOriginPanel(RNGElement tag) {
		super(tag);
		labelPanel.add(new HTML("Origin"));
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("definition")) {
			defPanel.add(element.getPanel());
		} else {
			afterDotsPanel.add(element.getPanel());
		} 
	}

}
