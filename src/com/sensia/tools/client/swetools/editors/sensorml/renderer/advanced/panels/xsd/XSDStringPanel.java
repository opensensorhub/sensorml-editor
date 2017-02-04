package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd;

import com.sensia.relaxNG.XSDString;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;

public class XSDStringPanel extends EditValuePanel{

	public XSDStringPanel(final XSDString data, IRefreshHandler refreshHandler) {
		super(data,refreshHandler);
	}

}
