package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.line;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.AbstractGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advancedviewer.AdvancedViewerRenderer;
import com.sensia.tools.client.swetools.editors.sensorml.utils.CloseWindow;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public abstract class ViewGenericLinePanel<T extends RNGTag> extends AbstractGenericLinePanel<T>{

	protected ViewGenericLinePanel(T tag) {
		super(tag);
		
		Label advancedButton = buildAdvancedButton(new AdvancedViewerRenderer());
		container.add(advancedButton);
	}
	
	@Override
	protected CloseWindow getAndDisplayAdvancedCloseDialog(Panel rootPanel, RNGElement element) {
		return Utils.displayDialogBox(rootPanel, "View "+element.getName());
	}
}
