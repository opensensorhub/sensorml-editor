package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedXLinkHrefPanel;

public class EditXLinkHrefPanel extends AdvancedXLinkHrefPanel{

	public EditXLinkHrefPanel(RNGAttribute att) {
		super(att,null);
	}

	public String getName() {
		return "href";
	}
}
