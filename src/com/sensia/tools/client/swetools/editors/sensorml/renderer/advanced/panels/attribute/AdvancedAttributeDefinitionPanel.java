package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;

public class AdvancedAttributeDefinitionPanel extends AbstractAdvancedAttributeOntologyIconPanel{

	public AdvancedAttributeDefinitionPanel(RNGAttribute tag,IRefreshHandler refreshHandler) {
		super(tag,"definition",refreshHandler);
	}

	@Override
	public String getName() {
		return "definition";
	}
}
