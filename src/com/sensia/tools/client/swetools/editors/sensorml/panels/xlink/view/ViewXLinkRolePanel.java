package com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.view;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.view.AbstractViewAttributeIconPanel;

public class ViewXLinkRolePanel  extends AbstractViewAttributeIconPanel<RNGAttribute>{

	public ViewXLinkRolePanel(RNGAttribute att) {
		super(att);
	}
	
	@Override
	public String getName() {
		return "role";
	}
}
