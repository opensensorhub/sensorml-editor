package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd;

import com.sensia.relaxNG.XSDInteger;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDIntegerPanel;

public class ViewXSDIntegerPanel extends XSDIntegerPanel{

	/**
	 * Instantiates a new sensor xsd integer widget.
	 *
	 * @param data the data
	 */
	public ViewXSDIntegerPanel(final XSDInteger data, IRefreshHandler refreshHandler) {
		super(data,refreshHandler);
		
		textBox.setEnabled(false);
	}
}
