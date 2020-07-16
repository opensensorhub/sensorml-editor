package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value.ViewValuePanel;

public class SWEEditUOMPanel extends EditSimpleElementPanel{

	public SWEEditUOMPanel(RNGElement element) {
		super(element);
	}

	@Override
	public void addInnerElement(IPanel element) {
		if(element.getName().equals("code")){
		    
		    if (!element.getElements().isEmpty()) {
		        Object firstChild = element.getElements().get(0);
		        if (firstChild instanceof ViewValuePanel)
		            ((ViewValuePanel)firstChild).prettifyUomSymbols();
		    }
		    
			// display only code
			super.addInnerElement(element);
		} 
	}
}
