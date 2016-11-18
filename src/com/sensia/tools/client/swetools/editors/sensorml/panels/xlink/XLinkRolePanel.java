package com.sensia.tools.client.swetools.editors.sensorml.panels.xlink;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.AbstractAttributeIconPanel;

public class XLinkRolePanel  extends AbstractAttributeIconPanel<RNGAttribute>{

	public XLinkRolePanel(RNGAttribute att) {
		super(att);
	}
	
	@Override
	public String getName() {
		return "role";
	}
}
