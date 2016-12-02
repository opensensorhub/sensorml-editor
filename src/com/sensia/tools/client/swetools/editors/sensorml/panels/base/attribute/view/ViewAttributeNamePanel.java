package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.view;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.ViewValuePanel;

//TODO: may be useful to isolate into a separate panel to identify that panel comes from attribute
public class ViewAttributeNamePanel extends ViewAttributePanel{

	public ViewAttributeNamePanel(RNGAttribute data) {
		super(data);
	}

	@Override
	public String getName() {
		return "name";
	}
}
