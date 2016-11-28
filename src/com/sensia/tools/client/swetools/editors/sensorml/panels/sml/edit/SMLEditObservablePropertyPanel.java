package com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.EditAbstractGenericLinePanel;


public class SMLEditObservablePropertyPanel extends EditAbstractGenericLinePanel<RNGElement> {

	public SMLEditObservablePropertyPanel(RNGElement tag) {
		super(tag);
	}
	
	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getTag() instanceof RNGOptional && ((RNGOptional)element.getTag()).isSelected()) {
			for(IPanel<?> child : element.getElements()){
				String name = child.getName();
				if(name.equals("label")) {
					labelPanel.add(element.getPanel());
				} else if(name.equals("definition")) {
					defPanel.add(element.getPanel());
				} else if(name.equals("description")) {
					afterDotsPanel.add(element.getPanel());
				} else {
					container.add(element.getPanel());
				}
			}
		} else {
			container.add(element.getPanel());
		}
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}
