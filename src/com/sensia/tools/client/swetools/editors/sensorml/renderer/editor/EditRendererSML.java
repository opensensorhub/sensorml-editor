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
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.element.DisclosureElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGOptionalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.sml.SMLAdvancedLabelPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.sml.SMLAdvancedValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute.EditAttributeCodePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute.EditAttributeDefinitionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute.EditAttributeNamePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute.EditAttributePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute.EditAttributeRefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute.EditAttributeReferenceFramePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute.EditXLinkArcrolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute.EditXLinkHrefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute.EditXLinkRolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.attribute.EditXLinkTitlePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gmd.GMDEditDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gmd.GMDEditUrl;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.GMLEditCoordinatesPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.GMLEditDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.GMLEditEnvelopePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.GMLEditIdentifierPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.GMLEditNamePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.GMLEditPointPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.time.GMLEditBeginPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.time.GMLEditEndPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.time.GMLEditTimeInstantPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.time.GMLEditTimePeriodPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.time.GMLEditTimePositionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.rng.EditRNGZeroOrMorePopupPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.root.EditRootPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditAxisPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditCapabilitiesPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditCapabilityPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditCharacteristicPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditCharacteristicsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditClassificationPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditClassifierPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditComponentPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditComponentsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditConfigurationPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditConnectionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditConnectionsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditContactsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditDocument;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditDocumentationPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditFeatureOfInterestPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditHistoryPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditIdentificationPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditIdentifierPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditInputPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditInputsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditKeywordsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditLegalConstraintsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditLocalReferenceFramePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditMethodPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditModePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditModesPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditObservablePropertyPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditOriginPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditOutputPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditOutputsPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditParameterPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditParametersPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditPositionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditPropertyPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditSetModePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditSetValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditSpatialFramePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditTermPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditTypeOfPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.SMLEditValidTimePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.sml.history.SMLEditEventPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.SMLEditorConstants;

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
public class EditRendererSML extends EditRendererSWE implements RNGTagVisitor {

	public EditRendererSML() {
		
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
			pushAndVisitChildren(new EditRootPanel(elt,getRefreshHandler()),elt.getChildren());
			return;
		}
		
		if(nsUri == null) {
			GWT.log("NameSpace for element "+elt.getName()+" does not exist");
			super.visit(elt);
			return;
		}
		
