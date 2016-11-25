package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.view;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.AttributePanel;

public class ViewAttributeRefPanel extends AttributePanel{

	public ViewAttributeRefPanel(RNGAttribute tag) {
		super(tag);
		isNiceLabel = false;
	}
	
	@Override
	public String getName() {
		return "ref";
	}
}
