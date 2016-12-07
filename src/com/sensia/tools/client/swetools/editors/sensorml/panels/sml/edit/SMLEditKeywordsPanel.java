package com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.edit.EditElementPanel;

public class SMLEditKeywordsPanel extends EditElementPanel{

	public SMLEditKeywordsPanel(RNGElement tag, IRefreshHandler refreshHandler) {
		super(tag, refreshHandler);
	}
	
	public SMLEditKeywordsPanel(RNGElement tag) {
		super(tag);
	}
}
