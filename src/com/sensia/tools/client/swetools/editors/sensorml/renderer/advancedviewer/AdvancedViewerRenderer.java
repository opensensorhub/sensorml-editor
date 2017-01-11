/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.advancedviewer;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.relaxNG.XSDAnyURI;
import com.sensia.relaxNG.XSDBoolean;
import com.sensia.relaxNG.XSDDateTime;
import com.sensia.relaxNG.XSDDecimal;
import com.sensia.relaxNG.XSDDouble;
import com.sensia.relaxNG.XSDInteger;
import com.sensia.relaxNG.XSDString;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedAttributeDefinitionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedAttributePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedAttributeReferenceFramePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedXLinkArcrolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedXLinkHrefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedXLinkRolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedXLinkTitlePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advancedviewer.attribute.AdvancedViewerAttributeDefinitionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value.ViewValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd.ViewXSDAnyURIPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd.ViewXSDDateTimePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd.ViewXSDDecimalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd.ViewXSDDoublePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd.ViewXSDIntegerPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.xsd.ViewXSDStringPanel;

/**
 * <p>
 * <b>Title:</b> RNGRenderer
 * </p>
 * 
 * <p>
 * <b>Description:</b><br/>
 * Renders content of an RNG grammar using GWT widgets
 * </p>
 * 
 * <p>
 * Copyright (c) 2011
 * </p>.
 *
 * @author Alexandre Robin
 * @date Aug 27, 2011
 */
public class AdvancedViewerRenderer extends AdvancedRendererSML{
	
	
	public AdvancedViewerRenderer() {
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGData)
	 */
	@Override
	public void visit(RNGData<?> data) {
		EditValuePanel editPanel = new EditValuePanel(data, getRefreshHandler());
		editPanel.setEnable(false);
		push(editPanel);
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDString)
	 */
	@Override
	public void visit(XSDString data) {
		push(new ViewXSDStringPanel(data,getRefreshHandler()));
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDBoolean)
	 */
	@Override
	public void visit(XSDBoolean data) {
		GWT.log("into XSDBoolean");
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDDecimal)
	 */
	@Override
	public void visit(XSDDecimal data) {
		push(new ViewXSDDecimalPanel(data,getRefreshHandler()));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDDouble)
	 */
	@Override
	public void visit(XSDDouble data) {
		push(new ViewXSDDoublePanel(data,getRefreshHandler()));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDInteger)
	 */
	@Override
	public void visit(XSDInteger data) {
		push(new ViewXSDIntegerPanel(data,getRefreshHandler()));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDAnyURI)
	 */
	@Override
	public void visit(XSDAnyURI data) {
		push(new ViewXSDAnyURIPanel(data,getRefreshHandler()));
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDDateTime)
	 */
	@Override
	public void visit(XSDDateTime data) {
		push(new ViewXSDDateTimePanel(data,getRefreshHandler()));
		
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRenderer#visit(com.sensia.relaxNG.RNGAttribute)
	 */
	@Override
	public void visit(RNGAttribute att) {
		String name = att.getName();
		if(name.equals("definition")) {
			pushAndVisitChildren(new AdvancedViewerAttributeDefinitionPanel(att,getRefreshHandler()),att.getChildren());
		} else {
			super.visit(att);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGChoice)
	 */
	@Override
	public void visit(RNGChoice choice) {
		RNGTag selectedPattern = choice.getSelectedPattern();
		if(selectedPattern != null) {
			selectedPattern.accept(this);
		} 
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGZeroOrMore)
	 */
	@Override
	public void visit(RNGZeroOrMore zeroOrMore) {
		List<List<RNGTag>> patternInstances = zeroOrMore.getPatternInstances();
		for(List<RNGTag> tags : patternInstances) {
			this.visitChildren(tags);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGOptional)
	 */
	@Override
	public void visit(RNGOptional optional) {
		if(optional.isSelected()){
			this.visitChildren(optional.getChildren());
		}
	}
}
