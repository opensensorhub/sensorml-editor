package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd;

import com.sensia.relaxNG.XSDAnyURI;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDAnyURIPanel;

public class ViewXSDAnyURIPanel extends XSDAnyURIPanel {


	public ViewXSDAnyURIPanel(final XSDAnyURI data, IRefreshHandler refreshHandler) {
		super(data, refreshHandler);
		
		textBox.setEnabled(false);
	}
}
