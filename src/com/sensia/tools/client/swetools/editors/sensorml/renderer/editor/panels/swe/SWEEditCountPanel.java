package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.ValueGenericLinePanel;

public class SWEEditCountPanel extends ValueGenericLinePanel{
	
	public SWEEditCountPanel(RNGElement tag) {
		super(tag);
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}

