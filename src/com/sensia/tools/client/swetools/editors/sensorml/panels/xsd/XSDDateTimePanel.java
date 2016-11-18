package com.sensia.tools.client.swetools.editors.sensorml.panels.xsd;

import com.sensia.relaxNG.XSDDateTime;

public class XSDDateTimePanel extends XSDPanel<XSDDateTime>{

	/** The Constant LENGTH. */
	private static final int LENGTH = 28;
	
	/**
	 * Instantiates a new sensor xsd date time widget.
	 *
	 * @param data the data
	 */
	public XSDDateTimePanel(XSDDateTime data) {
		super(data,LENGTH,null);
	}
}
