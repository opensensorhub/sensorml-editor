package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;

public abstract class AbstractEditAttributeHrefPanel extends EditAttributePanel{

	public AbstractEditAttributeHrefPanel(RNGAttribute tag) {
		super(tag);
	}
	
	@Override
	protected AbstractPanel<RNGAttribute> newInstance() {
		return null;
	}
}
