package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd;

import com.sensia.relaxNG.XSDAnyURI;

public class XSDAnyURIPanel extends XSDPanel<XSDAnyURI> {


	/**
	 * Instantiates a new sensor xsd any uri widget.
	 *
	 * @param data
	 *            the data
	 */
	public XSDAnyURIPanel(final XSDAnyURI data) {
		super(data, getLength(data), null);
	}

	/**
	 * Gets the length.
	 *
	 * @param data
	 *            the data
	 * @return the length
	 */
	private static int getLength(XSDAnyURI data) {
		int length = 60;

		int fixedLength = data.getLength();
		if (fixedLength > 0)
			length = fixedLength;

		int maxLength = data.getMaxLength();
		if (maxLength > 0)
			length = maxLength;

		return length;
	}
}
