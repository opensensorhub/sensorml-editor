package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.line;

import com.google.gwt.user.client.ui.Label;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.AbstractGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;

public abstract class EditGenericLinePanel<T extends RNGTag> extends AbstractGenericLinePanel<T>{

	protected EditGenericLinePanel(T tag) {
		super(tag);
		
		Label advancedButton = buildAdvancedButton(new AdvancedRendererSML());
		line.add(advancedButton);
	}
}
