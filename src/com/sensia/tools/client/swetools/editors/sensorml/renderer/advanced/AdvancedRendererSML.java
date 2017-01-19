/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import com.google.gwt.core.shared.GWT;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.GenericVerticalContainerPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedAttributeDefinitionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedAttributePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedAttributeReferenceFramePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedXLinkArcrolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedXLinkHrefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedXLinkRolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedXLinkTitlePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.gco.GCOAdvancedCharacterStringPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.sml.SMLAdvancedDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.sml.SMLAdvancedLabelPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.sml.SMLAdvancedLinkPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.sml.SMLAdvancedValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.GMLEditDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.GMLEditNamePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.root.EditRootPanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.NameRefResolver;
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
public class AdvancedRendererSML extends AdvancedRendererSWE implements RNGTagVisitor {
	
	/** The Constant SML_NS_1. */
	protected final static String SML_NS_1 = "http://www.opengis.net/sensorML/1.0.1";
	
	/** The Constant SML_NS_2. */
	protected final static String SML_NS_2 = "http://www.opengis.net/sensorML/2.0";
	
	/** The Constant GML_NS_1. */
	protected final static String GML_NS_1 = "http://www.opengis.net/gml";
	
	/** The Constant GML_NS_2. */
	protected final static String GML_NS_2 = "http://www.opengis.net/gml/3.2";
	
	/** The Constant GMD. */
	protected final static String GMD = "http://www.isotc211.org/2005/gmd";
	
	/** The Constant GCO. */
	protected final static String GCO = "http://www.isotc211.org/2005/gco";
	
	/** The Xlink constant. */
	protected final static String XLINK = "http://www.w3.org/1999/xlink";
	
	/** The skip list. */
	protected Set<String> skipList = new HashSet<String>();
	
	/** The root sections list. */
	protected Set<String> rootSectionsList = new HashSet<String>();
	
	
	public AdvancedRendererSML() {
		GenericVerticalContainerPanel rootAdvanced = new GenericVerticalContainerPanel();
		rootAdvanced.getPanel().addStyleName("advanced-dialog");
		
		GenericVerticalContainerPanel rootAdvanced2 = new GenericVerticalContainerPanel();
		rootAdvanced2.getPanel().add(rootAdvanced.getPanel());
		push(rootAdvanced2);
		
		rootSectionsList.add("PhysicalSystem");
		rootSectionsList.add("ProcessModel");
		rootSectionsList.add("AggregateProcess");
		rootSectionsList.add("SimpleProcess");
		rootSectionsList.add("PhysicalComponent");
		rootSectionsList.add("Component");
		
		skipList.add("OutputList");
		skipList.add("EventList");
		skipList.add("InputList");
		skipList.add("IdentifierList");
		skipList.add("ClassifierList");
		skipList.add("ParameterList");
		skipList.add("ConnectionList");
		skipList.add("CharacteristicList");
		skipList.add("CapabilityList");
		
		skipList.add("ContactList");
		skipList.add("DocumentList");
		skipList.add("ContactList");
		skipList.add("EventList");
		
		//skip sml:documents tags
		skipList.add("CI_OnlineResource");
		skipList.add("linkage");
		
		//skip sml:mode
		skipList.add("mode");
		
		//skip sml:event
		skipList.add("event");
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
		
		// handle GML elements
		if (nsUri.equalsIgnoreCase(GML_NS_1) || nsUri.equalsIgnoreCase(GML_NS_2)) {
			if(eltName.equalsIgnoreCase("description")) {
				pushAndVisitChildren(new GMLEditDescriptionPanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("name")) {
				pushAndVisitChildren(new GMLEditNamePanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("coordinates")) {
				pushAndVisitChildren(new GMLEditNamePanel(elt), elt.getChildren());
			} 
			else {
				super.visit(elt);
			}
			return;
		} else if (nsUri.equalsIgnoreCase(GMD)) {
			super.visit(elt);
			return;
		} else if (nsUri.equalsIgnoreCase(GCO)) {
			pushAndVisitChildren(new GCOAdvancedCharacterStringPanel(elt), elt.getChildren());
			return;
		} else if (nsUri.equalsIgnoreCase(SMLEditorConstants.SWE_NS_1) || nsUri.equalsIgnoreCase(SMLEditorConstants.SWE_NS_2)) {
			super.visit(elt);
			return;
		} else if (nsUri.equalsIgnoreCase(SML_NS_1) || nsUri.equalsIgnoreCase(SML_NS_2)) {
			// handle SML element
			if(eltName.equalsIgnoreCase("ObservableProperty")) {
				visitChildren(elt.getChildren());
			} else if(eltName.equalsIgnoreCase("identifier")) {
				visitChildren(elt.getChildren());
			} else if(eltName.equalsIgnoreCase("classifier")) {
				visitChildren(elt.getChildren());
			} else if(eltName.equalsIgnoreCase("description")) {
				pushAndVisitChildren(new SMLAdvancedDescriptionPanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("label")) {
				pushAndVisitChildren(new SMLAdvancedLabelPanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("value")) {
				pushAndVisitChildren(new SMLAdvancedValuePanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("Link")) {
				pushAndVisitChildren(new SMLAdvancedLinkPanel(elt), elt.getChildren());
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
				pushAndVisitChildren(new AdvancedXLinkRolePanel(att),att.getChildren());
			} else if(name.equals("arcrole")) {
				pushAndVisitChildren(new AdvancedXLinkArcrolePanel(att),att.getChildren());
			} else if(name.equals("href")) {
				pushAndVisitChildren(new AdvancedXLinkHrefPanel(att),att.getChildren());
			} else if(name.equals("title")) {
				pushAndVisitChildren(new AdvancedXLinkTitlePanel(att),att.getChildren());
			} else {
				GWT.log("[WARN] Unsupported XLink element: "+name+". Skipped.");
				super.visit(att);
			}
		} else if(name.equals("referenceFrame")) {
			pushAndVisitChildren(new AdvancedAttributeReferenceFramePanel(att),att.getChildren());
		} else if(name.equals("definition")) {
			pushAndVisitChildren(new AdvancedAttributeDefinitionPanel(att,getRefreshHandler()),att.getChildren());
		} else if(name.equals("codeSpace")) {
			pushAndVisitChildren(new AdvancedAttributePanel(att),att.getChildren());
		} else if(name.equals("definition")) {
			pushAndVisitChildren(new AdvancedAttributePanel(att),att.getChildren());
		} else if(name.equals("id")) {
			pushAndVisitChildren(new AdvancedAttributePanel(att),att.getChildren());
		} else if(name.equals("name")) {
			pushAndVisitChildren(new AdvancedAttributePanel(att),att.getChildren());
		} else {
			super.visit(att);
		}
	}
}
