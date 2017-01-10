package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.utils.NameRefResolver;

public class SMLEditSetModePanel extends SMLEditSetValuePanel{
	
	public SMLEditSetModePanel(RNGElement tag, NameRefResolver resolver,IRefreshHandler refreshHandler) {
		super(tag,resolver,refreshHandler);
	}
	
}
