package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class EditAttributeReferenceFramePanel extends AbstractEditAttributeHrefPanel{

	public EditAttributeReferenceFramePanel(RNGAttribute tag) {
		super(tag);
	}

	@Override
	public String getName() {
		return "referenceFrame";
	}
}
