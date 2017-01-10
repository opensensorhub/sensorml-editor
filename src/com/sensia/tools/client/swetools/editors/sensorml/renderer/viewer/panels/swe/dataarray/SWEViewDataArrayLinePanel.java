package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.dataarray;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.dataarray.SWEEditDataArrayLinePanel;

public class SWEViewDataArrayLinePanel extends SWEEditDataArrayLinePanel {

	public SWEViewDataArrayLinePanel(RNGElement tag) {
		super(tag,null);
		
		editable = false;
	}
}