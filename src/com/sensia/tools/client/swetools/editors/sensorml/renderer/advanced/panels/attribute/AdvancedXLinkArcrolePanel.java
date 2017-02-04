package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;

public class AdvancedXLinkArcrolePanel extends AbstractAdvancedAttributeUriPanel {

	public AdvancedXLinkArcrolePanel(RNGAttribute att,final IRefreshHandler refreshHandler) {
		super(att,refreshHandler);
	}
	
	@Override
	public String getName() {
		return "arcrole";
	}
}
