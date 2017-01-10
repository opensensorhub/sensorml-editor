package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.google.gwt.user.client.ui.HTML;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.line.EditGenericLinePanel;

public class SMLEditOriginPanel extends EditGenericLinePanel<RNGElement>{

	public SMLEditOriginPanel(RNGElement tag,IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		labelPanel.add(new HTML("Origin"));
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if(element.getName().equals("definition")) {
			defPanel.add(element.getPanel());
		} else {
			afterDotsPanel.add(element.getPanel());
		} 
	}

}
