/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.shared.GWT;
import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGDefine;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGGroup;
import com.sensia.relaxNG.RNGInterleave;
import com.sensia.relaxNG.RNGList;
import com.sensia.relaxNG.RNGOneOrMore;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGRef;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagVisitor;
import com.sensia.relaxNG.RNGText;
import com.sensia.relaxNG.RNGValue;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.relaxNG.XSDAnyURI;
import com.sensia.relaxNG.XSDBoolean;
import com.sensia.relaxNG.XSDDateTime;
import com.sensia.relaxNG.XSDDecimal;
import com.sensia.relaxNG.XSDDouble;
import com.sensia.relaxNG.XSDInteger;
import com.sensia.relaxNG.XSDString;
import com.sensia.tools.client.swetools.editors.sensorml.panels.IPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.EditValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.ViewValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.AttributePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit.EditAttributeDefinitionPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.base.attribute.edit.EditAttributeReferenceFramePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.document.EditDocumentPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.EditGenericListPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.rng.RNGChoicePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.rng.RNGOptionalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.rng.RNGZeroOrMorePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.sml.edit.SMLEditObservablePropertyPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.edit.EditXLinkArcrolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.edit.EditXLinkHrefPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xlink.edit.EditXLinkRolePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xsd.XSDAnyURIPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xsd.XSDDateTimePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xsd.XSDDecimalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xsd.XSDDoublePanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xsd.XSDIntegerPanel;
import com.sensia.tools.client.swetools.editors.sensorml.panels.xsd.XSDStringPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.ViewRNGRendererSML;

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
public class EditRNGRendererSML extends ViewRNGRendererSML implements RNGTagVisitor {

	protected Map<String,RENDER_ELEMENT_TYPE_EDIT> renderEditElements= new HashMap<String,RENDER_ELEMENT_TYPE_EDIT>();
	
	enum RENDER_ELEMENT_TYPE_EDIT {
		GENERIC_LIST
	}

	public EditRNGRendererSML() {
		renderEditElements.put("KeywordList", RENDER_ELEMENT_TYPE_EDIT.GENERIC_LIST); 
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
		
		if(renderElements.containsKey(eltName)) {
			RENDER_ELEMENT_TYPE_EDIT type = renderEditElements.get(eltName);
			IPanel<? extends RNGTag> panel = null;
			
			switch(type) {
				case GENERIC_LIST : panel = renderGenericListPanel(elt);break;
				default:break;
			}
			if(type == RENDER_ELEMENT_TYPE_EDIT.GENERIC_LIST) {
				push(panel);
			} else {
				pushAndVisitChildren(panel, elt.getChildren());
			}
			return;
		} else 
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
					pushAndVisitChildren(new SMLEditObservablePropertyPanel(elt), elt.getChildren());
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
	
	protected IPanel<RNGElement> renderGenericListPanel(RNGElement tag) {
		return new EditGenericListPanel(tag);
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
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGGroup)
	 */
	@Override
	public void visit(RNGGroup group) {
		this.visitChildren(group.getChildren());
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGInterleave)
	 */
	@Override
	public void visit(RNGInterleave interleave) {
		this.visitChildren(interleave.getChildren());
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGText)
	 */
	@Override
	public void visit(RNGText text) {
		// TODO Auto-generated method stub
		//push(new ValuePanel(text));
		GWT.log("into RNGText");
		//text.accept(this);
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGValue)
	 */
	@Override
	public void visit(RNGValue val) {
		push(new ViewValuePanel(val));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGList)
	 */
	@Override
	public void visit(RNGList list) {
		visitChildren(list.getChildren());
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGData)
	 */
	@Override
	public void visit(RNGData<?> data) {
		push(new EditValuePanel(data));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDString)
	 */
	@Override
	public void visit(XSDString data) {
		push(new XSDStringPanel(data));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDBoolean)
	 */
	@Override
	public void visit(XSDBoolean data) {
		GWT.log("into XSDBoolean");
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDDecimal)
	 */
	@Override
	public void visit(XSDDecimal data) {
		push(new XSDDecimalPanel(data));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDDouble)
	 */
	@Override
	public void visit(XSDDouble data) {
		push(new XSDDoublePanel(data));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDInteger)
	 */
	@Override
	public void visit(XSDInteger data) {
		push(new XSDIntegerPanel(data));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDAnyURI)
	 */
	@Override
	public void visit(XSDAnyURI data) {
		push(new XSDAnyURIPanel(data));
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDDateTime)
	 */
	@Override
	public void visit(XSDDateTime data) {
		push(new XSDDateTimePanel(data));
		
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGChoice)
	 */
	@Override
	public void visit(RNGChoice choice) {
		RNGTag selectedPattern = choice.getSelectedPattern();
		if(selectedPattern != null) {
			pushAndVisitChildren(new RNGChoicePanel(choice), selectedPattern);
		} else {
			push(new RNGChoicePanel(choice));
		}
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGOptional)
	 */
	@Override
	public void visit(RNGOptional optional) {
		push(new RNGOptionalPanel(optional));
		if(optional.isSelected()){
			this.visitChildren(optional.getChildren());
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGRef)
	 */
	@Override
	public void visit(RNGRef ref) {
		if (ref.getPattern() != null) {
			ref.getPattern().accept(this);
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGDefine)
	 */
	@Override
	public void visit(RNGDefine pattern) {
		this.visitChildren(pattern.getChildren());
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGOneOrMore)
	 */
	@Override
	public void visit(RNGOneOrMore oneOrMore) {
		this.visit((RNGZeroOrMore) oneOrMore);
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGZeroOrMore)
	 */
	@Override
	public void visit(RNGZeroOrMore zeroOrMore) {
		push(new RNGZeroOrMorePanel(zeroOrMore));
		List<List<RNGTag>> patternInstances = zeroOrMore.getPatternInstances();
		for(List<RNGTag> tags : patternInstances) {
			this.visitChildren(tags);
		}
	}
	
	protected void pushAndVisitChildren(IPanel<? extends RNGTag> widget, RNGTag tag) {
		push(widget);
		int stackSize = getStackSize();
		
		if (tag != null) {
			tag.accept(this);
		}
		if(stackSize < getStackSize()){
			IPanel<? extends RNGTag> child = pop();
			//child.setParent(widget);
			widget.addElement(child);
		}
	}
}
