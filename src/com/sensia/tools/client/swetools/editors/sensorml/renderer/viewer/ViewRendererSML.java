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
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewAttributeIdPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewAttributeNamePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewAttributePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewAttributeRefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewAttributeReferenceFramePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewXLinkArcrolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewXLinkHrefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.attribute.ViewXLinkRolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.gmd.GMDViewDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.gml.GMLViewDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.gml.GMLViewIdentifierPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.gml.GMLViewNamePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.gml.GMLViewPointPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.root.ViewRootPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewCapabilitiesPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewCharacteristicsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewClassificationPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewComponentPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewComponentsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewConfigurationPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewConnectionsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewContactsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewDocumentationPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewHistoryPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewIdentificationPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewInputsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewKeywordsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewLabelPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewLegalConstraintsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewLocalReferenceFramePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewMethodPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewModePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewModesPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewOutputsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewParametersPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewPositionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewTypeOfPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewValidTimePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.sml.SMLViewValuePanel;

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
			pushAndVisitChildren(new ViewRootPanel(elt),elt.getChildren());
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
			} else if(eltName.equalsIgnoreCase("Point")){
				pushAndVisitChildren(new GMLViewPointPanel(elt), elt.getChildren());
			} else {
				// handle others
				super.visit(elt);
			}
			return;
		} if (nsUri.equalsIgnoreCase(GMD)) {
				if(eltName.equals("description")) {
					pushAndVisitChildren(new GMDViewDescriptionPanel(elt), elt.getChildren());
					return;
				} else {
					// handle others
					super.visit(elt);
					return;
				}
		} else if (nsUri.equalsIgnoreCase(SML_NS_1) || nsUri.equalsIgnoreCase(SML_NS_2)) {
			if(eltName.equalsIgnoreCase("capabilities")) {
				pushAndVisitChildren(new SMLViewCapabilitiesPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("characteristics")) {
				pushAndVisitChildren(new SMLViewCharacteristicsPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("classification")) {
				pushAndVisitChildren(new SMLViewClassificationPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("components")) {
				pushAndVisitChildren(new SMLViewComponentsPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("component")) {
				pushAndVisitChildren(new SMLViewComponentPanel(elt), elt.getChildren());
				return;
			}  else if(eltName.equalsIgnoreCase("configuration")) {
				pushAndVisitChildren(new SMLViewConfigurationPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("connections")) {
				pushAndVisitChildren(new SMLViewConnectionsPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("contacts")) {
				pushAndVisitChildren(new SMLViewContactsPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("documentation")) {
				pushAndVisitChildren(new SMLViewDocumentationPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("history")) {
				pushAndVisitChildren(new SMLViewHistoryPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("identification")) {
				pushAndVisitChildren(new SMLViewIdentificationPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("inputs")) {
				pushAndVisitChildren(new SMLViewInputsPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("keywords")) {
				pushAndVisitChildren(new SMLViewKeywordsPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("legalConstraints")) {
				pushAndVisitChildren(new SMLViewLegalConstraintsPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("localReferenceFrame")) {
				pushAndVisitChildren(new SMLViewLocalReferenceFramePanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("Mode")) {
				pushAndVisitChildren(new SMLViewModePanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("modes")) {
				pushAndVisitChildren(new SMLViewModesPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("method")) {
				pushAndVisitChildren(new SMLViewMethodPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			}  else if(eltName.equalsIgnoreCase("outputs")) {
				pushAndVisitChildren(new SMLViewOutputsPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("parameters")) {
				pushAndVisitChildren(new SMLViewParametersPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("position")) {
				pushAndVisitChildren(new SMLViewPositionPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("typeOf")) {
				SMLViewTypeOfPanel typeOf = new SMLViewTypeOfPanel(elt, refreshHandler);
				pushAndVisitChildren(typeOf, elt.getChildren());
				
				// use href of typeof to update resolver remote file
				resolver.setRemoteFile(typeOf.getRemotePath());
				return;
			} else if(eltName.equalsIgnoreCase("validTime")) {
				pushAndVisitChildren(new SMLViewValidTimePanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			}
			
			//--------------------------------------
			else if(eltName.equalsIgnoreCase("description")) {
				pushAndVisitChildren(new SMLViewDescriptionPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("label")) {
				pushAndVisitChildren(new SMLViewLabelPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("value")) {
				pushAndVisitChildren(new SMLViewValuePanel(elt), elt.getChildren());
				return;
			} else {
				// handle others
				super.visit(elt);
				return;
			}
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
		} else if(name.equals("id")) {
			// skip ids
			pushAndVisitChildren(new ViewAttributeIdPanel(att),att.getChildren());
		} else {
			pushAndVisitChildren(new ViewAttributePanel(att),att.getChildren());
		}
	}
}
