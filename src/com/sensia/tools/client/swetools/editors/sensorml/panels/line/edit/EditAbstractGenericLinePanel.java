package com.sensia.tools.client.swetools.editors.sensorml.panels.line.edit;

import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.AbstractGenericLinePanel;

public abstract class EditAbstractGenericLinePanel<T extends RNGTag> extends AbstractGenericLinePanel<T>{

	protected  EditAbstractGenericLinePanel(T tag) {
		super(tag);
		
		// add styles
		beforeDotsPanel.addStyleName("line-generic-panel-before");
		labelPanel.addStyleName("line-generic-label-panel-before");
		defPanel.addStyleName("line-generic-definition-panel-before-edit");
		afterDotsPanel.addStyleName("horizontal-panel");
		dotsPanel.addStyleName("line-generic-dots-edit");
		container.addStyleName("line-generic-edit");
	}
}
