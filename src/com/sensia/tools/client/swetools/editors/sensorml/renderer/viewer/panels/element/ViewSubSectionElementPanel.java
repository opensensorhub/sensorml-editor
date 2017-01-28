package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.element;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditSubSectionElementPanel;

public class ViewSubSectionElementPanel extends EditSubSectionElementPanel{

	public ViewSubSectionElementPanel(RNGElement element,IRefreshHandler refreshHandler) {
		super(element,refreshHandler,false);
	}
}
