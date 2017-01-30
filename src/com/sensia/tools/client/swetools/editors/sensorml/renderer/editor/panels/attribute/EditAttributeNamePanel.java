package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class EditAttributeNamePanel extends EditAttributePanel{

	public EditAttributeNamePanel(RNGAttribute data) {
		super(data);
	}

	@Override
	public String getName() {
		return "name";
	}
}
