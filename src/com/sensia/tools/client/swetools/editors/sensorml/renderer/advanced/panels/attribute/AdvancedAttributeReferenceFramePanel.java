package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;

public class AdvancedAttributeReferenceFramePanel extends AbstractAdvancedAttributeUriPanel {

	public AdvancedAttributeReferenceFramePanel(RNGAttribute tag,final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
	}

	@Override
	public String getName() {
		return "referenceFrame";
	}
}
