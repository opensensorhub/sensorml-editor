package com.sensia.tools.client.swetools.editors.sensorml.panels.xsd;

import com.sensia.relaxNG.XSDInteger;

public class XSDIntegerPanel extends XSDPanel<XSDInteger>{

	/** The Constant ALLOWED_CHARS. */
	private static final String ALLOWED_CHARS = "-+0123456789";
	
	/**
	 * Instantiates a new sensor xsd integer widget.
	 *
	 * @param data the data
	 */
	public XSDIntegerPanel(final XSDInteger data) {
		super(data,getLength(data),ALLOWED_CHARS);
	}
	
	/**
	 * Gets the length.
	 *
	 * @param data the data
	 * @return the length
	 */
	private static int getLength(XSDInteger data){
		 int length = 10;
	        
	        int fixedLength = data.getTotalDigits();
	        if (fixedLength > 0)
	            length = fixedLength + 1;
        
        return length;
	}
}
