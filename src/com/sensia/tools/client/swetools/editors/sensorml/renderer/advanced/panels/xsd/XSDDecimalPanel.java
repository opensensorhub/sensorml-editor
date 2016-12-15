package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd;

import com.sensia.relaxNG.XSDDecimal;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;

public class XSDDecimalPanel extends XSDPanel<XSDDecimal>{
	/** The Constant ALLOWED_CHARS. */
	private static final String ALLOWED_CHARS = "-+0123456789";
	
	
	/**
	 * Instantiates a new sensor xsd integer widget.
	 *
	 * @param data the data
	 */
	public XSDDecimalPanel(final XSDDecimal data, IRefreshHandler refreshHandler) {
		super(data,getLength(data),ALLOWED_CHARS,refreshHandler);
	}
	
	/**
	 * Gets the length.
	 *
	 * @param data the data
	 * @return the length
	 */
	private static int getLength(XSDDecimal data){
		 int length = 10;
	        
	        int fixedLength = data.getTotalDigits();
	        if (fixedLength > 0)
	            length = fixedLength + 1;
        
        return length;
	}
}
