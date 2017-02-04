package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd;

import com.sensia.relaxNG.XSDAnyURI;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public class XSDAnyURIPanel extends EditValuePanel{

	public XSDAnyURIPanel(final XSDAnyURI data, IRefreshHandler refreshHandler) {
		super(data, refreshHandler);
		setTextBoxSize(50);
	}
}
