package com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.view;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.view.AbstractViewAttributeIconPanel;

public class ViewXLinkArcrolePanel extends AbstractViewAttributeIconPanel<RNGAttribute>{

	public ViewXLinkArcrolePanel(RNGAttribute att) {
		super(att);
	}
	
	@Override
	public String getName() {
		return "arcrole";
	}
}
