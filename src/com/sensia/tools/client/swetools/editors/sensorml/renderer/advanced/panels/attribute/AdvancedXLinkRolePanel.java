package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class AdvancedXLinkRolePanel  extends AbstractAdvancedAttributeHrefPanel{

	public AdvancedXLinkRolePanel(RNGAttribute att) {
		super(att);
	}
	
	@Override
	public String getName() {
		return "role";
	}
}
