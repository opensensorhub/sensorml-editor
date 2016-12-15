package com.sensia.tools.client.swetools.editors.sensorml.panels.line;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
public class ValueGenericLinePanel extends AbstractGenericLinePanel<RNGElement>{

	
	protected Panel valuePanel;
	protected Panel constraintPanel;
	
	protected IPanel valueIPanel;
	protected IPanel constraintIPanel;
	
	public ValueGenericLinePanel(RNGElement tag) {
		super(tag);
		valuePanel = new SimplePanel();
		constraintPanel = new HorizontalPanel();
		
		afterDotsPanel.add(valuePanel);
		afterDotsPanel.add(constraintPanel);
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
			valueIPanel = element;
			
			if(constraintIPanel != null) {
				constraintPanel.clear();
				constraintPanel.add(new HTML(" in "));
				constraintPanel.add(constraintIPanel.getPanel());
			}
			
		} else if(element.getName().equals("constraint")){
			constraintIPanel = element;
			
			if(valueIPanel != null) {
				constraintPanel.add(new HTML(" in "));
			}
			constraintPanel.add(element.getPanel());
		} 
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}

