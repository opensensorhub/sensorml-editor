package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public abstract class AbstractAdvancedAttributeUriPanel extends AdvancedAttributePanel {

	public AbstractAdvancedAttributeUriPanel(RNGAttribute tag,final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
	}
    
    @Override
    public void addInnerElement(IPanel element) {
        super.addInnerElement(element);
        if (element instanceof EditValuePanel) {
            ((EditValuePanel) element).setTextBoxSize(40);
            ((EditValuePanel) element).setPlaceholderText("Enter URI");
        }
    }

	@Override
	protected AbstractPanel<RNGAttribute> newInstance() {
		return null;
	}
}