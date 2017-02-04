package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public class AdvancedXLinkHrefPanel extends AbstractAdvancedAttributeUriPanel {

	public AdvancedXLinkHrefPanel(RNGAttribute att,final IRefreshHandler refreshHandler) {
		super(att,refreshHandler);
	}

	public String getName() {
		return "href";
	}
    
    @Override
    public void addInnerElement(IPanel element) {
        super.addInnerElement(element);
        if (element instanceof EditValuePanel)
            ((EditValuePanel) element).setTextBoxSize(50);
    }
}
