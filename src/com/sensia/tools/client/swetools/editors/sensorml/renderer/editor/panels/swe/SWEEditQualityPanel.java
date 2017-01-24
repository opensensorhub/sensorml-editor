package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;

public class SWEEditQualityPanel extends SWEEditFieldPanel{

	public SWEEditQualityPanel(RNGElement element,IRefreshHandler refreshHandler) {
		super(element,refreshHandler);
	}
	
	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
}
