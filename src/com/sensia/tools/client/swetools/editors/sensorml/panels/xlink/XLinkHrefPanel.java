package com.sensia.tools.client.swetools.editors.sensorml.panels.xlink;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.AbstractAttributeHrefPanel;

public class XLinkHrefPanel extends AbstractAttributeHrefPanel{

	public XLinkHrefPanel(RNGAttribute att) {
		super(att);
	}

	public String getName() {
		return "href";
	}
}
