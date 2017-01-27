/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.simpledatatype;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;

/**
 * <p>
 * <b>Title:</b> RNGRenderer
 * </p>
 * 
 * <p>
 * <b>Description:</b><br/>
 * Renders content of an RNG grammar using GWT widgets
 * </p>
 * 
 * <p>
 * Copyright (c) 2011
 * </p>.
 *
 * @author Alexandre Robin
 * @date Aug 27, 2011
 */
public class DataRecordSimpleAdvancedRenderer extends AdvancedRendererSML {
	
	public DataRecordSimpleAdvancedRenderer() {
		super();
	}
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGElement)
	 */
	@Override
	public void visit(RNGElement elt) {
		String eltName = elt.getName();
		if(!eltName.equals("field")){
			super.visit(elt);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGZeroOrMore)
	 */
	@Override
	public void visit(RNGZeroOrMore zeroOrMore) {
	}
}