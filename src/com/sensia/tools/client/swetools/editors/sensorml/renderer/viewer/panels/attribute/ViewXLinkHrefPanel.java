package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;

public class ViewXLinkHrefPanel extends AbstractViewAttributeHrefPanel{

	public ViewXLinkHrefPanel(RNGAttribute att) {
		super(att);
	}

	public String getName() {
		return "href";
	}
}
