package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditElementPanel;

public class SMLEditKeywordsPanel extends EditElementPanel{

	public SMLEditKeywordsPanel(RNGElement tag, IRefreshHandler refreshHandler) {
		super(tag, refreshHandler);
	}
	
	public SMLEditKeywordsPanel(RNGElement tag) {
		super(tag);
	}
}
