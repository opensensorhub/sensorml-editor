package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.sml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.element.AdvancedSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public class SMLAdvancedDescriptionPanel extends AdvancedSimpleElementPanel{

	public SMLAdvancedDescriptionPanel(RNGElement element) {
		super(element,"description");
	}
    
    @Override
    public void addInnerElement(IPanel element) {
        super.addInnerElement(element);
        if (element instanceof EditValuePanel)
            ((EditValuePanel) element).setTextBoxSize(50);
    }

}
