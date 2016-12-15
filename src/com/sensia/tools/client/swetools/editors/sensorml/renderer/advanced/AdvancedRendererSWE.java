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
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.swe.SWEAdvancedValuesPanel;

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
	
	/**
	 * Instantiates a new RNG renderer swe.
	 */
	public AdvancedRendererSWE() {}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRenderer#visit(com.sensia.relaxNG.RNGElement)
	 */
	@Override
	public void visit(RNGElement elt) {
		IPanel<RNGElement> widget = null;
		
		final String name = elt.getName();
		
		if(name.equalsIgnoreCase("description")) {
			widget = new SWEAdvancedDescriptionPanel(elt);
		} else if(name.equalsIgnoreCase("label")) {
			widget = new SWEAdvancedLabelPanel(elt);
		} else if(name.equalsIgnoreCase("identifier")) {
			widget = new SWEAdvancedIdentifierPanel(elt);
		} else if(name.equalsIgnoreCase("value")) {
			widget = new SWEAdvancedValuePanel(elt);
		} else if(name.equalsIgnoreCase("values")) {
			widget = new SWEAdvancedValuesPanel(elt);
		}
		
		if(widget != null) {
			pushAndVisitChildren(widget, elt.getChildren());
		} else {
			super.visit(elt);
		}
	}
}