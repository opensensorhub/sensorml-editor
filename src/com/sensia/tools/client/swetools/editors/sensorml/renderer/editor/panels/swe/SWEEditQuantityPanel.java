package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.AbstractGenericLinePanel;

// label
// uom
// value
public class SWEEditQuantityPanel extends AbstractGenericLinePanel<RNGElement>{

	
	protected Panel valuePanel;
	protected Panel uomPanel;
	
	public SWEEditQuantityPanel(RNGElement tag) {
		super(tag);
		valuePanel = new SimplePanel();
		uomPanel = new SimplePanel();
		
		afterDotsPanel.add(valuePanel);
		afterDotsPanel.add(uomPanel);
	}
	
	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("label")) {
			labelPanel.add(element.getPanel());
		} else if(element.getName().equals("definition")) {
			defPanel.add(element.getPanel());
		} else if(element.getName().equals("uom")){
			uomPanel.add(element.getPanel());
		} else if(element.getName().equals("value")){
			valuePanel.add(element.getPanel());
		} else {
			//afterDotsPanel.add(element.getPanel());
		}
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}

