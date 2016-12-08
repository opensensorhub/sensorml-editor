package com.sensia.tools.client.swetools.editors.sensorml.panels.gml.edit;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.edit.EditSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public class GMLEditDescriptionPanel extends EditSimpleElementPanel{

	public GMLEditDescriptionPanel(RNGElement element) {
		super(element,Utils.findLabel(element.getParent()));
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}