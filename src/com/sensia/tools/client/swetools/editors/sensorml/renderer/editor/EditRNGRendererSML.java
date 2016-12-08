/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor;

import com.google.gwt.core.shared.GWT;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit.EditAttributeDefinitionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit.EditAttributeReferenceFramePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.edit.EditElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.document.EditDocumentPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.gml.edit.GMLEditDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.gml.edit.GMLEditNamePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditCapabilitiesPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditCapabilityPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditCharacteristicsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditClassificationPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditConfigurationPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditContactsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditDocumentationPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditHistoryPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditIdentificationPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditInputPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditInputsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditKeywordsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditLegalConstraintsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditMethodPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditModesPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditObservablePropertyPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditOutputPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditOutputsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditParameterPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditParametersPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditTypeOfPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditValidTimePanel;
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
		
		if (nsUri.equalsIgnoreCase(GML_NS_1) || nsUri.equalsIgnoreCase(GML_NS_2)) {
			if(eltName.equalsIgnoreCase("description")) {
				pushAndVisitChildren(new GMLEditDescriptionPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("name")) {
				pushAndVisitChildren(new GMLEditNamePanel(elt), elt.getChildren());
				return;
			}
		}
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
			} else if(eltName.equalsIgnoreCase("keywords")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditKeywordsPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("identification")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditIdentificationPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("classification")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditClassificationPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("characteristics")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditCharacteristicsPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("contacts")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditContactsPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("documentation")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditDocumentationPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("history")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditHistoryPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("configuration")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditConfigurationPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("parameters")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditParametersPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("modes")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditModesPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("validTime")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditValidTimePanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("legalConstraints")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditLegalConstraintsPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("typeOf")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditTypeOfPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("method")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditMethodPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("capabilities")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditCapabilitiesPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("parameter")) {
				pushAndVisitChildren(new SMLEditParameterPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("capability")) {
				pushAndVisitChildren(new SMLEditCapabilityPanel(elt), elt.getChildren());
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
