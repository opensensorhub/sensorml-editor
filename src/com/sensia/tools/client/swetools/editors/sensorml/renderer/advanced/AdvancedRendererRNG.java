/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced;

import java.util.List;

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
import com.sensia.tools.client.swetools.editors.sensorml.panels.generic.GenericVerticalContainerPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.Renderer;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.attribute.AdvancedAttributePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGChoicePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGOptionalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGZeroOrMorePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDAnyURIPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDDateTimePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDDecimalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDDoublePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDIntegerPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.xsd.XSDStringPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.element.EditElementPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.value.EditValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value.ViewValuePanel;

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
public abstract class AdvancedRendererRNG extends Renderer {
	
	protected boolean skipTags = false;
	
	/**
	 * Instantiates a new RNG renderer.
	 */
	public AdvancedRendererRNG() {
		super();
		rootPanel.getPanel().addStyleName("advanced");;
	}

	public void visit(RNGTag tag) {
		tag.accept(this);
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGElement)
	 */
	@Override
	public void visit(RNGElement elt) {
		//pushAndVisitChildren(new DynamicDisclosureElementPanel(elt), elt.getChildren());
		pushAndVisitChildren(new EditElementPanel(elt,getRefreshHandler()), elt.getChildren());
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGAttribute)
	 */
	@Override
	public void visit(RNGAttribute attribute) {
		pushAndVisitChildren(new AdvancedAttributePanel(attribute), attribute.getChildren());
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
		push(new EditValuePanel(data,getRefreshHandler()));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDString)
	 */
	@Override
	public void visit(XSDString data) {
		push(new XSDStringPanel(data,getRefreshHandler()));
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
		push(new XSDDecimalPanel(data,getRefreshHandler()));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDDouble)
	 */
	@Override
	public void visit(XSDDouble data) {
		push(new XSDDoublePanel(data,getRefreshHandler()));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDInteger)
	 */
	@Override
	public void visit(XSDInteger data) {
		push(new XSDIntegerPanel(data,getRefreshHandler()));
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDAnyURI)
	 */
	@Override
	public void visit(XSDAnyURI data) {
		push(new XSDAnyURIPanel(data,getRefreshHandler()));
		
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.XSDDateTime)
	 */
	@Override
	public void visit(XSDDateTime data) {
		push(new XSDDateTimePanel(data,getRefreshHandler()));
		
	}
	
	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGChoice)
	 */
	@Override
	public void visit(RNGChoice choice) {
	    choice.combineNestedChoices();
		RNGTag selectedPattern = choice.getSelectedPattern();
		if(selectedPattern != null) {
			if(!skipTags) {
				pushAndVisitChildren(new RNGChoicePanel(choice,getRefreshHandler()), selectedPattern);
			} else {
				selectedPattern.accept(this);
			}
		} else {
			if(!skipTags) {
				push(new RNGChoicePanel(choice,getRefreshHandler()));
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGOptional)
	 */
	@Override
	public void visit(RNGOptional optional) {
		if(!skipTags) {
			push(new RNGOptionalPanel(optional,getRefreshHandler()));
		}
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
		if(!skipTags) {
			push(new RNGZeroOrMorePanel(zeroOrMore,getRefreshHandler()));
		}
		List<List<RNGTag>> patternInstances = zeroOrMore.getPatternInstances();
		int nbPattern = 0;
		for(List<RNGTag> tags : patternInstances) {
			//RNGZeroOrMorePatternPanel patternPanel = new RNGZeroOrMorePatternPanel(zeroOrMore, nbPattern++,getRefreshHandler());
			//pushAndVisitChildren(patternPanel, tags);
			this.visitChildren(tags);
		}
	}
}