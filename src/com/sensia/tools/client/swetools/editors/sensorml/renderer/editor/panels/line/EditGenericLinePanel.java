package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.line;

import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.AbstractGenericLinePanel;

public abstract class EditGenericLinePanel<T extends RNGTag> extends AbstractGenericLinePanel<T>{

	protected EditGenericLinePanel(T tag,IRefreshHandler refreshHandler) {
		super(tag);
		super.refreshHandler = refreshHandler;
		//Label advancedButton = buildAdvancedButton(new AdvancedRendererSML());
		//line.add(advancedButton);
	}
}
