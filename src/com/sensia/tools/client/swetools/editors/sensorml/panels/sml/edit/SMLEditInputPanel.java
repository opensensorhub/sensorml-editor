package com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.edit.EditSubSectionElementPanel;

public class SMLEditInputPanel extends EditSubSectionElementPanel{

	public SMLEditInputPanel(RNGElement element) {
		super(element);
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
