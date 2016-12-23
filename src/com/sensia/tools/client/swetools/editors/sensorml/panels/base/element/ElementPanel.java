package com.sensia.tools.client.swetools.editors.sensorml.panels.base.element;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;

public class ElementPanel extends AbstractPanel<RNGElement>{

	public ElementPanel(RNGElement element) {
		super(element,null);
	}
	
	public ElementPanel(RNGElement element,IRefreshHandler refreshHandler) {
		super(element,refreshHandler);
	}
	
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		container.add(element.getPanel());
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
