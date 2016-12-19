package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.AbstractEditIconPanel;

public class SWEEditDescriptionPanel extends AbstractEditIconPanel<RNGElement>{

	public SWEEditDescriptionPanel(RNGElement tag) {
		super(tag,new Image(GWT.getModuleBaseURL()+"images/icon_question.png"),"description-icon",false);
	}

	@Override
	public String getName() {
		return "description-icon";
	}
}
