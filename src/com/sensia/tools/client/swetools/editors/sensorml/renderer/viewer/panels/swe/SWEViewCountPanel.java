package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.line.ViewValueGenericLinePanel;

public class SWEViewCountPanel extends ViewValueGenericLinePanel{
	
	public SWEViewCountPanel(RNGElement tag) {
		super(tag);
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}

