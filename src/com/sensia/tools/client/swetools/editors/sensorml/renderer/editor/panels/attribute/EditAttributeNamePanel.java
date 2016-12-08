package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value.ViewValuePanel;

//TODO: may be useful to isolate into a separate panel to identify that panel comes from attribute
public class EditAttributeNamePanel extends EditAttributePanel{

	public EditAttributeNamePanel(RNGAttribute data) {
		super(data);
	}

	@Override
	public String getName() {
		return "name";
	}
}
