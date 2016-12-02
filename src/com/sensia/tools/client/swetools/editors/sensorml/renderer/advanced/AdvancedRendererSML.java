/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.tools.client.swetools.editors.sensorml.controller.IObserver;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.document.EditDocumentPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.GenericVerticalContainerPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditObservablePropertyPanel;
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
		
		
		// handle GML elements
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
					visitChildren(elt.getChildren());
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
	
	public void reset() {
		stack = new Stack<IPanel<? extends RNGTag>>();
		
		//TODO: improve two-way root panels
		GenericVerticalContainerPanel rootAdvanced = new GenericVerticalContainerPanel();
		rootAdvanced.getPanel().addStyleName("advanced-dialog");
		
		GenericVerticalContainerPanel rootAdvanced2 = new GenericVerticalContainerPanel();
		rootAdvanced2.getPanel().add(rootAdvanced.getPanel());
		push(rootAdvanced2);
	}
}
