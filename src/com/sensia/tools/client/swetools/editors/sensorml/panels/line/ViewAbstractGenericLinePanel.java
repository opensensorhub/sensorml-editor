package com.sensia.tools.client.swetools.editors.sensorml.panels.line;

import com.sensia.relaxNG.RNGTag;

public abstract class ViewAbstractGenericLinePanel<T extends RNGTag> extends AbstractGenericLinePanel<T>{

	protected  ViewAbstractGenericLinePanel(T tag) {
		super(tag);
		
		// add styles
		beforeDotsPanel.addStyleName("line-generic-panel-before");
		labelPanel.addStyleName("line-generic-label-panel-before");
		defPanel.addStyleName("line-generic-definition-panel-before");
		afterDotsPanel.addStyleName("horizontal-panel");
	}
}
