package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.ValuePanel;

//TODO: may be useful to isolate into a separate panel to identify that panel comes from attribute
public class AttributeNamePanel extends AttributePanel{

	public AttributeNamePanel(RNGAttribute data) {
		super(data);
	}

	@Override
	public String getName() {
		return "name";
	}
}
