package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.view;

import com.sensia.relaxNG.RNGAttribute;

public class ViewAttributeDefinitionPanel extends AbstractViewAttributeIconPanel<RNGAttribute>{

	public ViewAttributeDefinitionPanel(RNGAttribute tag) {
		super(tag);
	}

	@Override
	public String getName() {
		return "definition";
	}
}
