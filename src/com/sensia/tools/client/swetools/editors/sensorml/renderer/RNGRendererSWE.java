/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.swe.SWECategoryPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.swe.SWEQuantityPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.swe.SWEQuantityRangePanel;

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
public class RNGRendererSWE extends RNGRenderer implements RNGTagVisitor {
	
	/** The Constant SWE_NS_1. */
	protected final static String SWE_NS_1 = "http://www.opengis.net/swe/1.0.1";
	
	/** The Constant SWE_NS_2. */
	protected final static String SWE_NS_2 = "http://www.opengis.net/swe/2.0";

	/**
	 * Instantiates a new RNG renderer swe.
	 */
	public RNGRendererSWE() {}

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
		
		/*if(name.equalsIgnoreCase("DataRecord")) {
			return new SWEDataRecordPanel(elt);
		} else*/ if(name.equalsIgnoreCase("Quantity")) {
			return new SWEQuantityPanel(elt);
		} /*else if(name.equalsIgnoreCase("field")) {
			return new SWEFieldPanel(elt);
		}*/ else if(name.equalsIgnoreCase("Category")) {
			return new SWECategoryPanel(elt);
		} else if(name.equalsIgnoreCase("QuantityRange")) {
			return new SWEQuantityRangePanel(elt);
		}
		/* if(name.equals("Quantity") || name.equals("Count")){
			return new SWESensorQuantityWidget();
		} else if(name.equals("Vector")){
			return new SWESensorVectorWidget();
		} else if(name.equals("coordinate")){
			return new SWESensorCoordinateWidget();
		} else if(name.equals("Time")){ 
			return new SWESensorTimeWidget();
		} else if(name.equals("QuantityRange")){
			return new SWESensorQuantityRangeWidget();
		} else if(name.equals("TimeRange")){
			return new SWESensorTimeRangeWidget();
		} else if(name.equals("Category")){
			return new SWESensorCategoryWidget();
		} else if(name.equals("DataArray") || name.equals("DataStream")){
			return new SWESensorDataArrayWidget();
		} else if(name.equals("Curve")){
			return new SWESensorCurveWidget();
		} else if(name.equals("DataRecord")){
			return new SWESensorDataRecordWidget();
		} else if(name.equals("condition")){
			return new SWESensorConditionWidget();
		} else if(name.equals("position")){
			return new SWESensorPositionWidget();
		}else if(name.equals("AllowedValues") || name.equals("AllowedTokens")){
			return new SWESensorAllowedValuesWidget();
		} else {
			return super.getWidget(name);
		}*/
		return null;
	}
}