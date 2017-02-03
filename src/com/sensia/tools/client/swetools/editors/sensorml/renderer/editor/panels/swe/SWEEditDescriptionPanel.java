package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.element.AdvancedSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public class SWEEditDescriptionPanel extends AdvancedSimpleElementPanel{

	public SWEEditDescriptionPanel(RNGElement tag) {
		super(tag);
	}
    
    @Override
    public void addInnerElement(IPanel element) {
        super.addInnerElement(element);
        if (element instanceof EditValuePanel)
            ((EditValuePanel) element).setTextBoxSize(50);
    }

	@Override
	public String getName() {
		return "description";
	}
}
