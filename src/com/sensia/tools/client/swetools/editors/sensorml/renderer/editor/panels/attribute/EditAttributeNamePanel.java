package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value.ViewValuePanel;

public class EditAttributeNamePanel extends EditAttributePanel{

	public EditAttributeNamePanel(RNGAttribute data) {
		super(data);
		container.addStyleName("attribute-panel-edit");
	}

	@Override
	public String getName() {
		return "name";
	}
}
