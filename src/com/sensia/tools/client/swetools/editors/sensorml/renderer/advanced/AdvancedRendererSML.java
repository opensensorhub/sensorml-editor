/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced;

import java.util.HashSet;
import java.util.Set;
import com.google.gwt.core.shared.GWT;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.element.AdvancedSimpleElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.gmd.GMDAdvancedObjectPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.sml.SMLAdvancedDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.sml.SMLAdvancedLabelPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.sml.SMLAdvancedLinkPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.sml.SMLAdvancedValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.GMLEditDescriptionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.gml.GMLEditNamePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.root.EditRootPanel;

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
	
	/** The skip list. */
	protected Set<String> skipList = new HashSet<String>();
	
	/** The root sections list. */
	protected Set<String> rootSectionsList = new HashSet<String>();
	
	
	public AdvancedRendererSML() {
		/*GenericVerticalContainerPanel rootAdvanced = new GenericVerticalContainerPanel();
		rootAdvanced.getPanel().addStyleName("advanced-dialog");
		
		GenericVerticalContainerPanel rootAdvanced2 = new GenericVerticalContainerPanel();
		rootAdvanced2.getPanel().addStyleName(getDefaultStyle());
		rootAdvanced2.getPanel().add(rootAdvanced.getPanel());
		push(rootAdvanced2);*/
		
	    rootSectionsList.add("SimpleProcess");
        rootSectionsList.add("AggregateProcess");
		rootSectionsList.add("PhysicalComponent");
		rootSectionsList.add("PhysicalSystem");        
		
		skipList.add("OutputList");
		skipList.add("EventList");
		skipList.add("InputList");
		skipList.add("IdentifierList");
		skipList.add("ClassifierList");
		skipList.add("ParameterList");
        skipList.add("ComponentList");
		skipList.add("ConnectionList");
		skipList.add("CharacteristicList");
		skipList.add("CapabilityList");
		skipList.add("KeywordList");		
		skipList.add("ContactList");
		skipList.add("DocumentList");
		skipList.add("ContactList");
		skipList.add("EventList");
		skipList.add("FeatureList");
		skipList.add("SpatialFrame");
		
		skipList.add("member");
        skipList.add("CI_OnlineResource");
		skipList.add("CI_RoleCode");
		skipList.add("CI_Contact");
        skipList.add("CI_Address");
        skipList.add("CI_Telephone");
		skipList.add("linkage");
		
		//skip sml:mode
		skipList.add("mode");
		
		//skip sml:event
		skipList.add("event");
	}
	
	@Override
	public void visit(RNGElement elt) {
		String eltName = elt.getName();
		String nsUri = elt.getNamespace();
		
		// skip element and visit children
		if(skipList.contains(eltName)) {
			visitChildren(elt.getChildren());
			return;
		}
				
		if (rootSectionsList.contains(eltName)) {
			pushAndVisitChildren(new EditRootPanel(elt,getRefreshHandler()),elt.getChildren());
			return;
		}
		
		if (nsUri == null) {
			GWT.log("NameSpace for element "+elt.getName()+" does not exist");
			super.visit(elt);
			return;
		}
		
		// GML namespace
		else if (nsUri.equalsIgnoreCase(GML_NS_1) || nsUri.equalsIgnoreCase(GML_NS_2)) {
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
		} 
		
		// GMD namespace
		else if (nsUri.equalsIgnoreCase(GMD)) {
		    // simple properties
            if (eltName.equals("individualName") ||
                eltName.equals("organisationName") ||
                eltName.equals("positionName") ||
                eltName.equals("voice") ||
                eltName.equals("facsimile") ||
                eltName.equals("deliveryPoint") ||
                eltName.equals("city") ||
                eltName.equals("administrativeArea") ||
                eltName.equals("postalCode") ||
                eltName.equals("country") ||
                eltName.equals("electronicMailAddress") ||
                eltName.equals("hoursOfService") ||
                eltName.equals("contactInstructions")) {
                pushAndVisitChildren(new AdvancedSimpleElementPanel(elt), elt.getChildren());
                return;
            }
            
            else if (eltName.startsWith("CI_")) {
                pushAndVisitChildren(new GMDAdvancedObjectPanel(elt), elt.getChildren());
                return;
		    }
            
			super.visit(elt);
			return;
		} 
		
		// GCO namespace
		else if (nsUri.equalsIgnoreCase(GCO)) {
			visitChildren(elt.getChildren());
			return;
		} 
		
		// SML namespace
		else if (nsUri.equalsIgnoreCase(SML_NS_1) || nsUri.equalsIgnoreCase(SML_NS_2)) {
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
			} else if(eltName.equalsIgnoreCase("keyword")) {
                pushAndVisitChildren(new AdvancedSimpleElementPanel(elt), elt.getChildren());
            } else if(eltName.equalsIgnoreCase("value")) {
				pushAndVisitChildren(new SMLAdvancedValuePanel(elt), elt.getChildren());
			} else if(eltName.equalsIgnoreCase("Link")) {
				pushAndVisitChildren(new SMLAdvancedLinkPanel(elt), elt.getChildren());
			} else {
				super.visit(elt);
			}
			return;
		} 
		
		// let superclass handle other namespaces
		else {
			super.visit(elt);
			return;
		}
	}
}
