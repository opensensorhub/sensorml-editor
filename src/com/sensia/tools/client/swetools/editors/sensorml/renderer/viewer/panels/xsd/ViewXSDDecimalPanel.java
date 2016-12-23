package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd;

import com.sensia.relaxNG.XSDDecimal;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDDecimalPanel;

public class ViewXSDDecimalPanel extends XSDDecimalPanel{
	/**
	 * Instantiates a new sensor xsd integer widget.
	 *
	 * @param data the data
	 */
	public ViewXSDDecimalPanel(final XSDDecimal data, IRefreshHandler refreshHandler) {
		super(data,refreshHandler);
		
		textBox.setEnabled(false);
	}
}
