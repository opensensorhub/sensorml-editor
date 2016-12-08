package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class ViewXLinkArcrolePanel extends AbstractViewAttributeOntologyIconPanel<RNGAttribute>{

	public ViewXLinkArcrolePanel(RNGAttribute att) {
		super(att);
	}
	
	@Override
	public String getName() {
		return "arcrole";
	}
}
