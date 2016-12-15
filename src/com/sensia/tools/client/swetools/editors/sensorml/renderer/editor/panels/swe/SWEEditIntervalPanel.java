package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;

public class SWEEditIntervalPanel extends EditSimpleElementPanel{

	public SWEEditIntervalPanel(RNGElement element) {
		super(element,"interval");
		
		container.addStyleName("interval");
	}
}
