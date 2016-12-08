package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class EditXLinkRolePanel  extends AbstractEditAttributeOntologyIconPanel<RNGAttribute>{

	public EditXLinkRolePanel(RNGAttribute att) {
		super(att);
	}
	
	@Override
	public String getName() {
		return "role";
	}
}
