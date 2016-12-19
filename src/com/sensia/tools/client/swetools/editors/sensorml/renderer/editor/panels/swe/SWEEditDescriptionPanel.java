package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.element.AdvancedSimpleElementPanel;

public class SWEEditDescriptionPanel extends AdvancedSimpleElementPanel{

	public SWEEditDescriptionPanel(RNGElement tag) {
		super(tag);
	}

	@Override
	public String getName() {
		return "description";
	}
}
