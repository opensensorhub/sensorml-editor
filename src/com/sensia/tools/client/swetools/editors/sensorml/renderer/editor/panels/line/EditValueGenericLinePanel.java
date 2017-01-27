package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.line;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLHorizontalPanel;

// label
// uom
// value
public class EditValueGenericLinePanel extends EditGenericLinePanel<RNGElement>{

	
	protected Panel valuePanel;
	protected Panel constraintPanel;
	
	protected IPanel valueIPanel;
	protected IPanel constraintIPanel;
	
	public EditValueGenericLinePanel(RNGElement tag,IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		valuePanel = new SimplePanel();
		constraintPanel = new SMLHorizontalPanel();
		
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

