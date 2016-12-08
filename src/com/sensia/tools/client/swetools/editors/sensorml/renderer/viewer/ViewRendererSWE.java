/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
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
public class ViewRendererSWE extends ViewRNGRenderer implements RNGTagVisitor {
	

	/**
	 * Instantiates a new RNG renderer swe.
	 */
	public ViewRendererSWE() {}
}