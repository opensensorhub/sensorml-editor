package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.ElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.AbstractEditIconPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;

public class SWEViewDescriptionPanel extends AbstractEditIconPanel<RNGElement>{

	public SWEViewDescriptionPanel(RNGElement element) {
		super(element,new Image(GWT.getModuleBaseURL()+"images/icon_question.png"),"def-description",false);
	}

	@Override
	public String getName() {
		return "description-icon";
	}
}
