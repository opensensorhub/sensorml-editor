package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd;

import com.sensia.relaxNG.XSDDateTime;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDDateTimePanel;

public class ViewXSDDateTimePanel extends XSDDateTimePanel{

	/**
	 * Instantiates a new sensor xsd date time widget.
	 *
	 * @param data the data
	 */
	public ViewXSDDateTimePanel(XSDDateTime data, IRefreshHandler refreshHandler) {
		super(data,refreshHandler);
		
		textBox.setEnabled(false);
	}
}
