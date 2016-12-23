package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd;

import com.sensia.relaxNG.XSDString;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDStringPanel;

public class ViewXSDStringPanel extends XSDStringPanel{

	/**
	 * Instantiates a new sensor xsd string widget.
	 *
	 * @param data the data
	 */
	public ViewXSDStringPanel(final XSDString data, IRefreshHandler refreshHandler) {
		super(data, refreshHandler);
		
		textBox.setEnabled(false);
	}
}
