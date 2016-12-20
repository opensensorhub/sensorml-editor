package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;

public class SWEEditVectorPanel extends EditSubSectionElementPanel{

	public SWEEditVectorPanel(RNGElement element) {
		super(element);
		
		label.clear();
		label.add(new HTML("Location"));
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
