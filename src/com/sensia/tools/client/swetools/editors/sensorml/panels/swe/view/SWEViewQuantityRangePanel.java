package com.sensia.tools.client.swetools.editors.sensorml.panels.swe.view;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;

// label
// uom
// value
public class SWEViewQuantityRangePanel extends SWEViewQuantityPanel{
	
	private IPanel valueIPanel;
	
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
				valueIPanel = element;
			} else {
				valuePanel.add(element.getPanel());
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

