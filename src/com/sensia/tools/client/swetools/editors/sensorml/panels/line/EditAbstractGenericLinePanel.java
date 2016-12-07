package com.sensia.tools.client.swetools.editors.sensorml.panels.line;

import com.sensia.relaxNG.RNGTag;

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
