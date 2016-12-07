/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.shared.GWT;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit.EditAttributeDefinitionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit.EditAttributeReferenceFramePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.edit.EditElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.document.EditDocumentPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditInputPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditInputsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditObservablePropertyPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditOutputPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditOutputsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.edit.EditXLinkArcrolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.edit.EditXLinkHrefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.edit.EditXLinkRolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;

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
public class EditRNGRendererSML extends AdvancedRendererSML implements RNGTagVisitor {

	public EditRNGRendererSML() {
		/*renderEditElements.put("keywords", RENDER_ELEMENT_TYPE_EDIT.SECTION); 
		renderEditElements.put("inputs", RENDER_ELEMENT_TYPE_EDIT.SECTION);
		renderEditElements.put("parameters", RENDER_ELEMENT_TYPE_EDIT.SECTION);
		renderEditElements.put("outputs", RENDER_ELEMENT_TYPE_EDIT.SECTION);
		renderEditElements.put("characteristics", RENDER_ELEMENT_TYPE_EDIT.SECTION);
		renderEditElements.put("capabilities", RENDER_ELEMENT_TYPE_EDIT.SECTION);
		renderEditElements.put("identification", RENDER_ELEMENT_TYPE_EDIT.SECTION);
		renderEditElements.put("classification", RENDER_ELEMENT_TYPE_EDIT.SECTION);*/
		
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
			pushAndVisitChildren(new EditDocumentPanel(),elt.getChildren());
			return;
		}
		
		/*if(renderEditElements.containsKey(eltName)) {
			RENDER_ELEMENT_TYPE_EDIT type = renderEditElements.get(eltName);
			IPanel<? extends RNGTag> panel = null;
			
			switch(type) {
				case SECTION	: panel = renderGenericSectionPanel(elt);break;
				default:break;
			}
			if(type == RENDER_ELEMENT_TYPE_EDIT.SECTION	) {
				skipTags = true;
				pushAndVisitChildren(panel,elt.getChildren());
				skipTags = false;
			} else {
				pushAndVisitChildren(panel, elt.getChildren());
			}
			return;
		} else */
			/*// handle GML elements
			if (nsUri.equalsIgnoreCase(GML_NS_1) || nsUri.equalsIgnoreCase(GML_NS_2)) {
				super.visit(elt);
				return;
			} else if (nsUri.equalsIgnoreCase(GMD)) {
				super.visit(elt);
				return;
			} else if (nsUri.equalsIgnoreCase(SWE_NS_1) || nsUri.equalsIgnoreCase(SWE_NS_2)) {
				super.visit(elt);
				return;
			} else if (nsUri.equalsIgnoreCase(SML_NS_1) || nsUri.equalsIgnoreCase(SML_NS_2)) {
				// handle SML element
				if(eltName.equalsIgnoreCase("ObservableProperty")) {
					pushAndVisitChildren(new SMLEditObservablePropertyPanel(elt), elt.getChildren());
				} else {
					super.visit(elt);
				}
				return;
			} else {
				// handle others
				super.visit(elt);
				return;
			}*/
			if (nsUri.equalsIgnoreCase(SWE_NS_1) || nsUri.equalsIgnoreCase(SWE_NS_2)) {
				super.visit(elt);
				return;
			} else if (nsUri.equalsIgnoreCase(SML_NS_1) || nsUri.equalsIgnoreCase(SML_NS_2)) {
				// handle SML element
				if(eltName.equalsIgnoreCase("ObservableProperty")) {
					pushAndVisitChildren(new SMLEditObservablePropertyPanel(elt), elt.getChildren());
					return;
				} else if(eltName.equalsIgnoreCase("output")) {
					pushAndVisitChildren(new SMLEditOutputPanel(elt), elt.getChildren());
					return;
				} else if(eltName.equalsIgnoreCase("input")) {
					pushAndVisitChildren(new SMLEditInputPanel(elt), elt.getChildren());
					return;
				} else if(eltName.equalsIgnoreCase("inputs")) {
					skipTags = true;
					pushAndVisitChildren(new SMLEditInputsPanel(elt,getRefreshHandler()), elt.getChildren());
					skipTags = false;
					return;
				} else if(eltName.equalsIgnoreCase("outputs")) {
					skipTags = true;
					pushAndVisitChildren(new SMLEditOutputsPanel(elt,getRefreshHandler()), elt.getChildren());
					skipTags = false;
					return;
				} 
			} 
			
			pushAndVisitChildren(new EditElementPanel(elt), elt.getChildren());
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRenderer#visit(com.sensia.relaxNG.RNGAttribute)
	 */
	@Override
	public void visit(RNGAttribute att) {
		// handle xlink
		String nsUri = att.getNamespace();
		String name = att.getName();
		if (nsUri != null && nsUri.equalsIgnoreCase(XLINK)) {
			if(name.equals("role")){
				pushAndVisitChildren(new EditXLinkRolePanel(att),att.getChildren());
			} else if(name.equals("arcrole")) {
				pushAndVisitChildren(new EditXLinkArcrolePanel(att),att.getChildren());
			} else if(name.equals("href")) {
				pushAndVisitChildren(new EditXLinkHrefPanel(att),att.getChildren());
			} else {
				GWT.log("[WARN] Unsupported XLink element: "+name+". Skipped.");
				super.visit(att);
			}
		} else if(name.equals("referenceFrame")) {
			pushAndVisitChildren(new EditAttributeReferenceFramePanel(att),att.getChildren());
		} else if(name.equals("definition")) {
			pushAndVisitChildren(new EditAttributeDefinitionPanel(att),att.getChildren());
		} else {
			super.visit(att);
		}
	}
	
	
}
