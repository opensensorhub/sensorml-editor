package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.element;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSectionElementPanel;

public class ViewSectionElementPanel extends EditSectionElementPanel{

	public ViewSectionElementPanel(final RNGElement tag, final IRefreshHandler refreshHandler) {
		super(tag,refreshHandler);
		
		advancedButtonPanel.setVisible(false);
	}
}