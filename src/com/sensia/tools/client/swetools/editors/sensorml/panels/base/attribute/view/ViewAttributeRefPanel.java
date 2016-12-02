package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.view;

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
