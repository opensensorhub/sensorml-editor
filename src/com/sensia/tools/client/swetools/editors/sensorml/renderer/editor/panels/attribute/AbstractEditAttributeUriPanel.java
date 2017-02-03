package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public abstract class AbstractEditAttributeUriPanel extends EditAttributePanel{

	public AbstractEditAttributeUriPanel(RNGAttribute tag) {
		super(tag);
	}
    
    @Override
    public void addInnerElement(IPanel element) {
        super.addInnerElement(element);
        if (element instanceof EditValuePanel) {
            ((EditValuePanel) element).setTextBoxSize(50);
            ((EditValuePanel) element).setPlaceholderText("Enter URI");
        }
    }
	
	@Override
	protected AbstractPanel<RNGAttribute> newInstance() {
		return null;
	}
}
