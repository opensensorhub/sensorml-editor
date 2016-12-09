/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer;

import com.google.gwt.core.shared.GWT;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewAttributeCodePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewAttributeDefinitionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewAttributeNamePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewAttributePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewAttributeRefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewAttributeReferenceFramePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewXLinkArcrolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewXLinkHrefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewXLinkRolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewXLinkTitlePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.gml.GMLViewDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.gml.GMLViewIdentifierPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.gml.GMLViewNamePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.root.ViewRootPanel;

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
public class ViewRendererSML extends ViewRendererSWE implements RNGTagVisitor {

	/**
	 * Instantiates a new RNG renderer sml.
	 */
	public ViewRendererSML() {
	}

	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRendererSWE#visit(com.sensia.relaxNG.RNGElement)
	 */
	@Override
	public void visit(RNGElement elt) {
		String eltName = elt.getName();
		String nsUri = elt.getNamespace();

		//skip the element and visit children
		if(skipList.contains(eltName)) {
			visitChildren(elt.getChildren());
			return;
		}
		
		if(rootSectionsList.contains(eltName)) {
			pushAndVisitChildren(new ViewRootPanel(),elt.getChildren());
			return;
		}
		
		// handle GML elements
		if (nsUri.equalsIgnoreCase(GML_NS_1) || nsUri.equalsIgnoreCase(GML_NS_2)) {
			// gml:identifier
			if(eltName.equalsIgnoreCase("identifier")){
				pushAndVisitChildren(new GMLViewIdentifierPanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("description")){
				pushAndVisitChildren(new GMLViewDescriptionPanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("name")){
				pushAndVisitChildren(new GMLViewNamePanel(elt), elt.getChildren());
			} 
			return;
		} else {
			// handle others
			super.visit(elt);
			return;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRenderer#visit(com.sensia.relaxNG.RNGAttribute)
	 */
	@Override
	public void visit(RNGAttribute att) {
		//TODO: can use Edit super classes instead of some of them?
		// handle xlink
		String nsUri = att.getNamespace();
		String name = att.getName();
		if (nsUri != null && nsUri.equalsIgnoreCase(XLINK)) {
			if(name.equals("role")){
				pushAndVisitChildren(new ViewXLinkRolePanel(att),att.getChildren());
			} else if(name.equals("arcrole")) {
				pushAndVisitChildren(new ViewXLinkArcrolePanel(att),att.getChildren());
			} else if(name.equals("href")) {
				pushAndVisitChildren(new ViewXLinkHrefPanel(att),att.getChildren());
			} else if(name.equals("title")) {
				//pushAndVisitChildren(new ViewXLinkTitlePanel(att),att.getChildren());
				//skip title
			} else {
				GWT.log("[WARN] Unsupported XLink element: "+name+". Skipped.");
				pushAndVisitChildren(new ViewAttributePanel(att),att.getChildren());
			}
		} else if(name.equals("referenceFrame")) {
			pushAndVisitChildren(new ViewAttributeReferenceFramePanel(att),att.getChildren());
		} else if(name.equals("definition")) {
			pushAndVisitChildren(new ViewAttributeDefinitionPanel(att),att.getChildren());
		} else if(name.equals("name")) {
			pushAndVisitChildren(new ViewAttributeNamePanel(att),att.getChildren());
		} else if(name.equals("ref")) {
			pushAndVisitChildren(new ViewAttributeRefPanel(att),att.getChildren());
		} else if(name.equals("code")) {
			pushAndVisitChildren(new ViewAttributeCodePanel(att),att.getChildren());
		} else {
			pushAndVisitChildren(new ViewAttributePanel(att),att.getChildren());
		}
	}
}
