package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class ViewAttributeReferenceFramePanel extends AbstractViewAttributeHrefPanel{

	public ViewAttributeReferenceFramePanel(RNGAttribute tag) {
		super(tag);
	}

	@Override
	public String getName() {
		return "referenceFrame";
	}
}
