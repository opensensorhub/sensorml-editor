package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class AdvancedXLinkArcrolePanel extends AbstractAdvancedAttributeHrefPanel{

	public AdvancedXLinkArcrolePanel(RNGAttribute att) {
		super(att);
	}
	
	@Override
	public String getName() {
		return "arcrole";
	}
}
