package com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.view;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.view.AbstractViewAttributeHrefPanel;

public class ViewXLinkHrefPanel extends AbstractViewAttributeHrefPanel{

	public ViewXLinkHrefPanel(RNGAttribute att) {
		super(att);
	}

	public String getName() {
		return "href";
	}
}
