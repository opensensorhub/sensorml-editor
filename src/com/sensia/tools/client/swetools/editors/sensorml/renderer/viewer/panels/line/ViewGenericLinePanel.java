package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.line;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.tools.client.swetools.editors.sensorml.panels.line.AbstractGenericLinePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advancedviewer.AdvancedViewerRenderer;
import com.sensia.tools.client.swetools.editors.sensorml.utils.CloseDialog;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

public abstract class ViewGenericLinePanel<T extends RNGTag> extends AbstractGenericLinePanel<T>{

	protected ViewGenericLinePanel(T tag) {
		super(tag);
		
		Label advancedButton = buildAdvancedButton(new AdvancedViewerRenderer());
		line.add(advancedButton);
	}
	
	@Override
	protected CloseDialog getAndDisplayAdvancedCloseDialog(Panel rootPanel, RNGElement element) {
		return Utils.displayDialogBox(rootPanel, "View "+element.getName());
	}
}
