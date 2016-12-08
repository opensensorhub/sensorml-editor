package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class EditAttributeRefPanel extends EditAttributePanel{

	public EditAttributeRefPanel(RNGAttribute tag) {
		super(tag);
		isNiceLabel = false;
	}
	
	@Override
	public String getName() {
		return "ref";
	}
}
