/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.root;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGValue;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.relaxNG.XSDAnyURI;
import com.sensia.relaxNG.XSDBoolean;
import com.sensia.relaxNG.XSDDateTime;
import com.sensia.relaxNG.XSDDecimal;
import com.sensia.relaxNG.XSDDouble;
import com.sensia.relaxNG.XSDInteger;
import com.sensia.relaxNG.XSDString;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererRNG;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGZeroOrMorePanel;

/**
 * <p>
 * Simple renderer for add section popup<br/>
 * This renderer skips almost everything except optional content at the
 * first level of a SensorML profile
 * </p>
 *
 * @author Mathieu Dhainaut
 * @since Nov 25, 2016
 */
public class RootRenderer extends AdvancedRendererRNG {
	
	public RootRenderer() {
	}
	
	@Override
	public void visit(RNGElement elt) {
		//visitChildren(elt.getChildren());
	}
	
	@Override
	public void visit(RNGAttribute attribute) {
		//visitChildren(attribute.getChildren());
	}
	
	@Override
	public void visit(RNGValue val) {
	}

	@Override
	public void visit(RNGData<?> data) {
	}

	@Override
	public void visit(XSDString data) {
	}

	@Override
	public void visit(XSDBoolean data) {
	}

	@Override
	public void visit(XSDDecimal data) {
	}

	@Override
	public void visit(XSDDouble data) {
	}

	@Override
	public void visit(XSDInteger data) {
	}

	@Override
	public void visit(XSDAnyURI data) {
		
	}

	@Override
	public void visit(XSDDateTime data) {
		
	}
	
	@Override
	public void visit(RNGChoice choice) {
	}

	@Override
	public void visit(RNGOptional optional) {
		push(new RNGOptionalPanelNoContent(optional,getRefreshHandler()));
	}

	@Override
	public void visit(RNGZeroOrMore zeroOrMore) {
		push(new RNGZeroOrMorePanel(zeroOrMore,getRefreshHandler()));
	}
	
	@Override
	public String getDefaultStyle() {
		return "root";
	}
}