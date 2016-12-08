package com.sensia.tools.client.swetools.editors.sensorml.panels.sml;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.view.ViewAbstractGenericLinePanel;

public class SMLTermPanel extends ViewAbstractGenericLinePanel<RNGElement>{

	
	protected Panel valuePanel;
	
	public SMLTermPanel(RNGElement tag) {
		super(tag);
		valuePanel = new SimplePanel();
		
		afterDotsPanel.add(valuePanel);
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
		} else if(element.getName().equals("value")){
			valuePanel.add(element.getPanel());
		} /*else {
			afterDotsPanel.add(element.getPanel());
		}*/
		//TODO: details panel
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