		if (nsUri.equalsIgnoreCase(GML_NS_1) || nsUri.equalsIgnoreCase(GML_NS_2)) {
			if(eltName.equalsIgnoreCase("description")) {
				pushAndVisitChildren(new GMLEditDescriptionPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("name")) {
				pushAndVisitChildren(new GMLEditNamePanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("identifier")) {
				pushAndVisitChildren(new GMLEditIdentifierPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("Point")) {
				pushAndVisitChildren(new GMLEditPointPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("coordinates")) {
				pushAndVisitChildren(new GMLEditCoordinatesPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("Envelope")) {
				pushAndVisitChildren(new GMLEditEnvelopePanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("TimeInstant")) {
				pushAndVisitChildren(new GMLEditTimeInstantPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("TimePeriod")) {
				pushAndVisitChildren(new GMLEditTimePeriodPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("timePosition")) {
				pushAndVisitChildren(new GMLEditTimePositionPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("begin")) {
				pushAndVisitChildren(new GMLEditBeginPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("end")) {
				pushAndVisitChildren(new GMLEditEndPanel(elt), elt.getChildren());
				return;
			}
		}
		if (nsUri.equalsIgnoreCase(SMLEditorConstants.SWE_NS_1) || nsUri.equalsIgnoreCase(SMLEditorConstants.SWE_NS_2)) {
			super.visit(elt);
			return;
		} else if (nsUri.equalsIgnoreCase(GMD)) {
			if(eltName.equalsIgnoreCase("URL")) {
				pushAndVisitChildren(new GMDEditUrl(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("description")) {
				pushAndVisitChildren(new GMDEditDescriptionPanel(elt), elt.getChildren());
			} else {
				super.visit(elt);
			}
			return;
		} else if (nsUri.equalsIgnoreCase(GCO)) {
			//super.visit(elt);
		} else if (nsUri.equalsIgnoreCase(SML_NS_1) || nsUri.equalsIgnoreCase(SML_NS_2)) {
			// handle SML element
			if(eltName.equalsIgnoreCase("ObservableProperty")) {
				pushAndVisitChildren(new SMLEditObservablePropertyPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("output")) {
				pushAndVisitChildren(new SMLEditOutputPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("input")) {
				pushAndVisitChildren(new SMLEditInputPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("inputs")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditInputsPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("Mode")) {
				pushAndVisitChildren(new SMLEditModePanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("outputs")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditOutputsPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("position")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditPositionPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("outputs")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditOutputsPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("localReferenceFrame")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditLocalReferenceFramePanel(elt,getRefreshHandler()), elt.getChildren());
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
			} else if(eltName.equalsIgnoreCase("characteristic")) {
				pushAndVisitChildren(new SMLEditCharacteristicPanel(elt,getRefreshHandler()), elt.getChildren());
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
				SMLEditTypeOfPanel typeOf = new SMLEditTypeOfPanel(elt, refreshHandler);
				pushAndVisitChildren(typeOf, elt.getChildren());
				
				// use href of typeof to update resolver remote file
				resolver.setRemoteFile(typeOf.getRemotePath());
				return;
			} else if(eltName.equalsIgnoreCase("attachedTo")) {
                pushAndVisitChildren(new DisclosureElementPanel(elt), elt.getChildren());
                return;
            } else if(eltName.equalsIgnoreCase("method")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditMethodPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("featureOfInterest")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditFeatureOfInterestPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("capabilities")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditCapabilitiesPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("parameter")) {
				pushAndVisitChildren(new SMLEditParameterPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("capability")) {
				pushAndVisitChildren(new SMLEditCapabilityPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("axis")) {
				pushAndVisitChildren(new SMLEditAxisPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("components")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditComponentsPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("component")) {
				pushAndVisitChildren(new SMLEditComponentPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("connections")) {
				skipTags = true;
				pushAndVisitChildren(new SMLEditConnectionsPanel(elt,getRefreshHandler()), elt.getChildren());
				skipTags = false;
				return;
			} else if(eltName.equalsIgnoreCase("connection")) {
				pushAndVisitChildren(new SMLEditConnectionPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("Link")) {
				//pushAndVisitChildren(new SMLEditLinkPanel(elt), elt.getChildren());
				super.visit(elt);
				return;
			} else if(eltName.equalsIgnoreCase("Term")) {
				pushAndVisitChildren(new SMLEditTermPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("identifier")) {
				pushAndVisitChildren(new SMLEditIdentifierPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("classifier")) {
				pushAndVisitChildren(new SMLEditClassifierPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("Document")) {
				pushAndVisitChildren(new SMLEditDocument(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("origin")) {
				pushAndVisitChildren(new SMLEditOriginPanel(elt,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("SpatialFrame")) {
				pushAndVisitChildren(new SMLEditSpatialFramePanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("label")) {
				pushAndVisitChildren(new SMLAdvancedLabelPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("keywords")) {
				pushAndVisitChildren(new SMLEditKeywordsPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("value")) {
				pushAndVisitChildren(new SMLAdvancedValuePanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("setValue")) {
				pushAndVisitChildren(new SMLEditSetValuePanel(elt,resolver,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("setMode")) {
				pushAndVisitChildren(new SMLEditSetModePanel(elt,resolver,getRefreshHandler()), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("property")) {
				pushAndVisitChildren(new SMLEditPropertyPanel(elt), elt.getChildren());
				return;
			} else if(eltName.equalsIgnoreCase("Event")) {
				pushAndVisitChildren(new SMLEditEventPanel(elt), elt.getChildren());
				return;
			}
		} 
		//GWT.log("[WARN] "+nsUri+":"+eltName+" is not supported yet");
		pushAndVisitChildren(new EditElementPanel(elt,getRefreshHandler()), elt.getChildren());
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
			} else if(name.equals("title")) {
				pushAndVisitChildren(new EditXLinkTitlePanel(att),att.getChildren());
			} else {
				GWT.log("[WARN] Unsupported XLink element: "+name+". Skipped.");
				pushAndVisitChildren(new EditAttributePanel(att),att.getChildren());
			}
		} else if(name.equals("referenceFrame")) {
			pushAndVisitChildren(new EditAttributeReferenceFramePanel(att),att.getChildren());
		} else if(name.equals("definition")) {
			pushAndVisitChildren(new EditAttributeDefinitionPanel(att),att.getChildren());
		} else if(name.equals("name")) {
			pushAndVisitChildren(new EditAttributeNamePanel(att),att.getChildren());
		} else if(name.equals("ref")) {
			pushAndVisitChildren(new EditAttributeRefPanel(att),att.getChildren());
		} else if(name.equals("code")) {
			pushAndVisitChildren(new EditAttributeCodePanel(att),att.getChildren());
		} else if(name.equals("id")) {
			// skip ids
		} else {
			pushAndVisitChildren(new EditAttributePanel(att),att.getChildren());
		}
	}
	
	@Override
    public void visit(RNGOptional optional) {
        
	    // to show + buttons in the main sections
        if (!optional.isSelected() && canShowOptionalContent(optional)) {
            push(new RNGOptionalPanel(optional,getRefreshHandler()));
        }
        else {
            super.visit(optional);
        }
    }
	
	@Override
    public void visit(RNGZeroOrMore zeroOrMore) {
        super.visit(zeroOrMore);
        
        // to show + buttons in the main sections
        if (canShowOptionalContent(zeroOrMore)) {
            EditRNGZeroOrMorePopupPanel patternPanel = new EditRNGZeroOrMorePopupPanel(zeroOrMore, getRefreshHandler());
            push(patternPanel);
        }
    }
	
	protected boolean canShowOptionalContent(RNGTag tag)
	{
	    /*List<RNGElement> childElts = ModelHelper.findTags(null, null, tag);
        if (!childElts.isEmpty())
        {
            String name = childElts.get(0).getName(); 
            if (name != null && (name.equalsIgnoreCase("identifier") || 
                                 name.equalsIgnoreCase("classifier") ||
                                 name.equalsIgnoreCase("characteristic") ||
                                 name.equalsIgnoreCase("capability") ||
                                 name.equalsIgnoreCase("contact") ||
                                 name.equalsIgnoreCase("input") ||
                                 name.equalsIgnoreCase("output") ||
                                 name.equalsIgnoreCase("parameter") ||
                                 name.equalsIgnoreCase("field") ||
                                 name.equalsIgnoreCase("coordinate") ||
                                 name.equalsIgnoreCase("item")) ){
                return true;
            }
        }*/
        
        return false;
	}
}
