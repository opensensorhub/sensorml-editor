package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe;

import com.google.gwt.core.client.GWT;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewAttributeCodePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class SWEViewUOMPanel extends EditSimpleElementPanel{

	public SWEViewUOMPanel(RNGElement element) {
		super(element);
	}

	@Override
	public void addInnerElement(IPanel element) {
		if(element instanceof ViewAttributeCodePanel) {
			RNGAttribute codeAtt = (RNGAttribute) element.getTag();
			String code = Utils.getUOMSymbol(codeAtt.getChildValueText());
			((ViewAttributeCodePanel)element).setValue(code);
			// display only code
			super.addInnerElement(element);
		} 
	}
}
