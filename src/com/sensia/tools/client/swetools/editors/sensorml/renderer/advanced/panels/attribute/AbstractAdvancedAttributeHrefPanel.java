package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;

public abstract class AbstractAdvancedAttributeHrefPanel extends AdvancedAttributePanel {

	public AbstractAdvancedAttributeHrefPanel(RNGAttribute tag) {
		super(tag);
	}

	@Override
	protected AbstractPanel<RNGAttribute> newInstance() {
		return null;
	}
}