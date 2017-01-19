package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.AbstractPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.PanelHelper;

public class SMLEditKeywordsPanel extends AbstractPanel<RNGElement>{

	public SMLEditKeywordsPanel(RNGElement tag) {
		super(tag);
		container = new HorizontalPanel();
	}

	@Override
	public String getName() {
		return getTag().getName();
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		IPanel keywordPanel = PanelHelper.findPanel(element, "keyword");
		IPanel keywordValuePanel = PanelHelper.findPanel(keywordPanel, "value");
		if(keywordValuePanel != null) {
			container.add(keywordValuePanel.getPanel());
		}
	}

	@Override
	protected AbstractPanel<RNGElement> newInstance() {
		return null;
	}
	
}
