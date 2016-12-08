package com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.edit;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.AbstractGenericLinePanel;

public class EditSubSectionElementPanel extends EditElementPanel{

	private IPanel<?> namePanel;
	
	public EditSubSectionElementPanel(RNGElement element) {
		super(element);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element instanceof AbstractGenericLinePanel) {
			AbstractGenericLinePanel eltPanel = (AbstractGenericLinePanel) element;
			if(!eltPanel.isLabeled() && namePanel != null) {
				eltPanel.setLabel(namePanel.getPanel());
			}
			container.add(element.getPanel());
		} else if(element.getName().equals("name")) {
			namePanel = element;
		} else {
			super.addInnerElement(element);
		}
	}
}
