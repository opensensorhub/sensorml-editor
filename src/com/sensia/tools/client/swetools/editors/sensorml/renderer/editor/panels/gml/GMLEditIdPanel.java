package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute.EditAttributePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class GMLEditIdPanel extends EditAttributePanel{

	public GMLEditIdPanel(RNGAttribute attrib) {
		super(attrib,"Local ID");
	}
    
    @Override
    public void addInnerElement(IPanel element) {
        if (element instanceof EditValuePanel) {
            ((EditValuePanel) element).setPlaceholderText("Enter XML ID");
        }
        super.addInnerElement(element);
    }
}