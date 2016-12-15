/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditAllowedValuesPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditCategoryPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditConstraintPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditDataArrayPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditDataRecordPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditFieldPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditIntervalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditQuantityPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEEditQuantityRangePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEditElementTypePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.swe.SWEditEncodingPanel;

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
public class EditRendererSWE extends EditRendererRNG implements RNGTagVisitor {
	
	/**
	 * Instantiates a new RNG renderer swe.
	 */
	public EditRendererSWE() {}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRenderer#visit(com.sensia.relaxNG.RNGElement)
	 */
	@Override
	public void visit(RNGElement elt) {
		final String name = elt.getName();
		IPanel<RNGElement> widget = null;
		
		 if(name.equalsIgnoreCase("Quantity")) {
			widget = new SWEEditQuantityPanel(elt);
		} else if(name.equalsIgnoreCase("Category")) {
			widget = new SWEEditCategoryPanel(elt);
		} else if(name.equalsIgnoreCase("QuantityRange")) {
			widget = new SWEEditQuantityRangePanel(elt);
		} else if(name.equals("field")) {
			widget = new SWEEditFieldPanel(elt);
		} else if(name.equals("DataRecord")) {
			widget = new SWEEditDataRecordPanel(elt);
		} else if(name.equals("DataArray")) {
			widget = new SWEEditDataArrayPanel(elt);
		} else if(name.equals("elementType")) {
			widget = new SWEditElementTypePanel(elt);
		} else if(name.equals("encoding")) {
			widget = new SWEditEncodingPanel(elt);
		} else if(name.equals("constraint")) {
			widget = new SWEEditConstraintPanel(elt);
		} else if(name.equals("AllowedValues")) {
			widget = new SWEEditAllowedValuesPanel(elt);
		} else if(name.equals("interval")) {
			widget = new SWEEditIntervalPanel(elt);
		}
		
		if(widget != null) {
			pushAndVisitChildren(widget, elt.getChildren());
		} else {
			super.visit(elt);
		}
	}
}