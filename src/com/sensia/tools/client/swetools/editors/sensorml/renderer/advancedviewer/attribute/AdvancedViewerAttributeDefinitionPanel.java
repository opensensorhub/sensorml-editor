package com.sensia.tools.client.swetools.editors.sensorml.renderer.advancedviewer.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AbstractAdvancedAttributeOntologyIconPanel;

public class AdvancedViewerAttributeDefinitionPanel extends AbstractAdvancedAttributeOntologyIconPanel{

	public AdvancedViewerAttributeDefinitionPanel(RNGAttribute tag,IRefreshHandler refreshHandler) {
		super(tag,"definition",refreshHandler);
		ontologyImage.setVisible(false);
		
	}

	@Override
	public String getName() {
		return "definition";
	}
}
