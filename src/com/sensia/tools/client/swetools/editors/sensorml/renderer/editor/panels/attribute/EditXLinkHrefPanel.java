package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class EditXLinkHrefPanel extends AbstractEditAttributeHrefPanel{

	public EditXLinkHrefPanel(RNGAttribute att) {
		super(att);
	}

	public String getName() {
		return "href";
	}
}
