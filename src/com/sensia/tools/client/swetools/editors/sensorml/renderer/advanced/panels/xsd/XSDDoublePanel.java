package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd;

import com.sensia.relaxNG.XSDDouble;

public class XSDDoublePanel extends XSDPanel<XSDDouble>{

	/** The Constant LENGTH. */
	private static final int LENGTH = 10;
	
	/** The Constant ALLOWED_CHARS. */
	private static final String ALLOWED_CHARS = ".-+e0123456789";
	
	/**
	 * Instantiates a new sensor xsd double widget.
	 *
	 * @param data the data
	 */
	public XSDDoublePanel(XSDDouble data) {
		super(data,LENGTH,ALLOWED_CHARS);
	}
}
