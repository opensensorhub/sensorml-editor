package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;

public class SWEViewFieldPanel extends EditSubSectionElementPanel{

	public SWEViewFieldPanel(RNGElement element) {
		super(element,false);
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}