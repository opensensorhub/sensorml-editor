package com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.edit.EditElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.EditAbstractGenericLinePanel;

public class SMLEditInputPanel extends EditElementPanel{

	private IPanel<?> namePanel;
	
	public SMLEditInputPanel(RNGElement element) {
		super(element);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element instanceof EditAbstractGenericLinePanel) {
			EditAbstractGenericLinePanel eltPanel = (EditAbstractGenericLinePanel) element;
			if(!eltPanel.isLabeled() && namePanel != null) {
				eltPanel.setLabel(namePanel.getPanel());
			}
			container.add(element.getPanel());
		} else if(element.getName().equals("name")) {
			namePanel = element;
		}
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
