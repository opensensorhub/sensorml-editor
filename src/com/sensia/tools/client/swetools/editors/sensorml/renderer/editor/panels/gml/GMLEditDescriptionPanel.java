package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.XSDString;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDStringPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class GMLEditDescriptionPanel extends EditSimpleElementPanel{

	public GMLEditDescriptionPanel(RNGElement element) {
		super(element, "Description");
	}
	
	@Override
    public void addInnerElement(IPanel element) {
        if (element instanceof XSDStringPanel)
        {
            // replace it with a text area panel
            element = new EditValuePanel(((XSDString)element.getTag()), true, null);
            container.add(element.getPanel());
        }
    }
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}