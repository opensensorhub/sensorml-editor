/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced;

import com.sensia.relaxNG.RNGElement;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.swe.SWEAdvancedDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.swe.SWEAdvancedIdentifierPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.swe.SWEAdvancedLabelPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.swe.SWEAdvancedValuePanel;

/**
 * <p>
 * <b>Title:</b> RNGRenderer
 * </p>
 * 
 * <p>
 * <b>Description:</b><br/>
 * Renders content of an RNG grammar describing SWE Common data components using
 * GWT widgets
 * </p>
 * 
 * <p>
 * Copyright (c) 2011
 * </p>.
 *
 * @author Alexandre Robin
 * @date Aug 27, 2011
 */
public class AdvancedRendererSWE extends AdvancedRendererRNG {
	
	/** The Constant SWE_NS_1. */
	protected final static String SWE_NS_1 = "http://www.opengis.net/swe/1.0.1";
	
	/** The Constant SWE_NS_2. */
	protected final static String SWE_NS_2 = "http://www.opengis.net/swe/2.0";

	/**
	 * Instantiates a new RNG renderer swe.
	 */
	public AdvancedRendererSWE() {}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRenderer#visit(com.sensia.relaxNG.RNGElement)
	 */
	@Override
	public void visit(RNGElement elt) {
		final IPanel<RNGElement> widget = getPanel(elt);
		if(widget != null) {
			pushAndVisitChildren(widget, elt.getChildren());
		} else {
			super.visit(elt);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRenderer#getWidget(java.lang.String)
	 */
	protected IPanel<RNGElement> getPanel(RNGElement elt) {
		final String name = elt.getName();
		
		if(name.equalsIgnoreCase("description")) {
			return new SWEAdvancedDescriptionPanel(elt);
		} else if(name.equalsIgnoreCase("label")) {
			return new SWEAdvancedLabelPanel(elt);
		} else if(name.equalsIgnoreCase("identifier")) {
			return new SWEAdvancedIdentifierPanel(elt);
		} else if(name.equalsIgnoreCase("value")) {
			return new SWEAdvancedValuePanel(elt);
		}
		return null;
	}
	
}