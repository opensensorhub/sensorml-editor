package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd;

import com.sensia.relaxNG.XSDDouble;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDDoublePanel;

public class ViewXSDDoublePanel extends XSDDoublePanel{

	/**
	 * Instantiates a new sensor xsd double widget.
	 *
	 * @param data the data
	 */
	public ViewXSDDoublePanel(XSDDouble data, IRefreshHandler refreshHandler) {
		super(data,refreshHandler);
		
		textBox.setEnabled(false);
	}
}
