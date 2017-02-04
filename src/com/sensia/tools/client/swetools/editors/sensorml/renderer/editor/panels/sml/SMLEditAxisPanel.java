package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.line.EditGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public class SMLEditAxisPanel extends EditGenericLinePanel<RNGElement>{

	public SMLEditAxisPanel(RNGElement tag,IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
	}

	@Override
	protected void addInnerElement(IPanel<? extends RNGTag> element) {
		if (element.getName().equals("name")){
			labelPanel.add(element.getPanel());
		} else if (element instanceof EditValuePanel){
			((EditValuePanel) element).setTextBoxSize(50);
			afterDotsPanel.add(element.getPanel());
		} else {
			afterDotsPanel.add(element.getPanel());
		}
	}
}
