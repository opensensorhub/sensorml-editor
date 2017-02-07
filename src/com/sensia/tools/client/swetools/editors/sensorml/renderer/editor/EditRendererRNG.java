/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are Copyright (C) 2011 Sensia Software LLC.
 All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
 ******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.renderer.editor;

import java.util.List;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGOneOrMore;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGZeroOrMore;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.AdvancedRendererSML;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGChoicePanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGOptionalPanel;
import com.sensia.tools.client.swetools.editors.sensorml.renderer.advanced.panels.rng.RNGZeroOrMorePatternPanel;

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

	@Override
	public void visit(RNGChoice choice) {
		RNGTag selectedPattern = choice.getSelectedPattern();
		
		if(selectedPattern != null ) {
			if(!skipTags) {
				pushAndVisitChildren(new RNGChoicePanel(choice,getRefreshHandler()), selectedPattern);
			} else {
				selectedPattern.accept(this);
			} 
		} else if(!skipTags) {
			push(new RNGChoicePanel(choice,getRefreshHandler()));
		}
	}

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

	@Override
	public void visit(RNGOneOrMore oneOrMore) {
		this.visit((RNGZeroOrMore) oneOrMore);
	}

	@Override
	public void visit(RNGZeroOrMore zeroOrMore) {
		
	    List<List<RNGTag>> patternInstances = zeroOrMore.getPatternInstances();
	    
	    if(!skipTags) {
            int nbPattern = 0;
            for(List<RNGTag> tags : patternInstances) {
                RNGZeroOrMorePatternPanel patternPanel = new RNGZeroOrMorePatternPanel(zeroOrMore, nbPattern++,getRefreshHandler());
                pushAndVisitChildren(patternPanel, tags);
            }
        } else {
            for(List<RNGTag> tags : patternInstances) {
                this.visitChildren(tags);
            }
        }		
	}
	
	public String getDefaultStyle() {
		return "editor";
	}
}