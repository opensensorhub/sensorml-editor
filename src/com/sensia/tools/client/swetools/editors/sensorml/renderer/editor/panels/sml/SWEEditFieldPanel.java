package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;

public class SWEEditFieldPanel extends EditSubSectionElementPanel{

	public SWEEditFieldPanel(RNGElement element) {
		super(element);
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
