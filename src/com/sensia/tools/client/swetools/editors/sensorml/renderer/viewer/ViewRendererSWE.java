/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewIdentifierPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewLabelPanel;

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
public class ViewRendererSWE extends ViewRendererRNG implements RNGTagVisitor {
	
	/** The Constant SWE_NS_1. */
	protected final static String SWE_NS_1 = "http://www.opengis.net/swe/1.0.1";
	
	/** The Constant SWE_NS_2. */
	protected final static String SWE_NS_2 = "http://www.opengis.net/swe/2.0";

	/**
	 * Instantiates a new RNG renderer swe.
	 */
	public ViewRendererSWE() {}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRenderer#visit(com.sensia.relaxNG.RNGElement)
	 */
	@Override
	public void visit(RNGElement element) {
		String name = element.getName();
		if(name.equals("description")) {
			pushAndVisitChildren(new SWEViewDescriptionPanel(element), element.getChildren());
		} else if(name.equals("label")){
			pushAndVisitChildren(new SWEViewLabelPanel(element), element.getChildren());
		} else if(name.equals("identifier")) {
			pushAndVisitChildren(new SWEViewIdentifierPanel(element), element.getChildren());
		} else {
			super.visit(element);
		}
	}
}