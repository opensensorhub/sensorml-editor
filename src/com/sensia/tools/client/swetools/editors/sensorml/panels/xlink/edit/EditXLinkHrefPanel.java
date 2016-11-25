package com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.edit;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit.AbstractEditAttributeHrefPanel;

public class EditXLinkHrefPanel extends AbstractEditAttributeHrefPanel{

	public EditXLinkHrefPanel(RNGAttribute att) {
		super(att);
	}

	public String getName() {
		return "href";
	}
}
