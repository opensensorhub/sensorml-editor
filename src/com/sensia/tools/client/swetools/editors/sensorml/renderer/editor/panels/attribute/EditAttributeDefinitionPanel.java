package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.EditIconPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AbstractAdvancedAttributeOntologyIconPanel;

public class EditAttributeDefinitionPanel extends EditIconPanel<RNGAttribute>{

	public EditAttributeDefinitionPanel(RNGAttribute tag) {
		super(tag,new Image(GWT.getModuleBaseURL()+"images/icon_info.png"),"def-icon");
		this.setLinkTarget(AbstractAdvancedAttributeOntologyIconPanel.ONTOLOGY_TAB_NAME);
	}

	@Override
	public String getName() {
		return "definition";
	}
}
