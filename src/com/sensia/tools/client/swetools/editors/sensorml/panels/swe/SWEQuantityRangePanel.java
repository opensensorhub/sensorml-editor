package com.sensia.tools.client.swetools.editors.sensorml.panels.swe;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel.MODE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.AbstractGenericLinePanel;

// label
// uom
// value
public class SWEQuantityRangePanel extends SWEQuantityPanel{
	
	private IPanel valueIPanel;
	
	public SWEQuantityRangePanel(RNGElement tag) {
		super(tag);
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("value")){
			RNGElement valueElement = (RNGElement) element.getTag();
			String [] values = valueElement.getChildValueText().split(" ");
			valuePanel.add(new HTML(values[0]+" to "+values[1]));
			valueIPanel = element;
		} else {
			super.addInnerElement(element);
		}
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
	
	@Override
	protected void activeMode(MODE mode) {
		valuePanel.clear();
		if(mode == MODE.EDIT){
			valuePanel.add(valueIPanel.getPanel());
		} else if(mode == MODE.VIEW) {
			RNGElement valueElement = (RNGElement) valueIPanel.getTag();
			String [] values = valueElement.getChildValueText().split(" ");
			valuePanel.add(new HTML(values[0]+" to "+values[1]));
		}
		super.activeMode(mode);
	}
}

