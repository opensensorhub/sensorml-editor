package com.sensia.tools.client.swetools.editors.sensorml.panels.xsd;

import com.sensia.relaxNG.XSDString;

public class XSDStringPanel extends XSDPanel<XSDString>{

	/**
	 * Instantiates a new sensor xsd string widget.
	 *
	 * @param data the data
	 */
	public XSDStringPanel(final XSDString data) {
		super(data,getLength(data),null);
	}
	
	/**
	 * Gets the length.
	 *
	 * @param data the data
	 * @return the length
	 */
	private static int getLength(XSDString data){
		int length = -1;
		int fixedLength = data.getLength();
        if (fixedLength > 0)
            length = fixedLength;
        
        int maxLength = data.getMaxLength();
        if (maxLength > 0)
            length = maxLength;
        
        return length;
	}

}
