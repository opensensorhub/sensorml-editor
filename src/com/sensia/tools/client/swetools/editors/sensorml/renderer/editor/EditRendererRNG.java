/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGData;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGOneOrMore;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGValue;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.relaxNG.XSDAnyURI;
import com.sensia.relaxNG.XSDBoolean;
import com.sensia.relaxNG.XSDDateTime;
import com.sensia.relaxNG.XSDDecimal;
import com.sensia.relaxNG.XSDDouble;
import com.sensia.relaxNG.XSDInteger;
import com.sensia.relaxNG.XSDString;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGChoicePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGOptionalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGZeroOrMorePatternPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.EditRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.editor.panels.rng.EditRNGZeroOrMorePopupPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.viewer.panels.value.ViewValuePanel;
import com.sensia.tools.client.swetools.editors.sensorml.utils.Utils;

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
public class EditRendererRNG extends AdvancedRendererSML {
	
	/**
	 * Instantiates a new RNG renderer.
	 */
	public EditRendererRNG() {
	}


	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGChoice)
	 */
	@Override
	public void visit(RNGChoice choice) {
		RNGTag selectedPattern = choice.getSelectedPattern();
		if(selectedPattern != null) {
			if(!skipTags) {
				pushAndVisitChildren(new RNGChoicePanel(choice,getRefreshHandler()), selectedPattern);
			} else {
				selectedPattern.accept(this);
			} 
		} else if(!skipTags) {
			push(new RNGChoicePanel(choice,getRefreshHandler()));
		}
	}

	/* (non-Javadoc)
	 * @see com.sensia.relaxNG.RNGTagVisitor#visit(com.sensia.relaxNG.RNGOptional)
	 */
	@Override
	public void visit(RNGOptional optional) {
		if(!skipTags) {
			if(optional.isSelected()){
				push(new RNGOptionalPanel(optional,getRefreshHandler()));
				this.visitChildren(optional.getChildren());
			}
		} else if(optional.isSelected()){
				this.visitChildren(optional.getChildren());
		}
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
		List<List<RNGTag>> patternInstances = zeroOrMore.getPatternInstances();
			
		int nbPattern = 0;
		for(List<RNGTag> tags : patternInstances) {
			if(!skipTags) {
				RNGZeroOrMorePatternPanel patternPanel = new RNGZeroOrMorePatternPanel(zeroOrMore, nbPattern++,getRefreshHandler());
				pushAndVisitChildren(patternPanel, tags);
			} else {
				this.visitChildren(tags);
			}
		}
		String label = Utils.findLabel(zeroOrMore);
		if(label != null && label.equalsIgnoreCase("field")){
			EditRNGZeroOrMorePopupPanel patternPanel = new EditRNGZeroOrMorePopupPanel(zeroOrMore, getRefreshHandler());
			push(patternPanel);
		} 
	}
}