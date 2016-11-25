package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.view.AbstractViewAttributeHrefPanel;

public class EditAttributeReferenceFramePanel extends AbstractEditAttributeHrefPanel{

	public EditAttributeReferenceFramePanel(RNGAttribute tag) {
		super(tag);
	}

	@Override
	public String getName() {
		return "referenceFrame";
	}
}
