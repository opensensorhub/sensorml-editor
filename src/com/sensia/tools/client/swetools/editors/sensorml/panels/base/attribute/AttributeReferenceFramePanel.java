package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class AttributeReferenceFramePanel extends AbstractAttributeHrefPanel{

	public AttributeReferenceFramePanel(RNGAttribute tag) {
		super(tag);
	}

	@Override
	public String getName() {
		return "referenceFrame";
	}
}
