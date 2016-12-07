/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.shared.GWT;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGGrammar;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.view.ViewAttributeCodePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.view.ViewAttributeDefinitionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.view.ViewAttributeNamePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.view.ViewAttributeRefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.view.ViewAttributeReferenceFramePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.document.DocumentPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.gmd.GMDUrl;
import com.sensia.tools.client.swetools.editors.sensorml.panels.gml.view.GMLDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.gml.view.GMLIdentifierPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.gml.view.GMLNamePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.SMLAxisPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.SMLComponentPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.SMLDocument;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.SMLLinkPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.SMLOriginPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.SMLTermPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.view.SMLViewObservablePropertyPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.view.ViewXLinkArcrolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.view.ViewXLinkHrefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.view.ViewXLinkRolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.EditRNGRendererSML;

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

	/** The render sections list. */
	protected Map<String,String> renderSectionsList = new HashMap<String,String>();
	
	/** The render elements. */
	protected Map<String,RENDER_ELEMENT_TYPE> renderElements= new HashMap<String,RENDER_ELEMENT_TYPE>();
	
	/**
	 * The Enum RENDER_ELEMENT_TYPE.
	 */
	protected enum RENDER_ELEMENT_TYPE {
		
		/** The line. */
		LINE,
		/** The generic horizontal. */
		GENERIC_HORIZONTAL,
		
		/** The generic vertical. */
		GENERIC_VERTICAL,
		
		GENERIC_VERTICAL_LIST,
		
		DYNAMIC_DISCLOSURE,
		
		DISCLOSURE
	}
	
	/**
	 * Instantiates a new RNG renderer sml.
	 */
	public ViewRendererSML() {
		//render section names
		/*renderSectionsList.put("identification","Identification");
		renderSectionsList.put("typeOf","Type of");
		renderSectionsList.put("characteristics","Characteristics");
		renderSectionsList.put("capabilities","Capabilities");
		renderSectionsList.put("outputs", "Outputs");
		renderSectionsList.put("classification","Classification");
		//TODO: make only one constraint section
		renderSectionsList.put("validTime","Constraints (valid time)");
		renderSectionsList.put("securityConstraints","Constraints (security)");
		renderSectionsList.put("legalConstraints","Constraints (legal)");
		renderSectionsList.put("inputs", "Inputs");
		renderSectionsList.put("localReferenceFrame", "Local Reference Frame");
		renderSectionsList.put("parameters", "Parameters");
		renderSectionsList.put("method", "Method");
		renderSectionsList.put("contacts", "Contacts");
		renderSectionsList.put("documentation", "Documentation");
		renderSectionsList.put("connections", "Connections");
		renderSectionsList.put("components", "Components");
		renderSectionsList.put("component", "Component");
		renderSectionsList.put("position", "Position");
		renderSectionsList.put("boundedBy", "Bounded By");
		renderSectionsList.put("history", "History");
		renderSectionsList.put("position", "Position");
		renderSectionsList.put("configuration", "Configuration");
		renderSectionsList.put("modes", "Modes");*/
		
		//render default defined list elements
		renderElements.put("identification", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("typeOf", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("characteristics", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("capabilities", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("outputs", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("classification", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("inputs", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("localReferenceFrame", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("parameters", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("boundedBy", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("history", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("modes", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("configuration", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("position", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("documentation", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("contacts", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("connections", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("DataRecord", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		
		/*renderElements.put("OutputList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL_LIST);
		renderElements.put("InputList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL_LIST);
		renderElements.put("IdentifierList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL_LIST);
		renderElements.put("ClassifierList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL_LIST);
		renderElements.put("ParameterList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL_LIST);
		renderElements.put("ConnectionList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("connections", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL); //TODO: should support more than one ?
		renderElements.put("CharacteristicList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL);
		renderElements.put("CapabilityList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL_LIST);
		renderElements.put("ContactList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL_LIST);
		renderElements.put("DocumentList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL_LIST);
		renderElements.put("ComponentList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL_LIST);
		renderElements.put("ContactList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL_LIST);
		renderElements.put("EventList", RENDER_ELEMENT_TYPE.GENERIC_VERTICAL_LIST);*/
		
		renderElements.put("Settings", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("input", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		//renderElements.put("parameter", RENDER_ELEMENT_TYPE.DISCLOSURE);
		renderElements.put("SpatialFrame", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("characteristic", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		renderElements.put("connection", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE); //TODO: should support more than one ?
		renderElements.put("output", RENDER_ELEMENT_TYPE.DYNAMIC_DISCLOSURE);
		
		renderElements.put("KeywordList", RENDER_ELEMENT_TYPE.GENERIC_HORIZONTAL); 
		
		//render default defined elements
		/*renderElements.put("input",RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("output",RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("parameter", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("field", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("coordinate", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("characteristic", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("identifier", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("capability", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("elementCount", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("classifier", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("axis", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("origin", RENDER_ELEMENT_TYPE.LINE);
		renderElements.put("ObservableProperty", RENDER_ELEMENT_TYPE.LINE);*/
		
		//skip list
		/*skipList.add("event");
		skipList.add("contactInfo");
		skipList.add("Security");
		skipList.add("Term");
		skipList.add("data");
		skipList.add("NormalizedCurve");
		skipList.add("function");
		skipList.add("mode");
		skipList.add("TimeInstant");
		skipList.add("time");*/
		
		//skip contact elements tags
		/*skipList.add("CI_ResponsibleParty");
		skipList.add("contactInfo");
		skipList.add("CI_Contact");
		skipList.add("phone");
		skipList.add("CI_Telephone");
		skipList.add("address");
		skipList.add("CI_Address");*/
		
		//skip documents tags
		/*skipList.add("CI_OnlineResource");
		skipList.add("linkage");*/
	}

	/** The root min level. */
	public int rootMinLevel = 1;
	
	/* (non-Javadoc)
	 * @see com.sensia.tools.client.swetools.editors.sensorml.renderer.RNGRenderer#visit(com.sensia.relaxNG.RNGGrammar)
	 */
	@Override
	public void visit(RNGGrammar grammar) { 
		super.visit(grammar);
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
			pushAndVisitChildren(new DocumentPanel(),elt.getChildren());
			return;
		}
		
		if(renderElements.containsKey(eltName)) {
			RENDER_ELEMENT_TYPE type = renderElements.get(eltName);
			
			IPanel<? extends RNGTag> panel = null;
			
			switch(type) {
				case GENERIC_VERTICAL : panel = renderVerticalPanel(elt);break;
				case GENERIC_HORIZONTAL : panel = renderHorizontalWidget(elt);break;
				case DYNAMIC_DISCLOSURE : panel = renderDynamicDisclosure(elt);break;
				case DISCLOSURE : panel = renderDisclosure(elt);break;
				//case LINE : widget = renderLineWidget(eltName, ns, TAG_TYPE.ELEMENT);break;
				default:break;
			}
			pushAndVisitChildren(panel, elt.getChildren());
			return;
		} else 
		// handle GML elements
		if (nsUri.equalsIgnoreCase(GML_NS_1) || nsUri.equalsIgnoreCase(GML_NS_2)) {
			// gml:identifier
			if(eltName.equalsIgnoreCase("identifier")){
				pushAndVisitChildren(new GMLIdentifierPanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("description")){
				pushAndVisitChildren(new GMLDescriptionPanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("name")){
				pushAndVisitChildren(new GMLNamePanel(elt), elt.getChildren());
			} else {
				visitChildren(elt.getChildren());
			}
			return;
		} else if (nsUri.equalsIgnoreCase(GMD)) {
			if(eltName.equalsIgnoreCase("URL")) {
				pushAndVisitChildren(new GMDUrl(elt), elt.getChildren());
			} else {
				super.visit(elt);
			}
		} else if (nsUri.equalsIgnoreCase(SWE_NS_1) || nsUri.equalsIgnoreCase(SWE_NS_2)) {
			super.visit(elt);
			return;
		} else if (nsUri.equalsIgnoreCase(SML_NS_1) || nsUri.equalsIgnoreCase(SML_NS_2)) {
			// handle SML element
			if(eltName.equalsIgnoreCase("ObservableProperty")) {
				pushAndVisitChildren(new SMLViewObservablePropertyPanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("Component")) {
				pushAndVisitChildren(new SMLComponentPanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("Link")) {
				pushAndVisitChildren(new SMLLinkPanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("Term")) {
				pushAndVisitChildren(new SMLTermPanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("identifier")) {
				pushAndVisitChildren(renderVerticalElementListPanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("classifier")) {
				pushAndVisitChildren(renderVerticalElementListPanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("Document")) {
				pushAndVisitChildren(new SMLDocument(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("origin")) {
				pushAndVisitChildren(new SMLOriginPanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("axis")) {
				pushAndVisitChildren(new SMLAxisPanel(elt), elt.getChildren());
			} else {
				super.visit(elt);
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
			} else {
				GWT.log("[WARN] Unsupported XLink element: "+name+". Skipped.");
				super.visit(att);
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
		} else{
			super.visit(att);
		}
	}
}
