package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;

public class SMLEditParameterPanel extends EditSubSectionElementPanel{

	
	public SMLEditParameterPanel(RNGElement element, IRefreshHandler refreshHandler) {
		super(element, refreshHandler);
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
