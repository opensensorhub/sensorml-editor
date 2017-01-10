/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewCategoryPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewCountPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewIntervalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewLabelPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewQuantityPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewQuantityRangePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewTimePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewTimeRangePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewUOMPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewValuesPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.SWEViewVectorPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.swe.dataarray.SWEViewDataArrayPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;

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
		String nsUri = element.getNamespace();
		
		if (nsUri.equalsIgnoreCase(SMLEditorConstants.SWE_NS_1) || nsUri.equalsIgnoreCase(SMLEditorConstants.SWE_NS_2)) {
			if(name.equals("description")) {
				pushAndVisitChildren(new SWEViewDescriptionPanel(element), element.getChildren());
			} else if(name.equals("label")){
				pushAndVisitChildren(new SWEViewLabelPanel(element), element.getChildren());
			} else if(name.equals("label")){
				pushAndVisitChildren(new SWEViewLabelPanel(element), element.getChildren());
			} else if(name.equals("values")) {
				pushAndVisitChildren(new SWEViewValuesPanel(element), element.getChildren());
			} else if(name.equals("value")) {
				pushAndVisitChildren(new SWEViewValuePanel(element), element.getChildren());
			} else if(name.equals("uom")) {
				pushAndVisitChildren(new SWEViewUOMPanel(element), element.getChildren());
			}  else if(name.equals("interval")) {
				pushAndVisitChildren(new SWEViewIntervalPanel(element), element.getChildren());
			}  else if(name.equals("QuantityRange")) {
				pushAndVisitChildren(new SWEViewQuantityRangePanel(element), element.getChildren());
			}  else if(name.equals("Vector")) {
				pushAndVisitChildren(new SWEViewVectorPanel(element,getRefreshHandler()), element.getChildren());
			}  else if(name.equals("Category")) {
				pushAndVisitChildren(new SWEViewCategoryPanel(element), element.getChildren());
			} else if(name.equals("Time")) {
				pushAndVisitChildren(new SWEViewTimePanel(element), element.getChildren());
			} else if(name.equals("TimeRange")) {
				pushAndVisitChildren(new SWEViewTimeRangePanel(element), element.getChildren());
			} else if(name.equals("Quantity")) {
				pushAndVisitChildren(new SWEViewQuantityPanel(element), element.getChildren());
			} else if(name.equals("Count")) {
				pushAndVisitChildren(new SWEViewCountPanel(element), element.getChildren());
			} else if(name.equals("DataArray")) {
				pushAndVisitChildren(new SWEViewDataArrayPanel(element), element.getChildren());
			}  else {
				super.visit(element);
			}
		} else {
			super.visit(element);
		}
		
	}
}