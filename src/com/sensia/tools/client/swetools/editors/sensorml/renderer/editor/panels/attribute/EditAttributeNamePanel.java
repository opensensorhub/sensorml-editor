package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public class EditAttributeNamePanel extends EditAttributePanel{

	public EditAttributeNamePanel(RNGAttribute attr) {
		super(attr, false);
	}
    
    @Override
    public void addInnerElement(IPanel element) {
        super.addInnerElement(element);
        if (element instanceof EditValuePanel)
            ((EditValuePanel) element).setPlaceholderText("Enter name");
    }

	@Override
	public String getName() {
		return "name";
	}
}
