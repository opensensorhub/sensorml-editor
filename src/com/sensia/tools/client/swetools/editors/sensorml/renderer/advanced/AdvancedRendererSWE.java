/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IRefreshHandler;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditIdentifierPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditLabelPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditCategoryPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditQuantityPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditQuantityRangePanel;

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
	
}