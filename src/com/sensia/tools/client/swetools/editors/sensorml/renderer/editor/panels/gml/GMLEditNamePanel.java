package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class GMLEditNamePanel extends EditSimpleElementPanel{

	public GMLEditNamePanel(RNGElement element) {
		super(element,Utils.findLabel(element.getParent()));
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}
