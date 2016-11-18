package com.sensia.tools.client.swetools.editors.sensorml.panels.rng;

import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;

public class RNGOptionalPanel extends AbstractPanel<RNGChoice>{

	public RNGOptionalPanel(RNGChoice tag) {
		super(tag);
	}
	
	@Override
	public String getName() {
		return "";
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		container.add(element.getPanel());
		
	}

	@Override
	protected AbstractPanel<RNGChoice> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
