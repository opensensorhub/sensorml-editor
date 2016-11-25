package com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.edit;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit.AbstractEditAttributeHrefPanel;

public class EditXLinkArcrolePanel extends AbstractEditAttributeHrefPanel{

	public EditXLinkArcrolePanel(RNGAttribute att) {
		super(att);
	}
	
	@Override
	public String getName() {
		return "arcrole";
	}
}
