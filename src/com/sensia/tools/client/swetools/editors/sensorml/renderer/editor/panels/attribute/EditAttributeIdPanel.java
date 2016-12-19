package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;

//TODO: may be useful to isolate into a separate panel to identify that panel comes from attribute
public class EditAttributeIdPanel extends EditAttributePanel{

	public EditAttributeIdPanel(RNGAttribute data) {
		super(data);
	}

	@Override
	public String getName() {
		return "id";
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		// do nothing
	}
}
