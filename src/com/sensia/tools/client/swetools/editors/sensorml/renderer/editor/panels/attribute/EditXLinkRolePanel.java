package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.AbstractEditIconPanel;

public class EditXLinkRolePanel  extends AbstractEditIconPanel<RNGAttribute>{

	public EditXLinkRolePanel(RNGAttribute att) {
		super(att,new Image(GWT.getModuleBaseURL()+"images/icon_info.png"),"def-icon");
	}
	
	@Override
	public String getName() {
		return "role";
	}
}
