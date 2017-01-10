package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;

// label
// uom
// value
public class SWEEditTimeRangePanel extends SWEEditTimePanel{
	
	public SWEEditTimeRangePanel(RNGElement tag,IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}

