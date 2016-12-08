package com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;

public class EditAttributeCodeSpacePanel extends EditAttributePanel {

	public EditAttributeCodeSpacePanel(RNGAttribute tag) {
		super(tag);
		container = new HorizontalPanel();
	}

	protected AbstractPanel<RNGAttribute> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
