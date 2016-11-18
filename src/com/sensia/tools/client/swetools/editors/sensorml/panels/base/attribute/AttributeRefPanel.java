package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class AttributeRefPanel extends AttributePanel{

	public AttributeRefPanel(RNGAttribute tag) {
		super(tag);
		isNiceLabel = false;
	}
	
	@Override
	public String getName() {
		return "ref";
	}
}
