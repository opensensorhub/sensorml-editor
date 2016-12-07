package com.sensia.tools.client.swetools.editors.sensorml.panels.swe;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.ElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.ViewAbstractGenericLinePanel;

/**
 * codeSpace
 * constraint [0..1]
 * value [0..1]
 * @author mathieu.dhainaut@gmail.com
 *
 */
public class SWECategoryPanel extends ViewAbstractGenericLinePanel<RNGElement>{

	protected Panel valuePanel;
	protected Panel codeSpace;
	protected Panel constraint;
	
	public SWECategoryPanel(RNGElement element) {
		super(element);
		
		valuePanel = new SimplePanel();
		constraint = new SimplePanel();
		codeSpace = new SimplePanel();
		
		afterDotsPanel.add(valuePanel);
		afterDotsPanel.add(constraint);
		afterDotsPanel.add(codeSpace);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("label")) {
			labelPanel.add(element.getPanel());
		} else if(element.getName().equals("definition")) {
			defPanel.add(element.getPanel());
		} else if(element.getName().equals("constraint")){
			constraint.add(element.getPanel());
		} else if(element.getName().equals("value")){
			valuePanel.add(element.getPanel());
		} else if(element.getName().equals("codeSpace")){
			codeSpace.add(element.getPanel());
		} else {
			afterDotsPanel.add(element.getPanel());
		}
	}
}
