package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.ValueGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.line.EditValueGenericLinePanel;

public class SWEEditCountPanel extends EditValueGenericLinePanel{
	
	public SWEEditCountPanel(RNGElement tag,IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}

