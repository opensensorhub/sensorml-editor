package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

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
