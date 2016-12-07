package com.sensia.tools.client.swetools.editors.sensorml.panels.line;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;

public class ElementLinePanel extends AbstractPanel<RNGElement>{

	public ElementLinePanel(RNGElement tag) {
		super(tag);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		
	}

	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}

}
