package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class AttributeDefinitionPanel extends AbstractAttributeIconPanel<RNGAttribute>{

	public AttributeDefinitionPanel(RNGAttribute tag) {
		super(tag);
	}

	@Override
	public String getName() {
		return "definition";
	}
}
