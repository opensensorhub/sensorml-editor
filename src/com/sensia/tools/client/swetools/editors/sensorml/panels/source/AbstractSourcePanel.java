package com.sensia.tools.client.swetools.editors.sensorml.panels.source;

import com.google.gwt.user.client.ui.CheckBox;
import com.sensia.tools.client.swetools.editors.sensorml.RNGProcessorSML;

public abstract class AbstractSourcePanel implements ISourcePanel{

	protected RNGProcessorSML smlEditorProcessor;
	private CheckBox edit;
	
	public AbstractSourcePanel(final RNGProcessorSML smlEditorProcessor,final CheckBox edit) {
		this.smlEditorProcessor = smlEditorProcessor;
		this.edit = edit;
	}
	
	public void checkEditButton() {
		if(edit != null) {
			edit.setVisible(true);
			edit.setChecked(false);
		}
	}
}
