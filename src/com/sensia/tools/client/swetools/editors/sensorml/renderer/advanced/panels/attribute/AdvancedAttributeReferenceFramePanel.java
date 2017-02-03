package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class AdvancedAttributeReferenceFramePanel extends AbstractAdvancedAttributeUriPanel {

	public AdvancedAttributeReferenceFramePanel(RNGAttribute tag) {
		super(tag);
	}

	@Override
	public String getName() {
		return "referenceFrame";
	}
}
