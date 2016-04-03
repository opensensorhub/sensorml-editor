/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_DEF;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.ISensorWidget.TAG_TYPE;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.base.SensorGenericHorizontalContainerWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.SWESensorCategoryWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.SWESensorCondtionWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.SWESensorPositionWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.SWESensorCurveWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.dataarray.SWESensorDataArrayWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.SWESensorAllowedValuesWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.SWESensorDataRecordWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.SWESensorQuantityRangeWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.SWESensorQuantityWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.SWESensorTimeRangeWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.SWESensorTimeWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.SWESensorVectorWidget;
import com.sensia.tools.client.swetools.editors.sensorml.panels.widgets.swe.position.SWESensorCoordinateWidget;

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
 * </p>
 * 
 * @author Alexandre Robin
 * @date Aug 27, 2011
 */
public class RNGRendererSWE extends RNGRenderer implements RNGTagVisitor {
	protected final static String SWE_NS_1 = "http://www.opengis.net/swe/1.0.1";
	protected final static String SWE_NS_2 = "http://www.opengis.net/swe/2.0";

	public RNGRendererSWE() {}

	@Override
	public void visit(RNGElement elt) {
		final ISensorWidget widget = getWidget(elt.getName());
		if(widget != null) {
			pushAndVisitChildren(widget, elt.getChildren());
		} else if(elt.getNamespace().equals(SWE_NS_1) || elt.getNamespace().equals(SWE_NS_2)) {
			pushAndVisitChildren(new SensorGenericHorizontalContainerWidget(elt.getName(), TAG_DEF.SWE, TAG_TYPE.ELEMENT), elt.getChildren());
		} else {
			super.visit(elt);
		}
	}
	
	protected ISensorWidget getWidget(final String name) {
		 if(name.equals("Quantity") || name.equals("Count")){
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
		} else if(name.equals("DataArray")){
			return new SWESensorDataArrayWidget();
		} else if(name.equals("Curve")){
			return new SWESensorCurveWidget();
		} else if(name.equals("DataRecord")){
			return new SWESensorDataRecordWidget();
		} else if(name.equals("condition")){
			return new SWESensorCondtionWidget();
		} else if(name.equals("position")){
			return new SWESensorPositionWidget();
		}else if(name.equals("AllowedValues") || name.equals("AllowedTokens")){
			return new SWESensorAllowedValuesWidget();
		} else {
			return super.getWidget(name);
		}
	}
}