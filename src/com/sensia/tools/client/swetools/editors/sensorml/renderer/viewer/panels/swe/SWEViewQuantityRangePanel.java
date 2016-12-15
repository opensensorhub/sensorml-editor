package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditQuantityPanel;

// label
// uom
// value
public class SWEViewQuantityRangePanel extends SWEEditQuantityPanel{
	
	public SWEViewQuantityRangePanel(RNGElement tag) {
		super(tag);
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("value")){
			RNGElement valueElement = (RNGElement) element.getTag();
			String valueAsStr = valueElement.getChildValueText();
			if(valueAsStr != null) {
				String [] values = valueElement.getChildValueText().split(" ");
				valuePanel.add(new HTML(values[0]+" to "+values[1]));
			} else {
				valuePanel.add(element.getPanel());
			}
			
			valueIPanel = element;
			
			if(constraintIPanel != null) {
				constraintPanel.clear();
				constraintPanel.add(new HTML(" in "));
				constraintPanel.add(constraintIPanel.getPanel());
			}

		} else {
			super.addInnerElement(element);
		}
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}

