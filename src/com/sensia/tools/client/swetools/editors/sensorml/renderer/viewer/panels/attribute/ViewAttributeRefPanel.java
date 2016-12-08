package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class ViewAttributeRefPanel extends ViewAttributePanel{

	public ViewAttributeRefPanel(RNGAttribute tag) {
		super(tag);
		isNiceLabel = false;
	}
	
	@Override
	public String getName() {
		return "ref";
	}
}
