package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit;

import com.sensia.relaxNG.RNGAttribute;

public class EditAttributeDefinitionPanel extends AbstractEditAttributeOntologyIconPanel<RNGAttribute>{

	public EditAttributeDefinitionPanel(RNGAttribute tag) {
		super(tag);
	}

	@Override
	public String getName() {
		return "definition";
	}
}
