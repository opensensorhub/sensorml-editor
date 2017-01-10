package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.gml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.time.GMLEditTimePositionTypePanel;

public class GMLViewTimePositionPanel extends GMLEditTimePositionTypePanel{

	public GMLViewTimePositionPanel(RNGElement element) {
		super(element);
		// remove listbox to display only text
		indeterminatePositionPanel.clear();
	}
	
	@Override
	public void addInnerElement(IPanel element) {
		if(element.getName().equals("indeterminatePosition")){
			// get value
			indeterminatePositionPanel.add(element.getPanel());
			indeterminatePositionPanel.setVisible(true);
		} else {
			super.addInnerElement(element);
		}
	}
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}

