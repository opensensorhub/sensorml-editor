package com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.edit;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit.AbstractEditAttributeHrefPanel;

public class EditXLinkRolePanel  extends AbstractEditAttributeHrefPanel{

	public EditXLinkRolePanel(RNGAttribute att) {
		super(att);
	}
	
	@Override
	public String getName() {
		return "role";
	}
}
