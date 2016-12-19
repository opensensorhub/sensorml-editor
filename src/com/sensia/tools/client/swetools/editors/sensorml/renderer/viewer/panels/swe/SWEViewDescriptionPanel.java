package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;

public class SWEViewDescriptionPanel extends EditSimpleElementPanel{

	public SWEViewDescriptionPanel(RNGElement element) {
		super(element);
	}

	@Override
	public String getName() {
		return "description";
	}
}
