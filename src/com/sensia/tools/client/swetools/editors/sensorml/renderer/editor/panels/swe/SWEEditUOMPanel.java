package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;

public class SWEEditUOMPanel extends EditSimpleElementPanel{

	public SWEEditUOMPanel(RNGElement element) {
		super(element);
	}

	@Override
	public void addInnerElement(IPanel element) {
		if(element.getName().equals("code")){
			// display only code
			super.addInnerElement(element);
		} 
	}
}
