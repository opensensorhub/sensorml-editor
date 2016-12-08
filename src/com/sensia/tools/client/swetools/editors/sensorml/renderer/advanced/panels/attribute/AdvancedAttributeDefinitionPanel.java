package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class AdvancedAttributeDefinitionPanel extends AbstractAdvancedAttributeOntologyIconPanel<RNGAttribute>{

	public AdvancedAttributeDefinitionPanel(RNGAttribute tag) {
		super(tag);
	}

	@Override
	public String getName() {
		return "definition";
	}
}
