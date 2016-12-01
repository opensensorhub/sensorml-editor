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
public class AdvancedRNGRendererSML extends EditRNGRendererSML implements RNGTagVisitor {
	
	public AdvancedRNGRendererSML() {
		skipList.add("KeywordList");
		GenericVerticalContainerPanel rootAdvanced = new GenericVerticalContainerPanel();
		rootAdvanced.getPanel().addStyleName("advanced-dialog");
		
		GenericVerticalContainerPanel rootAdvanced2 = new GenericVerticalContainerPanel();
		rootAdvanced2.getPanel().add(rootAdvanced.getPanel());
		push(rootAdvanced2);
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
