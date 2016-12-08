package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class ViewXLinkRolePanel  extends AbstractViewAttributeOntologyIconPanel<RNGAttribute>{

	public ViewXLinkRolePanel(RNGAttribute att) {
		super(att);
	}
	
	@Override
	public String getName() {
		return "role";
	}
}
